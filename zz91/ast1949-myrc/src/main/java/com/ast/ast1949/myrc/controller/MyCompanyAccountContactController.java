package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.CompanyAccountContact;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CompanyAccountContactService;
import com.ast.ast1949.service.company.MyrcService;
import com.zz91.util.auth.frontsso.SsoUser;

@Controller
public class MyCompanyAccountContactController extends BaseController {
	@Resource
	private CompanyAccountContactService companyAccountContactService;
	@Resource
	private MyrcService myrcService;
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,Map<String, Object> out,
			PageDto<CompanyAccountContact> page) {
		out.put(FrontConst.MYRC_SUBTITLE, "联系人列表");
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务或者百度优化
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		page.setPageSize(8);
		page.setDir("desc");
		page.setSort("gmt_modified");
		page = companyAccountContactService.pageContactByCompany(sessionUser.getAccount(), page,null);
		out.put("page", page);
		out.put("contactList", page.getRecords());
		return null;
	}
	
	@RequestMapping
	public ModelAndView createContact(HttpServletRequest request,
			Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "添加联系人信息");
		return null;
	}
	
	@RequestMapping
	public ModelAndView insertContact(HttpServletRequest request,
			Map<String, Object> out, CompanyAccountContact contact) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		contact.setAccount(sessionUser.getAccount());
		
		ExtResult result = new ExtResult();
		Integer i = companyAccountContactService.createContact(contact);
		if(i!=null && i>0){
			result.setSuccess(true);
		}
		return new ModelAndView(new RedirectView(request.getContextPath()+"/mycompanyaccountcontact/index.htm"));
	}
	
	@RequestMapping
	public ModelAndView editContact(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put(FrontConst.MYRC_SUBTITLE, "修改联系人信息");
		out.put("companyAccountContact", companyAccountContactService.queryOneContactById(id));
		return null;
	}
	
	@RequestMapping
	public ModelAndView deleteContact(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		companyAccountContactService.deleteContactByIdAndAccount(id, sessionUser.getAccount());
		return new ModelAndView(new RedirectView(request.getContextPath()+"/mycompanyaccountcontact/index.htm"));
	}
	
	@RequestMapping
	public ModelAndView updateContact(HttpServletRequest request,
			Map<String, Object> out, CompanyAccountContact contact) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		contact.setAccount(sessionUser.getAccount());
		
		ExtResult result = new ExtResult();
		Integer i = companyAccountContactService.updateContactById(contact);
		if(i!=null && i>0){
			result.setSuccess(true);
		}
		return new ModelAndView(new RedirectView(request.getContextPath()+"/mycompanyaccountcontact/index.htm"));
	}
}
