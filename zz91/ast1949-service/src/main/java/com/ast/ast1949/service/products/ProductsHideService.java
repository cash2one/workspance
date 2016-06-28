package com.ast.ast1949.service.products;

import com.ast.ast1949.domain.products.ProductsHide;

public interface ProductsHideService {
	public Integer insert(ProductsHide productsHide);
	
	/**
	 * 通过产品id 查找
	 * @param productsId
	 * @return
	 */
	public ProductsHide queryByProductId(Integer productId);
	
	public Integer delete(Integer productId);
	
	public Integer countByCompanyId(Integer companyId);

}
