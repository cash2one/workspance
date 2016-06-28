package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmCsProfile;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmCsProfileDao;

@Component("crmCsProfileDao")
public class CrmCsProfileDaoImpl extends BaseDaoSupport implements CrmCsProfileDao {

	final static String SQL_PREFIX="crmCsProfile";
	
	@Override
	public Integer countProfile(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countProfile"), companyId);
	}

	@Override
	public Integer insertProfile(CrmCsProfile profile) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertProfile"), profile);
	}

	@Override
	public Integer updateLastVisit(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateLastVisit"),companyId);
	}

	@Override
	public Integer updateMemberShipCode(String membershipCode, Integer companyId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("membershipCode", membershipCode);
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateMemberShipCode"), map);
	}

	@Override
	public CrmCsProfile queryCrmCsProfileByCompanyId(Integer companyId) {
		return (CrmCsProfile) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCrmCsProfileByCompanyId"), companyId);
	}
	
}
