package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.news.NewsTech;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.news.NewsTechDTO;
import com.ast.ast1949.service.news.NewsTechService;
import com.ast.ast1949.web.controller.BaseController;

@Controller
public class TechController extends BaseController{
	
	@Resource
	private NewsTechService newsTechService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out) throws IOException{
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView query(Map<String, Object> out,PageDto<NewsTech> page,NewsTechDTO newDto) throws IOException{
		page=newsTechService.pageTech(newDto, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView delete(Map<String, Object> out,Integer id) throws IOException{
		
		ExtResult result=new ExtResult();
		Integer i=newsTechService.delete(id);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView content(HttpServletRequest request, Map<String, Object> out, Integer id, String success){
		out.put("id", id);
		NewsTech newsTech=newsTechService.queryById(id);
		out.put("content", newsTech.getContent());
		out.put("success", success);
		return null;
	}
	@RequestMapping
	public ModelAndView queryOneTech(Map<String, Object> out,Integer id) throws IOException{
		
		NewsTech newsTech=newsTechService.queryById(id);
		return printJson(newsTech, out);
	}
	
	@RequestMapping
	public ModelAndView doCreate(HttpServletRequest request, Map<String, Object> out, 
			NewsTech newsTech) throws IOException{
		Integer i=newsTechService.createOneTech(newsTech);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView doUpdate(HttpServletRequest request, Map<String, Object> out, 
			NewsTech newsTech) throws IOException{
		Integer i=newsTechService.updateOneTech(newsTech);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView doUpdateContent(HttpServletRequest request, Map<String, Object> out, 
			Integer id, String content) throws IOException{
		Integer i=newsTechService.updateContent(id, content);
		if(i!=null && i.intValue()>0){
			out.put("success", "1");
		}else{
			out.put("success", "0");
		}
		out.put("id", id);
		return new ModelAndView("redirect:content.htm");
	}
	
}
