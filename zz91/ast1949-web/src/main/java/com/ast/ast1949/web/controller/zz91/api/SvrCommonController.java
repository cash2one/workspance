/**
 * 
 */
package com.ast.ast1949.web.controller.zz91.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class SvrCommonController extends BaseController {
	
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CrmCsService crmCsService;
//	@Resource
//	private ScoreChangeDetailsService scoreChangeDetailsService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView open(HttpServletRequest request, Map<String, Object> out, String callbackCode, String callbackData){
		Map<String, Object> dataMap=(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(callbackData), Map.class);
		Integer companyId=(Integer) dataMap.get("companyId");
		out.put("companyId", companyId);
		out.put("companySvrId",dataMap.get("companySvrId"));
		CrmCs cs=crmCsService.queryCsOfCompany(companyId);
		if(cs!=null){
			dataMap.put("csAccount", cs.getCsAccount());
			if (StringUtils.isNotEmpty(cs.getCsAccount())) {
				dataMap.put("csName", AuthUtils.getInstance().queryStaffNameOfAccount(cs.getCsAccount()));
			}
		}
		out.put("data", JSONObject.fromObject(dataMap).toString());
		return null;
	}
	
	@RequestMapping
	public ModelAndView doOpen(HttpServletRequest request, Map<String, Object> out,
			CrmCompanySvr svr, String gmtPreStartDate, String gmtPreEndDate, 
			String gmtStartDate, String gmtEndDate, String gmtSignedDate, Integer integral,String oldCsAccount,String csAccount) throws IOException{
		
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
		
		Boolean r=crmCompanySvrService.openSvr(svr, svr.getCompanyId());
		
		ExtResult result = new ExtResult();
		result.setSuccess(r);
		
		// 添加指派运营人员
		crmCsService.reassign(oldCsAccount, csAccount, svr.getCompanyId());
		
//		if(result.isSuccess()){
//			if("0".equals(svr.getApplyStatus())){
//				if(integral!=null && integral.intValue()>0){
//					scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(svr.getCompanyId(), name, rulesCode, score, relatedId, remark))
//				}
//			}
//		}
		
//		//TODO 发送短信或邮件通知
//		if(noticeSms!=null && noticeSms){
//			//发送短信通知
//		}
//		if(noticeEmail!=null && noticeSms){
//			//发送邮件通知
//		}
		
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView querySvrById(HttpServletRequest request, Map<String, Object> out, 
			Integer id, Integer companyId) throws IOException{
		List<CrmCompanySvr> list=new ArrayList<CrmCompanySvr>();
		CrmCompanySvr svr=crmCompanySvrService.queryCompanySvrById(id);
		//上一次服务时间
		if(svr.getGmtPreStart()==null){
			CrmCompanySvr recentSvr=crmCompanySvrService.queryRecentHistory(svr.getCrmServiceCode(), svr.getCompanyId(), svr.getId());
			if(recentSvr!=null){
				svr.setGmtPreStart(recentSvr.getGmtStart());
				svr.setGmtPreEnd(recentSvr.getGmtEnd());
			}
		}
		list.add(svr);
		return printJson(list, out);
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
		Boolean result=crmCompanySvrService.closeSvr(companySvrId);
		if(result != null && result){
			out.put("result", 1);
		}else{
			out.put("result", 0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView change(HttpServletRequest request, Map<String, Object> out, String callbackCode, String callbackData){
		Map<String, Object> dataMap=(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(callbackData), Map.class);
		out.put("companyId", dataMap.get("companyId"));
		out.put("companySvrId",dataMap.get("companySvrId"));
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView doChange(HttpServletRequest request, Map<String, Object> out,
			CrmCompanySvr svr, String gmtPreStartDate, String gmtPreEndDate, 
			String gmtStartDate, String gmtEndDate, String gmtSignedDate, Integer integral) throws IOException{
		
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
		
		Boolean r=crmCompanySvrService.updateSvr(svr, svr.getCompanyId());
		
		ExtResult result = new ExtResult();
		result.setSuccess(r);
//		if(result.isSuccess()){
//			if("0".equals(svr.getApplyStatus())){
//				if(integral!=null && integral.intValue()>0){
//					scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(svr.getCompanyId(), name, rulesCode, score, relatedId, remark))
//				}
//			}
//		}
		
//		//TODO 发送短信或邮件通知
//		if(noticeSms!=null && noticeSms){
//			//发送短信通知
//		}
//		if(noticeEmail!=null && noticeSms){
//			//发送邮件通知
//		}
		
		return printJson(result, out);
	}
}
