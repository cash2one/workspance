package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneBlacklist;
import com.ast.ast1949.dto.PageDto;

public interface PhoneBlacklistDao {
	
	public Integer insert(PhoneBlacklist phoneBlacklist);
	
	public List<PhoneBlacklist> query(PhoneBlacklist phoneBlacklist,PageDto<PhoneBlacklist> page);
	
	public Integer queryCount(PhoneBlacklist phoneBlacklist);
	
	public PhoneBlacklist queryById(Integer id);

	public Integer batchInsert(List<PhoneBlacklist> list);

	public Integer batchDelete(String[] ids);

	public Integer deleteById(Integer id);

	public Integer isExistByPhone(String phone,Integer phoneLogId);
}
