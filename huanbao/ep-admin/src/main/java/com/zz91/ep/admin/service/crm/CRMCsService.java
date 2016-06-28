package com.zz91.ep.admin.service.crm;

import com.zz91.ep.domain.crm.CrmCs;

public interface CRMCsService {

	CrmCs queryCsOfCompany(Integer companyId);

	void reassign(String oldCsAccount, String csAccount, Integer cid);
}
