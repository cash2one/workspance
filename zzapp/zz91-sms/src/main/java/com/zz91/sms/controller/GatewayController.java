/**
 * 
 */
package com.zz91.sms.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.sms.common.ZZSms;
import com.zz91.sms.domain.Gateway;
import com.zz91.sms.dto.ExtResult;
import com.zz91.sms.service.GatewayService;
import com.zz91.sms.thread.ControlThread;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 * 
 */
@Controller
public class GatewayController extends BaseController {
	
	@Resource
	private GatewayService gatewayService;

	final static String JAR_FOLDER="/sms-gateway";
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		out.put("sendDebug", ControlThread.DEBUG);
		return null;
	}
	
	@RequestMapping
	public ModelAndView query(HttpServletRequest request,Map<String, Object>out,Integer enabled){
		
		List<Gateway> list=gatewayService.query(enabled);
		
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest requst,Map<String, Object>out,Integer id){
		
		Integer i=gatewayService.remove(id);
		
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView add(HttpServletRequest request,Map<String, Object>out ,Gateway gateway){
		
		Integer i=gatewayService.create(gateway);
		
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
		
	}
	
	@RequestMapping
	public ModelAndView queryOne(HttpServletRequest request,Map<String, Object>out,Integer id){
		
		Gateway gateway=gatewayService.queryOne(id);
		
		return printJson(gateway, out);
	}
	
	@RequestMapping
	public ModelAndView update(HttpServletRequest request,Map<String, Object>out,Gateway gateway){
		
		ExtResult result=new ExtResult();
		if(gateway!=null){
			Integer i=gatewayService.update(gateway);
			if(i!=null && i.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView enabledGate(HttpServletRequest request,Map<String, Object>out,Integer id, String code){
		
		gatewayService.enabled(id, code);
		
		ExtResult result=new ExtResult();
		if(id!=null && id.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView disenabledGate(HttpServletRequest request,Map<String, Object>out,Integer id, String code){
		
		gatewayService.disabled(id,code);
		
		ExtResult result=new ExtResult();
		if(id!=null && id.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView balance(HttpServletRequest request,Map<String, Object>out,
			String code){
		
		ZZSms sms = (ZZSms) GatewayService.CACHE_GATEWAY.get(code);
		
		ExtResult result=new ExtResult();
			if (sms != null ) {
				result.setData(sms.balance());
			}else {
				result.setData(0);
			}
			result.setSuccess(true);
		return printJson(result, out);
	}


	
	@RequestMapping
	public ModelAndView testGateway(HttpServletRequest request,
			Map<String, Object> out, String mobile,String gatewayCode) {

		ZZSms zzSms=(ZZSms) GatewayService.CACHE_GATEWAY.get(gatewayCode);

		ExtResult result = new ExtResult();
		do{
			if (zzSms == null ) {
				break;
			}
			if (ControlThread.DEBUG){
				System.out.println("send mobile:"+mobile+" ; send message:网管测试短信【test】");
			}else{
				zzSms.send(mobile, "网管测试短信【test】");
				result.setData("=======调试状态短信发送:send mobile(发送目标):"+mobile+" ; send message(发送内容):网管测试短信【test】");
			}
			
		}while(false);
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView uploadjar(HttpServletRequest request,
			Map<String, Object> out) throws Exception {
		
		String apiJar=MvcUpload.localUpload(request, MvcUpload.getDestRoot()+JAR_FOLDER,
			null, "uploadfile", new String[]{".jar"}, 
			new String[]{".bat", ".sh", ".exe"}, 2*1024);
		
		ExtResult result = new ExtResult();
		if (StringUtils.isNotEmpty(apiJar)) {
			result.setSuccess(true);
			result.setData(MvcUpload.getDestRoot()+JAR_FOLDER+"/"+apiJar);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView switchSendModel(Map<String,Object>out,Boolean debug){
		ExtResult result = new ExtResult();
		ControlThread.DEBUG=debug;
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView getSendModel(Map<String,Object>out){
		ExtResult result = new ExtResult();
		result.setData(ControlThread.DEBUG);
		return printJson(result, out);
	}
}
