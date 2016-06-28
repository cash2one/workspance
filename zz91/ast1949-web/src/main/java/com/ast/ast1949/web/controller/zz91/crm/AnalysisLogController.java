/**
 * 
 */
package com.ast.ast1949.web.controller.zz91.crm;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.analysis.AnalysisTrustCrmDto;
import com.ast.ast1949.domain.analysis.AnalysisTrustLog;
import com.ast.ast1949.dto.company.AnalysisCsLog;
import com.ast.ast1949.service.analysis.AnalysisTrustLogService;
import com.ast.ast1949.service.company.CrmCsLogService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 * @author mays (mays@zz91.net)
 *
 */
@Controller
public class AnalysisLogController extends BaseController {
	
	@Resource
	private CrmCsLogService crmCsLogService;
	@Resource
	private AnalysisTrustLogService analysisTrustLogService;
	
	final static String CS_DEPT_CODE="10001005";
	final static String TARGET_DATE_FORMAT="yyyy-MM-dd";
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(CS_DEPT_CODE);
		out.put("csMap", JSONObject.fromObject(map));
		
		out.put("from", DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		out.put("to", DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryAnalysisData(HttpServletRequest request, Map<String, Object> out, String from, String to) throws IOException{
		String csAccount=null;
		if(!AuthUtils.getInstance().authorizeRight("view_analysis_log", request, null)){
			csAccount=getCachedUser(request).getAccount();
		}
		long targetFrom=0, targetTo=0;
		if(StringUtils.isNotEmpty(from)){
			try {
				targetFrom=DateUtil.getMillis(DateUtil.getDate(from, TARGET_DATE_FORMAT));
				targetFrom=targetFrom/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			targetFrom=DateUtil.getTheDayZero(-1);
		}
		if(StringUtils.isNotEmpty(to)){
			try {
				targetTo = DateUtil.getMillis(DateUtil.getDate(to, TARGET_DATE_FORMAT));
				targetTo=targetTo/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			targetTo = DateUtil.getTheDayZero(-1);
		}
		
		List<AnalysisCsLog> list = null;
		if(targetFrom>0 && targetTo>0){
			list= crmCsLogService.queryCsLogAnalysis(csAccount, targetFrom, targetTo);
		}else{
			list=new ArrayList<AnalysisCsLog>();
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryAnalysisTrustLog(Map<String, Object>out,String from ,String to,String account) throws IOException{
		List<AnalysisTrustLog> list = analysisTrustLogService.queryByCondition(from,to,account);
		if (list==null) {
			list = new ArrayList<AnalysisTrustLog>();
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryDayLog(Map<String,Object> out) throws IOException{
		List<AnalysisTrustCrmDto> list = analysisTrustLogService.queryDayLog();
		return printJson(list, out);
	}
	
}
