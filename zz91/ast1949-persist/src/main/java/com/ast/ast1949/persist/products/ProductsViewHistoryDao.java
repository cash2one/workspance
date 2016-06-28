package com.ast.ast1949.persist.products;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.products.ProductsViewHistory;

/**
 * @Author:kongsj
 * @Date:2011-12-27
 */
public interface ProductsViewHistoryDao {
	public Integer insert(ProductsViewHistory productsViewHistory);

	public List<ProductsViewHistory> queryHistory(String cookieKey, Integer size);

	public Integer updateCompanyIdByCookieKey(String cookieKey, Integer companyId);

	public String queryKeyByCompanyId(Integer companyId);

	public Integer updateGmtLastView(Integer id);
}
