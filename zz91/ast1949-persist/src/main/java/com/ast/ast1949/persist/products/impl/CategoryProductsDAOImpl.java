/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-2
 */
package com.ast.ast1949.persist.products.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.persist.BaseDaoSupportMultipleDataSource;
import com.ast.ast1949.persist.products.CategoryProductsDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("categoryProductsDAO")
public class CategoryProductsDAOImpl extends BaseDaoSupportMultipleDataSource implements
		CategoryProductsDAO {
	private static final String SQL_FIX = "categoryProducts";
//	@Resource
//	private BaseDaoSupportMultipleDataSource baseDaoSupportMultipleDataSource;

	@SuppressWarnings("unchecked")
	public List<CategoryProductsDO> queryAllCategoryProducts() {
		try {
			return getSqlMapClient2().queryForList("categoryProducts.queryAllCategoryProducts");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CategoryProductsDO queryCategoryProductsByLabel(String label) {
		return (CategoryProductsDO) getSqlMapClientTemplate().queryForObject(
				"categoryProducts.queryCategoryProductsByLabel", label);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryProductsDO> queryAllCategoryProductsByLabel(String label) {
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryAllCategoryProductsByLabel"),
				label);
	}

	@SuppressWarnings("unchecked")
	public List<CategoryProductsDO> queryCategoryProductsByCode(String code,
			String isAssist) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		// 如果isAssist为空，则查找所有类别
		map.put("isAssist", isAssist);
		return getSqlMapClientTemplate().queryForList(
				"categoryProducts.queryCategoryProductsByCode", map);
	}

	// @SuppressWarnings("unchecked")
	// public List<CategoryProductsDO> queryCategoryProductsByCondition(
	// CategoryProductsDTO categoryProductsDTO) {
	// Assert.notNull(categoryProductsDTO, "categoryProductsDTO is not null");
	// return
	// getSqlMapClientTemplate().queryForList("categoryProducts.queryCategoryProductsByCondition",
	// categoryProductsDTO);
	// }

	public CategoryProductsDO queryCategoryProductsById(int id) {
		Assert.notNull(id, "id is not null");
		return (CategoryProductsDO) getSqlMapClientTemplate().queryForObject(
				"categoryProducts.queryCategoryProductsById", id);
	}

	public String queryMaxCodeBypreCode(String preCode) {

		return (String) getSqlMapClientTemplate().queryForObject(
				"categoryProducts.queryMaxCodeBypreCode", preCode);
	}

	// public int deleteCategoryProductsById(CategoryProductsDO
	// categoryProductsDO) {
	//
	// return
	// getSqlMapClientTemplate().update("categoryProducts.deleteCategoryProductsById",
	// categoryProductsDO);
	// }

	public int insertCategoryProducts(CategoryProductsDO categoryProductsDO) {

		return Integer.valueOf(getSqlMapClientTemplate().insert(
				"categoryProducts.insertCategoryProducts", categoryProductsDO)
				.toString());
	}

	public Integer updatecategoryProducts(CategoryProductsDO categoryProductsDO) {

		return getSqlMapClientTemplate().update(
				"categoryProducts.updatecategoryProducts", categoryProductsDO);
	}

	// @SuppressWarnings("unchecked")
	// public List<CategoryProductsDO> queryCategoryProductsFront() {
	// return
	// getSqlMapClientTemplate().queryForList("categoryProducts.queryCategoryProductsFront");
	// }

	// public CategoryProductsDO queryCategoryNameByCode(String code) {
	//
	// return (CategoryProductsDO)
	// getSqlMapClientTemplate().queryForObject("categoryProducts.queryCategoryNameByCode",
	// code);
	// }

	public Integer deleteCategoryProductsAndChild(String parentCode) {
		Assert.notNull(parentCode, "parent code can not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("isdel", DELED_TAGS);
		root.put("parentCode", parentCode);
		return getSqlMapClientTemplate().update(
				"categoryProducts.deleteCategoryProductsAndChild", root);
	}

	@SuppressWarnings("unchecked")
	public List<CategoryProductsDO> queryCategoryProductsByCnspell(String pingy) {
		Assert.notNull(pingy, "the object pingy can not be null");
		return getSqlMapClientTemplate().queryForList(
				"categoryProducts.queryCategoryProductsByCnspell", pingy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryProductsDO> queryCategoryByTags(String keywords,
			Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("keywords", keywords);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix("categoryProducts", "queryCategoryByTags"),
				root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryProductsDO> queryHistoryCategoryByCompanyId(
			Integer companyId) {
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryHistoryCategoryByCompanyId"),
				companyId);
	}

	@Override
	public CategoryProductsDO queryCategoryProductsByKey(String label) {
		return (CategoryProductsDO) getSqlMapClientTemplate().queryForObject("categoryProducts.queryCategoryProductsByKey", label);
	}

	@Override
	public String queryNameByCode(String code) {
		return (String)getSqlMapClientTemplate().queryForObject("categoryProducts.queryNameByCode",code);
	}

	@Override
	public Integer updateSearchLabelById(Integer id, String searchLabel) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("searchLabel", searchLabel);
		return getSqlMapClientTemplate().update("categoryProducts.updateSearchLabelById", map);
	}
}
