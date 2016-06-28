/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-7-15
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.auth.AuthForgotPassword;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.util.DateUtil;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Controller
public class UserController extends BaseController {

	@Autowired
	private AuthService authService;

	@RequestMapping(method = RequestMethod.GET)
	public void passwordReminder(Map<String, Object> model){
		// 跳转到密码更改页面
		setSiteInfo(new PageHeadDTO(), model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView passwordReminder2(Map<String, Object> model, String email, HttpServletRequest request)
		throws IOException, NoSuchAlgorithmException{
		//TODO 设置密码更改标记
		//TODO 跳转到相应的页面,给出提示
		ExtResult result = new ExtResult();
		String key = authService.generateForgotPasswordKey(email);
		if(key!=null){
			//TODO 发送邮件
			String url="#";
			if(request.getServerPort()==80){
				url=request.getServerName()+request.getContextPath();
			}else{
				url=request.getServerName()+":"+request.getServerPort()+request.getContextPath();
			}
			authService.sendResetPasswordMail(key,email,url);
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView isemailregist(Map<String, Object> model, String email) throws IOException{
		ExtResult result = new ExtResult();
		Integer i=authService.countUserByEmail(email);
		if (i!= null && i.intValue()>0) {
			result.setSuccess(true);
//			result.setData(u.getId());
		}
		return printJson(result, model);
	}

	@RequestMapping(method = RequestMethod.GET)
	public void resetPassword(Map<String, Object> model, String k, String am) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int outdate=1;
		do {
			if(am==null || "".equals(am)){
				break;
			}

			if(k==null || "".equals(k)){
				break;
			}
			AuthForgotPassword o= authService.listAuthForgotPasswordByKey(k);
			if(o==null){
				break;
			}
			long start=DateUtil.getMillis(o.getGmtCreated());
			long now=DateUtil.getMillis(new Date());
			long twoday=2*60*60*24*1000;
			if((now-start)>twoday){
				break;
			}
			outdate=0;
			model.put("k", k); //key
			model.put("am", am);
		} while (false);

		model.put("outDate", outdate);
		setSiteInfo(new PageHeadDTO(), model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView resetPassword(Map<String, Object> model, String k, String am, String pwd1, String pwd2)
		throws IOException, NoSuchAlgorithmException{
		ExtResult result= new ExtResult();
		result.setSuccess(authService.resetPasswordFromForgotPasswordKey(am, k, pwd1));
		return printJson(result, model);
	}
}
