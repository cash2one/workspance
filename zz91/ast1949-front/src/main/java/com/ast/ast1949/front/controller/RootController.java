/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010 下午04:58:55
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.auth.AuthAutoLogin;
import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.site.FriendLinkDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.information.ChartDataForIndexDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.site.FriendLinkDTO;
import com.ast.ast1949.exception.RegisterException;
import com.ast.ast1949.front.util.FrontConst;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.information.ChartDataService;
import com.ast.ast1949.service.information.ExhibitService;
import com.ast.ast1949.service.log.LogOperationService;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.site.FriendLinkService;
import com.ast.ast1949.service.site.WebBaseDataStatService;
import com.ast.ast1949.service.spot.SpotService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.NavConst;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.auth.frontsso.SsoConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * @author Ryan
 */
@Controller
public class RootController extends BaseController {

	@Autowired
	private AuthService authService;
	@Autowired
	private CompanyPriceService companyPriceService;
	@Autowired
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Autowired
	private PriceCategoryService priceCategoryService;
	@Autowired
	private FriendLinkService friendLinkService;
	@Autowired
	private ExhibitService exhibitService;
	@Autowired
	private ChartDataService chartDataService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private OauthAccessService oauthAccessService;
	@Resource
	WebBaseDataStatService webBaseDataStatService;
	@Resource
	private SpotService spotService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyService companyService;
	@Resource
	private PriceService priceService;
	@Resource
	private LogOperationService logOperationService;
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// @Resource
	// private DataIndexService dataIndexService;
	// @Resource
	// private DataIndexCategoryService dataIndexCategoryService;

	@RequestMapping
	public void loginFrame(HttpServletRequest request, Map<String, Object> out) {
		out.put("openStatus",
				ParamUtils.getInstance().getValue("baseConfig", "open_status"));
	}

	@RequestMapping
	public void enterprice() {

	}

	final static String PAGE_INDEX = "1000";

	@RequestMapping
	public ModelAndView oldIndex(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out)
			throws ParseException {
		// long start=System.currentTimeMillis();
		// 设置页面头部信息
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.INDEX);
		headDTO.setPageTitle("废金属_废塑料_废纸_纺织废料_再生资源_废橡胶_${site_name}");
		headDTO.setPageKeywords("废金属,废塑料,废纸,纺织废料,再生资源,废橡胶,废铜,废铝,废铁,企业报价,塑料颗粒,二手设备");
		headDTO.setPageDescription("${site_name}是中国最大最旺的网上废料贸易市场,这里为您精选了废金属,废塑料,"
				+ "废纸,废橡胶,纺织废料,废旧设备,废电子电器,废玻璃,废皮革,废铜,废铝,废铁,企业报价,塑料颗粒,"
				+ "二手设备等各类废料供求信息.公司黄页.行业资讯.价格行情.展会信息等。");
		setSiteInfo(headDTO, out);

		// 企业报价(最新前20条企业报价)方法如下:
		out.put("companyPriceList",
				companyPriceService.queryCompanyPriceByRefreshTime(null, 20));
		// 企业报价子类别 废塑料
		out.put("plasticList", categoryCompanyPriceService
				.queryCategoryCompanyPriceByCode("10001000"));
		// 企业报价子类别 废金属
		out.put("metalList", categoryCompanyPriceService
				.queryCategoryCompanyPriceByCode("1001"));

		// 最新废金属报价
		out.put("metalCategoryList",
				priceCategoryService.queryPriceCategoryByParentIdOrderList(17));
		// 废塑料报价
		out.put("plasticCategoryList",
				priceCategoryService.queryPriceCategoryByParentIdOrderList(22));

		// [废旧金属报价](最新金属走势图):

		// 金属 塑料 废纸(从标签页查 )

		// 热点展会 :最新前4条展会信息.会展资讯
		out.put("exhibitList",
				exhibitService.queryExhibit(AstConst.EXHIBIT_NEWS, null, 9));

		// 实用商讯 前6条最热标签下的帖子
		// out.put("bbsTagsList", bbsPostService.queryPostByType("8", 6));
		// out.put("bbsTagsList", bbsService.queryBbsPostDailyStatistics(1, 6));

		// 头条
		// out.put("topBbsList", bbsPostService.queryPostByType("3", 1));

		// 热门话题
		// out.put("hotBbsList", bbsPostService.queryPostByType("5", 2));

		// 法律法规56
		// out.put("newsList", newsService.queryNewsByModuleId(56, 8));

		// 首页推荐
		// Map<String, List<DataIndexDO>>
		// map=dataIndexService.queryDataIndexOrderCategory(
		// PAGE_INDEX, null);
		// out.put("indexMap", map);

		// 废金属报价
		List<ChartDataForIndexDTO> charts = chartDataService
				.queryChartDataForIndex(8);
		out.put("charts", charts);

		// 当天基础统计数据
		// out.put("webBaseDataMap",
		// webBaseDataStatService.queryDataByDate(null));

		out.put("nowTime", DateUtil.toString(new Date(), "MM.dd"));
		// 友情链接
		FriendLinkDTO friendLinkDTO = new FriendLinkDTO();
		FriendLinkDO friendLinkDO = new FriendLinkDO();
		friendLinkDO.setIsChecked("1");
		friendLinkDTO.setFriendLinkDO(friendLinkDO);
		out.put("friendLinkList",
				friendLinkService.queryFriendLinkByCondition(friendLinkDTO));

		out.put("indexAd", ParamUtils.getInstance().getChild("index_ad"));

		// 现货商城信息
		// PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		// page.setPageSize(2);
		// out.put("spotList",
		// spotService.pageSpotBySearchEngineForSimpleCompany(new ProductsDO(),
		// true, 2, page).getRecords());
		out.put("spotList", spotService.queryLastestOrderSpot(2));

		// 相册导购
		out.put("picList",
				productsService.querypicByKeyWord(new ProductsDO(), true, 4));

