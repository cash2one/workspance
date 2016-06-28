/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.service.ad;

import com.zz91.ads.board.domain.ad.AdBooking;
import com.zz91.ads.board.dto.Pager;


/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface AdBookingService {
	
	public Pager<AdBooking> pageBooking(AdBooking booking, Pager<AdBooking> page);
	
	public Integer createBooking(AdBooking booking);
	
	public Integer deleteBooking(Integer id);
	
	public Boolean bookingEnable(Integer positionId, String keywords);
	
}
