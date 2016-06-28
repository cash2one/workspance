/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-29
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.price.PriceOffer;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.MyfavoriteDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.MyfavoriteDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("myfavoriteDAO")
public class MyfavoriteDAOImpl extends BaseDaoSupport implements MyfavoriteDAO{

	final private int DEFAULT_BATCH_SIZE = 20;
	final String SQL_PREFIX = "myfavorite";
	public Integer bathDeleteMyfavoriteById(int entities[]) {
		
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
					impacted += getSqlMapClientTemplate().update(
							"myfavorite.deleteMyfavoriteById", entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check friend links failed.",
					e);
		}
		return impacted;
	
	}

	public Integer insertMyfavorite(MyfavoriteDO myfavoriteDO) {
		Assert.notNull(myfavoriteDO, "myfavoriteDO is not null");
		return Integer.valueOf(getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertMyfavorite"), myfavoriteDO).toString());
	}

	@SuppressWarnings("unchecked")
	public List<MyfavoriteDTO> queryMyfavoriteByCondition(MyfavoriteDTO myfavoriteDTO) {
		Assert.notNull(myfavoriteDTO, "myfavoriteDTO is not null");
		return getSqlMapClientTemplate().queryForList("myfavorite.queryMyfavoriteByCondition", myfavoriteDTO);
	}

	public MyfavoriteDO queryMyfavoriteByMap(Integer contentId, Integer companyId) {
		Assert.notNull(contentId, "id is not null");
		Assert.notNull(companyId, "companyId is not null");
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contentId", contentId);
		map.put("companyId", companyId);
		return (MyfavoriteDO) getSqlMapClientTemplate().queryForObject("myfavorite.queryMyfavoriteByMap", map);
	}

	public Integer queryMyfavoriteCountByCondition(MyfavoriteDTO myfavoriteDTO) {
         Assert.notNull(myfavoriteDTO, "myfavoriteDTO is not null");
		return Integer.valueOf(getSqlMapClientTemplate().queryForObject("myfavorite.queryMyfavoriteCountByCondition", myfavoriteDTO).toString());
	}

	@Override
	public Integer isExist(Integer companyId, Integer contentId,
			String favoriteTypeCode) {
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("contentId", contentId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "isExist"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MyfavoriteDO> pageMyCollect(PageDto<MyfavoriteDO> page,String keywrods,
			Integer companyId, String favoriteTypeCode, Integer theday) {
		Assert.notNull(companyId, "companyId is not null");
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("keywrods", keywrods);
		map.put("companyId", companyId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		map.put("theday", theday);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "pageMyCollect"), map);
	}

	@Override
	public Integer pageMyCollectCount(String keywrods, Integer companyId,
			String favoriteTypeCode, Integer theday) {
		Assert.notNull(companyId, "companyId is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("keywrods", keywrods);
		map.put("companyId", companyId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		map.put("theday", theday);
		return Integer.valueOf(getSqlMapClientTemplate().queryForObject("myfavorite.pageMyCollectCount", map).toString());
	}

	@Override
	public Integer deleteMyCollect(Integer companyId, Integer id) {
		Assert.notNull(companyId, "companyId is not null");
		Assert.notNull(id, "id is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("id", id);
		
		return getSqlMapClientTemplate().update("myfavorite.deleteMyCollect", map);
	}

	@Override
	public Integer countByCodeAndContentId(String favoriteTypeCode,
			Integer contentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("favoriteTypeCode", favoriteTypeCode);
		map.put("contentId", contentId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countByCodeAndContentId"), map);
	}
	
	@Override
	public Integer countByCompanyId(Integer companyId){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countByCompanyId"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyfavoriteDO> queryMyCollectForMyhuzhu(	PageDto<MyfavoriteDO> page, String keywrods, Integer companyId,String favoriteTypeCode, Integer theday) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("keywrods", keywrods);
		map.put("companyId", companyId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		map.put("theday", theday);
		map.put("page", page);
		map.put("isHuzhu", 1);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "pageMyCollect"), map);
	}

	@Override
	public Integer queryMyCollectForMyhuzhuCount(String keywrods,Integer companyId, String favoriteTypeCode, Integer theday) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("keywrods", keywrods);
		map.put("companyId", companyId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		map.put("theday", theday);
		map.put("isHuzhu", 1);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "pageMyCollectCount"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyfavoriteDO> queryNoticeByCondition(PageDto<PriceOffer> page,Integer companyId, String favoriteTypeCode,String keywords) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("page", page);
		map.put("companyId", companyId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		map.put("keywords", keywords);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryNoticeByCondition"), map);
	}

	@Override
	public Integer countNoticeByCondition(Integer companyId, String favoriteTypeCode,String keywords) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		map.put("keywords", keywords);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countNoticeByCondition"), map);
	}

	@Override
	public Integer deleteCollection(Integer companyId, String favoriteTypeCode, Integer contentId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		map.put("contentId", contentId);
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteCollection"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyfavoriteDO> queryYuanliaoCollectList(Integer companyId, String favoriteTypeCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("favoriteTypeCode", favoriteTypeCode);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryYuanliaoCollectList"), map);
	}

}
