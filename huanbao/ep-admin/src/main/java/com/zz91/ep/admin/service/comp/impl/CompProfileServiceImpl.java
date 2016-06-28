/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.comp.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.ep.admin.dao.comp.CompAccountDao;
import com.zz91.ep.admin.dao.comp.CompProfileDao;
import com.zz91.ep.admin.dao.crm.CRMMemberDao;
import com.zz91.ep.admin.service.comp.CompProfileService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.CompRecommend;
import com.zz91.ep.domain.comp.WebsiteStatistics;
import com.zz91.ep.domain.crm.CrmCompany;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.comp.CompanySearchDto;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author totly
 * 
 *         created on 2011-9-16
 */
@Transactional
@Service("compProfileService")
public class CompProfileServiceImpl implements CompProfileService {

	@Resource
	private CompProfileDao compProfileDao;
	@Resource
	private CRMMemberDao crmMemberDao;
	@Resource
	private CompAccountDao compAccountDao;

	// @Override
	// public String queryCompDetailsById(Integer cid) {
	// Assert.notNull(cid, "the cid can not be null");
	// return compProfileDao.queryCompDetailsById(cid);
	// }

	@Override
	public CompProfile queryCompProfileById(Integer cid) {
		return compProfileDao.queryCompProfileById(cid);
	}

	// @Override
	// public String getMemberCodeByCompanyId(Integer companyId) {
	// String memberCode= null;
	// try {
	// if ( companyId != null ) {
	// CompProfile compProfile = compProfileDao.queryCompProfileById(companyId);
	// memberCode = compProfile.getMemberCode();
	// } else {
	// return memberCode;
	// }
	// } catch (Exception e) {
	// //异常处理待添加
	// }
	// return memberCode;
	// }

	// @Override
	// public Integer updateBaseCompProfile(CompProfile comp) {
	// Assert.notNull(comp, "the comp can not be null");
	// Assert.notNull(comp.getId(), "the comp.getId() can not be null");
	// Assert.notNull(comp.getName(), "the comp.getName() can not be null");
	// // 设置详细信息的查询文本（提取详细信息的部分纯文本信息）
	// if (StringUtils.isNotEmpty(comp.getDetails())) {
	// String string = Jsoup.clean(comp.getDetails(), Whitelist.none());
	// comp.setDetailsQuery(string);
	// }
	// return compProfileDao.updateBaseCompProfile(comp);
	// }

	@Override
	public CompProfile queryFullProfile(Integer cid) {
		CompProfile compProfile = compProfileDao.queryFullProfile(cid);
		CompAccount compAccount=compAccountDao.queryCompAccountByCid(cid);
		if (StringUtils.isEmpty(compProfile.getDetailsQuery())) {
			if(StringUtils.isEmpty(compProfile.getMainProductSupply()) && StringUtils.isNotEmpty(compAccount.getContact())){
				compProfile
				.setDetailsQuery(compProfile.getName()
						+ "是中国环保注册会员，"
						+"联系人："+compAccount.getContact()
						+"。欢迎与我们联系洽谈合作");
			}else if(StringUtils.isNotEmpty(compProfile.getMainProductSupply()) && StringUtils.isEmpty(compAccount.getContact())){
				compProfile
				.setDetailsQuery(compProfile.getName()
						+ "是中国环保注册会员，该公司主营产品："
						+ compProfile.getMainProductSupply()
						+"。欢迎与我们联系洽谈合作");
			}else if(StringUtils.isEmpty(compProfile.getMainProductSupply()) && StringUtils.isEmpty(compAccount.getContact())){
				compProfile
				.setDetailsQuery(compProfile.getName()
						+ "是中国环保注册会员"
						+"。欢迎与我们联系洽谈合作");
			}else{
				compProfile
				.setDetailsQuery(compProfile.getName()
						+ "是中国环保注册会员，该公司主营产品："
						+compProfile.getMainProductSupply()
						+",联系人："+compAccount.getContact()
						+"。欢迎与我们联系洽谈合作");
			}
		}if(StringUtils.isEmpty(compProfile.getDetails())){
			if(StringUtils.isEmpty(compProfile.getMainProductSupply()) && StringUtils.isNotEmpty(compAccount.getContact())){
				compProfile
				.setDetails(compProfile.getName()
						+ "是中国环保注册会员，"
						+"联系人："+compAccount.getContact()
						+"。欢迎与我们联系洽谈合作");
			}
			if(StringUtils.isNotEmpty(compProfile.getMainProductSupply()) && StringUtils.isEmpty(compAccount.getContact())){
				compProfile
				.setDetails(compProfile.getName()
						+ "是中国环保注册会员，该公司主营产品："
						+ compProfile.getMainProductSupply()
						+"。欢迎与我们联系洽谈合作");
			}else if(StringUtils.isEmpty(compProfile.getMainProductSupply()) && StringUtils.isEmpty(compAccount.getContact())){
				compProfile
				.setDetails(compProfile.getName()
						+ "是中国环保注册会员"
						+"。欢迎与我们联系洽谈合作");
			}else{
				compProfile
				.setDetails(compProfile.getName()
						+ "是中国环保注册会员，该公司主营产品："
						+compProfile.getMainProductSupply()
						+",联系人："+compAccount.getContact()
						+"。欢迎与我们联系洽谈合作");
			}
		}
		return compProfile;
	}

