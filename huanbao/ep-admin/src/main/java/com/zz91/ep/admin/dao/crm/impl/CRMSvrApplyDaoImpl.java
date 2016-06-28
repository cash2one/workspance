package com.zz91.ep.admin.dao.crm.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.crm.CRMSvrApplyDao;
import com.zz91.ep.domain.crm.CrmCsComp;
import com.zz91.ep.domain.crm.CrmSvrApply;

@Component("crmSvrApplyDao")
public class CRMSvrApplyDaoImpl extends BaseDao implements CRMSvrApplyDao {
	
	final static String SQL_PREFIX="crmSvrApply";
	
	Map<String, Object> root = null;
	@Override
	public Integer insertApply(CrmSvrApply apply) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertApply"), apply);
	}
	@Override
	public CrmSvrApply queryApplyByGroup(String applyGroup) {
		return (CrmSvrApply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryApplyByGroup"), applyGroup);
	}
	@Override
	public Integer assignApplyToCs(CrmCsComp crmCsComp) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "assignApplyToCs"), crmCsComp);
	}
	@Override
	public Integer queryCompWithCs(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompWithCs"), id);
	}
	@Override
	public Integer updateDelStatus(Integer id) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelStatus"), id);
	}
}
