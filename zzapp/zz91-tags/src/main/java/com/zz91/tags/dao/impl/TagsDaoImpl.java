package com.zz91.tags.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.tags.dao.BaseDaoSupport;
import com.zz91.tags.dao.TagsDao;
import com.zz91.tags.domain.Tags;
import com.zz91.tags.dto.Pager;
import com.zz91.util.Assert;

@Component("tagsDao")
public class TagsDaoImpl extends BaseDaoSupport implements TagsDao {

	final static String sqlPreFix = "tags";

	@Override
	public Integer deleteTags(String tags) {
		Assert.notNull(tags, "the id can not be null");
		return (Integer) getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(sqlPreFix, "deleteTags"), tags);
	}

	@Override
	public Integer insertTags(Tags tags) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(sqlPreFix, "insertTags"), tags);
	}

	@Override
	public Integer queryTagsCount(String tags) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("tags", tags);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryTagsCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tags> queryTags(String tags, Pager<Tags> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("tags", tags);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryTags"), root);
	}

	@Override
	public Integer countTagsByName(String tags) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "countTagsByName"), tags);
	}

	@Override
	public Integer updateCitedCount(String tags) {
		Assert.notNull(tags, "the tags can not be null");
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateCitedCount"), tags);
	}

	@Override
	public Integer updateClickCount(String tag) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateClickCount"), tag);
	}

	@Override
	public Integer updateSearchCount(String tag) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateSearchCount"), tag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tags> queryTagsByKeywords(String keywords, String sort,
			Integer size) {

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("keywords", keywords);
		root.put("sort", sort);
		root.put("size", size);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryTagsByKeywords"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tags> queryTagsByTag(String tag, String sort, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("tag", tag);
		root.put("sort", sort);
		root.put("size", size);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryTagsByTag"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tags> queryTagsForHot(String from, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryTagsForHot"), map);
	}

	@Override
	public Integer updateTags(Tags tags) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateTags"), tags);
	}

}
