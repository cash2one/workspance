/**
 * @author shiqp
 * @date 2014-09-11
 */
package com.ast.ast1949.persist.analysis;

import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisMyrcVisitor;
import com.ast.ast1949.dto.PageDto;

public interface AnalysisMyrcVisitorDao {
	public Integer countViewByCidATime(Integer companyId, String from, String to);

	public Integer countVisitByCidATime(Integer companyId, String from, String to);
	
	public List<AnalysisMyrcVisitor> queryViewByCidATime(Integer companyId,String from,String to,PageDto<AnalysisMyrcVisitor> page);
	
	public List<AnalysisMyrcVisitor> queryVisitByCidATime(Integer companyId,String from,String to,PageDto<AnalysisMyrcVisitor> page);
	
	public Integer updateTitle(String title, String url);
	
	public Integer countViewByCidATimeLen(Integer companyId,String from,String to);
	
	public Integer countVisitByCidATimeLen(Integer companyId,String from,String to);

}
