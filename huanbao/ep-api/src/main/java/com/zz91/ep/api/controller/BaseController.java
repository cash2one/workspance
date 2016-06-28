/*
 * 文件名称：BaseController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.api.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：控制器基类,用于封装HttpServletResponse对象用于输出JOSN。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class BaseController {

	public static final Integer DEFAULT_SIZE = 10;
	
	/**
	 * 函数名称：printJson
	 * 功能描述：打印JSON字符串。
	 *         1.如果obj为Array类型,则调用JSONArray来进行转换
	 *         2.如果obj为Object类型,则调用JSONObject来进行转换
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public ModelAndView printJson(Object obj, Map<String, Object> out){
		String jsonString = null;
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

//	/**
//	 * 函数名称：setSessionUser
//	 * 功能描述：获取session中用户信息
//	 * 输入参数：
//	 *        @param request HttpServletRequest
//	 * 异　　常：无
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 */
//    public EpAuthUser getCachedUser(HttpServletRequest request){
//    	return EpAuthUtils.getInstance().getEpAuthUser(request, null);
//    }
	
}