package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneCallClickLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneCallClickLogDao;
@Component("phoneCallClickLogDao")
public class PhoneCallClickLogDaoImpl extends BaseDaoSupport implements PhoneCallClickLogDao {
	final static String SQL_FIX = "phoneCallClickLog";
	@Override
	public boolean countLogByBothTel(String callTel, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("callTel", callTel);
		map.put("companyId", companyId);
		Integer i=(Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countLogByBothTel"), map);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Integer insertLog(String callerTel,Integer companyId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("callerTel", callerTel);
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insertLog"), map);
	}
	
	@Override
	public Integer countCallClickFee(Integer companyId){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countCallClickFee"),companyId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneCallClickLog> queryLogByphoneCallClickLog(PhoneCallClickLog phoneCallClickLog,PageDto<PhoneCallClickLog> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneCallClickLog", phoneCallClickLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryLogByphoneCallClickLog"), map);
	}
	
	@Override
	public Integer countLogByphoneCallClickLog(PhoneCallClickLog phoneCallClickLog){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneCallClickLog", phoneCallClickLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countLogByphoneCallClickLog"), map);
	}
	
	@Override
	public Integer sumCallClickFee(PhoneCallClickLog phoneCallClickLog){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneCallClickLog", phoneCallClickLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "sumCallClickFee"), map);
	}
	
	@Override
	public Integer sumCallFee(){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "sumCallFee"));
	}

}
