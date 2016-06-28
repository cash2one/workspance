package com.ast.ast1949.persist.market;

import java.util.List;

import com.ast.ast1949.domain.market.ProductMarket;

public interface ProductMarketDao {
	public List<Integer> queryProductMarketBySize(Integer size);
	/**
	 * 市场供求总数
	 * @return
	 */
	public Integer countProducts();
	
	public ProductMarket queryProductMarketByProductId(Integer productId);

}
