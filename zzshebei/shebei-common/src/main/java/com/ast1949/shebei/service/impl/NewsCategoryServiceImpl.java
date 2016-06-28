/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-7 下午03:50:32
 */
package com.ast1949.shebei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast1949.shebei.dao.NewsCategoryDao;
import com.ast1949.shebei.domain.NewsCategory;
import com.ast1949.shebei.service.NewsCategoryService;

@Component("newsCategoryService")
public class NewsCategoryServiceImpl implements NewsCategoryService {

	@Resource
	private NewsCategoryDao newsCategoryDao;
	
	@Override
	public List<NewsCategory> queryAllNewsCategory() {
		return newsCategoryDao.queryAllNewsCategory();
	}

	@Override
	public NewsCategory queryNameAndKeywordsByCode(String category) {
		return newsCategoryDao.queryNameAndKeywordsByCode(category);
	}

}
