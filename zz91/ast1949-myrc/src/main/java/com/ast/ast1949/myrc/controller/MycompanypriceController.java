/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-24
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceDtoForMyrc;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.facade.ParseAreaCode;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.StringUtils;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class MycompanypriceController extends BaseController {

	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Resource
	private ProductsService productsService;
	@Resource
	private MyrcService myrcService;
	/**
	 * 获取本公司的所有报价分页信息 ，截止有效时间倒排
	 * 
	 * @param page
	 * @param out
	 * @param request
	 * @throws ParseException
	 */
	

	/**
	 * 初始化areaCode
	 * 
	 * @param companyPriceDO
	 *            不能为空
	 * @param out
	 */
	public void initAreaCode(CompanyPriceDO companyPriceDO, Map<String, Object> out) {
		if (companyPriceDO != null) {

			if (companyPriceDO.getCategoryCompanyPriceCode() != null
					&& companyPriceDO.getCategoryCompanyPriceCode().length() == 4) {
				out.put("threeCode", companyPriceDO.getCategoryCompanyPriceCode());
			}
			if (companyPriceDO.getCategoryCompanyPriceCode() != null
					&& companyPriceDO.getCategoryCompanyPriceCode().length() == 8) {
				out.put("secondCode", companyPriceDO.getCategoryCompanyPriceCode());
				out.put("threeCode", companyPriceDO.getCategoryCompanyPriceCode().substring(0,
						companyPriceDO.getCategoryCompanyPriceCode().length() - 4));
			}
			if (companyPriceDO.getCategoryCompanyPriceCode() != null
					&& companyPriceDO.getCategoryCompanyPriceCode().length() == 12) {
				out.put("firstCode", companyPriceDO.getCategoryCompanyPriceCode());
				out.put("secondCode", companyPriceDO.getCategoryCompanyPriceCode().substring(0,
						companyPriceDO.getCategoryCompanyPriceCode().length() - 4));
				out.put("threeCode", companyPriceDO.getCategoryCompanyPriceCode().substring(0,
						companyPriceDO.getCategoryCompanyPriceCode().length() - 8));
			}

			if (companyPriceDO.getAreaCode() != null && companyPriceDO.getAreaCode().length() > 4) {
				out.put("ccode", companyPriceDO.getAreaCode());
				out.put("pcode", companyPriceDO.getAreaCode().substring(0,
						companyPriceDO.getAreaCode().length() - 4));
			}
		}
	}
	
	/*********up old code**********/
	
	@RequestMapping
	public ModelAndView queryCategoryCompanyPriceByCode(String parentCode, Map<String, Object> map)
			throws IOException {
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceService
				.queryCategoryCompanyPriceByCode(parentCode);
		return printJson(list, map);

	}
	
	@RequestMapping
	public ModelAndView createPrice(Integer productId, Integer error,
			HttpServletRequest request, Map<String, Object> out){
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		if(productId!=null && productId.intValue()>0){
			ProductsDO products = productsService.queryProductsById(productId);
			out.put("products", products);
		}
		out.put("areaCode", getCachedUser(request).getAreaCode());
		out.put("error", error);
		return null;
	}
	/****************
	 * 添加报价
	 * @param request
	 * @param out
	 * @param companyPrice
	 * @param expired
	 * @return
	 */
	@RequestMapping
	public ModelAndView doCreate(HttpServletRequest request, Map<String, Object> out,
			CompanyPriceDO companyPrice, String expired){
		
		if (companyPrice.getRefreshTime() == null) {
			companyPrice.setRefreshTime(new Date());
		}
		if ("-1".equals(expired)) {// 有效期为最大时间
			try {
				companyPrice.setExpiredTime(DateUtil.getDate(AstConst.MAX_TIMT, AstConst.DATE_FORMATE_WITH_TIME));
			} catch (ParseException e) {
			}
		} else {
			companyPrice.setExpiredTime(DateUtil.getDateAfterDays(new Date(), Integer
					.valueOf(expired)));
		}
	
		
		SsoUser sessionUser = getCachedUser(request);
		companyPrice.setCompanyId(sessionUser.getCompanyId());
		companyPrice.setAccount(sessionUser.getAccount());
		Integer i = companyPriceService.insertCompanyPrice(sessionUser.getMembershipCode(), companyPrice);
		if(i!=null && i.intValue()>0){
			return new ModelAndView("redirect:list.htm");
		}
		out.put("error", 1);
		
		out.put("productId", companyPrice.getProductId());
		
		return new ModelAndView("redirect:createPrice.htm");
		 
	}
	
	@RequestMapping
	public ModelAndView updatePrice(HttpServletRequest request, Map<String, Object> out,
			Integer id, Integer error){
		CompanyPriceDO companyPrice = companyPriceService.queryCompanyPriceById(id);
		if (companyPrice == null) {
			out.put("error", 2);
			return null;
		} 
		
		Integer days=20;
		try {
			days = companyPriceService.queryCompanyPriceValidityTimeById(companyPrice.getRefreshTime(), companyPrice.getExpiredTime());
		} catch (ParseException e) {
		}
		out.put("days", days);
		
		out.put("companyPrice", companyPrice);
		
		out.put("error", error);
//		initAreaCode(companyPrice, out);
//		if(StringUtils.isNumber(companyPrice.getMaxPrice()) 
//				&& Integer.valueOf(companyPrice.getMaxPrice()).intValue()>0){
//			if(StringUtils.isNumber(companyPrice.getMinPrice()) 
//					&& Integer.valueOf(companyPrice.getMinPrice()).intValue()>0){
//				out.put("scropFlag", 1);
//			}
//		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView doUpdate(HttpServletRequest request, Map<String, Object> out, 
			CompanyPriceDO companyPrice, String expired){
		if (companyPrice.getRefreshTime() == null) {
			companyPrice.setRefreshTime(new Date());
		}
		
		if ("-1".equals(expired)) {// 有效期为最大时间
			try {
				companyPrice.setExpiredTime(DateUtil.getDate(AstConst.MAX_TIMT, AstConst.DATE_FORMATE_WITH_TIME));
			} catch (ParseException e) {
			}
		} else {
			companyPrice.setExpiredTime(DateUtil.getDateAfterDays(new Date(), Integer
					.valueOf(expired)));
		}
		
//		if("10051000".equals(getCachedUser(request).getMembershipCode())){
		// 所有报价一律流入审核状态
		companyPrice.setIsChecked("0");
//		}else{
//			companyPrice.setIsChecked("1");
//		}
		
		Integer i = companyPriceService.updateCompanyPrice(companyPrice);
		if(i!=null && i.intValue()>0){
			out.put("error", 0);
			return new ModelAndView("redirect:list.htm");
		}
		out.put("error", 1);
		out.put("id", companyPrice.getId());
		return new ModelAndView("redirect:updatePrice.htm");
	}
	
	@RequestMapping
	public ModelAndView delete(String ids, HttpServletRequest request, Map<String, Object> out) {
		String[] entities = ids.split(",");
		int[] i = new int [entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		Integer a = companyPriceService.batchDeleteCompanyPriceById(StringUtils.StringToIntegerArray(ids));
		if (a > 0) {
			out.put("error", 0);
		} else {
			out.put("error", 1);
		}
		return new ModelAndView("redirect:list.htm");
	}
	
	@RequestMapping
	public void list(PageDto<CompanyPriceDtoForMyrc> page, Integer error,
			Map<String, Object> out, HttpServletRequest request)
			throws ParseException {
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		page = companyPriceService.queryCompanyPriceListByCompanyId(sessionUser.getCompanyId(), page);
		List<CompanyPriceDtoForMyrc> companyPriceList = page.getRecords();
		for (CompanyPriceDtoForMyrc companyPrice : companyPriceList) {
			if (StringUtils.isNotEmpty(companyPrice.getAreaCode())) {
				ParseAreaCode parser = new ParseAreaCode();
				parser.parseAreaCode(companyPrice.getAreaCode());
				companyPrice.setCountryName(parser.getContry());
				companyPrice.setProvince(parser.getProvince());
				companyPrice.setCity(parser.getCity());
			}
		}
		out.put("error", error);
		out.put("page", page);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(MD5.encode("135246", MD5.LENGTH_16));
	}
}
