package com.ast1949.shebei.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.ast1949.shebei.dao.BaseDao;
import com.ast1949.shebei.dao.NewsDao;
import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.dto.PageDto;

@Component("newsDao")
public class NewsDaoImpl extends BaseDao implements NewsDao {

	final static String SQL_PREFIX="news";
	
	@Override
	public Integer insertNews(News news) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertNews"), news);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> queryNewsByCategoryAndType(String category,Short type,Integer size,Short flag) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("category", category);
		root.put("type", type);
		root.put("size", size);
		root.put("flag", flag);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByCategoryAndType"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> queryNews(String category, Short type, PageDto<News> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("category", category);
		root.put("type", type);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNews"), root);
	}

	@Override
	public Integer queryNewsCount(String category, Short type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("category", category);
		root.put("type", type);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewsCount"), root);
	}

	@Override
	public News queryNewsById(Integer id,Short type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("type", type);
		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewsById"), root);
	}
	
	@Override
	public News queryOnNewsById(Integer id,String categoryCode,Short type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("categoryCode", categoryCode);
		root.put("type", type);
		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOnNewsById"), root);
	}

	@Override
	public News queryDownNewsById(Integer id,String categoryCode,Short type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("categoryCode", categoryCode);
		root.put("type", type);
		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDownNewsById"), root);
	}

	@Override
	public Date queryMaxGmtShow() {
		return (Date) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxGmtShow"));
	}
}
