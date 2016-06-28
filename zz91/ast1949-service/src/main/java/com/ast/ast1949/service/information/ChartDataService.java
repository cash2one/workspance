/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-27 by Rolyer.
 */
package com.ast.ast1949.service.information;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.information.ChartDataDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.information.ChartDataDTO;
import com.ast.ast1949.dto.information.ChartDataForIndexDTO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface ChartDataService {
	/**
	 * 添加报价信息
	 * @param chartData
	 * @return 成功返回新增记录的ID
	 */
//	public Integer insertChartData(ChartDataDO chartData);
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
//	public Integer updateChartDataById(ChartDataDO chartData);
	/**
	 * 查询指定报价信息
	 * @param id
	 * @return 
	 */
//	public ChartDataDO queryChartDataById(Integer id);
	/**
	 * 精确查询指定报价信息
	 * @param chartInfoId 走势图编号,不能为空<br />
	 * @param chartCategoryId 类别编号,不能为空<br />
	 * @param name 类别名称,不能为空<br />
	 * @return 
	 */
	public ChartDataDO queryChartDataByCondition(Integer chartInfoId,Integer chartCategoryId,String name);
	/**
	 * 设置走势图数据值，该方法首先会判断数据是否存在，若已存在这更新数据，否则插入新数据。
	 * @param chartData
	 * @return 成功 success=true；失败 success==false。
	 */
	public ExtResult setChartDataValue(ChartDataDO chartData);
	/**
	 * 获取标价数据
	 * @param param 参数:<br />
	 * @param chartCategoryId 类别ID<br/>
	 * @param fromDate 起始时间<br/>
	 * @param toDate 终止时间<br/>
	 * @param name 分段时间<br/>
	 * @return
	 */
//	public List<ChartDataDTO> queryChartData(Map<String, Object> param);
	
	/**
	 * 获取分段数据，分段可是1或n段
	 * @param chartCategoryId 类别ID<br/>
	 * @param fromDate 起始时间<br/>
	 * @param toDate 终止时间<br/>
	 * @param name 分段时间<br/>
	 * @return
	 */
	public Map<String, List<ChartDataDTO>> queryChartData(Integer chartCategoryId,Date fromDate,Date toDate,String[] name);
	/**
	 * 根据类别ChartInfoId删除报价信息
	 * @param id 信息编号
	 * @return 成功返回删除的行数
	 */
	public Integer deleteChartDataByChartInfoId(Integer id);
	
	public List<ChartDataForIndexDTO> queryChartDataForIndex(Integer max);
	
	/**
	 * 获取制定类别、指定时间的走势图数据
	 * @param chartCategoryId 走势图类别 id
	 * @param fromDate 起始日期
	 * @param toDate 结束日期
	 * @return
	 */
	public List<ChartDataDTO> queryChartData(Integer chartCategoryId, Date fromDate,Date toDate);
	
	/**
	 * 获取走势图列表list 数据 根据父类别id
	 * @param id
	 * @return
	 */
	public List<ChartDataForIndexDTO> queryChartDataForPriceByParentId(Integer id);
}
