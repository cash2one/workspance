/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package com.zz91.sms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;


/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-5-4
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
