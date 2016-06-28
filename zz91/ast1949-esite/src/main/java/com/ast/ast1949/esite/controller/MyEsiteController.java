/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-22 by liulei.
 */
package com.ast.ast1949.esite.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSeriesDO;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmCompanySvrDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.company.EsiteNewsService;
import com.ast.ast1949.service.company.EsiteService;
import com.ast.ast1949.service.company.SeoTemplatesService;
import com.ast.ast1949.service.company.SubscribeService;
import com.ast.ast1949.service.credit.CreditCustomerVoteService;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;
import com.ast.ast1949.service.credit.CreditReferenceService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsSeriesService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.sample.SampleService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.PageCacheUtil;
import com.ast.ast1949.util.StringUtils;
import com.mongodb.DBObject;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.mongo.MongoDBUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * @author liulei
 * 
 */
@Controller
public class MyEsiteController extends BaseController {

	@Autowired
	private EsiteService esiteService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private EsiteNewsService esiteNewsService;
	@Autowired
	private CreditFileService creditFileService;
	@Autowired
	private CreditCustomerVoteService creditCustomerVoteService;
	@Autowired
	private CreditIntegralDetailsService creditIntegralDetailsService;
	@Autowired
	private CreditReferenceService creditReferenceService;
	@Autowired
	private CompanyAccountService companyAccountService;
	@Autowired
	private CompanyService companyService;
	@Resource
	private ProductsSeriesService productsSeriesService;
	@Resource
	private PriceService priceService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private SeoTemplatesService seoTemplatesService;
	@Resource
	private CompanyAttestService companyAttestService;
	@Resource
	private PhoneLogService phoneLogService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private SampleService sampleService;
	@Resource
	private SubscribeService subscribeService;

	final static Logger LOG = Logger.getLogger(MyEsiteController.class);
	final static String PRE_DOMAIN = ".zz91.com";

	private void initMyPosition(String code, String title,
			Map<String, Object> out) {
		Map<String, Object> myposition = new HashMap<String, Object>();
		myposition.put("code", code);
		myposition.put("title", title);
		out.put("myposition", myposition);
	}

	private Integer initCompanyId(Map<String, Object> out, String domain,
			Integer cid) {
		if (cid != null && cid.intValue() > 0) {
			String member = companyService.queryMembershipOfCompany(cid);
			if (StringUtils.isNotEmpty(member) && !"10051000".equals(member)) {
				return cid;
			}
			return null;
		}
		cid = esiteService.initCompanyIdFromDomain(domain);

		if (cid == null) {
			return null;
		}

		// 验证是否为 黑名单 用户
		if (companyService.validateIsBlack(cid)) {
			return null;
		}
		if (seoTemplatesService.validate(cid)) {
			return cid;
		}

		// 根据标签匹配报价
		do {
			Company company = companyService.queryCompanyById(cid);
			PageDto<PriceDO> pagePrice = new PageDto<PriceDO>();
			pagePrice.setPageSize(5);
			String tags = company.getTags();
			if (tags == null || StringUtils.isEmpty(tags)) {
				break;
			}
			if (tags.contains(",")) {
				String[] str = tags.split(",");
				tags = str[0];
			}
			out.put("tags", tags);
			// out.put("pagePrice",
			// priceService.queryPricePaginationListByTitle(tags, pagePrice));
			out.put("pagePrice",
					priceService.pagePriceBySearchEngine(tags, null, pagePrice));
		} while (false);

		// 判断该公司是否有百度优化这个服务
		int mark;
		String member = companyService.queryMembershipOfCompany(cid);
		if (crmCompanySvrService.validateEsitePeriod(cid)
				&& "10051000".equals(member)) {
			mark = 0;
		} else {
			mark = 1;
		}
		// 统计高会的流量
		// vipPv 代表是统计高会流量的标志 值1: 代表统计高会流量 0则不用
		if (!"10051000".equals(member) && !"10051003".equals(member)) {
			out.put("companyId", cid);
			out.put("vipPv", 1);
		}
		out.put("mark", mark);
		out.put("cid", cid);
		return cid;
	}

//	private ModelAndView badrequest() {
//		return new ModelAndView("redirect:" + AddressTool.getAddress("front"));
//	}

