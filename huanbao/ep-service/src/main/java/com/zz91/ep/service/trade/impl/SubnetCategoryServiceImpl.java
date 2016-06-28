package com.zz91.ep.service.trade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.trade.SubnetCategoryDao;
import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.dto.trade.SubnetCategoryDto;
import com.zz91.ep.service.trade.SubnetCategoryService;

@Component("subnetCategoryService")
public class SubnetCategoryServiceImpl implements SubnetCategoryService {
	
	@Resource
	private SubnetCategoryDao subnetCategoryDao;

	@Override
	public List<SubnetCategory> queryCategoryByParentId(Integer parentId,Integer size) {
		return subnetCategoryDao.queryCategoryByParentId(parentId,size);
	}

	@Override
	public List<SubnetCategoryDto> queryAllCategory(Integer id) {
		List<SubnetCategoryDto> result = new ArrayList<SubnetCategoryDto>();
		List<SubnetCategory> list = subnetCategoryDao.queryCategoryByParentId(id,null);
		for (SubnetCategory sub:list) {
			SubnetCategoryDto dto = new SubnetCategoryDto();
			dto.setCode(sub.getCode());
			dto.setName(sub.getName());
			dto.setChild(subnetCategoryDao.queryCategoryByParentId(sub.getId(),null));
			result.add(dto);
		}
		return result;
	}

	@Override
	public SubnetCategory queryCategoryByCode(String code) {
		return subnetCategoryDao.queryCategoryByCode(code);
	}

	@Override
	public SubnetCategory querySubCateById(Integer id) {
		return subnetCategoryDao.querySubCateById(id);
	}


}
