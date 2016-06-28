package com.ast.ast1949.persist.sample.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Account;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.AccountDAO;

@Component("accountDao")
public class AccountDAOImpl extends BaseDaoSupport  implements AccountDAO {

    public Integer insert(Account record) {
      return  (Integer) getSqlMapClientTemplate().insert("sample_account.insert", record);
    }

    public int updateByPrimaryKey(Account record) {
        int rows = getSqlMapClientTemplate().update("sample_account.updateByPrimaryKey", record);
        return rows;
    }

    public int updateByPrimaryKeySelective(Account record) {
        int rows = getSqlMapClientTemplate().update("sample_account.updateByPrimaryKeySelective", record);
        return rows;
    }

    public Account selectByPrimaryKey(Integer id) {
        Account key = new Account();
        key.setId(id);
        Account record = (Account) getSqlMapClientTemplate().queryForObject("sample_account.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        Account key = new Account();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("sample_account.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public Account selectByCompanyId(Integer id) {
        Account record = (Account) getSqlMapClientTemplate().queryForObject("sample_account.selectByCompanyId", id);
        return record;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> queryListByFilter(Map<String, Object> filterMap) {
		return getSqlMapClientTemplate().queryForList("sample_account.queryListByFilter", filterMap);
	}

	@Override
	public Integer queryListByFilterCount(Map<String, Object> filterMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_account.queryListByFilterCount", filterMap);
	}
}