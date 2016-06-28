package com.kl91.persist.products;

import java.util.List;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsDto;
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.products.Products;

public interface ProductsDao {

	public Integer insert(Products product);

	public Integer update(Products product);
	
	public Integer delete(Integer id);

	public List<Products> queryProducts(Products products,PageDto<Products> page);

	public Integer queryProductsCount(ProductsSearchDto searchDto,PageDto<ProductsDto> page);
	
	public Products queryById(Integer id);

	public Integer refreshProductsByIds(Integer[] ids);

	public Integer updateProductsIsNoPub(Integer[] ids,Integer publishFlag);
	
	public List<ProductsDto> queryProductsForList(ProductsSearchDto searchDto,Integer size,PageDto<ProductsDto> page);
	
	public List<Products> queryProductsByCompanyId(Integer companyId,Products products,PageDto<Products> page);
	
	public ProductsDto queryProductsAndCompanyById(Integer id);
	
	public Integer countProductsIsPassByCompanyId(Integer companyId);

	public Integer deleteMost(Integer[] ids,Integer deleteFlag);
	
	public Integer countProducts(Integer companyId, String productType);
	public Integer updatePub(Integer id);

}
