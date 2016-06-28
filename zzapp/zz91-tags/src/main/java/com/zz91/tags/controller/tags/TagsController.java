package com.zz91.tags.controller.tags;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.tags.controller.BaseController;
import com.zz91.tags.domain.Tags;
import com.zz91.tags.dto.ExtResult;
import com.zz91.tags.dto.Pager;
import com.zz91.tags.service.TagsService;

@Controller
public class TagsController extends BaseController{
	
	@Resource
	private TagsService tagsService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	/**
	 * 标签列表分页
	 */
	@RequestMapping
	public ModelAndView queryTags(HttpServletRequest request, Map<String, Object> out, 
			String tags, Pager<Tags> page){
		page=tagsService.pageTags(tags, page);
		return printJson(page, out);
	}
	/**
	 * 创建标签
	 */
	@RequestMapping
	public ModelAndView createTags(HttpServletRequest request,Tags tags,Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=tagsService.insertTags(tags);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateTags(HttpServletRequest request,Tags tags,Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=tagsService.updateTags(tags);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 删除标签
	 */
	@RequestMapping
    public ModelAndView deleteTags(HttpServletRequest request,String tags,Map<String, Object> out) throws IOException{
		ExtResult result=new ExtResult();
		Integer i=tagsService.deleteTags(tags);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
