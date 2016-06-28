/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-24
 */
package com.ast.ast1949.persist.information.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.ExhibitDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.information.ExhibitDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("exhibiteDAO")
public class ExhibitDAOImpl extends BaseDaoSupport implements ExhibitDAO {
	
	final private int DEFAULT_BATCH_SIZE = 20;
	
	final static String SQL_PREFIX="exhibit";

	public Integer deleteExhibitById(int[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().update("exhibit.deleteExhibitById",
							entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check friend links failed.", e);
		}
		return impacted;
	}

	public Integer insertExhibit(ExhibitDO exhibitDO) {
		Assert.notNull(exhibitDO, "exhibitDO is not null");
		return Integer.valueOf(getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertExhibit"), exhibitDO)
				.toString());
	}

	@SuppressWarnings("unchecked")
	public List<ExhibitDTO> queryExhibitByCondition(ExhibitDTO exhibitDTO) {
		Assert.notNull(exhibitDTO, "exhibitDTO is not null");
		return getSqlMapClientTemplate()
				.queryForList("exhibit.queryExhibitByCondition", exhibitDTO);
	}

	public Integer queryExhibitCountByCondition(ExhibitDTO exhibitDTO) {
		Assert.notNull(exhibitDTO, "exhibitDTO is not null");
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"exhibit.queryExhibitCountByCondition", exhibitDTO);
	}

	public Integer updateExhibit(ExhibitDO exhibitDO) {
		Assert.notNull(exhibitDO, "exhibitDO is not null");
		return getSqlMapClientTemplate().update("exhibit.updateExhibit", exhibitDO);
	}

//	public ExhibitDTO queryExhibitById(Integer id) {
//		Assert.notNull(id, "id is not null");
//		return (ExhibitDTO) getSqlMapClientTemplate().queryForObject("exhibit.queryExhibitById", id);
//	}

	public ExhibitDO selectExhibitById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (ExhibitDO) getSqlMapClientTemplate().queryForObject("exhibit.selectExhibitById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitDO> queryExhibit(ExhibitDO exhibitDO,
			PageDto<ExhibitDTO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("exhibit", exhibitDO);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList("exhibit.queryExhibit", root);
	}

	@Override
	public Integer queryExhibitCount(ExhibitDO exhibitDO) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("exhibit", exhibitDO);
		return (Integer) getSqlMapClientTemplate().queryForObject("exhibit.queryExhibitCount", root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitDO> queryExhibitByPlateCategory(String plateCategory,
			Integer max) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("plateCategory", plateCategory);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList("exhibit.queryExhibitByPlateCategory", root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitDO> queryExhibitByCategoryCode(String plateCategoryCode,
			String exhibitCategoryCode, Integer limitSize) {
		if(plateCategoryCode==null&&exhibitCategoryCode==null){
			Assert.notNull(null, "plateCategoryCode or exhibitCategoryCode can not both be null");
		}
		Assert.notNull(limitSize, "limitSize is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("plateCategoryCode", plateCategoryCode);
		map.put("exhibitCategoryCode",exhibitCategoryCode);
		map.put("limitSize", limitSize);
		return getSqlMapClientTemplate().queryForList("exhibit.queryExhibitByCategoryCode", map);
	}

	@Override
	public String queryContent(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryContent"), id);
	}

	@Override
	public Integer updateContent(Integer id, String content) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("content", content);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateContent"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitDO> queryExhibitByAdmin(ExhibitDO exhibit,
			PageDto<ExhibitDTO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("exhibit", exhibit);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryExhibitByAdmin"), root);
	}

	@Override
	public Integer queryExhibitByAdminCount(ExhibitDO exhibit) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("exhibit", exhibit);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryExhibitByAdminCount"), root);
	}

	@Override
	public Integer deleteExhibit(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteExhibitById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitDTO> queryNewestExhibit(String plateCategoryCode,
			String exhibitCategoryCode, Integer size) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("plateCategoryCode", plateCategoryCode);
		map.put("exhibitCategoryCode", exhibitCategoryCode);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryNewestExhibit"), map);
	}

	@Override
	public ExhibitDO queryUpNews(String plateCategory, String gmtCreated) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("plateCategoryCode", plateCategory);
		map.put("gmtCreated", gmtCreated);
		return (ExhibitDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryUpNews"), map);
	}

	@Override
	public ExhibitDO queryDownNews(String plateCategory, String gmtCreated) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("plateCategoryCode", plateCategory);
		map.put("gmtCreated", gmtCreated);
		return (ExhibitDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryDownNews"), map);
	}
	
}
