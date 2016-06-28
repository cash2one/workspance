package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.dto.PageDto;

/**
 *	author:kongsj
 *	date:2013-7-16
 */
public interface PhoneClickLogDao {
	
	public PhoneClickLog queryById(Integer companyId,Integer targetId);

	public Integer insert(PhoneClickLog phoneClickLog);

	public List<PhoneClickLog> queryList(PhoneClickLog phoneClickLog, PageDto<PhoneClickLog> page);

	public Integer queryListCount(PhoneClickLog phoneClickLog);
	
	public Integer countClick(Integer companyId);
	
	public Integer	queryCountClick(PhoneClickLog phoneClickLog);
	
	public Integer countAllClick();
	
	public Integer countById(Integer companyId,Integer targetId);
}
