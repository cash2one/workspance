/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.company.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.comp.CompTags;
import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompTagsDto;
import com.zz91.ep.dto.comp.CompanyNormDto;
import com.zz91.ep.dto.search.SearchCompanyDto;
import com.zz91.ep.service.common.CoreSeekService;
import com.zz91.ep.service.common.ParamService;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.common.SysAreaService;
import com.zz91.ep.service.comp.CompTagsService;
import com.zz91.ep.service.comp.IndustryChainService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.lang.StringUtils;
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
	private SolrService solrService;
	
	@Resource
	private ParamService paramService;
	
	@Resource
	private TradeCategoryService tradeCategoryService;
	
	@Resource
	private SysAreaService sysAreaService;
	
	@Resource
	private CompTagsService compTagsService;
	
	@Resource
	private IndustryChainService industryChainService;
	@Resource
	private CoreSeekService coreSeekService;
	/**
	 * 函数名称：index
	 * 功能描述：访问首页时，查询相关数据。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012SearchCompanyDto.java/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request) {
		String url = resultUrl(request);
		if(url!=null){
			return new ModelAndView("redirect:" +url);
		}else{
        // 获取公司类型
        List<Param> companyCategory = paramService.queryParamsByType("company_industry_code");
        out.put("companyCategory", companyCategory);
        // 获取所处行业类型
        List<TradeCategory> categorys = tradeCategoryService.queryCategoryByParent("1000", 0, 0);
        out.put("companyIndustry", categorys);
        //取得所有的标签
        List<CompTagsDto> allComTag = compTagsService.queryCompTags();
        out.put("allComTag", allComTag);
        SeoUtil.getInstance().buildSeo("", null, null, null, out);
        return null;
		}
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
    
    /**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新公司列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-07-26　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView newc(HttpServletRequest request, Map<String, Object> out,
			SearchCompanyDto search, PageDto<CompanyNormDto> page) throws SolrServerException {
		
		//设置分页大小
		page.setLimit(100);
		
		if (search.getPageNum()==null){
			page.setStart(0);
		}else {	
			page.setStart((search.getPageNum()-1)*page.getLimit());
		}
		page = solrService.pageCompanyBySearch(search, page);
		
		out.put("search", search);
		out.put("page", page);
		
		
		//编辑SEO
		String pageNum="";
		if (search.getPageNum()!=null){
			if (search.getPageNum() > 1) {
				pageNum = "-第"+search.getPageNum()+"页";
			}
		}
		String seoName="";
		String title = "";
		String[] titleParam =null;
		if (StringUtils.isNotEmpty(search.getProvinceCode())) {
			SysArea area = sysAreaService.getSysAreaByCode(search.getProvinceCode());
			if(area!=null&&StringUtils.isNotEmpty(area.getName())){
				title = area.getName();
				seoName = "newcArea";	
			}
		}
		if(StringUtils.isEmpty(title)&&StringUtils.isNotEmpty(search.getIndustryCode())){
			Param param = paramService.queryParamByKey(search.getIndustryCode());
			if(param!=null&&StringUtils.isNotEmpty(param.getName())){
				title = param.getName();
				seoName = "newcIndustry";
			}
		}
		if(StringUtils.isEmpty(title)){
			titleParam = new String[]{pageNum};
			seoName = "newc";
		}else{
			titleParam = new String[]{title,pageNum};
		}
		SeoUtil.getInstance().buildSeo(seoName, titleParam, null, null, out);				
		return null;
	}
	
	/**
	 * 函数名称：getAreaCode
	 * 功能描述：根据父节点取省/地区
	 * 输入参数：
	 *        @param parentCode
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/07/26　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView getAreaCodeJson(Map<String, Object> out, HttpServletRequest request, String parentCode){
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<SysArea> sysAreas = sysAreaService.querySysAreasByCode(parentCode);
    	map.put("areas", sysAreas);
		return printJson(map, out);
    }
    
    /**
	 * 函数名称：getAreaCode
	 * 功能描述：根据父节点取省/地区
	 * 输入参数：
	 *        @param parentCode
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/07/26　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView getParamsByType(Map<String, Object> out, HttpServletRequest request, String code){
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<Param> params = paramService.queryParamsByType(code);
    	map.put("params", params);
		return printJson(map, out);
    }
    
    /**
     * @author 陈庆林
     * @param out
     * @param request
     * @param search
     * @param page
     * @return
     * @throws UnsupportedEncodingException
     * @throws SolrServerException
     */
	@RequestMapping
    public ModelAndView list(Map<String, Object> out,HttpServletRequest request,
    		SearchCompanyDto search, PageDto<CompanyNormDto> page,Integer ctagsId,String cityCode,Integer industryId)throws UnsupportedEncodingException, SolrServerException{
	
		String keywords = new String(search.getKeywords().toString().toLowerCase().getBytes("ISO-8859-1"), "UTF-8");
		search.setKeywords(keywords);
		String name = null;
		Boolean success = false;
		List<IndustryChain> list  =null;
		if(StringUtils.isEmpty(keywords)){
			do{
				if(ctagsId!=null){
					
					keywords = compTagsService.queryCompkewordsById(ctagsId);
					break;
				}
			
				if(StringUtils.isNotEmpty(search.getBusinessCode())){
					name = paramService.queryNameByTypeAndValue("company_industry_code", search.getBusinessCode());
					break;
				}
				if(StringUtils.isNotEmpty(search.getIndustryCode())){
			
					name = CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, search.getIndustryCode());
					break;
				}
				if (StringUtils.isNotEmpty(cityCode)) {
					if (search.getIndustryChainId() != null) {
						// 获取产业链名称
						IndustryChain indus = industryChainService
								.queryIndustryChainName(search
										.getIndustryChainId());
						name = indus.getAreaName() + indus.getCategoryName();
					} else {
						keywords = CodeCachedUtil.getValue(
								CodeCachedUtil.CACHE_TYPE_AREA, cityCode);
					}
					list = industryChainService.queryIndustryChains(null,cityCode);
					if (list.size() > 0)
						success = true;
					break;
				}
			}while(false);
			
			search.setKeywords(keywords);
			
		}else{
			
//			search.setEncodeKeywords(URLEncoder.encode(search.getKeywords(), "utf-8"));
			search.setEncodeKeywords(keywords);
		}
		
