/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-28
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.service.company.CategoryGardenService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.site.CategoryService;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-28
 */
@Controller
public class CategoryController extends BaseController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	PriceCategoryService priceCategoryService;
	@Autowired
	CategoryGardenService categoryGardenService;

	@RequestMapping
	public ModelAndView areaChild(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {
		
		List<CategoryDO> list = new ArrayList<CategoryDO>(); 
		Map<String, String> map=CategoryFacade.getInstance().getChild(parentCode);
		if(map==null){
			return printJson(list, out);
		}
		
		for(Entry<String, String> m:map.entrySet()){
			CategoryDO c = new CategoryDO();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView priceChild(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		List<PriceCategoryDO> list = priceCategoryService
				.queryPriceCategoryByParentId(id);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView priceParent(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer rootId)
			throws IOException {
		List<Integer> categoryList = new ArrayList<Integer>();
		categoryList.add(id);
		recursivePriceCategory(categoryList, id, rootId);
		return printJson(categoryList, out);
	}

	private Integer recursivePriceCategory(List<Integer> list, Integer id,
			Integer rootId) {
		Integer pid = priceCategoryService.queryParentIdById(id);
		if (pid != null && pid.intValue() > 0
				&& pid.intValue() != rootId.intValue() && list.size() < 6) {
			list.add(pid);
			recursivePriceCategory(list, pid, rootId);
		}
		return null;
	}

	@RequestMapping
	public ModelAndView priceAssist(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		List<PriceCategoryDO> list = priceCategoryService
				.queryAssistPriceCategoryByTypeId(id);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView productChild(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {
		Map<String, String> map = CategoryProductsFacade.getInstance().getChild(parentCode);
		List<CategoryProductsDO> list=new ArrayList<CategoryProductsDO>();
		if(map==null){
			return printJson(list, out);
		}
		for(Entry<String, String> m:map.entrySet()){
			CategoryProductsDO c=new CategoryProductsDO();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView productAssist(HttpServletRequest request,
			Map<String, Object> out, String mainCode) throws IOException {
		Map<String, String> map = CategoryProductsFacade.getInstance().getAssistChild(mainCode);
		List<CategoryProductsDO> list=new ArrayList<CategoryProductsDO>();
		if(map==null){
			return printJson(list, out);
		}
		for(Entry<String, String> m:map.entrySet()){
			CategoryProductsDO c=new CategoryProductsDO();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView gardenChild(HttpServletRequest request,
			Map<String, Object> out, String mainCode) throws IOException{
		List<CategoryGardenDO> list = categoryGardenService.queryCategoryGardenByAreaCode(mainCode);
		List<CategoryDO> categoryList=new ArrayList<CategoryDO>();
		
		for(CategoryGardenDO garden:list){
			CategoryDO c=new CategoryDO();
			c.setCode(String.valueOf(garden.getId()));
			c.setLabel(garden.getShorterName());
			categoryList.add(c);
		}
		return printJson(categoryList, out);
	}
	
	@RequestMapping
	public ModelAndView categoryChild(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {
		
		List<CategoryDO> list = new ArrayList<CategoryDO>(); 
		Map<String, String> map=CategoryFacade.getInstance().getChild(parentCode);
		if(map==null){
			return printJson(list, out);
		}
		
		for(Entry<String, String> m:map.entrySet()){
			CategoryDO c = new CategoryDO();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		
		return printJson(list, out);
	}
}
