/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-10-8
 */
package com.ast.ast1949.persist.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.news.NewsDO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.news.NewsDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("newsDAO")
public class NewsDAOImpl extends BaseDaoSupport implements NewsDAO {
	private final static Integer DEFAULT_NUMBER=10;

//	public Integer deleteNewsById(Integer id) {
//		Assert.notNull(id, "the id must not be null");
//		return template.delete("news.deleteNewsById", id);
//	}

	public Integer insertNews(NewsDO news) {
		Assert.notNull(news, "the object of news must not be null");
		return (Integer) getSqlMapClientTemplate().insert("news.insertNews", news);
	}

	public NewsDO queryNewsById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (NewsDO) getSqlMapClientTemplate().queryForObject("news.queryNewsById", id);
	}

	public Integer updateNewsById(NewsDO news) {
		Assert.notNull(news, "the object of news must not be null");
		return getSqlMapClientTemplate().update("news.updateNewsById", news);
	}

//	@SuppressWarnings("unchecked")
//	public List<NewsForFrontDTO> queryNewsListForFront(
//			NewsForFrontDTO newsForFrontDTO) {
//		Assert.notNull(newsForFrontDTO, "the object of newsForFrontDTO must not be null");
//		return getSqlMapClientTemplate().queryForList("news.queryNewsListForFront", newsForFrontDTO);
//	}

	@SuppressWarnings("unchecked")
	public List<NewsDO> queryNewsByTitle(String title, Integer number) {
		if(number==null) {
			number = DEFAULT_NUMBER;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("title", title);
		param.put("number", number);
		
		return getSqlMapClientTemplate().queryForList("news.queryNewsByTitle", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsDO> queryNewsByModuleId(Integer moduleId, Integer size) {
		Assert.notNull(moduleId, "moduleId is not null");
		Assert.notNull(size, "size is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("moduleId", moduleId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList("news.queryNewsByModuleId", map);
	}
}
