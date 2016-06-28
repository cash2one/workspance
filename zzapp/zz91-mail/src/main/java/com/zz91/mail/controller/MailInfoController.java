/**
 * 
 */
package com.zz91.mail.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.dto.ExtResult;
import com.zz91.mail.domain.dto.PageDto;
import com.zz91.mail.service.MailInfoService;

/**
 * @author root
 * 
 */
@Controller
public class MailInfoController extends BaseController {

	@Resource
	private MailInfoService mailInfoService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		return null;
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request,
			Map<String, Object> out, Integer id) {

		Integer i = mailInfoService.deleteById(id);

		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView query(HttpServletRequest request,
			Map<String, Object> out, Date from, Date to, Integer priority,
			Integer status, PageDto<MailInfoDomain> page) {

		page = mailInfoService.pageMail(from, to, priority, page);

		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView resend(HttpServletRequest request,
			Map<String, Object> out, Integer id) {

		Boolean bo = mailInfoService.resend(id);

		ExtResult result = new ExtResult();
		if (bo.booleanValue()) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView details(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		MailInfoDomain mailInfo = mailInfoService.queryOne(id);

		out.put("mailInfo", mailInfo);
		return null;
	}

	@RequestMapping
	public ModelAndView queryOne(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		MailInfoDomain domain = mailInfoService.queryOne(id);

		PageDto<MailInfoDomain> page = new PageDto<MailInfoDomain>();
		List<MailInfoDomain> list = new ArrayList<MailInfoDomain>();
		list.add(domain);
		page.setRecords(list);

		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView send(HttpServletRequest request,Map<String, Object> out,
			String title,String sender,String receiver,String content) {

		Boolean bo = mailInfoService.sendMail(title, sender, receiver,content);

		ExtResult result = new ExtResult();
		if (bo.booleanValue()) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
