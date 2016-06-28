package com.kl91.front.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.auth.Kl91SsoUser;
import com.kl91.domain.auth.Kl91SsoUtils;
import com.kl91.domain.company.Company;
import com.kl91.service.company.CompanyService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class FrontController extends BaseController{
	
	private static String MAIL_TEMPLATE = "kl91-repwd";
	private static Integer PRIORITY_DEFAULT = 0;
	
	@Resource
	private CompanyService companyService;
	
	@RequestMapping
	public void index(Map<String, Object> out) {
		SeoUtil.getInstance().buildSeo(out);
		out.put("yoxi", "yoxi");
	}

	@RequestMapping
	public void login(Map<String, Object> out,String username,Integer result,String url) {
		SeoUtil.getInstance().buildSeo("register", out);
		out.put("username", username);
		out.put("url", url);
		out.put("result", result);
	}
	
	@RequestMapping
	public ModelAndView doLogin(HttpServletRequest request,HttpServletResponse response,Map<String,Object> out,String username,String psw,String url){
		
		Kl91SsoUser ssoUser = Kl91SsoUtils.getInstance().validateUser(response,username, psw, null);
		
		if (ssoUser != null) {
			setSessionUser(request, ssoUser);
			if(StringUtils.isEmpty(url)){
				url = "/zhushou/index.htm";
			}
			return new ModelAndView("redirect:" + url);
		} else {
			out.put("url", url);
			out.put("result", 0);
			out.put("username", username);
			return new ModelAndView("forward:login.htm");
		}
		
	}
	
	@RequestMapping
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, String url, Map<String, Object> model)
			throws IOException {
		Kl91SsoUtils.getInstance().logout(request, response, null);
		return new ModelAndView("redirect:"+request.getHeader("referer"));
	}
	
	@RequestMapping
	public ModelAndView ssoUser(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String a, String p) {
		do {
			//验证用户登录信息是否正确
			Integer companyId = companyService.validateAccount(request,a, p);
			
			if(companyId==null){
				break;
			}
			
			Company company = companyService.queryById(companyId);
			
			Kl91SsoUser ssoUser = setCompanyToSsoUser(company);
			
			if(ssoUser==null){
				break;
			}
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
			
//			companyAccountService.updateLoginInfo(account);
			
			//保存6小时
			MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, ssoUser);
			
		} while (false);
		
		return new ModelAndView("/api/ssoUser");
	}
	
	@RequestMapping
	public ModelAndView ssoTicket(HttpServletRequest request, Map<String, Object> out, String t){
		Kl91SsoUser ssoUser = (Kl91SsoUser) MemcachedUtils.getInstance().getClient().get(t);
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
			ssoUser.setTicket(vticket);
			ssoUser.setKey(key);
			
			out.put("user", ssoUser);
		} while (false);
		return new ModelAndView("/api/ssoTicket");
	}
	
	private Kl91SsoUser setCompanyToSsoUser(Company company){
		Kl91SsoUser ssoUser = new Kl91SsoUser();
		ssoUser.setAccount(company.getAccount());
		ssoUser.setCompanyId(company.getId());
		ssoUser.setDomain(company.getDomain());
		ssoUser.setEmail(company.getEmail());
		return ssoUser;
	}

	@RequestMapping
	public void password(Map<String, Object> out) {
		SeoUtil.getInstance().buildSeo("password", out);
	}

	@RequestMapping
	public ModelAndView mail(HttpServletRequest request,Map<String, Object> out,String email) {
		Company company = companyService.queryByEmail(email);
		if(company != null){
			Map<String,Object> map = new HashMap<String,Object>();
			String url = AddressTool.getAddress("front")+"/changePwd.htm?socketKey=";
			Random random = new Random(1000);
			String urlValue="";
			try {
				urlValue = MD5.encode(String.valueOf(DateUtil.getSecTimeMillis())+random.nextInt(), MD5.LENGTH_32);
				MemcachedUtils.getInstance().getClient().set(urlValue, 600,company.getId());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(urlValue.length()>0){
				map.put("account", company.getAccount());
				map.put("url", url+urlValue);
				MailUtil.getInstance().sendMail("KL91.com密码找回", email,"zz91", MAIL_TEMPLATE, map, PRIORITY_DEFAULT);
			}
		}
		SeoUtil.getInstance().buildSeo("password", out);
		out.put("email", email);
		return null;
	}
	
	@RequestMapping
	public ModelAndView changePwd(Map<String,Object> out,String socketKey){
		do{
			if(StringUtils.isEmpty(socketKey)){
				break;
			}
			Integer companyId = (Integer) MemcachedUtils.getInstance().getClient().get(socketKey);
			if(companyId==null){
				break;
			}
			Company company = companyService.queryById(companyId);
			if(company==null){
				break;
			}
			out.put("company", company);
			out.put("socketKey", socketKey);
			SeoUtil.getInstance().buildSeo("password", out);
			return null;
		}while(false);
		return new ModelAndView("redirect:login.htm");
	}
	
	@RequestMapping
	public ModelAndView doChangePwd(Map<String,Object>out,String pwd,Integer id,String socketKey){
		do{
			Integer companyId = (Integer) MemcachedUtils.getInstance().getClient().get(socketKey);
			if(companyId!=id){
				break;
			}
			
			try {
				pwd = MD5.encode(pwd, MD5.LENGTH_32);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			Integer flag = companyService.updatePwdById(pwd, id);
			if(flag>0){
				MemcachedUtils.getInstance().getClient().delete(socketKey);
			}
			
			return alertMsgAndLocation(out, "密码重置成功,请重新登录", "login.htm");
		}while(false);
		return new ModelAndView("redirect:login.htm");
//		return new ModelAndView("redirect:login.htm");
	}
	
	@RequestMapping
	public void submitCallback(Map<String, Object> out,Integer id){
		out.put("id", id);
	}
}
