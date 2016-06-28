/**
 * @author kongsj
 * @date 2015年4月20日
 * 
 */
package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsStar;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsStarDao;

@Component("productsDao")
public class ProductsStarDaoImpl extends BaseDaoSupport implements ProductsStarDao{

	final static String SQL_FIX = "productsStar";
	
	@Override
	public Integer insert(ProductsStar productsStar) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"),productsStar);
	}

	@Override
	public ProductsStar queryById(Integer id) {
		return (ProductsStar) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public ProductsStar queryByProductsId(Integer productsId) {
		return (ProductsStar) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByProductsId"), productsId);
	}

	@Override
	public Integer updateByProductsId(Integer productsId, Integer score) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productsId", productsId);
		map.put("score", score);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateByProductsId"), map);
	}
}