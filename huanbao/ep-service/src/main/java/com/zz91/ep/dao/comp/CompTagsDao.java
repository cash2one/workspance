package com.zz91.ep.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompTags;

/**
 * 
 * @author 陈庆林 2012-8-7 下午3:28:15
 */
public interface CompTagsDao {
	
	/**
	 * 查询个哦你公司标签（只查询一级 标签和三级标签）
	 * @param code
	 * @param flag
	 * @return
	 */
	public List<CompTags> queryCompTags(Integer parentId,Integer flag);
	
	/**
	 * 根据id查询关键字
	 * @param id
	 * @return
	 */
	public String queryCompKewordsById(Integer id);
}
