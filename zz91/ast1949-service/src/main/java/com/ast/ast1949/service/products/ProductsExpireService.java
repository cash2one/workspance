package com.ast.ast1949.service.products;

import com.ast.ast1949.domain.products.ProductsExpire;

public interface ProductsExpireService {
	
	public Integer insert(ProductsExpire productsExpire);
	/**
	 * 通过供求id查找
	 * @param productsId
	 * @return
	 */
	public ProductsExpire queryByProductsId(Integer productsId);
	
	/**
	 * 通过id 更新day字段
	 * @param id
	 * @param day
	 */
	public Integer UpdateDayById(Integer id,Integer day);

}
