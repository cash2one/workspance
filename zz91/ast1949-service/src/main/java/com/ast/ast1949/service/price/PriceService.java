/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-22
 */
package com.ast.ast1949.service.price;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.price.ForPriceDTO;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.dto.price.PriceDto2;
import com.zz91.util.search.SphinxException;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 * 
 */
public interface PriceService {
	/**
	 * <h3>添加资讯</h3>
	 * 
	 * @param price
	 * @return int 返回最后一条记录的编号；添加失败返回“0”。
	 */
	public Integer insertPrice(PriceDO price) throws IllegalArgumentException;
	public String queryPriceTtpePlasticOrMetal(Integer id);

	/**
	 * <h3>删除资讯</h3> 根据id删除一条资讯记录
	 * 
	 * @param id
	 *            编号
	 * @return 成功：返回影响行数；失败：返回“0”。
	 */
//	public Integer deletePriceById(Integer id) throws IllegalArgumentException;

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
	public Integer updatePriceById(PriceDO price) throws IllegalArgumentException;

	/**
	 * <h3>查询资讯信息</h3> 根据id查询资讯信息,用于编辑时.
	 * 
	 * @param id
	 * @return PriceDTO
	 * @throws IllegalArgumentException
	 */
	public PriceDTO queryPriceByIdForEdit(Integer id) throws IllegalArgumentException;
	
	/**
	 * 根据标题搜索分页报价信息
	 * 
	 * @return List<PriceDO> 报价列表
	 */
	@Deprecated
	public PageDto<PriceDO> queryPricePaginationListByTitle(String titleKeywords,PageDto<PriceDO> page);

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
//	public List<PriceDO> queryPriceByClickNumber();

	
	/**
	 * 根据类别报价信息记录数
	 * 
	 * @param priceDTO
	 *            不能为空 typeId 类别Id assistTypeId 辅助类别Id
	 * @return 成功返回 查询结果 失败返回 null
	 */
//	public Integer queryPriceCountByTypeId(PriceDTO priceDTO);

	/**
	 * 
	 * @param priceDTO
	 * @return
	 */
//	public List<PriceDO> queryPriceByAssistTypeId(PriceDTO priceDTO);


	/**
	 * 根据标题、类型查询资讯信息
	 * 
	 * @param typeId
	 *            资讯类型<br/>
	 * @param title
	 *            标题<br/>
	 * @param limitSize
	 *            查询记录数，默认为10。
	 * @return
	 */
	@Deprecated
	public List<PriceDO> queryPriceByTitleAndTypeId(String typeId, String title, String limitSize);

	/**
	 * <h3>查询下一篇信息</h3> 根据id查询 下一篇
	 * 
	 * @param id
	 *            编号
	 * @return
	 */

	public PriceDO queryDownPriceById(Integer id) throws IllegalArgumentException;

	/**
	 * <h3>查询上一篇信息</h3> 根据id查询 上一篇
	 * 
	 * @param id
	 *            编号
	 * @return
	 */
	public PriceDO queryOnPriceById(Integer id) throws IllegalArgumentException;

	/**
	 * 
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
	 * 根据list 循环查报价以及类别
	 * 
	 * @param list
	 *            不能为空
	 * @return 成功返回 List<PriceCategoryDTO> 失败返回null
	 */
	public List<PriceCategoryDTO> queryPriceAndCategory(List<PriceCategoryDO> list);

	/**
	 * 循环查报价以及类别
	 * 
	 * @param id
	 *            根据id 查询类别
	 * @param assistTypeId
	 *            辅助类别
	 * @param pageSize
	 * @return 成功返回 List<PriceCategoryDTO> 失败返回null
	 */
	public List<PriceCategoryDTO> queryPriceList(Integer id, Integer assistTypeId, Integer pageSize);

	/**
	 * 搜索 报价信息记录数
	 * 
	 * @param priceDTO
	 *            不能为空
	 * @param searchTitle
	 *            ,pageDto
	 * @return 记录数
	 */
//	public Integer queryPriceCountByCondition(PriceDTO priceDTO);

	/**
	 * 根据标签分页查询报价
	 * 
	 * @param tagsRelateArticleDTO
	 *            不能为空 tagsInfoId 不能为空 tagsArticleCategoryCode 不能为空 page 分页 不能为空
	 * @return List<PriceDO>
	 */
	//  public List<PriceDO> queryPriceByTagsCondition(TagsRelateArticleDTO tagsRelateArticleDTO);
	/**
	 * 根据标签查询标签记录总数
	 * 
	 * @param tagsRelateArticleDTO
	 *            不能为空 tagsInfoId 不能为空 tagsArticleCategoryCode 不能为空
	 * @return 报价记录总数
	 */
	//  public Integer queryPriceCountByTagsCondition(TagsRelateArticleDTO tagsRelateArticleDTO);
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
//	public List<PriceDO> queryPriceByTypeIdAndAssistTypeId(Integer typeId, Integer assistTypeId);

