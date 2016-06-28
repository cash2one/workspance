package com.ast.ast1949.persist.analysis;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisTrustLog;

public interface AnalysisTrustLogDao {
	public List<AnalysisTrustLog> queryByCondition(String from ,String to,String account);
}