		// long end=System.currentTimeMillis();
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));

		// 网站数据总数
		webBaseDataStatService.indexTotal(out);

		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out)
			throws ParseException {
		// long start=System.currentTimeMillis();
		// 设置页面头部信息
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.INDEX);
		headDTO.setPageTitle("废金属_废塑料_废纸_纺织废料_再生资源_废橡胶_${site_name}(原中国再生资源交易网)");
		headDTO.setPageKeywords("废金属,废塑料,废纸,纺织废料,再生资源,废橡胶,废铜,废铝,废铁,企业报价,塑料颗粒,二手设备");
		headDTO.setPageDescription("${site_name}是中国最大最旺的网上废料贸易市场,这里为您精选了废金属,废塑料,"
				+ "废纸,废橡胶,纺织废料,废旧设备,废电子电器,废玻璃,废皮革,废铜,废铝,废铁,企业报价,塑料颗粒,"
				+ "二手设备等各类废料供求信息.公司黄页.行业资讯.价格行情.展会信息等。");
		setSiteInfo(headDTO, out);

		// 企业报价(最新前20条企业报价)方法如下:
//		out.put("companyPriceList",
//				companyPriceService.queryCompanyPriceByRefreshTime(null, 20));
		// 企业报价子类别 废塑料
//		out.put("plasticList", categoryCompanyPriceService
//				.queryCategoryCompanyPriceByCode("10001000"));
		// 企业报价子类别 废金属
//		out.put("metalList", categoryCompanyPriceService
//				.queryCategoryCompanyPriceByCode("1001"));

		// 最新供求信息（各30条）
		PageDto<ProductsDto> productPage = new PageDto<ProductsDto>();
		ProductsDO product = new ProductsDO();
		// 即时供应
		productPage.setPageSize(30);
		product.setProductsTypeCode("10331000");
		out.put("supplyList",
				productsService.pageProductsBySearchEngine(product, null, null,
						productPage).getRecords());
		// 即时求购
		product.setProductsTypeCode("10331001");
		out.put("buyList",
				productsService.pageProductsBySearchEngine(product, null, null,
						productPage).getRecords());

		// 最新废金属报价
//		out.put("metalCategoryList",
//				priceCategoryService.queryPriceCategoryByParentIdOrderList(17));
		// 废塑料报价
//		out.put("plasticCategoryList",
//				priceCategoryService.queryPriceCategoryByParentIdOrderList(22));

		// 废金属 5
//		out.put("fjsList", priceService.queryListByAllTypeId(5, 2));
		// 废塑料 6
//		out.put("fslList", priceService.queryListByAllTypeId(6, 2));
		// 废纸 7
		out.put("fzList", priceService.queryListByAllTypeId(7, 2));
		// 废橡胶 8
		out.put("fxjList", priceService.queryListByAllTypeId(8, 4));

		// 废纺织品
		PageDto<ProductsDto> textilePage = new PageDto<ProductsDto>();
		ProductsDO products = new ProductsDO();
		products.setTitle("纺织品");
		textilePage.setPageSize(2);
		textilePage = productsService.pageLHProductsBySearchEngine(products,
				null, null, textilePage);
		out.put("textileList", textilePage.getRecords());

		// 二手设备
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		ProductsDO productsDO = new ProductsDO();
		productsDO.setTitle("二手设备");
		page.setPageSize(2);
		page = productsService.pageLHProductsBySearchEngine(productsDO, null,true, page);
		out.put("secHandList", page.getRecords());

		// [废旧金属报价](最新金属走势图):

		// 金属 塑料 废纸(从标签页查 )

		// 热点展会 :最新前4条展会信息.会展资讯
//		out.put("exhibitList",
//				exhibitService.queryExhibit(AstConst.EXHIBIT_NEWS, null, 9));

		// 实用商讯 前6条最热标签下的帖子
		// out.put("bbsTagsList", bbsPostService.queryPostByType("8", 6));
		// out.put("bbsTagsList", bbsService.queryBbsPostDailyStatistics(1, 6));

		// 头条
		// out.put("topBbsList", bbsPostService.queryPostByType("3", 1));

		// 热门话题
		// out.put("hotBbsList", bbsPostService.queryPostByType("5", 2));

		// 法律法规56
		// out.put("newsList", newsService.queryNewsByModuleId(56, 8));

		// 首页推荐
		// Map<String, List<DataIndexDO>>
		// map=dataIndexService.queryDataIndexOrderCategory(
		// PAGE_INDEX, null);
		// out.put("indexMap", map);

		// 废金属报价
		List<ChartDataForIndexDTO> charts = chartDataService
				.queryChartDataForIndex(8);
		out.put("charts", charts);

		// 期所价格 期货id ：30
		List<ChartDataForIndexDTO> ccList = chartDataService
				.queryChartDataForPriceByParentId(30);
		out.put("ccList", ccList);

		// 当天基础统计数据
		// out.put("webBaseDataMap",
		// webBaseDataStatService.queryDataByDate(null));

		out.put("nowTime", DateUtil.toString(new Date(), "MM-dd"));
		// 友情链接
		FriendLinkDTO friendLinkDTO = new FriendLinkDTO();
		FriendLinkDO friendLinkDO = new FriendLinkDO();
		PageDto pageDto=new PageDto();
		friendLinkDO.setIsChecked("1");
		friendLinkDTO.setFriendLinkDO(friendLinkDO);
		pageDto.setSort("f.show_index");
		pageDto.setDir("asc");
		pageDto.setPageSize(-1);
		friendLinkDTO.setPageDto(pageDto);
		out.put("friendLinkList",
				friendLinkService.queryFriendLinkByCondition(friendLinkDTO));
//		List<FriendLinkDTO> lit=friendLinkService.queryFriendLinkByCondition(friendLinkDTO);
		out.put("indexAd", ParamUtils.getInstance().getChild("index_ad"));

		// 现货商城信息
		// PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		// page.setPageSize(2);
		// out.put("spotList",
		// spotService.pageSpotBySearchEngineForSimpleCompany(new ProductsDO(),
		// true, 2, page).getRecords());
