package com.zz91.ep.admin.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.SubnetCategory;

public interface SubnetCategoryDao {
	
	/**
	 * 根据父类别查询子类别
	 */
	public List<SubnetCategory> queryCategoryByParentId(Integer parentId);
	
	/**
	 * 查询子类数
	 * @param id
	 * @return
	 */
	public Integer queryChildCountByParentId(Integer id);

	/**
	 * 添加类别
	 * @param category
	 * @return
	 */
	public Integer insertSubnetCategory(SubnetCategory category);

	/**
	 * 修改类别
	 * @param category
	 * @return
	 */
	public Integer updateSubnetCategory(SubnetCategory category);

	/**
	 * 删除父类或删除子类(方法共用)
	 * @param id
	 * @param parentId
	 * @return
	 */
	public Integer deleteCategoryByIdOrParentId(Integer id, Integer parentId);

	/**
	 * 查询所有类别
	 * @return
	 */
	public List<SubnetCategory> queryAllCategory();
}
