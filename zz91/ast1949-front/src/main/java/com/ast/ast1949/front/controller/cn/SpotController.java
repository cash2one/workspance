package com.ast.ast1949.front.controller.cn;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.products.ProductsSpotService;
import com.zz91.util.lang.StringUtils;
/**
 * 现货商城
 * @author sj
 *
 */
@Controller
public class SpotController extends BaseController {
	final static Integer PAGE_SIZE = 10;
	@Resource
	private ProductsService productsService;
	@Resource
	private ProductsSpotService productsSpotService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out,PageDto<ProductsDto> page,String keywords,ProductsDO product) throws UnsupportedEncodingException, ParseException {
		// 伪造的前台数据
		productsSpotService.buildBaseData(out);
		
		// 现货报价列表  供应GY  求购QG
		ProductsDO proDO = new ProductsDO();
		page.setPageSize(12);
		proDO.setProductsTypeCode("10331000");
		page = productsService.pageProductsForSpot(proDO,page,null,null,null);
		out.put("priceList", page.getRecords());

		// 求购
		page.setPageSize(8);
		proDO= new ProductsDO();
		proDO.setProductsTypeCode("10331001");
		out.put("buyList", productsService.pageProductsBySearchEngine(proDO,null, null, page).getRecords());
		
		// 现货列表
		if(StringUtils.isNotEmpty(keywords)){
			keywords = StringUtils.decryptUrlParameter(keywords);
			product.setTitle(keywords);
		}
		if(page.getStartIndex()>0){
			// 非第一页
			page.setPageSize(10);
		}else{
			// 第一页
			List<ProductsDto> list = new ArrayList<ProductsDto>();
			list.addAll(productsService.queryProductsForSpotByCondition("1",null,null,1));
			list.addAll(productsService.queryProductsForSpotByCondition(null,"1",null,1));
			list.addAll(productsService.queryProductsForSpotByCondition(null,null,"1",2));
			out.put("speList", list);
			page.setPageSize(PAGE_SIZE-list.size());
		}
		Integer size=page.getStartIndex()/page.getPageSize()+1;
		out.put("page", productsService.pageProductsForSpot(product,page,null,null,null));
		page.setPageSize(10);
		out.put("keywords", keywords);
		out.put("location", StringUtils.decryptUrlParameter(product.getLocation()));
		out.put("productsTypeCode", product.getProductsTypeCode());
		out.put("categoryProductsMainCode", product.getCategoryProductsMainCode());
		out.put("today", new Date());
		out.put("size", size);
		return null;
	}
	
	@RequestMapping
	public ModelAndView oldIndex(HttpServletRequest request,
			Map<String, Object> out,PageDto<ProductsDto> page) {
		page.setPageSize(10);
		page = productsService.pageProductsForSpot(null,page,null,null,null);
		out.put("page", page);
		return null;
	}
	
}
