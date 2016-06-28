/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.service.information.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.WeeklyArticleDO;
import com.ast.ast1949.dto.information.WeeklyDTO;
import com.ast.ast1949.persist.information.WeeklyArticleDAO;
import com.ast.ast1949.service.information.WeeklyArticleService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("weeklyArticleService")
public class WeeklyArticleServiceImpl implements WeeklyArticleService{

	@Autowired
	private WeeklyArticleDAO weeklyArticleDAO;
	
	public List<WeeklyDTO> ListWeeklyArticleByPageId(Integer pageId) {
		Assert.notNull(pageId, "pageId is not null");
		return weeklyArticleDAO.ListWeeklyArticleByPageId(pageId);
	}

	public Integer deleteWeeklyArticle(int[] entities) {
		Assert.notNull(entities, "entities is not null");
		return weeklyArticleDAO.deleteWeeklyArticle(entities);
	}

	public List<WeeklyDTO> listBbsAndWeeklyArticle(Integer periodicalId) {
		Assert.notNull(periodicalId, "periodicalId is not null");
		return weeklyArticleDAO.listBbsAndWeeklyArticle(periodicalId);
	}

	public WeeklyArticleDO listDownArticle(Integer pageId, Integer bbsPostId) {
		Assert.notNull(bbsPostId, "bbsPostId is not null");
		Assert.notNull(pageId, "pageId is not null");
		return weeklyArticleDAO.listDownArticle(pageId, bbsPostId);
	}

	public WeeklyArticleDO listOnArticle(Integer pageId, Integer bbsPostId) {
		Assert.notNull(bbsPostId, "bbsPostId is not null");
		Assert.notNull(pageId, "pageId is not null");
		return weeklyArticleDAO.listOnArticle(pageId, bbsPostId);
	}

	public Integer insertWeeklyArticle(Integer pageId, Integer[] bbsPostIds) {
		Assert.notNull(pageId, "pageId is not null");
		Assert.notNull(bbsPostIds, "bbsPostIds is not null");
		return weeklyArticleDAO.insertWeeklyArticle(pageId, bbsPostIds);
	}


}
