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
import com.zz91.ep.admin.service.trade.TradeCategoryService;
import com.zz91.ep.admin.service.trade.TradeKeywordService;
import com.zz91.ep.admin.service.trade.TradePropertyService;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.admin.util.SolrUpdateUtils;
import com.zz91.ep.domain.common.HideInfo;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradePropertyValueDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

@Controller
public class TradeSupplyController extends BaseController {

	public static final String[] WHITE_DOC = { ".jpg", ".jpeg", ".gif", ".png" };
	public static final String[] BLOCK_ANY = { ".bat", ".sh", ".exe" };

	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource
	private TradeCategoryService tradeCategoryService;
	@Resource
	private TradePropertyService tradePropertyService;
	@Resource
	private PhotoService photoService;
	@Resource
	private CompAccountService compAccountService;
	@Resource
	private TradeKeywordService tradeKeywordService;
	@Resource
	private HideInfoService hideInfoService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("ws", "污水处理网栏目1");
		map.put("ys", "原水处理网栏目1");
		map.put("kq", "空气净化网栏目1");
		map.put("yj", "材料药剂网栏目1");
		map.put("beng", "泵网栏目1");
		map.put("fa", "阀网栏目1");
		map.put("fj", "风机网栏目1");
		map.put("hw", "环卫设备网栏目1");
		map.put("yqyb", "仪器仪表网栏目1");
		map.put("zh", "综合设备网栏目1");

