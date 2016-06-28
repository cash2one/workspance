package com.ast.ast1949.myrc.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.phone.PhoneClickLogService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;

/**
 *	author:kongsj
 *	date:2013-7-11
 */
@Controller
public class MyphoneController extends BaseController{
	
	@Resource
	private PhoneService phoneService;
	@Resource
	private PhoneLogService phoneLogService;
	@Resource
	private PhoneClickLogService phoneClickLogService;
	
	@RequestMapping
	public void index(HttpServletRequest request,Map<String, Object> out){
		do {
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
			if(phone==null||StringUtils.isEmpty(phone.getTel())){
				break;
			}
			// 获取余额
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("phone",phone);
		} while (false);
		
	}
	
	@RequestMapping
	public void phoneLog(HttpServletRequest request,Map<String, Object> out,PhoneLog phoneLog,PageDto<PhoneLog>page){
		do {
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
			if(phone==null||StringUtils.isEmpty(phone.getTel())){
				break;
			}
			if(StringUtils.isEmpty(page.getDir())){
				page.setDir("desc");
			}
			if(StringUtils.isEmpty(page.getSort())){
				page.setSort("id");
			}
			phoneLog.setTel(phone.getTel());
			phoneLogService.pageList(phoneLog, page);
			// 获取余额
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("phone", phone);
			out.put("page", page);
		} while (false);
	}
	
	@RequestMapping
	public void clickLog(HttpServletRequest request,Map<String, Object> out,PageDto<PhoneClickLog>page,PhoneClickLog phoneClickLog){
		do {
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
			if(phone==null||StringUtils.isEmpty(phone.getTel())){
				break;
			}
			page.setSort("id");
			page.setDir("desc");
			phoneClickLog.setCompanyId(ssoUser.getCompanyId());
			page = phoneClickLogService.pageList(phoneClickLog, page);
			// 获取余额
			phone.setBalance(phoneLogService.countBalance(phone));
			out.put("phone", phone);
			out.put("page", page);
		} while (false);
	}
}
