package com.zz91.crm.controller.csale;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.crm.controller.BaseController;
import com.zz91.crm.domain.CrmStatistics;
import com.zz91.crm.dto.CrmContactStatisticsDto;
import com.zz91.crm.dto.CrmSaleStatisticsDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDataDto;
import com.zz91.crm.service.CrmSaleCompService;
import com.zz91.crm.service.ParamService;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-22 
 */
@Controller
public class CsDataController extends BaseController {
	
	private static final String DATA_INPUT_CONFIG="data_input_config";
	private static final String HUANBAO_DEPT_CODE="huanbao_dept_code";
	
	@Resource
	private CrmSaleCompService crmSaleCompService;
	@Resource
	private ParamService paramService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		
		return null;
	}

	@RequestMapping
	public ModelAndView queryData(Map<String, Object> out,
			HttpServletRequest request,String start,String end,String account,Short group) {
		List<CrmContactStatisticsDto> list=null;
		String deptCode=getCachedUser(request).getDeptCode();
		if (StringUtils.isNotEmpty(start)){
			start = start.substring(0, 10);
		}
		if (StringUtils.isNotEmpty(end)){
			end = end.substring(0, 10);
		} 
		list=crmSaleCompService.queryContactData(account, deptCode, start, end, group);
		return printJson(list, out);
	}
	
	/**
	 * 我的联系量统计
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView myContact(Map<String, Object> out,
			HttpServletRequest request) {
		out.put("account", getCachedUser(request).getAccount());
		return null;
	}

	/**
	 * 部门联系量统计
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView deptContact(Map<String, Object> out,
			HttpServletRequest request) {
		return null;
	}

	/**
	 * 销售到单统计
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView saleOrder(Map<String, Object> out,
			HttpServletRequest request) {
		return null;
	}

	@RequestMapping
	public ModelAndView registerData(Map<String, Object> out,
			HttpServletRequest request,String start,String end,Short group) {
		List<CrmSaleStatisticsDto> list=null;
		if (StringUtils.isNotEmpty(start)){
			start = start.substring(0, 10);
		}
		if (StringUtils.isNotEmpty(end)){
			end = end.substring(0, 10);
		} 
		list=crmSaleCompService.queryRegisterData(start, end, group);
		return printJson(list, out);
	}

	/**
	 * 注册客户数统计
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView register(Map<String, Object> out,
			HttpServletRequest request) {
		return null;
	}
	
	@RequestMapping
	public ModelAndView deptCompData(Map<String, Object> out,
			HttpServletRequest request) {
		String deptCode=getCachedUser(request).getDeptCode();
//		if (StringUtils.isNotEmpty(deptCode) && deptCode.equals("10001000")) {
//			deptCode = paramService.queryValueByKey(DATA_INPUT_CONFIG, HUANBAO_DEPT_CODE);
//		}
		List<SaleCompanyDataDto> list=crmSaleCompService.querySaleCompanyData(null, deptCode);
		return printJson(list, out);
	}
	
	/**
	 * 部门客户数统计
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView deptCompany(Map<String, Object> out,
			HttpServletRequest request) {
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryPerson(Map<String, Object> out,HttpServletRequest request){
		
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		Map<String, String> m = new HashMap<String, String>();
		m.put("account", "");
		m.put("name", "全部");
		list.add(m);
		
		SessionUser sessionUser=getCachedUser(request);
		String dept = getCachedUser(request).getDeptCode();
		if (StringUtils.isNotEmpty(dept) && dept.equals("10001000")) {
			dept = paramService.queryValueByKey(DATA_INPUT_CONFIG, HUANBAO_DEPT_CODE);
		}
		List<JSONObject> list1=AuthUtils.getInstance().queryStaffByDept(dept);
		
		//查询全部的环保人员信息
		boolean iscs=false;
		for (JSONObject object : list1) {
			Map<String, String> m0 = new HashMap<String, String>();
			m0.put("account", object.getString("account"));
			m0.put("name", object.getString("name"));
			list.add(m0);
			if(sessionUser.getAccount().equals(object.getString("account"))){
				iscs=true;
			}
		}	
		
		if(!iscs){
			Map<String, String> m0 = new HashMap<String, String>();
			m0.put("account", sessionUser.getAccount());
			m0.put("name", sessionUser.getName());
			list.add(m0);
		}
		return printJson(list, out);
	}
	
	/**
	 * 我今天的联系量统计
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView myContactToday(Map<String, Object> out,
			HttpServletRequest request) {
		out.put("account", getCachedUser(request).getAccount());
		return null;
	}
	
	/**
	 * 部门今天的联系量统计
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView deptContactToday(Map<String, Object> out,
			HttpServletRequest request) {
		out.put("deptCode", getCachedUser(request).getDeptCode());
		return null;
	}
	
	/**
	 * 查询当天联系量统计结果
	 * @param out
	 * @param request
	 * @param account
	 * @param deptCode
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryDataByToday(Map<String, Object> out,
			HttpServletRequest request,String account,String deptCode) {
		List<CrmContactStatisticsDto> list=new ArrayList<CrmContactStatisticsDto>();
		if (StringUtils.isNotEmpty(deptCode)){
			list=crmSaleCompService.queryDeptContactDataByToday(deptCode);
		}
		else {
			CrmContactStatisticsDto dto=crmSaleCompService.queryMyContactDataByToday(account, deptCode);
			list.add(dto);
			if (StringUtils.isEmpty(dto.getStatistics().getSaleName())){
				list=null;
			}
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryCrmStatisticsData(Map<String, Object> out,
			HttpServletRequest request,PageDto<CrmStatistics> page){
		page=crmSaleCompService.pageCrmStatistics(page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView dataOverView(Map<String, Object> out,HttpServletRequest request){
		List<Date> date=crmSaleCompService.queryGmtTarget();
		List<Long> list=new ArrayList<Long>();
		for (Date date2 : date) {
			int i = DateUtil.getDayOfWeekForDate(date2);
			if (i==1 || i==7){
				list.add(DateUtil.getMillis(date2));
				out.put("list", list);
			}
		}
		return null;
	}
}
