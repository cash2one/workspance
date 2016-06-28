/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-11
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.site.FeedbackService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-11
 */
@Controller
public class MyfeedbackController extends BaseController {

	@Autowired
	FeedbackService feedbackService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CrmCsService crmCsService;

	@RequestMapping
	public ModelAndView vip(HttpServletRequest request,
			Map<String, Object> out, PageDto<FeedbackDo> page, String m) {
		SsoUser sessionUser = getCachedUser(request);
		out.put(FrontConst.MYRC_SUBTITLE, "给客服留言");

		out.put("m", m);
		
		page.setPageSize(10);
		page.setSort("gmt_created");
		page.setDir("desc");
		page = feedbackService.pageFeedbackHistoryByUser(getCachedUser(request).getCompanyId(), null, FeedbackService.CATEGORY_VIP, page);
		
		out.put("myaccount", companyAccountService.queryAccountByAccount(getCachedUser(request).getAccount()));
		
		out.put("page", page);
		CrmCs cs=crmCsService.queryCsOfCompany(sessionUser.getCompanyId());
		
		if(cs!=null && StringUtils.isNotEmpty(cs.getCsAccount())) {
			String csinfo=ParamUtils.getInstance().getValue("cs_info", cs.getCsAccount());
			if(csinfo != null) {
				String[] info=csinfo.split(",");
				if(info.length>1){
					out.put("cs_name", info[0]);
				}
				if(info.length>2){
					out.put("cs_phone", info[1]);
				}
				if(info.length>3){
					out.put("cs_email", info[2]);
				}
			}
		}
		
		return null;
	}

	@RequestMapping
	public ModelAndView member(HttpServletRequest request,
			Map<String, Object> out, PageDto<FeedbackDo> page, String m) {
		out.put("m", m);
		
		page.setPageSize(10);
		page.setSort("gmt_created");
		page.setDir("desc");
		page = feedbackService.pageFeedbackHistoryByUser(getCachedUser(request).getCompanyId(), null, FeedbackService.CATEGORY_MEMBER, page);
		
		out.put("myaccount", companyAccountService.queryAccountByAccount(getCachedUser(request).getAccount()));
		out.put("page", page);
		return null;
	}

	@RequestMapping
	public ModelAndView feedbackVip(HttpServletRequest request,
			Map<String, Object> out, FeedbackDo feedback) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		feedback.setAccount(sessionUser.getAccount());
		feedback.setCompanyId(sessionUser.getCompanyId());
		feedback.setEmail(sessionUser.getEmail());
		//TODO 通过companyId查找个人助理的账户并写入checkPerson();
		CrmCs cs=crmCsService.queryCsOfCompany(sessionUser.getCompanyId());
		if(cs!=null && StringUtils.isNotEmpty(cs.getCsAccount())){
			feedback.setCheckPerson(cs.getCsAccount());
		}
//		Integer id = 
		feedbackService.insertFeedbackByVIP(feedback);
//		if(id!=null && id.intValue()>0){
		out.put("error", 0);
		return new ModelAndView("forward:vip.htm");
//		}
	}

	@RequestMapping
	public ModelAndView feedbackMember(HttpServletRequest request,
			Map<String, Object> out, FeedbackDo feedback) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		feedback.setAccount(sessionUser.getAccount());
		feedback.setCompanyId(sessionUser.getCompanyId());
		feedback.setEmail(sessionUser.getEmail());
		
//		Integer id = 
		feedbackService.insertFeedbackByMember(feedback);
//		if(id!=null && id.intValue()>0){
//			result.setSuccess(true);
//		}
//		return printJson(result, out);
		out.put("error", 0);
		return new ModelAndView("forward:member.htm");
	}
}
