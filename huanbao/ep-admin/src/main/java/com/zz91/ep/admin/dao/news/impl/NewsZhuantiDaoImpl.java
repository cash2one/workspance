package com.zz91.ep.admin.dao.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.news.NewsZhuantiDao;
import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.ZhuantiDto;

/**
 * @author 黄怀清
 *
 * created on 2012-9-13
 */
@Repository("newsZhuantiDao")
public class NewsZhuantiDaoImpl extends BaseDao implements NewsZhuantiDao{
	final static String SQL_PREFIX="newsZhuanti";

	@SuppressWarnings("unchecked")
	@Override
	public List<Zhuanti> queryZhuanti(Zhuanti zhuanti,
			PageDto<ZhuantiDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("category", zhuanti.getCategory());
		root.put("recommandStatus", zhuanti.getRecommandStatus());
		root.put("attentionStatus", zhuanti.getAttentionStatus());
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryZhuanti"), root);
	}
	
	@Override
	public Integer queryZhuantiCount(Zhuanti zhuanti) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("category", zhuanti.getCategory());
		root.put("recommandStatus", zhuanti.getRecommandStatus());
		root.put("attentionStatus", zhuanti.getAttentionStatus());
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryZhuantiCount"), root);
	}
	
	@Override
	public Integer insert(Zhuanti zhuanti) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"), zhuanti);
	}
	
	@Override
	public Integer update(Zhuanti zhuanti) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "update"), zhuanti);
	}
	@Override
	public Integer updateAttention(Zhuanti zhuanti) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateAttention"), zhuanti);
	}
	@Override
	public Integer updateRecommend(Zhuanti zhuanti) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRecommend"), zhuanti);
	}
	
	@Override
	public Zhuanti queryZhuantiDetail(Integer id) {
		return (Zhuanti) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryZhuantiDetail"), id);
	}
	@Override
	public Integer delete(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "delete"), id);
	}
}
