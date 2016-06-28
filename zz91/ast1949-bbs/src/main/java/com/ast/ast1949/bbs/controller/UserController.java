/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-16
 */
package com.ast.ast1949.bbs.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.exception.AuthorizeException;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Controller
public class UserController extends BaseController {
	
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;

	@RequestMapping
	public ModelAndView checkuser(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response, String username, String password, String randCode,
			String randCodeKey, String url, String cookieMaxAge) throws IOException {
		ExtResult extResult = new ExtResult();
		
		SsoUser ssoUser =null;
		try {
			ssoUser = SsoUtils.getInstance().validateUser(response, username, password, AstConst.COOKIE_AGE, null);
		} catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			extResult.setData(AuthorizeException.getMessage(e.getMessage()));
		}
		if(ssoUser!=null){
			setSessionUser(request, ssoUser);
			extResult.setSuccess(true);
			if (StringUtils.isEmpty(url)) {
				extResult.setData(request.getContextPath() + "/index.htm");
			} else {
				extResult.setData(url);
			}
			//检测是否有bbs_user_profiler数据，没有的话增加上去
			
			if (!bbsUserProfilerService.isProfilerExist(ssoUser.getAccount())) {
				bbsUserProfilerService.createEmptyProfilerByUser(ssoUser.getAccount());
			}
			
		}else{
			extResult.setData("用户名或者密码写错了，检查下大小写是否都正确了，再试一次吧");
		}
		
		return printJson(extResult, model);
	}

	@RequestMapping
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,
			String url, Map<String, Object> model) throws IOException {
		SsoUtils.getInstance().logout(request, response, null);
		ExtResult extResult = new ExtResult();
		extResult.setSuccess(true);
		if (StringUtils.isEmpty(url)) {
			extResult.setData("index.htm");
		} else {
			extResult.setData(url);
		}
		return printJson(extResult, model);
	}
	
	/**
	 * 登录用户Ip判断
	 * @param configIp 系统允许访问的ip
	 * @param clientIp 客户端ip
	 * @return
	 */
	public boolean allowLogin(String[] configIp,String clientIp){
		boolean result = false;
		if(configIp.length>0){
			for (String i : configIp) {
				if(i!=null&&i.length()>0){
					if(result==false&&i.equals(clientIp)){
						result = true;
					}
				}
			}
		}
		
		return result;
	}
	
	public ModelAndView favorites(HttpServletRequest request){
		return new ModelAndView();
	}
}
