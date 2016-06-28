package com.ast.ast1949.price.controller.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.price.ForPriceDTO;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.dto.price.PriceCategoryMinDto;
import com.ast.ast1949.price.controller.BaseController;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.information.ChartCategoryService;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.util.StringUtils;

@Controller
public class PriceFragmentController extends BaseController {
	@Resource
	private PriceService priceService;
	@Resource
	private ChartCategoryService chartCategoryService;
	@Resource
	private PriceCategoryService priceCategoryService;
	@Resource
	private CompanyPriceService companyPriceService;
	
	final static int MAX_SIZE=100;
	
	/*
	 * 周评日评
	 * param:parentId:10,12,14
	 */
	@RequestMapping
	public ModelAndView comment(HttpServletRequest request,Map<String, Object> out,Integer parentId,
			Integer assistId, Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(parentId != null) {
			if(size == null) {
				size = 10;
			}
			if(size.intValue()>100){
				size=MAX_SIZE;
			}
			List<PriceCategoryDTO> commentList = priceService.queryPriceCategoryInfoByParentIdAndAssistId(parentId, assistId, size);
			map.put("commentList", commentList);
		}
		
		return printJson(map, out);
	}
	
	/*
	 * 废金属，废塑料市场动态,塑料再生料
	 * param:parentId:19,22
	 */
	@RequestMapping
	public ModelAndView metalPlatic(HttpServletRequest request,Map<String, Object> out,Integer parentId) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(parentId != null) {
			List<ForPriceDTO> list = priceService.queryEachPriceByParentId(parentId);
			map.put("list", list);
		}
		return printJson(map, out);
	}
	
	/*
	 * 废纸|橡胶市场动态，行情综述,行情导读
	 * param:typeId:23,216,217,218
	 */
	@RequestMapping
	public ModelAndView paparPriceReview(HttpServletRequest request,Map<String, Object> out,Integer typeId, 
			Integer parentId, Integer assistTypeId, Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(size == null) {
			size = 7;
		}
		if(size.intValue()>100){
			size=MAX_SIZE;
		}
		List<PriceDO> list = priceService.queryPriceByTypeId(typeId, parentId, assistTypeId, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/*
	 *  网上报价,底部最新报价
	 */
	@RequestMapping
	public ModelAndView onlinePrice(HttpServletRequest request,Map<String, Object> out,Integer typeId, 
			Integer size)throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(size == null) {
			size = 7;
		}
		if(size.intValue()>100){
			size=MAX_SIZE;
		}
		List<ForPriceDTO> onlinePrice = priceService.queryPriceAndCategoryByTypeId(typeId, size);
		map.put("onlinePrice", onlinePrice);
		return printJson(map, out);
	}
	
	/*
	 * 各地报价
	 */
	@RequestMapping
	public ModelAndView overPrice(HttpServletRequest request,Map<String, Object> out,Integer parentId, 
			Integer size)throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(size == null) {
			size = 7;
		}
		if(size.intValue()>100){
			size=MAX_SIZE;
		}
		List<ForPriceDTO> overPrice = priceService.queryPriceByParentId(parentId, size);
		map.put("overPrice", overPrice);
		return printJson(map, out);
	}
	
	/*
	 * 首页，废金属频道页走势图 id:0
	 */
	@RequestMapping
	public ModelAndView chart(HttpServletRequest request,Map<String, Object> out,Integer id) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ChartCategoryDO> chartCategoryList = chartCategoryService.queryChartCategoryByParentId(id);
		map.put("chartCategoryList", chartCategoryList);
		return printJson(map, out);
	}
	
	/*
	 * 首页，右侧类别
	 */
	@RequestMapping
	public ModelAndView priceCategory(HttpServletRequest request,Map<String, Object> out,Integer id) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PriceCategoryDO> list = priceCategoryService.queryPriceCategoryByParentId(id);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/*
	 * 最新废金属，废塑料报价 china.zz91.com
	 */
	@RequestMapping
	public ModelAndView latestPrice(HttpServletRequest request,Map<String, Object> out,Integer parentId) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PriceCategoryMinDto> latestList = priceCategoryService.queryPriceCategoryByParentIdOrderList(parentId);
		map.put("latestList", latestList);
		return printJson(map, out);
	}
	/*
	 * 按刷新时间显示企业报价
	 * @param out
	 * @param title 报价标题
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView priceByRefresh(Map<String, Object> out,HttpServletRequest request,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		String title="";
		if (StringUtils.isEmpty(request.getParameter("title")) &&request.getParameter("title")==null){
			title=null;
		}
		else {
			title=new String(request.getParameter("title").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (size>20){
			size=20;
		}
		List<CompanyPriceDO> companyPriceList = companyPriceService.queryCompanyPriceByRefreshTime(title,size);
		map.put("list", companyPriceList);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView newHotPrice(Map<String, Object> out,String code,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size==null) {
			size=10;
		}
		if (size>50) {
			size=50;
		}
		List<ForPriceDTO> list=priceService.queryPriceByIndex(code, size);
		map.put("list",list);
		return printJson(map, out);
	}
}
