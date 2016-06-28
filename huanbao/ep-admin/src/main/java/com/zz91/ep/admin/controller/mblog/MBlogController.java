package com.zz91.ep.admin.controller.mblog;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.mblog.MBlogCommentService;
import com.zz91.ep.admin.service.mblog.MBlogService;
import com.zz91.ep.admin.service.mblog.MBlogSystemService;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;
import com.zz91.ep.dto.mblog.MBlogDto;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Controller
public class MBlogController extends BaseController {
	
	@Resource
	private MBlogService mBlogService; 
	@Resource
	private MBlogCommentService mBlogCommentService;
	@Resource
	private MBlogSystemService mBlogSystemService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out){
		return null;
	}
	@RequestMapping
	public ModelAndView queryAllMBlog(HttpServletRequest request,
			Map<String, Object> out,PageDto<MBlogDto> page,MBlog mBlog,MBlogInfo info){
		page.setLimit(20);
		page=mBlogService.queryAllMBlog(mBlog, info, page);
		return printJson(page, out);
	}
	
	@RequestMapping		
	public ModelAndView queryCommentByMblogId(HttpServletRequest request,
			Map<String, Object> out,Integer mblogId){
		
		List<MBlogCommentDto> dtoList=mBlogCommentService.queryCommentByMblogId(mblogId);
		
		return printJson(dtoList, out);
	}
	
	@RequestMapping
	public ModelAndView updateMBlog(HttpServletRequest request,
			Map<String, Object> out,MBlog mBlog){
		ExtResult result=new ExtResult();
		Integer i=mBlogService.updateMBlog(mBlog);
		
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request,
			Map<String, Object> out,Integer id) throws ParseException{
		ExtResult result=new ExtResult();
		//查询出人
		MBlog mBlog=mBlogService.queryOneMBlogById(id);
		String mblogDate= DateUtil.toString(mBlog.getGmtCreated(), "yyyy-MM-dd HH:mm");
		Integer i=mBlogService.delete(id);
		if(i!=null && i.intValue()>0){
			if(mBlog!=null && !mBlog.getIsDelete().equals("1")){
				//添加到系统表
				MBlogSystem mBlogSystem=new MBlogSystem();
				mBlogSystem.setFromId(1);
				mBlogSystem.setToId(mBlog.getInfoId());
				mBlogSystem.setType("4");
				if(mBlog.equals("2")){
					mBlogSystem.setContent(id+"=你好,你与"+mblogDate+"发布的话题有恶意发布的可能,所以我们将你的话题暂时删除,若你有任何问题可以给我们发邮件,邮箱地址为:service@huanbao.com,我们会在三个工作日内给你回复。");
				}else{
					mBlogSystem.setContent(id+"=你好,你与"+mblogDate+"发布的微文有恶意发布的可能,所以我们将你的微文暂时删除,若你有任何问题可以给我们发邮件,邮箱地址为:service@huanbao.com,我们会在三个工作日内给你回复。");	
				}
				mBlogSystemService.insert(mBlogSystem);
			}
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	//恢复数据
	@RequestMapping
	public ModelAndView updateDeleteStatus(HttpServletRequest request,
			Map<String, Object> out,Integer id){
		ExtResult result=new ExtResult();
		Integer i=mBlogService.updateDelete(id);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	
	/**
	 * 话题 
	 */
	
	@RequestMapping
	public ModelAndView topic(HttpServletRequest request,
			Map<String, Object> out){
		return null;
	}

	
	
	@RequestMapping
	public ModelAndView queryAllTopic(HttpServletRequest request,
			Map<String, Object> out,PageDto<MBlogDto> page,MBlog mBlog,MBlogInfo info){
		page.setLimit(20);
		if(mBlog!=null && StringUtils.isNotEmpty(mBlog.getTitle())){
		 mBlog.setTitle("#"+mBlog.getTitle());
		}
		page=mBlogService.queryAllTopic(mBlog, info, page);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView queryTopicByTitle(HttpServletRequest request,
			Map<String, Object> out,String title){
		List<MBlogDto> dtoList=mBlogService.queryTopicByTitle(title);
		return printJson(dtoList, out);
	}
	
	
}
