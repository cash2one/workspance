/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-15
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.EsiteNewsDao;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-15
 */
@Component("esiteNewsDao")
public class EsiteNewsDaoImpl extends BaseDaoSupport implements EsiteNewsDao {

	final static String SQL_PREFIX = "esiteNews";

	@Override
	public Integer deleteNewsByCompany(Integer id, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("companyId", companyId);
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteNewsByCompany"), root);
	}

	@Override
	public Integer insertNews(EsiteNewsDo news) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertNews"), news);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EsiteNewsDo> queryNewsByCompany(Integer companyId, PageDto page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		root.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryNewsByCompany"), root);
	}

	@Override
	public Integer queryNewsByCompanyCount(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryNewsByCompanyIdCount"),
				companyId);
	}

	@Override
	public Integer updateNewsById(EsiteNewsDo news) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateNewsById"), news);
	}

	@Override
	public EsiteNewsDo queryOneNewsById(Integer id) {
		return (EsiteNewsDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryOneNewsById"), id);
	}

	@Override
	public EsiteNewsDo queryLastNewsById(Integer id,Integer cid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("companyId", cid);
		return (EsiteNewsDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryLastNewsById"), root);
	}

	@Override
	public EsiteNewsDo queryNextNewsById(Integer id,Integer cid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("companyId", cid);
		return (EsiteNewsDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryNextNewsById"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EsiteNewsDo> queryList(Integer size){
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryList"), size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EsiteNewsDo> querybyCompanyAll(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querybyCompanyAll"),companyId);
	}

}
