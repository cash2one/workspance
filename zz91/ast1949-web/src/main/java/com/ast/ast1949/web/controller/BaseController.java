/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-25 下午01:57:56
 */
package com.ast.ast1949.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;

/**
 * 控制器基类,用于封装HttpServletResponse对象用于输出JOSN
 *
 * @author Ryan
 *
 */
public class BaseController {
	/**
	 * 打印JSON字符串
	 *
	 * @param obj
	 *            1.如果obj为Array类型,则调用JSONArray来进行转换
	 *            2.如果obj为Object类型,则调用JSONObject来进行转换
	 * @param model
	 *            将生成的JSON字符串放到该map里
	 * @throws IOException
	 *             出现IO异常时抛出
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView printJson(Object obj, Map<String, Object> model)
			throws IOException {
		String jsonString = "";
		if (obj instanceof List) {
			JSONArray json = JSONArray.fromObject(obj);
			jsonString = (json.toString());
		} else {
			JSONObject json = JSONObject.fromObject(obj);
			jsonString = (json.toString());
		}
		model.put("json", jsonString);

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

	public void setSessionUser(HttpServletRequest request, SessionUser sessionUser) {
		AuthUtils.getInstance().setSessionUser(request, sessionUser, null);
	}

	public void cleanCachedSession(HttpServletRequest request) {
		AuthUtils.getInstance().clearnSessionUser(request, null);
	}
	
	public SessionUser getCachedUser(HttpServletRequest request){
		return AuthUtils.getInstance().getSessionUser(request, null);
	}
}
