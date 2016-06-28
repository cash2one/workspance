package com.zz91.ep.admin.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.SubnetCategory;

public interface SubnetCategoryService {
	
	/**
	 * 根据父类查询子类
	 * @param parentId
	 * @return
	 */
	public List<SubnetCategory> queryCategoryByParentId(Integer parentId);

	/**
	 * 添加类别
	 * @param category
	 * @return
	 */
	public Integer createSubnetCategory(SubnetCategory category);

	/**
	 * 修改类别
	 * @param category
	 * @return
	 */
	public Integer updateSubnetCategory(SubnetCategory category);

	/**
	 * 删除类别
	 * @param id
	 * @return
	 */
	public Integer deleteCategory(Integer id);
	
	/**
	 * 删除子类
	 * @param id
	 * @param parentId
	 * @return
	 */
	public Integer deleteChildCategory(Integer id, Integer parentId);

	/**
	 * 查询所有类别
	 * @return
	 */
	public List<SubnetCategory> queryAllCategory();
}
