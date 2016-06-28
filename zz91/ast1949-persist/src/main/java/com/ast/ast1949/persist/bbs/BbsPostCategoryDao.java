/**
 * @author shiqp 日期:2014-11-10
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostCategory;

public interface BbsPostCategoryDao {

	public Integer insertCategory(BbsPostCategory bbsPostCategory);

	public Integer updateCategoryById(BbsPostCategory bbsPostCategory);

	public List<BbsPostCategory> queryCategoryByParentId(Integer parentId);

	public Integer queryMaxCategoryIdByParentId(Integer parentId);

	public BbsPostCategory querySimpleCategoryById(Integer id);
	/**
	 * 类别库所有未删除的记录
	 * @return
	 */
	public List<BbsPostCategory> queryAllCategory();

}
