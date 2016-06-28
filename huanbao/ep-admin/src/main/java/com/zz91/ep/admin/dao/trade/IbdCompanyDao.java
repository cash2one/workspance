/*
 * 文件名称：IbdCompanyDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午5:47:43
 * 版本号　：1.0.0
 */
package com.zz91.ep.admin.dao.trade;

import com.zz91.ep.domain.trade.IbdCompany;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：行业类别Dao
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface IbdCompanyDao {

	/**
	 * 函数名称：queryCountByCategoryCode
	 * 功能描述：根据类别查询企业数目
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/06/26　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
//	Integer queryCountByCategoryCode(String categoryCode);

	/**
	 * 函数名称：queryCompanyByCategoryAndKewords
	 * 功能描述：根据类别或关键字查询买家信息
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
//	List<IbdCompany> queryCompanyByCategoryAndKewords(String categoryCode, String keywords, PageDto<IbdCompany> page);
	
	/**
	 * 函数名称：queryCompanyByCategoryAndKewordsCount
	 * 功能描述：根据类别或关键字查询买家信息数
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
//	Integer queryCompanyByCategoryAndKewordsCount(String categoryCode, String keywords);

	/**
	 * 函数名称：queryIbdCompanyById
	 * 功能描述：
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
//	IbdCompany queryIbdCompanyById(Integer id);

	/**
	 * 函数名称：queryContactByCid
	 * 功能描述：查询联系方式
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
//	IbdCompany queryContactByCid(Integer id);

	/**
	 * 函数名称：insertIbdCompanyByAdmin
	 * 功能描述：添加买家库记录
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	Integer insertIbdCompanyByAdmin(IbdCompany comp);

}