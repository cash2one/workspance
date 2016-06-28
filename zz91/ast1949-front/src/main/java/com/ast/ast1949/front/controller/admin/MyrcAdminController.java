package com.ast.ast1949.front.controller.admin;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.zz91.util.auth.frontsso.SsoConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class MyrcAdminController extends BaseController{
	
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private AuthService authService;

	@RequestMapping
	public ModelAndView myrcLogin(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out,
			String account, String password){
		//查找用户信息
		//用ssoutil登录
		String ip=HttpUtils.getInstance().getIpAddr(request);
		String adminIp=ParamUtils.getInstance().getValue("baseConfig", "admin_ip");
		
		if(ip!=null && ip.contains(",")){
			ip=ip.split(",")[0].trim();
		}
		
		if(StringUtils.isEmpty(adminIp)){
			adminIp="61.234.184.252";
		}
		
		Set<String> ipSet = new HashSet<String>();
		if(adminIp.indexOf(",")!=-1){
			String[] ipArray = adminIp.split(",");
			for(String str :ipArray){
				ipSet.add(str);
			}
		}else{
			ipSet.add(adminIp);
		}
		if(!ipSet.contains(ip)){
			System.out.println("ip not contain"+":"+ip);
			return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
		}
//		SsoUser user=getCachedUser(request);
		
		SsoUtils.getInstance().logout(request, response, null);
		SsoUtils.getInstance().clearnSessionUser(request, null);
		
		// 清除改cookie
		String cookie = HttpUtils.getInstance().getCookie(request, AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
		authService.removeAuthLoginByCookie(cookie);
		
		String a ="";
		try {
			a = companyAccountService.validateUser(account, password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (AuthorizeException e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isEmpty(a)){
			System.out.println("account is null");
			return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
		}
		SsoUser ssoUser = companyAccountService.initSessionUser(a);
		
		if(ssoUser!=null){
			String key=UUID.randomUUID().toString();
			String ticket="";
			try {
				ticket = MD5.encode(a+password+key);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ssoUser.setTicket(ticket);
			HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
			MemcachedUtils.getInstance().getClient().set(ticket, 1*60*60, ssoUser);
//			SsoUtils.getInstance().setSessionUser(request, ssoUser, null);
			return new ModelAndView("redirect:"+AddressTool.getAddress("myrc"));
		}
		
		System.out.println("nothing is happen");
		return new ModelAndView("redirect:"+AddressTool.getAddress("front"));
	}
}
