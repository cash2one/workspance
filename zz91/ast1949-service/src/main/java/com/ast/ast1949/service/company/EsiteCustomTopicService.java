/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-17
 */
package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.EsiteCustomTopicDo;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-17
 */
public interface EsiteCustomTopicService {

	public Integer insertTopic(EsiteCustomTopicDo topic);
	
	public Integer deleteTopicById(Integer id, Integer companyId);
	
	/**
	 * 判断自定义风格库是否已经超出限制
	 * @param companyId：公司ID，不能为null
	 * @return
	 * 	true:表示没有超过限制
	 * 	false:表示已经超过限制
	 */
	public boolean isTopicNumOverLimit(Integer companyId);
}
