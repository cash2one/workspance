package com.ast.ast1949.service.phone.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.phone.PhoneClickLogDao;
import com.ast.ast1949.service.phone.PhoneClickLogService;

/**
 *	author:kongsj
 *	date:2013-7-16
 */
@Component("phoneClickLogService")
public class PhoneClickLogServiceImpl implements PhoneClickLogService{

	@Resource
	private PhoneClickLogDao phoneClickLogDao;
	@Resource
	private CompanyDAO companyDAO;
	
	@Override
	public Integer countClick(Integer companyId) {
		if(companyId==null){
			return 0;
		}
		return phoneClickLogDao.countClick(companyId);
	}

	@Override
	public Integer insert(PhoneClickLog phoneClickLog) {
		if(phoneClickLog.getCompanyId()==null||phoneClickLog.getTargetId()==null){
			return 0;
		}
		PhoneClickLog obj =  phoneClickLogDao.queryById(phoneClickLog.getCompanyId(), phoneClickLog.getTargetId());
		if(obj!=null){
			return 0;
		}
		return phoneClickLogDao.insert(phoneClickLog);
	}

	@Override
	public PageDto<PhoneClickLog> pageList(PhoneClickLog phoneClickLog,
			PageDto<PhoneClickLog> page) {
		page.setTotalRecords(phoneClickLogDao.queryListCount(phoneClickLog));
		List<PhoneClickLog> list = phoneClickLogDao.queryList(phoneClickLog, page);
		for(PhoneClickLog obj:list){
			obj.setName(companyDAO.queryCompanyNameById(obj.getTargetId()));
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public PhoneClickLog queryById(Integer companyId, Integer targetId) {
		if(companyId==null||targetId==null){
			return null;
		}
		return phoneClickLogDao.queryById(companyId, targetId);
	}

}
