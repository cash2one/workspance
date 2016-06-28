package com.ast.ast1949.persist.auth.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.auth.AuthAutoLogin;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.auth.AuthAutoLoginDao;

@Component("authAutoLoginDao")
public class AuthAutoLoginDaoImpl extends BaseDaoSupport implements AuthAutoLoginDao {

	final static String SQL_FIX = "authAutoLogin";
	
	@Override
	public Integer insert(AuthAutoLogin authAutoLogin) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), authAutoLogin);
	}

	@Override
	public AuthAutoLogin queryByCookie(String cookie) {
		return (AuthAutoLogin) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCookie"), cookie);
	}

	@Override
	public Integer delete(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "delete"), id);
	}

	@Override
	public Integer update(AuthAutoLogin authAutoLogin) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), authAutoLogin);
	}

}
