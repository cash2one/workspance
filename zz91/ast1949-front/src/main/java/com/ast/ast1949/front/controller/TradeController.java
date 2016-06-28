package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.front.util.FrontConst;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class TradeController extends BaseController {
//	@Resource
//	private TagsArticleService tagsArticleService;
//	@Resource
//	private CompanyService companyService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CategoryService categoryService;
//	@Resource
//	private PriceCategoryService priceCategoryService;
//	@Resource
//	private PriceService priceService;
//	@Resource
//	private CategoryProductsService categoryProductsService;
//	@Resource
//	private CompanyPriceService companyPriceService;
//	@Resource
//	private TagsInfoService tagsInfoService;
//	@Resource
//	private BbsService bbsService;
	@Resource
	private ProductsPicService productsPicService;
//	@Resource
//	private ProductsSearchAssociateKeywordsService productsSearchAssociateKeywords;
//	@Resource
//	private NewsService newsService;
//	@Resource
//	private ProductsRecommendService productsRecommendService;
//	@Resource
//	private ProductsKeywordsRankService productsKeywordsRankService;
//	@Resource
//	private CategoryCompanyPriceService categoryCompanyPriceService;
//	@Resource
//	private CompanyAccountService companyAccountService;
	
	

	private final static String DEFAULT_CITY_CODE = "10011000";
	public final static String DEFAULT_MEMBERSHIP_CODE = "10051000";
	private List<ExtTreeDto> listTree = new ArrayList<ExtTreeDto>();

	/**
	 * 交易中心首页
	 * @return 
	 */
	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
	public ModelAndView index(Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("trade")+"/index.htm");
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//		headDTO.setPageTitle("废金属_废塑料_废纸_纺织废料_废橡胶_再生资源_废料_${site_name}");
//		headDTO.setPageKeywords("废料,废料交易,废料供应,废料求购,废金属,废塑料,废纸,纺织废料,废橡胶,废料商机。");
//		headDTO.setPageDescription("${site_name}的再生资源交易中心是中国最大最旺的网上废料交易市场,"
//				+ "为您精选了废金属、废塑料、废纸、废橡胶、废旧二手物资、废旧二手设备的供应、求购、回收信息，"
//				+ "帮您快速寻找和复活废料商机，轻松达成交易。");
//		setSiteInfo(headDTO, out);
//		out.put("stringUtils", new StringUtils());
//		// 颗粒报价
//		String title = "颗粒";
//		List<CompanyPriceDO> companyPriceList = companyPriceService
//				.queryCompanyPriceByRefreshTime(title, 10);
//		out.put("companyPriceList", companyPriceList);
//		// 最新供求
//		List<ProductsDO> productsList = productsService.queryNewestProducts(null, null, 10);
//		out.put("productsList", productsList);
//		// 显示类别
//		List<CategoryProductsDTO> list = buildCategory(null);
//
//		out.put("categoryProductsList", list);
//		// 推荐厂商
//		List<Company> companyList = companyService.queryGoodCompany(20);
////				.queryCompanyNameByRegtime();
//		out.put("companyList", companyList);
//		// 热门标签
//		List<TagsInfoDO> tagsList = tagsInfoService
//				.queryTagsInfoByType("4", 30);
//		out.put("hotTags", tagsList);
//		// 商机聚集
//		out.put("newsList", newsService.queryNewsListForFront(222, null, null,
//				null, 6, null, null, true));
//		// 热门资讯
//		out.put("topnews", newsService.queryNewsListForFront(224, null, null,
//				null, 10, null, null, false));
	}

//	private List<CategoryProductsDTO> buildCategory(String parentCode) {
//		Map<String, String> map = CategoryProductsFacade.getInstance()
//				.getChild(parentCode);
//		if (map == null) {
//			return null;
//		}
//
//		List<CategoryProductsDTO> list = new ArrayList<CategoryProductsDTO>();
//		for (Entry<String, String> m : map.entrySet()) {
//			CategoryProductsDTO c = new CategoryProductsDTO();
//			c.setCategoryProductsDO(new CategoryProductsDO(null, m.getKey(), m
//					.getValue(), null, null, null, null));
//			c.setChild(buildCategory(m.getKey()));
//			list.add(c);
//		}
//
//		return list;
//	}

