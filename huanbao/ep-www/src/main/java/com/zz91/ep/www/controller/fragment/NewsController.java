/*
 * 文件名称：NewsController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.fragment;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.news.NewsCategory;
import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.news.NewsCategoryService;
import com.zz91.ep.service.news.NewsService;
import com.zz91.ep.service.news.NewsZhuantiService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.www.controller.BaseController;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：资讯相关页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 *			2012-09-20		马元生				1.0.1		移到ep-api项目
 */
@Deprecated
@Controller
public class NewsController extends BaseController {
	
	@Resource
	private NewsService newsService;
	
	@Resource
	private SolrService solrService;
	
	@Resource
	private TradeCategoryService tradeCategoryService;
	
	@Resource
	private NewsCategoryService newsCategoryService;
	@Resource
	private NewsZhuantiService newsZhuantiService;
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别资讯列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView categoryNews(HttpServletRequest request, Map<String, Object> out, String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<News> list = newsService.queryNewsByCategory(categoryCode, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别资讯列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView newsSubCategory(HttpServletRequest request, Map<String, Object> out, 
			String code,Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<NewsCategory> list = newsCategoryService.querySubNewsCategoryByCode(code,size);
		map.put("sublist", list);
		return printJson(map, out);
	}
	
	
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐资讯列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendNews(HttpServletRequest request, Map<String, Object> out, String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<CommonDto> list = newsService.queryNewsByRecommend(categoryCode, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：不同类别导航内容。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 * @throws UnsupportedEncodingException 
	 */
//	@RequestMapping
//	public ModelAndView daohangNews(HttpServletRequest request, Map<String, Object> out, String keyword, String categoryCode, Integer size) throws SolrServerException, UnsupportedEncodingException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (size == null) {
//			size = DEFAULT_SIZE;
//		}
////		String keywordEncode=StringUtils.decryptUrlParameter(keyword);
//		List<NewsSearchDto> list = solrService.queryNewByKeywords(keyword, size);
//		map.put("list", list);
//		List<String> tags = tradeCategoryService.queryTagsByCode(categoryCode);
//		map.put("tags", tags);
//		return printJson(map, out);
//	}

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：不同类别导航内容。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 * @throws UnsupportedEncodingException 
	 */
//	@RequestMapping
//	public ModelAndView keywordNews(HttpServletRequest request, Map<String, Object> out, String keyword, Integer size) throws SolrServerException, UnsupportedEncodingException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (size == null) {
//			size = DEFAULT_SIZE;
//		}
////		String keywordEncode=StringUtils.decryptUrlParameter(keyword);
//		List<NewsSearchDto> list = solrService.queryNewByKeywords(keyword, size);
//		map.put("list", list);
//		return printJson(map, out);
//	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：资讯阅读排行榜。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView topNews(HttpServletRequest request,Map<String, Object> out,Integer size){
		// 阅读排行榜(top榜)
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<News> topNewsList=newsService.queryTopNews(size);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("list", topNewsList);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别专题列表
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-09-18　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView categoryZhuanti(HttpServletRequest request, Map<String, Object> out, String category, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<Zhuanti> list = newsZhuantiService.queryByCategory(category, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐资讯列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-09-18　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendZhuanti(HttpServletRequest request, Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<Zhuanti> list = newsZhuantiService.queryRecommend(size);
		map.put("list", list);
		return printJson(map, out);
	}
}