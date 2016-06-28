/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.dao.ad.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.ad.AdExactTypeDao;
import com.zz91.ads.board.domain.ad.AdExactType;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("adExactTypeDao")
public class AdExactTypeDaoImpl extends BaseDaoSupport implements AdExactTypeDao {

	final static String sqlPreFix = "adExactType";

	@Override
	public Integer deleteAdExactTypeByAdId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(sqlPreFix, "deleteAdExactTypeByAdId"), id);
	}
	@Override
	public Integer insertAdExactType(AdExactType adExactType) {
		Assert.notNull(adExactType, "the object of adExactType must not be null");
		Assert.notNull(adExactType.getAdId(), "the adId must not be null");
		Assert.notNull(adExactType.getExactTypeId(), "the exactTypeId must not be null");
		Assert.notNull(adExactType.getAnchorPoint(), "the anchorPoint must not be null");

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(sqlPreFix, "insertAdExactType"), adExactType);
	}
	@Override
	public Integer queryAdIdById(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryAdIdById"), id);
	}

}
