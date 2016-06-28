package com.ast.ast1949.service.company.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCsProfile;
import com.ast.ast1949.domain.company.CrmServiceApply;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisCsRenewalDto;
import com.ast.ast1949.dto.company.CrmCompanySvrDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.CrmCompanySvrDao;
import com.ast.ast1949.persist.company.CrmCsProfileDao;
import com.ast.ast1949.persist.company.CrmSvrApplyDao;
import com.ast.ast1949.persist.company.CrmSvrDao;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.EsiteService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("crmCompanySvrService")
public class CrmCompanySvrServiceImpl implements CrmCompanySvrService {

	@Resource
	private CrmCompanySvrDao crmCompanySvrDao;
	@Resource
	private CrmSvrApplyDao crmSvrApplyDao;
	// @Resource
	// private CrmCsDao crmCsDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CrmSvrDao crmSvrDao;
	@Resource
	private CrmCsProfileDao crmCsProfileDao;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private EsiteService esiteService;
	
	final static String DEFAULT_STATUS = "0";

	final static String APPLY_STATUS_TRUE = "1";
	final static String APPLY_STATUS_FALSE = "2";

	final static String MONTH_DATE = "yyyy-MM";

	@Override
	public Boolean applySvr(Integer companyId, String[] svrCodeArr,
			CrmServiceApply apply, String svrgroup) {
		Assert.notNull(apply.getGmtIncome(),
				"the apply.gmtIncome can not be null");
		Assert.notNull(apply.getSaleStaff(),
				"the apply.saleStaff can not be null");
		Assert.notNull(apply.getAmount(), "the apply.amount can not be null");
		String code = UUID.randomUUID().toString();
		apply.setApplyGroup(code);
		crmSvrApplyDao.insertApply(apply);

		Map<String, String> membershipCode = crmSvrDao
				.queryMembershipOfSvr(svrgroup);

		for (String svrCode : svrCodeArr) {
			CrmCompanySvr companySvr = new CrmCompanySvr();
			companySvr.setApplyGroup(code);
			companySvr.setApplyStatus(DEFAULT_STATUS);
			companySvr.setCompanyId(companyId);
			companySvr.setCrmServiceCode(svrCode);
			companySvr.setMembershipCode(membershipCode.get(svrCode));
			crmCompanySvrDao.insertCompanySvr(companySvr);
		}
		return true;
	}

	@Override
	public List<CrmCompanySvrDto> queryCompanySvr(Integer companyId,
			Boolean expired) {
//		Assert.notNull(companyId, "the companyId can not be null");
		if(companyId==null){
			return null;
		}
		Date expiredDate = null;
		if (expired != null && !expired) {
			expiredDate = new Date();
		}
		return crmCompanySvrDao.queryCompanySvr(companyId, expiredDate);
	}

