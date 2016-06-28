package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ast.ast1949.domain.products.ProductsViewHistory;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsViewHistoryDao;

/**
 * @Author:kongsj
 * @Date:2011-12-27
 */
@Repository
public class ProductsViewHistoryDaoImpl extends BaseDaoSupport implements
		ProductsViewHistoryDao {
	private static String sqlPreFix = "productsViewHistory";

	@Override
	public Integer insert(ProductsViewHistory productsViewHistory) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insert"),productsViewHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsViewHistory> queryHistory(String cookieKey, Integer size) {
		Map<String,Object> map = new HashMap<String,Object> ();
		map.put("cookieKey", cookieKey);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryHistory"), map);
	}

	@Override
	public Integer updateCompanyIdByCookieKey(String cookieKey, Integer companyId) {
		Map<String,Object> map = new HashMap<String,Object> ();
		map.put("cookieKey", cookieKey);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateCompanyIdByCookieKey"), map);
	}

	@Override
	public String queryKeyByCompanyId(Integer companyId) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryKeyByCompanyId"), companyId);
	}

	@Override
	public Integer updateGmtLastView(Integer id) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateGmtLastView"), id);
	}

}