//	private Logger LOG = Logger.getLogger("com.zz91.index");
//
//	private void loginfo(String msg) {
//		StringBuilder sf = new StringBuilder();
//		sf.append(msg);
//		sf.append(DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
//		sf.append(" - ");
//		sf.append(System.currentTimeMillis());
//		LOG.debug(sf.toString());
//	}
	
	final static String URL_ENCODE = "utf-8";
	final static int SEARCH_EXPIRED = 20000;

	/**
	 * a:ptype
	 * b:province
	 * c:posttime
	 * d:priceflag
	 * e:nopiclist
	 * f:havepic
	 * p:page
	 */
	@RequestMapping
	public ModelAndView offerlist(String ptype,String province,
			String posttime, String priceflag, String nopiclist, 
			String havepic, String page, String keywords, String mainCode,
			HttpServletRequest request, Map<String, Object> out) throws Exception {
		return new ModelAndView("redirect:"+AddressTool.getAddress("trade")+"/index.htm");
//		out.put("ptype", ptype);  //1供应，2求购，其他全部
//		out.put("province", province); //
//		out.put("posttime", posttime); //整型，单位天
//		out.put("priceflag", priceflag); //1：必需有价格，其他：全部
//		out.put("nopiclist", nopiclist); //1：文字，其他：图文
//		out.put("havepic", havepic); //1：有图片，其他：全部
//		out.put("page", page);
//		out.put("mainCode", mainCode);
//
//		
////		out.put("keywordsEncode", keywords);
//		if(StringUtils.isNotEmpty(mainCode)){
//			keywords = CategoryProductsFacade.getInstance().getValue(mainCode); 
//		}else{
//			keywords = URLDecoder.decode(keywords, URL_ENCODE); 
//		}
//		out.put("keywords", keywords);
//		out.put("keywordsEncode", URLEncoder.encode(keywords,URL_ENCODE));
//		
//		do {
//			
//			//step1 获取标王信息（只ID）
//			List<Integer> keywordsList = null;
//			if(StringUtils.isEmpty(page) || Integer.valueOf(page).intValue()==1){
//				keywordsList = productsKeywordsRankService.queryProductsId(keywords,null,6);
//			}
//			StringBuffer sb=new StringBuffer();
//			String url = ParamUtils.getInstance().getValue("baseConfig", "search_api");
//			if(StringUtils.isNotEmpty(url)){
//				sb.append(url);
//			}else{
//				sb.append("http://python.zz91.com/prolist/?"); // 默认地址
//			}
//			sb.append("ptype=").append(StringUtils.getNotNullValue(ptype)).append("&");
//			sb.append("provincecode=").append(StringUtils.getNotNullValue(province)).append("&");
//			sb.append("province=").append(URLEncoder.encode(StringUtils.getNotNullValue(CategoryFacade.getInstance().getValue(province)),URL_ENCODE)).append("&");
//			sb.append("posttime=").append(StringUtils.getNotNullValue(posttime)).append("&");
//			sb.append("priceflag=").append(StringUtils.getNotNullValue(priceflag)).append("&");
//			sb.append("nopiclist=").append(StringUtils.getNotNullValue(nopiclist)).append("&");
//			sb.append("havepic=").append(StringUtils.getNotNullValue(havepic)).append("&");
//			sb.append("page=").append(StringUtils.getNotNullValue(page)).append("&");
//			sb.append("keywords=").append(URLEncoder.encode(keywords,URL_ENCODE));
//			
//			if(keywordsList!=null && keywordsList.size()>0){
//				String kl=keywordsList.toString();
//				sb.append("&pdtidlist=").append(kl.replace("[", "").replace("]", "").replace(" ", ""));
//			}
//
//			//step2 搜索供求信息
//			Document doc = Jsoup.parse(new URL(sb.toString()), SEARCH_EXPIRED);
//			String totalStr=doc.select("#totalrecords").val();
//			if(!StringUtils.isNumber(totalStr)){
//				totalStr = "0";
//			}
//			Integer total = Integer.valueOf(totalStr);
//			if(total==null || total.intValue()<=0){
//				break;
//			}
//			
//			out.put("totalRecords", total);
//			out.put("producsResult", doc.select("ul").toString().replace("&lt;", "<").replace("&gt;", ">"));
//			
//			//step3 获取相关类别 and 相关搜索
//			String code = "";
//			if (StringUtils.isNotEmpty(mainCode)) {
//				code = mainCode;
//			} else if (StringUtils.isNotEmpty(keywords)) {
//				CategoryProductsDO cp=categoryProductsService.queryCategoryProductsByLabel(keywords);
//				if(cp!=null){
//					code=cp.getCode();
//				}else{
//					code="";
//				}
//			}
//			
//			Map<String, String> relativeCategories = null;
//			if(code.length()>4){
//				relativeCategories=CategoryProductsFacade.getInstance().getChild(code.substring(0,code.length()-4));
//			}else{
//				relativeCategories=CategoryProductsFacade.getInstance().getChild(code);
//			}
//			out.put("relativeCategories", relativeCategories);
//			
//			List<ProductsSearchAssociateKeywordsDO> relativeSearch = productsSearchAssociateKeywords.queryByCategoryProductsCode(code);
//			
//			Map<String, String> relativeMap=new HashMap<String, String>();
//			for(ProductsSearchAssociateKeywordsDO k:relativeSearch){
//				relativeMap.put(k.getKeyword(), URLEncoder.encode(k.getKeyword(), URL_ENCODE));
//			}
//			out.put("relativeSearch", relativeSearch);
//			
//			//step4 获取相关标签
//			showTags(out, keywords);
//			//step5 获取相关企业报价
//			showCompanyPrice(out, keywords);
//			//step6 获取相关资讯
//			out.put("newsList", newsService.queryNewsByTitle(keywords, 3));
//			//step7 获取相关报价
//			out.put("priceList", priceService.queryPriceByTitleAndTypeId(null, keywords, "3"));
//			
//			//step8 分析导航
//			if (StringUtils.isNotEmpty(code)) {
//				// 分析类别导航
//				Map<String, String> navigationCategoryMap = new LinkedHashMap<String, String>();
//				for (int i = 4; i <= code.length(); i = i + 4) {
//					String pc = code.substring(0, i);
//					navigationCategoryMap.put(pc, CategoryProductsFacade
//							.getInstance().getValue(pc));
//				}
//				out.put("navigationCategoryMap", navigationCategoryMap);
//			}
//			
//			//step9 设置头部
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setPageTitle(keywords + "|" + keywords + "供求信息_" + keywords
//					+ "价格_交易中心_${site_name}");
//			headDTO.setPageKeywords(keywords + "," + keywords + "回收,求购" + keywords);
//			headDTO.setPageDescription("${site_name}" + keywords + "市场是中国最大的"
//					+ keywords + "网上贸易市场，我们为您提供" + keywords + "相关的供应信息、求购信息、价格行情等；");
//			setSiteInfo(headDTO, out);
//			return null;
//		} while (false);
//		
//		return new ModelAndView("/trade/offerNothing");
//		loginfo("开始处理搜索请求,准备搜索条件：");
//		 设置页面头部信息
//		 搜索条件
//		ProductsListItemForFrontDTO searchDto = new ProductsListItemForFrontDTO();
//		// 信息类别：求/供 1，2
//		buyOrSale = StringUtils.getNotNullValue(buyOrSale);
//		out.put("buyOrSale", buyOrSale);
//		if ("1".equals(buyOrSale)) {
//			searchDto.setBuyOrSale("求购");
//		} else if ("2".equals(buyOrSale)) {
//			searchDto.setBuyOrSale("供应");
//		}
//		// 关键字
//		if (StringUtils.isNotEmpty(keywords)) {
//			keywords = EscapeUnescape.unescape(keywords);
//		}
//		// 产品类别
//		if (StringUtils.isNotEmpty(productCateCode)) {
//			// 分析类别导航
//			Map<String, String> navigationCategoryMap = new LinkedHashMap<String, String>();
//			for (int i = 4; i <= productCateCode.length(); i = i + 4) {
//				String code = productCateCode.substring(0, i);
//				navigationCategoryMap.put(code, CategoryProductsFacade
//						.getInstance().getValue(code));
//			}
//			out.put("navigationCategoryMap", navigationCategoryMap);
//			out.put("productCateLabel", CategoryProductsFacade.getInstance()
//					.getValue(productCateCode));
//			if (StringUtils.isEmpty(keywords))
//				keywords = CategoryProductsFacade.getInstance().getValue(
//						productCateCode);
//		}
//		out.put("productCateCode", productCateCode);
//		searchDto.setCategoryProductsMainCode(productCateCode);
//		searchDto.setKeywords(keywords);
//		out.put("keywords", keywords);
//		// searchDto.setCategoryProductsAssistCode(assistCateCode);取消辅助条件
//		// 公司所在地
//		if (StringUtils.isNotEmpty(areaCode)) {
//			searchDto.setAreaCode(StringUtils.getNotNullValue(areaCode));
//		}
//		out.put("areaCode", areaCode);
//		// 时间期限
//		if (StringUtils.isNumber(postInDays)) {
//			searchDto.setLimitDays(Integer.valueOf(postInDays));
//		}
//		out.put("postInDays", postInDays);
//		searchDto.setHavePic(havePic);
//		out.put("havePic", havePic);
//		// out.put("isShowPice", isShowPrice);
//		// out.put("isShowPic", isShowPic);
//		// 相关咨询&相关报价
//		out.put("stringUtils", new StringUtils());
//		if (StringUtils.isNotEmpty(keywords)) {
//			List<NewsDO> newsList = newsService.queryNewsByTitle(keywords, 3);
//			out.put("newsList", newsList);
//			List<PriceDO> priceList = priceService.queryPriceByTitleAndTypeId(
//					null, keywords, "3");
//			out.put("priceList", priceList);
//		}
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setPageTitle(keywords + "|" + keywords + "供求信息_" + keywords
//				+ "价格_交易中心_${site_name}");
//		headDTO.setPageKeywords(keywords + "," + keywords + "回收,求购" + keywords);
//		headDTO.setPageDescription("${site_name}" + keywords + "市场是中国最大的"
//				+ keywords + "网上贸易市场，我们为您提供" + keywords + "相关的供应信息、求购信息、价格行情等；");
//		setSiteInfo(headDTO, out);
//		if (StringUtils.isEmpty(keywords)
//				&& StringUtils.isEmpty(productCateCode)) {
//			out.put(AstConst.JS_CODE, "alert('请选择类别或输入关键字！');history.back();");
//			return new ModelAndView("/js");
//		}
////		loginfo("根据搜索条件执行搜索：");
//		// 设置起始页码和每页条数
//		pager = productSearchService.search(searchDto, pager);
////		loginfo("搜索结束，开始查询标王信息：：");
//		out.put("results", pager.getRecords());
//		List<ProductsCompanyDTO> productsKeywordsRankList = new ArrayList<ProductsCompanyDTO>();
//		// 查询一条标王类型为金牌标王的供求信息
//		productsKeywordsRankList.addAll(productsKeywordsRankService
//				.queryProductsByKeywordsAndBuiedType(keywords, "10431000", 1));
//		// 查询2条标王类型为银牌标王的供求信息
//		productsKeywordsRankList.addAll(productsKeywordsRankService
//				.queryProductsByKeywordsAndBuiedType(keywords, "10431001", 2));
//		// 查询3条标王类型为铜牌标王的供求信息
//		productsKeywordsRankList.addAll(productsKeywordsRankService
//				.queryProductsByKeywordsAndBuiedType(keywords, "10431002", 3));
//		out.put("productsKeywordsRankList", productsKeywordsRankList);
//		
////		loginfo("查询标王信息结束：");
//		if (pager.getTotalRecords() > 0) {
//			// 相关类别
//			if (StringUtils.isNotEmpty(productCateCode)
//					&& productCateCode.length() > 4) {
////				loginfo("查询相关类别信息：");
//				String parentCode = productCateCode.substring(0,
//						productCateCode.length() - 4);
//				Map<String, String> relativeCategories = CategoryProductsFacade
//						.getInstance().getChild(parentCode);
//				out.put("relativeCategories", relativeCategories);
//			}
////			// 相关搜索##还未确定
//			if (StringUtils.isNotEmpty(productCateCode)
//					|| StringUtils.isNotEmpty(keywords)) {
////				loginfo("查询相关搜索信息：如果没有类别编码，查询相似关键字的类别编码，根据类别编码查询关联关键字。");
//				String code = "";
//				if (StringUtils.isNotEmpty(productCateCode)) {
//					code = productCateCode;
//				} else if (StringUtils.isNotEmpty(keywords)) {
//					CategoryProductsDO cp=categoryProductsService.queryCategoryProductsByLabel(keywords);
//					if(cp!=null)
//						code=cp.getCode();
//				}
//
//				List<ProductsSearchAssociateKeywordsDO> relativeSearch = productsSearchAssociateKeywords
//						.queryByCategoryProductsCode(code);
//				List<String[]> list = new ArrayList<String[]>();
//				for (ProductsSearchAssociateKeywordsDO search : relativeSearch) {
//					String[] arr = new String[] { search.getKeyword(),
//							EscapeUnescape.escape(search.getKeyword()) };
//					list.add(arr);
//				}
//				out.put("relativeSearch", list);
//			}
//			out.put("keywords", keywords);
//			out.put("totalRecords", pager.getTotalRecords());
//			out.put("pager", pager);
//			out.put("suffixUrl",
//					getSearchUrlByCondition(keywords, buyOrSale,
//							productCateCode, assistCateCode, areaCode,
//							postInDays, havePic));
//			showCompanyPrice(out, keywords);
//			showTags(out, keywords);
//			// headDTO.setTopNavIndex(NavConst.TRADE_CENTER); //
//			// 具体每个板块的对应值请参照common里的NavConst.java
//			// headDTO.setPageTitle(keywords
//			// + "_废料交易中心_废金属_废塑料_废纸_纺织废料_再生资源_废橡胶_${site_name}");
//			// headDTO.setPageKeywords(keywords
//			// + ",废料交易中心,废料交易,废料供求,求购废料,回收,废金属,废塑料,复活商机");
//			// headDTO.setPageDescription("${site_name}的废料交易中心是中国最大最旺的网上废料交易市场,"
//			// + "为您精选了废金属、 废塑料、废纸、废橡胶、废旧二手物资、废旧二手设备的供应、求购、回收等信息，"
//			// + "帮您快速寻找和复活废料商机， 轻松达成交易。");
//			// setSiteInfo(headDTO, out);// 定义在BaseController里
////			loginfo("搜索请求处理完毕。");
//			return null;
//		} else {
//			out.put("keywords", keywords);
//			out.put("buyOrSale", buyOrSale);
//			return new ModelAndView("/trade/offerNothing");
//		}

	}

//	private String getSearchUrlByCondition(String keywords, String buyOrSale,
//			String productCateCode, String assistCateCode, String areaCode,
//			String postInDays, String havePic) {
//		StringBuilder urlBuf = new StringBuilder();
//		// urlBuf.append("/trade/offerlist--");
//		urlBuf.append("--");
//		if (StringUtils.isNotEmpty(keywords))
//			urlBuf.append(EscapeUnescape.escape(keywords));
//		urlBuf.append("--bs");
//		if (StringUtils.isNotEmpty(buyOrSale))
//			urlBuf.append(buyOrSale);
//		urlBuf.append("--mc");
//		if (StringUtils.isNotEmpty(productCateCode))
//			urlBuf.append(productCateCode);
//		urlBuf.append("--ac");
//		if (StringUtils.isNotEmpty(assistCateCode))
//			urlBuf.append(assistCateCode);
//		urlBuf.append("--area");
//		if (StringUtils.isNotEmpty(areaCode))
//			urlBuf.append(areaCode);
//		urlBuf.append("--int");
//		if (StringUtils.isNumber(postInDays))
//			urlBuf.append(postInDays);
//		// urlBuf.append("--pri");
//		// if (StringUtils.isNumber(isShowPrice))
//		// urlBuf.append(isShowPrice);
//		// urlBuf.append("--pic");
//		// if (StringUtils.isNumber(isShowPic))
//		// urlBuf.append(isShowPic);
//		urlBuf.append("--havepic");
//		if (StringUtils.isNumber(havePic))
//			urlBuf.append(havePic);
//		// urlBuf.append("--p");
//		// if (StringUtils.isNumber(p))
//		// urlBuf.append(p);
//		// urlBuf.append(".htm");
//		return urlBuf.toString();
//	}

	/**
	 * 
	 * @param searchDTO
	 * @param out
	 * @param oldKeywords
	 */
//	private void showUrl(ProductsSearchForFrontDTO searchDTO,
//			Map<String, Object> out, String oldKeywords) {
//		String url = buildUrl(searchDTO, oldKeywords);
//		out.put("url", url);
//		String topNavUrl = buildTopNavUrl(searchDTO, oldKeywords);
//		out.put("topNavUrl", topNavUrl);
//		String noDateUrl = buildNoDateUrl(searchDTO, oldKeywords);
//		out.put("noDateUrl", noDateUrl);
//	}

	/**
	 * 
	 * @param searchDTO
	 * @param out
	 */
//	private void showRelative(ProductsSearchForFrontDTO searchDTO,
//			Map<String, Object> out) {
//		if (searchDTO.getMc() != null) {
//			String mainCode = searchDTO.getMc();
//			if (mainCode.length() > 4) {
//				String parentCode = mainCode
//						.substring(0, mainCode.length() - 4);
//				List<CategoryProductsDO> relativeCategories = categoryProductsService
//						.queryCategoryProductsByCode(parentCode, "0");
//				out.put("relativeCategories", relativeCategories);
//			}
//		}
//	}

	/**
	 * 
	 * @param searchDTO
	 * @param out
	 */
//	private void showRelativeSearch(ProductsSearchForFrontDTO searchDTO,
//			Map<String, Object> out) {
//		if (searchDTO.getAc() != null || searchDTO.getMc() != null) {
//			String code = searchDTO.getAc() != null ? searchDTO.getAc()
//					: searchDTO.getMc();
//
//			List<ProductsSearchAssociateKeywordsDO> relativeSearch = productsSearchAssociateKeywords
//					.queryByCategoryProductsCode(code);
//			List<String[]> list = new ArrayList<String[]>();
//			for (ProductsSearchAssociateKeywordsDO search : relativeSearch) {
//				String[] arr = new String[] { search.getKeyword(),
//						EscapeUnescape.escape(search.getKeyword()) };
//				list.add(arr);
//			}
//			out.put("relativeSearch", list);
//		}
//	}

//	/**
//	 * 
//	 * @param out
//	 * @param keywords
//	 */
//	private void showCompanyPrice(Map<String, Object> out, String keywords) {
//		// 用于控制字符串长度
////		out.put("stringUtils", new StringUtils());
//		CompanyPriceDTO companyPriceDTO = new CompanyPriceDTO();
//		companyPriceDTO.setTitle(keywords);
//		companyPriceDTO.getPage().setPageSize(10);
//		companyPriceDTO.getPage().setStartIndex(0);
//		List<CompanyPriceDTO> companyPriceList = companyPriceService
//				.queryCompanyPriceForFront(companyPriceDTO);
//		out.put("companyPriceList", companyPriceList);
//	}

	/**
	 * 
	 * @param out
	 * @param keywords
	 */
//	@SuppressWarnings("rawtypes")
//	private void showTags(Map<String, Object> out, String keywords) {
//		TagsInfoDTO tagsInfoDto = new TagsInfoDTO();
//		TagsInfoDO tagsInfoDO = new TagsInfoDO();
//		tagsInfoDO.setName(keywords);
//		tagsInfoDto.setTagsInfoDO(tagsInfoDO);
//		PageDto page = new PageDto();
//		page.setPageSize(20);
//		page.setStartIndex(0);
//		tagsInfoDto.setPage(page);
//		List<TagsInfoDO> tagsList = tagsInfoService
//				.queryTagsInfoByConditionFromCache(tagsInfoDto);
//		out.put("tagsList", tagsList);
//	}

	/**
	 * 组成用于分页的url
	 * 
	 * @param searchDTO
	 * @param oldKeywords
	 * @return
	 */
//	private String buildUrl(ProductsSearchForFrontDTO searchDTO,
//			String oldKeywords) {
//		String bs = "";
//		if (searchDTO.getBuyOrSale() != null) {
//			bs = searchDTO.getBuyOrSale();
//		}
//		String url = "offerlist---bs" + bs;
//		return url + buildTopNavUrl(searchDTO, oldKeywords);
//	}

	/**
	 * 列表头部导航链接地址，供求类型为固定
	 * 
	 * @param searchDTO
	 * @param oldKeywords
	 * @param url
	 * @return
	 */
//	private String buildTopNavUrl(ProductsSearchForFrontDTO searchDTO,
//			String oldKeywords) {
//		String url = buildNoDateUrl(searchDTO, oldKeywords);
//		url += "---d"
//				+ (searchDTO.getPostInDays() == null ? "" : searchDTO
//						.getPostInDays());
//		return url;
//	}

	/**
	 * 
	 * @param searchDTO
	 * @param oldKeywords
	 * @return
	 */
//	private String buildNoDateUrl(ProductsSearchForFrontDTO searchDTO,
//			String oldKeywords) {
//		String url = "---" + oldKeywords;
//		if (searchDTO.getMid() != null || searchDTO.getAid() != null) {
//			if (searchDTO.getMid() != null) {
//				url += "---m" + searchDTO.getMid();
//			}
//			if (searchDTO.getAid() != null) {
//				url += "---a" + searchDTO.getAid();
//			}
//		} else {
//			url += "---m";
//		}
//		url += "---area"
//				+ (searchDTO.getProvinceCode() == null ? "" : searchDTO
//						.getProvinceCode());
//		return url;
//	}

	@RequestMapping
	public void offerNothing(Map<String, Object> out) {

	}

	/**
	 * 根据类别id获取类别code,并设置关键字。
	 * 
	 * @param searchDTO
	 */
//	private String setCategoryCodeAndkeywords(
//			ProductsSearchForFrontDTO searchDTO) {
//		String keywords = null;
//		if (searchDTO.getAid() != null) {
//			CategoryProductsDO categoryProducts = categoryProductsService
//					.queryCategoryProductsById(searchDTO.getAid());
//			searchDTO.setAc(categoryProducts.getCode());
//			keywords = categoryProducts.getLabel();
//		}
//		if (searchDTO.getMid() != null) {
//			CategoryProductsDO categoryProducts = categoryProductsService
//					.queryCategoryProductsById(searchDTO.getMid());
//			if (categoryProducts != null) {
//				searchDTO.setMc(categoryProducts.getCode());
//				keywords = categoryProducts.getLabel();
//			}
//		}
//		if (searchDTO.getKeywords() != null
//				&& !searchDTO.getKeywords().equals("null")) {
//			keywords = searchDTO.getKeywords();
//		}
//		return keywords;
//	}

	/**
	 * 供求类别大类下详细页面
	 * 
	 * @param out
	 * @param mainCode
	 * @return 
	 */

	@RequestMapping(value = "offerindex.htm", method = RequestMethod.GET)
	public ModelAndView offerindex(Map<String, Object> out, String mainCode) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("trade")+"/offerindex.htm");
//		// common page information need improve
//		// (new PageHeadDTO(NavConst.TRADE_CENTER), out);
//
//		/**
//		 * offerindex 显示类别
//		 */
//		// if (mainCategoryId == null) {
//		// mainCategoryId = 0;
//		// }
//		// String mainCode = getCodeFromCategoryProductsId(mainCategoryId);
////		if (StringUtils.isEmpty(mainCode)) {
////			mainCode = "1000";
////		}
//
//		// List<CategoryProductsDTO> allList = buildCategory();
//		// List<CategoryProductsDTO> list = new
//		// ArrayList<CategoryProductsDTO>();
//		// CategoryProductsDO entity = new CategoryProductsDO();
//		// for (CategoryProductsDTO dto : allList) {
//		// entity = dto.getCategoryProductsDO();
//		// if (entity.getCode().equals(mainCode)) {
//		// list = dto.getChild();
//		// break;
//		// }
//		// }
//		CategoryProductsDO c = new CategoryProductsDO();
//		c.setCode(mainCode);
//		c.setLabel(CategoryProductsFacade.getInstance().getValue(mainCode));
//		out.put("currentCategoryProducts", c);
//		out.put("categoryProductsList", buildCategory(mainCode));
//
//		/**
//		 * 推荐再生通企业
//		 */
//		List<Company> companyList = companyService.queryGoodCompany(20);
////				.queryCompanyNameByRegtime();
//		out.put("companyList", companyList);
//		// 再生技术
//		// 行情综述
//		// 商务热点
////		String type[] = { "index_below_right_02", "review_quotes",
////				"index_below_right_05" };
////		List<BbsPostDTO> bList = bbsService
////				.batchQueryBbsPostByTagsSignTypes(type);
////		for (int i = 0; i < type.length; i++) {
////			out.put("bList" + type[i], bList);
////		}
//
//		/**
//		 * 查询大类别下的行情价格
//		 */
//		List<PriceCategoryDO> priceList;
//		List<ForPriceDTO> listForPrice;
//		PriceDTO priceDTO = new PriceDTO();
//		String code = null;
//		if ("1000".equals(mainCode)) {
//
//			// 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废金属|废金属价格|废金属供求信息_废料交易中心_${site_name}");
//			headDTO.setPageKeywords("废金属,废金属价格,废金属回收,废金属行情,废铜价格,废钢价格,废铁价格,废铜,废铝,废钢,废铁");
//			headDTO.setPageDescription("${site_name}废金属市场为您精选了废铜、废铝、废钢、废铁、贵金属等废金属的供应信息、求购信息、行业资讯、价格行情。");
//			setSiteInfo(headDTO, out);
//			// 废金属
//			code = "废金属";
//			// 金属价格
//			// 页面排行第一的时间
//
//			PriceDO priceDO = priceService.queryTopGmtOrderByParentId(17);
//			out.put("metalGmtOrder", priceDO.getGmtOrder());
//
//			priceList = priceCategoryService.queryPriceCategoryByParentId(17);
//			out.put("MetalList", priceList.subList(0, 9));
//
//			// 金属新料价格
//
//			List<PriceCategoryDO> metalNewCategory = priceCategoryService
//					.queryPriceCategoryByParentId(18);
//			out.put("metalNewCategory", metalNewCategory);
//			Integer pageSize = 2;
//			List<PriceCategoryDTO> bigList = priceService.queryPriceList(18,
//					null, pageSize);
//			out.put("bigList", bigList);
//
//		} else if ("1001".equals(mainCode)) {
//			// 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废塑料|废塑料价格行情|废钢铁,废钢铁回收,求购废钢铁,钢铁,钢管,钢筋废塑料供求信息_再生料_交易中心_${site_name}");
//			headDTO.setPageKeywords("废塑料,废塑料价格,废塑料供应,废塑料求购,废塑料回收, 再生料,PP,PVC");
//			headDTO.setPageDescription("${site_name}废塑料市场是中国最大的废塑料网上贸易市场，这里有我们为您精选的PP,PET PE,ABS,PVC,HDPE,LDPE,PA,PC,PS等废塑料、再生料的供应信息、求购信息、废塑料行业资讯、废塑料价格、废塑料行情、废塑料黄页信息等");
//			setSiteInfo(headDTO, out);
//			// 废塑料
//			code = "废塑料";
//			// 废塑料市场动态
//
//			listForPrice = priceService.queryEachPriceByParentId(22);
//			out.put("plasticMarketList", listForPrice);
//			// 塑料新料市场价出厂价
//			listForPrice = priceService.queryEachPriceByParentId(21);
//			out.put("newPlasticList", listForPrice);
//
//		} else if ("1002".equals(mainCode)) {
//			// 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废旧轮胎与废橡胶|废旧轮胎与废橡胶供求信息_交易中心_${site_name}");
//			headDTO.setPageKeywords("废旧轮胎,废橡胶,废旧轮胎回收,废橡胶回收,废橡胶价格,橡胶颗粒,橡胶粉");
//			headDTO.setPageDescription("${site_name}废旧轮胎与废橡胶市场是中国最大的废旧轮胎与废橡胶网上交易市场，这里有我们为您精选的废轮胎,橡胶粉,橡胶颗粒,硅胶,再生胶等废旧轮胎与废橡胶供应信息、求购信息,废旧轮胎与废橡胶行业资讯、价格行情、公司黄页、展会信息等");
//			setSiteInfo(headDTO, out);
//			// 废橡胶
//			code = "废橡胶";
////			priceDTO.setLimitSize(10);
////			priceDTO.setParentId(15);
//			listForPrice = priceService.queryPriceByParentId(15,10);
//			out.put("rubberList", listForPrice);
//
//		} else if ("1003".equals(mainCode)) {
//			// 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废纺织品与废皮革|废纺织品与废皮革价格行情_交易中心_${site_name}");
//			headDTO.setPageKeywords("废纺织品,废布,废布回收,废皮革,废棉料,化纤,无纺布,尼龙");
//			headDTO.setPageDescription("中国再生资源交易废纺织品与废皮革市场是中国最大的废纺织品与废皮革网上交易市场，这里有我们为您精选的废棉料,废布,碎布,无纺布,涤纶,尼龙等废纺织品与废皮革供应信息、求购信息、回收信息、公司黄页、行业资讯、价格行情等");
//			setSiteInfo(headDTO, out);
//		} else if ("1004".equals(mainCode)) {
//			// 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废纸|废纸价格行情|废纸供求信息_交易中心_${site_name}");
//			headDTO.setPageKeywords("废纸,废纸价格,废纸回收,求购废纸,废纸价格行情,白板纸,白卡纸,包装纸");
//			headDTO.setPageDescription("${site_name}废纸市场是中国最大的废纸网上交易市场，这里有我们为您精选的美废,废报纸书刊,欧废,日废,牛皮纸,纸板箱等废纸供应信息、求购信息、回收信息、废纸公司黄页、废纸行业资讯、废纸价格行情、废纸展会信息等");
//			setSiteInfo(headDTO, out);
//			// 废纸
//			code = "废纸";
////			priceDTO.setLimitSize(10);
////			priceDTO.setParentId(13);
//			listForPrice = priceService.queryPriceByParentId(13,10);
//			out.put("paperList", listForPrice);
//
//		} else if ("1005".equals(mainCode)) {
//			// 废纺织品与废皮革 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废电子电器|旧电脑供求信息_交易中心_${site_name}");
//			headDTO.setPageKeywords("废电子电器,旧电脑,电路板,旧电线,电池,电子元件,线路板,主板,电容");
//			headDTO.setPageDescription("${site_name}废电子电器市场是中国最大的废电子电器与废电脑设备网上贸易市场，这里有我们为您精选的电路板,线路板,CPU,主板,电容,电瓶,变压器,旧电脑等废电子电器与废电脑设备供应信息、废电子电器行业资讯、废电子电器行情、废电子电器展会信息等");
//			setSiteInfo(headDTO, out);
//		} else if ("1006".equals(mainCode)) {
//			// 废玻璃 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废玻璃|废玻璃价格行情|废玻璃供求信息_交易中心_${site_name}");
//			headDTO.setPageKeywords("废玻璃,废玻璃回收,废玻璃价格,玻璃纤维,玻璃瓶,碎玻璃,浮法玻璃");
//			headDTO.setPageDescription("${site_name}废玻璃市场是中国最大的废玻璃网上贸易市场，这里有我们为您精选的碎玻璃，玻璃纤维，有机玻璃等废玻璃供应信息、求购信息、公司黄页等");
//			setSiteInfo(headDTO, out);
//		} else if ("1007".equals(mainCode)) {
//			// 废旧二手设备设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废旧二手设备|废旧二手设备供求信息_交易中心_${site_name}");
//			headDTO.setPageKeywords("废旧设备,二手设备回收,废旧二手设备,废旧二手设备回收,工程设备，化工设备，制冷设备");
//			headDTO.setPageDescription("${site_name}废旧二手设备市场是中国最大的废旧设备与旧交通工具网上交易市场，这里有我们为您精选的机电,废变压器,注塑机,电动机,锅炉等废旧二手设备供应信息、废旧二手设备求购信息、回收信息、行业资讯、价格行情、展会信息等");
//			setSiteInfo(headDTO, out);
//		} else if ("1008".equals(mainCode)) {
//			// 其他废料 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("废木|化工废料|其他废料供求信息_交易中心_${site_name}");
//			headDTO.setPageKeywords("废木,废木制品,木削,木屑,化工废料,废碘,废油,刨花,木削");
//			headDTO.setPageDescription("${site_name}其他废料市场是中国最大的废木、化工废料网上贸易市场，这里有我们为您精选的废木、废木制品、木削、木屑、化工废料等废料供应信息、求购信息、公司黄页等");
//			setSiteInfo(headDTO, out);
//		} else {
//			// 服务 设置页面头部信息
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setTopNavIndex(NavConst.TRADE_CENTER);
//			headDTO.setPageTitle("服务|报关/清关服务|物流服务|代理服务_交易中心_${site_name}");
//			headDTO.setPageKeywords("废料服务,废料报关/清关服务,物流服务,废料进口代理服务, 报关服务,清关服务");
//			headDTO.setPageDescription("${site_name}是中国最大的再生资源网上贸易市场，我们为您提供报关/清关服务,物流服务,代理服务,招聘等一系列服务");
//			setSiteInfo(headDTO, out);
//		}
//		out.put("code", code);
//		out.put("stringUtils", new StringUtils());
//		String escapeCode = EscapeUnescape.escape(code);
//		out.put("escapeCode", escapeCode);
//
//		/**
//		 * 最新供求
//		 */
//		out.put("productsList", productsService.queryProductsByMainCode(mainCode, null, 14));

	}

	/**
	 * 根据productsId查询出供求信息,自动加载公司其他同类产品,产品相册，图片地址
	 * 
	 * @param productsId
	 *            传入后台的供求id主键值
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView productdetails(Integer productsId, String title,
			HttpServletRequest request, Map<String, Object> out)
			throws ParseException {
		return new ModelAndView("redirect:"+AddressTool.getAddress("trade")+"/productdetails"+productsId+".htm");
//		out.put("stringUtils", new StringUtils());
//		do {
//			// 获取供求信息 step1
//			if (productsId == null) {
//				out.put(AstConst.ERROR_TEXT, "您的地址有错误,请检查一下");
//				break;
//			}
//
//			ProductsDto productDetails=productsService.queryProductsDetailsById(productsId);
//			
////			ProductDetailsDTO productDetails = productDetailsService
////					.queryProductDetailsByProductsId(productsId);
//			if (productDetails == null) {
//				out.put(AstConst.ERROR_TEXT, "您的地址有错误,请检查一下");
//				break;
//			}
//
//			// 判断信息是否正常，（审核通过，未过期，没有暂停发布）
//			if (productDetails.getProducts().getCheckStatus() == null
//					|| !"1".equals(productDetails.getProducts().getCheckStatus())) {
//				if ("0".equals(productDetails.getProducts().getCheckStatus())) {
//					out.put(AstConst.ERROR_TEXT, "您查看的供求信息还没有被审核，请耐心等待！");
//				} else {
//					out.put(AstConst.ERROR_TEXT, "对不起，您查看的供求信息审核没有通过！");
//				}
//				break;
//			}
//
//			// 检查过期情况
//			int interval = DateUtil.getIntervalDays(new Date(),
//					productDetails.getProducts().getExpireTime());
//			if (interval > 0) {
//				out.put(AstConst.ERROR_TEXT, "对不起，您查看的供求信息已经过期了！");
//				break;
//			}
//
//			// 检查暂停发布情况
//			// XXX 数据类型不正确，数据库中是字符型
//			if (productDetails.getProducts().getIsPause() != null
//					&& productDetails.getProducts().getIsPause()) {
//				out.put(AstConst.ERROR_TEXT, "对不起，您查看的供求信息现在已经停止发布了！");
//				break;
//			}
//
//			out.put("productDetails", productDetails);
//
//			// 判断是否要获取联系信息，并根据根据查看和显示的标记获取联系信息 step2
//			boolean showFlag = false, viewFlag = false;
//
////			CompanyDO loginUserCompany = getCachedCompany(request);
//			SsoUser sessionUser = getCachedUser(request);
//			if (sessionUser != null) {
//				if (sessionUser.getMembershipCode() != null) {
//					String result = MemberRuleFacade.getInstance().getValue(sessionUser.getMembershipCode(), "view_contacts");
//					
//					viewFlag = Boolean.valueOf(result);
//				}
//			}
//
//			Company company = companyService.querySimpleCompanyById(productDetails.getProducts().getCompanyId());
//			if (company.getMembershipCode() != null) {
//				String result = MemberRuleFacade.getInstance().getValue(company.getMembershipCode(), "show_contacts");
//				showFlag = Boolean.valueOf(result);
//			}
//
////			CompanyContactsDO companyContacts = companyContactsService
////					.queryContactByAccount(productDetails.getProducts().getAccount());
//			CompanyAccount account=companyAccountService.queryAccountByAccount(productDetails.getProducts().getAccount());
//			if (showFlag || viewFlag) {
//				// 获取发布供求的用户信息和积分信息
//				// out.put("creditIntegral", creditIntegralService
//				// .queryCreditIntegralByCompanyId(company.getId()));
//				
//				out.put("companyAccount", account);
//			}
//
//			// 获取供求图片信息 step3
//			out.put("picList", productsPicService
//					.queryProductPicInfoByProductsId(productsId));
//
//			// 获取同公司，同类其他供求信息 step4
//			List<ProductsDO> similarProducts= productsService.queryProductsOfCompany(company.getId(), productDetails.getProducts().getCategoryProductsMainCode());
//			out.put("similarProducts", similarProducts);
//
//			// 获取同一个类别的企业报价 step5 (同一种类别，如果没有对应的类别，则找名称匹配的)
//			CompanyPriceDTO companyPriceDTO = new CompanyPriceDTO();
//			companyPriceDTO.getPage().setStartIndex(0);
//			companyPriceDTO.getPage().setPageSize(12);
//			companyPriceDTO.setCompanyPriceDO(new CompanyPriceDO());
//
//			// 查找有没有对应的code
//			CategoryCompanyPriceDO categoryCompanyPrice = categoryCompanyPriceService
//					.queryCategoryCompanyPriceByLabel(productDetails
//							.getCategoryProductsMainLabel());
//			
//			if (categoryCompanyPrice != null
//					&& categoryCompanyPrice.getCode() != null
//					&& categoryCompanyPrice.getCode().length() > 0) {
//				companyPriceDTO.getCompanyPriceDO()
//						.setCategoryCompanyPriceCode(
//								categoryCompanyPrice.getCode());
//			} else {
//				companyPriceDTO.setTitle(productDetails.getProducts().getTitle());
//			}
//			out.put("companyPriceList", companyPriceService
//					.queryCompanyPriceForFront(companyPriceDTO));
//
//			// 获取相关的标签信息 step6
//			List<TagsInfoDO> tagList = tagsArticleService
//					.queryTagListFromTagsArticleRelationByArticleId(
//							TagsArticleService.MODEL_PRODUCT,
//							productDetails.getProducts().getId());
//			out.put("tagList", tagList);
//
//			// FIXME 获取供求类别对应的产品相册信息 step7
//
//			// 分析类别导航 step8
//			Map<String, String> navigationCategoryMap = new LinkedHashMap<String, String>();
//			for (int i = 4; i <= productDetails.getProducts().getCategoryProductsMainCode()
//					.length(); i = i + 4) {
//				String code = productDetails.getProducts().getCategoryProductsMainCode()
//						.substring(0, i);
//				navigationCategoryMap.put(code, CategoryProductsFacade
//						.getInstance().getValue(code));
//			}
//			out.put("navigationCategoryMap", navigationCategoryMap);
//
//			// 初始化留言要用的信息 step9
//			Inquiry inquiry = new Inquiry();
//			inquiry.setTitle(productDetails.getProducts().getTitle());
//			inquiry.setBeInquiredId(productsId);
//			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_DEFAULT);
//			inquiry.setReceiverAccount(productDetails.getProducts().getAccount());
//			out.put("inquiry", inquiry);
//
//			//
////			if (!DEFAULT_MEMBERSHIP_CODE.equals(company.getMembershipCode())) {
//				// FIXME 查找用户的再生通年份
////				CompanyMembershipDO cm = companyMembershipService
////						.queryCompanyMembershipByCompanyId(productDetails.getProducts()
////								.getCompanyId());
////				if (cm == null) {
////					cm = new CompanyMembershipDO();
////					cm.setYears("1");
////				}
////				out.put("memberShipInfo", cm);
////			}
//
//			// SEO step11
//			StringBuffer sb = new StringBuffer();
//			sb.append(productDetails.getProductsTypeLabel());
//			sb.append(productDetails.getProducts().getTitle());
//			String bigCategory = null;
//			// for (String key : navigationCategoryMap.keySet()) {
//			// sb.append(navigationCategoryMap.get(key)).append(",");
//			// if (bigCategory == null) {
//			// bigCategory = navigationCategoryMap.get(key);
//			// }
//			// }
//
//			if (productDetails.getProducts().getCategoryProductsMainCode() != null
//					&& productDetails.getProducts().getCategoryProductsMainCode().length() > 4) {
//				bigCategory = navigationCategoryMap.get(productDetails
//						.getProducts().getCategoryProductsMainCode().subSequence(0, 4));
//			}
//
//			String tagKeyword = "";
//			if (tagList != null) {
//				for (TagsInfoDO tag : tagList) {
//					tagKeyword = tagKeyword + "," + tag.getName();
//				}
//			}
//			PageHeadDTO headDTO = new PageHeadDTO();
//			headDTO.setPageTitle(sb.toString() + "_" + bigCategory
//					+ "交易中心_${site_name}");
//			headDTO.setPageKeywords(sb.toString() + ","
//					+ productDetails.getProducts().getTitle() + "价格" + tagKeyword);
//			headDTO.setPageDescription("${site_name}" + sb.toString()
//					+ "市场是中国最大的" + sb.toString() + "网上贸易市场,我们为您提供"
//					+ sb.toString() + "相关的供应信息、求购信息、价格行情等");
//			setSiteInfo(headDTO, out);
//
//			out.put("resourceUrl",
//					(String) MemcachedUtils.getInstance().getClient().get(
//							"baseConfig.resource_url"));
//			return null;
//		} while (true);
//		return new ModelAndView("/common/error");
	}

	/**
	 * 初始化发布的供求信息
	 */
	@RequestMapping
	public ModelAndView postoffer_step1(Map<String, Object> out,
			HttpServletRequest request) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("trade")+"/postoffer_step1.htm");
