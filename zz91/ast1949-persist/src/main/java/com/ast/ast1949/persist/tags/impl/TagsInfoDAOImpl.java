/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17
 */
package com.ast.ast1949.persist.tags.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.tags.TagsInfoDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.tags.TagsInfoDAO;

/**
 * @author yuyonghui
 *
 */
@Component("tagsInfoDAO")
public class TagsInfoDAOImpl extends SqlMapClientDaoSupport implements
		TagsInfoDAO {

//	public Integer deleteTagsInfoById(Integer id) {
//		Assert.notNull(id, "id is not null");
//		return getSqlMapClientTemplate().delete("tagsInfo.deleteTagsInfoById",
//				id);
//	}

	public Integer getRecordCountByCondition(TagsInfoDTO tags) {
		Assert.notNull(tags, "tags is not null");
		return Integer.valueOf(getSqlMapClientTemplate().queryForObject(
				"tagsInfo.getRecordCountByCondition", tags).toString());
	}

	public Integer insertTagsInfo(TagsInfoDO tagsInfoDO) {
		Assert.notNull(tagsInfoDO, "tagsInfoDO is not null");
		if(tagsInfoDO.getTypeId()==null)
			tagsInfoDO.setTypeId("0");
		return Integer.valueOf(getSqlMapClientTemplate().insert(
				"tagsInfo.insertTagsInfo", tagsInfoDO).toString());
	}
	/*
	@SuppressWarnings( { "unchecked" })
	public List<TagsInfoDO> queryTagsInfoByArticleIdAndCategoryCode(
			TagsRelateArticleDTO tagsRelateArticleDTO) {
		return getSqlMapClientTemplate().queryForList(
				"tagsInfo.queryTagsInfoByArticleIdAndCategoryCode",
				tagsRelateArticleDTO);
	}
	*/
	
	@SuppressWarnings("unchecked")
	public List<TagsInfoDO> queryTagsInfoByCondition(TagsInfoDTO tags) {
		Assert.notNull(tags, "tags is not null");
		return getSqlMapClientTemplate().queryForList(
				"tagsInfo.queryTagsInfoByCondition", tags);
	}

	public TagsInfoDO queryTagsInfoById(Integer id) {

		return (TagsInfoDO) getSqlMapClientTemplate().queryForObject(
				"tagsInfo.queryTagsInfoById", id);
	}

	public Integer updateTagsInfo(TagsInfoDO tagsInfoDO) {
		Assert.notNull(tagsInfoDO, "tagsInfoDO is not null");
		return getSqlMapClientTemplate().update("tagsInfo.updateTagsInfo",
				tagsInfoDO);
	}

	public TagsInfoDO queryTagsInfoByName(String name) {
		Assert.notNull(name, "name is not null");
		return (TagsInfoDO) getSqlMapClientTemplate().queryForObject(
				"tagsInfo.queryTagsInfoByName", name);
	}

	final private int DEFAULT_BATCH_SIZE = 20;

	public Integer batchDeleteTagsInfoByIds(int[] ids) {
		Assert.notNull(ids, "ids is not null");
		int impacted = 0;
		int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > ids.length ? ids.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().delete(
							"tagsInfo.deleteTagsInfoById", ids[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete user failed.", e);
		}
		return impacted;
	}

	@Override
	public List<TagsInfoDO> queryTopLastTagList(Integer topNum) {
		return queryTopLastTagsByType(null,topNum);
	}

	@Override
	public List<TagsInfoDO> queryTopLastTagsByType(String typeId, Integer topNum) {
		if(topNum==null||topNum<=0)
			topNum=50;
		Map paramMap=new HashMap();
		paramMap.put("typeId", typeId);
		paramMap.put("topNum", topNum);
		return getSqlMapClientTemplate().queryForList(
				"tagsInfo.queryTopLastTagList", paramMap);
	}

//	@Override
//	public List<TagsInfoDO> queryTagsInfoByType(TagsInfoDto tagsInfoDto) {
//		return getSqlMapClientTemplate().queryForList(
//				"tagsInfo.queryTagsInfoByType", tagsInfoDto);
//	}

	@Override
	public List<TagsInfoDO> queryTagsInfoByType(String code, int topNum) {
		Map paramMap=new HashMap();
		paramMap.put("typeId", code);
		paramMap.put("topNum", topNum);
		return getSqlMapClientTemplate().queryForList(
				"tagsInfo.queryTagsInfoByType", paramMap);
	}
	
}
