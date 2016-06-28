package com.kl91.front.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kl91.domain.auth.Kl91SsoUser;
import com.kl91.domain.company.Company;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.dto.products.ProductsSolrDto;
import com.kl91.service.company.CompanyService;
import com.kl91.service.inquiry.InquiryService;
import com.kl91.service.products.ProductsService;
import com.kl91.service.products.ProductsSolrService;

@Controller
public class ZhushouController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsService productsService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private ProductsSolrService productsSolrService; 

	@RequestMapping
	public void index(HttpServletRequest request, Map<String, Object> out,ProductsSearchDto searchDto,PageDto<ProductsSolrDto>page) {
		Kl91SsoUser sessionUser = getCachedUser(request);
		Integer cid = sessionUser.getCompanyId();
		Company company = companyService.queryById(cid);
		out.put("company", company);
		// 发布供求条数 可考虑放在缓存里
		out.put("buyCount", productsService.countProducts(cid,
				ProductsService.BUY_TYPE_CODE));
		out.put("sellCount", productsService.countProducts(cid,
				ProductsService.SELL_TYPE_CODE));
		// 未读询盘条数 可考虑放在缓存里
		out.put("inquiryCount", inquiryService.countNoViewedInquiry(cid));
		
		page.setPageSize(5);
		out.put("latestProducts",productsSolrService.querySolrProductsByTypeCode(searchDto, page, null));
	}
	


}
