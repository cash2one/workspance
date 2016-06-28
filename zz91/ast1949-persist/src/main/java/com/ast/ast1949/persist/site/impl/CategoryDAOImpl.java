/*
 * Copyright 2009 ASTO.
 * All right reserved.
 */
package com.ast.ast1949.persist.site.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.site.CategoryDTO;
import com.ast.ast1949.persist.site.CategoryDAO;

/**
 * @author Ryan
 *
 */
@Component("categoryDAO")
public class CategoryDAOImpl extends SqlMapClientDaoSupport implements CategoryDAO {

	public int insertCategory(CategoryDO categories) {
		Integer i = (Integer) getSqlMapClientTemplate().insert("category.insertCategory", categories);
		return i;
	}

	@SuppressWarnings("unchecked")
	public List<CategoryDO> queryCategoriesByPreCode(String code) {
		return getSqlMapClientTemplate().queryForList("category.queryCategoriesByPreCode", code);
	}

	public String queryMaxCodeByPreCode(String preCode) {
		String s = (String) getSqlMapClientTemplate().queryForObject(
				"category.queryMaxCodeByPreCode", preCode);
		return s;
	}

	public int deleteCategoryByCode(String code) {
		return getSqlMapClientTemplate().delete("category.deleteCategoryByCode", code);
	}

	public int updateCategory(CategoryDO categoryDO) {
		return getSqlMapClientTemplate().update("category.updateCategory", categoryDO);
	}

//	public int queryRecordCountByCondition(CategoryDTO dto) {
//		return Integer.valueOf(getSqlMapClientTemplate()
//				.queryForObject("category.queryRecordCountByCondition", dto).toString());
//	}

	public CategoryDO queryCategoryById(int id) {
		return (CategoryDO) getSqlMapClientTemplate().queryForObject("category.queryCategoryById", id);
	}
	public CategoryDO queryCategoryBylabel(String label) {
	     return (CategoryDO) getSqlMapClientTemplate().queryForObject("category.queryCategoryByLabel", label);
	}
	@SuppressWarnings("unchecked")
	public List<CategoryDO> queryCategoriesByCondition(CategoryDTO dto) {
		return getSqlMapClientTemplate().queryForList("category.queryCategoriesByCondition", dto);
	}

	public CategoryDO queryCategoryByCode(String code) {

		return (CategoryDO)getSqlMapClientTemplate().queryForObject("category.queryCategoryByCode", code);
	}

//	@Override
//	public List<CategoryDO> queryCategoriesByParentCode(String parentCode) {
//		return getSqlMapClientTemplate().queryForList("category.queryCategoriesByParentCode", parentCode);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryDO> queryCategoryList() {
		return getSqlMapClientTemplate().queryForList("category.queryCategoryList");
	}


}