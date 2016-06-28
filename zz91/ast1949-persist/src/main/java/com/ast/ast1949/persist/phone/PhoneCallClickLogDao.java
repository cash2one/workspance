package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneCallClickLog;
import com.ast.ast1949.dto.PageDto;

public interface PhoneCallClickLogDao {
	public boolean countLogByBothTel(String callTel, Integer companyId);

	public Integer insertLog(String callerTel, Integer companyId);

	public Integer countCallClickFee(Integer companyId);
	
	public List<PhoneCallClickLog> queryLogByphoneCallClickLog(PhoneCallClickLog phoneCallClickLog,PageDto<PhoneCallClickLog> page);
	
	public Integer countLogByphoneCallClickLog(PhoneCallClickLog phoneCallClickLog);
	
	public Integer sumCallClickFee(PhoneCallClickLog phoneCallClickLog);
	
	public Integer sumCallFee();

}
