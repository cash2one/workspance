package com.ast.ast1949.yuanliao.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.auth.frontsso.SsoConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class RegisterController extends BaseController {
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private AuthService authService;

	final static String TOKEN_KEY = "regist_token";

	@RequestMapping
	public ModelAndView register_stp1(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response) {
		// 设置Token，用于防止表单重复提交
		String token = UUID.randomUUID().toString();
		SsoUtils.getInstance().setValue(request, response, TOKEN_KEY, token);
		// crsf攻击,生成标记
		HttpUtils.getInstance().setCookie(response, "csrfKey",
				HttpUtils.getInstance().getIpAddr(request),
				SsoConst.SSO_DOMAIN, null);
		out.put("token", token);
		SeoUtil.getInstance().buildSeo(out);
		PageCacheUtil.setNoCache(response);
		SeoUtil.getInstance().buildSeo("register", out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView firstview(Map<String, Object> out) {
		return null;
	}

	@RequestMapping
	public ModelAndView register_stp2(Company company,
			CompanyAccount companyAccount, Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			String token, String aginpassword) throws IOException {
		ExtResult rs = new ExtResult();
		do {
			// 注册来源
			company.setRegfromCode("10031042");
			// 防止csrf攻击
			String random = HttpUtils.getInstance().getCookie(request,
					"csrfKey", SsoConst.SSO_DOMAIN);
			if (StringUtils.isEmpty(random)) {
				rs.setSuccess(false);
				rs.setData("请刷新页面重新完成注册");
				break;
			}
			// 设置Token，用于防止表单重复提交
			String token1 = String.valueOf(SsoUtils.getInstance().getValue(
					request, response, TOKEN_KEY));

			// 验证Token，防止表单重复提交
			if (StringUtils.isEmpty(token)) {
				rs.setSuccess(false);
				rs.setData("请刷新页面重新完成注册");
				break;
			}
			if (!token1.equals(token)) {
				rs.setSuccess(false);
				rs.setData("请刷新页面重新完成注册");
				break;
			}
			// 处理注册信息
			if (company.getIndustryCode() == null) {
				company.setIndustryCode("10001010");
			}
			CategoryFacade cate = CategoryFacade.getInstance();
			String location = "";
			if (company.getAreaCode() != null) {
				if (company.getAreaCode().length() > 12) {
					location = cate.getValue(company.getAreaCode().substring(0,
							12))
							+ ""
							+ cate.getValue(company.getAreaCode().substring(0,
									16))+ ""
											+ cate.getValue(company.getAreaCode().substring(0,
													20));
				} else if (company.getAreaCode().length() > 8) {
					location = cate.getValue(company.getAreaCode().substring(0,
							8))
							+ ""
							+ cate.getValue(company.getAreaCode().substring(0,
									12));
				} else if (company.getAreaCode().length() == 8) {
					location = cate.getValue(company.getAreaCode().substring(0,
							8));
				}
				company.setAddress(location+company.getAddress());
			}

			String account = null;
			try {
				if (StringUtils.isEmpty(companyAccount.getAccount())) {
					companyAccount.setAccount(companyAccount.getMobile());
				}
				account = companyAccountService.registerUser(companyAccount
						.getAccount(), companyAccount.getEmail(),
						companyAccount.getPassword(), aginpassword,
						companyAccount, company, HttpUtils.getInstance()
								.getIpAddr(request));
			} catch (Exception e) {
				rs.setSuccess(false);
				rs.setData(e.getMessage());
			}
			if (StringUtils.isEmpty(account)) {
				if (rs.getData() == null) {
					rs.setData("用户注册失败");
				}
				rs.setSuccess(false);
				break;
			}

			// 注册成功后发送激活邮件
			companyValidateService.sendValidateByEmail(
					companyAccount.getAccount(), companyAccount.getEmail());

			// 自动登录
			SsoUser ssoUser = null;
			try {
				ssoUser = SsoUtils.getInstance().validateUser(response,
						companyAccount.getAccount(),
						companyAccount.getPassword(), null,
						HttpUtils.getInstance().getIpAddr(request));
			} catch (Exception e) {
				e.printStackTrace();
			}
			HttpUtils.getInstance().setCookie(response, "csrfKey", null,
					SsoConst.SSO_DOMAIN, 0);
			setSessionUser(request, ssoUser);
			SsoUtils.getInstance().remove(request, TOKEN_KEY);

			rs.setSuccess(true);
			return printJson(rs, out);
		} while (false);

		return printJson(rs, out);
	}

	/**
	 * 注册页面检验手机是否重复
	 * 
	 * @param mobile
	 * @param out
	 * @return 返回true 表示手机没有重复,当前注册客户可以使用改电话号码
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView regMobile(String mobile, Map<String, Object> out)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(mobile)) {
			Integer i = companyAccountService.countAccountOfMobile(mobile);
			if (i == null || i.intValue() <= 0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}

	/**
	 * 注册页面检验邮件是否重复
	 * 
	 * @param email
	 * @param out
	 * @return 返回true,证明不重复,用户可以使用该email
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView regEmail(String email, Map<String, Object> out)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(email)) {
			Integer i = authService.countUserByEmail(email);
			if (i == null || i.intValue() <= 0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView rest_success(Map<String, Object> out) {
		SeoUtil.getInstance().buildSeo("register2",out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView catChild(Map<String, Object> out, String parentCode)
			throws IOException {
		List<CategoryDO> list = new ArrayList<CategoryDO>();
		Map<String, String> map = CategoryFacade.getInstance().getChild(
				parentCode);
		if (map == null) {
			return printJson(list, out);
		}
		for (Entry<String, String> m : map.entrySet()) {
			CategoryDO c = new CategoryDO();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		return printJson(list, out);
	}
}
