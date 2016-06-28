package com.ast.ast1949.sample.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
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

@Controller
public class MiniController extends BaseController{
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private CompanyService companyService;
	/**
	 * 
	 * @param request
	 * @param out
	 * @param sampleId
	 * @param error
	 * @param nyType  登录完跳转到哪个页面 1:在线拿样 2:电话拿样页面  
	 * @param vip  卖家高会和来电宝客户标志的标志  1: 直接登录后跳转到订单页面
	 * @return
	 */
	@RequestMapping
	public ModelAndView nylogin(HttpServletRequest request,Map<String, Object>out,String sampleId, String error, String nyType,String vip){
		out.put("sampleId", sampleId);
		out.put("error", error);
		out.put("nyType", nyType);
		out.put("vip", vip);
		return new ModelAndView();
		
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @param destUrl
	 * @param account
	 * @param password
	 * @param vcode
	 * @param vip 卖家高会和来电宝客户标志的标志  1: 直接登录后跳转到订单页面
	 * @param regfrom
	 * @param sampleId
	 * @param nyType  登录完跳转到哪个页面 1:在线拿样 2:电话拿样页面  
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView doNyLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,String destUrl, String account, String password, String vcode,
			String vip, String regfrom,String sampleId,String nyType)throws Exception {
		// TODO 简版登录
		ExtResult result = new ExtResult();
		Map<String, String> map=new HashMap<String, String>();
		HttpUtils.getInstance().setCookie(response, AstConst.COOKIE_ACCOUNT,
				account, AstConst.COOKIE_DOMAIN, AstConst.COOKIE_AGE);
		SsoUser user = null;
		out.put("sampleId", sampleId);
		try {
			user = SsoUtils.getInstance().validateUser(response, account,
					password, AstConst.COOKIE_AGE,
					HttpUtils.getInstance().getIpAddr(request));
		} catch (HttpException e) {
		} catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			out.put("errorCode", e.getMessage());
		} catch (IOException e) {
		}
		if(user!=null){	
			//Integer id=Integer.valueOf(sampleId);
			result.setSuccess(true);
			result.setData(sampleId);
			//result.setData(Success);
			//return new ModelAndView("redirect:/apply_ny.htm?id="+sampleId );
		}else{
			result.setSuccess(false);
			map.put("sampleId", sampleId);
			map.put("error", "1");
			map.put("nyType", nyType);
			map.put("vip", vip);
			result.setData(map);
	
		}
		return printJson(result, out);
		//out.put("error", "1");
		//return new ModelAndView("redirect:/nylogin.htm");
	}
	@RequestMapping
	public ModelAndView nyreg(HttpServletRequest request,Map<String, Object>out,String sampleId,String nyType,String vip){
		out.put("sampleId", sampleId);
		out.put("nyType", nyType);
		out.put("vip", vip);
		return null;
		
	}
	@RequestMapping
	public ModelAndView nyreg_stp2(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, Company company,
			CompanyAccount companyAccount, String password,String regiestType,
			String passwordConfirm, String verifyCodeKey, String verifyCode,String sampleId,String nyType,String vip)
			        throws Exception  {

		if ("个人".equals(regiestType)) {
			companyAccount.setEmail("");
			company.setName("");
			company.setBusiness("");
			company.setAreaCode("");
		}
		//sampleId 为样品id
		out.put("sampleId", sampleId);
		out.put("regfrom", regfrom);
		out.put("destUrl", destUrl);
		out.put("tab", "register");
		out.put("vip", vip);
		out.put("nyType", nyType);
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
				
				return new ModelAndView("/mini/nyreg_suc");
			} else {
				return new ModelAndView();
			}
			
			
		} while (false);
        out.put("regiestType", regiestType);
		out.put("company", company);
		out.put("companyAccount", companyAccount);
		out.put("error", error);
		out.put("destUrl", destUrl);
		return new ModelAndView("/mini/nyreg");
	}
	@RequestMapping
	public ModelAndView doNyreg_suc(HttpServletRequest request,Map<String, Object>out,String industryCode,String serviceCode,String address,String addressFix,String introduction,String destUrl,String sampleId,String nyType,String vip){
		SsoUser ssoUser = getCachedUser(request);
		Company companyU = companyService.queryCompanyById(ssoUser.getCompanyId());
		out.put("sampleId", sampleId);
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
		out.put("nyType", nyType);
		out.put("vip", vip);
		return new ModelAndView("/mini/nyreg_suc");
	}
}
