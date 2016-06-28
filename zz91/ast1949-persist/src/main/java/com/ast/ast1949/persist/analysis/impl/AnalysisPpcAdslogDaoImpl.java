package com.ast.ast1949.persist.analysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisPpcAdslogDao;

@Component("analysisPpcAdslogDao")
public class AnalysisPpcAdslogDaoImpl extends BaseDaoSupport implements AnalysisPpcAdslogDao {
	final static String SQL_PREFIX = "analysisPpcAdslog";

	@Override
	public Integer queryShowCByCompanyIdATAA(String adposition,Integer companyId,String from,String to){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adposition", adposition);
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryShowCByCompanyIdATAA"), map);
	}
	@Override
	public Integer queryShowCByAdpositionAT(String adposition,String from,String to){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adposition", adposition);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryShowCByAdpositionAT"), map);
	}
	@Override
	public Integer queryShowCByCompanyIdAT(Integer companyId,String from,String to){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryShowCByCompanyIdAT"), map);
	}
	@Override
	public Integer queryCheckCByCompanyIdATAA(String adposition,Integer companyId,String from,String to){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adposition", adposition);
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCheckCByCompanyIdATAA"), map);
	}
	@Override
	public Integer queryCheckCByAdpositionAT(String adposition,String from,String to){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adposition", adposition);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCheckCByAdpositionAT"), map);
	}
	@Override
	public Integer queryCheckCByCompanyIdAT(Integer companyId,String from,String to){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCheckCByCompanyIdAT"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryCompanyIdById(Integer id){
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyIdById"), id);
	}
}
