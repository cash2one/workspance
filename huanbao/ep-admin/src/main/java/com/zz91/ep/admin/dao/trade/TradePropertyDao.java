/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradePropertyDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
public interface TradePropertyDao {

    /**
     * 根据产品类别查找对应的可以提供搜索的产品特有属性
     * categoryCode：产品code，不能为null
     * 返回值：List<CategoryCode>(属性名称, 属性ID, 搜索值)
     */
//    public List<TradeProperty> querySearchPropertyByCategory(String categoryCode);

    /**
     * 根据产品类别查找求购需要填写的专业属性内容
     * categoryCode：产品code，不能为null
     * 返回值：List<TradePropertyDto>
    */
    public List<TradeProperty> queryPropertyByCategoryCode(String categoryCode);

	public Integer deleteTradePropertyById(Integer id);
	
	public Integer deleteTradePropertyByCode(String code);

	public List<TradePropertyDto> queryPropertys(TradePropertyDto dto,
			PageDto<TradePropertyDto> page);

	public Integer queryPropertysCount(TradePropertyDto dto);

	public Integer insertTradeProperty(TradeProperty tradeProperty);

	public String queryMaxCode();

	public TradeProperty queryTradePropertyById(Integer id);

	public Integer updateTradeProperty(TradeProperty tradeProperty);

}