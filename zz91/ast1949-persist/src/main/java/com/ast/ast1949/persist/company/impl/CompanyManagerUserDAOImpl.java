/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-27
 */
package com.ast.ast1949.persist.company.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyManagerUserDO;
import com.ast.ast1949.persist.company.CompanyManagerUserDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("companyManagerUserDAO")
public class CompanyManagerUserDAOImpl extends SqlMapClientDaoSupport implements CompanyManagerUserDAO {

	public Integer countCompanyManagerUserByCompanyId(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject("companyManagerUser.countCompanyManagerUserByCompanyId", id);
	}

	public Integer deleteCompanyManagerUserByCompanyId(Integer id) {
		return getSqlMapClientTemplate().delete("companyManagerUser.deleteCompanyManagerUserByCompanyId", id);
	}

	public Integer insertCompanyManagerUser(
			CompanyManagerUserDO companyManagerUser) {
		return (Integer) getSqlMapClientTemplate().insert("companyManagerUser.insertCompanyManagerUser", companyManagerUser);
	}

	public Integer updateAdminUserIdByCompanyId(
			CompanyManagerUserDO companyManagerUser) {
		return getSqlMapClientTemplate().update("companyManagerUser.updateAdminUserIdByCompanyId", companyManagerUser);
	}

	public CompanyManagerUserDO queryCompanyManagerUserByCompanyId(Integer id) {
		Assert.notNull(id, "The id must not be null");
		return (CompanyManagerUserDO) getSqlMapClientTemplate().queryForObject("companyManagerUser.queryCompanyManagerUserByCompanyId", id);
	}
}
