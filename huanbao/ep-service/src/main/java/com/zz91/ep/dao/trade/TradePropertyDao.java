/*
 * 文件名称：TradePropertyDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-20 下午3:09:03
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeProperty;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Dao层
 * 模块描述：专业属性
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface TradePropertyDao {

	/**
	 * 函数名称：queryPropertyByCategoryCode
	 * 功能描述：根据类别查询专业属性
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeProperty> queryPropertyByCategoryCode(String categoryCode);

}