package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.CrmOutLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmOutLogDto;

/**
 * author:kongsj date:2013-5-17
 */
public interface CrmOutLogService {
	
	final static String STATUS_OUT = "0"; // 掉公海
	final static String STATUS_IN = "1"; // 捞公海
	final static String STATUS_ASSIGN = "2"; // 分配
	
	/**
	 * 记录新公海记录
	 * @param companyId
	 * @param operator
	 * @param status
	 * @return
	 */
	public Integer insert(Integer companyId,String oldCsAccount, String operator, String status);

	public CrmOutLog queryById(Integer id);

	/**
	 * 分页搜索公海 日志
	 * @param crmOutLogDto
	 * @param page
	 * @return
	 */
	public PageDto<CrmOutLogDto> pageCrmOutLog(CrmOutLogDto crmOutLogDto,PageDto<CrmOutLogDto> page);
	/**
	 * 根据companyId查找crm_out_log
	 * @param companyId
	 * @return
	 */
	public CrmOutLog queryCrmoutLogByCompanyId(Integer companyId);
}
