/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-3
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.score.ScoreConversionHistoryService;
import com.ast.ast1949.service.score.ScoreGoodsService;
import com.ast.ast1949.service.score.ScoreSummaryService;
import com.zz91.util.auth.frontsso.SsoUser;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-3
 */
@Controller
public class MyscoreController extends BaseController {

	@Autowired
	ScoreChangeDetailsService scoreChangeDetailsService;
	@Autowired
	ScoreConversionHistoryService scoreConversionHistoryService;
	@Autowired
	ScoreSummaryService scoreSummaryService;
	@Autowired
	ScoreGoodsService scoreGoodsService;
	@Resource
	private MyrcService myrcService;
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView changeDetails(HttpServletRequest request,
			Map<String, Object> out, PageDto page) {
		out.put(FrontConst.MYRC_SUBTITLE, "积分变更历史");
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		page = scoreChangeDetailsService.pageChangeDetailsByCompanyId(sessionUser.getCompanyId(), null, page);
		out.put("pageContext", page);
		out.put("scoreSummary", scoreSummaryService
				.querySummaryByCompanyId(sessionUser.getCompanyId()));
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView conversionHistory(HttpServletRequest request,
			Map<String, Object> out, PageDto page) {
		out.put(FrontConst.MYRC_SUBTITLE, "积分兑换历史");
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		page.setDir("desc");
		page.setSort("sch.gmt_created");
		page = scoreConversionHistoryService.pageConversionHistoryByCompanyId(
				sessionUser.getCompanyId(), null, page);
		out.put("pageContext", page);
		out.put("scoreSummary", scoreSummaryService
				.querySummaryByCompanyId(sessionUser.getCompanyId()));
		return null;
	}

	@RequestMapping
	public ModelAndView conversion(HttpServletRequest request,
			Map<String, Object> out, Integer id, String conversionCategory) {
		out.put(FrontConst.MYRC_SUBTITLE, "兑换商品或服务");
		SsoUser sessionUser = getCachedUser(request);
		out.put("scoreSummary", scoreSummaryService
				.querySummaryByCompanyId(sessionUser.getCompanyId()));
		out.put("goods", scoreGoodsService.queryGoodsById(id));

		out.put("conversionCategory", conversionCategory);
		return null;
	}

	@RequestMapping
	public ModelAndView createConversion(HttpServletRequest request,
			Map<String, Object> out, ScoreConversionHistoryDo conversion)
			throws IOException {
		ExtResult result = new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		conversion.setCompanyId(sessionUser.getCompanyId());
		Integer i = scoreConversionHistoryService
				.insertConversionByCompany(conversion);
		if (i != null && i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	final static String RULECODE_PUNCH="get_punch_daily";
	@RequestMapping
	public ModelAndView punch(HttpServletRequest request, Map<String, Object> out) throws IOException{
		ExtResult result=new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		if(sessionUser==null){
			result.setData("sessionTimeOut");
		}else{
			ScoreChangeDetailsDo details = new ScoreChangeDetailsDo();
			details.setRulesCode(RULECODE_PUNCH);
			details.setCompanyId(sessionUser.getCompanyId());
			Integer impact = scoreChangeDetailsService.saveChangeDetails(details);
			if(impact!=null && impact.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
}
