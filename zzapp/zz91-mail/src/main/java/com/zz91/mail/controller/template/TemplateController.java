package com.zz91.mail.controller.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.mail.controller.BaseController;
import com.zz91.mail.domain.TemplateDomain;
import com.zz91.mail.domain.dto.ExtResult;
import com.zz91.mail.domain.dto.PageDto;
import com.zz91.mail.service.TemplateService;

/**
 * 模板管理controller
 * @author Leon
 * 2011.11.17
 *
 */
@Controller
public class TemplateController extends BaseController {
	
	@Resource
	private TemplateService templateService;
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryTemplates(HttpServletRequest request, Map<String, Object> out, 
			 TemplateDomain template, PageDto<TemplateDomain> page){
		page = templateService.pageAllTemplates(template, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView createTemplate(HttpServletRequest request, Map<String, Object> out, TemplateDomain templateDomain){
		Integer i = templateService.insertTemplate(templateDomain);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateTemplate(HttpServletRequest request, Map<String, Object> out, TemplateDomain template){
		Integer i = templateService.updateTemplate(template);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteTemplate(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = templateService.deleteTemplateById(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneTemplate(HttpServletRequest request, Map<String, Object> out, Integer id){
		TemplateDomain templateDomain = templateService.queryTemplateById(id);
		PageDto<TemplateDomain> page=new PageDto<TemplateDomain>();
		List<TemplateDomain> list=new ArrayList<TemplateDomain>();
		list.add(templateDomain);
		page.setRecords(list);
		return printJson(page, out);
	}
	
}
