/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25 by Rolyer.
 */
package com.ast.ast1949.persist.information;

import java.util.List;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.dto.information.ChartCategoryDTO;

/**
 *
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface ChartCategoryDAO {
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
	 * 查询可在首页显示的类别
	 * @param max
	 * @return
	 */
	public List<ChartCategoryDO> queryChartCategoryCanShowInHome(Integer max);
}
