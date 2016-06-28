/*
 * 文件名称：CompAccountDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.comp.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.CompAccountDao;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.ResetPwd;
import com.zz91.util.encrypt.MD5;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：公司帐号信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("compAccountDao")
public class CompAccountDaoImpl extends BaseDao implements CompAccountDao {

	final static String SQL_PREFIX="compaccount";

	@Override
	public CompAccount queryCompAccountByEmail(String emailOrAccount) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompAccountByEmail"), emailOrAccount);
	}

	@Override
	public CompAccount queryCompAccountByAccount(String emailOrAccount) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompAccountByAccount"), emailOrAccount);
	}

	@Override
	public Integer insertResetPwd(ResetPwd resetPwd) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertResetPwd"),resetPwd);
	}

	@Override
	public ResetPwd listResetPwdByKey(String key) {
		return (ResetPwd) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "listResetPwdByKey"),key);
	}

	@Override
	public Integer resetPassword(String account, String newPwd) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("account", account);
		try {
			root.put("password", MD5.encode(newPwd));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		root.put("passwordClear", newPwd);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "resetPassword"), root);
	}

	@Override
	public void deleteResetPwdByKey(String k) {
		getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteResetPwdByKey"),k);
	}

	@Override
	public Integer queryCountCompAcountByEmail(String email) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountCompAcountByEmail"), email);
	}
	@Override
	public Integer getAccountIdByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getAccountIdByAccount"), account);
	}
	
	public Integer insertCompAccount(CompAccount compAccount) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompAccount"), compAccount);
	}
	
	@Override
	public Integer updateBaseCompAccount(CompAccount account) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBaseCompAccount"), account);
	}

	@Override
	public CompAccount queryCompAccountByCid(Integer cid) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompAccountByCid"), cid);
	}

	@Override
	public Integer differenceTime(Integer id) {
		
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "differenceTime"),id);
	}
	
	@Override
	public String queryAccountById(Integer uid) {
		return  (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountById"),uid);
	}
	
	@Override
	public CompAccount queryAccountDetails(String account) {
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountDetails"), account);
	}

	@Override
	public Integer updateAccountPwd(Integer uid, String password,
			String newPassword) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("uid", uid);
		try {
			root.put("password", MD5.encode(password));
			root.put("newPasswordClear", newPassword);
			root.put("newPassword", MD5.encode(newPassword));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateAccountPwd"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompAccount> queryImpCompAccount(Integer maxId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryImpCompAccount"),maxId);
	}

	@Override
	public Integer countUser(String account, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("password", password);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countUser"), root);
	}

	@Override
	public Integer updateCompAccount(CompAccount compAccount) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompAccount"), compAccount);
	}

	@Override
	public Integer validateByEmail(String username, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account",username);
		map.put("password", password);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "validateByEmail"),map);
	}


	@Override
	public Integer validateByAccount(String username, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account",username);
		map.put("password", password);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "validateByAccount"),map);
	}

	@Override
	public CompAccount getCompAccountByCid(Integer cid) {
		return (CompAccount)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getCompAccountByCid"),cid);
	}

	@Override
	public String queryQq(Integer cid) {
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryQq"),cid);
	}

	@Override
	public Integer queryUidByCid(Integer cid) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryUidByCid"), cid);
	}

	@Override
	public Integer queryLoginCount(Integer cid) {
		
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryLoginCount"),cid);
	}

	@Override
	public CompAccount queryAccountInfoByCid(Integer cid) {
		
		return (CompAccount)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountInfoByCid"), cid);
	}

	@Override
	public Integer queryCountCompAcountByCid(Integer cid) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountCompAcountByCid"), cid);
	}

	@Override
	public CompAccount queryContactByCid(Integer cid) {
		
		return (CompAccount) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryContactByCid"), cid);
	}

	@Override
	public Integer insertCompAccounts(CompAccount compAccount) {
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompAccounts"), compAccount);
	}

	@Override
	public Integer queryCountCompAcountByMobile(String mobile) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountCompAcountByMobile"), mobile);
	}

	@Override
	public Integer queryCountCompAcountByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountCompAcountByAccount"), account);
	}

}