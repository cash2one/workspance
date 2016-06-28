/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-11
 */
package com.ast.ast1949.persist.analysis;

import com.ast.ast1949.domain.analysis.AnalysisOptNumDaily;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-1-11
 */
public interface AnalysisOptNumDailyDao {

	Integer insertOptNumToday(AnalysisOptNumDaily o);
	
	Integer updateOptNumToday(String optCode, String account);
	
	Integer queryOptNumTodayCount(String optCode, String account);
	
	Integer queryOptNumByAccountToday(String optCode, String account);
}
