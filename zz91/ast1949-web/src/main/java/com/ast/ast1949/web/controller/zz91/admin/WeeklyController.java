/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.WeeklyPageDO;
import com.ast.ast1949.domain.information.WeeklyPeriodicalDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.WeeklyDTO;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.information.WeeklyArticleService;
import com.ast.ast1949.service.information.WeeklyPageService;
import com.ast.ast1949.service.information.WeeklyPeriodicalService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class WeeklyController extends BaseController {

	@Autowired
	private WeeklyArticleService weeklyArticleService;
	@Autowired
	private WeeklyPageService weeklyPageService;
	@Autowired
	private WeeklyPeriodicalService weeklyPeriodicalService;
	@Autowired
	private BbsService bbsService;

	@RequestMapping
	public void view(Map<String, Object> out) {
		out.put("pageId", null);
	}

	// 期刊列表
	@RequestMapping
	public ModelAndView periodicalList(PageDto page, Map<String, Object> out) throws IOException {
		page.setRecords(weeklyPeriodicalService.listWeeklyPeriodicalByPage(page));
		page.setTotalRecords(weeklyPeriodicalService.countWeeklyPeriodical());
		return printJson(page, out);
	}

	// 版面列表
	@RequestMapping
	public ModelAndView listPage(Integer periodicalId, Map<String, Object> out) throws IOException {
		PageDto page = new PageDto();
		if (periodicalId != null) {
			page.setRecords(weeklyPageService.listWeeklyPageByPeriodicalId(periodicalId));
		}
		return printJson(page, out);
	}

	// 文章列表
	@RequestMapping
	public ModelAndView listWeekly(Integer pageId, Map<String, Object> out) throws IOException {
		PageDto page = new PageDto();
		if (pageId != null) {
			List<WeeklyDTO> list = weeklyArticleService.ListWeeklyArticleByPageId(pageId);
			page.setRecords(list);
		}
		out.put("pageId", pageId);
		return printJson(page, out);
	}

	// 添加期刊
	@RequestMapping
	public ModelAndView addPeriodcal(WeeklyPeriodicalDO weeklyPeriodicalDO, Map<String, Object> out)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = weeklyPeriodicalService.insertWeeklyPeriodical(weeklyPeriodicalDO);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	// 初始化期刊
	@RequestMapping
	public ModelAndView init(Integer id, Map<String, Object> out) throws IOException {
		PageDto page = new PageDto();
		List<WeeklyPeriodicalDO> list = new ArrayList<WeeklyPeriodicalDO>();
		list.add(weeklyPeriodicalService.listWeeklyPeriodicalById(id));
		page.setRecords(list);
		return printJson(page, out);
	}

	// 期刊修改
	@RequestMapping
	public ModelAndView updatePeriodcal(WeeklyPeriodicalDO weeklyPeriodicalDO,
			Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = weeklyPeriodicalService.updateWeeklyPeriodical(weeklyPeriodicalDO);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView allWeeklyPeriodcal(Map<String, Object> out) throws IOException {
		PageDto page = new PageDto();
		List<WeeklyPeriodicalDO> list = weeklyPeriodicalService.listAllWeeklyPeriodical();
		page.setRecords(list);
		return printJson(page, out);
	}

	// 删除期刊
	@RequestMapping
	public ModelAndView deletePeriodcal(String ids, Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		Integer a = weeklyPeriodicalService.batchDeleteWeeklyPeriodical(i);
		if (a > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);

	}

	// 添加版面
	@RequestMapping
	public ModelAndView addPage(WeeklyPageDO weeklyPageDO, Map<String, Object> out)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = weeklyPageService.insertWeeklyPage(weeklyPageDO);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	// 查询版面
	@RequestMapping
	public ModelAndView initPage(Integer id, Map<String, Object> out) throws IOException {
		PageDto page = new PageDto();
		List<WeeklyDTO> list = new ArrayList<WeeklyDTO>();
		list.add(weeklyPageService.listPerdicalAndPageById(id));
		page.setRecords(list);
		return printJson(page, out);
	}

	// 修改版面
	@RequestMapping
	public ModelAndView updatePage(WeeklyPageDO weeklyPageDO, Map<String, Object> out)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = weeklyPageService.updateWeeklyPage(weeklyPageDO);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	// 版面删除
	@RequestMapping
	public ModelAndView deletePage(String ids, Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		Integer a = weeklyPageService.batchDeleteWeeklyPageById(i);
		if (a > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	// 所有帖子列表
	@RequestMapping
	public ModelAndView listBbs(PageDto page, WeeklyDTO weeklyDTO, Map<String, Object> out)
			throws IOException {
		if (page == null) {
			page = new PageDto(AstConst.PAGE_SIZE);
		} else {
			if (page.getPageSize() == null) {
				page.setPageSize(AstConst.PAGE_SIZE);
			}
		}
		weeklyDTO.setPage(page);
		page.setRecords(bbsService.listBbsPostByPage(weeklyDTO));
		page.setTotalRecords(bbsService.countBbsPost(weeklyDTO));
		return printJson(page, out);
	}

	// 添加文章
	@RequestMapping
	public ModelAndView addArticle(Integer pageId, String bbsPostIds, Map<String, Object> out)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = weeklyArticleService.insertWeeklyArticle(pageId, StringUtils
				.StringToIntegerArray(bbsPostIds));
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	// 删除文章
	@RequestMapping
	public ModelAndView deleteArticle(String ids, Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		Integer a = weeklyArticleService.deleteWeeklyArticle(i);
		if (a > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
}
