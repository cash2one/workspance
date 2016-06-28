/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-10-9
 */
package com.ast.ast1949.persist.news.impl;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.news.NewsRelateModuleDO;
import com.ast.ast1949.persist.news.NewsRelateModuleDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("newsRelateModuleDAO")
public class NewsRelateModuleDAOImpl extends SqlMapClientDaoSupport implements NewsRelateModuleDAO {

	public Integer deleteNewsRelateModuleByMap(Map<String, Object> param) {
		Assert.notNull(param, "the param must not be null");
		
		return getSqlMapClientTemplate().delete("newsRelateModule.deleteNewsRelateModuleByMap", param);
	}

	public Integer insertNewsRelateModule(NewsRelateModuleDO newsRelateModule) {
		Assert.notNull(newsRelateModule, "the object of newsRelateModule must not be null");
		return (Integer) getSqlMapClientTemplate().insert("newsRelateModule.insertNewsRelateModule", newsRelateModule);
	}

//	public NewsRelateModuleDO queryNewsRelateModuleByMap(
//			Map<String, Object> param) {
//		Assert.notNull(param, "the param must not be null");
//		return (NewsRelateModuleDO) getSqlMapClientTemplate().queryForObject("newsRelateModule.queryNewsRelateModuleByMap", param);
//	}

	public Integer updateNewsRelateModule(NewsRelateModuleDO newsRelateModule) {
		Assert.notNull(newsRelateModule, "the object of newsRelateModule must not be null");
		return getSqlMapClientTemplate().update("newsRelateModule.updateNewsRelateModule", newsRelateModule);
	}

	public NewsRelateModuleDO queryNewsRelateModule(
			NewsRelateModuleDO newsRelateModule) {
		Assert.notNull(newsRelateModule, "the object of newsRelateModule must not be null");
		return (NewsRelateModuleDO) getSqlMapClientTemplate().queryForObject("newsRelateModule.queryNewsRelateModule", newsRelateModule);
	}

}
