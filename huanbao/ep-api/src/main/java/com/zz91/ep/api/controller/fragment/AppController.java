package com.zz91.ep.api.controller.fragment;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.api.controller.BaseController;
import com.zz91.ep.service.trade.MessageService;

@Controller
public class AppController  extends BaseController{
	
	@Resource
	private MessageService messageService;
	
	/**
	 * 函数名称：topbar
	 * 功能描述：新版通用顶部页面
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param loginName String 登录用户名
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
	 */
	@RequestMapping
	public ModelAndView header(HttpServletRequest request, Map<String, Object> out, String ik) {
		return null;
	}
	
	/**
	 * 函数名称：footer
	 * 功能描述：全局共用尾部内容
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 */
	@RequestMapping
	public ModelAndView footer(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView message(HttpServletRequest request, Map<String, Object> out){
		// 获取最新供应留言
		out.put("messages", messageService.queryNewestMessage(null, 10));
		return null;
	}
}
