package com.ast.ast1949.persist.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotAuction;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionDto;

/**
 * author:kongsj 
 * date:2013-3-12
 */
public interface SpotAuctionDao {
	
	public SpotAuction queryBySpotId(Integer spotId);

	public Integer insert(SpotAuction spotAuction);

	public Integer update(SpotAuction spotAuction);

	public List<SpotAuction> queryByCondition(SpotAuction spotAuction,PageDto<SpotAuctionDto> page);

	public Integer queryCountByCondition(SpotAuction spotAuction);
	
	public Integer updateStatusById(Integer id,String status);
	
	public List<SpotAuction> queryAuctionBySize(Integer size);
	
	public List<SpotAuction> queryExpiredAuctionBySize(Integer size);
	
	public SpotAuction queryById(Integer id);

}