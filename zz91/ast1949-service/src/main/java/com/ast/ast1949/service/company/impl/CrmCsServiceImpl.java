package com.ast.ast1949.service.company.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.company.CrmCsProfile;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmCsDto;
import com.ast.ast1949.dto.company.CrmSearchDto;
import com.ast.ast1949.persist.analysis.AnalysisVipPvDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.CrmCsDao;
import com.ast.ast1949.persist.company.CrmCsProfileDao;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("crmCsService")
public class CrmCsServiceImpl implements CrmCsService {

	@Resource
	private CrmCsDao crmCsDao;
	@Resource
	private AnalysisVipPvDao analysisVipPvDao;
	@Resource
	private CrmCsProfileDao crmCsProfileDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CompanyDAO companyDAO;
	final static String DAY_DATE = "yyyy-MM-dd";
	@Override
	public Boolean intoHighSea(String csAccount, Integer companyId) {
		Assert.notNull(csAccount, "the csAccount can not be null");
		Assert.notNull(companyId, "the companyId can not be null");
		// TODO 放入公海有限制
		Integer result = crmCsDao.deleteCs(csAccount, companyId);
		if (result != null && result.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public PageDto<CrmCsDto> queryCoreCompany(CrmSearchDto search,
			String account, PageDto<CrmCsDto> page) {

		if (page.getSort() == null) {
			page.setSort("c.company_id");
		}

		if (page.getDir() == null) {
			page.setSort("desc");
		}
		page.setPageSize(20);

		// page.setRecords(crmCsDao.queryCoreCompany(search))

		// StringBuffer sb=new StringBuffer();
		// SphinxClient cl=SearchEngineUtils.getInstance().getClient();

		// 构造查询条件
		// sb=buildStringQuery(sb, "cs_account", search.getCsAccount());
		// sb=buildStringQuery(sb, "contact", search.getContact());
		// sb=buildStringQuery(sb, "email", search.getEmail());
		// sb=buildStringQuery(sb, "name", search.getCompanyName());
		// sb=buildStringQuery(sb, "mobile", search.getMobile());
		// sb=buildStringQuery(sb, "crm_service_code", search.getSvrCode());
		// sb=buildStringQuery(sb, "industry_code", search.getIndustryCode());
		// sb=buildStringQuery(sb, "(area_name,area_province)",
		// CategoryFacade.getInstance().getValue(search.getAreaCode()));

		// try {
		// buildDateRangQuery(cl, "gmt_end", search.getSvrEndFrom(),
		// search.getSvrEndTo());
		// buildDateRangQuery(cl, "gmt_next_visit_phone",
		// search.getNextVisitPhoneFrom(), search.getNextVisitPhoneTo());
		// buildDateRangQuery(cl, "gmt_visit", search.getVisitFrom(),
		// search.getVisitTo());

		// 公海查询
		// if(StringUtils.isEmpty(search.getCsAccount())){
		// cl.SetFilter("cs_account_len", 0, false);
		// }

		// 新分配未联系
		// if(search.getUnvisitFlag()!=null &&
		// search.getUnvisitFlag().longValue()==0){
		// cl.SetFilter("gmt_next_visit_phone", 0, false);
		// }

		// cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
		// cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
		// cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
		// page.getSort()+" "+page.getDir());
		//
		// SphinxResult res=cl.Query(sb.toString(), "crmcompany");

		// List<CrmCsDto> result=new ArrayList<CrmCsDto>();
		// if(res==null){
		// page.setTotalRecords(0);
		// if(StringUtils.isNotEmpty(cl.GetLastError())){
		// throw new ServiceLayerException(cl.GetLastError());
		// }
		// }else{
		// page.setTotalRecords(res.totalFound);
		// for ( int i=0; i<res.matches.length; i++ ){
		// SphinxMatch info = res.matches[i];
		// CrmCsDto
		// dto=crmCsDao.queryCoreCompanyById(Integer.valueOf(""+info.docId),
		// search.getSvrCode());
		// if(dto!=null){
		// result.add(dto);
		// }
		// }
		// }

		List<CrmCsDto> result = crmCsDao.queryCoreCompany(search, page);
		Integer i = 0;

		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(
				"10001005");
		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (CrmCsDto dto : result) {
			if (dto.getCrmCs() != null && dto.getCrmCs().getCsAccount() != "") {
				dto.setCsName(map.get(dto.getCrmCs().getCsAccount()));
			}
			if (dto.getCompany().getAreaCode().length() > 12) {
				dto.setAreaProvinceLabel(categoryFacade.getValue(dto
						.getCompany().getAreaCode().substring(0, 12)));
			}
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode()));
			dto.setClassifiedLabel(categoryFacade.getValue(dto.getCompany()
					.getClassifiedCode()));
			// 普通会员 黑色
			dto.setMembershipLabel(categoryFacade
					.getValue(CrmSvrService.PT_CODE));
			// 再生通会员 绿色
			String[] str = { categoryFacade.getValue(dto.getCompany()
					.getMembershipCode()) };
			if (dto.getCompany().getMembershipCode().length() >= 8
					&& CrmSvrService.ZST_CODE.equals(dto.getCompany()
							.getMembershipCode().substring(0, 8))) {
				MessageFormat mf = new MessageFormat(CrmCsService.ZST_TEMPLATE);
				dto.setMembershipLabel(mf.format(str));
			}
			// 品牌铜会员 红色
			if (dto.getCompany().getMembershipCode().length() >= 8
					&& CrmSvrService.PPT_CODE.equals(dto.getCompany()
							.getMembershipCode().substring(0, 8))) {
				MessageFormat mf = new MessageFormat(CrmCsService.PPT_TEMPLATE);
				dto.setMembershipLabel(mf.format(str));
			}
			if (dto.getCrmCs() == null) {
				dto.setCrmCs(new CrmCs());
			}
			if (search.getSvrCode() == null) {
				if (dto.getCompany().getId() != null) {
					Date time = (Date) crmCsDao.queryEndTimeByCompanyId(dto
							.getCompany().getId());
					if (time != null) {
						try {
							if(dto!=null&&dto.getCrmCompanySvr()!=null){
							dto.getCrmCompanySvr().setGmtEnd(time);
							}
						} catch (NullPointerException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (dto.getCrmCompanySvr() == null) {
				dto.setCrmCompanySvr(new CrmCompanySvr());
			}
			//统计总的流量
			Integer pv=analysisVipPvDao.queryAllPvByCid(dto.getCompany().getId());
			if(pv==null){
				pv=0;
			}
			dto.setPv(pv);
			if(dto.getCompany()!=null){
				CompanyAccount caccount = companyAccountDao.queryAccountByCompanyId(dto.getCompany().getId());
				if(caccount!=null){
					dto.setAccount(caccount);;
				}else{
					dto.setAccount(new CompanyAccount());;
				}
			}
		}
		page.setRecords(result);
		page.setTotalRecords(crmCsDao.queryCoreCompanyCount(search));

		// } catch (SphinxException e) {
		// e.printStackTrace();
		// }

		return page;
	}
	@Override
	public PageDto<CrmCsDto> queryLdbCoreCompany(CrmSearchDto search,
		    PageDto<CrmCsDto> page) {
		// TODO Auto-generated method stub
		if (page.getSort() == null) {
			page.setSort("c.company_id");
		}
		if (page.getDir() == null) {
			page.setSort("desc");
		}
		page.setPageSize(20);
		List<CrmCsDto> result = crmCsDao.queryLdbCoreCompany(search, page);
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept("10001005");
		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (CrmCsDto dto : result) {
			if (dto.getCrmCs() != null && dto.getCrmCs().getCsAccount() != "") {
				dto.setCsName(map.get(dto.getCrmCs().getCsAccount()));
			}
			if (dto.getCompany().getAreaCode().length() > 12) {
				dto.setAreaProvinceLabel(categoryFacade.getValue(dto.getCompany().getAreaCode().substring(0, 12)));
			}
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany().getAreaCode()));
			dto.setClassifiedLabel(categoryFacade.getValue(dto.getCompany().getClassifiedCode()));
			// 普通会员 黑色
			dto.setMembershipLabel(categoryFacade
					.getValue(CrmSvrService.PT_CODE));
			// 再生通会员 绿色
			String[] str = { categoryFacade.getValue(dto.getCompany().getMembershipCode()) };
			if (dto.getCompany().getMembershipCode().length() >= 8&& CrmSvrService.ZST_CODE.equals(dto.getCompany().getMembershipCode().substring(0, 8))) {
				MessageFormat mf = new MessageFormat(CrmCsService.ZST_TEMPLATE);
				dto.setMembershipLabel(mf.format(str));
			}
			// 品牌铜会员 红色
			if (dto.getCompany().getMembershipCode().length() >= 8&& CrmSvrService.PPT_CODE.equals(dto.getCompany().getMembershipCode().substring(0, 8))) {
				MessageFormat mf = new MessageFormat(CrmCsService.PPT_TEMPLATE);
				dto.setMembershipLabel(mf.format(str));
			}
			
			if (dto.getCrmCs() == null) {
				dto.setCrmCs(new CrmCs());
			}
			if (dto.getCrmCompanySvr() == null) {
				dto.setCrmCompanySvr(new CrmCompanySvr());
			}
			//统计总的流量
			Integer pv=analysisVipPvDao.queryAllPvByCid(dto.getCompany().getId());
			if(pv==null){
				pv=0;
			}
			dto.setPv(pv);
			// 获取最后来电宝时间
			dto.getCrmCompanySvr().setGmtEnd(crmCsDao.queryGmtEndForLDB(dto.getCompany().getId()));
		}
		page.setRecords(result);
		page.setTotalRecords(crmCsDao.queryLdbCoreCompanyCount(search));
		return page;
	}
	// private StringBuffer buildStringQuery(StringBuffer sb, String column,
	// String keywords){
	// if(StringUtils.isNotEmpty(keywords)){
	// if(sb.indexOf("@")!=-1){
	// sb.append("&");
	// }
	// sb.append("@").append(column).append(" ").append(keywords);
	// }
	// return sb;
	// }

	// private void buildDateRangQuery(SphinxClient cl, String column, Date
	// from, Date to) throws SphinxException{
	// if(from!=null && to!=null){
	// cl.SetFilterRange(column, DateUtil.getMillis(from),
	// DateUtil.getMillis(to), false);
	// }
	// }

	@Override
	public CrmCs queryCsOfCompany(Integer companyId) {
		return crmCsDao.queryCsOfCompany(companyId);
	}

	@Override
	public CrmCsDto queryCoreCompanyByCompanyId(Integer companyId,
			String svrCode) {
		CrmCsDto dto = crmCsDao.queryCoreCompanyById(companyId, svrCode);

		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(
				"1000");
		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		if (dto.getCrmCs() != null && dto.getCrmCs().getCsAccount() != "") {
			dto.setCsName(map.get(dto.getCrmCs().getCsAccount()));
		}

		if (dto.getCompany().getAreaCode().length() > 12) {
			dto.setAreaProvinceLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode().substring(0, 12)));
		}
		dto.setAreaLabel(categoryFacade
				.getValue(dto.getCompany().getAreaCode()));
		dto.setClassifiedLabel(categoryFacade.getValue(dto.getCompany()
				.getClassifiedCode()));
		dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
				.getMembershipCode()));
		if (dto.getCrmCs() == null) {
			dto.setCrmCs(new CrmCs());
		}

		return dto;
	}

	@Override
	public Boolean reassign(String oldCsAccount, String csAccount,
			Integer companyId) {
		if (oldCsAccount != null && oldCsAccount.equals(csAccount)) {
			return false;
		}
		// 重新分配客户
		if (StringUtils.isNotEmpty(oldCsAccount)) {
			crmCsDao.deleteCs(oldCsAccount, companyId);
		}
		// 分配新的客户给客服
		Integer result = crmCsDao.insertCs(csAccount, companyId);
		if (result != null && result.intValue() > 0) {
			return true;
		}
		return false;
	}
	@Override
	public Boolean reassignLdb(String csAccount, Integer companyId) {
		if(companyId!=null&&StringUtils.isNotEmpty(csAccount)){
			Integer i=crmCsDao.deleteLdbCs(companyId);
			if(i!=null&&i.intValue()>0){
				Integer j=crmCsDao.insertCs(csAccount, companyId);
				if(j!=null&&j.intValue()>0){
					return true;
				}
			}else {
				return false;
			}
		}
		return false;
	}
	@Override
	public String queryAccountByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		return crmCsDao.queryCsAccountByCompanyId(companyId);
	}

	@Override
	public Map<String, Map<String, Integer>> dayContactCount(Date start,
			Date end, String csAccount) {
		List<CrmCs> list = crmCsDao.queryCsList(csAccount, start, end);

		Map<String, Map<String, Integer>> resultMap = new HashMap<String, Map<String, Integer>>();

		// TODO 计算每日联系量
		Map<String, Integer> i = new HashMap<String, Integer>();
		for (CrmCs obj : list) {
			if (obj.getCsAccount() == null) {
				continue;
			}

			Map<String, Integer> m = resultMap.get(obj.getCsAccount());
			if (m == null) {
				m = new HashMap<String, Integer>();
				resultMap.put(obj.getCsAccount(), m);
			}
			calculate(m, DateUtil.toString(obj.getGmtNextVisitPhone(), DAY_DATE), 1);
			calculate(i, DateUtil.toString(obj.getGmtNextVisitPhone(), DAY_DATE), 1);
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
	public CrmCsDto queryCrmCsByCompanyId(Integer companyId) {
		CrmCsDto dto=new CrmCsDto();
		CrmCsProfile profile=crmCsProfileDao.queryCrmCsProfileByCompanyId(companyId);
		if(profile!=null&&profile.getNumLogin()==null){
			profile.setNumLogin(0);
		}
		if(profile!=null&&profile.getGmtLastLogin()!=null){
			dto.setGmtLastLogin(DateUtil.toString(profile.getGmtLastLogin(), "yyyy-MM-dd HH:mm:ss"));
		}
		//统计总的流量
        Integer pv=analysisVipPvDao.queryAllPvByCid(companyId);
        if(pv==null){
     	   pv=0;
        }
        dto.setCrmCsProfile(profile);
        dto.setPv(pv);
		return dto;
	}

	

}
