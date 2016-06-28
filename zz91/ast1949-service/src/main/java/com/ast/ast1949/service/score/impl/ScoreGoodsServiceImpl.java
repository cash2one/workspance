/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.service.score.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.score.ScoreGoodsDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.score.ScoreGoodsDao;
import com.ast.ast1949.service.score.ScoreGoodsService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
@Component("scoreGoodsService")
public class ScoreGoodsServiceImpl implements ScoreGoodsService {

	@Autowired
	ScoreGoodsDao scoreGoodsDao;

	final static int DEFAULT_MAX_RECORD = 10;
	
	final static String IS_HOME_TRUE="Y";
	final static String IS_HOME_FALSE="N";
	
	final static String IS_HOT_TRUE="Y";
	final static String IS_HOT_FALSE="N";

	@Override
	public Integer insertGoods(ScoreGoodsDo goods) {
		Assert.notNull(goods, "the goods can not be null");
		return scoreGoodsDao.insertGoods(goods);
	}

	@Override
	public List<ScoreGoodsDo> queryHotScoreGoods(Integer max) {
		if (max == null) {
			max = DEFAULT_MAX_RECORD;
		}
		return scoreGoodsDao.queryHotScoreGoods(max, "Y");
	}

	@Override
	public List<ScoreGoodsDo> queryIndexScroeGoods(Integer max) {
		if (max == null) {
			max = DEFAULT_MAX_RECORD;
		}
		return scoreGoodsDao.queryIndexScoreGoods(max, "Y");
	}

	@Override
	public List<ScoreGoodsDo> queryMostConversionGoods(Integer max) {
		if (max == null) {
			max = DEFAULT_MAX_RECORD;
		}
		return scoreGoodsDao.queryMostConversionGoods(max);
	}

	@Override
	public Integer updateGoodsById(ScoreGoodsDo goods) {
		Assert.notNull(goods, "the goods can not be null");
		Assert.notNull(goods.getId(), "the goods.id can not be null");
		
		if(goods.getIsHome()==null||!IS_HOME_TRUE.equals(goods.getIsHome())) {
			goods.setIsHome(IS_HOME_FALSE);
		}
		
		if(goods.getIsHot()==null||!IS_HOT_TRUE.equals(goods.getIsHot())) {
			goods.setIsHot(IS_HOT_FALSE);
		}
		
		// TODO 更新前删除原商品图片
		return scoreGoodsDao.updateGoodsById(goods);
	}

	@Override
	public PageDto<ScoreGoodsDo> pageScoreGoodsByCategory(Integer category,
			PageDto<ScoreGoodsDo> page) {
		Assert.notNull(page, "the object page can not be null");
		page.setTotalRecords(scoreGoodsDao
				.queryScoreGoodsByCategoryCount(category));
		page
				.setRecords(scoreGoodsDao.queryScoreGoodsByCategory(category,
						page));
		return page;
	}

	@Override
	public ScoreGoodsDo queryGoodsById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return scoreGoodsDao.queryGoodsById(id);
	}

	@Override
	public Integer deleteGoodsById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return scoreGoodsDao.deleteGoodsById(id);
	}

//	@Override
//	public List<ScoreGoodsDo> queryScoreGoodsByCategory(Integer category,
//			Integer max) {
//		if(max==null){
//			max=DEFAULT_MAX_RECORD;
//		}
//		return scoreGoodsDao.queryScoreGoodsByCategory(category, max);
//	}

}
