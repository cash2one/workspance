/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-26.
 */
package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsKeywordsRankDO;
import com.ast.ast1949.dto.products.ProductsCompanyDTO;
import com.ast.ast1949.dto.products.ProductsKeywordsRankDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsKeywordsRankDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("productsKeywordsRankDAO")
public class ProductsKeywordsRankDAOImpl extends BaseDaoSupport implements ProductsKeywordsRankDAO {

	final static int DEFAULT_BATCH_SIZE = 20;
	
	final static String SQL_PREFIX="productsKeywordsRanks";
		
	public Integer insertProductsKeywordsRank(ProductsKeywordsRankDO productsKeywordsRank) {
		Assert.hasLength(productsKeywordsRank.getName(), "the name must not be null");
		Assert.notNull(productsKeywordsRank.getName(), "the name must not be null");
		Assert.notNull(productsKeywordsRank.getProductId(), "the productId must not be null");
		Assert.notNull(productsKeywordsRank.getType(), "the type must not be null");
		return Integer.valueOf(getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertProductsKeywordsRank"),
				productsKeywordsRank).toString());
	}

	public Integer deleteProductsKeywordsRankById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return Integer.valueOf(getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteProductsKeywordsRankById"), id));
	}

	public Integer updateCheckedById(String isChecked, Integer ids[]) {
		Assert.notNull(ids, "the ids is not null");
		
		int impacted = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > ids.length ? ids.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					map.put("id", ids[i]);
					map.put("isChecked", isChecked);
					impacted += getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCheckedById"), map);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch updateCheckedById failed.", e);
		}
		return impacted;
	}

	public Integer updateProductsKeywordsRankById(ProductsKeywordsRankDO productsKeywordsRank) {
		Assert.notNull(productsKeywordsRank, "the productsKeywordsRank is not null");
		return getSqlMapClientTemplate()
				.update(addSqlKeyPreFix(SQL_PREFIX, "updateProductsKeywordsRankById"), productsKeywordsRank);
	}

	public Integer countProductsKeywordsRankByConditions(ProductsKeywordsRankDTO condition) {
		Assert.notNull(condition, "the condition is not null");
		return Integer.valueOf(getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, 
				"countProductsKeywordsRankByConditions"), condition).toString());
	}

	@SuppressWarnings("unchecked")
	public List<ProductsKeywordsRankDTO> queryProductsKeywordsRankByConditions(
			ProductsKeywordsRankDTO condition) {
		Assert.notNull(condition, "the condition is not null");
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryProductsKeywordsRankByConditions"),
				condition);
	}

	public ProductsKeywordsRankDO queryProductsKeywordsRankById(Integer id) {
		Assert.notNull(id, "the id is not null");
		return (ProductsKeywordsRankDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, 
				"queryProductsKeywordsRankById"), id);
	}

//	@SuppressWarnings("unchecked")
//	public List<ProductsKeywordsRankDO> queryProductsKeywordsRankByProductId(Integer id) {
//		Assert.notNull(id, "the id is not null");
//		return getSqlMapClientTemplate().queryForList("queryProductsKeywordsRankByProductId", id);
//	}

	@Override
	public List<ProductsCompanyDTO> queryProductsByKeywordsAndBuiedType(String keywords,
			String buiedType, int topNum) {
		Map paramMap = new HashMap();
		paramMap.put("keywords", keywords);
		paramMap.put("keywordsBuiedType", buiedType);
		paramMap.put("topNum", topNum < 1 ? 1 : topNum);
		return getSqlMapClientTemplate().queryForList(super.addSqlKeyPreFix(SQL_PREFIX,
				"queryProductsByKeywordsAndBuiedType"), paramMap);
	}
	
	public List<Integer> queryProductsId(String keywords,String buiedType, Integer maxSize){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("keywords", keywords);
		root.put("buiedType", buiedType);
		root.put("maxSize", maxSize);
		
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryProductsId"), root);
	}
	@SuppressWarnings("unchecked")
	public List<ProductsKeywordsRankDTO> queryProductsKeywordsRankByCompanyId(Integer companyId){
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryProductsKeywordsRankByCompanyId"), companyId);
	}

}
