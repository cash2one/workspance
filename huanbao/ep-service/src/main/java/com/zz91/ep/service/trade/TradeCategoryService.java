/*
 * 文件名称：TradeCategoryService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade;

import java.util.List;
import java.util.Map;

import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.search.SearchPropertyDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：交易中心供求信息类别接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface TradeCategoryService {

	/**
	 * 函数名称：queryNewsByCategory
	 * 功能描述：查询所有的不同类别资讯信息（页面片段缓存）
	 * 输入参数：
	 *        @param parentCode 查询用的类别名（前匹配）
	 *        @param deep：0：叶子节点；1：所有节点。
	 *        @param max：查询最大条数，默认为MAX_SIZE
     * 
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeCategory> queryCategoryByParent(String parentCode, Integer deep, Integer max);

	/**
	 * 函数名称：queryTagsByCode
	 * 功能描述：查询所有的不同类别查询标签信息（标签以,隔开）
	 * 输入参数：
	 *        @param categoryCode 查询用的类别名
     * 
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<String> queryTagsByCode(String categoryCode);
	
	/**
	 * 函数名称：queryCodeById
	 * 功能描述：根据id查询code
	 * 输入参数：
	 *        @param id
     * 
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/09/27　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String queryCodeById(Integer id);

	/**
	 * 函数名称：buildCategoryUrl
	 * 功能描述：编辑URL地址
	 * 输入参数：
	 *        @param category 查询用的类别名
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * 		   2012/09/28	 黄怀清			   1.0.1        url参数由类别的code改为id
	 */
	public String buildCategoryUrl(String category, String addressPre, String addressEnd);

	/**
	 * 函数名称：queryCategoryByTags
	 * 功能描述：根据标签查找与该标签有关的供求类别信息，主要在搜索时使用
	 * 输入参数：
     *        parentCode：父code，区分供应（1000）和求购（1001）
     *        tags：用于查询的标签/关键字，不能为null
     *        deep：0：叶子节点；1：所有节点。
     *        max：查询最大条数，默认为MAX_SIZE
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeCategory> queryCategoryByTags(String parentCode, String tags, Integer deep, Integer max);

	/**
	 * 函数名称：queryIsLeafByCode
	 * 功能描述：是否是叶子节点
	 * 输入参数：
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryIsLeafByCode(String category);
	
	/**
	 * 函数名称：querySearchPropertyByCategory
	 * 功能描述：根据产品类别查找对应的可以提供搜索的产品特有属性
	 * 输入参数：categoryCode：产品code，不能为null
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<SearchPropertyDto> querySearchPropertyByCategory(String categoryCode);
	/****
	 * 
	 * 函数名称：queryTagsById
	 * 功能描述：根据id 查询TradeCategory
	 * 输入参数：
	 * 　　　　　.......
	 * 　　　　
	 * 异　　常：[按照异常名字的字母顺序]
	 */
	public TradeCategory queryTagsById(Integer id);
	
	/**
	 * 
	 * 函数名称：buildCategory
	 * 功能描述：编辑类别路径（类别1>类别2>类别3）
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String buildCategory(String categoryCode);
	/**
	 * 函数名称：getCategoryByCode
	 * 功能描述：根据code查询类别
	 * 输入参数：code
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/07/26　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeCategory getCategoryByCode(String code);
	
	public String queryNameByCode(String code);
	
	/**
	 * queryBroCategoryByCode
	 * 根据code查询兄弟种类
	 * @return
	 */
	public List<TradeCategory> queryBroCategoryByCode(String code,Integer size);
	
	public void initSupplySearchTbar(String keywords, String categoryCode, Map<String, Object> out);
	
	public List<TradeCategory> buildCategoryPath(String categoryCode);

}
