package com.ast.feiliao91.www.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoConst;
import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * @author shiqp
 * @date 2016-01-09
 */
@Controller
public class RegisterController extends BaseController{
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private CompanyAccountService companyAccountService;
	/**
	 * 注册第一步
	 */
	@RequestMapping
	public void step1(Map<String,Object> out){
		SeoUtil.getInstance().buildSeo("register_step1",out);
	}
	
	/**
	 * 注册第二步
	 * @return
	 */
	@RequestMapping
	public void step2(Map<String,Object> out,  String account){
//		account = "13456854238";
		SeoUtil.getInstance().buildSeo("register_step2",out);
		out.put("account", account);
	}
	
	@RequestMapping
	public ModelAndView doStep2(Map<String,Object> out, HttpServletRequest request, HttpServletResponse response, CompanyInfo info, CompanyAccount account){
		//获取注册用户ip 
		account.setIp(HttpUtils.getInstance().getIpAddr(request));
		Integer cid = companyInfoService.insertCompanyInfo(info);
		if(cid > 0){
			//帐号信息处理
			account.setCompanyId(cid);
			Integer aid = companyAccountService.insertAccount(account);
			if(aid < 1){
				return new ModelAndView("redirect:"+"/register/step1.htm");
			}
		}
		//注册成功后，保存登录信息
		String ticket = null;
		try {
			 // TODO 增加私钥以保证安全
			ticket = MD5.encode(account.getAccount()+account.getPassword()+account.getIp());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//登录信息
		SsoUser ssoUser = new SsoUser();
		ssoUser.setAccount(account.getAccount());
		ssoUser.setAccountId(account.getId());
		ssoUser.setCompanyId(account.getCompanyId());
		HttpUtils.getInstance().setCookie(response,SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
		//6小时内有效
		MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, ssoUser);
		setSessionUser(request, ssoUser);
		return new ModelAndView("redirect:"+"/register/step3.htm");
	}
	
	/**
	 * 注册第三步
	 */
	@RequestMapping
	public ModelAndView step3(Map<String,Object> out, HttpServletRequest request, HttpServletResponse response){
		SsoUser user = getCachedUser(request);
		//公司帐号信息
		CompanyAccount account = companyAccountService.queryAccountByAccount(user.getAccount());
		//公司信息
		CompanyInfo info = companyInfoService.queryInfoByid(user.getCompanyId());
		out.put("account", account.getAccount());
		out.put("mobile", account.getMobile());
		out.put("name", info.getName());
		SeoUtil.getInstance().buildSeo("register_suc",out);
		return new ModelAndView();
	}
	
	/**
	 * 验证手机验证码
	 * @param mobile
	 * @param vcode
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView validateByMobile(Map<String,Object> out, String mobile, String vcode) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		//是否验证成功标志,默认为失败
		boolean isTrue = false;
		Integer i =companyValidateService.validateByMobile(mobile, vcode);
		if(i > 0){
			isTrue = true;
		}
		map.put("isTrue", isTrue);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView validateInfo(Map<String,Object> out, String mobile, String email) throws IOException{
		Map<String,Integer> map = new HashMap<String,Integer>();
		Integer i = companyAccountService.hasAM(mobile, email);
		map.put("info", i);
		return printJson(map, out);
	}

}
