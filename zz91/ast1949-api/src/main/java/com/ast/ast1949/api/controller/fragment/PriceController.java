/**
 * 
 */
package com.ast.ast1949.api.controller.fragment;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.api.controller.BaseController;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.price.ForPriceDTO;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.dto.price.PriceCategoryMinDto;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.information.ChartCategoryService;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.velocity.AddressTool;

/**
 * @author root
 * 
 */
@Controller
public class PriceController extends BaseController {
	@Resource
	private PriceService priceService;
	@Resource
	private ChartCategoryService chartCategoryService;
	@Resource
	private PriceCategoryService priceCategoryService;
	@Resource
	private CompanyPriceService companyPriceService;

	final static int MAX_SIZE = 100;
	final static String RELATED_PRICE = "tradedetail_price";
	final static String PYAPP_URL = "http://pyapp.zz91.com";

	/*
	 * 周评日评 param:parentId:10,12,14
	 */
	@RequestMapping
	public ModelAndView comment(HttpServletRequest request,
			Map<String, Object> out, Integer parentId, Integer assistId,
			Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (parentId != null) {
			if (size == null) {
				size = 10;
			}
			if (size.intValue() > 100) {
				size = MAX_SIZE;
			}
			List<PriceCategoryDTO> commentList = priceService
					.queryPriceCategoryInfoByParentIdAndAssistId(parentId,
							assistId, size);
			map.put("commentList", commentList);
		}

		return printJson(map, out);
	}

