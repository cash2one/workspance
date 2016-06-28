/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-27 上午11:21:11
 */
package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeSupply;

public interface SubnetTradeRecommendDao {

	List<TradeSupply> querySupplyBySubRec(String subnetCategory, Integer size);

}
