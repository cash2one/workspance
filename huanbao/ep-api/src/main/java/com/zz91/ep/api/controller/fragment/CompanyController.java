/*
 * 文件名称：CompanyController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.api.controller.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.ep.api.controller.BaseController;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsAndNewsDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.comp.CompNewsService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.comp.IndustryChainService;
import com.zz91.ep.service.comp.SubnetCompRecommendService;
import com.zz91.ep.service.news.NewsService;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：公司页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class CompanyController extends BaseController {

	@Resource
	private CompProfileService compProfileService;
	
	@Resource
	private CompNewsService compNewsService;
	
	@Resource
	private SolrService solrService;
	
	@Resource
	private SubnetCompRecommendService subnetCompRecommendService;
	@Resource
	private IndustryChainService	industryChainService;
	
	@Resource
	private NewsService  newsService;

	final static String q_code = "100010041002";
	final static String h_code = "100010041000";
	final static String J_code = "100010041001";


	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新注册公司信息列表（信息填写完整的信息）。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView newestCompany(HttpServletRequest request, Map<String, Object> out,String industryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompProfile> list = compProfileService.queryNewestCompany(industryCode,size);
		map.put("list", list);
		return printJson(map, out);
	}

	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐公司列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendCompany(HttpServletRequest request, Map<String, Object> out, String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompProfile> list = compProfileService.queryRecommendCompany(categoryCode, size);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取公司发布资讯信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping     
	public ModelAndView newestCompNews(HttpServletRequest request, Map<String, Object> out,String categoryCode, Integer cid, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompNews> list = compNewsService.queryNewestCompNews(categoryCode, cid, size);
		map.put("list", list);
		return printJson(map, out);
	}
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取高级会员公司发布资讯信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2013-06-06　　　方潮　　　　　　　 1.0.0　　　　　创建类文件
	 */
	@RequestMapping     
	public ModelAndView newestCompNewsTop(HttpServletRequest request, Map<String, Object> out,Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompNews> topList = compNewsService.queryNewestCompNewsTop(size);
	    map.put("topList", topList);
		return printJson(map, out);
	}
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取普通会员公司最新发布资讯信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2013-06-06　　　方潮　　　　　　　 1.0.0　　　　　创建类文件
	 */
	@RequestMapping     
	public ModelAndView newestCompNewsSize(HttpServletRequest request, Map<String, Object> out,Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
	    List<CompNews> sizeList = compNewsService.queryNewestCompNewsSize(size);
		map.put("sizeList", sizeList);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取相关公司信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView keywordCompany(HttpServletRequest request, Map<String, Object> out,String keyword, Integer size) throws SolrServerException, UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
//		String keywordEncode=StringUtils.decryptUrlParameter(keyword);
		List<CompProfile> list = solrService.queryCompanyByKeyword(keyword, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取子网推荐公司信息
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-06-27　　　齐振杰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView subRecommendComp(HttpServletRequest request,Map<String, Object> out,String cate,Integer size){
		List<CompProfile> list=subnetCompRecommendService.queryCompBySubRec(cate, size);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 获取产业链
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView loadIndustryChain(HttpServletRequest request,Map<String, Object> out,Integer size){
		List<IndustryChain> list = industryChainService.queryIndustryChains(size, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("industryChains", list);
		return printJson(map, out);
	};
	/**
	 * 获取最新资讯和技术文章
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryNewsInTechnical(Map<String, Object> out,Integer size) {

		Map<String, Object> map = new HashMap<String, Object>();

		List<CompNewsAndNewsDto> newsDtoList = new ArrayList<CompNewsAndNewsDto>();

		PageDto<CompNewsDto> page=new PageDto<CompNewsDto>();
		
		// 查询新闻版块文章
		List<CompNewsDto> compList=compNewsService.pageCompNewsForArticle(page).getRecords();
	
		//技术参数，前沿技术，环保百科
		List<News> newsList= new ArrayList<News>();
		
		// 技术参数
		List<News> jList = newsService.queryNewsByCode(J_code,size);
	    // 前沿技术
		List<News> qList = newsService.queryNewsByCode(q_code,size);
		
		// 环保百科
		List<News> hList = newsService.queryNewsByCode(h_code,size);
		//集合合并
		newsList.addAll(jList);
		newsList.addAll(qList);
		newsList.addAll(hList);
		
		List<CompNewsAndNewsDto> newsDtoList1 = new ArrayList<CompNewsAndNewsDto>();
		
		for (CompNewsDto compNewsDto : compList) {
			CompNewsAndNewsDto dto=new CompNewsAndNewsDto(); 
			dto.setId(compNewsDto.getCompNews().getId());
			dto.setTitle(compNewsDto.getCompNews().getTitle());
			dto.setTypeCode(compNewsDto.getCompNews().getCategoryCode());
			dto.setGmtPublish(compNewsDto.getCompNews().getGmtPublish());
			newsDtoList1.add(dto);
		}
		
		for (News news : newsList) {
			CompNewsAndNewsDto dto=new CompNewsAndNewsDto(); 
			dto.setId(news.getId());
			dto.setTitle(news.getTitle());
			dto.setTypeCode(news.getCategoryCode());
			dto.setGmtPublish(news.getGmtPublish());
			newsDtoList1.add(dto);
		}
		
		//新闻集合装map排序
		Map<Long ,Object>  map1=new  TreeMap<Long,Object>().descendingMap();
	    
		for (CompNewsAndNewsDto newsDto : newsDtoList1) {
		 	  map1.put(newsDto.getGmtPublish().getTime(), newsDto);
		}
	    
				
		//放入newsDtoList
	    for (Long lo : map1.keySet()) {
	    	CompNewsAndNewsDto compAndNewsDto=(CompNewsAndNewsDto) map1.get(lo);
	    	newsDtoList.add(compAndNewsDto);
		}
		
	      
		 map.put("newsDtoList", newsDtoList);
		 return printJson(map, out);
	}

	/**
	 * 获取最新技术文章
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryNewsForTechnical(Map<String, Object> out,PageDto<CompNewsDto> page,Integer size) {
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		if(size==null){
			   size=DEFAULT_SIZE;
		}
		page.setLimit(size);
		page = compNewsService.pageCompNewsForArticle(page);
		for (CompNewsDto compNewsDto : page.getRecords()) {
			if (compNewsDto.getCompNews().getDetails() != null) {
				compNewsDto.getCompNews().setDetails(
						Jsoup.clean(compNewsDto.getCompNews().getDetails().replaceAll("&nbsp;", ""),
								Whitelist.none()));
			}
		}

		map.put("list", page.getRecords());
	   
		return printJson(map, out);
		
	}

}