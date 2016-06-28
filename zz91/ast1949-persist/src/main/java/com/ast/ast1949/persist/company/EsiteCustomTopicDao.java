/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-16
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.EsiteCustomTopicDo;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-16
 */
public interface EsiteCustomTopicDao {

	public List<EsiteCustomTopicDo> queryTopicByCompany(Integer companyId);

	public Integer insertTopic(EsiteCustomTopicDo topic);

	public Integer deleteTopicById(Integer id, Integer companyId);
	
	public Integer countTopicByCompanyId(Integer companyId);
}
