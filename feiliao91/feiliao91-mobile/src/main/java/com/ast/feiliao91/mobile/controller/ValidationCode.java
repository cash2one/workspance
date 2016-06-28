/**
 * @author zhujq
 * @date 2016-06-14
 */
package com.ast.feiliao91.mobile.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.feiliao91.service.company.CompanyValidateService;
import com.ast.feiliao91.mobile.controller.BaseController;

@Controller
public class ValidationCode extends BaseController{
	@Resource
	private CompanyValidateService companyValidateService;
	
	@RequestMapping
	public void sendMobile(HttpServletRequest request, HttpServletResponse response, String mobile){
		companyValidateService.sendCodeByMobile(mobile,"0");
	}

}
