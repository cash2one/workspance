/*
 * 文件名称：BuyController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.trade;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchBuyDto;
import com.zz91.ep.dto.trade.TradeBuyDto;
import com.zz91.ep.dto.trade.TradeBuyNormDto;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.trade.TradeBuyService;
import com.zz91.ep.www.controller.BaseController;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：买家中心的供应信息处理操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class BuyController extends BaseController {
    
	private static final String BUY_TOP_CATEGORY = "1001";
	
	@Resource
	private TradeBuyService tradeBuyService;
	
	@Resource
	private CompProfileService compProfileService;
	
	/**
	 * 函数名称：index
	 * 功能描述：求购信息首页初始化
	 * 输入参数：
	 * 		  @param out
     * 		  @param request
	 * 异　　常：
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request) {
        SeoUtil.getInstance().buildSeo("tradeBuy", null, null, null, out);
    	return null;
    }
    
	/**
	 * 求购信息列表页初始化
	 * 
	 * @param out
	 * @param request
	 * @param search
	 *            页面搜索条件 （添加条件时，修改urlrewrite.xml的url规则）
	 * @param feature
	 *            页面专业属性（格式：id:value,id:value）
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws SolrServerException
	 */
	@RequestMapping
	public ModelAndView list(Map<String, Object> out, HttpServletRequest request, SearchBuyDto search, PageDto<TradeBuyNormDto> page) throws UnsupportedEncodingException, SolrServerException {
		
		if (StringUtils.isEmpty(search.getKeywords())) {
			search.setKeywords("");
		} 
		
		String key = search.getKeywords();
		if (!StringUtils.isContainCNChar(key)) {
			search.setKeywords(StringUtils.decryptUrlParameter(key));
		}
		
		search.setEncodeKeywords(URLEncoder.encode(search.getKeywords(), "utf-8"));
		
		
		// 根据相应过滤条件查询相关供应信息(搜索引擎)
		
		
//		page = solrService.pageBuysBySearch(search, page,"1"); // solr 搜索引擎
		
		page = tradeBuyService.pageBySearchEngine(search, page);
		
		if (page.getTotals() == null || page.getTotals() == 0) {
			String encodeKeywords = search.getEncodeKeywords();
			return new ModelAndView("redirect:/trade/buy/searchNothing.htm?keywords="+encodeKeywords);
		}
		out.put("page", page);
		out.put("search", search);
		// 编辑SEO
		String value = search.getKeywords();
		String[] titleParam = { value };
		String[] keywordsParam = { value };
		String[] descriptionParam = { value };
		SeoUtil.getInstance().buildSeo("tradeBuyList", titleParam, keywordsParam,
				descriptionParam, out);
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
//    	String key = StringUtils.decryptUrlParameter(keywords);
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
    	SeoUtil.getInstance().buildSeo("tradeBuyList", titleParam, keywordsParam, descriptionParam, out);
    	return null;
    }

	@RequestMapping
	public ModelAndView detail(Map<String, Object> out, HttpServletRequest request, Integer id,Integer flag)
			throws SolrServerException, ParseException {
		do {
			if (id == null) {
				break;
			}
			// 根据求购信息ID查询该求购信息详细信息
			TradeBuyDto buyDto = tradeBuyService.queryBuyDetailsById(id);
			if (buyDto == null) {
				break;
			}
			out.put("buyDto", buyDto);
			String commCategory = buyDto.getTradeBuy().getCategoryCode();
			if (StringUtils.isEmpty(commCategory)) {
				commCategory = BUY_TOP_CATEGORY;
			}
			String keywords = buyDto.getTradeBuy().getTitle();
			String[] titleParam = { keywords };
			String[] keywordsParam = { keywords };
			String[] descriptionParam = { keywords };
			SeoUtil.getInstance().buildSeo("tradeBuyDetail", titleParam, keywordsParam, descriptionParam, out);
			if (flag!=null && flag==1){
				out.put("show", 1);
			}else {
				out.put("show", 0);
			}
			return null;
		} while (false);
		return new ModelAndView("redirect:" + AddressTool.getAddress("trade") + "/buy/index.htm");
	}
	
	@RequestMapping
	public ModelAndView viewBuyContacts(Map<String, Object> out, HttpServletRequest request, Integer cid) throws SolrServerException {
		ExtResult result = new ExtResult();
		do {
			if (cid == null) {
				break;
			}
			// 会员信息
			String memberCode = EpAuthUtils.getInstance().getEpAuthUser(request, null) == null ? "" : EpAuthUtils.getInstance().getEpAuthUser(request, null).getMemberCode();
			if (memberCode != null && memberCode.equals("10011001")) {
				result.setData(compProfileService.queryContactByCid(cid));
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} while (false);
		return printJson(result, out);
	}
    
}