package com.ast.feiliao91.admin.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.admin.controller.BaseController;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.service.company.CompanyAccountService;

@Controller
public class CompanyAccountController extends BaseController{
	@Resource
	private CompanyAccountService companyAccountService;
	
	/**
	 * 后台公司信息获得帐号
	 * @param request
	 * @param companyId
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryCompanyAccount(HttpServletRequest request, Integer companyId,Map<String, Object> out) throws IOException {
		CompanyAccount companyAccount = companyAccountService.queryAccountByCompanyId(companyId);
		List<CompanyAccount> list = new ArrayList<CompanyAccount>();
		list.add(companyAccount);
		return printJson(list, out);
	}
}
