package com.ast.ast1949.web.controller.zz91.crm;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.web.controller.BaseController;

@Controller
public class CompanyAccountController extends BaseController {

	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CrmCsService crmCsService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryAccount(HttpServletRequest request, Map<String, Object> out, CompanyAccount account, PageDto<CompanyDto> page,CompanyAccountSearchDto dto) throws IOException{
		page=companyAccountService.queryAccountByAdmin(account,dto, page);
		return printJson(page, out);
	}
	//交易中心的公司库搜索
	@RequestMapping
	public ModelAndView queryAccountBySearch(HttpServletRequest request, Map<String, Object> out, CompanyAccount account, PageDto<CompanyDto> page,CompanyAccountSearchDto dto,Company company) throws IOException{
		page=companyAccountService.queryAccountByAdminSearch(account, dto, company, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView reassign(Map<String, Object> out, HttpServletRequest request,
			String oldCsAccount, Integer companyId, String csAccount) throws IOException{
		if(companyId != null && companyId.intValue()>0){
			oldCsAccount = crmCsService.queryAccountByCompanyId(companyId);
		}
		
		Boolean b=crmCsService.reassign(oldCsAccount, csAccount, companyId);
		crmCompanySvrService.createCsProfile(companyId);
		
		ExtResult result = new ExtResult();
		result.setSuccess(b);
		return printJson(result, out);
	}
	
}
