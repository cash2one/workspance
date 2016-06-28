/*
 * 文件名称：TradeGroupDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-18 下午3:45:53
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.TradeGroupDao;
import com.zz91.ep.domain.trade.TradeGroup;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：用户自定义产品类别操作实现类
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Component("tradeGroupDao")
public class TradeGroupDaoImpl extends BaseDao implements TradeGroupDao {

	final static String SQL_PREFIX = "tradegroup";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeGroup> queryTradeGroupById(Integer cid, Integer parentId) {
		Map<String, Object> root = new HashMap<String, Object>();
        root.put("parentId", parentId);
        root.put("cid", cid);
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTradeGroupById"), root);
	}

	@Override
	public String queryNameById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameById"), id);
	}
	
	 @Override
	    public Integer insertTradeGroup(TradeGroup tradeGroup) {
	        return (Integer) getSqlMapClientTemplate().insert((buildId(SQL_PREFIX, "insertTradeGroup")), tradeGroup);
	    }
	 
	  @Override
	    public Integer updateTradeGroup(TradeGroup tradeGroup) {
	        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTradeGroup"), tradeGroup);
	    }
	  
	  @Override
	    public Integer deleteTradeGroup(Integer gid, Integer cid) {
	        Map<String, Object> root = new HashMap<String, Object>();
	        root.put("gid", gid);
	        root.put("cid", cid);
	        return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteTradeGroup"), root);
	    }

	@Override
	public TradeGroup queryTradeGroup(Integer id) {
		
		return (TradeGroup)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTradeGroup"),id);
	}

	@Override
	public Integer queryChildCount(Integer cid, Integer parentId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("parentId", parentId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryChildCount"), root);
	}

}