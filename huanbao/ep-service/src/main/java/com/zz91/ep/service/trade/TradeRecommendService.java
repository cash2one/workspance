package com.zz91.ep.service.trade;

import com.zz91.ep.domain.trade.TradeRecommend;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-3-7 
 */
public interface TradeRecommendService {
	
	/**
	 * 
	 * 函数名称：createRecommend
	 * 功能描述：添加一条推荐信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer createRecommend(TradeRecommend recommend);
	
	/**
	 * 
	 * 函数名称：cancelRecommend
	 * 取消推荐信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer cancelRecommend(Integer id,Integer rid);

	/**
	 * 
	 * 函数名称：cancelRecommend
	 * 功能描述：重载方法 取消推荐信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer cancelRecommend(Integer targetId, Integer cid, Short type);
}
