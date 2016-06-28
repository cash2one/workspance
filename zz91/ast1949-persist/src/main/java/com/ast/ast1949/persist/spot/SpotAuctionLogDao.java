package com.ast.ast1949.persist.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotAuctionLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionLogDto;

/**
 *	author:kongsj
 *	date:2013-3-15
 */
public interface SpotAuctionLogDao {
	
	public List<SpotAuctionLog> queryByCondition(SpotAuctionLog spotAuctionLog,PageDto<SpotAuctionLogDto> page);
	
	public Integer queryCountByCondition(SpotAuctionLog spotAuctionLog);
	
	public List<SpotAuctionLog> queryByAuctionIdAndSize(Integer auctionId,Integer size);

	public Integer queryCountByAuctionId(Integer auctionId);
	
	public Integer insert(SpotAuctionLog spotAuctionLog);

	public Integer queryCountByCompanyIdAndAuctionId(Integer companyId,Integer spotAuctionId);
}
