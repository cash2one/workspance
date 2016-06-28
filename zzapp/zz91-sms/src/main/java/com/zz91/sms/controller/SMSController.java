package com.zz91.sms.controller;


import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.sms.domain.SmsLog;
import com.zz91.sms.dto.ExtResult;
import com.zz91.sms.service.SmsLogService;


@Controller
public class SMSController extends BaseController{
	
	@Resource
	private SmsLogService smsLogService;
	
	@RequestMapping
	public ModelAndView send(HttpServletRequest request,Map<String, Object>out,
			SmsLog sms)throws ParseException{
		
		Integer i=smsLogService.create(sms);
		
		ExtResult result = new ExtResult();	
		if (i!=null && i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out); 
		
	}

}
