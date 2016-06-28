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
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.company.CrmServiceApply;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.company.CrmSvrApplyService;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class ZstController extends BaseController{
	
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CrmCsService crmCsService;
	@Resource
	private CrmSvrApplyService crmSvrApplyService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private CreditIntegralDetailsService creditIntegralDetailsService;
	

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView open(HttpServletRequest request, Map<String, Object> out, String callbackCode, String callbackData){
		Map<String, Object> dataMap=(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(callbackData), Map.class);
		Integer companyId=(Integer) dataMap.get("companyId");
		Integer companySvrId=(Integer) dataMap.get("companySvrId");
		
		CrmCompanySvr crmCompanySvr = crmCompanySvrService.queryRecentHistory("1000", companyId, companySvrId);
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
		
		CrmCs cs=crmCsService.queryCsOfCompany(companyId);
		if(cs!=null){
			dataMap.put("csAccount", cs.getCsAccount());
			if (StringUtils.isNotEmpty(cs.getCsAccount())) {
				dataMap.put("csName", AuthUtils.getInstance().queryStaffNameOfAccount(cs.getCsAccount()));
			}
		}
		
		CrmCompanySvr svr = crmCompanySvrService.queryCompanySvrById(companySvrId);
		if(svr!=null){
			dataMap.put("svr", svr);
			CrmServiceApply apply=crmSvrApplyService.queryApplyByGroup(svr.getApplyGroup());
			if(apply!=null && apply.getAmount()!=null ){
				dataMap.put("integral", (apply.getAmount()/100));
			}
		}
		
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
		
		Boolean r=crmCompanySvrService.openZstSvr(svr, domainZz91, membershipCode);
		
		ExtResult result = new ExtResult();
		result.setSuccess(r);
		
		if(result.isSuccess()){
			if("0".equals(svr.getApplyStatus())){
				//TODO 变更积分和诚信值
				ScoreChangeDetailsDo details=new ScoreChangeDetailsDo();
				details.setRulesCode("service_vip_zst");
				
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(svr.getCompanyId(), null,
						"service_vip_zst", integral, svr.getId(), null));
				
				Integer creditIntegral = CreditIntegralDetailsService.OPERATION_INTEGRAL.get("service_zst_year") * svr.getZstYear();
				creditIntegralDetailsService.saveIntegeral(svr.getCompanyId(), "service_zst_year", svr.getId(), creditIntegral);
			}
			
			crmCsService.reassign(oldCsAccount, csAccount, svr.getCompanyId());
			
//			//TODO 发送短信或邮件通知
//			if(noticeSms!=null && noticeSms){
//				//发送短信通知
//			}
//			if(noticeEmail!=null && noticeSms){
//				//发送邮件通知
//			}
		}
		
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
		companyService.updateMembershipCode("10051000", companyId);
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
		
//		CrmCompanySvr crmCompanySvr = crmCompanySvrService.queryRecentHistory("1000", companyId, companySvrId);
//		if(crmCompanySvr!=null){
//			if(crmCompanySvr.getGmtStart()!=null){
//				dataMap.put("gmtPreStart", crmCompanySvr.getGmtStart().getTime());
//			}
//			if(crmCompanySvr.getGmtEnd()!=null){
//				dataMap.put("gmtPreEnd", crmCompanySvr.getGmtEnd().getTime());
//			}
//		}
		
		Company company=companyService.querySimpleCompanyById(companyId);
		dataMap.put("membershipCode", company.getMembershipCode());
		dataMap.put("domainZz91", company.getDomainZz91());
		
//		CrmCs cs=crmCsService.queryCsOfCompany(companyId);
//		if(cs!=null){
//			dataMap.put("csAccount", cs.getCsAccount());
//			if (StringUtils.isNotEmpty(cs.getCsAccount())) {
//				dataMap.put("csName", AuthUtils.getInstance().queryStaffNameOfAccount(cs.getCsAccount()));
//			}
//		}
		
		CrmCompanySvr svr = crmCompanySvrService.queryCompanySvrById(companySvrId);
		if(svr!=null){
			dataMap.put("svr", svr);
//			CrmServiceApply apply=crmSvrApplyService.queryApplyByGroup(svr.getApplyGroup());
//			if(apply.getAmount()!=null ){
//				dataMap.put("integral", (apply.getAmount()/100));
//			}
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
		
		Boolean r=crmCompanySvrService.updateZstSvr(svr, domainZz91, membershipCode);
		
		ExtResult result = new ExtResult();
		result.setSuccess(r);
		
//		if(result.isSuccess()){
//			if("0".equals(svr.getApplyStatus())){
//				//TODO 变更积分和诚信值
//				ScoreChangeDetailsDo details=new ScoreChangeDetailsDo();
//				details.setRulesCode("service_vip_zst");
//				
//				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(svr.getCompanyId(), null,
//						"service_vip_zst", integral, svr.getId(), null));
//				
//				Integer creditIntegral = CreditIntegralDetailsService.OPERATION_INTEGRAL.get("service_zst_year") * svr.getZstYear();
//				creditIntegralDetailsService.saveIntegeral(svr.getCompanyId(), "service_zst_year", svr.getId(), creditIntegral);
//			}
			
//			crmCsService.reassign(oldCsAccount, csAccount, svr.getCompanyId());
			
//			//TODO 发送短信或邮件通知
//			if(noticeSms!=null && noticeSms){
//				//发送短信通知
//			}
//			if(noticeEmail!=null && noticeSms){
//				//发送邮件通知
//			}
//		}
		
		return printJson(result, out);
	}
}
