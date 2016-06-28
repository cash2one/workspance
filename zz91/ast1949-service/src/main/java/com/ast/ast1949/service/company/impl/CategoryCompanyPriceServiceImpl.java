/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.service.company.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.persist.company.CategoryCompanyPriceDAO;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("categoryCompanyPriceService")
public class CategoryCompanyPriceServiceImpl implements CategoryCompanyPriceService{

	@Autowired
	private CategoryCompanyPriceDAO categoryCompanyPriceDAO;

	public List<CategoryCompanyPriceDO> queryCategoryCompanyPrice() {

		return categoryCompanyPriceDAO.queryCategoryCompanyPrice();
	}

	public List<CategoryCompanyPriceDO> queryCategoryCompanyPriceByCode(
			String code) {

		return categoryCompanyPriceDAO.queryCategoryCompanyPriceByCode(code);
	}

	public CategoryCompanyPriceDO queryByCode(String code) {

		return categoryCompanyPriceDAO.queryByCode(code);
	}

	public int deleteCategoryCompanyPriceByCode(String code) {
		Assert.notNull(categoryCompanyPriceDAO, "categoryCompanyPriceDAO is not null");
		return categoryCompanyPriceDAO.deleteCategoryCompanyPriceByCode(code);
	}

	public int insertCategoryCompanyPrice(CategoryCompanyPriceDO categories,String preCode) throws IOException {
		Assert.notNull(categories, "categories is not null");
		String code=getMaxCode(preCode);
		categories.setCode(code);
		return categoryCompanyPriceDAO.insertCategoryCompanyPrice(categories);
	}

//	public String selectMaxCodeByPreCode(String preCode) throws IOException {
//	    Assert.notNull(preCode, "preCode is not null");
//		return categoryCompanyPriceDAO.selectMaxCodeByPreCode(preCode);
//	}

	public int updateCategoryCompanyPrice(CategoryCompanyPriceDO categoryDO) {
		Assert.notNull(categoryDO, "categoryDO is not null");
		return categoryCompanyPriceDAO.updateCategoryCompanyPrice(categoryDO);
	}

	public List<ExtTreeDto> child(String code) {
		List<CategoryCompanyPriceDO> categoryDOs =categoryCompanyPriceDAO.selectCategoriesByPreCode(code);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for (CategoryCompanyPriceDO m : categoryDOs) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(m.getId()));
			node.setLeaf(false);
			node.setText(m.getLabel());
			node.setData(m.getCode());
			treeList.add(node);
		}
		return treeList;
	}

	public String getMaxCode(String preCode) throws IOException {
		String code = categoryCompanyPriceDAO.selectMaxCodeByPreCode(preCode);
		if (code == null || code.length() == 0) {
			code = preCode + "1000";
		}// Code值的长度为四时10000001+1
		else if (code.length() == 4) {
			code = String.valueOf(Integer.valueOf(code) + 1);
		} else if (code.length() == 8) {
			code = String.valueOf(Integer.valueOf(code) + 1);
		}
		// string类型 存值有限 必须 先 截取后四位+1 然后赋值
		else {
			String code3 = code.substring(code.length() - 4, code.length());
			String code4 = code.substring(0, code.length() - 4);
			code = code4 + String.valueOf(Integer.valueOf(code3) + 1);
		}
		return code;
	}

	public CategoryCompanyPriceDO selectCategoryCompanyPriceById(Integer id) {
		Assert.notNull(id, "id is not null");
		return categoryCompanyPriceDAO.selectCategoryCompanyPriceById(id);
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.service.company.CategoryCompanyPriceService#queryCategoryCompanyPriceByLabel(java.lang.String)
	 */
	public CategoryCompanyPriceDO queryCategoryCompanyPriceByLabel(String label) {
		return categoryCompanyPriceDAO.queryCategoryCompanyPriceByLabel(label);
	}

	@Override
	public Map<String, String> queryAllCompanyPrice() {
		Map<String,String> map = new HashMap<String,String>();
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceDAO.queryAllCompanyPrice();
		if(list.size()>0){
			for(CategoryCompanyPriceDO li : list){
				map.put(li.getLabel(),li.getCode());
				map.put(li.getCode(), li.getLabel());
			}
		}
		return map;
	}
}
