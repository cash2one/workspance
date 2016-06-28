package com.ast.ast1949.web.controller.zz91.analysis;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ast.ast1949.domain.analysis.AnalysisCsRenewal;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmCsLogService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.site.AnalysisCsRenewalService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
@Controller
public class AnalysisCsRenewalController extends BaseController {
	@Resource
	private AnalysisCsRenewalService analysisCsRenewalService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CrmCsLogService crmCsLogService;
	@Resource
	private CrmCsService crmCsService;
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out, 
			Date start, Date end){
		
		if(start==null){
			start=DateUtil.getLastMonthFistDay();
//			try {
//				start=DateUtil.getDate("2011-12-01", "yyyy-MM-dd");
//			} catch (ParseException e) {
//			}
		}
		
		if(end==null){
			end=start;
//			end=DateUtil.getDateAfterMonths(start, 1);
		}
		
		out.put("start", start);
		out.put("end", end);
		
		String account = null;
		if(!AuthUtils.getInstance().authorizeRight("view_cs_renewal", request, null)){
			account=getCachedUser(request).getAccount();
		}
		
		Map<String, List<AnalysisCsRenewal>> renewalMap=analysisCsRenewalService.queryAnalysisCsRenewal(account, start, end);
		out.put("renewalMap", renewalMap);
		
		List<String> monthList=analysisCsRenewalService.buildMonthList(start, end);
		out.put("monthList", monthList);
		
		out.put("csDept", AuthUtils.getInstance().queryStaffOfDept("10001005"));
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView monthExpired(HttpServletRequest request, Map<String, Object> out, 
			Date start, Date end){
		SessionUser sessionUser = getCachedUser(request);
		out.put("usermark", sessionUser.getAccount());
		if(!AuthUtils.getInstance().authorizeRight("vip_cs_monthexpired",request, null)){
			out.put("expiredMark", "no");
		}else{
			out.put("expiredMark", "yes");
		}
		if(start==null){
			start=DateUtil.getLastMonthFistDay();
		}
		if(end==null){
			end=DateUtil.getDateAfterMonths(start, 1);
		}
		out.put("start", start);
		out.put("end", end);

		end=DateUtil.getDateAfterDays(end, -1);
		
		List<String> monthList=analysisCsRenewalService.buildMonthList(start, end);
		out.put("monthList", monthList);
		
		Map<String, Map<String, Integer>> expiredCount=crmCompanySvrService.monthExpiredCountBySvrCode(start, end,CrmCompanySvrService.ZST_CODE);
		out.put("expiredCount", expiredCount);
		
		Map<String, Map<String, Integer>> jbExpiredCount=crmCompanySvrService.monthExpiredCountBySvrCode(start, end,CrmCompanySvrService.JBZST_CODE);
		out.put("jbExpiredCount", jbExpiredCount);
		
		out.put("csDept", AuthUtils.getInstance().queryStaffOfDept("10001005"));
		return null;
	}
	@RequestMapping
	public ModelAndView dayContact(HttpServletRequest request, Map<String, Object> out, 
			Date start, Date end) throws ParseException{
		if(start==null){
			start=DateUtil.getDate(new Date(), "yyyy-MM-dd");
		}
		if(end==null){
			 end=DateUtil.getDate(new Date(), "yyyy-MM-dd");
			
		}
		out.put("start", start);
		out.put("end", end);
		
		List<String> dayList=analysisCsRenewalService.buildDayList(start, end);
		out.put("dayList", dayList);
		out.put("account",  getCachedUser(request).getAccount());
		out.put("csName", getCachedUser(request).getName());
		String csAccount=getCachedUser(request).getAccount();
		if (AuthUtils.getInstance().authorizeRight("view_analysis_log", request,
				null)) {
			Map<String, String> nameMap=AuthUtils.getInstance().queryStaffOfDept("10001005");
			out.put("nameMap", nameMap);
			csAccount=null;
		}
		
		Map<String, Map<String, Integer>> expiredCount=crmCsService.dayContactCount(start, end, csAccount);
		out.put("expiredCount", expiredCount);
		
		return null;
	}
}
