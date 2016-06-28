package com.ast.ast1949.service.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneLogDto;

/**
 *	author:kongsj
 *	date:2013-7-13
 */
public interface PhoneLogService {
	

	public Integer insert(PhoneLog phoneLog);

	public PageDto<PhoneLog> pageList(PhoneLog phoneLog, PageDto<PhoneLog> page);
	
	public PageDto<PhoneLogDto> pageListByDto(PhoneLog phoneLog, PageDto<PhoneLogDto> page);
	
	public String countBalance(Phone phone);
	
	public PageDto<PhoneLogDto> pageDtoList(PhoneLog phoneLog, PageDto<PhoneLogDto> page);
	
	public List<PhoneLogDto> exportPhoneLog(Integer companyId,PhoneLog phoneLog, PageDto<PhoneLogDto> page);
	
	public PageDto<PhoneLogDto> queryAllFee(PhoneLog phoneLog,PageDto<PhoneLogDto> page);
	
	public PageDto<PhoneLogDto> queryPhoneCost(PhoneLog phoneLog,PageDto<PhoneLogDto> page);
	
	public PageDto<PhoneLogDto> queryCallPhoneRate(PhoneLog phoneLog,PageDto<PhoneLogDto> page);
	
	public PageDto<PhoneLog> queryListByTel(String tel,PageDto<PhoneLog> page);
	
	public PhoneLog queryPhoneLogByCallSn(String callSn);
	/**
	 * 拉黑来电并设置此通来电为0元
	 * @author zhujq
	 * @param ids
	 * @param blackReason
	 * @return
	 */
	public Integer insertPhoneBlackList(String ids,String callers,String checkPerson, String blackReason);
		
}
