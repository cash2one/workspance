/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-21
 */
package com.zz91.ads.board.controller.analysis;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.domain.analysis.AnalysisAdHit;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.analysis.AnalysisAdHitService;
import com.zz91.util.datetime.DateUtil;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-21
 */
@Controller
public class AdHitController extends BaseController {

	@Resource
	private AnalysisAdHitService analysisAdHitService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView query(HttpServletRequest request, Map<String, Object> out, 
			Pager<AnalysisAdHit> page, Integer positionId, Long gmtTarget){
		if(gmtTarget==null){
			gmtTarget=DateUtil.getTheDayZero(new Date(), -1);
		}else{
			gmtTarget = DateUtil.getTheDayZero(new Date(gmtTarget), 0);
		}
		page=analysisAdHitService.pageAdHitResult(positionId, gmtTarget, page);
		return printJson(page, out);
	}
}
