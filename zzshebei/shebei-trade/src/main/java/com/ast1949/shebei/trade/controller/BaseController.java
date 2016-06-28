package com.ast1949.shebei.trade.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView printJson(Object obj, Map<String, Object> out){
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
	
	public Object getCachedSession(HttpServletRequest request, String key){
		return request.getSession().getAttribute(key);
	}
	
	public void setCachedSession(HttpServletRequest request, HttpServletResponse response, String key, Object value){
		request.getSession().setAttribute(key, value);
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
