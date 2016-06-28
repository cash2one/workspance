package com.zz91.ads.board.service.ad.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.AdBookingDao;
import com.zz91.ads.board.dao.ad.AdDao;
import com.zz91.ads.board.dao.ad.AdPositionDao;
import com.zz91.ads.board.domain.ad.AdBooking;
import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.ad.AdBookingService;
import com.zz91.util.datetime.DateUtil;

@Component("adBookingService")
public class AdBookingServiceImpl implements AdBookingService {

	@Resource
	private AdBookingDao adBookingDao;
	@Resource
	private AdPositionDao adPositionDao;
	@Resource
	private AdDao adDao;
	
	@Override
	public Pager<AdBooking> pageBooking(AdBooking booking, Pager<AdBooking> page) {
		page.setRecords(adBookingDao.queryBooking(booking, page));
		page.setTotals(adBookingDao.queryBookingCount(booking));
		return page;
	}

	@Override
	public Integer createBooking(AdBooking booking) {
		return adBookingDao.insertBooking(booking);
	}

	@Override
	public Integer deleteBooking(Integer id) {
		return adBookingDao.deleteBooking(id);
	}

	@Override
	public Boolean bookingEnable(Integer positionId, String keywords) {
		AdPosition position=adPositionDao.queryAdPositionById(positionId);
		
		if(AdPositionDao.HAS_EXACT_AD_FALSE==position.getHasExactAd()){
			keywords=null;
		}
		
		Date gmtPlanEndTo = DateUtil.getDateAfterDays(new Date(), 7);
		Integer countExistsAd=adDao.countExistsAd(gmtPlanEndTo, keywords, positionId);
		
		Date gmtBooking = DateUtil.getDateAfterDays(new Date(), -2);
		Integer countExistsBooking=adBookingDao.countExistsBooking(gmtBooking, keywords, positionId);
		
		if(position.getMaxAd()==null){
			position.setMaxAd(1);
		}
		if(countExistsAd==null){
			countExistsAd=0;
		}
		if(countExistsBooking==null){
			countExistsBooking=0;
		}
		if(position.getMaxAd()>(countExistsAd+countExistsBooking)){
			return true;
		}
		return false;
	}

}
