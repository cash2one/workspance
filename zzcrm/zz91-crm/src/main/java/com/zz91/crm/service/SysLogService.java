package com.zz91.crm.service;

import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-1-6 
 */
public interface SysLogService {
	
	/**
	 * 创建一条新记录
	 * @param log
	 * @return
	 */
	public Integer createSysLog(SysLog log);
	
	/**
	 * 查询日志信息
	 * @param id (目标Id)
	 * @param page
	 * @return
	 */
	public PageDto<SysLog> pageByTargetId(Integer id,String saleName,String operation,PageDto<SysLog> page);
}
