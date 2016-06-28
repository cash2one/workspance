package com.ast.ast1949.service.company.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmOutLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmOutLogDto;
import com.ast.ast1949.persist.company.CrmOutLogDao;
import com.ast.ast1949.service.company.CrmOutLogService;
import com.zz91.util.Assert;

/**
 *	author:kongsj
 *	date:2013-5-17
 */
@Component("crmOutLogService")
public class CrmOutLogServiceImpl implements CrmOutLogService{

	@Resource
	private CrmOutLogDao crmOutLogDao;
	
	@Override
	public Integer insert(Integer companyId,String oldCsAccount, String operator, String status) {
		Assert.notNull(companyId, "companyId must not be null");
		Assert.notNull(companyId, "operator must not be null");
		CrmOutLog crmOutLog = new CrmOutLog(); 
		crmOutLog.setCompanyId(companyId);
		crmOutLog.setStatus(status);
		crmOutLog.setOperator(operator);
		crmOutLog.setOldCsAccount(oldCsAccount);
		return crmOutLogDao.insert(crmOutLog);
	}

	@Override
	public CrmOutLog queryById(Integer id) {
		Assert.notNull(id, "id must not be null");
		return crmOutLogDao.queryById(id);
	}
	
	@Override
	public PageDto<CrmOutLogDto> pageCrmOutLog(CrmOutLogDto crmOutLogDto,PageDto<CrmOutLogDto> page){
		page.setTotalRecords(crmOutLogDao.queryDtoCount(crmOutLogDto));
		page.setRecords(crmOutLogDao.queryDtoList(crmOutLogDto, page));
		return page;
	}

	@Override
	public CrmOutLog queryCrmoutLogByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "id must not be null");
		return crmOutLogDao.queryCrmOutLogByCompanyId(companyId);
	}

}
