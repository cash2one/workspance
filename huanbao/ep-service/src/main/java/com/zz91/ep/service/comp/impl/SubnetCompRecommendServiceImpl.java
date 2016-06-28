/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-26 下午07:41:25
 */
package com.zz91.ep.service.comp.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.comp.SubnetCompRecommendDao;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.service.comp.SubnetCompRecommendService;

@Component("subnetCompRecommendService")
public class SubnetCompRecommendServiceImpl implements
		SubnetCompRecommendService {

	@Resource
	private SubnetCompRecommendDao subnetCompRecommendDao;
	
	@Override
	public List<CompProfile> queryCompBySubRec(String subnetCategory,
			Integer size) {
		if (size!=null && size>50){
			size=50;
		}
		return subnetCompRecommendDao.queryCompBySubRec(subnetCategory,size);
	}
	
}
