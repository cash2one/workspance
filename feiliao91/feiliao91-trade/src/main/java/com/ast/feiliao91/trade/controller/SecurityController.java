package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class SecurityController extends BaseController {
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyValidateService companyValidateService;

	@RequestMapping
	public ModelAndView security(Map<String, Object> out,
			HttpServletRequest request) {
		do {
			SsoUser ssoUser = getCachedUser(request);
			// 未登陆
			if (ssoUser == null) {
				return null;
			}
			ssoUser = companyAccountService.initSsoUser(ssoUser.getAccount());
			// 查询登陆信息
			CompanyAccount account = companyAccountService
					.queryAccountByAccount(ssoUser.getAccount());
			request.getSession().setAttribute("flags", "0");
			out.put("ssoUser", ssoUser);
			if (StringUtils.isNotEmpty(account.getMobile())) {
				out.put("mobile", addHide(account.getMobile()));
			}
			if (StringUtils.isNotEmpty(account.getEmail())) {
				out.put("email", addHide(account.getEmail()));
			}
		} while (false);
		SeoUtil.getInstance().buildSeo("security", out);
		return null;
	}

	/**
	 * 绑定手机页面
	 * 
	 * @param out
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView securityPhone(Map<String, Object> out,
			HttpServletRequest request) throws IOException {
		do {
			SsoUser ssoUser = getCachedUser(request);
			ssoUser = companyAccountService.initSsoUser(ssoUser.getAccount());
			CompanyAccount account = companyAccountService
					.queryAccountByAccount(ssoUser.getAccount());
			String flags = (String) request.getSession().getAttribute("flags");
			if ("1".equals(flags)) {
				if (StringUtils.isNotEmpty(account.getMobile())) {
					break;
				}
			} else if ("0".equals(flags)) {
				if (StringUtils.isNotEmpty(account.getMobile())) {
					break;
				}
			}
			out.put("ssoUser", ssoUser);
			SeoUtil.getInstance().buildSeo("securityPhone", out);
			request.getSession().setAttribute("flags", null);
			return null;
		} while (false);
		return new ModelAndView("redirect:/security/security.htm");
	}

	/**
	 * 发送验证码
	 * 
	 * @param out
	 * @param mobile
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView securityPhoneCh(Map<String, Object> out, String mobile,
			HttpServletRequest request, String account, String code)
			throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		ssoUser = companyAccountService.initSsoUser(ssoUser.getAccount());
		if (mobile == null || mobile == "") {
			mobile = ssoUser.getMobile();
		}
//		companyValidateService.sendCodeByMobile(mobile, code);
//		request.getSession().setAttribute("User", "user");
		return new ModelAndView("redirect:/security/securityPhoneChange.htm");
	}

	/**
	 * ajax发送验证码
	 * 
	 * @param out
	 * @param mobile
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView sendMobileCode(Map<String, Object> out, String mobile,
			String code) throws IOException {
		ExtResult rs = new ExtResult();
		if (mobile == null || mobile == "") {
			rs.setSuccess(false);
			return printJson(rs, out);
		}
		companyValidateService.sendCodeByMobile(mobile, code);
		rs.setSuccess(true);
		return printJson(rs, out);
	}

	@RequestMapping
	public ModelAndView securityPhoneChange(Map<String, Object> out,
			HttpServletRequest request) throws IOException {
//		String user = (String) request.getSession().getAttribute("User");
//		if (user == null) {
//			return new ModelAndView("redirect:/security/security.htm");
//		}
		SsoUser ssoUser = getCachedUser(request);
		ssoUser = companyAccountService.initSsoUser(ssoUser.getAccount());
		if(StringUtils.isNotEmpty(ssoUser.getMobile())){
		out.put("mobile", addHide(ssoUser.getMobile()));
		}
		out.put("ssoUser", ssoUser);
		SeoUtil.getInstance().buildSeo("securityPhoneChange", out);
		return null;
	}

	/**
	 * 发送邮箱邮箱
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView sendEmail(Map<String, Object> out,
			HttpServletRequest request, String email, String code)
			throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		// 查询登陆信息
		CompanyAccount account = companyAccountService
				.queryAccountByAccount(ssoUser.getAccount());
		if (email == null || email == "") {
			email = account.getEmail();
		}
		companyValidateService.sendCodeByEmail(email, account.getAccount(),
				code);
		request.getSession().setAttribute("code", code);
		return new ModelAndView("redirect:/security/securityEmailChange.htm");
	}

	/**
	 * 发送邮箱邮箱
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView sendEmailCode(Map<String, Object> out, String email,
			String code, HttpServletRequest request) throws IOException {
		ExtResult rs = new ExtResult();
		SsoUser ssouser=getCachedUser(request);
		if (email == null || email == "") {
			rs.setSuccess(false);
			return printJson(rs, out);
		}
		companyValidateService.sendCodeByEmail(email, ssouser.getAccount(),
				code);
		rs.setSuccess(true);
		return printJson(rs, out);
	}

	@RequestMapping
	public ModelAndView securityEmailChange(Map<String, Object> out,
			HttpServletRequest request) {
		// 未登陆
		SsoUser ssoUser = getCachedUser(request);
		String code = (String) request.getSession().getAttribute("code");
		if (StringUtils.isEmpty(code)) {
			return new ModelAndView("redirect:/security/security.htm");
		} else {
			if (!code.equals("1")) {
				return new ModelAndView("redirect:/security/security.htm");
			}
		}
		// 查询登陆信息
		CompanyAccount account = companyAccountService
				.queryAccountByAccount(ssoUser.getAccount());
		out.put("account", account.getAccount());
		if(StringUtils.isNotEmpty(account.getEmail())){
		String[] email = account.getEmail().split("@");
		out.put("email", addHide(email[0]) + "@" + email[1]);
		out.put("readMyEmail", "http://mail." + email[1]);
		}
		SeoUtil.getInstance().buildSeo("securityEmailChange", out);
		return null;
	}

	
	/**
	 * 绑定邮箱成功页面
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView success(Map<String, Object> out)
			throws IOException {
		SeoUtil.getInstance().buildSeo("success", out);
		return null;
	}
	
	/**
	 * 解除手机绑定
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView unBindPhone(Map<String, Object> out, String account)
			throws IOException {
		ExtResult rs = new ExtResult();
		if (StringUtils.isNotEmpty(account)) {
			rs.setSuccess(true);
			companyAccountService.updatePhone(account, null);
		} else {
			rs.setSuccess(false);
		}
		return printJson(rs, out);
	}

	/**
	 * 绑定验证邮箱
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doValidEmail(Map<String, Object> out, String key,
			String account, HttpServletRequest request, String vcode,
			String email) throws IOException {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			String md5key = "";
			try {
				md5key = MD5.encode(account + email + vcode);
			} catch (Exception e) {
			}
			// 检验两次加密的结果是否一致
			if (!md5key.equals(key)) {
				break;
			}
			// 验证是否正确
			Integer i = companyValidateService.validateByType(email, vcode,
					CompanyValidateService.TYPE_EMAIL);
			if (i > 0) {
				// 绑定邮箱
				request.getSession().setAttribute("flags", "3");
				companyAccountService.updateEmail(account, email);
				return new ModelAndView("redirect:/security/success.htm");
			}

		} while (false);
		return new ModelAndView("redirect:/security/securityEmail.htm");
	}

	/**
	 * 解绑验证邮箱
	 */
	@RequestMapping
	public ModelAndView unValidEmail(Map<String, Object> out, String key,
			String account, HttpServletRequest request, String vcode,
			String email) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			String md5key = "";
			try {
				md5key = MD5.encode(account + email + vcode);
			} catch (Exception e) {
			}
			// 检验两次加密的结果是否一致
			if (!md5key.equals(key)) {
				break;
			}

			// 验证是否正确
			Integer i = companyValidateService.validateByType(email, vcode,
					CompanyValidateService.TYPE_EMAIL);
			if (i > 0) {
				request.getSession().setAttribute("flags", "3");
				companyAccountService.updateEmail(account, null);
				return new ModelAndView("redirect:/security/securityEmail.htm");
			}

		} while (false);
		return new ModelAndView("redirect:/security/securityEmailChange.htm");
	}

	/**
	 * 绑定邮箱页面
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView securityEmail(Map<String, Object> out,
			HttpServletRequest request) {
		SsoUser ssoUser = getCachedUser(request);
		ssoUser = companyAccountService.initSsoUser(ssoUser.getAccount());
		CompanyAccount account1 = companyAccountService
				.queryAccountByAccount(ssoUser.getAccount());
		
		String flags = (String) request.getSession().getAttribute("flags");
		if(flags==null){
		if(StringUtils.isNotEmpty(account1.getEmail())){
			return new ModelAndView("redirect:/security/security.htm");
		}
		}
		if ("0".equals(flags)) {
			if (StringUtils.isNotEmpty(account1.getEmail())) {
				return new ModelAndView("redirect:/security/security.htm");
			} 
		}
		out.put("ssoUser", ssoUser);
		SeoUtil.getInstance().buildSeo("securityEmail", out);
		return null;
	}

	/**
	 * 验证验证码
	 * 
	 * @param out
	 * @param mobile
	 * @param vcode
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doValidPhone(Map<String, Object> out, String mobile,
			String vcode, HttpServletRequest request) throws IOException {
		ExtResult rs = new ExtResult();
		// 验证验证码
		Integer count = companyValidateService.validateByMobile(mobile, vcode);
		if (count > 0) {
			rs.setSuccess(true);
		} else {
			rs.setSuccess(false);
		}
		request.getSession().setAttribute("flags", "1");
		request.getSession().setAttribute("bind", "true");
		return printJson(rs, out);
	}

	/**
	 * 绑定手机
	 * 
	 * @param out
	 * @param mobile
	 * @param vcode
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView bindPhone(Map<String, Object> out, String mobile,
			HttpServletRequest request) {
		SsoUser ssoUser = getCachedUser(request);
		String bind = (String) request.getSession().getAttribute("bind");
		if (StringUtils.isNotEmpty(bind)) {
			if (bind.equals("true")) {
				companyAccountService.updatePhone(ssoUser.getAccount(), mobile);
				request.getSession().setAttribute("fla", "");
			}
		} else {
			return new ModelAndView("redirect:/security/security.htm");
		}
		return null;
	}

	/**
	 * 加星隐藏保密隐私
	 * 
	 * @param str
	 * @return
	 */
	private String addHide(String str) {
		if (str.length() < 6) {
			return str;
		}
		int start = 0;
		if (str.length() > 7) {
			start = 3;
		} else {
			start = 3 - (8 - str.length());
		}
		int end = 0;
		if (str.length() < 9) {
			end = 1;
		} else {
			end = 1 + (str.length() - 8);
		}
		str = str.substring(0, start) + "****"
				+ str.substring(str.length() - end, str.length());
		return str;
	}

	/**
	 * 设置支付密码
	 * 
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView setPayPassword(Map<String, Object> out,
			HttpServletRequest request, String re,String url) {
		SsoUser ssoUser = getCachedUser(request);
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(ssoUser.getCompanyId());
		if (account != null) {
			companyValidateService.sendCodeByMobile(account.getMobile(), "0");
		}
		return new ModelAndView("redirect:/security/setPayPwdCode.htm?re=" + re+"&url="+url);
	}
	
	/**
	 * 获取验证码
	 * 
	 * @param out
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView getPhoneCode(Map<String, Object> out,
			HttpServletRequest request) throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		ExtResult rs = new ExtResult();
		if(ssoUser==null){
			rs.setSuccess(false);
			return printJson(rs, out);
		}
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(ssoUser.getCompanyId());
		if (account != null) {
			companyValidateService.sendCodeByMobile(account.getMobile(), "0");
		}
		rs.setSuccess(true);
		return printJson(rs, out);
	}
	
	
	/**
	 * 选择重置方式 - 重置步骤1
	 * 
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView resetPayPwdStp_1(Map<String, Object> out,
			HttpServletRequest request) {
		SeoUtil.getInstance().buildSeo("common", new String[] { "重置支付密码" },
				new String[] {}, new String[] {}, out);
		SsoUser ssoUser = getCachedUser(request);
		String url = request.getHeader("Referer");
        out.put("url", url);		
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(ssoUser.getCompanyId());
		out.put("account", account);
		if(StringUtils.isNotEmpty(account.getMobile())){
		out.put("mobile", addHide(account.getMobile()));
		}
		return new ModelAndView();
	}

	/**
	 * 使用支付密码重置 - 重置步骤2
	 * 
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView resetPayPwdStp_2(Map<String, Object> out,
			HttpServletRequest request,String url) {
		SeoUtil.getInstance().buildSeo("common", new String[] { "重置支付密码" },
				new String[] {}, new String[] {}, out);
		SsoUser ssoUser = getCachedUser(request);
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(ssoUser.getCompanyId());
		out.put("account", account);
		if(StringUtils.isNotEmpty(account.getMobile())){
		out.put("mobile", addHide(account.getMobile()));
		}
		out.put("url", url);
		return new ModelAndView();
	}

	/**
	 * 验证支付密码 - 重置步骤3
	 * 
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView resetPayPwdStp_3(Map<String, Object> out,
			HttpServletRequest request, String pwd,String url) {
		// SeoUtil.getInstance().buildSeo("common",new String[]{"重置支付密码"},new
		// String[]{},new String[]{}, out);
		SsoUser ssoUser = getCachedUser(request);
		CompanyAccount account = companyAccountService
				.queryByCompanyIdAndPayPwd(ssoUser.getCompanyId(), pwd);
		if (account == null) {
			return new ModelAndView("redirect:/security/resetPayPwdStp_2.htm?url="+url);
		}
		// out.put("account", account);
		// out.put("mobile", addHide(account.getMobile()));
		return new ModelAndView("redirect:/security/setPayPwd.htm?url="+url);
	}

	/**
	 * 输入短信验证码页面
	 * 
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView setPayPwdCode(Map<String, Object> out,
			HttpServletRequest request, String re,String url) {
		SeoUtil.getInstance().buildSeo("common",
				new String[] { "设置支付密码-短信验证" }, new String[] {},
				new String[] {}, out);
		SsoUser ssoUser = getCachedUser(request);
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(ssoUser.getCompanyId());
		out.put("account", account);
		out.put("re", re);
		out.put("url", url);
		return new ModelAndView("/security/setPayPwdCode");
	}

	/**
	 * 修改支付密码页面
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView setPayPwd(Map<String, Object> out,
			HttpServletRequest request, String re,String url) {
		SeoUtil.getInstance().buildSeo("common", new String[] { "修改支付密码" },
				new String[] {}, new String[] {}, out);
		SsoUser ssoUser = getCachedUser(request);
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(ssoUser.getCompanyId());
		out.put("account", account);
		out.put("re", re);
		out.put("url", url);
		return new ModelAndView();
	}

	/**
	 * 设置支付密码成功
	 * 
	 * @param out
	 * @param request
	 * @param pwd
	 * @return
	 */
	@RequestMapping
	public ModelAndView doSetPayPwd(Map<String, Object> out,
			HttpServletRequest request, String pwd, String re,String url) {
		SeoUtil.getInstance().buildSeo("common", new String[] { "支付密码修改成功" },
				new String[] {}, new String[] {}, out);
		SsoUser ssoUser = getCachedUser(request);
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(ssoUser.getCompanyId());
		companyAccountService.updatePayPwd(account.getAccount(), pwd);
		out.put("url", url);
		out.put("re", re);
		return new ModelAndView("/security/set_suc");
	}

}
