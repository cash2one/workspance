/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-25
 */
package com.ast.ast1949.persist.price.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.dto.price.PriceCategoryMinDto;
import com.ast.ast1949.persist.price.PriceCategoryDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */

@Component("priceCategoryDAO")
public class PriceCategoryDAOImpl extends SqlMapClientDaoSupport implements PriceCategoryDAO{

//	final private int DEFAULT_BATCH_SIZE=20;
//	public Integer batchDeletePriceCategoryById(int[] entities) {
//		int impacted=0;
//		int batchNum=(entities.length+DEFAULT_BATCH_SIZE-1)/DEFAULT_BATCH_SIZE;
//		try {
//			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
//				getSqlMapClient().startBatch();
//				int beginIndex=currentBatch*DEFAULT_BATCH_SIZE;
//				int endIndex=(currentBatch+1)*DEFAULT_BATCH_SIZE;
//				endIndex=endIndex>entities.length?entities.length:endIndex;
//				for (int i = beginIndex; i < beginIndex; i++) {
//					impacted+=getSqlMapClientTemplate().delete("priceCategory.deletePriceCategoryById",entities[i]);
//				}
//				getSqlMapClient().executeBatch();
//			}
//		} catch (Exception e) {
//			throw new PersistLayerException("delete price failure.",e);
//		}
//
//		return impacted;
//	}
	public String queryTagNameByTypeId(Integer id) {
		//Assert.notNull(id, "The id must not be null");
		if(id==null){
			return null;
		}
		return (String)getSqlMapClientTemplate().queryForObject("priceCategory.queryTypeNameByTypeId", id);
	};
	public Integer deletePriceCategoryById(Integer id)
			throws IllegalArgumentException {
		Assert.notNull(id, "The id must not be null.");
		return getSqlMapClientTemplate().delete("priceCategory.deletePriceCategoryById", id);
	}

//	public Integer getPriceCategoryRecordConutByCondition(
//			PriceCategoryDTO priceCategoryDto) {
//		return (Integer) getSqlMapClientTemplate().queryForObject("priceCategory.getPriceCategoryRecordConutByCondition", priceCategoryDto);
//	}

	public Integer insertPriceCategory(PriceCategoryDO priceCategoryDO)
			throws IllegalArgumentException {
		Assert.notNull(priceCategoryDO, "The object of priceCategory must not be null");
		Assert.notNull(priceCategoryDO.getName(), "The name of priceCategory must not be null");

		return (Integer) getSqlMapClientTemplate().insert("priceCategory.insertPriceCategory",priceCategoryDO);
	}

	@SuppressWarnings("unchecked")
	public List<PriceCategoryDO> queryPriceCategoryByCondition(
			PriceCategoryDTO priceCategoryDto) {
		return getSqlMapClientTemplate().queryForList("priceCategory.queryPriceCategoryByCondition", priceCategoryDto);
	}

	public PriceCategoryDO queryPriceCategoryById(Integer id)
			throws IllegalArgumentException {
		return (PriceCategoryDO) getSqlMapClientTemplate().queryForObject("priceCategory.queryPriceCategoryById", id);
	}

	public Integer updatePriceCategoryById(PriceCategoryDO priceCategory)
			throws IllegalArgumentException {
		Assert.notNull(priceCategory, "The PriceDO must not be null");
		Assert.notNull(priceCategory.getId(), "The Id must not be null");
		return getSqlMapClientTemplate().update("priceCategory.updatePriceCategoryById", priceCategory);
	}

	public Integer updatePriceCategoryIsDeleteById(Integer id, short isDelete)
			throws IllegalArgumentException {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<PriceCategoryDO> queryPriceCategoryByParentId(Integer id)
			throws IllegalArgumentException {
		Assert.notNull(id, "The parent id must not be null.");
		return getSqlMapClientTemplate().queryForList("priceCategory.queryPriceCategoryByParentId", id);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<PriceCategoryLinkDO> queryPriceCategoryLink(
//			PriceCategoryLinkDO priceCategoryLinkDO) {
//		Assert.notNull(priceCategoryLinkDO, "priceCategoryLinkDO is not null");
//		return getSqlMapClientTemplate().queryForList("priceCategory.queryPriceCategoryLink", priceCategoryLinkDO);
//	}

	@Override
	public PriceCategoryDTO queryPriceCategoryDtoById(Integer id) {
		Assert.notNull(id, "The id must not be null.");
		return (PriceCategoryDTO) getSqlMapClientTemplate().queryForObject("priceCategory.queryPriceCategoryDtoById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCategoryDO> queryAssistPriceCategoryByTypeId(Integer id) {
		Assert.notNull(id, "The id must not be null.");
		return getSqlMapClientTemplate().queryForList("priceCategory.queryAssistPriceCategoryByTypeId", id);
	}

//	@Override
//	public Integer countPriceCategoryByCondition(PriceCategoryDTO priceCategoryDto) {
//		return (Integer) getSqlMapClientTemplate().queryForObject("priceCategory.countPriceCategoryByCondition", priceCategoryDto);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Integer> queryAssistPriceCategoryIdByTypeId(Integer id) {
//		Assert.notNull(id, "The id must not be null.");
//		return getSqlMapClientTemplate().queryForList("priceCategory.queryAssistPriceCategoryIdByTypeId", id);
//	}

//	@Override
//	public Integer countPriceCategoryByParentId(Integer id) {
//		Assert.notNull(id, "The id must not be null.");
//		return (Integer) getSqlMapClientTemplate().queryForObject("priceCategory.countPriceCategoryByParentId", id);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCategoryMinDto> queryPriceCategoryByParentIdOrderList(Integer parentId) {
		return getSqlMapClientTemplate().queryForList("priceCategory.queryPriceCategoryByParentIdOrderList", parentId);
	}

	@Override
	public Integer queryParentIdById(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject("priceCategory.queryParentIdById",id);
	}

	@Override
	public Integer countChild(Integer parentId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("priceCategory.countChild", parentId);
	}

	@Override
	public Integer queryIdByName(String name) {
		return (Integer)getSqlMapClientTemplate().queryForObject("priceCategory.queryIdByName",name);
	}
	@Override
	public Integer updateSearchLabel(String key, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchLabel", key);
		map.put("id", id);
		return getSqlMapClientTemplate().update("priceCategory.updateSearchLabel", map);
	}
	@Override
	public String queryForPinyin(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject("priceCategory.queryForPinyin", id);
	}

}
