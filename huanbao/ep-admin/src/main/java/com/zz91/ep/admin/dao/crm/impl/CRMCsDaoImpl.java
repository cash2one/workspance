package com.zz91.ep.admin.dao.crm.impl;

import org.springframework.stereotype.Component;
import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.crm.CRMCsDao;
import com.zz91.ep.domain.crm.CrmCs;
@Component("crmCsDao")
public class CRMCsDaoImpl extends BaseDao implements CRMCsDao {
	final static String SQL_PREFIX="crmCs";

	@Override
	public CrmCs queryCsOfCompany(Integer companyId) {
		return (CrmCs) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCsOfCompany"), companyId);
	}

}
