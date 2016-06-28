/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.service.ad.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.AdvertiserDao;
import com.zz91.ads.board.domain.ad.Advertiser;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.ad.AdvertiserService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * 广告主接口
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("advertiserService")
public class AdvertiserServiceImpl implements AdvertiserService {

	@Resource
	private AdvertiserDao advertiserDao;

	@Override
	public Integer insertAdvertiser(Advertiser advertiser) {
		Assert.notNull(advertiser, "the object of advertiser must not be null");
		Assert.hasText(advertiser.getName(), "the name must not be null");

		if(advertiser.getCategory()==null){
			advertiser.setCategory(AdvertiserDao.DEF_CATEGORY);
		}
		if (StringUtils.isEmpty(advertiser.getEmail())) {
			return 0;
		}
		Integer i = queryIdByEmail(advertiser.getEmail());
		if (i!=null&&i>0) {
			return 0;
		}
		
		return advertiserDao.insertAdvertiser(advertiser);
	}

	@Override
	public Pager<Advertiser> pageAdvertiserByConditions(Advertiser advertiser, Boolean delete,
			Pager<Advertiser> pager) {
		Assert.notNull(pager, "the pager must not be null");
		
		if(advertiser==null){
			advertiser=new Advertiser();
		}
		if(delete!=null && delete){
			advertiser.setDeleted(AdvertiserDao.DELETE_TRUE);
		}else{
			advertiser.setDeleted(AdvertiserDao.DELETE_FALSE);
		}

		pager.setRecords(advertiserDao.queryAdvertiserByConditions(advertiser, pager));
		pager.setTotals(advertiserDao.queryAdvertiserByConditionsCount(advertiser));

		return pager;
	}

	@Override
	public Advertiser queryAdvertiserById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return advertiserDao.queryAdvertiserById(id);
	}

	@Override
	public Integer signDeleted(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return advertiserDao.signDeleted(id);
	}

	@Override
	public Integer updateAdvertiser(Advertiser advertiser) {
		Assert.notNull(advertiser, "the object of advertiser must not be null");
		Assert.notNull(advertiser.getId(), "the id must not be null");
		Assert.notNull(advertiser.getName(), "the name must not be null");
		
		if(advertiser.getCategory()==null){
			advertiser.setCategory(AdvertiserDao.DEF_CATEGORY);
		}

		return advertiserDao.updateAdvertiser(advertiser);
	}

	@Override
	public Integer queryIdByEmail(String email) {
		if(StringUtils.isEmpty(email)){
			return null;
		}
		return advertiserDao.queryIdByEmail(email);
	}

}
