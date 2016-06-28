package com.ast.ast1949.api.controller.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.api.controller.BaseController;
import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.products.CategoryProductsService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.tags.TagsUtils;

@Controller
public class TradeController extends BaseController{
	
	@Resource
	private InquiryService inquiryService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CategoryProductsService categoryProductsService;
	
	@RequestMapping
	public ModelAndView header(HttpServletRequest request, Map<String, Object> out, String ik){
		if(StringUtils.isEmpty("ik")){
			ik = "index";
		}
		out.put("ik", ik);
		
		List<InquiryDto> list = inquiryService.queryScrollInquiry();
		out.put("list", list);
		
		out.put("hotTags", TagsUtils.getInstance().queryTagsByCode("100010021002", null, 10));
		return null;
	}
	
	@RequestMapping
	public ModelAndView pageSimilarProduct(HttpServletRequest request, Map<String, Object> out, 
			Integer pid, String categoryCode, Integer cid) throws IOException{
		List<Integer> list=productsService.queryProductsIdsOfCompany(cid, categoryCode);
		int max=list.size();
		int start=1;
		
		for(int i=1;i<=list.size();i++){
			if(pid.intValue()==list.get(i-1)){
				max=i+8;
				start=i-8;
				break;
			}
		}
		
		if(max>list.size()){
			max=list.size();
		}
		
		if(start<1){
			start=1;
		}
		
		int pre=1,next=1;
		List<Integer> resultList=new ArrayList<Integer>();
		for(int i=start;i<=max;i++){
			resultList.add(list.get(i-1));
			if(pid.intValue()==list.get(i-1)){
				pre=i-2;
				next=i;
			}
		}
		
		if(pre<0){
			pre=0;
		}
		if(next>=list.size()){
			next=list.size()-1;
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", resultList);
		map.put("start", start);
		map.put("max", max);
		map.put("pre", list.get(pre));
		map.put("next", list.get(next));
		
		return printJson(map, out);
	}
	
	/**
	 * 最新供求信息
	 * @param out
	 * @param mainCode 供求主类别code
	 * @param typeCode 供求信息类型code
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView newestTrade(Map<String, Object> out,String mainCode, String typeCode, Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50) {
			size=50;
		}
		if (mainCode==null){
			mainCode=null;
		}
		if (typeCode==null) {
			typeCode=null;
		}
		List<ProductsDO> productsList = productsService.queryNewestProducts(mainCode, typeCode, size);
		map.put("productsList", productsList);
		return printJson(map, out);
	}
	
	/**
	 * 最新供求
	 * @param out
	 * @param mainCode 供求主类别
	 * @param typeCode 供求类型
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView trade(Map<String, Object> out, String mainCode,String keywords,String typeCode,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50) {
			size=50;
		}
		if (mainCode==null){
			mainCode=null;
		}
		if (typeCode==null){
			typeCode=null;
		}
		//在企业报价时候查询小类别时候，先根据小类别的名称查出对应的 供求code 当code不空时候为此为主mainCode搜索。不然以上一级code为条件搜索
		keywords = StringUtils.decryptUrlParameter(keywords);
		CategoryProductsDO categoryProductsDO =  categoryProductsService.queryCategoryProductsByKey(keywords);
		if(categoryProductsDO!=null&&StringUtils.isNotEmpty(categoryProductsDO.getCode())){
			mainCode = categoryProductsDO.getCode();
		}
		List<ProductsDto> list=productsService.queryProductsByMainCode(mainCode, typeCode, size);
		
		map.put("list", list);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView newTrade(Map<String, Object> out, String mainCode,String keywords,String typeCode,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50) {
			size=50;
		}
		if (mainCode==null){
			mainCode=null;
		}
		if (typeCode==null){
			typeCode=null;
		}
		
		
		
		//在企业报价时候查询小类别时候，先根据小类别的名称查出对应的 供求code 当code不空时候为此为主mainCode搜索。不然以上一级code为条件搜索
		keywords = StringUtils.decryptUrlParameter(keywords);
		CategoryProductsDO categoryProductsDO =  categoryProductsService.queryCategoryProductsByKey(keywords);
		if(categoryProductsDO!=null&&StringUtils.isNotEmpty(categoryProductsDO.getCode())){
			mainCode = categoryProductsDO.getCode();
		}
		List<ProductsDto> list=productsService.queryProductsByMainCode(mainCode, typeCode, size);
		
		map.put("list", list);
		
		return printJson(map, out);
	}
	
	
}
