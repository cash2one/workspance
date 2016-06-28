/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-5 下午04:07:37
 */
package com.zz91.ep.admin.controller.api;

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

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.comp.CompProfileService;
import com.zz91.ep.admin.service.crm.CRMCompSvrService;
import com.zz91.ep.admin.service.crm.CRMCsService;
import com.zz91.ep.admin.service.crm.CRMSvrApplyService;
import com.zz91.ep.admin.service.trade.TradeBuyService;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.domain.crm.CrmCs;
import com.zz91.ep.domain.crm.CrmSvrApply;
import com.zz91.ep.dto.ExtResult;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Controller
public class SeoSvrController extends BaseController{

	@Resource
	private CRMCompSvrService crmCompSvrService;
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private CRMSvrApplyService crmSvrApplyService;
	@Resource
	private CRMCsService crmCsService;
	@Resource
	private TradeBuyService tradeBuyService;
	@Resource
	private TradeSupplyService tradeSupplyService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView open(HttpServletRequest request, Map<String, Object> out, String callbackCode, String callbackData){
		
		Map<String, Object> dataMap=(Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(callbackData), Map.class);
		Integer cid=(Integer) dataMap.get("cid");
		Integer companySvrId=(Integer) dataMap.get("companySvrId");
		
		CrmCompSvr crmCompSvr = crmCompSvrService.queryRecentHistory("1003", cid, companySvrId);
		if(crmCompSvr!=null){
			dataMap.put("gmtPreStart", crmCompSvr.getGmtStart().getTime());
			dataMap.put("gmtPreEnd", crmCompSvr.getGmtEnd().getTime());
		}
		
		CompProfile compProfile=compProfileService.queryCompProfileById(cid);
		dataMap.put("memberCode", compProfile.getMemberCode());
		dataMap.put("domain", compProfile.getDomainTwo());
		
		CrmCs cs=crmCsService.queryCsOfCompany(cid);
		if(cs!=null){
			dataMap.put("csAccount", cs.getCsAccount());
			if (StringUtils.isNotEmpty(cs.getCsAccount())) {
				dataMap.put("csName", AuthUtils.getInstance().queryStaffNameOfAccount(cs.getCsAccount()));
			}
		}
		
		CrmCompSvr svr = crmCompSvrService.queryCompanySvrById(companySvrId);
		if(svr!=null){
			dataMap.put("svr", svr);
			CrmSvrApply apply=crmSvrApplyService.queryApplyByGroup(svr.getApplyGroup());
			if(apply.getAmount()!=null ){
				dataMap.put("integral", (apply.getAmount()/100));
			}
		}
		
		out.put("data", JSONObject.fromObject(dataMap).toString());
		return null;
	}
	
	@RequestMapping
	public ModelAndView doOpen(HttpServletRequest request, Map<String, Object> out, CrmCompSvr svr, 
			String gmtPreStartDate, String gmtPreEndDate, String gmtStartDate, String gmtEndDate, String gmtSignedDate, 
			String domain, String memberCode) throws IOException{
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
			tradeBuyService.updateGmtmodifiedBySvrClose(svr.getCid());
			tradeSupplyService.updateGmtmodifiedBySvrClose(svr.getCid());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Boolean r=crmCompSvrService.openZhtSvr(svr, domain, memberCode);
		
		ExtResult result = new ExtResult();
		result.setSuccess(r);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView querySvrById(HttpServletRequest request, Map<String, Object> out, 
			Integer id, Integer cid) throws IOException{
		List<CrmCompSvr> list=new ArrayList<CrmCompSvr>();
		CrmCompSvr svr=crmCompSvrService.queryCompSvrById(id);
		//上一次服务时间
		if(svr.getGmtPreStart()==null){
			CrmCompSvr recentSvr=crmCompSvrService.queryRecentHistory(svr.getCrmSvrId(), svr.getCid(), svr.getId());
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
		out.put("cid", dataMap.get("cid"));
		out.put("companySvrId",dataMap.get("companySvrId"));
		return null;
	}
	
	@RequestMapping
	public ModelAndView doClose(HttpServletRequest request, Map<String, Object> out,
			Integer cid, Integer companySvrId){
		Boolean result=crmCompSvrService.closeSeoSvr(cid,companySvrId);
		tradeBuyService.updateGmtmodifiedBySvrClose(cid);
		tradeSupplyService.updateGmtmodifiedBySvrClose(cid);
		if(result != null && result){
			out.put("result", 1);
		}else{
			out.put("result", 0);
		}
		return null;
	}
}
