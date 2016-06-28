/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-25 下午01:57:56
 */
package com.ast.ast1949.score.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.param.ParamUtils;

/**
 * 控制器基类,用于封装HttpServletResponse对象用于输出JOSN
 * 
 * @author Ryan
 * 
 */
public class BaseController {

//	@Autowired
//	InquiryService inquiryService;

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
	@SuppressWarnings("unchecked")
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

	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
//		String sessionId = HttpUtils.getInstance().getCookie(
//				request,
//				FrontConst.MYSESSIONID,
//				String.valueOf(MemcachedFacade.getInstance().get(
//						"baseConfig.domain")));
//		if (sessionId == null) {
//			sessionId = UUID.randomUUID().toString();
//		}
//		HttpUtils.getInstance().setCookie(
//				response,
//				FrontConst.MYSESSIONID,
//				sessionId,
//				String.valueOf(MemcachedFacade.getInstance().get(
//						"baseConfig.domain")), null);
		return null;
	}
	
	public Object getCachedSession(HttpServletRequest request, String key){
		return request.getSession().getAttribute(key);
	}
	
	public void setCachedSession(HttpServletRequest request, HttpServletResponse response, String key, Object value){
		request.getSession().setAttribute(key, value);
	}

	/**
	 * 获取登录用户的信息,并将信息保存到数据模型中
	 * 
	 * @param request
	 * @param model
	 *            :数据模型
	 * @return
	 */
	// public Map<String, Object> getLoginInformation(HttpServletRequest
	// request,
	// Map<String, Object> model) {
	// model.put("loginuser", getCachedSession(request,
	// FrontConst.SESSION_USER));
	// return model;
	// }

	/**
	 * 得到登录信息,保存在缓存中的账户信息 此信息非常重要,用来标记一个用户是否真的登录了
	 * 
	 * @param request
	 * @return
	 */
	// public AuthUser getCachedAuthUser(HttpServletRequest request) {
	// SessionUserDTO sessionUserDTO = (SessionUserDTO) getCachedSession(
	// request, FrontConst.SESSION_USER);
	// AuthUser user = sessionUserDTO.getUser();
	// return user;
	// }

	/**
	 * 得到登录信息,保存在缓存中的个人信息
	 * 
	 * @param request
	 * @return AdminUserDO
	 */
//	public SessionUser getCachedUser(HttpServletRequest request) {
//		SessionUser sessionuser = (SessionUser) getCachedSession(request,
//				FrontConst.SESSION_USER);
//		return sessionuser;
//	}

	// /**
	// * 得到登录信息保存在缓存中的组织信息
	// *
	// * @param request
	// * @return AuthDept
	// */
	// public CompanyDO getCachedCompany(HttpServletRequest request){
	// SessionUserDTO sessionUserDTO = (SessionUserDTO)
	// getCachedSession(request, FrontConst.SESSION_USER);
	// if(sessionUserDTO!=null){
	// CompanyDO company=(CompanyDO) sessionUserDTO.getCompany();
	// return company;
	// } else {
	// return null;
	// }
	//
	// }

	// /**
	// * 得到登录信息保存在缓存中的所有信息
	// *
	// * @param request
	// * @return SessionUserDTO
	// */
	// public SessionUserDTO getCachedSessionUser(HttpServletRequest request) {
	// SessionUserDTO sessionUserDTO = (SessionUserDTO) getCachedSession(
	// request, FrontConst.SESSION_USER);
	// return sessionUserDTO;
	// }

	/**
	 * 设置网页的head信息和导航条选中项
	 * 
	 * @param headDTO
	 *            头部信息和导航条选中项
	 * @param out
	 */
	public void setSiteInfo(PageHeadDTO headDTO, Map<String, Object> out) {
		Map<String, String> siteinfo = getSiteInfo();
		if (StringUtils.isNotEmpty(headDTO.getPageTitle())) {
			out.put("pageTitle", headDTO.getPageTitle().replace("${site_name}",
					siteinfo.get("site_name")));
		}
		if (StringUtils.isNotEmpty(headDTO.getPageKeywords())) {
			out.put("pageKeywords", headDTO.getPageKeywords().replace(
					"${site_name}", siteinfo.get("site_name")));

		}
		if (StringUtils.isNotEmpty(headDTO.getPageDescription())) {
			out.put("pageDescription", headDTO.getPageDescription().replace(
					"${site_name}", siteinfo.get("site_name")));
		}

		out.put("topNavIndex", headDTO.getTopNavIndex());

		for (String k : siteinfo.keySet()) {
			out.put(k, siteinfo.get(k));
		}

		// 头部滚动公告
//		out.put("scrollInfo", inquiryService.queryScrollInquiry());
	}

	/**
	 * 从缓存中读取网站信息，type:site_info_front
	 * 
	 * @return Map<KEY,VALUE>
	 */
	public Map<String, String> getSiteInfo() {
		return ParamUtils.getInstance().getChild("site_info_front");
	}
}
