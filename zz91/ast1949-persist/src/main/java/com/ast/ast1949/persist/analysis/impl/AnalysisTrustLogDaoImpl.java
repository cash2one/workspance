package com.ast.ast1949.persist.analysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisTrustLog;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisTrustLogDao;

@Component("analysisTrustLogDao")
public class AnalysisTrustLogDaoImpl extends BaseDaoSupport implements AnalysisTrustLogDao{

	final static String SQL_FIX = "analysisTrustLog";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisTrustLog> queryByCondition(String from,String to,String account) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("to", to);
		map.put("account", account);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

}
