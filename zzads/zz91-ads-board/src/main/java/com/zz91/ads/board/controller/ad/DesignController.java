/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-31.
 */
package com.zz91.ads.board.controller.ad;

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
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.service.ad.AdService;
import com.zz91.ads.board.utils.AdConst;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author hetao(hetao138@gmail.com)
 *
 */

@Controller
public class DesignController extends BaseController{
	
	@Resource
	private AdService adService;
	
	@RequestMapping
	public void index(Map<String, Object> model){
	
	}
	
//	@RequestMapping
//	public ModelAndView queryDsn(HttpServletRequest request, Map<String,Object> out,Ad dsn, Pager <AdDto> page){
//		page.setDir("desc");
//		page.setSort("gmt_start");
//		page = adService.pageAdByConditions(dsn, page);
//		return printJson(page, out);
//	}
	
	@RequestMapping
	public ModelAndView query(Map<String, Object> out, HttpServletRequest request, Ad ad, Pager<AdDto> page){
		ad.setDesignStatus("U");
		page = adService.pageAdByConditions(ad, new AdSearchDto(), page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView saveAndDesign(Map<String, Object> out, HttpServletRequest request, Ad ad, String gmtStartStr, String gmtPlanEndStr){
		try {
			ad.setGmtStart(DateUtil.getDate(gmtStartStr, AdConst.FORMAT_DATE_TIME));
			if(StringUtils.isNotEmpty(gmtPlanEndStr)){
				ad.setGmtPlanEnd(DateUtil.getDate(gmtPlanEndStr, AdConst.FORMAT_DATE));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ad.setDesigner(getCachedUser(request).getAccount());
		Integer i=adService.saveAndDesign(ad);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
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
			ad.setGmtStart(DateUtil.getDate(gmtStartStr, AdConst.FORMAT_DATE));
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
}
