package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsAutoCheck;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsAutoCheckDao;

@Component("productsAutoCheckDao")
public class ProductsAutoCheckDaoImpl extends BaseDaoSupport implements
		ProductsAutoCheckDao {

	final static String SQL_FIX = "productsAutoCheck";
	
	@Override
	public Integer insert(ProductsAutoCheck productsAutoCheck) {
		return  (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), productsAutoCheck);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsAutoCheck> queryCheckBySize(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryCheckBySize"), size);
	}

	@Override
	public Integer updateByStatus(Integer id, String checkStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"),map);
	}

	@Override
	public ProductsAutoCheck queryById(Integer id) {
		return (ProductsAutoCheck) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

}
