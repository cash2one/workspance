/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.persist.company.impl;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAdminUserDO;
import com.ast.ast1949.persist.company.CompanyAdminUserDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("companyAdminUserDAO")
public class CompanyAdminUserDAOImpl extends SqlMapClientDaoSupport implements CompanyAdminUserDAO{

	public Integer insertCompanyAdminUserDO(CompanyAdminUserDO companyAdminUserDO) {
		Assert.notNull(companyAdminUserDO, "the object of companyAdminUserDO must not be null");
		Assert.notNull(companyAdminUserDO.getAdminUserId(), "the adminUserId must not be null");
		Assert.notNull(companyAdminUserDO.getCompanyId(), "the companyId must not be null");
		
		return (Integer) getSqlMapClientTemplate().insert("companyAdminUser.insertCompanyAdminUserDO", companyAdminUserDO);
	}

	public Integer changeAdminUserIdByCompanyId(Map<String, Object> param) {
		Assert.notNull(param, "the param must not be null");
		return getSqlMapClientTemplate().update("companyAdminUser.changeAdminUserIdByCompanyId", param);
	}

	public Integer queryCountByCompanyId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject("companyAdminUser.queryCountByCompanyId", id);
	}

	public Integer deleteByCompanyId(Integer id) {
		return getSqlMapClientTemplate().delete("companyAdminUser.deleteByCompanyId", id);
	}
}
