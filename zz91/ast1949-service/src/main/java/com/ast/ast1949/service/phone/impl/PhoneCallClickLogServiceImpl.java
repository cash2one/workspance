package com.ast.ast1949.service.phone.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneCallClickLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.phone.PhoneCallClickLogDao;
import com.ast.ast1949.service.phone.PhoneCallClickLogService;

@Component("phoneCallClickLogService")
public class PhoneCallClickLogServiceImpl implements PhoneCallClickLogService {
	@Resource
	private PhoneCallClickLogDao phoneCallClickLogDao;

	@Override
	public boolean countLogByBothTel(String callTel, Integer companyId) {
		return phoneCallClickLogDao.countLogByBothTel(callTel, companyId);
	}

	@Override
	public Integer insertLog(String callerTel, Integer companyId) {
		return phoneCallClickLogDao.insertLog(callerTel, companyId);
	}

	@Override
	public PageDto<PhoneCallClickLog> pageCallClickList(PhoneCallClickLog phoneCallClickLog, PageDto<PhoneCallClickLog> page) {
		page.setTotalRecords(phoneCallClickLogDao.countLogByphoneCallClickLog(phoneCallClickLog));
		List<PhoneCallClickLog> list=phoneCallClickLogDao.queryLogByphoneCallClickLog(phoneCallClickLog, page);
		PhoneCallClickLog phoneCallClickLogl=new PhoneCallClickLog();
		Integer i=phoneCallClickLogDao.sumCallClickFee(phoneCallClickLog);
		if(i!=null){
			phoneCallClickLogl.setClickFee(Float.valueOf(i));
		}else{
			phoneCallClickLogl.setClickFee(Float.valueOf(0));
		}
		phoneCallClickLogl.setCallerTel("共查看:"+String.valueOf(phoneCallClickLogDao.countLogByphoneCallClickLog(phoneCallClickLog))+"个未接号码");
		list.add(phoneCallClickLogl);
		page.setRecords(list);
		return page;
	}

}
