package com.ast.ast1949.price.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.cache.MemcachedUtils;

@Controller
public class PlasticController extends BaseController{
	
	private static Integer AN_HALF_DAY_SECONDS= 21600;
	
	private static String PLASTIC_ADD_PRODUCTS = "plastic_add_products"; // 当天新增报价
	private static String PLASTIC_PRODUCTS_COUNT = "plastic_products_count"; // 当天新增报价
	
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private ProductsService productsService;
	@Resource
	private PriceService priceService;
	@Resource
	private MarketService marketService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,PageDto<ProductsDto> page,ProductsDO products,Company company){
		out.put("nowTime", new Date());
		//新增产品信息
		if(MemcachedUtils.getInstance().getClient().get(PLASTIC_ADD_PRODUCTS)==null){
			MemcachedUtils.getInstance().getClient().set(PLASTIC_ADD_PRODUCTS, AN_HALF_DAY_SECONDS,productsService.queryTodayCopperProductsCount("1001",new Date()));
		}
		out.put("todayCount",MemcachedUtils.getInstance().getClient().get(PLASTIC_ADD_PRODUCTS));
		//今日总产品信息
		if(MemcachedUtils.getInstance().getClient().get(PLASTIC_PRODUCTS_COUNT)==null){
			MemcachedUtils.getInstance().getClient().set(PLASTIC_PRODUCTS_COUNT, AN_HALF_DAY_SECONDS,productsService.queryTodayCopperProductsCount("1001",null));
		}
		out.put("countPlastic",MemcachedUtils.getInstance().getClient().get(PLASTIC_PRODUCTS_COUNT));
		//企业报价
		PageDto<CompanyPriceSearchDTO> cpricePage = new PageDto<CompanyPriceSearchDTO>();
		cpricePage.setPageSize(11);
		CompanyPriceSearchDTO cDto = new CompanyPriceSearchDTO();
		cDto.setCategoryCompanyPriceCode("1000");
		out.put("qybjList", companyPriceService.queryCompanyPriceSearchByFront(cDto, cpricePage));
		//供求速递
		PageDto<ProductsDto> productPage = new PageDto<ProductsDto>();
		ProductsDO product = new ProductsDO();
		product.setTitle(CategoryProductsFacade.getInstance().getValue("1001"));
		productPage.setPageSize(1);
		// 最新废塑料供求 一条
		out.put("zxgqList", productsService.pageProductsBySearchEngine(product,null,null, productPage).getRecords());
		productPage.setPageSize(6);
		// 最新废塑料供应 6条
		product.setProductsTypeCode("10331000");
		out.put("ftgyList", productsService.pageProductsBySearchEngine(product,null,null, productPage).getRecords());
		// 最新废塑料求购 6条
		product.setProductsTypeCode("10331001");
		out.put("ftqgList", productsService.pageProductsBySearchEngine(product,null,null, productPage).getRecords());
		//为您推荐
		out.put("listProducts", productsService.buildLIst());
	   //显示最新美国+欧洲的废塑料价格
		Integer []ids={62,63};
		List<PriceDTO> dtolist=priceService.queryListByIntArray(ids, 8);
		out.put("dtolist", dtolist);
		
		// 市场产业带 入口 废金属类型  按照公司数量 排序
		PageDto<Market> marketPage = new PageDto<Market>();
		marketPage.setPageSize(6);
		marketPage = marketService.pageSearchOfMarket(null, null, "废塑料", 0, null, marketPage,null,0);
		out.put("marketPage", marketPage);
		
		return null;
	}

}
