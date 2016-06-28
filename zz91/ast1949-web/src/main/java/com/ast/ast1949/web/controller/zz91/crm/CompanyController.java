/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-20
 */
package com.ast.ast1949.web.controller.zz91.crm;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.log.LogOperationService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.sms.SmsUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * 提供网站客户基本信息管理、展会客户收集、注册申请高会相关服务的方法
 * 
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-5-20
 */
@Controller
public class CompanyController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private AuthService authService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private LogOperationService logOperationService;

	@RequestMapping
	public ModelAndView saveCompany(Map<String, Object> out,
			HttpServletRequest request, Company company) throws IOException {
		Integer i = companyService.updateCompanyByAdmin(company);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryCompanyInfo(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) throws IOException {
		CompanyDto company = companyService.queryCompanyDetailById(companyId);
		List<CompanyDto> list = new ArrayList<CompanyDto>();
		list.add(company);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		initCommon(request, out);
		return null;
	}

	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}

	// @Deprecated
	// @RequestMapping
	// public ModelAndView queryCompany(Company company, CompanyAccount account,
	// PageDto<CompanyDto> page, Map<String, Object> model)
	// throws IOException {
	// page = companyService.pageCompanyByAdmin(company, account, page);
	//		
	// return printJson(page, model);
	// }

	// final static String
	// SMS_API="http://admin1949.zz91.com/reborn-admin/sms/main/doSendFromEP.htm?account=kongsj&password=123456";
	// final static String
	// SMS_TEMPLAGE="尊敬的用户，您在zz91再生网的账户密码已重置！用户名{0}，密码{1}，请牢记自己的密码。 zz91.com";

	@RequestMapping
	public ModelAndView resetPassword(HttpServletRequest request,
			Map<String, Object> out, String account, String password,
			String mobile, Boolean isSendMobile) throws IOException {
		ExtResult result = new ExtResult();
		result.setSuccess(companyAccountService.resetPasswordByAdmin(account,
				password));
		if (result.isSuccess() && isSendMobile != null && isSendMobile) {
			// 发短信通知
			SmsUtil.getInstance().sendSms("zz91_admin_pwd_reset", mobile, null,
					null, new String[] { account, password });

			// MessageFormat format=new MessageFormat(SMS_TEMPLAGE);
			// String content=format.format(new String[]{account,password});
			// content=URLEncoder.encode(content, "utf-8");
			//			
			// URL url=new URL(SMS_API+"&mobile="+mobile+"&content="+content);
			// Jsoup.parse(url, 10000);
		}
		// mobile=13738194812&content=

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView adminmyrc(HttpServletRequest request,
			Map<String, Object> out, String account) {
		if (StringUtils.isEmpty(account)) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("front"));
		}
		String password = authService.queryPassword(account);
		out.put("account", account);
		out.put("password", password);
		return new ModelAndView("redirect:" + AddressTool.getAddress("front")
				+ "/admin/myrcadmin/myrcLogin.htm");
	}

	@RequestMapping
	public ModelAndView compInfo(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}

	@RequestMapping
	public ModelAndView compInfoReadonly(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}

	@RequestMapping
	public ModelAndView companylist(HttpServletRequest request,
			Map<String, Object> out) {
		return null;
	}

	@Deprecated
	@RequestMapping
	public ModelAndView queryCompanyAdmin(HttpServletRequest request,
			Map<String, Object> out, Company company, PageDto<Company> page,
			String gmtRegisterStart, String gmtRegisterEnd, Integer cid,
			String email, String account, String mobile) throws IOException {
		Date startDate = null;
		Date endDate = null;
		if (StringUtils.isNotEmpty(gmtRegisterStart)) {
			try {
				startDate = DateUtil.getDate(gmtRegisterStart, "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(gmtRegisterEnd)) {
			try {
				endDate = DateUtil.getDate(gmtRegisterEnd, "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		page = companyService.pageCompanyByAdmin(company, startDate, endDate,
				cid, email, account, mobile, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView queryCompany(HttpServletRequest request,
			Map<String, Object> out, Company company, CompanyAccount account,
			Date regfrom, Date regto, String activeFlag,
			PageDto<CompanyDto> page) throws IOException {

		page = companyService.pageCompanyByAdmin(company, account, regfrom,
				regto, activeFlag, page);

		return printJson(page, out);
	}

	/**
	 * 拉黑客户 param：companyId,isBlock
	 */
	@RequestMapping
	public ModelAndView updateIsBlock(HttpServletRequest request,
			Map<String, Object> out, Integer companyId, String isBlock,
			String reason) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = companyService.updateIsBlock(companyId, isBlock);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
			// 插入后台日志操作标 log_operation
			SessionUser sessionUser = getCachedUser(request);
			String operation = "";
			if ("1".equals(isBlock)) {
				operation = LogOperationService.BLACK_OPERATION;
			}
			if ("0".equals(isBlock)) {
				operation = LogOperationService.UN_BLACK_OPERATION;
				reason = sessionUser.getAccount() + "取消了公司ID:" + companyId
						+ "客户的黑名单限制";
			}
			// 记录黑名单情况
			logOperationService.addOneOperation(companyId, sessionUser
					.getAccount(), operation, reason);
		}
		return printJson(result, out);
	}

	// 初始化CS
	private void initCommon(HttpServletRequest request, Map<String, Object> out) {
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept("10001005");
		out.put("csMap", JSONObject.fromObject(map));

		out.put("cs", getCachedUser(request).getAccount());
		out.put("csName", getCachedUser(request).getName());
		if (AuthUtils.getInstance().authorizeRight("assign_company", request,null)) {
			out.put("asignFlag", "1");
			out.put("allcs", map);
		}
	}

	@RequestMapping
	public ModelAndView blacklist(Map<String, Object> out,
			HttpServletRequest request) {
		initCommon(request, out);
		return null;
	}

	@RequestMapping
	public ModelAndView queryBlackList(Map<String, Object> out,
			HttpServletRequest request, Company company,CompanyAccount companyAccount,String reason,
			PageDto<CompanyDto> page) throws IOException {
		page = companyService.queryBlackListForAdmin(company,companyAccount,reason, page);
		return printJson(page, out);
	}
}
