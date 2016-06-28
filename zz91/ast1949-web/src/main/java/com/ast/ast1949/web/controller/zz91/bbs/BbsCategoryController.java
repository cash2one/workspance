/**
 * 互助类别管理 
 * @author shiqp
 */
package com.ast.ast1949.web.controller.zz91.bbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.domain.news.NewsModuleDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.bbs.BbsPostCategoryService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.StringUtils;
@Controller
public class BbsCategoryController extends BaseController{
	@Resource
	private BbsPostCategoryService bbsPostCategoryService;
	@RequestMapping
	public void index(){
		
	}
	@RequestMapping
	public void index3(){
		
	}
	@RequestMapping
	public ModelAndView categoryTreeNode(HttpServletRequest request, Map<String, Object> out, Integer parentCode) throws IOException{
		if(parentCode==null){
			parentCode=0;
		}else if (parentCode.equals("0")) {
			parentCode=0;
		}
		List<ExtTreeDto> category=bbsPostCategoryService.queryCategoryByParentId(parentCode);
		return printJson(category, out);
	}
	@RequestMapping
	public ModelAndView querySimpleCategoryById(Integer id,Map<String, Object> model) throws IOException{
		BbsPostCategory bbsPostCategory =new BbsPostCategory();
		if(id!=null&&id.intValue()>0){
			bbsPostCategory=bbsPostCategoryService.querySimpleCategoryById(id);
		}
		return printJson(bbsPostCategory, model);
	}
	
	@RequestMapping
	public ModelAndView update(Map<String, Object> out,String id,String name) throws IOException{
		ExtResult result=new ExtResult();
		
		if(StringUtils.isNotEmpty(id)){
			Integer ids=Integer.valueOf(id);
			if(ids!=null&&ids.intValue()>0){
				BbsPostCategory bbsPostCategory=new BbsPostCategory();
				bbsPostCategory.setId(ids);
				bbsPostCategory.setName(name);
				Integer i=bbsPostCategoryService.updateCategoryById(bbsPostCategory);
				if(i!=null&&i.intValue()>0){
					result.setSuccess(true);
				}
			}
		}
		
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView delete(Map<String, Object>out,String id) throws IOException{
		ExtResult result=new ExtResult();
		if(StringUtils.isNotEmpty(id)){
			Integer ids=Integer.valueOf(id);
			if(ids!=null&&ids.intValue()>0){
				BbsPostCategory bbsPostCategory=new BbsPostCategory();
				bbsPostCategory.setId(ids);
				bbsPostCategory.setState(1);
				Integer i=bbsPostCategoryService.updateCategoryById(bbsPostCategory);
				if(i!=null&&i.intValue()>0){
					result.setSuccess(true);
				}
			}
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView addCategory(Map<String, Object>out,BbsPostCategory bbsPostCategory) throws IOException{
		ExtResult result=new ExtResult();
		if(bbsPostCategory!=null){
			if(bbsPostCategory.getParentId()==null){
				bbsPostCategory.setParentId(0);
			}
			bbsPostCategory.setState(0);
			Integer i=bbsPostCategoryService.insertCategory(bbsPostCategory);
			if(i!=null&&i.intValue()>0){
				result.setSuccess(true);
				result.setData(i);
			}
		}
		return printJson(result, out);
	}
}
