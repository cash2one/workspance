/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade;

import java.util.Date;
import java.util.List;

import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
public interface TradeBuyDao {

    /**
     * 插入求购信息
     */
    public Integer insertTradeBuy(TradeBuy tradeBuy);

    /**
     * 根据求购信息Id查询求购信息
     */
//    public TradeBuy queryBuyDetailsById(Integer id);

    /**
     * 根据求购信息Id查询求购基本信息
     */
//    public TradeBuy queryBuySimpleDetailsById(Integer id);

    /**
     * 根据产品类别，查询更多同类求购信息（按更新时间排序）
     */
//    public List<TradeBuy> queryCommonBuyByCategory(String categoryCode, Integer max);

    /**
     * 根据产品类别，查找该类别或所有子类别的最新求购信息（按更新时间排序）
     * category：产品类别，如果为null则查找全部求购信息的最新的信息
     * isDirect：true 查询全部子类，false 仅自己（默认）
     * count：查询条数
     */
//    public List<TradeBuy> queryNewestBuyByCategory(String category, Boolean isDirect, Integer max);

    /**
     * 查找同一家公司其它求购信息
     */
//    public List<Integer> queryBuyListById(Integer cid, Integer max);

    /**
     * 更新求购产品的留言数 
     */
    public Integer updateMessageCountById(Integer id);

    /**
     * 条件筛选求购信息（分页显示）
     * @param cid 公司编号
     * @param pauseStatus 0：发布/1：暂不发布（null时不对其筛选）
     * @param overdueStatus 0：过期/1：未过期（null时不对其筛选）
     * @param checkStatus0：未审核 1：审核通过 2：审核不通过（null时不对其筛选）
     * @param page
     * @return
     */
//    public List<TradeBuy> queryBuyByConditions(Integer cid, Integer pauseStatus,Integer overdueStatus, Integer checkStatus, PageDto<TradeBuy> page);

    /**
     * 条件筛选求购信息（分页显示）
     * @param cid 公司编号
     * @param pauseStatus 0：发布/1：暂不发布（null时不对其筛选）
     * @param overdueStatus 0：过期/1：未过期（null时不对其筛选）
     * @param checkStatus0：未审核 1：审核通过 2：审核不通过（null时不对其筛选）
     * @return
     */
//    public Integer queryBuyByConditionsCount(Integer cid, Integer pauseStatus, Integer overdueStatus, Integer checkStatus);

    /**
     * 更新发布状态
     * @param id
     * @param newStatus
     * @return
     */
//    public Integer updatePauseStatusById(Integer id, Integer cid, Integer newStatus);

    /**
     * 刷新求购信息
     * @param id
     * @return
     */
//    public Integer updateRefreshById(Integer id, Integer cid);
    
    /**
     * 修改审核状态
     * @param id
     * @param checkStatus
     * @param check_admin
     * @param check_refuse
     * @return
     */
    public Integer updateBuyCheckStatus(Integer id,Integer checkStatus,
			String check_admin,String check_refuse);
    
    /**
     * 更新类别
     * @param id
     * @param category
     * @return
     */
//    public Integer updateCategoryById(Integer id, String category, Integer cid);
    
    /**
     * 更新求购信息基本信息
     * @param buy
     * @param id
     * @return
     */
//    public Integer updateBaseBuyById(TradeBuy buy, Integer id, Integer cid);

    /**
     * 根据ID删除
     * @param id
     */
	public Integer deleteBuyById(Integer id);

	/**
	 * 更新时查询求购信息
	 * @param id
	 * @param cid
	 * @return
	 */
//	public TradeBuy queryUpdateBuyById(Integer id, Integer cid);

//	public List<TradeBuyDto> queryBuyByCategoryCode(TradeBuyDto dto,
//			PageDto<TradeBuyDto> page);

//	public Integer queryBuyCount(TradeBuyDto dto);

	public Integer updateStatusOfTradeBuy(Integer id, Integer checkStatus);
	
	public Integer querySupplyCount(Integer companyId);
	
	public List<TradeBuyDto> queryCompBuyByAdmin(TradeBuy tradeBuy,PageDto<TradeBuyDto> page);
	
	public Integer queryCompBuyByAdminCount(TradeBuy tradeBuy);

	public List<TradeBuyDto> queryBuyByCategoryCodeAndTitleAndCheckStatus(
			TradeBuyDto dto, PageDto<TradeBuyDto> page, String gmtPublishStart,
			String gmtPublishEnd,String queryType,Short recommendType, Integer infoComeFrom, Integer regComeFrom);

	public Integer queryBuysCountByTitleAndCheckStatus(
			TradeBuyDto dto, PageDto<TradeBuyDto> page, String gmtPublishStart,
			String gmtPublishEnd,String queryType,Short recommendType, Integer infoComeFrom, Integer regComeFrom);

	public Integer updateDelStatus(Integer id, Integer delStatus);

	public Integer updateGmtRefresh(Integer id , Date gmtExpired , int validDays);

	public TradeBuy queryOneBuy(Integer id);

	public Integer queryCidById(Integer id);

	public Integer updateUnPassCheckStatus(Integer intId,
			Integer intCheckStatus, String admin, String checkRefuse);

	public Integer updateTradeBuy(TradeBuy buy);

	public Integer updateCategoryCodeById(Integer id, String categoryCode);

//	public List<TradeBuy> queryRecommendBuy(Integer size);

	public Integer countTradeBuy(Integer cid, Integer uid, String categoryCode,
			String title);

	/**
	 * 更新信息最后修改时间(服务关闭)
	 * @param cid
	 * @return
	 */
	public Integer updateGmtmodifiedBySvrClose(Integer cid);

//	public Integer updatePhotoCoverById(Integer id, String path, Integer cid);

	public Integer queryMaxId();

	/**
	 * @param page
	 * @return
	 */
	public List<TradeBuy> queryBuys(PageDto<TradeBuy> page);

	/**
	 * @return
	 */
	public Integer queryBuysCount();
}