//		page = solrService.pageCompanyBySearch(search, page,1);
		page = coreSeekService.pageCompany(search, page);
		
		out.put("page", page);
		
		out.put("search", search);
		
		out.put("industryList", list);
		
		out.put("success", success);
		
		out.put("ctagsId", ctagsId);
		
		out.put("cityCode", cityCode);
		// 获取公司类型

		List<Param> companyCategory = paramService.queryParamsByType("company_industry_code");
		
		out.put("companyCategory", companyCategory);
		// 获取所处行业类型

		List<TradeCategory> categorys = tradeCategoryService.queryCategoryByParent("1000", 0, 0);

		out.put("companyIndustry", categorys);
		//查询标签

		List<CompTags> comptags = compTagsService.queryCompTagsById(ctagsId);

		out.put("comptags", comptags);

		String [] titleOrKey = new String[]{"环保",""};
		if(StringUtils.isNotEmpty(keywords)){
			out.put("keywords", keywords);
			titleOrKey[0] = search.getKeywords();
		}else if(StringUtils.isNotEmpty(name)){
			out.put("keywords", name);
			titleOrKey[0] = name;
		}

		// 翻页
		String pages = "";
		if(page.getStart()>=20){
			pages ="第" + (page.getStart()/page.getLimit()+1)+"页_";
			titleOrKey[1] = pages;
		}
		SeoUtil.getInstance().buildSeo("list", titleOrKey, titleOrKey, titleOrKey, out);
		return null;
	}
    
    /**
     * 设置url路径
     * @param request
     * @return
     */
    public String resultUrl(HttpServletRequest request){
    	String param = request.getQueryString();
    	String url = null;
    	if(param!=null){
			String [] nAndValue = param.split("=");
			if("businessCode".equals(nAndValue[0])){
				url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/list--c"+nAndValue[1]+".htm";
			}else if("industryCode".equals(nAndValue[0])){
				url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/list--a"+nAndValue[1]+".htm";
			}
		}
    	return url;
    }
}