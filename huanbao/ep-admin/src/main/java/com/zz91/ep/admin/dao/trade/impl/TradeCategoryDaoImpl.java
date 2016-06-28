/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.TradeCategoryDao;
import com.zz91.ep.domain.trade.TradeCategory;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Component("tradeCategoryDao")
public class TradeCategoryDaoImpl extends BaseDao implements TradeCategoryDao {

    final static String SQL_PREFIX="tradeCategory";

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeCategory> queryCategoryByName(String name, Integer deep,
//            Integer count) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("name", name);
//        root.put("deep", deep);
//        root.put("count", count);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByName"), root);
//    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeCategory> queryCategoryByParent(String parentCode,
//            Integer deep, Integer count) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("parentCode", parentCode);
//        root.put("deep", deep);
//        root.put("count", count);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByParent"), root);
//
//    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeCategory> queryCategoryByTags(String parentCode, String tags,
//            Integer deep, Integer count) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("parentCode", parentCode);
//        root.put("tags", tags);
//        root.put("deep", deep);
//        root.put("count", count);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByTags"), root);
//    }

    @SuppressWarnings("unchecked")
	@Override
    public List<TradeCategory> queryTradeSupplyAll() {
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTradeSupplyAll"));
    }

    @Override
    public String queryCategoryCodeByCategoryName(String categoryName) {
        return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCategoryCodeByCategoryName"), categoryName);
    }

//    @Override
//    public Integer queryIsLeafByCode(String code) {
//        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryIsLeafByCode"), code);
//    }

	@Override
	public String queryBuyCategoryCodeByCategoryName(String categoryName) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuyCategoryCodeByCategoryName"), categoryName);
	}

	@Override
	public String querySupplyCategoryCodeByCategoryName(String categoryName) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyCategoryCodeByCategoryName"), categoryName);
	}

	@Override
	public Integer countChild(String code) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countChild"), code);
	}

	@Override
	public Integer deleteTradeCategory(String code) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteTradeCategory"), code);
	}

	@Override
	public Integer insertTradeCategory(TradeCategory tradeCategory) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTradeCategory"), tradeCategory);
	}

	@Override
	public TradeCategory queryCategoryByCode(String code) {
		return (TradeCategory) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCategoryByCode"), code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCategory> queryChild(String parentCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryChild"), parentCode);
	}

	@Override
	public String queryMaxCodeOfChild(String parentCode) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxCodeOfChild"), parentCode);
	}

	@Override
	public Integer updateTradeCategory(TradeCategory tradeCategory) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTradeCategory"), tradeCategory);
	}

	@Override
	public Integer updateParentLeafByParentCode(String parentCode) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateParentLeafByParentCode"), parentCode);
	}

	@Override
	public Integer countSupplyChild(String code) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countSupplyChild"), code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCategory> querySupplyChild(String parentCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyChild"), parentCode);
	}

	@Override
	public Integer countBuyChild(String code) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countBuyChild"), code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCategory> queryBuyChild(String parentCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuyChild"), parentCode);
	}

	@Override
	public Integer queryCountByNameOrTags(String name, String tags) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("name", name);
		root.put("tags", tags);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountByNameOrTags"), root);
	}
}