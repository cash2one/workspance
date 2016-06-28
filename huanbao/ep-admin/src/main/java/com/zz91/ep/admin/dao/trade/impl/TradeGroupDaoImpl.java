/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-28
 */
package com.zz91.ep.admin.dao.trade.impl;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.TradeGroupDao;

/**
 * @author totly
 *
 * created on 2011-9-28
 */
@Component("tradeGroupDao")
public class TradeGroupDaoImpl extends BaseDao implements TradeGroupDao {

    final static String SQL_PREFIX = "tradeGroup";

//    @Override
//    public Integer deleteTradeGroup(Integer gid, Integer cid) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("gid", gid);
//        root.put("cid", cid);
//        return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteTradeGroup"), root);
//    }

//    @Override
//    public Integer insertTradeGroup(TradeGroup tradeGroup) {
//        return (Integer) getSqlMapClientTemplate().insert((buildId(SQL_PREFIX, "insertTradeGroup")), tradeGroup);
//    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeGroup> queryTradeGroupById(Integer cid, Integer parentId) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("parentId", parentId);
//        root.put("cid", cid);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTradeGroupById"), root);
//    }

//    @Override
//    public Integer updateTradeGroup(TradeGroup tradeGroup) {
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTradeGroup"), tradeGroup);
//    }

//	@Override
//	public String queryNameById(Integer group) {
//		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameById"),group);
//	}
}