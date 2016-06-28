package com.ast.ast1949.price.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.information.ChartDataForIndexDTO;
import com.ast.ast1949.dto.price.PriceDto2;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.information.ChartDataService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.CNToHexUtil;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;
import com.zz91.util.velocity.AddressTool;


@Controller
public class JinShuController extends BaseController{
	
	private static Integer AN_HOUR_SECONDS= 3600;
	private static Integer AN_HALF_DAY_SECONDS= 21600;
	
	private static String JINSHU_ADD_PRICE = "jinshu_add_price"; // 当天新增报价
	private static String JINSHU_ADD_PRODUCTS = "jinshu_add_products";
	
	
	@Autowired
	private PriceService priceService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private CompanyPriceService companyPriceService;
	@Autowired
	private ChartDataService chartDataService;
	
	@RequestMapping
	public void index(Map<String, Object> out,PageDto<CompanyPriceSearchDTO> pager,
	String categoryCompanyPriceCode, String keywords, String areaCode,
			String postInDays, String priceRange) throws Exception, IOException{
		//统计今天新增报价
		if(MemcachedUtils.getInstance().getClient().get(JINSHU_ADD_PRICE)==null){
			MemcachedUtils.getInstance().getClient().set(JINSHU_ADD_PRICE, AN_HALF_DAY_SECONDS, priceService.queryPriceCount());
		}
		out.put("num", MemcachedUtils.getInstance().getClient().get(JINSHU_ADD_PRICE));
		//统计今天新增供求
		if(MemcachedUtils.getInstance().getClient().get(JINSHU_ADD_PRODUCTS)==null){
			MemcachedUtils.getInstance().getClient().set(JINSHU_ADD_PRODUCTS, AN_HALF_DAY_SECONDS,productsService.queryTodayCopperProductsCount("1000",new Date()));
		}
		out.put("proNum",MemcachedUtils.getInstance().getClient().get(JINSHU_ADD_PRODUCTS));
		//废金属企业报价
		CompanyPriceSearchDTO dto = new CompanyPriceSearchDTO();
		dto.setCategoryCompanyPriceCode("1001");
		pager.setPageSize(24);
		pager = companyPriceService.pageCompanyPriceSearch(dto, pager);
		out.put("pager", pager);
		//最新废金属产品信息放入缓存 一小时更新一次
		if(MemcachedUtils.getInstance().getClient().get("latest_scrap_metal_product_gy_information")==null){
			List<ProductsDto> list=productsService.queryProductsByMainCode("1000", "10331000", 12);
			MemcachedUtils.getInstance().getClient().set("latest_scrap_metal_product_gy_information", AN_HOUR_SECONDS, list);
		}
		if(MemcachedUtils.getInstance().getClient().get("latest_scrap_metal_product_qg_information")==null){
			List<ProductsDto> list1=productsService.queryProductsByMainCode("1000", "10331001", 12);
			MemcachedUtils.getInstance().getClient().set("latest_scrap_metal_product_qg_information", AN_HOUR_SECONDS, list1);
		}
		out.put("list", MemcachedUtils.getInstance().getClient().get("latest_scrap_metal_product_gy_information"));
		out.put("list1", MemcachedUtils.getInstance().getClient().get("latest_scrap_metal_product_qg_information"));
		//有色金属各地城市价格 (65, 1);
		List<PriceDO> metalNewmaterialsList = priceService.queryListByTypeId(65, 6);
		out.put("metalNewmaterialsList", metalNewmaterialsList);
		//废金属市场动态
		Map<Integer, List<PriceDto2>> metalMarketList = priceService.queryPriceOfParentCategory(19, 1);
		out.put("metalMarketList", metalMarketList);
		//最新有色金属报价
		Map<Integer, List<PriceDto2>> latestMetalList = priceService.queryPriceOfParentCategory(67, 1);
		out.put("latestMetalList", latestMetalList);
		//有色金属期货报价
		Map<Integer, List<PriceDto2>> qMetalList = priceService.queryPriceOfParentCategory(64, 1);
		out.put("qMetalList", qMetalList);
		//优质商家推荐 供应列表
		out.put("supplyList", productsService.queryNewestVipProducts("10331000","1000", 5));
		//优质商家推荐 求购列表
		out.put("buyList", productsService.queryNewestVipProducts("10331001", "1000",5));
		//热门推荐
		Map<String, String> map = TagsUtils.getInstance().queryTagsByCode("1000100210011000", null, 10);
		for(String key:map.keySet()){
			map.put(key, CNToHexUtil.getInstance().encode(key));
		}
		out.put("tags", map);
		//今日行情
		PageDto<PriceDO> page = new PageDto<PriceDO>();
		page.setPageSize(2);
		page.setSort("gmt_order");
		out.put("todayList", priceService.queryPriceByType(null, null,242, page)); //热点
		page.setPageSize(1);
		out.put("todayList1", priceService.queryPriceByType(null, null,243, page)); //废钢
		out.put("todayList2", priceService.queryPriceByType(null, null,244, page)); //有色
		out.put("todayList3", priceService.queryPriceByType(null, null,245, page)); //生铁
		out.put("todayList4", priceService.queryPriceByType(null, null,246, page)); //期货
		//热门供应
		List<ProductsDto> hotList =productsService.queryHotProducts("1000", "10331000", 10);
		out.put("hotList", hotList);
		//短信报价
		out.put("nowTime", DateUtil.toString(new Date(), "MM.dd"));
		// 废金属报价
		List<ChartDataForIndexDTO> charts = chartDataService.queryChartDataForIndex(8);
		out.put("charts", charts);
		//短信报价
		String responseText=HttpUtils.getInstance().httpGet(AddressTool.getAddress("exadmin")+"/reborn-admin/sms/main/selectTodaySmsForPrice.htm", HttpUtils.CHARSET_UTF8);
		if(StringUtils.isNotEmpty(responseText) && responseText.startsWith("{")){
			JSONObject jobj = JSONObject.fromObject(responseText);
			out.put("jsonArray", jobj);
		}
//		out.put("timelist", tlist);
		// seo t k d
		SeoUtil.getInstance().buildSeo("jinshu", out);
	}

}
