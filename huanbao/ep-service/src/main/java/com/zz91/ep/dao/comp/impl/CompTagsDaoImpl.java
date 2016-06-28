package com.zz91.ep.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.CompTagsDao;
import com.zz91.ep.domain.comp.CompTags;

@Component("compTagsDao")
public class CompTagsDaoImpl extends BaseDao implements CompTagsDao {
	
	final static String SQL_PREFIX="compTags";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompTags> queryCompTags(Integer id,Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("flag", flag);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompTags"),map);
	}

	@Override
	public String queryCompKewordsById(Integer id) {
		
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompKewordsById"),id);
	}

}
