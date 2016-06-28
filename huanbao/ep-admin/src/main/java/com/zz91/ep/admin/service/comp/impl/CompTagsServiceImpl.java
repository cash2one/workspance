package com.zz91.ep.admin.service.comp.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.CompTagsDao;
import com.zz91.ep.admin.service.comp.CompTagsService;
import com.zz91.ep.domain.comp.CompTags;
import com.zz91.ep.dto.ExtResult;

@Component("compTagsService")
public class CompTagsServiceImpl implements CompTagsService {

	@Resource
	private CompTagsDao compTagsDao;

	@Override
	public List<CompTags> queryComCategoryByParentId(Integer parentId) {
		return compTagsDao.queryComCategoryByParentId(parentId);
	}

	@Override
	public ExtResult updateComTags(CompTags copmtags){
		ExtResult result = new ExtResult();
		if (copmtags.getParentId() == null) {
			copmtags.setParentId(0);
		}
		if (copmtags.getShowIndex() == null) {
			copmtags.setShowIndex((short) 0);
		}
		Integer i = compTagsDao.updateComTags(copmtags);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return result;
	}

	@Override
	public ExtResult addComTags(CompTags comptags) {
		ExtResult result = new ExtResult();
		if (comptags.getParentId() == null) {
			comptags.setParentId(0);
		}
		if (comptags.getShowIndex() == null) {
			comptags.setShowIndex((short) 0);
		}
		Integer i = compTagsDao.addComTags(comptags);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return result;
	}

	@Override
	public ExtResult deleteChildCategory(Integer comtagsId, Integer parentId) {
			ExtResult result = new ExtResult();
			Integer i = compTagsDao.deleteChildCategory(comtagsId, parentId);
			if(i != null && i.intValue() > 0){
				result.setSuccess(true);
			}
		return result;
	}

}
