/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-2
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

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class CategoryCompanyPriceController extends BaseController {

	@Autowired
	private CategoryCompanyPriceService categoryCompanyPriceService;

	@RequestMapping
	public void view() {

	}

	@RequestMapping
	public ModelAndView child(@RequestParam(required = false) String parentCode,
			Map<String, Object> out) throws IOException {
		if (parentCode == null || parentCode == "") {
			parentCode = "";
		} else if (parentCode.equals("0")) {
			parentCode = "";
		}
		List<ExtTreeDto> list =categoryCompanyPriceService.child(parentCode);
		return printJson(list, out);
	}

	/**
	 * 初始化类别管理表单 id为0时不获取任何信息 id不为0,获取该记录对应的对象
	 * 
	 * @param id
	 * @param out
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "init.htm")
	public ModelAndView init(@RequestParam(required = false) String id, Map<String, Object> out)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(id) && StringUtils.isNumber(id)) {
			CategoryCompanyPriceDO category = categoryCompanyPriceService.selectCategoryCompanyPriceById(Integer.valueOf(id));
			List<CategoryCompanyPriceDO> list = new ArrayList<CategoryCompanyPriceDO>();
			list.add(category);
			map.put("records", list);
		}
		return printJson(map, out);

	}

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
	@RequestMapping(value = "edit.htm")
	public ModelAndView edit(CategoryCompanyPriceDO categoryCompanyPriceDO, String preCode,
			Map<String, Object> out) throws IOException {
		int impacted = 0;
		if (categoryCompanyPriceDO.getId() == null || categoryCompanyPriceDO.getId() <= 0) {
			impacted = categoryCompanyPriceService.insertCategoryCompanyPrice(
					categoryCompanyPriceDO, preCode);
		} else {
			impacted = categoryCompanyPriceService
					.updateCategoryCompanyPrice(categoryCompanyPriceDO);
		}
		ExtResult result = new ExtResult();
		if (impacted > 0) {
			result.setSuccess(true);
		}
		result.setData(impacted);

		return printJson(result, out);

	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete.htm")
	public ModelAndView delete(String code, Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		int i = categoryCompanyPriceService.deleteCategoryCompanyPriceByCode(code);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

}
