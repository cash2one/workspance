/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-20
 */
package com.ast.ast1949.web.controller.zz91.crm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyAccountContact;
import com.ast.ast1949.domain.company.CompanyLottery;
import com.ast.ast1949.domain.company.CrmCompanyProfile;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCsLog;
import com.ast.ast1949.domain.company.CrmCsLogAdded;
import com.ast.ast1949.domain.company.CrmOutLog;
import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneLostCus;
import com.ast.ast1949.domain.products.ProductsAutoRefresh;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmCsDto;
import com.ast.ast1949.dto.company.CrmCsLogDto;
import com.ast.ast1949.dto.company.CrmSearchDto;
import com.ast.ast1949.persist.company.CrmCompanySvrDao;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountContactService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyLotteryService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanyProfileService;
import com.ast.ast1949.service.company.CrmCsLogAddedService;
import com.ast.ast1949.service.company.CrmCsLogService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.company.CrmOutLogService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneLostCusService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductsAutoRefreshService;
import com.ast.ast1949.service.trust.TrustBuyLogService;
import com.ast.ast1949.service.trust.TrustCompanyLogService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

import net.sf.json.JSONObject;

/**
 * 客户核心客户管理相关关的业务方法
 * 
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-5-20
 */
@Controller
public class CsController extends BaseController {

	@Resource
	private CrmCsService crmCsService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyAccountContactService companyAccountContactService;
	@Resource
	private CrmCompanyProfileService crmCompanyProfileService;
	@Resource
	private CrmCsLogService crmCsLogService;
	@Resource
	private CrmCsLogAddedService crmCsLogAddedService;
	@Resource
	private CompanyService companyService;
	@Resource
	private AuthService authService;
	@Resource
	private CompanyLotteryService companyLotteryService;
	@Resource
	private CrmOutLogService crmOutLogService;
	@Resource
	private ProductsAutoRefreshService  productsAutoRefreshService;
	@Resource
	private OauthAccessService oauthAccessService;
	@Resource 
	private PhoneLostCusService phoneLostCusService;
	@Resource
	private TrustBuyLogService trustBuyLogService;
	@Resource
	private TrustCompanyLogService trustCompanyLogService;
	@Resource
	private CrmCompanySvrDao crmCompanySvrDao;
	@Resource
	private PhoneService phoneService;
	@Resource
	private PhoneCostSvrService phoneCostSvrService;

