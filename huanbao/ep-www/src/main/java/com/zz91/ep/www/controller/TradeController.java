/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchBuyDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeBuyNormDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.common.SysAreaService;
import com.zz91.ep.service.trade.SubnetCategoryService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.cache.CodeCachedUtil;
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
public class TradeController extends BaseController {
	
	@Resource
	private SysAreaService sysAreaService;
	
	@Resource
	private SolrService solrService;
	
	@Resource
	private TradeCategoryService tradeCategoryService; 
	
	@Resource
	private SubnetCategoryService subnetCategoryService;
	
	@Resource
	private TradeSupplyService tradeSupplyService;
	
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
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
        // 获取本周新增客户
    	String addCount = ParamUtils.getInstance().getValue("today_market", "weekCompanyCount");
    	out.put("weekCompanyCount", addCount);
        SeoUtil.getInstance().buildSeo("tradeIndex", null, null, null, out);
        return null;
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
	 * 函数名称：bq
	 * 功能描述：
	 *        比如：取中国所有省份调用url为 getAreaCode.htm?parentCode=10011000
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param parentCode(中国为10011000)
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws UnsupportedEncodingException 
	 */
    @RequestMapping
    public ModelAndView bq(Map<String, Object> out, HttpServletRequest request,Integer id,SearchSupplyDto search, PageDto<TradeSupplyNormDto> page) throws SolrServerException, UnsupportedEncodingException{
    	TradeCategory tradecg = tradeCategoryService.queryTagsById(id);
    	if (tradecg != null) {
        	if (search.getPageNum() == null) {
        		search.setPageNum(1);
    		}
    		page.setStart((search.getPageNum()-1)*30);
        	search.setKeywords(tradecg.getTags());
        	search.setEncodeKeywords( URLEncoder.encode(tradecg.getTags(),"utf-8"));
        	out.put("search",search);
        	
        	page= tradeSupplyService.searchListSupply(search, page,"1");
			
        	
        	out.put("page", page);
        	out.put("id", id);
        	String parentCode = tradecg.getCode().substring(0,tradecg.getCode().length()-4);
        	List<TradeCategory> tradecgs = tradeCategoryService.queryCategoryByParent(parentCode, 0, 0);
        	out.put("tradecgs", tradecgs);
        	String[] titleParam = {search.getKeywords()};
			String[] keywordsParam = {search.getKeywords()};
			String[] descriptionParam = {search.getKeywords()};
			SeoUtil.getInstance().buildSeo("tradeBq", titleParam, keywordsParam, descriptionParam, out);
    	}
    	return null;
    }
    
    /**
     * 环保子网列表页
     * @param out
     * @param request
     * @param parentCode
     * @return
     * @throws UnsupportedEncodingException 
     * @throws SolrServerException 
     */
    @RequestMapping
    public ModelAndView cp(Map<String, Object> out, HttpServletRequest request,
    		String code,SearchSupplyDto search, PageDto<TradeSupplyNormDto> page) throws UnsupportedEncodingException, SolrServerException{
    	
    		out.put("code", code);
    		String k=CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_SUBNET, code);
    		
    		if (StringUtils.isNotEmpty(k)){
    			search.setKeywords(k);
    			search.setEncodeKeywords(URLEncoder.encode(k, "utf-8"));
    		}
    		
    		if (search.getPageNum()==null){
    			page.setStart(0);
    		}else {
    			page.setStart((search.getPageNum()-1)*30);
			}
		  	
    		String web=null;
		  	SubnetCategory cate=subnetCategoryService.queryCategoryByCode(code);
		  	out.put("parentId", cate.getParentId());
		  	
		  	if (cate.getParentId()>13){
		  		SubnetCategory cate1=subnetCategoryService.querySubCateById(cate.getParentId());
		  		web=buildIndusCode(cate1,out);
		  	}else {
				web=buildIndusCode(cate,out);
			}
		  	
		  	out.put("subCode", cate.getCode());
		  	out.put("subName", cate.getName());
		  	
    		// 根据相应过滤条件查询相关供应信息(搜索引擎)
		  	
		  	page= tradeSupplyService.searchListSupply(search, page,"1");
    		
    		out.put("page", page);
    		out.put("search", search);
			
    		//编辑SEO
    		String pageNum="";
    		if (search.getPageNum()!=null){
    			if (search.getPageNum() > 1) {
    				pageNum = "-第"+search.getPageNum()+"页";
    			}
    		}
    		