//		setSiteInfo(new PageHeadDTO(), out);
//		// 判断是不是已经超过发布条数限制了
////		CompanyDO company = getCachedCompany(request);
//		SsoUser sessionUser = getCachedUser(request);
//		if (sessionUser != null) {
//			out.put("result", String.valueOf(productsService
//					.queryUserIsAddProducts(sessionUser.getCompanyId(),sessionUser.getMembershipCode())));
//		}
//		return null;
	}

	@RequestMapping
	public ModelAndView postAccess(Map<String, Object> out,
			HttpServletRequest request) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			result.setSuccess(productsService.queryUserIsAddProducts(sessionUser.getCompanyId(), sessionUser.getMembershipCode()));
		}
		return printJson(result, out);
	}

	final static String CATEGORY_MANUFACTURE = "1011";

	/**
	 * 初始化发布的供求信息,判断密码
	 * 
	 * @param productsDO为供求信息
	 * @param companyContactsDO为客户联系信息
	 * @param response请求响应
	 * @param out输出结果信息
	 * @return
	 * @return ModelAndView视图
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView postoffer_step2(ProductsDO productsDO, String email,
			Map<String, Object> out, HttpServletRequest request)
			throws IOException {
		return new ModelAndView("redirect:"+AddressTool.getAddress("trade")+"/postoffer_step2.htm");
//		CompanyDO company = getCachedCompany(request);
//		SsoUser sessionUser = getCachedUser(request);
//
//		if (sessionUser == null) {
//			return new ModelAndView(new RedirectView("postoffer_step1.htm"));
//		}
//
//		if (productsDO.getProductsTypeCode() == null) {
//			return new ModelAndView(new RedirectView("postoffer_step1.htm"));
//		}
//
//		if (StringUtils.isEmpty(email)) {
//			email = getCachedUser(request).getEmail();
//		}
//
//		setSiteInfo(new PageHeadDTO(), out);
//		// 转存productsDo和Email
//		out.put("productsDO", productsDO);
//		out.put("email", email);
//		out.put("uploadModel", FrontConst.UPLOAD_MODEL_PRODUCTS);
//
//		// 会员上传图片的数量
//		out.put("uploadPicNum", Integer.valueOf(MemberRuleFacade.getInstance().getValue(sessionUser.getMembershipCode(),"upload_products_picture")));
//
//		out.put("resourceUrl",
//				(String) MemcachedUtils.getInstance().getClient().get(
//						"baseConfig.resource_url"));
//
//		// 初始化加工说明
//		// out.put("manufactureMap",
//		// CategoryFacade.getInstance().listCategoryByParentCode(
//		// CATEGORY_MANUFACTURE));
//		out.put("manufactureMap",
//				categoryService.queryCategoriesByPreCode(CATEGORY_MANUFACTURE));
//
//		// 判断是不是再生通会员
//		return null;

		// //TODO:正式上线后修改这里，去掉else部分。
		// if (ParamFacade.getInstance().getParamValue("baseConfig",
		// "open_status") != null
		// && ParamFacade.getInstance().getParamValue("baseConfig",
		// "open_status").equals(
		// "all")) {
		// if (productsDO.getProductsTypeCode() == null) {
		// return new ModelAndView(new RedirectView("postoffer_step1.htm"));
		// }
		// // 根据email查询companyId注册页面
		// CompanyContactsDO companyContactsDO =
		// companyService.selectCompanyIdByEmail(email);
		// // 如果为空,跳转入(postoffer_step3.htm)
		// if (companyContactsDO == null) {
		//
		// // 公司类型
		// List<CategoryDO> companyTypeList = CategoryFacade.getInstance()
		// .listCategoryByParentCode("1020");
		// // 主营类别
		// List<CategoryDO> industryCodeList = CategoryFacade.getInstance()
		// .listCategoryByParentCode("1012");
		// out.put("companyTypeList", companyTypeList);
		// out.put("industryCodeList", industryCodeList);
		// out.put("productsDO", productsDO);
		// out.put("email", email);
		// out.put("uploadModel", FrontConst.UPLOAD_MODEL_PRODUCTS);
		// out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
		// "baseConfig.resource_url"));
		// return new ModelAndView("trade/postoffer_step3");
		// } else {
		// out.put("productsDO", productsDO);
		// out.put("companyContactsDO", companyContactsDO);
		// return new ModelAndView("trade/postoffer_step2");
		// }
		//
		// } else {
		// out
		// .put("js",
		// "alert('本站为测试版，新的生意管家后期将开放。请您在老站点登录！');window.location.href='http://www.zz91.com/cn/login.asp';");
		// return new ModelAndView("js");
		// }
	}

	/**
	 * 保存供求信息，用AJAX保存
	 * 
	 * @param out
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
//	@RequestMapping
//	public ModelAndView postProducts(Map<String, Object> out,
//			ProductsDO products, String picIds, String tagsArr,
//			Integer postlimittime, HttpServletRequest request)
//			throws IOException, ParseException {
//		ExtResult result = new ExtResult();
//
//		do {
//			CompanyDO companyDO = getCachedCompany(request);
//			CompanyContactsDO account = getCachedAccount(request);
//			if (account == null) {
//				result.setData("sessionTimeOut");
//				break;
//			}
//			// 准备数据
//			products.setAccount(account.getAccount());
//			products.setCompanyId(account.getCompanyId());
//			Date now = new Date();
//			products.setRefreshTime(now);
//			// 计算过期时间（本步骤应该在数据库中进行）
//			if (postlimittime == -1) {
//				products.setExpireTime(DateUtil.getDate("9999-12-31 23:59:59",
//						AstConst.DATE_FORMATE_WITH_TIME));
//			} else {
//				products.setExpireTime(DateUtil.getDateAfterDays(now,
//						postlimittime));
//			}
//			if (!StringUtils.isEmpty(products.getPrice())) {
//				products.setIsShowInPrice("1");
//			}
//			// 发布供求信息
//			Integer productId = productsService.publishProductsByCompany(products, companyDO.getMembershipCode());
//			
//			if (!StringUtils.isEmpty(products.getPrice())) {
//				// 添加为报价
//				CompanyPriceDO companyPriceDO = new CompanyPriceDO();
//				companyPriceDO.setProductId(productId);
//				companyPriceDO.setAccount(account.getAccount());
//				companyPriceDO.setCompanyId(account.getCompanyId());
//				companyPriceService.addProductsToCompanyPrice(companyPriceDO,
//						companyDO, products);
//			}
//			
//			// 没有发布成功
//			if (productId <= 0) {
//				result.setData("failureInsert");
//				break;
//			}
//			products.setId(productId);
//
//			// 关联上传的图片
//			if (StringUtils.isNotEmpty(picIds)) {
//				productsService.insertProductsPicRelation(productId,
//						StringUtils.StringToIntegerArray(picIds));
//			}
//
//			// 关联标签
//			addTags(tagsArr, products, request);
//
//			result.setData(products);
//			result.setSuccess(true);
//			// 高级用户可以直接增加积分，因为直接审核通过
//			if (!"10051000".equals(companyDO.getMembershipCode())) {
//				scoreChangeDetailsService
//						.saveChangeDetails(new ScoreChangeDetailsDo(companyDO
//								.getId(), null, "get_post_product", null,
//								productId, null));
//				List<ProductsPicDO> picList = productsPicService
//						.queryProductPicInfoByProductsId(productId);
//				for (ProductsPicDO pic : picList) {
//					scoreChangeDetailsService
//							.saveChangeDetails(new ScoreChangeDetailsDo(
//									companyDO.getId(), null,
//									"get_post_product_pic", null, pic.getId(),
//									null));
//				}
//			}
//
//			break;
//		} while (true);
//		return printJson(result, out);
//	}

	@RequestMapping
	public void postProductSuccess(Map<String, Object> out, Integer productId) {
		out.put("productId", productId);
	}

	@RequestMapping
	public ModelAndView postProductsPic(Map<String, Object> out,
			ProductsPicDO picture) throws IOException {
		ExtResult result = new ExtResult();
		picture.setIsDefault("0");
		picture.setIsCover("0");
		Integer i = productsPicService.insertProductsPic(picture);
		if (i.intValue() > 0) {
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}

	/**
	 * 根据密码添加供求信息
	 * 
	 * @param productsDO为供求信息
	 * @param out输出结果信息
	 * @return ModelAndView视图
	 * @throws IOException
	 */
