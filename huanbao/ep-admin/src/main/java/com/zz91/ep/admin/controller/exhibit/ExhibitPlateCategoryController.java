package com.zz91.ep.admin.controller.exhibit;

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
import com.zz91.ep.admin.service.exhibit.ExhibitPlateCategoryService;
import com.zz91.ep.domain.exhibit.ExhibitPlateCategory;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;

/**
 * 展会板块类别controller
 * @author Leon
 * 2011.10.17
 *
 */
@Controller
public class ExhibitPlateCategoryController extends BaseController {
	@Resource 
	private ExhibitPlateCategoryService exhibitPlateCategoryService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	/**
	 * 创建展会类别
	 */
	@RequestMapping
	public ModelAndView createCategory(HttpServletRequest request,ExhibitPlateCategory exhibitPlateCategory,
			String parentCode,Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i = exhibitPlateCategoryService.createCategory(exhibitPlateCategory, parentCode);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 删除展会类别，同时删除关联的展会文章内容
	 */
	@RequestMapping
	public ModelAndView deleteCategory(HttpServletRequest request,String code,Map<String, Object> out) 
			throws IOException  {
		ExtResult result=new ExtResult();
		Integer i = exhibitPlateCategoryService.deleteCategory(code);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 更新标签类别，同时更新关联表
	 */
	@RequestMapping
	public ModelAndView updateCategory(HttpServletRequest request, ExhibitPlateCategory exhibitPlateCategory, Map<String, Object> out)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i = exhibitPlateCategoryService.updateCategory(exhibitPlateCategory);
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
		List<ExtTreeDto> categoryNode = exhibitPlateCategoryService.queryCategoryNode(parentCode);
		return printJson(categoryNode, out);
	}
	/**
	 * 通过code查询展会类别
	 * @param request
	 * @param out
	 * @param code
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryCategoryByCode(HttpServletRequest request, Map<String, Object> out, String code){
		ExhibitPlateCategory exhibitPlateCategory = exhibitPlateCategoryService.queryCategoryByCode(code);
		List<ExhibitPlateCategory> list=new ArrayList<ExhibitPlateCategory>();
		if(exhibitPlateCategory!=null){
			list.add(exhibitPlateCategory);
		}
		return printJson(list, out);
	}
}
