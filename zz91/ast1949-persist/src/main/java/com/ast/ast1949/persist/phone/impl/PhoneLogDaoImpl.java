package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneLogDao;

/**
 * author:kongsj date:2013-7-13
 */
@Component("phoneLogDao")
public class PhoneLogDaoImpl extends BaseDaoSupport implements PhoneLogDao {

	final static String SQL_FIX = "phoneLog";

	@Override
	public Integer insert(PhoneLog phoneLog) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), phoneLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneLog> queryList(PhoneLog phoneLog, PageDto<PhoneLog> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(PhoneLog phoneLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}

	@Override
	public PhoneLog queryByCallSn(String callSn) {
		return (PhoneLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCallSn"), callSn);
	}

	@Override
	public String countCallFee(String tel) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countCallFee"), tel);
	}
}
