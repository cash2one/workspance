/**
 * @author kongsj
 * @date 2015年4月20日
 * 
 */
package com.ast.ast1949.service.products;

import com.ast.ast1949.domain.products.ProductsStar;

public interface ProductsStarService {
	public Integer insert(Integer productsId,Integer score);

	public ProductsStar queryById(Integer id);

	public ProductsStar queryByProductsId(Integer productsId);

	public Integer updateByProductsId(Integer productsId, Integer score);

}