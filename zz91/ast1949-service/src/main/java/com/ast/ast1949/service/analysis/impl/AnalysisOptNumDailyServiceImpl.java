/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-11
 */
package com.ast.ast1949.service.analysis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisOptNumDaily;
import com.ast.ast1949.persist.analysis.AnalysisOptNumDailyDao;
import com.ast.ast1949.service.analysis.AnalysisOptNumDailyService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-1-11
 */
@Component("analysisOptNumDailyService")
public class AnalysisOptNumDailyServiceImpl implements
		AnalysisOptNumDailyService {

	@Autowired
	AnalysisOptNumDailyDao analysisOptNumDailyDao;
	
	final static int DEFAULT_NUM = 1;
	
	@Override
	public void insertOptNum(String optCode, String account, Integer companyId) {
		Assert.notNull(optCode, "the opt code can not be null");
		Assert.notNull(account, "the account can not be null");
		
		Integer i = analysisOptNumDailyDao.queryOptNumTodayCount(optCode, account);
		if(i!=null && i.intValue()>0){
			//做更新操作
			analysisOptNumDailyDao.updateOptNumToday(optCode, account);
		}else{
			//做插入操作
			AnalysisOptNumDaily o = 
				new AnalysisOptNumDaily(companyId, account, optCode, DEFAULT_NUM, null, null, null);
			analysisOptNumDailyDao.insertOptNumToday(o);
		}
	}

	@Override
	public Integer queryOptNumByAccountToday(String optCode, String account) {
		Assert.notNull(optCode, "the opt code can not be null");
		Assert.notNull(account, "the account can not be null");
		Integer i = analysisOptNumDailyDao.queryOptNumByAccountToday(optCode, account);
		if(i==null){
			return 0;
		}
		return i;
	}

}
