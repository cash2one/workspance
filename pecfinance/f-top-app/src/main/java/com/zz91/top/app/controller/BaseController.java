/**
 * 
 */
package com.zz91.top.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.zz91.top.app.domain.SessionUser;
import com.zz91.top.app.utils.AppConst;

/**
 * @author mays
 *
 */
public class BaseController {

	public ModelAndView printJson(Object obj, Map<String, Object> out) {
		String jsonString = "";
		if (obj instanceof List) {
			jsonString = (JSONArray.fromObject(obj).toString());
		} else {
			jsonString = (JSONObject.fromObject(obj).toString());
		}
		out.put("json", jsonString);
		return new ModelAndView("json");
	}
	
	public SessionUser getSessionUser(HttpServletRequest request) {
		return (SessionUser) request.getSession().getAttribute(AppConst.SESSION_KEY);
	}
}
