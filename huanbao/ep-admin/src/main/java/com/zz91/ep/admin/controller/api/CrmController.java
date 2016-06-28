/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-9-12 上午11:04:55
 */
package com.zz91.ep.admin.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.comp.CompAccountService;
import com.zz91.ep.admin.service.comp.CompProfileService;
import com.zz91.ep.domain.crm.CrmCompany;

@Controller
public class CrmController extends BaseController {
	
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private CompAccountService compAccountService;
	
	@RequestMapping
	public ModelAndView syncProfile(Map<String, Object> out,HttpServletRequest request,
			Integer start,Integer limit,String from,String to){
		List<CrmCompany> cList = compProfileService.queryCompProfile(start,limit,from,to);
		return printJson(cList, out);
	}
	
	@RequestMapping
	public ModelAndView queryPwdByCid(Map<String, Object> out,HttpServletRequest request,Integer cid){
		Map<String, String> map = new HashMap<String, String>();
		String pwd=compAccountService.queryPasswordClearByCid(cid);
		map.put("pwd", pwd);
		return printJson(map, out);
	}
}
