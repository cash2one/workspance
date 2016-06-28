package com.zz91.crm.service.impl;
/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.crm.dao.ParamDao;
import com.zz91.crm.domain.Param;
import com.zz91.crm.service.ParamService;
import com.zz91.util.Assert;

/**
 * @author totly created on 2011-12-10
 */
@Component("paramService")
public class ParamServiceImpl implements ParamService {
	
	@Resource
	private ParamDao paramDao;
	
	@Override
	public List<Param> queryParamByTypes(String types, Integer isuse) {
		Assert.notNull(types, "the types can not be null");
		return paramDao.queryParamByTypes(types, isuse);
	}

	@Override
	public String queryValueByKey(String types, String key) {
		Assert.notNull(types, "the types can not be null");
		Assert.notNull(key, "the key can not be null");
		return paramDao.queryValueByKey(types, key);
	}

	@Override
	public Integer createParam(Param param) {
		Assert.notNull(param, "the param can not be null");
		if (param.getIsuse() == null) {
			param.setIsuse((short)0);
		}
		return paramDao.createParam(param);
	}

	@Override
	public Integer deleteParamById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return paramDao.deleteParamById(id);
	}

	@Override
	public Integer updateParam(Param param) {
		Assert.notNull(param, "the param can not be null");
		if (param.getIsuse() == null) {
			param.setIsuse((short)0);
		}
		return paramDao.updateParam(param);
	}

	@Override
	public boolean isExistByKey(String types, String key) {
		Assert.notNull(types, "the types can not be null");
		Assert.notNull(key, "the key can not be null");
		Integer i=paramDao.queryCountByKey(types, key);
		if (i!=null && i.intValue()>0){
			return true;
		}
		return false;
	}

	@Override
	public String queryKeyById(Integer id) {
		return paramDao.queryKeyById(id);
	}

	@Override
	public Integer updateParamByKey(Param param) {
		Assert.notNull(param, "the param can not be null");
		if (param.getIsuse() == null) {
			param.setIsuse((short)0);
		}
		return paramDao.updateParamByKey(param);
	}

}