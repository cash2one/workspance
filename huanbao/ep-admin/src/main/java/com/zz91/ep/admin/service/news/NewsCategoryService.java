package com.zz91.ep.admin.service.news;

import java.util.List;

import com.zz91.ep.domain.news.NewsCategory;
import com.zz91.ep.dto.ExtTreeDto;

public interface NewsCategoryService {
	/**
	 * 查询所以资讯类别（用于页面导航）
	 * @return
	 */
	public List<NewsCategory> queryNewsCategoryAll();
	/**
	 * 添加资讯类别
	 * @param category
	 * @param preCode 类别编号
	 * @return 
	 */
	public Integer createCategory(NewsCategory category,String preCode);
	/**
	 * 根据父类别查询子类别
	 * @param code 父类别
	 * @return List
	 */
	public List<ExtTreeDto> queryChild(String code);
	/**
	 * 编辑类别（根据类别编号）
	 * @param code 类别编号
	 * @param name 类别名
	 * @param sort 排序
	 * @param tags 标签
	 * @return
	 */
//	public Integer updateCategory(String code,String name,Integer sort,String tags);
	/**
	 * 删除类别
	 * @param code 类别编号（删除时子类一起删除）
	 * @return
	 */
	public Integer deleteCategoryByCode(String code);
	/**
	 * 更新资讯类别
	 * @param newsCategory
	 * @return
	 */
	public Integer updateCategory(NewsCategory newsCategory);
	/**
	 * 通过code查询资讯类别
	 * @param code
	 * @return
	 */
	public NewsCategory queryNewsCategoryByCode(String code);
	
}