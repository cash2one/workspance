package com.zz91.ep.admin.dao.crm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.crm.CRMRightDao;
import com.zz91.ep.domain.crm.CrmRight;
@Repository("crmRightDao")
public class CRMRightDaoImpl extends BaseDao implements CRMRightDao {
	final static String NAMESPACE = "crmRight" + ".";

	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryCrmRightListBycompanyId(Integer companyId, String parentRight) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", companyId);
		root.put("parentRight", parentRight);
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmRightListBycompanyId", root);
	}

	@Override
	public Integer countChildRight(String parentCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(NAMESPACE + "countChildRight", parentCode);
	}

	@Override
	public Integer createRight(CrmRight right) {
		
		return null;
	}

	@Override
	public Integer deleteCrmSvrRightByCode(String code) {
		
		return (Integer) getSqlMapClientTemplate().delete(NAMESPACE + "deleteCrmSvrRightByCode", code);
	}

	@Override
	public Integer deleteMemberRightByCode(String code) {
		
		return (Integer) getSqlMapClientTemplate().delete(NAMESPACE + "deleteMemberRightByCode", code);
	}

	@Override
	public Integer deleteRightByCode(String code) {
		
		return (Integer) getSqlMapClientTemplate().delete(NAMESPACE + "deleteRightByCode", code);
	}

	@Override
	public Integer insertRight(CrmRight right) {
		
		return (Integer) getSqlMapClientTemplate().insert(NAMESPACE + "insertRight", right);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmRight> queryChildRight(String parentCode) {
		
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryChildRight", parentCode);
	}

	@Override
	public Integer queryIdByCode(String code) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryIdByCode", code);
	}

	@Override
	public String queryMaxCodeOfChild(String parentCode) {
		
		return (String) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryMaxCodeOfChild", parentCode);
	}

	@Override
	public String queryNameByCode(String code) {
		
		return (String) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryNameByCode", code);
	}

	@Override
	public CrmRight queryOneRight(String code) {
		
		return (CrmRight) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryOneRight", code);
	}

	@Override
	public Integer updateRight(CrmRight right) {
		
		return (Integer) getSqlMapClientTemplate().update(NAMESPACE + "updateRight", right);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryCrmRightListByMemberCode(String memberCode, String parentRight) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("memberCode", memberCode);
		root.put("parentRight", parentRight);
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmRightListByMemberCode", root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmRight> queryCrmRightListBycrmRightIdArray(
			List<Integer> crmRightIds) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmRightListBycrmRightIdArray", crmRightIds);
	}


}
