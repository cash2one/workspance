package com.ast.ast1949.persist.company.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.dto.company.CategoryGardenDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CategoryGardenDAO;
import com.ast.ast1949.util.Assert;

@Component("CategoryGardenDAO")
public class CategoryGardenDAOImpl extends BaseDaoSupport implements CategoryGardenDAO {
	
	public CategoryGardenDO queryGardenNameById(Integer id){
		
		return (CategoryGardenDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix("categoryGarden", "queryGardenNameById"), id);
	}

	public int getCategoryGardenRecordCountByCondition(CategoryGardenDTO pageParam)throws IllegalArgumentException {
		Assert.notNull(pageParam, "parent code can not be null");
		return Integer.valueOf(getSqlMapClientTemplate().queryForObject(
				"categoryGarden.getCategoryGardenRecordCountByCondition",
				pageParam).toString());
	}

	@SuppressWarnings("unchecked")
	public List<CategoryGardenDTO> queryCategoryGardenByCondition(CategoryGardenDTO pageParam)throws IllegalArgumentException {
		Assert.notNull(pageParam, "pageParam code can not be null");
		return getSqlMapClientTemplate().queryForList("categoryGarden.queryCategoryGardenByCondition",
				pageParam);
	}

	public CategoryGardenDO queryCategoryGardenById(int id)throws IllegalArgumentException {
		Assert.notNull(id, "id can not be null");
		CategoryGardenDO categoryGardenDO = (CategoryGardenDO) getSqlMapClientTemplate()
				.queryForObject("categoryGarden.queryCategoryGardenById", id);
		return categoryGardenDO;
	}

	final private int DEFAULT_BATCH_SIZE = 20;

	public int batchDeleteCategoryGrdenById(int[] entities) throws IllegalArgumentException{
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().delete(
							"categoryGarden.batchDeleteCategoryGrdenById", entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete user failed.", e);
		}
		return impacted;
	}

	public int updateCategoryGrden(CategoryGardenDO categoryGardenDO)throws IllegalArgumentException {
		Assert.notNull(categoryGardenDO, "categoryGardenDO code can not be null");
		return getSqlMapClientTemplate().update("categoryGarden.updateCategoryGrden",
				categoryGardenDO);
	}

	public int insertCategoryGrden(CategoryGardenDO categoryGardenDO)throws IllegalArgumentException {
		Assert.notNull(categoryGardenDO, "categoryGardenDO code can not be null");
		return (Integer) getSqlMapClientTemplate().insert(
				"categoryGarden.insertCategoryGrden", categoryGardenDO);
	}

//	@SuppressWarnings("unchecked")
//	public List<CategoryGardenDO> queryCategoryGardenByAreaCode(CategoryGardenDO categoryGardenDO) {
//		return getSqlMapClientTemplate().queryForList("categoryGarden.queryCategoryGardenByAreaCode",
//				categoryGardenDO);
//	}

	@SuppressWarnings("unchecked")
	public List<CategoryGardenDO> queryCategoryGardenBySomeCode(
			CategoryGardenDO categoryGardenDO) {
		return getSqlMapClientTemplate().queryForList("categoryGarden.queryCategoryGardenBySomeCode", 
				categoryGardenDO);
	}

//	@SuppressWarnings("unchecked")
//	public List<CategoryGardenDO> queryCategoryGardenByAreaCodeFroRegister(
//			CategoryGardenDO categoryGardenDO) {
//		return getSqlMapClientTemplate().queryForList("categoryGarden.queryCategoryGardenByAreaCodeFroRegister", categoryGardenDO);
//	}

	@Override
	public List<CategoryGardenDO> queryCategoryGardenByAreaCode(String areaCode) {
		return getSqlMapClientTemplate().queryForList("categoryGarden.queryCategoryGardenByAreaCode", areaCode);
	}
}
