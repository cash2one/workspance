/**
 * 
 */
package com.zz91.crm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.crm.dto.ExtResult;
import com.zz91.util.auth.AuthConst;
import com.zz91.util.auth.AuthMenu;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;

/**
 * @author root
 *
 */
@Controller
public class RootController extends BaseController{

	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
		out.put("staffName", getCachedUser(request).getName());
		return null;
	}
	
	@RequestMapping
	public ModelAndView error(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView forbiden(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView login(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView welcome(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView checkuser(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response, String account, String password){
		SessionUser sessionUser = AuthUtils.getInstance().validateUser(response, account, password, AuthConst.PROJECT_CODE, AuthConst.PROJECT_PASSWORD);
		ExtResult result = new ExtResult();
		if(sessionUser!=null){
			setSessionUser(request, sessionUser);
			result.setSuccess(true);
		}else{
			result.setData("用户名或者密码写错了，检查下大小写是否都正确了，再试一次吧 :)");
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView logout(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response){
		AuthUtils.getInstance().logout(request, response, null);
		return new ModelAndView("redirect:login.htm");
	}
	
	
	@RequestMapping
	public ModelAndView mymenu(String parentCode, Map<String, Object> out, HttpServletRequest request){
		if(parentCode==null){
			parentCode="";
		}
		SessionUser sessionUser = getCachedUser(request);
		List<AuthMenu> list = AuthUtils.getInstance().queryMenuByParent(parentCode, AuthConst.PROJECT_CODE, sessionUser.getAccount());
		return printJson(list, out);
	}

}