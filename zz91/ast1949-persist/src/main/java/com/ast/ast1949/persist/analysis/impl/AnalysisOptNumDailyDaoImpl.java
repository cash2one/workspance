/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-11
 */
package com.ast.ast1949.persist.analysis.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisOptNumDaily;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisOptNumDailyDao;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-1-11
 */
@Component("analysisOptNumDailyDao")
public class AnalysisOptNumDailyDaoImpl extends BaseDaoSupport implements AnalysisOptNumDailyDao {

	final static String PREFIX = "analysisOptNumDaily";
	
	public Integer insertOptNumToday(AnalysisOptNumDaily o) {
		o.setSqlKey(super.addSqlKeyPreFix(PREFIX, "insertOptNumToday"));
		return insert(o);
	}

	public Integer queryOptNumByAccountToday(String optCode, String account) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", optCode);
		root.put("account", account);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				super.addSqlKeyPreFix(PREFIX, "queryOptNumByAccountToday"),root);
	}

	public Integer queryOptNumTodayCount(String optCode, String account) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", optCode);
		root.put("account", account);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				super.addSqlKeyPreFix(PREFIX, "queryOptNumTodayCount"),root);
	}

	public Integer updateOptNumToday(String optCode, String account) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", optCode);
		root.put("account", account);
		return getSqlMapClientTemplate().update(
				super.addSqlKeyPreFix(PREFIX, "updateOptNumToday"), root);
	}

}
