package com.ast.ast1949.persist.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotOrder;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotOrderDto;

/**
 * author:kongsj date:2013-3-25
 */
public interface SpotOrderDao {
	public SpotOrder queryById(Integer id);

	public List<SpotOrder> queryByCondition(SpotOrder spotOrder, PageDto<SpotOrderDto> page);

	public Integer queryCountByCondition(SpotOrder spotOrder);

	public Integer insert(SpotOrder spotOrder);

	public Integer updateByStatusAndId(String orderStatus, Integer id,Integer companyId);

	public Integer deleteOrderById(Integer id,Integer companyId);
	
	public Integer validateCart(Integer companyId,Integer spotId);

	public Integer batchDelete(Integer[] stringToIntegerArray, Integer companyId);

	public Integer batchUpdateByStatusAndId(String orderStatus, Integer[] ids,Integer companyId);
	
	public Integer countBySpotId(Integer spotId);
	
	public List<SpotOrder> queryOrder(Integer size);
}
