package com.ast.ast1949.persist.analysis.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisPhoneLogDao;
@Component("analysisPhoneLog")
public class AnalysisPhoneLogDaoImpl extends BaseDaoSupport implements AnalysisPhoneLogDao {
	final static String SQL_PREFIX = "analysisPhoneLog";
	@Override
	public Integer queryTelCByCompanyIdATAA(String adposition,Integer companyId,String from,String to){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("adposition", adposition);
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryTelCByCompanyIdATAA"), map);
	}
	
	@Override
	public Integer queryTelCByCompanyIdAT(Integer companyId,String from,String to){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryTelCByCompanyIdAT"), map);
	}
	
	@Override
	public Integer queryTelCByAdpositionAT(String adposition,String from,String to){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("adposition", adposition);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryTelCByAdpositionAT"), map);
	}

}
