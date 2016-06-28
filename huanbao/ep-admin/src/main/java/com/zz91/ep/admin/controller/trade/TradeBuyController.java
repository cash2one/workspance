package com.zz91.ep.admin.controller.trade;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.common.HideInfoService;
import com.zz91.ep.admin.service.comp.CompAccountService;
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.admin.service.trade.TradeBuyService;
import com.zz91.ep.admin.service.trade.TradeCategoryService;
import com.zz91.ep.admin.service.trade.TradePropertyValueService;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.domain.common.HideInfo;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;
import com.zz91.ep.dto.trade.TradePropertyValueDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;

@Controller
public class TradeBuyController extends BaseController {

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
	@Resource
	private HideInfoService hideInfoService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		return null;
	}

	@RequestMapping
	public ModelAndView propertyValue(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put("id", id);
		out.put("categoryCode", tradeBuyService.queryCategoryCodeById(id));
		return null;
	}

	@RequestMapping
	public ModelAndView edit(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put("id", id);
		if (id != null) {
			out.put("cid", tradeBuyService.queryCidById(id));
			out.put("categoryCode", tradeBuyService.queryCategoryCodeById(id));
		}
		return null;
	}

	@RequestMapping
	public ModelAndView child(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {
		List<ExtTreeDto> categoryNode = tradeCategoryService
				.queryTradeBuyCategoryNode(parentCode);
		return printJson(categoryNode, out);
	}

	@RequestMapping
	public ModelAndView queryTradeBuy(HttpServletRequest request,
			String categoryCode, String title, Integer checkStatus,Integer cid,
			Integer delStatus, Integer infoComeFrom, PageDto<TradeBuyDto> page,
			Map<String, Object> out, String from, String to,String queryType,String compName,String memberCode, Short recommendType, Integer regComeFrom,String checkAdmin)
			throws IOException, ParseException {
	    if (checkStatus == null && cid == null) {
            checkStatus = 0 ;
        }
	    if (checkStatus != null && checkStatus == 3) {
	        checkStatus = null;
	    }
        if (StringUtils.isEmpty(queryType)) {
            queryType = "0";
        }
        if (StringUtils.isEmpty(from)) {
            from = "1900-01-01 00:00:00";
        }
        if (StringUtils.isEmpty(to)) {
            to = DateUtil
                    .toString(new Date(), "yyyy-MM-dd HH:mm:ss");
        }

		page = tradeBuyService.pageBuyByCategoryCodeAndTitleAndCheckStatus(
				categoryCode,cid, page, title, checkStatus, delStatus,
				from, to,queryType,compName,memberCode,recommendType, infoComeFrom,
				regComeFrom,checkAdmin);

		/*for (TradeBuyDto obj : page.getRecords()) {
			HideInfo hideInfo = hideInfoService.queryHideInfoByIdAndType(obj
					.getTradeBuy().getId(), "2");
			if (hideInfo != null) {
				if (StringUtils.isEmpty(title)) {
					title = obj.getTradeBuy().getTitle();
				}
				obj.getTradeBuy().setTitle("(已隐藏)"+title);
			}
		}*/

		return printJson(page, out);
	}

	@RequestMapping
    public ModelAndView listOfCompany(HttpServletRequest request,
            Map<String, Object> out, Integer id) {
        out.put("id", id);
        return null;
    }
	@RequestMapping
	public ModelAndView updateCheckStatus(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer checkStatus, String unpassReson) {

	    if (checkStatus==null) {
            checkStatus = 0; // 默认的审核状态
        }

        ExtResult result = new ExtResult();
        
        // 验证该人员是否有审核权限
        /*if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
            result.setData("没有权限");
            result.setSuccess(false);
            return printJson(result, out);
        }*/
        
        SessionUser sessionUser = getCachedUser(request);
        
		Integer i = tradeBuyService.updateStatusOfTradeBuy(id, checkStatus);
		if (StringUtils.isNotEmpty(unpassReson)){
		    Integer j = tradeBuyService.updateUnPassCheckStatus(id, checkStatus, sessionUser.getAccount(), unpassReson);
	        if (j == null && j.intValue() > 0 && i != null && i.intValue() > 0) {
	            result.setSuccess(true);
	        }
		}
		
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		// LogUtil.getInstance().log(1000, getCachedUser(request).getAccount(),
		// i, null, "{'pauseStatus':"+pauseStatus+"}");
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView refreshBuy(HttpServletRequest request,
			Map<String, Object> out, Integer id) {

		Integer i = tradeBuyService.refreshBuy(id);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryOneBuy(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		List<TradeBuyDto> list = new ArrayList<TradeBuyDto>();
		TradeBuy tradeBuy = tradeBuyService.queryOneBuy(id);
		TradeBuyDto dto = new TradeBuyDto();
		if (tradeBuy != null) {
			dto.setCategoryName(CodeCachedUtil
					.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, tradeBuy
							.getCategoryCode()));
			dto.setAreaName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, tradeBuy.getAreaCode()));
			dto
					.setProvinceName(CodeCachedUtil.getValue(
							CodeCachedUtil.CACHE_TYPE_AREA, tradeBuy
									.getProvinceCode()));
			if(StringUtils.isNotEmpty(tradeBuy.getDetails())){
			 String string=tradeBuy.getDetails().replaceAll("\r\n", "<br/>").replaceAll(" ", "&nbsp;");
			 tradeBuy.setDetails(string);
			}
		}
		tradeBuy.setCheckAdmin(getCachedUser(request).getAccount());
		dto.setTradeBuy(tradeBuy);
		list.add(dto);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView updateTradeBuy(Map<String, Object> out,
			HttpServletRequest request, TradeBuy buy, String gmtPublishStr,
			String gmtRefreshStr, String gmtConfirmStr, String gmtReceiveStr,
			String gmtExpiredStr,Short isStatus) {
		ExtResult result = new ExtResult();
		if (StringUtils.isNotEmpty(gmtPublishStr)
				&& StringUtils.isNotEmpty(gmtRefreshStr)
				&& StringUtils.isNotEmpty(gmtConfirmStr)
				&& StringUtils.isNotEmpty(gmtReceiveStr)) {
			try {
				buy.setGmtPublish((DateUtil.getDate(gmtPublishStr,
						"yyyy-MM-dd HH:mm:ss")));
				buy.setGmtRefresh((DateUtil.getDate(gmtRefreshStr,
						"yyyy-MM-dd HH:mm:ss")));
				buy.setGmtConfirm((DateUtil.getDate(gmtConfirmStr,
						"yyyy-MM-dd HH:mm:ss")));
				buy.setGmtReceive((DateUtil.getDate(gmtReceiveStr,
						"yyyy-MM-dd HH:mm:ss")));
				buy.setGmtExpired((DateUtil.getDate(gmtExpiredStr,
						"yyyy-MM-dd HH:mm:ss")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String[] subCodeArr = buy.getAreaCode().split(",");
		if (subCodeArr.length > 2) {
			String subCode = subCodeArr[2];
			Integer length = subCode.length();
			if (length == 12) {
				buy.setProvinceCode(subCode);
				buy.setAreaCode(subCode);
			}
			if (length > 12) {
				buy.setProvinceCode(subCode.substring(0, 12));
				buy.setAreaCode(subCode);
			}
		}
		if(isStatus!=null){
			 buy.setCheckStatus(isStatus);
		}
		//前台显示不需要标签
		String stringDetails=buy.getDetails().replaceAll("<p>","").replaceAll("</p>", "").replaceAll("\n\t", "").replaceAll("<br />", "\r\n");
		buy.setDetails(stringDetails);
		//用于同步求购详细信息
		String string = Jsoup.clean(buy.getDetails(), Whitelist
	 			.none());
	    buy.setDetailsQuery(string.replace(" ", ""));
		Integer i = tradeBuyService.uupdateTradeBuy(buy);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
			result.setData(buy.getDetailsQuery());
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView unPassCheckStatus(Map<String, Object> out,
			HttpServletRequest request, Integer id, String checkRefuse,
			Integer checkStatus) {
		ExtResult result = new ExtResult();
		String admin = getCachedUser(request).getAccount();
		//Integer intId = Integer.parseInt(id.replace(",", ""));
		//Integer intCheckStatus = Integer.parseInt(checkStatus.replace(",", ""));
		//String[] arrCheckRefuse = checkRefuse.split(",");
		//checkRefuse = arrCheckRefuse[1];
		
		Integer i = tradeBuyService.updateUnPassCheckStatus(id,
				checkStatus, admin, checkRefuse);
		
		if (i != null && i.intValue() > 0) {
			// 发送审核不通过邮件

			CompAccount compAccount = compAccountService
					.getCompAccountByCid(tradeBuyService.queryCidById(id));
			TradeBuy tradeBuy = tradeBuyService.queryOneBuy(id);
			String gmtPublishStr = DateUtil.toString(tradeBuy.getGmtPublish(),
					"yyyy-MM-dd HH:mm:ss");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compAccount", compAccount);
			map.put("tradeBuy", tradeBuy);
			map.put("gmtPublishStr", gmtPublishStr);
			MailUtil.getInstance().sendMail(MailArga.TITLE_CHECK_FAILURE,
					compAccount.getEmail(),
					MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
					MailArga.TEMPLATE_CHECK_FAILURE, map,
					MailUtil.PRIORITY_HEIGHT);
			result.setSuccess(true);
		}
		
		return printJson(result, out);
	}

	/**
	 * 查询供应信息所对应的专业属性值
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryTradePropertyValue(Map<String, Object> out,
			HttpServletRequest request, Integer id, String categoryCode) {

		categoryCode = tradeBuyService.queryCategoryCodeById(id);
		List<TradePropertyValueDto> list = tradePropertyValueService
				.queryPropertyValueByBuyIdAndCategoryCode(id, categoryCode);
		PageDto<TradePropertyValueDto> page = new PageDto<TradePropertyValueDto>();
		page.setRecords(list);
		return printJson(page, out);
	}

	/**
	 * 标记删除状态
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param delStatus
	 * @return
	 */
	@RequestMapping
	public ModelAndView updateDelStatus(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer delStatus) {
		ExtResult result = new ExtResult();
		Integer i = tradeBuyService.updateDelStatus(id, delStatus);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView createPropertyValue(Map<String, Object> out,
			HttpServletRequest request, TradePropertyValue property) {
		ExtResult result = new ExtResult();
		Integer i = tradePropertyValueService
				.createTradePropertyValue(property);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updatePropertyValue(Map<String, Object> out,
			HttpServletRequest request, TradePropertyValue property) {
		ExtResult result = new ExtResult();
		Integer i = tradePropertyValueService
				.updateTradePropertyValue(property);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView listPhoto(Map<String, Object> out,
			HttpServletRequest request, String targetType, Integer targetId) {
		// 除了返回所有图片外,还要返回它对应的相册信息
		PageDto<Photo> page = new PageDto<Photo>();
		List<Photo> list = photoService.queryPhotoByTargetType(targetType,
				targetId);
		page.setRecords(list);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView buyPic(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView deleteTradePic(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		ExtResult result = new ExtResult();
		Integer i = photoService.deletePhotoStatusById(id);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

    @RequestMapping
    public ModelAndView cancelDeleteTradePic(Map<String, Object> out,
            HttpServletRequest request, Integer id) {
        ExtResult result = new ExtResult();
        Integer i = photoService.cancelDeletePhotoStatusById(id);
        if (i != null && i.intValue() > 0) {
            result.setSuccess(true);
        }
        return printJson(result, out);
    }
    
	@RequestMapping
	public ModelAndView buyDetails(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView editBuyCategory(HttpServletRequest request,
			Map<String, Object> out, Integer id, String categoryCode) {
		ExtResult result = new ExtResult();
		if (tradeBuyService.updateBuyCategory(id, categoryCode)) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView querySimplySupply(HttpServletRequest request,
			Map<String, Object> out, String keywords, String compName,
			Integer loginCount) {
		List<TradeSupplyDto> list = tradeSupplyService.querySimpleByKeyword(
				keywords, compName, loginCount);
		return printJson(list, out);
	}

	/**
	 * 隐藏求购信息
	 * 
	 * @param request
	 * @param id
	 * @param tagertType
	 * @return
	 */
	@RequestMapping
	public ModelAndView insertHideInfo(HttpServletRequest request,
			Map<String, Object> out, Integer id, String targetType) {
		HideInfo hideInfo = new HideInfo();
		hideInfo.setTargetId(id);
		hideInfo.setTargetType(targetType);
		Integer i = hideInfoService.insert(hideInfo);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 显示求购信息
	 * 
	 * @param request
	 * @param id
	 * @param tagertType
	 * @return
	 */
	@RequestMapping
	public ModelAndView deleteHideInfo(HttpServletRequest request,
			Map<String, Object> out, Integer id, String targetType) {

		Integer i = hideInfoService.delete(id, targetType);

		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 显示审核不通过的提示
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryCheckRefuse(HttpServletRequest request,
			Map<String, Object> out){
		  
		List<TradeSupplyDto> tradeDtoList=tradeSupplyService.queryCheckRefuse();
		
		return printJson(tradeDtoList, out);
	}

}
