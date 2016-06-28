package com.zz91.ep.admin.dao.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.news.NewsCategoryDao;
import com.zz91.ep.domain.news.NewsCategory;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-9-28 
 */
@Component("newsCategoryDao")
public class NewsCategoryDaoImpl extends BaseDao implements NewsCategoryDao {
	
	 final static String SQL_PREFIX="newsCategory";

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsCategory> queryNewsCategoryAll() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsCategoryAll"));
	}

	@Override
	public Integer insertCategory(NewsCategory category) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCategory"), category);
	}
	
	@Override
	public String queryMaxCodeByPreCode(String code) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxCodeByPreCode"), code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsCategory> queryCategoryByParentCode(String code) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByParentCode"), code);
	}

//	@Override
//	public Integer updateCategory(String code, String name, Integer sort,
//			String tags) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("code", code);
//		root.put("name", name);
//		root.put("sort", sort);
//		root.put("tags", tags);
//		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCategory"), root);
//	}

	@Override
	public Integer deleteCategoryByCode(String code) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteCategoryByCode"), code);
	}

	@Override
	public Integer countNewsCategoryChild(String code) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countNewsCategoryChild"), code);
	}

	@Override
	public Integer updateCategory(NewsCategory newsCategory) {
		return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCategory"), newsCategory);
	}

	@Override
	public NewsCategory queryOneNewsCategory(String code) {
		return (NewsCategory) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneNewsCategory"), code);
	}

}
