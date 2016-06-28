/*
 * 文件名称：TagsController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.www.controller.BaseController;
import com.zz91.util.tags.TagsUtils;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：标签系统片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 *			2012-09-20		马元生				1.0.1		移到ep-api项目
 */
@Deprecated
@Controller
public class TagsController extends BaseController {
	
	@Resource
	private TradeCategoryService tradeCategoryService;
	/**
	 * 函数名称：categoryTags
	 * 功能描述：标签系统关键字查询
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param categoryCode String 标签类别
	 *        @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView categoryTags(HttpServletRequest request, Map<String, Object> out,String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		Map<String, String> tagMap = TagsUtils.getInstance().queryTagsByCode(categoryCode, null, size);
		map.put("tags", tagMap);
		return printJson(map, out);
	}
	
	/**
	 * 函数名称：tradeCategoryTags
	 * 功能描述：产品类别标签查询
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param categoryCode String 标签类别
	 *        @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView tradeCategoryTags(HttpServletRequest request, Map<String, Object> out,String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
    	List<String> tags = tradeCategoryService.queryTagsByCode(categoryCode);
    	if (tags != null && tags.size() > size) {
    		tags = tags.subList(0, size);
		}
    	map.put("list", tags);
		return printJson(map, out);
	}

}