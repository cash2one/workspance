package com.ast.ast1949.persist.site.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisCsRenewal;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.site.AnalysisCsRenewalDao;

@Component("analysisCsRenewalDao")
public class AnalysisCsRenewalDaoImpl extends BaseDaoSupport implements AnalysisCsRenewalDao{

	final static String SQL_PREFIX = "analysisCsRenewal";	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisCsRenewal> queryAnalysisCsRenewal(String account, Date start, Date end) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", account);
		root.put("start", start);
		root.put("end", end);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAnalysisCsRenewal"), root);
	}
	
}
