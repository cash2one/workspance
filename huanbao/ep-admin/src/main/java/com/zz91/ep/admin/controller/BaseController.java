/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package com.zz91.ep.admin.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.common.LogInfo;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.log.LogUtil;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-5-4
 */
public class BaseController {
	
    protected final static String UPLOAD_ROOT="/mnt/data/huanbao/resources";
    protected final static String DEFAULT_FIELD="uploadfile";
    protected final static String REGISTER_TYPE="register_type";
   
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
	
	public String buildPath(String model){
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(new Date());
    	return "/"+model+"/"+calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
    }
	
	/**
	 * 给信息添加操作日志记录
	 * @param param
	 * @param id
	 * @return
	 */
	protected LogInfo queryLogs(Map<String, Object> param){
		
		JSONObject jobj=LogUtil.getInstance().readMongo(param, 0,1);
		LogInfo logInfo = new LogInfo("无", "暂无操作", null);
		if(jobj==null){
			return logInfo;
		}
		
		JSONArray logs = jobj.getJSONArray("records");
		//JSONObject ss = LogUtil.getInstance().readMongo(param, 0,1).getJSONArray("records").getJSONObject(0);
		if(logs.size()>0){
			JSONObject log = logs.getJSONObject(0);
			logInfo = new LogInfo(log.getString("operator"),log.getString("operation"),new Date(log.getLong("time")));
		}
//		else{
//			logInfo = new LogInfo("无", "暂无操作", null);
//		}
		return logInfo;
	}

}
