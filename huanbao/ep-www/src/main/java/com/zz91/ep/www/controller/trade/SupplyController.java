/*
 * 文件名称：SupplyController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.trade;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.ep.www.controller.BaseController;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：交易中心的供应信息处理操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class SupplyController extends BaseController {
    
    private static final String SUPPLY_TOP_CATEGORY = "1000";
    
    @Resource
    private TradeCategoryService tradeCategoryService;
    
    @Resource
    private TradeSupplyService tradeSupplyService;
    
    @Resource
    private MessageService messageService;
    
	/**
	 * 函数名称：index
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
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request, 
           SearchSupplyDto search, PageDto<TradeSupplyNormDto> page) throws UnsupportedEncodingException {
        
    	if (StringUtils.isEmpty(search.getKeywords())) {
        	if(StringUtils.isEmpty(search.getCategory()) || search.getCategory().length() < 4 || !(search.getCategory().substring(0, 4).equals(SUPPLY_TOP_CATEGORY))) {
        		return new ModelAndView("redirect:/trade/index.htm");
        	}
        } 
    	else {
        	if(StringUtils.isEmpty(search.getCategory()) || search.getCategory().length() < 4 || !(search.getCategory().substring(0, 4).equals(SUPPLY_TOP_CATEGORY))) {
        		search.setCategory(SUPPLY_TOP_CATEGORY);
        	}
        }
    	
        String categoryName = CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, search.getCategory());
        if (StringUtils.isEmpty(search.getKeywords())&&StringUtils.isNotEmpty(search.getCategory())) {
        	String str = tradeCategoryService.queryNameByCode(search.getCategory());
        	if(StringUtils.isNotEmpty(str)){
        		search.setKeywords(str);
        	}else{
        		search.setKeywords("");
        	}
        }
        
        String key = search.getKeywords();
		if (!StringUtils.isContainCNChar(key)) {
			search.setKeywords(StringUtils.decryptUrlParameter(key));
		}
        
    	search.setEncodeKeywords(URLEncoder.encode(search.getKeywords(), "utf-8"));
        
        // 根据相应过滤条件查询相关供应信息(搜索引擎)
    	page = tradeSupplyService.pageBySearchEngine(search, page);
//        page=tradeSupplyService.searchListSupply(search, page,"1");
		
        
        if (page==null||page.getTotals() == null || page.getTotals() == 0) {
//        	String encodeKeywords = search.getEncodeKeywords();
//        	if (StringUtils.isEmpty(encodeKeywords)) {
//        		encodeKeywords = URLEncoder.encode(categoryName, "utf-8");
//			}
        	if(StringUtils.isNotEmpty(search.getKeywords())){
        		out.put("keywords", search.getKeywords());
        	}else{
        		out.put("keywords", categoryName);
        	}
        	return new ModelAndView("/trade/supply/searchNothing");
        	//return new ModelAndView("redirect:/trade/supply/searchNothing.htm?keywords="+encodeKeywords);
        }
        
        out.put("page", page);
        
        out.put("categoryName", categoryName);
        out.put("search", search);
        
      //获取标王信息
        String bwKey = categoryName;
        if(StringUtils.isNotEmpty(search.getKeywords())){
        	bwKey = search.getKeywords();
        }
    	List<TradeKeyword> bwList= tradeSupplyService.queryBwListByKeyword(bwKey);
    	out.put("bwList", bwList);
    	
//    	String bwKey =null;
//    	if (StringUtils.isNotEmpty(search.getKeywords())){
//    		bwKey=search.getKeywords();
////    		bwKey=StringUtils.decryptUrlParameter(bwKey);  //(用于本地测试)
//    	}
//        if (StringUtils.isEmpty(search.getKeywords()) && StringUtils.isNotEmpty(categoryName)){
//        	bwKey=categoryName;
//        }
//        if (StringUtils.isNotEmpty(bwKey)){
//        	List<TradeKeyword> bwList= tradeSupplyService.queryBwListByKeyword(bwKey);
//        	out.put("bwList", bwList);
//        }
        
        out.put("bwKey", bwKey);
        
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
        	pageSize = "第"+pageInteger+"页";
		}
        
    	String[] titleParam = {value,pageSize};
    	String[] keywordsParam = {value};
    	String[] descriptionParam = {value,pageSize};
    	SeoUtil.getInstance().buildSeo("tradeSupply", titleParam, keywordsParam, descriptionParam, out);
    	return null;
    }
    
	/**
	 * 函数名称：searchNothing
	 * 功能描述：搜索无结果页面
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
	 * 异　　常：@throws UnsupportedEncodingException 
	 * 		   @throws SolrServerException
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView searchNothing(Map<String, Object> out,HttpServletRequest request, String keywords) throws UnsupportedEncodingException {
//        String key = StringUtils.decryptUrlParameter(keywords);
    	if(!StringUtils.isContainCNChar(keywords)){
    		keywords = StringUtils.decryptUrlParameter(keywords);
    	}
    	if(StringUtils.isNotEmpty(keywords)){
    		keywords = Jsoup.clean(keywords, Whitelist.none());
    	}
    	out.put("keywords", keywords);
    	String[] titleParam = {keywords};
    	String[] keywordsParam = {keywords};
    	String[] descriptionParam = {keywords};
    	SeoUtil.getInstance().buildSeo("tradeSupply", titleParam, keywordsParam, descriptionParam, out);
    	return null;
    }

	/**
	 * 函数名称：searchNothing
	 * 功能描述：发送消息
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
     * 		  @param search 页面搜索条件 （添加条件时，修改urlrewrite.xml的url规则）
     * 		  @param feature 页面专业属性（格式：id:value,id:value）
     * 		  @param page
	 * 异　　常：@throws UnsupportedEncodingException 
	 * 		   @throws SolrServerException
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView sendInquiry(Map<String, Object> out, HttpServletRequest request, Message message) {
        ExtResult result = new ExtResult();
        do{
            EpAuthUser sessionUser=EpAuthUtils.getInstance().getEpAuthUser(request, null);
            if (sessionUser == null) {
                break;
            }
            message.setCid(sessionUser.getCid());
            message.setUid(sessionUser.getUid());
            Integer i = messageService.sendMessageByUser(message, (int)message.getTargetType());
            if (i!=null && i.intValue()>0) {
            	result.setSuccess(true);
            }
        }while(false);
        return printJson(result, out);
    }
    
	/**
	 * 函数名称：batchInquiry
	 * 功能描述：批量询价
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
	 * 异　　常：
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView batchInquiry(Map<String, Object> out, HttpServletRequest request, Integer ids[]) {
    	do {
 			if (ids == null || ids.length > 5) {
				break;
			}
			List<TradeSupplyDto> inquiryInfo = tradeSupplyService.querySupplysByIds(ids,null);
			out.put("list", inquiryInfo);
    	} while (false);
        return null;
    }

	/**
	 * 函数名称：infoContrast
	 * 功能描述：信息对比
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
	 * 异　　常：
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView infoContrast(Map<String, Object> out, HttpServletRequest request, Integer[] ids) {
        do {
            if (ids == null || ids.length > 5) {
                break;
            }
            List<TradeSupplyDto> contrastInfo = tradeSupplyService.querySupplysByIds(ids,1);
            out.put("list", contrastInfo);
            out.put("idsArr", ids);
        }while(false);
        return null;
    }

}