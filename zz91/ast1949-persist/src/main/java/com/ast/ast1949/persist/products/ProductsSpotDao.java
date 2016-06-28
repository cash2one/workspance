package com.ast.ast1949.persist.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsSpot;

public interface ProductsSpotDao {
	public Integer insert(ProductsSpot productsSpot);

	public Integer delete(Integer id);
	
	public ProductsSpot queryById(Integer id);
	
	public ProductsSpot queryByProductId(Integer productId);
	
	public Integer updateIsTeByProductsId(String isTe,Integer productId);
	
	public Integer updateIsHotByProductsId(String isHot,Integer productId);
	
	public Integer updateIsYouByProductsId(String isYou,Integer productId);
	
	public Integer updateIsBailByProductsId(String isBail,Integer productId);
	
	public List<ProductsSpot> querySpot(Integer start, Integer size);
	
	public Integer queryCountSpot();

	public Integer updateViewCountById(Integer id);

	public Integer queryViewCountById(Integer id);
}
