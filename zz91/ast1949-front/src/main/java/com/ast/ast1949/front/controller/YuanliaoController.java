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

import com.ast.ast1949.domain.auth.AuthAutoLogin;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.util.PageCacheUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class YuanliaoController extends BaseController{
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private AuthService authService;
	
	@RequestMapping
	public ModelAndView status(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {
		PageCacheUtil.setNoCache(response);

		do {
			SsoUser ssoUser = getCachedUser(request);

			if (ssoUser != null) {
				model.put("isLogin", 1);
				model.put("yousuyuanUser", ssoUser);
				break;
			}
			String cookie = HttpUtils.getInstance().getCookie(request,
					AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
			AuthAutoLogin aal = authService.queryAutoLoginByCookie(cookie);

			if (aal == null || StringUtils.isEmpty(aal.getCompanyAccount())
					|| StringUtils.isEmpty(aal.getPassword())) {
				break;
			}

			String a = "";
			try {
				a = companyAccountService.validateUser(aal.getCompanyAccount(),
						MD5.encode(aal.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				break;
			} catch (UnsupportedEncodingException e) {
				break;
			} catch (AuthorizeException e) {
				break;
			}

			if (StringUtils.isEmpty(a)) {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("front"));
			}
			ssoUser = companyAccountService.initSessionUser(a);

			if (ssoUser != null) {
				String key = UUID.randomUUID().toString();
				String ticket = "";
				try {
					ticket = MD5.encode(a + aal.getPassword() + key);
				} catch (NoSuchAlgorithmException e) {
				} catch (UnsupportedEncodingException e) {
				}
				ssoUser.setTicket(ticket);
				HttpUtils.getInstance().setCookie(response,
						SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
				MemcachedUtils.getInstance().getClient()
						.set(ticket, 1 * 60 * 60, ssoUser);
			}
		} while (false);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// 清除改cookie
		String cookie = HttpUtils.getInstance().getCookie(request,
				AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
		authService.removeAuthLoginByCookie(cookie);

		SsoUtils.getInstance().logout(request, response, null);
		PageCacheUtil.setNoCache(response);
		return new ModelAndView("redirect:"+AddressTool.getAddress("yuanliao"));
	}
}
