/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-2
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.exception.ServiceLayerException;
import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.service.auth.ParamTypeService;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.servlet.InitSystem;
import com.zz91.util.domain.Param;
import com.zz91.util.domain.ParamType;
import com.zz91.util.param.ParamUtils;

/**
 * @author Mr.Mar (x03570227@gmail.com)
 *
 */
@Controller
public class ParamController extends BaseController {

	@Autowired
	private ParamService paramService;
	@Autowired
	private ParamTypeService paramTypeService;
	@Autowired
	private InitSystem initSystem;

	@RequestMapping
	public void view(Map<String, Object> model){
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
//		types="baseConfig"; // debug 只使用baseConfig配置
		PageDto page = new PageDto();
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
		PageDto page = new PageDto();
		page.setRecords(paramTypeService.listAllParamType());
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView createParamType(Map<String, Object> model, ParamType paramType) throws IOException{
		ExtResult result = new ExtResult();
		try {
			paramTypeService.createParamType(paramType);
			result.setSuccess(true);
		} catch (ServiceLayerException e) {
			result.setData(e.getMessage());
		}
		return printJson(result, model);
	}

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
		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
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
}
