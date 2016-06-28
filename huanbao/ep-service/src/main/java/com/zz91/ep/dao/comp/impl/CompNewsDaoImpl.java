/*
 * 文件名称：CompNewsDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.CompNewsDao;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：公司资讯信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("compNewsDao")
public class CompNewsDaoImpl extends BaseDao implements CompNewsDao {
	
	final static String SQL_PREFIX="compnews";

	@SuppressWarnings("unchecked")
	@Override
	public List<CompNews> queryNewestCompNews(String categoryCode, Integer cid, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", categoryCode);
		root.put("cid", cid);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestCompNews"), root);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompNews> queryNewestCompNewsSize(Integer size) {
		List<CompNews> fourList =getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestCompNewsSize"), size);
		return fourList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompNews> queryNewestCompNewsTop(Integer size) {
		
		List<CompNews> twoList =getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestCompNewsTop"), size);
		return twoList;
	}
	
	@Override
	public Integer queryWtgshCount(Integer cid) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCount"),cid);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompNews> queryCompNewsByCid(Integer cid, String type,
			String keywords, Short pause, Short check, Short delete,
			PageDto<CompNews> page) {
		Map<String,Object> root =new HashMap<String, Object>();
        root.put("cid", cid);
        root.put("keywords", keywords);
        root.put("pause", pause);
        root.put("check", check);
        root.put("delete", delete);
        root.put("type", type);
        root.put("page", page);
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "pageCompNewsByCid"),root);
	}

	@Override
	public Integer queryCompNewsByCidCount(Integer cid, String type,
			String keywords, Short pause, Short delete, Short check) {
		Map<String,Object> root =new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("keywords", keywords);
        root.put("pause", pause);
        root.put("check", check);
        root.put("delete", delete);
        root.put("type", type);
        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "pageCompNewsByCidCount"),root);
	}

	@Override
	public CompNews queryCompNewsById(Integer id) {
		return (CompNews) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompNewsById"),id);
	}

	@Override
	public Integer insertArticle(CompNews compNews) {
		Integer s = (Integer)getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertArticle"),compNews);
		return s;
	}

	@Override
	public Integer updateArticle(CompNews compNews) {
		
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateArticle"), compNews);
	}

	@Override
	public Integer updateDeleteStatusByCid(Integer id, Integer cid, Short status) {
		Map<String,Object> root =new HashMap<String, Object>();
		root.put("id", id);
		root.put("cid", cid);
		root.put("status", status);
        return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDeleteStatusByCid"),root);
	}
	
	@Override
	public Integer updatePauseStatusByCid(Integer id, Integer cid,
			Short status) {
		Map<String,Object> root =new HashMap<String, Object>();
		root.put("id", id);
		root.put("cid", cid);
		root.put("status", status);
        return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePauseStatusByCid"),root);
	}

    @Override
    public Integer countForCidAndTitle(Integer companyId, String title) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", companyId);
        map.put("title", title);
        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countForCidAndTitle"), map);
    }

   
	@Override
	public CompNews queryNextCompNewsById(Integer id, Integer cid,String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("cid", cid);
		root.put("categoryCode", categoryCode);
		return (CompNews) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNextCompNewsById"),root);
	}


	@Override
	public CompNews queryPrevCompNewsById(Integer id, Integer cid,String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("cid", cid);
		root.put("categoryCode", categoryCode);
		return (CompNews) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPrevCompNewsById"),root);
	}
	
	@Override
	public Integer updateViewCountById(Integer id) {
		return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateViewCountById"),id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompNewsDto> queryCompNewsForArticle(PageDto<CompNewsDto> page) {
		Map<String,Object> root =new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompNewsForArticle"),root);
	}
	@Override
	public Integer queryCompNewsForArticleCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompNewsForArticleCount"));
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompNews>  queryWeekForArticle(Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
        root.put("size",size);		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryWeekForArticle"),root);
	}
	
}