/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 上午10:22:36
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.exception.RegisterException;
import com.ast.ast1949.front.util.FrontConst;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.auth.frontsso.SsoConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

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
	@Resource
	private CompanyValidateService companyValidateService;
	
	final static String TOKEN_KEY="regist_token";
	
	Logger LOG=Logger.getLogger(RegisterController.class);
	
	@RequestMapping
	public ModelAndView register(Map<String, Object> model, String refurl) {
//		setSiteInfo(new PageHeadDTO(), model);
//		model.put("title", "会员注册");
//		model.put("refurl", refurl);
		
		return new ModelAndView("redirect:http://www.zz91.com/member/join_.asp?s=40");
	}
	
	@RequestMapping
	public ModelAndView register_stp1(HttpServletRequest request, Map<String, Object> out, 
			HttpServletResponse response, String regfromCode, String refurl) {
//		return new ModelAndView ("redirect:http://pyapp.zz91.com/zz91register/");
        
		out.put("refurl", refurl);
		out.put("regfromCode", regfromCode);
		//crsf攻击,生成标记
		HttpUtils.getInstance().setCookie(response,"csrfKey",HttpUtils.getInstance().getIpAddr(request),SsoConst.SSO_DOMAIN, null);
		//设置Token，用于防止表单重复提交
		String token=UUID.randomUUID().toString();
		SsoUtils.getInstance().setValue(request, response, TOKEN_KEY, token);
		out.put("token", token);
		SeoUtil.getInstance().buildSeo(out);
		
		PageCacheUtil.setNoCache(response);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView reg(HttpServletRequest request, Map<String, Object> out, 
			HttpServletResponse response, String regfromCode, String refurl) {
		out.put("refurl", refurl);
		out.put("regfromCode", regfromCode);
		//设置Token，用于防止表单重复提交
		String token=UUID.randomUUID().toString();
		SsoUtils.getInstance().setValue(request, response, TOKEN_KEY, token);
		out.put("token", token);
		
		SeoUtil.getInstance().buildSeo(out);
		
		PageCacheUtil.setNoCache(response);
		return new ModelAndView();
	}
	
	/**
	 *  注册页面第一步确认，第二步跳转
	 * @param request
	 * @param response
	 * @param out
	 * @param refurl
	 * @param token
	 * @param verifyCode
	 * @param password
	 * @param passwordConfirm
	 * @param company
	 * @param companyAccount
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping
	public ModelAndView register_stp2(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out)throws Exception {
		String refurl=request.getParameter("refurl");
		String token=request.getParameter("token");
//		String verifyCode=request.getParameter("verifyCode");
		String password=request.getParameter("password");
		String passwordConfirm=request.getParameter("passwordConfirm");
		String regiestType=request.getParameter("regiestType");
		Company company=new Company();
		String name=request.getParameter("name");
		company.setName(name);
		String business=request.getParameter("business");
		company.setBusiness(business);
		String areaCode=request.getParameter("areaCode");
		company.setAreaCode(areaCode);
		CompanyAccount companyAccount=new CompanyAccount();
		companyAccount.setRegiestType(regiestType);
		companyAccount.setPassword(password);
		String mobile=request.getParameter("mobile");
		companyAccount.setMobile(mobile);
		String contact=request.getParameter("contact");
		companyAccount.setContact(contact);
		String sex=request.getParameter("sex");
		companyAccount.setSex(sex);
		String email=request.getParameter("email");
		companyAccount.setEmail(email);
		if ("个人".equals(regiestType)) {
			companyAccount.setEmail("");
			company.setName("");
			company.setBusiness("");
			company.setAreaCode("");
		}
		//检测同一个IP是否频繁注册?
		StringBuffer sb=new StringBuffer();
		sb.append("REGIST REBOT-doreg-01: ")
			.append("from url:")
			.append(request.getHeader("referer"))
			.append(" ip:")
			.append(HttpUtils.getInstance().getIpAddr(request));
		
		LOG.debug(sb.toString());
//		ExtResult result = new ExtResult();
		do {
			// 防止csrf攻击
			String random = HttpUtils.getInstance().getCookie(request, "csrfKey", SsoConst.SSO_DOMAIN);
			if (StringUtils.isEmpty(random)) {
				break;
			}
			HttpUtils.getInstance().setCookie(response,"csrfKey",null,SsoConst.SSO_DOMAIN, 0);
			//验证Token，防止表单重复提交
			if(StringUtils.isEmpty(token)){
				out.put("error", "0");
				break;
			}
			String stoken = String.valueOf(SsoUtils.getInstance().getValue(request, response, TOKEN_KEY));
			SsoUtils.getInstance().remove(request, TOKEN_KEY);
			
			if(!token.equals(stoken)){
				out.put("error", "0");
				break;
			}
			//处理注册信息
			String account=null;
			try {
				if(StringUtils.isEmpty(companyAccount.getAccount())){
					companyAccount.setAccount(companyAccount.getMobile());
				}
				if ("个人".equals(regiestType)) {
					//注册成功，根据注册类型（公司/个人）转到不同的成功页面
					account=companyAccountService.registerUserAccount(companyAccount.getAccount(), password, passwordConfirm, companyAccount, company,HttpUtils.getInstance().getIpAddr(request));
				}else{
					account=companyAccountService.registerUser(companyAccount.getAccount(), companyAccount.getEmail(), password, passwordConfirm, companyAccount,company,HttpUtils.getInstance().getIpAddr(request));
				}
			}catch (Exception e) {
				account = null;
			}
			if(StringUtils.isEmpty(account)){
				break;
			}
			
			//地址地区中文
			String address = "";
			if(company!=null&&StringUtils.isNotEmpty(company.getAreaCode())){
				String code = company.getAreaCode();
				if(company.getAreaCode().length()>=8){
					address +=CategoryFacade.getInstance().getValue(code.substring(0,8));
				}
				if(company.getAreaCode().length()>=12){
					address +=CategoryFacade.getInstance().getValue(code.substring(0,12));
				}
				if(company.getAreaCode().length()>=16){
					address +=CategoryFacade.getInstance().getValue(code.substring(0,16));
				}
				out.put("addressLabel", address);
			}
			
			//注册成功后发送激活邮件
			companyValidateService.sendValidateByEmail(companyAccount.getAccount(), companyAccount.getEmail());
			//自动登录
			SsoUser ssoUser=null;
			try {
				ssoUser = SsoUtils.getInstance().validateUser(response,companyAccount.getAccount(), password, null, HttpUtils.getInstance().getIpAddr(request));
			} catch (AuthorizeException e) {
			} catch (NoSuchAlgorithmException e) {
			} catch (IOException e) {
			}
			setSessionUser(request, ssoUser);
			//加积分
			scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser.getCompanyId(), null, "base_register", null,null, null));
			// 帐号信息
			out.put("account", companyAccount);
			out.put("company", company);
			//注册成功，根据注册类型（公司/个人）转到不同的成功页面
			if ("个人".equals(regiestType)) {
				return new ModelAndView("/register/individual_reg_suc");
			} else {
				return new ModelAndView();
			}
		} while (false);
		out.put("account", companyAccount);
		out.put("company", company);
		out.put("regiestType", regiestType);
		//地址地区中文
		String address = "";
		if(company!=null&&StringUtils.isNotEmpty(company.getAreaCode())){
			String code = company.getAreaCode();
			if(company.getAreaCode().length()>=4){
				address +=CategoryFacade.getInstance().getValue(code.substring(0,8));
			}
			if(company.getAreaCode().length()>=8){
				address +=">"+CategoryFacade.getInstance().getValue(code.substring(0,8));
			}
			if(company.getAreaCode().length()>=12){
				address +=">"+CategoryFacade.getInstance().getValue(code.substring(0,12));
			}
			if(company.getAreaCode().length()>=16){
				address +=">"+CategoryFacade.getInstance().getValue(code.substring(0,16));
			}
			out.put("addressLabel", address);
		}
		out.put("refurl", refurl);
		out.put("regfromCode", company.getRegfromCode());
		//设置Token，用于防止表单重复提交
		token=UUID.randomUUID().toString();
		SsoUtils.getInstance().setValue(request, response, TOKEN_KEY, token);
		out.put("token", token);
		return new ModelAndView("/register/register_stp1"); //决定回到原来的页面
	}
	
	@RequestMapping
	public ModelAndView register_suc(HttpServletRequest request,Map<String, Object>out,String industryCode,String serviceCode,String address,String addressFix,String introduction){
		SsoUser ssoUser = getCachedUser(request);
		Company companyU = companyService.queryCompanyById(ssoUser.getCompanyId());
		do {
			if(companyU==null){
				break;
			}
			companyU.setAddress(addressFix+address);
			companyU.setIndustryCode(industryCode);
			// 验证公司简介
			if(StringUtils.isNotEmpty(introduction)){
				companyU.setIntroduction(introduction);
			}else if(StringUtils.isEmpty(introduction)&&StringUtils.isNotEmpty(companyU.getBusiness())){
				companyU.setIntroduction(companyU.getBusiness());
			}
			companyU.setServiceCode(serviceCode);
			companyService.updateCompanyByUser(companyU);
		} while (false);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doValidateByEmail(HttpServletRequest request, Map<String, Object> out, String k, String v){
		
		if(!companyValidateService.validateByEmail(k, v)){
			out.put("error", "1");
		}
		
		return null;
	}

	/**
	 * 注册第一步
	 * @throws Exception 
	 * */
	@Deprecated
	@RequestMapping
	public ModelAndView stepone(Map<String, Object> model, HttpServletRequest request,
			String verifyCode, String verifyCodeKey,CompanyAccount companyAccount, 
			String account, String email,
			String password, String password2, String regfromCode) throws Exception  {
		
//		String url = request.getHeader("referer");
//		String id = request.
		
		StringBuffer sb=new StringBuffer();
		sb.append("REGIST REBOT-stepone: ")
			.append("from url:")
			.append(request.getHeader("referer"))
			.append(" ip:")
			.append(HttpUtils.getInstance().getIpAddr(request))
			.append(" v:"+verifyCode);
		
		LOG.error(sb.toString());
		
		ExtResult result = new ExtResult();
//		Object object = getCachedSession(request, FrontConst.SESSION_CODE);
		do {
			// 检测验证码
//			if (object == null) {
//				result.setData("验证码获取失败！");
//				break;
//			}
//			if (!object.toString().equals(verifyCode)) {
//				result.setData("您输入的验证码有误！");
//				break;
//			}
			
			//获取验证码
			
			String captcha = (String) MemcachedUtils.getInstance().getClient().get("yzm"+verifyCodeKey);
			MemcachedUtils.getInstance().getClient().delete("yzm"+verifyCodeKey);
			
			if(captcha==null || "null".equals(captcha)){
				result.setData("验证码获取失败！");
				break;
			}
			
			if(!captcha.equalsIgnoreCase(verifyCode)){
				result.setData("您输入的验证码有错误！"+captcha+":"+verifyCode+":"+verifyCodeKey+":"+captcha.length());
				break;
			}
			
			
			Company company=new Company();
			company.setRegfromCode("10031025");
			
//			try {
				String username=null;
				try {
					username = companyAccountService.registerUser(account, 
							email, password,password2,
							companyAccount, company, HttpUtils.getInstance().getIpAddr(request));
				} catch (NoSuchAlgorithmException e) {
				} catch (RegisterException e) {
				}
				
				result.setData(username);
			
				result.setSuccess(true);
//			} catch (RegisterException e) {
//				result.setData(e.getMessage());
//				break;
//			} catch (NoSuchAlgorithmException e) {
//				e.printStackTrace();
//			}
			
		} while (false);

		return printJson(result, model);
	}

	/**
	 * 获取验证码
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView getVerifyCode(Map<String, Object> model, HttpServletRequest request)
			throws IOException {
		ExtResult result = new ExtResult();
		Object object = getCachedSession(request, FrontConst.SESSION_CODE);
		if (object != null) {
			result.setSuccess(true);
			result.setData(object.toString());
		}
		return printJson(result, model);
	}

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
	@Deprecated
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
				ssoUser = SsoUtils.getInstance().validateUser(response, companyAccount.getAccount(), companyAccount.getPassword(), null, HttpUtils.getInstance().getIpAddr(request));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
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
	public ModelAndView checkUserMail(Map<String, Object> model, String email) throws RegisterException, NoSuchAlgorithmException, SAXException, ParserConfigurationException, IOException  {
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

	@Deprecated
	@RequestMapping(value = "preregister.htm")
	public ModelAndView preregister(Map<String, Object> out, 
			CompanyAccount companyAccount,Company company,
			String password, String vpassword,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExtResult result = new ExtResult();
		do {
			
			try {
				String username = companyAccountService.registerUser(companyAccount.getAccount(), 
						companyAccount.getEmail(), password, vpassword,
						companyAccount, company, null);
				
				if(StringUtils.isEmpty(username)){
					break;
				}
				
				SsoUser ssoUser=null;
				try {
					ssoUser = SsoUtils.getInstance().validateUser(response, companyAccount.getAccount(), password, null, HttpUtils.getInstance().getIpAddr(request));
				} catch (AuthorizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				setSessionUser(request, ssoUser);
				
				result.setData(username);
				result.setSuccess(true);
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser.getCompanyId(), null,
						"base_register", null, null, null));
			} catch (RegisterException e) {
				result.setData(e.getMessage());
				break;
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
	
	@RequestMapping
	public ModelAndView firstview(Map<String , Object> out){
		return null;
	}
	@RequestMapping
	public ModelAndView validateCode(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response,String verifyCode) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isTrue = true;
		// 验证验证码，防止机器注册
		String vcode = String.valueOf(SsoUtils.getInstance().getValue(request, response, AstConst.VALIDATE_CODE_KEY));
		SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
		if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode) || !verifyCode.equalsIgnoreCase(vcode)) {
			isTrue = false;
		}
		map.put("isTrue", isTrue);
		return printJson(map, out);
	}
}
