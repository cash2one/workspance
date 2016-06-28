package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneClickLogDao;

/**
 *	author:kongsj
 *	date:2013-7-16
 */
@Component("phoneClickLogDao")
public class PhoneClickLogDaoImpl extends BaseDaoSupport implements PhoneClickLogDao{

	final static String SQL_FIX = "phoneClickLog";
	
	@Override
	public Integer countClick(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countClick"), companyId);
	}

	@Override
	public Integer insert(PhoneClickLog phoneClickLog) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), phoneClickLog);
	}

	@Override
	public PhoneClickLog queryById(Integer companyId, Integer targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("targetId", targetId);
		return (PhoneClickLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneClickLog> queryList(PhoneClickLog phoneClickLog,
			PageDto<PhoneClickLog> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneClickLog", phoneClickLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(PhoneClickLog phoneClickLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneClickLog", phoneClickLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}

	@Override
	public Integer queryCountClick(PhoneClickLog phoneClickLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneClickLog", phoneClickLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountClick"),map);
	}

	@Override
	public Integer countAllClick() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countAllClick"));
	}
	@Override
	public Integer countById(Integer companyId,Integer targetId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("targetId", targetId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countById"),map);
	}


}
