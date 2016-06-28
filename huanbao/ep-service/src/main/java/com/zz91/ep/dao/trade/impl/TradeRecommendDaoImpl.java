package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.TradeRecommendDao;
import com.zz91.ep.domain.trade.TradeRecommend;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-3-7 
 */
@Component("tradeRecommendDao")
public class TradeRecommendDaoImpl extends BaseDao implements TradeRecommendDao {
	
	final static String SQL_PREFIX="tradeRecommend";

	@Override
	public Integer insertRecommend(TradeRecommend recommend) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertRecommend"), recommend);
	}

	@Override
	public Integer deleteRecommend(Integer targetId,Integer rid) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("rid", rid);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRecommend"), root);
	}

	@Override
	public Integer deleteRecommend(Integer targetId, Integer cid, Short type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("cid", cid);
		root.put("type", type);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRecommendByCid"), root);
	}

	@Override
	public TradeRecommend queryRecommendByTargetIdAndType(Integer targetId,
			Short type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("type", type);
		return (TradeRecommend) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryRecommendByTargetIdAndType"), root);
	}

}
