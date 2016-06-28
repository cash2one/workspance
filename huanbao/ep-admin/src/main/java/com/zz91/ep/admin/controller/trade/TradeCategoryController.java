package com.zz91.ep.admin.controller.trade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.trade.TradeCategoryService;
import com.zz91.ep.admin.service.trade.TradePropertyService;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradePropertyDto;
import com.zz91.util.cache.CodeCachedUtil;
@Controller
public class TradeCategoryController extends BaseController{
	
	@Resource
	private TradeCategoryService tradeCategoryService;
	@Resource
	private TradePropertyService tradePropertyService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView details(HttpServletRequest request, Map<String, Object> out, Integer id){
		out.put("id", id);
		return null;
	}
	/**
	 * 删除关联交易类别属性
	 */
	@RequestMapping
	public ModelAndView deleteTradeProperty(HttpServletRequest request,Integer id,Map<String, Object> out)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i = tradePropertyService.deleteTradePropertyById(id);
		if(i != null && i.intValue() > 0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 交易关联列表（附带列表功能）
	 */
	@RequestMapping
	public ModelAndView queryTradeProperty(HttpServletRequest request,String categoryCode,
			PageDto<TradePropertyDto> page,Map<String, Object> out) throws IOException{
		page = tradePropertyService.pageTradeProperty(categoryCode, page);
		return printJson(page, out);
	}
	/**
	 * 创建交易关联
	 */
	@RequestMapping
	public ModelAndView createTradeProperty(HttpServletRequest request,Map<String, Object> out, TradeProperty tradeProperty)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i = tradePropertyService.createTradeProperty(tradeProperty);
		if(i != null && i.intValue() > 0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 创建交易类别
	 */
	@RequestMapping
	public ModelAndView createCategory(HttpServletRequest request,TradeCategory tradeCategory, String parentCode, Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer count=tradeCategoryService.queryCountByNameOrTags(tradeCategory.getName(),tradeCategory.getTags());
		if (count>0){
			result.setData("类别名称或标签名称已经存在!");
			result.setSuccess(false);
		}else {
			Integer i = tradeCategoryService.createTradeCategory(tradeCategory, parentCode);
			if(i!=null && i.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	/**
	 * 删除交易类别。同时删除关联表相关数据
	 */
	@RequestMapping
	public ModelAndView deleteCategory(HttpServletRequest request,String code,Map<String, Object> out) 
			throws IOException  {
		ExtResult result=new ExtResult();
		Integer i=tradeCategoryService.deleteTradeCategory(code);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 更新交易类别，同时更新关联表
	 */
	@RequestMapping
	public ModelAndView updateCategory(HttpServletRequest request,TradeCategory tradeCategory,Map<String, Object> out)
			throws IOException{
		ExtResult result=new ExtResult();
		Integer count=tradeCategoryService.queryCountByNameOrTags(tradeCategory.getName(),tradeCategory.getTags());
		if (count>0){
			result.setData("类别名称或标签名称已经存在!");
			result.setSuccess(false);
		}else {
			Integer i=tradeCategoryService.updateTradeCategory(tradeCategory);
			if(i!=null && i.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView child(HttpServletRequest request, Map<String, Object> out, String parentCode) 
			throws IOException{
		List<ExtTreeDto> categoryNode = tradeCategoryService.queryTradeCategoryNode(parentCode);
		return printJson(categoryNode, out);
	}
	
	@RequestMapping
	public ModelAndView queryCategoryByCode(HttpServletRequest request, Map<String, Object> out, String code){
		TradeCategory category=tradeCategoryService.queryCategoryByCode(code);
		List<TradeCategory> list=new ArrayList<TradeCategory>();
		if(category!=null){
			list.add(category);
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneTradeProperty(HttpServletRequest request, Map<String, Object> out,
			Integer id){
		List<TradePropertyDto> list=new ArrayList<TradePropertyDto>();
		TradeProperty tradeProperty=tradePropertyService.queryTradeCategoryById(id);
		TradePropertyDto dto=new TradePropertyDto();
		dto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, tradeProperty.getCategoryCode()));
		dto.setTradeProperty(tradeProperty);
		list.add(dto);
		return printJson(list, out);
	}
	@RequestMapping
	public ModelAndView updateTradeProperty(HttpServletRequest request, TradeProperty tradeProperty, Map<String, Object> out) 
			throws IOException{
		ExtResult result=new ExtResult();
		Integer i=tradePropertyService.updateTradeProperty(tradeProperty);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
