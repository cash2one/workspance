package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsSpotDao;

@Component("productsSpotDao")
public class ProductsSpotDaoImpl extends BaseDaoSupport implements
		ProductsSpotDao {
	final static String SQL_FIX = "productSpot";

	public Integer insert(ProductsSpot productsSpot) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), productsSpot);
	}

	public Integer delete(Integer id) {
		return (Integer) getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_FIX, "delete"), id);
	}

	@Override
	public ProductsSpot queryByProductId(Integer productId) {
		return (ProductsSpot) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryByProductId"), productId);
	}

	@Override
	public Integer updateIsBailByProductsId(String isBail, Integer productId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isBail", isBail);
		map.put("productId", productId);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateIsBailByProductsId"),map);
	}

	@Override
	public Integer updateIsHotByProductsId(String isHot, Integer productId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isHot", isHot);
		map.put("productId", productId);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateIsHotByProductsId"),map);
	}

	@Override
	public Integer updateIsTeByProductsId(String isTe, Integer productId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isTe", isTe);
		map.put("productId", productId);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateIsTeByProductsId"),map);
	}

	@Override
	public Integer updateIsYouByProductsId(String isYou, Integer productId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isYou", isYou);
		map.put("productId", productId);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateIsYouByProductsId"),map);
	}
	@SuppressWarnings("unchecked")
	public List<ProductsSpot> querySpot(Integer start, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "querySpot"), map);
	}
	
	public Integer queryCountSpot(){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountSpot"));
	}

	@Override
	public ProductsSpot queryById(Integer id) {
		return (ProductsSpot) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}
	
	@Override
	public Integer updateViewCountById(Integer id){
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateViewCountById"),id);
	}
	
	@Override
	public Integer queryViewCountById(Integer id){
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "queryViewCountById"),id);
	}
}
