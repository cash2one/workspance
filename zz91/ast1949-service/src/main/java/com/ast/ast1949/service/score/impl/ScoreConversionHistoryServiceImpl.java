/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.domain.score.ScoreGoodsDo;
import com.ast.ast1949.domain.score.ScoreSummaryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreConversionHistoryDto;
import com.ast.ast1949.persist.score.ScoreConversionHistoryDao;
import com.ast.ast1949.persist.score.ScoreGoodsDao;
import com.ast.ast1949.persist.score.ScoreSummaryDao;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.score.ScoreConversionHistoryService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
@Component("scoreConversionHistoryService")
public class ScoreConversionHistoryServiceImpl implements ScoreConversionHistoryService {

	@Autowired
	ScoreSummaryDao scoreSummaryDao;
	@Autowired
	ScoreConversionHistoryDao scoreConversionHistoryDao;
	@Autowired
	ScoreChangeDetailsService scoreChangeDetailsService;
	@Autowired
	ScoreGoodsDao scoreGoodsDao;

	final static String CATEGORY_SCORE = "0";
	final static String CONVERSION_SUCCESS = "1";
	final static String CONVERSION_FAILURE = "2";
	final static int DEFAULT_MAX_RECORD = 10;

	@Override
	public Integer insertConversionByCompany(ScoreConversionHistoryDo history) {
		Assert.notNull(history, "the object history can not be null");
		history.setStatus("0");
		return scoreConversionHistoryDao.insertConversionHistoryByCompany(history);
	}

	@Override

	public PageDto<ScoreConversionHistoryDto> pageConversionHistoryByCompanyId(
			Integer companyId, String status,
			PageDto<ScoreConversionHistoryDto> page) {
		Assert.notNull(page, "the object page can not be null ");
		Assert.notNull(companyId, "the companyId can not be null");
		page.setTotalRecords(scoreConversionHistoryDao.queryConversionHistoryByCompanyIdCount(
				companyId, status));
		page.setRecords(scoreConversionHistoryDao.queryConversionHistoryByCompanyId(companyId,
				status, page));
		return page;
	}

	@Override
	public PageDto<ScoreConversionHistoryDto> pageConversionHistoryByGoodsId(Integer goodsId,
			String status, PageDto<ScoreConversionHistoryDto> page) {
		Assert.notNull(page, "the object page can not be null ");
		Assert.notNull(goodsId, "the goodsId can not be null");
		page.setTotalRecords(scoreConversionHistoryDao.queryConversionHistoryByGoodsIdCount(
				goodsId, status));
		page.setRecords(scoreConversionHistoryDao.queryConversionHistoryByGoodsId(goodsId, status,
				page));
		return page;
	}

	@Override
	public PageDto<ScoreConversionHistoryDto> pageConversionHistoryWithGoods(
			ScoreConversionHistoryDo conversion, PageDto<ScoreConversionHistoryDto> page) {
		Assert.notNull(page, "the object page can not be null ");
		page.setTotalRecords(scoreConversionHistoryDao
				.queryConversionHistoryWithGoodsCount(conversion));
		page
				.setRecords(scoreConversionHistoryDao.queryConversionHistoryWithGoods(conversion,
						page));
		return page;
	}

	@Override
	public ScoreConversionHistoryDo queryConversionHistoryById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return scoreConversionHistoryDao.queryConversionHistoryById(id);
	}

	@Override
	public List<ScoreConversionHistoryDto> queryRecentConversionHistory(Integer max, String status) {
		if (max == null) {
			max = DEFAULT_MAX_RECORD;
		}
		return scoreConversionHistoryDao.queryRecentConversionHistory(max, status);
	}

	@Override
	public Integer updateConversionFailure(Integer id, String remark) {
		Assert.notNull(id, "the id can not be null");
		return scoreConversionHistoryDao.updateConversionHistoryStatusById(id, CONVERSION_FAILURE,
				remark);
	}

	@Override
	public Integer updateConversionSuccess(Integer id, String remark) {
		Assert.notNull(id, "the id can not be null");
		do {
			ScoreConversionHistoryDo history = scoreConversionHistoryDao
					.queryConversionHistoryById(id);
			if (history == null) {
				break;
			}

			ScoreGoodsDo goods = scoreGoodsDao.queryGoodsById(history.getScoreGoodsId());
			if (goods == null) {
				break;
			}

			if (CATEGORY_SCORE.equals(history.getConversionCategory())) {
				// 判断积分是否足够支付
				ScoreSummaryDo summary = scoreSummaryDao.querySummaryByCompanyId(history
						.getCompanyId());
				if (summary == null) {
					break;
				}

				if (summary.getScore().intValue() < goods.getScoreBuy().intValue()) {
					break;
				}

				ScoreChangeDetailsDo details = new ScoreChangeDetailsDo();
				details.setCompanyId(history.getCompanyId());
				details.setScore(-goods.getScoreBuy());
				details.setName(goods.getName());
				details.setRelatedId(id);
				scoreChangeDetailsService.saveChangeDetails(details);
			}
			// 改变申请单状态，记录备注
			scoreConversionHistoryDao.updateConversionHistoryStatusById(id, CONVERSION_SUCCESS,
					remark);
			// 增加商品成功兑换数量
			scoreGoodsDao.updateNumConversion(goods.getId());
			return 1;
		} while (false);

		return null;
	}

}
