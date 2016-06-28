/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.score.ScoreExchangeRulesDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.score.ScoreExchangeRulesDao;
import com.ast.ast1949.service.score.ScoreExchangeRulesService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
@Component("scoreExchangeRulesService")
public class ScoreExchangeRulesServiceImpl implements ScoreExchangeRulesService {

	@Autowired
	ScoreExchangeRulesDao scoreExchangeRulesDao;

	@Override
	public List<ScoreExchangeRulesDo> queryRulesByPreCode(String preCode) {
		return scoreExchangeRulesDao.queryRulesByPreCode(preCode);
	}

	@Override
	public PageDto<ScoreExchangeRulesDo> pageRules(PageDto<ScoreExchangeRulesDo> page) {
		Assert.notNull(page, "the object page can not be null ");

		page.setTotalRecords(scoreExchangeRulesDao.queryRulesCount(page));
		page.setRecords(scoreExchangeRulesDao.queryRules(page));

		return page;
	}

}
