/**
 * 
 */
package com.zz91.ep.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.service.exhibit.ExhibitIndustryCategoryService;
import com.zz91.ep.admin.service.exhibit.ExhibitPlateCategoryService;
import com.zz91.ep.admin.service.news.NewsCategoryService;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.ep.admin.service.sys.SysAreaService;
import com.zz91.ep.admin.service.trade.SubnetCategoryService;
import com.zz91.ep.admin.service.trade.TradeCategoryService;
import com.zz91.ep.domain.exhibit.ExhibitIndustryCategory;
import com.zz91.ep.domain.exhibit.ExhibitPlateCategory;
import com.zz91.ep.domain.news.NewsCategory;
import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.ExtResult;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.param.ParamUtils;

/**
 * 系统设置相关controller，如启用缓存设置等
 * @author leon
 *
 */
@Controller
public class SysConfigController extends BaseController{

	@Resource private NewsCategoryService newsCategoryService;
	@Resource private TradeCategoryService tradeCategoryService;
	@Resource private ParamService paramService;
	@Resource private SysAreaService sysAreaService;
	@Resource private ExhibitPlateCategoryService exhibitPlateCategoryService;
	@Resource private ExhibitIndustryCategoryService exhibitIndustryCategoryService;
	@Resource private SubnetCategoryService subnetCategoryService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	/**
	 * 更新开启地区目录缓存
	 */
	@RequestMapping
	public ModelAndView initCacheForAreaCategory(Map<String, Object> out, HttpServletRequest request){
		List<SysArea> list = sysAreaService.queryAreaAll();
		Map<String, String> map = new HashMap<String, String>();
		for (SysArea sysArea : list) {
			map.put(sysArea.getCode(), sysArea.getName());
		}
		CodeCachedUtil.init(map, CodeCachedUtil.CACHE_TYPE_AREA, null);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	/**
	 * 更新开启交易类别缓存
	 */
	@RequestMapping
	public ModelAndView initCacheForTradeCategory(Map<String, Object> out, HttpServletRequest request){
		List<TradeCategory> list = tradeCategoryService.queryTradeSupplyAll();
		Map<String, String> map = new HashMap<String, String>();
		for (TradeCategory tradeCategory : list) {
			map.put(tradeCategory.getCode(), tradeCategory.getName());
		}
		CodeCachedUtil.init(map, CodeCachedUtil.CACHE_TYPE_TRADE, null);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	/**
	 * 更新开启新闻目录缓存
	 */
	@RequestMapping
	public ModelAndView initCacheForNewsCategory(Map<String, Object> out, HttpServletRequest request){
		List<NewsCategory> list = newsCategoryService.queryNewsCategoryAll();
		Map<String, String> map = new HashMap<String, String>();
		for (NewsCategory newsCategory : list) {
			map.put(newsCategory.getCode(), newsCategory.getName());
		}
		CodeCachedUtil.init(map, CodeCachedUtil.CACHE_TYPE_NEWS, null);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	/**
	 * 更新开启展会目录缓存
	 */
	@RequestMapping
	public ModelAndView initCacheForExhibitCategory(Map<String, Object> out, HttpServletRequest request){
		List<ExhibitPlateCategory> list = exhibitPlateCategoryService.queryExhibitPlateCategoryAll();
		Map<String, String> map = new HashMap<String, String>();
		for (ExhibitPlateCategory exhibitPlateCategory : list) {
			map.put(exhibitPlateCategory.getCode(), exhibitPlateCategory.getName());
		}
		CodeCachedUtil.init(map, CodeCachedUtil.CACHE_TYPE_EXHIBIT, null);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	/**
	 * 更新开启展会行业目录缓存
	 */
	@RequestMapping
	public ModelAndView initCacheForExhibitIndustry(Map<String, Object> out, HttpServletRequest request){
		List<ExhibitIndustryCategory> list = exhibitIndustryCategoryService.queryExhibitIndustryCategoryAll();
		Map<String, String> map = new HashMap<String, String>();
		for (ExhibitIndustryCategory exhibitIndustryCategory : list) {
			map.put(exhibitIndustryCategory.getCode(), exhibitIndustryCategory.getName());
		}
		CodeCachedUtil.init(map, CodeCachedUtil.CACHE_TYPE_EXHIBIT, null);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	/**
	 * 系统参数类别
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView initParam(Map<String, Object> out, HttpServletRequest request){
		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
		/*List<Param> list = paramService.queryUsefulParam();
		Map<String, String> map = new HashMap<String, String>();
		for (Param param : list) {
			map.put(param.getKey(), param.getValue());
		}
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		paramMap.put(arg0, map)
		CodeCachedUtil.init(map, CodeCachedUtil.CACHE_TYPE_PARAM, null);*/
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	/**
	 * 更新开启子网类别目录缓存
	 */
	@RequestMapping
	public ModelAndView initCacheForSubnetCategory(Map<String, Object> out, HttpServletRequest request){
		List<SubnetCategory> list=subnetCategoryService.queryAllCategory();
		Map<String, String> map = new HashMap<String, String>();
		for (SubnetCategory category : list) {
			map.put(category.getCode(), category.getKeyword());
		}
		CodeCachedUtil.init(map, CodeCachedUtil.CACHE_TYPE_SUBNET, null);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
}
