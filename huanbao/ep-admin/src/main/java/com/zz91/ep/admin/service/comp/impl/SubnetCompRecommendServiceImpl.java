/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-26 下午07:41:25
 */
package com.zz91.ep.admin.service.comp.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.SubnetCompRecommendDao;
import com.zz91.ep.admin.service.comp.SubnetCompRecommendService;
import com.zz91.ep.domain.comp.SubnetCompRecommend;

@Component("subnetCompRecommendService")
public class SubnetCompRecommendServiceImpl implements
		SubnetCompRecommendService {
	
	@Resource
	private SubnetCompRecommendDao subnetCompRecommendDao;

	@Override
	public Integer createdSubRecComp(Integer cid,String type) {
		SubnetCompRecommend recommend= new SubnetCompRecommend();
		recommend.setCid(cid);
		recommend.setSubnetCategory(type);
		return subnetCompRecommendDao.insertSubnetCompRecommend(recommend);
	}

	@Override
	public Integer deleteSubRecComp(Integer cid) {
		return subnetCompRecommendDao.deleteSubnetCompRecommend(cid);
	}

}
