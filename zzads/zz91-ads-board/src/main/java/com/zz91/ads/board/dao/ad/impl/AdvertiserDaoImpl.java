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
import com.zz91.ads.board.dao.ad.AdvertiserDao;
import com.zz91.ads.board.domain.ad.Advertiser;
import com.zz91.ads.board.dto.Pager;
import com.zz91.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("advertiserDao")
public class AdvertiserDaoImpl extends BaseDaoSupport implements AdvertiserDao {

	final static String sqlPreFix = "advertiser";

	@Override
	public Integer insertAdvertiser(Advertiser advertiser) {
		Assert.notNull(advertiser, "the object of advertiser must not be null");
		Assert.notNull(advertiser.getName(), "the name must not be null");
		if(advertiser.getDeleted()==null){
			advertiser.setDeleted(AdvertiserDao.DELETE_FALSE);
		}

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(sqlPreFix, "insertAdvertiser"), advertiser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Advertiser> queryAdvertiserByConditions(Advertiser advertiser,
			Pager<Advertiser> pager) {
		Assert.notNull(pager, "the pager must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("advertiser", advertiser);
		param.put("pager", pager);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryAdvertiserByConditions"), param);
	}

	@Override
	public Integer queryAdvertiserByConditionsCount(Advertiser advertiser) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("advertiser", advertiser);
		
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryAdvertiserByConditionsCount"), param);
	}

	@Override
	public Advertiser queryAdvertiserById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (Advertiser) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryAdvertiserById"), id);
	}

	@Override
	public String queryEmailByAdvertiserId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryEmailByAdvertiserId"), id);
	}

	@Override
	public Integer signDeleted(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "signDeleted"), id);
	}

	@Override
	public Integer updateAdvertiser(Advertiser advertiser) {
		Assert.notNull(advertiser, "the object of advertiser must not be null");
		Assert.notNull(advertiser.getId(), "the id must not be null");
		Assert.notNull(advertiser.getName(), "the name must not be null");

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateAdvertiser"),
				advertiser);
	}

	@Override
	public Integer queryIdByEmail(String email) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryIdByEmail"), email);
	}

}
