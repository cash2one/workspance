/*
 * 文件名称：CompProfileServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.comp.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompAccountDao;
import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.dao.comp.CompanyIndustryChainDao;
import com.zz91.ep.dao.trade.PhotoDao;
import com.zz91.ep.dao.trade.TradeBuyDao;
import com.zz91.ep.dao.trade.TradeSupplyDao;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.WebsiteProfile;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.AreaDto;
import com.zz91.ep.dto.StatisticsDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.comp.RegistDto;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.crm.CrmCompSvrService;
import com.zz91.ep.service.crm.CrmMemberService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.util.AreaUtil;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.StringUtils;

/**
 * 项目名称：中国环保网 模块编号：数据操作Service层 模块描述：公司信息实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
 * 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("compProfileService")
public class CompProfileServiceImpl implements CompProfileService {

	@Resource
	private CompProfileDao compProfileDao;
	@Resource
	private CompAccountDao compAccountDao;
	@Resource
	private TradeBuyDao tradeBuyDao;
	@Resource
	private TradeSupplyDao tradeSupplyDao;
	@Resource
	private CompanyIndustryChainDao companyIndustryChainDao;
	@Resource
	private CrmMemberService crmMemberService;
	@Resource
	private PhotoDao photoDao;
	@Resource
	private CrmCompSvrService crmCompSvrService;

	@Override                
	public List<CompProfile> queryNewestCompany(String industryCode,
			Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return compProfileDao.queryNewestCompany(industryCode, size);
	}
	
   @Override                
	public List<CompProfile> queryRecommendCompany(String categoryCode,
			Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return compProfileDao.queryRecommendCompany(categoryCode, size);
	}
   
	@Override
	public CompProfileDto queryContactByCid(Integer cid) {
		CompProfileDto compProfile = compProfileDao.queryContactByCid(cid);
		if (compProfile != null) {
			compProfile.setProvinceName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, compProfile
							.getCompProfile().getProvinceCode()));
			compProfile.setAreaName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, compProfile
							.getCompProfile().getAreaCode()));
		}
		return compProfile;
	}

	@Override
	public CompProfileDto queryMemberInfoByCid(Integer cid) {
		if (cid == null) {
			return null;
		}
		CompProfileDto compProfile = compProfileDao.queryMemberInfoByCid(cid);
		if (compProfile != null) {
			if (PAY_MEMERCODE.equals(compProfile.getCompProfile()
					.getMemberCode())) {
				compProfile.setMemberName(PAY_MEMERNAME);
			} else {
				compProfile.setMemberName(DEFAULT_MEMERNAME);
			}
			compProfile.setProvinceName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, compProfile
							.getCompProfile().getProvinceCode()));
			compProfile.setAreaName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, compProfile
							.getCompProfile().getAreaCode()));
			compProfile.setIndustryName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_TRADE, compProfile
							.getCompProfile().getIndustryCode()));
			compProfile.setCompAccount(compAccountDao.queryAccountInfoByCid(cid));
		}
		return compProfile;
	}

	@Override
	public Boolean regist(CompProfile profile, CompAccount account) {
		boolean result=false;
		do {
			if(profile == null || account == null){
				break;
			}
			
			//初始化
			if(StringUtils.isEmpty(profile.getRegisterCode())){
				profile.setRegisterCode("1"); //默认注册来源
			}
			profile.setMemberCode("10011000"); //设置默认会员类型
			profile.setMemberCodeBlock("");
			account.setName("");
			
			try {
				account.setPassword(MD5.encode(account.getPasswordClear()));
			} catch (NoSuchAlgorithmException e) {
				break;
			} catch (UnsupportedEncodingException e) {
				break;
			}
			
			//注册公司
			Integer cid = compProfileDao.insertCompProfile(profile);
			if(cid == null || cid.intValue() <= 0){
				break;
			}
			
			//注册账户
			account.setCid(cid);
			Integer aid = compAccountDao.insertCompAccount(account);
			if(aid == null || aid.intValue() <= 0){
				break;
			}
			
			result=true;
			
		} while (false);
		
		return result;
		
		
		
//		Integer id = null;
//		do {
//			String captcha = String.valueOf(MemcachedUtils.getInstance()
//					.getClient().get(verifyCodeKey));
//			if (captcha == null) {
//				break;
//			}
//			if (!captcha.equals(verifyCode)) {
//				break;
//			}
//			MemcachedUtils.getInstance().getClient().delete(verifyCodeKey);
//			// 编辑空的公司信息
//			CompProfile compProfile = new CompProfile();
//			if (mainProduct == 1) {
//				compProfile.setMainSupply((short) 1);
//				compProfile.setMainBuy((short) 0);
//			} else {
//				compProfile.setMainSupply((short) 0);
//				compProfile.setMainBuy((short) 1);
//			}
//
//			// 普通会员
//			compProfile.setName("");
//			compProfile.setMemberCode("10011000");
//			compProfile.setMemberCodeBlock("");
//			// 创建公司信息
//			Integer cid = compProfileDao.insertCompProfile(compProfile);
//			if (cid != null && cid > 0) {
//				// 编辑帐号信
//				try {
//					CompAccount compAccount = new CompAccount();
//					compAccount.setCid(cid);
//					compAccount.setAccount(username);
//					compAccount.setEmail(email);
//					compAccount.setName("");
//					compAccount.setPasswordClear(password);
//					compAccount.setMobile(mobile);
//					compAccount.setQq(QQ);
//					compAccount.setPassword(MD5.encode(password));
//					id = compAccountDao.insertCompAccount(compAccount);
//				} catch (NoSuchAlgorithmException e) {
//					e.printStackTrace();
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
//		} while (false);
//
//		return id;
	}

	@Override
	public boolean updateRegistInfo(RegistDto registDto, Integer compProfileId,
			Integer[] industryChain) {
		boolean isSuccess = false;
		do {
			CompAccount account = new CompAccount();
			account.setId(registDto.getUserid());// 用户id
			account.setName(registDto.getLinkname());// 用户名
			account.setSex(registDto.getSex());
			account.setMobile(registDto.getMobile());
			account.setPhoneCountry(registDto.getPhoneCountry());
			account.setPhoneArea(registDto.getPhoneArea());
			account.setPhone(registDto.getPhone());
			account.setFaxCountry(registDto.getFaxCountry());
			account.setFaxArea(registDto.getFaxArea());
			account.setFax(registDto.getFax());
			account.setContact(registDto.getLinkother());
			account.setDept(registDto.getDepartment());
			account.setPosition(registDto.getOffice());

			Integer count = compAccountDao.updateBaseCompAccount(account);
			if (count == null || count < 1) {
				break;
			}
			CompProfile compProfile = new CompProfile();
			compProfile.setName(registDto.getCompany());
			compProfile.setProvinceCode(registDto.getProvinceCode());
			compProfile.setAreaCode(registDto.getAreaCode());
			compProfile.setAddress(registDto.getAddress());
			compProfile.setIndustryCode(registDto.getIndustryCode());
			compProfile.setDetails(registDto.getDetails());
			compProfile.setMainProductBuy(registDto.getMainProductBuy());
			compProfile.setMainProductSupply(registDto.getMainProductSupply());
			compProfile.setBusinessCode(registDto.getBusiness());
			compProfile.setDomain(registDto.getComurl());
			compProfile.setFunds(registDto.getRegcapital());
			compProfile.setLegal(registDto.getLegal());
			compProfile.setId(compProfileId);
			if (StringUtils.isNotEmpty(compProfile.getDetails())) {
				String string = Jsoup.clean(compProfile.getDetails(),
						Whitelist.none());
				compProfile.setDetailsQuery(string);
			}
			Integer compcount = compProfileDao
					.updateBaseCompProfile(compProfile);

			if (compcount == null || compcount < 1) {
				break;
			}
			
		
			
			if(industryChain!=null){
		
			try {
				for (int i = 0; i < industryChain.length; i++) {
					Integer num = companyIndustryChainDao.insertIndustryChain(
							industryChain[i], compProfileId);

					if (num < 1) {
						throw new RuntimeException();
					}
				}


			} catch (Exception e) {
				
				break;
				
			}
			
		}
			isSuccess = true;
			
	} while (false);
		
		return isSuccess;
}

	@Override
	public CompProfile getCompProfileById(Integer id) {
		return compProfileDao.getCompProfileById(id);
	}

	@Override
	public StatisticsDto statisticsMessage(Integer uid) {
		if (uid == null) {
			return null;
		} else {
			StatisticsDto staDto = new StatisticsDto();
			staDto.setMember_time(compAccountDao.differenceTime(uid));
			staDto.setAltogetherBuyMessage(tradeBuyDao.getTradeBuyId(uid));
			staDto.setAltogetherSupplyMessage(tradeSupplyDao
					.getTradeSupplyCount(uid, null));
			staDto.setSupplyCheckedTotal(tradeSupplyDao.getTradeSupplyCount(
					uid, 1));
			return staDto;
		}

	}

	@Override
	public Boolean updateBaseCompProfile(CompProfile comp,
			Integer[] industryChain) {
		Assert.notNull(comp, "the comp can not be null");
		Assert.notNull(comp.getId(), "the comp.getId() can not be null");
		Assert.notNull(comp.getName(), "the comp.getName() can not be null");
		// 设置详细信息的查询文本（提取详细信息的部分纯文本信息）
		Boolean success = false;
		if (StringUtils.isNotEmpty(comp.getDetails())) {
			String string = Jsoup.clean(comp.getDetails(), Whitelist.none());
			comp.setDetailsQuery(string);
		}
		// 产业链
	
		List<Integer> ids = companyIndustryChainDao.queryIdByCid(comp.getId());
			
		do {
			Integer count = compProfileDao.updateBaseCompProfile(comp);

			if(count == null || count < 1){
				break;
			}
			
			success = true;
			
			if(industryChain==null){
				
				for (int i = 0; i < ids.size(); i++) {
					companyIndustryChainDao.updateCompanyIndustryChain(
							ids.get(i), null, (short) 1);

				}
				break;
			}
			
			int num = ids.size() - industryChain.length;
			if (num == 0) {
				for (int i = 0; i < industryChain.length; i++) {
					companyIndustryChainDao.updateCompanyIndustryChain(
							ids.get(i), industryChain[i], (short) 0);
				}
				break;
			}

			if (num > 0) {

				for (int i = 0; i < ids.size(); i++) {

					if (i < industryChain.length) {

						companyIndustryChainDao.updateCompanyIndustryChain(
								ids.get(i), industryChain[i], (short) 0);
						continue;
					}

					companyIndustryChainDao.updateCompanyIndustryChain(
							ids.get(i), null, (short) 1);

				}

				break;
			}

			if (num < 0) {
				for (int i = 0; i < industryChain.length; i++) {

					if (i < ids.size()) {

						companyIndustryChainDao.updateCompanyIndustryChain(
								ids.get(i), industryChain[i], (short) 0);
						continue;
					}

					companyIndustryChainDao.insertIndustryChain(
							industryChain[i], comp.getId());

				}

				break;
			}
		} while (false);
		return success;
	}

	public List<CompProfile> queryImpCompProfile(Integer maxId) {
		return compProfileDao.queryImpCompProfile(maxId);
	}

	@Override
	public Integer updateMainProduct(Integer cid, String mainProductSupply,
			String mainProductBuy) {
		return compProfileDao.updateMainProduct(cid, mainProductSupply,
				mainProductBuy);
	}

	@Override
	public String queryCssStyleByCid(Integer companyId) {
		return compProfileDao.queryCssStyleByCid(companyId);
	}

	@Override
	public Integer updateStyle(String type, Integer cid) {
		if (type == null) {
			type = "advanced";
		}
		// 查看是否存在用户配置
		Integer count = compProfileDao.queryWebsiteConfigCount(cid);
		WebsiteProfile profile = new WebsiteProfile();
		profile.setCid(cid);
		profile.setCssStyle(type);
		Integer i=0;
		if (count > 0) {
			// 有-更新
			i = compProfileDao.updateCompanyStyle(profile);
		} else {
			// 无-创建
			i = compProfileDao.insertCompanyStyle(profile);
		}
		return i;
	}

//	@Override
//	public Integer queryIdByDomainOrDomainTwo(String domain,String domainTwo) {
//		return compProfileDao.queryIdByDomainOrDomainTwo(domain,domainTwo);
//	}

	@Override
	public CompProfile queryCompProfileById(Integer cid) {
		return compProfileDao.queryCompProfileById(cid);
	}

	@Override
	public void updateGmtModified(Integer cid) {
		compProfileDao.updateGmtModifiedById(cid);
	}

	@Override
	public CompProfile queryShortCompProfileById(Integer id) {
		return compProfileDao.queryShortCompProfileById(id);
	}

	@Override
	public String queryNameById(Integer id) {
		return compProfileDao.queryNameById(id);
	}

	@Override
	public Integer queryCompCountById(Integer cid) {
		return compProfileDao.queryCompCountById(cid);
	}

	@Override
	public String queryMemberCodeById(Integer id) {
		return compProfileDao.queryMemberCodeById(id);
	}

	@Override
	public Integer initCid(String serverName, Integer cid, Map<String, Object> out) {
		
		if(cid!=null && cid.intValue()>0){
			out.put("mid", cid);
			return cid;
		}
		
		//排除www,test,.....
		if(StringUtils.isNotEmpty(serverName)  && isDomainTwo(serverName) ){
			if(serverName.endsWith(".huanbao.com")){
				serverName = serverName.substring(0, serverName.lastIndexOf(".huanbao.com"));
				//二级域名
				return compProfileDao.queryIdByDomainOrDomainTwo(null, serverName);
			}else{
				//一级域名
				return compProfileDao.queryIdByDomainOrDomainTwo(serverName, null);
			}
		}
		return null;
	}

	@Override
	public boolean initEsite(Integer cid, String serverName, Map<String, Object> out) {
		do {
			CompProfile profile = compProfileDao.queryCompProfileById(cid);
			if(profile==null){
				break;
			}
			if(StringUtils.isNotEmpty(profile.getMemberCodeBlock())){
				break;
			}
			//非高会，且使用二级域名
//			if(!CompProfileService.PAY_MEMERCODE.equals(profile.getMemberCode()) && 
//					isDomainTwo(serverName)){
//				break;
//			}
			
			CompAccount account = compAccountDao.queryContactByCid(cid);
			if(account == null){
				break;
			}
			
			if(CompProfileService.PAY_MEMERCODE.equals(profile.getMemberCode())){
				out.put("esiteProfile", compProfileDao.querySiteProfile(cid));
			}
			
			AreaDto area = AreaUtil.buildArea(profile.getAreaCode());
			out.put("areaInfo", area);
			out.put("memberInfo", crmMemberService.queryName(profile.getMemberCode()));
			
			// 计算中环通会员年限
			out.put("memberYear", crmCompSvrService.queryYearByCid(cid));
			
			List<Photo> photoList = photoDao.queryPhotoByTargetType(PhotoService.TARGET_LOGO, cid, 1);
			if(photoList!= null && photoList.size()>0){
				out.put("logoPhoto", photoList.get(0));
			}
			
			out.put("profile", profile);
			out.put("account", account);
			
			return true;
		} while (false);
		
		return false;
	}
	
	private Integer intervalYear(Date from, Date to){
		Calendar srcCal = Calendar.getInstance();
        srcCal.setTime(from);
        Calendar dstCal = Calendar.getInstance();
        dstCal.setTime(to);

        // 比较年月日
        int year = dstCal.get(Calendar.YEAR) - srcCal.get(Calendar.YEAR);
        return year;
	}
	
	private boolean isDomainTwo(String domain){
		if(domain == null){
			return false;
		}
		
		if(domain.endsWith(".huanbao.com")){
			String d2 = domain.substring(0, domain.indexOf(".huanbao.com"));
			Set<String> exclude = new HashSet<String>();
			exclude.add("www");
			exclude.add("www1");
//			exclude.add("test");
			if(exclude.contains(d2)){
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public Boolean regists(CompProfile profile, CompAccount account) {
		//注册公司
		boolean result=false;
		do {
			
			if(profile == null || account == null){
				break;
			}
			
			//初始化
			if(StringUtils.isEmpty(profile.getRegisterCode())){
				profile.setRegisterCode("1"); //默认注册来源
			}
			profile.setMemberCode("10011000"); //设置默认会员类型
			profile.setMemberCodeBlock("");			
			try {
				account.setPassword(MD5.encode(account.getPasswordClear()));
			} catch (NoSuchAlgorithmException e) {
				break;
			} catch (UnsupportedEncodingException e) {
				break;
			}
			
			//注册公司
			Integer cid = compProfileDao.insertCompProfiles(profile);
			if(cid == null || cid.intValue() <= 0){
				break;
			}
			
			//注册账户
			account.setCid(cid);
			Integer aid = compAccountDao.insertCompAccounts(account);
			if(aid == null || aid.intValue() <= 0){
				break;
			}
			
			result=true;
			
		} while (false);
		
		return result;
		
	}		
}