	final static String URL_ENCODE = "utf-8";
	final static int SEARCH_EXPIRED = 20000;
	final static String DATE_FORMATE_DEFAULT = "yyyy-MM-dd";
	final static String CS_DEPT_CODE = "10001005";
	
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
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request, String cs, String svrCode,
			String svrEndMonth,String nextVisitPhoneFrom,String nextVisitPhoneTo) throws ParseException {
		initCommon(request, out);

		if (svrEndMonth != null) {
			Date start = DateUtil.getDate(svrEndMonth, "yyyy-MM");
			start = DateUtil.getDate(start, "yyyy-MM-01");
			Date end = DateUtil.getDateAfterDays(DateUtil.getDateAfterMonths(start, 1), -1);
			out.put("svrEndFrom", start);
			out.put("svrEndTo", end);
		}
		if (StringUtils.isNotEmpty(nextVisitPhoneFrom)) {
			out.put("nextVisitPhoneFrom", DateUtil.getDate(nextVisitPhoneFrom, DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(nextVisitPhoneTo)) {
			out.put("nextVisitPhoneTo", DateUtil.getDate(nextVisitPhoneTo, DATE_FORMATE_DEFAULT));
		}
		out.put("svrCode", svrCode);

		if (cs != null) {
			out.put("cs", cs);
		}

		return null;
	}

	/**
	 * 一个月未联系
	 */
	@RequestMapping
	public ModelAndView index0(Map<String, Object> out,
			HttpServletRequest request) {
		Date now = new Date();
		// out.put("visitFromStr", DateUtil.toString(DateUtil.getDateAfter(now,
		// Calendar.DATE, -31), DATE_FORMATE_DEFAULT));
		out.put("visitToStr", DateUtil.toString(DateUtil.getDateAfter(now,
				Calendar.DATE, -30), DATE_FORMATE_DEFAULT));

		initCommon(request, out);
		return null;
	}

	/**
	 * 今天安排联系
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView index1(Map<String, Object> out,
			HttpServletRequest request) throws ParseException {
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
	 * 必杀期客户
	 */
	@RequestMapping
	public ModelAndView index3(Map<String, Object> out,
			HttpServletRequest request) {
		Date now = new Date();
		Date to = DateUtil.getDateAfterMonths(now, 2);
		Date from = DateUtil.getDateAfterMonths(now, -3);
		Date oneMonth = DateUtil.getDateAfterMonths(now, -1);
		oneMonth = DateUtil.getDateAfterDays(oneMonth, -1);

		out.put("svrEndFromStr", DateUtil.toString(from, DATE_FORMATE_DEFAULT));
		out.put("svrEndToStr", DateUtil.toString(to, DATE_FORMATE_DEFAULT));
		out.put("oneMonthStr", DateUtil
				.toString(oneMonth, DATE_FORMATE_DEFAULT));

		initCommon(request, out);
		return null;
	}

	/**
	 * 昨天客户处理情况
	 */
	@RequestMapping
	public ModelAndView index4(Map<String, Object> out,
			HttpServletRequest request) {
		Date from = new Date(DateUtil.getTheDayZero(-1) * 1000);
		Date to = new Date(DateUtil.getTheDayZero(0) * 1000);

		out.put("logDayFrom", DateUtil.toString(from, DATE_FORMATE_DEFAULT));
		out.put("logDayTo", DateUtil.toString(to, DATE_FORMATE_DEFAULT));
		initCommon(request, out);
		return null;
	}
	
	/**
	 * 未过期客户
	 */
	@RequestMapping
	public ModelAndView index5(Map<String, Object>out,HttpServletRequest request){
		out.put("noExpired", 1);
		initCommon(request, out);
		return null;
	}
	
	/**
	 * 过期客户
	 */
	@RequestMapping
	public ModelAndView index6(Map<String, Object>out,HttpServletRequest request){
		out.put("expired", 1);
		initCommon(request, out);
		return null;
	}
	
	/**
	 * 品牌通客户
	 */
	@RequestMapping
	public ModelAndView index7(Map<String,Object>out,HttpServletRequest request){
		out.put("pptCode", CrmSvrService.PPT_CODE);
		initCommon(request, out);
		return null;
	}

	/**
	 * @param csFlag
	 *            :表示查找自己的或公海客户, ""：表示查找全部;"0":表示查找公海;"1":表示查找指定账户的
	 * @param contact
	 *            :账户联系人
	 * @param email
	 *            :账户的邮件
	 * @param companyName
	 *            :公司名称
	 * @param mobile
	 *            :账户的手机号码
	 * @param svrCode
	 *            :网站的服务类型
	 * @param svrRangeStart
	 *            :查找某服务的服务周期(始)
	 * @param svrRangeEnd
	 *            :查找某服务的服务周期(末)
	 * @param nextVisitPhoneStart
	 *            :下次电话联系时间范围(始)
	 * @param nextVisitPhoneEnd
	 *            :下次电话联系时间范围(末)
	 * @param csAccount
	 *            :按照客服账号查找
	 * @param areaCode
	 *            :按照省市区域查找
	 * @param industryCode
	 *            :按照行业查找
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView queryCoreCompany(Map<String, Object> out,
			HttpServletRequest request, PageDto<CrmCsDto> page,
			CrmSearchDto search, String visitFromStr, String visitToStr,
			String svrEndFromStr, String svrEndToStr,
			String nextVisitPhoneFromStr, String nextVisitPhoneToStr,
			String logDayFromStr, String logDayToStr, String svrBSEndFromStr,
			String svrBSEndToStr, String oneMonthStr,String cs) throws IOException,
			ParseException {

		if (StringUtils.isNotEmpty(visitFromStr)) {
			search.setVisitFrom(DateUtil.getDate(visitFromStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(visitToStr)) {
			search.setVisitTo(DateUtil
					.getDate(visitToStr, DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(svrEndFromStr)) {
			search.setSvrEndFrom(DateUtil.getDate(svrEndFromStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(svrEndToStr)) {
			search.setSvrEndTo(DateUtil.getDate(svrEndToStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(nextVisitPhoneFromStr)) {
			search.setNextVisitPhoneFrom(DateUtil.getDate(
					nextVisitPhoneFromStr, DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(nextVisitPhoneToStr)) {
			search.setNextVisitPhoneTo(DateUtil.getDate(nextVisitPhoneToStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(logDayFromStr)) {
			search.setLogDayFrom(DateUtil.getDate(logDayFromStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(logDayToStr)) {
			search.setLogDayTo(DateUtil.getDate(logDayToStr,
					DATE_FORMATE_DEFAULT));
		}
		// 必杀期 过二个月后过期 的客户
		if (StringUtils.isNotEmpty(svrBSEndToStr)) {
			search.setSvrBSEndTo(DateUtil.getDate(svrBSEndToStr,
					DATE_FORMATE_DEFAULT));
		}
		// 必杀期 已过期三个月 的客户
		if (StringUtils.isNotEmpty(svrBSEndToStr)) {
			search.setSvrBSEndTo(DateUtil.getDate(svrBSEndToStr,
					DATE_FORMATE_DEFAULT));
		}
		// 必杀期 过期后一个月未产生有效联系小记
		if (StringUtils.isNotEmpty(oneMonthStr)) {
			search.setOneMonthBS(DateUtil.getDate(oneMonthStr,
					DATE_FORMATE_DEFAULT));
		}

		SessionUser sessionUser = getCachedUser(request);
		
		//这个是每日联系中的cs字段
		if (StringUtils.isNotEmpty(cs)) {
			search.setCsAccount(cs);
		}
		if (search.getCsAccount() != null
				&& !AuthUtils.getInstance().authorizeRight(
						"view_private_company", request, null)) {
			search.setCsAccount(sessionUser.getAccount());
		}
		
		// 进入公海 则 检索排除生意不做、号码为空
		if(StringUtils.isNotEmpty(search.getCsFlag())&&StringUtils.isEmpty(search.getOutbusiness())){
			search.setOutbusiness("0");
		}
		
		page = crmCsService.queryCoreCompany(search, sessionUser.getAccount(),page);

		// List<CrmCsDto> list=new ArrayList<CrmCsDto>();
		// list.add(crmCsService.queryCoreCompanyByCompanyId(627461,
		// search.getSvrCode()));
		// list.add(crmCsService.queryCoreCompanyByCompanyId(627462,
		// search.getSvrCode()));
		//
		// page.setTotalRecords(30);
		// page.setRecords(list);

		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView queryLdbCoreCompany(Map<String, Object> out,
			HttpServletRequest request, PageDto<CrmCsDto> page,
			CrmSearchDto search, String visitFromStr, String visitToStr,
			String svrEndFromStr, String svrEndToStr,
			String nextVisitPhoneFromStr, String nextVisitPhoneToStr,
			String logDayFromStr, String logDayToStr, String svrBSEndFromStr,
			String svrBSEndToStr, String oneMonthStr) throws IOException,
			ParseException {
		if (StringUtils.isNotEmpty(visitFromStr)) {
			search.setVisitFrom(DateUtil.getDate(visitFromStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(visitToStr)) {
			search.setVisitTo(DateUtil
					.getDate(visitToStr, DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(svrEndFromStr)) {
			search.setSvrEndFrom(DateUtil.getDate(svrEndFromStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(svrEndToStr)) {
			search.setSvrEndTo(DateUtil.getDate(svrEndToStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(nextVisitPhoneFromStr)) {
			search.setNextVisitPhoneFrom(DateUtil.getDate(
					nextVisitPhoneFromStr, DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(nextVisitPhoneToStr)) {
			search.setNextVisitPhoneTo(DateUtil.getDate(nextVisitPhoneToStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(logDayFromStr)) {
			search.setLogDayFrom(DateUtil.getDate(logDayFromStr,
					DATE_FORMATE_DEFAULT));
		}
		if (StringUtils.isNotEmpty(logDayToStr)) {
			search.setLogDayTo(DateUtil.getDate(logDayToStr,
					DATE_FORMATE_DEFAULT));
		}
		SessionUser sessionUser = getCachedUser(request);
		if (StringUtils.isNotEmpty(search.getCsAccount())&& !AuthUtils.getInstance().authorizeRight("view_private_company", request, null)) {
			search.setCsAccount(sessionUser.getAccount());
		}
		page = crmCsService.queryLdbCoreCompany(search,page);

		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView queryCs(Map<String, Object> out,
			HttpServletRequest request) throws IOException {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> m = new HashMap<String, String>();
		m.put("account", "");
		m.put("name", "全部");
		list.add(m);

		SessionUser sessionUser = getCachedUser(request);
		if (AuthUtils.getInstance().authorizeRight("view_private_company",request, null)) {
			// 查询全部的客服信息
			Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(CS_DEPT_CODE);
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
		} else {
			Map<String, String> m0 = new HashMap<String, String>();
			m0.put("account", sessionUser.getAccount());
			m0.put("name", sessionUser.getName());
			list.add(m0);
		}

		return printJson(list, out);
	}
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,
			HttpServletRequest request, Integer companyId, Integer star,
			String companyName) {
        CompanyAccount companyAccount=companyAccountService.queryAccountByCompanyId(companyId);
        //为了查询广告系统 
        String email;
        if(companyAccount==null){
        	 email="-1";
        }else email=companyAccount.getEmail();

        out.put("email", email);
		out.put("companyId", companyId);
		out.put("star", star);
		out.put("gmt_last_login",DateUtil.toString(companyAccount.getGmtLastLogin(), "yyyy-MM-dd HH:mm:ss"));
		try {
			out.put("companyName", StringUtils.decryptUrlParameter(companyName));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//pv，最近登录时间，登录次数
		CrmCsDto dto=crmCsService.queryCrmCsByCompanyId(companyId);
		out.put("crmCsProfile", dto);
		
		// 检索是否有 交易小计
		Integer i = trustCompanyLogService.countLog(companyId);
		if (i>0) {
			out.put("haveTrustLog",i);
		}else{
			i = trustBuyLogService.countLog(companyId);
			if (i>0) {
				out.put("haveTrustLog",i);
			}
		}
		//来电宝用户显示扣服务费按钮
		Company comap = companyService.queryCompanyById(companyId);
		if(comap!=null&&"10051003".equals(comap.getMembershipCode())){
			out.put("feelog", 1);
		}
		
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView queryCompanyAccount(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) throws IOException {
		return printJson(
				companyAccountService.queryAccountOfCompany(companyId), out);
	}

	@RequestMapping
	public ModelAndView queryAuthUser(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) throws IOException {
		CompanyAccount ca = companyAccountService.queryAccountByCompanyId(companyId);
		AuthUser au = authService.queryAuthUserByUsername(ca.getAccount());
		List<AuthUser> list = new ArrayList<AuthUser>();
		list.add(au);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView updateAuthUser(HttpServletRequest request,String authUsername,String authAccount, Map<String, Object> map)
			throws IOException {
		ExtResult result = new ExtResult();
		do {
			if(!AuthUtils.getInstance().authorizeRight("vip_change_account",request, null)){
				result.setData("没有权限");
				result.setSuccess(false);
				break;
			}
			if(authAccount==null){
				result.setData("帐号不能为空");
				result.setSuccess(false);
				break;
			}
			if (authService.countUserByAccount(authAccount) > 0) {
				result.setData("帐号重复");
				result.setSuccess(false);
				break;
			}
			if (authService.countUserByEmail(authAccount) > 0) {
				result.setData("邮箱重复");
				result.setSuccess(false);
				break;
			}
			authService.resetAccount(authUsername, authAccount);
			result.setData("成功");
			result.setSuccess(true);
		} while (false);
		return printJson(result, map);
	}

	@RequestMapping
	public ModelAndView updateCompanyAccount(Map<String, Object> out,
			HttpServletRequest request, CompanyAccount account)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = companyAccountService.updateAccountByAdmin(account);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryAccountContact(Map<String, Object> out,
			HttpServletRequest request, String account) throws IOException {
		List<CompanyAccountContact> list = companyAccountContactService
				.queryContactOfAccount(account);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView queryProfile(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) throws IOException {
		CrmCompanyProfile profile = crmCompanyProfileService
				.queryProfile(companyId);
		List<CrmCompanyProfile> list = new ArrayList<CrmCompanyProfile>();
		list.add(profile);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView queryCsLogOfCompany(Map<String, Object> out,
			HttpServletRequest request, Integer companyId,
			PageDto<CrmCsLogDto> page, Integer callType, Integer star,
			String csAccount, Integer situation, String from, String to)
			throws IOException {
		page.setSort("gmt_created");
		page.setDir("desc");
		page = crmCsLogService.pageLogByCompany(companyId, callType, star,
				csAccount, situation, from, to, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView saveProfile(Map<String, Object> out,
			HttpServletRequest request, CrmCompanyProfile profile)
			throws IOException {
		Integer i;
		if (profile.getId() != null && profile.getId().intValue() > 0) {
			i = crmCompanyProfileService.updateProfile(profile);
		} else {
			i = crmCompanyProfileService.createProfile(profile);
		}

		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView saveLog(Map<String, Object> out,
			HttpServletRequest request, CrmCsLog log, Integer newStar,
			String gmtNextVisitEmailDate, String gmtNextVisitPhoneDate)
			throws IOException {
		log.setCsAccount(getCachedUser(request).getAccount());

		try {
			if (StringUtils.isNotEmpty(gmtNextVisitPhoneDate)) {
				log.setGmtNextVisitPhone(DateUtil.getDate(
						gmtNextVisitPhoneDate, "yyyy-M-d"));
			}
			if (StringUtils.isNotEmpty(gmtNextVisitEmailDate)) {
				log.setGmtNextVisitEmail(DateUtil.getDate(
						gmtNextVisitEmailDate, "yyyy-M-d"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Integer i = crmCsLogService.createCsLog(log, newStar);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			PhoneLostCus phoneLostCus=phoneLostCusService.queryPhoneLostCusBycompanyId(log.getCompanyId());
			if(phoneLostCus!=null){
				phoneLostCusService.deletePhoneLostCusBycompanyId(phoneLostCus.getCompanyId());
			}
			result.setSuccess(true);
		}
		
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView addedLog(Map<String, Object> out,
			HttpServletRequest request, CrmCsLogAdded added, Integer companyId)
			throws IOException {
		added.setCsAccount(getCachedUser(request).getAccount());
		Integer i = crmCsLogAddedService.createAdded(added, companyId);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 扔公海 
	 * @param out
	 * @param request
	 * @param companyId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView dropUser(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) throws IOException {
		String account = getCachedUser(request).getAccount();
		// 丢公海必须是自己的客户
		Boolean b = crmCsService.intoHighSea(account, companyId);
		ExtResult result = new ExtResult();
		result.setSuccess(b);
		if(b){
			crmOutLogService.insert(companyId, account,account, CrmOutLogService.STATUS_OUT);
		}
		return printJson(result, out);
	}

	/**
	 * 主管分配客户  捞公海
	 * @param out
	 * @param request
	 * @param oldCsAccount
	 * @param companyId
	 * @param csAccount
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView reassign(Map<String, Object> out,
			HttpServletRequest request, String oldCsAccount, Integer companyId,
			String csAccount,Integer assignFlag) throws IOException {
		String oldOwner="";
		if (StringUtils.isNotEmpty(oldCsAccount)) {
			oldOwner=oldCsAccount;
		}else {
			CrmOutLog crmOutLog=crmOutLogService.queryCrmoutLogByCompanyId(companyId);
			if(crmOutLog!=null&&crmOutLog.getOldCsAccount()!=null){
				oldOwner=crmOutLog.getOldCsAccount();
			}
		}
		Boolean b = crmCsService.reassign(oldCsAccount, csAccount, companyId);
		ExtResult result = new ExtResult();
		result.setSuccess(b);
		// 判断是捞公海还是主管分配
		String assignType;
		if(assignFlag!=null&&1==assignFlag){
			assignType = CrmOutLogService.STATUS_ASSIGN;
		}else{
			assignType = CrmOutLogService.STATUS_IN;
		}
		// 记录捞公海
		if(b){
			crmOutLogService.insert(companyId, oldOwner,csAccount, assignType);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView logOfCompany(HttpServletRequest request,
			Map<String, Object> out, Integer companyId, Integer readonly) {
		out.put("companyId", companyId);
		Company company = companyService.querySimpleCompanyById(companyId);
		if (company != null) {
			out.put("companyName", company.getName());
		}
		out.put("readonly", readonly);
		return null;
	}
	
	@RequestMapping
	public ModelAndView getICDAccount(Map<String, Object> out,Integer companyId) throws HttpException, IOException{
		ExtResult result = new ExtResult();
		String data = HttpUtils.getInstance().httpGet(ParamUtils.getInstance().getValue("baseConfig", "crm_url")+"/?com_id="+companyId, HttpUtils.CHARSET_UTF8);
		result.setData(data);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView addToLottery(HttpServletRequest request,Map<String,Object> out,Integer companyId){
		out.put("companyId", companyId);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView doAddToLottery(HttpServletRequest request,Map<String,Object> out,Integer companyId,String lotteryCode) throws IOException{
		SessionUser sessionUser = getCachedUser(request);
		CompanyAccount account = companyAccountService.queryAccountByCompanyId(companyId);
		ExtResult result = new ExtResult();
		if(account!=null){
			Integer i = companyLotteryService.addOne(companyId,lotteryCode,account.getAccount(),sessionUser.getAccount());
			if(i>0){
				result.setSuccess(true);
				result.setData("保存成功,帐号："+account.getAccount()+"增加了一次抽奖机会");
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView manageLottery(CompanyLottery companyLottery,Map<String,Object>out,PageDto<CompanyLottery>page) throws IOException{
		page.setSort("gmt_created");
		page.setDir("desc");
		page = companyLotteryService.pageCompanyLottery(companyLottery, page);
		out.put("page", page);
		out.put("companyLottery", companyLottery);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView updateToOpen(Integer id,String status,String startIndex){
		// 开通一条记录 根据id
		companyLotteryService.openOneLottery(id);
		return new ModelAndView("redirect:manageLottery.htm?status="+status+"&startIndex="+startIndex);
	}

	@RequestMapping
	public ModelAndView updateToClose(Integer id,String status,String startIndex){
		// 关闭一条记录 根据id
		companyLotteryService.closeOneLottery(id);
		return new ModelAndView("redirect:manageLottery.htm?status="+status+"&startIndex="+startIndex);
	}
	
	@RequestMapping
	public ModelAndView updateOutbusiness(Map<String,Object> out,Integer companyId,String type) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = crmCompanyProfileService.updateOutbusiness(companyId, type);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	//以下方法用于cs设置公司供求信息自动刷新设置
	@RequestMapping
	public ModelAndView init(Integer companyId, Map<String, Object> map)
			throws IOException {
		ProductsAutoRefresh productsAutoRefresh = productsAutoRefreshService.queryByCid(companyId);
		List<ProductsAutoRefresh> list = new ArrayList<ProductsAutoRefresh>();
		list.add(productsAutoRefresh);

		return printJson(list, map);
	}
	@RequestMapping
	public ModelAndView insertOrUpdate(ProductsAutoRefresh productsAutoRefresh, Map<String, Object> map)
			throws IOException {
		ExtResult result = new ExtResult();
		result.setSuccess(false);
		ProductsAutoRefresh autoRefresh = productsAutoRefreshService.queryByCid(productsAutoRefresh.getCompanyId());
		if (autoRefresh != null) {
			Integer i = productsAutoRefreshService.updateRefreshDate(productsAutoRefresh.getRefreshDate(), productsAutoRefresh.getRefreshRate(), productsAutoRefresh.getId());
			if (i != null && i.intValue()>0) {
				result.setSuccess(true);
			}
		} else {
			Integer i = productsAutoRefreshService.insertRefreshDate(productsAutoRefresh);
			if (i != null && i.intValue()>0) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		}
		return printJson(result, map);
	}
	@RequestMapping
	public ModelAndView closeRefreshDate(Integer id, Map<String, Object> map)
			throws IOException {
		ExtResult result = new ExtResult();
		result.setSuccess(false);
		Integer i = productsAutoRefreshService.updateRefreshDate("", null, id);
		if (i != null && i.intValue()>0) {
			result.setSuccess(true);
		}
		return printJson(result, map);
	}
	@RequestMapping
	public ModelAndView deleteWeiXin(Map<String, Object> map,Integer cid) throws IOException{
		ExtResult result = new ExtResult();
		result.setSuccess(false);
		CompanyAccount account=companyAccountService.queryAccountByCompanyId(cid);
		if(account!=null){
			//判断一下该用户有没有绑定微信
			OauthAccess oa=oauthAccessService.queryAccessByAccountAndType(account.getAccount(), "weixin.qq.com");
			//绑定微信
			if(oa!=null){
				oauthAccessService.deleteByTargetAccount(account.getAccount());
				result.setData("用户"+account.getAccount()+"微信");
				result.setSuccess(true);
			}else{
				//没有绑定微信
				result.setData("用户"+account.getAccount()+"没有绑定微信，无需解绑");
			}
		}else{
			result.setData("不存在该公司，请检查");
		}
		return printJson(result, map);
	}
	@RequestMapping
	public ModelAndView reduceServiceFee(Map<String, Object> out,HttpServletRequest request, 
			String preStartTime,String preEndTime,String startTime,String endTime,String signedTime,
			CrmCompanySvr svr)throws Exception {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);//操作人信息
		if(StringUtils.isNotEmpty(preStartTime)){
				svr.setGmtPreStart(DateUtil.getDate(preStartTime, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotEmpty(preEndTime)){
				svr.setGmtPreEnd(DateUtil.getDate(preEndTime, "yyyy-MM-dd"));
		}
		svr.setGmtStart(DateUtil.getDate(startTime, "yyyy-MM-dd"));
		svr.setGmtEnd(DateUtil.getDate(endTime, "yyyy-MM-dd"));
		svr.setGmtSigned(DateUtil.getDate(signedTime, "yyyy-MM-dd"));
		svr.setCrmServiceCode("1012");
		svr.setApplyGroup("1");
		svr.setApplyStatus("1");
		if("备注".equals(svr.getRemark())){
			svr.setRemark("");
		}
		svr.setRemark(svr.getRemark()+" "+"扣费人：" + sessionUser.getName());
		//总费用
		Phone phone = phoneService.queryByCompanyId(svr.getCompanyId());
		if(phone != null){
			Integer amount = Integer.valueOf(phone.getAmount())-500;
			Integer a = phoneService.updateAmountByCompanyId(String.valueOf(amount), svr.getCompanyId());
			 if(a > 0){
				//扣费
				Integer r = phoneCostSvrService.reduceServiceFee(svr.getCompanyId(), 500f);
				if(r > 0){
					svr.setRemark(svr.getRemark());
				}else{
					svr.setRemark(svr.getRemark() + "," + "扣费不成功，但是总费被扣了");
				}
				Integer i = crmCompanySvrDao.insertCompanySvr(svr);
				if(i > 0){
					if(r>0){
						result.setSuccess(true);
						result.setData("服务费扣除成功！");
					}else{
						result.setSuccess(false);
						result.setData(svr.getRemark() + "," + "扣费不成功，但是总费被扣了");
					}
				}else{
					if(r>0){
						result.setSuccess(false);
						result.setData("服务费扣除成功,但是扣费记录生成失败！");
					}else{
						result.setSuccess(false);
						result.setData("扣费不成功，但是总费被扣了！");
					}
				}
			}
		}
		return printJson(result, out);
	}
}