	// @Override
	// public CompProfileDto queryDetailsByCompanyId(Integer compId) {
	// return compProfileDao.queryDetailsByCompId(compId);
	// }

	@Override
	public Integer insertCompProfile(CompProfile compProfile) {
		return compProfileDao.insertCompProfile(compProfile);
	}

	@Override
	public Integer updateCompProfile(CompProfile compProfile) {
	    if (StringUtils.isNotEmpty(compProfile.getMemberCodeBlock()) && compProfile.getMemberCodeBlock() != null) {
	        compProfile.setDelStatus(1);
        }
		return compProfileDao.updateCompProfile(compProfile);
	}

	@Override
	public PageDto<CompProfileDto> pageCompDetails(
			PageDto<CompProfileDto> page, String name, String memberCode,String from,String to,
			String registerCode, String phone, String businessCode,
			String industryCode, Integer delStatus, String memberCodeBlock,
			String email, String recommendCode, String account,
			Integer otherSearch, Short serviceType,
			String subnetCategory, Integer messagetime, Short chainId) {

		CompProfile profile = new CompProfile();
		if (StringUtils.isNotEmpty(name) && name != null) {
			profile.setName(name);
		}
		if (StringUtils.isNotEmpty(memberCode) && memberCode != null) {
			profile.setMemberCode(memberCode);
		}
		if (StringUtils.isNotEmpty(registerCode) && registerCode != null) {
			profile.setRegisterCode(registerCode);
		}
		if (StringUtils.isNotEmpty(businessCode) && businessCode != null) {
			profile.setBusinessCode(businessCode);
		}
		if (StringUtils.isNotEmpty(industryCode) && industryCode != null) {
			profile.setIndustryCode(industryCode);
		}
		if (delStatus != null) {
			profile.setDelStatus(delStatus);
		}
		if (StringUtils.isNotEmpty(memberCodeBlock) && memberCodeBlock != null) {
			profile.setMemberCodeBlock(memberCodeBlock);
		}
		if (serviceType != null) {
			if (serviceType == 0) {
			    profile.setMainSupply((short) 1);
			} else if (serviceType == 1) {
			    profile.setMainBuy((short) 1);
			} else if (serviceType == 2) {
			    profile.setMainSupply((short) 1);
			    profile.setMainBuy((short) 1);
			} else {
			    profile.setMainSupply((short) 0);
                profile.setMainBuy((short) 0);
			}
		}
		
		if (chainId == null) {
			if (page.getSort() == null) {
				page.setSort("ca.gmt_register");
			}
			if (page.getDir() == null) {
				page.setDir("desc");
			}
		}
		List<CompProfileDto> dto = compProfileDao.queryCompProfile(page,
				profile,from,to, phone, email, recommendCode, account, otherSearch,
				subnetCategory, messagetime, chainId);
		for (CompProfileDto compDto : dto) {
			CompProfile compProfile = compDto.getCompProfile();
			String status = "";
			if (StringUtils.isNotEmpty(compProfile.getMemberCodeBlock())
					&& compProfile.getMemberCodeBlock() != null) {
			    status = "(已冻结)";
				
			}
			if (compProfile.getDelStatus()==1) {
			    status += "(已删除)";
			}
			compProfile.setName(compProfile.getName() + status);
		}

		page.setRecords(dto);
		page.setTotals(compProfileDao.queryCompProfileCount(profile,from,to, phone,
				email, recommendCode, account, otherSearch, subnetCategory,
				messagetime, chainId));
		return page;
	}

