package com.ast1949.shebei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast1949.shebei.dao.CategoryProductsDao;
import com.ast1949.shebei.domain.CategoryProducts;
import com.ast1949.shebei.service.CategoryProductsService;

@Component("categoryProductsService")
public class CategoryProductsServiceImpl implements CategoryProductsService {
	
	@Resource
	private CategoryProductsDao categoryProductsDao;
	
	@Override
	public List<CategoryProducts> queryAllCategorys(Short flag,String code) {
	
		return categoryProductsDao.queryAllCategorys(flag, code);
	}

	@Override
	public CategoryProducts queryCtypeByCode(String code) {
		
		return categoryProductsDao.queryCtypeByCode(code);
	}
}
