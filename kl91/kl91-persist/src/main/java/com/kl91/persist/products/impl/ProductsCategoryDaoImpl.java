package com.kl91.persist.products.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kl91.domain.products.ProductsCategory;
import com.kl91.persist.BaseDaoSupport;
import com.kl91.persist.products.ProductsCategoryDao;

@Component("productsCategoryDao")
public class ProductsCategoryDaoImpl extends BaseDaoSupport implements ProductsCategoryDao{

	private static String SQL_PREFIX = "productsCategory";
	@Override
	public Integer delete(Integer id) {
		return (Integer)getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "delete"),id);
	}

	@Override
	public Integer insertCategoryProducts(ProductsCategory productsCategory) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"),productsCategory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsCategory> queryByCode(String code) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryByCode"),code);
	}

	@Override
	public ProductsCategory queryById(Integer id) {
		return (ProductsCategory) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"),id);
	}

	@Override
	public String queryMaxCodeBypreCode(String preCode) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMaxCodeBypreCode"),preCode);
	}

	@Override
	public Integer updatecategoryProducts(ProductsCategory productsCategory) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "update"),productsCategory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsCategory> queryAllCategoryProducts() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllCategoryProducts"));
	}

}
