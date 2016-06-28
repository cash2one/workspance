package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.dto.PageDto;

/**
 *	author:kongsj
 *	date:2013-7-16
 */
public interface PhoneClickLogService {
	
	public PhoneClickLog queryById(Integer companyId,Integer targetId);
	

	public Integer insert(PhoneClickLog phoneClickLog);

	public PageDto<PhoneClickLog> pageList(PhoneClickLog phoneClickLog, PageDto<PhoneClickLog> page);
	
	public Integer countClick(Integer companyId);
	
	public PageDto<PhoneClickLog> pageDtoList(PhoneClickLog phoneClickLog, PageDto<PhoneClickLog> page);
	
	public Integer countById(Integer companyId,Integer targetId);

	
}
