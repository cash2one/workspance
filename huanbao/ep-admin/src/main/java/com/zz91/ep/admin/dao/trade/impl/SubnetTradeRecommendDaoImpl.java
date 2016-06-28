/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-27 上午11:24:47
 */
package com.zz91.ep.admin.dao.trade.impl;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.SubnetTradeRecommendDao;
import com.zz91.ep.domain.trade.SubnetTradeRecommend;

@Component("subnetTradeRecommendDao")
public class SubnetTradeRecommendDaoImpl extends BaseDao implements
		SubnetTradeRecommendDao {

	final static String PRIFIX="subnetTradeRecommend";

	@Override
	public Integer insertSubnetTradeRecommend(SubnetTradeRecommend recommend) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(PRIFIX, "insertSubnetTradeRecommend"), recommend);
	}

	@Override
	public Integer deleteSubnetTradeRecommend(Integer supplyId) {
		return getSqlMapClientTemplate().delete(buildId(PRIFIX, "deleteSubnetTradeRecommend"), supplyId);
	}

}
