/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25 by Rolyer.
 */
package com.ast.ast1949.persist.information;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.information.ChartDataDO;
import com.ast.ast1949.dto.information.ChartDataDTO;

/**
 *
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface ChartDataDAO {
	/**
	 * 添加报价信息
	 * @param chartData
	 * @return 成功返回新增记录的ID
	 */
	public Integer insertChartData(ChartDataDO chartData);
	/**
	 * 根据id删除报价信息
	 * @param id 记录编号
	 * @return 成功返回删除的行数
	 */
//	public Integer deleteChartDataById(Integer id);
	/**
	 * 根据类别id删除报价信息
	 * @param id 类别编号
	 * @return 成功返回删除的行数
	 */
//	public Integer deleteChartDataBychartCategoryId(Integer id);
	/**
	 * 更新报价信息
	 * @param chartData
	 * @return 成功返回更新的行数
	 */
	public Integer updateChartDataById(ChartDataDO chartData);
	/**
	 * 查询指定报价信息
	 * @param id
	 * @return 
	 */
//	public ChartDataDO queryChartDataById(Integer id);
	/**
	 * 精确查询指定报价信息
	 * @param param 参数：<br />
	 * 		chartInfoId 走势图编号<br />
	 * 		chartCategoryId 类别编号<br />
	 * 		name 类别名称
	 * @return 
	 */
	@Deprecated
	public ChartDataDO queryChartDataByCondition(Map<String, Object> param);
	
	/**
	 * 获取标价数据
	 * @param param 参数：<br/>
	 * chartCategoryId 类别ID<br/>
	 * fromDate 起始时间<br/>
	 * toDate 终止时间<br/>
	 * name 分段时间<br/>
	 * @return
	 */
	public List<ChartDataDTO> queryChartData(Map<String, Object> param);
	
	/**
	 * 准确查找一个走势图数据
	 * @param chartInfoId:走势图ID，不能为null
	 * @param chartCategoryId:类别ID，不能为null
	 * @param name:走势图时间段
	 * @return
	 */
	public ChartDataDO queryOneChartData(Integer chartInfoId, Integer chartCategoryId, String name);
	
	/**
	 * 查找走势图数据
	 * @param chartInfoId:走势图ID，不能为null
	 * @param chartCategoryId:类别ID，不能为null
	 * @return
	 */
	public List<ChartDataDTO> queryChartData(Integer chartInfoId, Integer chartCategoryId);
	/**
	 * 根据类别ChartInfoId删除报价信息
	 * @param id 信息编号
	 * @return 成功返回删除的行数
	 */
	public Integer deleteChartDataByChartInfoId(Integer id);
	
	public List<ChartDataDO> queryChartDataByChartCategoryId(Integer id);
	
	public List<ChartDataDTO> queryChartDataDesc(Map<String, Object> param);

}
