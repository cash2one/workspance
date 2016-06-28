/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.persist.information.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.WeeklyArticleDO;
import com.ast.ast1949.dto.information.WeeklyDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.information.WeeklyArticleDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("weeklyArticleDAO")
public class WeeklyArticleDAOImpl extends SqlMapClientDaoSupport implements WeeklyArticleDAO{
	final private int DEFAULT_BATCH_SIZE = 20;
	@SuppressWarnings("unchecked")
	public List<WeeklyDTO> ListWeeklyArticleByPageId(Integer pageId) {
        Assert.notNull(pageId, "pageId is not null");
		return getSqlMapClientTemplate().queryForList("weeklyArticle.ListWeeklyArticleByPageId", pageId);
	}

	public Integer insertWeeklyArticle(Integer pageId,Integer[] bbsPostIds) {
		Assert.notNull(pageId, "pageId is not null");
		Assert.notNull(bbsPostIds, "bbsPostIds is not null");
		WeeklyArticleDO weeklyArticleDO=new WeeklyArticleDO();
		Integer impact=0;
		try {
			getSqlMapClient().startBatch();
			for (Integer i : bbsPostIds) {
				weeklyArticleDO.setPageId(pageId);
				weeklyArticleDO.setBbsPostId(i);
				impact+=(Integer)getSqlMapClientTemplate().insert("weeklyArticle.insertWeeklyArticle", weeklyArticleDO);
			}
			getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return impact;
		
	}

	public Integer deleteWeeklyArticle(int[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().update("weeklyArticle.deleteWeeklyArticle",
							entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check  failed.", e);
		}
		return impacted;
	}

	@SuppressWarnings("unchecked")
	public List<WeeklyDTO> listBbsAndWeeklyArticle(Integer periodicalId) {
		Assert.notNull(periodicalId, "periodicalId is not null");
		return getSqlMapClientTemplate().queryForList("weeklyArticle.listBbsAndWeeklyArticle",periodicalId);
	}

	public WeeklyArticleDO listDownArticle(Integer pageId, Integer bbsPostId) {
		Assert.notNull(pageId, "pageId is not null");
		Assert.notNull(bbsPostId, "bbsPostId is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("pageId", pageId);
		map.put("bbsPostId", bbsPostId);
		return (WeeklyArticleDO) getSqlMapClientTemplate().queryForObject("weeklyArticle.listDownArticle", map);
	}

	public WeeklyArticleDO listOnArticle(Integer pageId, Integer bbsPostId) {
		Assert.notNull(pageId, "pageId is not null");
		Assert.notNull(bbsPostId, "bbsPostId is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("pageId", pageId);
		map.put("bbsPostId", bbsPostId);
		return (WeeklyArticleDO) getSqlMapClientTemplate().queryForObject("weeklyArticle.listOnArticle", map);
	}

}
