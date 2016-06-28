package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.exception.RegisterException;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

/**
 *	author:kongsj
 *	date:2013-5-6
 */
@Controller
public class MiniController extends BaseController {
	
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private CompanyService companyService;
	
	/**
	 * @param loginType作为登入的跳转标记,"1"为收藏的弹款登入信息不需要完善,
	 * @return
	 */
	@RequestMapping
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response, Map<String, Object> out,String destUrl, 
			String regfrom, String error, String errorCode,String loginType){
		out.put("loginType", loginType);
		out.put("destUrl", destUrl);
		out.put("regfrom", regfrom);
		out.put("error", error);
		out.put("errorTxt", AuthorizeException.getMessage(errorCode));
		out.put("account", HttpUtils.getInstance().getCookie(request,AstConst.COOKIE_ACCOUNT, AstConst.COOKIE_DOMAIN));
		out.put("referUrl", request.getHeader("referer"));
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView login_trust(HttpServletRequest request,HttpServletResponse response, Map<String, Object> out,String destUrl, 
			String regfrom, String error, String errorCode,String loginType){
		out.put("loginType", loginType);
		out.put("destUrl", destUrl);
		out.put("regfrom", regfrom);
		out.put("error", error);
		out.put("errorTxt", AuthorizeException.getMessage(errorCode));
		out.put("account", HttpUtils.getInstance().getCookie(request,AstConst.COOKIE_ACCOUNT, AstConst.COOKIE_DOMAIN));
		out.put("referUrl", request.getHeader("referer"));
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView reg(HttpServletRequest request,HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, String error, String errorCode,String loginType){
		out.put("loginType", loginType);
		out.put("destUrl", destUrl);
		out.put("regfrom", regfrom);
		out.put("error", error);
		out.put("errorTxt", AuthorizeException.getMessage(errorCode));
		out.put("account", HttpUtils.getInstance().getCookie(request,AstConst.COOKIE_ACCOUNT, AstConst.COOKIE_DOMAIN));
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView reg_stp2(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, Company company,
			CompanyAccount companyAccount, String password,
			String passwordConfirm, String verifyCodeKey, String verifyCode)
			        throws Exception  {

		out.put("regfrom", regfrom);
		out.put("destUrl", destUrl);
		out.put("tab", "register");

		String error = "2";
		do {

			String vcode = String.valueOf(SsoUtils.getInstance().getValue(request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode) || !verifyCode.equalsIgnoreCase(vcode)) {
				error = "3";
				break;
			}

			company.setRegfromCode(regfrom);

			try {
				String username = companyAccountService.registerUser(
						companyAccount.getAccount(), companyAccount.getEmail(),
						password, passwordConfirm, companyAccount, company,
						HttpUtils.getInstance().getIpAddr(request));

				if (StringUtils.isEmpty(username)) {
					break;
				}

				companyValidateService.sendValidateByEmail(companyAccount
						.getAccount(), companyAccount.getEmail());

				// 自动登录
				SsoUser ssoUser = null;
				try {
					ssoUser = SsoUtils.getInstance().validateUser(response,companyAccount.getAccount(), password, null,HttpUtils.getInstance().getIpAddr(request));
				} catch (AuthorizeException e) {
				}

				setSessionUser(request, ssoUser);
				
				// 登记登陆信息
				LogUtil.getInstance().log(""+ssoUser.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'mini-reg-login';account:'"+ssoUser.getAccount()+"'");

				// 增加积分
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser.getCompanyId(), null, "base_register", null,null, null));
			} catch (RegisterException e) {
				break;
			} catch (NoSuchAlgorithmException e) {
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

			out.put("account", companyAccount.getAccount());
			out.put("destUrl", destUrl);
			return new ModelAndView();
		} while (false);

		out.put("company", company);
		out.put("companyAccount", companyAccount);
		out.put("error", error);
		out.put("destUrl", destUrl);
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView("/mini/reg");
	}


	
	
	@RequestMapping
	public ModelAndView new_reg_stp2(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, Company company,
			CompanyAccount companyAccount, String password,String regiestType,
			String passwordConfirm, String verifyCodeKey, String verifyCode)
			        throws Exception  {

		if ("个人".equals(regiestType)) {
			companyAccount.setEmail("");
			company.setName("");
			company.setBusiness("");
			company.setAreaCode("");
		}
		
		
		out.put("regfrom", regfrom);
		out.put("destUrl", destUrl);
		out.put("tab", "register");

		String error = "2";
		do {

			String vcode = String.valueOf(SsoUtils.getInstance().getValue(request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode) || !verifyCode.equalsIgnoreCase(vcode)) {
				error = "3";
				break;
			}

			company.setRegfromCode(regfrom);
			String username = null;
			try {
				
				if(StringUtils.isEmpty(companyAccount.getAccount())){
					companyAccount.setAccount(companyAccount.getMobile());
				}
				
				if("个人".equals(regiestType)){
				username = companyAccountService.registerUserAccount(companyAccount.getAccount(), 
						password, passwordConfirm, companyAccount,
						company,HttpUtils.getInstance().getIpAddr(request));
				}else{
				username = companyAccountService.registerUser(
							companyAccount.getAccount(), companyAccount.getEmail(),
							password, passwordConfirm, companyAccount, company,
							HttpUtils.getInstance().getIpAddr(request));	
				}
				
				if (StringUtils.isEmpty(username)) {
					break;
				}

				companyValidateService.sendValidateByEmail(companyAccount
						.getAccount(), companyAccount.getEmail());

				// 自动登录
				SsoUser ssoUser = null;
				try {
					ssoUser = SsoUtils.getInstance().validateUser(response,
							companyAccount.getAccount(), password, null,
							HttpUtils.getInstance().getIpAddr(request));
				} catch (AuthorizeException e) {
				}

				setSessionUser(request, ssoUser);
				
				// 登记登陆信息
				LogUtil.getInstance().log(""+ssoUser.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'mini-reg-login';account:'"+ssoUser.getAccount()+"'");

				// 增加积分
				scoreChangeDetailsService
						.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser
								.getCompanyId(), null, "base_register", null,
								null, null));
			} catch (RegisterException e) {
				break;
			} catch (NoSuchAlgorithmException e) {
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

			out.put("account", companyAccount.getAccount());
			out.put("destUrl", destUrl);
			
			//注册成功，根据注册类型（公司/个人）转到不同的成功页面
			if ("个人".equals(regiestType)) {
				out.put("success", "1");
				out.put("data", "{}");
				out.put("destUrl", destUrl);
				out.put("regType", "1");
				out.put("account", companyAccount.getAccount());
				return new ModelAndView("/mini/reg_suc");
			} else {
				return new ModelAndView();
			}
			
			
		} while (false);
        out.put("regiestType", regiestType);
		out.put("company", company);
		out.put("companyAccount", companyAccount);
		out.put("error", error);
		out.put("destUrl", destUrl);
		out.put("loginType", "1");
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView("/mini/new_reg");
	}
	
	
	@RequestMapping
	public ModelAndView new_reg_stp2_trust(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, Company company,
			CompanyAccount companyAccount, String password,String regiestType,
			String passwordConfirm, String verifyCodeKey, String verifyCode)
			        throws Exception  {

		if ("个人".equals(regiestType)) {
			companyAccount.setEmail("");
			company.setName("");
			company.setBusiness("");
			company.setAreaCode("");
		}
		
		
		out.put("regfrom", regfrom);
		out.put("destUrl", destUrl);
		out.put("tab", "register");

		String error = "2";
		do {

			String vcode = String.valueOf(SsoUtils.getInstance().getValue(request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode) || !verifyCode.equalsIgnoreCase(vcode)) {
				error = "3";
				break;
			}

			company.setRegfromCode(regfrom);
			String username = null;
			try {
				
				if(StringUtils.isEmpty(companyAccount.getAccount())){
					companyAccount.setAccount(companyAccount.getMobile());
				}
				
				if("个人".equals(regiestType)){
				username = companyAccountService.registerUserAccount(companyAccount.getAccount(), 
						password, passwordConfirm, companyAccount,
						company,HttpUtils.getInstance().getIpAddr(request));
				}else{
				username = companyAccountService.registerUser(
							companyAccount.getAccount(), companyAccount.getEmail(),
							password, passwordConfirm, companyAccount, company,
							HttpUtils.getInstance().getIpAddr(request));	
				}
				
				if (StringUtils.isEmpty(username)) {
					break;
				}

				companyValidateService.sendValidateByEmail(companyAccount
						.getAccount(), companyAccount.getEmail());

				// 自动登录
				SsoUser ssoUser = null;
				try {
					ssoUser = SsoUtils.getInstance().validateUser(response,
							companyAccount.getAccount(), password, null,
							HttpUtils.getInstance().getIpAddr(request));
				} catch (AuthorizeException e) {
				}

				setSessionUser(request, ssoUser);
				
				// 登记登陆信息
				LogUtil.getInstance().log(""+ssoUser.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'mini-reg-login';account:'"+ssoUser.getAccount()+"'");

				// 增加积分
				scoreChangeDetailsService
						.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser
								.getCompanyId(), null, "base_register", null,
								null, null));
			} catch (RegisterException e) {
				break;
			} catch (NoSuchAlgorithmException e) {
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

			out.put("account", companyAccount.getAccount());
			out.put("destUrl", destUrl);
			
			//注册成功，根据注册类型（公司/个人）转到不同的成功页面
			if ("个人".equals(regiestType)) {
				out.put("success", "1");
				out.put("data", "{}");
				out.put("destUrl", destUrl);
				out.put("regType", "1");
				out.put("account", companyAccount.getAccount());
				return new ModelAndView("/mini/reg_suc_trust");
			} else {
				return new ModelAndView();
			}
			
			
		} while (false);
        out.put("regiestType", regiestType);
		out.put("company", company);
		out.put("companyAccount", companyAccount);
		out.put("error", error);
		out.put("destUrl", destUrl);
		out.put("loginType", "1");
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView("/mini/new_reg_trust");
	}
	
	
//	@RequestMapping
//	public ModelAndView reg_suc(Map<String, Object> out,String destUrl){
//		out.put("destUrl", destUrl);
//		return new ModelAndView();
//	}
	/**
	 * 验证公司信息是否完善
	 * @param request
	 * @param loginType作为登入的跳转标记,"1"为收藏的弹款登入信息不需要完善,
	 * @return
	 */
	@RequestMapping
	public ModelAndView doLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String account, String password, String vcode,
			String t, String regfrom, String forUrl,String loginType) {
		// TODO 简版登录
		HttpUtils.getInstance().setCookie(response, AstConst.COOKIE_ACCOUNT,
				account, AstConst.COOKIE_DOMAIN, AstConst.COOKIE_AGE);
		SsoUser user = null;
		try {
			user = SsoUtils.getInstance().validateUser(response, account,
					password, AstConst.COOKIE_AGE,
					HttpUtils.getInstance().getIpAddr(request));
		} catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			out.put("errorCode", e.getMessage());
		} catch (IOException e) {
		}
		

		//1为收藏登入不需要弹款完善
		if(StringUtils.isNotEmpty(loginType) && loginType.equals("1")){
			if (user != null) {
				setSessionUser(request, user);
				if (StringUtils.isNotEmpty(destUrl)) {
					return new ModelAndView("redirect:" + destUrl);
				} else {
					return new ModelAndView("submitCallback");
				}
			}
		}else{
			if(user!=null){
				setSessionUser(request, user);
				
				//登陆信息记录
				LogUtil.getInstance().log(""+user.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'mini-login';account:'"+user.getAccount()+"'");
				
				//判断信息是否是完善的不完善的需要完善的提示
				String checkInfo= companyService.validateCompanyInfo(user);
				if(checkInfo!=null && checkInfo=="1"){
					out.put("regfrom", regfrom);
					out.put("error", "1");
					out.put("destUrl", destUrl);
					out.put("success", "1");
					out.put("data", "{}");
					return new ModelAndView("/mini/showMessage");
				}
				if (StringUtils.isNotEmpty(destUrl)) {
					return new ModelAndView("redirect:" + destUrl);
				} else {
					return new ModelAndView("submitCallback");
				}
			}
			
		}
		
		out.put("regfrom", regfrom);
		out.put("destUrl", destUrl);
		// TODO miniLogin 处理错误信息
		out.put("error", "1");
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView("redirect:/mini/login.htm");
	}
	

	@RequestMapping
	public ModelAndView showMessage(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,String destUrl) {
		
		SsoUser ssoUser=getCachedUser(request);
		if(ssoUser==null){
			return new ModelAndView("redirect:/mini/login.htm");
		}
	
		out.put("destUrl", destUrl);
		out.put("success", "1");
		out.put("data", "{}");
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView do_reg_suc(HttpServletRequest request,Map<String, Object>out,String industryCode,String serviceCode,String address,String addressFix,String introduction,String destUrl){
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
		out.put("destUrl", destUrl);
		out.put("success", "1");
		out.put("data", "{}");
		
		return new ModelAndView("/mini/reg_suc");
	}

	@RequestMapping
	public ModelAndView clause(){/**
		 * 验证公司信息是否完善
		 * @param request
		 * @param out
		 * @return
		 */
		return null;
	}
	
	@RequestMapping
	public ModelAndView new_reg(HttpServletRequest request,HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, String error, String errorCode,String loginType){
		out.put("loginType", loginType);
		out.put("destUrl", destUrl);
		out.put("regfrom", regfrom);
		out.put("error", error);
		out.put("errorTxt", AuthorizeException.getMessage(errorCode));
		out.put("account", HttpUtils.getInstance().getCookie(request,AstConst.COOKIE_ACCOUNT, AstConst.COOKIE_DOMAIN));
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView new_reg_trust(HttpServletRequest request,HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, String error, String errorCode,String loginType){
		out.put("loginType", loginType);
		out.put("destUrl", destUrl);
		out.put("regfrom", regfrom);
		out.put("error", error);
		out.put("errorTxt", AuthorizeException.getMessage(errorCode));
		out.put("account", HttpUtils.getInstance().getCookie(request,AstConst.COOKIE_ACCOUNT, AstConst.COOKIE_DOMAIN));
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView();
	}
}
