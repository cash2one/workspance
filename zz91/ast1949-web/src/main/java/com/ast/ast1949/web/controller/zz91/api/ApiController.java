/**
 * 
 */
package com.ast.ast1949.web.controller.zz91.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class ApiController extends BaseController {

	@Resource
	private CrmSvrService crmSvrService;
	
	final static String CALLBACK_CODE="ZZ91.";
	
	@RequestMapping
	public ModelAndView goOpenApi(HttpServletRequest request, Map<String, Object> out, 
			String svrCode, Integer companySvrId, Integer companyId){
		
		String api=crmSvrService.queryOpenApi(svrCode);
		if(StringUtils.isEmpty(api)){
			return new ModelAndView("redirect:failure.htm");
		}
		
		Map<String, Object> callbackDataMap=new HashMap<String, Object>();
		callbackDataMap.put("companyId", companyId);
		callbackDataMap.put("companySvrId", companySvrId);
		out.put("callbackData", JSONObject.fromObject(callbackDataMap).toString());
		
		out.put("callbackCode", CALLBACK_CODE+svrCode);
		
		return new ModelAndView("redirect:"+api);
	}
	
	@RequestMapping
	public ModelAndView goChangeApi(HttpServletRequest request, Map<String, Object> out, 
			String svrCode, Integer companySvrId, Integer companyId){
		
		String api=crmSvrService.queryOpenApi(svrCode);
		if(StringUtils.isEmpty(api)){
			return new ModelAndView("redirect:failure.htm");
		}
		
		Map<String, Object> callbackDataMap=new HashMap<String, Object>();
		callbackDataMap.put("companyId", companyId);
		callbackDataMap.put("companySvrId", companySvrId);
		out.put("callbackData", JSONObject.fromObject(callbackDataMap).toString());
		
		out.put("callbackCode", CALLBACK_CODE+svrCode);
		
		return new ModelAndView("redirect:"+api.replace("open", "change"));
	}
	
	@RequestMapping
	public ModelAndView goCloseApi(HttpServletRequest request, Map<String, Object> out, 
			String svrCode, Integer companySvrId, Integer companyId){
		
		String api=crmSvrService.queryCloseApi(svrCode);
		if(StringUtils.isEmpty(api)){
			return new ModelAndView("redirect:failure.htm");
		}
		
		Map<String, Object> callbackDataMap=new HashMap<String, Object>();
		callbackDataMap.put("companyId", companyId);
		callbackDataMap.put("companySvrId", companySvrId);
		out.put("callbackData", JSONObject.fromObject(callbackDataMap).toString());
		
		out.put("callbackCode", CALLBACK_CODE+svrCode);
		
		return new ModelAndView("redirect:"+api);
	}
	
	@RequestMapping
	public ModelAndView failure(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
}
