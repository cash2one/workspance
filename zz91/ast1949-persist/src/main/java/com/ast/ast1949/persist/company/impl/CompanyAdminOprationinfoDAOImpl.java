/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-3
 */
package com.ast.ast1949.persist.company.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAdminOprationinfoDO;
import com.ast.ast1949.persist.company.CompanyAdminOprationinfoDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("companyAdminOprationinfoDAO")
public class CompanyAdminOprationinfoDAOImpl extends SqlMapClientDaoSupport implements CompanyAdminOprationinfoDAO {

	public Integer countByCompanyId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return (Integer) getSqlMapClientTemplate().queryForObject("companyAdminOprationinfo.countByCompanyId", id);
	}

	public Integer insertCompanyAdminOprationinfo(
			CompanyAdminOprationinfoDO companyAdminOprationinfo) {
		Assert.notNull(companyAdminOprationinfo, "the object of companyAdminOprationinfo must not be null");
		
		return (Integer) getSqlMapClientTemplate().insert("companyAdminOprationinfo.insertCompanyAdminOprationinfo", companyAdminOprationinfo);
	}

	public Integer updateSimplePropertyByCompanyId(
			CompanyAdminOprationinfoDO companyAdminOprationinfo) {
		Assert.notNull(companyAdminOprationinfo, "the object of companyAdminOprationinfo must not be null");
		Assert.notNull(companyAdminOprationinfo.getCompanyId(), "the company id must not be null");
		
		return getSqlMapClientTemplate().update("companyAdminOprationinfo.updateSimplePropertyByCompanyId", companyAdminOprationinfo);
	}

	public CompanyAdminOprationinfoDO queryCompanyAdminOprationinfoByCompanyId(
			Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return (CompanyAdminOprationinfoDO) getSqlMapClientTemplate().queryForObject("companyAdminOprationinfo.queryCompanyAdminOprationinfoByCompanyId", id);
	}

}
