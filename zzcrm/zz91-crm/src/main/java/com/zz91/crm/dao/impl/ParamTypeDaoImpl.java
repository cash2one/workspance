package com.zz91.crm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.ParamTypeDao;
import com.zz91.crm.domain.ParamType;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-12 
 */
@Component("paramTypeDao")
public class ParamTypeDaoImpl extends BaseDao implements ParamTypeDao {
	
	final static String SQL_PREFIX="paramType";

	@SuppressWarnings("unchecked")
	@Override
	public List<ParamType> queryAllParamType() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllParamType"));
	}

	@Override
	public Integer createParamType(ParamType paramType) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createParamType"), paramType);
	}

	@Override
	public Integer deleteParamTypeByKey(String key) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteParamTypeByKey"), key);
	}

	@Override
	public Integer updateParamType(ParamType paramType) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateParamType"), paramType);
	}

	@Override
	public Integer queryCountByKey(String key) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountByKey"), key);
	}

	@Override
	public String queryKeyById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryKeyById"), id);
	}

}
