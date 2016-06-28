package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;

/**
 * author:kongsj date:2013-7-13
 */
public interface PhoneLogDao {
	public PhoneLog queryByCallSn(String callSn);

	public Integer insert(PhoneLog phoneLog);

	public List<PhoneLog> queryList(PhoneLog phoneLog, PageDto<PhoneLog> page);

	public Integer queryListCount(PhoneLog phoneLog);
	
	public String countCallFee(String tel);
}