		map.put("ws1", "污水处理网栏目2");
		map.put("ys1", "原水处理网栏目2");
		map.put("kq1", "空气净化网栏目2");
		map.put("yj1", "材料药剂网栏目2");
		map.put("beng1", "泵网栏目2");
		map.put("fa1", "阀网栏目2");
		map.put("fj1", "风机网栏目2");
		map.put("hw1", "环卫设备网栏目2");
		map.put("yqyb1", "仪器仪表网栏目2");
		map.put("zh1", "综合设备网栏目2");
		out.put("subnetTradeRec", map);
		return null;
	}

	@RequestMapping
	public ModelAndView propertyValue(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put("id", id);
		out.put("categoryCode", tradeSupplyService.queryCategoryCodeById(id));
		return null;
	}

	@RequestMapping
	public ModelAndView edit(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put("id", id);
		if (id != null) {
			out.put("cid", tradeSupplyService.queryCidById(id));
			out.put("categoryCode", tradeSupplyService
					.queryCategoryCodeById(id));
		}
		return null;
	}

	@RequestMapping
	public ModelAndView child(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {
		List<ExtTreeDto> categoryNode = tradeCategoryService
				.queryTradeSupplyCategoryNode(parentCode);
		return printJson(categoryNode, out);
	}

	@RequestMapping
	public ModelAndView queryTradeSupply(HttpServletRequest request,
			String categoryCode, String title, Integer checkStatus,Integer cid,
			Integer delStatus, Integer infoComeFrom, String codeBlock,
			PageDto<TradeSupplyDto> page, Map<String, Object> out,
			String from, String to, Short recommendType,String queryType,String memberCode,
			String compName, String subRecommend,String checkAdmin) throws IOException,
			ParseException {
	    if (checkStatus == null && cid == null) {
	        checkStatus = 0 ;
	    }
	    if (checkStatus != null && checkStatus == 3) {
            checkStatus = null;
        }
	    if (StringUtils.isEmpty(queryType)) {
	        queryType = "0";
	    }
		if (StringUtils.isEmpty(to)) {
		    to = DateUtil
					.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		
		page = tradeSupplyService
				.pageSupplyByCategoryCodeAndTitleAndCheckStatus(categoryCode,cid,
						page, title, checkStatus, delStatus, codeBlock,
						infoComeFrom, from, to,queryType,memberCode,
						recommendType,  compName, subRecommend,checkAdmin);

		

		return printJson(page, out);
	}
	
	@RequestMapping
    public ModelAndView listOfCompany(HttpServletRequest request,
            Map<String, Object> out, Integer id) {
	    Map<String, String> map = new HashMap<String, String>();
        map.put("ws", "污水处理网栏目1");
        map.put("ys", "原水处理网栏目1");
        map.put("kq", "空气净化网栏目1");
        map.put("yj", "材料药剂网栏目1");
        map.put("beng", "泵网栏目1");
        map.put("fa", "阀网栏目1");
        map.put("fj", "风机网栏目1");
        map.put("hw", "环卫设备网栏目1");
        map.put("yqyb", "仪器仪表网栏目1");
        map.put("zh", "综合设备网栏目1");

        map.put("ws1", "污水处理网栏目2");
        map.put("ys1", "原水处理网栏目2");
        map.put("kq1", "空气净化网栏目2");
        map.put("yj1", "材料药剂网栏目2");
        map.put("beng1", "泵网栏目2");
        map.put("fa1", "阀网栏目2");
        map.put("fj1", "风机网栏目2");
        map.put("hw1", "环卫设备网栏目2");
        map.put("yqyb1", "仪器仪表网栏目2");
        map.put("zh1", "综合设备网栏目2");
        out.put("subnetTradeRec", map);
        out.put("id", id);
        return null;
    }

	/*
	 * @RequestMapping public ModelAndView
	 * queryDeleteTradeSupply(HttpServletRequest request,String categoryCode,
	 * String title, Integer checkStatu已删除s,Integer delStatus, Integer
	 * infoComeFrom, PageDto<TradeSupplyDto> page,Map<String, Object> out)
	 * throws IOException{ page =
	 * tradeSupplyService.pageSupplyByCategoryCodeAndTitleAndCheckStatus
	 * (categoryCode, page, title, checkStatus,delStatus, infoComeFrom); return
	 * printJson(page, out); }
	 */

	@RequestMapping
	public ModelAndView updateCheckStatus(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer checkStatus ,String unpassReson) {

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
		Integer i = tradeSupplyService.updateStatusOfTradeSupply(id,
				checkStatus );
		if (StringUtils.isNotEmpty(unpassReson)) {
		    Integer j = tradeSupplyService.updateUnPassCheckStatus(id, checkStatus, sessionUser.getAccount(), unpassReson);
		    if (j != null && j.intValue() > 0 && i != null && i.intValue() > 0) {
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
	public ModelAndView refreshSupply(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws ParseException {

		Integer i = tradeSupplyService.refreshSupply(id);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryOneSupply(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		List<TradeSupplyDto> list = new ArrayList<TradeSupplyDto>();
		TradeSupply tradeSupply = tradeSupplyService.queryOneSupplyById(id);
		tradeSupply.setTitle( Jsoup.clean(tradeSupply.getTitle(), Whitelist.none()));
		tradeSupply.setDetails( Jsoup.clean(tradeSupply.getDetails(), Whitelist.none().addTags("div","p","ul","li","br","th","tr","td","img").addAttributes("td","rowspan").addAttributes("img", "src","width" ,"height")));
		TradeSupplyDto dto = new TradeSupplyDto();
		
		if (tradeSupply != null) {
			dto.setCategoryName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_TRADE, tradeSupply
							.getCategoryCode()));
			dto.setAreaName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, tradeSupply.getAreaCode()));
			dto.setProvinceName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, tradeSupply
							.getProvinceCode()));
		}
		tradeSupply.setCheckAdmin(getCachedUser(request).getAccount());
		dto.setSupply(tradeSupply);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("data", "supply" + id);
		param.put("sort", "time");
		param.put("dir", "desc");
		dto.setLogInfo(queryLogs(param));
		list.add(dto);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView updateTradeSupply(Map<String, Object> out,
			HttpServletRequest request, TradeSupply supply,
			String gmtPublishStr, String gmtRefreshStr, String gmtExpiredStr) {
		ExtResult result = new ExtResult();
		if (StringUtils.isNotEmpty(gmtPublishStr)
				&& StringUtils.isNotEmpty(gmtRefreshStr)) {
			try {
				supply.setGmtPublish((DateUtil.getDate(gmtPublishStr,
						"yyyy-MM-dd HH:mm:ss")));
				supply.setGmtRefresh((DateUtil.getDate(gmtRefreshStr,
						"yyyy-MM-dd HH:mm:ss")));
				supply.setGmtExpired((DateUtil.getDate(gmtExpiredStr,
						"yyyy-MM-dd HH:mm:ss")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer length = supply.getAreaCode().length();
		String subCode = "";
		if (length == 12) {
			supply.setProvinceCode(supply.getAreaCode());
			supply.setAreaCode(supply.getAreaCode());
		}
		if (length > 12) {
			subCode = supply.getAreaCode().substring(0, 12);
			supply.setProvinceCode(subCode);
			supply.setAreaCode(supply.getAreaCode());
		}
	   //用于同步供应详细信息
    	String string = Jsoup.clean(supply.getDetails(), Whitelist
	 			.none().addTags("div","p","ul","li","br","th","tr","td","img").addAttributes("td","rowspan").addAttributes("img", "src","width" ,"height"));
	    supply.setDetailsQuery(string.replace(" ", ""));
			
		Integer i = tradeSupplyService.updateTradeSupply(supply);
		
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
			result.setData(supply.getDetailsQuery());
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView unPassCheckStatus(Map<String, Object> out,
			HttpServletRequest request, Integer  id, String checkRefuse,
			Integer checkStatus) {
		ExtResult result = new ExtResult();
		String admin = getCachedUser(request).getAccount();
		//Integer intId = Integer.parseInt(id.replace(",", ""));
		//Integer intCheckStatus = Integer.parseInt(checkStatus.replace(",", ""));
		//String[] arrCheckRefuse = checkRefuse.split(",");
		//checkRefuse = arrCheckRefuse[1];
		Integer i = tradeSupplyService.updateUnPassCheckStatus(id,
				checkStatus, admin, checkRefuse);
		if (i != null && i.intValue() > 0) {
			// 发送审核不通过邮件

			CompAccount compAccount = compAccountService
					.getCompAccountByCid(tradeSupplyService.queryCidById(id));
			TradeSupply tradeSupply = tradeSupplyService
					.queryOneSupplyById(id);
			String gmtPublishStr = DateUtil.toString(tradeSupply
					.getGmtPublish(), "yyyy-MM-dd HH:mm:ss");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compAccount", compAccount);
			map.put("tradeSupply", tradeSupply);
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
		// 取categorycode和propertyQuery
		TradeSupply supply = tradeSupplyService
				.queryPropertyQueryAndCategoryCodeById(id);
		// 解析propertyQuery得到Map
		Map<Integer, Object> mapsMap = buildPropertyValues(supply
				.getPropertyQuery());
		// 取得property属性
		List<TradeProperty> tradePropertys = tradePropertyService
				.queryPropertyByCategoryCode(categoryCode);
		List<TradePropertyValueDto> list = new ArrayList<TradePropertyValueDto>();
		// buildPropertyDto
		for (TradeProperty tradeProperty : tradePropertys) {
			TradePropertyValueDto dto = new TradePropertyValueDto();
			dto.setPropertyId(tradeProperty.getId());
			dto.setPropertyName(tradeProperty.getName());
			dto.setPropertyValue(String.valueOf(mapsMap.get(tradeProperty
					.getId())));
			dto.setSupplyId(id);
			list.add(dto);
		}
		return printJson(list, out);
	}

	private Map<Integer, Object> buildPropertyValues(String propertyQuery) {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		if (StringUtils.isNotEmpty(propertyQuery)) {
			String[] propertys = propertyQuery.split(";");
			for (int i = 0; i < propertys.length; i++) {
				String property = propertys[i];
				if (StringUtils.isNotEmpty(property)) {
					String[] value = property.split("_");
					if (value.length > 1) {
						map.put(Integer.valueOf(value[0]), value[1]);
					}
				}
			}
		}
		return map;
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
		Integer i = tradeSupplyService.updateDelStatus(id, delStatus);
		if (i != null && i.intValue() > 0) {
			StringBuffer sb = new StringBuffer("删除");
			if (delStatus == 0) {
				sb.replace(0, sb.length(), "取消删除");
			}
			LogUtil.getInstance().mongo(getCachedUser(request).getName(),
					sb.toString(), null, "supply" + id);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView createPropertyValue(Map<String, Object> out,
			HttpServletRequest request, TradePropertyValue property) {
		ExtResult result = new ExtResult();
        
		String query = tradeSupplyService.queryPropertyQueryById(property
				.getSupplyId());
		if (StringUtils.isNotEmpty(query)) {
			String propertyArr[] = query.split(";");
			int count = 0;
			for (String p : propertyArr) {
				if (property.getPropertyId().toString().equals(p.split("_")[0])) {
					query = query.replace(p, property.getPropertyId() + "_"
							+ property.getPropertyValue());
					count++;
				}
			}
			if (count == 0) {
				query = query + property.getPropertyId() + "_"
						+ property.getPropertyValue() + ";";
			}
		} else {
			query = property.getPropertyId() + "_"
					+ property.getPropertyValue() + ";";

		}
		tradeSupplyService.updatePropertyQueryAllById(property.getSupplyId(),
				query);
		result.setSuccess(true);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updatePropertyValue(Map<String, Object> out,
			HttpServletRequest request, TradePropertyValue property) {
		ExtResult result = new ExtResult();

		String query = tradeSupplyService.queryPropertyQueryById(property
				.getSupplyId());
		if (StringUtils.isNotEmpty(query)) {
			String propertyArr[] = query.split(";");
			int count = 0;
			for (String p : propertyArr) {
				if (property.getPropertyId().toString().equals(p.split("_")[0])) {
					query = query.replace(p, property.getPropertyId() + "_"
							+ property.getPropertyValue());
					count++;
				}
			}
			if (count == 0) {
				query = query + property.getPropertyId() + "_"
						+ property.getPropertyValue() + ";";
			}
		} else {
			query = property.getPropertyId() + "_"
					+ property.getPropertyValue() + ";";

		}
		tradeSupplyService.updatePropertyQueryAllById(property.getSupplyId(),
				query);
		result.setSuccess(true);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView listPhoto(Map<String, Object> out,
			HttpServletRequest request, String targetType, Integer targetId) {
		// 除了返回所有图片外,还要返回它对应的相册信息
		List<Photo> list = photoService.queryPhotoByTargetType(targetType,
				targetId);
		return printJson(list, out);
	}
	
	/**
	 * 图片审核通过
	 * @param out
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView passPic(Map<String, Object>out,String idArrayStr) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(StringUtils.isEmpty(idArrayStr)){
				break;
			}
			Integer i = photoService.batchUpdatePicStatus(idArrayStr, "", PhotoService.CHECK_STATUS_PASS);
			if(i==null||i<1){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 退回选中的图片
	 * @param out
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView refusePic(Map<String, Object>out,String idArrayStr,String unpassReason) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(StringUtils.isEmpty(idArrayStr)){
				break;
			}
			Integer i = photoService.batchUpdatePicStatus(idArrayStr, unpassReason, PhotoService.CHECK_STATUS_NO_PASS);
			if(i==null||i<1){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	
	/**
	 * 退回所有的图片 
	 * @param out
	 * @param targetId
	 * @param unpassReason
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView refuseAllPic(Map<String, Object>out,Integer targetId,String unpassReason) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(targetId==null){
				break;
			}
			Integer i = photoService.queryPhotoByTargetType("supply",targetId, unpassReason, PhotoService.CHECK_STATUS_NO_PASS);
			if(i==null||i<1){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView supplyPic(Map<String, Object> out,
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
	public ModelAndView setFrontPic(Map<String, Object> out,
			HttpServletRequest request, String path, Integer sId) {
		ExtResult result = new ExtResult();
		Integer i = tradeSupplyService.updatePhotoCoverById(sId, path);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView supplyDetails(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView refreshAllSearch(HttpServletRequest request,
			Map<String, Object> out) {
		ExtResult result = new ExtResult();

		// 验证是否在更新如果在更新，传null验证是否正在更新中
		try {
			if (!SolrUpdateUtils.runUpdateSolr(SolrUpdateUtils.TRADE_SUPPLY,
					null)) {
				if (!SolrUpdateUtils.runUpdateSolr(
						SolrUpdateUtils.TRADE_SUPPLY,
						SolrUpdateUtils.FULL_IMPORT)) {
					result.setSuccess(true);
				} else {
					result.setData("已经在更新!");
				}
			}
		} catch (IOException e) {
			result.setData(e);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView refreshPartSearch(HttpServletRequest request,
			Map<String, Object> out) {
		ExtResult result = new ExtResult();

		// 验证是否在更新如果在更新，传null验证是否正在更新中
		try {
			if (!SolrUpdateUtils.runUpdateSolr(SolrUpdateUtils.TRADE_SUPPLY,
					null)) {
				if (!SolrUpdateUtils.runUpdateSolr(
						SolrUpdateUtils.TRADE_SUPPLY,
						SolrUpdateUtils.DELTA_IMPORT)) {
					result.setSuccess(true);
				} else {
					result.setData("已经在更新!");
				}
			}
		} catch (IOException e) {
			result.setData(e);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView editSupplyCategory(HttpServletRequest request,
			Map<String, Object> out, Integer id, String categoryCode) {
		ExtResult result = new ExtResult();
		if (tradeSupplyService.updateSupplyCategory(id, categoryCode)) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 上传图片
	 * 
	 * @param out
	 * @param request
	 * @param targetId
	 * @return
	 */
	@RequestMapping
	public ModelAndView uploadFile(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		String filename = String.valueOf(System.currentTimeMillis());
		String path = buildPath("huanbao");
		String uploadedFile = null;
		try {
			uploadedFile = MvcUpload.localUpload(request, UPLOAD_ROOT + path,
					filename, DEFAULT_FIELD, WHITE_DOC, BLOCK_ANY, 20 * 1024);// 最大上传20M
		} catch (Exception e) {
			out.put("data", MvcUpload.getErrorMessage(e.getMessage()));
		}
		if (StringUtils.isNotEmpty(uploadedFile)) {
			Photo photo = new Photo();
			photo.setUid(0);
			photo.setCid(0);
			photo.setPhotoAlbumId(0);
			photo.setPath(path + "/" + uploadedFile);
			photo.setTargetId(id);
			photo.setTargetType("supply");
			Integer i = photoService.createPhoto(photo);
			if (i != null && i.intValue() > 0) {
				out.put("success", true);
				out.put("data", path + "/" + uploadedFile);
			}
		}
		return printJson(out, out);
	}

	@RequestMapping
	public ModelAndView recBw(Map<String, Object> out,
			HttpServletRequest request, Integer sid, Short type) {
		out.put("sid", sid);
		out.put("type", type);
		return null;
	}

	@RequestMapping
	public ModelAndView queryBwDetails(Map<String, Object> out,
			HttpServletRequest request, Integer sid) {
		TradeKeyword key = tradeSupplyService.buildTradeKeyword(sid);
		return printJson(key, out);
	}

	@RequestMapping
	public ModelAndView createRecBw(Map<String, Object> out,
			HttpServletRequest request, TradeKeyword keyword, String startStr,
			String endStr, String regStr) throws ParseException {
		ExtResult result = new ExtResult();
		keyword.setStart(DateUtil.getDate(startStr, "yyyy-MM-dd HH:mm:ss"));
		keyword.setEnd(DateUtil.getDate(endStr, "yyyy-MM-dd HH:mm:ss"));
		keyword.setGmtRegister(DateUtil.getDate(regStr, "yyyy-MM-dd HH:mm:ss"));
		Integer i = tradeKeywordService.createTradeKeyword(keyword);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView bw(Map<String, Object> out, HttpServletRequest request) {
		return null;
	}

	@RequestMapping
	public ModelAndView bwDetails(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView queryBw(Map<String, Object> out,
			HttpServletRequest request, String keyword, Short status,
			String startStr, String endStr, PageDto<TradeKeyword> page)
			throws ParseException {
		// if (StringUtils.isNotEmpty(startStr)){
		// startStr= DateUtil.toString(DateUtil.getDate(startStr, "yyyy-MM-dd"),
		// "yyyy-MM-dd");
		// }
		// if (StringUtils.isNotEmpty(end)){
		// key.setEnd(DateUtil.getDate(end, "yyyy-MM-dd HH:mm:ss"));
		// }
		page = tradeKeywordService.pageTradeKeyword(keyword, page, status,
				startStr, endStr);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView updateStatus(Map<String, Object> out,
			HttpServletRequest request, Integer id, Short status) {
		Integer i = tradeKeywordService.updateStatusById(id, status);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryDetails(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		TradeKeyword tradekeyword = tradeKeywordService
				.queryTradekeywordById(id);
		return printJson(tradekeyword, out);
	}

	@RequestMapping
	public ModelAndView updateKeyWord(Map<String, Object> out,
			HttpServletRequest request, TradeKeyword keyword, String startStr,
			String endStr, String regStr) throws ParseException {
		ExtResult result = new ExtResult();
		keyword.setStart(DateUtil.getDate(startStr, "yyyy-MM-dd HH:mm:ss"));
		keyword.setEnd(DateUtil.getDate(endStr, "yyyy-MM-dd HH:mm:ss"));
		keyword.setGmtRegister(DateUtil.getDate(regStr, "yyyy-MM-dd HH:mm:ss"));
		Integer i = tradeKeywordService.updateTradeKeywordById(keyword);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 隐藏供应信息
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
	 * 显示供应信息
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
