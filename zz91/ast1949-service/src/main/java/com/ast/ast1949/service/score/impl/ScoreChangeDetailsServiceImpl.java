/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.score.ScoreExchangeRulesDo;
import com.ast.ast1949.domain.score.ScoreSummaryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.score.ScoreChangeDetailsDao;
import com.ast.ast1949.persist.score.ScoreExchangeRulesDao;
import com.ast.ast1949.persist.score.ScoreSummaryDao;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.StringUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
@Component("scoreChangeDetailsService")
public class ScoreChangeDetailsServiceImpl implements ScoreChangeDetailsService {

	@Autowired
	ScoreExchangeRulesDao scoreExchangeRulesDao;
	@Autowired
	ScoreChangeDetailsDao scoreChangeDetailsDao;
	@Autowired
	ScoreSummaryDao scoreSummaryDao;

	@Override
	public PageDto<ScoreChangeDetailsDo> pageChangeDetailsByCompanyId(
			Integer companyId, Boolean isPlus,
			PageDto<ScoreChangeDetailsDo> page) {
		Assert.notNull(page, "the object page can not be null");
		Assert.notNull(companyId, "the companyId can not be null");
		if (page.getSort() == null) {
			page.setSort("gmt_created");
		}
		page.setTotalRecords(scoreChangeDetailsDao.queryChangeDetailsCount(
				companyId, isPlus));
		page.setRecords(scoreChangeDetailsDao.queryChangeDetails(companyId,
				isPlus, page));
		return page;
	}

	@Override
	public Integer saveChangeDetails(ScoreChangeDetailsDo details) {
		Assert.notNull(details, "the object details can not be null");
		Assert.notNull(details.getCompanyId(),
				"the details.companyId can not be null");

		do {
			ScoreExchangeRulesDo rules = null;
			if (details.getRulesCode() != null) {
				rules = scoreExchangeRulesDao.queryRulesByCode(details
						.getRulesCode());
			} else {
				details.setRulesCode("");
			}

			if (rules == null) {
				if (StringUtils.isEmpty(details.getName())
						|| details.getScore() == null) {
					break;
				}
			}

			if (rules != null) {
				if (rules.getCycleDay().intValue() >= 0
						&& rules.getScoreMax().intValue() > 0) {
					// 判断周期内已加分值
					Date cycleStart = null;
					if (rules.getCycleDay().intValue() > 0) {
						cycleStart = DateUtil.getDateAfterDays(new Date(),
								-rules.getCycleDay().intValue());
					}
					Integer sumScore = scoreChangeDetailsDao.queryScoreByCycle(details.getCompanyId(), details.getRulesCode(),cycleStart);
					if (sumScore != null) {
						if (rules.getScoreMax().intValue() <= sumScore.intValue()) {
							break;
						}
					}
				}
				if (details.getRelatedId() != null&& details.getRelatedId().intValue() > 0) {
					// 删除原变更
					scoreChangeDetailsDao.deleteChangeDetailsByRelatedId(details.getCompanyId(), details.getRulesCode(),details.getRelatedId());
				} else {
					details.setRelatedId(0);
				}
				if (rules.getScore() != null && rules.getScore() > 0 && details.getScore()==null) {
					details.setScore(rules.getScore());
				}
				details.setName(rules.getName());
			}

			Integer detailsId = scoreChangeDetailsDao
					.insertScoreDetails(details);

			ScoreSummaryDo summary = scoreSummaryDao
					.querySummaryByCompanyId(details.getCompanyId());
			if (summary == null) {
				summary = new ScoreSummaryDo();
				summary.setCompanyId(details.getCompanyId());
				summary.setScore(details.getScore());
				scoreSummaryDao.insertSummary(summary);
			} else {
				summary.setScore(summary.getScore().intValue() + details.getScore().intValue());
				scoreSummaryDao.updateSummary(summary);
			}

			return detailsId;
		} while (false);

		return null;
	}
}
