/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-6
 */
package com.ast.ast1949.front.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.front.util.FrontConst;
import com.zz91.util.velocity.AddressTool;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class CompanyPriceController extends BaseController {

	/**
	 * 
	 * @param pager
	 * @param categoryCompanyPriceCode
	 * @param keywords
	 * @param areaCode
	 * @param postInDays
	 * @param priceRange
	 * @param out
	 * @return 
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView index(/* String categoryCode, */
	String categoryCompanyPriceCode, String keywords, String areaCode,
			String postInDays, String priceRange, Map<String, Object> out)
			throws ParseException, UnsupportedEncodingException {
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/companyprice/index.htm");
//		CompanyPriceDTO companyPriceDTO = new CompanyPriceDTO();
//		keywords = URLDecoder.decode(keywords,"utf-8");
//		// 报价查询关键字
//		if (StringUtils.isNotEmpty(keywords))
//			companyPriceDTO.setKeywords(keywords);
//		out.put("keywords", keywords);
//		// 省市code 查询
//		companyPriceDTO.setAreaCode(areaCode);
//		out.put("areaCode", areaCode);
//		// 报价范围
//		out.put("priceRange", priceRange);
//		if (StringUtils.isNotEmpty(priceRange) && "-".indexOf(priceRange) != -1) {
//			String[] prices = priceRange.split("astopckk");
//			if (StringUtils.isNumber(prices[0])) {
//				companyPriceDTO.setPricefrom(Float.parseFloat(prices[0]));
//				out.put("pricefrom", prices[0]);
//			}
//			if (prices.length == 2 && StringUtils.isNumber(prices[1])) {
//				companyPriceDTO.setPriceto(Float.parseFloat(prices[1]));
//				out.put("priceto", prices[1]);
//			}
//		}
//		// 时间范围
//		out.put("postInDays", postInDays);
//		if (StringUtils.isNumber(postInDays)) {
//			companyPriceDTO.setRefreshTime(DateUtil.toString(
//					new Date(DateUtil.getTheDayZero(new Date(),
//							Integer.valueOf(postInDays))), "yyyy-MM-dd"));
//		}
//		// 报价类别
//		out.put("categoryCompanyPriceCode", categoryCompanyPriceCode);// k
//		companyPriceDTO.setCategoryCompanyPriceCode(categoryCompanyPriceCode);
//		// 企业报价列表
//		pager = companyPriceService.queryCompanyPricePagiationList(
//				companyPriceDTO, pager);
//		out.put("companyPriceList", pager.getRecords());
//		out.put("pager", pager);
//		// 当前报价分类信息
//		CategoryCompanyPriceDO categoryCompanyPriceDO = categoryCompanyPriceService
//				.queryByCode(categoryCompanyPriceCode);
//		out.put("categoryCompanyPrice", categoryCompanyPriceDO);
//		// 相关类别(下一级子类别)
//		List<CategoryCompanyPriceDO> categoryList = categoryCompanyPriceService
//				.queryCategoryCompanyPriceByCode(categoryCompanyPriceCode);
//		out.put("categoryList", categoryList);
//		// 查询企业报价前N条（发布报价量倒排）
//		Integer size = 6;
//		List<CompanyPriceDO> cList = companyPriceService
//				.queryCompanyPriceByCompanyIdCount(size);
//		out.put("cList", cList);
//		// 分不清楚
//		String categoryProductsMainCode = "";
//		if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
//				&& categoryCompanyPriceCode.startsWith("1000")) {
//			// 1000 塑料企业报价 1001', '废塑料'
//			categoryProductsMainCode = "1001";
//		}
//		if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
//				&& categoryCompanyPriceCode.startsWith("1001")) {
//			// 1001 废金属报价 1000', '废金属'
//			categoryProductsMainCode = "1000";
//		}
//		if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
//				&& categoryCompanyPriceCode.startsWith("1002")) {
//			// 1002 废纸报价 1004', '废纸'
//			categoryProductsMainCode = "1004";
//		}
//		// if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
//		// && categoryCompanyPriceCode.startsWith("1000")) {
//		// // 1003 综合废料报价 所有
//		// categoryProductsMainCode = "1001";
//		// }
//		// 交易链接(供求信息)
//		out.put("productsList", productsService.queryNewestProducts(categoryProductsMainCode, null, 14));
//		
//		out.put("suffixUrl",
//				getSearchUrlByCondition(keywords, categoryCompanyPriceCode,
//						areaCode, postInDays, priceRange));
//		// 行情综述
//		List<NewsForFrontDTO> newsList = new ArrayList<NewsForFrontDTO>();
//		newsList = newsService.queryNewsListForFront(13, null, null, null, 10,
//				null, null, false);
//		out.put("bList", newsList);
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.COMPANY_PRICE);
//		headDTO.setPageTitle("企业报价|企业报价_${site_name}");
//		headDTO.setPageKeywords("废料企业报价,网上废料价格,zz91网上价格,废金属报价,废塑料报价,综合废料报价");
//		headDTO.setPageDescription("${site_name}提供的各类废料企业报价涵盖了废塑料行业所有产品的最新报价，其中还包括了具体的产品价格、名称、所在地区和企业名称，为废料生意提供有力的数据参考");
//		setSiteInfo(headDTO, out);
//		out.put("stringUtils", new StringUtils());
	}

