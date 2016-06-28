package com.ast.ast1949.service.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotAuctionLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionLogDto;

/**
 *	author:kongsj
 *	date:2013-3-15
 */
public interface SpotAuctionLogService {
	/**
	 * 分页获取 竞拍小计的数据
	 * @param spotAuctionLog
	 * @param page
	 * @return
	 */
	public PageDto<SpotAuctionLogDto> pageAuctionLog(SpotAuctionLog spotAuctionLog,PageDto<SpotAuctionLogDto> page);
	
	/**
	 * 统计竞拍 小计条数
	 * @param auctionId
	 * @return
	 */
	public Integer queryCountByAuctionId(Integer auctionId);
	
	/**
	 * 获取竞拍小计 
	 */
	public List<SpotAuctionLogDto> queryByAuctionIdAndSize(Integer auctionId,Integer size);
	
	/**
	 * 新增一条新竞拍小计
	 */
	public Integer insert(SpotAuctionLog spotAuctionLog);
}
