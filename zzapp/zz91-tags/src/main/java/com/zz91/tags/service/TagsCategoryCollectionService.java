package com.zz91.tags.service;


import java.util.Map;

import com.zz91.tags.domain.TagsCategoryCollection;
import com.zz91.tags.dto.Pager;

public interface TagsCategoryCollectionService {
	
	public Integer createTagsCategoryCollection(TagsCategoryCollection tags);
	
	public Integer deleteTagsCategoryCollection(Integer id);
	
	public Pager<TagsCategoryCollection> pageCollection(String categoryCode, Boolean isDirect, Pager<TagsCategoryCollection> page);

	public Map<String, String> queryByIndexKey(String indexKey, Integer size);
	
	public Map<String, String> queryByCode(String code, Integer depth, Integer size);

}
