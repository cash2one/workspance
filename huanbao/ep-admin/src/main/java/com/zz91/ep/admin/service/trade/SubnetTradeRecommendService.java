/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-27 上午11:14:16
 */
package com.zz91.ep.admin.service.trade;

public interface SubnetTradeRecommendService {
	
	/**
	 * 创建一条子网推荐记录
	 * @param supplyId
	 * @param type
	 * @return
	 */
	public Integer createSubnetTradeRecommend(Integer supplyId,String type);
	
	/**
	 * 删除推荐记录
	 * @param supplyId
	 * @return
	 */
	public Integer deleteSubnetTradeRecommend(Integer supplyId);

}
