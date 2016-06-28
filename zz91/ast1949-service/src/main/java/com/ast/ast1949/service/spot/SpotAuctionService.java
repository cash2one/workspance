package com.ast.ast1949.service.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotAuction;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionDto;

/**
 *	author:kongsj
 *	date:2013-3-12
 */
public interface SpotAuctionService {
	
	final static String CHECK_STATUS_NO_CHECK = "0"; // 未审核
	final static String CHECK_STATUS_PASS = "1"; // 审核通过
	final static String CHECK_STATUS_FAILURE = "2"; // 退回
	
	
	/**
	 * 搜索一个竞拍现货
	 * @param spotId
	 * @return
	 */
	public SpotAuction queryBySpotId(Integer spotId);
	
	/**
	 * 信息id搜索
	 * @param spotId
	 * @return
	 */
	public SpotAuction queryById(Integer id);

	
	/**
	 * 申请一个竞拍现货
	 * @param spotAuction
	 * @return
	 */
	public Integer applyForAuction(Integer spotId,Integer productId,Integer companyId);
	
	/**
	 * 后台推荐一个竞拍现货
	 * @param spotAuction
	 * @return
	 */
	public Integer recommendByAdmin(SpotAuction spotAuction);

	/**
	 * 更新一条竞拍现货
	 * @param spotAuction
	 * @return
	 */
	public Integer editForAuction(SpotAuction spotAuction);

	/**
	 * 分页现实现货
	 * @param spotAuction
	 * @param page
	 * @return
	 */
	public PageDto<SpotAuctionDto> pageByCondition(SpotAuction spotAuction,PageDto<SpotAuctionDto> page);
	
	/**
	 * 更新审核状态
	 * @param id
	 * @param status
	 * @return
	 */
	public Integer updateStatusById(Integer id,String status);
	
	
	/**
	 * 检索最新的竞拍现货信息
	 * @param size
	 * @return
	 */
	public List<SpotAuctionDto> queryAuctionBySize(Integer size);
	
	/**
	 * 检索最新 过期的 竞拍现货
	 * @param size
	 * @return
	 */
	public List<SpotAuctionDto> queryExpiredAuctionBySize(Integer size);
	
	
}
