/*
 * 文件名称：SubnetController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.api.controller.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.api.controller.BaseController;
import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.domain.trade.SubnetLink;
import com.zz91.ep.dto.trade.SubnetCategoryDto;
import com.zz91.ep.service.trade.SubnetCategoryService;
import com.zz91.ep.service.trade.SubnetLinkService;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：首页页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class SubnetController extends BaseController {
	
	@Resource
	private SubnetCategoryService subnetCategoryService;
	@Resource
	private SubnetLinkService subnetLinkService;
	
	/**
	 * 函数名称：category
	 * 功能描述：子网类别查询
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param categoryCode String 标签类别
	 *        @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　  齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView categoryAll(HttpServletRequest request, Map<String, Object> out,Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SubnetCategoryDto> list = subnetCategoryService.queryAllCategory(id);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 函数名称：category
	 * 功能描述：子网友情链接
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param parentId 子网编号
	 *        @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　 2012/05/23　　  齐振杰　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView querySubLink(HttpServletRequest request, Map<String, Object> out,Integer parentId){
		List<SubnetLink> list=subnetLinkService.querySubnetLink(parentId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 函数名称：category
	 * 功能描述：子网相关产品
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param parentId 子网编号
	 *        @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　 2012/07/02　　  齐振杰　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView relatedProducts(Map<String, Object> out,Integer parentId,Integer size){
		List<SubnetCategory> list = subnetCategoryService.queryCategoryByParentId(parentId, size);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
}