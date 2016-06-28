/*
 * 文件名称：ExhibitIndustryCategoryService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.exhibit;

import java.util.List;

import com.zz91.ep.domain.exhibit.ExhibitIndustryCategory;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：展会行业类别信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface ExhibitIndustryCategoryService {

	/**
	 * 函数名称：queryExhibitIndustryCategoryAll
	 * 功能描述：查询所有展会行业类别信息（页面片段缓存）
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<ExhibitIndustryCategory> queryExhibitIndustryCategoryAll();

}