package com.ast.ast1949.service.analysis;

import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisTrustCrmDto;
import com.ast.ast1949.domain.analysis.AnalysisTrustLog;

public interface AnalysisTrustLogService {
	
	final static String CS_DEPT_CODE="10001015";
	
	public List<AnalysisTrustLog> queryByCondition(String from, String to,String account);

	/**
	 * 获取交易小组今日crm详单
	 * @return
	 */
	public List<AnalysisTrustCrmDto> queryDayLog();
}
