package com.ast.ast1949.service.spot;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;

/**
 *	author:kongsj
 *	date:2013-3-20
 */
public interface SpotService {
	
	/**
	 * 获取首页data_index 地区 推荐 数据
	 * @param code
	 * @param size
	 * @return
	 */
	public List<Map<String,Object>> getAreaSpotByDataIndex(String code ,Integer size);

	public PageDto<ProductsDto> pageSpotBySearchEngine(ProductsDO product,String areaCode, Boolean havePic, PageDto<ProductsDto> page);
	
	/**
	 * 搜索最新现货供求 排除重复公司
	 * @param product
	 * @param havePic
	 * @param size
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDto> pageSpotBySearchEngineForSimpleCompany(ProductsDO product, Boolean havePic,Integer size, PageDto<ProductsDto> page);
	/**
	 *  获取最新订单的现货信息列表
	 * @param size
	 * @return
	 */
	public List<ProductsDto> queryLastestOrderSpot(Integer size);
}
