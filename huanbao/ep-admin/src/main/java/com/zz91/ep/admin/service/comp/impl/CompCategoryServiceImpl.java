/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.ep.admin.dao.comp.CompCategoryDao;
import com.zz91.ep.admin.service.comp.CompCategoryService;
import com.zz91.ep.domain.comp.CompCategory;

/**
 *
 */
@Transactional
@Service("compCategoryService")
public class CompCategoryServiceImpl implements CompCategoryService{

    @Resource
    private CompCategoryDao compCategoryDao;

	@Override
	public List<CompCategory> listAllCompCategory() {
		return compCategoryDao.listAllCompCategory();
	}

	@Override
	public Integer createCompCategory(CompCategory compCategory) {
		return compCategoryDao.createCompCategory(compCategory);
		
	}

	@Override
	public Integer deleteCompCategory(Integer id) {
		return compCategoryDao.deleteCompCategory(id);
	}

	@Override
	public CompCategory listOneCompCategoryById(Integer id) {
		return compCategoryDao.listOneCompCategoryById(id);
	}

	@Override
	public Integer updateCompCategory(CompCategory compCategory) {
		return compCategoryDao.updateCompCategory(compCategory);
	}

	@Override
	public Map<String, String> listAllCompCategoryToMap() {
		List<CompCategory> list = compCategoryDao.listAllCompCategory();
		Map<String, String> map = new HashMap<String, String>();
		for (CompCategory compCategory : list) {
			map.put(compCategory.getCode(), compCategory.getName());
		}
		return map;
	}

    
}