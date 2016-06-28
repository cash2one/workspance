package com.zz91.ep.admin.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.SubnetCategoryDao;
import com.zz91.ep.admin.service.trade.SubnetCategoryService;
import com.zz91.ep.domain.trade.SubnetCategory;

@Component("subnetCategoryService")
public class SubnetCategoryServiceImpl implements SubnetCategoryService {
	
	@Resource
	private SubnetCategoryDao subnetCategoryDao;

	@Override
	public List<SubnetCategory> queryCategoryByParentId(Integer parentId) {
		return subnetCategoryDao.queryCategoryByParentId(parentId);
	}

	@Override
	public Integer createSubnetCategory(SubnetCategory category) {
		if (category.getParentId()==null){
			category.setParentId(0);
		}
		if (category.getShowIndex()==null){
			category.setShowIndex((short)0);
		}
		return subnetCategoryDao.insertSubnetCategory(category);
	}

	@Override
	public Integer updateSubnetCategory(SubnetCategory category) {
		if (category.getParentId()==null){
			category.setParentId(0);
		}
		if (category.getShowIndex()==null){
			category.setShowIndex((short)0);
		}
		return subnetCategoryDao.updateSubnetCategory(category);
	}

	@Override
	public Integer deleteCategory(Integer id) {
		Integer count=subnetCategoryDao.queryChildCountByParentId(id);
		 if (count!=null && count>0){
			 deleteChildCategory(null, id);
		 }
		Integer i=subnetCategoryDao.deleteCategoryByIdOrParentId(id,null);
		return i;
	}

	@Override
	public Integer deleteChildCategory(Integer id, Integer parentId) {
		return subnetCategoryDao.deleteCategoryByIdOrParentId(id, parentId);
	}

	@Override
	public List<SubnetCategory> queryAllCategory() {
		return subnetCategoryDao.queryAllCategory();
	}

}
