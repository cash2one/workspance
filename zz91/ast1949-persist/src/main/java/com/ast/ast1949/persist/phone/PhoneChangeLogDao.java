/**
 * @author kongsj
 * @date 2014年8月22日
 * 
 */
package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneChangeLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneChangeLogDto;

public interface PhoneChangeLogDao {
	
	public PhoneChangeLog queryById(Integer id);
	
	public Integer insert(PhoneChangeLog phoneChangeLog);
	
	public Integer update(PhoneChangeLog phoneChangeLog);
	
	public List<PhoneChangeLog> queryList(PhoneChangeLog phoneChangeLog,PageDto<PhoneChangeLog> page);
	
	public Integer queryListCount(PhoneChangeLog phoneChangeLog,String checkStatus);
	
	public List<PhoneChangeLogDto> queryAllPhoneChangeLogs(PageDto<PhoneChangeLogDto> page,String checkStatus);
	
}
