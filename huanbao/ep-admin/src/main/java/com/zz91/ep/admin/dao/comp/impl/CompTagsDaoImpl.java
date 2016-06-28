package com.zz91.ep.admin.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.comp.CompTagsDao;
import com.zz91.ep.domain.comp.CompTags;

@Component("compTagsDao")
public class CompTagsDaoImpl extends BaseDao implements CompTagsDao {
	
	final static String SQL_PREFIX = "compTags";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompTags> queryComCategoryByParentId(Integer parentId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryComCategoryByParentId"),parentId);
	}

	@Override
	public Integer updateComTags(CompTags copmtags){
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompTags"), copmtags);
	}

	@Override
	public Integer addComTags(CompTags copmtags) {
		return (Integer)getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompTags"),copmtags);
	}

	@Override
	public Integer deleteChildCategory(Integer comtagsId,Integer parentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comtagsId", comtagsId);
		map.put("parentId", parentId);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteChildCategory"),map);
	}

}
