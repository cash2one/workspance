package com.zz91.tags.dao;


import java.util.List;

import com.zz91.tags.domain.Tags;
import com.zz91.tags.dto.Pager;

public interface TagsDao {
	
	public Integer insertTags(Tags tags);
	
	public Integer deleteTags(String tags);
	
	public List<Tags> queryTags(String tags,Pager<Tags> page);
	
	public Integer queryTagsCount(String tags);
	
	public Integer countTagsByName(String tags);
	
	public Integer updateCitedCount(String tags);
	
	public Integer updateClickCount(String tag);
	
	public Integer updateSearchCount(String tag);
	
	public List<Tags> queryTagsByKeywords(String keywords, String sort, Integer size);
	
	public List<Tags> queryTagsByTag(String tag, String sort, Integer size);
	
	/**
	 * 搜索近30天内的热门标签，搜索量的数目作为热门的标准
	 */
	public List<Tags> queryTagsForHot(String from,Integer size);
	
	/**
	 * 只更新tags,tags_encode,keywords
	 */
	public Integer updateTags(Tags tags);
}
