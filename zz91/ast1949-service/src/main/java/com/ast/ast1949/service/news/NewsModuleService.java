/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 9, 2010 by Rolyer.
 */
package com.ast.ast1949.service.news;

import java.util.List;

import com.ast.ast1949.domain.news.NewsModuleDO;
import com.ast.ast1949.dto.ExtCheckBoxTreeDto;
import com.ast.ast1949.dto.ExtTreeDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface NewsModuleService {
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
	 * 通过父节点获取模块的所有子节点
	 * @param id :父节点ID,不能为null
	 * @return 包含子节点的Ext树结构
	 */
	public List<ExtTreeDto> queryExtTreeChildNodeByParentId(Integer id);
	/**
	 * 通过父节点获取模块的所有子节点,用于初始带复选框(CheckBox)的树。
	 * @param parentId :父节点ID,不能为null
	 * @param ids :已选中的id,参数举例：1,2,3,4
	 * @return 包含子节点的Ext树结构
	 */
	public List<ExtCheckBoxTreeDto> queryExtCheckBoxTreeChildNodeByParentId(Integer parentId, String ids);
	/**
	 * 根据新闻ID查询新闻模块信息列表
	 * @param id 新闻编号
	 * @return
	 */
//	public List<NewsModuleDO> queryNewsModuleListByNewsId(Integer id);
	/**
	 * 根据新闻ID统计新闻模块数量
	 * @param id 父编号
	 * @return
	 */
//	public Integer countNewsModuleByParentId(Integer id);
}
