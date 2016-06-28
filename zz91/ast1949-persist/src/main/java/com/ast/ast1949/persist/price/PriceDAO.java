/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-19 by Rolyer.
 */
package com.ast.ast1949.persist.price;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.price.ForPriceDTO;
import com.ast.ast1949.dto.price.PriceDTO;

/**
 * @author Rolyer (rolyer.live@gmail.com) 资讯接口
 */

public interface PriceDAO {
	/**
	 * <h3>添加资讯</h3>
	 * 
	 * @param price
	 * @return int 返回最后一条记录的编号；添加失败返回“0”。
	 */
	public Integer insertPrice(PriceDO price) throws IllegalArgumentException;

	/**
	 * <h3>删除资讯</h3> 根据id删除一条资讯记录
	 * 
	 * @param id
	 *            编号
	 * @return 成功：返回影响行数；失败：返回“0”。
	 */
	// public Integer deletePriceById(Integer id) throws
	// IllegalArgumentException;

	/**
	 * <h3>删除资讯</h3> 根据id批量删除资讯记录
	 * 
	 * @param entities
	 *            编号
	 * @return 成功：返回影响行数；失败：返回“0”。
	 */
	public Integer batchDeleteUserById(int[] entities);

	/**
	 * <h3>更新资讯</h3> 根据id更新资讯记录
	 * 
	 * @param price
	 * @return 成功：返回影响行数；失败：返回“0”。
	 */
	public Integer updatePriceById(PriceDO price)
			throws IllegalArgumentException;

	/**
	 * <h3>查询资讯信息</h3> 根据id查询资讯信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 */
	// public PriceDO queryPriceById(Integer id) throws
	// IllegalArgumentException;

	/**
	 * <h3>查询资讯信息</h3> 根据id查询资讯信息,用于编辑时.
	 * 
	 * @param id
	 * @return PriceDTO
	 * @throws IllegalArgumentException
	 */
	public PriceDTO queryPriceByIdForEdit(Integer id)
			throws IllegalArgumentException;

	/**
	 * <h3>分页查询资讯信息</h3> 按条件搜索资讯信息
	 * 
	 * @param price
	 *            条件、分页参数
	 * @return 资讯信息
	 */
	public List<PriceDTO> queryMiniPriceByCondition(PriceDTO price);

	/**
	 * <h3>统计资讯信息记录数</h3> 按条件查询记录总数
	 * 
	 * @param price
	 *            条件、分页参数
	 * @return 返回记录总数
	 */
	public Integer getPriceRecordConutByCondition(PriceDTO price);

	/**
	 * 按点击率查询前十的新闻
	 * 
	 * @return 成功 返回PriceDO 失败 返回 null
	 */
	// public List<PriceDO> queryPriceByClickNumber();

	/**
	 * 根据类别分页查询报价信息
	 * 
	 * @param priceDTO
	 *            不能为空 typeId 类别Id assistTypeId 辅助类别Id
	 * @return 成功返回 查询结果 失败返回 null
	 */
	public List<PriceDO> queryPriceByTypeId(Integer typeId, Integer parentId,
			Integer assistTypeId, int max);

	/**
	 * 根据类别报价信息记录数
	 * 
	 * @param priceDTO
	 *            不能为空 typeId 类别Id assistTypeId 辅助类别Id
	 * @return 成功返回 查询结果 失败返回 null
	 */
	// public Integer queryPriceCountByTypeId(PriceDTO priceDTO);

	/**
	 * 根据辅助类别查询报价信息
	 * 
	 * @param priceDTO
	 * @return
	 */
	// public List<PriceDO> queryPriceByAssistTypeId(PriceDTO priceDTO);

	/**
	 * 查询最新供求 根据 parentId
	 * 
	 * @param priceDTO
	 * @return 成功返回 查询结果 失败返回 null
	 */

	/**
	 * 根据标题、类型查询资讯信息
	 * 
	 * @param param
	 *            参数：<br/>
	 *            typeId 资讯类型<br/>
	 *            title 标题<br/>
	 *            limitSize 查询记录数，默认为10。
	 * @return
	 */
	public List<PriceDO> queryPriceByTitleAndTypeId(Map<String, Object> param);

	/**
	 * <h3>查询下一篇信息</h3> 根据id查询 下一篇
	 * 
	 * @param id
	 *            编号
	 * @return
	 */

	public PriceDO queryDownPriceById(Integer id)
			throws IllegalArgumentException;

	/**
	 * <h3>查询上一篇信息</h3> 根据id查询 上一篇
	 * 
	 * @param id
	 *            编号
	 * @return
	 */
	public PriceDO queryOnPriceById(Integer id) throws IllegalArgumentException;

	/**
	 * @param parentId
	 *            根据parentId 分组查询父类别为parentId的 price信息
	 * @return List<ForPriceDTO> 集合 失败返回 null
	 */
	public List<ForPriceDTO> queryEachPriceByParentId(Integer parentId);

	/**
	 * 查询排序第一的时间
	 * 
	 * @param parentId
	 * @return PriceDO 的排序时间
	 */
	public PriceDO queryTopGmtOrderByParentId(Integer parentId);

	/**
	 * 分页搜索 报价信息
	 * 
	 * @param priceDTO
	 *            不能为空
	 * @param searchTitle
	 *            ,pageDto
	 * @return List<PriceDO> 报价列表
	 */
	public PageDto<PriceDO> queryPricePaginationListByTitle(
			String titleKeywords, PageDto page);

