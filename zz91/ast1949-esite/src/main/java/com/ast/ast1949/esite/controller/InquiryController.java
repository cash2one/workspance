/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-19
 */
package com.ast.ast1949.esite.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.company.InquiryService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Controller
public class InquiryController extends BaseController {

	@Autowired
	private InquiryService inquiryService;

	@RequestMapping
	public ModelAndView createInquiry(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response,
			Inquiry inquiry, String email, String password) throws IOException, NoSuchAlgorithmException, AuthorizeException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		if(StringUtils.isNotEmpty(password)){
			ssoUser=SsoUtils.getInstance().validateUser(response, email, password, null,null);
		}
		
		//如果session失效，配置权限控制
		if(ssoUser==null){
			result.setData("sessionTimeOut");
			return printJson(result, model);
			
		}
		
		inquiry.setSenderAccount(ssoUser.getAccount());
		
		int i = inquiryService.inquiryByUser(inquiry, ssoUser.getCompanyId());
		
		if (i > 0)
			result.setSuccess(true);
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView inquiry(Map<String, Object> out, HttpServletRequest request,
			Inquiry inquiry) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		do {
			//如果session失效，配置权限控制
			if(ssoUser==null){
				result.setData("sessionTimeOut");
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());
			
			int i = inquiryService.inquiryByUser(inquiry, ssoUser.getCompanyId());
			if (i > 0){
				result.setSuccess(true);
			}
			
		} while (false);
		out.put("result", result);
		return new ModelAndView("submit");
	}
}
