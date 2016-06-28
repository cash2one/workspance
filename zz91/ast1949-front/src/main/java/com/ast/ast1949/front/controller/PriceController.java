/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.front.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.velocity.AddressTool;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class PriceController extends BaseController {
//	@Resource
//	private TagsArticleService tagsArticleService;
//	@Autowired
//	private PriceCategoryService priceCategoryService;
//	@Autowired
//	private PriceService priceService;
//	@Autowired
//	private PriceDataService priceDataService;
//	@Autowired
//	private ProductsService productsService;
//	@Autowired
//	private BbsService bbsService;
//	@Autowired
//	private AdsService adsService;
//	@Autowired
//	private CompanyService companyService;
//	@Autowired
//	private NewsService newsService;
//	@Autowired
//	private ChartCategoryService chartCategoryService;

	@RequestMapping
	public ModelAndView priceSearch(String title, String p, Map<String, Object> out)
			throws UnsupportedEncodingException {
		title = StringUtils.decryptUrlParameter(title);
		title=URLEncoder.encode(title, "utf-8");
		if(p==null){
			p="";
		}
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/priceSearch.htm?title="+title+"&p="+p);
//		setSiteInfo(new PageHeadDTO(), out);
//		List<ForPriceDTO> priceList;
//		List<PriceCategoryDO> list1;
//		p = StringUtils.getPageIndex(p);
//		out.put("currentPage", Integer.valueOf(p));
//		PageDto page=new PageDto();
//		page.setPageSize(40);
//		page.setStartIndex((Integer.valueOf(p) - 1) * page.getPageSize());
//		page=priceService.queryPricePaginationListByTitle(title,page);
//		List<PriceDO> allPriceList = page.getRecords();
//		out.put("title", title);
//		out.put("totalPages", page.getTotalPages());
//		out.put("allPriceList", allPriceList);
//		// 各地金属价格查询
//		List<PriceCategoryDO> list = priceCategoryService.queryPriceCategoryByParentId(3);
//		out.put("areaList", list);
//		// 塑料价格行情
//		// 塑料新料市厂价格
//		List<PriceDO> sList = priceService.queryPriceByTypeId(null,60,null,6);
//		out.put("sList", sList);
//		// 国内石化出厂价
//		List<PriceDO> cList = priceService.queryPriceByTypeId(null,61,null,6);
//		out.put("cList", cList);
//		// 油价 C
//		List<PriceDO> oilList = priceService.queryPriceByTypeId(190,null,null,6);
//		out.put("oilList", oilList);
//		// 各地废塑料
//		PriceDTO priceDTO = new PriceDTO();
//		priceDTO.setParentId(22);
//		priceList = priceService.queryPriceByParentId(priceDTO);
//		out.put("newPlasticList", priceList);
//		// /废纸废橡胶价格行情
//		PriceCategoryDO paperCategoryDO = priceCategoryService.queryPriceCategoryById(13);
//		PriceCategoryDO rubberCategoryDO = priceCategoryService.queryPriceCategoryById(15);
//		out.put("paperCategoryDO", paperCategoryDO);
//		out.put("rubberCategoryDO", rubberCategoryDO);
//
//		priceDTO.setParentId(13);
//		priceList = priceService.queryPriceByParentId(priceDTO);
//		out.put("paperList", priceList);
//
//		priceDTO.setParentId(15);
//		priceList = priceService.queryPriceByParentId(priceDTO);
//		out.put("rubberList", priceList);
//		// 金属废料报价
//		list1 = priceCategoryService.queryPriceCategoryByParentId(17);
//		out.put("categoryList", list1.subList(0, 9));
//
//		priceDTO.setParentId(17);
//		priceList = priceService.queryPriceByParentId(priceDTO);
//		out.put("priceList", priceList);
//		// 金属新料/期货价格行情
//		list1 = priceCategoryService.queryPriceCategoryByParentId(18);
//		out.put("newCategoryList", list1);
//
//		priceDTO.setParentId(18);
//		priceList = priceService.queryPriceByParentId(priceDTO);
//		out.put("newPriceList", priceList);
//		out.put("suffixUrl", "");
//		
//		out.put("companyList", companyService.queryGoodCompany(20));
//		
//		List<TagsInfoDO> tagsList = tagsArticleService.queryTagListByTagCatAndArtCat("4", "", 20);
//		out.put("tagsList", tagsList);
//		
//		return null;
	}

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out) {
		
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/index.htm");
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.NEWS);
//		headDTO.setPageTitle("报价资讯中心_废金属价格|废塑料价格|废纸价格|废橡胶价格|再生技术_ZZ91再生网_${site_name}");
//		headDTO.setPageKeywords("废金属价格,废塑料价格,废纸价格,废橡胶价格,再生技术,市场行情,行情评论,废铜价格,废钢价格,废铁价格");
//		headDTO.setPageDescription("${site_name}的资讯及报价中心以废金属价格,废塑料价格,废纸价格,废橡胶价格为主要报价,以再生技术,再生政策法规,再生行业要闻为辅的信息中心,为再生行业提供及时有效的废金属价格,废塑料价格,废纸及废橡胶价格以及再生行业的相关资讯!");
//		setSiteInfo(headDTO, out);
//		
//		List<ForPriceDTO> listForPrice;
//		List<PriceCategoryDO> list;
//		PriceCategoryDO price = new PriceCategoryDO();
//		PriceDTO priceDTO = new PriceDTO();
//
//
//		// ---------------------------------------------废金属价格- 市场行情
//		// 废金属报价分类列表
//		List<PriceCategoryMinDto> metalList=priceCategoryService.queryPriceCategoryByParentIdOrderList(17);
//		out.put("metalList", metalList);
//		// 金属新料价格
//		List<PriceCategoryDTO> metalNewmaterialsList=priceService.queryPriceCategoryInfoByParentIdAndAssistId(18,null,2);
//		out.put("metalNewmaterialsList", metalNewmaterialsList);
//		// 废金属辅助类别
//		list = priceCategoryService.queryPriceCategoryByParentId(3);
//		out.put("areaList", list.subList(0, 7));
//		// 废金属市场动态
//		listForPrice = priceService.queryEachPriceByParentId(19);
//		out.put("MarketMetallist", listForPrice);
//		// 废金属日评周评
//		List<PriceCategoryDTO> metalCommentList=priceService.queryPriceCategoryInfoByParentIdAndAssistId(10,null,10);
//		out.put("metalCommentList", metalCommentList);
//		// ---------------------------------------------------废塑料 - 市场行情
//		// 废塑料报价分类列表
//		List<PriceCategoryMinDto>  plasticList=priceCategoryService.queryPriceCategoryByParentIdOrderList(20);
//		out.put("plasticList", plasticList);
//		// 废塑料市场动态
//		listForPrice = priceService.queryEachPriceByParentId(22);
//		out.put("plasticMarketList", listForPrice.subList(0, 8));
//		// 塑料新料市厂价格
//		price = priceCategoryService.queryPriceCategoryById(60);
//		out.put("newPlasticPrice", price);
//
//		priceDTO.setLimitSize(8);
//		priceDTO.setParentId(60);
//		listForPrice = priceService.queryPriceByParentId(priceDTO);
//		out.put("newPlasticList", listForPrice);
//		//塑料再生料
//		listForPrice = priceService.queryEachPriceByParentId(98);
//		out.put("zzplastic", listForPrice.subList(0, 5));
//		// 国内石化出厂价
//		price = priceCategoryService.queryPriceCategoryById(61);
//		out.put("factoryPrice", price);
////		PriceDTO priceDTO1 = new PriceDTO();
////		priceDTO1.getPage().setStartIndex(0);
////		priceDTO1.getPage().setPageSize(8);
////		priceDTO1.setParentId(61);
//		List<PriceDO> pList = priceService.queryPriceByTypeId(null,61,null,8);
//		out.put("factoryList", pList);
//
//		// 废塑料日评周评
//		List<PriceCategoryDTO> plasticCommentList=priceService.queryPriceCategoryInfoByParentIdAndAssistId(12,null,10);
//		out.put("plasticCommentList", plasticCommentList);
//		// --------------------------------------------------废纸|橡胶-市场行情
//
//		list = priceCategoryService.queryPriceCategoryByParentId(13);
//		out.put("paperCatgeory", list.subList(3, list.size()));
//
//		list = priceCategoryService.queryPriceCategoryByParentId(15);
//		out.put("rubberCategory", list);
//
//		price = priceCategoryService.queryPriceCategoryById(13);
//		out.put("paperPrice", price);
//		// 废纸价格
//		priceDTO.setLimitSize(10);
//		priceDTO.setParentId(13);
//		listForPrice = priceService.queryPriceByParentId(priceDTO);
//		out.put("paperList", listForPrice);
//
//		price = priceCategoryService.queryPriceCategoryById(15);
//		out.put("rubberPrice", price);
//		// 废橡胶价格
//		priceDTO.setLimitSize(10);
//		priceDTO.setParentId(15);
//		listForPrice = priceService.queryPriceByParentId(priceDTO);
//		out.put("rubberList", listForPrice);
//
//		price = priceCategoryService.queryPriceCategoryById(23);
//		out.put("marketPrice", price);
//		// 市场动态
////		PriceDTO priceDTO2 = new PriceDTO();
////		priceDTO2.getPage().setStartIndex(0);
////		priceDTO2.getPage().setPageSize(5);
////		priceDTO2.setTypeId(23);
//		List<PriceDO> priceList = priceService.queryPriceByTypeId(23,null,null,5);
//		out.put("paperMarketList", priceList);
//
//		// 废纸日评周评
//		List<PriceCategoryDTO> paperCommentList=priceService.queryPriceCategoryInfoByParentIdAndAssistId(14,null,10);
//		out.put("paperCommentList", paperCommentList);
//		// 报价首页广告
////		int placeId[] = {50,44,45};
////		Integer topNum = 1;
////		List<AdsDTO> adsListato = adsService.queryAdsByPlaceId(placeId, topNum);
////		for (int i = 0; i < placeId.length; i++) {
////			out.put("adsList" + placeId[i], adsListato);
////		}
//		// 行情综述
//		// 废金属行情综述 metal
//		List<NewsForFrontDTO> newsList = new ArrayList<NewsForFrontDTO>();
//		newsList = newsService.queryNewsListForFront(13, "10421001", null, null, 6, null, null, false);
//		out.put("metalPriceList", newsList);
//		// 废塑料行情综述 plastic
//		newsList = newsService.queryNewsListForFront(13, "10421000", null, null, 6, null, null, false);
//		out.put("plasticPriceList", newsList);
//		// 废纸橡胶行情综述 paper
//		newsList = newsService.queryNewsListForFront(13, "10421002", null, null, 4, null, null, false);
//		out.put("otherWasteList", newsList);
//
//		// Charts
//		List<ChartCategoryDO> chartCategoryList = chartCategoryService
//				.queryChartCategoryByParentId(0);
//		out.put("chartCategoryList", chartCategoryList);
	}

	@RequestMapping
	public ModelAndView list(String code, Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/list_"+code+".htm");
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.NEWS);
//		headDTO.setPageTitle("报价资讯中心_废金属价格|废塑料价格|废纸价格|废橡胶价格|再生技术_${site_name}");
//		headDTO.setPageKeywords("废料交易中心,废料交易,废料供求,求购废料,回收,废金属,废塑料,复活商机");
//		headDTO.setPageDescription("${site_name}的资讯及报价中心以废金属价格,废塑料价格,废纸价格,"
//				+ "废橡胶价格为主要报价,以再生技术,再生政策法规,再生行业要闻为辅的信息中心,"
//				+ "为再生行业提供及时有效的废金属价格,废塑料价格,废纸及废橡胶价格和再生行业的信息!");
//		setSiteInfo(new PageHeadDTO(), out);
//		out.put("stringUtils", new StringUtils());
//
//		// 页面右侧部分
//		right(code, out);
//
//		// 需要查询出两种类别
//		Integer id = 0;
//		Integer otherId = null;
//
//		if (code.equals("metal")) {
//			id = 17;
//			otherId = 18;
//		} else if (code.equals("plastic")) {
//
//			id = 20;
//		} else if (code.equals("paper")) {
//			id = 13;
//			otherId = 15;
//		}
//		List<PriceCategoryDO> list = priceCategoryService.queryPriceCategoryByParentId(id);
//		out.put("list", list);
//		Integer pageSize = 3;
//		List<PriceCategoryDTO> bigList = priceService.queryPriceList(id, null, pageSize);
//		out.put("bigList", bigList);
//
//		PriceCategoryDO priceCategory = priceCategoryService.queryPriceCategoryById(id);
//		out.put("priceCategory", priceCategory);
//		if (otherId != null) {
//			// 如果是废金属或废纸 需要显示两个
//			PriceCategoryDO otherPriceCategory = priceCategoryService
//					.queryPriceCategoryById(otherId);
//
//			List<PriceCategoryDO> list1 = priceCategoryService
//					.queryPriceCategoryByParentId(otherId);
//			out.put("otherPriceCategory", otherPriceCategory);
//			out.put("otherList", list1);
//
//			List<PriceCategoryDTO> otherBigList = priceService.queryPriceList(otherId, null,
//					pageSize);
//			out.put("otherBigList", otherBigList);
//		}
	}

	@RequestMapping
	public ModelAndView moreList(Integer parentId, Integer assistTypeId, Integer id, String code,
			Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/moreList_p"+id+"_t"+code+".htm");
//		// 页面右侧部分
//		right(code, out);
//		
//		// 行情综述
//		List<NewsForFrontDTO> newsList = new ArrayList<NewsForFrontDTO>();
//		newsList = newsService.queryNewsListForFront(13, null, null, null, 50, null, null, false);
//		out.put("bList", newsList);
//
//		String keyword = "";
//		PriceCategoryDO priceCategory = new PriceCategoryDO();
////		StringBuffer childs = new StringBuffer();
//		if (id != null) {
//			priceCategory = priceCategoryService.queryPriceCategoryById(id);
//		} else if (assistTypeId != null) {
//			priceCategory = priceCategoryService.queryPriceCategoryById(assistTypeId);
//		} else {
//			priceCategory = priceCategoryService.queryPriceCategoryById(parentId);
//		}
//		out.put("priceCategory", priceCategory);
//		keyword = priceCategory.getName();
		//list=priceCategoryLinkService.queryAllByPriceId(id)
//		PriceCategoryLinkDO priceCategoryLinkDO = new PriceCategoryLinkDO();
//		priceCategoryLinkDO.setPriceTypeId(id);
//		priceCategoryLinkDO.setPriceAssistTypeId(assistTypeId);
//		List<PriceCategoryLinkDO> linkList = priceCategoryService
//				.queryPriceCategoryLink(priceCategoryLinkDO);
//		out.put("linkList", linkList);

//		List<PriceCategoryDO> list = priceCategoryService.queryPriceCategoryByParentId(parentId);
////		for (PriceCategoryDO c : list) {
////			childs.append("," + c.getName() + "价格");
////		}
//		List<PriceCategoryDTO> listDTO = new ArrayList<PriceCategoryDTO>();
//		for (PriceCategoryDO aDo : list) {
//			PriceCategoryDTO priceCategoryDTO = new PriceCategoryDTO();
////			PriceDTO priceDTO = new PriceDTO();\
//			Integer typeid=id;
//			Integer assistId=aDo.getId();
//			if (id == null) {
//				typeid = aDo.getId();
//				assistId=assistTypeId;
//			}
//			List<PriceDO> aDtos = priceService.queryPriceByTypeId(typeid,null,assistId,6);
//			if (aDtos != null && aDtos.size()>0) {
//				priceCategoryDTO.setPriceChildDO(aDtos);
//			}
//			listDTO.add(priceCategoryDTO);
//		}
//		if (list.size() < 1) {
//			return new ModelAndView(
//					new RedirectView("priceList_t" + parentId + "_" + code + ".htm"));
//		}
//		out.put("id", id);
//		out.put("parentId", parentId);
//		out.put("assistTypeId", assistTypeId);
//		out.put("list", list);
//		out.put("bigList", listDTO);
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.NEWS);
//		headDTO.setPageTitle(keyword+"价格_今日"+keyword+"价格_"+keyword+"价格行情_${site_name}");
//		headDTO.setPageKeywords(keyword+"价格,"+keyword+"行情,今日"+keyword+"价格");
//		headDTO.setPageDescription("${site_name}"+keyword+"报价，汇聚实时的"+keyword+"价格,"+keyword+"价格行情。助你掌握行业最及时的"+keyword+"价格行情；帮你了解"+keyword+"价格,"+keyword+"价格行情等行业的行情、动态。");
//		setSiteInfo(headDTO, out);
//		out.put("keyword", keyword);
//		return null;
	}

	@RequestMapping
	public ModelAndView priceList(Integer id, Integer categoryId, PageDto<PriceDO> page, Integer parentId, String code,
			Integer assistTypeId, Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/priceList_t"+categoryId+"_c"+id+"_"+code+".htm");
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.NEWS);
//		headDTO.setPageTitle("报价资讯中心_废金属价格|废塑料价格|废纸价格|废橡胶价格|再生技术_${site_name}");
//		headDTO.setPageKeywords("废料交易中心,废料交易,废料供求,求购废料,回收,废金属,废塑料,复活商机");
//		headDTO.setPageDescription("${site_name}的资讯及报价中心以废金属价格,废塑料价格,废纸价格,"
//				+ "废橡胶价格为主要报价,以再生技术,再生政策法规,再生行业要闻为辅的信息中心,"
//				+ "为再生行业提供及时有效的废金属价格,废塑料价格,废纸及废橡胶价格和再生行业的信息!");
//		setSiteInfo(headDTO, out);
////		out.put("stringUtils", new StringUtils());
//		/**
//		 * 页面右侧部分
//		 */
//		right(code, out);
//		// 行情综述
//
//		List<NewsForFrontDTO> newsList = new ArrayList<NewsForFrontDTO>();
//		newsList = newsService.queryNewsListForFront(13, null, null, null, 10, null, null, false);
//		out.put("bList", newsList);
////		PriceCategoryDO category = new PriceCategoryDO();
//		
////		if (p == null || !StringUtils.isNumber(p)) {
////			p = "1";
////		}
////		PriceDTO priceDTO = new PriceDTO();
////		priceDTO.getPage().setPageSize(48);
////		priceDTO.getPage().setStartIndex(
////				(Integer.valueOf(p) - 1) * priceDTO.getPage().getPageSize());
//		
//		Integer targetId=null;
//		if (id != null) {
//			targetId=id;
////			category = priceCategoryService.queryPriceCategoryById(id);
//		}
//		if (parentId != null) {
//			targetId=parentId;
//		}
//		if (assistTypeId != null) {
//			PriceCategoryDO assistType = priceCategoryService.queryPriceCategoryById(assistTypeId);
//			out.put("assistType", assistType);
//		}
//		if(targetId!=null){
//			PriceCategoryDO category = priceCategoryService.queryPriceCategoryById(targetId);
//			out.put("category", category);
//		}
//		
//		/**
//		 * 获得总记录数
//		 */
////		PageDto pageDTO = priceDTO.getPage();
////		pageDTO.setTotalRecords(priceService.queryPriceCountByTypeId(priceDTO));
//		/**
//		 * 根据总记录数计算总页数
//		 */
//		//		pageDTO.totalPages();
//		// 报价列表
////		List<PriceDO> list = priceService.queryPriceByTypeId(id,parentId,assistTypeId,48);
////		out.put("list", list);
//		
//
//		if (categoryId != null) {
//			PriceCategoryDO priceCategoryDO = priceCategoryService
//					.queryPriceCategoryById(categoryId);
//			out.put("priceCategoryDO", priceCategoryDO);
//			List<PriceCategoryDO> priceCatgoryList = priceCategoryService
//					.queryPriceCategoryByParentId(categoryId);
//			out.put("priceCatgoryList", priceCatgoryList);
//			out.put("suffixUrl", "&id=" + id + "&categoryId=" + categoryId);
//		}
//		
//		if(page.getPageSize()==null){
//			page.setPageSize(48);
//		}
//		page = priceService.pagePriceByType(id, parentId, assistTypeId, page);
//		out.put("page", page);
		
