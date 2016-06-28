/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-31.
 */
package com.zz91.ads.board.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.dto.ExtResult;
import com.zz91.util.auth.AuthConst;
import com.zz91.util.auth.AuthMenu;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Controller
public class RootController extends BaseController {
	
	final static Logger LOG = Logger.getLogger(RootController.class);
	
	@RequestMapping
	public void index(){
	}
	
	@RequestMapping
	public void welcome(){
	}
	
	@RequestMapping
	public void login(){
	}
	
	/**
	 * 退出
	 * @param request
	 */
	@RequestMapping
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
		AuthUtils.getInstance().logout(request, response, null);
		return new ModelAndView("redirect:login.htm");
	}
	
	@RequestMapping
	public ModelAndView mymenu(HttpServletRequest request, Map<String, Object> out,String parentCode){
		if(parentCode==null){
			parentCode="";
		}
		SessionUser sessionUser = getCachedUser(request);
		List<AuthMenu> list = AuthUtils.getInstance().queryMenuByParent(parentCode,AuthConst.PROJECT_CODE, sessionUser.getAccount());
		return printJson(list, out);
	}
	
	/**
	 * 登录
	 * @param request
	 * @param out
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping
	public ModelAndView authorize(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> out, String username, String password) {
		SessionUser sessionUser = AuthUtils.getInstance().validateUser(response, username, password, AuthConst.PROJECT_CODE, AuthConst.PROJECT_PASSWORD);
		ExtResult result = new ExtResult();
		if(sessionUser!=null){
			setSessionUser(request, sessionUser);
			result.setSuccess(true);
		}else{
			result.setData("用户名或者密码写错了，检查下大小写是否都正确了，再试一次吧 :)");
		}
		return printJson(result, out);
	}
	
	public static void main(String[] args) {
		try {
			for(int i=0;i<100;i++){
				Jsoup.parse(new URL("http://test.zz91.com:580/ads/show?p=4&keywords=PET"), 3000);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
