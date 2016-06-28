/*
 * 文件名称：PageController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.api.controller.fragment;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.api.controller.BaseController;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchBuyDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeBuyNormDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.ep.service.trade.TradeBuyService;
import com.zz91.ep.service.trade.TradeSupplyService;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：共用页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class WwwController extends BaseController {

	@Resource
	private MessageService messageService;
	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource
	private TradeBuyService tradeBuyService;
	@Resource
	private CompProfileService compProfileService;

	/**
	 * 函数名称：topbar
	 * 功能描述：全局共用导航栏目
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param loginName String 登录用户名
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView topbar(HttpServletRequest request, Map<String, Object> out, String loginName){
		if(StringUtils.isNotEmpty(loginName)){
			out.put("loginName", loginName);
		}
		return null;
	}

	/**
	 * 函数名称：header
	 * 功能描述：全局共用头部导航
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param ik String 模块（首页-index;供应-supply;求购-buy;公司库-company;资讯-news;展会-exhibit）
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView header(HttpServletRequest request, Map<String, Object> out, String ik) {
		// 获取热门关键词
//        Map<String, String> hotKeyWords = TagsUtils.getInstance().queryTagsByCode("100010011000", null, 8);
//        out.put("hotKeyWords", hotKeyWords);
        // 获取最新供应留言
        out.put("messages", messageService.queryNewestMessage(null, 10));
		out.put("ik", ik);
		return null;
	}
	
	/**
	 * 函数名称：footer
	 * 功能描述：全局共用尾部内容
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView footer(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
	
	/**
	 * 首页左上角 数据
	 * 5条最新采购，5条最新供应，5条最近注册企业
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView pageTop(Map<String, Object>out){
		// 最新供应
		Map<String, Object> map =new HashMap<String, Object>();
		PageDto<TradeSupplyNormDto> supplyPage = new PageDto<TradeSupplyNormDto>();
		supplyPage.setLimit(5);
		map.put("supplyList", tradeSupplyService.pageBySearchEngine(new SearchSupplyDto(),supplyPage).getRecords());
		// 最新求购
		PageDto<TradeBuyNormDto> buyPage = new PageDto<TradeBuyNormDto>();
		supplyPage.setLimit(5);
		map.put("buyList", tradeBuyService.pageBySearchEngine(new SearchBuyDto(),buyPage).getRecords());
		// 最新企业
		map.put("companyList", compProfileService.queryNewestCompany("", 5));

		return printJson(map, out);
	}
}