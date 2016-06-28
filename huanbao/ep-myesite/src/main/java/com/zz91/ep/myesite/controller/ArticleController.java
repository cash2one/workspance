package com.zz91.ep.myesite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.comp.CompNewsService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.tags.TagsUtils;

@Controller
public class ArticleController extends BaseController {

	@Resource
	private CompNewsService compNewsService;
	@Resource
	private MyEsiteService myEsiteService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request, Short pause, 
			PageDto<CompNews> page, String type, String success,Short checkStatus) {
		if(pause==null){
			pause=1;
		 }
		
	   out.put("pause", pause);
	
//		if(StringUtils.isEmpty(type)){
//			type = "1000";
//		}
	
		out.put("type", type);
		out.put("category", CATEGORYMAP_MAP);
		EpAuthUser cachedUser = getCachedUser(request);
//		page.setLimit(5);
		short status=2;
		
	    if(checkStatus!=null && checkStatus==status){
	    	if(StringUtils.isEmpty(type)){
	    	page = compNewsService.pageCompNewsByCid(cachedUser.getCid(), null,
					null, null, checkStatus, (short)1, page);
	    	}else if(StringUtils.isNotEmpty(type)){
	    		if(type==null){
	    			type="1000";
	    		}
	    		page = compNewsService.pageCompNewsByCid(cachedUser.getCid(), type,
						null, null, checkStatus, (short)1, page); 
	    	}
	    }else{
	    	if(StringUtils.isEmpty(type)){
	    		type="1000";	
	    	}
	    	page = compNewsService.pageCompNewsByCid(cachedUser.getCid(), type,
		 			null, pause, checkStatus, (short)1, page);
	    }
		out.put("page", page);
		out.put("id", cachedUser.getCid());
		out.put("checkStatus", checkStatus);
		
		//查询所有未通过审核
		
		myEsiteService.init(out, cachedUser.getCid());
		out.put("success", success);
		return null;

	}

	/**
	 * 
	 * @param out
	 * @param request
	 * @param type 文章类别
	 * @param adOrUp 0 添加文章,1 修改文章
	 * @return
	 */
	@RequestMapping
	public ModelAndView addOrUpdate(Map<String, Object> out, HttpServletRequest request,
			String type, Integer id, Integer pause){
		
		if(id!=null && id.intValue()>0){
			CompNews compNews = compNewsService.queryCompNewsById(id);
			if (compNews == null) {
				return new ModelAndView("redirect:index.htm");
			}
			out.put("compNews", compNews);
		}
		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("type", type);
		out.put("pause", pause);
		
		out.put("category", CATEGORYMAP_MAP);
		return null;
	}
	
//	/**
//	 * 首页
//	 * 
//	 * @param out
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping
//	public ModelAndView index(Map<String, Object> out,
//			HttpServletRequest request) {
//		return new ModelAndView("redirect:comnews.htm");
//	}

	/**
	 * 公司动态
	 */
