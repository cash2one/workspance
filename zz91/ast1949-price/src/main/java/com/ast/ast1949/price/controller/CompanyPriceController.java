/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-6
 */
package com.ast.ast1949.price.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.information.ChartDataForIndexDTO;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.ParseAreaCode;
import com.ast.ast1949.service.information.ChartDataService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.tags.TagsArticleService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.CNToHexUtil;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.PageCacheUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class CompanyPriceController extends BaseController {

	@Autowired
	private CompanyPriceService companyPriceService;
	@Autowired
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private CompanyService companyService;
	@Resource
	private TagsArticleService tagsArticleService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private ChartDataService  chartDataService;
	/**
	 * 
	 * @param pager
	 * @param categoryCompanyPriceCode
	 * @param keywords
	 * @param areaCode
	 * @param postInDays
	 * @param priceRange
	 * @param out
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView list(/* String categoryCode, */ 
			Map<String, Object> out, HttpServletResponse response,HttpServletRequest request)
			throws ParseException, UnsupportedEncodingException {
		
		PageDto<CompanyPriceSearchDTO> pager=new PageDto<CompanyPriceSearchDTO>();
		String  startIndexString =request.getParameter("startIndex");
		
		if(StringUtils.isNotEmpty(startIndexString)){
		 Integer startIndex= Integer.valueOf(startIndexString);
		 pager.setStartIndex(startIndex);
		}
		
		String  pageSizeString =request.getParameter("pageSize");
	    
		if(StringUtils.isNotEmpty(pageSizeString)){
			Integer pageSize= Integer.valueOf(pageSizeString);
		      pager.setPageSize(pageSize);
		}
		CompanyPriceSearchDTO dto = new CompanyPriceSearchDTO();
		String result=request.getParameter("result");
		String type=request.getParameter("type");
		String categoryCompanyPriceCode=request.getParameter("categoryCompanyPriceCode");
		String keywords=request.getParameter("keywords");
		String areaCode=request.getParameter("areaCode");
		String postInDays=request.getParameter("postInDays");
		String priceRange=request.getParameter("priceRange");
		String clickTimeString=request.getParameter("clickTime");
		
		if(StringUtils.isNotEmpty(clickTimeString))
		{
		  long clickTime=Long.valueOf(clickTimeString);
		  dto.setSearchDate(clickTime);
		}
		out.put("clickTime",dto.getSearchDate());
		
	    if(keywords!=null){
	    	//包含中文
	    	if(StringUtils.isContainCNChar(keywords)==true){
	    		keywords=URLDecoder.decode(keywords, HttpUtils.CHARSET_UTF8);
	    	}else{
		    	keywords=URLDecoder.decode(keywords, HttpUtils.CHARSET_UTF8);
	    	}
	    	dto.setKeywords(keywords);
	    }  
		if(keywords==null){
			keywords = "";
		}
		
		if(StringUtils.isNotEmpty(keywords) && keywords.startsWith("%")){
			keywords=URLDecoder.decode(keywords,"utf-8");
			dto.setKeywords(keywords);
		}
		// 省市code 查询
		dto.setAreaCode(areaCode);
		out.put("areaCode",areaCode);
		dto.setCategoryCompanyPriceCode(categoryCompanyPriceCode);
		// 报价范围
		out.put("priceRange", priceRange);
		if (StringUtils.isNotEmpty(priceRange) && priceRange.indexOf("-")!=-1) {
			String[] prices = priceRange.split("-");
			if (StringUtils.isNumber(prices[0])) {
				dto.setFromPrice(Integer.valueOf(prices[0]));
				//dto.setPricefrom(Float.parseFloat(prices[0]));
				out.put("pricefrom", prices[0]);
			}
			if (prices.length == 2 && StringUtils.isNumber(prices[1])) {
				dto.setToPrice(Integer.valueOf(prices[1]));
				//dto.setPriceto(Float.parseFloat(prices[1]));
				out.put("priceto", prices[1]);
			}
		}
		
		// 时间范围
		out.put("postInDays", postInDays);
		if (postInDays!=null && StringUtils.isNumber(postInDays.replace("-", ""))) {
			dto.setRefreshTime(DateUtil.getDateAfterDays(new Date(), Integer.valueOf(postInDays)));
		}
		pager=companyPriceService.pageCompanyPriceDtoBySearchEngine(dto, pager);
		out.put("pager", pager);
		//keywords=URLDecoder.decode(keywords, HttpUtils.CHARSET_UTF8);
		out.put("keywords", keywords);
		out.put("encodeKeywords", URLEncoder.encode(keywords, "utf-8"));
		//加密kewords
		String keywordsEncode= URLEncoder.encode(keywords, "utf-8");
		out.put("keywordsEncode", keywordsEncode);
		// 相关类别(下一级子类别)
		out.put("categoryListNav", categoryCompanyPriceService.queryCategoryCompanyPriceByCode(""));
		// 报价类别
		out.put("categoryCompanyPriceCode", categoryCompanyPriceCode);
		
		if(StringUtils.isNotEmpty(categoryCompanyPriceCode)&&categoryCompanyPriceCode.length()>=4){
			out.put("adkeywords", categoryCompanyPriceCode.subSequence(0, 4));
		}
		// 查询企业报价前N条（发布报价量倒排）
		Integer size = 6;
		List<CompanyPriceDO> cList = companyPriceService
				.queryCompanyPriceByCompanyIdCount(size);
		out.put("cList", cList);

		// 默认情况 seo 
		SeoUtil.getInstance().buildSeo("companyPrice",new String[]{"废塑料"},new String[]{"废塑料"},new String[]{"废塑料"}, out);

		
		// 当前报价分类信息
		if(StringUtils.isNotEmpty(categoryCompanyPriceCode)){
			CategoryCompanyPriceDO categoryCompanyPriceDO = categoryCompanyPriceService.queryByCode(categoryCompanyPriceCode);
			out.put("categoryCompanyPrice", categoryCompanyPriceDO);
			out.put("categoryList", categoryCompanyPriceService.queryCategoryCompanyPriceByCode(categoryCompanyPriceCode));
		
			// 根据不同的code 生成不同的 seo 
			String categoryProductsMainCode = "";
			String label = categoryCompanyPriceDO.getLabel();
			do{
				if(keywords!=null&&keywords.length()>0){
					label = keywords;
				}
				if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
						&& categoryCompanyPriceCode.startsWith("1000")) {
					// 1000 塑料企业报价 1001', '废塑料'
					categoryProductsMainCode = "1001";
				}
				if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
						&& categoryCompanyPriceCode.startsWith("1001")) {
					// 1001 废金属报价 1000', '废金属'
					categoryProductsMainCode = "1000";
				}
				if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
						&& categoryCompanyPriceCode.startsWith("1002")) {
					// 1002 废纸报价 1004', '废纸'
					categoryProductsMainCode = "1004";
				}
				if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
						&& categoryCompanyPriceCode.startsWith("1003")) {
					// 1003 综合废料 1004', '废纸'
					categoryProductsMainCode = "1000";
				}
				Integer  cpage = pager.getStartIndex()/pager.getPageSize()+1;
				String currentpage=cpage.toString();
				SeoUtil.getInstance().buildSeo("companyPrice",new String[]{label,currentpage},new String[]{label},new String[]{label}, out);
			}while(false);
			
			// 交易链接(供求信息)
			out.put("productsList", productsService.queryNewestProducts(categoryProductsMainCode, null, 14));
	
			Integer typeId=220;
			String code="paper";
			if("1000".equals(categoryCompanyPriceCode)){
				typeId=217;
				code="plastic";
			}else if("1001".equals(categoryCompanyPriceCode)){
				typeId=216;
				code="metal";
			}
			out.put("typeId", typeId);
			//out.put("priceList", priceService.queryPriceByTypeId(typeId, null, null, 12));
			out.put("code", code);
			PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN*30);
			out.put("keywordsUrl", URLEncoder.encode(label, HttpUtils.CHARSET_UTF8));
			out.put("result", result);
		}
		 return new ModelAndView();
	}

	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletResponse response,HttpServletRequest request){
		 //查询出高会最新的20条
		 Integer size=20;
		 List<CompanyPriceSearchDTO> dtoList=companyPriceService.queryCompanyPriceList(size);
		 out.put("dtoList", dtoList);
		 CompanyPriceSearchDTO dto = new CompanyPriceSearchDTO();
		//查询废塑料的报价
		PageDto<CompanyPriceSearchDTO> plasticPage=new PageDto<CompanyPriceSearchDTO>();
		plasticPage.setPageSize(7);
		dto.setCategoryCompanyPriceCode("1000");
		out.put("plasticList", categoryCompanyPriceService.queryCategoryCompanyPriceByCode("10001000"));
		plasticPage = companyPriceService.pageCompanyPriceSearch(dto, plasticPage);
		out.put("plasticPage", plasticPage);
		out.put("zsplasticList", categoryCompanyPriceService.queryCategoryCompanyPriceByCode("10001001"));
		//查询废金属的报价
		PageDto<CompanyPriceSearchDTO> metalPage=new PageDto<CompanyPriceSearchDTO>();
		metalPage.setPageSize(7);
		dto.setCategoryCompanyPriceCode("1001");
		out.put("metalList", categoryCompanyPriceService.queryCategoryCompanyPriceByCode("1001"));
		metalPage = companyPriceService.pageCompanyPriceSearch(dto, metalPage);
		out.put("metalPage", metalPage);
		//查询综合废料报价
		PageDto<CompanyPriceSearchDTO> otherWasteDtoPage=new PageDto<CompanyPriceSearchDTO>();
		otherWasteDtoPage.setPageSize(7);
		dto.setCategoryCompanyPriceCode("1003");
		out.put("otherWasteList", categoryCompanyPriceService.queryCategoryCompanyPriceByCode("1003"));
		otherWasteDtoPage = companyPriceService.pageCompanyPriceSearch(dto, otherWasteDtoPage);
		out.put("otherWasteDtoPage", otherWasteDtoPage);
		//废纸报价
		PageDto<CompanyPriceSearchDTO> wastePaperPage=new PageDto<CompanyPriceSearchDTO>();
		wastePaperPage.setPageSize(7);
		dto.setCategoryCompanyPriceCode("1002");
		out.put("wasteList", categoryCompanyPriceService.queryCategoryCompanyPriceByCode("1002"));
		wastePaperPage = companyPriceService.pageCompanyPriceSearch(dto, wastePaperPage);
		out.put("wastePaperPage", wastePaperPage);
		
		// 查询企业报价前N条（发布报价量倒排）
		Integer priceSize = 8;
		List<CompanyPriceDO> cList = companyPriceService
				.queryCompanyPriceByCompanyIdCount(priceSize);
		out.put("cList", cList);
		
		//热门供应列表
		out.put("gyProductList", productsService.queryProductsWithPicAndTypeCode("10331000", 8));
		//热门列表
		out.put("qgProductList", productsService.queryProductsWithPicAndTypeCode("10331001", 8));
		
		//期货价格
		List<ChartDataForIndexDTO> charts = chartDataService.queryChartDataForIndex(7);
		out.put("charts", charts);
		SeoUtil.getInstance().buildSeo("companyPrice",new String[]{"废塑料"},new String[]{"废塑料"},new String[]{"废塑料"}, out);
		return new ModelAndView();
		
	}
	
	
	
	
	
