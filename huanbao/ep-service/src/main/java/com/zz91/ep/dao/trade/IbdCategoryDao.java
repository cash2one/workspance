/*
 * 文件名称：IbdCategoryDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午5:47:43
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.IbdCategory;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：行业类别Dao
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface IbdCategoryDao {

	/**
	 * 函数名称：queryCategoryByParentCode
	 * 功能描述：根据父类查询子类信息列表
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/06/26　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	List<IbdCategory> queryCategoryByParentCode(String categoryCode);

}
