package com.ast.ast1949.persist.sample.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.ContactClickLog;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.ContactClickLogDao;

@Component("contactClickLogDao")
public class ContactClickLogDaoImpl extends BaseDaoSupport implements ContactClickLogDao {

	final static String SQL_FIX = "contactClickLog";

	@Override
	public Integer insert(ContactClickLog contactClickLog) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), contactClickLog);
	}

	@Override
	public ContactClickLog queryById(Integer companyId, Integer targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("targetId", targetId);
		return (ContactClickLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), map);
	}
	
	@Override
	public Integer countClick(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countClick"), companyId);
	}
	
}
