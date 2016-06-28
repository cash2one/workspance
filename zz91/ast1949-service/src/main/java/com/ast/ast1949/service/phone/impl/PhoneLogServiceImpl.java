package com.ast.ast1949.service.phone.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.phone.PhoneClickLogDao;
import com.ast.ast1949.persist.phone.PhoneLogDao;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.zz91.util.lang.StringUtils;

/**
 *	author:kongsj
 *	date:2013-7-13
 */
@Component("phoneLogService")
public class PhoneLogServiceImpl implements PhoneLogService{

	@Resource
	private PhoneLogDao phoneLogDao;
	@Resource
	private PhoneClickLogDao phoneClickLogDao;
	@Resource
	private PhoneService phoneService;
	
	@Override
	public Integer insert(PhoneLog phoneLog) {
		if(StringUtils.isEmpty(phoneLog.getCallSn())){
			return 0;
		}
		PhoneLog obj = phoneLogDao.queryByCallSn(phoneLog.getCallSn());
		if(obj!=null){
			return 0;
		}
		return phoneLogDao.insert(phoneLog);
	}

	@Override
	public PageDto<PhoneLog> pageList(PhoneLog phoneLog, PageDto<PhoneLog> page) {
		page.setTotalRecords(phoneLogDao.queryListCount(phoneLog));
		page.setRecords(phoneLogDao.queryList(phoneLog, page));
		return page;
	}

	@Override
	public String countBalance(Phone phone) {
		if(phone==null){
			return "0";
		}
		if(StringUtils.isEmpty(phone.getTel())&&phone.getCompanyId()!=null){
			phone = phoneService.queryByCompanyId(phone.getCompanyId());
		}
		if(phone==null){
			return "0";
		}
		Float total = Float.valueOf(phone.getAmount());
		String callFee = phoneLogDao.countCallFee(phone.getTel());
		if(callFee==null||StringUtils.isEmpty(callFee)){
			callFee = "0";
		}
		Float fee = Float.valueOf(callFee);
		Integer i = phoneClickLogDao.countClick(phone.getCompanyId());
		if(i==null){
			i= 0; 
		}
		Float feeClick = Float.valueOf(i);
		return String.valueOf(total-fee-feeClick);
	}

}
