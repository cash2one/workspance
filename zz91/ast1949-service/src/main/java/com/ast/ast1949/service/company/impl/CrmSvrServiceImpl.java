package com.ast.ast1949.service.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmSvr;
import com.ast.ast1949.persist.company.CrmSvrDao;
import com.ast.ast1949.service.company.CrmSvrService;

@Component("crmSvrService")
public class CrmSvrServiceImpl implements CrmSvrService {
	
	@Resource
	private CrmSvrDao crmSvrDao;
	@Override
	public List<CrmSvr> querySvr() {
		return crmSvrDao.querySvr();
	}
	@Override
	public List<CrmSvr> queryLdbSvr() {
		return crmSvrDao.queryLdbSvr();
	}
	@Override
	public List<CrmSvr> querySvrByGroup(String group) {
		return crmSvrDao.querySvrByGroup(group);
	}

	@Override
	public String queryCloseApi(String code) {
		
		return crmSvrDao.queryApi(code,"close");
	}

	@Override
	public String queryOpenApi(String code) {
		return crmSvrDao.queryApi(code,"open");
	}

	@Override
	public String queryRenewalsApi(String code) {
		return crmSvrDao.queryApi(code,"renewals");
	}

}