	private ModelAndView bad404request(HttpServletResponse response) {
		PageCacheUtil.setStatus(response, 404);
		response.setHeader("Location", "http://www.zz91.com/404.html");
		response.setHeader("Connection", "close");
		return null;
		// return new ModelAndView("redirect:" +
		// AddressTool.getAddress("front"));
	}

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Integer cid
	/*
	 * , PageDto < CompanyAccountContact > page
	 */) throws UnsupportedEncodingException {

		cid = initCompanyId(out, request.getServerName(), cid);

		if (cid == null) {
			return bad404request(response);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!crmCompanySvrService.validatePeriod(cid,CrmCompanySvrService.LDB_CODE)&& !crmCompanySvrService.validatePeriod(cid,CrmCompanySvrService.LDB_FIVE_CODE)) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(cid);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) <= 0.0) {
				break;
			}
			if (crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.BAIDU_CODE)) {
				break;
			}
			set301Status(response);
			response.setHeader("Location", "http://www.zz91.com/ppc/index"
					+ cid + ".htm");
			response.setHeader("Connection", "close");
			return null;
		} while (false);

		// 新版seo判断
		if (seoTemplatesService.validate(cid)) {
			set301Status(response);
			response.setHeader("Location", "http://"
					+ companyService.queryDomainOfCompany(cid).getDomainZz91()
					+ ".zz91.net");
			response.setHeader("Connection", "close");
			return null;
		}

		// out.put("resourceUrl", AddressTool.getAddress("resource"));

		esiteService.initBaseConfig(cid, null, out);
		// 一些特殊的配置
		initMyPosition("index", "首页", out);

		esiteService.initServerAddress(request.getServerName(),
				request.getServerPort(), request.getContextPath(), out);
		// 获取company对象
		Company company = (Company) out.get("company");

		if (company == null) {
			return bad404request(response);
		}
		// 获取公司帐号对象 account
		CompanyAccount account = (CompanyAccount) out.get("account");
		if (account == null) {
			return bad404request(response);
		}

		// 主营业务
		String business = company.getBusiness();
		if (business.length() > 60) {
			business = business.substring(0, 60) + "...";
		}
		// 搜索该公司的 最新的供求
		ProductsDO products = productsService.queryProductsByCid(cid);
		String key = company.getName();
		String title = "";
		String type = "";
		if (products != null) {
			if (products.getProductsTypeCode().equals("10331000")) {
				type = "供应";
			}
			if (products.getProductsTypeCode().equals("10331001")) {
				type = "求购";
			}
			if (products.getProductsTypeCode().equals("10331002")) {
				type = "合作";
			}
			key = products.getTitle();
		}

		String saleDetails = company.getSaleDetails();
		String buyDetails = company.getBuyDetails();
		String titleString = "";
		// Title 读取后台 主营方向(供应/求购) 两个字段信息
		if (StringUtils.isNotEmpty(saleDetails)) {
			titleString = saleDetails;
		} else if (StringUtils.isEmpty(saleDetails)
				&& StringUtils.isNotEmpty(buyDetails)) {
			titleString = buyDetails;
		} else {
			titleString = company.getName() + "," + account.getContact();
		}
		// 组装titie
		title = titleString;
		// 组装keywords
		String keywords = key;
		if (StringUtils.isNotEmpty(type)) {
			keywords = type + keywords;
		}

		// 获取公司开通服务的判断，属于2013-11-1之后还是之前
		if (getNewFlag(cid, out)) {
			keywords = company.getTags();
		}

		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("index", new String[] { title },
				new String[] { keywords },
				new String[] { company.getName(), business }, out);
		// cdn time set
		setCDNTime(response);

		return new ModelAndView("index");
	}

	// CACHE_TIME
	final static int INFO_CACHE_TIME = 60;

	/**
	 * 放入Cache中
	 * 
	 * @param info
	 * @return key
	 */
	String sendInfoToCache(String info, String key) {
		if (StringUtils.isNotEmpty(info)) {
			MemcachedUtils.getInstance().getClient()
					.set(key, INFO_CACHE_TIME, info);
			return key;
		}
		return null;
	}

	/**
	 * 
	 * 
	 * 
	 * @param type
	 *            ：联系方式类型，null或空不获取信息
	 * @param 公司ID号
	 *            Key
	 * @return
	 */
	@RequestMapping
	public ModelAndView viewContactInfo(Map<String, Object> out, String type,
			String key, String color, String position,
			HttpServletResponse response) throws IOException {

		if (key == null || type == null || key == "" || type == "")
			return null;

		CompanyAccount account = companyAccountService
				.queryAdminAccountByCompanyId(Integer.valueOf(key));
		String s = "";

		if (account != null) {
			String mobile = account.getMobile();
			String tel = (account.getTelAreaCode() == null ? "" : (account
					.getTelAreaCode() + " ")) + account.getTel();
			String tel2 = (account.getTelCountryCode() == null ? "" : (account
					.getTelCountryCode() + " "))
					+ (account.getTelAreaCode() == null ? "" : (account
							.getTelAreaCode() + " ")) + account.getTel();
			String fax = (account.getFaxCountryCode() == null ? "" : (account
					.getFaxCountryCode() + " "))
					+ (account.getFaxAreaCode() == null ? "" : (account
							.getFaxAreaCode() + " ")) + account.getFax();
			String email = account.getEmail();
			String email2 = account.getBackEmail();

			if (type.equalsIgnoreCase("mobileKey")) {
				s = mobile;
			} else if (type.equalsIgnoreCase("telKey")) {
				s = tel;
			} else if (type.equalsIgnoreCase("telKey2")) {
				s = tel2;
			} else if (type.equalsIgnoreCase("faxkey")) {
				s = fax;
			} else if (type.equalsIgnoreCase("emailKey")) {
				s = email;
			} else if (type.equalsIgnoreCase("emailKey2")) {
				s = email2;
			}
		}

		if (s == null) {
			return null;
		}
		int width = 0;
		int height = 0;
		// 多个手机号码的分割
		String[] phone = s.split("/");
		if ("mobileKey".equals(type) && s.length() > 11
				&& "left".equals(position)) {
			width = phone[0].length() * 8 + 4;
			height = 16 * phone.length;
		} else {
			width = s.getBytes().length * 8 + 2;
			height = 16;
		}

		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		if ("red".equals(color)) {
			g2.setColor(Color.red);
		} else {
			g2.setColor(Color.black);
		}
		if ("mobileKey".equals(type) && "left".equals(position)) {
			for (int i = 0; i < phone.length; i++) {
				StringBuffer sb = new StringBuffer();
				sb.append(phone[i] + "\n");
				g2.drawString(sb.toString(), 4, 15 + i * 16);
			}
		} else {
			g2.drawString(s, 2, 13);
		}
		ImageIO.write(bi, "jpg", response.getOutputStream());
		return null;
	}

	@RequestMapping
	public ModelAndView gsjs(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Integer cid) {
		cid = initCompanyId(out, request.getServerName(), cid);

		if (cid == null) {
			return bad404request(response);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!crmCompanySvrService.validatePeriod(cid,
					CrmCompanySvrService.LDB_CODE)
					&& !crmCompanySvrService.validatePeriod(cid,
							CrmCompanySvrService.LDB_FIVE_CODE)) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(cid);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) <= 0.0) {
				break;
			}
			if (crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.BAIDU_CODE)) {
				break;
			}
			set301Status(response);
			response.setHeader("Location", "http://www.zz91.com/ppc/index"
					+ cid + ".htm");
			response.setHeader("Connection", "close");
			return null;
		} while (false);
		// 新版seo判断
		if (seoTemplatesService.validate(cid)) {
			out = null;
			return new ModelAndView("redirect:http://"
					+ companyService.queryDomainOfCompany(cid).getDomainZz91()
					+ ".zz91.net");
		}

		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("gsjs", "公司介绍", out);
		esiteService.initServerAddress(request.getServerName(),
				request.getServerPort(), request.getContextPath(), out);

		// 荣誉证书
		out.put("fileList", creditFileService.queryFileByCompany(cid));
		out.put("categoryMap",
				CategoryFacade.getInstance().getChild(FILE_CATEGORY));

		/*
		 * String[] titleParam = { company.getTags(), company.getName() };
		 * String[] keywords = { company.getTags() }; String[] description = {
		 * StringUtils.isNotEmpty(company.getBusiness()) ? company
		 * .getBusiness() : company.getName() };
		 * SeoUtil.getInstance().buildSeo("zxgq", titleParam, keywords,
		 * description, out);
		 */

		// SEO
		Company company = (Company) out.get("company");
		String title = "";
		String business = company.getBusiness();
		// 重新组装title
		// String tags = company.getTags();
		String companyName = company.getName();
		// String titleString = "";
		// if (StringUtils.isEmpty(tags)) {
		// titleString = "-" + companyName;
		// } else {
		// titleString = "_" + tags + "-" + companyName;
		// }
		// title = titleString;
		title = "-" + companyName;

		// 重新组装keywords
		// String keywords = "";
		// if (StringUtils.isNotEmpty(tags)) {
		// keywords = tags;
		// }
		// 重新组装description
		String description = "";
		if (StringUtils.isNotEmpty(business)) {
			description = business;
		}
		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("gsjs", new String[] { title },
				new String[] {},
				new String[] { company.getName(), description }, out);

		// cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView zxgq(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out) {
		String markString = request.getParameter("mark");
		Integer mark = null;
		if (StringUtils.isNotEmpty(markString)) {
			mark = Integer.valueOf(markString);
		}
		String cidString = request.getParameter("cid");
		Integer cid = null;
		if (StringUtils.isNotEmpty(cidString)) {
			cid = Integer.valueOf(cidString);
		}
		String sidString = request.getParameter("sid");
		Integer sid = null;
		if (StringUtils.isNotEmpty(sidString)) {
			sid = Integer.valueOf(sidString);
		}
		String kw = request.getParameter("kw");
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotEmpty(startIndexString)) {
			Integer startIndex = Integer.valueOf(startIndexString);
			if (mark == null) {
				page.setStartIndex(startIndex);
			}
		}
		String pageSizeString = request.getParameter("pageSize");
		if (StringUtils.isNotEmpty(pageSizeString)) {
			Integer pageSize = Integer.valueOf(pageSizeString);
			page.setPageSize(pageSize);
		}
		out.put("pageCode", "all_offer_index");
		cid = initCompanyId(out, request.getServerName(), cid);

		if (cid == null) {
			return bad404request(response);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!crmCompanySvrService.validatePeriod(cid,CrmCompanySvrService.LDB_CODE) && !crmCompanySvrService.validatePeriod(cid,CrmCompanySvrService.LDB_FIVE_CODE)) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(cid);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) <= 0.0) {
				break;
			}
			if (crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.BAIDU_CODE)) {
				break;
			}
			set301Status(response);
			response.setHeader("Location", "http://www.zz91.com/ppc/index" + cid + ".htm");
			response.setHeader("Connection", "close");
			return null;
		} while (false);
		// 新版seo判断
		if (seoTemplatesService.validate(cid)) {
			out = null;
			return new ModelAndView("redirect:http://"
					+ companyService.queryDomainOfCompany(cid).getDomainZz91()
					+ ".zz91.net");
		}

		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("zxgq", "最新供求", out);
		esiteService.initServerAddress(request.getServerName(),request.getServerPort(), request.getContextPath(), out);

		try {
			kw = StringUtils.decryptUrlParameter(kw);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		page.setPageSize(8);
		// page = productsService.pageProductsWithPicByCompanyEsite(cid, kw,
		// sid,page);
		if (crmCompanySvrService.validatePeriod(cid, CrmSvrService.CRM_SP)) {
			if (mark == null) {
				page = productsService.pageProductsWithPicByCompanyForSp(cid,
						kw, sid, page);
			} else {
				page = productsService.pageProductsWithPicByCompanyForSp(cid,
						null, sid, page);
			}
		} else {
			page.setSort("p.id");
			page.setDir("desc");
			if (mark == null) {
				page = productsService
						.pageProductsWithPicByCompanyEsiteWithDetails(cid, kw,
								sid, page);
			} else {
				page = productsService
						.pageProductsWithPicByCompanyEsiteWithDetails(cid,
								null, sid, page);
			}
		}

		// 添加样品,询价数信息
		List<ProductsDto> dtoList = new ArrayList<ProductsDto>();
		for (ProductsDto productsDTO : (List<ProductsDto>) page.getRecords()) {
			// 样品信息
			Sample sample = sampleService.queryByIdOrProductId(null,
					productsDTO.getProducts().getId());
			if (sample != null && sample.getIsDel() == 0) {
				productsDTO.setSample(sample);
			}
			if (StringUtils.isNotEmpty(productsDTO.getProducts()
					.getManufacture())) {
				productsDTO.getProducts().setManufacture(
						CategoryFacade.getInstance().getValue(
								productsDTO.getProducts().getManufacture()));
			}
			dtoList.add(productsDTO);
		}

		page.setRecords(dtoList);

		out.put("page", page);
		PageDto<YuanliaoDto> ylPage = new PageDto<YuanliaoDto>();
		ylPage.setPageSize(8);
		if (mark != null && startIndexString != null) {
			ylPage.setStartIndex(Integer.valueOf(startIndexString));
		}
		ylPage.setSort("refresh_time");
		ylPage.setDir("desc");
		if (mark == null) {
			ylPage = companyService.pageYuanliaoByCompanyId(ylPage,
					Integer.valueOf(cid), null);
		} else {
			ylPage = companyService.pageYuanliaoByCompanyId(ylPage,
					Integer.valueOf(cid), kw);
		}
		out.put("ylpage", ylPage);
		out.put("mark", mark);
		out.put("sid", sid);
		// sid 存在的情况下 搜索出点击的类别中文名
		ProductsSeriesDO productsSeriesDO = new ProductsSeriesDO();
		if (sid != null) {
			productsSeriesDO = productsSeriesService
					.queryProductsSeriesById(sid);
			out.put("productCategory", productsSeriesDO);
		}
		out.put("kw", kw);

		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_HOUR);

		// SEO
		Company company = (Company) out.get("company");
		String title = "";
		String companyName = company.getName();
		String business = company.getBusiness();
		// 重组关键词keywords
		String keywords = "最新供求";
		// 重新组装description
		String description = "";
		if (StringUtils.isNotEmpty(business)) {
			description = company.getName() + business + "。更多信息请访问ZZ91再生网官网。";
		} else {
			description = company.getName() + "。更多信息请访问ZZ91再生网官网。";
		}
		page.setCurrentPage(page.getStartIndex() / page.getPageSize() + 1);
		if (page.getCurrentPage() > 1) {
			title = "最新供求" + "_第" + page.getCurrentPage() + "页" + "-"
					+ companyName;
		} else {
			title = "最新供求" + "-" + companyName;
		}
		if (sid != null) {
			ProductsSeriesDO psd = productsSeriesService
					.queryProductsSeriesById(sid);
			if (psd == null) {
				title = "最新供求" + "-" + companyName;
			}
			if (page.getCurrentPage() > 1) {
				title = psd.getName() + "_第" + page.getCurrentPage() + "页"
						+ "_最新供求" + "-" + companyName;
			} else {
				title = psd.getName() + "_最新供求" + "-" + companyName;
			}
		}
		if (StringUtils.isNotEmpty(kw)) {
			if (page.getCurrentPage() > 1) {
				title = kw + "_第" + page.getCurrentPage() + "页" + "_最新供求" + "-"
						+ companyName;
			} else {
				title = kw + "_最新供求" + "-" + companyName;
			}
			keywords = "/";
			description = "/";
		}
		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("zxgq", new String[] { title },
				new String[] { keywords }, new String[] { description }, out);
		// cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView gsdt(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			PageDto<EsiteNewsDo> page, Integer cid) {
		out.put("pageCode", "newslist_index");
		// long start=0;
		// long end=0;
		// start=System.currentTimeMillis();
		// end=System.currentTimeMillis();
		// System.out.println("cost1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));

		// start=System.currentTimeMillis();
		cid = initCompanyId(out, request.getServerName(), cid);

		// end=System.currentTimeMillis();
		// System.out.println("cost2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));

		if (cid == null) {
			return bad404request(response);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!crmCompanySvrService.validatePeriod(cid,
					CrmCompanySvrService.LDB_CODE)
					&& !crmCompanySvrService.validatePeriod(cid,
							CrmCompanySvrService.LDB_FIVE_CODE)) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(cid);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) <= 0.0) {
				break;
			}
			if (crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.BAIDU_CODE)) {
				break;
			}
			set301Status(response);
			response.setHeader("Location", "http://www.zz91.com/ppc/index"
					+ cid + ".htm");
			response.setHeader("Connection", "close");
			return null;

		} while (false);
		// 新版seo判断
		if (seoTemplatesService.validate(cid)) {
			out = null;
			return new ModelAndView("redirect:http://"
					+ companyService.queryDomainOfCompany(cid).getDomainZz91()
					+ ".zz91.net");
		}

		// start=System.currentTimeMillis();
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);
		// end=System.currentTimeMillis();
		// System.out.println("cost3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));

		initMyPosition("gsdt", "公司动态", out);
		esiteService.initServerAddress(request.getServerName(),
				request.getServerPort(), request.getContextPath(), out);

		page.setSort("post_time");
		page.setDir("desc");
		page.setPageSize(15);
		// start=System.currentTimeMillis();
		page = esiteNewsService.pageNewsByCompany(cid, page);
		// end=System.currentTimeMillis();
		// System.out.println("cost4>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+(end-start));

		out.put("page", page);
		page.getRecords();
		// SEO
		Company company = (Company) out.get("company");
		String title = "";
		String companyName = company.getName();
		String business = company.getBusiness();
		page.setCurrentPage(page.getStartIndex() / page.getPageSize() + 1);
		if (page.getCurrentPage() > 1) {
			title = "_第" + page.getCurrentPage() + "页" + "-" + companyName;
		} else {
			title = "-" + companyName;
		}

		// 重新组装description
		String description = "";
		if (StringUtils.isNotEmpty(business)) {
			description = business;
		}
		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("gsdt", new String[] { title },
				new String[] {},
				new String[] { company.getName(), description }, out);
		// cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

