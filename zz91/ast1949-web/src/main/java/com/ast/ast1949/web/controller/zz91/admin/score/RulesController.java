/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-3.
 */
package com.ast.ast1949.web.controller.zz91.admin.score;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.score.ScoreExchangeRulesDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.score.ScoreExchangeRulesService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Controller
public class RulesController extends BaseController {

	@Autowired
	private ScoreExchangeRulesService scoreExchangeRulesService;

	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view() {
		
	}

	/**
	 * 读取规则
	 * @param model
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model, PageDto<ScoreExchangeRulesDo> page)
			throws IOException {
		if (page == null) {
			page = new PageDto<ScoreExchangeRulesDo>(AstConst.PAGE_SIZE);
		}
		page.setSort("id");
		page.setDir("desc");

		PageDto<ScoreExchangeRulesDo> p = scoreExchangeRulesService.pageRules(page);

		return printJson(p, model);
	}
	
	/**
	 * 读取数据（用于下拉列表框）
	 * @param model
	 * @param preCode Code值
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryRulesByPreCode(Map<String, Object> model,String preCode)
			throws IOException {
		PageDto<ScoreExchangeRulesDo> page = new PageDto<ScoreExchangeRulesDo>();
		List<ScoreExchangeRulesDo> list = scoreExchangeRulesService.queryRulesByPreCode(preCode);
		page.setRecords(list);

		return printJson(page, model);
	}
}
