/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-19
 */
package com.ast.ast1949.company.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.company.InquiryService;
import com.zz91.util.auth.frontsso.SsoUser;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Controller
public class InquiryController extends BaseController {

	@Autowired
	private InquiryService inquiryService;

	@RequestMapping
	public ModelAndView createInquiry(Map<String, Object> model, HttpServletRequest request,
			Inquiry inquiry) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		//如果session失效，配置权限控制
		if(ssoUser==null){
			result.setData("sessionTimeOut");
			return printJson(result, model);
		}
		
		inquiry.setSenderAccount(ssoUser.getAccount());
		
		int i = inquiryService.inquiryByUser(inquiry, ssoUser.getCompanyId());
		
		if (i > 0){
			result.setSuccess(true);
			result.setData(inquiry.getBeInquiredId());			
		}
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView inquirySuccess(Map<String, Object> out, HttpServletRequest request,Integer cid){
		out.put("cid", cid);
		return null;
	}
}
