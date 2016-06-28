/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-25
 */
package com.zz91.ep.admin.controller.crm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.crm.CRMSvrService;
import com.zz91.ep.domain.crm.CrmSvr;

/**
 * 
 */
@Controller
public class SvrController extends BaseController{

	@Resource
	private CRMSvrService crmSvrService;
	
	@RequestMapping
	public ModelAndView querySvr(Map<String, Object> out, HttpServletRequest request) 
		throws IOException{
		
		List<CrmSvr> list=crmSvrService.queryCrmSvr();
		
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView querySvrByCid(Map<String, Object> out, HttpServletRequest request, Integer cid) 
		throws IOException{
		
		List<CrmSvr> list=crmSvrService.queryCrmSvr();
		
		return printJson(list, out);
	}
}
