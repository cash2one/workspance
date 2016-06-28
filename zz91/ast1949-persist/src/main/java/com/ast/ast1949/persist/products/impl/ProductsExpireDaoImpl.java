package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsExpire;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsExpireDao;
@Component("productsExpireDao")
public class ProductsExpireDaoImpl extends BaseDaoSupport implements ProductsExpireDao{
	private static String sqlPreFix = "productsExpire";
	@Override
	public Integer insert(ProductsExpire productsExpire) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insert"), productsExpire);
	}

	@Override
	public ProductsExpire queryByProductsId(Integer productsId) {
		return (ProductsExpire) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryByProductsId"), productsId);
	}

	@Override
	public Integer updateDayById(Integer id, Integer day) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("day", day);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateDayById"), map);
	} 

}
