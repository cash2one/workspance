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

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreSummaryDto;
import com.ast.ast1949.service.score.ScoreSummaryService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Controller
public class SummaryController extends BaseController {
	
	@Autowired
	private ScoreSummaryService scoreSummaryService;
	
	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view() {
		
	}
	
	/**
	 * 积分变更
	 */
	@RequestMapping
	public void edit(Map<String, Object> model,String id,String companyId,String relatedId) {
		model.put("id", id);
		model.put("companyId", companyId);
		model.put("relatedId", relatedId);
	}
	
	/**
	 * 读取用户积分列表
	 * @param model
	 * @param company 参数包含：name,membershipCode
	 * @param condictions  参数包含:email
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model,Company company,ScoreSummaryDto condictions,PageDto<ScoreSummaryDto> page) throws IOException {
		if(page == null){
			page = new PageDto<ScoreSummaryDto>(AstConst.PAGE_SIZE);
		}
		
		page.setSort("ss.score");
		page.setDir("desc");
		
		condictions.setCompany(company);
		PageDto<ScoreSummaryDto> p = scoreSummaryService.pageSummaryByCondictions(condictions, page);
		
		return printJson(p, model);
	}
	
	
}
