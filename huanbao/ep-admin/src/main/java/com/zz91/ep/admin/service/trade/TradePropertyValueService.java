/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.dto.trade.TradePropertyValueDto;

/**
 * @author totly
 *
 * created on 2011-9-15
 */
public interface TradePropertyValueService {
 
    /**
     * 创建专业属性信息
     */
    public Integer createTradePropertyValue(TradePropertyValue tradePropertyValue);
    
    /**
     * 根据供应信息id删除专业属性值
     */
//    public Integer deleteTradePropertyValue(Integer supplyId);
    /**
     * 根据供应信息ID查询供应信息对应的专业属性值
     */
//    public List<TradePropertyValueDto> queryTradePropertyValueBySupplyId(Integer supplyId);
    /**
     * 根据供应信息ID和类别查询专业属性
     * @param id
     * @param categoryCode
     * @return
     */
    public List<TradePropertyValueDto> queryPropertyValueBySupplyIdAndCategoryCode(Integer id,String categoryCode);
    /**
     * 更新属性值
     * @param tradePropertyValue
     * @return
     */
    public Integer updateTradePropertyValue(TradePropertyValue tradePropertyValue);

	public List<TradePropertyValueDto> queryPropertyValueByBuyIdAndCategoryCode(
			Integer id, String categoryCode);
}