package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;

@Controller
public class ValidateController extends BaseController {
	
	@Resource
	private AuthService authService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyService	companyService;
	@Resource
	private OauthAccessService oauthAccessService;

	@RequestMapping
	public ModelAndView account(HttpServletRequest request, Map<String, Object> out, 
			String fieldId, String fieldValue, String extraData){
		boolean result=false;
		if (StringUtils.isNotEmpty(fieldValue)) {
			Integer userCount = authService.countUserByAccount(fieldValue);
			if (userCount == null || userCount == 0) {
				result=true;
			}
		}
		out.put("json", buildResult(fieldId, result));
		return new ModelAndView("json");
	}
	
	@RequestMapping
	public ModelAndView email(HttpServletRequest request, Map<String, Object> out, 
			String fieldId, String fieldValue, String extraData){
		boolean result=false;
		if (StringUtils.isNotEmpty(fieldValue)) {
			Integer i=authService.countUserByEmail(fieldValue);
			if (i== null || i.intValue()<=0) {
				result = true;
			}
		}
		out.put("json", buildResult(fieldId, result));
		return new ModelAndView("json");
	}
	
	@RequestMapping
	public ModelAndView mobile(HttpServletRequest request, Map<String, Object> out, 
			String fieldId, String fieldValue, String extraData){
		boolean result=false;
		if (StringUtils.isNotEmpty(fieldValue)) {
			Integer num = companyAccountService.countAccountOfMobile(fieldValue);
			if(num==null || num.intValue()<=0){
				result= true;
			}
		}
		out.put("json", buildResult(fieldId, result));
		return new ModelAndView("json");
	}
	
	private String buildResult(String fieldId, boolean result){
		return "[\""+fieldId+"\","+result+",\"\"]";
	}
	
	/**
	 * 注册页面检验邮件是否重复
	 * @param email
	 * @param out
	 * @return 返回true,证明不重复,用户可以使用该email
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView regEmail(String email, Map<String, Object> out) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(email)) {
			Integer i=authService.countUserByEmail(email);
			if (i== null || i.intValue()<=0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}
	
	/**
	 * 注册页面检验手机是否重复
	 * @param mobile
	 * @param out
	 * @return 返回true 表示手机没有重复,当前注册客户可以使用改电话号码
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView regMobile(String mobile, Map<String, Object> out) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(mobile)) {
			Integer i=companyAccountService.countAccountOfMobile(mobile);
			if (i== null || i.intValue()<=0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}
	
	/**
	 * 注册页面检验帐号是否重复
	 * @param account
	 * @param out
	 * @return 返回true 表示用户没有重复,当前注册用户可以正常使用
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView regAccount(String account, Map<String, Object> out) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(account)) {
			Integer i=authService.countUserByAccount(account);
			if (i== null || i.intValue()<=0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}
	
	
	/**
	 * 验证公司信息是否完善
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping     
	public ModelAndView validateCompanyInfo(HttpServletRequest request,Map<String,Object>out) throws IOException{
		SsoUser	user=getCachedUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		Company dto = companyService.queryCompanyById(user.getCompanyId());
		//搜索邮箱
		//搜索邮箱
		CompanyAccount account=companyAccountService.queryAccountByAccount(user.getAccount());
		
		//String checkInfo=null;
		do{
			if(dto==null){
				map.put("success", false);
				break;
			}
			//邮箱
			//检索邮箱
			if(StringUtils.isEmpty(account.getEmail()) ){
				map.put("success", false);  
				break;
			}
			// 公司名
			if(StringUtils.isEmpty(dto.getName())){  
				map.put("success", false);
				break;
			}
			// 地址
			if(StringUtils.isEmpty(dto.getAddress())){
				map.put("success", false);
				break;
			}
			// 国家地区
			if(StringUtils.isEmpty(dto.getAreaCode())){
				map.put("success", false);
				break;
			}
			// 主营行业
			if(StringUtils.isEmpty(dto.getIndustryCode())){
				map.put("success", false);
				break;
			}
			// 公司类型
			if(StringUtils.isEmpty(dto.getServiceCode())){
				map.put("success", false);
				break;
			}
			// 主营业务
			if(StringUtils.isEmpty(dto.getBusiness())){
				map.put("success", false);
				break;
			}
			// 公司简介
			if(StringUtils.isEmpty(dto.getIntroduction())){
				map.put("success", false);
				break;
			}
			
		}while(false);
		
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView validateWXCode(Map<String, Object> out,String yzm,String account) throws IOException{
		ExtResult result = new ExtResult();
		result.setSuccess(oauthAccessService.validateWXCode(yzm, account));
		return printJson(result, out);
	}
	
}
