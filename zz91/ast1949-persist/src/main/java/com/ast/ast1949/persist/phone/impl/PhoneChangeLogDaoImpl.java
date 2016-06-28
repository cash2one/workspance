/**
 * @author kongsj
 * @date 2014年8月22日
 * 
 */
package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneChangeLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneChangeLogDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneChangeLogDao;

@Component("phoneChangeLogDao")
public class PhoneChangeLogDaoImpl extends BaseDaoSupport implements PhoneChangeLogDao {

	final static String SQL_FIX = "phoneChangeLog";
	
	@Override
	public PhoneChangeLog queryById(Integer id) {
		return (PhoneChangeLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public Integer insert(PhoneChangeLog phoneChangeLog) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), phoneChangeLog);
	}

	@Override
	public Integer update(PhoneChangeLog phoneChangeLog) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), phoneChangeLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneChangeLog> queryList(PhoneChangeLog phoneChangeLog,PageDto<PhoneChangeLog> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneChangeLog", phoneChangeLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(PhoneChangeLog phoneChangeLog,String checkStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneChangeLog", phoneChangeLog);
		map.put("checkStatus", checkStatus);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneChangeLogDto> queryAllPhoneChangeLogs(PageDto<PhoneChangeLogDto> page,String checkStatus){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("page", page);
		map.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryAllPhoneChangeLogs"), map);
	}

}
