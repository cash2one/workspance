/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-31.
 */
package com.zz91.ads.board.controller.ad;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.service.ad.AdPositionService;
import com.zz91.ads.board.service.ad.AdService;
import com.zz91.ads.board.service.ad.AdvertiserService;
import com.zz91.ads.board.service.ad.ExactTypeService;
import com.zz91.ads.board.utils.AdConst;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;


/**
 * @author hetao(hetao138@gmail.com)
 *
 */

@Controller
public class CheckController extends BaseController{
	
	@Resource
	private AdService adService;
	@Resource
	private ExactTypeService exactTypeService;
	@Resource
	private AdPositionService adPositionService;
	@Resource
	private AdvertiserService advertiserService;
	
	@RequestMapping
	public void index(Map<String, Object> model){
		
	}
	
	@RequestMapping
	public ModelAndView queryAd(HttpServletRequest request, Map<String, Object> out, 
		Ad ad, Pager<AdDto> page, String email){
		if(StringUtils.isNotEmpty(email)){
			ad.setAdvertiserId(advertiserService.queryIdByEmail(email));
			if(ad.getAdvertiserId()==null){
				ad.setAdvertiserId(-1);
			}
		}
		page = adService.pageAdByConditions(ad, new AdSearchDto(), page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryAdById(HttpServletRequest request, Map<String, Object> out, Integer id){
		List<Ad> list= new ArrayList<Ad>();
		list.add(adService.queryAdById(id));
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView updateOnly(HttpServletRequest request, Map<String, Object> out, Ad ad, String gmtStartStr, String gmtPlanEndStr){
		try {
			ad.setGmtStart(DateUtil.getDate(gmtStartStr, AdConst.FORMAT_DATE_TIME));
			if(StringUtils.isNotEmpty(gmtPlanEndStr)){
				ad.setGmtPlanEnd(DateUtil.getDate(gmtPlanEndStr, AdConst.FORMAT_DATE));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer i=adService.saveOnly(ad);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateAndCheck(HttpServletRequest request, Map<String, Object> out, Ad ad, String gmtStartStr, String gmtPlanEndStr){
		try {
			ad.setGmtStart(DateUtil.getDate(gmtStartStr, AdConst.FORMAT_DATE_TIME));
			if(StringUtils.isNotEmpty(gmtPlanEndStr)){
				ad.setGmtPlanEnd(DateUtil.getDate(gmtPlanEndStr, AdConst.FORMAT_DATE));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ad.setReviewer(getCachedUser(request).getAccount());
		Integer i=adService.saveAndCheck(ad);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView checkAd(HttpServletRequest request, Map<String, Object> out, String reviewStatus, Integer id){
		Integer i;
		String account=getCachedUser(request).getAccount();
		if(StringUtils.isEmpty(reviewStatus) || "N".equals(reviewStatus)){
			i=adService.back(id,account);
		}else{
			i=adService.pass(id,account);
		}
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView buildOnlineCode(HttpServletRequest request, Map<String, Object> out, 
			Integer adId, Integer pid, Integer hasexactid){
		if(hasexactid==null){
			AdPosition position=adPositionService.queryAdPositionById(pid);
			if(position!=null && position.getHasExactAd()!=null){
				hasexactid=position.getHasExactAd();
			}
		}
		
		StringBuffer code=new StringBuffer();
		code.append("<script type=\"text/javascript\" src=\"http://gg.zz91.com/show?p=").append(pid);
		if(hasexactid!=null && hasexactid.intValue()==1){
			//精确投放
			List<ExactType> list=exactTypeService.queryExactTypeByAdPositionId(pid);
			for(ExactType et:list){
				code.append("&").append(et.getExactName()).append("=$").append(et.getExactName()).append("$");
			}
		}
		code.append("\"></script>");
		if(hasexactid!=null && hasexactid.intValue()==1){
			code.append("\n请将$$之间的字符(包含$)替换成投放应用产生的精确条件");
		}
		ExtResult result = new ExtResult();
		result.setData(code.toString());
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView publishAd(HttpServletRequest request, Map<String, Object> out, Integer id, String onlineStatus){
		Integer i;
		if(onlineStatus!=null && "Y".equals(onlineStatus)){
			i = adService.setOnline(id);
		}else{
			i = adService.setOffline(id);
		}
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteAd(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = adService.deleteAdById(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView designAd(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = adService.sendToDesign(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateSequence(HttpServletRequest request, Map<String, Object> out, BigDecimal sequence, Integer id){
		Integer i = adService.updateSequence(sequence, id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView moveAd(HttpServletRequest request, Map<String, Object> out, Integer id, Integer positionId){
		Integer i = adService.updatePosition(id, positionId);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
