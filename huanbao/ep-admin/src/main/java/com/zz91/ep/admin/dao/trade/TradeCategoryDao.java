/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
import java.util.List;
import com.zz91.ep.domain.trade.TradeCategory;

public interface TradeCategoryDao {

    /**
    * 查询所有类别信息，并按照sort排序
    */
    public List<TradeCategory> queryTradeSupplyAll();

    /**
     * 根据标签查找与该标签有关的供求类别信息，主要在搜索时使用
     * tags：用于查询的标签/关键字，不能为null
     * deep：0：叶子节点；1：所以节点。
     * count：查询最大条数
     */
//    public List<TradeCategory> queryCategoryByTags(String parentCode, String tags, Integer deep, Integer count);

    /**
     * 查询parentCode的所有子类别信息
     * parentCode：父code，默认""，表示根类别
     * deep：0：父节点下的一级子节点；1：父节点下的所有叶子节点；2：父节点下的所有节点。
     * count：查询最大条数
     */
//    public List<TradeCategory> queryCategoryByParent(String parentCode, Integer deep, Integer count);

    /**
     * 根据类别名称查询相关类别
     * name：查询用的类别名（前匹配）
     * deep：0：叶子节点；1：所有节点。
     * count：查询最大条数
     */
//    public List<TradeCategory> queryCategoryByName(String name, Integer deep, Integer count);

    /**
     * 根据交易类别名称查询交易类别code
     * @param categoryName
     * @return
     */
    public String queryCategoryCodeByCategoryName(String categoryName);

	/**
	 * 通过求购类别名称查询求购类别代码
	 * @param categoryName
	 * @return
	 */
	public String queryBuyCategoryCodeByCategoryName(String categoryName);

	/**
	 * 通过供应类别名称查询供应类别代码
	 * @param categoryName
	 * @return
	 */
	public String querySupplyCategoryCodeByCategoryName(String categoryName);

    /**
     * 是否是叶子节点
     * true：是叶子节点，false：不是叶子节点
     * @return
     */
//    public Integer queryIsLeafByCode(String code);

	public String queryMaxCodeOfChild(String parentCode);

	public Integer insertTradeCategory(TradeCategory tradeCategory);

	public Integer deleteTradeCategory(String code);

	public TradeCategory queryCategoryByCode(String code);

	public List<TradeCategory> queryChild(String parentCode);

	public Integer countChild(String code);

	public Integer updateTradeCategory(TradeCategory tradeCategory);

	public Integer updateParentLeafByParentCode(String parentCode);

	public List<TradeCategory> querySupplyChild(String parentCode);

	public Integer countSupplyChild(String code);

	public List<TradeCategory> queryBuyChild(String parentCode);

	public Integer countBuyChild(String code);

	/**
	 * @param name
	 * @param tags
	 * @return
	 */
	public Integer queryCountByNameOrTags(String name, String tags);
}