package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneNumberChangeLog;
import com.ast.ast1949.dto.PageDto;

/**
 * author:kongsj date:2013-7-3
 */
public interface PhoneNumberChangeLogDao {
	public Integer insert(PhoneNumberChangeLog phoneNumberChangeLog);

	public List<PhoneNumberChangeLog> queryByAdmin(PhoneNumberChangeLog phoneNumberChangeLog,PageDto<PhoneNumberChangeLog> page);

	public Integer queryByAdminCount(PhoneNumberChangeLog phoneNumberChangeLog);

	public Integer updateForStatus(Integer id, Integer status);

	public PhoneNumberChangeLog queryById(Integer id);

}
