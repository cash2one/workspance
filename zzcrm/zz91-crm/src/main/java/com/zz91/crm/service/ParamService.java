/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.service;

import java.util.List;

import com.zz91.crm.domain.Param;

/**
 * @author totly created on 2011-12-10
 */
public interface ParamService {

    /**
     * 根据参数类型查询所有参数
     * @param types 参数类型
     * @param isuse 有效/无效
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
     * 删除参数
     * @param id
     * @return
     */
    public Integer deleteParamById(Integer id);

    /**
     * 修改类型
     * @param param
     * @return
     */
    public Integer updateParam(Param param);

    /**
     * 查询是否存在该key的类型
     * @param types
     * @param key
     * @return
     */
    public boolean isExistByKey(String types, String key);

    /**
     * 查询是否存在key
     * @param integer
     * @return
     */
	public String queryKeyById(Integer id);

	/**
	 * 根据key和Types更新参数
	 * @param param
	 * @return
	 */
	public Integer updateParamByKey(Param param);
}