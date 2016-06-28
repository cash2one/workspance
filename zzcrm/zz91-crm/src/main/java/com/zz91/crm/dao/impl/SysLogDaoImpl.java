package com.zz91.crm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.SysLogDao;
import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-1-6 
 */
@Component("sysLogDao")
public class SysLogDaoImpl extends BaseDao implements SysLogDao {
	
	final static String SQL_PREFIX="sysLog";

	@Override
	public Integer insertSysLog(SysLog log) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertSysLog"), log);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysLog> querySysLog(Integer id,String saleName,String operation,PageDto<SysLog> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("saleName", saleName);
		root.put("operation", operation);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySysLog"), root);
	}

	@Override
	public Integer querySysLogCount(Integer id,String saleName,String operation,PageDto<SysLog> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("saleName", saleName);
		root.put("operation", operation);
		root.put("page", page);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySysLogCount"), root);
	}

}