	/**
	 * 获取会员定制的最新的一条报价信息
	 * 
	 * @param typeId
	 * @param assistTypeId
	 * @return
	 */
//	public PriceDO queryPriceForSubscribe(Integer typeId, Integer assistTypeId);

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
	public List<PriceDTO> queryNewPriceOnWeek(Date firstDate, Date lastDate, Integer size);

	/**
	 * <h3>查询资讯信息</h3> 根据id查询资讯信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 */
//	@Deprecated
//	public PriceDO queryPriceById(Integer id) throws IllegalArgumentException;
	
	public List<PriceCategoryDTO> queryPriceCategoryInfoByParentIdAndAssistId(Integer parentId, Integer assistId,
			Integer topNum);

	/**
	 * 根据类别分页查询报价信息
	 * 
	 * @param priceDTO
	 *            不能为空 typeId 类别Id 
	 *            assistTypeId 辅助类别Id
	 * @return 成功返回 查询结果 失败返回 null
	 */
	public List<PriceDO> queryPriceByTypeId(Integer typeId, Integer parentId, Integer assistTypeId, Integer max);
	
	public List<PriceDO> queryPriceByType(Integer typeId, Integer parentId, Integer assistTypeId, PageDto<PriceDO> page);
	
	public PageDto<PriceDO> pagePriceByType(Integer typeId, Integer parentId, Integer assistTypeId, PageDto<PriceDO> page);
	/**
	 * 搜索　　2016-05-01 以后的数据
	 * @param typeId
	 * @param parentId
	 * @param assistTypeId
	 * @param page
	 * @return
	 */
	public PageDto<PriceDO> pagePriceByTypeTwo(Integer typeId, Integer parentId, Integer assistTypeId, PageDto<PriceDO> page);
	
	public List<ForPriceDTO> queryPriceByParentId(Integer parentId, Integer max);
	
	public List<ForPriceDTO> queryPriceAndCategoryByTypeId(Integer typeId, Integer max);
	
	public Map<Integer, List<PriceDto2>> queryPriceOfParentCategory(Integer parentId, Integer topNum);
	
	public List<ForPriceDTO> queryPriceByIndex(String code, Integer max);
	
	public PageDto<PriceDO> pagePriceBySearchEngine(String titleKeywords,PriceDO priceDO, PageDto<PriceDO> page);

	List<ForPriceDTO> queryLatestPrice(String code, Integer max);
	
	/***************
	 * 在点击资讯详细情况时候，改变其对应的流量。
	 * @param id
	 * @return
	 */
	public Integer updateRealClickNumberById(Integer id);
	
	//查询今天最新报价总数
	public Integer queryPriceCount();
	
	public List<PriceDO> queryPriByTypeId();
	
	public Integer queryTypeidById(Integer id);
	
	/**
	 * 根据id检索资讯UV
	 * @param id
	 * @return
	 */
	public Integer queryUVById(Integer id);
	/**
	 * 
	 * @param map
	 * @return
	 */
	public Map<Integer, List<PriceDto2>> queryLatestPriceUsePageEngine(Map<Integer, List<PriceDto2>> map,String queryKey);
	public String  queryContentByPageEngine(String priceSearchKey,String zaocanSearchKey,String wanbaoSearchKey);
	
	/**
	 * 验证当天资讯标题重复
	 * 网络卡的时候，运营容易多次点击发布多条相同信息，故做此判断
	 * 逻辑：以title为检索条件检索当天报价中，重复的标题，存在则返回true,不存在则返回false
	 * @param date: 时间范围起始 ，默认为当天0点
	 * @return
	 */
	public Boolean forbidDoublePub(String title,Date date);
	
	/**
	 * 更新表从search_label字段，适应搜索引擎关键字搜索
	 */
	@Deprecated
	public void updateAllSearchLabel();
	
	/**
	 * 根据开始时间结束时间 搜索时间内的所有list 
	 * 慎用。。使用于导数据
	 * @param from
	 * @param to
	 * @return
	 * @throws ParseException 
	 */
	public List<PriceDO> queryListByFromTo(String from,String to);
	
