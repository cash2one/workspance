/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-8-12
 */
package com.zz91.tags.service;


import java.util.Map;

import com.zz91.tags.domain.Tags;
import com.zz91.tags.dto.Pager;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-8-12
 */
public interface TagsService {
	
	public Integer insertTags(Tags tags);
	
	public Integer deleteTags(String tags);
	
	public Pager<Tags> pageTags(String tags,Pager<Tags> page);
	
	public Integer updateClickCount(String tag);
	
	public Integer updateSearchCount(String tag);
	
	public Map<String, String> queryTagsByKw(String k, String sort, Integer size);
	
	public Map<String, String> queryTagsByTag(String tag, String sort, Integer size);
	
	/**
	 * 近30天热门标签
	 */
	public Map<String,String> queryTagsForHot(Integer size,Integer day);
	
	public Integer updateTags(Tags tags);
}
