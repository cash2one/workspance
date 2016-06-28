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

import com.ast.ast1949.domain.score.ScoreExchangeRulesDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.score.ScoreExchangeRulesDao;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("ScoreExchangeRulesDao")
public class ScoreExchangeRulesDaoImp extends BaseDaoSupport implements ScoreExchangeRulesDao {

	final static String SQL_PREFIX = "scoreExchangeRules";

	@Override
	public ScoreExchangeRulesDo queryRulesByCode(String code) {
		if(code==null) {
			return null;
		}
		return (ScoreExchangeRulesDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryRulesByCode"), code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreExchangeRulesDo> queryRulesByPreCode(String preCode) {
		Assert.notNull(preCode, "the preCode must not be null");

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryRulesByPreCode"), preCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreExchangeRulesDo> queryRules(PageDto<ScoreExchangeRulesDo> page) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryRules"), param);
	}

	@Override
	public Integer queryRulesCount(PageDto<ScoreExchangeRulesDo> page) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", page);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryRulesCount"), param);
	}

}
