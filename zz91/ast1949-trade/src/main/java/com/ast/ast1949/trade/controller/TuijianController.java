package com.ast.ast1949.trade.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

/**
 * 
 * @author 	sj
 * @date	2013-2-26
 */
@Controller
public class TuijianController extends BaseController {
	@Resource
	private ProductsService productsService;
	@Resource
	private PriceService priceService;
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response,Map<String, Object>out,String mainCode,String keywords,Integer isSellOrBuy,Integer p,PageDto<ProductsDto> page){
		
		out.put("mainCode", mainCode);
		out.put("isSellOrBuy", isSellOrBuy);
		out.put("keywords", keywords);
		// 单页取数据数量
		if(p!=null&&p>1){
			page.setStartIndex(p);
		}
		// 关键字
		if(StringUtils.isEmpty(keywords)){
			keywords = keyMap.get(mainCode);
		}
		// 类目code
		if(StringUtils.isNotEmpty(mainCode)){
			out.put("mainCode", mainCode);
		}
		ProductsDO product = new ProductsDO();
		product.setCrmCompanySvr(CrmSvrService.CRM_SP);
		if(StringUtils.isNotEmpty(keywords)){
			product.setTitle(keywords);
		}
		if(isSellOrBuy!=null&&isSellOrBuy==1){
			product.setProductsTypeCode("10331000");
		}else if(isSellOrBuy!=null&&isSellOrBuy==2){
			product.setProductsTypeCode("10331001");
		}
		
		page = productsService.pageSPProductsBySearchEngine(product, page);
		out.put("page", page);
		// 商铺服务的废金属 供求
		product = new ProductsDO();
		product.setCrmCompanySvr(CrmSvrService.CRM_SP);
		product.setTitle("废金属");
		PageDto<ProductsDto> keyPage = new PageDto<ProductsDto>();
		keyPage.setPageSize(5); // 条数
		product.setProductsTypeCode("10331000");// 供应
		out.put("gyList", productsService.pageProductsBySearchEngine(product, null, null, keyPage).getRecords());
		product.setProductsTypeCode("10331001"); // 求购
		out.put("qgList", productsService.pageProductsBySearchEngine(product, null, null, keyPage).getRecords());
		
		// 最新报价
		PageDto<PriceDO> pricePage = new PageDto<PriceDO>();
		pricePage.setPageSize(8);
		out.put("priceList", priceService.pagePriceBySearchEngine(keywords, null, pricePage).getRecords());
		
		// trade的最新供求
		ProductsDO nproduct = new ProductsDO();
		PageDto<ProductsDto> TradePage = new PageDto<ProductsDto>();
		product.setTitle("废金属");
		TradePage.setPageSize(5); // 条数
		product.setProductsTypeCode("10331000");// 供应
		out.put("gyTradeList", productsService.pageProductsBySearchEngine(nproduct, null, null, TradePage).getRecords());
		product.setProductsTypeCode("10331001"); // 求购
		out.put("qgTradeList", productsService.pageProductsBySearchEngine(nproduct, null, null, TradePage).getRecords());
		
		// 搜索栏下方标签
		Map<String, String> hotTags = TagsUtils.getInstance().queryTagsByHot(5, 30);
		for(String str:hotTags.keySet()){
			String valStr = CNToHexUtil.getInstance().encode(str);
			hotTags.put(str,valStr);
		}
		out.put("hotTags", hotTags);
		SeoUtil.getInstance().buildSeo("tjIndex", out);
		if(StringUtils.isNotEmpty(keywords)){
			SeoUtil.getInstance().buildSeo("tjkey",new String[]{keywords},new String[]{keywords},new String[]{keywords}, out);
		}
		return new ModelAndView();
	}
	
	final static Map<String,String> keyMap =new HashMap<String, String>();
	static{
		keyMap.put("100310081000", "废金属");
		keyMap.put("100310081001", "废塑料");
		keyMap.put("100310081002", "综合废料");
	}
}
