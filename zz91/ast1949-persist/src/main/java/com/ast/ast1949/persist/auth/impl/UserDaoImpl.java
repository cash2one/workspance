/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-16
 */
package com.ast.ast1949.persist.auth.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.auth.AuthForgotPassword;
import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.auth.UserDao;
import com.ast.ast1949.util.Assert;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component("userDao")
public class UserDaoImpl extends BaseDaoSupport implements UserDao {
	
	final static String SQL_PREFIX="authUser";

	public AuthUser queryUserByEmail(String email) {
		Assert.hasText(email, "emain can not be empty");
		return (AuthUser) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryUserByEmail"),email);
	}
	
	public Integer generateForgotPasswordKey(AuthForgotPassword obj) {
		Assert.notNull(obj, "obj can not be null");

		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "generateForgotPasswordKey"), obj);
	}

	public AuthForgotPassword listAuthForgotPasswordByKey(String key) {
		Assert.notNull(key, "key can not be null");
		return (AuthForgotPassword) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "listAuthForgotPasswordByKey"), key);
	}
	
	public void deleteAuthForgotPasswordByKey(String key) {
		Assert.notNull(key, "key can not be null");
		getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteAuthForgotPasswordByKey"), key);
	}
	
	public AuthUser queryUserByUsername(String username) {
		Assert.hasText(username, "username can not be empty");
		return (AuthUser) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryUserByUsername"),username);
	}
	
	public Integer updatePasswordByUsername(String username, String password) {
		Assert.hasText(username, "username can not be empty");
		Assert.hasText(password, "password can not be empty");
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("username", username);
		root.put("password", password);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updatePasswordByUsername"),root);
	}
	
	@Override
	public Integer updateAccount(String oldAccount, String account){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("username", oldAccount);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateAccount"), root);
	}

	@Override
	public Integer countUserByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUserByAccount"), account);
	}

	@Override
	public Integer countUserByEmail(String email) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUserByEmail"), email);
	}

	@Override
	public Integer insertUser(AuthUser user) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertUser"), user);
	}

	@Override
	public Integer updateSteping(Integer id, Integer steping) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("steping", steping);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateSteping"), root);
	}

	@Override
	public Integer countUserByAccountLogin(String username, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("username", username);
		root.put("password", password);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUserByAccountLogin"), root);
	}

	@Override
	public Integer countUserByEmailLogin(String email, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("email", email);
		root.put("password", password);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUserByEmailLogin"), root);
	}

	@Override
	public String queryAccountByEmail(String email) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAccountByEmail"), email);
	}

	@Override
	public String queryPassword(String username) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPassword"), username);
	}

	@Override
	public String validateByAccount(String username, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("username", username);
		root.put("password", password);
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "validateByAccount"), root);
	}
	
	@Override
	public String validateByMobile(String mobile, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("mobile", mobile);
		root.put("password", password);
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "validateByMobile"), root);
	}

	@Override
	public String validateByEmail(String username, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("username", username);
		root.put("password", password);
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "validateByEmail"), root);
	}

	@Override
	public String validateByUsername(String username, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("username", username);
		root.put("password", password);
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "validateByUsername"), root);
	}
	
	@Override
	public Integer deleteById(Integer id){
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteById"), id);
	}

	@Override
	public Integer updateEmail(String username, String email) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("username", username);
		root.put("email", email);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateEmail"),root);
	}

	@Override
	public Integer countUserByMobile(String mobile) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUserByMobile"),mobile);
	}

	@Override
	public AuthUser queryUserByMobile(String mobile) {
		return (AuthUser) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryUserByMobile"),mobile);
	}

	@Override
	public Integer updateMobile(String account, String mobile) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("username", account);
		root.put("mobile", mobile);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateMobile"), root);
	}
	
}
