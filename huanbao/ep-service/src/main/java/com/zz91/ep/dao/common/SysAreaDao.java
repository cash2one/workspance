/*
 * 文件名称：SysAreaDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.common;

import java.util.List;

import com.zz91.ep.domain.sys.SysArea;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：地区信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface SysAreaDao {
	
	/**
	 * 函数名称：queryAllSysAreas
	 * 功能描述：查询所有的地区信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<SysArea> queryAllSysAreas();

	/**
	 * 函数名称：querySysAreaByCode
	 * 功能描述：根据父类别查询地区信息
	 * 输入参数：
	 *         @param code 父类别
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<SysArea> querySysAreasByCode(String code);

	/**
	 * 函数名称：queryNameByCode
	 * 功能描述：通过省份名称查询省份code
	 * 输入参数：
	 *         @param code 类别code
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String queryNameByCode(String code);
	
	/**
	 * 函数名称：getSysAreaByCode
	 * 功能描述：通过省份名称查询省份code
	 * 输入参数：
	 *         @param code 类别code
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/07/26　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */	
	public SysArea getSysAreaByCode(String code);

}