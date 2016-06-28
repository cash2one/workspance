package com.zz91.tags.dao.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import com.zz91.tags.dao.BaseDaoSupport;
import com.zz91.tags.dao.TagsCategoryDao;
import com.zz91.tags.domain.TagsCategory;
import com.zz91.util.Assert;

@Component("tagsCategoryDao")
public class TagsCategoryDaoImpl extends BaseDaoSupport implements TagsCategoryDao{
	
	private final static String sqlPreFix = "tagsCategory";
	@Override
	public Integer updateTagsCategory(TagsCategory category) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateTagsCategory"), category);
	}

	@Override
	public Integer deleteTagsCategory(String code) {
		Assert.notNull(code, "the code can not be null");
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix, "deleteTagsCategory"), code);
	}

	@Override
	public Integer insertTagsCategory(TagsCategory tags) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insertTagsCategory"), tags);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TagsCategory> queryChild(String parentCode) {
		Assert.notNull(parentCode, "the parentCode can not be null");
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryChild"), parentCode);
	}

	@Override
	public Integer countChild(String parentCode) {
		Assert.notNull(parentCode, "parentCode");
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "countChild"), parentCode);
	}

	@Override
	public String queryMaxCodeOfChild(String parentCode) {
		Assert.notNull(parentCode, "parentCode");
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryMaxCodeOfChild"), parentCode);
	}
	
	@Override
	public String queryIndexKeyByCode(String code) {
		//Assert.notNull(code, "the code can not be null");
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryIndexKeyByCode"), code);
	}

	@Override
	public TagsCategory queryCategoryByCode(String code) {
		return (TagsCategory) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryCategoryByCode"), code);
	}
	
}
