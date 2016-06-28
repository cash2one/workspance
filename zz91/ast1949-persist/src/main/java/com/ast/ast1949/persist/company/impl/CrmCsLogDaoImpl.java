package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmCsLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.AnalysisCsLog;
import com.ast.ast1949.dto.company.CrmCsLogDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmCsLogDao;

@Component("crmCsLogDao")
public class CrmCsLogDaoImpl extends BaseDaoSupport implements CrmCsLogDao {

	private static String SQL_PREFIX = "crmCsLog";

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCsLog> queryLogByCompany(Integer companyId,
			Integer callType, Integer star, String csAccount,
			Integer situation, String from, String to, PageDto<CrmCsLogDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("callType", callType);
		root.put("star", star);
		root.put("csAccount", csAccount);
		root.put("situation", situation);
		root.put("from", from);
		root.put("to", to);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryLogByCompany"), root);
	}

	@Override
	public Integer queryLogByCompanyCount(Integer companyId, Integer callType,
			Integer star, String csAccount, Integer situation, String from,
			String to) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("callType", callType);
		root.put("star", star);
		root.put("csAccount", csAccount);
		root.put("situation", situation);
		root.put("from", from);
		root.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryLogByCompanyCount"), root);
	}

	@Override
	public Integer insertLog(CrmCsLog log) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertLog"), log);
	}

	@Override
	public Integer queryRecentLogId(Integer companyId) {

		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryRecentLogId"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisCsLog> queryCsLogAnalysis(String csAccount, Long from,
			Long to) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("csAccount", csAccount);
		root.put("targetFrom", from);
		root.put("targetTo", to);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCsLogAnalysis"), root);
	}

}
