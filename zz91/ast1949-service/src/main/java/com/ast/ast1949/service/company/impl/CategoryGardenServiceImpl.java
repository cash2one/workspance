package com.ast.ast1949.service.company.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.dto.company.CategoryGardenDTO;
import com.ast.ast1949.persist.company.CategoryGardenDAO;
import com.ast.ast1949.service.company.CategoryGardenService;

@Component
public class CategoryGardenServiceImpl implements CategoryGardenService {

	@Autowired
	private CategoryGardenDAO categoryGardenDAO;

	public int getCategoryGardenRecordCountByCondition(CategoryGardenDTO pageParam) {
		return categoryGardenDAO.getCategoryGardenRecordCountByCondition(pageParam);
	}

	public CategoryGardenDO queryCategoryGardenById(int id) {
		return categoryGardenDAO.queryCategoryGardenById(id);
	}

	public List<CategoryGardenDTO> queryCategoryGardenByCondition(
			CategoryGardenDTO pageParam) {
		return categoryGardenDAO.queryCategoryGardenByCondition(pageParam);
	}

	public int batchDeleteCategoryGrdenById(int[] entities) {

		return categoryGardenDAO.batchDeleteCategoryGrdenById(entities);
	}

	public int updateCategoryGrden(CategoryGardenDO categoryGardenDO) {

		return categoryGardenDAO.updateCategoryGrden(categoryGardenDO);
	}

	public int insertCategoryGrden(CategoryGardenDO categoryGardenDO) {

		return categoryGardenDAO.insertCategoryGrden(categoryGardenDO);

	}

//	public List<CategoryGardenDO> queryCategoryGardenByAreaCode(CategoryGardenDO categoryGardenDO) {
//		Assert.notNull(categoryGardenDO, "The id can not be null");
//		return categoryGardenDAO.queryCategoryGardenByAreaCode(categoryGardenDO);
//	}

	public List<CategoryGardenDO> queryCategoryGardenBySomeCode(
			CategoryGardenDO categoryGardenDO) {
		return categoryGardenDAO.queryCategoryGardenBySomeCode(categoryGardenDO);
	}

//	public List<CategoryGardenDO> queryCategoryGardenByAreaCodeFroRegister(
//			CategoryGardenDO categoryGardenDO) {
//		return categoryGardenDAO.queryCategoryGardenByAreaCodeFroRegister(categoryGardenDO);
//	}

	@Override
	public List<CategoryGardenDO> queryCategoryGardenByAreaCode(String areaCode) {
		return categoryGardenDAO.queryCategoryGardenByAreaCode(areaCode);
	}


}
