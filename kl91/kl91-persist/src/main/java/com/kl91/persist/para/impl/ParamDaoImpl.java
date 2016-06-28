/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-2
 */
package com.kl91.persist.para.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.kl91.persist.para.ParamDao;
import com.zz91.util.Assert;
import com.zz91.util.domain.Param;
import com.zz91.util.domain.ParamType;

/**
 * @author Mr.Mar (x03570227@gmail.com)
 *
 */
@Component("paramDao")
public class ParamDaoImpl extends SqlMapClientDaoSupport implements ParamDao {

	/* (non-Javadoc)
	 * @see com.ast.ast1949.persist.param.ParamDao#deleteParamById(java.lang.Long)
	 */
	public Integer deleteParamById(Integer id) {
		Assert.notNull(id, "id can not be null");
		return getSqlMapClientTemplate().delete("param.deleteParamById", id);
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.persist.param.ParamDao#deleteParamByTypes(java.lang.String)
	 */
	public Integer deleteParamByTypes(String types) {
		Assert.notNull(types, "types can not be null");
		return getSqlMapClientTemplate().delete("param.deleteParamByTypes", types);
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.persist.param.ParamDao#insertParam(com.ast.ast1949.domain.param.Param)
	 */
	public Integer insertParam(Param param) {
		Assert.notNull(param, "param can not be null");
		Assert.notNull(param.getKey(), "attribute key can not be null");
		Assert.notNull(param.getTypes(), "attribute types can not be null");
		if(param.getIsuse()==null){
			param.setIsuse(ISUSE_TRUE);
		}
		return (Integer) getSqlMapClientTemplate().insert("param.insertParam", param);
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.persist.param.ParamDao#listAllParamType()
	 */
	@SuppressWarnings("unchecked")
	public List<ParamType> listAllParamType() {
		return getSqlMapClientTemplate().queryForList("param.listAllParamType");
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.persist.param.ParamDao#listParam(com.ast.ast1949.domain.param.Param)
	 */
	@SuppressWarnings("unchecked")
	public List<Param> listParam(Param param) {
		Assert.notNull(param, "param can not be null");
		return getSqlMapClientTemplate().queryForList("param.listParam", param);
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.persist.param.ParamDao#updateParam(com.ast.ast1949.domain.param.Param)
	 */
	public Integer updateParam(Param param) {
		Assert.notNull(param, "param can not be null");
		Assert.notNull(param.getId(), "attribute id can not be null");
		Assert.notNull(param.getKey(), "attribute key can not be null");
		if(param.getIsuse()==null){
			param.setIsuse(ISUSE_FALSE);
		}
		return getSqlMapClientTemplate().update("param.updateParam", param);
	}

	public Param listOneParamById(Integer id) {
		Assert.notNull(id, "id can not be null");
		return (Param) getSqlMapClientTemplate().queryForObject("param.listOneParamById", id);
	}

}
