/*
 * 文件名称：NewsDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.news.NewsDao;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;
import com.zz91.ep.dto.news.NewsSearchDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：资讯信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("newsDao")
public class NewsDaoImpl extends BaseDao implements NewsDao {

	final static String SQL_PREFIX="news";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<News> queryNewsByCategory(String code, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", code);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByCategory"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonDto> queryNewsByRecommend(String code, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", code);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByRecommend"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> queryTopNews(Integer size) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTopNews"), size);
	}

	@Override
	public Integer queryNewsByCategoryKeyCount(String categoryKey) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryKey", categoryKey);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewsByCategoryKeyCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsSearchDto> queryNewsByCategoryKey(String categoryKey,
			PageDto<NewsSearchDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryKey", categoryKey);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByCategoryKey"), root);
	}

	@Override
	public News queryNewDetailsById(Integer id) {
		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewDetailsById"), id);
	}

	@Override
	public News queryPrevNewsById(Integer id, String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("categoryCode", categoryCode);
		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPrevNewsById"), root);
	}

	@Override
	public News queryNextNewsById(Integer id, String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("categoryCode", categoryCode);
		return (News) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNextNewsById"), root);
	}

	@Override
	public Integer updateViewCountById(Integer id) {
		return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateViewCountById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsSearchDto> queryNewsByCategoryCode(String categoryCode,
			PageDto<NewsSearchDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsByCategoryCode"), root);
	}

	@Override
	public Integer queryNewsByCategoryCodeCount(String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNewsByCategoryCodeCount"), root);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<News> queryNewsByCode(String categoryCode, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		root.put("size",size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"queryNewsByCode"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsDto> queryNewsAndUrlByCode(String code, Integer size) {
		
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", code);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewsAndUrlByCode"), root);
	}
	

}