//	@RequestMapping
//	@Deprecated
//	public ModelAndView addProducts(ProductsDO productsDO,
//			Map<String, Object> model) throws IOException {
//		ExtResult result = new ExtResult();
//		productsDO.setExpireTime(new Date());
//		Integer i = productsService.insertProduct(productsDO);
//		if (i > 0) {
//			result.setSuccess(FrontConst.SUCCESS);
//			return printJson(result, model);
//		} else {
//			model.put(AstConst.ERROR_TEXT, "error text");
//			return new ModelAndView("/common/error");
//		}
//	}

	/**
	 * 发布供求成功跳转页面
	 */
	@RequestMapping
	@Deprecated
	public void joinprocess(Map<String, Object> out) {
		out.put("openStatus",
				ParamUtils.getInstance().getValue("baseConfig",
						"open_status"));
	}

	/**
	 * 判断email和Mobile是否存在
	 * 
	 * @param companyContactsDO为公司联系信息
	 * @param out输出结果信息
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView selectContactsByEmailAndMobile(String mobile,
//			String email, Map<String, Object> model) throws IOException {
//		String result = "true";
//		if (StringUtils.isNotEmpty(mobile)) {
//			CompanyContactsDO companyContactsDO = new CompanyContactsDO();
//			companyContactsDO.setMobile(mobile);
//			// 根据email和Mobile查询出CompanyContactsDO信息
//			List<CompanyContactsDO> comList = companyService
//					.selectContactsByEmailAndMobile(companyContactsDO);
//			if (comList.size() > 0) {
//				result = "false";
//			}
//		}
//		model.put("json", result);
//		return new ModelAndView("json");
//	}

	/**
	 * 注册公司和发布供求信息
	 * 
	 * @param productsDO为供求信息
	 * @param companyContactsDO为公司联系信息
	 * @param companyDO为公司基本信息
	 * @param out输出结果信息
	 * @return ModelAndView视图
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws FileUploadException
	 */
