package com.ast.feiliao91.persist.logistics.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.logistics.Logistics;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.logistics.LogisticsDao;

@Component("logisticsDao")
public class LogisticsDaoImpl extends BaseDaoSupport implements LogisticsDao{
    final static String SQL_PREFIX="logistics";
	@Override
	public Logistics selectLogisticsByCode(String code) {
		
		return (Logistics) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectLogisticsByCode"),code);
	}
	@Override
	public Integer insertLogistics(Logistics logistics) {
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertLogistics"),logistics);
	}
	@Override
	public Integer updateLogisticsByCode(String code,String logisticsInfo){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("logisticsInfo", logisticsInfo);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateLogisticsByCode"),map);
	}
}
