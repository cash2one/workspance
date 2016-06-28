package com.ast.ast1949.service.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsRub;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;

public interface ProductsRubService {

	public PageDto<ProductsRub> pageRub(ProductsRub productsRub,PageDto<ProductsRub> page);
	
	public PageDto<ProductsDto> pageRubByAdmin(ProductsRub productsRub,PageDto<ProductsDto> page);
	
	public ProductsRub queryRubByProductId(Integer productId);

	public Integer addProductsRub(ProductsRub productsRub);

	public Integer deleteProductsRubByProductId(Integer id);
	
	public ProductsRub productsToRub(ProductsDO product);
	
	public List<ProductsRub> queryRubForDetail(Integer companyId,Integer size);

	public ProductsDto rubToProductsDto(ProductsRub productsRub);

	public Integer editProductsRub(ProductsRub productsRub);
}
