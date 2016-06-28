package com.zz91.ep.myesite.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.domain.trade.TradeGroup;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.domain.trade.TradePropertyValue;
import com.zz91.ep.domain.trade.TradeRecommend;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;
import com.zz91.ep.dto.trade.SupplyMessageDto;
import com.zz91.ep.dto.trade.TradeGroupDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradeGroupService;
import com.zz91.ep.service.trade.TradePropertyService;
import com.zz91.ep.service.trade.TradePropertyValueService;
import com.zz91.ep.service.trade.TradeRecommendService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

@Controller
public class SupplyController extends BaseController {

	@Resource
	private TradeCategoryService tradeCategoryService;
	@Resource
	private TradePropertyService tradePropertyService;
	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource
	private PhotoService photoService;
	@Resource
	private TradeGroupService tradeGroupService;
	@Resource
	private TradeRecommendService tradeRecommendService;
	@Resource
	private MessageService messageService;
	@Resource
	private TradePropertyValueService tradePropertyValueService;
	@Resource
	private MyEsiteService myEsiteService;

	
	public static String COOKDAMIN="huanbao.com";
	/**
	 * 首页
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	// @RequestMapping
	// public ModelAndView index(Map<String, Object> out, HttpServletRequest
	// request) {
	// return new ModelAndView("redirect:supply.htm");
	// }

	/**
	 * 初始化发布供应信息页（第一步）
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView publish_StpOne(Map<String, Object> out,
			HttpServletRequest request, String category, String choiceType) {
		do {
			EpAuthUser cachedUser = getCachedUser(request);
			if (cachedUser == null) {
				break;
			}
			Integer companyId = cachedUser.getCid();

			// 判断是否普会，判断供求发布条数是否超过限制
			if (CompProfileService.DEFAULT_MEMERCODE.equals(cachedUser.getMemberCode())) {
				if (!tradeSupplyService.validateToPub(companyId)) {
					out.put("result", 1);
					break;
				}
			}

			Map<String, String> publishedCategory = new LinkedHashMap<String, String>();
			List<TradeSupply> list = tradeSupplyService
					.queryCategoryByCid(companyId);
			for (TradeSupply supply : list) {
				publishedCategory.put(supply.getCategoryCode(),
						tradeCategoryService.buildCategory(supply
								.getCategoryCode()));
			}
			out.put("publishedCategory", publishedCategory);

			out.put("categoryPath", tradeCategoryService
					.buildCategory(category));
			myEsiteService.init(out, getCachedUser(request).getCid());
			out.put("category", category);
			out.put("choiceType", choiceType);

			return new ModelAndView();
		} while (false);
		return new ModelAndView("redirect:/supply/index.htm");
	}

	/**
	 * 初始化发布供应信息页（第二步）
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView publish_StpTwo(Map<String, Object> out,
			HttpServletRequest request,HttpServletResponse response, String category, String choiceType,String result) {
		do {
			if (StringUtils.isEmpty(category)) {
				return new ModelAndView("redirect:/supply/publish_StpOne.htm");
			}
			category = Jsoup.clean(category, Whitelist.none());
			//防止机器注册 每次获取saveuuid
			String saveuuid=HttpUtils.getInstance().getCookie(request, "publish", COOKDAMIN);
			if(StringUtils.isEmpty(saveuuid)){
				saveuuid=UUID.randomUUID().toString();
				HttpUtils.getInstance().setCookie(response, "publish", saveuuid, COOKDAMIN, null);
			}	
			out.put("saveuuid", saveuuid);
			// 取得专业属性
			List<TradeProperty> propertys = tradePropertyService
					.queryPropertyByCategoryCode(category);
			Map<Integer, Object> properyValueMap = new HashMap<Integer, Object>();
			for (TradeProperty property : propertys) {
				if (StringUtils.isNotEmpty(property.getVtypeValue())) {
					properyValueMap.put(property.getId(), JSONArray
							.fromObject(property.getVtypeValue()));
				}
			}
			out.put("vtypeMap", properyValueMap);
			out.put("categoryPath", tradeCategoryService.buildCategory(category));
			out.put("propertys", propertys);
			out.put("category", category);
			myEsiteService.init(out, getCachedUser(request).getCid());
			out.put("choiceType", choiceType);
			out.put("result", result);
		} while (false);
		return new ModelAndView();
	}

	/**
	 * 供应信息管理
	 * 
	 * @param out
	 * @param request
	 * @param pauseStatus
	 * @param overdueStatus
	 * @param checkStatus
	 * @param groupId
	 * @param page
	 * @return
	 */
	// @RequestMapping
	// public ModelAndView supply(Map<String, Object> out, HttpServletRequest
	// request, Integer pauseStatus,
	// Integer overdueStatus, Integer checkStatus, Integer recommend, Integer
	// groupId, PageDto<TradeSupply> page) {
	// // 查找该用户供应信息分组类别
	// List<TradeGroup> groups =
	// tradeGroupService.queryTradeGroupById(getCompanyId(request));
	// out.put("groups", groups);
	// if (pauseStatus != null) {
	// out.put("pauseStatus", pauseStatus);
	// }
	// if (overdueStatus != null) {
	// out.put("overdueStatus", overdueStatus);
	// }
	// if (checkStatus != null) {
	// out.put("checkStatus", checkStatus);
	// }
	// if (groupId != null) {
	// out.put("groupId", groupId);
	// }
	// if (recommend != null) {
	// out.put("recommend", recommend);
	// }
	// page.setSort("ts.gmt_publish");
	// page.setDir("desc");
	// page = tradeSupplyService.pageSupplyByConditions(getCompanyId(request),
	// pauseStatus, overdueStatus, checkStatus,recommend, groupId, page);
	// out.put("page", page);
	// out.put("item", "product");
	// return null;
	// }

