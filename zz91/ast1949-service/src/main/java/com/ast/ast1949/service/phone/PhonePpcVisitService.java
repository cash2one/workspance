package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhonePpcVisit;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhonePpcVisitDto;

public interface PhonePpcVisitService {
	public PageDto<PhonePpcVisitDto> pagePpcVisitList(PhonePpcVisit phonePpcVisit, PageDto<PhonePpcVisitDto> page);


}
