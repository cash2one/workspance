package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhonePpcVisit;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhonePpcVisitDto;

public interface PhonePpcVisitDao {

	public Integer countVisitByTargetId(Integer targetId);

	public List<PhonePpcVisitDto> queryVisitByTargetId(PhonePpcVisit phonePpcVisit, PageDto<PhonePpcVisitDto> page);
	
	public Integer countVisitByBoth(Integer targetId,Integer cid);


}
