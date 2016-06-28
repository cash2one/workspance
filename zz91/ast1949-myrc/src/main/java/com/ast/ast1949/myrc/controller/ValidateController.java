package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;

@Controller
public class ValidateController extends BaseController{
	
	final static String NO_PER_URL = "/mycompany/updateCompany.htm";
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsService productsService;
	@Resource
	private	 AuthService authService;
	@Resource
	private CompanyAccountService companyAccountService;
	/**
	 * 验证公司信息是否完善
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView validateCompanyInfo(HttpServletRequest request,Map<String,Object>out,String basePath) throws IOException{
		SsoUser ssoUser = getCachedUser(request);
		Company dto = companyService.queryCompanyById(ssoUser.getCompanyId());
		do{
			out.put("result", 0);
			if(dto==null){
				break;
			}
			// 公司名
			if(StringUtils.isEmpty(dto.getName())){
				break;
			}
			// 地址
			if(StringUtils.isEmpty(dto.getAddress())){
				break;
			}
			// 国家地区
			if(StringUtils.isEmpty(dto.getAreaCode())){
				break;
			}
			// 主营行业
			if(StringUtils.isEmpty(dto.getIndustryCode())){
				break;
			}
			// 公司类型
			if(StringUtils.isEmpty(dto.getServiceCode())){
				break;
			}
			// 主营业务
			if(StringUtils.isEmpty(dto.getBusiness())){
				break;
			}
			// 公司简介
			if(StringUtils.isEmpty(dto.getIntroduction())){
				break;
			}
			out.put("result", 1);
		}while(false);
		if(NO_PER_URL.equals(basePath)){
			out.put("result", 1);
		}
		return new ModelAndView();
	}
	
	/**
	 * 验证title是否可用
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping
	public ModelAndView ajaxTitle(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String fieldValue, String fieldId, String extraData) throws UnsupportedEncodingException{
		SsoUser ssoUser = getCachedUser(request);
		if(!StringUtils.isContainCNChar(fieldValue)){
			fieldValue = StringUtils.decryptUrlParameter(fieldValue);
		}
		boolean pass = productsService.validateTitleAndAccount(fieldValue, ssoUser.getAccount());
		boolean result=false;
		if(pass){
			result=true;
		}
		out.put("json", buildResult(fieldId, result, null));
		return new ModelAndView("json");
	}
	
	private String buildResult(String fieldId, boolean result, String message){
		if(message==null){
			message="";
		}
		return "[\""+fieldId+"\","+result+",\""+message+"\"]";
	}
	
	@RequestMapping
	public ModelAndView regEmail(String email, Map<String, Object> out) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(email)) {
			Integer i=companyAccountService.countUserByEmail(email);
			if (i== null || i.intValue()<=0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}

}
