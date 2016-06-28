package com.zz91.ep.admin.dao.trade;

import com.zz91.ep.domain.trade.TradeRecommend;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-3-7 
 */
public interface TradeRecommendDao {
	
	/**
	 * 添加一条推荐信息
	 * @param recommend
	 * @return
	 */
	public Integer insertRecommend(TradeRecommend recommend);
	
	/**
	 * 删除推荐信息
	 * @param targetId
	 * @param rid
	 * @return
	 */
	public Integer deleteRecommend(Integer targetId,Integer rid);
	
	/**
	 * 查询推荐信息
	 * @param targetId
	 * @param type
	 * @return
	 */
	public TradeRecommend queryRecommendByTargetIdAndType(Integer targetId,Short type);
	
	/**
	 * 删除推荐信息
	 * @param targetId
	 * @param cid
	 * @param type
	 * @return
	 */
//	public Integer deleteRecommend(Integer targetId, Integer cid, Short type);
}
