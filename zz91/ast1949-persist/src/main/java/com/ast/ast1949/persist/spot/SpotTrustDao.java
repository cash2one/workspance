package com.ast.ast1949.persist.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotTrust;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotTrustDto;

/**
 * author:kongsj date:2013-5-20
 */
public interface SpotTrustDao {
	public SpotTrust queryById(Integer id);

	public Integer insert(SpotTrust spotTrust);

	public Integer update(SpotTrust spotTrust);

	public List<SpotTrust> queryListForFront(Integer start,Integer size);
	
	public List<SpotTrust> queryList(SpotTrustDto spotTrustDto,PageDto<SpotTrustDto> page);
	
	public Integer queryListCount(SpotTrustDto spotTrustDto);

	public Integer updateForChecked(String isChecked, Integer id);

	public Integer updateForDelete(String isDelete, Integer id);
}
