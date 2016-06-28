/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-18.
 */
package com.zz91.ads.board.controller.ad;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.dao.ad.AdDao;
import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.domain.ad.AdBooking;
import com.zz91.ads.board.domain.ad.AdRenew;
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.service.ad.AdBookingService;
import com.zz91.ads.board.service.ad.AdRenewService;
import com.zz91.ads.board.service.ad.AdService;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Controller
public class BookingController extends BaseController{
	
	@Resource
	private AdBookingService adBookingService;
	@Resource
	private AdRenewService adRenewService;
	@Resource
	private AdService adService;
	@Resource
	private AdDao adDao;
	
//	final static int EXPIRE_DAY=2;
	
	@RequestMapping
	public void index(Map<String, Object> model){
		
	}
	
	@RequestMapping
	public ModelAndView query(HttpServletRequest request, Map<String, Object> out,
			Pager<AdBooking> page,AdBooking booking){
		//计算期限
//		if(booking.getGmtBooking()==null){
//		}
//		booking.setGmtBooking(DateUtil.getDateAfterDays(new Date(), -EXPIRE_DAY));
		
		if(booking.getPositionId()!=null && booking.getPositionId()==0){
			booking.setPositionId(null);
		}
		
		page = adBookingService.pageBooking(booking, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView createBooking(HttpServletRequest request, Map<String, Object> out, AdBooking booking){
		
		
		ExtResult result=new ExtResult();
		if(!adBookingService.bookingEnable(booking.getPositionId(), booking.getKeywords())){
			return printJson(result, out);
		}
		booking.setAccount(getCachedUser(request).getAccount());
		Integer i=adBookingService.createBooking(booking);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteBooking(HttpServletRequest request, Map<String, Object> out, Integer id){
		
		Integer i=adBookingService.deleteBooking(id);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView createRenew(HttpServletRequest request, Map<String, Object> out, AdRenew adRenew){
		Integer i = 0;
		SessionUser sessionUser = getCachedUser(request);
		adRenew.setApplicant(sessionUser.getAccount());
		if(adRenew.getId()!=null){
			i = adRenewService.updateRenew(adRenew);
		}else{
			i = adRenewService.createRenew(adRenew);
		}
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView queryRenewByAdId(HttpServletRequest request, Map<String, Object> out, Pager<AdRenew> page, Integer adId){
		page = adRenewService.pageRenewByAdId(page, adId);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView updateDeal(HttpServletRequest request, Map<String, Object> out, Pager<AdRenew> page, Integer id){
		ExtResult result=new ExtResult();
		if(!AuthUtils.getInstance().authorizeRight("vip_cs_renew",request, null)){
			result.setData("权限不够");
			result.setSuccess(false);
		}else{
			AdRenew adRenew = adRenewService.queryRenewById(id);
			SessionUser sessionUser = getCachedUser(request);
			adRenew.setDealer(sessionUser.getAccount());
			adRenew.setGmtDeal(new Date());
			Integer i = adRenewService.updateRenew(adRenew);
			Ad ad = adService.queryAdById(adRenew.getAdId());
			ad.setGmtPlanEnd(adRenew.getGmtEnd());
			adDao.updateAd(ad);
			if(i!=null && i>0){
				result.setData("续费成功");
				result.setSuccess(true);
			}else{
				result.setData("续费失败");
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView queryRenewById(HttpServletRequest request, Map<String, Object> out, Integer id){
		AdRenew adRenew = adRenewService.queryRenewById(id);
		return printJson(adRenew, out);
	}
	
	@RequestMapping
	public ModelAndView queryRenewInfo(HttpServletRequest request, Map<String, Object> out, Pager<AdDto> page,AdSearchDto adSearch){
		page = adRenewService.pageRenewByCondition(adSearch, page);
		return printJson(page, out);
	}
	
}
