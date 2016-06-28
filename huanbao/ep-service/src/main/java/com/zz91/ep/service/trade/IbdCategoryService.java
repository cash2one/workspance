/*
 * 文件名称：IbdCategoryService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.IbdCategory;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：求购信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface IbdCategoryService {

	/**
	 * 函数名称：queryCategoryByParentCode
	 * 功能描述：根据父类查询子类列表
	 * 输入参数：id Integer 
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/06/26　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<IbdCategory> queryCategoryByParentCode(String categoryCode);

}