//	@RequestMapping
//	@Deprecated
//	public ModelAndView addProductsDetails(ProductsDO productsDO,
//			CompanyContactsDO companyContactsDO, String tags,
//			CompanyDO companyDO, String fileName, String postlimittime,
//			Map<String, Object> out, HttpServletRequest request)
//			throws ParseException, NoSuchAlgorithmException,
//			UnsupportedEncodingException, AuthorizeException {
//		String[] str = new String[2];
//		// 自动生成密码和注册帐号
//		str = AlgorithmUtils.autoMD5();
//		AuthUser user = new AuthUser();
//		user.setUsername(companyContactsDO.getAccount());
//		user.setPassword(str[1]);
//		user.setEmail(companyContactsDO.getEmail());
//		AuthRole authRole = roleService
//				.listOneRoleByName((String) MemcachedUtils.getInstance().getClient().get(
//						"baseConfig.default_regist_role"));
//		userService.createUserByRegist(user, StringUtils
//				.StringToIntegerArray(String.valueOf(authRole.getId())));
//
//		// 添加客户基本信息(companyDO)信息
//		Integer c = companyService.insertCompany(companyDO);
//		// 把companyDO中id传给companyContactsDO表companyId
//		companyContactsDO.setCompanyId(c);
//		// 添加客户联系(CompanyContactsDO)信息
//		companyService.insertCompanyContacts(companyContactsDO);
//		// 添加CompanyAccessgradeDO(黑名单)信息
//		CompanyAccessGradeDO companyAccessGrade = new CompanyAccessGradeDO();
//		companyAccessGrade.setAccessGradCode(AstConst.BLACK_CODE);
//		companyAccessGrade.setCompanyId(c);
//		companyService.insertCompanyAccessGrade(companyAccessGrade);
//		// 设置供求companyId值
//		productsDO.setCompanyId(c);
//		if ("-1".equals(postlimittime)) {
//			// 有效期为最大时间
//			productsDO.setExpireTime(DateUtil.getDate("9999-12-31 23:59:59",
//					"yyyy-MM-dd HH:mm:ss"));
//		} else {
//			productsDO.setExpireTime(DateUtil.getDateAfter(new Date(),
//					Calendar.DAY_OF_YEAR, Integer.valueOf(postlimittime)));
//		}
//
//		// 添加供求信息
//		Integer i = productsService.insertProduct(productsDO);
//		if (i > 0) {
//			// 添加标签
//			productsDO.setId(i);
//			addTags(tags, productsDO, request);
//			// 插入图片信息
//			ProductsPicDO productsPicDO = new ProductsPicDO();
//			productsPicDO.setProductId(i);
//			productsPicDO.setPicAddress(fileName);
//			productsPicDO.setAlbumId(0);
//			productsPicDO.setIsDefault("0");
//			productsPicDO.setIsCover("0");
//			productsPicService.insertProductsPic(productsPicDO);
//		}
//
//		out.put("account", companyContactsDO.getAccount());
//		out.put("password", str[0]);
//		return new ModelAndView("trade/joinprocess");
//	}

	@RequestMapping
	public ModelAndView selectCategory(Map<String, Object> model, String code)
			throws IOException {
		JSONArray json = null;
		if (code == null) {
			listTree = categoryService.child(DEFAULT_CITY_CODE);
			json = JSONArray.fromObject(listTree);
		} else {
			listTree = categoryService.child(code);
			json = JSONArray.fromObject(listTree);
		}
		return printJson(json, model);
	}

	/**
	 * 查询产品类别code长度为4的信息
	 * 
	 * @throws IOException
	 */
	// @RequestMapping
	// public ModelAndView queryCategoryProductsFront(Map<String, Object> model)
	// throws IOException {
	// List<CategoryProductsDO> list = new ArrayList<CategoryProductsDO>();
	//
	// ProductsCategoryFacade.getInstance()
	// .queryProductsCategoryDirectChildrenByParentCode(null, "0");
	//
	// return printJson(list, model);
	// }

	/**
	 * 根据preCode查询所有产品类别
	 * 
	 * @param preCode
	 *            为类别Code
	 * @param model
	 * @throws IOException
	 */
	// @RequestMapping
	// public ModelAndView queryCategoryProductsByCondition(String preCode,
	// String isAssist,
	// Map<String, Object> model) throws IOException {
	// List<CategoryProductsDO> list = ProductsCategoryFacade.getInstance()
	// .queryProductsCategoryDirectChildrenByParentCode(preCode, isAssist);
	// return printJson(list, model);
	// }

	/**
	 * 根据供求类别ID获取对应的code
	 * 
	 * @param categoryId
	 * @return
	 */
