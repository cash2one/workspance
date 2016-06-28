/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-9 by Ryan.
 */
package com.ast.ast1949.service.site.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.site.CategoryDTO;
import com.ast.ast1949.persist.site.CategoryDAO;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.StringUtils;

/**
 * @author Ryan
 *
 */
@Component("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDAO categoryDAO;

	public List<CategoryDO> queryCategoriesByPreCode(String code) {
		return categoryDAO.queryCategoriesByPreCode(code);
//		List<CategoryDO> cateList = new ArrayList<CategoryDO>();
//		List<CategoryDO> newList = categoryDAO.selectCategoriesByPreCode(code);
//		for (Object o : newList) {
//			cateList.add((CategoryDO) o);
//		}
//		return cateList;

	}

	@Override
	public List<CategoryDO> queryCategoryList() {
		return categoryDAO.queryCategoryList();
	}

	public int insertCategory(CategoryDO categoryDO,String preCode) throws IOException {
			String code=getMaxCode(preCode);
			categoryDO.setCode(code);
			categoryDO.setIsLeaf(true);
		return categoryDAO.insertCategory(categoryDO);

	}

	public String getMaxCode(String preCode) throws IOException {
		String code = categoryDAO.queryMaxCodeByPreCode(preCode);
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

//	public String queryMaxCodeByPreCode(String preCode) throws IOException {
//
//		return categoryDAO.queryMaxCodeByPreCode(preCode);
//
//	}

	public int deleteCategoryByCode(String code) {
		return categoryDAO.deleteCategoryByCode(code);

	}

//	public int queryRecordCountByCondition(CategoryDTO pageParam) {
//		return categoryDAO.queryRecordCountByCondition(pageParam);
//	}

	public List<CategoryDO> queryCategoriesByCondition(CategoryDTO categoryDTO) {
		return categoryDAO.queryCategoriesByCondition(categoryDTO);
	}

	public CategoryDO queryCategoryById(int id) {
		return categoryDAO.queryCategoryById(id);
	}

	public List<ExtTreeDto> child(String code) {
		List<CategoryDO> categoryDOs = categoryDAO.queryCategoriesByPreCode(code);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
//		Map<String, String> childMap=null;
		String childCode=null;
		for (CategoryDO m : categoryDOs) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(m.getId()));
//			childMap=CategoryFacade.getInstance().getChild(m.getCode());
			childCode=categoryDAO.queryMaxCodeByPreCode(m.getCode());
			if(StringUtils.isEmpty(childCode)){
				node.setLeaf(true);
			}else{
				node.setLeaf(false);
			}
			node.setText(m.getLabel());
			node.setData(m.getCode());
			treeList.add(node);
		}
		return treeList;
	}
	public List<ExtTreeDto> childSearch(String code) {
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
//		Map<String, String> childMap=null;
		String childCode=null;
		
		if("10011000".equals(code)){
			List<CategoryDO> list=new ArrayList<CategoryDO>();
			CategoryDO categoryDO=new CategoryDO();
			categoryDO.setId(0);
			categoryDO.setLabel("全部");
			list.add(categoryDO);
			List<CategoryDO> categoryDOs = categoryDAO.queryCategoriesByPreCode(code);
			for(CategoryDO c: categoryDOs){
					list.add(c);
			}
			for (CategoryDO m : list) {
				ExtTreeDto node = new ExtTreeDto();
				node.setId("node-" + String.valueOf(m.getId()));
//				childMap=CategoryFacade.getInstance().getChild(m.getCode());
				if(m.getId()!=0){
					childCode=categoryDAO.queryMaxCodeByPreCode(m.getCode());
					if(StringUtils.isEmpty(childCode)){
						node.setLeaf(true);
					}else{
						node.setLeaf(false);
					}
				}else {
					node.setLeaf(false);
				}
				node.setText(m.getLabel());
				node.setData(m.getCode());
				treeList.add(node);
			}
			
		}
		else if(code!=null&&!code.equals("")){
			List<CategoryDO> categoryDOs = categoryDAO.queryCategoriesByPreCode(code);
			for (CategoryDO m : categoryDOs) {
				ExtTreeDto node = new ExtTreeDto();
				node.setId("node-" + String.valueOf(m.getId()));
//				childMap=CategoryFacade.getInstance().getChild(m.getCode());
				if(0!=m.getId()){
					childCode=categoryDAO.queryMaxCodeByPreCode(m.getCode());
					if(StringUtils.isEmpty(childCode)){
						node.setLeaf(true);
					}else{
						node.setLeaf(false);
					}
				}else {
					node.setLeaf(false);
				}
				node.setText(m.getLabel());
				node.setData(m.getCode());
				treeList.add(node);
			}
		}
		return treeList;
	}
	public int updateCategory(CategoryDO categoryDO) {
		
		return categoryDAO.updateCategory(categoryDO);
	}

	public CategoryDO queryCategoryByCode(String code) {

		return categoryDAO.queryCategoryByCode(code);
	}

	@Override
	public String queryCodeByLabel(String label) {
		return categoryDAO.queryCodeByLabel(label);
	}


//	@Override
//	public List<CategoryDO> queryCategoriesByParentCode(String parentCode) {
//		return categoryDAO.queryCategoriesByParentCode(parentCode);
//	}

}
