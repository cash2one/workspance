package com.ast.ast1949.web.controller.zz91.phonecrm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
@Controller
public class MyServiceController extends BaseController{
	final static String CS_DEPT_CODE = "10001005";
	private void initCommon(HttpServletRequest request, Map<String, Object> out) {
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(CS_DEPT_CODE);
		out.put("csMap", JSONObject.fromObject(map));
		// out.put("csDeptCode", CS_DEPT_CODE);

		out.put("csName", getCachedUser(request).getName());
		if (AuthUtils.getInstance().authorizeRight("assign_company", request,
				null)) {
			out.put("asignFlag", "1");
			out.put("allcs", map);
		}
	}
	
	/**
	 * 我的服务台
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,Map<String, Object>out){
		out.put("from", null);
		out.put("to", null);
		return null;
	}
	/**
	 * 留言管理
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView myfeedback(HttpServletRequest request,Map<String, Object>out){
		return null;
	}
	/**
	 * 操作日志
	 * @param out
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView dailyrecord(Map<String, Object>out,Integer companyId){
		out.put("companyId", companyId);
		return new ModelAndView();
	}
	/**
	 * 服务开通
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView ldbApply(Map<String, Object>out,HttpServletRequest request){
		return null;
	}
	/**
	 * 来电宝部门所有客户 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView allcustomer(Map<String, Object>out,HttpServletRequest request){
		initCommon(request, out);
		//allcustomerFlag 为1表示查询是需要连接phone 表
		out.put("allCustomerFlag", 1);
		return null;
	}
	/**
	 * 来电宝部门过期客户
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView expirecustomer(Map<String, Object>out,HttpServletRequest request){
		initCommon(request, out);
		//过期客户标志 1:表示是  
		out.put("expiredFlag", 1);
		return null;
	}
	/**
	 * 来电宝部门跟丢客户
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView lostcustomer(Map<String, Object>out,HttpServletRequest request){
		initCommon(request, out);
		//部门跟丢客户标志 连接phone_lost_customer 表  1:表示需要连接
		out.put("lostCustomerFlag", 1);
		return null;
	}
	/**
	 * 部门必杀期客户
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView index1(Map<String, Object>out, HttpServletRequest request){
		initCommon(request, out);
		
		return null;
	}
}
