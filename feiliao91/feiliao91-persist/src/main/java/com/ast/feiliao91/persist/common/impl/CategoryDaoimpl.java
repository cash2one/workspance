package com.ast.feiliao91.persist.common.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.common.Category;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.common.CategoryDao;

@Component("categoryDao")
public class CategoryDaoimpl extends BaseDaoSupport implements CategoryDao {
	final static String SQL_PREFIX = "category";

	@Override
	public Integer insert(Category category) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"), category);
	}

	@Override
	public Category selectById(Integer id) {
		return (Category) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> queryAllCategory() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllCategory"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> queryCategoriesByPreCode(String code) {
		return getSqlMapClientTemplate().queryForList("category.queryCategoriesByPreCode", code);
	}

	@Override
	public String queryMaxCodeByPreCode(String preCode) {
		String s = (String) getSqlMapClientTemplate().queryForObject("category.queryMaxCodeByPreCode", preCode);
		return s;
	}

	@Override
	public Integer delete(Integer id) {
		return 	getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "delete"), id);
	}

	@Override
	public Integer deleteCategoryByCode(String code) {
		return getSqlMapClientTemplate().delete("category.deleteCategoryByCode", code);
	}

	@Override
	public Category selectByCode(String code) {
		return (Category) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectByCode"), code);
	}
	
	@Override
	public Integer updateCategory(Category category){
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCategory"), category);
	}

}
