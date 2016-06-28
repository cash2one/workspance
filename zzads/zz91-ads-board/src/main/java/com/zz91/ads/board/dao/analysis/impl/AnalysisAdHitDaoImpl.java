/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-21
 */
package com.zz91.ads.board.dao.analysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.analysis.AnalysisAdHitDao;
import com.zz91.ads.board.domain.analysis.AnalysisAdHit;
import com.zz91.ads.board.dto.Pager;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-21
 */
@Component("analysisAdHitDao")
public class AnalysisAdHitDaoImpl extends BaseDaoSupport implements AnalysisAdHitDao {

	final static String SQL_PREFIX="analysisAdHit";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisAdHit> queryAnalysis(Integer adPositionId,
			Long gmtTarget, Pager<AnalysisAdHit> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("adPositionId", adPositionId);
		root.put("gmtTarget", gmtTarget);
		root.put("pager", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAnalysis"), root);
	}

	@Override
	public Integer queryAnalysisCount(Integer adPositionId, Long gmtTarget) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("adPositionId", adPositionId);
		root.put("gmtTarget", gmtTarget);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAnalysisCount"), root);
	}

}
