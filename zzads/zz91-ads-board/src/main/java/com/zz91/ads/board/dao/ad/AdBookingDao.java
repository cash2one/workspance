/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.dao.ad;

import java.util.Date;
import java.util.List;

import com.zz91.ads.board.domain.ad.AdBooking;
import com.zz91.ads.board.dto.Pager;

/**
 * 广告接口
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface AdBookingDao {
	
	public List<AdBooking> queryBooking(AdBooking booking, Pager<AdBooking> page);
	
	public Integer queryBookingCount(AdBooking booking);
	
	public Integer insertBooking(AdBooking booking);
	
	public Integer deleteBooking(Integer id);
	
	public Integer countExistsBooking(Date gmtBooking, String keywords, Integer positionId);
}
