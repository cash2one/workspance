package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CrmCsLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.AnalysisCsLog;
import com.ast.ast1949.dto.company.CrmCsLogDto;



public interface CrmCsLogDao {
 
	/**
	 *  
	 *  
	 */
	public List<CrmCsLog> queryLogByCompany(Integer companyId,Integer callType,Integer star,String csAccount,Integer situation,String from,String to, PageDto<CrmCsLogDto> page);
	/**
	 *  
	 */
	public Integer queryLogByCompanyCount(Integer companyId,Integer callType,Integer star,String csAccount,Integer situation,String from,String to);
	
	public Integer insertLog(CrmCsLog log);
	
	public Integer queryRecentLogId(Integer companyId);
	
	public List<AnalysisCsLog> queryCsLogAnalysis(String csAccount, Long from, Long to);
}
 
