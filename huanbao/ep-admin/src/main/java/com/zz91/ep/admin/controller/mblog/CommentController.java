package com.zz91.ep.admin.controller.mblog;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.mblog.MBlogCommentService;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;
@Controller
public class CommentController extends BaseController {
	
	@Resource
	private MBlogCommentService mBlogCommentService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryAllMblogComment(HttpServletRequest request,
			Map<String, Object> out,MBlogComment comment,MBlogInfo info, PageDto<MBlogCommentDto> page){
		page.setLimit(20);
		page=mBlogCommentService.queryAllMblogComment(comment,info,page);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request,
			Map<String, Object> out,Integer id){
		ExtResult result=new ExtResult();
		Integer i = mBlogCommentService.delete(id);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView	updateDeleteStatus(HttpServletRequest request,
			Map<String, Object> out,Integer id){
		ExtResult result=new ExtResult();
		Integer i = mBlogCommentService.updateDeleteStatus(id);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
