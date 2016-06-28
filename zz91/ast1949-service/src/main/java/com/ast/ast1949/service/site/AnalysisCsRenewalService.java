package com.ast.ast1949.service.site;


import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.analysis.AnalysisCsRenewal;

public interface AnalysisCsRenewalService {
	
	public Map<String, List<AnalysisCsRenewal>> queryAnalysisCsRenewal(String account, Date start, Date end);
	
	public List<String> buildMonthList(Date start, Date end);
	
	public List<String> buildDayList(Date start,Date end) throws ParseException;
}