//	@RequestMapping
//	public ModelAndView comnews(Map<String, Object> out,
//			HttpServletRequest request, Short status, PageDto<CompNews> page) {
//		if (status == null) {
//			status = 1;
//		}
//		page.setSort("gmt_publish");
//		page.setDir("desc");
//		page = compNewsService.pageCompNewsByCid(getCompanyId(request), "1000",
//				null, status, null, (short)1, page);
//		out.put("page", page);
//		out.put("item", "comnews");
//		out.put("status", status);
//		return null;
//	}
	

	/**
	 * 跳转到添加文章页面
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
//	@RequestMapping
//	public ModelAndView addArticle(Map<String, Object> out,
//			HttpServletRequest request, String type) {
//		if ("1000".equals(type)) {
//			out.put("type", type);
//			out.put("item", "comnews");
//		} else if ("1001".equals(type)) {
//			out.put("type", type);
//			out.put("item", "techares");
//		} else if ("1002".equals(type)) {
//			out.put("type", type);
//			out.put("item", "succstories");
//		} else {
//			out.put("type", "1000");
//			out.put("item", "comnews");
//		}
//		return null;
//	}

	/**
	 * 添加文章
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
//	@RequestMapping
//	public ModelAndView doArticle(Map<String, Object> out,
//			HttpServletRequest request, String title, String details,
//			String categoryCode) {
//		CompNews compNews = new CompNews();
//		compNews.setCategoryCode(categoryCode);
//		compNews.setTitle(title);
//		compNews.setDetails(details);
//		compNews.setCid(getCompanyId(request));
//		compNews.setCheckStatus((short) 0);
//		compNews.setPauseStatus((short) 1);
//		String url = compNewsService.createArticle(compNews);
//		return new ModelAndView(url);
//	}

	/**
	 * 修改文章
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
//	@RequestMapping
//	public ModelAndView doUpdateArticle(Map<String, Object> out,
//			HttpServletRequest request, Integer id, String title,
//			String details, String categoryCode) {
//		CompNews compNews = new CompNews();
//		compNews.setId(id);
//		compNews.setTitle(title);
//		compNews.setDetails(details);
//		compNews.setCategoryCode(categoryCode);
//		compNews.setCid(getCompanyId(request));
//		String url = compNewsService.updateArticle(compNews);
//		return new ModelAndView(url);
//
//	}

	/**
	 * 修改文章
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
//	@RequestMapping
//	public ModelAndView updateArticle(Map<String, Object> out,
//			HttpServletRequest request, Integer id) {
//		CompNews compNews = compNewsService.queryCompNewsById(id);
//		if (compNews == null) {
//			return new ModelAndView("redirect:comnews.htm");
//		}
//		if ("1000".equals(compNews.getCategoryCode())) {
//			out.put("item", "comnews");
//		} else if ("1001".equals(compNews.getCategoryCode())) {
//			out.put("item", "techares");
//		} else if ("1002".equals(compNews.getCategoryCode())) {
//			out.put("item", "succstories");
//		} else {
//			out.put("item", "comnews");
//		}
//		out.put("compNews", compNews);
//		return null;
//	}

	/**
	 * 删除文章
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView doDelete(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		ExtResult result = compNewsService.deleteArticle(id,
				getCompanyId(request));
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}

	/**
	 * 发布/暂不发布文章
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView doPause(Map<String, Object> out,
			HttpServletRequest request, Integer id, Short status) {
		ExtResult result = compNewsService.publishArticle(id, getCompanyId(request), status);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}
	
	 /**
     * 技术文章
     */
//    @RequestMapping
//    public ModelAndView techares(Map<String, Object> out, HttpServletRequest request, Short status, PageDto<CompNews> page) {
//    	if (status == null) {
//    		status = 1;
//		}
//    	page.setSort("gmt_publish");
//        page.setDir("desc");
//        page = compNewsService.pageCompNewsByCid(getCompanyId(request), "1001", null, status, null,(short)1, page);
//        out.put("page", page);
//    	out.put("item", "techares");
//    	out.put("status", status);
//        return null;
//    }
    
    /**
     * 成功案例文章
     */
//    @RequestMapping
//    public ModelAndView succstories(Map<String, Object> out, HttpServletRequest request, Short status, PageDto<CompNews> page) {
//    	if (status == null) {
//    		status = 1;
//		}
//    	page.setSort("gmt_publish");
//        page.setDir("desc");
//        page = compNewsService.pageCompNewsByCid(getCompanyId(request), "1002", null, status, null, (short)1, page);
//        out.put("page", page);
//    	out.put("item", "succstories");
//    	out.put("status", status);
//        return null;
//    }
	
	final static Map<String, String> CATEGORYMAP_MAP=new HashMap<String, String>();
	static{
		CATEGORYMAP_MAP.put("1000", "公司动态");
		CATEGORYMAP_MAP.put("1001", "技术文章");
		CATEGORYMAP_MAP.put("1002", "成功案例");
	}
	
	
	

	
	/**
	 * 添加或者修改
	 * @param out
	 * @param request
	 * @param adOrUp
	 * @param compNews
	 * @return
	 */
	@RequestMapping
	public ModelAndView doAddOrUpdate(Map<String, Object> out, HttpServletRequest request,
			CompNews compNews){
		out.put("type", compNews.getCategoryCode());
		
		do{
			if(compNews==null){
				break;
			}
			
			EpAuthUser cachedUser = getCachedUser(request);
			
			compNews.setCid(cachedUser.getCid());
			compNews.setAccount(cachedUser.getAccount());
			
			compNews.setCheckStatus((short) 0);
			if(compNews.getPauseStatus()==null){
				
				compNews.setPauseStatus((short) 1);
			}

			//判断同一个公司是否添加相同的标题
			if (!compNewsService.validateTitleRepeat(compNews.getCid(), compNews.getTitle())) {
			    out.put("success", 0);
		        return new ModelAndView("redirect:index.htm");
			}
			//判断该操作是更新还是添加
			if(compNews.getTags()!=null){
			   compNews.setTags(TagsUtils.getInstance().arrangeTags(compNews.getTags()));
			}
			if(compNews.getId()!=null && compNews.getId().intValue()>0){
			        compNewsService.updateArticle(compNews);
			}else{
			        compNewsService.createArticle(compNews);
			}
		}while(false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("success", 1);
		return new ModelAndView("redirect:index.htm");
	}
}
