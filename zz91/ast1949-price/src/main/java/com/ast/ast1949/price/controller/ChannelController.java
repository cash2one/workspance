/**
] * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-7
 */
package com.ast.ast1949.price.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.seo.SeoUtil;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class ChannelController extends BaseController {

	@Resource
	private PriceService priceService;
	@Resource
	private PriceCategoryService priceCategoryService;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private ProductsService productsService;
//	@Resource
//	private BbsPostService bbsPostService;
//	@Resource
//	private AdsService adsService;
//	@Resource
//	private NewsService newsService;
//	@Resource
//	private ChartCategoryService chartCategoryService;

	@RequestMapping
	public ModelAndView index(HttpServletResponse response, Integer id, Map<String, Object> out) {
		CompanyPriceDTO companyPriceDTO = new CompanyPriceDTO();
		
		PriceDTO priceDTO1 = new PriceDTO();
		PriceCategoryDO priceCategory;
		Integer typeId1 = null;
		Integer typeId2 = null;
//		String signType = null;
		String categoryCompanyPriceCode = null;
		String categoryCompanyPriceCode1 = null;
		String categoryProductsMainCode = null;
		// 设置页面头部信息
		String title = "";
		String seoTitle = "";
		String keywords="";
		String description="";
		String code = "";
		if (id == 1) {
			title = "废金属";
//			seoTitle = "废金属网_废金属价格|废金属资讯|废金属行情_报价资讯中心_${site_name}";
//			keywords="废金属网,废金属价格,废金属资讯,废金属行情,废金属市场,废钢,废铁,废铜,废铝,废金属";
//			description="${site_name}旗下废金属网,为您提供各地区废钢价格、废铁价格、废铜价格、废铝价格等废金属价格、市场动态、行情资讯、供应、求购信息,是废金属商人销售产品、拓展市场及网络推广的首选网站。";
			code = "metal";
			SeoUtil.getInstance().buildSeo("channel",new String[]{title},new String[]{title,"废钢,废铁,废铜,废铝,废金属"},new String[]{title,"废钢价格、废铁价格、废铜价格、废铝价格等"}, out);
		} else if (id == 2) {
			title = "废塑料";
//			seoTitle = "废塑料网_废塑料价格|废塑料资讯|废塑料行情_报价资讯中心_${site_name}";
//			keywords="废塑料网,废塑料价格,废塑料资讯,废塑料行情, 废塑料市场,abs ,eva,pa,pp,ps,pe,pvc,pc,pet,塑料颗粒";
//			description="${site_name}旗下废塑料网,为您提供各地ABS价格,EVA价格,PA价格,PP价格,PS价格,PE价格,PVC价格,PC价格,PET价格,塑料颗粒价格等废塑料价格、市场动态、行情资讯、供应、求购信息,是废塑料商人销售产品、拓展市场及网络推广的首选网站。";
			code = "plastic";
			out.put("adcode", "plastic");
			SeoUtil.getInstance().buildSeo("channel",new String[]{title},new String[]{title,"abs ,eva,pa,pp,ps,pe,pvc,pc,pet,塑料颗粒"},new String[]{title,"ABS价格,EVA价格,PA价格,PP价格,PS价格,PE价格,PVC价格,PC价格,PET价格,塑料颗粒价格等"}, out);
		} else {
			title = "废纸废橡胶";
//			seoTitle = "废纸废橡胶网_废纸价格|废橡胶价格|废纸价格行情|废橡胶价格行情_报价资讯中心_${site_name}";
//			keywords="废纸废橡胶网,废纸价格,废橡胶价格,废纸资讯,废橡胶资讯,废纸行情,废橡胶行情";
//			description="${site_name}旗下废纸废橡胶网,为您提供废纸废橡胶报价、废纸废橡胶行情、废纸废橡胶再生技术、等废纸废橡胶资讯信息,是以废纸废橡胶为核心的一个专业频道版块。";
			code = "paper";
			SeoUtil.getInstance().buildSeo("zonghe",out);
		}
		out.put("title", title);
		out.put("code", code);

		out.put("id", id);
		Integer pricetypeId = 216;
		if (id == 1) {// 废金属网
			typeId1 = 32;
			typeId2 = 33;
			// 行情综述
//			signType = "metal";
			// 废金属市场动态
//			listForPrice = priceService.queryPriceByParentId(19,15);
//			out.put("marketList", listForPrice);
			// 废金属最新供应信息,最新求购信息
			categoryProductsMainCode = "1000";
			// 企业报价
			categoryCompanyPriceCode = "1001";
			// 废金属价格行情
			PriceDO priceDO = priceService.queryTopGmtOrderByParentId(17);
			out.put("metalGmtOrder", priceDO.getGmtOrder());
//			list = priceCategoryService.queryPriceCategoryByParentId(17);
//			out.put("metalList", list);
			// 金属新料/期货价格行情
//			List<PriceCategoryDTO> priceList1 = new ArrayList<PriceCategoryDTO>();
//			list = priceCategoryService.queryPriceCategoryByParentId(18);
//			priceList1 = priceService.queryPriceAndCategory(list);
//			Map<Integer, String> map=new HashMap<Integer, String>();
//			for(PriceCategoryDO category: list){
//				map.put(category.getId(), category.getName());
//			}
//			out.put("metalPriceNew", map);
//			out.put("priceList1", priceService.queryPriceByIndex("metal_price_new", 12));

			// 废金属期货
//			out.put("metalPriceList", priceService.queryPriceByParentId(64, 5));
			//			// 废金属网上报价51
			priceDTO1.setTypeId(51);
			priceDTO1.setLimitSize(5);
			
			// Charts
//			List<ChartCategoryDO> chartCategoryList = chartCategoryService
//					.queryChartCategoryByParentId(0);
//			out.put("chartCategoryList", chartCategoryList);

		} else if (id == 2) { // 废塑料网
			pricetypeId = 217;
			// 日评 周评
			typeId1 = 34;
			typeId2 = 35;
			// 行情综述
//			signType = "plastic";
			// 市场动态
//			listForPrice = priceService.queryEachPriceByParentId(22);
//			out.put("marketList", listForPrice.subList(0, 15));
			// 废塑料供应信息,求购信息
			categoryProductsMainCode = "1001";
			// 废塑料企业报价，塑料颗粒报价
			categoryCompanyPriceCode = "10001000";
			categoryCompanyPriceCode1 = "10001001";
			// 塑料废料价格行情
			PriceDO priceDO = priceService.queryTopGmtOrderByParentId(20);
			if(priceDO!=null){
				out.put("metalGmtOrder", priceDO.getGmtOrder());
			}

//			list = priceCategoryService.queryPriceCategoryByParentId(20);
//			out.put("plasticList", list);

			// 塑料新料价格行情
//			listForPrice = priceService.queryPriceByParentId(60,10);
//			out.put("newPlasticList", listForPrice);
			// 石化出厂价

//			listForPrice = priceService.queryPriceAndCategoryByTypeId(61, 5);
//			out.put("factoryList", listForPrice);
			// 网上报价137
			priceDTO1.setTypeId(137);
			priceDTO1.setLimitSize(5);

		} else {// 综合废料网
			pricetypeId = 220;
			// 废纸日评 废纸周评
			typeId1 = 36;
			typeId2 = 37;
			// 行情综述
//			signType = "otherWaste";
			// 市场动态
//			priceDTO.setTypeId(23);
//			priceDTO.getPage().setStartIndex(0);
//			priceDTO.getPage().setPageSize(15);
//			priceList = priceService.queryPriceByTypeId(23,null,null,15);
//			out.put("paperMarketList", priceList);
			// 综合废料供应信息,求购信息
			categoryProductsMainCode = "1004";
			// 综合废料企业报价
			categoryCompanyPriceCode = "1003";
			// 废纸价格行情
//			listForPrice = priceService.queryPriceByParentId(13,10);
//			out.put("paperList", listForPrice);

			priceCategory = priceCategoryService.queryPriceCategoryById(15);
			out.put("rubberPrice", priceCategory);
			// 废橡胶价格
//			listForPrice = priceService.queryPriceByParentId(15,10);
//			out.put("rubberList", listForPrice);

			priceCategory = priceCategoryService.queryPriceCategoryById(23);
			out.put("marketPrice", priceCategory);
			// 废纸废橡胶网上报价25
			priceDTO1.setTypeId(25);
			priceDTO1.setLimitSize(10);

		}
		// 日评
		out.put("typeId1", typeId1);
//		priceList = priceService.queryPriceByTypeId(typeId1,null,null,6);
//		out.put("dayList", priceList);
		// 周评
//		priceDTO.setTypeId(typeId2);
		out.put("typeId2", typeId2);
//		priceList = priceService.queryPriceByTypeId(typeId2,null,null,6);
//		out.put("weekList", priceList);
		// 市场动态

		// 企业报价
		if (companyPriceDTO.getCompanyPriceDO() == null) {
			companyPriceDTO.getPage().setStartIndex(0);
			companyPriceDTO.getPage().setPageSize(5);
			CompanyPriceDO companyPriceDO = new CompanyPriceDO();
			companyPriceDO.setCategoryCompanyPriceCode(categoryCompanyPriceCode);
			companyPriceDTO.setCompanyPriceDO(companyPriceDO);
		}

		PageDto<CompanyPriceDTO> page = new PageDto<CompanyPriceDTO>();
		page.setPageSize(5);
//		CompanyPriceDO companyPriceDO = new CompanyPriceDO();
//		companyPriceDO.setTitle(keywords);
		page = companyPriceService.pageCompanyPriceBySearchEngine(companyPriceDTO, page);
		List<CompanyPriceDTO> companyPriceList = page.getRecords();
//		List<CompanyPriceDTO> companyPriceList = companyPriceService.queryCompanyPriceForFront(companyPriceDTO);

		out.put("companyPriceList", companyPriceList);
		CompanyPriceDTO companyPriceDTO2 = new CompanyPriceDTO();
		if (companyPriceDTO2.getCompanyPriceDO() == null) {
			companyPriceDTO2.getPage().setStartIndex(0);
			companyPriceDTO2.getPage().setPageSize(5);
			CompanyPriceDO companyPriceDO1 = new CompanyPriceDO();
			companyPriceDO1.setCategoryCompanyPriceCode(categoryCompanyPriceCode1);
			companyPriceDTO2.setCompanyPriceDO(companyPriceDO1);
		}

//		List<CompanyPriceDTO> companyPriceList1 = companyPriceService
//				.queryCompanyPriceForFront(companyPriceDTO2);
		page = companyPriceService.pageCompanyPriceBySearchEngine(companyPriceDTO2, page);
		List<CompanyPriceDTO> companyPriceList1 = page.getRecords();
		out.put("companyPriceList1", companyPriceList1);
		
		// 供应
		out.put("productsBuyList", productsService.queryProductsByMainCode(categoryProductsMainCode, "10331000", 20));
		// 求购
		out.put("productsSaleList", productsService.queryProductsByMainCode(categoryProductsMainCode, "10331001", 20));

		// 网上报价
		out.put("typeId", priceDTO1.getTypeId());
		out.put("size", priceDTO1.getLimitSize());
//		listForPrice = priceService.queryPriceAndCategoryByTypeId(priceDTO1.getTypeId(), priceDTO1.getLimitSize());
//		out.put("onlinePrice", listForPrice);
		// 互助社区
		// 废料动态
		//out.put("bbsPostList1", bbsPostService.querySimplePostByCategory(1, 7));
		// 行业知识
		//out.put("bbsPostList2", bbsPostService.querySimplePostByCategory(2, 7));
		// 江湖风云
		//out.put("bbsPostList3", bbsPostService.querySimplePostByCategory(3, 7));
		// ZZ91动态
		//out.put("bbsPostList4", bbsPostService.querySimplePostByCategory(4, 7));
		//行情综述
		//tradeCode 10421000 废塑料 10421001 废金属 10421002 综合废料
		out.put("pricetypeId", pricetypeId);
//		out.put("bList", priceService.queryPriceByTypeId(pricetypeId, null, null, 15));
		
		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN*30);
		
		List<PriceDO> pdList=priceService.queryListByTypeId(20, 15);
		
		out.put("pdList", pdList);
		
		return new ModelAndView();
	}
	

}
