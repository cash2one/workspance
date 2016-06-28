package com.ast.ast1949.bbs.controller.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.bbs.controller.BaseController;
import com.ast.ast1949.domain.bbs.AnalysisBbsTop;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.service.bbs.AnalysisBbsTopService;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.util.StringUtils;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-11-4 
 */
@Controller
public class BbsFragmentController extends BaseController{
	
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private AnalysisBbsTopService analysisBbsTopService;
	
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	
	/**
	 * 24小时热帖
	 * @param out
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView hotReply(Map<String, Object> out,Integer size,Integer categoryId) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50){
			size=50;
		}
		List<BbsPostDO> postDailylist=bbsPostService.query24HourHot(size,categoryId);
		map.put("postDailylist", postDailylist);
		return printJson(map, out);
	}
	/**
	 * 最新资讯
	 * @param out
	 * @param categoryId 类别id
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView newestPost(Map<String, Object> out,Integer categoryId,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50){
			size=50;
		}
		List<BbsPostDO> newsList=bbsPostService.querySimplePostByCategory(categoryId, size);
		map.put("newsList", newsList);
		return printJson(map, out);
	}
	/**
	 * 历史牛帖
	 * @param out
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView mostPost(Map<String, Object> out,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50){
			size=50;
		}
		List<BbsPostDO> allBbsPostList=bbsPostService.queryMostViewedPost(size);
		map.put("allBbsPostList", allBbsPostList);
		return printJson(map, out);
	}
	
	/**
	 * 最新话题
	 * @param out
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView newPost(Map<String, Object> out,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50){
			size=50;
		}
		List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(size);
		map.put("frontBbsPostList", frontBbsPostList);
		return printJson(map, out);
	}
	
	/**
	 * 本周牛人，牛帖 
	 * @param out
	 * @param category 类型：post-牛帖 profile-牛人
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView bbsTop(Map<String, Object> out,String category,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50){
			size=50;
		}
		List<AnalysisBbsTop> postOneWeeklist=analysisBbsTopService.queryBbsTopsByType(category, size);
		map.put("postOneWeeklist", postOneWeeklist);
		return printJson(map, out);
	} 
	/**
	 * 最牛网商
	 * @param out
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView topByPostNum(Map<String, Object> out,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50){
			size=50;
		}
		List<BbsUserProfilerDO> allIntegralList = bbsUserProfilerService.queryTopByPostNum(6);
		map.put("allIntegralList", allIntegralList);
		return printJson(map, out);
	}
	
	/**
	 * 根据帖子类型查帖
	 * @param out
	 * @param postType 帖子类型
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView postByType(Map<String, Object> out,String postType,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50){
			size=50;
		}
		List<PostDto> list=bbsPostService.queryPostByType(postType, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView hostPost(Map<String, Object> out,String account,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50) {
			size=50;
		}
		if (account==null) {
			account=null;
		}
		List<BbsPostDO> hotBbsPostList = bbsPostService.queryHotPost(account,size);
		map.put("list", hotBbsPostList);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView search(HttpServletRequest request, Map<String, Object> out, Integer size, String keywords) throws IOException{
		if(size==null || size.intValue()<=0){
			size=10;
		}
		if(size.intValue()>100){
			size=100;
		}
		if(StringUtils.isEmpty(keywords)){
			keywords="";
		}
		keywords=StringUtils.decryptUrlParameter(keywords);
		
		PageDto<PostDto> pageDto=new PageDto<PostDto>();
		pageDto.setPageSize(size);
		
		//pageDto = bbsPostService.pagePostBySearchEngine(keywords, pageDto);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", pageDto.getRecords());
		return printJson(map, out);
	}
}
