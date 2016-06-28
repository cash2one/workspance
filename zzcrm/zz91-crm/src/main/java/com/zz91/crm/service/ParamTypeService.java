/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.service;

import java.util.List;

import com.zz91.crm.domain.ParamType;

/**
 * @author totly created on 2011-12-10
 */
public interface ParamTypeService {

    /**
     * 获取所有参数类型
     * @return
     */
    public List<ParamType> queryAllParamType();

    /**
     * 添加参数类型
     * @param paramType
     * @return
     */
    public Integer createParamType(ParamType paramType);

    /**
     * 删除参数类型(同时删除相应参数)
     * @param key
     * @return
     * @throws Exception 
     */
    public Integer deleteParamTypeByKey(String key);

    /**
     * 更新参数类型(同时更新参数表中参数Types)
     * @param paramType
     * @return
     * @throws Exception 
     */
    public Integer updateParamType(ParamType paramType,String oldKey);

    /**
     * 查询是否存在改key的类型
     * @param key
     * @return
     */
    public boolean isExistByKey(String key);

    /**
     * 根据ID查key
     * @param id
     * @return
     */
	public String queryKeyById(Integer id);
}