	/**
	 * 搜索 报价信息记录数
	 * 
	 * @param priceDTO
	 *            不能为空
	 * @param searchTitle
	 *            ,pageDto
	 * @return 记录数
	 */
	// public Integer queryPriceCountByCondition(PriceDTO priceDTO);

	/**
	 * 根据标签分页查询报价
	 * 
	 * @param tagsRelateArticleDTO
	 *            不能为空 tagsInfoId 不能为空 tagsArticleCategoryCode 不能为空 page 分页 不能为空
	 * @return List<PriceDO>
	 */
	// public List<PriceDO> queryPriceByTagsCondition(TagsRelateArticleDTO
	// tagsRelateArticleDTO);
	/**
	 * 根据标签查询标签记录总数
	 * 
	 * @param tagsRelateArticleDTO
	 *            不能为空 tagsInfoId 不能为空 tagsArticleCategoryCode 不能为空
	 * @return 报价记录总数
	 */
	// public Integer queryPriceCountByTagsCondition(TagsRelateArticleDTO
	// tagsRelateArticleDTO);
	/**
	 * 查询最新的报价信息（前20条）
	 * 
	 * @param id
	 *            type_id
	 * @return
	 */
	public List<PriceDO> queryLatestPriceByTypeId(Integer id);

	/**
	 * 根据编号更新审核状态
	 * 
	 * @param id
	 *            编号
	 * @param isChecked
	 *            审核状态,<br/>
	 *            "1" 代表已审核;"0" 代表未审核。
	 * @return
	 */
	public Integer updateIsCheckedById(Integer id, String isChecked);

	// public List<PriceDO> queryPriceByTypeIdAndAssistTypeId(Integer typeId,
	// Integer assistTypeId);

	/**
	 * 获取会员定制的最新的一条报价信息
	 * 
	 * @param typeId
	 * @param assistTypeId
	 * @return
	 */
	public PriceDO queryPriceForSubscribe(Integer typeId, Integer assistTypeId);

	/**
	 * 查询本周最新的报价信息
	 * 
	 * @param firstDate
	 *            本周第一天
	 * @param lastDate
	 *            本周最后一天
	 * @param size
	 *            条数
	 * @return List<PriceDTO>
	 */
	public List<PriceDTO> queryNewPriceOnWeek(Date firstDate, Date lastDate,
			Integer size);

	public List<PriceDO> queryPriceByType(Integer typeId, Integer parentId,
			Integer assistTypeId, PageDto<PriceDO> page);

	public Integer queryPriceByTypeCount(Integer typeId, Integer parentId,
			Integer assistTypeId);

	public List<ForPriceDTO> queryPriceByParentId(Integer parentId, Integer max);

	public List<ForPriceDTO> queryPriceAndCategoryByTypeId(Integer typeId,
			Integer max);

	public List<PriceDO> queryPriceByTypeIdOrParentId(Integer id, Integer max);

	/**
	 * 没有用到的接口
	 * 
	 * @param code
	 * @param max
	 * @return
	 */
	public List<ForPriceDTO> queryPriceByIndex(String code, Integer max);

	public List<ForPriceDTO> queryLatestPrice(String code, Integer max);

	/***************
	 * 在点击行情报价里面的一条资讯详细情况时候，改变其对应的流量。 使用在行情报价的报价详细页面
	 * 
	 * @param number
	 * @return
	 */
	public Integer updateRealClickNumberById(Integer number, Integer Id);

	/**
	 * 今日更新行情报价***条
	 */
	public Integer queryPriceCount(String from);

	public List<PriceDO> queryPriByTypeId();

	public Integer getTypeidById(Integer id);

	public String queryTagsById(Integer id);

	/**
	 * 根据id检索资讯UV
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryUVById(Integer id);

	/**
	 * 根据标题(title)检索id
	 * 
	 * @return
	 */
	public Integer queryIdByTitle(String title, String date);

	/**
	 * 检索 时间区域内的所有list
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public List<PriceDO> queryListByFromTo(String from, String to);

	/**
	 * 根据类别获取最新报价
	 * 
	 * @param typeId
	 * @param size
	 * @return
	 */
	public List<PriceDO> queryListByTypeId(Integer typeId, Integer size);

	/**
	 * 根据总类别获取最新报价
	 * 
	 * @param typeId
	 * @param size
	 * @return
	 */
	public List<PriceDO> queryListByAllTypeId(Integer typeId, Integer size);

	/**
	 * 更新 索引资讯字段
	 * @param id
	 * @param content
	 * @return
	 */
	public Integer updateContentQueryById(Integer id, String content);
	/**
	 * 查询最新废塑料行情综述下面的PVC、ABS/PS、PP、HDPE、LLDPE  全国各地PP市场概况和全国各地PE市场概况
	 * @return
	 */
	public List<PriceDO> queryNewPrice(Integer typeId);

	public List<PriceDO> queryNewPrice2(Integer typeId);
	/**
	 * 
	 * @param type　
	 * @param month　
	 * @return
	 */
	public List<PriceDO> queryListByTypeIdHalfYear(Integer type, Integer month);

	public List<PriceDO> queryPriceByTypeTwo(Integer typeId, Integer parentId,
			Integer assistTypeId, PageDto<PriceDO> page);

	public Integer queryPriceByTypeCountTwo(Integer typeId, Integer parentId,
			Integer assistTypeId);
}
