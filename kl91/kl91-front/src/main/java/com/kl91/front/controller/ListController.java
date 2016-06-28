package com.kl91.front.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.dto.products.ProductsSolrDto;
import com.kl91.service.products.ProductsService;
import com.kl91.service.products.ProductsSolrService;
import com.zz91.util.seo.SeoUtil;

@Controller
public class ListController extends BaseController{
	@Resource
	private ProductsSolrService productsSolrService;
	
	@RequestMapping
	public void offerlist(Map<String, Object> out,ProductsSearchDto searchDto,PageDto<ProductsSolrDto> page){
		
	}
	
	@RequestMapping
	public ModelAndView doSellSearch(Map<String, Object> out,ProductsSearchDto searchDto,PageDto<ProductsSolrDto> page,String keywords){
		SeoUtil.getInstance().buildSeo("products", out);
		page.setPageSize(5);
		
		List<ProductsSolrDto> list=productsSolrService.queryProductsByTypeCodeAndKeywords(searchDto, page, ProductsService.BUY_TYPE_CODE, keywords);
		
		if(list.size()>0){
			out.put("keywords", keywords);
			return new ModelAndView("redirect:noProducts.htm");
		}
		out.put("page", page);
		return new ModelAndView("redirect:offerlist.htm");
	}
	@RequestMapping
	public ModelAndView doBuySearch(Map<String, Object>out){
		return null;
	}
	
	@RequestMapping
	public void noProducts(Map<String, Object> out,ProductsSearchDto searchDto,PageDto<ProductsSolrDto> page ){
		
	}
	
	@RequestMapping
	public void noCompany(Map<String, Object> out,ProductsSearchDto searchDto,PageDto<ProductsSolrDto> page ){
		
	}

}