//		out.put("totalPages", pageDTO.getTotalPages());
//		out.put("currentPage", Integer.valueOf(p));
//		out.put("suffixUrl", "");

	}

	@RequestMapping
	public ModelAndView priceDetails(Integer id, String code, HttpServletRequest req,
			Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/priceDetails_"+id+"_"+code+".htm");
//		out.put("openStatus", ParamUtils.getInstance().getValue("baseConfig", "open_status"));
//		out.put("stringUtils", new StringUtils());
//		if (code == null) {
//			code = queryParentById(id);
//		}
//		List<PriceDataDO> priceDataDOList = priceDataService.queryPriceDataByPriceId(id);
//		if(priceDataDOList!=null&&priceDataDOList.size()>0){
//			out.put("priceDataDOList", priceDataDOList);
//			out.put("i", priceDataDOList.size());
//		}
//		// 查询报价信息
//		PriceDO price = priceService.queryPriceByIdForEdit(id).getPrice();
//		String tag[] = price.getTags().split(",");
//		out.put("tag", tag);
//		out.put("price", price);
//		int inDays = 0;
//		try {
//			if (price.getGmtOrder() != null) {
//				inDays = DateUtil.getIntervalDays(new Date(), price.getGmtOrder());
//			}
//		} catch (ParseException e) {
//		}
//		out.put("daysInterval", inDays);
//		PriceCategoryDO categoryDO = priceCategoryService.queryPriceCategoryById(price.getTypeId());
//		out.put("categoryDO", categoryDO);
//		// 查询上一篇文章
//		PriceDO onPrice = priceService.queryOnPriceById(id);
//		if (onPrice != null && price.getTypeId().equals(onPrice.getTypeId())) {
//			out.put("onPrice", onPrice);
//		}
//		// 查询下一篇文章
//		PriceDO downPrice = priceService.queryDownPriceById(id);
//		if (downPrice != null && price.getTypeId().equals(downPrice.getTypeId())) {
//			out.put("downPrice", downPrice);
//		}
//		// 文章标签
//		List<TagsInfoDO> tagsInfoList = tagsArticleService
//				.queryTagListFromTagsArticleRelationByArticleId("10351000", id);
//		out.put("tagsInfoList", tagsInfoList);
//		StringBuffer tags = new StringBuffer();
//		for (TagsInfoDO t : tagsInfoList) {
//			tags.append(t.getName() + ",");
//		}
//		out.put("id", id);
//		right(code, out);
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.NEWS);
//		String pindao = "";
//		if (code == "metal") {
//			pindao = "废金属";
//		} else if (code == "plastic") {
//			pindao = "废塑料";
//		} else {
//			pindao = "废纸废橡";
//		}
//		PriceCategoryDO priceCategory = priceCategoryService.queryPriceCategoryById(price.getTypeId());
//		out.put("priceCategory", priceCategory);
//		out.put("code", code);
//		
//		headDTO.setPageTitle(price.getTitle() + "_" + pindao + "价格_报价资讯中心_${site_name}");
//		headDTO.setPageKeywords(tags.toString());
//		headDTO.setPageKeywords(price.getTitle());
//		if(priceCategory!=null && priceCategory.getName()!=null){
//			headDTO.setPageKeywords(headDTO.getPageKeywords()+","+priceCategory.getName());
//		}
//		headDTO.setPageDescription("欢迎阅读'"+price.getTitle()+"'这条资讯。ZZ91再生网汇聚实时、最新的废金属价格、废塑料价格、废橡胶价格和废纸价格行情。");
//		setSiteInfo(headDTO, out);
	}

	@RequestMapping
	public void login(Map<String, Object> out) {
		out.put("openStatus", ParamUtils.getInstance().getValue("baseConfig", "open_status"));
	}


