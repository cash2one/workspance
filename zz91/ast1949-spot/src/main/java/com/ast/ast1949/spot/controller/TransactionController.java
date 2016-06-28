package com.ast.ast1949.spot.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.spot.SpotAuctionLog;
import com.ast.ast1949.domain.spot.SpotOrder;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.spot.SpotOrderDto;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.spot.SpotAuctionLogService;
import com.ast.ast1949.service.spot.SpotOrderService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * author:kongsj date:2013-3-25
 */
@Controller
public class TransactionController extends BaseController {

	@Resource
	private SpotOrderService spotOrderService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private SpotAuctionLogService spotAuctionLogService;

	@RequestMapping
	public ModelAndView addToTrans(Map<String, Object> out,
			SpotOrder spotOrder, HttpServletRequest request) throws IOException {
		SsoUser user = getCachedUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		// 订单公司id
		spotOrder.setCompanyId(user.getCompanyId());
		// 订单公司名
		spotOrder.setOrderCompanyName(companyService.queryCompanyNameById(user.getCompanyId()));
		// 订单详细
		spotOrder.setDetail("");
		// 订单标题名 价格单位 质量单位 中文转码
		spotOrder.setOrderProductTitle(URLDecoder.decode(spotOrder
				.getOrderProductTitle(), HttpUtils.CHARSET_UTF8));
		spotOrder.setPriceUnit(URLDecoder.decode(spotOrder.getPriceUnit(),
				HttpUtils.CHARSET_UTF8));
		spotOrder.setQuantityUnit(URLDecoder.decode(
				spotOrder.getQuantityUnit(), HttpUtils.CHARSET_UTF8));
		// 新增购物车
		Integer i = spotOrderService.insert(spotOrder);
		if (i > 0) {
			map.put("success", true);
		} else {
			map.put("success", false);
		}
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView cart(Map<String, Object> out,
			HttpServletRequest request, PageDto<SpotOrderDto> page) {
		SsoUser user = getCachedUser(request);
		page = spotOrderService.pageSpotOrderForFront(user.getCompanyId(), SpotOrderService.STATUS_CART,page);
		out.put("page", page);
		//seo 
		SeoUtil.getInstance().buildSeo("cart", out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView confirm(Map<String, Object> out,
			HttpServletRequest request, PageDto<SpotOrderDto> page) {
		SsoUser user = getCachedUser(request);
		Integer companyId = user.getCompanyId();
		page = spotOrderService.pageSpotOrderForFront(user.getCompanyId(), SpotOrderService.STATUS_CONFIRM,page);
		CompanyDto dto = companyService.queryCompanyDetailById(companyId);
		dto.setAccount(companyAccountService.queryAccountByCompanyId(companyId));
		out.put("dto", dto);
		out.put("page", page);
		//seo 
		SeoUtil.getInstance().buildSeo("confirm", out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView deleteBySpotId(String id, Integer pageType,HttpServletRequest request) {
		SsoUser user = getCachedUser(request);
		do {
			if (StringUtils.isEmpty(id)) {
				break;
			}
			// 删除
			spotOrderService.deleteSpotOrderById(id,user.getCompanyId());
		} while (false);
		if (pageType==null||pageType == 0) {
			return new ModelAndView("redirect:/cart.htm");
		} else {
			return new ModelAndView("redirect:/confirm.htm");
		}
	}
	
	@RequestMapping
	public ModelAndView doConfirm(String id,HttpServletRequest request){
		SsoUser user = getCachedUser(request);
		do {
			if(StringUtils.isEmpty(id)){
				break;
			}
			// 更新为购买状态，进入确认购买页面
			spotOrderService.confirmTransaction(SpotOrderService.STATUS_CONFIRM, id, user.getCompanyId());
		} while (false);
		return new ModelAndView("redirect:/confirm.htm");
	}
	
	@RequestMapping
	public ModelAndView doTrans(String id,HttpServletRequest request){
		SsoUser user = getCachedUser(request);
		do {
			if(StringUtils.isEmpty(id)){
				break;
			}
			// 更新为购买状态，进入确认购买页面
			spotOrderService.confirmTransaction(SpotOrderService.STATUS_SUCCESS, id, user.getCompanyId());
		} while (false);
		return new ModelAndView("redirect:/success.htm");
	}
	
	@RequestMapping
	public ModelAndView doAuctionLog(Map<String, Object> out,HttpServletRequest request,SpotAuctionLog spotAuctionLog) throws IOException{
		SsoUser user = getCachedUser(request);
		spotAuctionLog.setCompanyId(user.getCompanyId());
		Integer i = spotAuctionLogService.insert(spotAuctionLog);
		Map<String, Object> map = new HashMap<String, Object>();
		if(i>0){
			map.put("success",true);
		}
		return printJson(map, out);
	}

}