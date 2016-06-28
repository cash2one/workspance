package com.ast.ast1949.persist.products;

import com.ast.ast1949.domain.products.ProductsAutoRefresh;

/**
 * 
 * @author zhozuk
 * DATE : 2013.1.16
 */
public interface ProductsAutoRefreshDAO {
	
	public ProductsAutoRefresh queryByCompanyId (Integer companyId);
	
	public Integer update (String refreshDate , Integer id);
	
	public Integer insert (ProductsAutoRefresh productsAutoRefresh);

}
