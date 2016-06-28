/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.exhibit.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.domain.exhibit.ExhibitIndustryCategory;
import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompanySearchDto;
import com.zz91.ep.dto.exhibit.ExhibitNormDto;
import com.zz91.ep.dto.search.SearchCompanyDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.common.SysAreaService;
import com.zz91.ep.service.exhibit.ExhibitIndustryCategoryService;
import com.zz91.ep.service.exhibit.ExhibitService;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：首页默认控制类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class RootController extends BaseController {
	
	@Resource
	private SysAreaService sysAreaService;
	
	@Resource
	private ExhibitService exhibitService;
	
	@Resource
	private SolrService solrService;

	@Resource
	private ExhibitIndustryCategoryService exhibitIndustryCategoryService;
	
	/**
	 * 函数名称：index
	 * 功能描述：访问首页时，查询相关数据。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request,
    		SearchCompanyDto search, PageDto<CompanySearchDto> page) throws UnsupportedEncodingException, SolrServerException{
		List<ExhibitIndustryCategory> IndustryCategory = exhibitIndustryCategoryService.queryExhibitIndustryCategoryAll();
		out.put("IndustryCategory", IndustryCategory);
		// 获取本周新增客户。
        String addCount = ParamUtils.getInstance().getValue("today_market", "weekCompanyCount");
        out.put("weekAddAccount", addCount);
        out.put("keywords", search.getKeywords());
		SeoUtil.getInstance().buildSeo("index", null, null, null, out);
        return null;
    }
	
	/**
	 * 函数名称：list
	 * 功能描述：列表页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView list(String categoryCode, PageDto<ExhibitNormDto> page,Map<String, Object> out) throws SolrServerException {
		Exhibit exhibit = new Exhibit();
		exhibit.setPlateCategoryCode(categoryCode);
		
		page = solrService.pageExhibitBySearch(exhibit, page);
		
		String categoryName = CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, categoryCode);
		out.put("page", page);
		out.put("categoryCode", categoryCode);
		out.put("categoryName", categoryName);
		
		List<ExhibitIndustryCategory> IndustryCategory = exhibitIndustryCategoryService.queryExhibitIndustryCategoryAll();
		out.put("IndustryCategory", IndustryCategory);
		
        // 获取本周新增客户 如果参数表中设置为0，取真实数据。
        String addCount = ParamUtils.getInstance().getValue("today_market", "weekCompanyCount");
        out.put("weekAddAccount", addCount);
        if (StringUtils.isEmpty(categoryName)) {
        	categoryName = "";
		}
        
    	String[] titleParam = {categoryName};
    	SeoUtil.getInstance().buildSeo("list", titleParam, null, null, out);
		return null;
	}
	
	/**
	 * 函数名称：search
	 * 功能描述：搜索列表页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView search(Exhibit exhibit, PageDto<ExhibitNormDto> page,
			String startTime, String endTime, Map<String, Object> out, String keywords) throws UnsupportedEncodingException, SolrServerException{
		if (StringUtils.isNotEmpty(startTime)) {
			try {
				exhibit.setGmtStart(DateUtil.getDate(startTime, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(endTime)) {
			try {
				exhibit.setGmtEnd(DateUtil.getDate(endTime, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String keyword = new String(keywords.getBytes(), "utf-8");
		if (StringUtils.isNotEmpty(keyword)) {
			exhibit.setName(keyword);
		}
		
		page = solrService.pageExhibitBySearch(exhibit, page);
		out.put("exhibitCondition", exhibit);
		
		if (StringUtils.isNotEmpty(exhibit.getName())) {
			out.put("encodeKeywords", URLEncoder.encode(exhibit.getName(), "utf-8"));
		}
		out.put("page", page);
		List<ExhibitIndustryCategory> IndustryCategory = exhibitIndustryCategoryService.queryExhibitIndustryCategoryAll();
		out.put("IndustryCategory", IndustryCategory);
        // 获取本周新增客户 如果参数表中设置为0，取真实数据。
        String addCount = ParamUtils.getInstance().getValue("today_market", "weekCompanyCount");
    	out.put("weekAddAccount", addCount);
        keywords = exhibit.getName();
        if (StringUtils.isEmpty(keywords) && StringUtils.isNotEmpty(exhibit.getIndustryCode())) {
        	keywords = CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, exhibit.getIndustryCode());
		}
        if (StringUtils.isEmpty(keywords)) {
        	keywords = "";
		}
    	String[] titleParam = {keywords};
    	String[] keywordsParam = {keywords};
    	String[] descriptionParam = {keywords};
    	SeoUtil.getInstance().buildSeo("search", titleParam, keywordsParam, descriptionParam, out);
		return null;
	}
	
	/**
	 * 函数名称：list
	 * 功能描述：展会详细页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView details(HttpServletRequest request, Map<String, Object> out,Integer id) throws ParseException {
		do {
			if (id == null || id.intValue() <= 0) {
				break;
			}
			Exhibit exhibit = exhibitService.queryExhibitDetailsById(id);
			
			if (exhibit == null) {
				break;
			}
			
			if (exhibit != null) {
				Document doc = Jsoup.parse(exhibit.getDetails());
				exhibit.setDetails(doc.select("body").html());
				out.put("exhibit", exhibit);
				//离开展时间
				Integer intervalDays=DateUtil.getIntervalDays(exhibit.getGmtStart(),new Date());
				out.put("intervalDays", intervalDays);
				//判断展会是否过期
				Integer expiredDay=DateUtil.getIntervalDays(exhibit.getGmtEnd(),new Date());
				out.put("expiredDay", expiredDay);
				List<ExhibitIndustryCategory> IndustryCategory = exhibitIndustryCategoryService.queryExhibitIndustryCategoryAll();
				out.put("IndustryCategory", IndustryCategory);
				String[] titleParam = {exhibit.getName()};
				String[] keywordsParam = {exhibit.getName()};
		    	String[] descriptionParam = {exhibit.getName()};
		    	SeoUtil.getInstance().buildSeo("details", titleParam, keywordsParam, descriptionParam, out);
			}
	        // 获取本周新增客户 如果参数表中设置为0，取真实数据。
	        String addCount = ParamUtils.getInstance().getValue("today_market", "weekCompanyCount");
	        out.put("weekAddAccount", addCount);
	        return null;
		}while(false);
		return new ModelAndView("redirect:index.htm");
	}
	
	/**
	 * 函数名称：resourceNotFound
	 * 功能描述：发生404错误页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView resourceNotFound(HttpServletRequest request, Map<String, Object> out) {
		//SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}
	
	/**
	 * 函数名称：uncaughtException
	 * 功能描述：发生异常错误页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView uncaughtException(HttpServletRequest request, Map<String, Object> out) {
		//SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}
	
	/**
	 * 函数名称：getAreaCode
	 * 功能描述：根据父节点取省/地区
	 *        比如：取中国所有省份调用url为 getAreaCode.htm?parentCode=10011000
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param parentCode(中国为10011000)
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView getAreaCode(Map<String, Object> out, HttpServletRequest request, String parentCode){
    	List<SysArea> sysAreas = sysAreaService.querySysAreasByCode(parentCode);
		return printJson(sysAreas, out);
    }
}