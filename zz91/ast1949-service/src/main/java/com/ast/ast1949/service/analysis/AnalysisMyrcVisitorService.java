/**
 * @author shiqp
 * @date 2014-09-11
 */
package com.ast.ast1949.service.analysis;
import java.util.Map;

import com.ast.ast1949.domain.analysis.AnalysisMyrcVisitor;
import com.ast.ast1949.domain.analysis.AnalysisMyrcVisitors;
import com.ast.ast1949.dto.PageDto;

public interface AnalysisMyrcVisitorService {
	// 获取昨日数据统计
	public AnalysisMyrcVisitors getVisitorsData(Integer companyId, String from, String to);

	// 获取该公司来访用户的信息
	public PageDto<AnalysisMyrcVisitor> getVisitorList(Integer companyId, String from, String to,Integer tag, PageDto<AnalysisMyrcVisitor> page);
	
	//坐标
	public Map<String,Object> getPoint(Integer companyId, String from, String to, String menberShip);
}
