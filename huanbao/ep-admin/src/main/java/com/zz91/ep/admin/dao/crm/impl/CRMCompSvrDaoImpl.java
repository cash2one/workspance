/**
 * 
 */
package com.zz91.ep.admin.dao.crm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.crm.CRMCompSvrDao;
import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.crm.CrmCompSvrDto;

/**
 * @author root
 *
 */
@Repository("crmCompSvrDao")
public class CRMCompSvrDaoImpl extends BaseDao implements CRMCompSvrDao {
	final static String SQL_PREFIX="crmCompSvr";
	@Override
	public int[] queryCrmSvrIdByCid(Integer companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insertCompSvr(CrmCompSvr companySvr) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompSvr"), companySvr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompSvrDto> queryApplyCompany(Integer crmSvrId,
			String applyStatus, PageDto<CrmCompSvrDto> page,String account) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("crmSvrId", crmSvrId);
		root.put("applyStatus", applyStatus);
		root.put("account", account);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryApplyCompany"), root);
	}

	@Override
	public Integer queryApplyCompanyCount(Integer crmSvrId, String applyStatus,String account) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("crmSvrId", crmSvrId);
		root.put("applyStatus", applyStatus);
		root.put("account", account);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryApplyCompanyCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompSvr> queryApplySvrHistory(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryApplySvrHistory"), companyId);
	}

	@Override
	public Integer countOpenedApplyByGroup(String applyGroup) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countOpenedApplyByGroup"), applyGroup);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompSvrDto> queryApplyByGroup(String applyGroup) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryApplyByGroup"), applyGroup);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompSvr> querySvrHistory(Integer companyId, Integer crmSvrId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("crmSvrId", crmSvrId);
		root.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySvrHistory"), root);
	}

	@Override
	public Integer updateCrmCompSvr(CrmCompSvr crmCompSvr) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCrmCompSvr"), crmCompSvr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrmCompSvr queryRecentHistory(Integer crmSvrId, Integer cid,
			Integer id) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("crmSvrId", crmSvrId);
		root.put("cid", cid);
		root.put("id", id);
		List<CrmCompSvr> list = getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRecentHistory"), root);
		if(list==null || list.size()==0)
			return null;
		return list.get(0);
	}

	@Override
	public CrmCompSvr queryCompanySvrById(Integer companySvrId) {
		return (CrmCompSvr) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompanySvrById"), companySvrId);
	}

	@Override
	public Integer refusedApplyUpdateApplyStatus(String applyGroup, Short applyStatusFailure) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("applyGroup", applyGroup);
		root.put("applyStatusFailure", applyStatusFailure);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "refusedApplyUpdateApplyStatus"), root);
	}

	@Override
	public Integer updateSvrStatusById(Integer companySvrId,
			Short status) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("status", status);
		root.put("compSvrId", companySvrId);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSvrStatusById"), root);
	}

}
