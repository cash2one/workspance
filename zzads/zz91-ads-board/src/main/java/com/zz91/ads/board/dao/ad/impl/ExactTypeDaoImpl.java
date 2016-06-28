/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.dao.ad.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.ad.ExactTypeDao;
import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.dto.Pager;
import com.zz91.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("exactTypeDao")
public class ExactTypeDaoImpl extends BaseDaoSupport implements ExactTypeDao {

	final static String sqlPreFix = "exactType";

	@Override
	public Integer deleteExactTypeById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix, "deleteExactTypeById"),
				id);
	}

	@Override
	public Integer insertExactType(ExactType exactType) {
		Assert.notNull(exactType, "the object of exactType must not be null");
		Assert.notNull(exactType.getExactName(), "the name must not be null");

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(sqlPreFix, "insertExactType"), exactType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExactType> queryExactType(Pager<ExactType> pager) {
		Assert.notNull(pager, "the pager must not be null");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pager", pager);
		
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryExactType"),
				param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExactType> queryExactTypeByAdPositionId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryExactTypeByAdPositionId"), id);
	}

	@Override
	public ExactType queryExactTypeById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return (ExactType) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryExactTypeById"), id);
	}

	@Override
	public Integer queryExactTypeCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryExactTypeCount"));
	}

	@Override
	public ExactType queryExactTypeByExactName(String name) {
		Assert.notNull(name, "the name must not be null");

		return (ExactType) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryExactTypeByExactName"), name);
	}

}
