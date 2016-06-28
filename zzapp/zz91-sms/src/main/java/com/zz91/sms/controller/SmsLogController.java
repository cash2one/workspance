/**
 * 
 */
package com.zz91.sms.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.sms.domain.SmsLog;
import com.zz91.sms.dto.ExtResult;
import com.zz91.sms.dto.Pager;
import com.zz91.sms.service.SmsLogService;

/**
 * @author root
 * 
 */
@Controller
public class SmsLogController extends BaseController {

	@Resource
	private SmsLogService smsLogService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		return null;
	}
	@RequestMapping
	public ModelAndView querySms(HttpServletRequest request,
			Map<String, Object> out, String from, String to,
			Integer sendStatus, String receiver, String gatewayCode,
			Integer priority, String content, String templateCode, Pager<SmsLog> page) {
		page = smsLogService.pageLog(from, to, sendStatus, receiver,gatewayCode, priority, content, templateCode, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView deleteSms(HttpServletRequest request,
			Map<String, Object> out, Integer id) {

		Integer i = smsLogService.remove(id);

		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView resendSms(HttpServletRequest request,
			Map<String, Object> out, Integer id) {

		smsLogService.resend(id);
		ExtResult result = new ExtResult();
		if (id != null && id.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
