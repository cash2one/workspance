package com.ast.ast1949.web.controller.zz91.phonecrm;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
@Controller
public class PhoneCsController extends BaseController{
	final static String CS_DEPT_CODE = "10001005";
	final static String URL_ENCODE = "utf-8";
	final static int SEARCH_EXPIRED = 20000;

	final static String DATE_FORMATE_DEFAULT = "yyyy-MM-dd";

	private void initCommon(HttpServletRequest request, Map<String, Object> out) {
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(
				CS_DEPT_CODE);
		out.put("csMap", JSONObject.fromObject(map));
		// out.put("csDeptCode", CS_DEPT_CODE);

		out.put("cs", getCachedUser(request).getAccount());
		out.put("csName", getCachedUser(request).getName());
		if (AuthUtils.getInstance().authorizeRight("assign_company", request,
				null)) {
			out.put("asignFlag", "1");
			out.put("allcs", map);
		}
	}
	
	/**
	 * 核心客户管理初始页面，在这个页面上查找核心客户及对客户信息进行维护
	 * 
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object>out,HttpServletRequest request,String cs, String svrCode,
			String svrEndMonth) throws ParseException {
		initCommon(request, out);

		if (svrEndMonth != null) {
			Date start = DateUtil.getDate(svrEndMonth, "yyyy-MM");
			start = DateUtil.getDate(start, "yyyy-MM-01");
			Date end = DateUtil.getDateAfterDays(DateUtil.getDateAfterMonths(start, 1), -1);
			out.put("svrEndFrom", start);
			out.put("svrEndTo", end);
		}

		out.put("svrCode", svrCode);

		if (cs != null) {
			out.put("cs", cs);
		}

		return null;
	}
	/**
	 * 今天安排联系
	 */
	@RequestMapping
	public ModelAndView index1(Map<String, Object> out,
			HttpServletRequest request) {
		Date from = new Date(DateUtil.getTheDayZero(0) * 1000);
		Date to = new Date(DateUtil.getTheDayZero(1) * 1000);
		out.put("nextVisitPhoneFromStr", DateUtil.toString(from,
				DATE_FORMATE_DEFAULT));
		out.put("nextVisitPhoneToStr", DateUtil.toString(to,
				DATE_FORMATE_DEFAULT));

		initCommon(request, out);
		return null;
	}
	
	/**
	 * 新分配未联系
	 */
	@RequestMapping
	public ModelAndView index2(Map<String, Object> out,
			HttpServletRequest request) {
		out.put("unvisitFlag", 0);

		initCommon(request, out);
		return null;
	}
	
	/**
	 * 过期客户
	 */
	@RequestMapping
	public ModelAndView index6(Map<String, Object>out,HttpServletRequest request){
		out.put("expiredFlag", 1);
		initCommon(request, out);
		return null;
	}
	
	/**
	 * 跟丢客户
	 */
	@RequestMapping
	public ModelAndView index4(Map<String, Object> out,
			HttpServletRequest request) {
		//Date from = new Date(DateUtil.getTheDayZero(-1) * 1000);
		Date from=new Date();
		//Date to = new Date(DateUtil.getTheDayZero(0) * 1000);

		out.put("logDayFrom", DateUtil.toString(from, DATE_FORMATE_DEFAULT));
		//out.put("logDayTo", DateUtil.toString(to, DATE_FORMATE_DEFAULT));
		initCommon(request, out);
		return null;
	}
	/**
	 * 必杀期客户
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView index3(Map<String, Object>out,HttpServletRequest request){
		initCommon(request, out);
		return null;
	}
}
