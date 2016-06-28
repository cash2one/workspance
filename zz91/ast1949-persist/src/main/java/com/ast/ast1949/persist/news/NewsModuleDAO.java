/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 8, 2010 by Rolyer.
 */
package com.ast.ast1949.persist.news;

import java.util.List;

import com.ast.ast1949.domain.news.NewsModuleDO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface NewsModuleDAO {
	/**
	 * 添加新闻模块
	 * @param chartCategory
	 * @return 成功返回新增记录的ID
	 */
	public Integer insertNewsModule(NewsModuleDO newsModule);
	/**
	 * 删除新闻模块
	 * @param id
	 * @return 成功返回删除的行数
	 */
	public Integer deleteNewsModuleById(Integer id);
	/**
	 * 更新新闻模块
	 * @param newsModule
	 * @return 成功返回更新的行数
	 */
	public Integer updateNewsModuleById(NewsModuleDO newsModule);
	/**
	 * 查询指定新闻模块
	 * @param id 新闻模块编号
	 * @return 
	 */
	public NewsModuleDO queryNewsModuleById(Integer id);
	/**
	 * 根据父ID查询新闻模块信息
	 * @param id
	 * @return 
	 */
	public List<NewsModuleDO> queryNewsModuleByParentId(Integer id);
	/**
	 * 根据父ID查询新闻模块信息列表
	 * @param newsModuleDTO
	 * @return 返回符合条件的结果集
	 */
//	public List<NewsModuleDTO> queryNewsModuleListByParentId(NewsModuleDTO newsModuleDTO);
	/**
	 * 根据父ID统计新闻模块信息列表
	 * @param NewsModuleDTO
	 * @return 返回符合条件的记录总数
	 */
//	public Integer countNewsModuleListByParentId(NewsModuleDTO newsModuleDTO);
	/**
	 * 根据新闻ID查询新闻模块信息列表
	 * @param id 新闻编号
	 * @return
	 */
	public List<NewsModuleDO> queryNewsModuleListByNewsId(Integer id);
	/**
	 * 根据新闻ID统计新闻模块数量
	 * @param id 父编号
	 * @return
	 */
	public Integer countNewsModuleByParentId(Integer id);
}
