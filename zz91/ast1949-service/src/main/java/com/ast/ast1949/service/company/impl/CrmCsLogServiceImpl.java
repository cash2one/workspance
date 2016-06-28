package com.ast.ast1949.service.company.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CrmCsLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.AnalysisCsLog;
import com.ast.ast1949.dto.company.CrmCsLogDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.CrmCsDao;
import com.ast.ast1949.persist.company.CrmCsLogAddDao;
import com.ast.ast1949.persist.company.CrmCsLogDao;
import com.ast.ast1949.persist.company.CrmCsProfileDao;
import com.ast.ast1949.service.company.CrmCsLogService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;

@Component("crmCsLogService")
public class CrmCsLogServiceImpl implements CrmCsLogService {

	@Resource
	private CrmCsLogDao crmCsLogDao;
	@Resource
	private CrmCsLogAddDao crmCsLogAddDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CrmCsDao crmCsDao;
	@Resource
	private CrmCsProfileDao crmCsProfileDao;

	@Override
	public PageDto<CrmCsLogDto> pageLogByCompany(Integer companyId,
			Integer callType, Integer star, String csAccount,
			Integer situation, String from, String to, PageDto<CrmCsLogDto> page) {
		// Assert.notNull(companyId,"the companyId can not be null");
		// 判断结束时间是否为 空 
		if (StringUtils.isNotEmpty(to)) {
			try {
				to = DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(to, "yyyy-MM-dd"), 1),"yyyy-MM-dd");
			} catch (ParseException e) {
				to = null;
			}
		}
		List<CrmCsLog> list = crmCsLogDao.queryLogByCompany(companyId,
				callType, star, csAccount, situation, from, to, page);
		List<CrmCsLogDto> dtoList = new ArrayList<CrmCsLogDto>();
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept("1000");
		for (CrmCsLog cslog : list) {
			CrmCsLogDto dto = new CrmCsLogDto();
			dto.setAddedList(crmCsLogAddDao.queryAddedByLog(cslog.getId()));
			dto.setLog(cslog);
			dto.setCsName(map.get(cslog.getCsAccount()));
			dtoList.add(dto);
			Company company = new Company();
			company.setName(companyDAO.queryCompanyNameById(cslog.getCompanyId()));
			dto.setCompany(company);
		}
		page.setRecords(dtoList);
		page.setTotalRecords(crmCsLogDao.queryLogByCompanyCount(companyId,
				callType, star, csAccount, situation, from, to));
		return page;
	}

	@Override
	public Integer createCsLog(CrmCsLog log, Integer newStar) {
		Assert.notNull(log, "the object can not be null");
		Assert.notNull(log.getCompanyId(), "log.companyId can not be null");
		Assert.notNull(log.getCsAccount(), "log.csAccount can not be null");

		if (newStar == null) {
			newStar = 0;
		}

		if (newStar != null && !newStar.equals(log.getStar())) {
			companyDAO.updateStar(log.getCompanyId(), newStar);
		}

		// 更新crm_cs 表
		crmCsDao.updateLogInfo(log.getCompanyId(), log.getCsAccount(),
				log.getGmtNextVisitPhone(), log.getGmtNextVisitEmail(),
				log.getVisitTarget());
		// 更新company 表
		companyDAO.updateLastVisit(log.getCompanyId());
		
		// 更新crm_cs_profile 表
		crmCsProfileDao.updateLastVisit(log.getCompanyId());

		return crmCsLogDao.insertLog(log);
	}

	@Override
	public List<AnalysisCsLog> queryCsLogAnalysis(String csAccount, Long from,
			Long to) {
		List<AnalysisCsLog> list = crmCsLogDao.queryCsLogAnalysis(csAccount,
				from, to);
		if (list == null) {
			return list;
		}
		AnalysisCsLog summary = new AnalysisCsLog();
		summary.setCsAccount("汇总");
		for (AnalysisCsLog analysis : list) {
			summary.setStar0N(summary(summary.getStar0N(), analysis.getStar0N()));
			summary.setStar0Y(summary(summary.getStar0Y(), analysis.getStar0Y()));
			summary.setStar1N(summary(summary.getStar1N(), analysis.getStar1N()));
			summary.setStar1Y(summary(summary.getStar1Y(), analysis.getStar1Y()));
			summary.setStar2N(summary(summary.getStar2N(), analysis.getStar2N()));
			summary.setStar2Y(summary(summary.getStar2Y(), analysis.getStar2Y()));
			summary.setStar3N(summary(summary.getStar3N(), analysis.getStar3N()));
			summary.setStar3Y(summary(summary.getStar3Y(), analysis.getStar3Y()));
			summary.setStar4N(summary(summary.getStar4N(), analysis.getStar4N()));
			summary.setStar4Y(summary(summary.getStar4Y(), analysis.getStar4Y()));
			summary.setStar5N(summary(summary.getStar5N(), analysis.getStar5N()));
			summary.setStar5Y(summary(summary.getStar5Y(), analysis.getStar5Y()));
			summary.setServiceCall(summary(summary.getServiceCall(),
					analysis.getServiceCall()));
			summary.setSaleCall(summary(summary.getSaleCall(),
					analysis.getSaleCall()));
		}
		list.add(summary);
		return list;
	}

	private Integer summary(Integer i, Integer j) {
		if (i == null) {
			return j;
		}
		return i.intValue() + j.intValue();
	}

}
