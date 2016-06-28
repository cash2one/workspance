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
import com.zz91.ep.admin.dao.trade.TradePropertyValueDao;
import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.dto.trade.TradePropertyValueDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Component("tradePropertyValue")
public class TradePropertyValueDaoImpl extends BaseDao implements
        TradePropertyValueDao {

    final static String SQL_PREFIX="tradePropertyValue";

    @Override
    public Integer insertTradePropertyValue(
            TradePropertyValue tradePropertyValue) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTradePropertyValue"), tradePropertyValue);
    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradePropertyValueDto> queryPropertyValueBySupply(Integer id) {
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPropertyValueBySupply"), id);
//    }

//	@Override
//	public Integer deleteValueBySupplyId(Integer sid) {
//		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteValueBySupplyId"), sid);
//	}

//	@Override
//	public Integer deleteTradePropertyValueByPropertyId(Integer propertyId) {
//		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteTradePropertyValueByPropertyId"), propertyId);
//		
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradePropertyValueDto> queryPropertyValueBySupplyIdAndCategoryCode(
			Integer id, String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("supplyId", id);
		root.put("categoryCode", categoryCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPropertyValueBySupplyIdAndCategoryCode"), root);
	}

	@Override
	public Integer updateTradePropertyValue(
			TradePropertyValue tradePropertyValue) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTradePropertyValue"), tradePropertyValue);
	}

}