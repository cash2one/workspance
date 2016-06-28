/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-2 下午04:56:01
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.CategoryProductsWithKeywordsDTO;
import com.ast.ast1949.service.products.CategoryProductsService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Ryan(rxm1025@gmail.com)
 *
 */
@Controller
public class CategoryProductsController extends BaseController {
	@Autowired
	CategoryProductsService categoryProductsService;
//	@Autowired
//	ProductsSearchAssociateKeywordsService productsSearchAssociateKeywordsService;

	/**
	 * 类别管理主界面
	 */
	@RequestMapping
	public void view() {

	}

	/**
	 * 通过父节点编号(code)获取子节点列表
	 *
	 * @param parentCode
	 *            父节点编号
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "child.htm")
	public ModelAndView child(@RequestParam(required = false) String parentCode,String isAssist,
			Map<String, Object> out) throws IOException {
		if (parentCode == null) {
			parentCode = "";
		} else if (parentCode.equals("0")) {
			parentCode = "";
		}
		if(StringUtils.isEmpty(isAssist)){
			isAssist="0";
		}
		List<ExtTreeDto> list = categoryProductsService.child(parentCode,isAssist);
		return printJson(list, out);
	}

	final static String ASSIST_TRUE = "1";

	@RequestMapping
	public ModelAndView assistCombo(Map<String, Object> model,String parentCode) throws IOException{
		if(parentCode == null || "0".equals(parentCode)){
			parentCode = "";
		}
		PageDto page = new PageDto();
		page.setRecords(categoryProductsService.child(parentCode,ASSIST_TRUE));
		return printJson(page, model);
	}

	/**
	 * 初始化类别管理表单 id为0时不获取任何信息 id不为0,获取该记录对应的对象
	 *
	 * @param code
	 * @param out
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "query.htm")
	public ModelAndView query(String code, String isAssist, Map<String, Object> out)
			throws IOException {
		//TODO 删除product与关键字关联表
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(code) && StringUtils.isNumber(code)) {
			List<CategoryProductsDO> list = categoryProductsService.queryCategoryProductsByCode(
					code, isAssist);

			List<CategoryProductsWithKeywordsDTO> list3 = new ArrayList<CategoryProductsWithKeywordsDTO>();
			for (CategoryProductsDO categoryProducts : list) {
//				List<ProductsSearchAssociateKeywordsDO> list2 = productsSearchAssociateKeywordsService
//						.queryByCategoryProductsCode(categoryProducts.getCode());
				String keywords = "";
//				for (ProductsSearchAssociateKeywordsDO keyword : list2) {
//					keywords = keywords.concat(keyword.getKeyword() + ",");
//				}
				if (keywords.length() > 0) {
					keywords = keywords.substring(0, keywords.length() - 1);
				}
				CategoryProductsWithKeywordsDTO categoryProductsWithKeywordsDTO = new CategoryProductsWithKeywordsDTO();
				categoryProductsWithKeywordsDTO.setCategoryProductsDO(categoryProducts);
				categoryProductsWithKeywordsDTO.setKeywords(keywords);
				list3.add(categoryProductsWithKeywordsDTO);
			}
			map.put("records", list3);

		}
		return printJson(map, out);

	}

	final static String DEFAULT_IS_ASSIST = "0";

	/**
	 * 新增和编辑类别信息
	 *
	 * @param category
	 *            类别对象
	 * @param preCode
	 *            父类别编号
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateCategoryProducts(CategoryProductsDO categoryProductsDO, String preCode,
			Map<String, Object> out) throws IOException {
		if(categoryProductsDO.getIsAssist()==null){
			categoryProductsDO.setIsAssist(DEFAULT_IS_ASSIST);
		}
		Integer impacted = categoryProductsService.updatecategoryProducts(categoryProductsDO);

		ExtResult result = new ExtResult();
		if (impacted!= null && impacted > 0) {
			result.setSuccess(true);
			result.setData(impacted);
		}

		return printJson(result, out);

	}

	@RequestMapping
	public ModelAndView createCategoryProducts(Map<String, Object> model, CategoryProductsDO categoryProductsDO, String preCode)
		throws IOException{
		ExtResult result = new ExtResult();
		if(categoryProductsDO.getIsAssist()==null){
			categoryProductsDO.setIsAssist(DEFAULT_IS_ASSIST);
		}
		if(categoryProductsService.insertCategoryProducts(categoryProductsDO, preCode)>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	/**
	 * 初始化类别管理表单 id为0时不获取任何信息 id不为0,获取该记录对应的对象
	 *
	 * @param id
	 * @param out
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView init(@RequestParam(required = false) String id, Map<String, Object> out)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(id) && StringUtils.isNumber(id)) {
			CategoryProductsDO categoryProducts = categoryProductsService
					.queryCategoryProductsById(Integer.valueOf(id));
			List<CategoryProductsDO> list = new ArrayList<CategoryProductsDO>();
			list.add(categoryProducts);
			map.put("records", list);
		}
		return printJson(map, out);

	}

	/**
	 *
	 * 删除
	 */
	@RequestMapping(value = "delete.htm")
	public ModelAndView delete(Integer id, Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = categoryProductsService.deleteCategoryProductsAndChildById(id);
		if (i!=null && i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
