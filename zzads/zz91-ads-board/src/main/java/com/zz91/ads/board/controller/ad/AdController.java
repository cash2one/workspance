/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-18.
 */
package com.zz91.ads.board.controller.ad;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.domain.ad.AdExactType;
import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.service.ad.AdService;
import com.zz91.ads.board.service.ad.AdvertiserService;
import com.zz91.ads.board.service.ad.ExactTypeService;
import com.zz91.util.auth.AuthConst;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Controller
public class AdController extends BaseController{
	
	@Resource
	private AdService adService;
	@Resource
	private ExactTypeService exactTypeService;
	@Resource
	private AdvertiserService advertiserService;
	
	final static String SALE_ACCOUNT="adminsale";
	final static String SALE_ACCOUNT_PASSWORD="zj88friend";
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out,String email){
		out.put("email", email);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView saleAd(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out, Integer crmId){
		out.put("crmId", crmId);
		
		SessionUser user=getCachedUser(request);
		if(user==null){
			user = AuthUtils.getInstance().validateUser(response, SALE_ACCOUNT, SALE_ACCOUNT_PASSWORD, AuthConst.PROJECT_CODE, AuthConst.PROJECT_PASSWORD);
			setSessionUser(request, user);
		}
		
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView apply(Map<String, Object> out,Integer id){
		if (id!=null) {
			out.put("id", id);
		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView query(HttpServletRequest request, Map<String, Object> out,
			Pager<AdDto> page, Ad ad, AdSearchDto adSearch){
		if(ad.getPositionId()!=null && ad.getPositionId()==0){
			ad.setPositionId(null);
		}
		page = adService.pageAdByConditions(ad, adSearch, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView applyAd(HttpServletRequest request, Map<String, Object> out, Ad ad){
		ad.setApplicant(getCachedUser(request).getAccount());
		Integer i = adService.applyAd(ad);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
	
	/**
	 * 复制广告功能
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView copy(Map<String, Object> out, Integer id){
		ExtResult result = new ExtResult();
		do {
			if(id==null){
				break;
			}
			Ad ad = adService.queryAdById(id);
			if(ad==null){
				break;
			}
			Integer i = adService.applyAd(ad);
			if(i!=null && i.intValue()>0){
				result.setSuccess(true);
				result.setData(i);
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryAdById(HttpServletRequest request, Map<String, Object> out, Integer id){
		List<Ad> list= new ArrayList<Ad>();
		list.add(adService.queryAdById(id));
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView upload(HttpServletRequest request,
			Map<String, Object> out) {
		ExtResult result = new ExtResult();
		Long monthFolder=DateUtil.getMillis(DateUtil.getNowMonthFirstDay());
		String uploadedFile;
		try {
			uploadedFile = MvcUpload.localUpload(request, MvcUpload.getDestRoot()+"/ads/"+monthFolder, UUID.randomUUID().toString());
			if (StringUtils.isNotEmpty(uploadedFile)) {
				result.setSuccess(true);
				result.setData("/"+monthFolder+"/"+uploadedFile);
			}
		} catch (Exception e) {
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateOnly(HttpServletRequest request, Map<String, Object> out, Ad ad){
		Integer i=adService.saveOnly(ad);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateAndCheck(HttpServletRequest request, Map<String, Object> out, Ad ad){
		ad.setReviewer(getCachedUser(request).getAccount());
		Integer i=adService.saveAndCheck(ad);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryAdExact(HttpServletRequest request, Map<String, Object> out, Integer aid){
		return printJson(adService.queryAdExact(aid), out);
	}
	
	@RequestMapping
	public ModelAndView deleteAdExact(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i=adService.deleteAdExactTypeById(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createAdExact(HttpServletRequest request, Map<String, Object> out, AdExactType adexact){
		adexact.setAdPositionId(adService.queryPositionOfAd(adexact.getAdId()));
		Integer i=adService.insertAdExactType(adexact);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryExactTypeOfAd(HttpServletRequest request, Map<String, Object> out, Integer aid){
		Integer pid=adService.queryPositionOfAd(aid);
		List<ExactType> list=exactTypeService.queryExactTypeByAdPositionId(pid);
		return printJson(list, out);
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
	public ModelAndView moveAd(HttpServletRequest request, Map<String, Object> out, Integer id, Integer positionId){
		Integer i = adService.updatePosition(id, positionId);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
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
	public ModelAndView removeRent(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = adService.removeRent(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public void renew(Map<String, Object> model){
		
	}
	
}
