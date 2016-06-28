package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmCsDto;
import com.ast.ast1949.dto.company.CrmSearchDto;

public interface CrmCsService {
	
	public static final String ZST_TEMPLATE="<span style='color:green'>{0}</span>";
	public static final String PPT_TEMPLATE="<span style='color:red'>{0}</span>";
 
	/**
	 * 将客户放入公海，删除关联的客户信息
	 */
	public Boolean intoHighSea(String csAccount, Integer companyId);
	
	public Boolean reassign(String oldCsAccount, String csAccount, Integer companyId);
	
	public PageDto<CrmCsDto> queryCoreCompany(CrmSearchDto search, String account, PageDto<CrmCsDto> page);
	
	public CrmCs queryCsOfCompany(Integer companyId);
	
	public CrmCsDto queryCoreCompanyByCompanyId(Integer companyId, String svrCode);

	public String queryAccountByCompanyId(Integer companyId);
	
}
 
