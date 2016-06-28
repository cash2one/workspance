package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhoneNumberChangeLog;
import com.ast.ast1949.dto.PageDto;

/**
 *	author:kongsj
 *	date:2013-7-3
 */
public interface PhoneNumberChangeLogService {
	
	final static Integer STATUS_WAIT = 0;
	final static Integer STATUS_START = 1;
	final static Integer STATUS_END = 2;
	
	
	public Integer insert(String telFrom,String telTo,String operator);
	
	public PageDto<PhoneNumberChangeLog> pageByAdmin(PhoneNumberChangeLog phoneNumberChangeLog,PageDto<PhoneNumberChangeLog> page);
	
	public Integer start(Integer id);

}
