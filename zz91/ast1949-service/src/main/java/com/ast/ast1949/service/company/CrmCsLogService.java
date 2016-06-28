package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.CrmCsLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.AnalysisCsLog;
import com.ast.ast1949.dto.company.CrmCsLogDto;



public interface CrmCsLogService {
 
	/**
	 * 分页查找客户服务小记历史
	 *
	 *  
	 */
	public PageDto<CrmCsLogDto> pageLogByCompany(Integer companyId,Integer callType,Integer star,String csAccount,Integer situation,String from,String to, PageDto<CrmCsLogDto> page);
	
	public Integer createCsLog(CrmCsLog log, Integer newStar);
	
	public List<AnalysisCsLog> queryCsLogAnalysis(String csAccount, Long from, Long to);
}
 
