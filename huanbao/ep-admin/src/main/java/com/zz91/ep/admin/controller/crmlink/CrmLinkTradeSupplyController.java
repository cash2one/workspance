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
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.admin.service.trade.TradeCategoryService;
import com.zz91.ep.admin.service.trade.TradePropertyValueService;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradePropertyValueDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
@Controller
public class CrmLinkTradeSupplyController extends BaseController{
	
	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource 
	private TradeCategoryService tradeCategoryService;
	@Resource
	private TradePropertyValueService tradePropertyValueService;
	@Resource
	private PhotoService photoService;
	@Resource
	private CompAccountService compAccountService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView propertyValue(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		out.put("categoryCode", tradeSupplyService.queryCategoryCodeById(id));
		return null;
	}
	
	@RequestMapping
	public ModelAndView edit(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		if (id!=null){
			out.put("cid", tradeSupplyService.queryCidById(id));
			out.put("categoryCode", tradeSupplyService.queryCategoryCodeById(id));
		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView child(HttpServletRequest request, Map<String, Object> out, String parentCode) 
			throws IOException{
		List<ExtTreeDto> categoryNode = tradeCategoryService.queryTradeSupplyCategoryNode(parentCode);
		return printJson(categoryNode, out);
	}
	
	@RequestMapping
	public ModelAndView queryTradeSupply(HttpServletRequest request,String categoryCode,Integer cid,
			String title, Integer checkStatus,Integer delStatus,String codeBlock, Integer infoComeFrom,
			PageDto<TradeSupplyDto> page,Map<String, Object> out, String gmtPublishStart, String gmtPublishEnd,String queryType,String memberCode) throws IOException, ParseException{
		if(StringUtils.isNotEmpty(gmtPublishStart)&&StringUtils.isEmpty(gmtPublishEnd)) {
			gmtPublishEnd = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		if(StringUtils.isEmpty(gmtPublishStart)&&StringUtils.isNotEmpty(gmtPublishEnd)) {
			gmtPublishStart = "1900-01-01 00:00:00";
		}
		if (StringUtils.isEmpty(queryType)) {
            queryType = "0";
        }
		page = tradeSupplyService.pageSupplyByCategoryCodeAndTitleAndCheckStatus(categoryCode,cid, page, title, checkStatus,delStatus, codeBlock , infoComeFrom,
				gmtPublishStart, gmtPublishEnd,queryType,memberCode,null,null,null,null);
		return printJson(page, out);
	}
	/*
	@RequestMapping
	public ModelAndView queryDeleteTradeSupply(HttpServletRequest request,String categoryCode,
			String title, Integer checkStatus,Integer delStatus, Integer infoComeFrom,
			PageDto<TradeSupplyDto> page,Map<String, Object> out) throws IOException{
		page = tradeSupplyService.pageSupplyByCategoryCodeAndTitleAndCheckStatus(categoryCode, page, title, checkStatus,delStatus, infoComeFrom);
		return printJson(page, out);
	}*/
	
	@RequestMapping
	public ModelAndView updateCheckStatus(HttpServletRequest request, Map<String, Object> out,
			Integer id, Integer checkStatus){
		
		Integer i=tradeSupplyService.updateStatusOfTradeSupply(id, checkStatus);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
//		LogUtil.getInstance().log(1000, getCachedUser(request).getAccount(), i, null, "{'pauseStatus':"+pauseStatus+"}");
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView refreshSupply(HttpServletRequest request, Map<String, Object> out,
			Integer id) throws ParseException{
		
		Integer i=tradeSupplyService.refreshSupply(id);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView queryOneSupply(HttpServletRequest request, Map<String, Object> out,
			Integer id){
		List<TradeSupplyDto> list=new ArrayList<TradeSupplyDto>();
		TradeSupply tradeSupply=tradeSupplyService.queryOneSupplyById(id);
		TradeSupplyDto dto=new TradeSupplyDto();
		if(tradeSupply!=null) {
			dto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, tradeSupply.getCategoryCode()));
			dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, tradeSupply.getAreaCode()));
			dto.setProvinceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, tradeSupply.getProvinceCode()));
		}
		tradeSupply.setCheckAdmin(getCachedUser(request).getAccount());
		dto.setSupply(tradeSupply);
		list.add(dto);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView updateTradeSupply(Map<String, Object> out,HttpServletRequest request,TradeSupply supply,
			String gmtPublishStr, String gmtRefreshStr){
		ExtResult result = new ExtResult();
		if(StringUtils.isNotEmpty(gmtPublishStr)&&StringUtils.isNotEmpty(gmtRefreshStr)){
			try {
				supply.setGmtPublish((DateUtil.getDate(gmtPublishStr, "yyyy-MM-dd HH:mm:ss")));
				supply.setGmtRefresh((DateUtil.getDate(gmtRefreshStr, "yyyy-MM-dd HH:mm:ss")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer length= supply.getAreaCode().length();
		String subCode="";
		if (length==12){
			supply.setProvinceCode(supply.getAreaCode());
			supply.setAreaCode(supply.getAreaCode());
		}
		if (length>12){
			subCode=supply.getAreaCode().substring(0, 12);
			supply.setProvinceCode(subCode);
			supply.setAreaCode(supply.getAreaCode());
		}
 		Integer i=tradeSupplyService.updateTradeSupply(supply);
		
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
		Integer i=tradeSupplyService.updateUnPassCheckStatus(intId,intCheckStatus, admin, checkRefuse);
		if (i!=null && i.intValue()>0){
			//发送审核不通过邮件
			
			CompAccount compAccount = compAccountService.getCompAccountByCid(tradeSupplyService.queryCidById(intId));
			TradeSupply tradeSupply = tradeSupplyService.queryOneSupplyById(intId);
			String gmtPublishStr = DateUtil.toString(tradeSupply.getGmtPublish(), "yyyy-MM-dd HH:mm:ss");
      	  Map<String, Object> map = new HashMap<String, Object>();
    		  map.put("compAccount", compAccount);
    		  map.put("tradeSupply", tradeSupply);
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
		
		categoryCode=tradeSupplyService.queryCategoryCodeById(id);
		List<TradePropertyValueDto> list = tradePropertyValueService.queryPropertyValueBySupplyIdAndCategoryCode(id, categoryCode);
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
		Integer i=tradeSupplyService.updateDelStatus(id, delStatus);
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
	public ModelAndView supplyPic(Map<String, Object> out,HttpServletRequest request,Integer id){
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
	public ModelAndView supplyDetails(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		return null;
	}
}
