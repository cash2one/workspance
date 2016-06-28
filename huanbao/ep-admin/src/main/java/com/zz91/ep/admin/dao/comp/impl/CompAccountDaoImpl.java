package com.zz91.ep.admin.dao.comp.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.comp.CompAccountDao;
import com.zz91.ep.domain.comp.CompAccount;

@Repository("compAccountDao")
public class CompAccountDaoImpl extends BaseDao implements CompAccountDao {

	final static String SQL_PREFIX = "compAccount";
	
	@Override
	public Integer getAccountIdByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getAccountIdByAccount"), account);
	}

//	@Override
//	public Integer getCompanyIdByAccountId(Integer accountId) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getCompanyIdByAccountId"), accountId);
//	}

	@Override
	public CompAccount queryCompAccountByAccount(String account) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompAccountByAccount"), account);
	}

//	@Override
//	public Integer queryCountCompAcountByEmail(String email) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountCompAcountByEmail"), email);
//	}

	@Override
	public CompAccount queryCompAccountByEmail(String email) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompAccountByEmail"), email);
	}

	@Override
	public Integer updateCompAccount(CompAccount compAccount) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompAccount"), compAccount);
	}

//	@Override
//	public Integer queryCountCompAcountByAccount(String account) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountCompAcountByAccount"), account);
//	}

	@Override
	public Integer insertCompAccount(CompAccount compAccount) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompAccount"), compAccount);
	}

	@Override
	public CompAccount queryCompAccountByCid(Integer cid) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompAccountByCid"), cid);
	}

//	@Override
//	public Integer updateAccountPwd(Integer uid, String password,
//			String newPassword) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("uid", uid);
//		try {
//			root.put("password", MD5.encode(password));
//			root.put("newPasswordClear", newPassword);
//			root.put("newPassword", MD5.encode(newPassword));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateAccountPwd"), root);
//	}

	@Override
	public Integer updateBaseCompAccount(CompAccount account) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBaseCompAccount"), account);
	}

	@Override
	public Integer queryCompAccountByMobile(String mobile) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompAccountByMobile"), mobile);
	}

	@Override
	public Integer countUser(String account, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("password", password);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countUser"), root);
	}

	@Override
	public CompAccount queryAccountDetails(String account) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountDetails"), account);
	}

	@Override
	public Integer queryCidByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCidByAccount"), account);
	}

//	@Override
//	public String queryAccountNameByCid(Integer cid) {
//		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountNameByCid"), cid);
//	}

//	@Override
//	public String queryNameByUid(Integer uid) {
//		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameByUid"), uid);
//	}

//	@Override
//	public Integer queryUidByCid(Integer cid) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryUidByCid"), cid);
//	}

//	@Override
//	public Integer queryWeekAddAccount() {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryWeekAddAccount"));
//	}

	@Override
	public Integer updateAccount(CompAccount account) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateAccount"), account);
	}

//	@Override
//	public Integer queryAllAddAccount() {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAllAddAccount"));
//	}

//	@Override
//	public String queryAccountById(Integer uid) {
//		return  (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountById"),uid);
//	}

	@Override
	public Integer countUserByEmail(String email) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countUserByEmail"),email);
	}

//	@Override
//	public Integer generateResetPwdKey(ResetPwd resetPwd) {
//		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "generateResetPwdKey"),resetPwd);
//	}

//	@Override
//	public void deleteResetPwdByKey(String k) {
//		getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteResetPwdByKey"),k);
//	}

//	@Override
//	public ResetPwd listResetPwdByKey(String key) {
//		return (ResetPwd) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "listResetPwdByKey"),key);
//	}

//	@Override
//	public Integer resetPassword(String account, String pwd1) {
//		Map<String, Object> root= new HashMap<String, Object>();
//		root.put("account", account);
//		try {
//			root.put("password", MD5.encode(pwd1));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		root.put("passwordClear", pwd1);
//		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "resetPassword"), root);
//	}

	@Override
	public Integer updateEmailByAccount(String account, String email) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("account", account);
		root.put("email", email);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateEmailByAccount"), root);
	}

	@Override
	public Integer updateEmailToBlank(String email, String tmpEmail) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("tmpEmail", tmpEmail);
		root.put("email", email);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateEmailToBlank"), root);
	}

	@Override
	public Integer countUserByEmailAndAccount(String email, String account) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("account", account);
		root.put("email", email);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countUserByEmailAndAccount"), root);
	}

	@Override
	public CompAccount queryMobileAndPhoneByCid(Integer id) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMobileAndPhoneByCid"), id);
	}

	@Override
	public Integer queryMaxId() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxId"));
	}

	@Override
	public Integer queryLoginCountByCid(Integer cid) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryLoginCountByCid"),cid);
	}

	@Override
	public String queryPasswordClearByCid(Integer cid) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPasswordClearByCid"), cid);
	}

	@Override
	public CompAccount queryAccountByEmail(String email) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountByEmail"), email);
	}
}
