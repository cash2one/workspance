package com.zz91.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.crm.dao.SysLogDao;
import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.service.SysLogService;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-1-6 
 */
@Component("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	
	final static Integer SIZE=20;
	@Resource
	private SysLogDao sysLogDao;
	
	@Override
	public Integer createSysLog(SysLog log) {
		return sysLogDao.insertSysLog(log);
	}

	@Override
	public PageDto<SysLog> pageByTargetId(Integer id,String saleName,String operation,PageDto<SysLog> page) {
		if (page.getLimit()==null){
			page.setLimit(SIZE);
		}
		if (page.getSort()==null){
			page.setSort("gmt_modified");
			page.setDir("desc");
		}
		page.setRecords(sysLogDao.querySysLog(id,saleName,operation,page));
		page.setTotals(sysLogDao.querySysLogCount(id,saleName,operation,page));
		return page;
	}

}
