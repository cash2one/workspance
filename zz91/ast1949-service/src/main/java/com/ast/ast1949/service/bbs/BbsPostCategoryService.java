/**
 * @author shiqp 日期:2014-11-10
 */
package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.dto.ExtTreeDto;

public interface BbsPostCategoryService {

	public List<ExtTreeDto> queryCategoryByParentId(Integer parentId);

	public BbsPostCategory querySimpleCategoryById(Integer id);

	public Integer insertCategory(BbsPostCategory bbsPostCategory);
	
	public Integer updateCategoryById(BbsPostCategory bbsPostCategory);
	/**
	 * 类别库所有未删除的记录
	 * @return
	 */
	public List<BbsPostCategory> queryAllCategory();
	/**
	 * 某类别下的所有子类别
	 * @param parentId
	 * @return
	 */
	public List<BbsPostCategory> queryCategorysByParentId(Integer parentId);

}
