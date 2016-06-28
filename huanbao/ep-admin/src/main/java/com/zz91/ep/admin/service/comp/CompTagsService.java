package com.zz91.ep.admin.service.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompTags;
import com.zz91.ep.dto.ExtResult;

public interface CompTagsService {
	/**
	 * 查询类别
	 * @param parentId
	 * @return
	 */
	public List<CompTags> queryComCategoryByParentId(Integer parentId);
	
	/**
	 * 修改类别
	 * @param copmtags
	 * @return
	 */
	public ExtResult updateComTags(CompTags copmtags);
	
	/**
	 * 添加类别
	 * @param copmtags
	 * @return
	 */
	public ExtResult addComTags(CompTags copmtags);
	
	/**
	 * 删除类别
	 * @param comtagsId
	 * @return
	 */
	public ExtResult deleteChildCategory(Integer comtagsId,Integer parentId);
}
