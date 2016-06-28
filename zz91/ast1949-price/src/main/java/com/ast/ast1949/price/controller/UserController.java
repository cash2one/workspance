/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.price.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.velocity.AddressTool;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class UserController extends BaseController {
	
	@RequestMapping
	public ModelAndView checkuser(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response,
			String username, String password, String randCode,
			String randCodeKey, String url, String cookieMaxAge)
			throws IOException {
		ExtResult extResult = new ExtResult();
		
		SsoUser ssoUser=null;
		try {
			ssoUser = SsoUtils.getInstance().validateUser(response, username, password, null, null);
		} catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			extResult.setData(AuthorizeException.getMessage(e.getMessage()));
		}
		
		if(ssoUser!=null){
			setSessionUser(request, ssoUser);
			extResult.setSuccess(true);
			if (StringUtils.isEmpty(url)) {
				extResult.setData(AddressTool.getAddress("myrc") + "/index.htm");
			} else {
				extResult.setData(url);
			}
		}else {
			extResult.setData("用户名或者密码写错了，检查下大小写是否都正确了，再试一次吧");
		}
		return printJson(extResult, model);
	}
}
