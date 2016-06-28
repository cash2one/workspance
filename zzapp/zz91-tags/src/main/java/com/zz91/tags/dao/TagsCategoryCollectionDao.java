package com.zz91.tags.dao;

import java.util.List;

import com.zz91.tags.domain.Tags;
import com.zz91.tags.domain.TagsCategoryCollection;
import com.zz91.tags.dto.Pager;

public interface TagsCategoryCollectionDao {
	
	public Integer insertCollection(TagsCategoryCollection tags);
	
	public Integer deleteCollection(String tags);
	
	public Integer updateIndexKey(String code, String indexKey);
	
	public List<TagsCategoryCollection> queryCollection(String categoryCode,Boolean isDirect,Pager<TagsCategoryCollection> page);
	
	public Integer queryCollectionCount(String categoryCode,Boolean isDirect);
	
	public Integer deleteCollectionByCategory(String code);
	
	public Integer deleteCollectionById(Integer id);
	
	public List<Tags> queryByCode(String code, Integer depth, Integer size);
	
	public List<Tags> queryByIndexKey(String indexKey, Integer size);
	
}
