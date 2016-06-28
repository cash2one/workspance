package com.ast.ast1949.service.company.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmCsLogAdded;
import com.ast.ast1949.persist.company.CrmCsLogAddDao;
import com.ast.ast1949.persist.company.CrmCsLogDao;
import com.ast.ast1949.service.company.CrmCsLogAddedService;
import com.ast.ast1949.util.Assert;

@Component("crmCsLogAddedService")
public class CrmCsLogAddedServiceImpl implements CrmCsLogAddedService {
	
	@Resource
	private CrmCsLogAddDao crmCsLogAddDao;
	@Resource
	private CrmCsLogDao crmCsLogDao;
	
	@Override
	public Integer createAdded(CrmCsLogAdded added, Integer companyId) {
		added.setCrmCsLogId(crmCsLogDao.queryRecentLogId(companyId));
		return crmCsLogAddDao.createAdded(added);
	}
}
