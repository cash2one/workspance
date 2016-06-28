package com.zz91.ep.myesite.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.BuyMessageDto;
import com.zz91.ep.dto.trade.MessageDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.service.trade.TradeBuyService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.auth.ep.EpAuthUser;

@Controller
public class BuyController extends BaseController {
	@Resource
	private TradeBuyService tradeBuyService;
	
	@Resource
	private PhotoService photoService;
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource
	private MyEsiteService myEsiteService;
	
	/**
     * 首页
     * @param out
     * @param request
     * @return
     */
//    @RequestMapping
//    public ModelAndView index(Map<String, Object> out, HttpServletRequest request) {
//        return new ModelAndView("redirect:buy.htm");
//    }

	/**
	 * 函数名称：publish_StpOne
	 * 功能描述：初始化求购信息页
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView publish_StpOne(Map<String, Object> out, HttpServletRequest request) {
    	myEsiteService.init(out, getCachedUser(request).getCid());
        return null;
    }
    
    /**
	 * 函数名称：doStpOne
	 * 功能描述：发布求购信息
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    @RequestMapping
    public ModelAndView doStpOne(Map<String, Object> out, HttpServletRequest request, TradeBuy tradeBuy) {

    	// 判断前一张页面是否huanbao网
		String preUrl = request.getHeader("referer");
		if(preUrl.indexOf("huanbao.com")==-1){
			return new ModelAndView("redirect:/supply/index.htm?result=2");
		}

    	EpAuthUser cachedUser = getCachedUser(request);
    	
    	tradeBuy.setUid(cachedUser.getUid());
    	tradeBuy.setCid(cachedUser.getCid());
    	
    	tradeBuy.setTagsSys("");
    	tradeBuy.setBuyType((short)0);
        Integer id = tradeBuyService.createTradeBuy(tradeBuy);
        if (id != null && id > 0) {
			out.put("keywords" , tradeBuy.getTitle());
        	return new ModelAndView("redirect:publish_Success.htm");
        }
        myEsiteService.init(out, getCachedUser(request).getCid());
    	out.put("tradeBuy", tradeBuy);
    	return new ModelAndView("redirect:publish_StpOne.htm");
    }
    
    /**
     * 跳转到发布成功页面
     * @param out
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     * @throws SolrServerException 
     */
    @RequestMapping
    public ModelAndView publish_Success(Map<String, Object> out, HttpServletRequest request, String keywords) throws UnsupportedEncodingException, SolrServerException {
    	
    	out.put("keywordsEncode", URLEncoder.encode(keywords, "utf-8"));
    	out.put("keywords", keywords);
    	PageDto<TradeSupplyNormDto> page = new PageDto<TradeSupplyNormDto>(10);
    	SearchSupplyDto search = new SearchSupplyDto();
    	page = tradeSupplyService.searchSupplyByCategory(search, page, "1");
		if(page.getRecords()==null||page.getRecords().size()==0){
			page  = tradeSupplyService.searchSupplyByCategory(search, page, "3");
		}
		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("newestSupply", page.getRecords());
		myEsiteService.init(out, getCachedUser(request).getCid());
        return null;
    }
    
    /**
     * 求购信息管理
     * @param out
     * @param request
     * @param pauseStatus
     * @param overdueStatus
     * @param checkStatus
     * @param groupId
     * @param page
     * @return
     */
//    @RequestMapping
//    public ModelAndView buy(Map<String, Object> out, HttpServletRequest request, Integer pauseStatus, 
//           Integer overdueStatus, Integer checkStatus, PageDto<TradeBuy> page) {
//        if (pauseStatus != null) {
//            out.put("pauseStatus", pauseStatus);
//        }
//        if (overdueStatus != null) {
//            out.put("overdueStatus", overdueStatus);
//        }
//        if (checkStatus != null) {
//            out.put("checkStatus", checkStatus);
//        }
//        page.setSort("gmt_publish");
//        page.setDir("desc");
//        page = tradeBuyService.pageBuyByConditions(getCompanyId(request), pauseStatus, overdueStatus, checkStatus, page);
//        out.put("page", page);
//        out.put("item", "product");
//        return null;
//    }
    
    /**
     * 删除供应信息
     * @param out
     * @param request
     * @param id
     * @return
     */
    @RequestMapping
    public ModelAndView doDelete(Map<String, Object> out, HttpServletRequest request, Integer id) {
        ExtResult result = new ExtResult();
        do {
            if (id == null) {
                break;
            }
            Integer count = tradeBuyService.deleteBuyById(id, getCompanyId(request));
            if (count != null && count > 0) {
                result.setSuccess(true);
            }
        } while (false);
        myEsiteService.init(out, getCachedUser(request).getCid());
        return printJson(result, out);
    }
    
