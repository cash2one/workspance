package com.ast.feiliao91.persist.auth.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.persist.BaseDaoSupportMultipleDataSource;
import com.ast.feiliao91.persist.auth.AuthUserDao;

@Component("authUserDao")
public class AuthUserDaoImpl extends BaseDaoSupportMultipleDataSource implements AuthUserDao{
	
	final static String SQL_PREFIX="authUser";
	
	@Override
	public String queryAccountByEmail(String email){
		try {
			return (String) getSqlMapClient2().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAccountByEmail"), email);
		} catch (SQLException e) {
		}
//		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAccountByEmail"), email);
		return "";
	}
}
