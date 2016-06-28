/**
 * 
 */
package com.zz91.ep.admin.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.crm.CRMSvrService;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class SvrApiController extends BaseController {

	@Resource
	private CRMSvrService crmSvrService;
	
	final static String CALLBACK_CODE="ZZ91.";
	
	@RequestMapping
	public ModelAndView goOpenApi(HttpServletRequest request, Map<String, Object> out, 
			Integer crmSvrId, Integer companySvrId, Integer cid){
		
		String api=crmSvrService.queryOpenApi(crmSvrId);
		if(StringUtils.isEmpty(api)){
			return new ModelAndView("redirect:failure.htm");
		}
		
		Map<String, Object> callbackDataMap=new HashMap<String, Object>();
		callbackDataMap.put("cid", cid);
		callbackDataMap.put("companySvrId", companySvrId);
		out.put("callbackData", JSONObject.fromObject(callbackDataMap).toString());
		
		out.put("callbackCode", CALLBACK_CODE+crmSvrId);
		
		return new ModelAndView("redirect:"+api);
	}
	
	@RequestMapping
	public ModelAndView goCloseApi(HttpServletRequest request, Map<String, Object> out, 
			Integer crmSvrId, Integer companySvrId, Integer cid){
		
		String api=crmSvrService.queryCloseApi(crmSvrId);
		if(StringUtils.isEmpty(api)){
			return new ModelAndView("redirect:failure.htm");
		}
		
		Map<String, Object> callbackDataMap=new HashMap<String, Object>();
		callbackDataMap.put("cid", cid);
		callbackDataMap.put("companySvrId", companySvrId);
		out.put("callbackData", JSONObject.fromObject(callbackDataMap).toString());
		
		out.put("callbackCode", CALLBACK_CODE+crmSvrId);
		
		return new ModelAndView("redirect:"+api);
	}
	
	@RequestMapping
	public ModelAndView failure(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
}
