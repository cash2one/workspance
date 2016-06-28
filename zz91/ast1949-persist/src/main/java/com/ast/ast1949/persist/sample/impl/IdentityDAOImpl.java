package com.ast.ast1949.persist.sample.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Identity;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.IdentityDAO;

@Component("identityDao")
public class IdentityDAOImpl extends BaseDaoSupport implements IdentityDAO {

	public Integer insert(Identity record) {
		return (Integer)getSqlMapClientTemplate().insert("sample_identity.insert", record);
	}

	public int updateByPrimaryKey(Identity record) {
		int rows = getSqlMapClientTemplate().update("sample_identity.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(Identity record) {
		int rows = getSqlMapClientTemplate().update("sample_identity.updateByPrimaryKeySelective", record);
		return rows;
	}

	public Identity selectByPrimaryKey(Integer id) {
		Identity key = new Identity();
		key.setId(id);
		Identity record = (Identity) getSqlMapClientTemplate().queryForObject("sample_identity.selectByPrimaryKey", key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		Identity key = new Identity();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("sample_identity.deleteByPrimaryKey", key);
		return rows;
	}

	public Identity queryIdentityByCompanyId(Integer companyId) {
		return (Identity) getSqlMapClientTemplate().queryForObject("sample_identity.queryIdentityByCompanyId", companyId);
	}

	public void updateFrontByCompanyId(Integer companyId, String str) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("str", str);
		getSqlMapClientTemplate().update("sample_identity.updateFrontByCompanyId", root);
	}

	public void updateBackByCompanyId(Integer companyId, String str) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("str", str);
		getSqlMapClientTemplate().update("sample_identity.updateBackByCompanyId", root);
	}

	@Override
	public Integer queryListByFilterCount(Map<String, Object> filterMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_identity.queryListByFilterCount", filterMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Identity> queryListByFilter(Map<String, Object> filterMap) {
		return getSqlMapClientTemplate().queryForList("sample_identity.queryListByFilter", filterMap);
	}
}