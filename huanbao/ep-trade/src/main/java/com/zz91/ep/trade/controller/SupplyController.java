/*
 * 文件名称：SupplyController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.trade.controller;

import java.io.UnsupportedEncodingException;
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

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradePropertyService;
import com.zz91.ep.service.trade.TradeSupplyService;
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
    private TradeSupplyService tradeSupplyService;
    
    @Resource
    private MessageService messageService;
    
    @Resource
    private CompProfileService compProfileService;
    
    @Resource
    private TradePropertyService tradePropertyService;
    
    @Resource
    private TradeCategoryService tradeCategoryService;
    
	@Resource
	private PhotoService photoService;
	
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
	 * 　　　　　2012/09/25	 黄怀清			  1.0.1			修改为二级域名产品中心的:及时供应
	 */
    @RequestMapping
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request, 
    		SearchSupplyDto search, PageDto<TradeSupplyNormDto> page) throws UnsupportedEncodingException {

    	if(StringUtils.isEmpty(search.getCategory())) {
    		search.setCategory(SUPPLY_TOP_CATEGORY);
    	}else{
    		out.put("cn", search.getCategory());
    	}
    	page.setLimit(80);
    	//最大显示500页
    	if(page.getStart()>=page.getLimit()*500){
    		page.setStart(page.getLimit()*499);
    	}
    	
        String categoryName = CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, search.getCategory());
        
        if(StringUtils.isNotEmpty(categoryName)){
        	search.setKeywords(categoryName);
        }
        // 根据相应过滤条件查询相关供应信息(搜索引擎)
//        try {
//        	page=tradeSupplyService.searchListSupply(search, page,"1");
        	page = tradeSupplyService.pageBySearchEngine(search, page);
        	
//		} catch (Exception e) {
//			return new ModelAndView("redirect:/uncaughtException.htm");
//		}
        
        out.put("page", page);
        out.put("search", search);
        if (categoryName.equals("供应类别")) {
			categoryName="环保设备";
		}
        out.put("categoryName", categoryName);
        
       
        //编辑SEO
        String pageSize = "";
        Integer pageInteger = page.getStart()/page.getLimit()+1;
        if (pageInteger > 1) {
        	pageSize = "-第"+pageInteger+"页";
		}
        
    	String[] titleParam = {categoryName,pageSize};
    	String[] keysParam = {categoryName};
    	String[] desParam = {categoryName};
    	SeoUtil.getInstance().buildSeo("supply", titleParam, keysParam, desParam, out);
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
    	if(StringUtils.isNotEmpty(keywords)){
    		keywords = Jsoup.clean(keywords, Whitelist.none());
    	}
    	out.put("keywords", keywords);
    	String[] titleParam = {keywords};
    	String[] keywordsParam = {keywords};
    	String[] descriptionParam = {keywords};
    	SeoUtil.getInstance().buildSeo("supply", titleParam, keywordsParam, descriptionParam, out);
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
    
    
    /**
	 * 函数名称：detail
	 * 功能描述：访问产品最终页时，查询相关数据。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out, HttpServletRequest request, Integer id) throws UnsupportedEncodingException {
		
		do {
			//获取产品信息
            if (id == null) {
            	break;
            }
            TradeSupplyDto tradeSupply = tradeSupplyService.queryDetailsById(id);
            if (tradeSupply == null) {
            	break;
            }
            CompProfile comp = compProfileService.queryCompProfileById(tradeSupply.getSupply().getCid());
            if(comp==null){
            	tradeSupplyService.updateGmtModefiled(id);
            	break;
            }
            out.put("tradeSupply", tradeSupply);
            
			out.put("picList", photoService. queryPhotoByTargetType("supply", id));
            
            out.put("categoryPath", tradeCategoryService.buildCategoryPath(tradeSupply.getSupply().getCategoryCode()));
//            TradeCategory oneCategory=tradeCategoryService.getCategoryByCode(tradeSupply.getSupply().getCategoryCode().substring(0,tradeSupply.getSupply().getCategoryCode().length()-8));
//            out.put("oneCategory", oneCategory);
//            TradeCategory secondCategory=tradeCategoryService.getCategoryByCode(tradeSupply.getSupply().getCategoryCode().substring(0,tradeSupply.getSupply().getCategoryCode().length()-4));
//            out.put("secondCategory",secondCategory);
//            TradeCategory thca=tradeCategoryService.getCategoryByCode(tradeSupply.getSupply().getCategoryCode());
//            out.put("thca",thca);
            //查询相关类别
            out.put("broCode", tradeCategoryService.queryBroCategoryByCode(tradeSupply.getSupply().getCategoryCode().substring(0,tradeSupply.getSupply().getCategoryCode().length()-4), 15));
            
            //获取专业属性
            List<TradeProperty> propertys= tradePropertyService.queryPropertyByCategoryCode(tradeSupply.getSupply().getCategoryCode());
            out.put("tradeProperty", propertys);
            out.put("propertyValueMap", tradeSupply.getPropertyValue());
            
			// 公司信息和会员信息
			CompProfileDto compProfile = compProfileService.queryMemberInfoByCid(tradeSupply.getSupply().getCid());
			
			//公司信息
			CompProfile cp = compProfileService.queryCompProfileById(tradeSupply.getSupply().getCid());
			
			if (compProfile == null) {
				break;
			}
			out.put("cid", tradeSupply.getSupply().getCid());
			out.put("uid", tradeSupply.getSupply().getUid());
			out.put("compProfile", compProfile);
			out.put("cp", cp);
			
			out.put("rlist", tradeSupplyService.queryRandomSupply(null, (int)(Math.random()*10000)));
			
			
			// SEO
			String [] title = {tradeSupply.getSupply().getTitle(),tradeSupply.getCategoryName()};
            String [] keyword = {tradeSupply.getCategoryName()};
            String [] descriptionParam = {tradeSupply.getSupply().getTitle()};
            SeoUtil.getInstance().buildSeo("detail", title, keyword, descriptionParam, out);
			return null;
		} while (false);
		
		return new ModelAndView("resourceNotFound");
		
		
	}

}