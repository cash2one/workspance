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
import com.zz91.ep.admin.dao.trade.TradePropertyDao;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradePropertyDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Component("tradePropertyDao")
public class TradePropertyDaoImpl extends BaseDao implements TradePropertyDao {

    final static String SQL_PREFIX="tradeProperty";

    @SuppressWarnings("unchecked")
	@Override
    public List<TradeProperty> queryPropertyByCategoryCode(String categoryCode) {
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPropertyByCategoryCode"), categoryCode);
    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeProperty> querySearchPropertyByCategory(String categoryCode) {
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySearchPropertyByCategory"), categoryCode);
//    }

	@Override
	public Integer deleteTradePropertyById(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteTradePropertyById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradePropertyDto> queryPropertys(TradePropertyDto dto,
			PageDto<TradePropertyDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPropertys"), root);
	}

	@Override
	public Integer queryPropertysCount(TradePropertyDto dto) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("dto", dto);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPropertysCount"), root);
	}

	@Override
	public Integer insertTradeProperty(TradeProperty tradeProperty) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTradeProperty"), tradeProperty);
	}

	@Override
	public String queryMaxCode() {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxCode"));
	}

	@Override
	public Integer deleteTradePropertyByCode(String code) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteTradePropertyByCode"), code);
	}

	@Override
	public TradeProperty queryTradePropertyById(Integer id) {
		return (TradeProperty) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTradePropertyById"), id);
	}

	@Override
	public Integer updateTradeProperty(TradeProperty tradeProperty) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTradeProperty"), tradeProperty);
	}
}