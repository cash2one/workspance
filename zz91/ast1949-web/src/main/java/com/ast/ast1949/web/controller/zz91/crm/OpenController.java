/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-20
 */
package com.ast.ast1949.web.controller.zz91.crm;

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

import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmServiceApply;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CrmCompanySvrDto;
import com.ast.ast1949.dto.products.ProductsKeywordsRankDTO;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmSvrApplyService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.products.ProductsKeywordsRankService;
import com.ast.ast1949.web.controller.BaseController;
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
	private CompanyService companyService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CrmSvrService crmSvrService;
	@Resource
	private CrmSvrApplyService crmSvrApplyService;
	@Resource
	private ProductsKeywordsRankService productsKeywordsRankService;

	/**
	 * 销售人员访问这个页面给客户申请开通服务
	 */
	@RequestMapping
	public ModelAndView apply(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView applyQueryCompany(Map<String, Object> out,HttpServletRequest request, PageDto<CompanyDto> page, String email) throws IOException{
		page.setRecords(companyService.queryCompanyByEmail(email));
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView applyQuerySvrHistory(Map<String, Object> out,HttpServletRequest request, Integer companyId) throws IOException{
		List<CrmCompanySvrDto> list =crmCompanySvrService.queryCompanySvr(companyId, null) ;
		//搜索公司是否有标王服务
		List<ProductsKeywordsRankDTO> list2=productsKeywordsRankService.queryProductsKeywordsRankByCompanyId(companyId);
		for(ProductsKeywordsRankDTO obj :list2){
			CrmCompanySvrDto crmCompanySvrDto=new CrmCompanySvrDto();
			CrmCompanySvr  crmCompanySvr=new CrmCompanySvr(); 
			crmCompanySvrDto.setKeywords(obj.getProductsKeywordsRank().getName());
			crmCompanySvrDto.setSvrName(obj.getLabel());
			crmCompanySvr.setGmtStart(obj.getProductsKeywordsRank().getStartTime());
			crmCompanySvr.setGmtEnd(obj.getProductsKeywordsRank().getEndTime());
			crmCompanySvrDto.setCrmCompanySvr(crmCompanySvr);
			//crmCompanySvrDto.getCrmCompanySvr().setCompanyId(obj.getProductsKeywordsRank().getCompanyId());
			list.add(crmCompanySvrDto);
		}
		for (CrmCompanySvrDto dto:list) {
			CrmServiceApply obj = crmSvrApplyService.queryApplyByGroup(dto.getCrmCompanySvr().getApplyGroup());
			if (obj==null||obj.getAmount()==null) {
				continue;
			}
			dto.setAmount(""+obj.getAmount()/100);
		}
		
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView applyQuerySvr(Map<String, Object> out,HttpServletRequest request, String svrgroup) throws IOException{
		//TODO 按照svr group查找
		return printJson(crmSvrService.querySvrByGroup(svrgroup), out);
	}
	
	final static String CS_DEPT_CODE="10001005";
	
	@RequestMapping
	public ModelAndView applyQueryCs(Map<String, Object> out,HttpServletRequest request) throws IOException{
		Map<String, String> map=AuthUtils.getInstance().queryStaffOfDept(CS_DEPT_CODE);
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		for(String k:map.keySet()){
			Map<String, String> m = new HashMap<String, String>();
			m.put("account", k);
			m.put("name", map.get(k));
			list.add(m);
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView applySubmit(Map<String, Object> out,HttpServletRequest request, 
			Integer companyId, String svrCodeArr, CrmServiceApply apply, 
			String gmtIncomeDate, String svrgroup) throws IOException{
		try {
			apply.setGmtIncome(DateUtil.getDate(gmtIncomeDate, "yyyy-M-d"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Boolean r = crmCompanySvrService.applySvr(companyId, svrCodeArr.split(","), apply, svrgroup);
		ExtResult result = new ExtResult();
		if(r!=null){
			result.setSuccess(r);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryCompanyByGroup(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView clearApply(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryApplyCompany(Map<String, Object> out, HttpServletRequest request, 
			String svrCode, String applyStatus,String email,Integer companyId, PageDto<CrmCompanySvrDto> page) throws IOException{
//		PageDto<CrmCompanySvrDto> page=new PageDto<CrmCompanySvrDto>();
//		page.setRecords(crmCompanySvrService.queryApplyCompany(svrCode, applyStatus));
		page = crmCompanySvrService.pageApplyCompany(svrCode, applyStatus,email,companyId, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out, HttpServletRequest request, String applyGroup, Integer companyId){
		out.put("applyGroup", applyGroup);
		out.put("companyId", companyId);
		Integer i=crmCompanySvrService.countOpenedApplyByGroup(applyGroup);
		if(i!=null && i.intValue()>0){
			out.put("openedFlag", "1");
		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryApplySvr(Map<String, Object> out, HttpServletRequest request, String applyGroup) throws IOException{
		List<CrmCompanySvrDto> list=crmCompanySvrService.queryApplyByGroup(applyGroup);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView querySvrById(Map<String, Object> out, HttpServletRequest request, Integer id) throws IOException{
		List<CrmCompanySvr> list=new ArrayList<CrmCompanySvr>();
		CrmCompanySvr csvr=crmCompanySvrService.queryCompanySvrById(id);
		list.add(csvr);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView querySvrHistory(Map<String, Object> out, HttpServletRequest request, Integer companyId, String svrCode) throws IOException{
		List<CrmCompanySvr> list=crmCompanySvrService.querySvrHistory(companyId, svrCode);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryApplyForm(Map<String, Object> out, HttpServletRequest request, String applyGroup) throws IOException{
		CrmServiceApply apply=crmSvrApplyService.queryApplyByGroup(applyGroup);
		List<CrmServiceApply> list=new ArrayList<CrmServiceApply>();
		list.add(apply);
		return printJson(list, out);
	}
	
	@Deprecated
	@RequestMapping
	public ModelAndView updateApplySvr(Map<String, Object> out, HttpServletRequest request, CrmCompanySvr svr, 
			String gmtPreStartDate, String gmtPreEndDate, String gmtStartDate, String gmtEndDate, String gmtSignedDate) throws IOException{
		try {
			if(StringUtils.isNotEmpty(gmtPreStartDate)){
				svr.setGmtPreStart(DateUtil.getDate(gmtPreStartDate, "yyyy-M-d"));
			}
			if(StringUtils.isNotEmpty(gmtPreEndDate)){
				svr.setGmtPreEnd(DateUtil.getDate(gmtPreEndDate, "yyyy-M-d"));
			}
			svr.setGmtSigned(DateUtil.getDate(gmtSignedDate, "yyyy-M-d"));
			svr.setGmtStart(DateUtil.getDate(gmtStartDate, "yyyy-M-d"));
			svr.setGmtEnd(DateUtil.getDate(gmtEndDate, "yyyy-M-d"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Integer i=crmCompanySvrService.updateSvrById(svr);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@Deprecated
	@RequestMapping
	public ModelAndView openSvr(Map<String, Object> out, HttpServletRequest request, 
			String applyGroup, CrmServiceApply apply, String csAccount, Integer companyId, String gmtIncomeDate) throws IOException{
		try {
			apply.setGmtIncome(DateUtil.getDate(gmtIncomeDate, "yyyy-M-d"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ExtResult result = new ExtResult();
//		result.setSuccess(crmCompanySvrService.openSvr(applyGroup, apply, csAccount, companyId));
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView refusedApply(Map<String, Object> out, HttpServletRequest request, String applyGroup) throws IOException{
		ExtResult result = new ExtResult();
		result.setSuccess(crmCompanySvrService.refusedApply(applyGroup));
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView saveApply(HttpServletRequest request, Map<String, Object> out, CrmServiceApply apply) throws IOException{
		Integer i=crmSvrApplyService.updateApply(apply);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
