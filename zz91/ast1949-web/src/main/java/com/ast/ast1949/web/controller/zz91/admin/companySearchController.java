package com.ast.ast1949.web.controller.zz91.admin;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.log.AuthAdminService;
import com.ast.ast1949.service.log.LogOperationService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;

@Controller
public class companySearchController extends BaseController{
	@Resource
	private CompanyService companyService;
	@Resource
	private AuthService authService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private LogOperationService logOperationService;
	@Resource
	private AuthAdminService authAdminService;


	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		initCommon(request, out);
		return null;
	}


	// 初始化CS
	private void initCommon(HttpServletRequest request, Map<String, Object> out) {
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(
				"10001005");
		out.put("csMap", JSONObject.fromObject(map));

		out.put("cs", getCachedUser(request).getAccount());
		out.put("csName", getCachedUser(request).getName());
		if (AuthUtils.getInstance().authorizeRight("assign_company", request,
				null)) {
			out.put("asignFlag", "1");
			out.put("allcs", map);
		}
	}


}
