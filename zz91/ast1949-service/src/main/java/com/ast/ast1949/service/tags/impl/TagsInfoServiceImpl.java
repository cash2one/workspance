/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-18
 */
package com.ast.ast1949.service.tags.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.tags.TagsInfoDTO;
import com.ast.ast1949.persist.tags.TagsArticleRelationDao;
import com.ast.ast1949.persist.tags.TagsInfoDAO;
import com.ast.ast1949.service.tags.TagsInfoService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.param.ParamUtils;

/**
 * @author yuyonghui
 *
 */
@Service ("tagsInfoService")
//@Component("TagsInfoService")
public class TagsInfoServiceImpl implements TagsInfoService {
	@Resource
	private TagsInfoDAO tagsInfoDAO;
	@Resource
	private TagsArticleRelationDao tagsArticleRelationDao; 
	
//	@Autowired
//	private TagsRelateArticleDAO tagsRelateArticleDAO;

	public Integer batchDeleteTagsInfoByIds(int[] ids) {
		tagsArticleRelationDao.batchDeleteTagsArticleRelationByTagIds(ids);
		return tagsInfoDAO.batchDeleteTagsInfoByIds(ids);
	}

//	public Integer deleteTagsInfoById(Integer id) {
//		Assert.notNull(id, "id is not null");
//		tagsArticleRelationDao.deleteTagsArticleRelationByTagId(id);
//		return tagsInfoDAO.deleteTagsInfoById(id);
//	}

	public Integer getRecordCountByCondition(TagsInfoDTO tags) {
		Assert.notNull(tags, "tags is not null");
		return tagsInfoDAO.getRecordCountByCondition(tags);
	}

//	public Integer insertTagsInfo(TagsInfoDO tagsInfoDO) {
//		Assert.notNull(tagsInfoDO, "tagsInfoDO is not null");
//		return tagsInfoDAO.insertTagsInfo(tagsInfoDO);
//	}

//	public List<TagsInfoDO> queryTagsInfoByArticleIdAndCategoryCode(TagsRelateArticleDTO tagsRelateArticleDTO) {
//		return tagsInfoDAO.queryTagsInfoByArticleIdAndCategoryCode(tagsRelateArticleDTO);
//	}

	public List<TagsInfoDO> queryTagsInfoByCondition(TagsInfoDTO tags) {
		Assert.notNull(tags, "tags is not null");
		return tagsInfoDAO.queryTagsInfoByCondition(tags);
	}


	@SuppressWarnings("unchecked")
	public List<TagsInfoDO> queryTagsInfoByConditionFromCache(TagsInfoDTO tags) {
		Assert.notNull(tags, "tags is not null");
		String key=tags.getTagsInfoDO().getName()+"-"+tags.getTagsInfoDO().getTypeId();
		List<TagsInfoDO> list= (List<TagsInfoDO>) MemcachedUtils.getInstance().getClient().get(key);
		if (list== null) {
			list = queryTagsInfoByCondition(tags);
			String cacheExpire = ParamUtils.getInstance().getValue("cache_exp_time", "tags_list");
			if (cacheExpire != null && list != null) {
				MemcachedUtils.getInstance().getClient().set(key, Integer.valueOf(cacheExpire), list);
			}
		}
		return list;
	}
	public TagsInfoDO queryTagsInfoById(Integer id) {
		Assert.notNull(id, "id is not null");
		return tagsInfoDAO.queryTagsInfoById(id);
	}

	public TagsInfoDO queryTagsInfoByName(String name) {
		Assert.notNull(name, "name is not null");
		return tagsInfoDAO.queryTagsInfoByName(name);
	}

	public Integer updateTagsInfo(TagsInfoDO tagsInfoDO) {
		Assert.notNull(tagsInfoDO, "tagsInfoDO is not null");
		return tagsInfoDAO.updateTagsInfo(tagsInfoDO);
	}

//	public void insertOrUpdateTags(TagsDTO tagsDTO) {
//		List<TagsInfoDO> newTagsInfoList = tagsDTO.getTagsInfoList();
//		TagsRelateArticleDO newRelate = tagsDTO.getTagsRelateArticleDO();
//		// 删除所有旧关联
//		tagsRelateArticleDAO.deleteTagsRelateArticleByTagsRelateArticle(newRelate);
//		for (TagsInfoDO newTagsInfo : newTagsInfoList) {
//			TagsInfoDO tagsInfo = queryTagsInfoByName(newTagsInfo.getName());
//			if (tagsInfo == null) {
//				Integer tagsId = insertTagsInfo(newTagsInfo);
//				newRelate.setTagsInfoId(tagsId);
//				tagsRelateArticleDAO.insertTagsRelateArticle(newRelate);
//			}else{
//				newRelate.setTagsInfoId(tagsInfo.getId());
//				TagsRelateArticleDTO tagsRelateArticleDTO = tagsRelateArticleDAO.selectTagsRelateByNameAndArticleIdAndCategoryCode
//				(newTagsInfo.getName(), newRelate.getArticleId(), newRelate.getTagsArticleCategoryCode());
//				if(tagsRelateArticleDTO == null){
//					tagsRelateArticleDAO.insertTagsRelateArticle(newRelate);
//				}
//			}
//		}
//	}

	@Override
	public List<TagsInfoDO> queryTagsInfoByType(String tagType,Integer topNum) {
		Assert.notNull(tagType, "tags type is not null");
		Assert.notNull(topNum, "tags type is not null");
//		List<TagsInfoDO> tagsList=
//		List<TagsInfoDTO> tagsInfoDtoList=new ArrayList<TagsInfoDTO>();
//		for(TagsInfoDO tag: tagsList){
//			TagsInfoDTO tagsInfo=new TagsInfoDTO();
//			tagsInfo.setTagsInfoDO(tag);
//			tagsInfoDtoList.add(tagsInfo);
//		}
		return tagsInfoDAO.queryTagsInfoByType(tagType,topNum);
	}

	@Override
	public List<TagsInfoDO> queryLastTags(Integer topNum) {
		return tagsInfoDAO.queryTopLastTagList(topNum);
	}
	
}
