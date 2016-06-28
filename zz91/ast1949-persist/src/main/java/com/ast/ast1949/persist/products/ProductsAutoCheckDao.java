package com.ast.ast1949.persist.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsAutoCheck;

public interface ProductsAutoCheckDao {
	
	public List<ProductsAutoCheck> queryCheckBySize(Integer size);

	public Integer insert(ProductsAutoCheck productsAutoCheck);

	public Integer updateByStatus(Integer id,String checkStatus);
	
	public ProductsAutoCheck queryById(Integer id);
}
