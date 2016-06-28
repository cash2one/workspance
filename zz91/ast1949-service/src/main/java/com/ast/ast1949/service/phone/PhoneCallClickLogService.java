package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhoneCallClickLog;
import com.ast.ast1949.dto.PageDto;

public interface PhoneCallClickLogService {
	
	public boolean countLogByBothTel(String callTel, Integer companyId);

	public Integer insertLog(String callerTel, Integer companyId);
	 
	public PageDto<PhoneCallClickLog> pageCallClickList(PhoneCallClickLog phoneCallClickLog, PageDto<PhoneCallClickLog> page);

}
