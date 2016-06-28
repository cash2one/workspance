/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-10
 */
package com.zz91.ep.admin.dao.sys.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.sys.ParamTypeDao;
import com.zz91.util.Assert;
import com.zz91.util.domain.ParamType;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component("paramTypeDao")
public class ParamTypeDaoImpl extends BaseDao implements ParamTypeDao {
	final static String SQL_PREFIX="param";
	
	public Integer createParamType(ParamType type) {
		Assert.notNull(type, "type can not be null");
		Assert.hasText(type.getKey(), "type.key must has text");

		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createParamType"), type);
	}

	@SuppressWarnings("unchecked")
	public List<ParamType> listAllParamType() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "listAllParamType"));
	}

	public ParamType listOneParamTypeByKey(String key) {
		Assert.notNull(key, "key can not be null");
		return (ParamType) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "listOneParamTypeByKey"),key);
	}

	public Integer updateParamType(ParamType type) {
		Assert.notNull(type, "type can not be null");
		Assert.hasText(type.getKey(), "type.key must has text");

		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateParamType"), type);
	}

	public Integer deleteParamType(String key) {
		Assert.notNull(key, "key can not be null");
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteParamType"), key);
	}

}
