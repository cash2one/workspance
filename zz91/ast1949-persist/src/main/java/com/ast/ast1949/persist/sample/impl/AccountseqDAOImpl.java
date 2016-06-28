package com.ast.ast1949.persist.sample.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Accountseq;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.AccountseqDAO;

@Component("accountseqDao")
public class AccountseqDAOImpl extends BaseDaoSupport implements AccountseqDAO {

	public Integer insert(Accountseq record) {
		return (Integer)getSqlMapClientTemplate().insert("sample_accountseq.insert", record);
	}

	public int updateByPrimaryKey(Accountseq record) {
		int rows = getSqlMapClientTemplate().update("sample_accountseq.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(Accountseq record) {
		int rows = getSqlMapClientTemplate().update("sample_accountseq.updateByPrimaryKeySelective", record);
		return rows;
	}

	public Accountseq selectByPrimaryKey(Integer id) {
		Accountseq key = new Accountseq();
		key.setId(id);
		Accountseq record = (Accountseq) getSqlMapClientTemplate().queryForObject("sample_accountseq.selectByPrimaryKey",
				key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		Accountseq key = new Accountseq();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("sample_accountseq.deleteByPrimaryKey", key);
		return rows;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Accountseq> queryListByFilter(Map<String, Object> filterMap) {
		return getSqlMapClientTemplate().queryForList("sample_accountseq.queryListByFilter", filterMap);
	}

	@Override
	public Integer queryListByFilterCount(Map<String, Object> filterMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_accountseq.queryListByFilterCount", filterMap);
	}
}