/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-18
 */
package com.ast.ast1949.service.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteCustomTopicDo;
import com.ast.ast1949.persist.company.EsiteCustomTopicDao;
import com.ast.ast1949.service.company.EsiteCustomTopicService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-18
 */
@Component("esiteCustomTopicService")
public class EsiteCustomTopicServiceImpl implements EsiteCustomTopicService {

	@Autowired
	EsiteCustomTopicDao esiteCustomTopicDao;
	
	final static int OVER_LIMIT = 5;
	
	@Override
	public Integer deleteTopicById(Integer id, Integer companyId) {
		Assert.notNull(id, "the id can not be null");
		return esiteCustomTopicDao.deleteTopicById(id, companyId);
	}

	@Override
	public Integer insertTopic(EsiteCustomTopicDo topic) {
		Assert.notNull(topic, "the topic can not be null");
		return esiteCustomTopicDao.insertTopic(topic);
	}

	@Override
	public boolean isTopicNumOverLimit(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		Integer num= esiteCustomTopicDao.countTopicByCompanyId(companyId);
		if(num!=null && num.intValue()<OVER_LIMIT){
			return true;
		}
		return false;
	}
}
