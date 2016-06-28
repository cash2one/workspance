package com.ast.ast1949.service.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsViewHistory;

/**
 * @Author:kongsj
 * @Date:2011-12-27
 */
public interface ProductsViewHistoryService {
	
	public final static String DOMAIN="zz91.com";
	public final static String HISTORY_KEY="phistory";
	
	public Integer create(ProductsViewHistory productsViewHistory);

	public List<ProductsViewHistory> queryHistory(String cookieKey, Integer size);

	public Integer updateCompanyIdByCookieKey(Integer companyId, String cookieKey);

	public String queryKeyByCompanyId(Integer companyId);

	public Integer updateGmtLastView(Integer id);
	
}
