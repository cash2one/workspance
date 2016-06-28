/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.comp.CompNewsDao;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Repository("compNewsDao")
public class CompNewsDaoImpl extends BaseDao implements CompNewsDao {

	final static String SQL_PREFIX = "compNews";
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<CompNews> queryCompNewsByCid(Integer cid, String type,
//			Short pause, Short check, Integer size) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("cid", cid);
//		root.put("type", type);
//		root.put("pause", pause);
//		root.put("delete", 1);
//		root.put("check", check);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompNewsByCid"), root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<CompNews> queryCompNewsByCid(Integer cid, String type, String keywords,
//			Short pause, Short check,Short delete, PageDto<CompNews> page) {
//		Map<String,Object> root =new HashMap<String, Object>();
//        root.put("cid", cid);
//        root.put("keywords", keywords);
//        root.put("pause", pause);
//        root.put("check", check);
//        root.put("delete", delete);
//        root.put("type", type);
//        root.put("page", page);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "pageCompNewsByCid"),root);
//	}

//	@Override
//	public Integer queryCompNewsByCidCount(Integer cid, String type, String keywords,
//			Short pause, Short check,Short delete) {
//		Map<String,Object> root =new HashMap<String, Object>();
//		root.put("cid", cid);
//		root.put("keywords", keywords);
//        root.put("pause", pause);
//        root.put("check", check);
//        root.put("delete", delete);
//        root.put("type", type);
//        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "pageCompNewsByCidCount"),root);
//	}

//	@Override
//	public CompNews queryCompNewsById(Integer id) {
//		return (CompNews) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompNewsById"),id);
//	}

//	@Override
//	public Integer insertArticle(CompNews compNews) {
//		return (Integer)getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertNews"),compNews);
//	}

	@Override
	public Integer updateDeleteStatusByCid(Integer id, Integer cid, Short status) {
		Map<String,Object> root =new HashMap<String, Object>();
		root.put("id", id);
		root.put("cid", cid);
		root.put("status", status);
        return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDeleteStatusByCid"),root);
	}

//	@Override
//	public Integer updatePauseStatusByCid(Integer id, Integer cid,
//			Short status) {
//		Map<String,Object> root =new HashMap<String, Object>();
//		root.put("id", id);
//		root.put("cid", cid);
//		root.put("status", status);
//        return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePauseStatusByCid"),root);
//	}

//	@Override
//	public Integer updateArticle(CompNews compNews) {
//		return (Integer)getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateArticle"),compNews);
//	}

	@Override
	public Integer updateCheckStatus(Integer id, Short status,String account) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("status", status);
		root.put("person", account);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCheckStatus"), root);
	}

	@Override
	public CompNews queryDetailsById(Integer id) {
		return (CompNews) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDetailsById"), id);
	}

	@Override
	public Integer updateContent(Integer id, String content,String title,String categoryCode,String tags) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("content", content);
		root.put("title", title);
		root.put("categoryCode", categoryCode);
		root.put("tags", tags);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateContent"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompNewsDto> queryCompNewsByAdmin(Integer cid,String type,
			String title, Short pause, Short check, Short delete,
			PageDto<CompNewsDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
        root.put("keywords", title);
        root.put("pause", pause);
        root.put("check", check);
        root.put("delete", delete);
        root.put("type", type);
        root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompNewsByAdmin"), root);
	}

	@Override
	public Integer queryCompNewsCountByAdmin(Integer cid,String type,
			String title, Short pause, Short check, Short delete) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("type", type);
        root.put("keywords", title);
        root.put("pause", pause);
        root.put("check", check);
        root.put("delete", delete);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompNewsCountByAdmin"), root);
	}
}