/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-25
 */
package com.ast.ast1949.web.controller.zz91.crm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CrmSvr;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-25
 */
@Controller
public class SvrController extends BaseController{

	@Resource
	private CrmSvrService crmSvrService;
	
	@RequestMapping
	public ModelAndView querySvr(Map<String, Object> out, HttpServletRequest request) 
		throws IOException{
		
		List<CrmSvr> list=crmSvrService.querySvr();
//		CrmSvr svr=new CrmSvr();
//		svr.setName("全部客户");
//		svr.setCode("");
//		list.add(0, svr);
		
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView queryLdbSvr(Map<String, Object> out, HttpServletRequest request) 
		throws IOException{
		
		List<CrmSvr> list=crmSvrService.queryLdbSvr();
//		CrmSvr svr=new CrmSvr();
//		svr.setName("全部客户");
//		svr.setCode("");
//		list.add(0, svr);
		
		return printJson(list, out);
	}
}
