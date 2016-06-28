/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 9, 2010 by Rolyer.
 */
package com.ast.ast1949.service.news.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.news.NewsRelateModuleDO;
import com.ast.ast1949.persist.news.NewsRelateModuleDAO;
import com.ast.ast1949.service.news.NewsRelateModuleService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Component("newsRelateModuleService")
public class NewsRelateModuleServiceImpl implements NewsRelateModuleService {

	@Autowired
	private NewsRelateModuleDAO newsRelateModuleDAO;
	
	public Integer deleteNewsRelateModuleByMap(Integer id, Integer newsId,
			Integer moduleId, Integer tradeId) {
		boolean b=false;
		if(id!=null) {
			b=true;
		}
		if(newsId!=null) {
			b=true;
		}
		if(moduleId!=null) {
			b=true;
		}
		if(tradeId!=null) {
			b=true;
		}
		Assert.isTrue(b, "the param must not be null");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("newsId", newsId);
		param.put("moduleId", moduleId);
		param.put("tradeId", tradeId);
		
		return newsRelateModuleDAO.deleteNewsRelateModuleByMap(param);
	}

//	public Integer insertNewsRelateModule(NewsRelateModuleDO newsRelateModule) {
//		return newsRelateModuleDAO.insertNewsRelateModule(newsRelateModule);
//	}

//	public NewsRelateModuleDO queryNewsRelateModuleByMap(Integer id,
//			Integer newsId, Integer moduleId, Integer tradeId) {
//		Assert.isTrue(id==null&&newsId==null&&moduleId==null&&tradeId==null, "the param must not be null");
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("id", id);
//		param.put("newsId", newsId);
//		param.put("moduleId", moduleId);
//		param.put("tradeId", tradeId);
//		
//		return newsRelateModuleDAO.queryNewsRelateModuleByMap(param);
//	}

//	public Integer updateNewsRelateModule(NewsRelateModuleDO newsRelateModule) {
//		return newsRelateModuleDAO.updateNewsRelateModule(newsRelateModule);
//	}

//	public NewsRelateModuleDO queryNewsRelateModule(
//			NewsRelateModuleDO newsRelateModule) {
//		return newsRelateModuleDAO.queryNewsRelateModule(newsRelateModule);
//	}

	public Integer insert(NewsRelateModuleDO newsRelateModule) {
		Assert.notNull(newsRelateModule, "the object of newsRelateModule must not be mull");
		NewsRelateModuleDO module =newsRelateModuleDAO.queryNewsRelateModule(newsRelateModule);
		if(module!=null&&module.getId()>0){
			return module.getId();
		} else {
			return newsRelateModuleDAO.insertNewsRelateModule(newsRelateModule);
		}
	}

//	public Integer update(NewsRelateModuleDO newsRelateModule) {
//		Assert.notNull(newsRelateModule, "the object of newsRelateModule must not be mull");
//		NewsRelateModuleDO param = newsRelateModule;
//		param.setId(null);
//		param.setTradeId(null);
//		NewsRelateModuleDO module =newsRelateModuleDAO.queryNewsRelateModule(newsRelateModule);
//		if(module!=null&&module.getId()>0){
//			newsRelateModuleDAO.updateNewsRelateModule(newsRelateModule);
//			return module.getId();
//		} else {
//			return newsRelateModuleDAO.insertNewsRelateModule(newsRelateModule);
//		}
//	}
	
	

}
