package com.ast.ast1949.service.site.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisCsRenewal;
import com.ast.ast1949.persist.site.AnalysisCsRenewalDao;
import com.ast.ast1949.service.site.AnalysisCsRenewalService;
import com.zz91.util.datetime.DateUtil;
@Component("analysisCsRenewalService")
public class AnalysisCsRenewalServiceImpl implements AnalysisCsRenewalService{
	
	@Autowired
	private AnalysisCsRenewalDao analysisCsRenewalDao;
	
	final static String MONTH_DATE="yyyy-MM";

	@Override
	public Map<String, List<AnalysisCsRenewal>> queryAnalysisCsRenewal(String account, Date start, Date end) {
		
		List<AnalysisCsRenewal> list=analysisCsRenewalDao.queryAnalysisCsRenewal(account, start, end);
		
		Map<String, List<AnalysisCsRenewal>> resultMap=new HashMap<String, List<AnalysisCsRenewal>>();
		
		for(AnalysisCsRenewal obj:list){
			List<AnalysisCsRenewal> monthList=resultMap.get(DateUtil.toString(obj.getGmtTarget(), MONTH_DATE));
			if(monthList==null){
				monthList=new ArrayList<AnalysisCsRenewal>();
				resultMap.put(DateUtil.toString(obj.getGmtTarget(), MONTH_DATE), monthList);
			}
			monthList.add(obj);
		}
		
		
		
		return resultMap;
	}

	@Override
	public List<String> buildMonthList(Date start, Date end) {
		
		if(start==null){
			start=DateUtil.getLastMonthFistDay();
		}
		
		if(end==null){
			end=start;
		}
		
		int month=DateUtil.getIntervalMonths(start, end);
		List<String> monthList=new ArrayList<String>();
		
		for(int i=0;i<=month;i++){
			monthList.add(DateUtil.toString(DateUtil.getDateAfterMonths(start, i), MONTH_DATE));
		}
		
		return monthList;
	}
	@Override
	public List<String> buildDayList(Date start, Date end) throws ParseException {
		
		if(start==null){
			start=DateUtil.getDate(new Date(), "yyyy-MM-dd");	
		
		}
		if(end==null){
			Date ends=DateUtil.getDateAfterDays(new Date(), 1);
			end=DateUtil.getDate(ends, "yyyy-MM-dd");
		}
		
		int day=DateUtil.getIntervalDays(end, start);
		List<String> dayList=new ArrayList<String>();
		
		for(int i=0;i<=day;i++){
			dayList.add(DateUtil.toString(DateUtil.getDateAfterDays(start, i), "yyyy-MM-dd"));
		}
		
		return dayList;
	}
	
}
