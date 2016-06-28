package com.kl91.persist.products;

import java.util.List;

import com.kl91.domain.products.ProductsCategory;

public interface ProductsCategoryDao {
	
	public String queryMaxCodeBypreCode(String preCode);
	
	public Integer insertCategoryProducts(ProductsCategory productsCategory);
	
	public Integer updatecategoryProducts(ProductsCategory productsCategory);
	
	public Integer delete(Integer id);
	
	public ProductsCategory queryById(Integer id);
	
	public List<ProductsCategory> queryByCode(String code);
	
	public List<ProductsCategory> queryAllCategoryProducts();
}
