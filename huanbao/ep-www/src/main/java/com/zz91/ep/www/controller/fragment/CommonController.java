/*
 * 文件名称：CommonController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.fragment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.service.common.ParamService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.www.controller.BaseController;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.param.ParamUtils;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：首页页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 *			2012-09-20		马元生				1.0.1		移到ep-api项目
 */
@Deprecated
@Controller
public class CommonController extends BaseController {
	
	@Resource
	private ParamService paramService;
	
	@Resource
	private TradeCategoryService tradeCategoryService;

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取首页今日市场相关Json数据。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView todayMarket(HttpServletRequest request, Map<String, Object> out) {
		Map<String, Object> map = new HashMap<String, Object>();
		String dateTime =DateUtil.toString(new Date(), "MM.dd");
		//今日市场 日期
		map.put("dateTime", dateTime);
		//环保企业总数
		String allCompanyCount = ParamUtils.getInstance().getValue("today_market", "allCompanyCount");
		map.put("allCompanyCount", allCompanyCount);
		//环保信息总数
		String allInfoCount = ParamUtils.getInstance().getValue("today_market", "allInfoCount");
		map.put("allInfoCount", allInfoCount);
		//一周新增客户数
		String weekCompanyCount = ParamUtils.getInstance().getValue("today_market", "weekCompanyCount");
		map.put("weekCompanyCount", weekCompanyCount);
		//一周新增信息量
		String weekInfoCount = ParamUtils.getInstance().getValue("today_market", "weekInfoCount");
		map.put("weekInfoCount", weekInfoCount);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取买家中心首页今日市场相关Json数据。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView buyMarket(HttpServletRequest request, Map<String, Object> out) {
		Map<String, Object> map = new HashMap<String, Object>();
    	//已有采购商入驻
    	String existingBuy = ParamUtils.getInstance().getValue("procurement_match", "existing_buy");
    	map.put("existingBuy", existingBuy);
    	//已有供应商入驻
    	String existingSupply = ParamUtils.getInstance().getValue("procurement_match", "existing_supply");
    	map.put("existingSupply", existingSupply);
    	//已有采购需求
    	String existingBuyRequire = ParamUtils.getInstance().getValue("procurement_match", "existing_buy_require");
    	map.put("existingBuyRequire", existingBuyRequire);
    	//共匹配商品
    	String matchingGoods = ParamUtils.getInstance().getValue("procurement_match", "matching_goods");
    	map.put("matchingGoods", matchingGoods);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取首页友情链接数据。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView friendLink(HttpServletRequest request,Map<String, Object> out, Integer size){
		Map<String, Object> map=new HashMap<String, Object>();
		//友情链接
		List<Param> friendLink = paramService.queryParamsByType("friend_link");
		map.put("friendLink", friendLink);
        return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：不同类别标签内容。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView daohangTags(HttpServletRequest request, Map<String, Object> out, String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> tags = tradeCategoryService.queryTagsByCode(categoryCode);
		map.put("tags", tags);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：公告信息。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
	@RequestMapping
	public ModelAndView announcement(HttpServletRequest request, Map<String, Object> out, String categoryCode, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Param> list = paramService.queryParamsByType("huanbao_announcement");
		map.put("list", list);
		return printJson(map, out);
	}
}