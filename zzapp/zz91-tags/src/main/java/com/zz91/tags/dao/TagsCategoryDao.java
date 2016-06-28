package com.zz91.tags.dao;

import java.util.List;

import com.zz91.tags.domain.TagsCategory;


public interface TagsCategoryDao {
	
	public Integer updateTagsCategory(TagsCategory category);
	
	public Integer deleteTagsCategory(String code);
	
	public List<TagsCategory> queryChild(String parentCode);
	
	public Integer insertTagsCategory(TagsCategory tags);
	
	public Integer countChild(String parentCode);
	
	public String queryMaxCodeOfChild(String parentCode);
	
	public String queryIndexKeyByCode(String code);
	
	public TagsCategory queryCategoryByCode(String code);
	
}