//	private String getSearchUrlByCondition(String keywords,
//			String categoryCompanyPriceCode, String areaCode,
//			String postInDays, String priceRange) {
//		StringBuilder urlBuf = new StringBuilder();
//		urlBuf.append("--");
//		if (StringUtils.isNotEmpty(keywords)){
//			try {
//				urlBuf.append(URLEncoder.encode(keywords, "utf-8"));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
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
			String categoryCompanyPriceCode, 
			HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> out) {
 		CompanyPriceDO companyPriceDO = null;
 		String pindao="";
		// id,productId,在ID没有值时，取productId查询
		if (id != null){
			// 该产品企业报价
			companyPriceDO = companyPriceService.queryCompanyPriceById(id);
		}
		if (companyPriceDO == null && productId != null){
			companyPriceDO = companyPriceService.queryCompanyPriceByProductId(productId,null);
		}
		if (companyPriceDO == null){
			
			return new ModelAndView(new RedirectView("index.htm?result=1"));
		}
		
		
		// 验证是否为黑名单用户
		if (companyService.validateIsBlack(companyPriceDO.getCompanyId())){
			return new ModelAndView("index");
		}
		// 报价公司信息
		Company company = companyService.querySimpleCompanyById(companyPriceDO.getCompanyId());
		
		// 判断信息是否被审核（高会直接通过）
		if ("10051000".equals(company.getMembershipCode())) {
		    if ("0".equals(companyPriceDO.getIsChecked())) {
	            out.put(AstConst.ERROR_TEXT, "您查看的报价信息还没有被审核，请耐心等待！");
	            return new ModelAndView("/common/error");
	        }
		}
        
		out.put("companyPriceDO", companyPriceDO);
		try {
			if(companyPriceDO.getTitle()!=null){
				companyPriceDO.setTitle(companyPriceDO.getTitle().replace("-", "").replace("_", ""));
			}
			out.put("encodeTitle", URLEncoder.encode(companyPriceDO.getTitle(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		out.put("isLimitDisplay", 0);
		// 7*24小时内的信息并且没有登录,限制查看
		SsoUser user=getCachedUser(request);
		if (companyPriceDO.getRefreshTime()!=null&&(companyPriceDO.getRefreshTime().getTime() + 7 * 24 * 60 * 60 * 1000) > new Date().getTime() && user==null) {
			out.put("isLimitDisplay", "1");
		}
		
		if (StringUtils.isEmpty(categoryCompanyPriceCode)) {
			categoryCompanyPriceCode = companyPriceDO
					.getCategoryCompanyPriceCode();
		}
//		CompanyContactsDO companyAccount = companyService
//				.selectDefaultContactsById(companyPriceDO.getCompanyId());
		if (company != null) {
			ParseAreaCode parse = new ParseAreaCode();
			parse.parseAreaCode(company.getAreaCode());
			out.put("cityName", parse.getCity());
			out.put("provinceName", parse.getProvince());
			out.put("company", company);
			if(!"10051000".equals(company.getMembershipCode())){
				CompanyAccount companyAccount = companyAccountService.queryAdminAccountByCompanyId(companyPriceDO.getCompanyId());
				out.put("companyContactsDO", companyAccount);
			}
		}
		String productCategoryCode = null;
		if (StringUtils.isNotEmpty(categoryCompanyPriceCode)
				&& categoryCompanyPriceCode.length() >= 4) {
			// 最热标签
			String tagsCode = categoryCompanyPriceCode.substring(0, 4);
			List<TagsInfoDO> mostList = tagsArticleService
					.queryTagListByTagCatAndArtCat("4", tagsCode, 30);
			out.put("mostList", mostList);
			Map<Integer, String> encodeTags = new HashMap<Integer, String>();
			for(TagsInfoDO tags:mostList){
				encodeTags.put(tags.getId(), CNToHexUtil.getInstance().encode(tags.getName()));
			}
			out.put("mostListEncode", encodeTags);
			
			categoryCompanyPriceCode = categoryCompanyPriceCode.substring(0, 4);
			// 产品报价
			if (categoryCompanyPriceCode.equals("1000")) {
				categoryCompanyPriceCode = "10001000";
			}
			List<CategoryCompanyPriceDO> categoryList = categoryCompanyPriceService
					.queryCategoryCompanyPriceByCode(categoryCompanyPriceCode);
			out.put("categoryList", categoryList);
			// 左侧报价,右侧报价资讯
			newsPrice(categoryCompanyPriceCode, out);
			// 查询企业报价所属类别
			CategoryCompanyPriceDO categoryCompanyPriceDO = categoryCompanyPriceService
					.queryByCode(categoryCompanyPriceCode);
			out.put("categoryCompanyPrice", categoryCompanyPriceDO);
			
			if (categoryCompanyPriceDO != null) {
				String categoryName = categoryCompanyPriceDO.getLabel();
				if (categoryName != null) {
					if (categoryName.equals("废金属")) {
						productCategoryCode = "1000";
					} else if (categoryName.equals("废塑料")) {
						productCategoryCode = "1001";
					} else if (categoryName.equals("废纸")) {
						productCategoryCode = "1004";
					} else {
						productCategoryCode = null;
					}
				}
			}
			
			// 行情综述
			Integer typeId=220;
			String code="paper";
			
			if("1000".equals(tagsCode)){
				typeId=217;
				code="plastic";
				pindao="废塑料";
			}else if("1001".equals(tagsCode)){
				typeId=216;
				code="metal";
				pindao="废金属";
			}
			else {
				pindao="综合废料";
			}
			out.put("code", code);
			out.put("typeId", typeId);
			//out.put("priceList", priceService.queryPriceByTypeId(typeId, null, null, 8));
		}
		// 该产品交易链接
		out.put("productsList", productsService.queryNewestProducts(productCategoryCode, null, 14));

		out.put("categoryCompanyPriceCode", categoryCompanyPriceCode);
		// 查询该公司的其他企业报价
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("companyId", companyPriceDO.getCompanyId());
		List<CompanyPriceDO> otherPrice = companyPriceService
				.queryCompanyPriceByCompanyId(param);
		out.put("otherPrice", otherPrice);

		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.COMPANY_PRICE);
//		headDTO.setPageTitle(companyPriceDO.getTitle()+"价格_"+pindao+"价格_"+pindao+"价格_企业报价_${site_name}");
//		headDTO.setPageKeywords("企业报价,"+pindao+"价格,"+pindao+"行情,今日"+pindao+"价格,"+companyPriceDO.getTitle()+","+companyPriceDO.getTitle()+"价格");
//		headDTO.setPageDescription("${site_name}ZZ91再生网报价中心"+companyPriceDO.getTitle()+"企业报价，云集了国内外最新"+companyPriceDO.getTitle()
//				+"企业价格信息。涵盖了"+companyPriceDO.getTitle()+"企业，让您掌握最新的"+companyPriceDO.getTitle()+"企业的价格行情动态!");
//		setSiteInfo(headDTO, out);
		
		SeoUtil.getInstance().buildSeo("companypriceDetails", 
				new String[]{companyPriceDO.getTitle(),pindao}, 
				new String[]{companyPriceDO.getTitle(),pindao}, 
				new String[]{companyPriceDO.getTitle()}, out);
		
		if(companyPriceDO.getRefreshTime()!=null&&(companyPriceDO.getRefreshTime().getTime() + 7 * 24 * 60 * 60 * 1000) > new Date().getTime()){
			PageCacheUtil.setNoCDNCache(response);
		}
		
		return new ModelAndView();
	}

	@RequestMapping
	public void login(String categoryCompanyPriceCode, Integer id,
			Map<String, Object> out) {
		setSiteInfo(new PageHeadDTO(), out);
		out.put("openStatus",
				ParamUtils.getInstance().getValue("baseConfig",
						"open_status"));
		out.put("categoryCompanyPriceCode", categoryCompanyPriceCode);
		out.put("id", id);
	}

	// 报价行情综述
	private void newsPrice(String categoryCompanyPriceCode,
			Map<String, Object> out) {

		Integer typeId=51;
		Integer parentId=9;
		String code = "";
		if (categoryCompanyPriceCode.substring(0, 4).equals("1001")) {
			// 废金属报价，网上报价
			parentId=17;
			typeId=51;
			code = "metal";
		} else if (categoryCompanyPriceCode.substring(0, 4).equals("1002")) {
			// 废纸橡胶报价
			parentId=13;
			typeId=25;
			code = "plastic";
		} else if (categoryCompanyPriceCode.substring(0, 4).equals("1000")) {
			// 所有报价
			// 废塑料报价，网上报价
			parentId=11;
			typeId=137;
			code = "paper";
		}
		out.put("code", code);

		// 左侧各地报价
		out.put("priceTypeId", typeId);
		out.put("parentId", parentId);
//		List<ForPriceDTO> overPrice = priceService
//				.queryPriceByParentId(parentId,7);
//		out.put("overPrice", overPrice);
		// 右侧各地网上报价
//		List<ForPriceDTO> onlinePrice = priceService
//				.queryPriceAndCategoryByTypeId(typeId, 7);
//		out.put("onlinePrice", onlinePrice);
	}
}