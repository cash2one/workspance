/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.dto.trade.TradePropertyValueDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
public interface TradePropertyValueDao {

    /**
     * 插入专业属性信息
     */
    public Integer insertTradePropertyValue(TradePropertyValue tradePropertyValue);
    
    /**
     * 根据供应信息ID查询供应信息专业属性值
     */
    public List<TradePropertyValueDto> queryPropertyValueBySupply(Integer id);
    
    /**
     * 根据供应信息编号删除该供应信息所有专业属性值
     * @param sid
     * @return
     */
    public Integer deleteValueBySupplyId(Integer sid);

	public Integer deleteTradePropertyValueByPropertyId(Integer propertyId);
	
	public List<TradePropertyValueDto> queryPropertyValueBySupplyIdAndCategoryCode(Integer id,String categoryCode);
	
	public Integer updateTradePropertyValue(TradePropertyValue tradePropertyValue);
}