	/**
	 * 传入 typeId 获取该类别最新报价
	 * @param typeId
	 * @param size
	 * @return
	 */
	public List<PriceDO> queryListByTypeId(Integer typeId, Integer size);
	
	/**
     * 根据总类别获取最新报价
     * @param typeId
     * @param size
     * @return
     */
    public List<PriceDTO> queryListByAllTypeId(Integer typeId, Integer size);
	
	/**
	 * 搜索父类别下所有子类别的最新资讯 type_id  or parent_id
	 * @param parentId
	 * @param size
	 * @return
	 */
	public List<PriceDTO> queryListByParentId(Integer parentId, Integer size);
	
	/**
	 * 传入多个type_id的，搜索多个list，然后按照时间排序显示
	 * @param ids
	 * @param size
	 * @return
	 */
	public List<PriceDTO> queryListByIntArray(Integer[] ids, Integer size);
	/**
	 * 上下模版报价固定内容组装
	 * @param priceSearchKey
	 * @param zaocanSearchKey
	 * @param wanbaoSearchKey
	 * @param tags
	 * @return
	 */
	public String buildTemplateContent(String priceSearchKey, String zaocanSearchKey,String wanbaoSearchKey, String tags);
	
	/**
	 * 搜索引擎检索报价列表
	 * @param keyWords 	关键字
	 * @param from		开始时间
	 * @param to 		结束时间
	 * @param size		条数
	 * @return
	 * @throws ParseException 
	 * @throws SphinxException 
	 */
	public List<PriceDO> queryListForSearchEngine(String keyWords, Date from,Date to, Integer size) throws ParseException, SphinxException;
	
	/**
	 * 更新搜索引擎 字段 中文 企业报价 资讯专用　
	 * @param id
	 * @param content
	 * @return
	 */
	Integer updateContentQueryById(Integer id, String content);
	/**
	 * 查询最新废塑料行情综述下面的PVC、ABS/PS、PP、HDPE、LLDPE  全国各地PP市场概况和全国各地PE市场概况　行情首页
	 * @return
	 */
	public List<PriceDO> queryNewPrice(Integer typeId);
	/**　
	 * 获取今日行情报价　行情首页
	 * @return
	 */
	public List<PriceDO> queryEveryDayHq(Integer typeId);
	/**
	 * 获取昨日　行情首页
	 * @param typeId
	 * @return
	 */
	public List<PriceDO> queryEveryDayHq2(Integer typeId);
	/**
	 * 昨日期货收盘价　行情首页
	 * @param i
	 * @param j
	 * @return
	 */
	public List<PriceDO> queryListByTypeIdThree(Integer typeId);
	/**
	 * 国内塑料市场价格　行情首页
	 */
	public List<PriceDO> queryListByTypeIdFour();
	/**
	 * 根据type最新报价 类别包括：ABS、PP、PVC、PS、LDPE、HDPE、LLDPE　行情首页
	 */
	public List<PriceDO> queryByTypeIdYYC(Integer typeId);
	/**
	 * 热点行情 行情首页
	 */
	public List<PriceDO> queryRedian();
	/**
	 * 国内市场概况　行情首页
	 * @return
	 */
	public List<PriceDO> queryListByTypeIdGK();
	/**
	 * 市场评论　行情首页
	 */
	public List<PriceDO> queryListByTypeIdComments();
	/**
	 * 行情列表收索页
	 * @param keyWords　根据titil中是否包含keyWords　收索
	 * @param page
	 * @return
	 */
	public PageDto<PriceDO> pagePriceByTitleSearchEngine(String keyWords,
			PageDto<PriceDO> page);
	/**
	 * 根据typeId
	 * 获取到month 月内的数据
	 */
	public List<PriceDO> queryListByTypeIdHalfYear(Integer type,Integer month);
	/**
	 * 获取５条最新　每日价格行情（PVC、PP、HDPE、ABS/PS）
	 * 
	 */
	public List<PriceDO> queryBytypeIdNewPrice();
	/**
	 * 获取5条东莞、齐鲁化工城、临沂、顺德、上海塑料市场价格
	 */
	public List<PriceDO> queryProvin();
	
	/**
	 * 获取5条东莞、齐鲁化工城、临沂、顺德、北京　杭州塑料市场价格
	 */
	public List<PriceDO> querySexProvin();
	/**
	 * 获取2条　ABS/PS  PP PVC  １条PE
	 * @return
	 */
	public List<PriceDO> queryListByTypeIdHq();
}
