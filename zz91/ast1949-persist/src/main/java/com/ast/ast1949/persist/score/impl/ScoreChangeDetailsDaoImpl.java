/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-2.
 */
package com.ast.ast1949.persist.score.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.score.ScoreChangeDetailsDao;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("scoreChangeDetailsDao")
public class ScoreChangeDetailsDaoImpl extends BaseDaoSupport implements ScoreChangeDetailsDao {

	final static String SQL_PREFIX = "scoreChangeDetails";

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreChangeDetailsDao#
	 * deleteChangeDetailsByRelatedId(java.lang.Integer, java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public Integer deleteChangeDetailsByRelatedId(Integer companyId, String ruleCode,
			Integer relatedId) {
		Assert.notNull(companyId, "the companyId must not be null");
//		Assert.notNull(ruleCode, "the ruleCode must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("ruleCode", ruleCode);
		param.put("relatedId", relatedId);

		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteChangeDetailsByRelatedId"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreChangeDetailsDao#insertScoreDetails
	 * (com.ast.ast1949.domain.score.ScoreChangeDetailsDo)
	 */
	@Override
	public Integer insertScoreDetails(ScoreChangeDetailsDo details) {
		Assert.notNull(details, "the details must not be null");

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertScoreDetails"), details);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreChangeDetailsDao#queryChangeDetails
	 * (java.lang.Integer, java.lang.Boolean, com.ast.ast1949.dto.PageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreChangeDetailsDo> queryChangeDetails(Integer companyId, Boolean isPlus,
			PageDto page) {
		Assert.notNull(companyId, "the companyId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("isPlus", isPlus);
		param.put("page", page);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryChangeDetails"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreChangeDetailsDao#queryChangeDetailsCount
	 * (java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public Integer queryChangeDetailsCount(Integer companyId, Boolean isPlus) {
		Assert.notNull(companyId, "the companyId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("isPlus", isPlus);

		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryChangeDetailsCount"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreChangeDetailsDao#queryScoreByCycle
	 * (java.lang.Integer, java.lang.String, java.util.Date)
	 */
	@Override
	public Integer queryScoreByCycle(Integer companyId, String ruleCode, Date startTime) {
		Assert.notNull(companyId, "the companyId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("ruleCode", ruleCode);
		param.put("startTime", startTime);

		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryScoreByCycle"), param);
	}

}
