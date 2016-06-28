package com.zz91.ep.admin.service.trade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.TradeRecommendDao;
import com.zz91.ep.admin.service.trade.TradeRecommendService;
import com.zz91.ep.domain.trade.TradeRecommend;

/**
 * @author qizj
 * @email qizj@zz91.net
 * @version 创建时间：2012-3-7
 */
@Component("tradeRecommendService")
public class TradeRecommendServiceImpl implements TradeRecommendService {

	@Resource
	private TradeRecommendDao tradeRecommendDao;

	@Override
	public Integer createRecommend(TradeRecommend recommend) {
		Integer reInteger=0;
		TradeRecommend recommend2=tradeRecommendDao.queryRecommendByTargetIdAndType(
				recommend.getTargetId(), recommend.getType());
		if (recommend2!=null){
			reInteger=1;
		}else {
			reInteger = tradeRecommendDao.insertRecommend(recommend);
		}
		return reInteger;
	}

	@Override
	public Integer cancelRecommend(Integer id, Integer rid) {
		return tradeRecommendDao.deleteRecommend(id, rid);
	}

//	@Override
//	public Integer cancelRecommend(Integer targetId, Integer cid, Short type) {
//		Assert.notNull(targetId, "the targetId can not be null");
//		Assert.notNull(cid, "the cid can not be null");
//		Assert.notNull(type, "the type can not be null");
//		return tradeRecommendDao.deleteRecommend(targetId,cid,type);
//	}

}
