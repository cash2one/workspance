/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.score.ScoreSummaryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreSummaryDto;
import com.ast.ast1949.persist.score.ScoreSummaryDao;
import com.ast.ast1949.service.score.ScoreSummaryService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-3-1
 */
@Component("scoreSummaryService")
public class ScoreSummaryServiceImpl implements ScoreSummaryService {

	@Autowired
	ScoreSummaryDao scoreSummaryDao;
	final static int DEFAULT_MAX_RECORD = 10;
	
	@Override
	public List<ScoreSummaryDto> queryMostOfUserScore(Integer max) {
		if(max==null){
			max=DEFAULT_MAX_RECORD;
		}
		return scoreSummaryDao.queryMostOfUserScore(max);
	}

	@Override
	public ScoreSummaryDo querySummaryByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		return scoreSummaryDao.querySummaryByCompanyId(companyId);
	}

	@Override
	public PageDto<ScoreSummaryDto> pageSummaryByCondictions(
			ScoreSummaryDto condictions, PageDto<ScoreSummaryDto> page) {
		Assert.notNull(page, "the object page can not be null" );
		page.setTotalRecords(scoreSummaryDao.querySummaryByCondictionsCount(condictions));
		page.setRecords(scoreSummaryDao.querySummaryByCondictions(condictions, page));
		return page;
	}

}
