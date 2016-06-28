package com.ast.ast1949.front.controller.cn;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.tags.TagsUtils;
/**
 * 二手设备网改版页面
 * @author sj
 *
 */
@Controller
public class ShebeiController extends BaseController {

	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyPriceService companyPriceService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,ProductsDO products,PageDto<ProductsDto> page) {
		Date start = new Date();
		// 广告下 中部产品滚动图片
		products.setCategoryProductsMainCode("1007");
		out.put("picList",productsService.queryProductsForPic(products, 10));
		// 机床设备10071013 
		page.setPageSize(5);
		products.setProductsTypeCode("10331000");
		products.setTitle("机床设备");
		out.put("jcGYList",productsService.pageProductsBySearchEngine(products, null,true, page).getRecords()); // 供应 机床设备
		products.setProductsTypeCode("10331001");
		out.put("jcQGList",productsService.pageProductsBySearchEngine(products, null,true, page).getRecords()); // 求购 机床设备
		//印刷设备 10071018
		products.setTitle("印刷设备");
		out.put("ysQGList",productsService.pageProductsBySearchEngine(products, null,true, page).getRecords()); // 求购 印刷设备
		products.setProductsTypeCode("10331000");
		out.put("ysGYList",productsService.pageProductsBySearchEngine(products, null,true, page).getRecords()); // 供应 印刷设备
		// 其他设备
		products.setTitle("废旧二手设备");
		out.put("qtGYList", productsService.pageProductsBySearchEngine(products, null,true, page).getRecords()); // 供应 其他设备
		products.setProductsTypeCode("10331001");
		out.put("qtQGList", productsService.pageProductsBySearchEngine(products, null,true, page).getRecords()); // 求购 其他设备
		// 企业报价
		CompanyPriceSearchDTO dto = new CompanyPriceSearchDTO();
		dto.setTitle("二手设备");
		PageDto<CompanyPriceSearchDTO> pager = new PageDto<CompanyPriceSearchDTO>();
		pager.setPageSize(6);
		out.put("priceList",companyPriceService.queryCompanyPriceSearchByFront(dto, pager));
		// 底部热门标签 标签系统
		Map<String,String> map = TagsUtils.getInstance().queryTagsByCode("1000100210011000", null, 10);
		for(String key:map.keySet()){
			map.put(key, CNToHexUtil.getInstance().encode(key));
		}
		out.put("tags", map);
		Date end = new Date();
		out.put("cTime",end.getTime()-start.getTime());
		return new ModelAndView();
	}
}
