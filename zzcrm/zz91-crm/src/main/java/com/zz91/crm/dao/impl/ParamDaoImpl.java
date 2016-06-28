package com.zz91.crm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.ParamDao;
import com.zz91.crm.domain.Param;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-12 
 */
@Component("paramDao")
public class ParamDaoImpl extends BaseDao implements ParamDao {
	
	final static String SQL_PREFIX="param";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Param> queryParamByTypes(String types, Integer isuse) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("types", types);
		root.put("isuse", isuse);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryParamByTypes"), root);
	}

	@Override
	public String queryValueByKey(String types, String key) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("types", types);
		root.put("key", key);
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryValueByKey"), root);
	}

	@Override
	public Integer createParam(Param param) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createParam"), param);
	}

	@Override
	public Integer deleteParamById(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteParamById"), id);
	}

	@Override
	public Integer updateParam(Param param) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateParam"), param);
	}

	@Override
	public Integer deleteParamByTypes(String types) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteParamByTypes"), types);
	}

	@Override
	public Integer updateParamTypes(String types, String newTypes) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("types", types);
		root.put("newTypes", newTypes);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateParamTypes"), root);
	}

	@Override
	public Integer queryCountByKey(String types, String key) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("types", types);
		root.put("key", key);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountByKey"), root);
	}

	@Override
	public String queryKeyById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryKeyById"), id);
	}

	@Override
	public Integer updateParamByKey(Param param) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateParamByKey"), param);
	}
}
