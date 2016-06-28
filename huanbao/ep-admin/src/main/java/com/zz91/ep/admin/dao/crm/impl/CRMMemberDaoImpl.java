/**
 * 
 */
package com.zz91.ep.admin.dao.crm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.crm.CRMMemberDao;
import com.zz91.ep.domain.crm.CrmMember;

/**
 * @author leon
 * 2011-09-19
 *
 */
@Repository("crmMemberDao")
public class CRMMemberDaoImpl extends BaseDao implements CRMMemberDao {
	final static String NAMESPACE="crmMember" + ".";
	@Override
	public Integer deleteCrmMember(String crmMemberCode) {
		
		return getSqlMapClientTemplate().delete(NAMESPACE + "deleteCrmMember", crmMemberCode);
	}

	@Override
	public Integer deleteCrmMemberCrmRight(String crmMemberCode, Integer rightId) {
		Map<String, Object> root = buildArgsMap(crmMemberCode, rightId);
		return getSqlMapClientTemplate().delete(NAMESPACE + "deleteCrmMemberCrmRight", root);
	}

	@Override
	public Integer deleteCrmRightOfCrmMember(String crmMemberCode) {
		
		return getSqlMapClientTemplate().delete(NAMESPACE + "deleteCrmRightOfCrmMember", crmMemberCode);
	}
	@Override
	public Integer insertCrmMember(CrmMember crmMember) {
		
		return (Integer) getSqlMapClientTemplate().insert(NAMESPACE + "insertCrmMember", crmMember);
	}

	@Override
	public Integer insertCrmMemberCrmRight(String crmMemberCode,
			Integer crmRightId) {
		Map<String, Object> root = buildArgsMap(crmMemberCode, crmRightId);
		return (Integer) getSqlMapClientTemplate().insert(NAMESPACE + "insertCrmMemberCrmRight", root);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CrmMember> queryCrmMemberList() {
		
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmMemberList");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryCrmRightIdOfCrmMember(String crmMemberCode) {
		
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmRightIdOfCrmMember", crmMemberCode);
	}

	@Override
	public Integer updateCrmMember(CrmMember crmMember) {
		
		return getSqlMapClientTemplate().update(NAMESPACE + "updateCrmMember", crmMember);
	}
	
	private Map<String, Object> buildArgsMap(String crmMemberCode, Integer crmRightId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("crmMemberCode", crmMemberCode);
		root.put("crmRightId", crmRightId);
		return root;
	}

	@Override
	public Integer countCrmMemberChild(String code) {
		return (Integer) getSqlMapClientTemplate().queryForObject(NAMESPACE + "countCrmMemberChild", code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmMember> queryCrmMemberChild(String parentCode) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmMemberChild", parentCode);
	}

	@Override
	public String queryMaxCodeOfChild(String parentCode) {
		return (String) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryMaxCodeOfChild", parentCode);
	}

	@Override
	public CrmMember queryOneCrmMember(String memberCode) {
		return (CrmMember) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryOneCrmMember", memberCode);
	}

	@Override
	public String queryNameByCode(String memberCode) {
		return (String) getSqlMapClientTemplate().queryForObject(NAMESPACE+"queryNameByCode", memberCode);
	}

}