	// @Override
	// public Integer updateMeberCodeBlockById(Integer cid, String block) {
	// Assert.notNull(cid, "the cid can not be null");
	// return compProfileDao.updateMeberCodeBlockById(cid, block);
	// }

	@Override
	public CompProfileDto builDto(CompProfile profile) {
		CompProfileDto dto = new CompProfileDto();
		if (profile.getMainBuy() == 0 && profile.getMainSupply() == 0) {
		    dto.setServiceType((short) 3);
		} else if (profile.getMainBuy() == 1 && profile.getMainSupply() == 1) {
		    dto.setServiceType((short) 2);
		} else if (profile.getMainBuy() == 1 && profile.getMainSupply() == 0) {
		    dto.setServiceType((short)1);
		} else if (profile.getMainBuy() == 0 && profile.getMainSupply() == 1) {
		    dto.setServiceType((short)0);
		}
		dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA,
				profile.getAreaCode()));
		dto.setProvinceName(CodeCachedUtil.getValue(
				CodeCachedUtil.CACHE_TYPE_AREA, profile.getProvinceCode()));
		dto
				.setMemberName(crmMemberDao.queryNameByCode(profile
						.getMemberCode()));
		dto.setCompProfile(profile);
		return dto;
	}

	// @Override
	// public PageDto<CompanySearchDto> pageCompanyBySearch(SearchCompanyDto
	// search,
	// PageDto<CompanySearchDto> page) throws SolrServerException {
	// SolrServer server = SorlUtil.getInstance().getSolrServer("/company");
	// SolrQuery query = new SolrQuery();
	// //关键字不为空时，有高亮显示
	// if (StringUtils.isNotEmpty(search.getKeywords())) {
	// String keywords = buildKeyWord(search.getKeywords());
	// if (StringUtils.isEmpty(keywords)) {
	// return page;
	// }
	// query.setQuery(keywords);
	// query.setHighlight(true);
	// query.addHighlightField("name");
	// query.addHighlightField("detailsQuery");
	// query.setHighlightSimplePre("<em>");
	// query.setHighlightSimplePost("</em>");
	// } else {
	// query.setQuery("*:*");
	// }
	// query.addFilterQuery("delStatus:0");
	// if (StringUtils.isNotEmpty(search.getBusinessCode())) {
	// query.addFilterQuery("businessCode:"+search.getBusinessCode());
	// }
	// if (StringUtils.isNotEmpty(search.getIndustryCode())) {
	// query.addFilterQuery("industryCode:"+search.getIndustryCode());
	// }
	// if (StringUtils.isNotEmpty(search.getAreaCode())) {
	// query.addFilterQuery("areaCode:"+search.getAreaCode());
	// }
	// if (StringUtils.isNotEmpty(search.getProvinceCode())) {
	// query.addFilterQuery("provinceCode:"+search.getProvinceCode());
	// }
	// query.addSortField("memberCode", ORDER.desc);
	// query.addSortField("score", ORDER.desc);
	// query.setStart(page.getStart());
	// query.setRows(page.getLimit());
	// QueryResponse rsp = server.query(query);
	// List<CompanySearchDto> beans = rsp.getBeans(CompanySearchDto.class);
	// page.setTotals((int)rsp.getResults().getNumFound());
	// //高亮显示(替换普通文本)
	// if (StringUtils.isNotEmpty(search.getKeywords())) {
	// Map<String,Map<String,List<String>>> high = rsp.getHighlighting();
	// for (CompanySearchDto bean : beans) {
	// Map<String,List<String>> root = high.get(bean.getId().toString());
	// if ( root != null) {
	// if (root.get("name") != null) {
	// bean.setName(root.get("name").get(0));
	// }
	// if (root.get("detailsQuery") != null) {
	// bean.setDetailsQuery(root.get("detailsQuery").get(0));
	// }
	// }
	// }
	// }
	// page.setRecords(buildBeans(beans));
	// return page;
	// }

	private List<CompanySearchDto> buildBeans(List<CompanySearchDto> beans) {
		for (CompanySearchDto bean : beans) {
			bean.setProvinceName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, bean.getProvinceCode()));
			bean.setAreaName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, bean.getAreaCode()));
		}
		return beans;
	}

	private String buildKeyWord(String keywords) {
		// `~!@#$%^&*()+=|{}':;',\\[\\].<>/?
		String regEx = "[~!@#$%^&*()+=|{}':;',\\[\\].<>/?]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(keywords);
		return m.replaceAll("").trim();
	}

	// @Override
	// public List<CompProfile> queryCompProfileByCategory(String
	// parentCode,Integer size) {
	// Assert.notNull(parentCode, "the parentCode can not be null");
	// if(size == null) {
	// size = 5;
	// }
	// return compProfileDao.queryCompProfileByCategory(parentCode,size);
	// }

	@Override
	public List<CompProfileDto> queryCompanyByEmail(String email) {
		return compProfileDao.queryCompanyByEmail(email);
	}

	@Override
	public Integer updateDelStatus(Integer id, Integer delStatus) {
		return compProfileDao.updateDelStatus(id, delStatus);
	}

	@Override
	public Integer updateMemberCodeBlock(Integer id, String memberCodeBlock) {
		return compProfileDao.updateMemberCodeBlock(id, memberCodeBlock);
	}

	@Override
	public List<CrmCompany> queryTodayUpdateCompany(Date date, Integer start,
			Integer limit) throws ParseException {
		Date date2 = DateUtil.getDateAfterDays(date, -1);
		String dateStr = DateUtil.toString(date2, "yyyy-MM-dd");
		return compProfileDao.queryTodayUpdateCompany(dateStr, start, limit);
	}

	@Override
	public Integer updateMemberCode(String code, Integer companyId) {
		return compProfileDao.updateMeberCodeById(code, companyId);
	}

	@Override
	public Boolean validateDomain(Integer companyId, String domain) {
		if (companyId == null || companyId.intValue() == 0) {
			return false;
		}
		Integer cid = compProfileDao.queryCidByDomainTwo(domain);
		if (cid == null || cid.intValue() == companyId) {
			return true;
		}
		return false;
	}

	@Override
	public List<CompProfile> queryNewestComp(Integer size, String week) {
		if (size == null) {
			size = 10;
		}
		return compProfileDao.queryNewestComp(size, week);
	}

	@Override
	public Integer cancelRecommendComp(Integer id) {
		Integer rtnInteger = 0;
		if (id != null) {
			rtnInteger = compProfileDao.cancelRecommendComp(id);
		}
		return rtnInteger;
	}

	@Override
	public Integer updateCompRecommend(Integer id, String type) {
		Integer rtnInteger = 0;
		if (id != null && StringUtils.isNotEmpty(type)) {
			CompRecommend compRecommend = compProfileDao
					.queryRecommendByCidAndType(id, type);
			if (compRecommend != null) {
				rtnInteger = 1;
			} else {
				rtnInteger = compProfileDao.insertCompRecommend(id, type);
			}
		}
		return rtnInteger;
	}

	@Override
	public List<CompProfileDto> queryCommonCompByContacts(Integer id) {
		CompAccount account = compAccountDao.queryMobileAndPhoneByCid(id);
		if (account == null) {
			return null;
		}
		if (StringUtils.isNotEmpty(account.getMobile())
				|| StringUtils.isNotEmpty(account.getPhone())) {
			return compProfileDao.queryCommCompanyByContact(id, account
					.getMobile(), account.getPhone());
		}
		return null;
	}

	// @Override
	// public EsiteMemberDto queryMemberInfoByCid(Integer cid) {
	// return compProfileDao.queryMemberInfoByCid(cid);
	// }

	// @Override
	// public Integer updateMainProduct(Integer cid,
	// String mainProductSupply, String mainProductBuy) {
	// return
	// compProfileDao.updateMainProduct(cid,mainProductSupply,mainProductBuy);
	// }

	// @Override
	// public Integer insertCompanyStyle(WebsiteProfile profile) {
	// return compProfileDao.insertCompanyStyle(profile);
	// }

	// @Override
	// public Integer queryWebsiteConfigCount(Integer companyId) {
	// return compProfileDao.queryWebsiteConfigCount(companyId);
	// }

	// @Override
	// public Integer updateCompanyStyle(WebsiteProfile profile) {
	// return compProfileDao.updateCompanyStyle(profile);
	// }

	// @Override
	// public String queryCssStyleByCid(Integer companyId) {
	// return compProfileDao.queryCssStyleByCid(companyId);
	// }

	@Override
	public Integer queryMaxId() {
		return compProfileDao.queryMaxId();
	}

	@Override
	public PageDto<WebsiteStatistics> pageWebsiteStatistics(
			PageDto<WebsiteStatistics> page) {
		List<WebsiteStatistics> web = compProfileDao
				.queryWebsiteStatistics(page);
		page.setRecords(web);
		page.setTotals(compProfileDao.queryWebsiteStatisticsCount());
		return page;
	}

	@Override
	public Integer queryTodayUpdateCompanyCount(Date date) {
		Date date2 = DateUtil.getDateAfterDays(date, -1);
		String dateStr = DateUtil.toString(date2, "yyyy-MM-dd");
		return compProfileDao.queryTodayUpdateCompanyCount(dateStr);
	}

	@Override
	public List<CrmCompany> queryCompProfile(Integer start, Integer limit,
			String from, String to) {
		List<CrmCompany> cList = compProfileDao.queryCrmCompany(start, limit,
				from, to);
		CompAccount account = null;
		for (CrmCompany comp : cList) {
			if (StringUtils.isNotEmpty(comp.getDetails())) {
				comp.setDetails(comp.getDetails().replace("\"", " ").replace(
						"&quot;", " "));
			}
			account = compAccountDao.queryCompAccountByCid(comp.getId());
			if (account != null) {
				comp.setLoginCount(account.getLoginCount());
				comp.setGmtLogin(account.getGmtLogin());
				comp.setGmtRegister(account.getGmtRegister());
				comp.setUid(account.getId());
				comp.setAccount(account.getAccount());
				comp.setEmail(account.getEmail());
				comp.setName(account.getName());
				comp.setSex(account.getSex());
				comp.setMobile(account.getMobile());
				comp.setFaxCountry(account.getFaxCountry());
				comp.setFaxArea(account.getFaxArea());
				comp.setFax(account.getFax());
				comp.setPhoneCountry(account.getPhoneCountry());
				comp.setPhoneArea(account.getPhoneArea());
				comp.setPhone(account.getPhone());
				comp.setPosition(account.getPosition());
				comp.setContact(account.getContact());
			}
		}
		return cList;
	}

	@Override
	public Integer updateProfileGmtModified(Integer id) {
		return compProfileDao.updateProfileGmtModified(id);
	}

}