	/**
	 * 供应信息类别管理
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	// @RequestMapping
	// public ModelAndView tradeGroup(Map<String, Object> out,
	// HttpServletRequest request) {
	// List<TradeGroupDto> list =
	// tradeGroupService.queryTradeGroupDtoById(getCompanyId(request));
	// out.put("list", list);
	// out.put("item", "product");
	// return null;
	// }

    /**
     * 发布供应信息（第二步）
     * @param out
     * @param request
     * @return
     */
    @RequestMapping
	public ModelAndView doStpTwo(Map<String, Object> out,
			HttpServletRequest request,HttpServletResponse response, TradeSupply tradeSupply, String photos,String verifyCode,String saveuuid) {
		do {

			// CompProfile compProfile =
			// compProfileService.getCompProfileById(getCompanyId(request));
			// if("".equals(compProfile.getName())){
			// return new ModelAndView("redirect:/company/updateCompany.htm");
			// }
			// else{

			EpAuthUser cachedUser = getCachedUser(request);
			
			tradeSupply.setUid(cachedUser.getUid());
			tradeSupply.setCid(cachedUser.getCid());
			tradeSupply.setTagsSys("");
			tradeSupply.setTitle(tradeSupply.getTitle().replaceAll("\\s", ""));
			//每次提交后saveuuid 置为空
			String savecookie= HttpUtils.getInstance().getCookie(request, "publish", COOKDAMIN);
			
			//防止机器发布
			if(StringUtils.isEmpty(saveuuid)||StringUtils.isEmpty(savecookie)||!saveuuid.equals(savecookie)){
				return new ModelAndView("redirect:/supply/index.htm");
			}
			// 验证验证码，防止机器注册
			String vcode = String.valueOf(EpAuthUtils.getInstance().getValue(request, response,"vcodes"));
			SsoUtils.getInstance().remove(request, "vcodes");
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode) || !verifyCode.equalsIgnoreCase(vcode)) {
				return new ModelAndView("redirect:/supply/index.htm?result=3");
			}
			
			// 判断标题是否重复
			if(!tradeSupplyService.validateTitleRepeat(cachedUser.getCid(), tradeSupply.getTitle())){
				return new ModelAndView("redirect:/supply/index.htm?result=2");
			}
			if(tradeSupply!=null&&tradeSupply.getTotalNum()!=null){
				if(tradeSupply.getTotalNum()<1){
					return new ModelAndView("redirect:/supply/index.htm?result=4");
				}
			}
			// if(StringUtils.isNotEmpty(tradeSupply.getPhotoCover())&&tradeSupply.getPhotoCover().contains(",")){
			// tradeSupply.setPhotoCover(tradeSupply.getPhotoCover().split(",")[0]);
			// }
			// 保存供应信息

