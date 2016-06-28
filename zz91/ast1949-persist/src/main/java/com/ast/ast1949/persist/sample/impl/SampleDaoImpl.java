package com.ast.ast1949.persist.sample.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.SampleDao;

@Component("sampleDao")
public class SampleDaoImpl extends BaseDaoSupport implements SampleDao{

	final static String  SQL_FIX = "sample";
	
	@Override
	public Integer insert(Sample sample) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"),sample);
	}

	@Override
	public Sample queryById(Integer id) {
		return (Sample) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public Sample queryByProductId(Integer productId) {
		return (Sample) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByProductId"), productId);
	}

	@Override
	public Integer update(Sample sample) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), sample);
	}
	
	@Override
	public Integer updateDelStatus(Integer id ,Integer isDel){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("isDel", isDel);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateDelStatus"), map);
	}
	
	@Override
	public Integer countByCompanyId(Integer companyId){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByCompanyId"), companyId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sample> queryListByCompanyId(Integer companyId,Integer size){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryListByCompanyId"), map);
	}

	@Override
	public Integer countAmountByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countAmountByCompanyId"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sample> queryListByFilter(Map<String, Object> filterMap) {
		return getSqlMapClientTemplate().queryForList("sample.queryListByFilter", filterMap);
	}

	@Override
	public Integer queryListByFilterCount(Map<String, Object> filterMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sample.queryListByFilterCount", filterMap);
	}

	@Override
	public Integer updateSampleForUnpassReason(Integer id, String unpassReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("unpassReason", unpassReason);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateSampleForUnpassReason"), map);
	}

}
