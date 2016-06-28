package com.ast.ast1949.persist.log.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.log.AuthAdminDao;

@Component("authAdminDao")
public class AuthAdminDaoImpl extends BaseDaoSupport implements AuthAdminDao {
	final static String SQL_FIX = "authAdmin";

	@Override
	public Integer insertAuthAdmin(String account) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insertAuthAdmin"), account);
	}

	@Override
	public Integer countAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "countAccount"), account);
	}

}
