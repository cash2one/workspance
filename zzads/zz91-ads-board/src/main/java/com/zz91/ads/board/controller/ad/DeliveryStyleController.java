/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-11
 */
package com.zz91.ads.board.controller.ad;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.domain.ad.DeliveryStyle;
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.ad.DeliveryStyleService;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-11
 */
@Controller
public class DeliveryStyleController extends BaseController{

	@Resource
	private DeliveryStyleService deliveryStyleService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryStyle(HttpServletRequest request, Map<String, Object> out, Pager<DeliveryStyle> page){
		page.setRecords(deliveryStyleService.queryDeliveryStyle());
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneStyle(HttpServletRequest request, Map<String, Object> out, Integer id){
		return null;
	}
	
	@RequestMapping
	public ModelAndView updateStyle(HttpServletRequest request, Map<String, Object> out, DeliveryStyle style){
		Integer i=deliveryStyleService.updateStyle(style);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createStyle(HttpServletRequest request, Map<String, Object> out, DeliveryStyle style){
		Integer i=deliveryStyleService.createStyle(style);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteStyle(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i=deliveryStyleService.deleteStyle(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