//		out.put("spotList", spotService.queryLastestOrderSpot(2));

		// 相册导购
//		out.put("picList",productsService.querypicByKeyWord(new ProductsDO(), true, 4));

		// long end=System.currentTimeMillis();
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));

		// 网站数据总数
//		webBaseDataStatService.indexTotal(out);

//		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		return new ModelAndView();
	}

	/**
	 * 前台登陆提交
	 * 
	 * @param out
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping(value = "login.htm", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, AuthUser authUser,
			Map<String, Object> out, String cookieMaxAge) throws IOException {
		return null;
	}

	// 验证书写的email是否是会员
	// @RequestMapping
	// public ModelAndView checkemail(Map<String, Object> out,
	// HttpServletRequest request, HttpServletResponse response,
	// String email, String url) throws IOException {
	// // if (ParamFacade.getInstance()
	// // .getParamValue("baseConfig", "open_status") != null
	// // && ParamFacade.getInstance().getParamValue("baseConfig",
	// // "open_status").equals("all")) {
	// // 根据email查询companyId注册页面
	// Map<String, Object> mymap = new HashMap<String, Object>();
	// ProductsDO productsDO = new ProductsDO();
	// CompanyContactsDO companyContactsDO = (CompanyContactsDO) mymap
	// .get("companyContactsDO");
	// CompanyContactsDO companyContactDO = companyService
	// .selectCompanyIdByEmail(email);
	// if (companyContactsDO == null) {
	// // 公司类型
	// // List<CategoryDO> companyTypeList =
	// // CategoryFacade.getInstance()
	// // .listCategoryByParentCode("1020");
	// List<CategoryDO> companyTypeList = categoryService
	// .queryCategoriesByPreCode("1020");
	// // 主营类别
	// // List<CategoryDO> industryCodeList = CategoryFacade
	// // .getInstance().listCategoryByParentCode("1012");
	// List<CategoryDO> industryCodeList = categoryService
	// .queryCategoriesByPreCode("1012");
	// out.put("companyTypeList", companyTypeList);
	// out.put("industryCodeList", industryCodeList);
	// out.put("productsDO", productsDO);
	// out.put("email", email);
	// } else {
	// out.put("productsDO", productsDO);
	// out.put("companyContactDO", companyContactDO);
	// }
	// // }
	// return printJson(null, out);
	// }

	@RequestMapping
	public ModelAndView checkuser(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response,
			String username, String password, String randCode,
			String randCodeKey, String url, String cookieMaxAge)
			throws IOException {
		ExtResult extResult = new ExtResult();

		SsoUser ssoUser = null;
		try {
			ssoUser = SsoUtils.getInstance().validateUser(response, username,
					password, null, HttpUtils.getInstance().getIpAddr(request));
		} catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			extResult.setData(AuthorizeException.getMessage(e.getMessage()));
		}

		if (ssoUser != null) {
			setSessionUser(request, ssoUser);
			extResult.setSuccess(true);
			if (StringUtils.isEmpty(url)) {
				// extResult.setData(AddressTool.getAddress("myrc") +
				// "/index.htm");
				extResult.setData(request.getContextPath() + "/myrc/index.htm");
			} else {
				extResult.setData(url);
			}
		} else {
			extResult.setData("登录失败，错误原因：" + extResult.getData());
		}
		return printJson(extResult, model);
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
	public ModelAndView checkUserEmail(Map<String, Object> model, String email)
			throws IOException {
		String result = "true";
		if (StringUtils.isNotEmpty(email)) {
			Integer i = authService.countUserByEmail(email);
			if (i != null && i.intValue() > 0) {
				result = "false";
			}
		}
		model.put("json", result);
		return new ModelAndView("json");
	}

	/**
	 * 验证手机号是否已经被注册
	 * 
	 * @param model
	 * @param mobile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView checkUserMobile(Map<String, Object> model, String mobile)
			throws IOException {
		String result = "true";
		if (StringUtils.isNotEmpty(mobile)) {
			Integer num = companyAccountService.countAccountOfMobile(mobile);
			if (num != null && num.intValue() > 0) {
				result = "false";
			}
		}
		model.put("json", result);
		return new ModelAndView("json");
	}

	@RequestMapping
	public ModelAndView checkValidationCode(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model,
			String verifyCode) {
		String result = "true";
		if (StringUtils.isNotEmpty(verifyCode)) {
			Object object = getCachedSession(request, FrontConst.SESSION_CODE);
			if (verifyCode.equals(object)) {
				result = "true";
			} else {
				result = "false";
			}

		}
		model.put("json", result);
		return new ModelAndView("json");
	}

	@RequestMapping
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, String url, Map<String, Object> model)
			throws IOException {
		ExtResult extResult = new ExtResult();

		// 清除改cookie
		String cookie = HttpUtils.getInstance().getCookie(request,
				AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
		authService.removeAuthLoginByCookie(cookie);

		SsoUtils.getInstance().logout(request, response, null);
		extResult.setSuccess(true);
		if (StringUtils.isEmpty(url)) {
			extResult.setData("index.htm");
		} else {
			extResult.setData(url);
		}
		PageCacheUtil.setNoCache(response);
		return printJson(extResult, model);
	}

	@RequestMapping
	public void errorPage404(Map<String, Object> out) {
	}

	@RequestMapping
	public void errorPage500(Map<String, Object> out) {
	}

	@RequestMapping
	public ModelAndView uploads(Map<String, Object> out) {
		out.put("title", "AjaxUpload");
		return null;
	}

	/**
	 * 图片上传页面初始化
	 * 
	 * @param out
	 * @param model
	 *            所属模块，如：bbs，ads，news
	 * @param filetype
	 *            文件类型，如：img，doc，zip
	 */
	@RequestMapping
	public void ajaxUpload(Map<String, Object> out, String model,
			String filetype, String control) {
		// upload?model=periodical&filetype=zip&control=
		out.put("filetype", filetype);
		out.put("model", model);
		out.put("control", control);
	}

	@RequestMapping
	public ModelAndView error(String s, Map<String, Object> out) {
		if (s == null) {
			out.put(AstConst.ERROR_TEXT, "发生错误，请重试!");
		} else {
			out.put(AstConst.ERROR_TEXT, "该信息不存在或审核未通过!");
		}
		return new ModelAndView("/common/error");
	}

	@RequestMapping
	public ModelAndView status(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {
		PageCacheUtil.setNoCache(response);

		do {
			SsoUser ssoUser = getCachedUser(request);

			if (ssoUser != null) {
				model.put("isLogin", 1);
				break;
			}
			String cookie = HttpUtils.getInstance().getCookie(request,
					AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
			AuthAutoLogin aal = authService.queryAutoLoginByCookie(cookie);

			if (aal == null || StringUtils.isEmpty(aal.getCompanyAccount())
					|| StringUtils.isEmpty(aal.getPassword())) {
				break;
			}

			String a = "";
			try {
				a = companyAccountService.validateUser(aal.getCompanyAccount(),
						MD5.encode(aal.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				break;
			} catch (UnsupportedEncodingException e) {
				break;
			} catch (AuthorizeException e) {
				break;
			}

			if (StringUtils.isEmpty(a)) {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("front"));
			}
			ssoUser = companyAccountService.initSessionUser(a);

			if (ssoUser != null) {
				String key = UUID.randomUUID().toString();
				String ticket = "";
				try {
					ticket = MD5.encode(a + aal.getPassword() + key);
				} catch (NoSuchAlgorithmException e) {
				} catch (UnsupportedEncodingException e) {
				}
				ssoUser.setTicket(ticket);
				HttpUtils.getInstance().setCookie(response,
						SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
				MemcachedUtils.getInstance().getClient()
						.set(ticket, 1 * 60 * 60, ssoUser);
			}
		} while (false);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView webst(HttpServletResponse response,
			HttpServletRequest request, Map<String, Object> out) {
		PageCacheUtil.setNoCache(response);

		do {
			SsoUser ssoUser = getCachedUser(request);

			if (ssoUser != null) {
				break;
			}
			String cookie = HttpUtils.getInstance().getCookie(request,
					AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
			AuthAutoLogin aal = authService.queryAutoLoginByCookie(cookie);

			if (aal == null || StringUtils.isEmpty(aal.getCompanyAccount())
					|| StringUtils.isEmpty(aal.getPassword())) {
				break;
			}

			String a = "";
			try {
				a = companyAccountService.validateUser(aal.getCompanyAccount(),
						MD5.encode(aal.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				break;
			} catch (UnsupportedEncodingException e) {
				break;
			} catch (AuthorizeException e) {
				break;
			}

			if (StringUtils.isEmpty(a)) {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("front"));
			}

			ssoUser = companyAccountService.initSessionUser(a);

			if (ssoUser != null) {
				String key = UUID.randomUUID().toString();
				String ticket = "";
				try {
					ticket = MD5.encode(a + aal.getPassword() + key);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				ssoUser.setTicket(ticket);
				HttpUtils.getInstance().setCookie(response,
						SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
				MemcachedUtils.getInstance().getClient()
						.set(ticket, 1 * 60 * 60, ssoUser);
			}
		} while (false);

		return new ModelAndView();
	}
	
	
	@RequestMapping
	public ModelAndView logout_if(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response) {
		PageCacheUtil.setNoCache(response);

		// 清除改cookie
		String cookie = HttpUtils.getInstance().getCookie(request,
				AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
		authService.removeAuthLoginByCookie(cookie);

		SsoUtils.getInstance().logout(request, response, null);
		return new ModelAndView("redirect:status.htm?"
				+ System.currentTimeMillis());
	}

	@RequestMapping
	public ModelAndView logout_st(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response) {
		PageCacheUtil.setNoCache(response);

		// 清除改cookie
		String cookie = HttpUtils.getInstance().getCookie(request,
				AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
		authService.removeAuthLoginByCookie(cookie);

		SsoUtils.getInstance().logout(request, response, null);
		return new ModelAndView("redirect:" + AddressTool.getAddress("front"));
	}

	/**
	 * 首页拼音索引使用的搜索结果
	 */
	// @RequestMapping
	// public void pingy(Map<String, Object> model, String pingy) {
	// model.put("pyresulr",
	// categoryProductsService.queryCategoryProductsByCnspell(pingy));
	// }

	@RequestMapping(value = "login.htm", method = RequestMethod.GET)
	public ModelAndView login(Map<String, Object> out,HttpServletRequest request,HttpServletResponse response) {
		String username = request.getParameter("username");
		String url = request.getParameter("url");
	 	String error = request.getParameter("error");
		
		out.put("customerServicePhone",
				ParamUtils.getInstance().getValue("baseConfig",
						"customer_service_phone"));
		out.put("url", url);
		if (StringUtils.isNumber(error)) {
			out.put("error", AuthorizeException.getMessage(error));
		} else {
			out.put("error", error);
		}
		out.put("preview",ParamUtils.getInstance().getValue("baseConfig", "myrc_preview"));
		out.put("username", username);
		String cookie = HttpUtils.getInstance().getCookie(request, "loginInfo", SsoConst.SSO_DOMAIN);
		if(StringUtils.isEmpty(cookie)){
			cookie = UUID.randomUUID().toString();
			HttpUtils.getInstance().setCookie(response,"loginInfo", cookie,SsoConst.SSO_DOMAIN, null);
		}
		out.put("saveCookie", cookie);
		
		// cdn clear
		clearCDN(response);
		
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doLogin(Map<String, Object> out,HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password"); 
		String url = request.getParameter("url");
		String isRemember = request.getParameter("isRemember");
		//前一个页面连接
		if(StringUtils.isEmpty(username)){
				String ll = request.getHeader("Referer");
				username = ll.split("account=")[1];
				CompanyAccount account = companyAccountService.queryAccountByAccount(username);
				if(account != null){
					password = account.getPassword();
				}
		}
		// csrf攻击登陆 key获取
//		String loginCookie = request.getParameter("cookie");
//		String saveCookie = HttpUtils.getInstance().getCookie(request, "loginInfo", SsoConst.SSO_DOMAIN);
		//获取ip地址
		String ip=HttpUtils.getInstance().getIpAddr(request);
		
		do {
			SsoUser ssoUser = null;
			
//			if(StringUtils.isEmpty(loginCookie)||StringUtils.isEmpty(saveCookie)||!loginCookie.equals(saveCookie)){
//				out.put("error", AuthorizeException.getMessage(AuthorizeException.ERROR_SERVER));
//				break;
//			}

			if (StringUtils.isEmpty(password)) {
				out.put("error", AuthorizeException.getMessage(AuthorizeException.NEED_PASS));
				break;
			}

			try {
				ssoUser = SsoUtils.getInstance().validateUser(response,
						username, password, null,
						HttpUtils.getInstance().getIpAddr(request));
			}catch (NoSuchAlgorithmException e) {
			} catch (AuthorizeException e) {
				if("300".equals(e.getMessage())){
					//该公司的编号
					Integer cid=companyAccountService.queryCompanyIdByNameAndPassw(username, password);
					//冻结原因的获取
					if(cid>0){
						String remark=logOperationService.queryRemarkByCId(cid);
						out.put("remark", remark);
					}
				}
				if(e.getMessage()!=null&&e.getMessage().startsWith("validate")){
					String[] msgArray = e.getMessage().split(":");
					if (msgArray.length==2) {
						url = AddressTool.getAddress("front") + "/user/activate.htm?account="+msgArray[1];
					}else{
						url = AddressTool.getAddress("front") + "/user/activate.htm?account="+username;
					}
					return new ModelAndView("redirect:" + url);
				}
				out.put("error", AuthorizeException.getMessage(e.getMessage()));
			} catch (IOException e) {
			}

			if (ssoUser != null) {
				//登录日志
				LogUtil.getInstance().log(
						"myrc",
						"myrc-operate",
						ip,
						"{'account':'"
								+ username
								+ "','operatype_id':'10','gmt_created':'"
								+ DateUtil
										.toString(new Date(), DATE_FORMAT)
								+ "'}","myrc");
				LogUtil.getInstance().log(""+ssoUser.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'front-login';account:'"+ssoUser.getAccount()+"'"); // 登记登陆信息
				if ("1".equals(isRemember)) {
					// 登陆成功 记住密码 cookie
					String cookie = HttpUtils.getInstance().getCookie(request,
							AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
					if (StringUtils.isEmpty(cookie)) {
						HttpUtils.getInstance().setCookie(response,
								AuthService.AUTO_LOGIN_KEY,
								UUID.randomUUID().toString(),
								AuthService.DOMAIN, 30 * 24 * 60 * 60);
					}
					authService.createAutoLogin(
							ssoUser.getCompanyId(),
							ssoUser.getAccount(),
							HttpUtils.getInstance().getCookie(request,
									AuthService.AUTO_LOGIN_KEY,
									AuthService.DOMAIN), password);
					
					SsoUser ssoUsers = getCachedUser(request);
				}

				setSessionUser(request, ssoUser);
				if (StringUtils.isEmpty(url)) {
					url = AddressTool.getAddress("myrc") + "/index.htm";
				}
				return new ModelAndView("redirect:" + url);
			}
		} while (false);
		out.put("url", url);
		out.put("username", username);
//		out.put("saveCookie", loginCookie); // cookie 回传
		return new ModelAndView("login");
	}

	@RequestMapping
	public ModelAndView doStatusLogin(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			String username, String password) throws IOException {

		ExtResult result = new ExtResult();

		do {

			if (StringUtils.isEmpty(username)) {
				result.setData(AuthorizeException.NEED_ACCOUNT);
			}

			if (StringUtils.isEmpty(password)) {
				result.setData(AuthorizeException.NEED_PASS);
			}

			SsoUser ssoUser = null;
			try {
				ssoUser = SsoUtils.getInstance().validateUser(response,
						username, password, null,
						HttpUtils.getInstance().getIpAddr(request));
			} catch (NoSuchAlgorithmException e) {
			} catch (AuthorizeException e) {
				result.setData(e.getMessage());
			} catch (IOException e) {
			}

			if (ssoUser != null) {
				setSessionUser(request, ssoUser);
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView miniLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, String error, String errorCode) {
		out.put("destUrl", destUrl);
		out.put("regfrom", regfrom);
		out.put("error", error);
		out.put("errorTxt", AuthorizeException.getMessage(errorCode));
		out.put("account",
				HttpUtils.getInstance().getCookie(request,
						AstConst.COOKIE_ACCOUNT, AstConst.COOKIE_DOMAIN));
		SeoUtil.getInstance().buildSeo(out);

		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView miniLoginNoQQ(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, String error, String errorCode) {
		out.put("destUrl", destUrl);
		out.put("regfrom", regfrom);
		out.put("error", error);
		out.put("errorTxt", AuthorizeException.getMessage(errorCode));
		out.put("account",
				HttpUtils.getInstance().getCookie(request,
						AstConst.COOKIE_ACCOUNT, AstConst.COOKIE_DOMAIN));
		SeoUtil.getInstance().buildSeo(out);

		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doMiniLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String account, String password, String vcode,
			String t, String regfrom, String forUrl) {
		// TODO 简版登录
		HttpUtils.getInstance().setCookie(response, AstConst.COOKIE_ACCOUNT,
				account, AstConst.COOKIE_DOMAIN, AstConst.COOKIE_AGE);

		SsoUser user = null;
		try {
			user = SsoUtils.getInstance().validateUser(response, account,
					password, AstConst.COOKIE_AGE,
					HttpUtils.getInstance().getIpAddr(request));
		} catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			out.put("errorCode", e.getMessage());
		} catch (IOException e) {
		}

		if (user != null) {
			setSessionUser(request, user);
			if (StringUtils.isNotEmpty(destUrl)) {
				return new ModelAndView("redirect:" + destUrl);
			} else {
				return new ModelAndView("submitCallback");
			}
		}
		out.put("regfrom", regfrom);
		out.put("destUrl", destUrl);
		// TODO miniLogin 处理错误信息
		out.put("error", "1");
		return new ModelAndView("redirect:miniLogin.htm");
	}

	// @RequestMapping
	// public ModelAndView miniRegister(HttpServletRequest request, Map<String,
	// Object> out, String destUrl){
	// out.put("destUrl", destUrl);
	// return null;
	// }

	@RequestMapping
	public ModelAndView doMiniRegister(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String regfrom, Company company,
			CompanyAccount companyAccount, String password,
			String passwordConfirm, String verifyCodeKey, String verifyCode)
			throws Exception {

		out.put("regfrom", regfrom);
		out.put("destUrl", destUrl);
		out.put("tab", "register");

		String error = "2";
		do {
			// // 获取验证码
			// String captcha = String.valueOf(MemcachedUtils.getInstance()
			// .getClient().get(verifyCodeKey));
			// MemcachedUtils.getInstance().getClient().delete(verifyCodeKey);
			//
			// if (captcha == null) {
			// error = "3";
			// break;
			// }
			//
			// if (!captcha.equals(verifyCode)) {
			// error = "3";
			// break;
			// }

			String vcode = String.valueOf(SsoUtils.getInstance().getValue(
					request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode)
					|| !verifyCode.equalsIgnoreCase(vcode)) {
				error = "3";
				break;
			}

			company.setRegfromCode(regfrom);

			try {
				String username = companyAccountService.registerUser(
						companyAccount.getAccount(), companyAccount.getEmail(),
						password, passwordConfirm, companyAccount, company,
						HttpUtils.getInstance().getIpAddr(request));

				if (StringUtils.isEmpty(username)) {
					break;
				}

				companyValidateService.sendValidateByEmail(
						companyAccount.getAccount(), companyAccount.getEmail());

				// 自动登录
				SsoUser ssoUser = null;
				try {
					ssoUser = SsoUtils.getInstance().validateUser(response,
							companyAccount.getAccount(), password, null,
							HttpUtils.getInstance().getIpAddr(request));
				} catch (AuthorizeException e) {
				}

				setSessionUser(request, ssoUser);

				// 增加积分
				scoreChangeDetailsService
						.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser
								.getCompanyId(), null, "base_register", null,
								null, null));
			} catch (RegisterException e) {
				break;
			} catch (NoSuchAlgorithmException e) {
				break;
			}

			out.put("account", companyAccount.getAccount());
			out.put("success", "1");
			out.put("data", "{}");
			return new ModelAndView();
		} while (false);

		out.put("company", company);
		out.put("companyAccount", companyAccount);
		out.put("error", error);
		return new ModelAndView("miniLogin");
	}

	@RequestMapping
	public ModelAndView accessToQQLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String referUrl) {
		PageCacheUtil.setNoCDNCache(response);

		if (StringUtils.isEmpty(referUrl)) {
			referUrl = request.getHeader("referer");
		}

		// 获取qqClientId 错了可能就导入其他网站去了 后台可配置
		String qqClientId = ParamUtils.getInstance().getValue("oauth_config",
				"qqClientId");
		if (StringUtils.isEmpty(qqClientId)) {
			// 默认
			qqClientId = "100345758";
		}
		// 统计点击次数代码
		LogUtil.getInstance().log("qq_login", "qq_login_click");

		return new ModelAndView(
				"redirect:"
						+ "http://openapi.qzone.qq.com/oauth/show?which=ConfirmPage&display=pc&client_id="
						+ qqClientId
						+ "&response_type=code&scope=get_user_info&redirect_uri="
						+ AddressTool.getAddress("front") + "/qqLogin.htm"
						+ "?referUrl=" + referUrl);
	}

	@RequestMapping("qqLogin.htm")
	public ModelAndView qqLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, String code,
			String deskUrl, String accessToken, String account, String error,
			String referUrl) throws UnsupportedEncodingException {
		do {
			if (StringUtils.isEmpty(code) && StringUtils.isEmpty(accessToken)) {
				break;
			}
			if (StringUtils.isNotEmpty(code)
					&& StringUtils.isEmpty(accessToken)) {
				// 根据code 获取access_token(登录状态) 和openId(与qq号码绑定的关联id)
				String domain = request.getServerName();
				String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=100345758&client_secret=220387a3670793e0f337c616ce21b3a6&redirect_uri=http://"
						+ domain + "/qqLogin.htm&code=" + code;
				String accessTokenResult;
				try {
					accessTokenResult = HttpUtils.getInstance().httpGet(url,
							HttpUtils.CHARSET_UTF8);
				}catch (Exception e) {
					accessTokenResult = null;
				}
				if (StringUtils.isEmpty(accessTokenResult)) {
					break;
				}
				if (accessTokenResult.indexOf("error") != -1) {
					return new ModelAndView("redirect:" + "http://www.zz91.com");
				}
				accessToken = getQQAccessToken(accessTokenResult);
			}
			// 获取不到access_token 跳回首页
			if (StringUtils.isEmpty(accessToken)) {
				break;
			}
			// 账号重新绑定
			if (StringUtils.isNotEmpty(account)) {
				out.put("account", account);
				break;
			}
			String openId = getQQOpenId(accessToken);
			// 获取不到openId 跳回首页
			if (StringUtils.isEmpty(openId)) {
				break;
			}
			OauthAccess oa = oauthAccessService.queryAccessByOpenIdAndType(openId, OauthAccessService.OPEN_TYPE_QQ);
			// openId关联账号不存在, 跳转至 绑定账号或者重新注册账号
			if (oa == null || StringUtils.isEmpty(oa.getTargetAccount())) {
				break;
			}
			// 自动登录
			account = oa.getTargetAccount();
			SsoUser ssoUser = companyAccountService.validateQQLogin(account,
					HttpUtils.getInstance().getIpAddr(request));
			if (ssoUser == null) {
				break;
			}
			HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY,ssoUser.getTicket(), SsoConst.SSO_DOMAIN, null);
			setSessionUser(request, ssoUser);
			
			// 登记登陆信息
			LogUtil.getInstance().log(""+ssoUser.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'qq-login';account:'"+ssoUser.getAccount()+"'");
			
			return new ModelAndView("redirect:" + getReferUrl(referUrl));
		} while (false);
		if (StringUtils.isNotEmpty(error)) {
			out.put("error", StringUtils.decryptUrlParameter(error));
		}
		out.put("deskUrl", deskUrl);
		out.put("referUrl", getReferUrl(referUrl));
		out.put("accessToken", accessToken);
		return new ModelAndView("qqlogin/login");
	}

	@RequestMapping
	public ModelAndView doQQRegister(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			CompanyAccount companyAccount, Company company, String accessToken,
			String passwordConfirm, String referUrl,String regfromCode) throws Exception {
		do {
			//QQ询价regfromCode
			if(referUrl.indexOf("priceDetails")!=-1){
				regfromCode ="10031031";
				
			}
			//QQ询盘regfromCode编码
			else if (referUrl.indexOf("inquiryApi")!=-1||referUrl.indexOf("productdetails")!=-1) {
				regfromCode="10031032";
			}
			//QQ发布供求注册
			else if (referUrl.indexOf("postoffer_step1")!=-1){
				regfromCode="10031035";
			}
			//QQ登录注册
			else regfromCode="10031026";
				
			 //QQ导入注册
			company.setRegfromCode(regfromCode);
			
			try {

				// 转中文地址
				String address = "";
				if (company != null
						&& StringUtils.isNotEmpty(company.getAreaCode())) {
					String code = company.getAreaCode();
					if (company.getAreaCode().length() >= 8) {
						address += CategoryFacade.getInstance().getValue(
								code.substring(0, 8));
					}
					if (company.getAreaCode().length() >= 12) {
						address += CategoryFacade.getInstance().getValue(
								code.substring(0, 12));
					}
					if (company.getAreaCode().length() >= 16) {
						address += CategoryFacade.getInstance().getValue(
								code.substring(0, 16));
					}
					out.put("addressLabel", address);
				}

				String username = companyAccountService.registerUser(
						companyAccount.getAccount(), companyAccount.getEmail(),
						passwordConfirm, passwordConfirm, companyAccount,
						company, HttpUtils.getInstance().getIpAddr(request));

				if (StringUtils.isEmpty(username)) {
					break;
				}
				out.put("username", username);
				companyValidateService.sendValidateByEmail(
						companyAccount.getAccount(), companyAccount.getEmail());

				// 统计注册次数
				LogUtil.getInstance().log("qq_login", "qq_login_register");

				// 自动登录
				SsoUser ssoUser = SsoUtils.getInstance().validateUser(response,
						companyAccount.getAccount(), passwordConfirm, null,
						HttpUtils.getInstance().getIpAddr(request));
				String password = authService.queryPassword(companyAccount
						.getAccount());

				if (ssoUser != null) {
					// 登记登陆信息
					LogUtil.getInstance().log(""+ssoUser.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'qq-reg-login';account:'"+ssoUser.getAccount()+"'");
					
					String key = UUID.randomUUID().toString();
					String ticket = "";
					try {
						ticket = MD5.encode(companyAccount.getAccount()
								+ password + key);
					} catch (NoSuchAlgorithmException e) {
					} catch (UnsupportedEncodingException e) {
					}
					ssoUser.setTicket(ticket);
					HttpUtils.getInstance().setCookie(response,
							SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN,
							null);
					MemcachedUtils.getInstance().getClient()
							.set(ticket, 1 * 60 * 60, ssoUser);
				}

				// 增加积分
				scoreChangeDetailsService
						.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser
								.getCompanyId(), null, "base_register", null,
								null, null));
			} catch (RegisterException e) {
				break;
			} catch (NoSuchAlgorithmException e) {
				break;
			}

			if (accessToken.indexOf("error") != -1) {
				return new ModelAndView("redirect:" + "http://www.zz91.com");
			}
			out.put("accessToken", accessToken);
			// 获取 openId
			String openId = getQQOpenId(accessToken);
			Integer i = oauthAccessService.addOneAccess(openId,
					OauthAccessService.OPEN_TYPE_QQ,
					companyAccount.getAccount());
			if (i == null || i <= 0) {
				break;
			}
			// 清除accessToken
			OauthAccessService.OPEN_ID_MAP.remove(accessToken);
			out.put("logined", 1);
			out.put("referUrl", referUrl);

			return new ModelAndView("qqlogin/register2");
		} while (false);
		out.put("accessToken", accessToken);
		out.put("referUrl", referUrl);
		return new ModelAndView("redirect:" + "register.htm");
	}

	@RequestMapping
	public ModelAndView doQQBind(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String account, String password, String accessToken, String referUrl,String regfromCode ) {
		do {
			// 自动登录
			SsoUser user = null;
			try {
				user = SsoUtils.getInstance().validateUser(response, account,
						password, AstConst.COOKIE_AGE,
						HttpUtils.getInstance().getIpAddr(request));
			} catch (NoSuchAlgorithmException e) {
			} catch (AuthorizeException e) {
				out.put("error", AuthorizeException.getMessage(e.getMessage()));
			} catch (IOException e) {
			}
			if (user == null) {
				break;
			}
			setSessionUser(request, user);

			if (accessToken.indexOf("error") != -1) {
				return new ModelAndView("redirect:" + "http://www.zz91.com");
			}

			String openId = getQQOpenId(accessToken);
			// 确定账号是否已经绑定过
			OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_QQ);
			if (oa != null) {
				// 账号已经绑定过
				out.put("error", "账号已经绑定");
				out.put("referUrl", referUrl);
				break;
			}
			// 执行 绑定操作 update 或者 insert openid
			oa = oauthAccessService.queryAccessByOpenIdAndType(openId,OauthAccessService.OPEN_TYPE_QQ);
			
			// 登记登陆信息
			LogUtil.getInstance().log(""+user.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'qq-bind-login';account:'"+user.getAccount()+"'"); 
			
			//QQ报价而绑定
			if(referUrl.indexOf("priceDetails")!=-1){
				regfromCode ="10031033";
			}
			
			//QQ询盘而绑定
			else if (referUrl.indexOf("inquiryApi")!=-1||referUrl.indexOf("productdetails")!=-1) {
				regfromCode="10031034";
			}
			//QQ发布产品而绑定
			else if (referUrl.indexOf("postoffer_step1")!=-1){
				regfromCode="10031036";
			}
			//QQ登录绑定
			else regfromCode="10031028";
			
			companyService.updateRegFromCode(companyAccountService.queryCompanyIdByAccount(account),regfromCode);

			if (oa != null) {
				oauthAccessService.updateByOpenId(openId, account);
			} else {
				oauthAccessService.addOneAccess(openId,
						OauthAccessService.OPEN_TYPE_QQ, account);
			}

			// 清除accessToken
			OauthAccessService.OPEN_ID_MAP.remove(accessToken);
			out.put("logined", 1);
			// 统计注册次数
			LogUtil.getInstance().log("qq_login", "qq_login_bind");
			return new ModelAndView("qqlogin/login");
		} while (false);
		// 1、账号不存在 或者 账号密码错误 绑定失败 2、清空登录状态
		SsoUtils.getInstance().logout(request, response, null);
		out.put("account", account);
		out.put("accessToken", accessToken);
		out.put("referUrl", referUrl);
		return new ModelAndView("redirect:" + "qqLogin.htm");
	}

	public String getQQOpenId(String accessToken) {
		String openId = "";
		do {
			openId = OauthAccessService.OPEN_ID_MAP.get(accessToken);
			if (StringUtils.isNotEmpty(openId)) {
				break;
			}

			String str = null;
			try {
				str = HttpUtils.getInstance().httpGet(
						"https://graph.qq.com/oauth2.0/me?access_token="
								+ accessToken, HttpUtils.CHARSET_UTF8);
			} catch (Exception e) {
				return null;
			}
			// 取大括号的内容 正则
			Pattern pattern = Pattern.compile("\\{[^}]+\\}");
			Matcher matcher = pattern.matcher(str);
			matcher.find();
			str = matcher.group();
			JSONObject jobj = JSONObject.fromObject(str);
			try {
				openId = jobj.getString("openid");
			} catch (Exception e) {
				openId = null;
			}
			if (StringUtils.isNotEmpty(openId)) {
				OauthAccessService.OPEN_ID_MAP.put(accessToken, openId);
			}
			return openId;
		} while (false);
		return openId;
	}

	private String getQQAccessToken(String accessTokenUrl) {
		String[] str = accessTokenUrl.split("&");
		accessTokenUrl = str[0].replace("access_token=", "");
		return accessTokenUrl;
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
	public ModelAndView getLoginStatus(HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		String success = "false";
		SsoUser ssoUser = getCachedUser(request);
		if (ssoUser != null) {
			return printText(
					"var success='true';var companyId='"
							+ ssoUser.getCompanyId() + "';", out);
		} else {
			return printText("var success='" + success + "';", out);
		}

		// return printJson(map, out);
	}

	// qq注册第一步
	@RequestMapping
	public ModelAndView register(Map<String, Object> out, String accessToken,String referUrl) {

		if (accessToken.indexOf("error") != -1) {
			return new ModelAndView("redirect:" + "http://www.zz91.com");
		}
		if (StringUtils.isNotEmpty(accessToken)) {
			out.put("accessToken", accessToken);
		}
		out.put("referUrl", referUrl);
		return new ModelAndView("qqlogin/register");
	}

	// QQ注册成功页
	@RequestMapping
	public ModelAndView register_suc(HttpServletRequest request,
			Map<String, Object> out, String accessToken, String userName,
			String industryCode, String serviceCode, String address,
			String addressFix, String introduction, String referUrl) {

		SsoUser ssoUser = getCachedUser(request);
		Company company = companyService.queryCompanyById(ssoUser
				.getCompanyId());
		if (accessToken.indexOf("error") != -1) {
			return new ModelAndView("redirect:" + "http://www.zz91.com");
		}
		do {

			if (company == null) {
				break;
			}
			company.setAddress(addressFix + address);
			company.setIndustryCode(industryCode);
			company.setIntroduction(introduction);
			company.setServiceCode(serviceCode);
			companyService.updateCompanyByUser(company);
			out.put("username", userName);
			out.put("accessToken", accessToken);
			out.put("referUrl", referUrl);
			return new ModelAndView("qqlogin/register_suc");
		} while (false);
		out.put("accessToken", accessToken);
		return new ModelAndView("qqlogin/register");
	}

	private String getReferUrl(String url) {
		do {
			// 空
			if (StringUtils.isEmpty(url)) {
				break;
			}

			// 通用询盘
			if (url.indexOf("www.zz91.com/inquiry/inquiryApi.htm") != -1) {
				return url;
			}

			// 首页页面
			if (url.indexOf("www.zz91.com") != -1) {
				break;
			}

			// 首页页面
			if (url.indexOf("china.zz91.com") != -1) {
				break;
			}

			return url;
		} while (false);
		return AddressTool.getAddress("myrc");
	}

}
