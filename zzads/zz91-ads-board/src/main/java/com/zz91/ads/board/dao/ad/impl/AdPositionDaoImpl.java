/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.dao.ad.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.ad.AdPositionDao;
import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.dto.ad.AdPositionDto;
import com.zz91.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("adPositionDao")
public class AdPositionDaoImpl extends BaseDaoSupport implements AdPositionDao {

	final static String sqlPreFix = "adPosition";

	@Override
	public Integer countAdPositionNodesByParentId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "countAdPositionNodesByParentId"), id);
	}

	@Override
	public Integer insertAdPosition(AdPosition adPosition) {
		Assert.notNull(adPosition, "the object of adPosition must not be null");
		Assert.notNull(adPosition.getName(), "the name must not be null");

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(sqlPreFix, "insertAdPosition"), adPosition);
	}

	@Override
	public AdPosition queryAdPositionById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return (AdPosition) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryAdPositionById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdPosition> queryAdPositionByParentId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryAdPositionByParentId"), id);
	}

	@Override
	public AdPositionDto queryAdPositionDtoById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return (AdPositionDto) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryAdPositionDtoById"), id);
	}

	@Override
	public Integer signDelete(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "signDelete"), id);
	}

	@Override
	public Integer updateAdPosition(AdPosition adPosition) {
		Assert.notNull(adPosition, "the object of adPosition must not be null");
		Assert.notNull(adPosition.getId(), "the id must not be null");
		Assert.notNull(adPosition.getName(), "the name must not be null");
		Assert.notNull(adPosition.getSequence(), "the sequence must not be null");

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateAdPosition"),
				adPosition);
	}

	@Override
	public AdPositionDto queryAdPositionDtoByIdForEdit(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return (AdPositionDto) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryAdPositionDtoByIdForEdit"), id);
	}

}
