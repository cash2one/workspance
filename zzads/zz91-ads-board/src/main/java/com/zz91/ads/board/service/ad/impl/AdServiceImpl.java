/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.service.ad.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.AdDao;
import com.zz91.ads.board.dao.ad.AdExactTypeDao;
import com.zz91.ads.board.dao.ad.AdMaterialDao;
import com.zz91.ads.board.dao.ad.AdvertiserDao;
import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.domain.ad.AdExactType;
import com.zz91.ads.board.domain.ad.Advertiser;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.dto.ad.ExactTypeDto;
import com.zz91.ads.board.service.ad.AdService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Component("adService")
public class AdServiceImpl implements AdService {

	@Resource
	private AdDao adDao;
	@Resource
	private AdMaterialDao adMaterialDao;
	@Resource
	private AdExactTypeDao adExactTypeDao;
	@Resource
	private AdvertiserDao advertiserDao;
	
	@Override
	public Integer appoint(Integer id, String designer) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(designer, "the designer must not be null");
		
		return adDao.updateDesigner(id, designer);
	}

	@Override
	public Integer back(Integer id, String reviewer) {
		Assert.notNull(id, "the id must not be null");
		
		return adDao.updateReviewStatus(id, AdDao.REVIEW_STATUS_FALSE, reviewer);
	}

	@Override
	public Integer deleteAdById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		adMaterialDao.deleteAdMaterialByAdId(id);
		adExactTypeDao.deleteAdExactTypeByAdId(id);
		return adDao.deleteAdById(id);
	}

	@Override
	public Integer finishAdByDesigner(Ad ad) {
		Assert.notNull(ad, "the object of ad must not be null");
		Assert.notNull(ad.getId(), "the id must not be null");
		
		ad.setDesignStatus(AdDao.DESIGN_STATUS_FINISH);
		return adDao.updateAdByDesigner(ad);
	}
	
	@Override
	public Integer applyAd(Ad ad) {
		Assert.notNull(ad, "the object of ad must not be null");
		Assert.notNull(ad.getPositionId(), "the positionId must not be null");
		Assert.notNull(ad.getAdvertiserId(), "the advertiserId must not be null");
		Assert.notNull(ad.getAdTitle(), "the adTitle must not be null");
		Assert.notNull(ad.getAdTargetUrl(), "the adTargetUrl must not be null");
		Assert.notNull(ad.getApplicant(), "the applicant must not be null");
		
		ad.setOnlineStatus(AdDao.ONLINE_STATUS_FALSE);
		ad.setReviewStatus(AdDao.REVIEW_STATUS_UN);
		ad.setExpiredRent("");
		
		return adDao.insertAd(ad);
	}

	@Override
	public Integer insertAdExactType(AdExactType adExactType) {
		Assert.notNull(adExactType, "the object of adExactType must not be null");
		Assert.notNull(adExactType.getAdId(), "the adId must not be null");
		Assert.notNull(adExactType.getExactTypeId(), "the exactTypeId must not be null");
		Assert.notNull(adExactType.getAnchorPoint(), "the anchorPoint must not be null");
		Integer i=adExactTypeDao.insertAdExactType(adExactType);
		if(i!=null && i.intValue()>0){
			updateSearchExact(adExactType.getAdId());
			return i;
		}
		return 0;
	}
	
	private void updateSearchExact(Integer adid){
		List<ExactTypeDto> list=adDao.queryAdExact(adid);
		StringBuffer sb=new StringBuffer();
		sb.append("|");
		for(ExactTypeDto dto:list){
			sb.append(dto.getExact().getId()+":"+dto.getAdExact().getAnchorPoint());
			sb.append("|");
		}
		adDao.updateSearchExact(adid, sb.toString());
	}

	@Override
	public Pager<AdDto> pageAdByConditions(Ad ad, AdSearchDto adSearch, Pager<AdDto> pager) {
		Assert.notNull(pager, "the pager nust not be null");
		if(pager.getSort()==null){
			pager.setSort("a.gmt_start");
		}
		if(pager.getDir()==null){
			pager.setDir("desc");
		}
		pager.setRecords(adDao.queryAdByConditions(ad, adSearch, pager));
		pager.setTotals(adDao.queryAdByConditionsCount(ad, adSearch));
		
		return pager;
	}

	@Override
	public Integer pass(Integer id, String reviewer) {
		Assert.notNull(id, "the id nust not be null");
		
		return adDao.updateReviewStatus(id, AdDao.REVIEW_STATUS_TRUE, reviewer);
	}

	@Override
	public Ad queryAdById(Integer id) {
		Assert.notNull(id, "the id nust not be null");
		Ad ad = adDao.queryAdById(id);
		Advertiser advertiser = advertiserDao.queryAdvertiserById(ad.getAdvertiserId());
		if (advertiser!=null) {
			if (StringUtils.isNotEmpty(advertiser.getName())) {
				ad.setAdvertiserName(advertiser.getName());
			}
			if (StringUtils.isNotEmpty(advertiser.getContact())) {
				ad.setAdvertiserContact(advertiser.getContact());
			}
			if (StringUtils.isNotEmpty(advertiser.getEmail())) {
				ad.setAdvertiserEmail(advertiser.getEmail());
			}
			if (StringUtils.isNotEmpty(advertiser.getPhone())) {
				ad.setAdvertiserPhone(advertiser.getPhone());
			}
		}
		return ad;
	}

	@Override
	public Integer saveAndDesign(Ad ad) {
		Assert.notNull(ad, "the object of ad must not be null");
		Assert.notNull(ad.getId(), "the id must not be null");
		//TODO：发送邮件给提交者和审核者。
		ad.setDesignStatus(AdDao.DESIGN_STATUS_FINISH);
		
		return adDao.updateAd(ad);
	}

	@Override
	public Integer saveAndCheck(Ad ad) {
		Assert.notNull(ad, "the object of ad must not be null");
		Assert.notNull(ad.getId(), "the id must not be null");
		
		ad.setReviewStatus(AdDao.REVIEW_STATUS_TRUE);
		
		return adDao.updateAd(ad);
	}

	@Override
	public Integer saveOnly(Ad ad) {
		Assert.notNull(ad, "the object of ad must not be null");
		Assert.notNull(ad.getId(), "the id must not be null");
		if (StringUtils.isEmpty(ad.getExpiredRent())) {
			ad.setExpiredRent("");
		}
		return adDao.updateAd(ad);
	}

	@Override
	public Integer sendToDesign(Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return adDao.updateDesignStatus(id, AdDao.DESIGN_STATUS_U);
	}

	@Override
	public Integer setOffline(Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return adDao.updateOnlineStatus(id, AdDao.ONLINE_STATUS_FALSE);
	}

	@Override
	public Integer setOnline(Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return adDao.updateOnlineStatus(id, AdDao.ONLINE_STATUS_TRUE);
	}

	@Override
	public List<ExactTypeDto> queryAdExact(Integer aid) {
		return adDao.queryAdExact(aid);
	}

	@Override
	public Integer queryPositionOfAd(Integer aid) {
		return adDao.queryPositionOfAd(aid);
	}

	@Override
	public Integer deleteAdExactTypeById(Integer id) {
		Integer adid=adExactTypeDao.queryAdIdById(id);
		Integer i= adDao.deleteAdExactTypeById(id);
		updateSearchExact(adid);
		return i;
	}

	@Override
	public Integer updateSequence(BigDecimal sequence, Integer id) {
		if(sequence==null){
			sequence=new BigDecimal(0);
		}
		return adDao.updateSequence(sequence, id);
	}

	@Override
	public Integer updatePosition(Integer id, Integer positionId) {
		return adDao.updatePosition(id, positionId);
	}

	@Override
	public Integer removeRent(Integer id) {
		return adDao.updateRent(id, "");
	}

}
