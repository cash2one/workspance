/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.trade;

import java.text.ParseException;
import java.util.List;

import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;

/**
 * @author totly
 * 
 *         created on 2011-9-15
 */
public interface TradeSupplyService {

	public static final int MAX_SIZE = 10;

	public static final int STATUS_PAUSE_YES = 1;

	public static final int STATUS_PAUSE_NO = 0;

	public static final int STATUS_OVERDUE_YES = 0;

	public static final int STATUS_OVERDUE_NO = 1;

	public static final int STATUS_CHECK_UN = 0;

	public static final int STATUS_CHECK_YES = 1;

	public static final int STATUS_CHECK_NO = 2;

	/**
	 * 查找供应信息及公司基本信息（纯文本简介）
	 */
//	public TradeSupplyDto queryShortDetailsById(Integer id);

	/**
	 * 查找供应信息及公司基本信息（富文本简介）
	 */
//	public TradeSupplyDto queryLongDetailsById(Integer id);

	/**
	 * 查找更新供应信息及公司基本信息（富文本简介）
	 */
//	public TradeSupplyDto queryUpdateSupplyById(Integer id, Integer cid);

	/**
	 * 根据一组供应信息ID，查询供应信息的基本信息（标题、uic、cid等）
	 */
//	public List<TradeSupplyDto> querySimpleSupplyByIds(Integer[] id);

	/**
	 * 发布供应信息
	 */
	public Integer createTradeSupply(TradeSupply tradeSupply);

	/**
	 * 根据供应信息ID更新供应信息缩略图（封面图片）
	 */
	public Integer updatePhotoCoverById(Integer id, String photoCover);

//	/**
//	 * 查询供应信息（分页）
//	 */
//	public PageDto<TradeSupply> pageSupplyByCompany(String categoryCode,
//			Integer cid, PageDto<TradeSupply> page);
	
	/**
	 * 查询供应信息（分页）
	 */
//	public PageDto<TradeSupply> pageSupplyByCompany(String categoryCode,String keywords,
//			Integer cid, PageDto<TradeSupply> page);

	/**
	 * 保存供应信息，返回布尔类型
	 * 
	 * @param tradeBuy
	 * @return
	 */
//	public boolean insertTradeSupply(TradeSupply tradeSupply);

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @return
	 */
//	public Integer deleteSupplyById(Integer id);

	/**
	 * 根据不同条件筛选供应信息（分页显示）
	 * 
	 * @param cid
	 *            公司编号
	 * @param pauseStatus
	 *            0：发布/1：暂不发布（null时不对其筛选）
	 * @param overdueStatus
	 *            0：过期/1：未过期（null时不对其筛选）
	 * @param checkStatus
	 *            0：未审核 1：审核通过 2：审核不通过（null时不对其筛选）
	 * @param groupId
	 *            分组id（null时不对其筛选）
	 * @param page
	 *            分页内容
	 * @return 分页结果集
	 */
//	public PageDto<TradeSupply> pageSupplyByConditions(Integer cid,
//			Integer pauseStatus, Integer overdueStatus, Integer checkStatus,
//			Integer groupId, PageDto<TradeSupply> page);

	/**
	 * 根据不同条件筛选供应信息（分页显示）
	 * 
	 * @param cid
	 *            公司编号
	 * @param pauseStatus
	 *            0：发布/1：暂不发布（null时不对其筛选）
	 * @param overdueStatus
	 *            0：过期/1：未过期（null时不对其筛选）
	 * @param checkStatus
	 *            0：未审核 1：审核通过 2：审核不通过（null时不对其筛选）
	 * @param groupId
	 *            分组id（null时不对其筛选）
	 * @param page
	 *            分页内容
	 * @return 分页结果集
	 */
//	public PageDto<TradeSupply> pageSupplyByConditions(Integer cid,
//			Integer pauseStatus, Integer overdueStatus, Integer checkStatus, Integer recommend,
//			Integer groupId, PageDto<TradeSupply> page);
	
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

	/**
	 * TODO 搜索
	 * 
	 * @param search
	 * @param page
	 * @return
	 * @throws SolrServerException 
	 */
//	public PageDto<TradeSupplySearchDto> pageSupplyBySearch(
//			SearchSupplyDto search, PageDto<TradeSupplySearchDto> page) throws SolrServerException;

	public Integer updateStatusOfTradeSupply(Integer id, Integer checkStatus);
	
//	public PageDto<TradeSupplyDto> pageSupplyByCategoryCode(
//			String categoryCode, PageDto<TradeSupplyDto> page);

