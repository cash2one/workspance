package com.zz91.tags.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.tags.dao.TagsCategoryCollectionDao;
import com.zz91.tags.dao.TagsCategoryDao;
import com.zz91.tags.domain.TagsCategory;
import com.zz91.tags.dto.ExtTreeDto;
import com.zz91.tags.service.TagsCategoryService;
import com.zz91.util.Assert;
@Component("tagsCategoryService")
public class TagsCategoryServiceImpl implements TagsCategoryService{

	@Resource
	private TagsCategoryDao tagsCategoryDao;
	@Resource
	private TagsCategoryCollectionDao tagsCategoryCollectionDao;
	@Override
	public Integer updateTagsCategory(TagsCategory category) {
		Assert.notNull(category.getCode(), "the code can not be null");
		
		tagsCategoryCollectionDao.updateIndexKey(category.getCode(), category.getIndexKey());
		return tagsCategoryDao.updateTagsCategory(category);
	}

	@Override
	public Integer deleteTagsCategory(String code) {
		Assert.notNull(code, "the code can not be null");
		
		tagsCategoryCollectionDao.deleteCollectionByCategory(code);
		return tagsCategoryDao.deleteTagsCategory(code);
	}

	@Override
	public Integer createTagsCategory(TagsCategory tags, String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		String code=tagsCategoryDao.queryMaxCodeOfChild(parentCode);
		if(code!=null && code.length()>0){
			code = code.substring(parentCode.length());
			Integer codeInt=Integer.valueOf(code);
			codeInt++;
			tags.setCode(parentCode+String.valueOf(codeInt));
		}else{
			tags.setCode(parentCode+"1000");
		}
		return tagsCategoryDao.insertTagsCategory(tags);
	}

	@Override
	public List<ExtTreeDto> queryTagsCategroyNode(String parentCode) {
		
		if(parentCode==null){
			parentCode="";
		}
		
		List<TagsCategory> list=tagsCategoryDao.queryChild(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		for(TagsCategory tagsCategory:list){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(tagsCategory.getId()));
			node.setText(tagsCategory.getName());
			node.setData(tagsCategory.getCode());
			Integer i = tagsCategoryDao.countChild(tagsCategory.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		return nodeList;
	
	}

	@Override
	public TagsCategory queryCategoryByCode(String code) {
		if(code==null){
			return null;
		}
		return tagsCategoryDao.queryCategoryByCode(code);
	}

}
