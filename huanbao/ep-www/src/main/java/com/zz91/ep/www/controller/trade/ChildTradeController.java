/*
 * 文件名称：SupplyController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.trade;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.ep.www.controller.BaseController;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

/**
 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：交易中心的供应信息处理操作 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class ChildTradeController extends BaseController {

	final static Map<String, Object> PTITLE_MAP = new LinkedHashMap<String, Object>();
	final static Map<String, Object> SUBNET_MAP = new HashMap<String, Object>();
	final static Map<String, Object> NEWS_MAP = new HashMap<String, Object>();
	final static Map<String, Object> EXHIBIT_MAP = new HashMap<String, Object>();
	final static Map<String, Object> HOTSUPPLY_MAP = new HashMap<String, Object>();
	final static Map<String, Object> NEWCOMP_MAP = new HashMap<String, Object>();

	static {
		PTITLE_MAP.put("ws", "污水处理");
		PTITLE_MAP.put("ys", "原水处理");
		PTITLE_MAP.put("kq", "空气净化");
		PTITLE_MAP.put("yj", "材料药剂");
		PTITLE_MAP.put("beng", "泵");
		PTITLE_MAP.put("fa", "阀");
		PTITLE_MAP.put("fj", "风机");
		PTITLE_MAP.put("hw", "环卫设备");
		PTITLE_MAP.put("yqyb", "仪器仪表");
		PTITLE_MAP.put("zh", "综合设备");

		SUBNET_MAP.put("ws", 3); // categoryAll
		SUBNET_MAP.put("ys", 5);
		SUBNET_MAP.put("kq", 6);
		SUBNET_MAP.put("yj", 7);
		SUBNET_MAP.put("beng", 8);
		SUBNET_MAP.put("fa", 12);
		SUBNET_MAP.put("fj", 9);
		SUBNET_MAP.put("hw", 10);
		SUBNET_MAP.put("yqyb", 11);
		SUBNET_MAP.put("zh", 13);

		NEWS_MAP.put("ws", "10001000"); // categoryNews
		NEWS_MAP.put("ys", "10001003");
		NEWS_MAP.put("kq", "10001004");
		NEWS_MAP.put("yj", "10001005");
		NEWS_MAP.put("beng", "10001000");
		NEWS_MAP.put("fa", "10001006");
		NEWS_MAP.put("fj", "10001004");
		NEWS_MAP.put("hw", "10001002");
		NEWS_MAP.put("yqyb", "10001001");
		NEWS_MAP.put("zh", "10001006");

		EXHIBIT_MAP.put("ws", "10011001");
		EXHIBIT_MAP.put("ys", "10011002");
		EXHIBIT_MAP.put("kq", "10011003");
		EXHIBIT_MAP.put("yj", "10001007");
		EXHIBIT_MAP.put("beng", "10011004");
		EXHIBIT_MAP.put("fa", "10011004");
		EXHIBIT_MAP.put("fj", "10011004");
		EXHIBIT_MAP.put("hw", "10001014");
		EXHIBIT_MAP.put("yqyb", "10001005");
		EXHIBIT_MAP.put("zh", "10001006");

		HOTSUPPLY_MAP.put("ws", "10001000");
		HOTSUPPLY_MAP.put("ys", "10001001");
		HOTSUPPLY_MAP.put("kq", "10001002");
		HOTSUPPLY_MAP.put("yj", "10001003");
		HOTSUPPLY_MAP.put("beng", "100010041000");
		HOTSUPPLY_MAP.put("fa", "100010041001");
		HOTSUPPLY_MAP.put("fj", "100010041002");
		HOTSUPPLY_MAP.put("hw", "10001005");
		HOTSUPPLY_MAP.put("yqyb", "10001006");
		HOTSUPPLY_MAP.put("zh", "10001007");

		NEWCOMP_MAP.put("ws", "10001000");
		NEWCOMP_MAP.put("ys", "10001001");
		NEWCOMP_MAP.put("kq", "10001002");
		NEWCOMP_MAP.put("yj", "10001003");
		NEWCOMP_MAP.put("beng", "10001004");
		NEWCOMP_MAP.put("fa", "10001004");
		NEWCOMP_MAP.put("fj", "10001004");
		NEWCOMP_MAP.put("hw", "10001005");
		NEWCOMP_MAP.put("yqyb", "10001006");
		NEWCOMP_MAP.put("zh", "10001007");

	}

	@Resource
	private TradeSupplyService tradeSupplyService;

	/**
	 * 函数名称：index 功能描述：子网首页 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/09/26　　 马元生　　 　　 　 1.0.0　　 　　 重构子网首页
	 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out, String child,
			PageDto<TradeSupplyNormDto> page) {
		child = child.replace("/", "");
		// 从map或参数表里查找出对应的code,id等

		if (!PTITLE_MAP.containsKey(child)) {
			return new ModelAndView("redirect:/" + child + "/index.htm");
		}

		SeoUtil.getInstance().buildSeo(child, null, null, null, out);

		out.put("child", child);
		out.put("ptitleMap", PTITLE_MAP);
		out.put("subnetCategory", SUBNET_MAP.get(child));
		out.put("newsCategory", NEWS_MAP.get(child));
		out.put("exhibitCategory", EXHIBIT_MAP.get(child));
		out.put("hotSupply", HOTSUPPLY_MAP.get(child));
		out.put("newComp", NEWCOMP_MAP.get(child));

		out.put("tags", TagsUtils.getInstance().queryTagsByCode("100010011000",
				null, 8));
		out.put("parentId", ParamUtils.getInstance().getValue(
				"subnet_code_list", child));

		// 搜索引擎
		SearchSupplyDto search = new SearchSupplyDto();
		String category = (String) HOTSUPPLY_MAP.get(child);
		search.setCategory(category);
		search.setKeywords(CodeCachedUtil.getValue(
				CodeCachedUtil.CACHE_TYPE_TRADE, search.getCategory()));
		page.setLimit(10);
		page = tradeSupplyService.pageBySearchEngineTrade(search, page);
		out.put("tradeList", page.getRecords());

		return null;
	}

}