			if (StringUtils.isNotEmpty(photos)) {
				String[] photo = photos.split(",");
				String photoId = photo[0];
				if (StringUtils.isNumber(photoId)) {
					Photo photoPath = photoService.queryPhotoById(Integer
							.parseInt(photoId));
					tradeSupply.setPhotoCover(photoPath.getPath());

				}
			}

			// 判断是否普会，判断供求发布条数是否超过限制
			if (CompProfileService.DEFAULT_MEMERCODE.equals(cachedUser
					.getMemberCode())) {
				if (!tradeSupplyService.validateToPub(cachedUser.getCid())) {
					break;
				}
				//
				tradeSupply.setCheckStatus((short) TradeSupplyService.STATUS_CHECK_UN);
			}else{
				tradeSupply.setCheckStatus((short) TradeSupplyService.STATUS_CHECK_YES);
			}
			Integer id = tradeSupplyService.createTradeSupply(tradeSupply);

			// 关联上传的图片 （service）
			if (id != null && id > 1) {
				if (StringUtils.isNotEmpty(photos)) {
					photoService.updateTargetId(StringUtils.StringToIntegerArray(photos), id);
				}

				// 高会发布供应信息插入相册图片，它的审核状态有问题
				if (cachedUser != null && "10011001".equals(cachedUser.getMemberCode())) {
					// 高会从相册中选择图片为产品图片时，图片将置为已审核
					for (Integer pid : StringUtils.StringToIntegerArray(photos)) {
						photoService.updateCheckStatus(pid, "1");
					}
				}
				
				out.put("pid", id);
				return new ModelAndView("redirect:publish_Success.htm");
			}
			myEsiteService.init(out, getCachedUser(request).getCid());
			out.put("category", tradeSupply.getCategoryCode());
			return new ModelAndView("supply/publish_StpTwo");
		} while (false);
		return new ModelAndView("redirect:/supply/index.htm?result=1");
		// }  
		    
	}
    
    /**
     * 供应信息管理
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
//    public ModelAndView supply(Map<String, Object> out, HttpServletRequest request, Integer pauseStatus, 
//           Integer overdueStatus, Integer checkStatus, Integer recommend, Integer groupId, PageDto<TradeSupply> page) {
//        // 查找该用户供应信息分组类别
//        List<TradeGroup> groups = tradeGroupService.queryTradeGroupById(getCompanyId(request));
//        out.put("groups", groups);
//        if (pauseStatus != null) {
//            out.put("pauseStatus", pauseStatus);
//        }
//        if (overdueStatus != null) {
//            out.put("overdueStatus", overdueStatus);
//        }
//        if (checkStatus != null) {
//            out.put("checkStatus", checkStatus);
//        }
//        if (groupId != null) {
//            out.put("groupId", groupId);
//        }
//        if (recommend != null) {
//            out.put("recommend", recommend);
//        }
//        page.setSort("ts.gmt_publish");
//        page.setDir("desc");
//        page = tradeSupplyService.pageSupplyByConditions(getCompanyId(request), pauseStatus, overdueStatus, checkStatus,recommend, groupId, page);
//        out.put("page", page);
//        out.put("item", "product");
//        return null;
//    }
    

    /**
     * 供应信息类别管理
     * @param out
     * @param request
     * @return
     */
