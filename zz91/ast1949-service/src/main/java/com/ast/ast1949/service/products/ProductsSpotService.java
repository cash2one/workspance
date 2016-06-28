package com.ast.ast1949.service.products;

import java.text.ParseException;
import java.util.Map;

import com.ast.ast1949.domain.products.ProductsSpot;

public interface ProductsSpotService {
	/**
	 * 添加一条现货供求
	 * @param productId
	 * @return
	 */
	public Integer addOneSpot(Integer productId);
	/**
	 * 永久删除一条现货供求
	 * @param id
	 * @return
	 */
	public Integer removeOneSpot(Integer id);
	
	/**
	 * 根据现货id搜索现货
	 * @param productId
	 * @return
	 */
	public ProductsSpot queryById(Integer id);
	
	/**
	 * 根据供求id搜索现货
	 * @param productId
	 * @return
	 */
	public ProductsSpot queryByProductId(Integer productId);
	
	public Integer updateIsTeByProductsId(String isTe,Integer productId);
	
	public Integer updateIsHotByProductsId(String isHot,Integer productId);
	
	public Integer updateIsYouByProductsId(String isYou,Integer productId);
	
	public Integer updateIsBailByProductsId(String isBail,Integer productId);
	/**
	 * 现货页面左上角 数字
	 * @param out
	 * @throws ParseException 
	 */
	void buildBaseData(Map<String, Object> out) throws ParseException;
	
	/**
	 * 累计浏览 现货 - 关注数
	 */
	public Integer updateViewCountById(Integer id);
	/**
	 * 根据现货id搜索改现货浏览数(view_count)
	 * @param id
	 * @return
	 */
	public Integer queryViewCountById(Integer id);
	
}
