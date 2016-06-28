/**
 * 
 */
package com.zz91.sms.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.zz91.sms.domain.Template;
import com.zz91.sms.dto.ExtResult;
import com.zz91.sms.service.TemplateService;
/**
 * @author root
 * 
 */
@Controller
public class TemplateController extends BaseController {

	@Resource
	private TemplateService templateService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		return null;
	}

	@RequestMapping
	public ModelAndView queryTemplate(HttpServletRequest request,
			Map<String, Object> out) {

		List<Template> tempList = templateService.query();

		return printJson(tempList, out);
	}

	@RequestMapping
	public ModelAndView delete(HttpServletRequest request,
			Map<String, Object> out, Integer id) {

		Integer i = templateService.remove(id);

		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView add(HttpServletRequest request,
			Map<String, Object> out, Template template) {

		ExtResult result = new ExtResult();

		if (template != null) {
			Integer id = templateService.create(template);
			if (id != null && id.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView update(HttpServletRequest request, Map<String, Object> out, Template template) {
		ExtResult result = new ExtResult();

		if (template != null) {
			Integer im = templateService.update(template);
			if (im != null && im.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryById(HttpServletRequest request,
			Map<String, Object> out, Integer id) {

		Template temp = templateService.queryOne(id);

		return printJson(temp, out);
	}
}
