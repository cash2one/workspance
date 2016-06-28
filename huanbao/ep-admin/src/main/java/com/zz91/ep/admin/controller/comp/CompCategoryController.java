package com.zz91.ep.admin.controller.comp;

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
import com.zz91.ep.admin.service.comp.CompCategoryService;
import com.zz91.ep.domain.comp.CompCategory;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;

/**
 * 公司类别管理
 */
@Controller
public class CompCategoryController extends BaseController {


	@Resource
	private CompCategoryService compCategoryService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		return null;
	}

	/**
	 * 列出所有公司类别信息
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listCompCategory(Map<String, Object> model) throws IOException{
		PageDto<CompCategory> page = new PageDto<CompCategory>();
		page.setRecords(compCategoryService.listAllCompCategory());
		return printJson(page, model);
	}
	/**
	 * 增加公司类别信息
	 * @param model
	 * @param compCategory
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView createCompCategory(Map<String, Object> model, CompCategory compCategory) throws IOException{
		ExtResult result = new ExtResult();
		compCategoryService.createCompCategory(compCategory);
		result.setSuccess(true);
		return printJson(result, model);
	}

	/**
	 * 修改公司类别信息
	 * @param model
	 * @param compCategory
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateCompCategory(Map<String, Object> model, CompCategory compCategory) throws IOException{
		ExtResult result = new ExtResult();
		compCategoryService.updateCompCategory(compCategory);
		result.setSuccess(true);
		return printJson(result, model);
	}

	/**
	 * 查询单个公司类别信息
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listOneCompCategory(Map<String, Object> model, Integer id) throws IOException{
		List<CompCategory> list = new ArrayList<CompCategory>();
		list.add(compCategoryService.listOneCompCategoryById(id));
		PageDto<CompCategory> page = new PageDto<CompCategory>();
		page.setRecords(list);
		return printJson(page, model);
	}

	/**
	 * 删除公司类别信息
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView deleteCompCategory(Map<String, Object> model, Integer id) throws IOException{
		ExtResult result = new ExtResult();
		Integer i=compCategoryService.deleteCompCategory(id);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}
}