//	private String getSearchUrlByCondition(String keywords,
//			String categoryCompanyPriceCode, String areaCode,
//			String postInDays, String priceRange) {
//		StringBuilder urlBuf = new StringBuilder();
//		urlBuf.append("--");
//		if (StringUtils.isNotEmpty(keywords))
//			urlBuf.append(EscapeUnescape.escape(keywords));
//		urlBuf.append("--pc");
//		if (StringUtils.isNotEmpty(categoryCompanyPriceCode))
//			urlBuf.append(categoryCompanyPriceCode);
//		urlBuf.append("--area");
//		if (StringUtils.isNotEmpty(areaCode))
//			urlBuf.append(areaCode);
//		urlBuf.append("--int");
//		if (StringUtils.isNumber(postInDays))
//			urlBuf.append(postInDays);
//		urlBuf.append("--pr");
//		if (StringUtils.isNotEmpty(priceRange))
//			urlBuf.append(priceRange);
//		return urlBuf.toString();
//	}

	@RequestMapping
	public ModelAndView priceDetails(Integer id, Integer productId,
			String categoryCompanyPriceCode, HttpServletRequest request,
			Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/companyprice/priceDetails"+id+".htm");
//		CompanyPriceDO companyPriceDO = null;
		// id,productId,在ID没有值时，取productId查询
//		if (id != null)
//			// 该产品企业报价
//			companyPriceDO = companyPriceService.queryCompanyPriceById(id);
//		if (companyPriceDO == null && productId != null)
//			companyPriceDO = companyPriceService
//					.queryCompanyPriceByProductId(productId);
//
//		if (companyPriceDO == null)
//			return new ModelAndView(new RedirectView("index.htm"));
//		out.put("companyPriceDO", companyPriceDO);
//		out.put("isLimitDisplay", false);
//		// 7*24小时内的信息并且没有登录,限制查看
//		if ((companyPriceDO.getGmtCreated().getTime() + 7 * 24 * 60 * 60 * 1000) > new Date()
//				.getTime() && getCachedUser(request) == null) {
//			out.put("isLimitDisplay", true);
//		}
//		if (StringUtils.isEmpty(categoryCompanyPriceCode)) {
//			categoryCompanyPriceCode = companyPriceDO
//					.getCategoryCompanyPriceCode();
//		}
//		// 报价公司信息
//		Company company=companyService.querySimpleCompanyById(companyPriceDO.getCompanyId());
//		
//		CompanyAccount companyAccount = companyAccountService.queryAdminAccountByCompanyId(companyPriceDO.getCompanyId());
//		
////		CompanyContactsDO companyAccount = companyService
////				.selectDefaultContactsById(companyPriceDO.getCompanyId());
//		out.put("companyContactsDO", companyAccount);
//		if (company != null) {
//			ParseAreaCode parse = new ParseAreaCode();
//			parse.parseAreaCode(company.getAreaCode());
//			out.put("cityName", parse.getCity());
//			out.put("provinceName", parse.getProvince());
//		}
//		String productCategoryCode = null;
//		if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
//				&& categoryCompanyPriceCode.length() >= 4) {
//			// 最热标签
//			String tagsCode = categoryCompanyPriceCode.substring(0, 4);
//			List<TagsInfoDO> mostList = tagsArticleService
//					.queryTagListByTagCatAndArtCat("4", tagsCode, 30);
//			out.put("mostList", mostList);
//			categoryCompanyPriceCode = categoryCompanyPriceCode.substring(0, 4);
//			// 产品报价
//			if (categoryCompanyPriceCode.equals("1000")) {
//				categoryCompanyPriceCode = "10001000";
//			}
//			List<CategoryCompanyPriceDO> categoryList = categoryCompanyPriceService
//					.queryCategoryCompanyPriceByCode(categoryCompanyPriceCode);
//			out.put("categoryList", categoryList);
//			// 左侧报价,右侧报价资讯
//			newsPrice(categoryCompanyPriceCode, out);
//			// 查询企业报价所属类别
//			CategoryCompanyPriceDO categoryCompanyPriceDO = categoryCompanyPriceService
//					.queryByCode(categoryCompanyPriceCode);
//			out.put("categoryCompanyPrice", categoryCompanyPriceDO);
//
//			if (categoryCompanyPriceDO != null) {
//				String categoryName = categoryCompanyPriceDO.getLabel();
//				if (categoryName != null) {
//					if (categoryName.equals("废金属")) {
//						productCategoryCode = "1000";
//					} else if (categoryName.equals("废塑料")) {
//						productCategoryCode = "1001";
//					} else if (categoryName.equals("废纸")) {
//						productCategoryCode = "1004";
//					} else {
//						productCategoryCode = null;
//					}
//				}
//			}
//		}
//		// 该产品交易链接
//		out.put("productsList", productsService.queryNewestProducts(productCategoryCode, null, 14));
//
//		out.put("categoryCompanyPriceCode", categoryCompanyPriceCode);
//		// 查询该公司的其他企业报价
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("id", id);
//		param.put("companyId", companyPriceDO.getCompanyId());
//		List<CompanyPriceDO> otherPrice = companyPriceService
//				.queryCompanyPriceByCompanyId(param);
//		out.put("otherPrice", otherPrice);
//
//		out.put("openStatus",
//				ParamUtils.getInstance().getValue("baseConfig",
//						"open_status"));
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.COMPANY_PRICE);
//		headDTO.setPageTitle("企业报价_产品价格_${site_name}");
//		headDTO.setPageKeywords("企业报价,网上报价,产品价格");
//		headDTO.setPageDescription("${site_name}提供的再生资源企业报价涵盖了再生资源行业所有产品的最新报价，"
//				+ "其中还包括了具体的产品价格、名称、所在地区和企业名称，为再生资源生意提供有力的数据参考。");
//		setSiteInfo(headDTO, out);
//		out.put("stringUtils", new StringUtils());
//		return null;
	}

	@RequestMapping
	public ModelAndView login(String categoryCompanyPriceCode, Integer id,
			Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/companyprice/login.htm");
//		setSiteInfo(new PageHeadDTO(), out);
//		out.put("openStatus",
//				ParamUtils.getInstance().getValue("baseConfig",
//						"open_status"));
//		out.put("categoryCompanyPriceCode", categoryCompanyPriceCode);
//		out.put("id", id);
	}

	@RequestMapping
	public ModelAndView postPrice(HttpServletRequest request) {
		Object c = getCachedSession(request, FrontConst.SESSION_USER);
		if (c != null) {
			return new ModelAndView("redirect:"+AddressTool.getAddress("myrc")+
					"/myproducts/office_post2.htm");

		} else {
			return new ModelAndView("redirect:"+AddressTool.getAddress("front")+"/login.htm");

		}
	}


}
