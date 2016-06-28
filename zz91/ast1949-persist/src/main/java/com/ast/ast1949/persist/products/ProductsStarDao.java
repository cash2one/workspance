/**
 * @author kongsj
 * @date 2015年4月20日
 * 
 */
package com.ast.ast1949.persist.products;

import com.ast.ast1949.domain.products.ProductsStar;

public interface ProductsStarDao {
	public Integer insert(ProductsStar productsStar);

	public ProductsStar queryById(Integer id);

	public ProductsStar queryByProductsId(Integer productsId);

	public Integer updateByProductsId(Integer productsId, Integer score);
}