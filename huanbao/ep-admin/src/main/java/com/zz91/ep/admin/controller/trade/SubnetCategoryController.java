package com.zz91.ep.admin.controller.trade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.trade.SubnetCategoryService;
import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.dto.ExtResult;

@Controller
public class SubnetCategoryController extends BaseController {
	
	@Resource
	private SubnetCategoryService subnetCategoryService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryCategoryByParentId(HttpServletRequest request,Map<String, Object> out,Integer parentId){
		List<SubnetCategory> childList=subnetCategoryService.queryCategoryByParentId(parentId);
		return printJson(childList, out);
	}
	
	@RequestMapping
	public ModelAndView addSubnetCategory(HttpServletRequest request,Map<String, Object> out,SubnetCategory category){
		ExtResult result = new ExtResult();
		Integer i=subnetCategoryService.createSubnetCategory(category);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateSubnetCategory(HttpServletRequest request,Map<String, Object> out,SubnetCategory category){
		ExtResult result = new ExtResult();
		Integer i=subnetCategoryService.updateSubnetCategory(category);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	
	@RequestMapping
	public ModelAndView deleteCategory(HttpServletRequest request,Map<String, Object> out,Integer id){
		ExtResult result = new ExtResult();
		Integer i=subnetCategoryService.deleteCategory(id);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteChildCategory(HttpServletRequest request,Map<String, Object> out,Integer id,Integer parentId){
		ExtResult result = new ExtResult();
		Integer i=subnetCategoryService.deleteChildCategory(id, parentId);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
