/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 上午10:22:36
 */
package com.ast.ast1949.trade.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.exception.RegisterException;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.velocity.AddressTool;

@Deprecated
@Controller
public class RegisterController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private AuthService authService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;

	@RequestMapping
	public ModelAndView register(Map<String, Object> model, String refurl) {
		setSiteInfo(new PageHeadDTO(), model);
		model.put("title", "会员注册");
		model.put("refurl", refurl);
		return null;
	}

	/**
	 * 注册第一步
	 * */
//	@RequestMapping
//	public ModelAndView stepone(Map<String, Object> model, HttpServletRequest request,
//			String verifyCode,CompanyAccount companyAccount, 
//			String account, String email,
//			String password, String password2) throws IOException {
//		ExtResult result = new ExtResult();
//		Object object = getCachedSession(request, FrontConst.SESSION_CODE);
//		do {
//			// 检测验证码
//			if (object == null) {
//				result.setData("验证码获取失败！");
//				break;
//			}
//			if (!object.toString().equals(verifyCode)) {
//				result.setData("您输入的验证码有误！");
//				break;
//			}
//			
//			try {
//				String username = companyAccountService.registerUser(account, 
//						email, password,password2,
//						companyAccount, new Company(), null);
//				result.setData(username);
//				result.setSuccess(true);
//			} catch (RegisterException e) {
//				result.setData(e.getMessage());
//				break;
//			} catch (NoSuchAlgorithmException e) {
//				e.printStackTrace();
//			}
//			
//		} while (false);
//
//		return printJson(result, model);
//	}

	/**
	 * 获取验证码
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView getVerifyCode(Map<String, Object> model, HttpServletRequest request)
//			throws IOException {
//		ExtResult result = new ExtResult();
//		Object object = getCachedSession(request, FrontConst.SESSION_CODE);
//		if (object != null) {
//			result.setSuccess(true);
//			result.setData(object.toString());
//		}
//		return printJson(result, model);
//	}

	/**
	 * 注册第二步
	 * 
	 * @param out
	 * @param request
	 * @param response
	 * @param companyContactsDO
	 * @param company
	 * @param nation
	 * @param refurl
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView steptwo(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response, CompanyAccount companyAccount, 
			Company company, String refurl)
			throws IOException {

		ExtResult result = new ExtResult();

		do {
			if (StringUtils.isEmpty(companyAccount.getAccount())) {
				result.setData("对不起，您提交的帐号有误，请按提示填写注册信息！");
				break;
			}
			
			CompanyAccount existaccount = companyAccountService.queryAccountByAccount(companyAccount.getAccount());
			company.setId(existaccount.getCompanyId());
			companyService.updateCompanyByUser(company);
			companyAccount.setId(existaccount.getId());
			companyAccountService.updateAccountByUser(companyAccount);
			
			SsoUser ssoUser=null;
			try {
				ssoUser = SsoUtils.getInstance().validateUser(response, companyAccount.getAccount(), companyAccount.getPassword(), null, null);
			} catch (NoSuchAlgorithmException e) {
			} catch (AuthorizeException e) {
				
			}
			
			SsoUtils.getInstance().setSessionUser(request, ssoUser, null);
			
			setSessionUser(request, ssoUser);
			
			if (refurl != null && StringUtils.isNotEmpty(refurl)) {
				result.setData(refurl);
			} else {
				result.setData(AddressTool.getAddress("myrc") + "/myrc/index.htm");
			}
			result.setSuccess(true);
		}while(false);
			
		return printJson(result, model);
	}

	/**
	 * 验证邮箱是否已被注册
	 * 
	 * @param model
	 * @param email
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "checkUserMail.htm", method = RequestMethod.GET)
	public ModelAndView checkUserMail(Map<String, Object> model, String email) throws IOException {
		String result = "true";
		if (StringUtils.isNotEmpty(email)) {
			Integer i=authService.countUserByEmail(email);
			if (i!=null && i.intValue()>0) {
				result = "false";
			}
		}
		model.put("json", result);
		return new ModelAndView("json");
	}

	@RequestMapping(value = "preregister.htm")
	public ModelAndView preregister(Map<String, Object> out, 
			CompanyAccount companyAccount,Company company,
			String password, String vpassword,
			HttpServletRequest request, HttpServletResponse response,
			String verifyCodeKey, String verifyCode)
			throws Exception {
		
		ExtResult result = new ExtResult();
		do {
			//获取验证码
			String captcha = String.valueOf(MemcachedUtils.getInstance().getClient().get(verifyCodeKey));
			if(captcha==null){
				result.setData("验证码获取失败！");
				break;
			}
			
			if(!captcha.equals(verifyCode)){
				result.setData("您输入的验证码有错误！");
				break;
			}
			
			MemcachedUtils.getInstance().getClient().delete(verifyCodeKey);
			
			company.setRegfromCode("10031002");
			
			try {
				String username = companyAccountService.registerUser(companyAccount.getAccount(), 
						companyAccount.getEmail(), password, vpassword,
						companyAccount, company, null);
				
				if(StringUtils.isEmpty(username)){
					break;
				}
				
				SsoUser ssoUser= SsoUtils.getInstance().validateUser(response, companyAccount.getAccount(), password, null, null);
				
				setSessionUser(request, ssoUser);
				
				result.setData(username);
				result.setSuccess(true);
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser.getCompanyId(), null,
						"base_register", null, null, null));
			} catch (RegisterException e) {
				result.setData(e.getMessage());
				break;
			} catch (AuthorizeException e) {
			}
			
			// TODO 注册成功后发个邮件提醒一下用户，发送的内容需要重新确认
//			String title = "zz91再生网（原中国再生资源交易网） 注册信息";
//			StringBuffer sb = new StringBuffer();
//			sb.append(companyAccount.getContact()).append("，您好！");
//			sb.append("<br/><br/>");
//			sb
//					.append("欢迎加入 <a href='http://www.zz91.com' target='_blank'>zz91再生网（原中国再生资源交易网）</a> ");
//			sb.append("<br/><br/>");
//			sb
//					.append("以后您可以<a href='http://www.zz91.com' target='_blank'>登录zz91再生网（原中国再生资源交易网）</a>发布供求信息了");
//			sb.append("<br/><br/>");
//			sb.append("如果您对您的账户有任何问题或疑问，请联系我们：");
//			sb.append("<br/><br/>");
//			sb.append("http://www.zz91.com");
//
//			Email.getInstance().send(
//					ParamFacade.getInstance().getParamValue("site_email", "service_account"),
//					companyAccount.getEmail(),
//					ParamFacade.getInstance().getParamValue("site_email", "service_pwd"), title,
//					sb.toString());
			
		} while (false);
		return printJson(result, out);
	}
}