//    @RequestMapping
//    public ModelAndView tradeGroup(Map<String, Object> out, HttpServletRequest request) {
//        List<TradeGroupDto> list = tradeGroupService.queryTradeGroupDtoById(getCompanyId(request));
//        out.put("list", list);
//        out.put("item", "product");
//        return null;
//    }
    
    
    /**
     * 添加供应信息类别
     * @param out
     * @param request
     * @param id
     * @return
     */
    @RequestMapping
    public ModelAndView addGroup(Map<String, Object> out, HttpServletRequest request,TradeGroup tradeGroup) {
        ExtResult result = new ExtResult();
        do {
        	if(tradeGroup==null){
        		break;
        	}
            tradeGroup.setCid(getCompanyId(request));
            tradeGroup.setUid(getUid(request));
            if (tradeGroup.getSort() == null) {
            	tradeGroup.setSort((short)0);
			}
            Integer count = tradeGroupService.createTradeGroup(tradeGroup);
            result.setData(count);
            result.setSuccess(true);
        } while (false);
        myEsiteService.init(out, getCachedUser(request).getCid());
        out.put("success", result.getSuccess());
        out.put("data", result.getData());
        return new ModelAndView("/callback/common");
    }
    
    /**
     * 修改供应信息类别
     * @param out
     * @param request
     * @param id
     * @return
     */
    @RequestMapping
    public ModelAndView updateGroup(Map<String, Object> out, HttpServletRequest request,TradeGroup tradeGroup) {
        ExtResult result = new ExtResult();
        
        do {
        	if(tradeGroup==null){
        		break;
        	}
        	
        	EpAuthUser cachedUser = getCachedUser(request);
            tradeGroup.setCid(cachedUser.getCid());
            tradeGroup.setUid(cachedUser.getUid());

            if (tradeGroup.getSort() == null) {
            	tradeGroup.setSort((short)0);
			}
            
            Integer count = tradeGroupService.updateTradeGroup(tradeGroup);
            
            if (count != null && count > 0) {
                result.setSuccess(true);
            }
        } while (false);
        out.put("success", result.getSuccess());
        out.put("data", result.getData());
        myEsiteService.init(out, getCachedUser(request).getCid());
        return new ModelAndView("/callback/common");
    }
    
    /**
     * 删除供应信息类别
     * @param out
     * @param request
     * @param id
     * @return
     */
	@RequestMapping
	public ModelAndView deleteGroup(Map<String, Object> out,
			HttpServletRequest request, Integer id) {

		tradeGroupService.deleteTradeGroup(id, getCompanyId(request));
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:tradeGroup.htm");
	}

	/**
	 * 删除供应信息
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doDelete(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		ExtResult result = new ExtResult();
		do {
			if (id == null) {
				break;
			}
			Integer count = tradeSupplyService.deleteSupplyById(id,
					getCompanyId(request));
			if (count != null && count > 0) {
				result.setSuccess(true);
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}

	/**
	 * 推荐供应信息
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doRecommend(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		ExtResult result = new ExtResult();
		do {
			if (id == null) {
				break;
			}
			TradeRecommend recommend = new TradeRecommend();
			recommend.setCid(getCompanyId(request));
			recommend.setTargetId(id);
			recommend.setType((short) 2);
			Integer count = tradeRecommendService.createRecommend(recommend);
			if (count != null && count > 0) {
				result.setSuccess(true);
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}

	/**
	 * 取消推荐供应信息
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doDeleteRecommend(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		ExtResult result = new ExtResult();
		do {
			if (id == null) {
				break;
			}
			Integer count = tradeRecommendService.cancelRecommend(id,
					getCompanyId(request), (short) 2);
			if (count != null && count > 0) {
				result.setSuccess(true);
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}

	/**
	 * 供应信息分组（画面“移动到“按钮操作）
	 * 
	 * @param out
	 * @param request
	 * @param ids
	 * @param category
	 * @return
	 */
	@RequestMapping
	public ModelAndView group(Map<String, Object> out,
			HttpServletRequest request, Integer id, Integer gid) {
		ExtResult result = new ExtResult();
		do {
			Integer count = tradeSupplyService.updateSupplyGroupIdById(id,
					getCompanyId(request), gid);
			if (count != null && count > 0) {
				result.setData(id);
				result.setSuccess(true);
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}

	/**
	 * 供应信息发布/暂不发布按钮操作
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping
	public ModelAndView doPause(Map<String, Object> out,
			HttpServletRequest request, Integer id, Integer status) {
		ExtResult result = new ExtResult();
		do {
			Integer count = tradeSupplyService.updatePauseStatusById(id,
					getCompanyId(request), status);
			if (count != null && count > 0) {
				result.setSuccess(true);
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}

	/**
	 * 供应信息刷新按钮操作
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doRefresh(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		ExtResult result = new ExtResult();
		do {
			Integer count = tradeSupplyService.updateRefreshById(id,
					getCompanyId(request));
			if (count != null && count > 0) {
				result.setSuccess(true);
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}

	/**
	 * 供应信息查看询盘
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @param replyStatus
	 * @param page
	 * @return
	 */
	@RequestMapping
	public ModelAndView message(Map<String, Object> out,
			HttpServletRequest request, Integer id, Integer replyStatus,
			Integer readStatus, PageDto<MessageDto> page) {
		do {
			TradeSupply supply = tradeSupplyService.querySimpleDetailsById(id);
			if (supply == null) {
				break;
			}
			out.put("supply", supply);
			out.put("id", id);
			page = messageService
					.queryMessagesByCompany(getCompanyId(request),
							MessageService.SENDSTATUS_RECEIVE, id,
							MessageService.TARGET_SUPPLY, replyStatus,
							readStatus, page);
			out.put("page", page);
			return null;
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:/supply/index.htm");
	}

	/**
	 * 修改供应信息页面初始化
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView update(Map<String, Object> out,
			HttpServletRequest request, Integer id, Integer info,
			String categoryCode) {
		do {
			// 修改默认图片

			TradeSupplyDto tradeSupplyDto = tradeSupplyService
					.queryUpdateSupplyById(id, getCompanyId(request));
			if (tradeSupplyDto == null) {
				break;
			}
			out.put("supply", tradeSupplyDto);

			if (StringUtils.isEmpty(categoryCode)) {
				categoryCode = tradeSupplyDto.getSupply().getCategoryCode();
				// tradeSupplyDto.getSupply().setCategoryCode(categoryCode);
			}
			out.put("categoryCode", categoryCode);

			List<TradeProperty> propertys = null;
			out.put("categoryPath", tradeCategoryService
					.buildCategory(categoryCode));
			// 判断专业属性值
			if (tradeSupplyDto.getSupply().getId() == null) {
				break;
			}

			// 取得专业属性
			propertys = tradePropertyService
					.queryPropertyByCategoryCode(categoryCode);
			Map<Integer, Object> properyValueMap = new HashMap<Integer, Object>();
			for (TradeProperty property : propertys) {
				if (StringUtils.isNotEmpty(property.getVtypeValue())) {
					properyValueMap.put(property.getId(), JSONArray
							.fromObject(property.getVtypeValue()));
				}
			}
			if (info == null) {
				out.put("message", "<b style='red'>修改产品</b>");
			} else if (info == 0) {
				out.put("message", "<b style='red'>更新成功</b>");
			} else if (info == 1) {
				out.put("message", "<b style='red'>更新成功</b>");
			}
			out.put("propertyMap", properyValueMap);
			out.put("propertys", propertys);
			out.put("propertyInit", JSONArray.fromObject(propertys).toString());
			out.put("propertyValueMap", tradeSupplyDto.getPropertyValue());
			myEsiteService.init(out, getCachedUser(request).getCid());
			out.put("photoList", photoService.queryPhotoByTargetType("supply",
					tradeSupplyDto.getSupply().getId()));
			out.put("photoCover", tradeSupplyDto.getSupply().getPhotoCover());

			return null;
		} while (false);
		return new ModelAndView("redirect:/supply/supply.htm");
	}

	/**
	 * 选择不同类别，显示不同专业属性
	 * 
	 * @param out
	 * @param request
	 * @param category
	 * @return
	 */
	@RequestMapping
	public ModelAndView getCategoryProperty(Map<String, Object> out,
			HttpServletRequest request, String category) {
		ExtResult result = new ExtResult();
		do {
			if (StringUtils.isEmpty(category)) {
				break;
			}
			List<TradeProperty> propertys = tradePropertyService
					.queryPropertyByCategoryCode(category);
			if (propertys != null && propertys.size() > 0) {
				result.setData(propertys);
				result.setSuccess(true);
			}
		} while (false);

		return printJson(result, out);
	}

	/**
	 * 修改供应信息类别
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @param category
	 * @param values
	 * @return
	 */
	@RequestMapping
	public ModelAndView doUpdateCategory(Map<String, Object> out,
			HttpServletRequest request, Integer id, String category,
			String propertyQuery) {
		do {

			Integer count = tradeSupplyService.updateCategoryById(category,
					propertyQuery, id, getCompanyId(request));
			if (count != null && count > 0) {
				out.put("info", 0);
				break;
			}
			out.put("info", 1);
		} while (false);
		return new ModelAndView("redirect:update.htm?id=" + id);
	}

	/**
	 * 修改供应信息基本信息
	 * 
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView doUpdateBase(Map<String, Object> out,
			HttpServletRequest request, TradeSupply supply) {
		do {
			if (supply == null || supply.getId() == null) {
				return new ModelAndView("redirect:index.htm");
			}
			Integer count = tradeSupplyService.updateBaseSupplyById(supply,
					supply.getId(), getCompanyId(request));
			if (count != null && count > 0) {
				out.put("info", 0);
				break;
			}
			out.put("info", 1);
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("redirect:index.htm");
	}

	/**
	 * 获取父类下所以一级子类
	 * 
	 * @param request
	 * @param out
	 * @param parentCode
	 * @return
	 */
	@RequestMapping
	public ModelAndView publish_Category(HttpServletRequest request,
			Map<String, Object> out, String parentCode) {
		List<TradeCategory> list = tradeCategoryService.queryCategoryByParent(
				parentCode, 0, 0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("childCategory", list);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(map, out);
	}

	/**
	 * 根据关键字查询相关类别
	 * 
	 * @param request
	 * @param out
	 * @param parentCode
	 * @return
	 */
	@RequestMapping
	public ModelAndView publish_Search(HttpServletRequest request,
			Map<String, Object> out, String keyWords, String category) {
		Map<String, Object> map = new HashMap<String, Object>();
		do {
			if (StringUtils.isEmpty(keyWords)) {
				break;
			}
			if (StringUtils.isEmpty(category)) {
				break;
			}
			List<TradeCategory> list = tradeCategoryService
					.queryCategoryByTags(category, keyWords, 0, 50);
			map.put("parentCategory", list);
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(map, out);
	}

	/**
	 * 发布供应信息（第二步）
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView doProperty(Map<String, Object> out,
			HttpServletRequest request, TradePropertyValue tradePropertyValue) {
		ExtResult result = new ExtResult();
		Integer count = tradeSupplyService.updatePropertyQueryById(
				tradePropertyValue.getSupplyId(), tradePropertyValue
						.getPropertyId()
						+ "_" + tradePropertyValue.getPropertyValue());
		Integer count2 = tradePropertyValueService
				.createTradePropertyValue(tradePropertyValue);
		if (count != null && count > 0 && count2 != null && count2 > 0) {
			result.setSuccess(true);
		}
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(result, out);
	}

	/**
	 * 跳转到发布成功页面
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView publish_Success(Map<String, Object> out,
			HttpServletRequest request, Integer pid) {
		// 发送供应信息发布成功邮件
		// EpAuthUser authUser = getCachedUser(request).getAccount();
		// TradeSupply supply = tradeSupplyService.queryOneSupplyById(pid);
		// String account =
		// compAccountService.queryAccountById(supply.getUid());
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("supply", supply);
		// map.put("account", account);
		// TODO 是否要发送邮件
		// MailUtil.getInstance().sendMail(MailArga.TITLE_PUBLISH_SUCCESS,
		// compAccountService.queryAccountDetails(account).getEmail(),
		// MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
		// MailArga.TEMPLATE_PUBLISH_SUCCESS, map, MailUtil.PRIORITY_HEIGHT);
		out.put("pid", pid);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return null;

	}

	/**
	 * 首页
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request, Integer pauseStatus,
			Integer overdueStatus, Integer checkStatus, Integer recommend,
			Integer groupId, PageDto<SupplyMessageDto> page, Integer result) {

		// if(checkStatus==null){
		// checkStatus = 1;
		// }

		// 查找该用户供应信息分组类别
		List<TradeGroup> groups = tradeGroupService
				.queryTradeGroupById(getCompanyId(request));
		out.put("groups", groups);
		out.put("pauseStatus", pauseStatus);
		out.put("overdueStatus", overdueStatus);
		out.put("checkStatus", checkStatus);

		out.put("groupId", groupId);
		out.put("recommend", recommend);

		page = tradeSupplyService.pageSupplyByConditions(getCompanyId(request),
				pauseStatus, overdueStatus, checkStatus, recommend, groupId,
				page);
		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("productsCount", tradeSupplyService.countSupplyByCompany(
				getCompanyId(request), groupId));
		
		// 该列表下，供求图片上传统计
		out.put("supplyPicCount", photoService.countPhotoBySupplyId(page.getRecords()));
		
		// 和编辑的时候显示的图片保持同步.
		for (SupplyMessageDto supplyMessageDto : page.getRecords()) {
			TradeSupplyDto tradeSupplyDto = tradeSupplyService
					.queryUpdateSupplyById(supplyMessageDto.getTradeSupply()
							.getId(), getCompanyId(request));
			Photo photo = photoService.queryPhotoByTypeAndId("supply",
					tradeSupplyDto.getSupply().getId());
			if (photo != null) {
				supplyMessageDto.getTradeSupply()
						.setPhotoCover(photo.getPath());
			}
			
			//该列表下，供求图片中没有审核通过的统计
			// if("1".equals(tradeSupplyDto.getSupply().getCheckStatus())){
			// supplyMessageDto.setPhotoList(photoService.queryPhotoListByTypeAndId("supply",tradeSupplyDto.getSupply().getId(),"2"));//审核状态
			// 0:未审核,1:审核通过,2:审核不通过
			// }
			
        	if(supplyMessageDto.getTradeSupply().getCheckRefuse().indexOf("技术文章版块")!=-1){
        		supplyMessageDto.getTradeSupply().setCheckRefuse(supplyMessageDto.getTradeSupply().getCheckRefuse().replaceAll("技术文章版块", "<a href=\"http://www.huanbao.com/compnews/technicalarticles/publish.htm\">技术文章</a>版块"));
        	}
        	if(supplyMessageDto.getTradeSupply().getCheckRefuse().indexOf("发布供应信息")!=-1){
        		supplyMessageDto.getTradeSupply().setCheckRefuse(supplyMessageDto.getTradeSupply().getCheckRefuse().replaceAll("发布供应信息", "<a href=\"http://www.huanbao.com/myesite/supply/publish_StpOne.htm\">发布供应信息</a>"));
        	}
		}
		out.put("page", page);

		// 记录报错 状态
		out.put("result", result);

		return new ModelAndView();
	}

	/**
	 * 供应信息类别管理
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView tradeGroup(Map<String, Object> out,
			HttpServletRequest request) {
		List<TradeGroupDto> list = tradeGroupService
				.queryTradeGroupDtoById(getCompanyId(request));
		out.put("list", list);
		out.put("item", "product");
		myEsiteService.init(out, getCachedUser(request).getCid());
		return null;
	}

	/**
	 * 添加类别
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView createGroup(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		EpAuthUser cachedUser = getCachedUser(request);
		if (id != null && id.intValue() > 0) {
			TradeGroup tradeGroup = tradeGroupService.queryTradeGroup(id);
			out.put("tradeGroup", tradeGroup);
			if (tradeGroupService.childAvalible(cachedUser.getCid(), id)) {
				out.put("childAvalibe", "1");
			}
			out.put("id", id);
		}
		List<TradeGroup> list = tradeGroupService.queryTradeGroupByCid(
				cachedUser.getCid(), 0);
		out.put("list", list);
		myEsiteService.init(out, getCachedUser(request).getCid());

		return null;
	}

	/**
	 * 产品信息图片
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView supply_img(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("photoCover", tradeSupplyService.queryPhotoCover(id));
		out.put("photoList", photoService.queryPhotoByTargetType("supply", id));
		out.put("id", id);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return null;
	}

	@RequestMapping
	public ModelAndView updatePhotoCover(Map<String, Object> out,
			HttpServletRequest request, Integer id) {

		Map<String, Object> map = new HashMap<String, Object>();

		Photo photo = photoService.queryPhotoById(id);
		Integer pInteger = tradeSupplyService.updatePhotoCover(photo
				.getTargetId(), photo.getPath());
		if (pInteger != 0) {
			map.put("photoCover", photo.getPath());
			map.put("success", "成功");
			List<Photo> photoList = photoService.queryPhotoByTargetType(
					"supply", photo.getTargetId());
			map.put("photoList", photoList);

		}

		return printJson(map, out);
	}
	
}
