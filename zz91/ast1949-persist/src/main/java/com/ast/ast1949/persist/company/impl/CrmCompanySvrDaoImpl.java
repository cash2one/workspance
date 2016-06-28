package com.ast.ast1949.persist.company.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisCsRenewalDto;
import com.ast.ast1949.dto.company.CrmCompanySvrDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmCompanySvrDao;

@Component("crmCompanySvrDao")
public class CrmCompanySvrDaoImpl extends BaseDaoSupport implements
		CrmCompanySvrDao {

	private static String SQL_PREFIX = "crmCompanySvr";

	@Override
	public Integer insertCompanySvr(CrmCompanySvr companySvr) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertCompanySvr"), companySvr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompanySvrDto> queryCompanySvr(Integer companyId,
			Date expiredDate) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("expiredDate", expiredDate);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanySvr"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompanySvrDto> queryApplyCompany(String svrCode,
			String applyStatus, Integer companyId,
			PageDto<CrmCompanySvrDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("svrCode", svrCode);
		root.put("applyStatus", applyStatus);
		root.put("companyId", companyId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryApplyCompany"), root);
	}

	@Override
	public Integer queryApplyCompanyCount(String svrCode, String applyStatus,
			Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("svrCode", svrCode);
		root.put("applyStatus", applyStatus);
		root.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryApplyCompanyCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompanySvrDto> queryApplyByGroup(String applyGroup) {
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryApplyByGroup"), applyGroup);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompanySvr> querySvrHistory(Integer companyId, String svrCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("crmServiceCode", svrCode);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "querySvrHistory"), root);
	}

	@Override
	public Integer updateSvrStatusByGroup(String applyGroup, String status) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("applyGroup", applyGroup);
		root.put("status", status);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateSvrStatusByGroup"), root);

	}

	@Override
	public Integer updateBaseSvr(CrmCompanySvr svr) {
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateBaseSvr"), svr);
	}

	@Override
	public CrmCompanySvr queryCompanySvrById(Integer id) {

		return (CrmCompanySvr) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanySvrById"), id);
	}

	@Override
	public Integer countSvrYears(Integer companyId, String svrCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("crmServiceCode", svrCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countSvrYears"), root);
	}

	@Override
	public Integer countApplyByGroup(String applyGroup, String applyStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("applyGroup", applyGroup);
		root.put("applyStatus", applyStatus);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countApplyByGroup"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompanySvr> queryRecentSvr(Integer companyId,
			String svrCode, Date expiredDate) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("crmServiceCode", svrCode);
		root.put("expiredDate", expiredDate);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryRecentSvr"), root);
	}

	@Override
	public CrmCompanySvr queryRecentHistory(String svrCode, Integer companyId,
			Integer companySvrId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("crmServiceCode", svrCode);
		root.put("companySvrId", companySvrId);
		return (CrmCompanySvr) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryRecentHistory"), root);
	}

	@Override
	public Integer updateSvrStatusById(Integer svrId, String status) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", svrId);
		root.put("status", status);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateSvrStatusById"), root);
	}

	@Override
	public Integer sumYear(Integer companyId, String svrCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("svrCode", svrCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "sumYear"), root);
	}

	@Override
	public Integer period(Integer companyId, String svrCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("svrCode", svrCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "period"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisCsRenewalDto> monthExpiredCount(Date start, Date end) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("start", start);
		root.put("end", end);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "monthExpiredCount"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisCsRenewalDto> monthExpiredCountBySvrCode(Date start, Date end,String code) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("start", start);
		root.put("end", end);
		root.put("code", code);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "monthExpiredCountBySvrCode"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryLatestOpen(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryLatestOpen"), size);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompanySvr> querySvrByCompanyId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querySvrByCompanyId"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Date> queryGmtendByCompanyId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryGmtendByCompanyId"), companyId);
	}

	@Override
	public Integer history(Integer companyId, String svrCode) {
	    Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("svrCode", svrCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "history"), root);
	}


}
