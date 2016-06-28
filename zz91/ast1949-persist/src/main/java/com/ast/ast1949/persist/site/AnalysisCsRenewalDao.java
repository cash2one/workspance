package com.ast.ast1949.persist.site;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisCsRenewal;

public interface AnalysisCsRenewalDao {
	
	public List<AnalysisCsRenewal> queryAnalysisCsRenewal(String account, Date start, Date end);
	
}
