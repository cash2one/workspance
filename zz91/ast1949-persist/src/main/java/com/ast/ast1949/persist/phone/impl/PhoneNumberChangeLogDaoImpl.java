package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneNumberChangeLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneNumberChangeLogDao;

@Component("phoneNumberChangeLogDao")
public class PhoneNumberChangeLogDaoImpl extends BaseDaoSupport implements PhoneNumberChangeLogDao{

	final static String SQL_FIX = "phoneNumberChangeLog";
	
	@Override
	public PhoneNumberChangeLog queryById(Integer id) {
		return (PhoneNumberChangeLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}
	
	@Override
	public Integer insert(PhoneNumberChangeLog phoneNumberChangeLog) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), phoneNumberChangeLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneNumberChangeLog> queryByAdmin(PhoneNumberChangeLog phoneNumberChangeLog,
			PageDto<PhoneNumberChangeLog> page) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("search", phoneNumberChangeLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByAdmin"), map);
	}

	@Override
	public Integer queryByAdminCount(PhoneNumberChangeLog phoneNumberChangeLog) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("search", phoneNumberChangeLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByAdminCount"), map);
	}
	
	@Override
	public Integer updateForStatus(Integer id,Integer status){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateForStatus"), map);
	}
}
