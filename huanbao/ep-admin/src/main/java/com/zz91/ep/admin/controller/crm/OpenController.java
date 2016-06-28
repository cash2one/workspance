/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-20
 */
package com.zz91.ep.admin.controller.crm;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.comp.CompProfileService;
import com.zz91.ep.admin.service.crm.CRMCompSvrService;
import com.zz91.ep.admin.service.crm.CRMSvrApplyService;
import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.domain.crm.CrmSvrApply;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.crm.CrmCompSvrDto;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * CRM中服务开通有关的服务
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-20
 */
@Controller
public class OpenController extends BaseController{
	
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private CRMCompSvrService crmCompSvrService;
//	@Resource
//	private CRMSvrService crmSvrService;
	@Resource
	private CRMSvrApplyService crmSvrApplyService;

	/**
	 * 销售人员访问这个页面给客户申请开通服务
	 */
	@RequestMapping
	public ModelAndView apply(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	/**
	 * 通过邮箱查询公司
	 * @param out
	 * @param request
	 * @param page
	 * @param email
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView applyQueryCompany(Map<String, Object> out,HttpServletRequest request, PageDto<CompProfileDto> page, String email) throws IOException{
		page.setRecords(compProfileService.queryCompanyByEmail(email));
		return printJson(page, out);
	}
	
	/*@RequestMapping
	public ModelAndView applyQuerySvrHistory(Map<String, Object> out,HttpServletRequest request, Integer cid) throws IOException{
		List<CrmCompSvrDto> list =crmCompSvrService.queryCompanySvr(cid, null) ;
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView applyQuerySvr(Map<String, Object> out,HttpServletRequest request, String svrgroup) throws IOException{
		//TODO 按照svr group查找
		return printJson(crmSvrService.querySvrByGroup(svrgroup), out);
	}
	*/
	final static String SALE_DEPT_CODE="100010071000";
	
	@RequestMapping
	public ModelAndView applyQueryCs(Map<String, Object> out,HttpServletRequest request) throws IOException{
		Map<String, String> map=AuthUtils.getInstance().queryStaffOfDept(SALE_DEPT_CODE);
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		for(String k:map.keySet()){
			Map<String, String> m = new HashMap<String, String>();
			m.put("account", k);
			m.put("name", map.get(k));
			list.add(m);
		}
		return printJson(list, out);
	}
	
	/**
	 * 提交服务申请单
	 * @param out
	 * @param request
	 * @param cid
	 * @param svrCodeArr
	 * @param apply
	 * @param gmtIncomeDate
	 * @param svrgroup
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView applySubmit(Map<String, Object> out,HttpServletRequest request, 
			Integer cid, String svrCodeArr, CrmSvrApply apply, 
			String gmtIncomeDate,String gmtStartDate,String gmtEndDate,String gmtSignedDate) throws IOException{
		apply.setSaleStaffName(AuthUtils.getInstance().queryStaffNameOfAccount(apply.getSaleStaff()));
		CrmCompSvr crmCompSvr = new CrmCompSvr();
		try {
			apply.setGmtIncome(DateUtil.getDate(gmtIncomeDate, "yyyy-M-d"));
			crmCompSvr.setGmtSigned(DateUtil.getDate(gmtSignedDate, "yyyy-M-d"));
			crmCompSvr.setGmtStart(DateUtil.getDate(gmtStartDate, "yyyy-M-d"));
			crmCompSvr.setGmtEnd(DateUtil.getDate(gmtEndDate, "yyyy-M-d"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Boolean isSucess = crmSvrApplyService.applySvr(cid, svrCodeArr.split(","), apply, crmCompSvr);
		ExtResult result = new ExtResult();
		if(isSucess!=null){
			result.setSuccess(isSucess);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryCompanyByGroup(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView clearApply(Map<String, Object> out, HttpServletRequest request,Short flag){
		if(flag==1) {
			out.put("account", getCachedUser(request).getAccount());
		}else {
			out.put("account", "");
		}
		out.put("flag", flag);
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryApplyCompany(Map<String, Object> out, HttpServletRequest request, 
			String svrCode, String applyStatus, PageDto<CrmCompSvrDto> page,String account) throws IOException{
		page = crmCompSvrService.pageApplyCompany(svrCode, applyStatus, page, account);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryApplySvrHistory(Map<String, Object> out, HttpServletRequest request, Integer cid) throws IOException{
		List<CrmCompSvrDto> list=crmCompSvrService.queryApplySvrHistory(cid);
		return printJson(list, out);
	}
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out, HttpServletRequest request, String applyGroup, Integer cid){
		out.put("applyGroup", applyGroup);
		out.put("cid", cid);
		Integer i=crmCompSvrService.countOpenedApplyByGroup(applyGroup);
		if(i!=null && i.intValue()>0){
			out.put("openedFlag", "1");
		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryApplySvr(Map<String, Object> out, HttpServletRequest request, String applyGroup) throws IOException{
		List<CrmCompSvrDto> list=crmCompSvrService.queryApplyByGroup(applyGroup);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryApplyForm(Map<String, Object> out, HttpServletRequest request, String applyGroup) throws IOException{
		CrmSvrApply apply=crmSvrApplyService.queryApplyByGroup(applyGroup);
		List<CrmSvrApply> list=new ArrayList<CrmSvrApply>();
		list.add(apply);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView querySvrHistory(Map<String, Object> out, HttpServletRequest request, Integer cid, Integer crmSvrId) throws IOException{
		List<CrmCompSvr> list=crmCompSvrService.querySvrHistory(cid, crmSvrId);
		return printJson(list, out);
	}
	/*
	@RequestMapping
	public ModelAndView querySvrById(Map<String, Object> out, HttpServletRequest request, Integer id) throws IOException{
		List<CrmCompSvr> list=new ArrayList<CrmCompSvr>();
		CrmCompSvr csvr=crmCompSvrService.queryCompanySvrById(id);
		list.add(csvr);
		return printJson(list, out);
	}
	
	
	
	*/
	
	@RequestMapping
	public ModelAndView updateApplySvr(Map<String, Object> out, HttpServletRequest request, CrmCompSvr crmCompSvr, 
			String gmtPreStartDate, String gmtPreEndDate, String gmtStartDate, String gmtEndDate, String gmtSignedDate) throws IOException{
		try {
			if(StringUtils.isNotEmpty(gmtPreStartDate)){
				crmCompSvr.setGmtPreStart(DateUtil.getDate(gmtPreStartDate, "yyyy-M-d"));
			}
			if(StringUtils.isNotEmpty(gmtPreEndDate)){
				crmCompSvr.setGmtPreEnd(DateUtil.getDate(gmtPreEndDate, "yyyy-M-d"));
			}
			crmCompSvr.setGmtSigned(DateUtil.getDate(gmtSignedDate, "yyyy-M-d"));
			crmCompSvr.setGmtStart(DateUtil.getDate(gmtStartDate, "yyyy-M-d"));
			crmCompSvr.setGmtEnd(DateUtil.getDate(gmtEndDate, "yyyy-M-d"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Integer i=crmCompSvrService.openCrmCompSvr(crmCompSvr);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@Deprecated
	@RequestMapping
	public ModelAndView openSvr(Map<String, Object> out, HttpServletRequest request, 
			String applyGroup, CrmSvrApply apply, String csAccount, Integer cid, String gmtIncomeDate) throws IOException{
		try {
			apply.setGmtIncome(DateUtil.getDate(gmtIncomeDate, "yyyy-M-d"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ExtResult result = new ExtResult();
//		result.setSuccess(crmCompanySvrService.openSvr(applyGroup, apply, csAccount, cid));
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView refusedApply(Map<String, Object> out, HttpServletRequest request, String applyGroup) throws IOException{
		ExtResult result = new ExtResult();
		result.setSuccess(crmCompSvrService.refusedApply(applyGroup));
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView assignApplyToCs(HttpServletRequest request, Map<String, Object> out, Integer id, String account) throws IOException{
		Integer i=crmSvrApplyService.assignApplyToCs(id, account);
    		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
