/**
 * @author shiqp
 * @date 2014-09-11
 */
package com.ast.ast1949.persist.analysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisMyrcVisitor;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisMyrcVisitorDao;

@Component("analysisMyrcVisitorDao")
public class AnalysisMyrcVisitorDaoImpl extends BaseDaoSupport implements AnalysisMyrcVisitorDao {
	final static String SQL_PRE = "analysisMyrcVisitor";

	@Override
	public Integer countViewByCidATime(Integer companyId, String from, String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PRE, "countViewByCidATime"), map);
	}

	@Override
	public Integer countVisitByCidATime(Integer companyId, String from,String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PRE, "countVisitByCidATime"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisMyrcVisitor> queryViewByCidATime(Integer companyId,String from, String to, PageDto<AnalysisMyrcVisitor> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PRE, "queryViewByCidATime"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisMyrcVisitor> queryVisitByCidATime(Integer companyId,String from, String to, PageDto<AnalysisMyrcVisitor> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PRE, "queryVisitByCidATime"), map);
	}

	@Override
	public Integer updateTitle(String title, String url) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("title", title);
		map.put("url", url);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PRE, "updateTitle"), map);
		
	}

	@Override
	public Integer countViewByCidATimeLen(Integer companyId, String from, String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PRE, "countViewByCidATimeLen"), map);
	}

	@Override
	public Integer countVisitByCidATimeLen(Integer companyId, String from, String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PRE, "countVisitByCidATimeLen"), map);
	}

}
