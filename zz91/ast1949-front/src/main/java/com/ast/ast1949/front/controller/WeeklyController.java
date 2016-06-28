/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.front.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.information.WeeklyArticleDO;
import com.ast.ast1949.domain.information.WeeklyPageDO;
import com.ast.ast1949.domain.information.WeeklyPeriodicalDO;
import com.ast.ast1949.dto.information.WeeklyDTO;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.information.WeeklyArticleService;
import com.ast.ast1949.service.information.WeeklyPageService;
import com.ast.ast1949.service.information.WeeklyPeriodicalService;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class WeeklyController extends BaseController{

	@Autowired
	private WeeklyPeriodicalService weeklyPeriodicalService;
	@Autowired
	private WeeklyPageService weeklyPageService;
	@Autowired
	private WeeklyArticleService weeklyArticleService;
	@Autowired
	private BbsPostService bbsPostService;

	@RequestMapping
	public void index(Integer id, Integer pageId, Map<String, Object> out) {
		WeeklyPeriodicalDO weeklyPeriodicalDO = null;
		if (id != null) {
			weeklyPeriodicalDO = weeklyPeriodicalService.listWeeklyPeriodicalById(id);
		} else {
			weeklyPeriodicalDO = weeklyPeriodicalService.listFirstWeeklyPeriodical();
			Integer periodicalId = weeklyPeriodicalDO.getId();
			// 上一期
			WeeklyPeriodicalDO onPeriodical = weeklyPeriodicalService
					.listOnWeeklyPeriodical(periodicalId);
			out.put("onPeriodical", onPeriodical);
			// 下一期
			WeeklyPeriodicalDO downPeriodical = weeklyPeriodicalService
					.listDownWeeklyPeriodical(periodicalId);
			out.put("downPeriodical", downPeriodical);
			// 期刊下面所有版面
			List<WeeklyPageDO> pageList = weeklyPageService
					.listWeeklyPageByPeriodicalId(periodicalId);
			out.put("pageList", pageList);
			// 版面下面所有文章
			if (pageList.size() > 0) {
				if (pageId == null) {
					pageId = pageList.get(0).getId();
				}
				WeeklyPageDO weeklyPage = weeklyPageService.listWeeklyPageById(pageId);
				out.put("weeklyPage", weeklyPage);
				// 上一版面
				WeeklyPageDO onPage = weeklyPageService.listOnWeeklyPageById(pageId);
				out.put("onPage", onPage);
				// 下一版面
				WeeklyPageDO downPage = weeklyPageService.listDownWeeklyPageById(pageId);
				out.put("downPage", downPage);
				List<WeeklyDTO> weeklyList = weeklyArticleService.ListWeeklyArticleByPageId(pageId);
				out.put("weeklyList", weeklyList);
				out.put("pageId", pageId);
			}
		}
		out.put("weeklyPeriodicalDO", weeklyPeriodicalDO);

	}

	@RequestMapping
	public void list(Integer id, Map<String, Object> out) {
		// 上一期
		WeeklyPeriodicalDO onPeriodical = weeklyPeriodicalService
				.listOnWeeklyPeriodical(id);
		out.put("onPeriodical", onPeriodical);
		// 下一期
		WeeklyPeriodicalDO downPeriodical = weeklyPeriodicalService
				.listDownWeeklyPeriodical(id);
		out.put("downPeriodical", downPeriodical);
		
		// 版面列表
		List<WeeklyPageDO> pageList = weeklyPageService.listWeeklyPageByPeriodicalId(id);
		out.put("pageList", pageList);
		List<WeeklyDTO> bbsList=weeklyArticleService.listBbsAndWeeklyArticle(id);
        out.put("bbsList", bbsList);
	}

	@RequestMapping
	public void details(Integer id, Integer pageId, Integer bbsPostId, Map<String, Object> out) {
		if (bbsPostId!=null) {
//			BbsPostDO bbsPostDO = bbsService.queryBbsPostById(bbsPostId);
			BbsPostDO bbsPostDO = bbsPostService.queryPostById(bbsPostId);
			out.put("bbsPostDO", bbsPostDO);
			//上一篇文章
			WeeklyArticleDO onArticle=weeklyArticleService.listOnArticle(pageId, bbsPostId);
			out.put("onArticle", onArticle);
			//下一篇文章
			WeeklyArticleDO downArticle=weeklyArticleService.listDownArticle(pageId, bbsPostId);
			out.put("downArticle", downArticle);
		}

		WeeklyPeriodicalDO weeklyPeriodicalDO = weeklyPeriodicalService
				.listWeeklyPeriodicalById(id);
		out.put("weeklyPeriodicalDO", weeklyPeriodicalDO);
		if(weeklyPeriodicalDO!=null){
			Integer periodicalId = weeklyPeriodicalDO.getId();
			if (periodicalId != null) {
				// 上一期
				WeeklyPeriodicalDO onPeriodical = weeklyPeriodicalService
						.listOnWeeklyPeriodical(periodicalId);
				out.put("onPeriodical", onPeriodical);
				// 下一期
				WeeklyPeriodicalDO downPeriodical = weeklyPeriodicalService
						.listDownWeeklyPeriodical(periodicalId);
				out.put("downPeriodical", downPeriodical);
				// 期刊下面所有版面
				List<WeeklyPageDO> pageList = weeklyPageService
						.listWeeklyPageByPeriodicalId(periodicalId);
				out.put("pageist", pageList);
				// 版面下面所有文章
				WeeklyPageDO weeklyPage = weeklyPageService.listWeeklyPageById(pageId);
				out.put("weeklyPage", weeklyPage);
				// 上一版面
				WeeklyPageDO onPage = weeklyPageService.listOnWeeklyPageById(pageId);
				out.put("onPage", onPage);
				// 下一版面
				WeeklyPageDO downPage = weeklyPageService.listDownWeeklyPageById(pageId);
				out.put("downPage", downPage);
				List<WeeklyDTO> weeklyList = weeklyArticleService.ListWeeklyArticleByPageId(pageId);
				out.put("weeklyList", weeklyList);
				out.put("pageId", pageId);

			}
		}


	}
}
