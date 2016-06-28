/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-28
 */
package com.zz91.ep.admin.dao.news;

import java.util.List;

import com.zz91.ep.domain.news.NewsCategory;

/**
 * @author totly
 *
 * created on 2011-9-28
 */
public interface NewsCategoryDao {

	/**
	* 查询所有资讯类别（用于页面导航）
	*/
	public List<NewsCategory> queryNewsCategoryAll();
	/**
	 * 添加资讯类别
	 * @param category
	 * @return
	 */
	public Integer insertCategory(NewsCategory category);
	/**
	 * 根据父类编号查找子类
	 * @param code 父类别编后
	 * @return
	 */
	public List<NewsCategory> queryCategoryByParentCode(String code);
	/**
	 * 编辑类别
	 * @param code 类别编号
	 * @param name 类别名称
	 * @param sort 排序
	 * @param tags 标签
	 * @return
	 */
//	public Integer updateCategory(String code, String name, Integer sort,String tags);
	/**
	 * 删除类别(同时删除子类)
	 * @param code 类别编号
	 * @return
	 */
	public Integer deleteCategoryByCode(String code);
	/**
	 * 查询最大的类别编号(用于添加新类别)
	 * @param code
	 * @return
	 */
	public String queryMaxCodeByPreCode(String code);
	/**
	 * 查询所含子节点个数
	 * @param code
	 * @return
	 */
	public Integer countNewsCategoryChild(String code);
	
	public Integer updateCategory(NewsCategory newsCategory);
	
	public NewsCategory queryOneNewsCategory(String code);
}
