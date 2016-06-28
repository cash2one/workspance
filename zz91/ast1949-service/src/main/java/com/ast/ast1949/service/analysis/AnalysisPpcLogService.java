/**
 * 
 */
package com.ast.ast1949.service.analysis;

import com.ast.ast1949.domain.analysis.AnalysisPpcLog;
import com.ast.ast1949.dto.PageDto;


/**
 * @author root
 *
 */
public interface AnalysisPpcLogService {
	
	public Integer queryAllPvByCid(Integer cid);
	
	public PageDto<AnalysisPpcLog> queryList(AnalysisPpcLog analysisPpcLog,PageDto<AnalysisPpcLog> page);
}
