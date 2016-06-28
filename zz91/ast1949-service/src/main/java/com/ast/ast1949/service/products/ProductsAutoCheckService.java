package com.ast.ast1949.service.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsAutoCheck;

public interface ProductsAutoCheckService {
	
	final static String WAIT_CHECK = "0";
	final static String CHECKING = "1";
	final static String CHECKED = "2";

	public List<ProductsAutoCheck> queryCheckBySize(Integer size);

	public Integer insert(Integer productId);

	public Integer updateByStatus(Integer id,String checkStatus);
	
	public ProductsAutoCheck queryById(Integer id);

}