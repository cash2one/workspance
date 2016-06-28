/**
 * @author zhujq
 */
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
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanySearch;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyDto;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.zz91.util.lang.StringUtils;

@Controller
public class CompanyController extends BaseController{
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private CompanyAccountService companyAccountService;
	
	@RequestMapping
	public ModelAndView list(){
		return null;
	}
	
	/**
	 * 公司列表
	 * @param out
	 * @param page
	 * @param search
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryCompanyList(Map<String,Object> out,PageDto<CompanyDto> page, CompanySearch search) throws IOException{
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("gmt_created");
		}
		page = companyInfoService.pageBySearchAdmin(page, search);
		return printJson(page, out);
	}
	
	/**
	 * 公司信息聚合页
	 * @param out
	 * @param request
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}
	/**
	 * 公司信息页
	 * @param request
	 * @param out
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView compInfo(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}
	/**
	 * 公司信息
	 * @param out
	 * @param request
	 * @param companyId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryCompanyInfo(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) throws IOException {
		CompanyDto company = companyInfoService.queryCompanyDtoByIdAdmin(companyId);
		List<CompanyDto> list = new ArrayList<CompanyDto>();
		list.add(company);
		return printJson(list, out);
	}
	
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
	
	/**
	 * 保存公司信息
	 * @param out
	 * @param request
	 * @param companyInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView saveCompany(Map<String, Object> out,
			HttpServletRequest request, CompanyInfo companyInfo) throws IOException {
		Integer i = companyInfoService.updateCompanyByAdmin(companyInfo);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

}
