/**
 * Copyright 2011 ASTO.
 * All right reserved.
 */
package com.zz91.ep.admin.dao.comp.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.comp.CompCategoryDao;
import com.zz91.ep.domain.comp.CompCategory;

/**
 *
 */
@Repository("compCategoryDao")
public class CompCategoryDaoImpl extends BaseDao implements CompCategoryDao {

    final static String SQL_PREFIX = "compCategory";

	@SuppressWarnings("unchecked")
	@Override
	public List<CompCategory> listAllCompCategory() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "listAllCompCategory"));
	}

	@Override
	public Integer createCompCategory(CompCategory compCategory) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createCompCategory"), compCategory);
	}

	@Override
	public Integer deleteCompCategory(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteCompCategory"), id);
	}

	@Override
	public CompCategory listOneCompCategoryById(Integer id) {
		return (CompCategory) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "listOneCompCategoryById"), id);
	}

	@Override
	public Integer updateCompCategory(CompCategory compCategory) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompCategory"), compCategory);
	}


}