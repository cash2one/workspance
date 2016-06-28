/*
 * 文件名称：BaseDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午6:02:28
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.common.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：Dao底层类（所有Dao实现类都继承自这个类）
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class BaseDao extends SqlMapClientDaoSupport {

	/**
	 * 函数名称：buildId
	 * 功能描述：组装SQL语句
	 * 输入参数：
	 * 		  @Param prefix String 查询前缀
	 *        @Param sqlId String SQL名称
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String buildId(String prefix, String sqlId){
		return prefix+"."+sqlId;
	}

}