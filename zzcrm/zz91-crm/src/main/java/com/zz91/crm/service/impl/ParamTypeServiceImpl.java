package com.zz91.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.crm.dao.ParamDao;
import com.zz91.crm.dao.ParamTypeDao;
import com.zz91.crm.domain.ParamType;
import com.zz91.crm.service.ParamTypeService;
import com.zz91.util.Assert;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-12 
 */
@Component("paramTypeService")
public class ParamTypeServiceImpl implements ParamTypeService {
	
	@Resource
	private ParamTypeDao paramTypeDao;
	@Resource
	private ParamDao paramDao;

	@Override
	public List<ParamType> queryAllParamType() {
		return paramTypeDao.queryAllParamType();
	}

	@Override
	public Integer createParamType(ParamType paramType) {
		Assert.notNull(paramType.getKey(), "the key can not be null");
		return paramTypeDao.createParamType(paramType);
	}

	@Override
	@Transactional
	public Integer deleteParamTypeByKey(String key){
		Assert.notNull(key, "the key can not be null");
		paramDao.deleteParamByTypes(key);
		return paramTypeDao.deleteParamTypeByKey(key);
	}

	@Override
	@Transactional
	public Integer updateParamType(ParamType paramType,String oldKey){
		Assert.notNull(paramType.getId(), "the id can not be null");
		Integer count = paramTypeDao.updateParamType(paramType);
		if (count > 0 && paramType.getKey()!=null) {
			paramDao.updateParamTypes(oldKey, paramType.getKey());
		}
		return count;
	}

	@Override
	public boolean isExistByKey(String key) {
		Integer i=paramTypeDao.queryCountByKey(key);
		if (i!=null && i.intValue()>0){
			return true;
		}
		return false;
	}

	@Override
	public String queryKeyById(Integer id) {
		return paramTypeDao.queryKeyById(id);
	}

}
