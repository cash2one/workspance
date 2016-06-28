/**
 * @author zhujq
 * @data 2016-06-20
 * @describe 生意管家首页
 */
package com.ast.feiliao91.mobile.controller;

import java.io.UnsupportedEncodingException;
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
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class HomeController extends BaseController{
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyValidateService companyValidateService;
	
	/**
	 * 我的首页
	 * @param out
	 * @param request
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request) {
		SsoUser user = getCachedUser(request);
		if(user == null){
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("mobile")+"/login.htm");
		}
		CompanyAccount companyAccount = companyAccountService.queryAccountByCompanyId(user.getCompanyId());
		out.put("user", user);
		out.put("companyAccount", companyAccount);
		return null;
	}
	
	/**
	 * 登出
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView loginout(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String ticket = HttpUtils.getInstance().getCookie(request,
				SsoConst.TICKET_KEY, SsoConst.SSO_DOMAIN);
		if (StringUtils.isNotEmpty(ticket)) {
			MemcachedUtils.getInstance().getClient().delete(ticket);
		}
		SsoUtils.getInstance().clearnSessionUser(request, null);
		request.getSession().removeAttribute(SsoUser.SESSION_KEY);
		HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, "",
				SsoConst.SSO_DOMAIN, 1);
		return new ModelAndView("redirect:"
				+ AddressTool.getAddress("mobile")+"/login.htm");
	}
	
	/**
	 * 修改当前所登录帐号的密码
	 * @param out
	 * @param request
	 * @param vcode验证码
	 * @param mobile 手机号
	 * @return
	 */
	@RequestMapping
	public ModelAndView changePwd(Map<String, Object> out,
			HttpServletRequest request){
		SsoUser user = getCachedUser(request);//当前登录的帐号
		if(user == null){
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("mobile")+"/login.htm");
		}
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView doChangePwd(Map<String, Object> out,
			HttpServletRequest request, String vcode,String newPsd,
			String mobile){
		if(StringUtils.isEmpty(vcode) || StringUtils.isEmpty(newPsd) || StringUtils.isEmpty(mobile)){
			return new ModelAndView("redirect:/changePwd.htm");
		}
//		1.如果手机验证码验证通过，则对密码进行重置
		Integer i =companyValidateService.validateByMobile(mobile, vcode);
		if (i<1) {
			//失败
			return new ModelAndView("redirect:/changePwd.htm");
		}else {
			SsoUser user = getCachedUser(request);
			CompanyAccount companyAccount = companyAccountService.queryAccountByCompanyId(user.getCompanyId());
			Integer j = companyAccountService.updatePwd(companyAccount.getAccount(),newPsd);
			if(j>0){
				//修改成功
				return new ModelAndView("redirect:/changePwdSucc.htm");
			}
		}
		return new ModelAndView("redirect:/changePwd.htm");
	}
	
	/**
	 * 修改成功页
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView changePwdSucc(Map<String, Object> out,HttpServletRequest request) {
		return null;
	}
}
