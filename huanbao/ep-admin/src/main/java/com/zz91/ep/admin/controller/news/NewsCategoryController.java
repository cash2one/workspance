package com.zz91.ep.admin.controller.news;

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
import com.zz91.ep.admin.service.news.NewsCategoryService;
import com.zz91.ep.domain.news.NewsCategory;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;

/**
 * 会员权限管理controller
 * @author Leon
 * 2011.10.17
 *
 */
@Controller
public class NewsCategoryController extends BaseController {
	@Resource 
	private NewsCategoryService newsCategoryService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	/**
	 * 创建资讯类别
	 */
	@RequestMapping
	public ModelAndView createCategory(HttpServletRequest request,NewsCategory newsCategory,
			String parentCode,Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i = newsCategoryService.createCategory(newsCategory, parentCode);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 删除资讯类别，同时删除关联的资讯文章内容
	 */
	@RequestMapping
	public ModelAndView deleteCategory(HttpServletRequest request,String code,Map<String, Object> out) 
			throws IOException  {
		ExtResult result=new ExtResult();
		Integer i = newsCategoryService.deleteCategoryByCode(code);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 更新标签类别，同时更新关联表
	 */
	@RequestMapping
	public ModelAndView updateCategory(HttpServletRequest request, NewsCategory newsCategory, Map<String, Object> out)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i = newsCategoryService.updateCategory(newsCategory);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 通过父code查询出子类别
	 * @param request
	 * @param out
	 * @param parentCode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView child(HttpServletRequest request, Map<String, Object> out, String parentCode) 
			throws IOException{
		List<ExtTreeDto> categoryNode = newsCategoryService.queryChild(parentCode);
		return printJson(categoryNode, out);
	}
	/**
	 * 通过code查询资讯类别
	 * @param request
	 * @param out
	 * @param code
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryCategoryByCode(HttpServletRequest request, Map<String, Object> out, String code){
		NewsCategory newsCategory = newsCategoryService.queryNewsCategoryByCode(code);
		List<NewsCategory> list=new ArrayList<NewsCategory>();
		if(newsCategory!=null){
			list.add(newsCategory);
		}
		return printJson(list, out);
	}
}
