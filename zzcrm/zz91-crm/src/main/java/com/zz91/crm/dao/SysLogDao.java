package com.zz91.crm.dao;

import java.util.List;

import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-1-6 
 */
public interface SysLogDao {
	
	/**
	 * 创建一条新日志信息
	 * @param log
	 * @return
	 */
	public Integer insertSysLog(SysLog log);
	
	/**
	 * 查询日志信息
	 * @param id (目标ID)
	 * @param page
	 * @return
	 */
	public List<SysLog> querySysLog(Integer id,String saleName,String operation,PageDto<SysLog> page);
	
	/**
	 * 查看日志数量
	 * @param id
	 * @param page
	 * @return
	 */
	public Integer querySysLogCount(Integer id,String saleName,String operation,PageDto<SysLog> page);
}
