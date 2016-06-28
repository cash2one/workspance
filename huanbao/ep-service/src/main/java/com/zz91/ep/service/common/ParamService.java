/*
 * 文件名称：ParamService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.common;

import java.util.List;

import com.zz91.util.domain.Param;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：系统参数接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface ParamService {
	
	/**
	 * 函数名称：queryUsefulParam
	 * 功能描述：查询所有的有用的参数信息（用于缓存在memcached中）
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	List<Param> queryUsefulParam();

	/**
	 * 函数名称：queryUsefulParam
	 * 功能描述：根据父类别查询所有的有用的参数信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	List<Param> queryParamsByType(String code);
	/**
	 * 函数名称：queryParamByKey
	 * 功能描述：根据key查询参数信息
	 * 输入参数：
	 *         @param key
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/07/26　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Param queryParamByKey(String key);
	
	public String queryNameByTypeAndValue(String catory,String value);

}