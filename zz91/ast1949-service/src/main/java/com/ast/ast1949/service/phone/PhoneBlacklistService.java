package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhoneBlacklist;
import com.ast.ast1949.dto.PageDto;


public interface PhoneBlacklistService {
	
	public PageDto<PhoneBlacklist> page(PhoneBlacklist phoneBlacklist,PageDto<PhoneBlacklist> page);
	
	public Integer insert(PhoneBlacklist phoneBlacklist);
	
	public PhoneBlacklist queryById(Integer id);

	public Integer batchInsert(String phone);
	
	public Integer batchDelete(String ids);

}
