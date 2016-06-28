/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.price.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.price.PriceDataDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.information.ChartDataForIndexDTO;
import com.ast.ast1949.dto.price.ForPriceDTO;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.dto.price.PriceDto2;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.information.ChartDataService;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.price.PriceDataService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.CNToHexUtil;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class PriceController extends BaseController {

	@Autowired
	private PriceCategoryService priceCategoryService;
	@Autowired
	private PriceService priceService;
	@Autowired
	private PriceDataService priceDataService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private CompanyService companyService;
	@Resource
	private ChartDataService chartDataService;

	private static final String PLASTIC_SEARCH_KEY = "塑料";
	private static final String METAL_SEARCH_KEY = "金属";

	@RequestMapping
	public ModelAndView priceSearch(
			Map<String, Object> out, HttpServletResponse response,HttpServletRequest request)
			throws UnsupportedEncodingException {
		String title=request.getParameter("title");
		String p=request.getParameter("p");
		setSiteInfo(new PageHeadDTO(), out);
		// List<ForPriceDTO> priceList;
		// List<PriceCategoryDO> list1;
		p = StringUtils.getPageIndex(p);
		out.put("currentPage", Integer.valueOf(p));
		if(StringUtils.isNotEmpty(title)){
			title = title.replaceAll("astojh", "#");
			if(!StringUtils.isContainCNChar(title)){
				title = StringUtils.decryptUrlParameter(title);
			}
		}
		PageDto<PriceDO> page = new PageDto<PriceDO>();
		page.setPageSize(40);
		page.setStartIndex((Integer.valueOf(p) - 1) * page.getPageSize());
		page = priceService.pagePriceBySearchEngine(title, null, page);
		List<PriceDO> allPriceList = page.getRecords();
		out.put("title", title);
		out.put("totalPages", page.getTotalPages());
		out.put("allPriceList", allPriceList);

		// 各地金属价格查询
		// List<PriceCategoryDO> list =
		// priceCategoryService.queryPriceCategoryByParentId(3);
		// out.put("areaList", list);
		// 塑料价格行情
		// 塑料新料市厂价格
		List<PriceDO> sList = priceService
				.queryPriceByTypeId(null, 60, null, 6);
		out.put("sList", sList);
		// 国内石化出厂价
		List<PriceDO> cList = priceService
				.queryPriceByTypeId(null, 61, null, 6);
		out.put("cList", cList);
		// 油价 C
		List<PriceDO> oilList = priceService.queryPriceByTypeId(190, null,
				null, 6);
		out.put("oilList", oilList);
		// 各地废塑料
		// PriceDTO priceDTO = new PriceDTO();
		// priceDTO.setParentId(22);
		// priceList = priceService.queryPriceByParentId(22,6);
		// out.put("newPlasticList", priceList);
		// /废纸废橡胶价格行情
		// PriceCategoryDO paperCategoryDO =
		// priceCategoryService.queryPriceCategoryById(13);
		// out.put("paperCategoryDO", paperCategoryDO);
		// PriceCategoryDO rubberCategoryDO =
		// priceCategoryService.queryPriceCategoryById(15);
		// out.put("rubberCategoryDO", rubberCategoryDO);

		// priceDTO.setParentId(13);
		// priceList = priceService.queryPriceByParentId(13,6);
		// out.put("paperList", priceList);

		// priceDTO.setParentId(15);
		// priceList = priceService.queryPriceByParentId(15,6);
		// out.put("rubberList", priceList);
		// 金属废料报价
		// list1 = priceCategoryService.queryPriceCategoryByParentId(17);
		// out.put("categoryList", list1.subList(0, 9));

		// priceDTO.setParentId(17);
		// priceList = priceService.queryPriceByParentId(17, 6);
		// out.put("priceList", priceList);
		// 金属新料/期货价格行情
		// list1 = priceCategoryService.queryPriceCategoryByParentId(18);
		// out.put("newCategoryList", list1);

		// priceDTO.setParentId(18);
		// priceList = priceService.queryPriceByParentId(18,6);
		// out.put("newPriceList", priceLiSEARCH_KRYst);

		// out.put("companyList", companyService.queryGoodCompany(20));

		// List<TagsInfoDO> tagsList =
		// tagsArticleService.queryTagListByTagCatAndArtCat("4", "", 20);
		// out.put("tagsList", tagsList);

		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView oldIndex(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out) {

		// 设置页面头部信息
		// PageHeadDTO headDTO = new PageHeadDTO();
		// headDTO.setTopNavIndex(NavConst.NEWS);
		// headDTO.setPageTitle("报价资讯中心_废金属价格|废塑料价格|废纸价格|废橡胶价格|再生技术_${site_name}");
		// headDTO
		// .setPageKeywords("废金属价格,废塑料价格,废纸价格,废橡胶价格,再生技术,市场行情,行情评论,废铜价格,废钢价格,废铁价格");
		// headDTO
		// .setPageDescription("${site_name}的资讯及报价中心以废金属价格,废塑料价格,废纸价格,废橡胶价格为主要报价,以再生技术,再生政策法规,再生行业要闻为辅的信息中心,为再生行业提供及时有效的废金属价格,废塑料价格,废纸及废橡胶价格以及再生行业的相关资讯!");
		// setSiteInfo(headDTO, out);
		//		
		SeoUtil.getInstance().buildSeo("index", out);

		// Map<Integer, List<PriceDto2>> qMetalList =
		// priceService.queryPriceOfParentCategory(64, 1);
		// Map<Integer, List<PriceDto2>> qYMetalList =
		// priceService.queryPriceOfParentCategory(65, 1);
		// out.put("qMetalList", qMetalList);
		// out.put("qYMetalList", qYMetalList);

		Map<Integer, List<PriceDto2>> map = new HashMap<Integer, List<PriceDto2>>();
		// 利用搜索引擎搜索和塑料匹配的最新信息,组装到map里面
		out.put("qSuList", priceService.queryLatestPriceUsePageEngine(map,
				PLASTIC_SEARCH_KEY));

		// Map<Integer, List<PriceDto2>> qSuList = priceService
		// .queryPriceOfParentCategory(20, 1);
		// out.put("qSuList", qSuList);

		// 金属新料价格
		Map<Integer, List<PriceDto2>> metalMap = new HashMap<Integer, List<PriceDto2>>();
		out.put("qJinList", priceService.queryLatestPriceUsePageEngine(
				metalMap, METAL_SEARCH_KEY));
		Map<Integer, List<PriceDto2>> metalNewmaterialsList = priceService
				.queryPriceOfParentCategory(18, 2);
		out.put("metalNewmaterialsList", metalNewmaterialsList);

		List<PriceCategoryDO> list = priceCategoryService
				.queryPriceCategoryByParentId(13);
		out.put("paperCatgeory", list.subList(3, list.size()));

		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		return new ModelAndView();

	}

	@RequestMapping
	public ModelAndView list(HttpServletResponse response, String code,
			Map<String, Object> out) {

		// 页面右侧部分
		right(code, out, null);

		// 需要查询出两种类别
		Integer id = 0;
		Integer otherId = null;
		// PageHeadDTO headDTO = new PageHeadDTO();
		if (code.equals("metal")) {
			id = 17;
			otherId = 18;
			// headDTO
			// .setPageTitle("废金属价格_废铜_废铝_废铁_废钢_有色金属_贵金属_报价资讯中心_${site_name}");
			// headDTO
			// .setPageKeywords("废金属价格,废金属行情,废铜价格,废铝价格,废铁价格,废钢价格,有色金属价格,贵金属价格");
			// headDTO
			// .setPageDescription("${site_name}废金属报价资讯中心，这里为您精选了废铜，废铝，废铁，废钢，生铁，有色金属，贵金属等废金属最新国内外市场价格信息，让您掌握最新的废金属行情!");
			SeoUtil.getInstance().buildSeo(
					"list",
					new String[] { "废金属", "废铜_废铝_废铁_废钢_有色金属_贵金属" },
					new String[] { "废金属", "废金属",
							"废铜价格,废铝价格,废铁价格,废钢价格,有色金属价格,贵金属价格" },
					new String[] { "废金属", "废铜，废铝，废铁，废钢，生铁，有色金属，贵金属等" }, out);
		} else if (code.equals("plastic")) {
			id = 11;
			// headDTO
			// .setPageTitle("废塑料价格_PP_POM_PS_ABS_PC_PVC_PA_ PE_PET_报价资讯中心_${site_name}");
			// headDTO
			// .setPageKeywords("废塑料价格,废塑料行情,PP价格,POM价格,PS价格,ABS价格,PC价格,PVC价格,PA价格,PMMA价格,PE价格,PET价格,HDPE价格,LDPE价格");
			// headDTO
			// .setPageDescription("${site_name}废塑料报价资讯中心，这里为您精选了PP，POM，PS，ABS，PC，PVC，PA，PMMA，PE，PET，HDPE，LDPE等废塑料最新国内外市场价格信息，让您掌握最新的废塑料行情!");
			SeoUtil
					.getInstance()
					.buildSeo(
							"list",
							new String[] { "废塑料",
									"PP_POM_PS_ABS_PC_PVC_PA_ PE_PET" },
							new String[] { "废塑料", "废塑料",
									"PP价格,POM价格,PS价格,ABS价格,PC价格,PVC价格,PA价格,PMMA价格,PE价格,PET价格,HDPE价格,LDPE价格" },
							new String[] { "废塑料",
									"PP，POM，PS，ABS，PC，PVC，PA，PMMA，PE，PET，HDPE，LDPE等" },
							out);
		} else if (code.equals("paper")) {
			id = 13;
			otherId = 15;
			// headDTO
			// .setPageTitle("废纸价格_废橡胶价格_国内废纸价格_国外废纸价格_国内橡胶价格_国外橡胶价格_报价资讯中心{site_name}");
			// headDTO
			// .setPageKeywords("废纸价格,废橡胶价格,废纸行情,废橡胶行情,国内废纸价格,国外废纸价格,美废价格,日废价格,欧废价格,废纸网上报价,国内橡胶价格,国外橡胶价格");
			// headDTO
			// .setPageDescription("${site_name}废纸、废橡胶报价资讯中心报价中心，这里为您精选了国内废纸，美废，日废，国内橡胶，国外橡胶等废纸、废橡胶最新国内外市场价格信息，让您掌握最新的废纸、废橡胶行情!");
			SeoUtil
					.getInstance()
					.buildSeo(
							"list",
							new String[] { "废纸价格_废橡胶",
									"国内废纸价格_国外废纸价格_国内橡胶价格_国外橡胶价格" },
							new String[] { "废纸价格,废橡胶", "废纸行情,废橡胶",
									"国内废纸价格,国外废纸价格,美废价格,日废价格,欧废价格,废纸网上报价,国内橡胶价格,国外橡胶价格" },
							new String[] { "废纸、废橡胶", "国内废纸，美废，日废，国内橡胶，国外橡胶等" },
							out);
		}
		List<PriceCategoryDO> list = priceCategoryService
				.queryPriceCategoryByParentId(id);
		if (code.equals("plastic")) {
			for (int i = 0 ; i < list.size(); i++) {
				if (list.get(i).getId()==21 || list.get(i).getId()==22) {
					list.remove(i);
					i--;
				}
			}
		}
		out.put("list", list);
		Integer pageSize = 3;
		List<PriceCategoryDTO> bigList = priceService.queryPriceList(id, null,
				pageSize);
		out.put("bigList", bigList);

		PriceCategoryDO priceCategory = priceCategoryService
				.queryPriceCategoryById(id);
		out.put("priceCategory", priceCategory);
		if (otherId != null) {
			// 如果是废金属或废纸 需要显示两个
			PriceCategoryDO otherPriceCategory = priceCategoryService
					.queryPriceCategoryById(otherId);

			List<PriceCategoryDO> list1 = priceCategoryService
					.queryPriceCategoryByParentId(otherId);
			out.put("otherPriceCategory", otherPriceCategory);
			out.put("otherList", list1);

			List<PriceCategoryDTO> otherBigList = priceService.queryPriceList(
					otherId, null, pageSize);
			out.put("otherBigList", otherBigList);
		}

		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView moreList(Integer parentId, Integer assistTypeId,
			Integer id, String code, Map<String, Object> out,
			HttpServletResponse response) {

		// 页面右侧部分
		right(code, out, null);

		// String keyword = "";
		Integer categoryId = id;
		if (id != null) {
			categoryId = id;
		} else if (assistTypeId != null) {
			categoryId = assistTypeId;
		} else if (parentId != null) {
			categoryId = parentId;
		}
		PriceCategoryDO priceCategory = priceCategoryService
				.queryPriceCategoryById(categoryId);
		out.put("priceCategory", priceCategory);

		List<PriceCategoryDO> list = priceCategoryService
				.queryPriceCategoryByParentId(parentId);
		if (list.size() < 1) {
			return new ModelAndView(new RedirectView("priceList_t" + parentId
					+ "_" + code + ".htm"));
		}

		Map<Integer, List<PriceDO>> priceMap = new HashMap<Integer, List<PriceDO>>();
		for (PriceCategoryDO category : list) {
			Integer typeid = id;
			Integer assistId = category.getId();
			if (id == null) {
				typeid = category.getId();
				assistId = assistTypeId;
			}
			if (parentId != null && parentId.intValue() == 18
					&& typeid.intValue() != 66) {
				priceMap.put(category.getId(), priceService.queryPriceByTypeId(
						null, typeid, assistId, 6));
			} else {
				priceMap.put(category.getId(), priceService.queryPriceByTypeId(
						typeid, null, assistId, 6));
			}
		}

		out.put("id", id);
		out.put("parentId", parentId);
		out.put("assistTypeId", assistTypeId);

		out.put("categoryList", list);
		out.put("priceMap", priceMap);

		// String pindao="";
		// if(StringUtils.isNotEmpty(code)){
		// if("metal".equals(code));
		// pindao="废金属";
		// if("plastic".equals(code)){
		// pindao="废塑料";
		// }
		// if("paper".equals(code)){
		// pindao="综合废料";
		// }
		// }
		// 设置页面头部信息
		// PageHeadDTO headDTO = new PageHeadDTO();
		// headDTO.setTopNavIndex(NavConst.NEWS);
		// headDTO.setPageTitle(priceCategory.getName() + "价格_"
		// + priceCategory.getName() + "价格行情_今日" + priceCategory.getName()
		// + "价格_报价资讯中心_${site_name}");
		// headDTO.setPageKeywords(priceCategory.getName() + "价格,"
		// + priceCategory.getName() + "价格行情,今日" + priceCategory.getName()
		// + "价格");
		// headDTO.setPageDescription("${site_name}" + priceCategory.getName()
		// + "报价，为您提供今日" + priceCategory.getName() + "价格行情。助你掌握行业最及时的"
		// + priceCategory.getName() + "价格行情；帮你了解今日"
		// + priceCategory.getName() + "价格行情、市场动态。");
		// setSiteInfo(headDTO, out);
		if (priceCategory == null
				|| StringUtils.isEmpty(priceCategory.getName())) {
			priceCategory = new PriceCategoryDO();
			priceCategory.setName("废料");
		}
		SeoUtil.getInstance().buildSeo("moreList",
				new String[] { priceCategory.getName() },
				new String[] { priceCategory.getName() },
				new String[] { priceCategory.getName() }, out);

		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView priceList(Integer id, Integer categoryId,
			PageDto<PriceDO> page, Integer parentId, String code,
			Integer assistTypeId, Map<String, Object> out,
			HttpServletResponse response) {

		out.put("id", id);
		out.put("parentId", parentId);
		out.put("assistTypeId", assistTypeId);
		out.put("categoryId", categoryId);

		// out.put("stringUtils", new StringUtils());

		// Integer typeId=216;
		String pindao = "";
		if (StringUtils.isNotEmpty(code)) {
			if ("metal".equals(code))
				;
			pindao = "废金属";
			if ("plastic".equals(code)) {
				pindao = "废塑料";
			}
			if ("paper".equals(code)) {
				pindao = "综合废料";
			}
		}

		/**
		 * 页面右侧部分
		 */
		right(code, out, null);
		// 行情综述

		// List<NewsForFrontDTO> newsList = new ArrayList<NewsForFrontDTO>();
		// newsList = newsService.queryNewsListForFront(13, null, null, null,
		// 10, null, null, false);
		// out.put("bList", priceService.queryPriceByTypeId(typeId, null, null,
		// 10));
		// PriceCategoryDO category = new PriceCategoryDO();

		// if (p == null || !StringUtils.isNumber(p)) {
		// p = "1";
		// }
		// PriceDTO priceDTO = new PriceDTO();
		// priceDTO.getPage().setPageSize(48);
		// priceDTO.getPage().setStartIndex(
		// (Integer.valueOf(p) - 1) * priceDTO.getPage().getPageSize());

		Integer targetId = null;
		if (id != null) {
			targetId = id;
			// category = priceCategoryService.queryPriceCategoryById(id);
		}
		if (parentId != null) {
			targetId = parentId;
		}
		Integer currentpage = page.getStartIndex() / page.getPageSize() + 1;
		if (assistTypeId != null) {
			PriceCategoryDO assistType = priceCategoryService
					.queryPriceCategoryById(assistTypeId);
			out.put("assistType", assistType);
			SeoUtil.getInstance()
					.buildSeo(
							"priceList",
							new String[] { assistType.getName(),
									currentpage.toString() },
							new String[] { assistType.getName() },
							new String[] { assistType.getName(), pindao }, out);
		}
		if (targetId != null) {
			PriceCategoryDO category = priceCategoryService
					.queryPriceCategoryById(targetId);
			out.put("category", category);
			SeoUtil.getInstance()
					.buildSeo(
							"priceList",
							new String[] { category.getName(),
									currentpage.toString() },
							new String[] { category.getName() },
							new String[] { category.getName(), pindao }, out);
		}

		/**
		 * 获得总记录数
		 */
		// PageDto pageDTO = priceDTO.getPage();
		// pageDTO.setTotalRecords(priceService.queryPriceCountByTypeId(priceDTO));
		/**
		 * 根据总记录数计算总页数
		 */
		// pageDTO.totalPages();
		// 报价列表
		// List<PriceDO> list =
		// priceService.queryPriceByTypeId(id,parentId,assistTypeId,48);
		// out.put("list", list);

		if (categoryId != null) {
			PriceCategoryDO priceCategoryDO = priceCategoryService.queryPriceCategoryById(categoryId);
			out.put("priceCategoryDO", priceCategoryDO);
			List<PriceCategoryDO> priceCatgoryList = priceCategoryService.queryPriceCategoryByParentId(categoryId);
			out.put("priceCatgoryList", priceCatgoryList);
			out.put("suffixUrl", "&id=" + id + "&categoryId=" + categoryId);
		}
		if (id!=null && (22 == id || 60 == id)) {
			out.put("priceList", priceService.queryPriceByParentId(id, 48));
		} else {
			page.setPageSize(48);
			page.setSort("gmt_order");
			page.setDir("desc");
			page = priceService.pagePriceByType(id, parentId, assistTypeId,page);
			
			// 没有数据page
			if(page==null||page.getRecords().size()==0){
				PriceCategoryDO priceCategoryDO = (PriceCategoryDO) out.get("category");
				page = priceService.pagePriceBySearchEngine(priceCategoryDO.getName(), new PriceDO(), page);
			}
			
			out.put("page", page);
		}

		// out.put("totalPages", pageDTO.getTotalPages());
		// out.put("currentPage", Integer.valueOf(p));
		// out.put("suffixUrl", "");
//		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		return new ModelAndView();

	}

	// 流量统计
	@RequestMapping
	public ModelAndView countFlow(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		priceService.updateRealClickNumberById(id);
		// 日志统计 报价资讯id 的点击数
		LogUtil.getInstance().log("zz91_price", "click_count",
				HttpUtils.getInstance().getIpAddr(request), "id:" + id);
		return null;
	}

	@RequestMapping
	public ModelAndView priceDetails(Integer id, String code,
			HttpServletRequest req, Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			PageDto<PriceDataDO> page) throws HttpException, IOException {
		SsoUser sessionUser = getCachedUser(request);
		out.put("sessionUser", sessionUser);
		if (code == null) {
			code = queryParentById(id);
		}
		List<PriceDataDO> priceDataDOList = priceDataService
				.queryPriceDataByPriceId(id, page);
		if (priceDataDOList != null && priceDataDOList.size() > 0) {
			out.put("priceDataDOList", priceDataDOList);
			out.put("i", priceDataDOList.size());
		}

		// 查询报价信息
		PriceDTO dto = priceService.queryPriceByIdForEdit(id);
		if (dto == null || dto.getPrice() == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("front") + "/error.htm");
		}
		PriceDO price = dto.getPrice();

		Document doc = Jsoup.parse(price.getContent());
		// 修改table标签中的style属性
		doc.select("table").attr("style", "width:100% ;height:100%;");
		// 清除导致表格超出页面的属性，width
		doc.select("table").removeAttr("height");
		doc.select("table").removeAttr("width");
		price.setContent(doc.select("body").html());
		// 标签不得为空
		if (StringUtils.isNotEmpty(price.getTags())) {
			String tag[] = price.getTags().split(",");
			out.put("tag", tag);
			if (tag.length > 0) {
				out.put("theUrlKey", URLEncoder.encode(tag[0], "utf-8"));
			}
			Map<String, String> encodeTagsMap = new HashMap<String, String>();
			for (String t : tag) {
				encodeTagsMap.put(t, CNToHexUtil.getInstance().encode(t));
			}
			out.put("encodeTagsMap", encodeTagsMap);
		}

		out.put("price", price);
		int inDays = 0;// 保存 比较两个日期相差的天数的结果
		try {
			if (price.getGmtOrder() != null) {
				inDays = DateUtil.getIntervalDays(new Date(), price
						.getGmtOrder());
			}
		} catch (ParseException e) {
		}
		out.put("daysInterval", inDays);
		PriceCategoryDO categoryDO = priceCategoryService
				.queryPriceCategoryById(price.getTypeId());
		out.put("categoryDO", categoryDO);
		// 查询上一篇文章
		PriceDO onPrice = priceService.queryOnPriceById(id);
		if (onPrice != null && price.getTypeId().equals(onPrice.getTypeId())) {
			out.put("onPrice", onPrice);
		}
		// 查询下一篇文章
		PriceDO downPrice = priceService.queryDownPriceById(id);
		if (downPrice != null
				&& price.getTypeId().equals(downPrice.getTypeId())) {
			out.put("downPrice", downPrice);
		}
		
		out.put("id", id);
		right(code, out, price.getTypeId());
		code = (String) out.get("code");
		
		//根据code来显示相关的类别 matal:废金属, plastic:废塑料
		if(code!=null && code.equals("metal")){
			out.put("cacheCode", "100810021000");
			out.put("cacheArea", "100810021001");
		}else if(code!=null && code.equals("plastic")){
			out.put("cacheCode", "100810021002");
			out.put("cacheArea", "100810021003");
		}else {
			out.put("cacheCode", "100810021004");
			out.put("cacheArea", "100810021005");
		}

		// 获取类别名
		PriceCategoryDO priceCategory = priceCategoryService.queryPriceCategoryById(price.getTypeId());
		out.put("priceCategory", priceCategory);
		// 设置页面头部信息
		String pindao = "";
		String keywords = price.getTitle();

		if ("metal".equals(code)) {
			pindao = "废金属";
			if (priceCategory != null && priceCategory.getName() != null) {
				keywords = keywords + ",金属," + priceCategory.getName() + "," + pindao + "价格,今日" + pindao + "价格";
			}
		} else if ("plastic".equals(code)) {
			pindao = "废塑料";
			if (priceCategory != null && priceCategory.getName() != null) {
				keywords = keywords + ",塑料,废料,价格,行情,POM," + pindao + "价格,今日" + pindao + "价格";
			}
		} else {
			pindao = "综合废料";
			if (priceCategory != null && priceCategory.getName() != null) {
				keywords = keywords + "," + pindao + "价格,行情,行情动态," + pindao + "价格,今日" + pindao + "价格";
			}
		}
		SeoUtil.getInstance().buildSeo("price",new String[] { price.getTitle(), pindao },new String[] { keywords },new String[] { price.getTitle(), pindao }, out);
		if (inDays <= 3) {
			PageCacheUtil.setNoCDNCache(response);
		}

		// 获取现货商城报价 要求一定要塑料类别才读取
//		if ("plastic".equals(out.get("code"))) {
//			List<ProductsDto> list = productsService.queryProductsForSpotByCondition(null, null, null, 5);
//			List<ProductsDto> nlist = new ArrayList<ProductsDto>();
//			for (ProductsDto obj : list) {
//				// 获取三级类目的类别名
//				String mainCode = obj.getProducts().getCategoryProductsMainCode();
//				Integer length = 4;
//				if (mainCode == null) {
//					mainCode = "1001";
//				}
//				if (mainCode.length() >= 12) {
//					length = 12;
//				} else if (mainCode.length() > 8) {
//					length = 8;
//				}
//				obj.getProducts().setTitle(
//						CategoryProductsFacade.getInstance().getValue(
//								mainCode.substring(0, length)));
//				nlist.add(obj);
//			}
//			out.put("spotList", nlist);
//		}
		
		// 相关文章
		PageDto<PriceDO> pPage = new PageDto<PriceDO>();
		pPage.setPageSize(10);
		pPage = priceService.pagePriceBySearchEngine(priceCategory.getName(),new PriceDO(), pPage);
		out.put("similarList", pPage.getRecords());
		
		// 相关最新资讯
		String newsData = "";
		String relatedWords = "";
		try {
			if ("metal".equals(code)) {
				relatedWords = "金属";
			} else if ("plastic".equals(code)) {
				relatedWords = "塑料";
			} else {
				relatedWords = "纸";
			}
			String key= URLEncoder.encode(relatedWords, HttpUtils.CHARSET_UTF8);
			newsData = HttpUtils.getInstance().httpGet(AddressTool.getAddress("pyapp")+"/news/javagetnewslist.html?keywords="+key+"=&num=6", HttpUtils.CHARSET_UTF8);
		} catch (Exception e) {
			newsData = null;
		}
		if (StringUtils.isNotEmpty(newsData)) {
			out.put("newsHtml", newsData);
		}
		
		// 12条相关产品推荐
		PageDto<ProductsDto> proPage = new PageDto<ProductsDto>();
		proPage.setPageSize(12);
		ProductsDO product = new ProductsDO();
		product.setTitle(priceCategory.getName());
		proPage = productsService.pageProductsBySearchEngine(product, "", true, proPage);
		List<ProductsDto> recommendProList = proPage.getRecords();
		if(recommendProList.size()<12){
			PageDto<ProductsDto> mproPage = new PageDto<ProductsDto>();
			mproPage.setPageSize(12-proPage.getRecords().size());
			product.setTitle(relatedWords); // 主类别 金属 塑料 综合
			mproPage = productsService.pageProductsBySearchEngine(product, "", true, mproPage);
			recommendProList.addAll(mproPage.getRecords());
		}
		out.put("recommendProList",recommendProList);
		
		return new ModelAndView();
		
		// 选择模版 检索为空，不是上下板块
//		if (priceTemplateService.queryByPriceId(id) == null) {
//			return new ModelAndView();
//		} else {
//			if ("metal".equals(code)) {
//				out.put("bjddList", priceService.queryListByParentId(17, 13));
//			} else if ("plastic".equals(code)) {
//				out.put("bjddList", priceService.queryListByParentId(11, 13));
//			} else {
//				out.put("bjddList", priceService.queryListByIntArray(
//						new Integer[] { 231, 23, 25, 26, 27, 28, 29, 30, 190 },
//						13));
//			}
//			return new ModelAndView("priceDetailsUD");
//		}
		
	}

	@RequestMapping
	public void login(Map<String, Object> out) {
	}

	private String queryParentById(Integer id) {
		String code = "";
		PriceDTO pdto = priceService.queryPriceByIdForEdit(id);

		PriceDO priceDO = pdto == null ? null : pdto.getPrice();
		if (priceDO != null) {
			PriceCategoryDO priceCategoryDO = priceCategoryService
					.queryPriceCategoryById(priceDO.getTypeId());
			if (priceDO.getTypeId() != null && priceDO.getTypeId() <= 8) {
				if (priceCategoryDO != null) {
					code = priceCategoryDO.getName();
				} else {
					code = "";
				}
			} else if (priceCategoryDO != null) {
				Integer parentId = priceCategoryDO.getParentId();
				for (int i = 0; i < 6; i++) {
					priceCategoryDO = priceCategoryService
							.queryPriceCategoryById(parentId);
					if (priceCategoryDO != null) {
						if (priceCategoryDO.getParentId() != null) {
							if (priceCategoryDO.getParentId() == 1) {
								code = priceCategoryDO.getName();
								break;
							} else {
								parentId = priceCategoryDO.getParentId();
							}
						}
					}
				}
			}
		}
		return code;
	}

	private void right(String code, Map<String, Object> out, Integer parentId) {
		// List<PriceCategoryDO> list1 = new ArrayList<PriceCategoryDO>();
		List<ForPriceDTO> priceList = new ArrayList<ForPriceDTO>();
		// PriceDTO priceDTO = new PriceDTO();

		// List<Company> companyList = companyService.queryGoodCompany(20);
		List<Company> companyList = companyService.queryCompanyZstMember(20,
				null);
		// 再生通最新会员
		out.put("companyList", companyList);

		String mainCode = null;
		// String signType = null;
		Integer typeId = 216;
		if (code.equals("metal") || code.equals("废金属")) {
			code = "metal";
			mainCode = "1000";
			// signType = "metal";

			// 各地金属价格查询
			// list1 = priceCategoryService.queryPriceCategoryByParentId(3);
			// out.put("areaList", list1);

			// 金属废料报价
			// list1 = priceCategoryService.queryPriceCategoryByParentId(17);
			// out.put("categoryList", list1.subList(0, 9));

			// priceList = priceService.queryPriceByParentId(17,6);
			// out.put("rightPriceList", priceList);

			// 金属新料/期货价格行情
			// list1 = priceCategoryService.queryPriceCategoryByParentId(18);
			// out.put("newCategoryList", list1);

			// priceList = priceService.queryPriceByIndex("metal_price_new",8);
			// out.put("rightPriceListLem", priceList);

			// 底部最新报价
			// if(parentId!=null){
			// priceList = priceService.queryPriceAndCategoryByTypeId(parentId,
			// 8);
			// }
			// out.put("priceList", priceList);
			out.put("typeId", typeId);
		} else if (code.equals("plastic") || code.equals("废塑料")) {
			code = "plastic";
			mainCode = "1001";
			// signType = "plastic";
			typeId = 217;
			// 塑料价格行情
			// priceDTO.setParentId(21);
			// priceDTO.setLimitSize(6);
			// priceList = priceService.queryPriceByParentId(priceDTO);
			// out.put("plasticList", priceList);
			// 塑料新料市厂价格
			List<PriceDO> sList = priceService.queryPriceByTypeId(null, 60,
					null, 6);
			out.put("sList", sList);
			// 国内石化出厂价
			List<PriceDO> cList = priceService.queryListByTypeId(61, 6);
			out.put("cList", cList);

			// 油价
			List<PriceDO> oilList = priceService.queryPriceByTypeId(190, null,
					null, 6);
			out.put("oilList", oilList);
			// 各地废塑料
			// priceList = priceService.queryPriceByParentId(22,6);
			// out.put("newPlasticList", priceList);

			// 底部最新报价
			// if(parentId!=null){
			// priceList = priceService.queryPriceAndCategoryByTypeId(parentId,
			// 8);
			// }else{
			// priceList = priceService.queryPriceByIndex("plastic_price",8);
			// }
			// out.put("priceList", priceList);
			out.put("typeId", typeId);
		} else {
			// 废纸废橡胶价格行情
			code = "paper";
			mainCode = "1002";
			// signType = "otherWaste";
			typeId = 220;

			PriceCategoryDO paperCategoryDO = priceCategoryService
					.queryPriceCategoryById(13);
			PriceCategoryDO rubberCategoryDO = priceCategoryService
					.queryPriceCategoryById(15);
			out.put("paperCategoryDO", paperCategoryDO);
			out.put("rubberCategoryDO", rubberCategoryDO);

			priceList = priceService.queryPriceByParentId(15, 6);
			out.put("rubberList", priceList);

			priceList = priceService.queryPriceByParentId(13, 8);
			out.put("paperList", priceList);

			// 底部最新报价
			// if(parentId!=null){
			// priceList = priceService.queryPriceAndCategoryByTypeId(parentId,
			// 8);
			// }
			// out.put("priceList", priceList);
			out.put("typeId", typeId);
		}
		out.put("mainCode", mainCode);
		out.put("code", code);
		// 行情导读
		// List<NewsForFrontDTO> newsList = new ArrayList<NewsForFrontDTO>();
		// newsList = newsService.queryNewsListForFront(228, null, null, null,
		// 6, null, null, false);
		out.put("priceTypeId", typeId);
		out.put("priceTagsList", priceService.queryPriceByTypeId(typeId, null,
				null, 6));

		// 最新资讯
		// BbsPostDTO bbsPostDTO1 = new BbsPostDTO();
		// PageDto pageDto1 = new PageDto();
		// pageDto1.setDir("desc");
		// pageDto1.setSort("a.reply_time");
		// pageDto1.setStartIndex(0);
		// pageDto1.setPageSize(8);
		// bbsPostDTO1.setPageDto(pageDto1);
		// bbsPostDTO1.setSignType1(signType);
		// List<BbsPostDO> newPriceTagsList=;
		// out.put("newPriceTagsList",
		// bbsPostService.querySimplePostByCategory(1, 8));

		// 最新供求
		out.put("productsList", productsService.queryNewestProducts(mainCode,
				null, 14));
		// 热门标签
		// List<TagsInfoDO> tagsList =
		// tagsArticleService.queryTagListByTagCatAndArtCat("4", mainCode, 20);
		// out.put("tagsList", tagsList);
		out.put("dateUtil", new DateUtil());
		out.put("nowTime", new Date());
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

	// @RequestMapping
	// public ModelAndView getBottomNavigate(HttpServletRequest
	// request,Map<String , Object>out,String code){
	// // 悬浮框资讯
	// out.put("susList",
	// JSONArray.fromObject(priceService.queryPriceByTypeId(RIPINID.get(code),
	// null, null, 6)).toString());
	// // 悬浮框供求
	// out.put("suspList",
	// JSONArray.fromObject(productsService.queryProductsByMainCode(PRODUCT_CODE.get(code),
	// null, 6)).toString());
	// // 悬浮框短信报价
	// // String
	// responseText="["+HttpUtils.getInstance().httpGet(AddressTool.getAddress("exadmin")+"/reborn-admin/sms/main/selectNewSmsForPriceByCode.htm?categoryCode="+PRODUCT_CODE.get(code)+"",
	// HttpUtils.CHARSET_UTF8)+"]";
	// // if(StringUtils.isNotEmpty(responseText) &&
	// responseText.startsWith("[")){
	// // JSONArray jobj = JSONArray.fromObject(responseText);
	// // out.put("jsonArray", jobj);
	// // }
	// return null;
	// }

	@RequestMapping
	public void index(Map<String, Object> out) {
		// 废金属辅助类别
		List<PriceCategoryDO> jsFZList = priceCategoryService
				.queryPriceCategoryByParentId(3);
		out.put("jsFZList", jsFZList);

		// 现货有色 67
		out.put("xhList", priceService.queryListByParentId(67, 14));

		// 贵金属 68
		out.put("gjsList", priceService.queryListByParentId(68, 3));

		// 生铁 66
		out.put("stList", priceService.queryListByTypeId(66, 3));

		// 废金属评论 32
		out.put("jsplList", priceService.queryListByTypeId(32, 7));

		// 废金属评论 33
		out.put("jszbList", priceService.queryListByTypeId(33, 7));

		// 期所价格 期货id ：30
		List<ChartDataForIndexDTO> ccList = chartDataService
				.queryChartDataForPriceByParentId(30);
		out.put("ccList", ccList);

		// 全国废塑料价格 20
		out.put("qgslList", priceService.queryListByTypeId(20, 7));

		// 商城报价 282
		out.put("scbjList", priceService.queryListByTypeId(282, 1));

		// 各地废塑料价格 22
		out.put("gdslList", priceService.queryListByParentId(22, 8));

		// 塑料再生料 98
		out.put("slzsList", priceService.queryListByTypeId(98, 8));

		// 塑料期货 233
		out.put("slqhList", priceService.queryListByTypeId(233, 8));

		// 废塑料辅助类别 4
		out.put("slFZList", priceCategoryService
				.queryPriceCategoryByParentId(4));

		// 美国废塑料价格 62
		out.put("mgslList", priceService.queryListByTypeId(62, 7));

		// 欧洲废塑料价格 63
		out.put("ozslList", priceService.queryListByTypeId(63, 7));

		// 新料市场价 60
		out.put("xlscjList", priceService.queryListByParentId(60, 7));

		// 新料出厂价 61
		out.put("xlccjList", priceService.queryListByTypeId(61, 7));

		// 油价快报 190
		out.put("yjkbList", priceService.queryListByTypeId(190, 6));

		// 废塑料评论 34
		out.put("slplList", priceService.queryListByTypeId(34, 12));

		// 废塑料周报 35
		out.put("slzbList", priceService.queryListByTypeId(35, 12));

		// 综合行情综述 220
		out.put("zhhqzsList", priceService.queryListByTypeId(220, 8));

		// 废橡胶价格 8：30
		out.put("fxjjgList", priceService.queryListByTypeId(30, 8));

		// 废纸价格 13
		out.put("fzjgList", priceService.queryListByParentId(13, 6));

		// 综合废料评论 14
		out.put("zhflplList", priceService.queryListByParentId(14, 8));

		Date date = new Date();
		out.put("to", DateUtil.toString(date, "yyyy-MM-dd"));
		out.put("from", DateUtil.toString(DateUtil.getDateAfterDays(date, -14),
				"yyyy-MM-dd"));
		// seo t k d
		SeoUtil.getInstance().buildSeo("index", out);
	}

	final static Map<String, String> typeNameMap = new HashMap<String, String>();
	static {

	}
}