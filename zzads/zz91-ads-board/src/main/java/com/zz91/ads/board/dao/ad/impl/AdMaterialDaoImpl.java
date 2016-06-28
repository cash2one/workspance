/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.dao.ad.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.ad.AdMaterialDao;
import com.zz91.ads.board.domain.ad.AdMaterial;
import com.zz91.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("adMaterialDao")
public class AdMaterialDaoImpl extends BaseDaoSupport implements AdMaterialDao {

	final static String sqlPreFix = "adMaterial";

	@Override
	public Integer deleteAdMaterialByAdId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(sqlPreFix, "deleteAdMaterialByAdId"), id);
	}

	@Override
	public Integer deleteAdMaterialById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix, "deleteAdMaterialById"),
				id);
	}

	@Override
	public Integer insertAdMaterial(AdMaterial adMaterial) {
		Assert.notNull(adMaterial, "the object of adMaterial must not be null");
		Assert.notNull(adMaterial.getName(), "the name must not be null");

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(sqlPreFix, "insertAdMaterial"), adMaterial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdMaterial> queryAdMaterialByAdId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryAdMaterialByAdId"), id);
	}

}
