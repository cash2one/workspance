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

import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreConversionHistoryDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.score.ScoreConversionHistoryDao;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("scoreConversionHistoryDao")
public class ScoreConversionHistoryDaoImpl extends BaseDaoSupport implements
		ScoreConversionHistoryDao {

	final static String SQL_PREFIX = "scoreConversionHistory";

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreConversionHistoryDao#
	 * insertConversionHistoryByCompany
	 * (com.ast.ast1949.domain.score.ScoreConversionHistoryDo)
	 */
	@Override
	public Integer insertConversionHistoryByCompany(ScoreConversionHistoryDo conversion) {
		Assert.notNull(conversion, "the conversion must not be null");

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertConversionHistoryByCompany"), conversion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreConversionHistoryDao#
	 * queryConversionHistoryByCompanyId(java.lang.Integer, java.lang.String,
	 * com.ast.ast1949.dto.PageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreConversionHistoryDto> queryConversionHistoryByCompanyId(Integer companyId,
			String status, PageDto page) {
		Assert.notNull(companyId, "the companyId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("status", status);
		param.put("page", page);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryConversionHistoryByCompanyId"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreConversionHistoryDao#
	 * queryConversionHistoryByCompanyIdCount(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public Integer queryConversionHistoryByCompanyIdCount(Integer companyId, String status) {
		Assert.notNull(companyId, "the companyId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("status", status);

		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryConversionHistoryByCompanyIdCount"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreConversionHistoryDao#
	 * queryConversionHistoryByGoodsId(java.lang.Integer, java.lang.String,
	 * com.ast.ast1949.dto.PageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreConversionHistoryDto> queryConversionHistoryByGoodsId(Integer goodsId,
			String status, PageDto page) {
		Assert.notNull(goodsId, "the goodsId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("goodsId", goodsId);
		param.put("status", status);
		param.put("page", page);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryConversionHistoryByGoodsId"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreConversionHistoryDao#
	 * queryConversionHistoryByGoodsIdCount(java.lang.Integer, java.lang.String)
	 */
	@Override
	public Integer queryConversionHistoryByGoodsIdCount(Integer goodsId, String status) {
		Assert.notNull(goodsId, "the goodsId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("goodsId", goodsId);
		param.put("status", status);

		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryConversionHistoryByGoodsIdCount"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreConversionHistoryDao#
	 * queryConversionHistoryById(java.lang.Integer)
	 */
	@Override
	public ScoreConversionHistoryDo queryConversionHistoryById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (ScoreConversionHistoryDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryConversionHistoryById"), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreConversionHistoryDao#
	 * queryConversionHistoryWithGoods
	 * (com.ast.ast1949.domain.score.ScoreConversionHistoryDo,
	 * com.ast.ast1949.dto.PageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreConversionHistoryDto> queryConversionHistoryWithGoods(
			ScoreConversionHistoryDo conversion, PageDto page) {
		Assert.notNull(conversion, "the conversion must not be null");
		// TODO:其他条件验证

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("conversion", conversion);
		param.put("page", page);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryConversionHistoryWithGoods"), param);
	}

	@Override
	public Integer queryConversionHistoryWithGoodsCount(ScoreConversionHistoryDo conversion) {
		Assert.notNull(conversion, "the conversion must not be null");
		// TODO:其他条件验证
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("conversion", conversion);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryConversionHistoryWithGoodsCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreConversionHistoryDto> queryRecentConversionHistory(int max, String status) {
		Assert.notNull(max, "the max must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("max", max);
		param.put("status", status);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryRecentConversionHistory"), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ast.ast1949.persist.score.ScoreConversionHistoryDao#
	 * updateConversionHistoryStatusById(java.lang.Integer, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer updateConversionHistoryStatusById(Integer id, String status, String remark) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(status, "the status must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", status);
		param.put("remark", remark);

		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateConversionHistoryStatusById"), param);
	}

}
