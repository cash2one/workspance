package com.ast.ast1949.persist.company.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmServiceApply;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmSvrApplyDao;

@Component("crmSvrApplyDao")
public class CrmSvrApplyDaoImpl extends BaseDaoSupport implements CrmSvrApplyDao {
    
	private static String SQL_PREFIX = "crmSvrApply";
	@Override
	public CrmServiceApply queryApplyByGroup(String applyGroup) {
		return (CrmServiceApply) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryApplyByGroup"),applyGroup);
	}

	@Override
	public Integer insertApply(CrmServiceApply apply) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertApply"), apply);
	}

	@Override
	public Integer updateApply(CrmServiceApply apply) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateApply"),apply);
	}
}