	@Override
	public PageDto<CrmCompanySvrDto> pageApplyCompany(String svrCode,
			String applyStatus, String email, Integer companyId,
			PageDto<CrmCompanySvrDto> page) {
		// Assert.notNull(svrCode, "the svrCode can not be null");
		if (StringUtils.isNotEmpty(email)) {
			companyId = companyAccountService.queryCompanyIdByEmail(email);
		}
		page.setTotalRecords(crmCompanySvrDao.queryApplyCompanyCount(svrCode,applyStatus, companyId));
		List<CrmCompanySvrDto> list = crmCompanySvrDao.queryApplyCompany(svrCode,applyStatus, companyId, page);
		for (CrmCompanySvrDto dto : list) {
			if(dto.getAccount()==null){
				dto.setAccount(new CompanyAccount());
			}
			if(dto.getCompany()==null){
				dto.setCompany(new Company());
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public List<CrmCompanySvrDto> queryApplyByGroup(String applyGroup) {
		Assert.notNull(applyGroup, "the applyGroup can not be null");
		return crmCompanySvrDao.queryApplyByGroup(applyGroup);
	}

	@Override
	public List<CrmCompanySvr> querySvrHistory(Integer companyId, String svrCode) {
		Assert.notNull(companyId, "the companyId can not be null");
		Assert.notNull(svrCode, "the svrCode can not be null");
		return crmCompanySvrDao.querySvrHistory(companyId, svrCode);
	}

	@Override
	public Boolean openSvr(CrmCompanySvr svr, Integer companyId) {
		// TODO 判断是否有profile，没有的话创建

		crmCompanySvrDao.updateBaseSvr(svr);

		crmCompanySvrDao.updateSvrStatusById(svr.getId(), APPLY_STATUS_TRUE);

		createCsProfile(companyId);

		return true;
	}

	@Override
	public void createCsProfile(Integer companyId) {
		// TODO 判断是否有profile，没有的话创建
		Integer i = crmCsProfileDao.countProfile(companyId);
		if (i == null || i.intValue() <= 0) {
			Company company = companyDAO.querySimpleCompanyById(companyId);
			CompanyAccount account = companyAccountDao
					.queryAdminAccountByCompanyId(companyId);
			CrmCsProfile profile = new CrmCsProfile();
			profile.setAccount(account.getAccount());
			profile.setAreaCode(company.getAreaCode());
			profile.setClassifiedCode(company.getClassifiedCode());
			profile.setCompanyId(companyId);
			profile.setContact(account.getContact());
			profile.setDomain(company.getDomain());
			profile.setDomainZz91(company.getDomainZz91());
			profile.setEmail(account.getEmail());
			profile.setFax(account.getFax());
			profile.setFaxAreaCode(account.getFaxAreaCode());
			profile.setFaxCountryCode(account.getFaxCountryCode());
			profile.setGmtLastLogin(account.getGmtLastLogin());
			profile.setGmtVisit(company.getGmtVisit());
			profile.setIndustryCode(company.getIndustryCode());
			profile.setIsBlock(company.getIsBlock());
			profile.setMembershipCode(company.getMembershipCode());
			profile.setMobile(account.getMobile());
			profile.setName(company.getName());
			profile.setNumLogin(account.getNumLogin());
			profile.setNumVisitMonth(company.getNumVisitMonth());
			profile.setServiceCode(company.getServiceCode());
			profile.setStar(company.getStar());
			profile.setStarSys(company.getStarSys());
			profile.setTel(account.getTel());
			profile.setTelAreaCode(account.getTelAreaCode());
			profile.setTelCountryCode(account.getTelCountryCode());
			crmCsProfileDao.insertProfile(profile);
		}
	}

	@Override
	public CrmCompanySvr queryCompanySvrById(Integer id) {
		return crmCompanySvrDao.queryCompanySvrById(id);
	}

	@Override
	public Integer updateSvrById(CrmCompanySvr svr) {
		// 判断第几年续签
		Integer years = crmCompanySvrDao.countSvrYears(svr.getCompanyId(), svr
				.getCrmServiceCode());
		if (years == null) {
			years = 0;
		}
		svr.setSignedType(String.valueOf(years));
		if (svr.getZstYear() == null) {
			svr.setZstYear(0);
		}
		return crmCompanySvrDao.updateBaseSvr(svr);
	}

	@Override
	public Integer countOpenedApplyByGroup(String applyGroup) {
		return crmCompanySvrDao
				.countApplyByGroup(applyGroup, APPLY_STATUS_TRUE);
	}

	@Override
	public Boolean refusedApply(String applyGroup) {
		Integer status = crmCompanySvrDao.updateSvrStatusByGroup(applyGroup,
				APPLY_STATUS_FALSE);
		if (status != null && status.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public CrmCompanySvr queryZstSvr(Integer companyId) {
		Date date;
		try {
			date = DateUtil.getDate(new Date(), "yyyy-MM-dd");
		} catch (ParseException e) {
			date = null;
		}
		List<CrmCompanySvr> list = crmCompanySvrDao.queryRecentSvr(companyId,
				"1000", date);
		if (list == null || list.size() <= 0) {
			return null;
		}
		CrmCompanySvr crmCompanySvr = new CrmCompanySvr();
		crmCompanySvr.setGmtStart(list.get(0).getGmtStart());
		crmCompanySvr.setGmtEnd(list.get(0).getGmtEnd());
		if (list.size() >= 2) {
			for (CrmCompanySvr obj : list) {
				if (obj.getGmtStart()==null||crmCompanySvr.getGmtStart()==null||obj.getGmtEnd()==null||crmCompanySvr.getGmtEnd()==null) {
					continue;
				}
				if (obj.getGmtStart().getTime() < crmCompanySvr.getGmtStart()
						.getTime()) {
					crmCompanySvr.setGmtStart(obj.getGmtStart());
				}
				if (obj.getGmtEnd().getTime() > crmCompanySvr.getGmtEnd()
						.getTime()) {
					crmCompanySvr.setGmtEnd(obj.getGmtEnd());
				}
			}
		}

		return crmCompanySvr;
	}

	@Override
	public CrmCompanySvr queryRecentHistory(String svrCode, Integer companyId,
			Integer companySvrId) {
		return crmCompanySvrDao.queryRecentHistory(svrCode, companyId,
				companySvrId);
	}

	@Override
	public Boolean openZstSvr(CrmCompanySvr svr, String domainZz91,
			String membershipCode) {
		Assert.notNull(svr, "the company service object can not be null");
		Assert.notNull(svr.getCompanyId(), "the company id can not be null");

		Integer years = crmCompanySvrDao.countSvrYears(svr.getCompanyId(), svr
				.getCrmServiceCode());
		if (years == null) {
			years = 0;
		}
		svr.setSignedType(String.valueOf(years));
		if (svr.getZstYear() == null) {
			svr.setZstYear(0);
		}
		crmCompanySvrDao.updateBaseSvr(svr);

		crmCompanySvrDao.updateSvrStatusById(svr.getId(), APPLY_STATUS_TRUE);
		
		// 更新 crm_cs_profile
		crmCsProfileDao.updateMemberShipCode(membershipCode, svr.getCompanyId());

		Integer zstYear = crmCompanySvrDao.sumYear(svr.getCompanyId(), "1000");

		companyDAO.updateZstOpenInfo(svr.getCompanyId(), zstYear, domainZz91,membershipCode);
		
		if (StringUtils.isNotEmpty(domainZz91)) {
			// 更新域名
			companyDAO.updateDomainZz91(svr.getCompanyId(), domainZz91);
			esiteService.initDomain();// 更新公司缓存 门市部所有二级域名
		}

		createCsProfile(svr.getCompanyId());

		return true;
	}

	@Override
	public Boolean openSPSvr(CrmCompanySvr svr, String domainZz91) {
		Assert.notNull(svr, "the company service object can not be null");
		Assert.notNull(svr.getCompanyId(), "the company id can not be null");

		// 更新 开通状态
		crmCompanySvrDao.updateSvrStatusById(svr.getId(), APPLY_STATUS_TRUE);

		// 更新 服务日期
		crmCompanySvrDao.updateBaseSvr(svr);

		// 更新域名
		companyDAO.updateDomainZz91(svr.getCompanyId(), domainZz91);

		createCsProfile(svr.getCompanyId());

		return true;
	}

	@Override
	public Boolean closeSvr(Integer companySvrId) {
		Integer integer = crmCompanySvrDao.updateSvrStatusById(companySvrId,
				APPLY_STATUS_FALSE);
		if (integer != null && integer.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validatePeriod(Integer companyId, String svrCode) {
		Assert.notNull(companyId, "the company id can not be null");
		Integer i = crmCompanySvrDao.period(companyId, svrCode);
		if (i != null && i.intValue() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean validateEsitePeriod(Integer companyId) {
		Assert.notNull(companyId, "the company id can not be null");
		// 百度优化服务
		Integer i = crmCompanySvrDao.period(companyId, CrmCompanySvrService.BAIDU_CODE);
		if (i != null && i.intValue() > 0) {
			return true;
		}
		// 商铺服务
		i = crmCompanySvrDao.period(companyId, CrmCompanySvrService.ESITE_CODE);
		if (i != null && i.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Map<String, Integer>> monthExpiredCountBySvrCode(Date start,
			Date end,String code) {

		List<AnalysisCsRenewalDto> list = crmCompanySvrDao.monthExpiredCountBySvrCode(
				start, end,code);

		Map<String, Map<String, Integer>> resultMap = new HashMap<String, Map<String, Integer>>();

		// TODO 计算过期客户总数
		Map<String, Integer> i = new HashMap<String, Integer>();
		for (AnalysisCsRenewalDto obj : list) {
			if (obj.getCsAccount() == null) {
				continue;
			}

			Map<String, Integer> m = resultMap.get(obj.getCsAccount());
			if (m == null) {
				m = new HashMap<String, Integer>();
				resultMap.put(obj.getCsAccount(), m);
			}
			calculate(m, DateUtil.toString(obj.getGmtEnd(), MONTH_DATE), 1);
			calculate(i, DateUtil.toString(obj.getGmtEnd(), MONTH_DATE), 1);
		}
		resultMap.put("total", i);
		return resultMap;
	}

	private void calculate(Map<String, Integer> map, String k, Integer num) {
		if (map.get(k) != null) {
			num = num.intValue() + map.get(k).intValue();
		}
		map.put(k, num);
	}

	@Override
	public Boolean updateZstSvr(CrmCompanySvr svr, String domainZz91,
			String membershipCode) {
		Assert.notNull(svr, "the company service object can not be null");
		Assert.notNull(svr.getCompanyId(), "the company id can not be null");

		Integer years = crmCompanySvrDao.countSvrYears(svr.getCompanyId(), svr
				.getCrmServiceCode());
		if (years == null) {
			years = 0;
		}
		svr.setSignedType(String.valueOf(years));
		if (svr.getZstYear() == null) {
			svr.setZstYear(0);
		}
		crmCompanySvrDao.updateBaseSvr(svr);

		Integer zstYear = crmCompanySvrDao.sumYear(svr.getCompanyId(), "1000");

		companyDAO.updateZstOpenInfo(svr.getCompanyId(), zstYear, domainZz91,membershipCode);
		
		if (StringUtils.isNotEmpty(domainZz91)) {
			// 更新域名
			companyDAO.updateDomainZz91(svr.getCompanyId(), domainZz91);
		}

		return true;
	}

	@Override
	public Boolean updateSPSvr(CrmCompanySvr svr, String domainZz91) {
		Assert.notNull(svr, "the company service object can not be null");
		Assert.notNull(svr.getCompanyId(), "the company id can not be null");

		Integer years = crmCompanySvrDao.countSvrYears(svr.getCompanyId(), svr
				.getCrmServiceCode());
		if (years == null) {
			years = 0;
		}
		svr.setSignedType(String.valueOf(years));
		if (svr.getZstYear() == null) {
			svr.setZstYear(0);
		}
		crmCompanySvrDao.updateBaseSvr(svr);

		companyDAO.updateDomainZz91(svr.getCompanyId(), domainZz91);

		return true;
	}

	@Override
	public Boolean updateSvr(CrmCompanySvr svr, Integer companyId) {
		crmCompanySvrDao.updateBaseSvr(svr);
		return true;
	}

	@Override
	public List<CrmCompanySvrDto> queryLatestOpen(Integer size) {
		if(size==null){
			size = 10;
		}
		if(size>100){
			size = 100;
		}
		List<Integer> list = crmCompanySvrDao.queryLatestOpen(size*2);
		List<CrmCompanySvrDto> nlist = new ArrayList<CrmCompanySvrDto>();
		Set<Integer> set = new HashSet<Integer>();
		for(Integer id:list){
			if(!set.contains(id)){
				CrmCompanySvrDto dto = new CrmCompanySvrDto();
				dto.setCompany(companyDAO.queryCompanyById(id));
				nlist.add(dto);
				set.add(id);
			}
			if(nlist.size()>=size){
				break;
			}
		}
		return nlist;
	}
	public boolean validateLDB(Integer companyId, String svrLDBCode1,String svrLDBCode2){
		Assert.notNull(companyId, "the company id can not be null");
		Integer i = crmCompanySvrDao.period(companyId, svrLDBCode1);
		Integer j=crmCompanySvrDao.period(companyId, svrLDBCode2);
		if ((i != null && i.intValue() > 0)||(j != null && j.intValue() > 0)) {
			return true;
		}
		return false;
		
	}

	@Override
	public List<CrmCompanySvr> querySvrByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "the company id can not be null");
		return crmCompanySvrDao.querySvrByCompanyId(companyId);
	}
	
	@Override
	public String queryGmtendByCompanyId(Integer companyId) {
		// 获取服务结束时间并String类型传递页面
		List<Date> list = new ArrayList<Date>();
		// 取得该ID下所有服务的到期时间
		list = crmCompanySvrDao.queryGmtendByCompanyId(companyId);
		//存储最大时间的String 用于传到index页面
		String gmtend = null;
		//初始比大小值
		long longtm = 0;
		//如果没数据gmtend是空格表示此时见不存在
		if (list == null) {
			gmtend = "";
		} else {
			//如果有数据进行for循环比较
			for (int i = 0; i < list.size(); i++) {
			  //当list中有数据大于先取得值时，longtm替换，同时list.get()转换为String的gmtend
				if (longtm < list.get(i).getTime()) {
					longtm = list.get(i).getTime();
					gmtend = DateUtil.toString(list.get(i), "yyyy-MM-dd");
				}
			}
		}
		//最终gmtend返回
		return gmtend;
	}

	@Override
	public Integer validatehistory(Integer companyId, String svrCode) {
		Assert.notNull(companyId, "the company id can not be null");
		return  crmCompanySvrDao.history(companyId, svrCode);
	}

}
