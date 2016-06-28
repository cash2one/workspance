package com.zz91.ep.admin.dao.news.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.news.NewsDao;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;
import com.zz91.util.datetime.DateUtil;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-9-28 
 */
@Component("newsDao")
public class NewsDaoImpl extends BaseDao implements NewsDao {
	
	final static String SQL_PREFIX="news";

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<News> queryNewsByRecommend(String type, Integer max) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("type", type);
//		root.put("max", max);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByRecommend"), root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<News> queryTopNews(Integer max) {
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTopNews"), max);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<News> queryNewsByCategory(String categoryCode,
//			PageDto<NewsDto> page) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("categoryCode", categoryCode);
//		root.put("page", page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByCategory"), root);
//	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<News> queryNewsByCategoryKey(String categoryKey,
//			PageDto<NewsDto> page) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("categoryKey", categoryKey);
//		root.put("page", page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByCategoryKey"), root);
//	}

//	@Override
//	public Integer queryNewsByCategoryCount(String categoryCode) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("categoryCode", categoryCode);
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewsByCategoryCount"), root);
//	}
	
//	@Override
//	public Integer queryNewsByCategoryKeyCount(String categoryKey) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("categoryKey", categoryKey);
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewsByCategoryKeyCount"), root);
//	}

	@Override
	public News queryNewDetailsById(Integer id) {
		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewDetailsById"), id);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<News> queryRelatedNewsByTag(String tag, Integer max) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("tag", tag);
//		root.put("max", max);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRelatedNewsByTag"), root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<News> queryNewestNewsByCategory(String categoryCode, Integer max) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("categoryCode", categoryCode);
//		root.put("max", max);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestNewsByCategory"), root);
//	}

//	@Override
//	public News queryOnNewsById(Integer id,String categoryCode) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("id", id);
//		root.put("categoryCode", categoryCode);
//		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOnNewsById"), root);
//	}

//	@Override
//	public News queryDownNewsById(Integer id,String categoryCode) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("id", id);
//		root.put("categoryCode", categoryCode);
//		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDownNewsById"), root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<News> queryNewsByTitle(String keywords,PageDto<NewsDto> page) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("title", keywords);
//		root.put("page", page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByTitle"), root);
//	}
	
	public Integer queryNewsByTitleCount(String keywords){
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("title", keywords);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewsByTitleCount"), root);
	}

	@Override
	public Integer insertNewsByAdmin(News news) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertNewsByAdmin"), news);
	}

	@Override
	public Integer updateNews(News news) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateNews"), news);
	}

	@Override
	public Integer deleteNewsById(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteNewsById"), id);
	}

	@Override
	public Integer updateStatusOfNews(Integer id, Integer status) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("status", status);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStatusOfNews"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsDto> queryNewsByAdmin(NewsDto dto,PageDto<NewsDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByAdmin"), root);
	}

	@Override
	public Integer queryNewsByAdminCount(NewsDto dto) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("dto", dto);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewsByAdminCount"), root);
	}

//	@Override
//	public Integer updateViewCountById(Integer id) {
//		return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateViewCount"), id);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<News> queryNewByKeywords(String keywords, Integer size) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("keywords", keywords);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewByKeywords"), root);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> queryRecommendNewByWeekly(String type, Integer size,
			Integer recommend, String category) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("type", type);
		root.put("size", size);
		root.put("recommend", recommend);
		root.put("category", category);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRecommendNewByWeekly"), root);
	}

	@Override
	public Integer countByTitle(String title) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title",title);
		map.put("today", DateUtil.toString(new Date(),"yyyy-MM-dd"));
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countBytitle"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> queryListByFromTo(String from,String to){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("to", to);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryListByFromTo"), map);
	}
	
}
