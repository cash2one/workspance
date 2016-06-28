/****************
 * 再生地图接口 实现类
 */
package com.ast.ast1949.service.map.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.map.MapService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.lang.StringUtils;

@Component("mapService")
public class MapServiceImpl implements MapService {

	@Resource
	private CompanyDAO companyDAO;
	@Autowired
	private ProductsDAO productsDAO;
	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CompanyPriceService companyPriceService;

	@Resource
	private BbsPostDAO bbsPostDAO;

	@Override
	public PageDto<CompanyDto> queryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode, String keywords) {
		page.setRecords(companyDAO.queryCompanyVip(company, page, areaCode,
				keywords));
		page.setTotalRecords(companyDAO.countQueryCompanyVip(company, page,
				areaCode, keywords));
		return page;
	}

	@Override
	public PageDto<CompanyDto> queryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode) {
		return null;
	}

	@Override
	public List<ProductsDto> queryProductsByAreaCode4Map(String mainCode,
			String title, String typeCode, String areaCode, Integer maxSize) {
		List<ProductsDto> productsList = productsDAO
				.queryProductsByAreaCode4Map(mainCode, title, typeCode,
						areaCode, maxSize);

		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (ProductsDto dto : productsList) {
			dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
					.getProductsTypeCode()));
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode()));
			dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
					.getMembershipCode()));
			dto.setCategoryProductsMainLabel(CategoryProductsFacade
					.getInstance().getValue(
							dto.getProducts().getCategoryProductsMainCode()));
			dto.setCategoryProductsAssistLabel(CategoryProductsFacade
					.getInstance().getValue(
							dto.getProducts().getCategoryProductsAssistCode()));
		}

		return productsList;
	}

	@Override
	public PageDto<ProductsDto> pageProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode, String keywords) {
		page.setRecords(productsDAO.queryProductsWithCompany(company, products,
				page, industryCode, areaCode, keywords));
		page.setTotalRecords(productsDAO.countQueryProductsWithCompany(company,
				products, page, industryCode, areaCode, keywords));
		return page;
	}

	@Override
	public List<ProductsDto> indexHotProductsByArea(String industryCode,
			String areaCode, String keywords, String typeCode, Integer size,
			PageDto<ProductsDto> page) {
		if (size == null) {
			size = 10;
		}
		if ("1002".equals(industryCode)) {
			industryCode = "zh";
		}
		if (page == null) {
			page = new PageDto<ProductsDto>();
		}
		page.setPageSize(size);
		ProductsDO product = new ProductsDO();
		product.setTitle(keywords);
		List<ProductsDto> plist = productsService.pageLHProductsBySearchEngine(product, areaCode, null, page).getRecords();
		return plist;
	}

	@Override
	public List<ProductsDto> indexHotCompanysByArea(String industryCode,
			String areaCode, String keywords, Integer size,
			PageDto<ProductsDto> page) {
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		// 主营金属
		if ("1000".equals(industryCode)) {
			industryCode = "10001001";
		}
		// 主营塑料
		if ("1001".equals(industryCode)) {
			industryCode = "10001000";
		}
		// 主营综合
		if ("1002".equals(industryCode)) {
			industryCode = "zh";
		}
		if (page == null) {
			page = new PageDto<ProductsDto>();
		}
		page.setPageSize(size);
		List<CompanyDto> clist = companyService.queryCompanyZstMemberByAreacode(industryCode, areaCode,keywords, page);
		for (CompanyDto obj : clist) {
			ProductsDto dto = new ProductsDto();
			dto.setCompany(obj.getCompany());
			ProductsDO products = productsService.queryProductsByCidForLatest(
					obj.getCompany().getId(), null);
			if (products != null) {
				dto.setProducts(products);
				List<ProductsPicDO> picList = productsPicService
						.queryProductPicInfoByProductsId(products.getId());
				if (picList.size() > 0) {
					dto.setCoverPicUrl(picList.get(0).getPicAddress());
				}
			}
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<CompanyPriceDTO> indexLatestCompanysPriceByArea(
			String industryCode, String areaCode, Integer size) {
		CompanyPriceDTO searchDto = new CompanyPriceDTO();
		CompanyPriceDO cdo = new CompanyPriceDO();
		List<CompanyPriceDTO> list = new ArrayList<CompanyPriceDTO>();
		if (StringUtils.isEmpty(areaCode)) {
			areaCode = "100110001010";
		}
		// 金属
		if ("1000".equals(industryCode)) {
			cdo.setCategoryCompanyPriceCode("1001");
		}
		// 塑料
		if ("1001".equals(industryCode)) {
			cdo.setCategoryCompanyPriceCode("1000");
		}
		// 综合
		if (StringUtils.isEmpty(industryCode)) {
			searchDto.setZhFlag(true);
		}
		// searchDto.setProvince(areaCode);
		cdo.setAreaCode(areaCode);
		searchDto.setCompanyPriceDO(cdo);
		PageDto<CompanyPriceDTO> page = new PageDto<CompanyPriceDTO>();
		page.setPageSize(size);
		searchDto.setPage(page);
		// List<CompanyPriceDTO> dlist =
		// companyPriceService.queryCompanyPriceForFront(searchDto);
		List<CompanyPriceDTO> dlist = companyPriceService.pageCompanyPriceBySearchEngine(searchDto, page).getRecords();
		for (CompanyPriceDTO obj : dlist) {
			Company company;
			if (obj.getCompanyPriceDO() != null) {
				company = companyService.queryCompanyById(obj.getCompanyPriceDO().getCompanyId());
				obj.setDomainZz91(company.getDomainZz91());
				obj.setMembershipCode(company.getMembershipCode());
				obj.setProvinceName(CategoryFacade.getInstance().getValue(
				obj.getCompanyPriceDO().getAreaCode().substring(0, 12)));
				list.add(obj);
			}

		}
		return list;
	}

	@Override
	public PageDto<ProductsDto> pageHotCompanysByArea(String industryCode,String areaCode, String keywords, Integer size,PageDto<ProductsDto> page) {
		page.setRecords(indexHotCompanysByArea(industryCode, areaCode,keywords, size, page));
		page.setTotalRecords(companyService.countQueryCompanyZstMemberByAreacode(industryCode, areaCode,keywords));
		return page;
	}

	@Override
	public PageDto<ProductsDto> pageHotProductsByArea(String industryCode,String areaCode, String keywords, String typeCode, Integer size,PageDto<ProductsDto> page) {
		page.setRecords(indexHotProductsByArea(industryCode, areaCode,keywords, typeCode, size, page));
		ProductsDO product = new ProductsDO();
		product.setTitle(keywords);
		productsService.pageLHProductsBySearchEngine(product, areaCode, null, page);
		return page;
	}

	@Override
	public List<PostDto> queryPostByKey(String keyworld, Integer size) {
		if (size == null) {
			size = 8;
		}

		return bbsPostDAO.queryPostByKey(keyworld, size);
	}

	@Override
	public PageDto<ProductsDto> queryBySearchEnegine(String province,
			String keywords, String productsCode, PageDto<ProductsDto> page)
			throws MalformedURLException, IOException {
		ProductsDO product = new ProductsDO();
		String codeName = CategoryProductsFacade.getInstance().getValue(
				productsCode);
		if (StringUtils.isNotEmpty(codeName)) {
			keywords = codeName;
		}
		if (StringUtils.isNotEmpty(keywords)) {
			product.setTitle(keywords);
		}
		if (StringUtils.isEmpty(province)) {
			province = "";
		} else {
			province = CategoryFacade.getInstance().getValue(province);
		}
		if (StringUtils.isEmpty(productsCode)) {
			product.setCategoryProductsMainCode(productsCode);
		}
		page = productsService.pageProductsBySearchEngine(product, province,
				null, page);
		List<ProductsDto> pList = new ArrayList<ProductsDto>();
		for (ProductsDto p : page.getRecords()) {
			p.setProducts(productsService.queryProductsById(p.getProducts().getId()));
			if (p.getProducts() != null) {
				p.setCompany(companyService.queryCompanyById(p.getProducts().getCompanyId()));
			}
			pList.add(p);
		}
		page.setRecords(pList);
		// StringBuffer sb=new StringBuffer();
		// String url = ParamUtils.getInstance().getValue("baseConfig",
		// "search_api");
		// if(StringUtils.isNotEmpty(url)){
		// sb.append(url);
		// }else{
		// sb.append("http://python.zz91.com/prolist/?"); // 默认地址
		// }
		// //
		// sb.append("ptype=").append(StringUtils.getNotNullValue(ptype)).append("&");
		// sb.append("provincecode=").append(StringUtils.getNotNullValue(province)).append("&");
		// sb.append("province=").append(URLEncoder.encode(StringUtils.getNotNullValue(CategoryFacade.getInstance().getValue(province)),URL_ENCODE)).append("&");
		// //
		// sb.append("posttime=").append(StringUtils.getNotNullValue(posttime)).append("&");
		// //
		// sb.append("priceflag=").append(StringUtils.getNotNullValue(priceflag)).append("&");
		// //
		// sb.append("nopiclist=").append(StringUtils.getNotNullValue(nopiclist)).append("&");
		// //
		// sb.append("havepic=").append(StringUtils.getNotNullValue(havepic)).append("&");
		// //
		// sb.append("page=").append(StringUtils.getNotNullValue(page)).append("&");
		// sb.append("keywords=").append(URLEncoder.encode(keywords,URL_ENCODE));
		//
		// //step2 搜索供求信息
		// Document doc = Jsoup.parse(new URL(sb.toString()), SEARCH_EXPIRED);
		// String totalStr=doc.select("#totalrecords").val();
		// String data = doc.select(".offerFilterListPic").val();
		// if(!StringUtils.isNumber(totalStr)){
		// totalStr = "1";
		// }
		// Integer total = Integer.valueOf(totalStr);
		// if(total==null || total.intValue()<=0){
		// // break;
		// }
		//		
		// // out.put("totalRecords", total);
		// // out.put("producsResult",
		// doc.select("ul").toString().replace("&lt;", "<").replace("&gt;",
		// ">"));
		//		
		return page;
	}

}
