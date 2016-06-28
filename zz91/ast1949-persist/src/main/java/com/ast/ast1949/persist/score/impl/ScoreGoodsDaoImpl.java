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

import com.ast.ast1949.domain.score.ScoreGoodsDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.score.ScoreGoodsDao;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("scoreGoodsDao")
public class ScoreGoodsDaoImpl extends BaseDaoSupport implements ScoreGoodsDao {

	final static String SQL_PERFIX = "scoreGoods";

	@Override
	public Integer insertGoods(ScoreGoodsDo goods) {
		Assert.notNull(goods, "the goods must not be null");
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PERFIX, "insertGoods"), goods);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreGoodsDo> queryHotScoreGoods(int max, String isHot) {
		Assert.notNull(max, "the max must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("max", max);
		param.put("isHot", isHot);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PERFIX, "queryHotScoreGoods"), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreGoodsDo> queryIndexScoreGoods(int max, String isHome) {
		Assert.notNull(max, "the max must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("max", max);
		param.put("isHome", isHome);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PERFIX, "queryIndexScoreGoods"), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreGoodsDo> queryMostConversionGoods(int max) {
		Assert.notNull(max, "the max must not be null");
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PERFIX, "queryMostConversionGoods"), max);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreGoodsDao#queryScoreGoodsByCategory
	 * (java.lang.String, int)
	 */
	// @Override
	// public List<ScoreGoodsDo> queryScoreGoodsByCategory(Integer category, int
	// max) {
	// Assert.notNull(category, "the category must not be null");
	// Assert.notNull(max, "the max must not be null");
	//
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("max", max);
	// param.put("category", category);
	//
	// return getSqlMapClientTemplate().queryForList(
	// addSqlKeyPreFix(SQL_PERFIX, "queryScoreGoodsByCategory"), param);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.persist.score.ScoreGoodsDao#queryScoreGoodsByCategory
	 * (java.lang.Integer, com.ast.ast1949.dto.PageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreGoodsDo> queryScoreGoodsByCategory(Integer category, PageDto page) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("category", category);
		param.put("page", page);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PERFIX, "queryScoreGoodsByCategory"), param);
	}

	@Override
	public Integer queryScoreGoodsByCategoryCount(Integer category) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("category", category);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PERFIX, "queryScoreGoodsByCategoryCount"), param);
	}

	@Override
	public Integer updateGoodsById(ScoreGoodsDo goods) {
		Assert.notNull(goods, "the goods must not be null");
		Assert.notNull(goods.getId(), "the id must not be null");

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PERFIX, "updateGoodsById"),
				goods);
	}

	@Override
	public Integer updateNumConversion(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PERFIX, "updateNumConversion"),
				id);
	}

	@Override
	public ScoreGoodsDo queryGoodsById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (ScoreGoodsDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PERFIX, "queryGoodsById"), id);
	}

	@Override
	public Integer deleteGoodsById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PERFIX, "deleteGoodsById"), id);
	}

}
