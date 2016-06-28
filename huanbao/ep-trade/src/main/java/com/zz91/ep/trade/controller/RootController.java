/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.trade.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.dto.trade.TradeSupplySearchDto;
import com.zz91.ep.service.common.ParamService;
import com.zz91.ep.service.common.SysAreaService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.domain.Param;
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
	private static final String SUPPLY_TOP_CATEGORY = "1000";
	
	@Resource
	private SysAreaService sysAreaService;
	@Resource
	private TradeCategoryService tradeCategoryService; 
	@Resource
    private TradeSupplyService tradeSupplyService;
	@Resource
	private ParamService paramService;
	
	final static Logger LOG = Logger.getLogger("com.huanbao.trade.access");
	
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
    	List<Param> friendLink = paramService.queryParamsByType("friend_links_trade");
    	out.put("friendLinkList", friendLink);
        SeoUtil.getInstance().buildSeo("index", null, null, null, out);
        return null;
    }
    
	/**
	 * 函数名称：index
	 * 功能描述：访问首页时，查询相关数据。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/09/23　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView category(Map<String, Object> out, HttpServletRequest request, 
            SearchSupplyDto search, String cn, PageDto<TradeSupplySearchDto> page)throws UnsupportedEncodingException, SolrServerException {
    	
    	String category = ParamUtils.getInstance().getValue("trade_supply_page", cn);
    	if(!StringUtils.isEmpty(category)){
    		search.setCategory(category);
    	}
    		
    	if(StringUtils.isEmpty(search.getCategory()) || search.getCategory().length() < 4 || !(search.getCategory().substring(0, 4).equals(SUPPLY_TOP_CATEGORY))) {
    		return new ModelAndView("redirect:/index.htm");
    	}
    	
        String categoryName = CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, search.getCategory());
        if (StringUtils.isEmpty(search.getKeywords())) {
        	search.setKeywords("");
        }
    	search.setEncodeKeywords(URLEncoder.encode(search.getKeywords(), "utf-8"));
        
        
        out.put("categoryName", categoryName);
        out.put("search", search);
        out.put("cn", cn);
        
        //编辑SEO
        
    	String[] titleParam = {categoryName};
    	String[] keywordsParam = {categoryName};
        SeoUtil.getInstance().buildSeo("category", titleParam, keywordsParam, null, out);
    	return null;
    }

    /**
	 * 函数名称：list
	 * 功能描述：供应信息列表页初始化
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
     * 		  @param search 页面搜索条件 （添加条件时，修改urlrewrite.xml的url规则）
     * 		  @param feature 页面专业属性（格式：id:value,id:value）
     * 		  @param page
	 * 异　　常：@throws UnsupportedEncodingException 
	 * 		   @throws SolrServerException
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/09/24　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView list(Map<String, Object> out, HttpServletRequest request, 
           SearchSupplyDto search, Integer id,PageDto<TradeSupplyNormDto> page) throws UnsupportedEncodingException {
    	
    	if(StringUtils.isEmpty(search.getKeywords())&&StringUtils.isEmpty(search.getCategory())&&id!=null&&id!=0){
    		search.setCategory(tradeCategoryService.queryCodeById(id));
    	}
    	
    	if (StringUtils.isEmpty(search.getKeywords())) {
        	if(StringUtils.isEmpty(search.getCategory()) || search.getCategory().length() < 4 || !(search.getCategory().substring(0, 4).equals(SUPPLY_TOP_CATEGORY))) {
        		return new ModelAndView("redirect:/index.htm");
        	}
        } else {
        	if(StringUtils.isEmpty(search.getCategory()) || search.getCategory().length() < 4 || !(search.getCategory().substring(0, 4).equals(SUPPLY_TOP_CATEGORY))) {
        		search.setCategory(SUPPLY_TOP_CATEGORY);
        	}
        }
    	
        String categoryName = CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, search.getCategory());
        
        if(StringUtils.isNotEmpty(categoryName)){
        	search.setCategoryName(categoryName);
        }
        
        if (StringUtils.isEmpty(search.getKeywords())) {
        	search.setKeywords("");
        }else{
        	search.setKeywords(StringUtils.decryptUrlParameter(search.getKeywords()));
        	search.setEncodeKeywords(URLEncoder.encode(search.getKeywords(), "utf-8"));
        }
    	
//        out.put("bwKey", bwKey);
        
        // 根据相应过滤条件查询相关供应信息(搜索引擎)
//        page = tradeSupplyService.searchListSupply(search, page, "1");
        
        
        //最大显示5000页
    	if(page.getStart()>=page.getLimit()*5000){
    		page.setStart(page.getLimit()*4999);
    	}
    	
        page = tradeSupplyService.pageBySearchEngine(search, page);
        
        if (page==null||page.getTotals() == null || page.getTotals() == 0) {
        	String encodeKeywords = search.getEncodeKeywords();
        	if (StringUtils.isEmpty(encodeKeywords)) {
        		encodeKeywords = URLEncoder.encode(categoryName, "utf-8");
			}
        	return new ModelAndView("redirect:/supply/searchNothing.htm?keywords="+encodeKeywords);
        }
        
        out.put("page", page);
        
      	//标王列表
        String bwKey = categoryName;
        if(StringUtils.isNotEmpty(search.getKeywords())){
        	bwKey = search.getKeywords();
        }
    	List<TradeKeyword> bwList= tradeSupplyService.queryBwListByKeyword(bwKey);
    	out.put("bwList", bwList);
    	
        out.put("id", id);
        out.put("categoryName", categoryName);
        
        out.put("search", search);
        
        // 初始化搜索用的类别信息
        tradeCategoryService.initSupplySearchTbar(search.getKeywords(), search.getCategory(), out);
        
        out.put("categoryPath", tradeCategoryService.buildCategoryPath(search.getCategory()));
        
        //编辑SEO
        String value = search.getKeywords();
        if (StringUtils.isEmpty(value)) {
        	value = categoryName;
		}
        
        String pageSize = "";
        Integer pageInteger = page.getStart()/page.getLimit()+1;
        if (pageInteger > 1) {
        	pageSize = "-第"+pageInteger+"页";
		}
        
    	String[] titleParam = {value,pageSize};
    	String[] keywordsParam = {value};
    	String[] descriptionParam = {value,pageSize};
    	SeoUtil.getInstance().buildSeo("list", titleParam, keywordsParam, descriptionParam, out);
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
	
	@RequestMapping
	public ModelAndView epmap(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("epmap", null, null, null, out);
		return null;
	}
	
	/**************common url***********/
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
	 * 模块描述：获取交易类别列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-07-23　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView getCategory(HttpServletRequest request, Map<String, Object> out,String code,Integer deep) throws SolrServerException , ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<TradeCategory> list = tradeCategoryService.queryCategoryByParent(code, 
				deep==null?0:deep, 0);
		
		map.put("cglist", list);
		return printJson(map, out);
	}
	
}