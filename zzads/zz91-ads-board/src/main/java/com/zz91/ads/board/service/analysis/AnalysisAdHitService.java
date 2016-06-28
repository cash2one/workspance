/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-21
 */
package com.zz91.ads.board.service.analysis;

import com.zz91.ads.board.domain.analysis.AnalysisAdHit;
import com.zz91.ads.board.dto.Pager;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-21
 */
public interface AnalysisAdHitService {

	public Pager<AnalysisAdHit> pageAdHitResult(Integer adPositionId,
			Long gmtTarget, Pager<AnalysisAdHit> page);
}
