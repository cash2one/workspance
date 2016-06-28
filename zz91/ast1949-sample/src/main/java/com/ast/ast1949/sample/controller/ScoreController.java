package com.ast.ast1949.sample.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.sample.WeixinPrize;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.sample.WeixinPrizeService;
import com.ast.ast1949.service.sample.WeixinPrizelogService;
import com.ast.ast1949.service.sample.WeixinScoreService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.seo.SeoUtil;

@Controller
public class ScoreController extends BaseController {
	@Resource
	private WeixinScoreService weixinScoreService;
	@Resource
	private WeixinPrizelogService weixinPrizelogService;
	@Resource
	private WeixinPrizeService weixinPrizeService;
	
	/**
	 * 积分兑换首页
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView scorePrizeList(HttpServletRequest request, Map<String, Object> out, PageDto<WeixinPrize> page) {
		
		SsoUser sessionUser = getCachedUser(request);
		String account =null;
		if (sessionUser != null) {
			account = sessionUser.getAccount();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 2);
		page.setPageSize(20);
		page = weixinPrizeService.queryListByFilter(page, map);
		
		//可兑换的积分数
		Integer totalScore = weixinScoreService.totalAvailableScore(account); 
		//已兑换的积分数
		Integer totalScoreEx = weixinPrizelogService.totalConvertScore(account); 
		
		out.put("totalScore", totalScore - totalScoreEx);
		
		out.put("page", page);
		
		SeoUtil.getInstance().buildSeo(out);
		
		return new ModelAndView("index");
	}
	
	/**
	 * 积分兑换详情
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView scorePrizeDetail(HttpServletRequest request, Map<String, Object> out, Integer    id) {
		WeixinPrize   weixinPrize = weixinPrizeService.selectByPrimaryKey(id);
		out.put("weixinPrize", weixinPrize);
		return new ModelAndView("/score/scorePrizeDetail");
	}
	
	/**
	 * 积分兑换申请
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView scorePrizeApply(HttpServletRequest request, Map<String, Object> out, Integer    id) {
		SsoUser sessionUser = getCachedUser(request);
		String account =null;
		if (sessionUser != null) {
			account = sessionUser.getAccount();
		}
		
		if(account ==null){
			out.put("result", 9);
			return new ModelAndView("/score/scorePrizeSuc");
		}
		
		WeixinPrize   weixinPrize = weixinPrizeService.selectByPrimaryKey(id);
		
		//兑换申请
		Integer ischeck = 0; 
		if (weixinPrize.getTitle().contains("联系方式")||weixinPrize.getTitle().contains("查看")) { //查看微站联系方式不需要审核
			ischeck = 1;
		}
		
		Integer result =	weixinPrizeService.prizeApply(id,account,ischeck);
		
		out.put("weixinPrize", weixinPrize);
		out.put("result", result);
		return new ModelAndView("/score/scorePrizeSuc");
	}
	
}
