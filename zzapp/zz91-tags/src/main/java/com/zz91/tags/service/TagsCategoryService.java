package com.zz91.tags.service;


import java.util.List;

import com.zz91.tags.domain.TagsCategory;
import com.zz91.tags.dto.ExtTreeDto;

public interface TagsCategoryService {
	
	
	public Integer updateTagsCategory(TagsCategory category);
	
	public Integer createTagsCategory(TagsCategory tags, String parentCode);
	
	public Integer deleteTagsCategory(String code);
	
	public List<ExtTreeDto> queryTagsCategroyNode(String parentCode);
	
	public TagsCategory queryCategoryByCode(String code);
}
