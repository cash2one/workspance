package com.zz91.ep.admin.service.trade;

import com.zz91.ep.domain.trade.TradeRecommend;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-3-7 
 */
public interface TradeRecommendService {
	
	/**
	 * 添加一条推荐信息
	 * @param recommend
	 * @return
	 */
	public Integer createRecommend(TradeRecommend recommend);
	
	/**
	 * 取消推荐信息
	 * @param id
	 * @param rid
	 * @return
	 */
	public Integer cancelRecommend(Integer id,Integer rid);

	/**
	 * 取消推荐信息
	 * @param targetId
	 * @param cid
	 * @param type
	 * @return
	 */
//	public Integer cancelRecommend(Integer targetId, Integer cid, Short type);
}
