/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-14
 */
package com.ast.ast1949.myrc.controller.esite;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.SeoTemplatesService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-14
 */
@Controller
public class EsiteDomainController extends BaseController {

	@Autowired
	private CompanyService companyService;
	@Resource
	private SeoTemplatesService seoTemplatesService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "绑定商铺域名");

		out.put("memberInfo", companyService
				.queryDomainOfCompany(getCachedUser(request).getCompanyId()));
		out.put("customDomainIp", ParamUtils.getInstance().getValue(
				"baseConfig", "custom_domain_ip"));
		out.put("csPhone", ParamUtils.getInstance().getValue(
				"baseConfig", "cs_phone"));

		return null;
	}
	
	@RequestMapping
	public ModelAndView toEsite(HttpServletRequest request){
		String str = companyService.queryDomainOfCompany(getCachedUser(request).getCompanyId()).getDomainZz91();
		if(StringUtils.isEmpty(str)){
			str = "myrc";
		}
		if(seoTemplatesService.validate(getCachedUser(request).getCompanyId())){
			return new ModelAndView("redirect:http://"+str+".zz91.net");
		}else{
			return new ModelAndView("redirect:http://"+str+".zz91.com");
		}
		
	}

	@RequestMapping
	public ModelAndView bind(HttpServletRequest request,
			Map<String, Object> out, String domain) throws IOException {
		companyService.saveCustomDomain(getCachedUser(request).getCompanyId(), domain);
		return new ModelAndView(new RedirectView("index.htm"));
	}

}
