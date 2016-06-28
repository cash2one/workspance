/**
 * 
 */
package com.ast.ast1949.web.controller.zz91.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.company.CrmServiceApply;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.company.CrmSvrApplyService;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.sms.SmsUtil;

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
	private CompanyAccountService companyAccountService;
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
		
		// 获取查看最近是否存在再生通服务 获取其开始时间和结束时间
		CrmCompanySvr crmCompanySvr = crmCompanySvrService.queryRecentHistory(CrmCompanySvrService.ZST_CODE, companyId, companySvrId);
		if(crmCompanySvr!=null){
			if(crmCompanySvr.getGmtStart()!=null){
				dataMap.put("gmtPreStart", crmCompanySvr.getGmtStart().getTime());
			}
			if(crmCompanySvr.getGmtEnd()!=null){
				dataMap.put("gmtPreEnd", crmCompanySvr.getGmtEnd().getTime());
			}
			dataMap.put("zstNoExist", 0);
		}else{
			dataMap.put("zstNoExist", 1);
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
			Boolean noticeEmail, String csAccount, String oldCsAccount,Integer zstNoExist) throws IOException{
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
				// 变更积分和诚信值
				ScoreChangeDetailsDo details=new ScoreChangeDetailsDo();
				details.setRulesCode("service_vip_zst");
				
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(svr.getCompanyId(), null,"service_vip_zst", integral, svr.getId(), null));
				
				Integer creditIntegral = CreditIntegralDetailsService.OPERATION_INTEGRAL.get("service_zst_year") * svr.getZstYear();
				creditIntegralDetailsService.saveIntegeral(svr.getCompanyId(), "service_zst_year", svr.getId(), creditIntegral);
			}
			
			crmCsService.reassign(oldCsAccount, csAccount, svr.getCompanyId());
			
			CrmCompanySvr nSvr = crmCompanySvrService.queryCompanySvrById(svr.getId());
			if (nSvr!=null) {
				svr.setCrmServiceCode(nSvr.getCrmServiceCode());
			}
			
			// 获取客服信息
			String csInfo = ParamUtils.getInstance().getValue("cs_info", csAccount);
			
			// 获取操作员信息
			SessionUser ssoUser = getCachedUser(request);
			
			String[] csInfoArray = new String[]{} ;
			if (StringUtils.isNotEmpty(csInfo) && csInfo.indexOf(",")!=-1) {
				csInfoArray = csInfo.split(",");
			}
			LogUtil.getInstance().log(ssoUser.getAccount(), "open_vip", "", "cs_account:"+csAccount+"csname:"+csInfoArray[0]+"tel:"+csInfoArray[1]);
			
			if (zstNoExist!=null&&zstNoExist==1&& CrmCompanySvrService.ZST_CODE.equals(svr.getCrmServiceCode()) ) {
				
				// 获取公司电话和邮箱
				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(svr.getCompanyId());
				
				
				// 发送短信或邮件通知   尊敬的客户，您购买的会员服务已开通，用户名：{0}密码{1}，请妥善保管。如有问题请致电商务助理{2}({3}) 
				if(StringUtils.isNotEmpty(ca.getMobile()) && csInfoArray.length>=2 ){
					SmsUtil.getInstance().sendSms("zz91_vip_open", ca.getMobile(), null, null, new String[]{ca.getAccount(),ca.getPassword(),csInfoArray[0],csInfoArray[1]});
				}
				if(StringUtils.isNotEmpty(ca.getEmail()) && csInfoArray.length>=2 ){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("account", ca.getAccount());
					map.put("password", ca.getPassword());
					map.put("csName", csInfoArray[0]);
					map.put("csTel", csInfoArray[1]);
					MailUtil.getInstance().sendMail("开通成功邮件", ca.getEmail(), "zz91_vip_open", map, 0);
				}
			}
			
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
