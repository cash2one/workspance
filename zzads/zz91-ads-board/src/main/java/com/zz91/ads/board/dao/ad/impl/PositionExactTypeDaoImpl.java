/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.dao.ad.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.ad.PositionExactTypeDao;
import com.zz91.ads.board.domain.ad.PositionExactType;
import com.zz91.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("positionExactTypeDao")
public class PositionExactTypeDaoImpl extends BaseDaoSupport implements PositionExactTypeDao {

	final static String SQL_PERFIX = "positionExactType";

	@Override
	public Integer conut(Integer adPositionId, Integer exactTypeId) {
		Assert.notNull(adPositionId, "the adPositionId must not be null");
		Assert.notNull(exactTypeId, "the exactTypeId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("adPositionId", adPositionId);
		param.put("exactTypeId", exactTypeId);

		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PERFIX, "conut"), param);
	}

	@Override
	public Integer deletePositionExactTypeByExactTypeId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PERFIX, "deletePositionExactTypeByExactTypeId"), id);
	}

	@Override
	public Integer deletePositionExactTypeByPositionId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PERFIX, "deletePositionExactTypeByPositionId"), id);
	}

	@Override
	public Integer deletePositionExactTypeByPositionIdAndExactTypeId(Integer adPositionId,
			Integer exactTypeId) {
		Assert.notNull(adPositionId, "the adPositionId must not be null");
		Assert.notNull(exactTypeId, "the exactTypeId must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("adPositionId", adPositionId);
		param.put("exactTypeId", exactTypeId);

		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PERFIX, "deletePositionExactTypeByPositionIdAndExactTypeId"),
				param);
	}

	@Override
	public void insertPositionExactType(PositionExactType positionExactAds) {
		Assert.notNull(positionExactAds, "the object of positionExactAds must not be null");
		Assert.notNull(positionExactAds.getAdPositionId(), "the adPositionId must not be null");
		Assert.notNull(positionExactAds.getExactTypeId(), "the exactTypeId must not be null");

		getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PERFIX, "insertPositionExactType"),
				positionExactAds);
	}

}
