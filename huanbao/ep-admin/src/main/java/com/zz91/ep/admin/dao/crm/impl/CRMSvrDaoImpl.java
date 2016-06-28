package com.zz91.ep.admin.dao.crm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.crm.CRMSvrDao;
import com.zz91.ep.domain.crm.CrmSvr;
@Component("crmSvrDao")
public class CRMSvrDaoImpl extends BaseDao implements CRMSvrDao {
	final static String NAMESPACE="crmSvr" + ".";
	Map<String, Object> root = null;
	@Override
	public Integer deleteCrmRightOfCrmSvr(String code) {
		
		return getSqlMapClientTemplate().delete(NAMESPACE + "deleteCrmRightOfCrmSvr", code);
	}

	@Override
	public Integer deleteCrmSvr(String code) {
		
		return getSqlMapClientTemplate().delete(NAMESPACE + "deleteCrmSvr", code);
	}

	@Override
	public Integer deleteCrmSvrCrmRight(Integer crmSvrId, Integer crmRightId) {
		Map<String, Object> root = buildArgsMap(crmSvrId, crmRightId);
		return getSqlMapClientTemplate().delete(NAMESPACE + "deleteCrmSvrCrmRight", root);
	}
	

	@Override
	public Integer insertCrmSvr(CrmSvr crmSvr) {
		
		return (Integer) getSqlMapClientTemplate().insert(NAMESPACE + "insertCrmSvr", crmSvr);
	}

	@Override
	public Integer insertCrmSvrCrmRight(Integer crmSvrId, Integer crmRightId) {
		
		root = buildArgsMap(crmSvrId, crmRightId);
		return (Integer) getSqlMapClientTemplate().insert(NAMESPACE + "insertCrmSvrCrmRight", root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryCrmRightIdOfCrmSvr(Integer crmSvrId) {
		
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmRightIdOfCrmSvr", crmSvrId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmSvr> queryCrmSvrList() {
		
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmSvrList");
	}

	@Override
	public Integer updateCrmSvr(CrmSvr crmSvr) {
		
		return getSqlMapClientTemplate().update(NAMESPACE + "updateCrmSvr", crmSvr);
	}
	
	private Map<String, Object> buildArgsMap(Integer crmSvrId,
			Integer crmRightId) {
		root = new HashMap<String, Object>();
		root.put("crmSvrId", crmSvrId);
		root.put("crmRightId", crmRightId);
		return root;
	}

	@Override
	public Integer countCrmSvrChild(String code) {
		return (Integer) getSqlMapClientTemplate().queryForObject(NAMESPACE + "countCrmSvrChild", code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmSvr> queryCrmSvrChild(String parentCode) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "queryCrmSvrChild", parentCode);
	}

	@Override
	public CrmSvr queryOneCrmSvr(String code) {
		return (CrmSvr) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryOneCrmSvr", code);
	}

	@Override
	public String queryMaxCodeOfChild(String parentCode) {
		return (String) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryMaxCodeOfChild", parentCode);
	}

	@Override
	public Integer queryIdByCode(String svrCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryIdByCode", svrCode);
	}

	@Override
	public String querySvrNameById(Integer crmSvrId) {
		return (String) getSqlMapClientTemplate().queryForObject(NAMESPACE + "querySvrNameById", crmSvrId);
	}

	@Override
	public String queryCloseApi(Integer crmSvrId) {
		return (String) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryCloseApi", crmSvrId);
	}

	@Override
	public String queryOpenApi(Integer crmSvrId) {
		return (String) getSqlMapClientTemplate().queryForObject(NAMESPACE + "queryOpenApi", crmSvrId);
	}

}
