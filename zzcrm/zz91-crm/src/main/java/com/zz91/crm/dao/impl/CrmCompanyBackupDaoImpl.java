package com.zz91.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.CrmCompanyBackupDao;
import com.zz91.crm.domain.CrmCompanyBackup;

@Repository("crmCompanyBackupDao")
public class CrmCompanyBackupDaoImpl extends BaseDao implements
		CrmCompanyBackupDao {

	final static String SQL_PREFIX = "crmCompany";

	@Override
	public Integer createCompany(CrmCompanyBackup company) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createCompany"),company);
	}
}