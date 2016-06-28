package com.zz91.tags.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.tags.dao.TagsCategoryCollectionDao;
import com.zz91.tags.dao.TagsDao;
import com.zz91.tags.domain.Tags;
import com.zz91.tags.dto.Pager;
import com.zz91.tags.service.TagsService;
import com.zz91.tags.utils.TagsConst;
import com.zz91.util.Assert;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.tags.TagsUtils;

@Component("tagsService")
public class TagsServiceImpl implements TagsService {
	
	@Resource
	private TagsDao tagsDao;
	@Resource
	private TagsCategoryCollectionDao tagsCategoryCollectionDao;
	
	@Override
	public Integer deleteTags(String tags) {
		Assert.notNull(tags,"the tags can not be null");
		tagsCategoryCollectionDao.deleteCollection(tags);
		return tagsDao.deleteTags(tags);
	}

	@Override
	public Integer insertTags(Tags tags) {
		Integer i=tagsDao.countTagsByName(tags.getTags());
		Integer j=0;
		tags.setKeywords(TagsUtils.getInstance().arrangeTags(tags.getKeywords()));
		
		if(i!=null && i.intValue()>0){
			j=tagsDao.updateCitedCount(tags.getTags());
		}else{
			try {
				tags.setTagsEncode(URLEncoder.encode(tags.getTags(), TagsConst.URL_ENCODE));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			tags.setClickCount(0);
			tags.setSearchCount(0);
			tags.setCitedCount(1);
			j=tagsDao.insertTags(tags);
		}
		return j;
	}

	@Override
	public Pager<Tags> pageTags(String tags, Pager<Tags> page) {
		Assert.notNull(page, "the page can not be null");
		page.setRecords(tagsDao.queryTags(tags, page));
		page.setTotals(tagsDao.queryTagsCount(tags));
		return page;
	}

	@Override
	public Integer updateClickCount(String tag) {
		if(StringUtils.isEmpty(tag)){
			return null;
		}
		return tagsDao.updateClickCount(tag);
	}

	@Override
	public Integer updateSearchCount(String tag) {
		if(StringUtils.isEmpty(tag)){
			return null;
		}
		return tagsDao.updateSearchCount(tag);
	}

	@Override
	public Map<String, String> queryTagsByKw(String k, String sort, Integer size) {
		if(size==null || size.intValue()==0){
			size=10;
		}
		if(!"search_count".equals(sort)
				&&!"click_count".equals(sort)
				&&!"cited_count".equals(sort)){
			sort="search_count";
		}
		
		List<Tags> list=tagsDao.queryTagsByKeywords(k, sort, size);
		
		Map<String, String> map=new HashMap<String, String>();
		for(Tags t:list){
			map.put(t.getTags(), t.getTagsEncode());
		}
		return map;
	}

	@Override
	public Map<String, String> queryTagsByTag(String tag, String sort,
			Integer size) {
		if(size==null || size.intValue()==0){
			size=10;
		}
		if(StringUtils.isEmpty(sort)){
			if(!"search_count".equals(sort)&&!"click_count".equals(sort)&&!"cited_count".equals(sort)){
				sort="search_count";
			}
		}
		List<Tags> list=tagsDao.queryTagsByTag(tag, sort, size);
		
		Map<String, String> map=new HashMap<String, String>();
		for(Tags t:list){
			map.put(t.getTags(), t.getTagsEncode());
		}
		return map;
	}
	
	@Override
	public Map<String, String> queryTagsForHot(Integer size,Integer day) {
		if(size==null || size.intValue()==0){
			size=10;
		}
		if(day==null || day.intValue()==0){
			day=7;
		}
		String from = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -day), "yyyy-MM-dd");
		List<Tags> list = tagsDao.queryTagsForHot(from, size);
		Map<String, String> map=new HashMap<String, String>();
		for(Tags t:list){
			map.put(t.getTags(), t.getTagsEncode());
		}
		return map;
	}

	@Override
	public Integer updateTags(Tags tags) {
		try {
			tags.setTagsEncode(URLEncoder.encode(tags.getTags(), TagsConst.URL_ENCODE));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		tags.setKeywords(TagsUtils.getInstance().arrangeTags(tags.getKeywords()));
		return tagsDao.updateTags(tags);
	}


}
