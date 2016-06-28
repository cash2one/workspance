/**
 * 
 */
package com.ast.ast1949.web.controller.zz91.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/** 商铺服务 开通 页面 controller
 * @author kongsj
 * @date 2012-07-30
 */
@Controller
public class SpsvrController extends BaseController{
	
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CompanyService companyService;
	

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView open(HttpServletRequest request, Map<String, Object> out, String callbackCode, String callbackData){
		Map<String, Object> dataMap=(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(callbackData), Map.class);
		Integer companyId=(Integer) dataMap.get("companyId");
		Integer companySvrId=(Integer) dataMap.get("companySvrId");
		
		CrmCompanySvr crmCompanySvr = crmCompanySvrService.queryRecentHistory(CrmCompanySvrService.ESITE_CODE, companyId, companySvrId);
		if(crmCompanySvr!=null){
			if(crmCompanySvr.getGmtStart()!=null){
				dataMap.put("gmtPreStart", crmCompanySvr.getGmtStart().getTime());
			}
			if(crmCompanySvr.getGmtEnd()!=null){
				dataMap.put("gmtPreEnd", crmCompanySvr.getGmtEnd().getTime());
			}
		}
		
		Company company=companyService.querySimpleCompanyById(companyId);
		dataMap.put("membershipCode", company.getMembershipCode());
		dataMap.put("domainZz91", company.getDomainZz91());
		
		out.put("data", JSONObject.fromObject(dataMap).toString());
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView doOpen(HttpServletRequest request, Map<String, Object> out, CrmCompanySvr svr, 
			String gmtPreStartDate, String gmtPreEndDate, String gmtStartDate, String gmtEndDate, String gmtSignedDate, 
			String domainZz91, String membershipCode, Integer integral, Boolean noticeSms, 
			Boolean noticeEmail, String csAccount, String oldCsAccount) throws IOException{
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
		
		Boolean r=crmCompanySvrService.openSPSvr(svr, domainZz91);
		
		ExtResult result = new ExtResult();
		result.setSuccess(r);
		
		return printJson(result, out);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView close(HttpServletRequest request, Map<String, Object> out, String callbackCode, String callbackData){
		Map<String, Object> dataMap=(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(callbackData), Map.class);
		out.put("companyId", dataMap.get("companyId"));
		out.put("companySvrId",dataMap.get("companySvrId"));
		return null;
	}
	
	@RequestMapping
	public ModelAndView doClose(HttpServletRequest request, Map<String, Object> out,
			Integer companyId, Integer companySvrId){
//		companyService.updateMembershipCode("10051000", companyId);
		Boolean result=crmCompanySvrService.closeSvr(companySvrId);
		if(result != null && result){
			out.put("result", 1);
		}else{
			out.put("result", 0);
		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView validateDomain(HttpServletRequest request, Map<String, Object> out, 
			Integer companyId, String domainZz91) throws IOException{
		ExtResult result = new ExtResult();
		result.setSuccess(companyService.validateDomainZz91(companyId, domainZz91));
		return printJson(result, out);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView change(HttpServletRequest request, Map<String, Object> out, String callbackCode, String callbackData){
		Map<String, Object> dataMap=(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(callbackData), Map.class);
		Integer companyId=(Integer) dataMap.get("companyId");
		Integer companySvrId=(Integer) dataMap.get("companySvrId");
		
		
		Company company=companyService.querySimpleCompanyById(companyId);
		dataMap.put("membershipCode", company.getMembershipCode());
		dataMap.put("domainZz91", company.getDomainZz91());
		
		
		CrmCompanySvr svr = crmCompanySvrService.queryCompanySvrById(companySvrId);
		if(svr!=null){
			dataMap.put("svr", svr);
		}
		
		out.put("data", JSONObject.fromObject(dataMap).toString());
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView doChange(HttpServletRequest request, Map<String, Object> out, CrmCompanySvr svr, 
			String gmtPreStartDate, String gmtPreEndDate, String gmtStartDate, String gmtEndDate, String gmtSignedDate, 
			String domainZz91, String membershipCode, Integer integral, Boolean noticeSms, 
			Boolean noticeEmail, String csAccount, String oldCsAccount) throws IOException{
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
		
		Boolean r=crmCompanySvrService.updateSPSvr(svr, domainZz91);
		
		ExtResult result = new ExtResult();
		result.setSuccess(r);
		
		return printJson(result, out);
	}
}
