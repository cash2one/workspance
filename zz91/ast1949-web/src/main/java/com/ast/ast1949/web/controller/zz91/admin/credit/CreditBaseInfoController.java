package com.ast.ast1949.web.controller.zz91.admin.credit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAttestDto;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.velocity.AddressTool;
/***
 * 
 * @author zhozuk
 *  @deprecated 2014-1-8
 *  用于工商或个人认证信息
 */
@Controller
public class CreditBaseInfoController extends BaseController{

	@Resource
	private CompanyAttestService companyAttestService;
	@Resource
	private CompanyService companyService;
	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view(Map<String, Object> model) {
	}
	
	/**
	 * 读取列表
	 * 
	 * @param page
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView list(PageDto<CompanyAttestDto> page,
			Map<String, Object> model,CompanyAttest companyAttest , String compName) throws IOException {
		if (page == null) {
			page = new PageDto<CompanyAttestDto>(AstConst.PAGE_SIZE);
		}
		page.setSort("id");
		page.setDir("desc");


		page = companyAttestService.pageCompanyAttest(companyAttest, compName, page);

		return printJson(page, model);
	}
	
	@RequestMapping
	public ModelAndView edit(String id, String activeFlg, String type,Map<String, Object> model) {
		//用于激活选项卡的位置
		if ("1".equals(activeFlg)) {
			model.put("activeFlg", activeFlg);
		} else {
			model.put("activeFlg", "0");
		}
		//来自证书管理的认证管理
		if ("1".equals(type)) {
			model.put("type", type);
		} else {
			model.put("type", "0");
		}
		CompanyAttest attest = companyAttestService.queryAttestById(Integer.valueOf(id));
		model.put("id", id);
		model.put("cid", attest.getCompanyId());
		if ("0".equals(attest.getAttestType())) {
			 return new ModelAndView("redirect:editGeTi.htm");
		} else {
			 return new ModelAndView("redirect:editGongShang.htm");
		}
 	}
	@RequestMapping
	public void editGeTi(String id, String cid, String activeFlg, String type,Map<String, Object> model) {
		model.put("id", id);
		model.put("cid", cid);
		model.put("activeFlg", activeFlg);
		model.put("type", type);
 	}
	@RequestMapping
	public void editGongShang(String id,String cid, String activeFlg, String type,Map<String, Object> model) {
		model.put("id", id);
		model.put("cid", cid);
		model.put("activeFlg", activeFlg);
		model.put("type", type);
 	}
	
	@RequestMapping
	public ModelAndView init(Integer id, Map<String, Object> model) throws IOException {
		
		CompanyAttest  companyAttest =  companyAttestService.queryAttestById(id);
		List<CompanyAttest> list  =  new ArrayList<CompanyAttest>();
		list.add(companyAttest);
		return printJson(list, model);
 	}
	//进入门市部的诚信档案
		@RequestMapping
		public ModelAndView goEsite(HttpServletRequest request,
				Map<String, Object> out, Integer companyId) {
			if (companyId == null) {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("front"));
			}
			Company company = companyService.queryDomainOfCompany(companyId);
			if (StringUtils.isNotEmpty(company.getDomainZz91())) {
				return new ModelAndView("redirect:http://"+company.getDomainZz91()+".zz91.com/cxda.htm");
			} else {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("front"));
			}
			
		}
	/**
	 * 修改审核状态
	 * @param model
	 * @param ids 记录编号
	 * @param checkStatus 状态值
	 * @param currents 当前状态
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "updateCheckStatus.htm", method = RequestMethod.GET)
	public ModelAndView updateCheckStatus(Map<String, Object> model, String ids,
			String checkStatus,String currents, HttpServletRequest request)
			throws IOException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		if (StringUtils.isEmpty(checkStatus)) {
			checkStatus = "0"; // 默认的审核状态
		}
		// 验证该人员是否有审核权限
		if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
			result.setData("没有权限");
			result.setSuccess(false);
			return printJson(result, model);
		}
		
		if (currents!=null&&currents.equals(checkStatus)) {
			result.setSuccess(true);
		} else {
			if (StringUtils.isNumber(ids)) {
					Integer affected = companyAttestService.updateCheckStatus(Integer.valueOf(ids), checkStatus, sessionUser.getAccount());
					if (affected != null && affected.intValue() > 0) {
						result.setSuccess(true);
					} else {
						result.setSuccess(false);
					}
				}
			}
		
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView updateAttest(Map<String, Object> model,CompanyAttest companyAttest, HttpServletRequest request)
			throws IOException {
		ExtResult result = new ExtResult();
		//仅仅是保存更新
		companyAttest.setPicAddress("");  // 设置为空，确保图片信息后台审核的时候无法修改
		Integer i  = companyAttestService.updateInfoByAdmin(companyAttest);
		if (i != null && i.intValue()>0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, model);
	}
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model,Integer id, HttpServletRequest request)
			throws IOException {
		ExtResult result = new ExtResult();
		//仅仅是保存更新
		Integer i  = companyAttestService.deleteCompanyAttest(id);
		if (i != null && i.intValue()>0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, model);
	}
	
}
