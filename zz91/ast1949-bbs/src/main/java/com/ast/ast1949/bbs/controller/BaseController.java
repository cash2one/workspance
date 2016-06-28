/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-25 下午01:57:56
 */
package com.ast.ast1949.bbs.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.PageHeadDTO;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.tags.TagsUtils;

/**
 * 控制器基类,用于封装HttpServletResponse对象用于输出JOSN
 * 
 * @author Ryan
 * 
 */
public class BaseController {

	// @Autowired
	// private RightService rightService;
	// @Autowired
	// private BbsService bbsService;

//	@SuppressWarnings("deprecation")
//	public Map<String, Object> getCached(HttpServletRequest request) {
//		Integer companyId = null;
//		String account = null;
//		Integer hour = null;
//		Map<String, Object> map = new HashMap<String, Object>();
//		// 从缓存中获取companyId,account信息
//		if (getCachedCompany(request) != null) {
//			companyId = getCachedCompany(request).getId();
//			account = getCachedAuthUser(request).getUsername();
//			if (account != null) {
//				hour = new Date().getHours();
//				if (hour > 12)
//					map.put("hour", "下午好");
//				else
//					map.put("hour", "上午好");
//			}
//		}
//		map.put("account", account);
//		map.put("companyId", companyId);
//		return map;
//	}

	/**
	 * 打印JSON字符串
	 * 
	 * @param obj
	 *            1.如果obj为Array类型,则调用JSONArray来进行转换
	 *            2.如果obj为Object类型,则调用JSONObject来进行转换
	 * @param out
	 *            将生成的JSON字符串放到该map里
	 * @throws IOException
	 *             出现IO异常时抛出
	 */
	public ModelAndView printJson(Object obj, Map<String, Object> out)
			throws IOException {
		String jsonString = "";
		if (obj instanceof List) {
			JSONArray json = JSONArray.fromObject(obj);
			jsonString = (json.toString());
		} else {
			JSONObject json = JSONObject.fromObject(obj);
			jsonString = (json.toString());
		}
		out.put("json", jsonString);
		return new ModelAndView("json");
	}

	/**
	 * 打印JS字符串
	 * 
	 * @param s
	 *            输出的js
	 * @param out
	 *            将生成的JS字符串放到该map里
	 * @throws IOException
	 *             出现IO异常时抛出
	 */
	public ModelAndView printJs(String s, Map<String, Object> out)
			throws IOException {
		out.put("js", s);
		return new ModelAndView("js");
	}

	public void setSessionUser(HttpServletRequest request, SsoUser ssoUser) {

		SsoUtils.getInstance().setSessionUser(request, ssoUser, null);
	}

	public void cleanCachedSession(HttpServletRequest request) {
		SsoUtils.getInstance().clearnSessionUser(request, null);
	}

	public SsoUser getCachedUser(HttpServletRequest request) {
		return SsoUtils.getInstance().getSessionUser(request, null);
	}

	/**
	 * 设置网页的head信息和导航条选中项
	 * 
	 * @param headDTO
	 *            头部信息和导航条选中项
	 * @param out
	 */
	public void setPageHead(PageHeadDTO headDTO, Map<String, Object> out) {
		out.put("topNavIndex", headDTO.getTopNavIndex());
		out.put("pageTitle", headDTO.getPageTitle());
		out.put("pageKeywords", headDTO.getPageKeywords());
		out.put("pageDescription", headDTO.getPageDescription());
	}
	
	public void commonHead(Map<String, Object> out) {
		Map<String, String> tagsMap = new LinkedHashMap<String, String>();
		try {
			tagsMap.put("废电器", URLEncoder.encode("废电器", "utf-8"));
			tagsMap.put("国内资讯", URLEncoder.encode("国内资讯", "utf-8"));
			tagsMap.put("废料百态", URLEncoder.encode("废料百态", "utf-8"));
			tagsMap.put("行情综述", URLEncoder.encode("行情综述", "utf-8"));
			tagsMap.put("今日导读", URLEncoder.encode("今日导读", "utf-8"));
			tagsMap.put("国外资讯", URLEncoder.encode("国外资讯", "utf-8"));
			tagsMap.put("热门评论", URLEncoder.encode("热门评论", "utf-8"));
			tagsMap.put("资讯评论", URLEncoder.encode("资讯评论", "utf-8"));
			tagsMap.put("商务交流", URLEncoder.encode("商务交流", "utf-8"));
			tagsMap.put("焦点关注", URLEncoder.encode("焦点关注", "utf-8"));
		} catch (UnsupportedEncodingException e) {
		}
		// 查询热点标签 100010001000
		out.put("tagsList", TagsUtils.getInstance().queryTagsByCode(
				"100010001000", 0, 12));
	}
	
	/**
	 * 清除cdn缓存
	 */
	public void clearCDN(HttpServletResponse response){
		response.addHeader( "Cache-Control", "no-cache" );
		response.setDateHeader("Expires", 0);
	}

}
