/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-31 by Rolyer.
 */
package com.ast.ast1949.persist.information;

import java.util.List;

import com.ast.ast1949.domain.information.ChartsInfoDO;
import com.ast.ast1949.dto.information.ChartsInfoDTO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface ChartsInfoDAO {
	/**
	 * 添加信息
	 * @param chartsInfo<br/>
	 * 		chartCategoryId 类别不能为空<br/>
	 * 		gmtDate 报价日期不能为空<br/>
	 * @return 成功返回添加记录的id值
	 */
	public Integer insertChartsInfo(ChartsInfoDO chartsInfo);
	/**
	 * 删除信息
	 * @param id 要删除记录的编号
	 * @return 成功返回影响行数
	 */
	public Integer deleteChartsInfoById(Integer id);
	/**
	 * 更新信息
	 * @param chartsInfo
	 * 		id 所要更新记录的编号不能为空
	 * 		chartCategoryId 类别不能为空<br/>
	 * 		gmtDate 报价日期不能为空<br/>
	 * @return 成功返回影响行数
	 */
	public Integer updateChartsInfoById(ChartsInfoDO chartsInfo);
	/**
	 * 获取指点信息
	 * @param id 编号
	 * @return 成功结果集
	 */
	public ChartsInfoDO queryChartsInfoById(Integer id);
	/**
	 * 获取信息列表
	 * @param chartsInfoDTO 参数:<br/>
	 * 		gmtDate 按标价时间查询<br/>
	 * 		chartCategoryId 按类别查询<br/>
	 * 		title 按标题查询<br/>
	 * @return 返回符合条件的结果集
	 */
	public List<ChartsInfoDTO> queryChartsInfoList(ChartsInfoDTO chartsInfoDTO);
	/**
	 * 统计信息记录条数
	 * @param chartsInfoDTO 参数:<br/>
	 * 		gmtDate 按标价时间查询<br/>
	 * 		chartCategoryId 按类别查询<br/>
	 * 		title 按标题查询<br/>
	 * @return 返回符合条件的记录条数
	 */
	public Integer countChartsInfoList(ChartsInfoDTO chartsInfoDTO);
}
