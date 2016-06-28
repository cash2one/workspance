/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.trade;

import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;

/**
 * @author totly
 * 
 *         created on 2011-9-15
 */
public interface TradeBuyService {

	public static final int MAX_SIZE = 10;

	public static final int STATUS_PAUSE_YES = 1;

	public static final int STATUS_PAUSE_NO = 0;

	public static final int STATUS_CHECK_UN = 0;

	public static final int STATUS_CHECK_YES = 1;

	public static final int STATUS_CHECK_NO = 2;

	/**
	 * 发布求购信息
	 */
	public Integer createTradeBuy(TradeBuy tradeBuy);

//	public TradeBuyDto buildTradeBuyDto(TradeBuy tradeBuy);

//	public List<TradeBuyDto> buildTradeBuyDtoList(List<TradeBuy> list);

	/**
	 * 根据求购信息Id查询求购信息
	 */
//	public TradeBuy queryBuyDetailsById(Integer id);

	/**
	 * 根据求购信息Id查询求购信息
	 */
//	public TradeBuy queryUpdateBuyById(Integer id, Integer cid);

	/**
	 * 根据求购信息Id查询求购基本信息（列表展现）
	 */
//	public TradeBuy queryBuySimpleDetailsById(Integer id);

	/**
	 * 根据产品类别，查询更多同类求购信息（按更新时间排序）
	 */
//	public List<TradeBuy> queryCommonBuyByCategory(String categoryCode,
//			Integer max);

	/**
	 * 根据产品类别，查找该类别或所有子类别的最新求购信息（按更新时间排序） category：产品类别，如果为null则查找全部求购信息的最新的信息
	 * isDirect：true 查询全部子类，false 仅自己（默认） max：查询最大条数
	 */
//	public List<TradeBuy> queryNewestBuyByCategory(String category,
//			Boolean isDirect, Integer max);

	/**
	 * 查找同一家公司其它求购信息
	 */
//	public List<Integer> queryBuyListById(Integer cid, Integer max);

	/**
	 * 条件筛选求购信息（分页显示）
	 * 
	 * @param cid
	 *            公司编号
	 * @param pauseStatus
	 *            0：发布/1：暂不发布（null时不对其筛选）
	 * @param overdueStatus
	 *            0：过期/1：未过期（null时不对其筛选）
	 * @param checkStatus0
	 *            ：未审核 1：审核通过 2：审核不通过（null时不对其筛选）
	 * @param page
	 * @return
	 */
//	public PageDto<TradeBuy> pageBuyByConditions(Integer cid,
//			Integer pauseStatus, Integer overdueStatus, Integer checkStatus,
//			PageDto<TradeBuy> page);

	/**
	 * 更新发布状态
	 * 
	 * @param id
	 * @param newStatus
	 * @return
	 */
//	public Integer updatePauseStatusById(Integer id, Integer cid,
//			Integer newStatus);

	/**
	 * 刷新求购信息
	 * 
	 * @param id
	 * @return
	 */
//	public Integer updateRefreshById(Integer id, Integer cid);

	/**
	 * 更新类别
	 * 
	 * @param id
	 * @param category
	 * @return
	 */
//	public Integer updateCategoryById(Integer id, String category, Integer cid);

	/**
	 * 更新求购信息基本信息
	 * 
	 * @param buy
	 * @param id
	 * @return
	 */
//	public Integer updateBaseBuyById(TradeBuy buy, Integer id, Integer cid);

	/**
	 * 搜索
	 * 
	 * @param search
	 * @param page
	 * @return
	 */
//	public PageDto<TradeBuySearchDto> pageBuyBySearch(SearchBuyDto search,
//			PageDto<TradeBuySearchDto> page) throws SolrServerException;

//	public PageDto<TradeBuyDto> pageBuyByCategoryCode(String categoryCode,
//			PageDto<TradeBuyDto> page);

	public Integer updateStatusOfTradeBuy(Integer id, Integer checkStatus);
	
	public Integer querySupplyCount(Integer companyId);

	public PageDto<TradeBuyDto> pageBuyByAdmin(Integer cid, String title,
			Integer checkStatus, PageDto<TradeBuyDto> page);

	/**
	 * 后台管理方法
	 * @param id
	 * @return
	 */
	public String queryCategoryCodeById(Integer id);

	public Integer queryCidById(Integer id);

	public PageDto<TradeBuyDto> pageBuyByCategoryCodeAndTitleAndCheckStatus(
			String categoryCode,Integer cid, PageDto<TradeBuyDto> page, String title,
			Integer checkStatus, Integer delStatus, String gmtPublishStart,
			String gmtPublishEnd,String queryType , String compName,String memberCode,Short recommendType, Integer infoComeFrom, Integer regComeFrom,String checkAdmin);

	public Integer refreshBuy(Integer id);

	public Integer uupdateTradeBuy(TradeBuy buy);

	public Integer updateUnPassCheckStatus(Integer intId,
			Integer intCheckStatus, String admin, String checkRefuse);

	public Integer updateDelStatus(Integer id, Integer delStatus);

	public TradeBuy queryOneBuy(Integer id);

	public boolean updateBuyCategory(Integer id, String categoryCode);

//	public List<TradeBuy> queryRecommendBuy(Integer i);
	
	public Integer updateGmtmodifiedBySvrClose(Integer cid);

//	public Integer updatePhotoCoverById(Integer id, String path, Integer companyId);

//	public List<TradeBuySearchDto> queryMatchBuy(String keywords, Integer pageSize) throws SolrServerException;

	public Integer queryMaxId();
}