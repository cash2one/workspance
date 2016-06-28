package com.zz91.ep.news.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.service.common.ParamService;
import com.zz91.ep.service.news.NewsZhuantiService;
import com.zz91.util.domain.Param;
import com.zz91.util.seo.SeoUtil;

/**
 * 专题管理controller
 * @author 黄怀清
 * 2012.09.12
 *
 */
@Controller
public class ZhuantiController extends BaseController{
	public static final String[] WHITE_DOC={".doc",".docx",".xls", ".pdf",".jpg", ".jpeg", ".gif", ".bmp", ".png"};
	public static final String[] BLOCK_ANY={".bat", ".sh", ".exe"};
	
	@Resource
	private NewsZhuantiService newsZhuantiService;
	@Resource
	private ParamService paramService;
	
	/**
	 * 专题首页
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		List<Param> params = paramService.queryParamsByType("zhuanti_category");
		
		out.put("categorys", params);
		
		
		List<Zhuanti> list = newsZhuantiService.queryAttention(1);
		if(list.size()>0){
			Zhuanti first=list.get(0);
			out.put("first", first);
		}
		
		
		SeoUtil.getInstance().buildSeo("zhuantiIndex", null, null, null, out);
		return null;
	}
	
	
	/**
	 * 根据类别查询专题列表
	 * @param request
	 * @param out
	 * @param page
	 * @param category
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request, Map<String, Object> out,PageDto<Zhuanti> page,
			String category) 
			throws IOException{
		page = newsZhuantiService.pageByCategory(category, page);
		out.put("page", page);
		Param pa=paramService.queryParamByKey(category);
		out.put("category", pa);
		
		
		
		// SEO
		String[] titleParam = { pa.getName() };
		String[] keywordsParam = { pa.getName() };
		String[] descriptionParam = { pa.getName() };
		SeoUtil.getInstance().buildSeo("zhuantiList", titleParam, keywordsParam,
				descriptionParam, out);
		return null;
	}
	
}
