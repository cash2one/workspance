/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-3.
 */
package com.ast.ast1949.web.controller.zz91.admin.score;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Controller
public class ChangeController extends BaseController {

	@Autowired
	private ScoreChangeDetailsService scoreChangeDetailsService;

	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view() {

	}

	/**
	 * 读取公司的积分变更信息
	 * 
	 * @param model
	 * @param companyId
	 *            公司编号
	 * @param isPlus
	 *            是否加分项
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryByCompanyId(Map<String, Object> model, Integer companyId,
			Boolean isPlus, PageDto<ScoreChangeDetailsDo> page) throws IOException {
		if (page == null) {
			page = new PageDto<ScoreChangeDetailsDo>(AstConst.PAGE_SIZE);
		}
		page.setSort("gmt_created");
		page.setDir("desc");
		PageDto<ScoreChangeDetailsDo> p = new PageDto<ScoreChangeDetailsDo>();
		if (companyId != null) {
			p = scoreChangeDetailsService.pageChangeDetailsByCompanyId(companyId, isPlus, page);
		}
		return printJson(p, model);
	}

	/**
	 * 保存变更
	 * 
	 * @param model
	 * @param details
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView save(Map<String, Object> model, ScoreChangeDetailsDo details)
			throws IOException {
		ExtResult result = new ExtResult();

		Integer im = scoreChangeDetailsService.saveChangeDetails(details);
		if (im.intValue() > 0) {
			result.setSuccess(true);
		}

		return printJson(result, model);
	}
}
