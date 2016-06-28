/**
 * 
 */
package com.ast.ast1949.persist.analysis;

import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisPpcLog;
import com.ast.ast1949.dto.PageDto;


/**
 * @author root
 *
 */
public interface AnalysisPpcLogDao {
	
	public Integer queryAllPvByCid(Integer cid);
	
	public List<AnalysisPpcLog> queryList(AnalysisPpcLog analysisPpcLog,PageDto<AnalysisPpcLog> page);
	
	public Integer queryListCount(AnalysisPpcLog analysisPpcLog);
	
	public Integer sumPvByTimeACid(AnalysisPpcLog analysisPpcLog);
}
