/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade;

import java.util.Date;
import java.util.List;

import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;

/**
 * @author totly
 * 
 *         created on 2011-9-13
 */
public interface TradeSupplyDao {

	/**
	 * 查找供应信息及公司基本信息
	 */
//	public TradeSupplyDto queryShortDetailsById(Integer id);

	/**
	 * 插入供应信息
	 */
	public Integer insertTradeSupply(TradeSupply tradeSupply);

	/**
	 * 更新产品图片
	 */
	public Integer updatePhotoCoverById(Integer id, String photoCover);

	/**
	 * 查询供应信息（分页）
	 */
//	public List<TradeSupply> querySupplyByCompany(String categoryCode,
//			Integer group, String keywords, Integer cid,
//			PageDto<TradeSupply> page);

	/**
	 * 根据供应信息ID，查找对应的供应信息及公司详细信息
	 */
//	public TradeSupplyDto queryLongDetailsById(Integer id);

	/**
	 * 根据供应信息ID，查找供应信息及公司详细信息（更新）
	 */
//	public TradeSupplyDto queryUpdateSupplyById(Integer id, Integer cid);

	/**
	 * 更新供应产品的留言数
	 */
	public Integer updateMessageCountById(Integer id);

	/**
	 * 查询供应信息条数
	 */
//	public Integer querySupplyByCompanyCount(String categoryCode,
//			Integer group, String keywords, Integer cid);

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteSupplyById(Integer id);

	/**
	 * 根据不同条件筛选供应信息（分页显示）
	 * 
	 * @param cid
	 *            公司编号
	 * @param pauseStatus
	 *            0：发布/1：暂不发布（null时不对其筛选）
	 * @param checkStatus
	 *            0：未审核 1：审核通过 2：审核不通过（null时不对其筛选）
	 * @param groupId
	 *            分组id（null时不对其筛选）
	 * @param page
	 *            分页内容
	 * @return 分页结果集
	 */
//	public List<TradeSupply> querySupplyByConditions(Integer cid,
//			Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer recommend,
//			Integer groupId, PageDto<TradeSupply> page);

	/**
	 * 根据不同条件筛选供应信息数（分页显示）
	 * 
	 * @param cid
	 *            公司编号
	 * @param pauseStatus
	 *            0：发布/1：暂不发布（null时不对其筛选）
	 * @param checkStatus
	 *            0：未审核 1：审核通过 2：审核不通过（null时不对其筛选）
	 * @param groupId
	 *            分组id（null时不对其筛选）
	 * @param page
	 *            分页内容
	 * @return 分页结果数
	 */
//	public Integer querySupplyByConditionsCount(Integer cid,
//			Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer recommend,
//			Integer groupId);

	/**
	 * 根据一组供应信息编号更新供应信息分组编号
	 * 
	 * @param id
	 * @param gid
	 * @return
	 */
//	public Integer updateSupplyGroupIdById(Integer id, Integer cid, Integer gid);

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
	 * 刷新供应信息
	 * 
	 * @param id
	 * @return
	 */
//	public Integer updateRefreshById(Integer id, Integer cid);

	/**
	 * 更新供应信息类别
	 * 
	 * @param category
	 * @param id
	 * @return
	 */
//	public Integer updateCategoryById(String category,String propertyValue, Integer id, Integer cid);

	/**
	 * 更新供应信息基本信息
	 * 
	 * @param supply
	 * @param id
	 * @return
	 */
//	public Integer updateBaseSupplyById(TradeSupply supply, Integer id,
//			Integer cid);

	public Integer countTradeSupply(Integer cid, Integer uid,
			String categoryCode, String title);

	public Integer updateStatusOfTradeSupply(Integer id, Integer checkStatus);
	
	
//	public Integer querySupplysCount(TradeSupplyDto dto);

	public List<TradeSupplyDto> querySupplyByCategoryCodeAndTitleAndCheckStatus(
			TradeSupplyDto dto, PageDto<TradeSupplyDto> page,String gmtPublishStart, 
			String gmtPublishEnd,String queryType,String memberCode,Short recommendType, String subRecommend,String codeBlock);

	public Integer querySupplysCountByTitleAndCheckStatus(TradeSupplyDto dto,
			PageDto<TradeSupplyDto> page,String gmtPublishStart,
			String gmtPublishEnd,String queryType,String memberCode,Short recommendType, String subRecommend,String codeBlock);

	public Integer querySupplyCount(Integer companyId);

//	public Integer querySupplyCountByCategory(String categoryCode);

	public List<TradeSupplyDto> querySupplyByAdmin(
			PageDto<TradeSupplyDto> page, TradeSupply tradeSupply,Short type);

	public Integer querySupplyByAdminCount(TradeSupply tradeSupply,Short type);

//	public Integer queryAllSupplyCount();

//	public Integer queryWeekSupplyCount();

	public TradeSupply queryOneSupplyById(Integer id);

	public Integer updateTradeSupply(TradeSupply supply);

	public Integer queryCidById(Integer id);

	public Integer updateUnPassCheckStatus(Integer id, Integer checkStatus,
			String checkAdmin, String checkRefuse);

	public Integer updateDelStatus(Integer id, Integer delStatus);

	public String queryCategoryCodeById(Integer id);

	public Integer updateGmtRefresh(Integer id , Date gmtExpired , int validDays);
	
	public Integer updateSupplyCheckStatus(Integer id,Integer checkStatus,
			String check_admin,String check_refuse);

//	public Integer updatePropertyQueryById(Integer id, String properyValue);

	public String queryPropertyQueryById(Integer id);

	public Integer updatePropertyQueryAllById(Integer supplyId, String query);

//	public List<CompProfile> queryCompByKeywords(String keywords, Integer size);

	public Integer updateCategoryCodeById(Integer id, String categoryCode);

	public List<TradeSupply> querySupplyByKeywords(String keywords, Integer size);

//	public List<TradeSupplyDto> querySupplysByKeywords(String keywords,
//			Integer size);

//	public List<TradeSupply> queryRecommendSupply(Integer cid, Integer size);
	
//	public List<TradeSupply> queryRecommendSupplyC(Integer cid, Integer size);
	/**
	 * 根据标签或标题查询供应信息
	 * @param keywords
	 * @param page
	 * @return
	 */
	public List<TradeSupply> querySimplyByKeywords(String keywords,String compName,Integer loginCount);

	/**
	 * 查询最新供应信息(简单查询)
	 * @param parentCode
	 * @param size不是
	 * @return
	 */
//	public List<TradeSupplyDto> queryNewSimplySupply(String parentCode,
//			Integer size);

	/**
	 * 更新信息最后修改时间(服务关闭)
	 * @param cid
	 * @return
	 */
	public Integer updateGmtmodifiedBySvrClose(Integer cid);

	public List<TradeSupply> queryNewestSupply(Integer cid,Integer max);

//	public List<TradeSupply> queryNewestSupply(String category,
//			Integer size);

	public TradeSupply queryPropertyQueryAndCategoryCodeById(Integer id);

	public Integer queryMaxId();

	/**
	 * 查询信息(标王使用)
	 * @param sid
	 * @return
	 */
	public TradeSupply queryShortBwDetailsById(Integer sid);

	/**
	 * @param page
	 * @return
	 */
	public List<TradeSupply> querySupplys(PageDto<TradeSupply> page);

	/**
	 * @return
	 */
	public Integer querySupplysCount();

}