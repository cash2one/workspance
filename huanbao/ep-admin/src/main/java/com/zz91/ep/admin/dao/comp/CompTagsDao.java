package com.zz91.ep.admin.dao.comp;

import java.util.List;
import com.zz91.ep.domain.comp.CompTags;

public interface CompTagsDao {
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
	public Integer updateComTags (CompTags copmtags);
	
	/**
	 * 添加类别
	 * @param copmtags
	 * @return
	 */
	public Integer addComTags(CompTags copmtags);
	
	/**
	 * 删除类别
	 * @param comtagsId
	 * @return
	 */
	public Integer deleteChildCategory(Integer comtagsId,Integer parentId);

}
