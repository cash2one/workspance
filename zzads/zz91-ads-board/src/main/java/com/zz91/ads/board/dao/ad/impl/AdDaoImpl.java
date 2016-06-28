/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.dao.ad.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.ad.AdDao;
import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.dto.ad.ExactTypeDto;
import com.zz91.ads.board.utils.AdConst;
import com.zz91.util.Assert;
import com.zz91.util.datetime.DateUtil;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("adDao")
public class AdDaoImpl extends BaseDaoSupport implements AdDao {

	final static String sqlPreFix = "ad";

	@Override
	public Integer deleteAdById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix, "deleteAdById"), id);
	}

	@Override
	public Integer insertAd(Ad ad) {
		Assert.notNull(ad, "the object of ad must not be null");
		Assert.notNull(ad.getPositionId(), "the positionId must not be null");
		Assert.notNull(ad.getAdvertiserId(), "the advertiserId must not be null");
		Assert.notNull(ad.getAdTitle(), "the adTitle must not be null");
		Assert.notNull(ad.getAdTargetUrl(), "the adTargetUrl must not be null");
		Assert.notNull(ad.getApplicant(), "the Applicant must not be null");
		
		if(ad.getGmtPlanEnd()!=null){
			String planEnd=DateUtil.toString(ad.getGmtPlanEnd(), AdConst.FORMAT_DATE);
			try {
				ad.setGmtPlanEnd(DateUtil.getDate(planEnd+" 23:59:59", AdConst.FORMAT_DATE_TIME));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insertAd"),
				ad);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdDto> queryAdByConditions(Ad ad, AdSearchDto adSearch, Pager<AdDto> pager) {
		Assert.notNull(pager, "the pager must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ad", ad);
		param.put("search", adSearch);
		param.put("pager", pager);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryAdByConditions"), param);
	}

	@Override
	public Integer queryAdByConditionsCount(Ad ad, AdSearchDto adSearch) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ad", ad);
		param.put("search", adSearch);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryAdByConditionsCount"), param);
	}

	@Override
	public Ad queryAdById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return (Ad) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "qureyAdById"), id);
	}

	@Override
	public Integer updateAd(Ad ad) {
		Assert.notNull(ad, "the object of ad must not be null");
		Assert.notNull(ad.getAdTitle(), "the adTitle must not be null");
		Assert.notNull(ad.getAdTargetUrl(), "the adTargetUrl must not be null");
		if(ad.getGmtPlanEnd()!=null){
			String planEnd=DateUtil.toString(ad.getGmtPlanEnd(), AdConst.FORMAT_DATE);
			try {
				ad.setGmtPlanEnd(DateUtil.getDate(planEnd+" 23:59:59", AdConst.FORMAT_DATE_TIME));
			} catch (ParseException e) {
				return 0;
			}
		}
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateAd"), ad);
	}

	@Override
	public Integer updateAdByDesigner(Ad ad) {
		Assert.notNull(ad, "the object of ad must not be null");
		Assert.notNull(ad.getAdTitle(), "the adTitle must not be null");
		Assert.notNull(ad.getAdTargetUrl(), "the adTargetUrl must not be null");

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateAdByDesigner"),
				ad);
	}

	@Override
	public Integer updateDesignStatus(Integer id, String status) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(status, "the status must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", status);

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateDesignStatus"),
				param);
	}

	@Override
	public Integer updateOnlineStatus(Integer id, String status) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(status, "the status must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", status);

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateOnlineStatus"),
				param);
	}

	@Override
	public Integer updateReviewStatus(Integer id, String status, String reviewer) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(status, "the status must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", status);
		param.put("reviewer", reviewer);

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateReviewStatus"),
				param);
	}

	@Override
	public Integer updateDesigner(Integer id, String designer) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(designer, "the designer must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("designer", designer);

		return getSqlMapClientTemplate()
				.update(addSqlKeyPreFix(sqlPreFix, "updateDesigner"), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExactTypeDto> queryAdExact(Integer aid) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryAdExact"), aid);
	}

	@Override
	public Integer queryPositionOfAd(Integer aid) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryPositionOfAd"), aid);
	}

	@Override
	public Integer deleteAdExactTypeById(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix, "deleteAdExactTypeById"), id);
	}

	@Override
	public Integer updateSearchExact(Integer id, String searchExact) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("searchExact", searchExact);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateSearchExact"), root);
	}

	@Override
	public Integer updateSequence(BigDecimal sequence, Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("sequence", sequence);
		root.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateSequence"), root);
	}

	@Override
	public Integer updatePosition(Integer id, Integer positionId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("positionId", positionId);
		root.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updatePosition"), root);
	}

	@Override
	public Integer countExistsAd(Date gmtPlanEnd, String keywords, Integer positionId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("gmtPlanEnd", gmtPlanEnd);
		root.put("keywords", keywords);
		root.put("positionId", positionId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "countExistsAd"), root);
	}

	@Override
	public Integer updateRent(Integer id, String rent) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("expiredRent", rent);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateRent"), root);
	}

}
