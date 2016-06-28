package com.zz91.ep.admin.dao.exhibit.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.exhibit.ExhibitPlateCategoryDao;
import com.zz91.ep.domain.exhibit.ExhibitPlateCategory;

@Component("exhibitPlateCategoryDao")
public class ExhibitPlateCategoryDaoImpl extends BaseDao implements ExhibitPlateCategoryDao {
	final static String SQL_PREFIX = "plate";
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitPlateCategory> queryExhibitPlateCategoryAll() {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitPlateCategoryAll"));
	}
	@Override
	public Integer countChild(String code) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countChild"), code);
	}
	@Override
	public Integer deleteCategory(String code) {
		
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteCategory"), code);
	}
	@Override
	public Integer insertExhibitCategory(ExhibitPlateCategory ex) {
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertExhibitCategory"), ex);
	}
	@Override
	public ExhibitPlateCategory queryCategoryByCode(String code) {
		
		return (ExhibitPlateCategory) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCategoryByCode"), code);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitPlateCategory> queryChild(String parentCode) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryChild"), parentCode);
	}
	@Override
	public String queryMaxCodeOfChild(String parentCode) {
		
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxCodeOfChild"), parentCode);
	}
	@Override
	public Integer updateCategory(ExhibitPlateCategory ex) {

		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCategory"), ex);
	}

}
