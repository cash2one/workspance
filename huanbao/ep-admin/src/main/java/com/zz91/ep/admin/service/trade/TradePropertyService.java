/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradePropertyDto;

/**
 * @author totly
 *
 * created on 2011-9-15
 */
public interface TradePropertyService {

    /**
     * 根据产品类别查找对应的可以提供搜索的产品特有属性
     * categoryCode：产品code，不能为null
     * 返回值：List<SearchPropertyDto>=List<搜索属性Dto>
     */
//    public List<SearchPropertyDto> querySearchPropertyByCategory(String categoryCode);

    /**
     * 根据产品类别查找求购需要填写的专业属性内容
     * categoryCode：产品code，不能为null
     * 返回值：List<TradeProperty>
    */
    public List<TradeProperty> queryPropertyByCategoryCode(String categoryCode);


	public Integer createTradeProperty(TradeProperty tradeProperty);

	public PageDto<TradePropertyDto> pageTradeProperty(String categoryCode,
			PageDto<TradePropertyDto> page);

	public Integer deleteTradePropertyById(Integer id);

	public TradeProperty queryTradeCategoryById(Integer id);

	public Integer updateTradeProperty(TradeProperty tradeProperty);
}