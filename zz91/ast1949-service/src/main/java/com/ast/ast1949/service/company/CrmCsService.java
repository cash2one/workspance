package com.ast.ast1949.service.company;

import java.util.Date;
import java.util.Map;

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
	/**
	 * 分配来电宝客户
	 * @param csAccount
	 * @param companyId
	 * @return
	 */
	public Boolean reassignLdb( String csAccount, Integer companyId);
	
	public PageDto<CrmCsDto> queryCoreCompany(CrmSearchDto search, String account, PageDto<CrmCsDto> page);
	
	public CrmCs queryCsOfCompany(Integer companyId);
	
	public CrmCsDto queryCoreCompanyByCompanyId(Integer companyId, String svrCode);

	public String queryAccountByCompanyId(Integer companyId);
	
	public PageDto<CrmCsDto> queryLdbCoreCompany(CrmSearchDto search,PageDto<CrmCsDto> page);
	
	/**
	 * 查找每天联系量
	 * @param start
	 * @param end
	 * @param code
	 * @return
	 */
	public Map<String, Map<String, Integer>> dayContactCount(Date start, Date end,String csAccount);
	public CrmCsDto queryCrmCsByCompanyId(Integer companyId);
	
}
 
