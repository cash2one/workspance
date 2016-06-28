package com.ast.ast1949.service.analysis.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisTrustCrmDto;
import com.ast.ast1949.domain.analysis.AnalysisTrustLog;
import com.ast.ast1949.persist.analysis.AnalysisTrustLogDao;
import com.ast.ast1949.persist.trust.TrustCrmDao;
import com.ast.ast1949.service.analysis.AnalysisTrustLogService;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("analysisTrustService")
public class AnalysisTrustServiceImpl implements AnalysisTrustLogService{

	@Resource
	private AnalysisTrustLogDao analysisTrustLogDao;
	@Resource
	private TrustCrmDao trustCrmDao;
	
	@Override
	public List<AnalysisTrustLog> queryByCondition(String from ,String to,String account) {
		if (StringUtils.isEmpty(from)) {
			try {
				from = DateUtil.toString(DateUtil.getDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
			} catch (ParseException e) {
				from = new Date().toString();
			}
		}
		if (StringUtils.isEmpty(to)) {
			to = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd");
		}else{
			try {
				to = DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(to, "yyyy-MM-dd"),1), "yyyy-MM-dd");
			} catch (ParseException e) {
				to = new Date().toString();
			}
		}
		
		return analysisTrustLogDao.queryByCondition(from,to,account);
	}

	@Override
	public List<AnalysisTrustCrmDto> queryDayLog() {
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(CS_DEPT_CODE);
		List<AnalysisTrustCrmDto> list =new ArrayList<AnalysisTrustCrmDto>();
		for (String key:map.keySet()) {
			AnalysisTrustCrmDto dto = new AnalysisTrustCrmDto();
			if (StringUtils.isEmpty(key)) {
				continue;
			}
			dto.setAccount(key);
			Map<String , Object> map1 =new HashMap<String, Object>();
			map1.put("account", key);
			map1.put("star", 1);
			dto.setNum1(trustCrmDao.selectDayLog(map1));
			Map<String , Object> map2 =new HashMap<String, Object>();
			map2.put("account", key);
			map2.put("star", 2);
			dto.setNum2(trustCrmDao.selectDayLog(map2));
			Map<String , Object> map3 =new HashMap<String, Object>();
			map3.put("account", key);
			map3.put("star", 3);
			dto.setNum3(trustCrmDao.selectDayLog(map3));
			Map<String , Object> map4 =new HashMap<String, Object>();
			map4.put("account", key);
			map4.put("star", 4);
			dto.setNum4(trustCrmDao.selectDayLog(map4));
			Map<String , Object> map5 =new HashMap<String, Object>();
			map5.put("account", key);
			map5.put("star", 5);
			dto.setNum5(trustCrmDao.selectDayLog(map5));
			dto.setNumAll(dto.getNum1()+dto.getNum2()+dto.getNum3()+dto.getNum4()+dto.getNum5());
			Map<String , Object> mapNew =new HashMap<String, Object>();
			mapNew.put("account", key);
			mapNew.put("isNew", true);
			dto.setNumNew(trustCrmDao.selectDayLog(mapNew));
			Map<String , Object> mapLost =new HashMap<String, Object>();
			mapLost.put("account", key);
			mapLost.put("isLost", true);
			dto.setNumLost(trustCrmDao.selectDayLog(mapLost));
			Map<String , Object> mapToday =new HashMap<String, Object>();
			mapToday.put("account", key);
			mapToday.put("isToday", true);
			dto.setNumToday(trustCrmDao.selectDayLog(mapLost));
			Map<String , Object> mapTomorrow =new HashMap<String, Object>();
			mapTomorrow.put("account", key);
			mapTomorrow.put("isTomorrow", true);
			dto.setNumTomorrow(trustCrmDao.selectDayLog(mapTomorrow));
			list.add(dto);
		}
		return list;
	}
	
}
