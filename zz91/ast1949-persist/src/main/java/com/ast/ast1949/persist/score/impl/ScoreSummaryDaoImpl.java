/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-2.
 */
package com.ast.ast1949.persist.score.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.score.ScoreSummaryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreSummaryDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.score.ScoreSummaryDao;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("scoreSummaryDao")
public class ScoreSummaryDaoImpl extends BaseDaoSupport implements ScoreSummaryDao {
	final static String SQL_PERFIX = "scoreSummary";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreSummaryDao#insertSummary(com.ast.ast1949
	 * .domain.score.ScoreSummaryDo)
	 */
	@Override
	public Integer insertSummary(ScoreSummaryDo summary) {
		Assert.notNull(summary, "the summary must not be null");
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PERFIX, "insertSummary"), summary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreSummaryDao#queryMostOfUserScore(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreSummaryDto> queryMostOfUserScore(int max) {
		Assert.notNull(max, "the max must not be null");
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PERFIX, "queryMostOfUserScore"), max);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreSummaryDao#querySummaryByCompanyId
	 * (java.lang.Integer)
	 */
	@Override
	public ScoreSummaryDo querySummaryByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "the companyId must not be null");
		return (ScoreSummaryDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PERFIX, "querySummaryByCompanyId"), companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreSummaryDao#querySummaryByCondictions
	 * (com.ast.ast1949.dto.score.ScoreSummaryDto, com.ast.ast1949.dto.PageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreSummaryDto> querySummaryByCondictions(ScoreSummaryDto condictions, PageDto page) {
		Assert.notNull(condictions, "the condictions must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("condictions", condictions);
		param.put("page", page);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PERFIX, "querySummaryByCondictions"), param);
	}

	@Override
	public Integer querySummaryByCondictionsCount(ScoreSummaryDto condictions) {
		Assert.notNull(condictions, "the condictions must not be null");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("condictions", condictions);
		
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PERFIX, "querySummaryByCondictionsCount"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreSummaryDao#updateSummary(com.ast.ast1949
	 * .domain.score.ScoreSummaryDo)
	 */
	@Override
	public Integer updateSummary(ScoreSummaryDo summary) {
		Assert.notNull(summary, "the summary must not be null");
		Assert.notNull(summary.getId(), "the id must not be null");

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PERFIX, "updateSummary"),
				summary);
	}

}
