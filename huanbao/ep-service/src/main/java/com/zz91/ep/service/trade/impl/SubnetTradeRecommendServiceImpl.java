/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-27 上午11:17:21
 */
package com.zz91.ep.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.trade.SubnetTradeRecommendDao;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.service.trade.SubnetTradeRecommendService;

@Component("subnetTradeRecommendService")
public class SubnetTradeRecommendServiceImpl implements
		SubnetTradeRecommendService {
	
	@Resource
	private SubnetTradeRecommendDao subnetTradeRecommendDao;

	@Override
	public List<TradeSupply> querySupplyBySubRec(String subnetCategory,
			Integer size) {
		if (size!=null && size>50){
			size=50;
		}
		return subnetTradeRecommendDao.querySupplyBySubRec(subnetCategory,size);
	}

}
