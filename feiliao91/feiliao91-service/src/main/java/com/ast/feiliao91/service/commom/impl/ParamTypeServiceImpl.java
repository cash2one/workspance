/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-10
 */
package com.ast.feiliao91.service.commom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.feiliao91.persist.common.ParamDao;
import com.ast.feiliao91.persist.common.ParamTypeDao;
import com.ast.feiliao91.service.commom.ParamTypeService;
import com.zz91.util.Assert;
import com.zz91.util.domain.ParamType;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component("paramTypeService")
public class ParamTypeServiceImpl implements ParamTypeService {

	@Autowired
	private ParamTypeDao paramTypeDao;

	@Autowired
	private ParamDao paramDao;

	public Integer createParamType(ParamType type) {
		Assert.notNull(type, "type can not be null");
		Assert.hasText(type.getKey(), "type.key must has text");
		ParamType t = paramTypeDao.listOneParamTypeByKey(type.getKey());
		if(t!=null){
			return 0;
		}

		return paramTypeDao.createParamType(type);
	}

	public List<ParamType> listAllParamType() {

		return paramTypeDao.listAllParamType();
	}

	public ParamType listOneParamTypeByKey(String key) {
		Assert.notNull(key, "key can not be null");
		return paramTypeDao.listOneParamTypeByKey(key);
	}

	public Integer deleteParamType(String key) {
		Assert.notNull(key, "key can not be null");
		paramDao.deleteParamByTypes(key);
		return paramTypeDao.deleteParamType(key);
	}

	public Integer updateParamType(ParamType type) {
		Assert.notNull(type, "type can not be null");
		return paramTypeDao.updateParamType(type);
	}

}
