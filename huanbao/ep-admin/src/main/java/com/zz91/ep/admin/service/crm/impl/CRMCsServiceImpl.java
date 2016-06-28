package com.zz91.ep.admin.service.crm.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.crm.CRMCsDao;
import com.zz91.ep.admin.service.crm.CRMCsService;
import com.zz91.ep.domain.crm.CrmCs;
import com.zz91.util.Assert;
@Component("crmCsService")
public class CRMCsServiceImpl implements CRMCsService {

	@Resource private CRMCsDao crmCsDao;

	@Override
	public CrmCs queryCsOfCompany(Integer companyId) {
		Assert.notNull(companyId, "companyId 不能为空");
		return crmCsDao.queryCsOfCompany(companyId);
	}

	@Override
	public void reassign(String oldCsAccount, String csAccount, Integer cid) {
		// TODO Auto-generated method stub
		
	}

}
