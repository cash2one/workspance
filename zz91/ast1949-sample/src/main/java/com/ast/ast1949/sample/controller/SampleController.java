/**
 * @author kongsj
 * @date 2014年5月27日
 * 
 */
package com.ast.ast1949.sample.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.sample.OrderBill;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.paychannel.ChannelConst;
import com.ast.ast1949.paychannel.OrderDto;
import com.ast.ast1949.paychannel.ReturnState;
import com.ast.ast1949.paychannel.exception.BizException;
import com.ast.ast1949.sample.paychannel.alipay.util.AlipayNotify;
import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.phone.PhoneClickLogService;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.sample.AccountService;
import com.ast.ast1949.service.sample.AddressService;
import com.ast.ast1949.service.sample.ContactClickLogService;
import com.ast.ast1949.service.sample.CustominfoService;
import com.ast.ast1949.service.sample.OrderBillService;
import com.ast.ast1949.service.sample.RefundService;
import com.ast.ast1949.service.sample.SampleRelateProductService;
import com.ast.ast1949.service.sample.SampleService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class SampleController extends BaseController {
	
//	private static Logger log = Logger.getLogger(MngrController.class);

	@Resource
	private SampleService sampleService;
	@Resource
	private ProductsService productsService;
	@Resource
	private SampleRelateProductService sampleRelateProductService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CompanyService companyService;

	@Resource
	private AccountService accountService;

	@Resource
	private OrderBillService orderBillService;

	@Resource
	private AddressService addressService;

	@Resource
	private ParamService paramService;

	@Resource
	private RefundService refundService;

	@Resource
	private CustominfoService custominfoService;

	@Resource
	private CompanyAccountService companyAccountService;

	@Resource
	private ProductAddPropertiesService productAddPropertiesService;
	
	@Resource
	private PhoneService phoneService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	
	@Resource
	private PhoneLogService phoneLogService; 
	@Resource
	private PhoneClickLogService phoneClickLogService;

	@Resource
	private PhoneCostSvrService phoneCostSvrService;
	
	@Resource
	private ContactClickLogService  contactClickLogService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out) {

		// 服务热线
		out.put("serviceTel", ParamUtils.getInstance().getValue("site_info_front", "zst_phone"));

		// 正在求购样品
		PageDto<ProductsDto> buyPage = new PageDto<ProductsDto>();
		ProductsDO buyPro = new ProductsDO();
		buyPro.setIsYP(true);
		buyPro.setProductsTypeCode("10331001");
		buyPage.setPageSize(6);
		buyPage = productsService.pageProductsBySearchEngine(buyPro, null, null, buyPage);
		out.put("buyList", buyPage.getRecords());

		// 今日拿样品的人数 和 今日新增 样品数
		out.put("todayCount", sampleRelateProductService.countTodayAdd());
		String from=DateUtil.toString(new Date(), "yyyy-MM-dd");
		String to=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd");
		Integer nyCount=orderBillService.countBuyerIdByTime(from, to);
		if(nyCount!=null){
			out.put("nyCount", nyCount);
		}else{
			out.put("nyCount", 0);
		}
		// 最新样品供应商
		PageDto<ProductsDto> sellPage = new PageDto<ProductsDto>();
		ProductsDO sellPro = new ProductsDO();
		sellPro.setIsYP(true);
		sellPro.setProductsTypeCode("10331000");
		sellPage.setPageSize(8);
		sellPage = productsService.pageLHProductsBySearchEngine(sellPro, null, null, sellPage);
		List<ProductsDto> list = sellPage.getRecords();
		for (ProductsDto dto : list) {
			dto.setTotalSampleAmount(sampleService.countAmountByCompanyId(dto.getProducts().getCompanyId()));
		}
		out.put("sellList", list);

		// 所有样品
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		ProductsDO allPro = new ProductsDO();
		allPro.setIsYP(true);
		allPro.setProductsTypeCode("10331000");
		page.setPageSize(20);
		page = productsService.pageProductsBySearchEngine(allPro, "shouye", null, page);
		out.put("allList", page.getRecords());
		
		//来电宝会员样品推荐
		PageDto<ProductsDto> ldbPage = new PageDto<ProductsDto>();
		ProductsDO pdo = new ProductsDO();
		pdo.setIsYP(true);
		pdo.setProductsTypeCode("10331000");
		ldbPage.setPageSize(20);
		ldbPage = productsService.pagePPCProductsBySearchEngine(pdo, null, ldbPage);
		Map<Integer, String> maps = new HashMap<Integer, String>();
		Phone p = null;
		List<ProductsDto> ldbList = ldbPage.getRecords();
		// 不为 4的倍数 补充
		if(ldbList.size()%4!=0){
			int l = ldbList.size()%4;
			for (int i = 0; i < l; i++) {
				ldbList.add(ldbList.get(i));
			}
		}
		for (ProductsDto ob : ldbList) {
			p = phoneService.queryByCompanyId(ob.getCompany().getId());
			if (p != null && StringUtils.isNotEmpty(p.getFrontTel())){
				maps.put(ob.getCompany().getId(), p.getFrontTel()); // 来电宝400号码
			}
		}
		out.put("maps", maps);
		out.put("ldbList", ldbPage.getRecords());
		
		
		SeoUtil.getInstance().buildSeo(out);
		return new ModelAndView();
	}

	final static Map<String, String> CATEGORY_MAP = new HashMap<String, String>();
	static {
		CATEGORY_MAP.put("ppkl", "PP再生颗粒");
		CATEGORY_MAP.put("pekl", "PE再生颗粒");
		CATEGORY_MAP.put("pvckl", "PVC再生颗粒");
		CATEGORY_MAP.put("abskl", "ABS再生颗粒");
		CATEGORY_MAP.put("pskl", "PS再生颗粒");
		CATEGORY_MAP.put("evakl", "EVA再生颗粒");
		CATEGORY_MAP.put("pakl", "PA再生颗粒");
		CATEGORY_MAP.put("petkl", "PET再生颗粒");
		CATEGORY_MAP.put("ppokl", "PPO再生颗粒");
		CATEGORY_MAP.put("pckl", "PC再生颗粒");
		CATEGORY_MAP.put("qtkl", "其他再生颗粒");
	}

	final static Map<String, String> LEVEL_MAP = new HashMap<String, String>();
	static {
		LEVEL_MAP.put("yiji", "一级");
		LEVEL_MAP.put("erji", "二级");
		LEVEL_MAP.put("sanji", "三级");
		LEVEL_MAP.put("teji", "特级");
		LEVEL_MAP.put("qita", "其他");
	}

	final static Map<String, String> COLOR_MAP = new HashMap<String, String>();
	static {
		COLOR_MAP.put("hongse", "红色");
		COLOR_MAP.put("huangse", "黄色");
		COLOR_MAP.put("lvse", "绿色");
		COLOR_MAP.put("lanse", "蓝色");
		COLOR_MAP.put("zise", "紫色");
		COLOR_MAP.put("heise", "黑色");
		COLOR_MAP.put("baise", "白色");
		COLOR_MAP.put("huibaise", "灰白色");
		COLOR_MAP.put("huise", "灰色");
		COLOR_MAP.put("bense", "本色");
		COLOR_MAP.put("touming", "透明");
		COLOR_MAP.put("zase", "杂色");
		COLOR_MAP.put("qita", "其他");
	}

	final static Map<String, String> AREA_MAP = new HashMap<String, String>();
	static {
		AREA_MAP.put("zhejiang", "浙江");
		AREA_MAP.put("guangdong", "广东");
		AREA_MAP.put("jiangsu", "江苏");
		AREA_MAP.put("shandong", "山东");
		AREA_MAP.put("hebei", "河北");
		AREA_MAP.put("henan", "河南");
		AREA_MAP.put("fujian", "福建");
		AREA_MAP.put("anhui", "安徽");
		AREA_MAP.put("shanghai", "上海");
		AREA_MAP.put("chongqing", "重庆");
		AREA_MAP.put("hubei", "湖北");
		AREA_MAP.put("hunan", "湖南");
		AREA_MAP.put("liaoning", "辽宁");
		AREA_MAP.put("jiangxi", "江西");
		AREA_MAP.put("sichuan", "四川");
		AREA_MAP.put("tianjin", "天津");
		AREA_MAP.put("shanxi", "山西");
		AREA_MAP.put("neimenggu", "内蒙古");
		AREA_MAP.put("jilin", "吉林");
		AREA_MAP.put("heilongjiang", "黑龙江");
		AREA_MAP.put("guangxi", "广西");
		AREA_MAP.put("hainan", "海南");
		AREA_MAP.put("guizhou", "贵州");
		AREA_MAP.put("yunnan", "云南");
		AREA_MAP.put("xizang", "西藏");
		AREA_MAP.put("gansu", "甘肃");
		AREA_MAP.put("qinghai", "青海");
		AREA_MAP.put("ningxia", "宁夏");
		AREA_MAP.put("xinjiang", "新疆");
		AREA_MAP.put("taiwan", "台湾");
		AREA_MAP.put("xianggang", "香港");
		AREA_MAP.put("aomen", "澳门");
	}

	@RequestMapping
	public ModelAndView nyzx(Map<String, Object> out, String color, String level, String title, String area, String date, String baoyou,
			String xiaoliang, String nayang, String type, PageDto<ProductsDto> page) throws UnsupportedEncodingException {

		String keywords = "";
		ProductsDO allPro = new ProductsDO();
		allPro.setProductsTypeCode("10331000");
		page.setPageSize(20);

		String[] seoTitle = new String[] { "", "", "" };
		String[] seoKeywords = new String[] { "", "", "" };
		String[] seoDescription = new String[] { "" };
		String seoTemplate = "nyzx";

		// 服务热线
		out.put("serviceTel", ParamUtils.getInstance().getValue("site_info_front", "zst_phone"));
		// 转码
		if (StringUtils.isNotEmpty(level)) {
			keywords = keywords + "&";
			String[] levelArray = level.split(",");
			Set<String> chooseLevel = new HashSet<String>();
			String tempKey = "";
			for (String levelCode : levelArray) {
				if (StringUtils.isNotEmpty(LEVEL_MAP.get(levelCode))) {
					tempKey += "|" + "(" + LEVEL_MAP.get(levelCode) + ")";
					chooseLevel.add(LEVEL_MAP.get(levelCode));
					if (StringUtils.isNotEmpty(seoTitle[1])) {
						seoTitle[1] = seoTitle[1] + "," + LEVEL_MAP.get(levelCode);
						seoKeywords[1] = seoKeywords[1] + "," + LEVEL_MAP.get(levelCode);
					} else {
						seoTitle[1] = LEVEL_MAP.get(levelCode);
						seoKeywords[1] = LEVEL_MAP.get(levelCode);
					}
				}
			}
			keywords = keywords + "(" + tempKey + ")";
			seoTemplate = "list";
			out.put("levelStr", level);
			out.put("levelCN", chooseLevel);
			out.put("levelUrl", "l-" + level);
		}
		if (StringUtils.isNotEmpty(color)) {
			keywords = keywords + "&";
			String[] colorArray = color.split(",");
			Set<String> chooseColor = new HashSet<String>();
			String tempKey = "";
			for (String colorCode : colorArray) {
				if (StringUtils.isNotEmpty(COLOR_MAP.get(colorCode))) {
					tempKey += "|" + "(" + COLOR_MAP.get(colorCode) + ")";
					chooseColor.add(COLOR_MAP.get(colorCode));
					if (StringUtils.isNotEmpty(seoTitle[0])) {
						seoTitle[0] = seoTitle[0] + "," + COLOR_MAP.get(colorCode);
						seoKeywords[0] = seoKeywords[0] + "," + COLOR_MAP.get(colorCode);
					} else {
						seoTitle[0] = COLOR_MAP.get(colorCode);
						seoKeywords[0] = COLOR_MAP.get(colorCode);
					}
				}
				;
			}
			keywords = keywords + "(" + tempKey + ")";
			seoTemplate = "list";
			out.put("colorStr", color);
			out.put("colorCN", chooseColor);
			out.put("colorUrl", "c-" + color);
		}

		// 地区
		if (StringUtils.isNotEmpty(area)) {
			out.put("allFlag", "1");
			String areaStr = CategoryFacade.PINYIN_MAP.get(area);
			if (StringUtils.isEmpty(areaStr)) {
				return new ModelAndView("redirect:index.htm");
			}
			keywords += "&(" + CategoryFacade.getInstance().getValue(areaStr) + ")";
			out.put("area", area);
		}
		// 时间
		if (StringUtils.isNotEmpty(date) && StringUtils.isNumber(date)) {
			out.put("allFlag", "1");
			out.put("dateStr", date);
			allPro.setSearchRangeRefresh(Integer.valueOf(date));
		}
		// 包邮
		if (StringUtils.isNotEmpty(baoyou)) {
			out.put("allFlag", "1");
			out.put("baoyou", baoyou);
			allPro.setIsBaoyou(true);
		}
		// 销量
		if (StringUtils.isNotEmpty(xiaoliang)) {
			out.put("allFlag", "1");
			out.put("xiaoliang", xiaoliang);
			if (xiaoliang.endsWith("1")) {
				page.setSearchOrderByAndDir("amount desc");
			} else {
				page.setSearchOrderByAndDir("amount asc");
			}
		}
		// 拿样价
		if (StringUtils.isNotEmpty(nayang)) {
			out.put("allFlag", "1");
			out.put("nayang", nayang);
			if (StringUtils.isNotEmpty(page.getSearchOrderByAndDir())) {
				page.setSearchOrderByAndDir(page.getSearchOrderByAndDir() + ",");
			} else {
				page.setSearchOrderByAndDir("");
			}
			if (nayang.endsWith("1")) {
				page.setSearchOrderByAndDir(page.getSearchOrderByAndDir() + "take_price desc");
			} else {
				page.setSearchOrderByAndDir(page.getSearchOrderByAndDir() + "take_price asc");
			}
		}

		if (StringUtils.isNotEmpty(type)) {
			out.put("type", type);
			// 主类别
			if (StringUtils.isNotEmpty(title) && type.equals("m")) {
				keywords += "&(" + CATEGORY_MAP.get(title) + ")";
				out.put("codeStr", title);
				out.put("mainLabel", CATEGORY_MAP.get(title));
				seoTitle[2] = title.replace("kl", "");
				seoKeywords[2] = CATEGORY_MAP.get(title);
				seoDescription[0] = title.replace("kl", "");
				seoTemplate = "list";
			}

			// 关键字
			if (StringUtils.isNotEmpty(title) && !StringUtils.isContainCNChar(title)) {
				title = StringUtils.decryptUrlParameter(title);
			}
			if (StringUtils.isNotEmpty(title) && type.equals("k")) {
				keywords += "&(" + title + ")";
				out.put("titleStr", title);
				out.put("mainLabel", title);
				seoTitle[2] = title;
				seoKeywords[0] = title;
				seoDescription[0] = title;
				seoTemplate = "search";
			}

		}

		if (StringUtils.isNotEmpty(keywords)) {
			if (keywords.startsWith("|")) {
				keywords = keywords.substring(1, keywords.length());
			}
			if (keywords.startsWith("&")) {
				keywords = keywords.substring(1, keywords.length());
			}

			keywords = keywords.replace("(|", "(");
			allPro.setTitle(keywords);
		}

		// 组装列表页url
		buildUrl(out);

		// 所有样品
		allPro.setIsYP(true);
		page.setPageSize(20);
		page = productsService.pageProductsBySearchEngine(allPro, null, null, page);
		out.put("page", page);

		// seo
		SeoUtil.getInstance().buildSeo(seoTemplate, seoTitle, seoKeywords, seoDescription, out);

		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView ny_details(HttpServletRequest request, Map<String, Object> out, String id,PageDto<OrderBill> orderPage) throws UnsupportedEncodingException {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId = null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}
		out.put("sessionUser", sessionUser);
		// 资讯状态
		if (StringUtils.isEmpty(id) || !StringUtils.isNumber(id)) {
			return new ModelAndView("redirect:/index.htm");
		}
		Integer productId = Integer.valueOf(id);
		Sample sample = sampleService.queryByIdOrProductId(null, productId);
		if (sample==null) {
			return new ModelAndView("redirect:/index.htm");
		}
		if(sessionUser!=null){
			if (sample.getCompanyId().equals(companyId)) {
				out.put("cute", 0);
			} else {
				out.put("cute", 1);
			}
		}else{
			   out.put("cute", 2);
		}
		//申请过订单的人数
		Integer takeAmount=orderBillService.countBuyerIdBySampleId(sample.getId());
		if(takeAmount!=null){
			out.put("takeAmount", takeAmount);
		}
		else{
			out.put("takeAmount", 0);
		}
		// 样品信息
		ProductsDto dto = productsService.queryProductsDetailsById(productId);
		if (sample == null || sample.getIsDel() == 1 || dto == null) {
			return new ModelAndView("redirect:/index.htm");
		}
		// 求购跳转至交易中心最终页面
		if (!ProductsService.PRODUCTS_TYPE_OFFER.equals(dto.getProducts().getProductsTypeCode())) {
			return new ModelAndView("redirect:" + AddressTool.getAddress("trade") + "/productdetails" + dto.getProducts().getId() + ".htm");
		}
		Integer accSum=orderBillService.sumSampleBySampleId(sample.getId());
		if(sample.getAmount()!=null){
			if(accSum==null){
				accSum=sample.getAmount();
			}else{
				if(sample.getAmount()>0){
				accSum=accSum+sample.getAmount();
				}else{
					accSum=accSum-sample.getAmount();
				}
			}
		}else{
			sample.setAmount(0);
			accSum=0;
		}
		out.put("accSum", accSum);
		dto.setSample(sample);
		out.put("dto", dto);
		// 供求图片
		List<ProductsPicDO> picList = productsPicService.queryProductPicInfoByProductsIdForFront(productId);
		out.put("picList", picList);

		// 主类别
		String key = "";
		if (dto.getProducts() != null && StringUtils.isNotEmpty(dto.getProducts().getCategoryProductsMainCode())) {
			String mainLabel = CategoryProductsFacade.getInstance().getValue(dto.getProducts().getCategoryProductsMainCode());
			out.put("mainLabel", mainLabel);
			out.put("mainEncode", URLEncoder.encode(mainLabel, HttpUtils.CHARSET_UTF8));
			key = mainLabel;
		}
		// 发货地址
		String areaCode = StringUtils.isNotEmpty(sample.getAreaCode()) ? sample.getAreaCode() : "";
		if (areaCode.length() == 12) {
			out.put("sampleAreaLabel", CategoryFacade.getInstance().getValue(areaCode));
		}
		if (areaCode.length() == 16) {
			out.put("sampleAreaLabel", CategoryFacade.getInstance().getValue(areaCode.substring(0, 12)) + "  "
					+ CategoryFacade.getInstance().getValue(areaCode));
		}
		// 所在地区
		Company company = companyService.queryCompanyById(dto.getProducts().getCompanyId());
		dto.setCompany(company);
		areaCode = (company != null && StringUtils.isNotEmpty(company.getAreaCode())) ? company.getAreaCode() : "";
		if (areaCode.length() == 12) {
			out.put("companyAreaLabel", CategoryFacade.getInstance().getValue(areaCode));
		}
		if (areaCode.length() >= 16) {
			out.put("companyAreaLabel", CategoryFacade.getInstance().getValue(areaCode.substring(0, 12)) + "  "
					+ CategoryFacade.getInstance().getValue(areaCode.substring(0, 16)));
		}

		// 服务热线
		out.put("serviceTel", ParamUtils.getInstance().getValue("site_info_front", "zst_phone"));
		// 400号码
		Phone p = phoneService.queryByCompanyId(sample.getCompanyId());
		if (p!=null && StringUtils.isNotEmpty(p.getFrontTel())&&0 ==p.getExpireFlag()) {
			out.put("tel400", p.getFrontTel());
		}

		// 相关样品
		PageDto<ProductsDto> relatePage = new PageDto<ProductsDto>();
		relatePage.setPageSize(4);
		ProductsDO product = new ProductsDO();
		product.setTitle(key);
		product.setIsYP(true);
		product.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_OFFER);
		out.put("relateList", productsService.pageProductsBySearchEngine(product, null, null, relatePage).getRecords());

		// 总共样品 总共供求 总共询盘
		sampleService.countSampleInfo(out, company.getId());

		// 同公司的其他样品
		out.put("ownList", sampleService.queryListByCompanyId(company.getId(), productId));

		// 属性
		setProperties(dto, out);
		
		// 拿样记录
		orderPage.setPageSize(10);
		orderPage.setSort("id");
		orderPage.setDir("desc");
		orderPage = orderBillService.queryListBySampleId(null, null, orderPage, null, null, sample.getId(),"1");
		out.put("orderPage", orderPage);

		// seo
		SeoUtil.getInstance().buildSeo("detail", new String[] { dto.getProducts().getTitle() },
				new String[] { dto.getProducts().getTitle() }, new String[] { dto.getProducts().getTitle() }, out);

		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView order(HttpServletRequest request, Map<String, Object> out, PageDto<ProductsDto> page, ProductsDO product,
			String date, String area,String topStatus) throws UnsupportedEncodingException {

		if (StringUtils.isNotEmpty(topStatus)) {
			out.put("topStatus", topStatus);
		}
		
		// 服务热线
		out.put("serviceTel", ParamUtils.getInstance().getValue("site_info_front", "zst_phone"));

		// 左上角求购滚动
		PageDto<ProductsDto> buyPage = new PageDto<ProductsDto>();
		buyPage.setPageSize(20);
		ProductsDO buyPro = new ProductsDO();
		buyPro.setIsYP(true);
		buyPro.setProductsTypeCode("10331001");
		List<ProductsDto> buyList = productsService.pageProductsBySearchEngine(buyPro, null, true, buyPage).getRecords();
		Map<Integer, List<ProductsDto>> buyMap = new HashMap<Integer, List<ProductsDto>>();
		List<ProductsDto> tempList = new ArrayList<ProductsDto>();
		buyMap.put(1, tempList);
		int i = 1;
		int j = 1;
		for (ProductsDto dto : buyList) {
			tempList.add(dto);
			if (j % 4 == 0) {
				i++;
				tempList = new ArrayList<ProductsDto>();
				buyMap.put(i, tempList);
			}
			j++;
		}
		out.put("buyMap", buyMap);

		String url = "buy/";

		String keywords = "";
		// 关键字
		if (StringUtils.isNotEmpty(product.getTitle())) {
			String title = product.getTitle();
			if (!StringUtils.isContainCNChar(title)) {
				title = StringUtils.decryptUrlParameter(title);
			}
			out.put("title", product.getTitle());
			keywords += "&(" + product.getTitle() + ")";
			out.put("titleUrl", URLEncoder.encode(product.getTitle(), HttpUtils.CHARSET_UTF8));
			url += "k-" + URLEncoder.encode(product.getTitle(), HttpUtils.CHARSET_UTF8) + "/";
			out.put("topStatus", 1);
		}
		if (StringUtils.isNotEmpty(area) || StringUtils.isNotEmpty(date)) {
			out.put("allFlag", "1");
			// 地区
			if (StringUtils.isNotEmpty(area)) {
				String code = CategoryFacade.PINYIN_MAP.get(area);
				keywords += "&(" + CategoryFacade.getInstance().getValue(code) + ")";
				out.put("area", area);
				url += "/c" + area;
			} else {
				url += "/c";
			}

			// 日期
			if (StringUtils.isNotEmpty(date)) {
				out.put("dateStr", date);
				url += "-d" + date;
				product.setSearchRangeRefresh(Integer.valueOf(date));
			} else {
				url += "-d";
			}
		}

		url = url.replace("/-", "/");
		url = url.replace("//", "/");
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		if (url.startsWith("/")) {
			url = url.substring(1, url.length());
		}
		if (url.equals("buy")) {
			url = "buy/order";
		}
		out.put("url", url);

		// 所有求购信息
		product.setIsYP(true);
		product.setProductsTypeCode("10331001");
		product.setTitle(keywords);
		page.setPageSize(20);
		out.put("page", productsService.pageProductsBySearchEngine(product, null, null, page));

		// seo
		SeoUtil.getInstance().buildSeo("order", out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView pubSample(HttpServletRequest request, ProductsDO product, Map<String, Object> out, String level) {
		String error = "";
		Sample sample = new Sample();
		do {

			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser == null) {
				error = "你还没有登陆";
				break;
			}

			if (StringUtils.isEmpty(product.getTitle())) {
				error = "标题没有没有填写";
				break;
			}

			if (StringUtils.isEmpty(product.getCategoryProductsMainCode())) {
				error = "类别没有选择";
				break;
			}

			if (StringUtils.isEmpty(product.getColor())) {
				error = "颜色没有填写";
				break;
			}
			if (StringUtils.isEmpty(product.getUseful())) {
				error = "用途没有填写";
				break;
			}
			if (StringUtils.isEmpty(product.getDetails())) {
				error = "描述没有填写";
				break;
			}

			if (StringUtils.isEmpty(level)) {
				error = "等级没有填写";
				break;
			}

			product.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_BUY);
			product.setCompanyId(ssoUser.getCompanyId());
			product.setAccount(ssoUser.getAccount());
			product.setCategoryProductsAssistCode("");
			product.setRefreshTime(new Date());
			product.setQuantityUnit("千克");
			// 信息来源 myrc
	        product.setSourceTypeCode("yang");
			Integer productId = productsService.publishProductsByCompany(product, ssoUser.getMembershipCode(), null);

			if (productId == null || productId < 1) {
				break;
			}
			// 级别属性
			ProductAddProperties productAddProperties = new ProductAddProperties();
			productAddProperties.setPid(productId);
			productAddProperties.setContent(level);
			productAddProperties.setProperty("级别");
			productAddProperties.setIsDel(false);
			productAddPropertiesService.addProperties(productAddProperties);
			// 样品添加
			sample.setCompanyId(ssoUser.getCompanyId());
			sampleService.createSample(sample, productId);
			return new ModelAndView("redirect:" + AddressTool.getAddress("myrc") + "/myproducts/office_post_suc.htm?productId=" + productId);
		} while (false);
		out.put("product", product);
		out.put("sample", sample);
		out.put("level", level);
		out.put("error", error);
		return new ModelAndView("redirect:/order.htm");
	}
	
	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request, Map<String, Object> out, String success, String data) {
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

	private void setProperties(ProductsDto dto, Map<String, Object> out) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 颜色
		if (StringUtils.isNotEmpty(dto.getProducts().getColor())) {
			map.put("颜色", dto.getProducts().getColor());
		}
		// 形态
		ProductAddProperties shape = productAddPropertiesService.queryByPidAndProperty(dto.getProducts().getId(), "形态");
		if (shape != null && StringUtils.isNotEmpty(shape.getContent())) {
			map.put("形态", shape.getContent());
		}

		// 级别
		ProductAddProperties level = productAddPropertiesService.queryByPidAndProperty(dto.getProducts().getId(), "级别");
		if (level != null && StringUtils.isNotEmpty(level.getContent())) {
			map.put("级别", level.getContent());
		}

		// 外观
		if (StringUtils.isNotEmpty(dto.getProducts().getAppearance())) {
			map.put("外观", dto.getProducts().getAppearance());
		}
		// 货源地
		if (StringUtils.isNotEmpty(dto.getProducts().getSource())) {
			map.put("货源地", dto.getProducts().getSource());
		}
		// 来源产品：
		if (StringUtils.isNotEmpty(dto.getProducts().getOrigin())) {
			map.put("来源产品", dto.getProducts().getOrigin());
		}
		// 产品规格
		if (StringUtils.isNotEmpty(dto.getProducts().getSpecification())) {
			map.put("产品规格", dto.getProducts().getSpecification());
		}
		// 此废料可用于
		if (StringUtils.isNotEmpty(dto.getProducts().getUseful())) {
			map.put("此废料可用于", dto.getProducts().getUseful());
		}
		// 杂质（杂物）含量： （请写明具体杂质及其含量，如：含镍3%）
		if (StringUtils.isNotEmpty(dto.getProducts().getImpurity())) {
			map.put("杂质（杂物）含量", dto.getProducts().getImpurity());
		}
		// 加工说明
		if (StringUtils.isNotEmpty(dto.getProducts().getManufacture())) {
			map.put("加工说明", CategoryFacade.getInstance().getValue(dto.getProducts().getManufacture()));
		}

		out.put("properties", map);
	}

	private void buildUrl(Map<String, Object> out) {
		String url = "";

		if (out.get("type") != null) {
			url = out.get("type").toString() + "-";
			if (out.get("codeStr") != null) {
				out.put("codeUrl", url + out.get("codeStr").toString());
				url += out.get("codeStr").toString() + "/";
			} else if (out.get("titleStr") != null) {
				out.put("codeUrl", url + out.get("titleStr").toString());
				url += out.get("titleStr").toString() + "/";
			} else {
				url = "";
			}
		}
		if (out.get("levelUrl") != null) {
			url = url + "/" + out.get("levelUrl").toString();
		}
		if (out.get("colorUrl") != null) {
			url = url + "-" + out.get("colorUrl").toString();
		}

		if (out.get("allFlag") != null && out.get("allFlag").toString().equals("1")) {
			if (out.get("area") != null) {
				url = url + "/c" + out.get("area").toString();
			} else {
				url = url + "/c";
			}
			if (out.get("dateStr") != null) {
				url = url + "-d" + out.get("dateStr").toString();
			} else {
				url = url + "-d";
			}
			if (out.get("baoyou") != null) {
				url = url + "-b" + out.get("baoyou").toString();
			} else {
				url = url + "-b";
			}
			if (out.get("xiaoliang") != null) {
				url = url + "-x" + out.get("xiaoliang").toString();
			} else {
				url = url + "-x";
			}
			if (out.get("nayang") != null) {
				url = url + "-n" + out.get("nayang").toString();
			} else {
				url = url + "-n";
			}
		}

		url = url.replace("/-", "/");
		url = url.replace("//", "/");
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		if (url.startsWith("/")) {
			url = url.substring(1, url.length());
		}
		if (url.startsWith("-")) {
			url = url.substring(1, url.length());
		}
		if (StringUtils.isEmpty(url)) {
			if (out.get("titleStr") != null) {
				url = "search";
			}
			url = "nyzx";
		}
		out.put("url", url);
	}

	
	/**
	 * 交易完成后 处理回调
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView alipayNotifyUrl(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out) {
//		log.info("===========alipayNotifyUrl=====start===========");

//		OrderDto dto = this.putChannelparam(null, ChannelConst.CHANNEL_ALIPAY);
		String msg = "";

		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
//			log.info("===========" + name + "=" + valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = "";
		String trade_no = "";
		String trade_status = ""; // 交易状态
		String total_fee = "";
		String seller_email = "";
		String buyer_email = "";
		try {
			out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 交易金额
			total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

			// 卖方支付宝账户
			seller_email = new String(request.getParameter("seller_email").getBytes("ISO-8859-1"), "UTF-8");
			// 买方支付宝账户
			buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if (AlipayNotify.verify(params)) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				ReturnState rs = new ReturnState();
				rs.setOrderSeq(out_trade_no);
				rs.setChannelRecvSn(trade_no);
				rs.setChannelType(ChannelConst.CHANNEL_ALIPAY);
				rs.setRelTranAmount(Double.parseDouble(total_fee));
				rs.setChannelRetCode("0000");

				rs.setBuyerAcc(buyer_email);
				rs.setSellerAcc(seller_email);

				try {
					orderBillService.dealCallback(rs);
				} catch (BizException e) {
					msg = e.getMessage();
//					log.info(msg);
					e.printStackTrace();
				}

			}

			if (trade_status.equals("TRADE_FINISHED")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				// 注意：
				// 该种交易状态只在两种情况下出现
				// 1、开通了普通即时到账，买家付款成功后。
				// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				// 注意：
				// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			}

			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			msg = "success" + " " + msg;
			// out.println("success"); //请不要修改或删除
			try {
				response.getWriter().print("success");
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {// 验证失败
			msg = "fail";
			try {
				response.getWriter().print("fail");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

//		log.info(msg);
//		log.info("===========alipayNotifyUrl=====end===========");

		return null;
	}
	
	/**
	 * 
	 * @param orderDto
	 * @param channelType
	 * @return
	 */
	public OrderDto putChannelparam(OrderDto dto, String channelType) {
		if (dto == null)
			dto = new OrderDto();

		if (ChannelConst.CHANNEL_TENPAY.equals(channelType)) {
			Map<String, String> paramMap = this.queryParamsMapByTypes("tenpay");
			// 接受回执URL
			String notifyUrl = paramMap.get("notifyUrl");
			// 返回跳转URL
			String returnUrl = paramMap.get("returnUrl");
			// 收款方
			String spname = paramMap.get("spname");
			// 商户号
			String partner = paramMap.get("partner");
			// 密钥
			String key = paramMap.get("key");

			String astoAccount = paramMap.get("astoAccount");

			dto.setNotifyUrl(notifyUrl);
			dto.setReturnUrl(returnUrl);
			dto.setSpname(spname);
			dto.setPartner(partner);
			dto.setKey(key);
			dto.setAstoAccount(astoAccount);
		} else if (ChannelConst.CHANNEL_ALIPAY.equals(channelType)) {
			Map<String, String> paramMap = this.queryParamsMapByTypes("alipay");
			// 接受回执URL
			String notifyUrl = paramMap.get("notifyUrl");
			if (notifyUrl == null || notifyUrl.trim().equals("")) {
				notifyUrl = "http://test_apps.zz91.com/sample/alipayNotifyUrl.htm";
			}

			// 返回跳转URL
			String returnUrl = paramMap.get("returnUrl");
			if (returnUrl == null || returnUrl.trim().equals("")) {
				returnUrl = "http://test_apps.zz91.com/sample/manager/alipayReturnUrl.htm";
			}

			// 合作身份者ID，以2088开头由16位纯数字组成的字符串
			String partner = paramMap.get("partner");
			// 商户的私钥
			String key = paramMap.get("key");

			String astoAccount = paramMap.get("astoAccount");
			if (astoAccount == null || astoAccount.trim().equals("")) {
				astoAccount = "zhifu@asto-inc.com";
			}

			dto.setNotifyUrl(notifyUrl);
			dto.setReturnUrl(returnUrl);
			dto.setPartner(partner);
			dto.setKey(key);
			dto.setAstoAccount(astoAccount);
		}
		return dto;
	}
	
	/**
	 * 
	 * @param types
	 * @return
	 */
	public Map<String, String> queryParamsMapByTypes(final String types) {
		final List<Param> retList = paramService.listParamByTypes(types);
		final Map<String, String> sampleParam = new HashMap<String, String>(retList.size());
		for (final Param param : retList) {
			sampleParam.put(param.getKey(), param.getValue());
		}
		return sampleParam;
	}
	
	@RequestMapping
	public ModelAndView yiyuan(Map<String, Object>out,PageDto<Sample> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("takePrice", 1);
		map.put("checkStatus", 0);
		map.put("productsTypeCode", ProductsService.PRODUCTS_TYPE_OFFER);
		map.put("productStatus", 1);
		page.setPageSize(20);
		page = sampleService.queryListByFilter(page, map);
		out.put("page", page);
		return new ModelAndView();
	}
	@RequestMapping
	public ModelAndView apply_dhny(Map<String, Object>out,HttpServletRequest request,String sampleId){
		SsoUser ssoUser=getCachedUser(request);
		out.put("ssoUser", ssoUser);
		//cute 判断不能向自己拿样的标志 1：不能向自己拿样
		String cute="0";
		//vip  1：卖家家高会和来电宝客户标志的标志 
		String  vip="0";
		out.put("sampleId", sampleId);
		if(sampleId!=null){
				Integer id=Integer.valueOf(sampleId);
				Sample sample=sampleService.queryByIdOrProductId(id, null);
				if(sample!=null&&sample.getCompanyId()!=null){
					//得到联系人
					CompanyAccount companyAccount=companyAccountService.queryAdminAccountByCompanyId(sample.getCompanyId());
					if(companyAccount!=null){
						out.put("account", companyAccount);
					}
					Company company=companyService.queryCompanyById(sample.getCompanyId());
					if(company!=null){
					out.put("company", company);
					//判断不能想自己拿样 
					if(ssoUser!=null&&ssoUser.getCompanyId()!=null)
						if(ssoUser.getCompanyId().equals(company.getId())){
							cute="1";
						}else{
							cute="0";
						}
					}
				}
				
				do{
					//判断是不是卖家展示号码
					Boolean isShow=false;
					if(sample!=null&&sample.getCompanyId()!=null){
							if(crmCompanySvrService.validatePeriod(sample.getCompanyId(), CrmCompanySvrService.ZST_CODE)){
								isShow=true;
								
							}
							if(!isShow){
								if(crmCompanySvrService.validateLDB(sample.getCompanyId(),  CrmCompanySvrService.LDB_CODE, CrmCompanySvrService.LDB_FIVE_CODE)){
									isShow=true;
								}
							}
							if(isShow){
								out.put("isSee", isShow);
								vip="1";
							}
							if(ssoUser==null){
								break;
							}
							
					}
					//判断买家是不是有权限查看号码
					Boolean isView = false;
				    //JBZST_CODE 是减版再生通 可以免费看联系方式
					isView = crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.JBZST_CODE);
					if(!isView){
						//判断是否为一元或者五元来电宝
						isView =crmCompanySvrService.validateLDB(ssoUser.getCompanyId(), CrmCompanySvrService.LDB_CODE, CrmCompanySvrService.LDB_FIVE_CODE);
						if(isView){
							Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
							String balance = phoneLogService.countBalance(phone);
							PhoneCostSvr phoneCostSvr = phoneCostSvrService.queryByCompanyId(ssoUser.getCompanyId());
							if(phoneCostSvr==null){
								break;
							}
							out.put("clickFee", phoneCostSvr.getClickFee());
							PhoneClickLog phoneClickLog = phoneClickLogService.queryById(ssoUser.getCompanyId(), sample.getCompanyId());
							if(phoneClickLog!=null){
								out.put("isSee", true);
							}else{
								   if(StringUtils.isNotEmpty(balance)&&Double.valueOf(balance)>0){
									out.put("ldbFlag", 1);
								   }else{
									out.put("ldbFlag", 0);
								    }
							}
						}
					}
				}while(false);
		}
		
		out.put("cute", cute);
		out.put("vip", vip);
		return null;
	}
	@RequestMapping
	public ModelAndView apply_zxny(HttpServletRequest request,Map<String, Object>out,String sampleId){
		SsoUser ssoUser=getCachedUser(request);
		out.put("ssoUser", ssoUser);
		//cute 判断不能向自己拿样的标志 1：不能向自己拿样
		String cute="0";
		out.put("sampleId", sampleId);
		if(sampleId!=null){
			Integer id=Integer.valueOf(sampleId);
			Sample sample=sampleService.queryByIdOrProductId(id, null);
			if(sample!=null&&sample.getCompanyId()!=null){
				
				Company company=companyService.queryCompanyById(sample.getCompanyId());
				if(company!=null){
					out.put("company", company);
					//判断不能想自己拿样 
					if(ssoUser!=null&&ssoUser.getCompanyId()!=null){
						if(ssoUser.getCompanyId().equals(company.getId())){
							cute="1";
						}else{
							cute="0";
						}
					}	
				}
			}
		}
		out.put("cute", cute);
		return null;
	}
	@RequestMapping
	public ModelAndView nyToClick(HttpServletRequest request,
			Integer targetId, Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		do {
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser == null || targetId == null) {
				break;
			}
			Integer companyId = ssoUser.getCompanyId();
			PhoneClickLog phoneClickLog = new PhoneClickLog();
			phoneClickLog.setCompanyId(companyId);
			phoneClickLog.setTargetId(targetId);
			// 获取服务
			PhoneCostSvr phoneCostSvr = phoneCostSvrService
					.queryByCompanyId(companyId);
			if (phoneCostSvr == null) {
				break;
			}
			// 0表示扣费失败 余额不足
			if (0 == phoneCostSvrService.reduceFee(phoneCostSvr.getId(),
					companyId, phoneCostSvr.getClickFee())) {
				break;
			}
			phoneClickLog.setClickFee(phoneCostSvr.getClickFee());
			phoneClickLogService.insert(phoneClickLog);
		} while (false);
		return printJson(result, out);
	}

	/**
	 * 将联系方式转成图片
	 * @param type
	 *            ：联系方式类型，null或空不获取信息
	 * @param 公司ID号
	 *            Key
	 * @return
	 */
	@RequestMapping
	public ModelAndView viewContactInfo(Map<String, Object> out, String type,
			String key, String color, HttpServletResponse response)
			throws IOException {

		if (StringUtils.isEmpty(key)){
			return null;
		}
		Integer id=Integer.valueOf(key);
		CompanyAccount account = companyAccountService
				.queryAdminAccountByCompanyId(id);
		Phone phone=phoneService.queryByCompanyId(id);
		String s = "";
		if(phone!=null&&phone.getTel()!=null){
			s=phone.getTel();
		}else {
			if(account!=null){
				if(account.getMobile()!=null){
					s=account.getMobile();
				}
				else if (account.getTel()!=null) {
					s=account.getTel();
				}
			}
		}
		

		if (StringUtils.isEmpty(s)) {
			return null;
		}

		int width = s.getBytes().length * 8 + 2;
		int height = 16;

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
		g2.drawString(s, 2, 13);
		ImageIO.write(bi, "jpg", response.getOutputStream());
		return null;
	}
	
}
