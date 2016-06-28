/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.CompanyCustomersDO;
import com.ast.ast1949.domain.company.CompanyCustomersGroupDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.company.CompanyCustomersGroupService;
import com.ast.ast1949.service.company.CompanyCustomersService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.MyrcService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Controller
public class MycompanyCustomersController extends BaseController {

//	@Autowired
//	private CompanyService companyService;
	@Autowired
	private CompanyCustomersService companyCustomersService;
	@Autowired
	private CompanyCustomersGroupService companyCustomersGroupService;
//	@Autowired
//	private CategoryService categoryService;

//	Boolean success = true;
	@Resource
	private MyrcService myrcService;
	@Resource
	private CompanyService companyService;
	
	
	/**
	 * <pre>
	 * 三种查询情况：
	 * 1 、 分组ID为空，该公司的所有客户：ALL
	 * 2 、 分组ID为空，客户自定义分组中的所有客户：OTHER
	 * 3 、 根据分组ID查询该分组下的所有客户（0 未分组，1 供求商，2 采购商，3 商界好友 ,XX 自定义分组ID）
	 * 以下为公有的查询条件： 联系人，公司名称，电话，手机
	 * </pre>
	 * 
	 * 客户以公司做为关联条件，而非帐号
	 * @param group
	 * @param request
	 * @param companyCustomersDO
	 * @param out
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping
	public void list(String group,  PageDto page,
			CompanyCustomersDO customer, Integer error,
			HttpServletRequest request, Map<String, Object> out) {
		
		//处理查询条件
		try {
			if(StringUtils.isNotEmpty(customer.getCompany())){
				customer.setCompany(StringUtils.decryptUrlParameter(customer.getCompany()));
			}
			if(StringUtils.isNotEmpty(customer.getName())){
				customer.setName(StringUtils.decryptUrlParameter(customer.getName()));
			}
		} catch (UnsupportedEncodingException e) {
		}
		
		
		try {
			if(customer!=null && customer.getCompany()!=null){
				out.put("companyEncode", URLEncoder.encode(customer.getCompany(), HttpUtils.CHARSET_UTF8));
			}
			if(customer!=null && customer.getName()!=null){
				out.put("nameEncode", URLEncoder.encode(customer.getName(), HttpUtils.CHARSET_UTF8));
			}
		} catch (UnsupportedEncodingException e) {
		}
		
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		//自定义客户分组列表
		List<CompanyCustomersGroupDO> customersGroupList = companyCustomersGroupService
				.queryCompanyCustomersGroupByCompanyId(sessionUser.getCompanyId());
		out.put("customersGroupList", customersGroupList);
		
		customer.setCompanyId(sessionUser.getCompanyId());
		page = companyCustomersService
				.queryCompanyCustomerListByCompanyIdAndGroupId(customer, page);
		out.put("customer", customer);
		out.put("page", page);
		
		out.put("error", error);
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request, Map<String, Object> out, 
			String ids) {

		Integer i = companyCustomersService.batchDeleteCustomerById(StringUtils.StringToIntegerArray(ids));
		out.put("error", 1);
		if(i!=null && i.intValue()>0){
			out.put("error", 0);
		}
		return new ModelAndView("redirect:list.htm");
	}

	/**
	 * 查询可以导入的客户信息列表
	 * 
	 * @param out
	 * @param request
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView importCustomerFromInquiry(Map<String, Object> out,
//			HttpServletRequest request) throws IOException {
//		SsoUser sessionUser = getCachedUser(request);
//		List<CompanyCustomersDO> companyCustomerList = companyCustomersService
//				.queryCompanyCustomersForImportByInquiry(sessionUser.getCompanyId());
//		companyCustomersService.insertCompanyCustomersForImport(sessionUser.getAccount(), companyCustomerList);
//		ExtResult result = new ExtResult();
//		result.setData(companyCustomerList);
//		return printJson(result, out);
//	}

	@RequestMapping
	public void saveCompanyCustomers(Integer id, Integer error, Map<String, Object> out, HttpServletRequest request) {

		if (id != null) {
//			out.put(FrontConst.MYRC_SUBTITLE, "添加客户");
			CompanyCustomersDO companyCustomersDO = companyCustomersService
					.queryCompanyCustomersById(id);
//			if (companyCustomersDO.getAreaCode() != null) {
//				if (companyCustomersDO.getAreaCode().length() > 4) {
//					out.put("ccode", companyCustomersDO.getAreaCode());
//					out.put("pcode", companyCustomersDO.getAreaCode().substring(0,
//							companyCustomersDO.getAreaCode().length() - 4));
//				}
//			}
//			out.put(FrontConst.MYRC_SUBTITLE, "客户详细信息");
			out.put("companyCustomers", companyCustomersDO);
		}
		SsoUser sessionUser=getCachedUser(request); 
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		List<CompanyCustomersGroupDO> customersGroupList = companyCustomersGroupService
				.queryCompanyCustomersGroupByCompanyId(sessionUser.getCompanyId());
		out.put("customersGroupList", customersGroupList);
		
		out.put("error", error);
		
//		out.put("nationList", categoryService.queryCategoriesByPreCode(AstConst.NATION_CODE));
	}

	@RequestMapping
	public ModelAndView save(CompanyCustomersDO companyCustomersDO, Integer id,
			Map<String, Object> out, HttpServletRequest request) {
		SsoUser sessionUser = getCachedUser(request);
		companyCustomersDO.setCompanyId(sessionUser.getCompanyId());
		companyCustomersDO.setAccount(sessionUser.getAccount());
		
//		if (companyCustomersDO.getAreaCode() == null || companyCustomersDO.getAreaCode().equals("")) {
//			companyCustomersDO.setAreaCode(fAreaCode);
//		}
		
		Integer i=null;
		if (id == null) {
			i = companyCustomersService.insertCompanyCustomers(companyCustomersDO);
		} else {
			i = companyCustomersService.updateCompanyCustomers(companyCustomersDO);
		}

		if(i!=null && i.intValue()>0){
			out.put("error", 0);
			return new ModelAndView("redirect:list.htm");
		}else{
			out.put("error", 1);
			out.put("id", id);
			return new ModelAndView("redirect:saveCompanyCustomers.htm");
		}
		
	}

	@RequestMapping
	public ModelAndView groupList(Map<String, Object> out, HttpServletRequest request,
			Integer error) {
//		Boolean isShowRadio = false;
//		if (StringUtils.isNotEmpty(ids)) {
//			isShowRadio = true;
//			out.put("ids", ids);
//		}
		SsoUser sessionUser = getCachedUser(request);
		List<CompanyCustomersGroupDO> list = companyCustomersGroupService
				.queryCompanyCustomersGroupByCompanyId(sessionUser.getCompanyId());
		out.put("groupList", list);
		out.put("error", error);
//		out.put("isShowRadio", isShowRadio);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView groupCustomer(Map<String, Object> out, HttpServletRequest request,
			String ids, Integer error){
		out.put("ids", ids);
		out.put("error", error);
		
		SsoUser sessionUser = getCachedUser(request);
		List<CompanyCustomersGroupDO> list = companyCustomersGroupService
				.queryCompanyCustomersGroupByCompanyId(sessionUser.getCompanyId());
		out.put("groupList", list);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView doGroupCustomer(Map<String, Object> out, HttpServletRequest request,
			String ids, Integer groupId){
		companyCustomersService.batchUpdateGroupById(ids, groupId);
		return new ModelAndView("redirect:"+request.getContextPath()+"/submitCallback.htm");
	}
	
	@RequestMapping
	public ModelAndView doUnGroupCustomer(Map<String, Object> out, HttpServletRequest request,
			String ids) throws IOException{
		Integer[] customerIds=StringUtils.StringToIntegerArray(ids);
		Integer impact=0;
		for(Integer id:customerIds){
			Integer i = companyCustomersService.updateCustomersGroup(0, id);
			if(i!=null && i.intValue()>0){
				impact++;
			}
		}
		ExtResult result = new ExtResult();
		if(impact>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView doEditGroup(HttpServletRequest request, 
			CompanyCustomersGroupDO companyCustomersGroupDO, Map<String, Object> out) {
		SsoUser sessionUser = getCachedUser(request);
		companyCustomersGroupDO.setCompanyId(sessionUser.getCompanyId());
		Integer i=null;
		if (companyCustomersGroupDO.getId() == null) {
			i=companyCustomersGroupService.insertCompanyCustomersGroup(companyCustomersGroupDO);
		} else {
			i=companyCustomersGroupService.updateCompanyCustomersGroup(companyCustomersGroupDO);
		}
		
		if(i!=null && i.intValue()>0){
			out.put("error",0);
			return new ModelAndView(new RedirectView("groupList.htm"));
		}
		out.put("id", companyCustomersGroupDO.getId());
		out.put("error", 1);
		return new ModelAndView(new RedirectView("redirect:editGroup.htm"));
	}
	
	@RequestMapping
	public ModelAndView editGroup(HttpServletRequest request, Map<String, Object> out, 
			Integer id, Integer error){
		if(id!=null && id.intValue()>0){
			out.put("group", companyCustomersGroupService.queryGroupById(id));
		}
		out.put("error", error);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView deleteGroup(HttpServletRequest request, Map<String, Object> out,
			Integer id) {
		companyCustomersGroupService.deleteCompanyCustomersGroupById(id);
		//删除该组下的所有客戶
//		companyCustomersService.updateCompanyCustomersByGroupId(0, id);
		out.put("error", 0);
		return new ModelAndView(new RedirectView("groupList.htm"));
	}
	
	
	
	
	
	

//	@RequestMapping
//	public void fenzu(Integer id, Map<String, Object> out, HttpServletRequest request) {
//		SsoUser sessionUser = getCachedUser(request);
//		if (id != null) {
//			CompanyCustomersGroupDO companyCustomersGroup = companyCustomersGroupService
//					.queryGroupById(id);
//			//未分组的情况下，页面分组初始化为空。
//			if(companyCustomersGroup!=null) {
//				out.put("name", companyCustomersGroup.getName());
//			}
//		}
//
//		List<CompanyCustomersGroupDO> list = companyCustomersGroupService
//				.queryCompanyCustomersGroupByCompanyId(sessionUser.getCompanyId());
//		out.put("groupList", list);
//	}

//	@RequestMapping
//	public void updateGroup(Integer companyCustomersGroupId, String ids, Map<String, Object> out) {
//
//		companyCustomersService.batchUpdateGroupById(ids, companyCustomersGroupId);
//		out.put("result", "归入成功");
//		out.put("link", "list.htm");
//	}



//	@RequestMapping
//	public ModelAndView addCompanyToCustomers(Integer id, Map<String, Object> model)
//			throws IOException {
//		ExtResult result = new ExtResult();
//		if (id != null) {
//			CompanyCustomersDO companyCustomers = companyCustomersService
//					.queryCompanyCustomersById(id);
//			if (companyCustomers == null) {
//				CompanyDO companyDO = companyService.selectCompanyById(id);
//				CompanyContactsDO companyContactsDO = companyService
//						.selectDefaultContactsById(companyDO.getId());
//				Integer i = companyCustomersService.insertCompanyToCustomers(companyDO,
//						companyContactsDO);
//				if (i <= 0) {
//					success = false;
//				}
//			}
//			result.setSuccess(success);
//			return printJson(result, model);
//
//		} else {
//			return new ModelAndView("register/Register");
//		}
//	}

//	@RequestMapping
//	public void fenpei() {
//
//	}
}
