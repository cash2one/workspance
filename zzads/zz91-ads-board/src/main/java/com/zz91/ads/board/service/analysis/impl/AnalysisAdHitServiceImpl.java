/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-21
 */
package com.zz91.ads.board.service.analysis.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.analysis.AnalysisAdHitDao;
import com.zz91.ads.board.domain.analysis.AnalysisAdHit;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.analysis.AnalysisAdHitService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-21
 */
@Component("analysisAdHitService")
public class AnalysisAdHitServiceImpl implements AnalysisAdHitService {

	@Resource
	private AnalysisAdHitDao analysisAdHitDao;
	
	@Override
	public Pager<AnalysisAdHit> pageAdHitResult(Integer adPositionId,
			Long gmtTarget, Pager<AnalysisAdHit> page) {
		if(page.getSort()==null){
			page.setSort("num_hit");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		page.setRecords(analysisAdHitDao.queryAnalysis(adPositionId, gmtTarget, page));
		page.setTotals(analysisAdHitDao.queryAnalysisCount(adPositionId, gmtTarget));
		return page;
	}

}
