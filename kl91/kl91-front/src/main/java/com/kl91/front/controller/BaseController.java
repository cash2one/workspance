package com.kl91.front.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.auth.Kl91SsoUser;
import com.kl91.domain.auth.Kl91SsoUtils;
import com.zz91.util.auth.frontsso.SsoUtils;

public class BaseController {

	public Kl91SsoUser getSessionUser(HttpServletRequest request) {
		return (Kl91SsoUser) request.getSession().getAttribute(
				Kl91SsoUser.SESSION_KEY);
	}

	protected ModelAndView alertMsgAndLocation(Map<String, Object> out,
			String msg, String location) {
		out.put("msg", msg);
		out.put("location", location);
		return new ModelAndView("common/alertLocationJs");
	}

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

	public void setSessionUser(HttpServletRequest request, Kl91SsoUser ssoUser) {
		Kl91SsoUtils.getInstance().setSessionUser(request, ssoUser, null);
	}

	public void cleanCachedSession(HttpServletRequest request) {
		SsoUtils.getInstance().clearnSessionUser(request, null);
	}

	public Kl91SsoUser getCachedUser(HttpServletRequest request) {
		return Kl91SsoUtils.getInstance().getSessionUser(request, null);
	}

	public void setCachedSession(HttpServletRequest request,
			HttpServletResponse response, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

}
