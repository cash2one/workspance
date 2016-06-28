package com.zz91.tags.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.tags.dao.BaseDaoSupport;
import com.zz91.tags.dao.TagsCategoryCollectionDao;
import com.zz91.tags.domain.Tags;
import com.zz91.tags.domain.TagsCategoryCollection;
import com.zz91.tags.dto.Pager;
import com.zz91.util.Assert;

@Component("tagsCategoryCollectionDao")
public class TagsCategoryCollectionDaoImpl extends BaseDaoSupport implements
		TagsCategoryCollectionDao {

	private final static String sqlPreFix = "tagsCategoryCollection";

	@Override
	public Integer insertCollection(TagsCategoryCollection tags) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(sqlPreFix, "insertCollection"), tags);
	}

	@Override
	public Integer deleteCollection(String tags) {
		Assert.notNull(tags, "the tags can not be null");
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(sqlPreFix, "deleteCollection"), tags);
	}

	@Override
	public Integer updateIndexKey(String code, String indexKey) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", code);
		root.put("indexKey", indexKey);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateIndexKey"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TagsCategoryCollection> queryCollection(String categoryCode,
			Boolean isDirect, Pager<TagsCategoryCollection> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		root.put("isDirect", isDirect);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryCollection"), root);
	}

	@Override
	public Integer queryCollectionCount(String categoryCode, Boolean isDirect) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		root.put("isDirect", isDirect);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryCollectionCount"), root);
	}

	@Override
	public Integer deleteCollectionByCategory(String code) {
		Assert.notNull(code, "the code can not be null");
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(sqlPreFix, "deleteCollectionByCategory"), code);
	}

	@Override
	public Integer deleteCollectionById(Integer id) {
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(sqlPreFix, "deleteCollectionById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tags> queryByCode(String code, Integer depth, Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("code", code);
		root.put("depth", depth);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryByCode"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tags> queryByIndexKey(String indexKey, Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("indexKey", indexKey);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryByIndexKey"), root);
	}

}
