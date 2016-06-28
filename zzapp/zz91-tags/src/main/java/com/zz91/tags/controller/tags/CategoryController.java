package com.zz91.tags.controller.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.tags.controller.BaseController;
import com.zz91.tags.domain.TagsCategory;
import com.zz91.tags.domain.TagsCategoryCollection;
import com.zz91.tags.dto.ExtResult;
import com.zz91.tags.dto.ExtTreeDto;
import com.zz91.tags.dto.Pager;
import com.zz91.tags.service.TagsCategoryCollectionService;
import com.zz91.tags.service.TagsCategoryService;
@Controller
public class CategoryController extends BaseController{
	
	@Resource
	private TagsCategoryCollectionService tagsCategoryCollectionService;
	@Resource
	private TagsCategoryService tagsCategoryService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	/**
	 * 删除标签关联
	 */
	@RequestMapping
	public ModelAndView deleteCollection(HttpServletRequest request,Integer id,Map<String, Object> out)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=tagsCategoryCollectionService.deleteTagsCategoryCollection(id);
		if(i != null && i.intValue() > 0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 标签关联列表（附带列表功能）
	 */
	@RequestMapping
	public ModelAndView queryCollection(HttpServletRequest request,String categoryCode,
			Pager<TagsCategoryCollection> page,Map<String, Object> out) throws IOException{
		page = tagsCategoryCollectionService.pageCollection(categoryCode, false, page);
		return printJson(page, out);
	}
	/**
	 * 创建标签关联
	 */
	@RequestMapping
	public ModelAndView createCollection(HttpServletRequest request,Map<String, Object> out, TagsCategoryCollection tags)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=tagsCategoryCollectionService.createTagsCategoryCollection(tags);
		if(i != null && i.intValue() > 0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 创建标签类别
	 */
	@RequestMapping
	public ModelAndView createCategory(HttpServletRequest request,TagsCategory tags,String parentCode,Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=tagsCategoryService.createTagsCategory(tags, parentCode);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 删除标签类别。同时删除关联表相关数据
	 */
	@RequestMapping
	public ModelAndView deleteCategory(HttpServletRequest request,String code,Map<String, Object> out) 
			throws IOException  {
		ExtResult result=new ExtResult();
		Integer i=tagsCategoryService.deleteTagsCategory(code);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 更新标签类别，同时更新关联表
	 */
	@RequestMapping
	public ModelAndView updateCategory(HttpServletRequest request,TagsCategory tags,Map<String, Object> out)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=tagsCategoryService.updateTagsCategory(tags);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView child(HttpServletRequest request, Map<String, Object> out, String parentCode) 
			throws IOException{
		List<ExtTreeDto> categoryNode = tagsCategoryService.queryTagsCategroyNode(parentCode);
		return printJson(categoryNode, out);
	}
	
	@RequestMapping
	public ModelAndView queryCategoryByCode(HttpServletRequest request, Map<String, Object> out, String code){
		TagsCategory category=tagsCategoryService.queryCategoryByCode(code);
		List<TagsCategory> list=new ArrayList<TagsCategory>();
		if(category!=null){
			list.add(category);
		}
		return printJson(list, out);
	}
	
}
