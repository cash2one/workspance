package com.ast.ast1949.web.controller.zz91.trust;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustCrm;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.trust.TrustCrmDto;
import com.ast.ast1949.dto.trust.TrustCrmSearchDto;
import com.ast.ast1949.dto.trust.TrustCsLogDto;
import com.ast.ast1949.dto.trust.TrustCsLogSearchDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.trust.TrustBuyLogService;
import com.ast.ast1949.service.trust.TrustBuyService;
import com.ast.ast1949.service.trust.TrustCompanyLogService;
import com.ast.ast1949.service.trust.TrustCrmService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

import net.sf.json.JSONObject;

@Controller
public class CrmController extends BaseController{

	final static String TRUST_DEPT_CODE = "10001015";
	final static String CS_DEPT_CODE = "10001005";

	@Resource
	private TrustCrmService trustCrmService;
	@Resource
	private TrustBuyLogService trustBuyLogService;
	@Resource
	private TrustBuyService trustBuyService;
	@Resource
	private CompanyService companyService;
	@Resource
	private TrustCompanyLogService trustCompanyLogService;

	/**
	 * 今天安排联系的客户
	 */
	@RequestMapping
	public ModelAndView cs_today(Map<String, Object> out,HttpServletRequest request,String account){
		initCommon(request, out);
		if (StringUtils.isNotEmpty(account)) {
			out.put("cs", account);
		}
		return new ModelAndView();
	}
	
	/**
	 * 新分配未联系的客户
	 */
	@RequestMapping
	public ModelAndView cs_new(Map<String, Object> out,HttpServletRequest request,String account){
		initCommon(request, out);
		if (StringUtils.isNotEmpty(account)) {
			out.put("cs", account);
		}
		return new ModelAndView();
	}
	
	/**
	 * 第一次发布采购单的客户
	 */
	@RequestMapping
	public ModelAndView cs_first(Map<String, Object> out,HttpServletRequest request,String account){
		initCommon(request, out);
		if (StringUtils.isNotEmpty(account)) {
			out.put("cs", account);
		}
		return new ModelAndView();
	}
	
	/**
	 * 我的所有客户
	 */
	@RequestMapping
	public ModelAndView cs_all(Map<String, Object> out,HttpServletRequest request,Integer star,String account){
		initCommon(request, out);
		if (StringUtils.isNotEmpty(account)) {
			out.put("cs", account);
		}
		out.put("star", star);
		if (star==null||star<0) {
			out.put("star", "undefined");
		}
		return new ModelAndView();
	}
	
