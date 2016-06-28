package com.ast.ast1949.persist.auth.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.auth.AuthUserBlockLogDao;

@Component("authUserBlockLogDao")
public class AuthUserBlockLogDaoImpl extends BaseDaoSupport implements AuthUserBlockLogDao{

	final static String SQL_FIX = "authUserBlockLog";
	
	@Override
	public Integer insert(Integer companyId, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("mobile", mobile);
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), map);
	}
	
}