	public PageDto<TradeSupplyDto> pageSupplyByCategoryCodeAndTitleAndCheckStatus(
			String categoryCode, Integer cid,PageDto<TradeSupplyDto> page, String title,
			Integer checkStatus,Integer delStatus, String codeBlock , Integer infoComeFrom, String gmtPublishStart, 
			String gmtPublishEnd,String queryType,String memberCode,Short recommendType, String compName, String subRecommend,String checkAdmin);

	/**
	 * 查询发布供应信息数
	 * 
	 * @param companyId
	 * @return
	 */
	public Integer querySupplyCount(Integer companyId);

//	public Integer querySupplyCountByCategory(String categoryCode);

	public PageDto<TradeSupplyDto> pageSupplyByAdmin(Integer cid, String title,
			Integer checkStatus, Short type, PageDto<TradeSupplyDto> page);

//	public Integer queryAllSupplyCount();

//	public Integer queryWeekSupplyCount();

	/**
	 * 根据id查询一条记录
	 * @param id
	 * @return
	 */
	public TradeSupply queryOneSupplyById(Integer id);
	/**
	 * 更新供应信息
	 * @param supply
	 * @return
	 */
	public Integer updateTradeSupply(TradeSupply supply);
	/**
	 * 查询公司Id根据id
	 * @param id
	 * @return
	 */
	public Integer queryCidById(Integer id);
	/**
	 * 更新未通过审核的一些信息
	 * @param id 供应ID
	 * @param checkStatus 审核状态
	 * @param checkAdmin 审核人
	 * @param checkRefuse 未通过原因
	 * @return
	 */
	public Integer updateUnPassCheckStatus(Integer id,Integer checkStatus,String checkAdmin,String checkRefuse);
	/**
	 * 更新删除标记
	 * @param id
	 * @param delStatus
	 * @return
	 */
	public Integer updateDelStatus(Integer id,Integer delStatus);
	
	public String queryCategoryCodeById(Integer id);

	/**
	 * 刷新供应信息
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	public Integer refreshSupply(Integer id) throws ParseException;

	/**
	 * 更新专业属性（搜索用）
	 * @param supplyId
	 * @param properyValue
	 */
//	public Integer updatePropertyQueryById(Integer id, String properyValue);
	
	/**
	 * 查询属性值
	 * @param id
	 * @return
	 */
	public String queryPropertyQueryById(Integer id);

	public Integer updatePropertyQueryAllById(Integer supplyId, String query);
	
	/**
	 * 根据发布产品(关键字)查询公司
	 * @param keywords
	 * @param size
	 * @return
	 */
//	public List<CompProfile> queryCompByKeywords(String keywords,Integer size);
	
	/**
	 * 根据发布产品(关键字)查询供应信息
	 * @param keywords
	 * @param size
	 * @return
	 */
//	public List<TradeSupply> querySupplyByKeywords(String keywords,Integer size);
	
	public boolean updateSupplyCategory(Integer id, String categoryCode);

//	public List<TradeSupplyDto> queryCompsByKeywords(String keywords, Integer size);

//	public List<TradeSupply> queryRecommendSupply(Integer cid, Integer size);
//	public List<TradeSupply> queryRecommendSupplyC(Integer cid, Integer size);

//	public PageDto<TradeSupply> pageSupplyByCompany(String category,
//			Integer group, String keywords, Integer cid,
//			PageDto<TradeSupply> page);
	
	/**
	 * 根据标题查询供应信息
	 * @param keywords
	 * @return
	 */
	public List<TradeSupplyDto> querySimpleByKeyword(String keywords,String compName,Integer loginCount);

	/**
	 * 查询最新供应信息(简单查询)
	 * @param parentCode
	 * @param size
	 * @return
	 */
//	public List<TradeSupplyDto> queryNewSimplySupply(String parentCode, Integer size);

	/**
	 * 更新信息最后修改时间(服务关闭)
	 * @param cid
	 * @return
	 */
	public Integer updateGmtmodifiedBySvrClose(Integer cid);

	public List<TradeSupply> queryTopNewestSupply(Integer cid,Integer pageSize);

//	public List<TradeSupply> queryNewestSupply(String category, Integer size);

//	public List<TradeSupplySearchDto> queryMatchSupply(String keywords, Integer pageSize) throws SolrServerException;

	public TradeSupply queryPropertyQueryAndCategoryCodeById(Integer id);

	public Integer queryMaxId();
	
	public TradeKeyword buildTradeKeyword(Integer sid);

	/**
	 * @param page
	 * @return
	 */
	public PageDto<TradeSupply> pageSupplys(PageDto<TradeSupply> page);
	
	public List<TradeSupplyDto> queryCheckRefuse();
}
