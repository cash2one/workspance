package com.zz91.sms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.sms.dao.BaseDaoSupport;
import com.zz91.sms.dao.GatewayDao;
import com.zz91.sms.domain.Gateway;

@Component("gatewayDao")
public class GatewayDaoImpl extends BaseDaoSupport implements GatewayDao {
	
	final static String SQL_PREFIX = "gateway";
	
	@Override
	public Integer delete(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "delete"), id);
	}

	@Override
	public Integer insert(Gateway gateway) {
		return (Integer)getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"), gateway);
	}

	@Override
	public Integer update(Gateway gateway) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "update"), gateway);
	}

	@Override
	public Integer updateEnabled(Integer id, Integer status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", status);
		param.put("id", id);
		
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateEnabled"), param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Gateway> query(Integer enabled) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enabled",enabled);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "query"), map);
	}

	@Override
	public Gateway queryOne(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (Gateway)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryOne"), map);
	}

}
