/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-27 上午11:24:47
 */
package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.SubnetTradeRecommendDao;
import com.zz91.ep.domain.trade.TradeSupply;

@Component("subnetTradeRecommendDao")
public class SubnetTradeRecommendDaoImpl extends BaseDao implements
		SubnetTradeRecommendDao {

	final static String PRIFIX="subnetTradeRecommend";

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> querySupplyBySubRec(String subnetCategory,
			Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("subnetCategory", subnetCategory);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(PRIFIX, "querySupplyBySubRec"), root);
	}

}
