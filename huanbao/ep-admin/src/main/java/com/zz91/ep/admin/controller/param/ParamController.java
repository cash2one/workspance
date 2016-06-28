/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-2
 */
package com.zz91.ep.admin.controller.param;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.init.InitSystem;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.ep.admin.service.sys.ParamTypeService;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.util.domain.Param;
import com.zz91.util.domain.ParamType;

/**
 *
 */
@Controller
public class ParamController extends BaseController {

	@Resource
	private ParamService paramService;
	@Resource
	private ParamTypeService paramTypeService;
	@Resource
	private InitSystem initSystem;

	@RequestMapping
	public ModelAndView index(Map<String, Object> model){
		return null;
	}

	@RequestMapping
	public ModelAndView createParam(Param param, Map<String, Object> model) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = paramService.insertParam(param);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView listParamByTypes(String types, Map<String, Object> model) throws IOException{
		PageDto<Param> page = new PageDto<Param>();
		page.setRecords(paramService.listParamByTypes(types));
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView updateParam(Param param, Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = paramService.updateParam(param);
		if(i!=null && i.intValue()>=0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView listParamType(Map<String, Object> model) throws IOException{
		PageDto<ParamType> page = new PageDto<ParamType>();
		page.setRecords(paramTypeService.listAllParamType());
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView createParamType(Map<String, Object> model, ParamType paramType) throws IOException{
		ExtResult result = new ExtResult();
			paramTypeService.createParamType(paramType);
			result.setSuccess(true);
		return printJson(result, model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView listOneParam(Map<String, Object> model, Param param) throws IOException{
		List<Param> list = new ArrayList<Param>();
		list.add(paramService.listOneParam(param.getId()));
		PageDto page = new PageDto();
		page.setRecords(list);
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView updateParamType(Map<String, Object> model, ParamType paramType) throws IOException{
		ExtResult result = new ExtResult();
		paramTypeService.updateParamType(paramType);
		result.setSuccess(true);
		return printJson(result, model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView listOneParamType(Map<String, Object> model, String key) throws IOException{
		List<ParamType> list = new ArrayList<ParamType>();
		list.add(paramTypeService.listOneParamTypeByKey(key));
		PageDto page = new PageDto();
		page.setRecords(list);
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView deleteParamType(Map<String, Object> model, String key) throws IOException{
		ExtResult result = new ExtResult();
		Integer i=paramTypeService.deleteParamType(key);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView deleteParam(Map<String, Object> model, Integer id) throws IOException{
		ExtResult result = new ExtResult();
		Integer i=paramService.deleteParam(id);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView refreshcache(Map<String, Object> model) throws IOException{
		ExtResult result=new ExtResult();
		initSystem.initParamConfig();
		result.setSuccess(true);
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView backup(Map<String, Object> model) throws IOException{
		ExtResult result = new ExtResult();
		String backup = paramService.backupToSqlString();
		if(backup!=null){
			result.setSuccess(true);
			result.setData(backup);
		}
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView paramByTypes(Map<String, Object> out,HttpServletRequest request,String types){
		List<Param> list=paramService.listParamByTypes(types);
		return printJson(list, out);
	}
}
