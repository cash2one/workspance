/*
 * 文件名称：TradeController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller.fragment;

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

import com.zz91.ep.domain.trade.IbdCategory;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.GenericCategoryDto;
import com.zz91.ep.dto.trade.TradeBuySearchDto;
import com.zz91.ep.dto.trade.TradeSupplySearchDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.trade.IbdCategoryService;
import com.zz91.ep.service.trade.IbdCompanyService;
import com.zz91.ep.service.trade.SubnetTradeRecommendService;
import com.zz91.ep.service.trade.TradeBuyService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.ep.www.controller.BaseController;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：交易中心页面片段缓存。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 *			2012-09-20		马元生				1.0.1		移到ep-api项目
 */
@Deprecated
@Controller
public class TradeFragmentController extends BaseController {
	
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
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取所有在首页显示的交易类别列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
    @RequestMapping
    public ModelAndView getAllCategory(HttpServletRequest request, Map<String, Object> out) {
        List<TradeCategory> allObjects = tradeCategoryService.queryCategoryByParent(SUPPLY_TOP_CATEGORY, 2, 0);
        Map<String, Object> map=new HashMap<String, Object>();
        try {
            // 重新组织类别
            List<GenericCategoryDto<TradeCategory>> list = linkGenericCategory(allObjects, SUPPLY_TOP_CATEGORY);
            for (int i = 0; i < list.size(); i++) {
            	// 设置类别产品数
//            	Integer count = tradeSupplyService.querySupplyCountByCategory(list.get(i).getCategoryDomain().getCode());
//            	list.get(i).setCount(20);
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
    
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：编辑交易类别，按父子类别存储。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
    public static <T> List<GenericCategoryDto<T>> linkGenericCategory(List<T> allCategories,
            String code) throws IllegalArgumentException, IllegalAccessException,
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
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐供应信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendSupply(HttpServletRequest request, Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<CommonDto> list = tradeSupplyService.querySupplysByRecommend(size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendBuy(HttpServletRequest request, Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<CommonDto> list = tradeBuyService.queryBuysByRecommend(size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别推荐求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView recommendDetailsBuy(HttpServletRequest request, Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<TradeBuy> list = tradeBuyService.queryBuysDetailsByRecommend(size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取不同类别最新求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView newestBuy(HttpServletRequest request, Map<String, Object> out, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		List<CommonDto> list = tradeBuyService.queryNewestBuys(size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新供应信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 */
	@RequestMapping
	public ModelAndView newestSupply(HttpServletRequest request, Map<String, Object> out,Integer cid, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = DEFAULT_SIZE;
		}
//		List<CommonDto> list = tradeSupplyService.queryNewestSupplys(cid, size,null,null);
//		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新热门供应信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
//	@RequestMapping
//	public ModelAndView hotSupply(HttpServletRequest request, Map<String, Object> out,String categoryCode, String keyWords, Integer size) throws SolrServerException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (size == null) {
//			size = DEFAULT_SIZE;
//		}
//		List<TradeSupplySearchDto> list = solrService.queryHotSupplys(categoryCode, keyWords, size);
//		map.put("list", list);
//		return printJson(map, out);
//	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新热门求购信息列表。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 * @throws ParseException 
	 */
//	@RequestMapping
//	public ModelAndView hotBuy(HttpServletRequest request, Map<String, Object> out, String keyWords, Integer size) throws SolrServerException, ParseException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (size == null) {
//			size = DEFAULT_SIZE;
//		}
//		List<TradeBuySearchDto> list = solrService.queryHotBuys(keyWords, size);
//		map.put("list", list);
//		return printJson(map, out);
//	}
	
	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新热门求购信息关键字。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
    @RequestMapping
    public ModelAndView topKeyWordsBuys(HttpServletRequest request,Map<String, Object> out){
    	Map<String, String> map = ParamUtils.getInstance().getChild(BUY_INDEX_TOP10);
		return printJson(map, out);
    }

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取子网推荐产品信息
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-06-27　　　齐振杰　　　　　　　1.0.0　　　　　创建类文件
	 */
    @RequestMapping
    public ModelAndView subRecommendSupply(HttpServletRequest request,Map<String, Object> out,String cate,Integer size){
    	List<TradeSupply> list=subnetTradeRecommendService.querySupplyBySubRec(cate, size);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("list", list);
    	return printJson(map, out);
    }

	/**
	 * 项目名称：中国环保网
	 * 模块编号：业务控制层
	 * 模块描述：获取最新热门求购信息关键字。
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
	 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
	 * @throws SolrServerException 
	 */
    @RequestMapping
    public ModelAndView ibdCategory(HttpServletRequest request,Map<String, Object> out, String categoryCode){
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<IbdCategory> list = ibdCategoryService.queryCategoryByParentCode(categoryCode);
    	//如果是终端企业名录的话，查询相应类别对应的企业数
    	if (IBD_DEFAULT_CATEGORY.equals(categoryCode)) {
			for (IbdCategory idb:list) {
				idb.setIsShow(ibdCompanyService.queryCountByCategoryCode(idb.getCode()));
			}
		}
    	map.put("list", list);
		return printJson(map, out);
    }
}
