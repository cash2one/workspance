/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyMembershipDO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyMembershipDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 * 
 */
@Component("companyMembershipDAO")
public class CompanyMembershipDAOImpl extends BaseDaoSupport implements
		CompanyMembershipDAO {

	final static String SQL_PREFIX = "companyMembership";

	public Integer insertCompanyMembership(
			CompanyMembershipDO companyMembershipDO) {
		return (Integer) getSqlMapClientTemplate().insert(
				"companyMembership.insertCompanyMembershipDO",
				companyMembershipDO);
	}

	public CompanyMembershipDO queryCompanyMembershipByCompanyId(
			Integer companyId) {
		Assert.notNull(companyId, "The companyId can not be null");
		return (CompanyMembershipDO) getSqlMapClientTemplate()
				.queryForObject(
						addSqlKeyPreFix(SQL_PREFIX,
								"queryCompanyMembershipByCompanyId"), companyId);
	}

	public Integer updateCompanyMembershipByCompanyId(
			CompanyMembershipDO companyMembership) {
		Assert.notNull(companyMembership,
				"The object of companyMembership must not be null");
		Assert.notNull(companyMembership.getCompanyId(),
				"The companyId must not be null");
		return getSqlMapClientTemplate().update(
				"companyMembership.updateCompanyMembershipByCompanyId",
				companyMembership);
	}

	public Integer queryCompanyIdByUrl(String url) {
		Assert.notNull(url, "the url can not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyIdByUrl"), url);
	}

	@Override
	public Integer queryCompanyIdByDomain(String domain) {
		Assert.notNull(domain, "the domain can not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyIdByDomain"), domain);
	}

	@Override
	public Integer saveCustomDomain(Integer companyId, String domain) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("domain", domain);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "saveCustomDomain"), root);
	}

}
