/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.TradePropertyValueDao;
import com.zz91.ep.admin.service.trade.TradePropertyValueService;
import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.dto.trade.TradePropertyValueDto;
import com.zz91.util.Assert;

/**
 * @author totly
 *
 * created on 2011-9-16
 */
@Component("tradePropertyValueService")
public class TradePropertyValueServiceImpl implements TradePropertyValueService {

    @Resource
    private TradePropertyValueDao tradePropertyValueDao;

    @Override
    public Integer createTradePropertyValue(
            TradePropertyValue tradePropertyValue) {
        Assert.notNull(tradePropertyValue, "the tradePropertyValue can not be null");
        Assert.notNull(tradePropertyValue.getPropertyId(), "the PropertyId can not be null");
        Assert.notNull(tradePropertyValue.getSupplyId(), "the SupplyId can not be null");
        return tradePropertyValueDao.insertTradePropertyValue(tradePropertyValue);
    }

//	@Override
//	public Integer deleteTradePropertyValue(Integer supplyId) {
//		Assert.notNull(supplyId, "the supplyId can not be null");
//		return tradePropertyValueDao.deleteValueBySupplyId(supplyId);
//	}

//	@Override
//	public List<TradePropertyValueDto> queryTradePropertyValueBySupplyId(
//			Integer supplyId) {
//		Assert.notNull(supplyId, "the supplyId can not be null");
//		return tradePropertyValueDao.queryPropertyValueBySupply(supplyId);
//	}

	@Override
	public List<TradePropertyValueDto> queryPropertyValueBySupplyIdAndCategoryCode(
			Integer id, String categoryCode) {
		return tradePropertyValueDao.queryPropertyValueBySupplyIdAndCategoryCode(id, categoryCode);
	}

	@Override
	public Integer updateTradePropertyValue(
			TradePropertyValue tradePropertyValue) {
		return tradePropertyValueDao.updateTradePropertyValue(tradePropertyValue);
	}

	@Override
	public List<TradePropertyValueDto> queryPropertyValueByBuyIdAndCategoryCode(
			Integer id, String categoryCode) {
		return null;
	}
}