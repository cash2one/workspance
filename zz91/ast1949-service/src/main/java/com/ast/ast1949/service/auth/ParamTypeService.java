/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-10
 */
package com.ast.ast1949.service.auth;

import java.util.List;

import com.zz91.util.domain.ParamType;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public interface ParamTypeService {

	/**
	 * 创建参数类型,参数key不能重复,创建前需要验证key是否已存在
	 * @param type:待创建的参数类型,不能为null</br>
	 * 		type.key:参数key不能为空
	 * @return
	 */
	public Integer createParamType(ParamType type);

	/**
	 * 列出所有参数类型
	 * @return
	 */
	public List<ParamType> listAllParamType();

	/**
	 * 根据参数类型key,查找一个参数类型信息
	 * @param key:参数key,不能为null
	 * @return
	 */
	public ParamType listOneParamTypeByKey(String key);

	/**
	 * 更新参数类型
	 * @param type:类型信息,不能为null
	 * 		type.key:待更新的key,不能为null
	 * @return
	 */
	public Integer updateParamType(ParamType type);

	/**
	 * 根据类型key删除参数类型
	 * @param key:类型key,不能为null
	 * @return
	 */
	public Integer deleteParamType(String key);
}
