/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.ExtTreeDto;

/**
 * @author totly
 *
 * created on 2011-9-15
 */
public interface TradeCategoryService {
 
    public static final int MAX_SIZE = 10;

    /**
    * 查询所有类别信息，并按照sort排序
    */
    public List<TradeCategory> queryTradeSupplyAll();

    /**
     * 根据标签查找与该标签有关的供求类别信息，主要在搜索时使用
     * parentCode：父code，区分供应（1000）和求购（1001）
     * tags：用于查询的标签/关键字，不能为null
     * deep：0：叶子节点；1：所有节点。
     * max：查询最大条数，默认为MAX_SIZE
     */
//    public List<TradeCategory> queryCategoryByTags(String parentCode, String tags, Integer deep, Integer max);

    /**
     * 查询parentCode的所有子类别信息
     * parentCode：父code，默认""，表示根类别
     * deep：0：父节点下的一级子节点；1：父节点下的所有叶子节点；2：父节点下的所有节点。
     * max：查询最大条数，默认为MAX_SIZE
     */
//    public List<TradeCategory> queryCategoryByParent(String parentCode, Integer deep, Integer max);

    /**
     * 根据类别名称查询相关类别
     * name：查询用的类别名（前匹配）
     * deep：0：叶子节点；1：所有节点。
     * max：查询最大条数，默认为MAX_SIZE
     */
//    public List<TradeCategory> queryCategoryByName(String name, Integer deep, Integer max);


    /**
     * 是否是叶子节点
     * @return
     */
//	public Integer queryIsLeafByCode(String code);

	public Integer createTradeCategory(TradeCategory tradeCategory,
			String parentCode);

	public Integer deleteTradeCategory(String code);

	public Integer updateTradeCategory(TradeCategory tradeCategory);

	public List<ExtTreeDto> queryTradeCategoryNode(String parentCode);

	public TradeCategory queryCategoryByCode(String code);

	public List<ExtTreeDto> queryTradeSupplyCategoryNode(String parentCode);

	public List<ExtTreeDto> queryTradeBuyCategoryNode(String parentCode);


	/**
	 * 编辑类别路径（类别1>类别2>类别3）
	 * @param categoryCode
	 * @return
	 */
//	public String buildCategory(String categoryCode);

//	String buildCategoryUrl(String categoryCode, String urlpre, String urlpost);

	/**
	 * 根据名字或标签查询数量
	 * @param name
	 * @param tags
	 * @return
	 */
	public Integer queryCountByNameOrTags(String name, String tags);

}