			String[] titleParam = {cate.getName(),pageNum,web};
			String[] keywordsParam = {cate.getName()};
			String[] descriptionParam = {cate.getName(),web,pageNum};
			SeoUtil.getInstance().buildSeo("tradeCp", titleParam, keywordsParam, descriptionParam, out);
			return null;
    }
    /**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新供应信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-07-19　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView newg(HttpServletRequest request, Map<String, Object> out,
			SearchSupplyDto search, PageDto<TradeSupplyNormDto> page) throws SolrServerException {
		//指定分页大小
		page.setLimit(100);

		if (search.getPageNum()==null){
			page.setStart(0);
		}else {
			page.setStart((search.getPageNum()-1)*page.getLimit());
		}
		
		page= tradeSupplyService.searchListSupply(search, page,"1");
		
		
		out.put("search", search);
		out.put("page", page);
		
		
		//编辑SEO
		String pageNum="";
		if (search.getPageNum()!=null){
			if (search.getPageNum() > 1) {
				pageNum = "-第"+search.getPageNum()+"页";
			}
		}
		
		String[] titleParam = {pageNum};
		String[] desc = null;
		String seoName = "tradeNewg";
		if (StringUtils.isNotEmpty(search.getCategory())) {
			TradeCategory cg = tradeCategoryService.getCategoryByCode(search.getCategory());
			titleParam = new String[]{cg.getName(),pageNum};
			desc = new String[]{cg.getName()};
			seoName = "tradeNewgCategory";
		}
		SeoUtil.getInstance().buildSeo(seoName, titleParam, null, desc, out);
		return null;
	}
    /**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-07-19　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView newb(HttpServletRequest request, Map<String, Object> out,
			SearchBuyDto search, PageDto<TradeBuyNormDto> page) throws SolrServerException , ParseException{
		//设置分页大小为100
		page.setLimit(100);
		
		if (search.getPageNum()==null){
			page.setStart(0);
		}else {
			page.setStart((search.getPageNum()-1)*page.getLimit());
		}
		
			page = solrService.pageBuysBySearch(search, page,"1");
		
		out.put("search", search);
		out.put("page", page);
		
		//编辑SEO
		String pageNum="";
		if (search.getPageNum()!=null){
			if (search.getPageNum() > 1) {
				pageNum = "-第"+search.getPageNum()+"页";
			}
		}
		
		String[] titleParam = {pageNum};
		SeoUtil.getInstance().buildSeo("tradeNewb", titleParam, null, null, out);
		return null;
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取交易类别列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-07-23　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView zw(HttpServletRequest request, Map<String, Object> out,
			SubnetCategory sub,PageDto<SubnetCategory> page,Integer pageNum) throws SolrServerException , ParseException{
		
		//设置分页大小为
		page.setLimit(120);
		
		if (pageNum==null){
			page.setStart(0);
		}else {
			page.setStart((pageNum-1)*page.getLimit());
		}
		
		
			page = solrService.pageSubCategory(sub, page);
		
		
		out.put("page", page);
		out.put("search", sub);
		
		//编辑SEO
		String num="";
		if (pageNum!=null){
			if (pageNum > 1) {
				num = "-第"+pageNum+"页";
			}
		}
		
		String[] titleParam = {num};
		String[] desc = null;
		String seoName = "tradeZw";
		String categoryName = "所有类别";
		if (sub.getParentId()!=null) {
			SubnetCategory s = subnetCategoryService.querySubCateById(sub.getParentId());
			titleParam = new String[]{s.getName(),num};
			desc = new String[]{s.getName()};
			seoName = "tradeZwCategory";
			categoryName = s.getName();
		}
		out.put("categoryName", categoryName);
		SeoUtil.getInstance().buildSeo(seoName, titleParam, null, desc, out);
		return null;
	}
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新标签列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-07-23　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView newbq(HttpServletRequest request, Map<String, Object> out,TradeCategory cat, 
			PageDto<TradeCategory> page,Integer pageNum) throws SolrServerException , ParseException{
		
		//设置分页大小为
		page.setLimit(120);
		
		if (pageNum==null){
			page.setStart(0);
		}else {
			page.setStart((pageNum-1)*page.getLimit());
		}
		cat.setLeaf((short)1);
		
			page = solrService.pageCategory(cat, page);
		
		
		out.put("page", page);
		out.put("search", cat);
		
		//编辑SEO
		String num="";
		if (pageNum!=null){
			if (pageNum > 1) {
				num = "-第"+pageNum+"页";
			}
		}
		
		String[] titleParam = {num};
		String[] desc = null;
		String seoName = "tradeZw";
		String categoryName = "所有类别";
		if (cat.getCode()!=null) {
			TradeCategory tc = tradeCategoryService.getCategoryByCode(cat.getCode());
			titleParam = new String[]{tc.getName(),num};
			desc = new String[]{tc.getName()};
			seoName = "tradeZwCategory";
			categoryName = tc.getName();
		}
		out.put("categoryName", categoryName);
		SeoUtil.getInstance().buildSeo(seoName, titleParam, null, desc, out);
		
		return null;
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取交易类别列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-07-23　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 *			2012-09-20		马元生				1.0.1		移至api fragment.TradeController
	 * @throws SolrServerException 
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView zwCategory(HttpServletRequest request, Map<String, Object> out,
			SubnetCategory sub,PageDto<SubnetCategory> page) throws SolrServerException , ParseException{
		if(sub.getParentId()==null){
			sub.setParentId(0);
		}
		page.setLimit(Integer.MAX_VALUE);
		Map<String, Object> map = new HashMap<String, Object>();
		//若未指定parentId则加载所有1级类别下的2级类别
		//得到1级类别列表parents
		List<SubnetCategory> parents = new ArrayList<SubnetCategory>();
		
			parents.addAll(solrService.pageSubCategory(sub, page).getRecords());
		
		
		page.getRecords().clear();
		
		if(parents.size()>0){
			//加载2级类别
			PageDto<SubnetCategory> seconds = new PageDto<SubnetCategory>();
			for(SubnetCategory subDto : parents){
				//指定当前要牵索的类别parentId
				sub.setParentId(subDto.getId());
				//取出当前1级类别下的2级类别列表
				
					seconds = solrService.pageSubCategory(sub, seconds);
								
				//追加进2级类别总集合
				if(seconds.getRecords().size()>0){
					page.getRecords().addAll(seconds.getRecords());	
				}
			}
		}
		
		map.put("list", page.getRecords());
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取交易类别列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-07-23　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 *			2012-09-20		马元生				1.0.1		移至api fragment.TradeController
	 * @throws SolrServerException 
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView getCategory(HttpServletRequest request, Map<String, Object> out,String code,Integer deep) throws SolrServerException , ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<TradeCategory> list = tradeCategoryService.queryCategoryByParent(code, 
				deep==null?0:deep, 0);
		
		map.put("cglist", list);
		return printJson(map, out);
	}
    
    private String buildIndusCode(SubnetCategory cate,Map<String, Object> out){
    	String web=null;
    	if(cate==null){
    		out.put("indusCode", "10001000");
			out.put("compIndusCode", "10001000");
			web="污水处理网";
    		return web;
    	}
    	if(cate.getParentId()==3){
			out.put("indusCode", "10001000");
			out.put("compIndusCode", "10001000");
			web="污水处理网";
		}
		if(cate.getParentId()==5){
			out.put("indusCode", "10001001");
			out.put("compIndusCode", "10001001");
			web="原水处理网";
		}
		if(cate.getParentId()==6){
			out.put("indusCode", "10001002");
			out.put("compIndusCode", "10001002");
			web="空气净化网";
		}
		if(cate.getParentId()==7){
			out.put("indusCode", "10001003");
			out.put("compIndusCode", "10001003");
			web="材料药剂网";
		}
		if(cate.getParentId()==8){
			out.put("indusCode", "100010041000");
			out.put("compIndusCode", "10001004");
			web="泵网";
		}
		if(cate.getParentId()==9){
			out.put("indusCode", "100010041002");
			out.put("compIndusCode", "10001004");
			web="风机网";
		}
		if(cate.getParentId()==10){
			out.put("indusCode", "10001005");
			out.put("compIndusCode", "10001005");
			web="环卫清洁网";
		}
		if(cate.getParentId()==11){
			out.put("indusCode", "10001006");
			out.put("compIndusCode", "10001006");
			web="仪器仪表网";
		}
		if(cate.getParentId()==12){
			out.put("indusCode", "100010041001");
			out.put("compIndusCode", "10001004");
			web="阀网";
		}
		if(cate.getParentId()==13){
			out.put("indusCode", "10001007");
			out.put("compIndusCode", "10001007");
			web="综合设备网";
		}
		return web;
    }
}