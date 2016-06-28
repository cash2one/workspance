/**
 * @author shiqp
 * @date 2015-07-08
 */
package com.zz91.ads.board.service.ad.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.AdDao;
import com.zz91.ads.board.dao.ad.AdPositionDao;
import com.zz91.ads.board.dao.ad.AdRenewDao;
import com.zz91.ads.board.dao.ad.AdvertiserDao;
import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.domain.ad.AdRenew;
import com.zz91.ads.board.domain.ad.Advertiser;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.service.ad.AdRenewService;

@Component("adRenewService")
public class AdRenewServiceImpl implements AdRenewService {
	@Resource
	private AdRenewDao adRenewDao;
	@Resource
	private AdDao adDao;
	@Resource
	private AdvertiserDao advertiserDao;
	@Resource
	private AdPositionDao adPositionDao;

	@Override
	public Integer createRenew(AdRenew adRenew) {
		return adRenewDao.createRenew(adRenew);
	}

	@Override
	public Pager<AdRenew> pageRenewByAdId(Pager<AdRenew> page, Integer adId) {
		page.setDir("asc");
		page.setSort("gmt_created");
		page.setRecords(adRenewDao.queryRenewByAdId(page, adId));
		page.setTotals(adRenewDao.countRenewByAdId(adId));
		return page;
	}

	@Override
	public Integer updateRenew(AdRenew adRenew) {
		return adRenewDao.updateRenew(adRenew);
	}

	@Override
	public AdRenew queryRenewById(Integer id) {
		return adRenewDao.queryRenewById(id);
	}

	@Override
	public Pager<AdDto> pageRenewByCondition(AdSearchDto adSearch, Pager<AdDto> page) {
		page.setDir("gmt_created");
		page.setSort("desc");
		List<AdRenew> list = adRenewDao.queryRenewByCondition(adSearch, page);
		List<AdDto> listm = new ArrayList<AdDto>();
		for(AdRenew ar : list){
			AdDto dto = new AdDto();
			dto.setRenew(ar);
			//Ad
			Ad ad = adDao.queryAdById(ar.getAdId());
			if(ad!=null){
				dto.setAd(ad);
				Advertiser advertiser = advertiserDao.queryAdvertiserById(ad.getAdvertiserId());
				if(advertiser!=null){
					dto.setAdvertiserName(advertiser.getName());
					dto.setEmail(advertiser.getEmail());
				}
				AdPosition position = adPositionDao.queryAdPositionById(ad.getPositionId());
				if(position!=null){
					dto.setPositionName(position.getName());
					dto.setHeight(position.getHeight());
					dto.setWidth(position.getWidth());
					dto.setRequestUrl(position.getRequestUrl());
				}
			}
			listm.add(dto);
		}
		page.setRecords(listm);
		page.setTotals(adRenewDao.countRenewByCondition(adSearch));
		return page;
	}

}
