package com.ast.feiliao91.persist.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.company.CompanyAccountDao;

@Component("companyAccountDao")
public class CompanyAccountDaoImpl extends BaseDaoSupport implements CompanyAccountDao {

	final static String SQL_FIX = "companyAccount";

	@Override
	public Integer insert(CompanyAccount companyAccount) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), companyAccount);
	}

	@Override
	public Integer selectByAccountAndPassword(String account, String mobile, String email, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("mobile", mobile);
		map.put("email", email);
		map.put("password", password);
		return (Integer) getSqlMapClientTemplate()
				.queryForObject(addSqlKeyPreFix(SQL_FIX, "selectByAccountAndPassword"), map);
	}

	@Override
	public Integer countByMobileOrEmail(String account, String email) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account);
		map.put("email", email);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByMobileOrEmail"),
				map);
	}

	@Override
	public CompanyAccount queryByAccount(String account) {
		return (CompanyAccount) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByAccount"),
				account);
	}

	@Override
	public CompanyAccount queryByCompanyIdAndPayPwd(Integer companyId, String payPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("payPassword", payPassword);
		return (CompanyAccount) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCompanyIdAndPayPwd"),map);
	}

	@Override
	public Integer updatePwd(String account, String pwd, String pwdMD5) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("pwd", pwd);
		map.put("pwdMD5", pwdMD5);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updatePwd"), map);
	}
	
	@Override
	public Integer updatePayPwd(String account, String pwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("pwd", pwd);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updatePayPwd"), map);
	}
	
	

	@Override
	public CompanyAccount queryByCompanyId(Integer companyId) {
		return (CompanyAccount) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"),
				companyId);
	}

	@Override
	public Integer updatePhone(String account, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("mobile", phone);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updatePhone"), map);
	}

	@Override
	public Integer updateEmail(String account, String email) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("email", email);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateEmail"), map);
	}

	@Override
	public Integer updateSumMoney(Integer companyId, Float sumMoney) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("sumMoney", sumMoney);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateSumMoney"), map);
	}

	@Override
	public String selectPassWord(Integer companyId) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "selectPassWord"),companyId);
	}
	
	@Override
	public Integer updateGmtLastLogin(Integer companyId){
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateGmtLastLogin"), companyId);
	}

	@Override
	public Integer updateByCompanyAccount(CompanyAccount companyAccount) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateByCompanyAccount"), companyAccount);
	}
}
