package com.zz91.mail.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.mail.dao.AccountDao;
import com.zz91.mail.dao.BaseDao;
import com.zz91.mail.domain.AccountDomain;
import com.zz91.mail.domain.dto.PageDto;

@Repository("accountDao")
public class AccountDaoImpl extends BaseDao implements AccountDao {

    final static String SQL_PREFIX = "accountDomain";

    @Override
    public AccountDomain queryAccountByCode(String code) {
        return (AccountDomain) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountByCode"), code);
    }

    @Override
    public AccountDomain queryAccountByEmail(String email) {
        return (AccountDomain) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountByEmail"), email);
    }
    
    public AccountDomain queryAccountByUsername(String username){
        return (AccountDomain) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountByUsername"), username);
    }
    
    @Override
    public Integer insertAccount(AccountDomain account) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertAccount"), account);
    }

    @Override
    public Integer updateAccount(AccountDomain account) {
        return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateAccount"), account);
    }

    @Override
    public Integer deleteAccountById(Integer id) {
        return (Integer) getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteAccountById"), id);
    }

    @Override
    public Integer deleteAccountByHost(String host) {
        return (Integer) getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteAccountByHost"), host);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AccountDomain> queryAccount(AccountDomain account, PageDto<AccountDomain> page) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("account", account);
        root.put("page", page);
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAccount"), root);
    }

    @Override
    public Integer queryAccountCount(AccountDomain account) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("template", account);
        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountCount"), root);
    }

    @Override
    public AccountDomain queryAccountById(Integer id) {
        return (AccountDomain) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountById"), id);
    }

    @Override
    public Integer updateStatusOfAccount(Integer id, Integer pauseStatus) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", id);
        root.put("pauseStatus", pauseStatus);
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStatusOfAccount"), root);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountDomain> queryAllAccount(String code,
			Integer pauseStatus, Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("code", code);
		root.put("pauseStatus", pauseStatus);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllAccount"), root);
	}

}
