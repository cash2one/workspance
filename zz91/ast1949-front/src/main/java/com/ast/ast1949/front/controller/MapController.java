/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-6
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.velocity.AddressTool;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 * 
 */
@Controller
public class MapController extends BaseController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CompanyService companyService;
//	@Autowired
//	private AdsService adsService;

	private final static String DEFAULT_CITY_CODE = "10011000";
//	/**
//	 * 供应
//	 */
//	private final static String PRODUCTS_TYPE_CODE_SUPPLY = "10331000";
//	/**
//	 * 求购
//	 */
//	private final static String PRODUCTS_TYPE_CODE_BUY = "10331001";

	/**
	 * 再生地图首页
	 * 
	 * @param out
	 * @param type
	 *            信息类别 ［0企业信息，1供求信息］
	 * @param areaCode
	 *            地区编码
	 * @param industryCode
	 *            行业编码
	 * @param searchName
	 *            关键字
	 * @param pcode
	 *            省份地区编码
	 * @param ccode
	 *            城市地区编码
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, String type, String areaCode, String industryCode,
			String searchName, String pcode, String ccode) throws Exception {
		return new ModelAndView("redirect:"+AddressTool.getAddress("map")+"/index.htm");
//		// 设置页面头部信息
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.MAP);
//		headDTO.setPageTitle("再生地图|再生地图_${site_name}");
//		headDTO.setPageKeywords("再生地图,废料地图,");
//		headDTO
//				.setPageDescription("ZZ91-再生地图, 向您展现全景式中国废料蓝图。鼠标轻轻一点，直达全国各废料集散地、园区，大量废料商家尽收眼底，800万废料商机全景呈送。再生地图，助您轻松找到客户,把握商机.${site_name}。");
//		setSiteInfo(headDTO, out);
//		//infoType 信息类别 默认 0企业信息 
//		if (StringUtils.isEmpty(type) || !StringUtils.isNumber(type)) {
//			type = "0";
//		}
//		out.put("type", type);
//		//关键字编码转换
//		searchName = StringUtils.decryptUrlParameter(searchName);//new String(searchName.getBytes("ISO-8859-1"),"UTF-8");
//		//供应列表
//		List<ProductsDO> supplyList = productsService.queryNewestProducts(null, PRODUCTS_TYPE_CODE_SUPPLY, 8);
//		out.put("supplyList", supplyList);
//		//求购列表
//		List<ProductsDO> buyList = productsService.queryNewestProducts(null, PRODUCTS_TYPE_CODE_BUY, 8);
//		out.put("buyList", buyList);
//		
//		String title = null;
//		title = CategoryFacade.getInstance().getValue(areaCode);
//
//		List<PriceDO> advisoryList = priceService.queryPriceByTitleAndTypeId("1", title, "10");
//		out.put("advisoryList", advisoryList);
//
//		List<PriceDO> quoteList = priceService.queryPriceByTitleAndTypeId("0", title, "10");
//		out.put("quoteList", quoteList);

		//广告位-右上
//		List<AdsDO> adsList1 = adsService.queryAdsByPlaceId(247, 1);
//		out.put("adsList1", adsList1);
//
//		//广告位-右中
//		List<AdsDO> adsList2 = adsService.queryAdsByPlaceId(248, 1);
//		out.put("adsList2", adsList2);
//
//		//广告位-右下
//		List<AdsDO> adsList3 = adsService.queryAdsByPlaceId(249, 1);
//		out.put("adsList3", adsList3);

		// 最新话题,即商家热门话题
//		List<BbsPostDTO> hottopic = bbsService.queryBbsPostByStatistics(null, "a.gmt_modified",
//				"desc", 8);
//		out.put("hottopic", hottopic);
//
//		//商户展示
//		out.put("showCompanyList", companyService.queryCompanyByArea(pcode, 10));
//		out.put("pcode", pcode);
//		out.put("ccode", ccode);
//		out.put("areaCode", areaCode);
//		//		out.put("icode", industryCode);
//		out.put("keywords", searchName);
//		out.put("industryCode", industryCode);
//		out.put("searchName", searchName);
	}
	
	@RequestMapping
	public void list(Map<String, Object> out, String type,String areaCode,String pcode, String ccode,
			String industryCode, String searchName, PageDto<CompanyDto> page) throws Exception {
		setSiteInfo(new PageHeadDTO(), out);
		
		if (StringUtils.isEmpty(type) || !StringUtils.isNumber(type)) {
			type = "0";
		}
		out.put("type", type);
		searchName = StringUtils.decryptUrlParameter(searchName);
		out.put("searchName", searchName);
		out.put("searchNameEncode", URLEncoder.encode(searchName, "utf-8"));
		out.put("industryCode", industryCode);
		out.put("pcode", pcode);
		out.put("ccode", ccode);
		
		if ("1".equals(type)) {
			//TODO 搜索供求信息功能，未完成
//			ProductsListItemForFrontDTO searchDTO = new ProductsListItemForFrontDTO();
//			searchDTO.setKeywords(StringUtils.decryptUrlParameter(searchName));
//			if (StringUtils.isNotEmpty(areaCode) && areaCode.length() >= 4)
//				searchDTO.setAreaCode(areaCode);
//			else if (StringUtils.isNotEmpty(pcode) && pcode.length() >= 4) {
//				searchDTO.setAreaCode(pcode);
//			}
//			PageDto<SearchSupport> pager = new PageDto<SearchSupport>();
//			pager.setPageSize(8);
//			pager.setStartIndex((Integer.valueOf(p) - 1) * pager.getPageSize());
//			pager = productSearchService.search(searchDTO, pager);
//			for (SearchSupport obj : pager.getRecords()) {
//				ProductsListItemForFrontDTO product = (ProductsListItemForFrontDTO) obj;
//				ParseAreaCode parser=new ParseAreaCode();
//				parser.parseAreaCode(product.getAreaCode());
//				product.setProvince(parser.getProvince());
//				product.setArea(parser.getCity());
//			}
//			out.put("keywords", searchDTO.getKeywords());
//			out.put("totalPagesSD", pager.getTotalPages());
//			out.put("currentPageSD", Integer.valueOf(p));
//			out.put("suffixUrlSD", "");
//			out.put("resultsSD", pager.getRecords());
		} else {
			//公司列表
			
			Company company = new Company();
			
			company.setAreaCode(pcode);
			if(StringUtils.isNotEmpty(ccode)){
				company.setAreaCode(ccode);
			}
			if(StringUtils.isNotEmpty(areaCode)){
				company.setAreaCode(areaCode);
			}
			company.setIndustryCode(industryCode);
			page=companyService.pageCompanyBySearch(company, page);
			out.put("page", page);
			
//			CompanyDetailsDTO dto = new CompanyDetailsDTO();
//			dto.setCompanyContacts(new CompanyContactsDO());
//			dto.setSearchKeywords(searchName);
//			CompanyDO company = new CompanyDO();
//			company.setIndustryCode(industryCode);
//			company.setAreaCode(areaCode);
//			if (StringUtils.isEmpty(areaCode) && StringUtils.isNotEmpty(pcode)) {
//				company.setAreaCode(pcode);
//			}
//			p = StringUtils.getPageIndex(p);
//			dto.setCompany(company);
//			PageDto<CompanyDetailsDTO> pager = new PageDto<CompanyDetailsDTO>();
//			pager.setPageSize(8);
//			pager.setSort("membership_code");
//			pager.setDir("desc");
//			pager.setStartIndex((Integer.valueOf(p) - 1) * 8);
//			pager = companyService.queryCompanyList(dto, pager);
//			out.put("totalPage", pager.getTotalPages());
//			out.put("currentPage", Integer.valueOf(p));
//			out.put("suffixUrl", "");
//			out.put("results", pager.getRecords());
		}

		out.put("industryCode", industryCode);
		out.put("areaCode", areaCode);
		out.put("searchName", searchName);
	}

	/**
	 * 获取行业
	 * 
	 * @param code
	 *            类别Code
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getIndustryCodeByCode.htm", method = RequestMethod.GET)
	public ModelAndView getIndustryCodeByCode(String code, Map<String, Object> out)
			throws IOException {
		JSONArray json = JSONArray.fromObject(categoryService.queryCategoriesByPreCode(code));
		return printJson(json, out);
	}

	/**
	 * 获取省份
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getProvince(Map<String, Object> model) throws IOException {
		return printJson(JSONArray.fromObject(categoryService.child(DEFAULT_CITY_CODE)), model);
	}

	/**
	 * 获取城市
	 * 
	 * @param model
	 * @param pid
	 *            省份Code值
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getCity.htm", method = RequestMethod.GET)
	public ModelAndView getCity(Map<String, Object> model, String pid) throws IOException {
		JSONArray json = JSONArray.fromObject(categoryService.child(pid));
		return printJson(json, model);
	}
}
