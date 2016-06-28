package com.ast.ast1949.persist.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotPromotions;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotPromotionsDto;

/**
 *	author:kongsj
 *	date:2013-3-6
 */
public interface SpotPromotionsDao {
	public SpotPromotions queryBySpotId(Integer spotId);
	
	public SpotPromotions queryByIdAndCompanyId(Integer id, Integer companyId);
	
	public Integer insert(SpotPromotions spotPromotions);
	
	public Integer update(SpotPromotions spotPromotions);

	public List<SpotPromotions> queryByCondition(SpotPromotions spotPromotions,PageDto<SpotPromotionsDto> page);

	public Integer queryCountByCondition(SpotPromotions spotPromotions);
	
	public List<SpotPromotions> queryPromotionsBySize(Integer size);
	
	public List<SpotPromotions> queryExpiredPromotionsBySize(Integer size);
	

}
