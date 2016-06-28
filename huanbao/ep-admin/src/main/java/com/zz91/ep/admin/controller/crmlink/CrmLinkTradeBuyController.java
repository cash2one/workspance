package com.zz91.ep.admin.controller.crmlink;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.comp.CompAccountService;
import com.zz91.ep.admin.service.trade.MessageService;
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.admin.service.trade.TradeBuyService;
import com.zz91.ep.admin.service.trade.TradeCategoryService;
import com.zz91.ep.admin.service.trade.TradePropertyValueService;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;
import com.zz91.ep.dto.trade.TradePropertyValueDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
@Controller
public class CrmLinkTradeBuyController extends BaseController{
	
	@Resource
	private TradeBuyService tradeBuyService;
	@Resource 
	private TradeCategoryService tradeCategoryService;
	@Resource
	private TradePropertyValueService tradePropertyValueService;
	@Resource
	private PhotoService photoService;
	@Resource
	private CompAccountService compAccountService;
	@Resource
	private TradeSupplyService tradeSupplyService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView propertyValue(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		out.put("categoryCode", tradeBuyService.queryCategoryCodeById(id));
		return null;
	}
	
	@RequestMapping
	public ModelAndView edit(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		if (id!=null){
			out.put("cid", tradeBuyService.queryCidById(id));
			out.put("categoryCode", tradeBuyService.queryCategoryCodeById(id));
		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView child(HttpServletRequest request, Map<String, Object> out, String parentCode) 
			throws IOException{
		List<ExtTreeDto> categoryNode = tradeCategoryService.queryTradeBuyCategoryNode(parentCode);
		return printJson(categoryNode, out);
	}
	
	@RequestMapping
	public ModelAndView queryTradeBuy(HttpServletRequest request,String categoryCode,
			String title, Integer checkStatus,Integer cid,Integer delStatus, 
			PageDto<TradeBuyDto> page,Map<String, Object> out, String gmtPublishStart, String gmtPublishEnd,String queryType,String compName , String memberCode) throws IOException, ParseException{
		if(StringUtils.isNotEmpty(gmtPublishStart)&&StringUtils.isEmpty(gmtPublishEnd)) {
			gmtPublishEnd = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		if(StringUtils.isEmpty(gmtPublishStart)&&StringUtils.isNotEmpty(gmtPublishEnd)) {
			gmtPublishStart = "1900-01-01 00:00:00";
		}
		
		page = tradeBuyService.pageBuyByCategoryCodeAndTitleAndCheckStatus(categoryCode,cid, page, title, checkStatus,delStatus,gmtPublishStart, gmtPublishEnd,queryType,compName,memberCode,null,null,null,null);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView updateCheckStatus(HttpServletRequest request, Map<String, Object> out,
			Integer id, Integer checkStatus){
		
		Integer i=tradeBuyService.updateStatusOfTradeBuy(id, checkStatus);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
//		LogUtil.getInstance().log(1000, getCachedUser(request).getAccount(), i, null, "{'pauseStatus':"+pauseStatus+"}");
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView refreshBuy(HttpServletRequest request, Map<String, Object> out,
			Integer id){
		
		Integer i=tradeBuyService.refreshBuy(id);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView queryOneBuy(HttpServletRequest request, Map<String, Object> out,
			Integer id){
		List<TradeBuyDto> list=new ArrayList<TradeBuyDto>();
		TradeBuy tradeBuy=tradeBuyService.queryOneBuy(id);
		TradeBuyDto dto=new TradeBuyDto();
		if(tradeBuy!=null) {
			dto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, tradeBuy.getCategoryCode()));
			dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, tradeBuy.getAreaCode()));
			dto.setProvinceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, tradeBuy.getProvinceCode()));
		}
		tradeBuy.setCheckAdmin(getCachedUser(request).getAccount());
		dto.setTradeBuy(tradeBuy);
		list.add(dto);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView updateTradeBuy(Map<String, Object> out,HttpServletRequest request,TradeBuy buy,
			String gmtPublishStr, String gmtRefreshStr, String gmtConfirmStr, String gmtReceiveStr){
		ExtResult result = new ExtResult();
		if(StringUtils.isNotEmpty(gmtPublishStr)&&StringUtils.isNotEmpty(gmtRefreshStr)
				&&StringUtils.isNotEmpty(gmtConfirmStr)&&StringUtils.isNotEmpty(gmtReceiveStr)){
			try {
				buy.setGmtPublish((DateUtil.getDate(gmtPublishStr, "yyyy-MM-dd HH:mm:ss")));
				buy.setGmtRefresh((DateUtil.getDate(gmtRefreshStr, "yyyy-MM-dd HH:mm:ss")));
				buy.setGmtConfirm((DateUtil.getDate(gmtConfirmStr, "yyyy-MM-dd HH:mm:ss")));
				buy.setGmtReceive((DateUtil.getDate(gmtReceiveStr, "yyyy-MM-dd HH:mm:ss")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String[] subCodeArr = buy.getAreaCode().split(",");
		if(subCodeArr.length>2) {
			String subCode = subCodeArr[2];
			Integer length= subCode.length();
			if (length==12){
				buy.setProvinceCode(subCode);
				buy.setAreaCode(subCode);
			}
			if (length>12){
				buy.setProvinceCode(subCode.substring(0, 12));
				buy.setAreaCode(subCode);
			}
		}
 		Integer i=tradeBuyService.uupdateTradeBuy(buy);
		
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView unPassCheckStatus(Map<String, Object> out,HttpServletRequest request,String id,String checkRefuse,String checkStatus){
		ExtResult result = new ExtResult();
		String admin=getCachedUser(request).getAccount();
		Integer intId = Integer.parseInt(id.replace(",", ""));
		Integer intCheckStatus = Integer.parseInt(checkStatus.replace(",", ""));
		String[] arrCheckRefuse = checkRefuse.split(",");
		checkRefuse = arrCheckRefuse[1];
		Integer i=tradeBuyService.updateUnPassCheckStatus(intId,intCheckStatus, admin, checkRefuse);
		if (i!=null && i.intValue()>0){
			//发送审核不通过邮件
			
			CompAccount compAccount = compAccountService.getCompAccountByCid(tradeBuyService.queryCidById(intId));
			TradeBuy tradeBuy = tradeBuyService.queryOneBuy(intId);
			String gmtPublishStr = DateUtil.toString(tradeBuy.getGmtPublish(), "yyyy-MM-dd HH:mm:ss");
      	  Map<String, Object> map = new HashMap<String, Object>();
    		  map.put("compAccount", compAccount);
    		  map.put("tradeBuy", tradeBuy);
    		  map.put("gmtPublishStr", gmtPublishStr);
      	  MailUtil.getInstance().sendMail(MailArga.TITLE_CHECK_FAILURE, compAccount.getEmail(),  MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
    				MailArga.TEMPLATE_CHECK_FAILURE, map, MailUtil.PRIORITY_HEIGHT);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 查询供应信息所对应的专业属性值
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryTradePropertyValue(Map<String, Object> out,HttpServletRequest request,Integer id,String categoryCode){
		
		categoryCode=tradeBuyService.queryCategoryCodeById(id);
		List<TradePropertyValueDto> list = tradePropertyValueService.queryPropertyValueByBuyIdAndCategoryCode(id, categoryCode);
		PageDto<TradePropertyValueDto> page=new PageDto<TradePropertyValueDto>();
		page.setRecords(list);
		return printJson(page, out);
	}
	
	/**
	 * 标记删除状态
	 * @param request
	 * @param out
	 * @param id
	 * @param delStatus
	 * @return
	 */
	@RequestMapping
	public ModelAndView updateDelStatus(HttpServletRequest request, Map<String, Object> out,Integer id, Integer delStatus){
		ExtResult result=new ExtResult();
		Integer i=tradeBuyService.updateDelStatus(id, delStatus);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createPropertyValue(Map<String, Object> out,HttpServletRequest request,TradePropertyValue property){
		ExtResult result = new ExtResult();
		Integer i=tradePropertyValueService.createTradePropertyValue(property);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updatePropertyValue(Map<String, Object> out,HttpServletRequest request,TradePropertyValue property){
		ExtResult result = new ExtResult();
		Integer i=tradePropertyValueService.updateTradePropertyValue(property);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView listPhoto(Map<String, Object> out,HttpServletRequest request,String targetType,Integer targetId){
		// 除了返回所有图片外,还要返回它对应的相册信息
		PageDto<Photo> page = new PageDto<Photo>();
		List<Photo> list=photoService.queryPhotoByTargetType(targetType, targetId);
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView buyPic(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView deleteTradePic(Map<String, Object> out,HttpServletRequest request,Integer id,String path){
		ExtResult result = new ExtResult();
		Integer i=photoService.deletePhotoById(id);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView buyDetails(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		return null;
	}
	
	//用户CRM外链供求信息
	@RequestMapping
	public ModelAndView trade(Map<String, Object> out,
			HttpServletRequest request, Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView supply(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView queryCompSupply(Map<String, Object> out,
			HttpServletRequest request, PageDto<TradeSupplyDto> page,
			Integer cid, String title, Integer checkStatus,Short type) {
		page = tradeSupplyService.pageSupplyByAdmin(cid, title, checkStatus,type,
				page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView buy(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView queryCompBuy(Map<String, Object> out,
			HttpServletRequest request, PageDto<TradeBuyDto> page, Integer cid,
			String title, Integer checkStatus) {
		page = tradeBuyService.pageBuyByAdmin(cid, title, checkStatus, page);
		return printJson(page, out);
	}
	
	//CRM外链留言
	
	@Resource
	private MessageService messageService;

	@RequestMapping
	public ModelAndView sendMessage(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView receiveMessage(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView crmMessage(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryMessage(Map<String, Object> out,HttpServletRequest request,Integer targetId,
			Integer cid,Integer targetCid,String targetType,PageDto<MessageDto> page){
		if (targetType.contains(",")){
			String[] str=targetType.split(",");
			targetType=str[1];
		}
		if (targetType==null || targetType==""){
			targetType="0";
		}
		page=messageService.pageAllMessage(cid,targetType, targetId, targetCid, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneMessage(Map<String, Object> out,HttpServletRequest request,Integer id){
		Message message=messageService.queryShortMessageById(id);
		return printJson(message, out);
	}
}
