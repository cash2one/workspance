/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 下午02:19:01
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.company.SubscribeService;
import com.ast.ast1949.service.company.SubscribeSmsPriceService;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;

/**
 * 订制信息
 * 
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
@Controller
public class SubscribeController extends BaseController {
	@Autowired
	private SubscribeService subscribeService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PriceCategoryService priceCategoryService;
	@Autowired
	private AuthService authService;
	@Autowired
	ScoreChangeDetailsService scoreChangeDetailsService;
	@Autowired
	private CompanyAccountService companyAccountService;
	@Autowired
	private SubscribeSmsPriceService subscribeSmsPriceService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MyrcService myrcService;
	
	@RequestMapping
	public ModelAndView subscribe(HttpServletRequest request,String searchKey,
			Map<String, Object> out, String email, String subscribeType,
			Integer id) {
		SsoUser sessionUser = getCachedUser(request);
		
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		// 如果ID有值则初始化订阅内容
		setSiteInfo(new PageHeadDTO(), out);
		out.put("subscribeType", subscribeType);
		
		CompanyAccount account = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		
		out.put("currentEmail", companyAccountService.currentEmail(account));
		if (id != null && id.intValue() > 0) {
			SubscribeDO subscribe = subscribeService.querySubscribeById(id);
			if(account.getIsUseBackEmail()!=null && account.getIsUseBackEmail().equals("1")) {
				subscribe.setEmail(account.getBackEmail());
			}else{
				subscribe.setEmail(account.getEmail());
			}
			out.put("subscribe", subscribe);
			out.put("account", account);
			out.put("subscribeType", subscribe.getSubscribeType());
		}else{
			try {
				SubscribeDO subscribe=new SubscribeDO();
				subscribe.setKeywords( StringUtils.decryptUrlParameter(searchKey));
				out.put("subscribe", subscribe);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// 判断订阅者是否已登录系统
		if (sessionUser == null) {
			// 判断email是否已被注册
			Integer i=authService.countUserByEmail(email);
			if (i!= null && i.intValue()>0) {
				// email已被注册
				out.put("regFlag", "1");
			} else {
				out.put("regFlag", "0");
			}
		}else{
			out.put("email", sessionUser.getEmail());
		}
		
		//判断用户信息是否完善
		if(sessionUser!=null){
			String checkInfo=companyService.validateCompanyInfo(sessionUser);
			out.put("checkInfo", checkInfo);
		}
		
		
		
		return null;
	}

	@RequestMapping
	public ModelAndView saveSubscribe(HttpServletRequest request,
			Map<String, Object> out, SubscribeDO subscribe, String priceAllTypeId) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		subscribe.setCompanyId(sessionUser.getCompanyId());
		subscribe.setAccount(sessionUser.getAccount());
		if(subscribe.getIsSearchByArea()==null){
			subscribe.setIsSearchByArea(false);
		}
		if(subscribe.getIsSendByEmail()==null){
			subscribe.setIsSendByEmail(false);
		}
		
//		if("1".equals(subscribe.getSubscribeType())){
//			
//		}

		if (subscribeService.allowSubscribeByMemberRule(sessionUser.getCompanyId(),
				sessionUser.getMembershipCode(), subscribe.getSubscribeType())) {
			Integer impact = subscribeService.insertSubscribe(subscribe);

			if (impact != null && impact.intValue() > 0) {
				result.setSuccess(true);
				// 积分操作
				// 为用户增加积分
				if ("1".equals(subscribe.getSubscribeType())) {
					scoreChangeDetailsService
							.saveChangeDetails(new ScoreChangeDetailsDo(
									subscribe.getCompanyId(), null,
									"get_subscribe_price", null, null, null));
				} else {
					scoreChangeDetailsService
							.saveChangeDetails(new ScoreChangeDetailsDo(
									subscribe.getCompanyId(), null,
									"get_subscribe_product", null, null, null));
				}
			}
		}else{
			result.setData("outofSubscribeLimit");
		}

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updateSubscribe(HttpServletRequest request,
			Map<String, Object> out, SubscribeDO subscribe) throws IOException {
		ExtResult result = new ExtResult();
		Integer impact=subscribeService.updateSubscribe(subscribe);
		if(impact!=null && impact.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public void subscribeIndex(String orderEmail, HttpServletRequest request,
			Map<String, Object> out) {
		PageHeadDTO headDTO = new PageHeadDTO();
		setSiteInfo(headDTO, out);
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			out.put("currentEmail", sessionUser.getEmail());
		} else {
			out.put("currentEmail", orderEmail);
		}
	}

	@RequestMapping
	public void getSubCategory(String preCode, Map<String, Object> out) {
		List<CategoryDO> list = categoryService
				.queryCategoriesByPreCode(preCode);
		StringBuilder s = new StringBuilder(
				"<?xml version=\"1.0\"  encoding=\"utf-8\" ?>");
		s.append("<mess>");
		for (CategoryDO category : list) {
			s.append("<user_code>" + category.getCode()
					+ "</user_code><user_meno>" + category.getLabel()
					+ "</user_meno>");
		}
		s.append("</mess>");
		out.put("output", s.toString());
	}

	/**
	 * 根据父类别查询子类
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryPriceCategoryByParentId(Integer id,
			Map<String, Object> model) throws IOException {
		List<PriceCategoryDO> list = priceCategoryService
				.queryPriceCategoryByParentId(id);
		return printJson(list, model);
	}
	
	@RequestMapping
	public ModelAndView addSubscribeSms(Map<String, Object> out,HttpServletRequest request,
			String categoryCode,Integer areaNodeId) throws IOException{
		ExtResult result=new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		String code="";
		if(categoryCode.contains("_")){
			String str[]=categoryCode.split("_");  
			code=str[0];
			areaNodeId=Integer.parseInt(str[1]);
//			Integer count=subscribeSmsPriceService.countSubscribeSMS(code, sessionUser.getCompanyId());
//			if(count>0){
//				result.setData("outSubscribeLimit");
//			}else{
				Integer data=subscribeSmsPriceService.addSubscribeSMS(sessionUser.getCompanyId(), code, areaNodeId);
				if(data!=null && data.intValue()>0){
					result.setSuccess(true);
				}else{
					result.setData("outofSubscribeLimit");
				}
//			}
		}else{
//			Integer count=subscribeSmsPriceService.countSubscribeSMS(categoryCode, sessionUser.getCompanyId());
//			if(count>0){
//				result.setData("outSubscribeLimit");
//			}else{
				Integer data=subscribeSmsPriceService.addSubscribeSMS(sessionUser.getCompanyId(), categoryCode, areaNodeId);
				if(data!=null && data.intValue()>0){
					result.setSuccess(true);
				}else {
					result.setData("outofSubscribeLimit");
				}
			}
//		}
		return printJson(result, out);
	}
}