	/**
	 * 跟丢客户
	 */
	@RequestMapping
	public ModelAndView cs_lost(Map<String, Object> out,HttpServletRequest request,String account){
		initCommon(request, out);
		if (StringUtils.isNotEmpty(account)) {
			out.put("cs", account);
		}
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView queryCrm(Map<String, Object>model, TrustCrmSearchDto searchDto,PageDto<TrustCrmDto>page) throws IOException{
		page.setSort("id");
		page.setDir("desc");
		page = trustCrmService.page(searchDto, page);
		return printJson(page, model);
	}
	
	/**
	 * 关联页面
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView relate(Map<String, Object> out,Integer id){
		do {
			if (id==null) {
				break;
			}
			TrustBuy trustBuy = trustBuyService.queryTrustById(id);
			out.put("trustBuy", trustBuy);
			
		} while (false);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView relateCompanyBySearch(PageDto<CompanyDto> page,Map<String, Object> out,String name,String mobile) throws IOException{
//		page = companyService.pageCompanyByAdmin(company, account, null, null, null, page);
		page = companyService.queryCompanyDtoByNameAndMobile(name,mobile);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView relateByCompany(Map<String, Object>out,Integer trustId,Integer companyId) throws IOException{
		ExtResult result =new ExtResult();
		do {
			
			TrustBuy tb = trustBuyService.queryTrustById(trustId);
			if (tb==null||StringUtils.isEmpty(tb.getMobile())) {
				break;
			}
			
			Integer i = trustBuyService.relateCompanyByMobile(companyId, tb.getMobile());
			if (i>0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 自己捞公海
	 */
	@RequestMapping
	public ModelAndView assignCrmAccount(Map<String, Object> out,HttpServletRequest request,Integer companyId) throws IOException{
		ExtResult result =new ExtResult();
		do {
			SessionUser ssoUser = getCachedUser(request);
			if (ssoUser==null) {
				break;
			}
			Integer i = trustCrmService.assignAccount(companyId, ssoUser.getAccount());
			if (i==null||i==0) {
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 分配客户 管理权限
	 */
	@RequestMapping
	public ModelAndView assign(Map<String, Object> out,HttpServletRequest request,String crmAccount,Integer companyId) throws IOException{
		ExtResult result =new ExtResult();
		do {
			// 没有分配权限
			if(!AuthUtils.getInstance().authorizeRight("assignCrm", request, null)){
				break;
			}
			
			Integer i = trustCrmService.assignAccountByRight(companyId, crmAccount);
			if (i==null||i==0) {
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 丟公海
	 */
	@RequestMapping
	public ModelAndView lost(Map<String, Object> model,Integer companyId,HttpServletRequest request) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if (companyId==null) {
				break;
			}
			TrustCrm obj = trustCrmService.queryByCompanyId(companyId);
			if (obj==null) {
				break;
			}
			SessionUser ssoUser = getCachedUser(request);
			// 确保只有客服本人 可以 丢公海
			if (!ssoUser.getAccount().equals(obj.getCrmAccount())) {
				break;
			}
			
			Integer i = trustCrmService.lost(companyId);
			if (i>0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, model);
	}
	
	/**
	 * 丟废品池
	 */
	@RequestMapping
	public ModelAndView destory(Map<String, Object> model,Integer companyId,HttpServletRequest request) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if (companyId==null) {
				break;
			}
			TrustCrm obj = trustCrmService.queryByCompanyId(companyId);
			if (obj==null) {
				break;
			}
			SessionUser ssoUser = getCachedUser(request);
			// 确保只有客服本人 可以 丢废品池
			if (!ssoUser.getAccount().equals(obj.getCrmAccount())) {
				break;
			}
			
			Integer i = trustCrmService.destory(companyId);
			if (i>0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, model);
	}
	
	/**
	 * 初始化部门信息
	 */
	private void initCommon(HttpServletRequest request, Map<String, Object> out) {
		out.put("cs", getCachedUser(request).getAccount());
		out.put("csName", getCachedUser(request).getName());
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(TRUST_DEPT_CODE);
		out.put("trustMap", JSONObject.fromObject(map));
		if (AuthUtils.getInstance().authorizeRight("assignCrm", request, null)) {
			out.put("asignFlag", "1");
		}
		out.put("allTrustCS", map);
		
		Map<String, String> csMap = AuthUtils.getInstance().queryStaffOfDept(CS_DEPT_CODE);
		out.put("csMap", JSONObject.fromObject(csMap));
		out.put("allcs", csMap);
	}
	
	/**
	 * 获取所有采购交易客服帐号
	 */
	@RequestMapping
	public ModelAndView queryTrustCs(Map<String, Object> out,
			HttpServletRequest request) throws IOException {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		SessionUser sessionUser = getCachedUser(request);
		if (AuthUtils.getInstance().authorizeRight("assignCrm", request, null)) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("account", "all");
			m.put("name", "全部");
			list.add(m);
			// 查询全部的客服信息
			Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(TRUST_DEPT_CODE);
			boolean iscs = false;
			for (String k : map.keySet()) {
				Map<String, String> m0 = new HashMap<String, String>();
				m0.put("account", k);
				m0.put("name", map.get(k));
				list.add(m0);
				if (sessionUser.getAccount().equals(k)) {
					iscs = true;
				}
			}
			if (!iscs) {
				Map<String, String> m0 = new HashMap<String, String>();
				m0.put("account", sessionUser.getAccount());
				m0.put("name", sessionUser.getName());
				list.add(m0);
			}
			
		}else{
			Map<String, String> m0 = new HashMap<String, String>();
			m0.put("account", sessionUser.getAccount());
			m0.put("name", sessionUser.getName());
			list.add(m0);
		}

		return printJson(list, out);
	}
	
	/**
	 * 导入crm库
	 * 放置个人库，放入公海
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView importToCrm(HttpServletRequest request,Integer companyId,Map<String, Object> out) throws IOException{
		ExtResult result = new ExtResult();
		do {
			
			SessionUser ssoUser = getCachedUser(request);
			if (ssoUser==null) {
				break;
			}
			
			Integer i = trustCrmService.importToCrm(companyId);
			if (i>0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 统计工作小计
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView analysis(Map<String, Object> out,String from,String to,String date,String account,HttpServletRequest request) throws ParseException{
		if (StringUtils.isNotEmpty(date)) {
			from = DateUtil.toString(DateUtil.getDate(date, "yyyy-MM-dd"), "yyyy-MM-dd");
		}
		if (StringUtils.isEmpty(from)) {
			from = DateUtil.toString(new Date(), "yyyy-MM-dd");
		}
		if (StringUtils.isEmpty(to)) {
			to = from;
		}
		out.put("from", from);
		out.put("to", to);
		out.put("account",account);
		// 有查看权限
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(TRUST_DEPT_CODE);
		if(AuthUtils.getInstance().authorizeRight("checkAll", request, null)){
			out.put("trustMap", JSONObject.fromObject(map));
		}else{
			SessionUser ssoUser = getCachedUser(request);
			Map<String, String > ownerMap =new HashMap<String, String>();
			ownerMap.put(ssoUser.getAccount(), map.get(ssoUser.getAccount()));
			out.put("trustMap", JSONObject.fromObject(ownerMap));
		}
		out.put("trustMap", JSONObject.fromObject(map));
		return new ModelAndView();
	}
	
	/**
	 * 统计工作小计
	 */
	@RequestMapping
	public ModelAndView analysis_day_log(Map<String, Object> out,String from,String to){
		if (StringUtils.isEmpty(from)) {
			from = DateUtil.toString(new Date(), "yyyy-MM-dd");
		}
		if (StringUtils.isEmpty(to)) {
			to = from;
		}
		out.put("from", from);
		out.put("to", to);
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(TRUST_DEPT_CODE);
		out.put("trustMap", JSONObject.fromObject(map));
		return new ModelAndView();
	}
	
	/**
	 * 月工作小计一览
	 */
	@RequestMapping
	public ModelAndView analysis_month_log(Map<String, Object> out,Integer year,Integer month,String account,HttpServletRequest request){
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			if (year==null) {
				year = DateUtil.getYearForDate(new Date());
			}
			if (month==null) {
				month = DateUtil.getMonthForDate(new Date());
			}
			out.put("year", year);
			out.put("month", month);
			out.put("account", account);
			
			List<Map<String, Object>> list = trustCrmService.queryMonthLog(year, month, account);
			out.put("list", list);
		} while (false);
		// 有查看权限
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(TRUST_DEPT_CODE);
		if(AuthUtils.getInstance().authorizeRight("checkAll", request, null)){
			out.put("trustMap", JSONObject.fromObject(map));
		}else{
			SessionUser ssoUser = getCachedUser(request);
			Map<String, String > ownerMap =new HashMap<String, String>();
			ownerMap.put(ssoUser.getAccount(), map.get(ssoUser.getAccount()));
			out.put("trustMap", JSONObject.fromObject(ownerMap));
		}
		
		return new ModelAndView();
	}
	
	
	/**
	 * 统计小计详细获取
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView queryCsLogOfCompany(Map<String, Object> out,TrustCsLogSearchDto searchDto,PageDto<TrustCsLogDto> page)throws IOException, ParseException {
		page.setSort("gmt_created");
		page.setDir("desc");
		String from = searchDto.getFrom();
		String to = searchDto.getTo();
		searchDto.setFrom(DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"),"yyyy-MM-dd"));
		searchDto.setTo(DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(to, "yyyy-MM-dd"), 1), "yyyy-MM-dd"));
		page = trustCrmService.pageLog(searchDto, page);
		return printJson(page, out);
	}

}