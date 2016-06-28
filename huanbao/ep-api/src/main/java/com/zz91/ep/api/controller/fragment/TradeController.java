/**
 * 文件名称：TradeController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.api.controller.fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.api.controller.BaseController;
import com.zz91.ep.domain.trade.IbdCategory;
import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.GenericCategoryDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchBuyDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeBuyNormDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.trade.IbdCategoryService;
import com.zz91.ep.service.trade.IbdCompanyService;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.ep.service.trade.SubnetTradeRecommendService;
import com.zz91.ep.service.trade.TradeBuyService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

/**
 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：交易中心页面片段缓存。 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class TradeController extends BaseController {

	// 默认顶级供应信息类别
	private static final String SUPPLY_TOP_CATEGORY = "1000";

	private static final String IBD_DEFAULT_CATEGORY = "1000";

	private static final String BUY_INDEX_TOP10 = "search_buy_top10";

	@Resource
	private TradeCategoryService tradeCategoryService;

	@Resource
	private TradeSupplyService tradeSupplyService;

	@Resource
	private TradeBuyService tradeBuyService;

	@Resource
	private SolrService solrService;

	@Resource
	private IbdCategoryService ibdCategoryService;

	@Resource
	private IbdCompanyService ibdCompanyService;

	@Resource
	private SubnetTradeRecommendService subnetTradeRecommendService;

	@Resource
	private MessageService messageService;

	@Resource
	private CompAccountService compAccountService;

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取所有在首页显示的交易类别列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView getAllCategory(HttpServletRequest request,
			Map<String, Object> out) {
		List<TradeCategory> allObjects = tradeCategoryService
				.queryCategoryByParent(SUPPLY_TOP_CATEGORY, 2, 0);
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 重新组织类别
			List<GenericCategoryDto<TradeCategory>> list = linkGenericCategory(
					allObjects, SUPPLY_TOP_CATEGORY);
			for (int i = 0; i < list.size(); i++) {
				// 设置类别产品数
				Integer count = tradeSupplyService
						.querySupplyCountByCategory(list.get(i)
								.getCategoryDomain().getCode());
				list.get(i).setCount(count);
				// 设置热门标签
				String tagsString = list.get(i).getCategoryDomain().getTags();
				if (StringUtils.isNotEmpty(tagsString)) {
					List<String> tagsList = new ArrayList<String>();
					String[] tagsArr = tagsString.split(",");
					for (int j = 0; j < tagsArr.length; j++) {
						tagsList.add(tagsArr[j]);
					}
					list.get(i).setTagsList(tagsList);
				}
			}
			map.put("supplyCategory", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView queryPicByCode(HttpServletRequest request,Map<String, Object> out,String code,Integer size){
		if(size==null){
			size = 10;
		}
		if(size>50){
			size = 50;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 搜索引擎获取 图片供求
		SearchSupplyDto search = new SearchSupplyDto();
		PageDto<TradeSupplyNormDto> page = new PageDto<TradeSupplyNormDto>();
		search.setHavePic(true);
		if(StringUtils.isNotEmpty(code)){
			search.setKeywords(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, code));
		}
		page.setLimit(3);
		map.put("picList", tradeSupplyService.pageBySearchEngineTrade(search, page).getRecords());
		
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取所有在首页显示的交易类别列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * 
	 */
	@RequestMapping
	public ModelAndView queryIndexCategory(HttpServletRequest request,
			Map<String, Object> out, String category) {

		if (StringUtils.isEmpty(category)) {
			category = SUPPLY_TOP_CATEGORY;
		}

		List<TradeCategory> allObjects = tradeCategoryService
				.queryCategoryByParent(category, 2, 0);
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 重新组织类别
			List<GenericCategoryDto<TradeCategory>> list = linkGenericCategory(
					allObjects, category);
			for (int i = 0; i < list.size(); i++) {
				// 设置类别产品数
				Integer count = tradeSupplyService
						.querySupplyCountByCategory(list.get(i)
								.getCategoryDomain().getCode());
				list.get(i).setCount(count);
				// 设置热门标签
				String tagsString = list.get(i).getCategoryDomain().getTags();
				if (StringUtils.isNotEmpty(tagsString)) {
					List<String> tagsList = new ArrayList<String>();
					String[] tagsArr = tagsString.split(",");
					for (int j = 0; j < tagsArr.length; j++) {
						tagsList.add(tagsArr[j]);
					}
					list.get(i).setTagsList(tagsList);
				}
			}

			map.put("parent", tradeCategoryService.getCategoryByCode(category));
			map.put("supplyCategory", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：编辑交易类别，按父子类别存储。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	public static <T> List<GenericCategoryDto<T>> linkGenericCategory(
			List<T> allCategories, String code)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException, NoSuchMethodException {
		List<GenericCategoryDto<T>> listDto = new ArrayList<GenericCategoryDto<T>>();
		List<T> listDomain = new ArrayList<T>();
		if (code == null) {
			code = "";
		}
		for (T category : allCategories) {
			Method method = category.getClass().getMethod("getCode");
			String tmpCode = method.invoke(category).toString();
			if (tmpCode.length() == code.length() + 4
					&& tmpCode.substring(0, code.length()).equals(code)) {
				listDomain.add(category);
			}
		}
		for (T entity : listDomain) {
			GenericCategoryDto<T> dto = new GenericCategoryDto<T>();
			dto.setCategoryDomain(entity);
			Method method = entity.getClass().getMethod("getCode");
			code = method.invoke(entity).toString();
			dto.setChild(linkGenericCategory(allCategories, code));
			listDto.add(dto);
		}
		return listDto;
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取不同类别推荐供应信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendSupply(HttpServletRequest request,
			Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<CommonDto> list = tradeSupplyService.querySupplysByRecommend(size);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取不同类别推荐求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendBuy(HttpServletRequest request,
			Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<CommonDto> list = tradeBuyService.queryBuysByRecommend(size);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取不同类别推荐求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendDetailsBuy(HttpServletRequest request,
			Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<TradeBuy> list = tradeBuyService.queryBuysDetailsByRecommend(size);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取不同类别最新求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView newestBuy(HttpServletRequest request,
			Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<CommonDto> list = tradeBuyService.queryNewestBuys(size);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取最新供应信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件 　　　　　
	 * 2012-09-20　　　黄怀清　　　　　　　1.0.1　　　　　增加类别条件category
	 */
	@RequestMapping
	public ModelAndView newestSupply(HttpServletRequest request,
			Map<String, Object> out, Integer cid, Integer size,
			String category, Integer uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		if (uid == null || uid <= 0) {
			uid = compAccountService.queryUidByCid(cid);
		}
		List<TradeSupply> list = tradeSupplyService.queryNewestSupplys(cid,
				size, null, uid);
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取最新热门供应信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * 
	 * @throws SolrServerException
	 */
	@RequestMapping
	public ModelAndView hotSupply(HttpServletRequest request,
			Map<String, Object> out, SearchSupplyDto search,
			PageDto<TradeSupplyNormDto> page) throws SolrServerException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (search == null) {
			search = new SearchSupplyDto();
		}
		search.setKeywords(CodeCachedUtil.getValue(
				CodeCachedUtil.CACHE_TYPE_TRADE, search.getCategory()));
		page = tradeSupplyService.pageBySearchEngine(search, page);
		//page=tradeSupplyService.pageBySearchEngineTrade(search, page);
		map.put("list", page.getRecords());
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取最新热门求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * 
	 * @throws SolrServerException
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView hotBuy(HttpServletRequest request,
			Map<String, Object> out, PageDto<TradeBuyNormDto> page,
			SearchBuyDto search) throws SolrServerException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (search == null) {
			search = new SearchBuyDto();
		}
		page = solrService.pageBuysBySearch(search, page, "2");
		map.put("list", page.getRecords());
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取最新热门求购信息关键字。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * 
	 * @throws SolrServerException
	 */
	@RequestMapping
	public ModelAndView topKeyWordsBuys(HttpServletRequest request,
			Map<String, Object> out) {
		Map<String, String> map = ParamUtils.getInstance().getChild(
				BUY_INDEX_TOP10);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取子网推荐产品信息
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-06-27　　　齐振杰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView subRecommendSupply(HttpServletRequest request,
			Map<String, Object> out, String cate, Integer size) {
		List<TradeSupply> list = subnetTradeRecommendService
				.querySupplyBySubRec(cate, size);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取最新热门求购信息关键字。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
	 * 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * 
	 * @throws SolrServerException
	 */
	@RequestMapping
	public ModelAndView ibdCategory(HttpServletRequest request,
			Map<String, Object> out, String categoryCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<IbdCategory> list = ibdCategoryService
				.queryCategoryByParentCode(categoryCode);
		// 如果是终端企业名录的话，查询相应类别对应的企业数
		if (IBD_DEFAULT_CATEGORY.equals(categoryCode)) {
			for (IbdCategory idb : list) {
				idb.setIsShow(ibdCompanyService.queryCountByCategoryCode(idb
						.getCode()));
			}
		}
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取交易类别列表。 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 * 　　　　　 2012-09-20　　　马元生　　　　　　　1.0.0　　　　　从TradeController移过来
	 * 
	 * @throws SolrServerException
	 */
	@RequestMapping
	public ModelAndView getCategory(HttpServletRequest request,
			Map<String, Object> out, String code, Integer deep)
			throws SolrServerException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();

		List<TradeCategory> list = tradeCategoryService.queryCategoryByParent(
				code, deep == null ? 0 : deep, 0);

		map.put("cglist", list);
		return printJson(map, out);
	}

	/**
	 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：获取交易类别列表。 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 * 　　　　　 2012-09-21　　　马元生　　　　　　　1.0.0　　　　　从TradeController移过来
	 * 
	 * @throws SolrServerException
	 */
	@RequestMapping
	public ModelAndView zwCategory(HttpServletRequest request,
			Map<String, Object> out, SubnetCategory sub,
			PageDto<SubnetCategory> page) throws SolrServerException,
			ParseException {
		if (sub.getParentId() == null) {
			sub.setParentId(0);
		}
		page.setLimit(Integer.MAX_VALUE);
		Map<String, Object> map = new HashMap<String, Object>();
		// 若未指定parentId则加载所有1级类别下的2级类别
		// 得到1级类别列表parents
		List<SubnetCategory> parents = new ArrayList<SubnetCategory>();
		parents.addAll(solrService.pageSubCategory(sub, page).getRecords());
		page.getRecords().clear();

		if (parents.size() > 0) {
			// 加载2级类别
			PageDto<SubnetCategory> seconds = new PageDto<SubnetCategory>();
			for (SubnetCategory subDto : parents) {
				// 指定当前要牵索的类别parentId
				sub.setParentId(subDto.getId());
				// 取出当前1级类别下的2级类别列表
				seconds = solrService.pageSubCategory(sub, seconds);
				// 追加进2级类别总集合
				if (seconds.getRecords().size() > 0) {
					page.getRecords().addAll(seconds.getRecords());
				}
			}
		}

		map.put("list", page.getRecords());
		return printJson(map, out);
	}

	/**
	 * 函数名称：topbar 功能描述：全局共用导航栏目 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map
	 * @param loginName
	 *            String 登录用户名 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/09/27　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView topbar(HttpServletRequest request,
			Map<String, Object> out, String loginName) {
		if (StringUtils.isNotEmpty(loginName)) {
			out.put("loginName", loginName);
		}
		return null;
	}

	/**
	 * 函数名称：header 功能描述：全局共用头部导航 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map
	 * @param ik
	 *            String
	 *            模块（首页-index;供应-supply;求购-buy;公司库-company;资讯-news;展会-exhibit）
	 *            异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容 　　　　　2012/09/27　　
	 *            黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView header(HttpServletRequest request,
			Map<String, Object> out, String ik) {
		out.put("messages", messageService.queryNewestMessage(null, 10));
		out.put("ik", ik);

		return null;
	}

	/**
	 * 函数名称：footer 功能描述：全局共用尾部内容 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/10/18　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView footer(HttpServletRequest request,
			Map<String, Object> out) {
		return null;
	}

}
