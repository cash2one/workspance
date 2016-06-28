/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31
 */
package com.ast.ast1949.service.company.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyCustomersGroupDO;
import com.ast.ast1949.persist.company.CompanyCustomersGroupDao;
import com.ast.ast1949.service.company.CompanyCustomersGroupService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("companyCustomersGroupService")
public class CompanyCustomersGroupServiceImpl extends SqlMapClientDaoSupport
		implements CompanyCustomersGroupService {

	@Autowired
	private CompanyCustomersGroupDao companyCustomersGroupDao;

	public Integer deleteCompanyCustomersGroupById(Integer id) {
		Assert.notNull(id, "id is not null");
		return companyCustomersGroupDao.deleteCompanyCustomersGroupById(id);
	}

	public Integer insertCompanyCustomersGroup(
			CompanyCustomersGroupDO companyCustomersGroupDO) {
		Assert.notNull(companyCustomersGroupDO,
				"companyCustomersGroupDO is not null");
		return companyCustomersGroupDao
				.insertCompanyCustomersGroup(companyCustomersGroupDO);
	}


	public Integer updateCompanyCustomersGroup(
			CompanyCustomersGroupDO companyCustomersGroupDO) {
		Assert.notNull(companyCustomersGroupDO,
				"companyCustomersGroupDO is not null");
		return companyCustomersGroupDao
				.updateCompanyCustomersGroup(companyCustomersGroupDO);
	}

	public CompanyCustomersGroupDO queryGroupById(Integer id) {
         Assert.notNull(id, "id is not null");
		return companyCustomersGroupDao.queryGroupById(id);
	}

	public List<CompanyCustomersGroupDO> queryCompanyCustomersGroupByCompanyId(
			Integer companyId) {

		return companyCustomersGroupDao.queryCompanyCustomersGroupByCompanyId(companyId);
	}



}
