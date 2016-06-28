/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dao;

import java.util.List;

import com.zz91.crm.domain.Param;

/**
 * @author totly created on 2011-12-10
 */
public interface ParamDao {

    /**
     * 根据参数类型查询所有参数
     * @param types
     * @param isuse
     * @return
     */
    public List<Param> queryParamByTypes(String types, Integer isuse);

    /**
     * 根据关键字查询Key对应Value
     * @param types
     * @param key
     * @return
     */
    public String queryValueByKey(String types, String key);

    /**
     * 添加参数
     * @param param
     * @return
     */
    public Integer createParam(Param param);

    /**
     * 根据ID删除参数
     * @param id
     * @return
     */
    public Integer deleteParamById(Integer id);

    /**
     * 修改参数内容
     * @param param
     * @return
     */
    public Integer updateParam(Param param);

    /**
     * 根据类型删除参数
     * @param types
     * @return
     */
    public Integer deleteParamByTypes(String types);

    /**
     * 修改类型
     * @param types
     * @param newTypes
     * @return
     */
    public Integer updateParamTypes(String types, String newTypes);

    /**
     * 查询是否存在该key的类型
     * @param types
     * @param key
     * @return
     */
    public Integer queryCountByKey(String types, String key);
    
    /**
     * 查询是否存在该key
     * @param id
     * @return
     */
	public String queryKeyById(Integer id);

	public Integer updateParamByKey(Param param);

}