package com.ast.ast1949.persist.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsRub;
import com.ast.ast1949.dto.PageDto;

public interface ProductsRubDao {
	public List<ProductsRub> queryRub(ProductsRub productsRub,PageDto<ProductsRub> page);

	public Integer queryRubCount(ProductsRub productsRub);

	public ProductsRub queryRubByProductId(Integer productId);

	public Integer insert(ProductsRub productsRub);

	public Integer deleteByProductId(Integer productId);
	
	public List<ProductsRub> queryRubForDetail(Integer companyId,Integer size);

	public Integer update(ProductsRub productsRub);
}
