package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsRub;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsRubDao;

@Component("productsRubDao")
public class ProductsRubDaoImpl extends BaseDaoSupport implements
		ProductsRubDao {

	final static String SQL_FIX = "productsRub";

	@Override
	public Integer deleteByProductId(Integer productId) {
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_FIX, "deleteByProductId"), productId);
	}

	@Override
	public Integer insert(ProductsRub productsRub) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), productsRub);
	}
	
	@Override
	public Integer update(ProductsRub productsRub) {
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "update"), productsRub);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsRub> queryRub(ProductsRub productsRub,
			PageDto<ProductsRub> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productsRub", productsRub);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryRub"), map);
	}

	@Override
	public Integer queryRubCount(ProductsRub productsRub) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productsRub", productsRub);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryRubCount"), map);
	}

	@Override
	public ProductsRub queryRubByProductId(Integer productId) {
		return (ProductsRub) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryRubByProductId"), productId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsRub> queryRubForDetail(Integer companyId,Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryRubForDetail"),map);
	}

}
