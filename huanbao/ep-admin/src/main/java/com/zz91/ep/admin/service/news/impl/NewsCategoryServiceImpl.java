package com.zz91.ep.admin.service.news.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.news.NewsCategoryDao;
import com.zz91.ep.admin.service.news.NewsCategoryService;
import com.zz91.ep.domain.news.NewsCategory;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.util.Assert;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-9-28 
 */
@Component("newsCategoryService")
public class NewsCategoryServiceImpl implements NewsCategoryService {
	
	@Resource
	private NewsCategoryDao newsCategoryDao;
	
	@Override
	public List<NewsCategory> queryNewsCategoryAll() {
		return newsCategoryDao.queryNewsCategoryAll();
	}
	@Override
	public Integer createCategory(NewsCategory category, String preCode){
		Assert.notNull(category, "the category can not be null");
		String code = getMaxCode(preCode);
		category.setCode(code);
		if (category.getSort()==null || category.getSort().intValue()==0){
			category.setSort(0);
		}
		if (category.getShowIndex()==null || category.getShowIndex()==0){
			category.setShowIndex(0);
		}
		if(category.getShowIndex()==null){
			category.setShowIndex(0);
		}
		return newsCategoryDao.insertCategory(category);
	}
	
	private String getMaxCode(String preCode){
		String code = newsCategoryDao.queryMaxCodeByPreCode(preCode);
		if (code==null || code.length()==0) {
			code=preCode + "1000";
		} else if(code.length()==4) {
			code=String.valueOf(Integer.valueOf(code)+1);
		} else if (code.length()==8) {
			code=String.valueOf(Integer.valueOf(code)+1);
		} else {
			String code1 = code.substring(code.length() - 4, code.length());
			String code2 = code.substring(0,code.length() - 4);
			code= code2 + String.valueOf(Integer.valueOf(code1)+1);
		}
		return code; 
	}
	
	@Override
	public List<ExtTreeDto> queryChild(String code) {
		List<NewsCategory> cateList = newsCategoryDao.queryCategoryByParentCode(code);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for (NewsCategory category : cateList) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(category.getId()));
			Integer i =  newsCategoryDao.countNewsCategoryChild(category.getCode());
			if(i==null || i.intValue()<=0)
				node.setLeaf(true);
			
			node.setText(category.getName());
			node.setData(category.getCode());
			treeList.add(node);
		}
		return treeList;
	}
	
//	@Override
//	public Integer updateCategory(String code, String name, Integer sort,
//			String tags) {
//		return newsCategoryDao.updateCategory(code, name, sort, tags);
//	}
	
	@Override
	public Integer deleteCategoryByCode(String code) {
		return newsCategoryDao.deleteCategoryByCode(code);
	}
	@Override
	public Integer updateCategory(NewsCategory newsCategory) {
		if(newsCategory.getShowIndex()==null){
			newsCategory.setShowIndex(0);
		}
		return newsCategoryDao.updateCategory(newsCategory);
	}
	@Override
	public NewsCategory queryNewsCategoryByCode(String code) {
		return newsCategoryDao.queryOneNewsCategory(code);
	}

}
