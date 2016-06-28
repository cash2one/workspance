/*
 * 文件名称：IbdCompanyService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade;

import com.zz91.ep.domain.trade.IbdCompany;
import com.zz91.ep.dto.PageDto;


/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：求购信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface IbdCompanyService {

	/**
	 * 函数名称：queryCountByCategoryCode
	 * 功能描述：根据类别查询企业数目
	 * 输入参数：id Integer 
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/06/26　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryCountByCategoryCode(String categoryCode);

	/**
	 * 函数名称：pageCompanyByCategoryAndKewords
	 * 功能描述：根据类别或关键字查新潜在买家库心系
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/06/27　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public PageDto<IbdCompany> pageCompanyByCategoryAndKewords(String categoryCode, String keywords, PageDto<IbdCompany> page);

	/**
	 * 函数名称：queryIbdCompanyById
	 * 功能描述：根据ID查询买家信息
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public IbdCompany queryIbdCompanyById(Integer id);

	/**
	 * 函数名称：queryContactByCid
	 * 功能描述：查询联系方式
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　  涂灵峰 　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public IbdCompany queryContactByCid(Integer id);

}