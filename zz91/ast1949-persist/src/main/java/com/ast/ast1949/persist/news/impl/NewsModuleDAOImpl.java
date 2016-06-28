/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-10-8
 */
package com.ast.ast1949.persist.news.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.news.NewsModuleDO;
import com.ast.ast1949.persist.news.NewsModuleDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("newsModuleDAO")
public class NewsModuleDAOImpl extends SqlMapClientDaoSupport implements NewsModuleDAO {

//	public Integer countNewsModuleListByParentId(NewsModuleDTO newsModuleDTO) {
//		return (Integer) getSqlMapClientTemplate().queryForObject("newsModule.countNewsModuleListByParentId", newsModuleDTO);
//	}

	public Integer deleteNewsModuleById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().delete("newsModule.deleteNewsModuleById", id);
	}

	public Integer insertNewsModule(NewsModuleDO newsModule) {
		Assert.notNull(newsModule, "the object of newsModule must not be null");
		return (Integer) getSqlMapClientTemplate().insert("newsModule.insertNewsModule", newsModule);
	}

	public NewsModuleDO queryNewsModuleById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (NewsModuleDO) getSqlMapClientTemplate().queryForObject("newsModule.queryNewsModuleById", id);
	}

	@SuppressWarnings("unchecked")
	public List<NewsModuleDO> queryNewsModuleByParentId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().queryForList("newsModule.queryNewsModuleByParentId", id);
	}

//	@SuppressWarnings("unchecked")
//	public List<NewsModuleDTO> queryNewsModuleListByParentId(
//			NewsModuleDTO newsModuleDTO) {
//		return getSqlMapClientTemplate().queryForList("newsModule.queryNewsModuleListByParentId", newsModuleDTO);
//	}

	public Integer updateNewsModuleById(NewsModuleDO newsModule) {
		Assert.notNull(newsModule, "the object of newsModule must not be null");
		return getSqlMapClientTemplate().update("newsModule.updateNewsModuleById", newsModule);
	}

	@SuppressWarnings("unchecked")
	public List<NewsModuleDO> queryNewsModuleListByNewsId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().queryForList("newsModule.queryNewsModuleListByNewsId", id);
	}

	@Override
	public Integer countNewsModuleByParentId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject("newsModule.countNewsModuleByParentId", id);
	}
	
}
