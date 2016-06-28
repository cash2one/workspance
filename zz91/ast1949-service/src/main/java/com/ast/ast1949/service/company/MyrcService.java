/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23 by liulei.
 */
package com.ast.ast1949.service.company;

import java.util.Map;


/**
 * 这是一个用户商管处理的基础接口，初始化，myrc 的公用模块
 * 
 * 
 */


public interface MyrcService {

	/*********
	 * 判断用户是否拥有了商铺服务
	 * @param out
	 * @param companyId
	 */
	public void initMyrc(Map<String, Object> out,Integer companyId);
}
