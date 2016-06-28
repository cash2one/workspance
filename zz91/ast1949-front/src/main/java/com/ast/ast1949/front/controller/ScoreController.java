/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-3
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.service.site.FeedbackService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.velocity.AddressTool;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-3
 */
@Controller
public class ScoreController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("score")+"/index.htm");
//		setSiteInfo(new PageHeadDTO(), out);
//		sidebarData(request, out); // 初始化侧边栏
//
//		out.put("indexGoodsLIst", scoreGoodsService.queryIndexScroeGoods(4));
//		// 获取近期兑换成功的信息
//		List<ScoreConversionHistoryDto> recentConversion = scoreConversionHistoryService
//				.queryRecentConversionHistory(5, CONVERSION_SUCCESS);
//		Map<String, String> industryMap = new HashMap<String, String>();
//		for (ScoreConversionHistoryDto dto : recentConversion) {
//			dto.setCompany(companyService.querySimpleCompanyById(dto.getHistory()
//					.getCompanyId()));
//			dto.setContacts(companyAccountService.queryAdminAccountByCompanyId(dto.getHistory().getCompanyId()));
//
//			industryMap.put(dto.getCompany().getIndustryCode(), CategoryFacade
//					.getInstance().getValue(
//							dto.getCompany().getIndustryCode()));
//			if (dto.getProfiler() == null) {
//				dto.setProfiler(new BbsUserProfilerDO());
//			}
//			dto.getProfiler().setPicturePath(
//					bbsService.queryUserProfilerPictureByCompanyId(dto
//							.getHistory().getCompanyId()));
//		}
//		out.put("recentConversionList", recentConversion);
//		out.put("industryMap", industryMap);
//		return null;
	}

	@RequestMapping
	public ModelAndView rule(HttpServletRequest request, Map<String, Object> out) {
		setSiteInfo(new PageHeadDTO(), out);
		return new ModelAndView("redirect:"+AddressTool.getAddress("score")+"/rule.htm");
//		sidebarData(request, out); // 初始化侧边栏
//		return null;
	}

	@RequestMapping
	public ModelAndView goods(HttpServletRequest request,
			Map<String, Object> out, String category) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("score")+"/goods.htm");
//		setSiteInfo(new PageHeadDTO(), out);
//		sidebarData(request, out); // 初始化侧边栏
//
//		List<ScoreGoodsDo> hotGoods = scoreGoodsService.queryHotScoreGoods(4);
//		out.put("hotGoods", hotGoods);
//
//		if (!StringUtils.isNumber(category)) {
//			category = "0";
//		}
//		page = scoreGoodsService.pageScoreGoodsByCategory(Integer
//				.valueOf(category), page);
//		out.put("page", page);
//
//		out.put("category", category);
//		return null;
	}

	@RequestMapping
	public ModelAndView goodsdetail(HttpServletRequest request,
			Map<String, Object> out, String category,Integer id) {
//		out.put("goods", scoreGoodsService.queryGoodsById(id));
////		CompanyContactsDO account = getCachedAccount(request);
//		SsoUser sessionUser = getCachedUser(request);
//		if (sessionUser != null) {
//			out.put("scoreSummary", scoreSummaryService
//					.querySummaryByCompanyId(sessionUser.getCompanyId()));
//		}
//		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
//				"baseConfig.resource_url"));
		return null;
	}

	@RequestMapping
	public ModelAndView annotated(HttpServletRequest request,
			Map<String, Object> out, PageDto<FeedbackDo> page) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("score")+"/annotated.htm");
//		setSiteInfo(new PageHeadDTO(), out);
//		sidebarData(request, out); // 初始化侧边栏
//
//		page.setPageSize(10);
//		page.setSort("gmt_created");
//		page.setDir("desc");
//
//		page = feedbackService.pageFeedbackByCategory(
//				FeedbackService.CATEGORY_SCORE, null, page);
//		Map<Integer, BbsUserProfilerDO> profilerMap = new HashMap<Integer, BbsUserProfilerDO>();
//		for (FeedbackDo feedback : page.getRecords()) {
//			if (feedback.getAccount() == null || feedback.getAccount()!="") {
//				profilerMap.put(feedback.getId(), null);
//			} else {
//				profilerMap.put(feedback.getId(), bbsService
//						.queryBbsUserProfilerByAccount(feedback.getAccount()));
//			}
//		}
//
//		out.put("page", page);
//		out.put("userProfilerMap", profilerMap);
//
//		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
//				"baseConfig.resource_url"));
//
//		return null;
	}

	final static String CONVERSION_SUCCESS = "1";

//	private void sidebarData(HttpServletRequest request, Map<String, Object> out) {
////		CompanyContactsDO account = getCachedAccount(request);
//		SsoUser sessionUser =getCachedUser(request);
//		// 获取BBS profiler信息
//		if (sessionUser != null) {
//			out.put("bbsUserProfiler", bbsUserProfilerService.queryProfilerOfAccount(sessionUser.getAccount()));
//			out.put("scoreSummary", scoreSummaryService
//					.querySummaryByCompanyId(sessionUser.getCompanyId()));
//		}
//		out.put("mostConversionGoods", scoreGoodsService
//				.queryMostConversionGoods(5));
//		// 获取积分达人信息
//		List<ScoreSummaryDto> mostOfUserScore = scoreSummaryService
//				.queryMostOfUserScore(5);
//		for (ScoreSummaryDto summary : mostOfUserScore) {
//			summary.setPicturePath(bbsService
//					.queryUserProfilerPictureByCompanyId(summary.getSummary()
//							.getCompanyId()));
//		}
//		out.put("mostOfUserScoreList", mostOfUserScore);
//
//		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
//				"baseConfig.resource_url"));
//
//	}

	@RequestMapping
	public ModelAndView adxml(HttpServletRequest request,
			Map<String, Object> out) {

		return null;
	}
	
	@RequestMapping
	public ModelAndView feedbackScore(HttpServletRequest request,
			Map<String, Object> out, FeedbackDo feedback) throws IOException{
		ExtResult result=new ExtResult();
		
//		CompanyContactsDO account = getCachedAccount(request);
		SsoUser sessionUser = getCachedUser(request);
		if(sessionUser!=null){
			feedback.setAccount(sessionUser.getAccount());
			feedback.setCompanyId(sessionUser.getCompanyId());
			feedback.setEmail(sessionUser.getEmail());
		}
		Integer id = feedbackService.insertFeedbackByScore(feedback);
		if(id!=null && id.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

}