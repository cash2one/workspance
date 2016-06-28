package com.zz91.ep.admin.dao.crm;

import com.zz91.ep.domain.crm.CrmCs;

/**
 * 
 * @author leon
 * 2011-09-16
 */
public interface CRMCsDao {

	CrmCs queryCsOfCompany(Integer companyId);
}