//	private String getCodeFromCategoryProductsId(Integer categoryId) {
//		String code = "";
//		CategoryProductsDO categoryProducts = categoryProductsService
//				.queryCategoryProductsById(categoryId);
//		if (categoryProducts != null) {
//			code = categoryProductsService
//					.queryCategoryProductsById(categoryId).getCode();
//		}
//		return code;
//	}

	/**
	 * 添加标签
	 * 
	 * @param tagsName
	 * @param articleId
	 */
//	private void addTags(String tagsName, ProductsDO productsDO,
//			HttpServletRequest request) {
//		int pdtid = productsDO.getId();
//		// 获取标签列表
//		String[] tagNames = StringUtils
//				.distinctStringArray(tagsName.split(","));
//		// 获取操作员信息
////		CompanyContactsDO cc = getCachedAccount(request);
//		// 删除与文章相关的所有标签关联信息
//		List<TagsInfoDO> tagsList = tagsArticleService
//				.queryTagListFromTagsArticleRelationByArticleId("10351001",
//						pdtid);
//		List<String> tagNamesQueried = new ArrayList<String>();
//		// tagNamesQueried 存在，tagNames 不存在 删除标签关联
//		List<TagsArticleRelation> deletedTagArtRelList = new ArrayList<TagsArticleRelation>();
//		// tagNamesQueried 不存在，tagNames 存在 新新标签关联
//		List<TagsArticleRelation> newTagArtRelList = new ArrayList<TagsArticleRelation>();
//		for (TagsInfoDO tag : tagsList) {
//			tagNamesQueried.add(tag.getName());
//			if (!StringUtils.isContains(tagNames, tag.getName())) {
//				TagsArticleRelation tagArtRel = new TagsArticleRelation();
//				tagArtRel.setTagId(tag.getId());
//				tagArtRel.setTagName(tag.getName());
//				tagArtRel.setArticleId(pdtid);
//				deletedTagArtRelList.add(tagArtRel);
//			}
//		}
//		tagsArticleService.deleteTagsArticleRelationByArticleId("10351001",
//				pdtid);
//		// 设置,添加标签关联信息
//		for (String tagName : tagNames) {
//			TagsArticleRelation relation = new TagsArticleRelation();
//			relation.setArticleModuleCode("10351001");// 10351001 ,供求信息
//			relation.setArticleCategoryCode(productsDO
//					.getCategoryProductsMainCode());
//			relation.setArticleId(pdtid);
//			relation.setArticleTitle(productsDO.getTitle());
//			relation.setTagName(tagName);
//			relation.setCreator(getCachedUser(request).getAccountId());
//			try {
//				tagsArticleService.insertTagsArticleRelation(relation);
//				if (!tagNamesQueried.contains(tagName)) {
//					newTagArtRelList.add(relation);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		List<OperationLogInfo> optLogList = OperationLogCreator
//				.createTagLogInfoByTagArticleRelation(OperationLogLevel.INFO
//						.getValue(), request.getRemoteAddr(), request
//						.getContextPath(), cc, TagOptType.DELETE.getCode(),
//						"删除标签和供求文章关联关系", deletedTagArtRelList
//								.toArray(new TagsArticleRelation[] {}));
//		optLogList
//				.addAll(OperationLogCreator
//						.createTagLogInfoByTagArticleRelation(
//								OperationLogLevel.INFO.getValue(), request
//										.getRemoteAddr(), request
//										.getContextPath(), cc,
//								TagOptType.CREATE.getCode(), "新建标签和供求文章关联关系",
//								newTagArtRelList
//										.toArray(new TagsArticleRelation[] {})));
//		operationLogInfoService.insertOperationLogInfos(optLogList);
//	}

	/**
	 * 供求推荐
	 */
