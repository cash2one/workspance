package com.ast.ast1949.persist.company.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmCsDto;
import com.ast.ast1949.dto.company.CrmSearchDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmCsDao;

@Component("crmCsDao")
public class CrmCsDaoImpl extends BaseDaoSupport implements CrmCsDao {

	private static String SQL_PREFIX = "crmCs";

	@Override
	public Integer deleteCs(String csAccount, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("csAccount", csAccount);
		root.put("companyId", companyId);
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteCs"), root);
	}
	@Override
	public Integer deleteLdbCs(Integer companyId){
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteLdbCs"),companyId);
	}
	@Override
	public CrmCsDto queryCoreCompanyById(Integer companyId, String serviceCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("serviceCode", serviceCode);
		return (CrmCsDto) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCoreCompanyById"), root);
	}

	@Override
	public Integer insertCs(String csAccount, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("csAccount", csAccount);
		root.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertCs"), root);
	}

	@Override
	public Integer updateLogInfo(Integer companyId, String csAccount,
			Date nextVisitPhone, Date nextVisitEmail, String visitTarget) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("gmtNextVisitPhone", nextVisitPhone);
		root.put("gmtNextVisitEmail", nextVisitEmail);
		root.put("companyId", companyId);
		root.put("csAccount", csAccount);
		root.put("visitTarget", visitTarget);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateLogInfo"), root);
	}

	@Override
	public CrmCs queryCsOfCompany(Integer companyId) {

		return (CrmCs) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCsOfCompany"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCsDto> queryCoreCompany(CrmSearchDto search,
			PageDto<CrmCsDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("search", search);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCoreCompany"), root);
	}

	@Override
	public Integer queryCoreCompanyCount(CrmSearchDto search) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCoreCompanyCount"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCsDto> queryLdbCoreCompany(CrmSearchDto search,
			PageDto<CrmCsDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("search", search);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryLdbCoreCompany"), root);
	}

	@Override
	public Integer queryLdbCoreCompanyCount(CrmSearchDto search) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryLdbCoreCompanyCount"), root);
	}
	
	@Override
	public String queryCsAccountByCompanyId(Integer companyId) {

		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCsAccountByCompanyId"),
				companyId);
	}

	@Override
	public Date queryEndTimeByCompanyId(Integer companyId) {
		return (Date) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryEndTimeByCompanyId"),
				companyId);
	}
	
	@Override
	public Date queryGmtEndForLDB(Integer companyId) {
		return (Date) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryGmtEndForLDB"),companyId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCs> queryCsList(String csAccount, Date start,
			Date end) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("csAccount", csAccount);
		root.put("start", start);
		root.put("end", end);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCsList"), root);
	}

}
