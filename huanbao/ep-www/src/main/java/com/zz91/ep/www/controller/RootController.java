/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

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
	 private CompAccountService compAccountService;
	 
	 @Resource
	 private TradeSupplyService tradeSupplyService;
	 
	 @Resource
	 private MessageService messageService;
	 
	 /**
	 * 函数名称：index
	 * 功能描述：访问首页时，初始化相关数据。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView oldIndex(HttpServletRequest request, Map<String, Object> out) throws Exception{
		//SEO初始化
		SeoUtil.getInstance().buildSeo("index", null, null, null, out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out) throws Exception{
		//SEO初始化
		SeoUtil.getInstance().buildSeo("index", null, null, null, out);
		return null;
	}
	
	
	/**
	 * 函数名称：search
	 * 功能描述：跳搜索页面
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView search(Map<String, Object> out, HttpServletRequest request, String searchKeyWords, String searchTypeValue) throws UnsupportedEncodingException {
    	String url = "";
    	do {
    		if (StringUtils.isEmpty(searchKeyWords)) {
    			break;
    		}
    		String encodeKeywords="";
        	if (StringUtils.isNotEmpty(searchKeyWords)) {
				encodeKeywords=URLEncoder.encode(searchKeyWords, "utf-8");
        	}
    		if ("0".equals(searchTypeValue)) {
        		url = AddressTool.getAddress("trade")+"/supply/index.htm?keywords="+encodeKeywords;
        	} else if("1".equals(searchTypeValue)) {
        		url = AddressTool.getAddress("news")+"/search.htm?keywords="+encodeKeywords;
        	} else if("2".equals(searchTypeValue)) {
        		url = AddressTool.getAddress("exhibit")+"/search.htm?keywords="+encodeKeywords;
        	} else if("3".equals(searchTypeValue)) {
        		url = AddressTool.getAddress("company")+"/list.htm?keywords="+encodeKeywords;
        	} else if("4".equals(searchTypeValue)) {
        		url = AddressTool.getAddress("trade")+"/buy/list.htm?keywords="+encodeKeywords;
        	} else if("5".equals(searchTypeValue)){
        		url = AddressTool.getAddress("www")+"/compnews/technicalarticles/tagsList.htm?keywords="+encodeKeywords;
        	}else {
        		url = "index.htm";
        	}
		} while (false);
    	return new ModelAndView("redirect:"+url);
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
	 * 函数名称：login
	 * 功能描述：用户登录页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView login(Map<String, Object> out, HttpServletRequest request, String refurl, String error) {
        out.put("error", error);
        out.put("refurl", refurl);
        SeoUtil.getInstance().buildSeo("login", null, null, null, out);
        return null;
    }
    
	/**
	 * 函数名称：login
	 * 功能描述：验证登录。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView doLogin(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response, String username, String password, String vcode, String refurl)
    		{
    	if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
    		out.put("error", "用户名或密码不能为空！");
            return new ModelAndView("redirect:login.htm");
        }
    	EpAuthUser user =null;
		try {
			user = EpAuthUtils.getInstance().validateUser(response, username, password, null);
			compAccountService.updateLoginInfo(user.getUid(), null,user.getCid());
			EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
			 if(StringUtils.isNotEmpty(refurl)){
		            return new ModelAndView("redirect:"+refurl);
		        }
		} catch (NoSuchAlgorithmException e) {
			this.handleAuthorizeException(refurl, out, new AuthorizeException(AuthorizeException.ERROR_SERVER));
			return new ModelAndView("login");
		} catch (IOException e) {
			this.handleAuthorizeException(refurl, out, new AuthorizeException(AuthorizeException.ERROR_SERVER));
			return new ModelAndView("login");
		} catch (AuthorizeException e) {
			this.handleAuthorizeException(refurl, out, e);
			return new ModelAndView("redirect:login.htm");
		}
        return new ModelAndView("redirect:/myesite/index.htm");
    }
	
	/**
	 * 函数名称：logout
	 * 功能描述：用户退出。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView logout(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response){
        EpAuthUtils.getInstance().logout(request, response, null);
        return new ModelAndView("redirect:login.htm");
    }
   
	/**
	 * 函数名称：daohang
	 * 功能描述：导航页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView daohang(Map<String, Object> out, HttpServletRequest request){
		// SEO
		SeoUtil.getInstance().buildSeo("daohang", null, null, null, out);
    	return null;
    }
    
	/**
	 * 函数名称：daohangList
	 * 功能描述：导航列表页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws UnsupportedEncodingException 
	 */
    @Deprecated
    @RequestMapping
    public ModelAndView daohangList(Map<String, Object> out, HttpServletRequest request, String keywords,String parentCode) throws UnsupportedEncodingException {
//    	String keywordEncode=StringUtils.decryptUrlParameter(keywords);
    	// 相关最新供应信息
//    	List<TradeSupply> list = tradeSupplyService.querySupplysByKewords(keywords, 50);
    	List<TradeSupply> list = new ArrayList<TradeSupply>();
    	out.put("supplyList", list);
    	out.put("keywordEncode", URLEncoder.encode(keywords,"UTF-8"));
		out.put("keywords", keywords);
		out.put("parentCode", parentCode);
    	// SEO
    	String[] titleParam = {keywords};
    	String[] keywordsParam = {keywords};
    	String[] descriptionParam = {keywords};
		SeoUtil.getInstance().buildSeo("daohangList", titleParam, keywordsParam, descriptionParam, out);
    	return null;
    }
    
    @RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
    
    @RequestMapping
    public void testSearchEngine(){
    	PageDto<TradeSupplyNormDto> page = new PageDto<TradeSupplyNormDto>();
    	SearchSupplyDto search = new SearchSupplyDto();
    	search.setKeywords("铁");
    	page = tradeSupplyService.pageBySearchEngine(search, page);
    }
}