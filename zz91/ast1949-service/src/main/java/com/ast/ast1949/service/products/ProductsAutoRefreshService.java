package com.ast.ast1949.service.products;

import java.util.HashMap;
import java.util.Map;

import com.ast.ast1949.domain.products.ProductsAutoRefresh;

/**
 * 
 * @author zhozuk
 *DATE : 2013.1.16
 */
public interface ProductsAutoRefreshService {
	public static Map<String ,Object> dateMap = new HashMap<String ,Object>();
	
	public ProductsAutoRefresh queryByCid (Integer cid);
	
	public Integer updateRefreshDate (String refreshDate , String refreshRate, Integer id);
	
	public Integer insertRefreshDate(ProductsAutoRefresh productsAutoRefresh);
}