//	@RequestMapping
//	public ModelAndView recommend(Integer id, Map<String, Object> out,
//			HttpServletResponse response, HttpServletRequest request) {
//		ProductsDO products = productsService.queryProductsById(id);
//
//		if (products == null) {
//			try {
//				response.sendRedirect(request.getContextPath()
//						+ "/root/error.htm?s=" + id);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//		out.put("products", products);
//		if (products.getAccount() != null) {
////			out.put("companyContacts", companyService
////					.selectContactsByAccount(products.getAccount()));
//			out.put("companyAccount", companyAccountService.queryAccountByAccount(products.getAccount()));
//		}
//		out.put("id", id);
//		out.put("serverName", request.getServerName());
//		if (request.getServerPort() != 8080) {
//			out.put("serverPort", ":" + request.getServerPort());
//		}
//		return new ModelAndView("/trade/recommend");
//	}

	/**
	 * 发送供求推荐
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 * @throws AddressException
	 */
//	@RequestMapping
//	public ModelAndView sendRecommend(ProductsRecommendDO productsRecommend,
//			Map<String, Object> model) throws AddressException, IOException,
//			MessagingException {
//
//		if (productsRecommend == null) {
//			model.put(AstConst.ERROR_TEXT, "error text");
//			return new ModelAndView("/common/error");
//		}
//
//		String email = productsRecommend.getReceiveEmail();
//		email = email.trim().replace(",,", "");
//		if (email.startsWith(",")) {
//			email = email.substring(1, email.length());
//		}
//		if (email.endsWith(",")) {
//			email = email.substring(0, email.length() - 1);
//		}
//
//		productsRecommend.setReceiveEmail(email);
//
//		Integer i = productsRecommendService.sendRecommend(productsRecommend);
//		if (i == null || i.intValue() <= 0) {
//			// go to error page
//			model.put(AstConst.ERROR_TEXT, "发送失败");
//			return new ModelAndView("/common/error");
//		} else {
//			model.put("id", productsRecommend.getProductId());
//			return new ModelAndView("trade/recommend_suc");
//		}
//
//	}

	/**
	 * 显示图片和flash
	 */
	@RequestMapping
	public ModelAndView picshow(Integer pid, Integer psort,
			Map<String, Object> out) {
		// out.put("pid", pid);
		// out.put("psort", psort);
		if (pid == null || pid.intValue() <= 0) {
			out.put(AstConst.ERROR_TEXT, "您的地址有错误,请检查一下");
			return new ModelAndView("/common/error");
		}
		// 查找对应的图片信息
		out.put("picList",
				productsPicService.queryProductPicInfoByProductsId(pid));
		out.put("resourceUrl",
				(String) MemcachedUtils.getInstance().getClient().get(
						"baseConfig.resource_url"));
		return null;
	}

	/**
	 * 供求详细图片查看
	 */
	@RequestMapping
	public void pics(Integer pid, Map<String, Object> out) {
		List<ProductsPicDO> list = productsPicService
				.queryProductPicInfoByProductsId(pid);
		out.put("list", list);
		out.put("uploadModel", FrontConst.UPLOAD_MODEL_PRODUCTS);
		out.put("resourceUrl",
				(String) MemcachedUtils.getInstance().getClient().get(
						"baseConfig.resource_url"));
	}

//	/**
//	 * 初始化新增供求信息
//	 * 
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping
//	public ModelAndView initAddProductInfo(Map<String, Object> model,
//			HttpServletRequest request, String reqFrom) {
//		// 是否超过每天的发布条数限制
//		CompanyDO company = getCachedCompany(request);
//		if (company != null) {
//			boolean isNotOverLimit = productsService
//					.queryUserIsAddProducts(company);
//			// 跳转到提示页面：超过每天的发布条数限制 来源：myrc/前台快速入口
//			model.put("isNotOverLimit", isNotOverLimit);
//		}
//		Map<String, String> productTypeMap = CategoryFacade.getInstance()
//				.getChild("1033");
//		model.put("productTypeMap", productTypeMap);
//		model.put("reqFrom", reqFrom);
//		if ("myrc".equals(reqFrom))
//			return new ModelAndView("/myrc/myproducts/office_post1");
//		return new ModelAndView("/trade/postoffer_step1");
//	}

}
