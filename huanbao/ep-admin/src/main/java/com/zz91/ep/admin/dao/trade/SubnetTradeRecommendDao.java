/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-27 上午11:21:11
 */
package com.zz91.ep.admin.dao.trade;

import com.zz91.ep.domain.trade.SubnetTradeRecommend;

public interface SubnetTradeRecommendDao {
	
	public Integer insertSubnetTradeRecommend(SubnetTradeRecommend recommend);
	
	public Integer deleteSubnetTradeRecommend(Integer supplyId);

}
