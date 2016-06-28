/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-27 by Rolyer.
 */
package com.ast.ast1949.service.information;

import java.util.LinkedList;
import java.util.List;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.information.ChartCategoryDTO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface ChartCategoryService {
	/**
	 * 添加类别
	 * @param chartCategory
	 * @return 成功返回新增记录的ID
	 */
	public Integer insertChartCategory(ChartCategoryDO chartCategory);
	/**
	 * 删除类别
	 * @param id
	 * @return 成功返回删除的行数
	 */
	public Integer deleteChartCategoryById(Integer id);
	/**
	 * 更新类别
	 * @param chartCategory
	 * @return 成功返回更新的行数
	 */
	public Integer updateChartCategoryById(ChartCategoryDO chartCategory);
	/**
	 * 查询指定类别
	 * @param id 类别编号
	 * @return 
	 */
	public ChartCategoryDO queryChartCategoryById(Integer id);
	/**
	 * 根据父ID查询类别信息
	 * @param id
	 * @return 
	 */
	public List<ChartCategoryDO> queryChartCategoryByParentId(Integer id);
	
	/**
	 * 通过父节点获取类别的所有子节点
	 * @param id :父节点ID,不能为null
	 * @return 包含子节点的Ext树结构
	 */
	public List<ExtTreeDto> queryExtTreeChildNodeByParentId(Integer id);
	/**
	 * 根据父ID查询类别信息列表
	 * @param chartCategoryDTO
	 * @return 返回符合条件的结果集
	 */
	public List<ChartCategoryDTO> queryChartCategoryListByParentId(ChartCategoryDTO chartCategoryDTO);
	/**
	 * 根据父ID统计类别信息列表
	 * @param chartCategoryDTO
	 * @return 返回符合条件的记录总数
	 */
//	public Integer countChartCategoryListByParentId(ChartCategoryDTO chartCategoryDTO);
	/**
	 * 根据父ID查询类别信息
	 * @param id
	 * @return 
	 */
	public ChartCategoryDTO queryChartCategoryDtoById(Integer id);
	
	/**
	 * 初始化类别对应的图表数据
	 * @param categoryList:需要初始化的子类别，不能为null
	 * @param chartInfoId:数据图表对应的趋势图信息
	 * @return
	 */
	public List<ChartCategoryDTO> initChartData(List<ChartCategoryDTO> categoryList, Integer chartInfoId, Integer parentId);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public LinkedList<ChartCategoryDO> queryChartCategoryTreeByParentId(Integer id);
}
