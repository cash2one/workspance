package com.ast.feiliao91.service.commom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.common.Category;
import com.ast.feiliao91.dto.ExtTreeDto;
import com.ast.feiliao91.persist.common.CategoryDao;
import com.ast.feiliao91.service.commom.CategoryService;
import com.zz91.util.lang.StringUtils;

@Component("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Resource
	private CategoryDao categoryDao;

	@Override
	public Integer insert(Category category) {
		String code = getMaxCode(category.getParentcode());
		category.setCode(code);
		category.setIsDel(0);
		return categoryDao.insert(category);
	}

	@Override
	public Category selectById(Integer id) {
		return categoryDao.selectById(id);
	}

	@Override
	public Category selectByCode(String code) {
		return categoryDao.selectByCode(code);
	}

	@Override
	public List<Category> queryAllCategory() {
		return categoryDao.queryAllCategory();
	}

	@Override
	public List<ExtTreeDto> child(String code) {
		List<Category> categoryDOs = categoryDao.queryCategoriesByPreCode(code);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		// Map<String, String> childMap=null;
		String childCode = null;
		for (Category m : categoryDOs) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(m.getId()));
			// childMap=CategoryFacade.getInstance().getChild(m.getCode());
			childCode = categoryDao.queryMaxCodeByPreCode(m.getCode());
			if (StringUtils.isEmpty(childCode)) {
				node.setLeaf(true);
			} else {
				node.setLeaf(false);
			}
			node.setText(m.getLabel());
			node.setData(m.getCode());
			treeList.add(node);
		}
		return treeList;
	}

	@Override
	public Integer delete(String code) {
		return categoryDao.deleteCategoryByCode(code);
	}

	public String getMaxCode(String preCode) {
		String code = categoryDao.queryMaxCodeByPreCode(preCode);
		if (code == null || code.length() == 0) {
			code = preCode + "1000";
		} // Code值的长度为四时10000001+1
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

	@Override
	public Integer updateCategory(Category category) {
		return categoryDao.updateCategory(category);
	}

}
