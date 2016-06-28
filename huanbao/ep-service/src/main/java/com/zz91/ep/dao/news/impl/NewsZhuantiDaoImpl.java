package com.zz91.ep.dao.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.news.NewsZhuantiDao;
import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.PageDto;

/**
 * @author 黄怀清
 *
 * created on 2012-9-18
 */
@Repository("newsZhuantiDao")
public class NewsZhuantiDaoImpl extends BaseDao implements NewsZhuantiDao{
	final static String SQL_PREFIX="newsZhuanti";

	@SuppressWarnings("unchecked")
	@Override
	public List<Zhuanti> queryByCategory(String category,
			PageDto<Zhuanti> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("category", category);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryByCategory"), root);
	}
	
	@Override
	public Integer queryByCategoryCount(String category) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("category", category);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryByCategoryCount"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Zhuanti> queryAttention(Integer size){
		Map<String, Object> root = new HashMap<String, Object>();
		PageDto<Zhuanti> page= new PageDto<Zhuanti>();
		page.setLimit(size);
		page.setSort("nz.gmt_publish");
		page.setDir("desc");
		root.put("attentionStatus", NewsZhuantiDao.ATTENTION_Y);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryByCategory"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Zhuanti> queryRecommend(Integer size){
		Map<String, Object> root = new HashMap<String, Object>();
		PageDto<Zhuanti> page= new PageDto<Zhuanti>();
		page.setLimit(size);
		page.setSort("nz.gmt_publish");
		page.setDir("desc");
		root.put("recommandStatus", NewsZhuantiDao.RECOMMEND_Y);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryByCategory"), root);
	}
}
