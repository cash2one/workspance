/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dao;

import java.util.List;

import com.zz91.crm.domain.ParamType;

/**
 * @author totly created on 2011-12-10
 */
public interface ParamTypeDao {

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
     * 删除参数类型
     * @param id
     * @return
     */
    public Integer deleteParamTypeByKey(String key);

    /**
     * 更新参数类型
     * @param paramType
     * @return
     */
    public Integer updateParamType(ParamType paramType);
    
    /**
     * 查询是否存在该key的类型
     * @param key
     * @return
     */
    public Integer queryCountByKey(String key);

    /**
     * 根据ID查询Key
     * @param id
     * @return
     */
	public String queryKeyById(Integer id);
}