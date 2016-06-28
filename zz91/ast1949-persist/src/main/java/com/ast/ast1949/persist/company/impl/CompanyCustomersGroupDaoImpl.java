/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31
 */
package com.ast.ast1949.persist.company.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyCustomersGroupDO;
import com.ast.ast1949.persist.company.CompanyCustomersGroupDao;

/**
 * @author yuyonghui
 *
 */
@Component("CompanyCustomersGroupDao")
public class CompanyCustomersGroupDaoImpl extends SqlMapClientDaoSupport implements CompanyCustomersGroupDao{

	public Integer deleteCompanyCustomersGroupById(Integer id) {
		return getSqlMapClientTemplate().delete("companyCustomersGroup.deleteCompanyCustomersGroupById", id);
	}

	public Integer insertCompanyCustomersGroup(
			CompanyCustomersGroupDO companyCustomersGroupDO) {
		return Integer.valueOf(getSqlMapClientTemplate().insert("companyCustomersGroup.insertCompanyCustomersGroup", companyCustomersGroupDO).toString());
	}

	@SuppressWarnings("unchecked")
	public List<CompanyCustomersGroupDO> queryCompanyCustomersGroupByCompanyId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList("companyCustomersGroup.queryCompanyCustomersGroupByCompanyId",companyId);
	}

	public Integer updateCompanyCustomersGroup(
			CompanyCustomersGroupDO companyCustomersGroupDO) {
		return getSqlMapClientTemplate().update("companyCustomersGroup.updateCompanyCustomersGroup", companyCustomersGroupDO);
	}

	public CompanyCustomersGroupDO queryGroupById(Integer id) {

		return (CompanyCustomersGroupDO) getSqlMapClientTemplate().queryForObject("companyCustomersGroup.queryGroupById", id);
	}

}
