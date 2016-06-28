/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-27 上午11:17:21
 */
package com.zz91.ep.admin.service.trade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.SubnetTradeRecommendDao;
import com.zz91.ep.admin.service.trade.SubnetTradeRecommendService;
import com.zz91.ep.domain.trade.SubnetTradeRecommend;

@Component("subnetTradeRecommendService")
public class SubnetTradeRecommendServiceImpl implements
		SubnetTradeRecommendService {

	@Resource
	private SubnetTradeRecommendDao subnetTradeRecommendDao;

	@Override
	public Integer createSubnetTradeRecommend(Integer supplyId, String type) {
		SubnetTradeRecommend recommend = new SubnetTradeRecommend();
		recommend.setSupplyId(supplyId);
		recommend.setSubnetCategory(type);
		return subnetTradeRecommendDao.insertSubnetTradeRecommend(recommend);
	}

	@Override
	public Integer deleteSubnetTradeRecommend(Integer supplyId) {
		return subnetTradeRecommendDao.deleteSubnetTradeRecommend(supplyId);
	}

}
