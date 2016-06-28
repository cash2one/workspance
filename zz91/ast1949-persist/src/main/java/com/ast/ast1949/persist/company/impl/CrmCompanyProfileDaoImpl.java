package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmCompanyProfile;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmCompanyProfileDao;

@Component("crmCompanyProfileDao")
public class CrmCompanyProfileDaoImpl extends BaseDaoSupport implements CrmCompanyProfileDao {
	
	private static String SQL_PREFIX = "crmCompanyProfile";
	@Override
	public CrmCompanyProfile queryProfile(Integer companyId) {
		return (CrmCompanyProfile) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryProfile"), companyId);
	}

	@Override
	public Integer insertProfile(CrmCompanyProfile profile) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "createProfile"), profile);
	}

	@Override
	public Integer updateProfile(CrmCompanyProfile profile) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateProfile"), profile);
	}

	@Override
	public Integer updateOutbusiness(Integer companyId, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("type", type);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateOutbusiness"), map);
	}

}
