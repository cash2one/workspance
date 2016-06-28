/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-19
 */
package com.ast.ast1949.trade.controller;

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

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Deprecated
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
	public ModelAndView inquirySuccess(Map<String, Object> out, HttpServletRequest request,Integer pid){
		out.put("pid", pid);
		return null;
	}

	@RequestMapping
	public ModelAndView inquiryProducts(Map<String, Object> out, HttpServletRequest request, 
			HttpServletResponse response,
			Inquiry inquiry, String account, String password){
		SsoUser ssoUser = getCachedUser(request);
		do {
			if(ssoUser==null){
				try {
					ssoUser = SsoUtils.getInstance().validateUser(response, account, password, null, null);
				} catch (NoSuchAlgorithmException e) {
				} catch (AuthorizeException e) {
				} catch (IOException e) {
				}
			}
			
			if(ssoUser==null){
				break;
			}
			
			inquiry.setSenderAccount(ssoUser.getAccount());
			
			int i = inquiryService.inquiryByUser(inquiry, ssoUser.getCompanyId());
			
			if (i > 0){
				//TODO 发送留言成功
			}
			
		} while (false);
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView inquiryCompany(Map<String, Object> out, HttpServletRequest request, 
			Inquiry inquiry, String account, String password){
		return null;
	}
	
}
