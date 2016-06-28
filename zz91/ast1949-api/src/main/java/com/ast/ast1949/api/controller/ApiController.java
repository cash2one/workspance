/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package com.ast.ast1949.api.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-4
 */
@Controller
public class ApiController extends BaseController {
	
	@Resource
	private CompanyAccountService companyAccountService;
	
	@Deprecated
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
	
	@Deprecated
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
	
	@RequestMapping
	public ModelAndView validationUser(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String a, String p, String ip) throws IOException {
		
		Map<String, Object> result=new HashMap<String, Object>();
		
		do {
			//验证用户登录信息是否正确
			String account=null;
			try {
				account = companyAccountService.validateUser(a, p);
			} catch (NoSuchAlgorithmException e1) {
			} catch (UnsupportedEncodingException e1) {
			} catch (AuthorizeException e1) {
				result.put("error", e1.getMessage());
			}
			
			if(StringUtils.isEmpty(account)){
				break;
			}
			
			SsoUser ssoUser = companyAccountService.initSessionUser(account);
			
			if(ssoUser==null){
				result.put("error", AuthorizeException.ERROR_SERVER);
				break;
			}
			
			CompanyAccount acc = companyAccountService.queryAccountByAccount(account);
			if (acc!=null&&acc.getGmtLastLogin()!=null&&"10051000".equals(ssoUser.getMembershipCode())) {
				Integer limitDay = 0;
				try {
					limitDay = DateUtil.getIntervalDays(new Date(), acc.getGmtLastLogin());
				} catch (ParseException e) {
				}
				if(limitDay > 180){
					result.put("error", "validate:"+acc.getAccount());
					ssoUser = null;
					break;
				}
				
			}
			
			
			//生成ticket
			String key=UUID.randomUUID().toString();
			String ticket=null;
			try {
				ticket = MD5.encode(a+p+key); // TODO 增加私钥以保证安全
			} catch (NoSuchAlgorithmException e) {
			} catch (UnsupportedEncodingException e) {
			}
			
			result.put("key", key);
			result.put("ticket", ticket);
			
			companyAccountService.updateLoginInfo(account, ip);
			
			LogUtil.getInstance().log(String.valueOf(ssoUser.getCompanyId()), "login", ip);
			
			ssoUser.setTicket(ticket);
			//保存6小时
			MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, ssoUser);
			result.put("ssoUser", ssoUser);
		} while (false);
		
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView validationTicket(HttpServletRequest request, Map<String, Object> out, String t) throws IOException{
		
		Map<String, Object> result=new HashMap<String, Object>();
		if(StringUtils.isEmpty(t)){
			return printJson(result, out);
		}
		
		SsoUser ssoUser = (SsoUser) MemcachedUtils.getInstance().getClient().get(t);
		
		do {
			if(ssoUser==null){
				break;
			}
			
			String key = UUID.randomUUID().toString();
			String vticket = null;
			try {
				vticket = MD5.encode(key+t);
			} catch (NoSuchAlgorithmException e) {
			} catch (UnsupportedEncodingException e) {
			}
			result.put("vticket", vticket);
			result.put("key", key);
			
			result.put("ssoUser", ssoUser);
			
		} while (false);
		return printJson(result, out);
	}
	
}
