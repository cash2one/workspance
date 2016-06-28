/*
 * 文件名称：ExhibitService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.exhibit;

import java.util.List;

import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.exhibit.ExhibitDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：展会信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface ExhibitService {

	/**
	 * 函数名称：queryExhibitsByRecommend
	 * 功能描述：根据推荐类型code查询展会信息（页面片段缓存）
	 * 输入参数：
	 *        @param code String 展会类型
	 *        @param size Integer 获取信息条数
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<Exhibit> queryExhibitsByRecommend(String categoryCode, Integer size);

	/**
	 * 函数名称：queryExhibitsByCategory
	 * 功能描述：根据类型code查询展会信息（页面片段缓存）
	 * 输入参数：
	 * 		  @param industryCode 行业类型
	 *        @param code String 展会类型
	 *        @param size Integer 获取信息条数
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<Exhibit> queryExhibitsByCategory(String categoryCode, String industryCode, Integer size);

	/**
	 * 函数名称：queryExhibitDtosByCategory
	 * 功能描述：根据类型code查询展会扩展信息（页面片段缓存）
	 * 输入参数：
	 *        @param code String 展会类型
	 *        @param size Integer 获取信息条数
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　　1.0.0　　 　　 创建方法函数
	 * 			2012/08/27		马元生				1.0.1		更改方法名，原 queryExhibitDtosByCategory，变更返回类型，原ExthibitDto
	 */
	public List<ExhibitDto> queryByCategory(String categoryCode, Integer size);

	/**
	 * 函数名称：pageExhibitByCategory
	 * 功能描述：根据类型code查询展会扩展信息
	 * 输入参数：
	 *        @param categoryCode String 展会类型
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * 			2012/08/27		马元生				1.0.1		使用 @Deprecated 注解，原因：该方法未被任何人调用，且方法名与实际调用DAO可能会产生歧义
	 */
	@Deprecated
	public PageDto<ExhibitDto> pageExhibitByCategory(String categoryCode, PageDto<ExhibitDto> page);

	/**
	 * 函数名称：queryExhibitDetailsById
	 * 功能描述：根据id查询展会详细信息
	 * 输入参数：
	 *        @param id Integer 展会id
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Exhibit queryExhibitDetailsById(Integer id);
	
	/**
	 * 函数名称：queryExhibitsByCategoryCode
	 * 功能描述：根据类型code查询展会信息（页面片段缓存）
	 * 输入参数：
	 * 		  @param industryCode 行业类型
	 *        @param code String 展会类型
	 *        @param size Integer 获取信息条数
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/09/14　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<ExhibitDto> queryExhibitsByCategoryCode(String categoryCode, String industryCode, Integer size);
	
	
}