    /**
     * 求购信息发布/暂不发布按钮操作
     * @param out
     * @param request
     * @param id
     * @param status 
     * @return
     */
    @RequestMapping
    public ModelAndView doPause(Map<String, Object> out, HttpServletRequest request, Integer id, Integer status) {
        ExtResult result = new ExtResult();
        do {
            Integer count = tradeBuyService.updatePauseStatusById(id, getCompanyId(request), status);
            if (count != null && count > 0) {
                result.setSuccess(true);
            }
        } while (false);
        myEsiteService.init(out, getCachedUser(request).getCid());
        return printJson(result, out);
    }
    
    /**
     * 求购信息刷新按钮操作
     * @param out
     * @param request
     * @param id
     * @return
     */
    @RequestMapping
    public ModelAndView doRefresh(Map<String, Object> out, HttpServletRequest request, Integer id) {
        ExtResult result = new ExtResult();
        do {
            Integer count = tradeBuyService.updateRefreshById(id, getCompanyId(request));
            if (count != null && count > 0) {
                result.setSuccess(true);
            }
        } while (false);
        myEsiteService.init(out, getCachedUser(request).getCid());
        return printJson(result, out);
    }
    
    /**
     * 求购信息查看询盘
     * @param out
     * @param request
     * @param id
     * @param replyStatus
     * @param page
     * @return
     */
    @RequestMapping
    public ModelAndView message(Map<String, Object> out, HttpServletRequest request, Integer id, 
           Integer replyStatus, Integer readStatus,PageDto<MessageDto> page) {
        TradeBuy buy = tradeBuyService.queryBuySimpleDetailsById(id);
        out.put("buy", buy);
        out.put("id", id);
        page = messageService.queryMessagesByCompany(getCompanyId(request), MessageService.SENDSTATUS_RECEIVE, id, MessageService.TARGET_BUY, replyStatus,readStatus, page);
        out.put("page", page);
        myEsiteService.init(out, getCachedUser(request).getCid());
        return null;
    }
    
    /**
     * 修改求购信息页面初始化
     * @param out
     * @param request
     * @param id
     * @return
     */
    @RequestMapping
    public ModelAndView update(Map<String, Object> out, HttpServletRequest request, Integer id) {
        do {
            TradeBuy tradeBuy = tradeBuyService.queryUpdateBuyById(id, getCompanyId(request));
            if (tradeBuy == null) {
                break;
            }
            // 查询产品图片
            List<Photo> photos = photoService.queryPhotoByTargetType(PhotoService.TARGET_BUY, id);
            out.put("buy", tradeBuy);
            out.put("photos", photos);
            out.put("item", "product");
            myEsiteService.init(out, getCachedUser(request).getCid());
            return null;
        } while (false);
        return new ModelAndView("redirect:/Buy/Buy.htm");
    }

    /**
     * 修改求购信息基本信息
     * @param out
     * @param request
     * @param id
     * @return
     */
    @RequestMapping
    public ModelAndView doUpdate(Map<String, Object> out, HttpServletRequest request, TradeBuy buy) {
        ExtResult result = new ExtResult();
        do {
            Integer count = tradeBuyService.updateBaseBuyById(buy, buy.getId(), getCompanyId(request));
            if (count != null && count > 0) {
                result.setSuccess(true);
            }
        } while (false);
        myEsiteService.init(out, getCachedUser(request).getCid());
        return new ModelAndView("redirect:index.htm");
    }
    
    /**
     * 首页
     * 求购信息管理
     * @param out
     * @param request
     * @param pauseStatus
     * @param overdueStatus
     * @param checkStatus
     * @param groupId
     * @param page
     * @return
     */
    @RequestMapping
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request, Integer pauseStatus, 
           Integer overdueStatus, Integer checkStatus, PageDto<BuyMessageDto> page) {
        out.put("pauseStatus", pauseStatus);
        out.put("overdueStatus", overdueStatus);
        out.put("checkStatus", checkStatus);

        page = tradeBuyService.pageBuyByConditions(getCompanyId(request), pauseStatus, overdueStatus, checkStatus, page);
        out.put("productsCount", tradeBuyService.countSupplyByCompany(getCompanyId(request)));
        out.put("page", page);
        myEsiteService.init(out, getCachedUser(request).getCid());
        
        return new ModelAndView();
    }
   
}
