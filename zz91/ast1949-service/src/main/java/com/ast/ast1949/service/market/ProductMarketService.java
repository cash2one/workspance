package com.ast.ast1949.service.market;

import java.util.List;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketDto;
import com.ast.ast1949.dto.products.ProductsDto;

public interface ProductMarketService {
	/**
	 * 
	 * @param page
	 * @param marketId
	 * @param type
	 * @param industry
	 * @param flag
	 * @param isVip 1:为高会
	 * @param isYP	1：是样品
	 * @return
	 */
	public PageDto<ProductsDto> pageSearchProducts(PageDto<ProductsDto> page, Integer marketId, String type, String industry, Integer flag,Integer isVip,Integer isYP);
	
	public List<ProductsDto> queryProductMarketBySize(Integer size);
	
	public Integer countProducts();
	
	/**
	 * 
	 * @param page
	 * @param marketId
	 * @param type
	 * @param industry
	 * @param flag
	 * @param isVip 1:为高会
	 * @param isYP	1：是样品
	 * @return
	 */
	public PageDto<ProductsDto> pageSearchProductsByAdmin(PageDto<ProductsDto> page, Integer marketId, String type, String industry, Integer flag,Integer isVip,Integer isYP);
	/**
	 * 获取供求
	 * @param page
	 * @param type
	 * @param industry
	 * @param isVip
	 * @param isYP
	 * @return
	 */
	public PageDto<MarketDto> pageSearchProductsForZhuanTi(PageDto<MarketDto> page, String province, String industry,Integer isVip,Integer isYP,Integer index);

}