	/*
	 * 废金属，废塑料市场动态,塑料再生料 param:parentId:19,22
	 */
	@RequestMapping
	public ModelAndView metalPlatic(HttpServletRequest request,
			Map<String, Object> out, Integer parentId) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (parentId != null) {
			List<ForPriceDTO> list = priceService
					.queryEachPriceByParentId(parentId);
			map.put("list", list);
		}
		return printJson(map, out);
	}

	/*
	 * 废纸|橡胶市场动态，行情综述,行情导读 param:typeId:23,216,217,218
	 */
	@RequestMapping
	public ModelAndView paparPriceReview(HttpServletRequest request,
			Map<String, Object> out, Integer typeId, Integer parentId,
			Integer assistTypeId, Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = 7;
		}
		if (size.intValue() > 100) {
			size = MAX_SIZE;
		}
		List<PriceDO> list = priceService.queryPriceByTypeId(typeId, parentId,
				assistTypeId, size);
		map.put("list", list);
		return printJson(map, out);
	}

	/*
	 * 网上报价,底部最新报价
	 */
	@RequestMapping
	public ModelAndView onlinePrice(HttpServletRequest request,
			Map<String, Object> out, Integer typeId, Integer size)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = 7;
		}
		if (size.intValue() > 100) {
			size = MAX_SIZE;
		}
		List<ForPriceDTO> onlinePrice = priceService
				.queryPriceAndCategoryByTypeId(typeId, size);
		map.put("onlinePrice", onlinePrice);
		return printJson(map, out);
	}

	/*
	 * 各地报价
	 */
	@RequestMapping
	public ModelAndView overPrice(HttpServletRequest request,
			Map<String, Object> out, Integer parentId, Integer size)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = 7;
		}
		if (size.intValue() > 100) {
			size = MAX_SIZE;
		}
		List<ForPriceDTO> overPrice = priceService.queryPriceByParentId(
				parentId, size);
		map.put("overPrice", overPrice);
		return printJson(map, out);
	}

	/*
	 * 首页，废金属频道页走势图 id:0
	 */
	@RequestMapping
	public ModelAndView chart(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ChartCategoryDO> chartCategoryList = chartCategoryService
				.queryChartCategoryByParentId(id);
		map.put("chartCategoryList", chartCategoryList);
		return printJson(map, out);
	}

	/*
	 * 首页，右侧类别
	 */
	@RequestMapping
	public ModelAndView priceCategory(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PriceCategoryDO> list = priceCategoryService
				.queryPriceCategoryByParentId(id);
		map.put("list", list);
		return printJson(map, out);
	}

	/*
	 * 最新废金属，废塑料报价 china.zz91.com
	 */
	@RequestMapping
	public ModelAndView latestPrice(HttpServletRequest request,
			Map<String, Object> out, Integer parentId) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PriceCategoryMinDto> latestList = priceCategoryService.queryPriceCategoryByParentIdOrderList(parentId);
		map.put("latestList", latestList);
		return printJson(map, out);
	}

	/*
	 * 按刷新时间显示企业报价
	 * 
	 * @param out
	 * 
	 * @param title 报价标题
	 * 
	 * @param size 显示条数
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView priceByRefresh(Map<String, Object> out,
			HttpServletRequest request, Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String title = "";
		if (StringUtils.isEmpty(request.getParameter("title"))
				&& request.getParameter("title") == null) {
			title = null;
		} else {
			title = new String(request.getParameter("title").getBytes(
					"ISO-8859-1"), "UTF-8");
		}
		if (size > 20) {
			size = 20;
		}
		List<CompanyPriceDO> companyPriceList = companyPriceService
				.queryCompanyPriceByRefreshTime(title, size);
		map.put("list", companyPriceList);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView newHotPrice(Map<String, Object> out, String code,
			Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = 10;
		}
		if (size > 50) {
			size = 50;
		}
		List<ForPriceDTO> list = priceService.queryPriceByIndex(code, size);
		map.put("list", list);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView newestCompPrice(Map<String, Object> out, String code,
			Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = 10;
		}
		if (size > 50) {
			size = 50;
		}
		if (code != null && code.length() > 4) {
			code = code.substring(0, 4);
		}
		List<CompanyPriceDO> list = companyPriceService
				.queryNewestVipCompPrice(code, size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 搜索最新报价资讯 
	 *  使用于myrc首页最新报价获取数据
	 * @param out
	 * @param code
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryLatestPrice(Map<String, Object> out,String code,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size==null) {
			size=10;
		}
		if (size>50) {
			size=50;
		}
		List<ForPriceDTO> list=priceService.queryLatestPrice(code, size);
		map.put("list",list);
		return printJson(map, out);
	}
	
	@Deprecated
	@RequestMapping
	public ModelAndView priceCompany(Map<String, Object> out, 
			Integer size,PageDto<CompanyPriceSearchDTO> pager,String areaCode) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null) {
			size = 10;
		}
		if (size > 50) {
			size = 50;
		}
		CompanyPriceSearchDTO searchDTO=new CompanyPriceSearchDTO();
		searchDTO.setAreaCode(areaCode);
		pager = companyPriceService.pageCompanyPriceSearch(searchDTO, pager);
		map.put("pager", pager);
		return printJson(map, out);
	}

	/**
	 * 在trade 的产品详细页面的 相关报价查询方法
	 * 
	 * @param out
	 * @param keyword
	 *            关键字
	 * @param num
	 *            读取的记录条数
	 * @param len
	 *            输出的长度
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView queryRelatedPriceList(Map<String, Object> out,
			String keywords, Integer listnum, Integer wordslen) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (listnum == null) {
			listnum = 10;
		}
		if (listnum > 50) {
			listnum = 10;
		}
		if(com.zz91.util.lang.StringUtils.isNotEmpty(keywords)){
			keywords = URLEncoder.encode(StringUtils.decryptUrlParameter(keywords), HttpUtils.CHARSET_UTF8);
		}else{
			keywords = "";
		}
		String address = AddressTool.getAddress("pyapp");
		if(com.zz91.util.lang.StringUtils.isEmpty(address)){
			address = PYAPP_URL;
		}
		String url = address + "/" + RELATED_PRICE + "/?keywords=" + keywords + "&listnum=" + listnum + "&wordslen=" + wordslen;
		String relatedPriceList = HttpUtils.getInstance().httpGet(url,HttpUtils.CHARSET_UTF8);
		JSONArray list = JSONArray.fromObject(relatedPriceList);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 根据类别搜索报价 
	 * @param out
	 * @param parentId
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView queryPriceByTypeId(Map<String, Object> out,Integer typeId,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", priceService.queryListByTypeId(typeId,size));
		return printJson(map, out);
	}
	
}