//	private String queryParentById(Integer id) {
//		//if (code == null) {
//		String code = "";
//		PriceDTO pdto=priceService.queryPriceByIdForEdit(id);
//		
//		PriceDO priceDO = pdto==null?null:pdto.getPrice();
//		if (priceDO != null) {
//			PriceCategoryDO priceCategoryDO = priceCategoryService.queryPriceCategoryById(priceDO
//					.getTypeId());
//			if (priceDO.getTypeId() <= 8) {
//				code = priceCategoryDO.getName();
//			} else if (priceCategoryDO != null) {
//				Integer parentId = priceCategoryDO.getParentId();
//				for (int i = 0; i < 6; i++) {
//					priceCategoryDO = priceCategoryService.queryPriceCategoryById(parentId);
//					if (priceCategoryDO != null) {
//						if (priceCategoryDO.getParentId() != null) {
//							if (priceCategoryDO.getParentId() == 1) {
//								code = priceCategoryDO.getName();
//								break;
//							} else {
//								parentId = priceCategoryDO.getParentId();
//							}
//						}
//					}
//				}
//			}
//		}
//		return code;
//	}

//	private void right(String code, Map<String, Object> out) {
//		List<PriceCategoryDO> list1 = new ArrayList<PriceCategoryDO>();
//		List<ForPriceDTO> priceList = new ArrayList<ForPriceDTO>();
////		PriceDTO priceDTO = new PriceDTO();
//		
//		// 再生通最新会员
//		out.put("companyList", companyService.queryGoodCompany(20));
//
//		
//		String mainCode = null;
//		Integer parentId = null;
//		String signType = null;
//
//		if (code.equals("metal") || code.equals("废金属")) {
//			code = "metal";
//			mainCode = "1000";
//			parentId = 9;
//			signType = "metal";
//
//			// 金属废料报价
//			list1 = priceCategoryService.queryPriceCategoryByParentId(17);
//			out.put("categoryList", list1.subList(0, 9));
//
////			priceDTO.setParentId(17);
////			priceDTO.setLimitSize(6);
//			priceList = priceService.queryPriceByParentId(17,6);
//			out.put("priceList", priceList);
//
//			// 金属新料/期货价格行情
//			list1 = priceCategoryService.queryPriceCategoryByParentId(18);
//			out.put("newCategoryList", list1);
//
////			priceDTO.setParentId(18);
////			priceDTO.setLimitSize(6);
//			priceList = priceService.queryPriceByParentId(18,6);
//			out.put("newPriceList", priceList);
//
//			// 各地金属价格查询
//			list1 = priceCategoryService.queryPriceCategoryByParentId(3);
//			out.put("areaList", list1);
//
//		} else if (code.equals("plastic") || code.equals("废塑料")) {
//			code = "plastic";
//			mainCode = "1001";
//			parentId = 11;
//			signType = "plastic";
//			// 塑料价格行情
//			//			priceDTO.setParentId(21);
//			//			priceDTO.setLimitSize(6);
//			//			priceList = priceService.queryPriceByParentId(priceDTO);
//			//			out.put("plasticList", priceList);
//			// 塑料新料市厂价格
////			priceDTO.getPage().setStartIndex(0);
////			priceDTO.getPage().setPageSize(6);
////			priceDTO.setParentId(60);
//			List<PriceDO> sList = priceService.queryPriceByTypeId(null,60,null,6);
//			out.put("sList", sList);
//			// 国内石化出厂价
////			priceDTO.getPage().setStartIndex(0);
////			priceDTO.getPage().setPageSize(6);
////			priceDTO.setParentId(61);
//			List<PriceDO> cList = priceService.queryPriceByTypeId(null,61,null,6);
//			out.put("cList", cList);
//
////			PriceDTO priceDTO1 = new PriceDTO();
//			// 油价
////			priceDTO1.setTypeId(190);
////			priceDTO1.getPage().setStartIndex(0);
////			priceDTO1.getPage().setPageSize(6);
//			List<PriceDO> oilList = priceService.queryPriceByTypeId(190,null,null,6);
//			out.put("oilList", oilList);
//			// 各地废塑料
////			priceDTO.setParentId(22);
////			priceDTO.setLimitSize(6);
//			priceList = priceService.queryPriceByParentId(22,6);
//			out.put("newPlasticList", priceList);
//		} else {
//			// 废纸废橡胶价格行情
//			code = "paper";
//			mainCode = "1002";
//			parentId = 13;
//			signType = "otherWaste";
//
//			PriceCategoryDO paperCategoryDO = priceCategoryService.queryPriceCategoryById(13);
//			PriceCategoryDO rubberCategoryDO = priceCategoryService.queryPriceCategoryById(15);
//			out.put("paperCategoryDO", paperCategoryDO);
//			out.put("rubberCategoryDO", rubberCategoryDO);
//
////			priceDTO.setParentId(13);
////			priceDTO.setLimitSize(6);
//			priceList = priceService.queryPriceByParentId(13,6);
//			out.put("paperList", priceList);
//
////			priceDTO.setParentId(15);
////			priceDTO.setLimitSize(6);
//			priceList = priceService.queryPriceByParentId(15,6);
//			out.put("rubberList", priceList);
//
//		}
//		out.put("code", code);
//		// 行情导读
//		List<NewsForFrontDTO> newsList = new ArrayList<NewsForFrontDTO>();
//		newsList = newsService.queryNewsListForFront(228, null, null, null, 6, null, null, false);
//		out.put("priceTagsList", newsList);
//
//		// 最新资讯
////		BbsPostDTO bbsPostDTO1 = new BbsPostDTO();
////		PageDto pageDto1 = new PageDto();
////		pageDto1.setDir("desc");
////		pageDto1.setSort("a.reply_time");
////		pageDto1.setStartIndex(0);
////		pageDto1.setPageSize(8);
////		bbsPostDTO1.setPageDto(pageDto1);
////		bbsPostDTO1.setSignType1(signType);
////		List<BbsPostDTO> newPriceTagsList = bbsService.queryBbsPostByTagsSignTypes(bbsPostDTO1);
////		out.put("newPriceTagsList", newPriceTagsList);
//		// 最新报价
////		priceDTO.setLimitSize(8);
////		priceDTO.setParentId(parentId);
//		priceList = priceService.queryPriceByParentId(parentId, 8);
//		out.put("priceList", priceList);
//		// 最新供求
//		out.put("productsList", productsService.queryNewestProducts(mainCode, null, 14));
//		// 热门标签
//		List<TagsInfoDO> tagsList = tagsArticleService.queryTagListByTagCatAndArtCat("4", mainCode, 20);
//		out.put("tagsList", tagsList);
//		out.put("dateUtil", new DateUtil());
//		out.put("nowTime", new Date());
//	}
}