//	@SuppressWarnings("unused")
	@RequestMapping
	public ModelAndView gsdtdetail(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Integer id,
			Integer cid) {

		cid = initCompanyId(out, request.getServerName(), cid);
		if (cid == null) {
			return bad404request(response);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!crmCompanySvrService.validatePeriod(cid,
					CrmCompanySvrService.LDB_CODE)
					&& !crmCompanySvrService.validatePeriod(cid,
							CrmCompanySvrService.LDB_FIVE_CODE)) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(cid);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) <= 0.0) {
				break;
			}
			if (crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.BAIDU_CODE)) {
				break;
			}
			set301Status(response);
			response.setHeader("Location", "http://www.zz91.com/ppc/index"
					+ cid + ".htm");
			response.setHeader("Connection", "close");
			return null;

		} while (false);

		// 新版seo判断
		if (seoTemplatesService.validate(cid)) {
			out = null;
			return new ModelAndView("redirect:http://"
					+ companyService.queryDomainOfCompany(cid).getDomainZz91()
					+ ".zz91.net");
		}

		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("gsdt", "公司动态", out);
		esiteService.initServerAddress(request.getServerName(),
				request.getServerPort(), request.getContextPath(), out);

		// 详细的动态信息
		EsiteNewsDo news = esiteNewsService.queryOneNewsById(id);
		// 判断页面是否存在 判断信息是否属于此公司(zhengrp 2015-5-29)
		if (news == null || !cid.equals(news.getCompanyId())) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ModelAndView("errorPage404");
		}

		// 查询上一篇文章
		EsiteNewsDo lastNews = esiteNewsService.queryLastNewsById(id, cid);
		out.put("lastNews", lastNews);
		// 查询下一篇文章
		EsiteNewsDo nextNews = esiteNewsService.queryNextNewsById(id, cid);
		out.put("nextNews", nextNews);

		news.setContent(news.getContent().replace("\n", "<br/>"));
		news.setContent(news.getContent().replace("<br/>", ""));
		// news.setContent(Jsoup.clean(news.getContent(), Whitelist.basic()));
		out.put("news", news);
		String str = news.getContent().trim();
		str = StringUtils.removeHTML(str);
		if (str.length() > 200) {
			str = str.substring(0, 200);
		}
		// 百度推广
		out.put("sendCheck",
				ParamUtils.getInstance().getValue("seo_config", "send_check"));
		// seo
		Company company = companyService.queryCompanyById(cid);
		// String newsName = news.getTitle();
		/*
		 * String[] titleParam = { news.getTitle(), company.getName() };
		 * String[] keyParam = { company.getTags() }; String[] description = {
		 * str }; SeoUtil.getInstance().buildSeo("gsdtdetail", titleParam,
		 * keyParam, description, out);
		 */
		String tags = company.getTags();
		String companyName = company.getName();
		// String business = company.getBusiness();
		// 组装title
		String[] titleParam = { news.getTitle() + "-" + companyName };
		// 重新组装keywords
		String keywords = "";
		if (StringUtils.isNotEmpty(tags)) {
			keywords = tags;
		}
		// 重新组装description
		String[] description = { str };
		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("gsdtdetail", titleParam,
				new String[] { keywords }, description, out);
		// cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	final static String FILE_CATEGORY = "1040";

	@RequestMapping
	public ModelAndView cxda(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Integer cid,
			String type) {
		cid = initCompanyId(out, request.getServerName(), cid);

		if (cid == null) {
			return bad404request(response);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!crmCompanySvrService.validatePeriod(cid,
					CrmCompanySvrService.LDB_CODE)
					&& !crmCompanySvrService.validatePeriod(cid,
							CrmCompanySvrService.LDB_FIVE_CODE)) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(cid);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) <= 0.0) {
				break;
			}
			if (crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.BAIDU_CODE)) {
				break;
			}
			set301Status(response);
			response.setHeader("Location", "http://www.zz91.com/ppc/index"
					+ cid + ".htm");
			response.setHeader("Connection", "close");
			return null;

		} while (false);
		// 新版seo判断
		if (seoTemplatesService.validate(cid)) {
			out = null;
			return new ModelAndView("redirect:http://"
					+ companyService.queryDomainOfCompany(cid).getDomainZz91()
					+ ".zz91.net");
		}

		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("cxda", "诚信档案", out);
		esiteService.initServerAddress(request.getServerName(),
				request.getServerPort(), request.getContextPath(), out);

		// 从生意管家进入到门市部的诚信档案
		if (StringUtils.isEmpty(type)) {
			out.put("type", "0");
		} else {
			out.put("type", type);
		}
		// 荣誉证书
		List<CreditFileDo> fileList = creditFileService.queryFileByCompany(cid);

		// 审核通过证书
		List<CreditFileDo> passList = new ArrayList<CreditFileDo>();
		// 过期证书数量
		int expiredNum = 0;
		for (CreditFileDo file : fileList) {
			if ("1".equals(file.getCheckStatus())) {
				passList.add(file);
			}
		}
		// 未过期证书
		List<CreditFileDo> NoExpiredList = new ArrayList<CreditFileDo>();
		for (CreditFileDo file : passList) {
			if (file.getEndTime() != null
					&& file.getEndTime().getTime() - new Date().getTime() < 0) {
				expiredNum++;
			} else {
				NoExpiredList.add(file);
			}
		}

		out.put("passList", passList);
		out.put("expiredNum", expiredNum);
		out.put("NoExpiredList", NoExpiredList);
		/*
		 * out.put("categoryMap", CategoryFacade.getInstance()
		 * .getChild(FILE_CATEGORY));
		 */

		// 资信参考人
		out.put("referenceList",
				creditReferenceService.queryReferenceByCompany(cid));

		// 证书编号 (11位)
		String zeroNum = "";
		for (int i = 0; i < 8 - cid.toString().length(); i++) {
			zeroNum += 0;
		}
		out.put("creditId", "007" + zeroNum + cid);

		// 客户评价
		/*
		 * out.put("vote_0", creditCustomerVoteService.countVoteNumByToCompany(
		 * cid, "0", "1", null)); // 统计收到的有效好评数 out.put("vote_1",
		 * creditCustomerVoteService.countVoteNumByToCompany( cid, "1", "1",
		 * true)); // 统计收到的有效中评数 out.put("vote_2",
		 * creditCustomerVoteService.countVoteNumByToCompany( cid, "2", "1",
		 * true)); // 统计收到的有效差评数
		 */
		// 开通的服务
		List<CrmCompanySvrDto> list = crmCompanySvrService.queryCompanySvr(cid,
				true);
		List<CrmCompanySvrDto> openList = new ArrayList<CrmCompanySvrDto>();
		Boolean zst = false;
		Boolean ldb = false;
		for (int i = list.size() - 1; i >= 0; i--) {
			// 开通服务是否开通
			if (list.get(i).getCrmCompanySvr().getApplyStatus().equals("1")) {
				if (list.get(i).getCrmCompanySvr().getCrmServiceCode()
						.equals("1000")
						&& !zst) {
					if ("再生通".equals(list.get(i).getSvrName())) {
						openList.add(list.get(i));
						zst = true;
					}
				}
				if (list.get(i).getCrmCompanySvr().getCrmServiceCode()
						.equals("1007")
						&& !ldb) {
					openList.add(list.get(i));
					ldb = true;
				}
			}
		}
		out.put("crmDto", openList);

		// 认证信息
		CompanyAttest companyAttest = new CompanyAttest();
		companyAttest.setCompanyId(cid);
		companyAttest.setCheckStatus("1");
		CompanyAttest attest = companyAttestService.queryOneInfo(companyAttest);
		if (attest != null) {
			out.put("showType", "1");
			// 年检时间用于前台显示
			attest.setInspectionTimeStr(DateUtil.toString(
					attest.getInspectionTime(), "yyyy年MM月dd日"));
			if ("1".equals(attest.getAttestType())) {
				// 如果客户不显示，则注册号后8位用*表示
				if ("0".equals(attest.getShowCode())) {
					String code = attest.getRegisterCode();
					if (code.length() > 8) {
						code = code.replace(code.substring(code.length() - 8),
								"********");
					} else {
						code = code.replace(code, "********");
					}

					attest.setRegisterCode(code);
				}
				// 如果客户选择不显示，则注册资金有几位则用几个*表示
				if ("0".equals(attest.getShowCapital())) {
					attest.setRegisterCapital(companyAttestService
							.replaceStar(attest.getRegisterCapital()));
				}
				// 如果客户选择不显示，则法定代表人用*表示
				if ("0".equals(attest.getShowLegal())) {
					attest.setLegal(companyAttestService.replaceStar(attest
							.getLegal()));
				}
				// 如果客户选择不显示，则登记机关用*表示
				if ("0".equals(attest.getShowOrg())) {
					attest.setOrganization(companyAttestService
							.replaceStar(attest.getOrganization()));
				}
				// 如果客户选择不显示，则年检时间用*表示
				if ("0".equals(attest.getShowInspection())) {
					String timeStr = attest.getInspectionTimeStr();
					timeStr = timeStr.replace(timeStr.substring(5), "**月**日");
					attest.setInspectionTimeStr(timeStr);
				}
			} else {
				// 如果客户选择不显示，则身份证号码后12位用*表示
				if ("0".equals(attest.getShowIdNumber())) {
					String code = attest.getIdNumber();
					if (StringUtils.isNotEmpty(code)) {
						code = code.replace(code.substring(attest.getIdNumber()
								.length() - 12), "************");
					}
					attest.setIdNumber(code);
				}
			}
		}
		out.put("attest", attest);

		// 诚信指数算法：注册信息工商/个人（10分）+每个证书（5分）+ 再生通服务年限
		Integer attestIntegral = 0;
		if (attest != null && "1".equals(attest.getCheckStatus())) {
			attestIntegral += 10;
		}
		attestIntegral += NoExpiredList.size()
				* 5
				+ creditIntegralDetailsService.countIntegralByOperationKey(cid,
						"service_zst");
		out.put("attestIntegral", attestIntegral);

		// 诚信积分
		out.put("integral_service_zst", creditIntegralDetailsService
				.countIntegralByOperationKey(cid, "service_zst")); // 再生通服务年限
		out.put("integral_credit_file", NoExpiredList.size() * 5);// 证书积分

		// SEO
		Company company = (Company) out.get("company");
		String title = "";
		String business = company.getBusiness();
		// 重新组装title
		// String tags = company.getTags();
		String companyName = company.getName();
		// String titleString = "";
		// if (StringUtils.isEmpty(tags)) {
		// titleString = "-" + companyName;
		// } else {
		// titleString = "_" + tags + "-" + companyName;
		// }
		// title = titleString;
		title = "-" + companyName;

		// 重新组装keywords
		// String keywords = "";
		// if (StringUtils.isNotEmpty(tags)) {
		// keywords = tags;
		// }
		// 重新组装description
		String description = "";
		if (StringUtils.isNotEmpty(business)) {
			description = business;
		}
		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("cxda", new String[] { title },
				new String[] {},
				new String[] { company.getName(), description }, out);
		// cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView cxdavote(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Integer cid) {
		cid = initCompanyId(out, request.getServerName(), cid);

		if (cid == null) {
			return bad404request(response);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!crmCompanySvrService.validatePeriod(cid,
					CrmCompanySvrService.LDB_CODE)
					&& !crmCompanySvrService.validatePeriod(cid,
							CrmCompanySvrService.LDB_FIVE_CODE)) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(cid);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) <= 0.0) {
				break;
			}
			if (crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.BAIDU_CODE)) {
				break;
			}
			set301Status(response);
			response.setHeader("Location", "http://www.zz91.com/ppc/index"
					+ cid + ".htm");
			response.setHeader("Connection", "close");
			return null;

		} while (false);
		// 新版seo判断
		if (seoTemplatesService.validate(cid)) {
			out = null;
			return new ModelAndView("redirect:http://"
					+ companyService.queryDomainOfCompany(cid).getDomainZz91()
					+ ".zz91.net");
		}

		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("cxda", "发表评价", out);
		esiteService.initServerAddress(request.getServerName(),
				request.getServerPort(), request.getContextPath(), out);

		// cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	/**
	 * 从zz91主站进入的方法
	 */
	@RequestMapping
	public ModelAndView initVote(HttpServletRequest request,
			Map<String, Object> out, Integer cid) {
		out.put("company", companyService.querySimpleCompanyById(cid));
		return null;
	}

	/**
	 * 从zz91主站进入的方法
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView voteToCompany(HttpServletRequest request,
			Map<String, Object> out, CreditCustomerVoteDo vote, Integer cid)
			throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		if (!ssoUser.getCompanyId().equals(vote.getToCompanyId())) {
			vote.setFromAccount(ssoUser.getAccount());
			vote.setFromCompanyId(ssoUser.getCompanyId());
			vote.setCheckStatus("0");
			Integer i = creditCustomerVoteService.voteToCompany(vote);
			if (i != null && i > 0) {
				result.setSuccess(true);
			}
		} else {
			result.setData("canNotVoteSelf");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView zxly(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, String a,
			Integer id) {

		id = initCompanyId(out, request.getServerName(), id);

		if (id == null) {
			id = companyAccountService.queryCompanyIdByAccount(a);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!(crmCompanySvrService.validatePeriod(id,
					CrmCompanySvrService.LDB_CODE) && !crmCompanySvrService
					.validatePeriod(id, CrmCompanySvrService.LDB_FIVE_CODE))) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(id);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) <= 0.0) {
				break;
			}
			if (crmCompanySvrService.validatePeriod(id, CrmCompanySvrService.BAIDU_CODE)) {
				break;
			}
			set301Status(response);
			response.setHeader("Location", "http://www.zz91.com/ppc/index" + id + ".htm");
			response.setHeader("Connection", "close");
			return null;
		} while (false);
		// 新版seo判断
		if (seoTemplatesService.validate(id)) {
			out = null;
			return new ModelAndView("redirect:http://"+ companyService.queryDomainOfCompany(id).getDomainZz91() + ".zz91.net");
		}

		Company company = companyService.queryCompanyById(id);
		if (company == null) {
			out.put(AstConst.ERROR_TEXT, "对不起，没有您查看的公司信息，请检查地址是否正确！");
		}
		if (company.getIsBlock() == null || "1".equals(company.getIsBlock())) {
			out.put(AstConst.ERROR_TEXT, "对不起，该公司已不做生意或账号被冻结！");
		}

		out.put("companyinfo", company);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		esiteService.initBaseConfig(id, null, out);

		initMyPosition("zxly", "在线留言", out);
		esiteService.initServerAddress(request.getServerName(),
				request.getServerPort(), request.getContextPath(), out);

		// SEO
		// CompanyAccount account = (CompanyAccount) out.get("account");
		// String introduction = (String) out.get("introduction");
		// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
		// 如果为true表示还没有过期，有当前业务
		// Boolean isView = crmCompanySvrService.validatePeriod(id,
		// CrmCompanySvrService.ZST_CODE);
		// Boolean bool = crmCompanySvrService.validatePeriod(id,
		// CrmCompanySvrService.ESITE_CODE);
		/*
		 * String[] keywords = { company.getName(), "在线留言" }; String[]
		 * description = { introduction }; if (company.getMembershipCode() !=
		 * CrmSvrService.PT_CODE) { String[] titleParam = { company.getName(),
		 * "在线留言", account.getContact(), "，" + account.getMobile() };
		 * SeoUtil.getInstance().buildSeo("zxly", titleParam, keywords,
		 * description, out);
		 * 
		 * } else { String[] titleParam = { company.getName(), "在线留言",
		 * account.getContact(), "" }; SeoUtil.getInstance().buildSeo("zxly",
		 * titleParam, keywords, description, out); }
		 */
		String title = "";
		String business = company.getBusiness();
		// 重新组装title
		// String tags = company.getTags();
		String companyName = company.getName();
		// String titleString = "";
		// if (StringUtils.isEmpty(tags)) {
		// titleString = "-" + companyName;
		// } else {
		// titleString = "_" + tags + "-" + companyName;
		// }
		// title = titleString;
		title = "-" + companyName;

		// 重新组装keywords
		// String keywords = "";
		// if (StringUtils.isNotEmpty(tags)) {
		// keywords = tags;
		// }
		// 重新组装description
		String description = "";
		if (StringUtils.isNotEmpty(business)) {
			description = business;
		}
		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("zxly", new String[] { title },
				new String[] {},
				new String[] { company.getName(), description }, out);

		// cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}

	@RequestMapping
	public ModelAndView sitemap(HttpServletRequest request,
			Map<String, Object> out, HttpServletResponse response, Integer cid)
			throws IOException {
		cid = initCompanyId(out, request.getServerName(), cid);
		if (cid == null) {
			return bad404request(response);
		}
		response.setContentType("application/xml");
		ServletOutputStream rs = response.getOutputStream();
		Company company = companyService.queryCompanyById(cid);
		String url = company.getDomainZz91();
		if (url == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("myrc"));
		}
		// 查询所有动态
		List<EsiteNewsDo> News = esiteNewsService.querybyCompanyAll(cid);
		// 最新供求
		List<ProductsDO> list3 = productsService.queryNewProducts(cid);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(new Date());
		StringBuffer buff = new StringBuffer();
		buff.append("<?xml version='1.0' encoding='utf-8'?>" + "<urlset>"
				+ "<url>" + "<loc>http://"
				+ url
				+ ".zz91.com/</loc>"
				+ "<lastmod>"
				+ time
				+ "</lastmod>"
				+ "<changefreq>daily</changefreq>"
				+ "<priority>1.0</priority>"
				+ "</url>"
				+ "<url>"
				+ "<loc>http://"
				+ url
				+ ".zz91.com/gsjs.htm</loc>"
				+ "<lastmod>"
				+ time
				+ "</lastmod>"
				+ " <changefreq>daily</changefreq>"
				+ "<priority>0.8</priority>"
				+ "</url>"
				+ "<url>"
				+ "<loc>http://"
				+ url
				+ ".zz91.com/gsdt.htm</loc>"
				+ "<lastmod>"
				+ time
				+ "</lastmod>"
				+ "<changefreq>daily</changefreq>"
				+ "<priority>0.8</priority>"
				+ "</url>"
				+ "<url>"
				+ "<loc>http://"
				+ url
				+ ".zz91.com/zxgq.htm</loc>"
				+ "<lastmod>"
				+ time
				+ "</lastmod>"
				+ "<changefreq>daily</changefreq>"
				+ "<priority>0.8</priority>"
				+ "</url>	"
				+ " <url>	"
				+ "<loc>http://"
				+ url
				+ ".zz91.com/lxfs.htm</loc>"
				+ "<lastmod>"
				+ time
				+ "</lastmod>"
				+ "<changefreq>daily</changefreq>"
				+ "<priority>0.8</priority>"
				+ "</url>"
				+ "<url>"
				+ " <loc>http://"
				+ url
				+ ".zz91.com/cxda.htm</loc>"
				+ "<lastmod>"
				+ time
				+ "</lastmod>"
				+ "<changefreq>daily</changefreq>"
				+ "<priority>0.8</priority>" + "</url>");
		if (News.size() > 0) {
			for (int i = 0; i < News.size(); i++) {
				String New = "<url>" + " <loc>http://" + url + ".zz91.com/news"
						+ News.get(i).getId() + ".htm</loc>" + "<lastmod>"
						+ format.format(News.get(i).getPostTime())
						+ "</lastmod>" + " <changefreq>daily</changefreq>"
						+ "<priority>0.6</priority></url>";
				buff.append(New);
			}
		}
		if (list3.size() > 0) {
			for (int i = 0; i < list3.size(); i++) {
				String pro = "<url>" + "<loc>http://" + url
						+ ".zz91.com/products" + list3.get(i).getId()
						+ ".htm</loc>" + "<lastmod>"
						+ format.format(list3.get(i).getRefreshTime())
						+ "</lastmod>" + "<changefreq>daily</changefreq>	"
						+ "<priority>0.6</priority></url>";
				buff.append(pro);
			}
		}
		buff.append("</urlset>");
		rs.println(buff.toString());
		return null;
	}

	@RequestMapping
	public ModelAndView lxfs(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Integer cid
	/*
	 * , PageDto < CompanyAccountContact > page
	 */) {
		cid = initCompanyId(out, request.getServerName(), cid);

		if (cid == null) {
			return bad404request(response);
		}
		do {
			// 判断400客户 一元来电宝和五元来电宝
			if (!crmCompanySvrService.validatePeriod(cid,
					CrmCompanySvrService.LDB_CODE)
					&& !crmCompanySvrService.validatePeriod(cid,
							CrmCompanySvrService.LDB_FIVE_CODE)) {
				break;
			}
			// 获取400客户
			Phone phone = phoneService.queryByCompanyId(cid);
			if (phone == null || StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			// 获取余额
			String balance = phoneLogService.countBalance(phone);
			// 判断400客户是否欠费
			if (Float.valueOf(balance) > 0.0) {
				return new ModelAndView(
						"redirect:http://www.zz91.com/ppc/index" + cid + ".htm");
			}

		} while (false);
		// 新版seo判断
		if (seoTemplatesService.validate(cid)) {
			out = null;
			return new ModelAndView("redirect:http://"
					+ companyService.queryDomainOfCompany(cid).getDomainZz91()
					+ ".zz91.net");
		}

		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		esiteService.initBaseConfig(cid, null, out);

		initMyPosition("lxfs", "联系方式", out);
		esiteService.initServerAddress(request.getServerName(),
				request.getServerPort(), request.getContextPath(), out);
		// CompanyAccount account =
		// companyAccountService.queryAdminAccountByCompanyId(cid);
		// page =
		// companyAccountContactService.pageContactByCompany(account.getAccount(),
		// page,"0");
		// out.put("page", page);
		// TODO 以图片形式展示联系方式
		// out.put("contactInfo",companyAccountService.queryAdminAccountByCompanyId(cid));

		// SEO
		Company company = (Company) out.get("company");
		/*
		 * String[] titleParam = { company.getTags(), company.getName() };
		 * String[] keywords = { company.getTags() }; String[] description = {
		 * StringUtils.isNotEmpty(company.getBusiness()) ? company
		 * .getBusiness() : company.getName() };
		 * SeoUtil.getInstance().buildSeo("lxfs", titleParam, keywords,
		 * description, out);
		 */
		String title = "";
		// String tags = company.getTags();
		String companyName = company.getName();
		String business = company.getBusiness();
		// String titleString = "";
		// if (StringUtils.isEmpty(tags)) {
		// titleString = "-" + companyName;
		// } else {
		// titleString = "_" + tags + "-" + companyName;
		// }
		// title = titleString;
		title = "-" + companyName;
		// 重新组装keywords
		// String keywords = "";
		// if (StringUtils.isNotEmpty(tags)) {
		// keywords = tags;
		// }
		// 重新组装description
		String description = "";
		if (StringUtils.isNotEmpty(business)) {
			description = business;
		}
		// 装载页面tkd元素
		SeoUtil.getInstance().buildSeo("lxfs", new String[] { title },
				new String[] {},
				new String[] { company.getName(), description }, out);
		// cdn time set
		setCDNTime(response);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView errorPage404(HttpServletRequest request,
			Map<String, Object> out) {
		LOG.warn("[no page found!]: referer referer:"
				+ request.getHeader("referer") + " request URI:"
				+ request.getRequestURI()+" ip is : "+HttpUtils.getInstance().getIpAddr(request));
		return null;
	}

	private Boolean getNewFlag(Integer cid, Map<String, Object> out) {
		List<CrmCompanySvr> list = crmCompanySvrService.querySvrHistory(cid,
				CrmCompanySvrService.ZST_CODE);
		Date date = new Date();
		try {
			date = DateUtil.getDate("2013-11-1", "yyyy-MM-dd");
		} catch (ParseException e) {
		}
		do {
			if (list != null && list.size() > 0) {
				CrmCompanySvr crmCompanySvr = list.get(0);
				long startTime = crmCompanySvr.getGmtStart().getTime();
				if (startTime - date.getTime() > 0) {
					return true;
				}
			}
			list = crmCompanySvrService.querySvrHistory(cid,
					CrmCompanySvrService.BAIDU_CODE);
			if (list != null && list.size() > 0) {
				CrmCompanySvr crmCompanySvr = list.get(0);
				long startTime = crmCompanySvr.getGmtStart().getTime();
				if (startTime - date.getTime() > 0) {
					return true;
				}
			}

		} while (false);
		return false;
	}

	private void setCDNTime(HttpServletResponse response) {
		// 设置cdn过期时间
		PageCacheUtil.setCDNCache(response, 60 * 30);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView silianXML(HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> out,String fileName,Integer cid) throws IOException {
		
		cid = initCompanyId(out, request.getServerName(), cid);

		if (cid == null) {
			return bad404request(response);
		}
		
		do {
			//如果mongoDB中存在cid和filename.xml,则显示，否则返回404
			// 门市部死链Xml
			DBObject dBObject = MongoDBUtils.getInstance().find("esite_xml",cid,fileName+".xml");
			if(dBObject != null && StringUtils.isNotEmpty(dBObject.get("domain_zz91").toString()) 
					){
				response.setContentType("application/xml");
				response.setCharacterEncoding("UTF-8"); 
				ServletOutputStream rs = response.getOutputStream();
				StringBuffer buff = new StringBuffer();
				buff.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><urlset>");
				List<String> l = (List<String>) dBObject.get("urlList");
				for (String string : l) {
					buff.append("<url><loc>"+string+"</loc></url>");
				}
				buff.append("</urlset>");
				rs.println(buff.toString());
			}else{
				return bad404request(response);
			}
		} while (false);
		return null;
	}
	
}