package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;

/**
 *	author:kongsj
 *	date:2013-7-13
 */
public interface PhoneLogService {
	

	public Integer insert(PhoneLog phoneLog);

	public PageDto<PhoneLog> pageList(PhoneLog phoneLog, PageDto<PhoneLog> page);
	
	public String countBalance(Phone phone);

}
