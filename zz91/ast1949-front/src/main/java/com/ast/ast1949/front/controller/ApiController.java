/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.service.company.CompanyAccountService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-4
 */
@Deprecated
@Controller
public class ApiController extends BaseController {
	
	@Resource
	private CompanyAccountService companyAccountService;
	
	@RequestMapping
	public ModelAndView ssoUser(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String a, String p) {
		
		do {
			//验证用户登录信息是否正确
			String account=null;
			try {
				account = companyAccountService.validateUser(a, p);
			} catch (NoSuchAlgorithmException e1) {
			} catch (UnsupportedEncodingException e1) {
			} catch (AuthorizeException e1) {
			}
			
			if(StringUtils.isEmpty(account)){
				break;
			}
			
			SsoUser ssoUser = companyAccountService.initSessionUser(account);
			
			if(ssoUser==null){
				break;
			}
//			//验证用户是否允许使用该系统
//			if(!bsService.allowAccess(account, pc)){
//				break;
//			}
			
			//生成ticket
			String key=UUID.randomUUID().toString();
			String ticket=null;
			try {
				ticket = MD5.encode(a+p+key);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ssoUser.setKey(key);
			ssoUser.setTicket(ticket);
			
			out.put("user", ssoUser);
			companyAccountService.updateLoginInfo(account, null);
			LogUtil.getInstance().log(String.valueOf(ssoUser.getCompanyId()), "login");
			//保存6小时
			MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, ssoUser);
		} while (false);
		
		return new ModelAndView("/api/ssoUser");
	}
	
	@RequestMapping
	public ModelAndView ssoTicket(HttpServletRequest request, Map<String, Object> out, String t){
//		MemcachedUtils.getInstance().getClient(DesktopConst.CACHE_ZZWORK);
		//TODO 可以换成数据库方式实现
		SsoUser ssoUser = (SsoUser) MemcachedUtils.getInstance().getClient().get(t);
		do {
			if(ssoUser==null){
				break;
			}
			
			String key = UUID.randomUUID().toString();
			String vticket = null;
			try {
				vticket = MD5.encode(t+key);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ssoUser.setVticket(vticket);
			ssoUser.setKey(key);
			
			out.put("user", ssoUser);
		} while (false);
		return null;
	}
	
	@RequestMapping
	public ModelAndView ssoLogout(HttpServletRequest request, Map<String, Object> out, String t) throws IOException{
		MemcachedUtils.getInstance().getClient().delete(t);
		return printJson("{result:true}", out);
	}
	
}
