package com.ast.ast1949.persist.company;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmCsDto;
import com.ast.ast1949.dto.company.CrmSearchDto;

public interface CrmCsDao {

	public Integer deleteCs(String csAccount, Integer companyId);
	
	public Integer deleteLdbCs(Integer companyId);
	
	public CrmCsDto queryCoreCompanyById(Integer companyId, String serviceCode);

	public Integer insertCs(String csAccount, Integer companyId);

	public Integer updateLogInfo(Integer companyId, String csAccount,
			Date nextVisitPhone, Date nextVisitEmail, String visitTarget);

	public CrmCs queryCsOfCompany(Integer companyId);

	public List<CrmCsDto> queryCoreCompany(CrmSearchDto search,
			PageDto<CrmCsDto> page);
	public Integer queryCoreCompanyCount(CrmSearchDto search);
	
	public List<CrmCsDto> queryLdbCoreCompany(CrmSearchDto search,
			PageDto<CrmCsDto> page);
	
	public Integer queryLdbCoreCompanyCount(CrmSearchDto search);

	public String queryCsAccountByCompanyId(Integer companyId);

	public Date queryEndTimeByCompanyId(Integer companyId);
	
	public List<CrmCs> queryCsList(String csAccount,Date start,Date end);

	public Date queryGmtEndForLDB(Integer companyId);

}
