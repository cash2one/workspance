package com.ast.ast1949.map.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.information.ExhibitService;
import com.ast.ast1949.service.map.MapService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.site.WebBaseDataStatService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

@Controller
public class MapController extends BaseController {
	@Autowired
	private PriceService priceService;
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private WebBaseDataStatService webBaseDataStatService;
	@Resource
	private ExhibitService exhibitService;
	@Resource
	private MapService mapService;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private InquiryService inquiryService;

	@RequestMapping
	public ModelAndView province(Map<String, Object> out,
			PageDto<CompanyPriceSearchDTO> pager, HttpServletRequest request,
			HttpServletResponse response, String plateCategoryCode,
			String category, String productCode, String areaCode,
			String areaKey, Integer startIndex, String keywords,
			String listType, PageDto<ProductsDto> pageProduct)
			throws MalformedURLException, IOException {
		// 网站数据统计
		webBaseDataStatService.indexTotal(out);
		// 基础数据判断
		buildBaseUrl(out, productCode, areaCode);

		// 地区 热门供求搜索
		productCode = out.get("productCode").toString();
		areaKey = out.get("areaKey").toString();
		String areaName = out.get("areaName").toString();
		// pageProduct = (PageDto<ProductsDto>)
		// MemcachedUtils.getInstance().getClient().get("productsList");
		// if(pageProduct==null){
		// pageProduct = new PageDto<ProductsDto>();
		pageProduct.setPageSize(30);
		
		
       pageProduct = mapService.queryBySearchEnegine(areaKey, keywords,
				productCode, pageProduct);
		// }
		// pageProduct =
		// mapService.queryBySearchEnegine(areaKey,"",productCode,pageProduct);
		// out.put("productsList",
		// mapService.indexHotProductsByArea(productCode,areaKey, null, null,
		// 30, null));
		
		out.put("productsList", pageProduct.getRecords());
	
        
		// 推荐热门网商
		out.put("vipList", mapService.indexHotCompanysByArea(productCode,
				areaKey, null, 9, null));

		
		// 地区 网商报价
	
		out.put("comppriceList", mapService.indexLatestCompanysPriceByArea(
				productCode, areaKey, 30));
		
		out.put("areaName", areaName);
		out.put("keywords", keywords);
		out.put("headUrl", areaName);
		out.put("listType", listType);
		// 互助头条
		out.put("postByType3", bbsPostService
				.queryPostWithContentByType("3", 4));
		
		
		// 最新展会
		out.put("exhibitList", exhibitService.queryNewestExhibit(null,
						null, 2));
		
		
		// 优质客户

		out.put("yzCompanyList", mapService.indexHotCompanysByArea(productCode,
				areaKey, keywords, 10, null));

		
		// 优质供求
		// out.put("yzProductList",
		// mapService.indexHotProductsByArea(productCode,
		// areaKey, keywords, null, 10, null));
		PageDto<ProductsDto> buttomProduct = new PageDto<ProductsDto>();
		buttomProduct.setPageSize(10);
		buttomProduct = mapService.queryBySearchEnegine(areaKey, keywords,
				productCode, pageProduct);
		out.put("yzProductList", buttomProduct.getRecords());
		
		
		// 最新报价
		out.put("companyPriceList", companyPriceService
				.queryCompanyPriceByRefreshTime(null, 60));
		// 行情综述
		PageDto<PriceDO> page = new PageDto<PriceDO>();
		page.setPageSize(6);
		page.setSort("gmtCreated");
		page.setDir("desc");
		
        out.put("jinshu", priceService.pagePriceByType(216, null, null, page)
				.getRecords());

		out.put("suliao", priceService.pagePriceByType(217, null, null, page)
				.getRecords());

		
		page.setPageSize(4);
		
		out.put("yuanyou", priceService.pagePriceByType(220, null, null, page)
				.getRecords());
		
		
		out.put("adsCode", areaAdsCode.get(areaKey));

		SeoUtil.getInstance().buildSeo(
				"index",
				new String[] { out.get("name").toString(),
						out.get("productName").toString() },
				new String[] { out.get("name").toString(),
						out.get("productName").toString() },
				new String[] { out.get("name").toString(),
						out.get("productName").toString() }, out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView areaChild(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {

		List<CategoryDO> list = new ArrayList<CategoryDO>();
		Map<String, String> map = CategoryFacade.getInstance().getChild(
				parentCode);
		if (map == null) {
			return printJson(list, out);
		}

		for (Entry<String, String> m : map.entrySet()) {
			CategoryDO c = new CategoryDO();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}

		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView info(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String mainCode, String keywords, String areaKey, String areaName,
			Integer size) throws IOException {

		// 基础数据判断
		buildBaseUrl(out, null, areaName);

		areaKey = out.get("areaKey").toString();

		if (StringUtils.isEmpty(areaKey)) {
			areaKey = "100110001010";
		}
		if (areaKey == null) {
			areaKey = "100110001010";
		}

		if (StringUtils.isEmpty(keywords)) {
			keywords = "pp";
		} else {
			keywords = URLDecoder.decode(keywords, "utf-8");
		}
		// try {
		// keywords = StringUtils.decryptUrlParameter(keywords);
		// out.put("keywords", keywords);
		// out.put("encodeKeywords", URLEncoder.encode(keywords, "utf-8"));
		// } catch (UnsupportedEncodingException e) {
		// }
		out.put("keywords", keywords);
		out.put("areaName", areaName);
		out.put("productName", "废料");
		out.put("areaKey", areaKey);
		out.put("headUrl", areaName);
		// 获取供应和求购 列表
		out.put("gList", mapService.indexHotProductsByArea(null, areaKey,
				keywords, "10331000", size, null));
		out.put("pList", mapService.indexHotProductsByArea(null, areaKey,
				keywords, "10331001", size, null));

		out.put("name", CategoryFacade.getInstance().getValue(areaKey));
		// 报价
		PageDto<PriceDO> page = new PageDto<PriceDO>();
		page.setSort("gmt_order");
		page.setDir("desc");
//		page = priceService.queryPricePaginationListByTitle(keywords, page);
		page = priceService.pagePriceBySearchEngine(keywords,null,page);
		
		if (page.getRecords().size() > 8) {
			out.put("priceList", page.getRecords().subList(0, 8));
		} else {
			out.put("priceList", page.getRecords());
		}

		// 资讯
		List<PostDto> infoList = mapService.queryPostByKey(keywords, 8);
		// PageDto<PostDto> p = new PageDto<PostDto>();
		// p = bbsPostService.pagePostBySearchEngine(keywords, p);
		// if (p.getRecords().size() > 8) {
		// out.put("infoList", p.getRecords().subList(0, 8));
		// } else {
		// out.put("infoList", p.getRecords());
		// }
		out.put("infoList", infoList);
		// 跟关键词相关的标签
		out.put("tagsList", TagsUtils.getInstance().queryTagsByTag(
				keywords.replace("/", ""), TagsUtils.ORDER_SEARCH, 20));
		// seo
		SeoUtil.getInstance().buildSeo("info",
				new String[] { out.get("name").toString(), keywords },
				new String[] { out.get("name").toString(), keywords },
				new String[] { out.get("name").toString(), keywords }, out);
		return new ModelAndView();

	}

	@RequestMapping
	public ModelAndView index(Map<String, Object> out) {
		SeoUtil.getInstance().buildSeo(out);
		return new ModelAndView();
	}

	@RequestMapping
	public void list(Map<String, Object> out, String productType,
			String keywords, String areaName, String areaKey,
			Integer startIndex, String province, String listType,
			PageDto<ProductsDto> page) throws MalformedURLException, IOException {
		// 基础数据判断
		buildBaseUrl(out, productType, areaName);

		String seoStr = "";

		if (StringUtils.isNotEmpty(keywords)) {
			out.put("tagsList", TagsUtils.getInstance().queryTagsByTag(
					keywords.replace("/", ""), TagsUtils.ORDER_SEARCH, 20));
			out.put("searchKey", keywords);
			out.put("searchKeyEncode", URLEncoder.encode(keywords, "utf-8"));
			seoStr = keywords;
		}

		String productCode = out.get("productCode").toString();
		if (StringUtils.isEmpty(seoStr)) {
			seoStr = out.get("productName").toString();
		}
		areaKey = out.get("areaKey").toString();
		if ("p".equals(listType)) {
			// 优质供求
			page.setPageSize(10);
			out.put("page", mapService.queryBySearchEnegine(areaKey, keywords,productCode, page));
		} else {
			// 优质客户
			out.put("page", mapService.pageHotCompanysByArea(productCode,areaKey, keywords, 10, page));
		}

		out.put("headUrl", "list" + "-" + out.get("areaName").toString());
		// $!{areaName}#if($!{productType})
		out.put("listType", listType);
		out.put("keywords", keywords);
		// 页面分页 url组装
		String url = "";
		url = "-" + out.get("areaName").toString();
		if (StringUtils.isNotEmpty(productType)) {
			url += "-" + productType;
		}
		if (StringUtils.isNotEmpty(keywords)) {
			url += "-" + URLEncoder.encode(keywords, "utf-8");
		}
		if ("p".equals(listType)) {
			url += "-p";
		}
		out.put("pageUrl", url);
		Integer i = page.getStartIndex() / page.getPageSize() + 1;
		// int size = page.getCurrentPage()/page.getPageSize()+1;
		SeoUtil.getInstance().buildSeo(
				"list",
				new String[] { out.get("name").toString(), seoStr,
						String.valueOf(i) },
				new String[] { out.get("name").toString(), seoStr },
				new String[] { out.get("name").toString(), seoStr }, out);
	}

	public void buildBaseUrl(Map<String, Object> out, String productCode,
			String areaCode) {
		// 判断 页面信息
		if (StringUtils.isNotEmpty(productCode)) {
			out.put("url", "-" + productCode);
			out.put("formUrl", areaCode + "-" + productCode);
		} else {
			out.put("url", "");
			out.put("formUrl", areaCode);
		}

		if ("feijinshu".equals(productCode)) {
			out.put("productCode", "1000");
			out.put("productName", "废金属");
		} else if ("feisuliao".equals(productCode)) {
			out.put("productCode", "1001");
			out.put("productName", "废塑料");
		} else if ("zhfeiliao".equals(productCode)) {
			out.put("productCode", "1002");
			out.put("productName", "综合废料");
		} else if (StringUtils.isEmpty(productCode)) {
			productCode = "";
			out.put("productCode", "");
			out.put("productName", "废料");
		}
		out.put("productType", productCode);

		// 地区 判断
		if (StringUtils.isEmpty(areaCode)) {
			areaCode = "zhejiang";
		}
		out.put("areaKey", AREA_CODE.get(areaCode));
		// 页头询盘
		out.put("listInquiry", inquiryService.queryScrollInquiry());

		out.put("areaName", areaCode);
		// 地区中文名
		out.put("name", CategoryFacade.getInstance().getValue(
				out.get("areaKey").toString()));
	}

	// 制定地区的广告的 areaAdsCode 集合
	final static Map<String, String> areaAdsCode = new HashMap<String, String>();
	static {
		areaAdsCode.put("100110001009", "463");
		areaAdsCode.put("100110001018", "464");
		areaAdsCode.put("100110001010", "465");
		areaAdsCode.put("100110001014", "466");
		areaAdsCode.put("100110001000", "467");
		areaAdsCode.put("100110001008", "468");
		areaAdsCode.put("100110001001", "469");
		areaAdsCode.put("100110001002", "470");
		areaAdsCode.put("100110001015", "471");
		areaAdsCode.put("100110001017", "472");
	}
	// 地区code 对应拼音
	final static Map<String, String> AREA_CODE = new HashMap<String, String>();
	static {
		AREA_CODE.put("zhejiang", "100110001010");
		AREA_CODE.put("guangdong", "100110001018");
		AREA_CODE.put("shandong", "100110001014");
		AREA_CODE.put("jiangsu", "100110001009");
		AREA_CODE.put("beijing", "100110001000");
		AREA_CODE.put("shanghai", "100110001008");
		AREA_CODE.put("tianjin", "100110001001");
		AREA_CODE.put("henan", "100110001015");
		AREA_CODE.put("hebei", "100110001002");
		AREA_CODE.put("hunan", "100110001017");

	}
}
