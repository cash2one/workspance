package com.zz91.tags.service.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.tags.dao.TagsCategoryCollectionDao;
import com.zz91.tags.dao.TagsCategoryDao;
import com.zz91.tags.domain.Tags;
import com.zz91.tags.domain.TagsCategoryCollection;
import com.zz91.tags.dto.Pager;
import com.zz91.tags.service.TagsCategoryCollectionService;
import com.zz91.tags.utils.TagsConst;
@Component("tagsCategpryConnectionService")
public class TagsCategoryCollectionServiceImpl implements TagsCategoryCollectionService {

	@Resource
	private TagsCategoryCollectionDao tagsCategoryCollectionDao;
	@Resource
	private TagsCategoryDao tagsCategoryDao;
	
	final static int MAX_SIZE=10;
	
	@Override
	public Integer deleteTagsCategoryCollection(Integer id) {
		return tagsCategoryCollectionDao.deleteCollectionById(id);
	}

	@Override
	public Integer createTagsCategoryCollection(TagsCategoryCollection tags) {
		tags.setCategoryIndexKey(tagsCategoryDao.queryIndexKeyByCode(tags.getCategoryCode()));
		try {
			tags.setTagsEncode(URLEncoder.encode(tags.getTags(), TagsConst.URL_ENCODE));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tagsCategoryCollectionDao.insertCollection(tags);
	}

	@Override
	public Pager<TagsCategoryCollection> pageCollection(String categoryCode,
			Boolean isDirect, Pager<TagsCategoryCollection> page) {
		if(isDirect==null){
			isDirect=false;
		}
		page.setRecords(tagsCategoryCollectionDao.queryCollection(categoryCode, isDirect, page));
		page.setTotals(tagsCategoryCollectionDao.queryCollectionCount(categoryCode, isDirect));
		return page;
	}
	
	@Override
	public Map<String, String> queryByCode(String code, Integer depth,
			Integer size) {
		if(size==null) {
			size = MAX_SIZE;
		}
		if(depth==null){
			depth=0;
		}
		List<Tags> list=tagsCategoryCollectionDao.queryByCode(code, depth, size);
		Map<String, String> map=new HashMap<String, String>();
		for(Tags t:list){
			map.put(t.getTags(), t.getTagsEncode());
		}
		return map;
	}

	@Override
	public Map<String, String> queryByIndexKey(String indexKey, Integer size) {
		if(size==null) {
			size = MAX_SIZE;
		}
		List<Tags> list=tagsCategoryCollectionDao.queryByIndexKey(indexKey, size);
		Map<String, String> map=new HashMap<String, String>();
		for(Tags t:list){
			map.put(t.getTags(), t.getTagsEncode());
		}
		return map;
	}
	
}
