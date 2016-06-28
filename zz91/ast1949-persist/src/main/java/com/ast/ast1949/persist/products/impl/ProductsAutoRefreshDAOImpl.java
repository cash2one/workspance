package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsAutoRefresh;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsAutoRefreshDAO;

@Component("productsAutoRefreshDAO")
public class ProductsAutoRefreshDAOImpl extends BaseDaoSupport implements ProductsAutoRefreshDAO {

	private static String sqlPreFix = "productsAutoRefresh";
	@Override
	public ProductsAutoRefresh queryByCompanyId(Integer companyId) {
		
		return (ProductsAutoRefresh) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "query"), companyId);
	}

	@Override
	public Integer update(String refreshDate, Integer id) {
		Map<String , Object>  map = new HashMap<String ,Object>();
		map.put("refreshDate", refreshDate);
		map.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "update"), map);
	}

	@Override
	public Integer insert(ProductsAutoRefresh productsAutoRefresh) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insert"), productsAutoRefresh);
	}

}
