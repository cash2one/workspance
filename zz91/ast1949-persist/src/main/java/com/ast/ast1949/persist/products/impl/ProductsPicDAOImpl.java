/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-26
 */
package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsPicDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("productsPicDAO")
public class ProductsPicDAOImpl extends BaseDaoSupport implements
		ProductsPicDAO {

	private final static String sqlPreFix = "productsPic";

	@SuppressWarnings("unchecked")
	public List<ProductsPicDO> queryProductsPicByCondition(
			ProductsPicDTO productsPicDTO) {
		Assert.notNull(productsPicDTO, "productsPicDTO is not null");
		return getSqlMapClientTemplate().queryForList(
				"productsPic.queryProductsPicByCondition", productsPicDTO);
	}

	public int getProductsPicRecordCountByCondition(
			ProductsPicDTO productsPicDTO) {

		return Integer.valueOf(getSqlMapClientTemplate().queryForObject(
				"productsPic.getProductsPicRecordCountByCondition",
				productsPicDTO).toString());
	}

	// @SuppressWarnings("unchecked")
	// public List<ProductsPicDO> queryProductPicByproductId(int productId) {
	//
	// Assert.notNull(productId, "productId is not null");
	// return
	// getSqlMapClientTemplate().queryForList("productsPic.queryProductPicByproductId",
	// productId);
	// }

	public int insertProductsPic(ProductsPicDO productsPicDO) {

		return Integer.valueOf(getSqlMapClientTemplate().insert(
				"productsPic.insertProductsPic", productsPicDO).toString());
	}

	public int updateProductsPic(ProductsPicDO productsPicDO) {

		return getSqlMapClientTemplate().update(
				"productsPic.updateProductsPic", productsPicDO);
	}

	public ProductsPicDTO queryProductPicById(int id) {

		return (ProductsPicDTO) getSqlMapClientTemplate().queryForObject(
				"productsPic.queryProductPicById", id);
	}

	final private int DEFAULT_BATCH_SIZE = 20;

	public int batchDeleteProductPicbyId(Integer[] entities) {
		Assert.notNull(entities, "entities is not null");
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
							"productsPic.batchDeleteProductsPicbyId",
							entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete user failed.", e);
		}
		return impacted;
	}

	public Integer updateProductsPicAddr(ProductsPicDO productsPicDO) {
		return getSqlMapClientTemplate().update(
				"productsPic.updateProductsPicAddr", productsPicDO);
	}

	public Integer updateProductsPicName(ProductsPicDO productsPicDO) {
		return getSqlMapClientTemplate().update(
				"productsPic.updateProductsPicName", productsPicDO);
	}

	public Integer updateProductsPicIsDefault(ProductsPicDO productsPicDO) {
		return getSqlMapClientTemplate().update(
				"productsPic.updateProductsPicIsDefault", productsPicDO);
	}

	// @SuppressWarnings("unchecked")
	// public List<ProductsPicDTO> queryAllProductsPicByTitle(
	// ProductsDTO productsDTO) {
	// return
	// getSqlMapClientTemplate().queryForList("productsPic.queryAllProductsPicByTitle",
	// productsDTO);
	// }

	// public Integer queryAllProductsPicByTitleCount(ProductsDTO productsDTO) {
	// return Integer.valueOf(getSqlMapClientTemplate()
	// .queryForObject("productsPic.queryAllProductsPicByTitleCount",
	// productsDTO).toString());
	// }

	// @SuppressWarnings("unchecked")
	// public List<ProductsPicDTO> queryforthProductsPic(
	// ProductsDO productsDO) {
	// return getSqlMapClientTemplate()
	// .queryForList("productsPic.queryforthProductsPic", productsDO);
	// }

	public ProductsPicDTO queryProductsPicDetails(Integer id) {
		return (ProductsPicDTO) getSqlMapClientTemplate().queryForObject(
				"productsPic.queryProductsPicDetails", id);
	}

	// @SuppressWarnings("unchecked")
	// public List<ProductsPicDTO> queryAllProductsPicByHadSubSeries(
	// ProductsDTO productsDTO) {
	// return getSqlMapClientTemplate()
	// .queryForList("productsPic.queryAllProductsPicByHadSubSeries",
	// productsDTO);
	// }

	// public Integer queryAllProductsPicByHadSubSeriesCount(
	// ProductsDTO productsDTO) {
	// return Integer.valueOf(getSqlMapClientTemplate()
	// .queryForObject("productsPic.queryAllProductsPicByHadSubSeriesCount",
	// productsDTO).toString());
	// }

	// @SuppressWarnings("unchecked")
	// public List<ProductsPicDTO> queryAllProductsPicByNoHadSubSeries(
	// ProductsDTO productsDTO) {
	// return getSqlMapClientTemplate()
	// .queryForList("productsPic.queryAllProductsPicByNoHadSubSeries",
	// productsDTO);
	// }

	// public Integer queryAllProductsPicByNoHadSubSeriesCount(
	// ProductsDTO productsDTO) {
	// return Integer.valueOf(getSqlMapClientTemplate()
	// .queryForObject("productsPic.queryAllProductsPicByNoHadSubSeriesCount",
	// productsDTO).toString());
	// }

	@SuppressWarnings("unchecked")
	public List<ProductsPicDO> queryProductPicInfoByProductsId(Integer productId) {
		Assert.notNull(productId, "The productId can not be null");
		return getSqlMapClientTemplate().queryForList(
				"productsPic.queryProductPicInfoByProductsId", productId);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsPicDO> queryProductPicInfoByProductsIdForFront(
			Integer productId) {
		return getSqlMapClientTemplate().queryForList(
				"productsPic.queryProductPicInfoByProductsIdForFront",
				productId);
	}

	@Override
	public Integer updateProductsIdById(Integer productId, Integer id) {
		Assert.notNull(productId, "The productId can not be null");
		Assert.notNull(id, "The id can not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("productId", productId);
		root.put("id", id);
		return getSqlMapClientTemplate().update(
				"productsPic.updateProductsIdById", root);
	}

	@Override
	public Integer countProductPicByProductId(Integer productId) {

		return (Integer) getSqlMapClientTemplate().queryForObject(
				"productsPic.countProductPicByProductId", productId);
	}

	@Override
	public String queryPicPath(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(
				"productsPic.queryPicPath", id);
	}

	@Override
	public String queryPicPathByProductId(Integer productId) {
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryPicPathByProductId"),
				productId);
	}

	@Override
	public String queryPicByProId(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryPicByProId"), id);
	}

	@Override
	public Integer batchUpdatePicStatus(Integer[] entities, String checkStatus,
			String unpassReason) {
		Assert.notNull(entities, "entities is not null");
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
					impacted += updatePicStatus(checkStatus, unpassReason,
							entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete user failed.", e);
		}
		return impacted;
	}

	public Integer updatePicStatus(String checkStatus, String unpassReason,
			Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", checkStatus);
		map.put("unpassReason", unpassReason);
		map.put("id", id);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updatePicStatus"), map);
	}

	@Override
	public Integer countPicIsNoPass(Integer productId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "countPicIsNoPass"), productId);
	}

	@Override
	public Integer updateIsDefaultByProductId(Integer productId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("status", status);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateIsDefaultByProductId"), map);
	}

	// 根据id更新isDefault
	@Override
	public Integer updateProductsPicIsDefaultById(Integer id, String isDefault) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("isDefault", isDefault);

		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateProductsPicIsDefaultById"),
				map);

	}

}
