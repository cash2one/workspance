package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.MyrcMessage;
import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.domain.phone.LdbLevel;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyCouponService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.company.SubscribeService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.service.market.MyrcMessageService;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.ast.ast1949.service.phone.LdbLevelService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductsHideService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.sample.OrderBillService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class MyrcController extends BaseController {

	final static String NO_PER_URL = "/mycompany/updateCompany.htm";

	final static int INEX_PRODUCTS_NUM = 8;

	@Resource
	private AuthService authService;
	@Resource
	private ProductsService productsService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Resource
	private CrmCsService crmCsService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private MyrcService myrcService;
	@Resource
	private SubscribeService subscribeService;
	@Resource
	private CompanyAttestService companyAttestService;
	@Resource
	private PhoneLogService phoneLogService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private OauthAccessService oauthAccessService;
	@Resource
	private OrderBillService orderBillService;
	@Resource
	private CompanyCouponService companyCouponService;
	@Resource
	private ProductsHideService productsHideService;
	@Resource
	private MarketCompanyService marketCompanyService;
	@Resource
	private MarketService marketService;
	@Resource
	private MyrcMessageService myrcMessageService;
	@Resource
	private LdbLevelService ldbLevelService;

	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) throws Exception {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		// 判断是否终身制会员
		if (crmCompanySvrService.validatePeriod(sessionUser.getCompanyId(),
				"10001003")) {
			out.put("isZSVip", true);
		} else {
			out.put("isZSVip", false);
		}
		out.put(FrontConst.MYRC_SUBTITLE, "生意管家");
		out.put("membershipLable",
				CategoryFacade.getInstance().getValue(
						sessionUser.getMembershipCode()));

		Integer num = inquiryService.countUnviewedInquiry(null,
				sessionUser.getAccount(), sessionUser.getCompanyId());
		out.put("unviewedRecordNumber", num != null ? num : 0);
		// 计算发布了多少供求信息
		Integer publishedProducts = productsService
				.countProductsByCompanyId(sessionUser.getCompanyId());
		out.put("publishedProducts",
				publishedProducts != null ? publishedProducts : 0);

		Date dt = productsService.queryMaxRefreshTimeByCompanyId(sessionUser
				.getCompanyId());
		if (dt != null) {
			int days = DateUtil.getIntervalDays(new Date(), dt);
			out.put("days", days);
		}

		out.put("today", DateUtil.toString(new Date(), "yyyy-MM-dd"));

		CrmCompanySvr svr = crmCompanySvrService.queryZstSvr(sessionUser
				.getCompanyId());
		Boolean flag = crmCompanySvrService.validateLDB(
				sessionUser.getCompanyId(), "1007", "1008");
		if (svr != null || flag) {
			if (svr != null) {
				long start = DateUtil.getMillis(svr.getGmtStart());
				long end = DateUtil.getMillis(svr.getGmtEnd());
				long now = DateUtil.getMillis(new Date());
				if (end - start == 0) {
					out.put("membershipPercent", 100);
				} else {
					out.put("membershipPercent",
							(100 * (now - start) / (end - start)));
				}
				out.put("membershipFromDate",
						DateUtil.toString(svr.getGmtStart(), "yyyy-MM-dd"));
				out.put("membershipEndDate",
						DateUtil.toString(svr.getGmtEnd(), "yyyy-MM-dd"));
			}

			CrmCs cs = crmCsService
					.queryCsOfCompany(sessionUser.getCompanyId());
			if (cs != null && StringUtils.isNotEmpty(cs.getCsAccount())) {

				String csinfo = ParamUtils.getInstance().getValue("cs_info",
						cs.getCsAccount());
				if (csinfo != null) {
					String[] info = csinfo.split(",");
					if (info.length >= 1) {
						out.put("cs_name", info[0]);
					}
					if (info.length >= 2) {
						out.put("cs_phone", info[1]);
					}
					if (info.length >= 3) {
						out.put("cs_email", info[2]);
					}
				}
			}

			out.put("domainInfo", companyService
					.queryDomainOfCompany(sessionUser.getCompanyId()));
			// 获取公司名称
			out.put("compName",
					companyService.queryCompanyById(sessionUser.getCompanyId())
							.getName());
			// 查看公司认证信息是否认证
			CompanyAttest companyAttest = companyAttestService
					.queryAttestByCid(sessionUser.getCompanyId());
			if (companyAttest != null) {
				out.put("attestStatus", companyAttest.getCheckStatus());
			}
		}
		// zhengrp判断再生通或品牌通客户是否过期
		String gmtend = "";
		// 普通会员有无再生通和品牌通查询
		if (sessionUser.getMembershipCode().equals("10051000")) {
			// 判断是否有开通过再生通和品牌通的历史
			Integer history = crmCompanySvrService.validatehistory(
					sessionUser.getCompanyId(), "1000");
			if (history != null && history.intValue() > 0) {
				// 判断服务是否过期
				boolean judge = crmCompanySvrService.validatePeriod(
						sessionUser.getCompanyId(), "1000");
				if (judge) {
					gmtend = "";
				} else {
					gmtend = "gmtend";
				}
			}
		}
		// 页面返回gmtend参数，如果不满足条件则未初始值
		out.put("gmtend", gmtend);

		// 帐号信息
		CompanyAccount myaccount = companyAccountService
				.queryAccountByAccount(sessionUser.getAccount());
		out.put("myaccount", myaccount);
		if (StringUtils.isEmpty(myaccount.getEmail())) {
			out.put("email", 1);
		}

		// 判断激活状态
		if (!companyValidateService.isValidate(sessionUser.getAccount())) {
			out.put("compValidate", 1);
		}

		// 电话号码配置都去
		out.put("serviceTel",
				ParamUtils.getInstance().getValue("myrc_config",
						"member_service_tel"));
		out.put("upTel",
				ParamUtils.getInstance().getValue("myrc_config",
						"member_up_tel"));

		// 获取用户消息信息 （问答、供求、帖子、询盘 条数）
		companyService.countCompanyInfo(sessionUser.getCompanyId(), out);

		// 来电宝用户判断
		do {
			Phone phone = phoneService.queryByCompanyId(sessionUser
					.getCompanyId());
			if (phone == null) {
				break;
			}
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("phone", phone);

			// 获取来电宝积分信息
			LdbLevel lb = ldbLevelService.queryByCompanyId(sessionUser
					.getCompanyId());
			out.put("ldbLevel", lb);
		} while (false);

		// 最新供求
		List<SubscribeDO> list = subscribeService
				.querySubscribeByCompanyIdAndSubscribeType(
						sessionUser.getCompanyId(), "0");
		if (list.size() > 0) {
			List<ProductsDto> list2 = productsService.queryVipProductsForMyrc(
					list.get(0).getKeywords(), 6);
			out.put("list2", list2);
		} else {
			Company company = companyService.queryCompanyById(sessionUser
					.getCompanyId());
			List<ProductsDto> list2 = productsService.queryNewestVipProducts(
					null, company.getIndustryCode(), 6);
			out.put("list2", list2);
		}

		// 资讯中心
		String newsData = "";
		try {
			newsData = HttpUtils.getInstance().httpGet(
					AddressTool.getAddress("pyapp") + "/news/javagetnewslist.html?num=6",
					HttpUtils.CHARSET_UTF8);
		} catch (Exception e) {
			newsData = null;
		}
		if (StringUtils.isNotEmpty(newsData)) {
			out.put("newsHtml", newsData);
		}

		// 验证是否手机绑定
		AuthUser au = authService.queryAuthUserByUsername(sessionUser
				.getAccount());
		if (au != null) {
			out.put("au", au);
		}

		// 验证是否微信绑定
		OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(
				sessionUser.getAccount(), OauthAccessService.OPEN_TYPE_WEIXIN);
		if (oa != null) {
			out.put("oa", oa);
		}

		// 样品信息
		orderBillService.queryBuyListByCompanyIdCount(
				sessionUser.getCompanyId(), null, null, null, out);
		orderBillService.querySellListByCompanyIdCount(
				sessionUser.getCompanyId(), null, null, null, out);
		// 给普会的弹出消息提醒弹框(排除高会，来电宝和百度优化客户)
		List<CrmCompanySvr> ccsList = crmCompanySvrService
				.querySvrByCompanyId(sessionUser.getCompanyId());
		if (ccsList == null || ccsList.size() <= 0) {
			ProductsDO products = new ProductsDO();
			products.setCheckStatus("1");
			products.setCompanyId(sessionUser.getCompanyId());
			// 统计该客户有多少过期供求数据
			Integer expireNum = productsService
					.countExpireProductByCompanyId(products);
			// 统计该客户长期未刷新并长期有效的供求数量
			products.setExpireTime(DateUtil.getDate("9999-12-31 23:59:59",
					"yyyy-MM-dd HH:mm:ss"));
			Integer validNum = productsService
					.countValidProductByCompanyId(products);

			if (expireNum.intValue() > 0) {
				out.put("expireNum", expireNum);
			}
			if (validNum.intValue() > 0) {
				out.put("validNum", validNum);
			}
		}
		// 该客户隐藏信息的条数
		Integer hideNum = productsHideService.countByCompanyId(sessionUser
				.getCompanyId());
		if (hideNum.intValue() > 0) {
			out.put("hideNum", hideNum);
		}

		// 双十一 优惠券 获取
		// Date today = new Date();
		// Date sday = DateUtil.getDate("2014-11-12", "yyyy-MM-dd");
		// if (today.getTime()<sday.getTime()) {
		// List<CompanyCoupon> couponList =
		// companyCouponService.queryForMyrc(sessionUser.getCompanyId());
		// for (CompanyCoupon obj : couponList) {
		// if (obj.getType()==1) {
		// int i = 0;
		// if (obj.getServiceName().indexOf("来电宝")!=-1) {
		// i = 1;
		// }
		// if (obj.getServiceName().indexOf("再生通一年")!=-1) {
		// i = 2;
		// }
		// if (obj.getServiceName().indexOf("再生通两年")!=-1) {
		// i = 3;
		// }
		// if (obj.getServiceName().indexOf("金牌通")!=-1) {
		// i = 4;
		// }
		// out.put("reducePrice", i+"_"+obj.getReducePrice());
		// }
		// String [] codeArray = obj.getCode().split("-");
		// obj.setCode(codeArray[codeArray.length-1]);
		// }
		// out.put("couponList", couponList);
		// }
		Integer marketi = (Integer) out.get("countInfo");
		if (marketi >= 60) {
			getMarket(out, sessionUser.getCompanyId());
		}
		return null;
	}

	@RequestMapping
	public ModelAndView oldIndex(Map<String, Object> out,
			HttpServletRequest request) throws Exception {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		// 判断是否信息确实，进入公司修改页
		// if(StringUtils.isEmpty(companyService.queryCompanyNameById(sessionUser.getCompanyId()))){
		// return new
		// ModelAndView("redirect:/mycompany/updateCompany.htm?firstLogin=1");
		// }
		// 最新供求
		List<SubscribeDO> list = subscribeService
				.querySubscribeByCompanyIdAndSubscribeType(
						sessionUser.getCompanyId(), "0");
		if (list.size() > 0) {
			List<ProductsDto> list2 = productsService.queryVipProductsForMyrc(
					list.get(0).getKeywords(), 6);
			out.put("list2", list2);
		} else {
			Company company = companyService.queryCompanyById(sessionUser
					.getCompanyId());
			List<ProductsDto> list2 = productsService.queryNewestVipProducts(
					null, company.getIndustryCode(), 6);
			out.put("list2", list2);
		}

		out.put(FrontConst.MYRC_SUBTITLE, "生意管家");
		out.put("membershipLable",
				CategoryFacade.getInstance().getValue(
						sessionUser.getMembershipCode()));

		Integer num = inquiryService.countUnviewedInquiry(null,
				sessionUser.getAccount(), sessionUser.getCompanyId());
		out.put("unviewedRecordNumber", num != null ? num : 0);
		// 计算发布了多少供求信息
		Integer publishedProducts = productsService
				.countProductsByCompanyId(sessionUser.getCompanyId());
		out.put("publishedProducts",
				publishedProducts != null ? publishedProducts : 0);

		Date dt = productsService.queryMaxRefreshTimeByCompanyId(sessionUser
				.getCompanyId());
		if (dt != null) {
			int days = DateUtil.getIntervalDays(new Date(), dt);
			out.put("days", days);
		}

		// 最新话题
		// List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(9);
		// out.put("frontBbsPostList", frontBbsPostList);
		out.put("today", DateUtil.toString(new Date(), "yyyy-MM-dd"));

		CrmCompanySvr svr = crmCompanySvrService.queryZstSvr(sessionUser
				.getCompanyId());
		if (svr != null) {
			long start = DateUtil.getMillis(svr.getGmtStart());
			long end = DateUtil.getMillis(svr.getGmtEnd());
			long now = DateUtil.getMillis(new Date());
			if (end - start == 0) {
				out.put("membershipPercent", 100);
			} else {
				out.put("membershipPercent",
						(100 * (now - start) / (end - start)));
			}
			out.put("membershipFromDate",
					DateUtil.toString(svr.getGmtStart(), "yyyy-MM-dd"));
			out.put("membershipEndDate",
					DateUtil.toString(svr.getGmtEnd(), "yyyy-MM-dd"));

			CrmCs cs = crmCsService
					.queryCsOfCompany(sessionUser.getCompanyId());
			if (cs != null && StringUtils.isNotEmpty(cs.getCsAccount())) {

				String csinfo = ParamUtils.getInstance().getValue("cs_info",
						cs.getCsAccount());
				if (csinfo != null) {
					String[] info = csinfo.split(",");
					if (info.length >= 1) {
						out.put("cs_name", info[0]);
					}
					if (info.length >= 2) {
						out.put("cs_phone", info[1]);
					}
					if (info.length >= 3) {
						out.put("cs_email", info[2]);
					}
				}
			}

			out.put("domainInfo", companyService
					.queryDomainOfCompany(sessionUser.getCompanyId()));

			// 获取公司名称
			out.put("compName",
					companyService.queryCompanyById(sessionUser.getCompanyId())
							.getName());
			// 查看公司认证信息是否认证
			CompanyAttest companyAttest = companyAttestService
					.queryAttestByCid(sessionUser.getCompanyId());
			if (companyAttest != null) {
				out.put("attestStatus", companyAttest.getCheckStatus());
			}

		}

		// 非高会，显示普会信息
		CompanyAccount myaccount = companyAccountService
				.queryAccountByAccount(sessionUser.getAccount());
		out.put("myaccount", myaccount);

		// List<SubscribeDO> subscribeList =
		// subscribeService.querySubscribeByCompanyIdAndSubscribeType(sessionUser.getCompanyId(),
		// "0");
		// out.put("subscribeList", subscribeList);

		// 判断激活状态
		if (!companyValidateService.isValidate(sessionUser.getAccount())) {
			out.put("compValidate", 1);
		}

		// 电话号码配置都去
		out.put("serviceTel",
				ParamUtils.getInstance().getValue("myrc_config",
						"member_service_tel"));
		out.put("upTel",
				ParamUtils.getInstance().getValue("myrc_config",
						"member_up_tel"));

		// 判断信息是否完善
		String checkInfo = companyService.validateCompanyInfo(sessionUser);
		out.put("checkInfo", checkInfo);

		return null;
	}

	@RequestMapping
	public void changepassword(Map<String, Object> out,
			HttpServletRequest request, String error) {
		SsoUser sessionUser = getCachedUser(request);
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());

		Company company = companyService.queryCompanyById(sessionUser
				.getCompanyId());
		CompanyAccount companyAccount = companyAccountService
				.queryAccountByAccount(sessionUser.getAccount());
		out.put("company", company);
		out.put("companyAccount", companyAccount);

		out.put(FrontConst.MYRC_SUBTITLE, "更改密码");

		out.put("error", error);
	}

	@RequestMapping
	public ModelAndView savepassword(Map<String, Object> model,
			HttpServletRequest request, String originalPassword,
			String newPassword, String verifyPassword) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		boolean result = false;
		// 获取ip地址
		String ip = HttpUtils.getInstance().getIpAddr(request);
		try {
			result = companyAccountService.changePassword(
					sessionUser.getAccount(), originalPassword, newPassword,
					verifyPassword);
			if (!result) {
				model.put("error", "1");
			} else {
				// 修改密码日志
				LogUtil.getInstance().log(
						"myrc",
						"myrc-operate",
						ip,
						"{'account':'" + sessionUser.getAccount()
								+ "','operatype_id':'9','gmt_created':'"
								+ DateUtil.toString(new Date(), DATE_FORMAT)
								+ "'}", "myrc");
				model.put("error", "0");
			}
		} catch (NoSuchAlgorithmException e) {
			model.put("error", "1");
		} catch (AuthorizeException e) {
			model.put("error", "1");
		}
		return new ModelAndView("changepassword");
	}

	@RequestMapping
	public void changeaccountname(Map<String, Object> out,
			HttpServletRequest request) {
		// AuthUser user = getCachedAuthUser(request);
		// TODO 修改用户名，实际是修改auth_user的account字段，username字段为注册时填写信息，永远无法修改
		SsoUser sessionUser = getCachedUser(request);
		CompanyAccount companyAccount = companyAccountService
				.queryAccountByAccount(sessionUser.getAccount());
		out.put("companyAccount", companyAccount);

		sessionUser = companyAccountService.initSessionUser(sessionUser
				.getAccount());
		boolean isEmailUserName = StringUtils.isEmail(companyAccount
				.getAccount());
		boolean isEmptyAccount = StringUtils.isEmpty(sessionUser.getAccount());
		boolean isChineseUserName = false;
		out.put("sessionUser", sessionUser);
		try {
			isChineseUserName = StringUtils.isContainCNChar(companyAccount
					.getAccount());
		} catch (UnsupportedEncodingException e) {
		}

		if (isChineseUserName || isEmailUserName) {
			if (isEmptyAccount) {
				out.put("enableModified", 1);
			}
		}

		/*
		 * if(isChineseUserName && isEmailUserName && isEmptyAccount){
		 * out.put("enableModified", 1); }
		 */
	}

	@RequestMapping
	public ModelAndView saveaccountname(Map<String, Object> model,
			HttpServletRequest request, String newUserName) throws IOException {
		ExtResult result = new ExtResult();

		if (StringUtils.isNotEmpty(newUserName)) {
			try {

				SsoUser sessionUser = getCachedUser(request);
				Integer count = authService.resetAccount(
						sessionUser.getAccount(), newUserName);
				if (count > 0) {
					result.setSuccess(true);
				} else {
					result.setSuccess(false);
				}
			} catch (Exception e) {
			}
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView loadesite(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		ExtResult result = new ExtResult();
		Company company = companyService.queryDomainOfCompany(id);
		result.setData(company);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView loadaccount(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		ExtResult result = new ExtResult();
		CompanyAccount account = companyAccountService
				.queryAccountByAccount(getCachedUser(request).getAccount());
		result.setData(account);
		return printJson(result, out);
	}

	// 新添加的
	@RequestMapping
	public ModelAndView queryCategoryCompanyPrice(Map<String, Object> map)
			throws IOException {
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceService
				.queryCategoryCompanyPrice();
		return printJson(list, map);
	}

	@RequestMapping
	public ModelAndView queryCategoryCompanyPriceByCode(String code,
			Map<String, Object> map) throws IOException {
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceService
				.queryCategoryCompanyPriceByCode(code);
		return printJson(list, map);
	}

	@RequestMapping
	public void ajaxUpload(Map<String, Object> out, String model,
			String filetype, String control) {
		out.put("filetype", filetype);
		out.put("model", model);
		out.put("control", control);
	}

	@RequestMapping
	public ModelAndView checkUsername(Map<String, Object> model, String account)
			throws IOException {
		ExtResult result = new ExtResult();
		if (StringUtils.isNotEmpty(account)) {
			Integer userCount = authService.countUserByAccount(account);
			if (userCount == null || userCount == 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, String url, Map<String, Object> model)
			throws IOException {
		ExtResult extResult = new ExtResult();

		SsoUtils.getInstance().logout(request, response, null);
		extResult.setSuccess(true);
		if (StringUtils.isEmpty(url)) {
			extResult.setData("index.htm");
		} else {
			extResult.setData(url);
		}
		return printJson(extResult, model);
	}

	@RequestMapping
	public ModelAndView dologout(HttpServletRequest request,
			Map<String, Object> out, HttpServletResponse response) {
		SsoUser sessionUser = getCachedUser(request);
		// 获取ip地址
		String ip = HttpUtils.getInstance().getIpAddr(request);
		// 退出日志
		LogUtil.getInstance().log(
				"myrc",
				"myrc-operate",
				ip,
				"{'account':'" + sessionUser.getAccount()
						+ "','operatype_id':'11','gmt_created':'"
						+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}",
				"myrc");
		SsoUtils.getInstance().logout(request, response, null);
		// return new ModelAndView(new
		// RedirectView(AddressTool.getAddress("front")));

		// 清除改cookie
		String cookie = HttpUtils.getInstance().getCookie(request,
				AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
		authService.removeAuthLoginByCookie(cookie);

		return new ModelAndView("logout/index");
	}

	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			// data=URLDecoder.decode(data, HttpUtils.CHARSET_UTF8);
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}

	@RequestMapping
	public ModelAndView unViewedMessage(HttpServletRequest request,
			Map<String, Object> out) {
		SsoUser sessionUser = getCachedUser(request);
		Integer num = inquiryService.countUnviewedInquiry(null,
				sessionUser.getAccount(), sessionUser.getCompanyId());
		if (num == null) {
			num = 0;
		}
		out.put("num", num);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView sendValidate(HttpServletRequest request,
			Map<String, Object> out) {
		companyValidateService.sendValidateByEmail(getCachedUser(request)
				.getAccount(), null);
		return null;
	}

	public void getMarket(Map<String, Object> out, Integer companyId) {

		Company company = companyService.queryCompanyById(companyId);
		Integer mark = 1;// 1表示要弹，0表示不弹
		String industry = "";
		String address = "";
		List<Market> listM = new ArrayList<Market>();
		// 是否已经入驻过
		List<Market> list = marketCompanyService
				.queryMarketByCompanyId(companyId);
		// 主营行业
		if (list.size() > 0 || !"10001000".equals(company.getIndustryCode())
				&& !"10001001".equals(company.getIndustryCode())
				&& !"10001007".equals(company.getIndustryCode())) {
			mark = 0;
		} else {
			if ("10001000".equals(company.getIndustryCode())) {
				industry = "废塑料";
			} else if ("10001001".equals(company.getIndustryCode())) {
				industry = "废金属";
			} else if ("10001007".equals(company.getIndustryCode())) {
				industry = "二手设备";
			}
			// 地址
			if (company.getAreaCode().length() < 15) {
				mark = 0;
			} else {
				address = CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 12))
						+ " "
						+ CategoryFacade.getInstance().getValue(
								company.getAreaCode().substring(0, 16));
			}
			listM = marketService.queryMarketByCondition(industry, address, 15);
			if (listM.size() == 0) {// 20071000
				mark = 0;
			}
			// 暂时不入驻的
			Integer count = myrcMessageService.queryMyrcMessageByCIdAndType(
					companyId, "20071000", null);
			if (count > 0) {
				mark = 0;
			}
		}
		if (mark == 1) {
			out.put("listM", listM);
		}
		out.put("marketMark", mark);
		out.put("industry", industry);
		out.put("addr", address);
	}

	@RequestMapping
	public ModelAndView reduceMark(HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		MyrcMessage myrcMessage = new MyrcMessage();
		myrcMessage.setCompanyId(sessionUser.getCompanyId());
		myrcMessage.setIsRead(1);
		myrcMessage.setType("20071000");
		myrcMessageService.insertMyrcMessage(myrcMessage);
		return printJson(null, out);
	}
}
