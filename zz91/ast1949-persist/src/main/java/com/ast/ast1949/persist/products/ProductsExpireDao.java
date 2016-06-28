package com.ast.ast1949.persist.products;

import com.ast.ast1949.domain.products.ProductsExpire;

public interface ProductsExpireDao {
	
	public Integer insert(ProductsExpire productsExpire);
	
	/**
	 * 通过产品id 查找
	 * @param productsId
	 * @return
	 */
	public ProductsExpire queryByProductsId(Integer productsId);
	
	/**
	 * 通过id 更新day字段
	 * @param id
	 * @param day
	 * @return
	 */
	public Integer updateDayById(Integer id,Integer day);

}
