/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-10
 */
package com.ast.ast1949.persist.auth.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.auth.ParamTypeDao;
import com.ast.ast1949.util.Assert;
import com.zz91.util.domain.ParamType;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component("paramTypeDao")
public class ParamTypeDaoImpl extends SqlMapClientDaoSupport implements ParamTypeDao {

	public Integer createParamType(ParamType type) {
		Assert.notNull(type, "type can not be null");
		Assert.hasText(type.getKey(), "type.key must has text");

		return (Integer) getSqlMapClientTemplate().insert("param.createParamType", type);
	}

	public List<ParamType> listAllParamType() {
		return getSqlMapClientTemplate().queryForList("param.listAllParamType");
	}

	public ParamType listOneParamTypeByKey(String key) {
		Assert.notNull(key, "key can not be null");
		return (ParamType) getSqlMapClientTemplate().queryForObject("param.listOneParamTypeByKey",key);
	}

	public Integer updateParamType(ParamType type) {
		Assert.notNull(type, "type can not be null");
		Assert.hasText(type.getKey(), "type.key must has text");

		return getSqlMapClientTemplate().update("param.updateParamType", type);
	}

	public Integer deleteParamType(String key) {
		Assert.notNull(key, "key can not be null");
		return getSqlMapClientTemplate().delete("param.deleteParamType", key);
	}

}
