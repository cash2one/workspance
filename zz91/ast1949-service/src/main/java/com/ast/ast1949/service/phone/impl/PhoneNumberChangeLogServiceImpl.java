package com.ast.ast1949.service.phone.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneNumberChangeLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.phone.PhoneNumberChangeLogDao;
import com.ast.ast1949.service.phone.PhoneNumberChangeLogService;

@Component("phoneNumberChangeLogService")
public class PhoneNumberChangeLogServiceImpl implements PhoneNumberChangeLogService{

	@Resource
	private PhoneNumberChangeLogDao phoneNumberChangeLogDao;
	
	@Override
	public Integer insert(String telFrom,String telTo,String operator) {
		PhoneNumberChangeLog phoneNumberChangeLog = new PhoneNumberChangeLog();
		phoneNumberChangeLog.setTelFrom(telFrom);
		phoneNumberChangeLog.setTelTo(telTo);
		phoneNumberChangeLog.setOperator(operator);
		phoneNumberChangeLog.setStatus(STATUS_WAIT);
		return phoneNumberChangeLogDao.insert(phoneNumberChangeLog);
	}

	@Override
	public PageDto<PhoneNumberChangeLog> pageByAdmin(PhoneNumberChangeLog phoneNumberChangeLog,PageDto<PhoneNumberChangeLog> page) {
		page.setTotalRecords(phoneNumberChangeLogDao.queryByAdminCount(phoneNumberChangeLog));
		page.setRecords(phoneNumberChangeLogDao.queryByAdmin(phoneNumberChangeLog, page));
		return page;
	}

	@Override
	public Integer start(Integer id) {
		if (id==null||id<1) {
			return 0;
		}
		PhoneNumberChangeLog obj = phoneNumberChangeLogDao.queryById(id);
		if (obj==null||obj.getStatus()!=0) {
			return 0;
		}
		return phoneNumberChangeLogDao.updateForStatus(id, STATUS_START);
	}

}
