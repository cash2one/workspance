/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 下午02:19:01
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.company.SubscribeService;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.site.CategoryService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.velocity.AddressTool;

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
	ScoreChangeDetailsService scoreChangeDetailsService;

	@RequestMapping
	public ModelAndView subscribe(HttpServletRequest request,String searchKey,
			Map<String, Object> out, String email, String subscribeType,
			Integer id) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("myrc")+"/subscribe/subscribe.htm?id="+id);
//		// 如果ID有值则初始化订阅内容
//		setSiteInfo(new PageHeadDTO(), out);
//		
//		out.put("email", email);
//		out.put("subscribeType", subscribeType);
//		if (id != null && id.intValue() > 0) {
//			SubscribeDO subscribe = subscribeService.querySubscribeById(id);
//			out.put("subscribe", subscribe);
//			out.put("email", subscribe.getEmail());
//			out.put("subscribeType", subscribe.getSubscribeType());
//		}else{
////			try {
////				SubscribeDO subscribe=new SubscribeDO();
////				subscribe.setKeywords( URLDecoder.decode(searchKey, "utf-8"));
////				out.put("subscribe", subscribe);
////			} catch (UnsupportedEncodingException e) {
////				e.printStackTrace();
////			}
//			
//			try {
//				SubscribeDO subscribe=new SubscribeDO();
//				subscribe.setKeywords( StringUtils.decryptUrlParameter(searchKey));
//				out.put("subscribe", subscribe);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//
////		CompanyContactsDO account = getCachedAccount(request);
//		SsoUser sessionUser = getCachedUser(request);
//
//		// 判断订阅者是否已登录系统
//		if (sessionUser == null) {
//			// 判断email是否已被注册
//			Integer i=authService.countUserByEmail(email);
//			if (i!= null && i.intValue()>0) {
//				// email已被注册
//				out.put("regFlag", "1");
//			} else {
//				out.put("regFlag", "0");
//			}
//		}else{
//			out.put("email", sessionUser.getEmail());
//		}
//
//		return null;
	}

	@RequestMapping
	public ModelAndView saveSubscribe(HttpServletRequest request,
			Map<String, Object> out, SubscribeDO subscribe, String priceAllTypeId) throws IOException {
		ExtResult result = new ExtResult();

//		CompanyContactsDO account = getCachedAccount(request);
//		CompanyDO company = getCachedCompany(request);
		SsoUser sessionUser = getCachedUser(request);
		
		subscribe.setCompanyId(sessionUser.getCompanyId());
		subscribe.setAccount(sessionUser.getAccount());
		
		if(subscribe.getIsSearchByArea()==null){
			subscribe.setIsSearchByArea(false);
		}
		if(subscribe.getIsSendByEmail()==null){
			subscribe.setIsSendByEmail(false);
		}
		
		if("1".equals(subscribe.getSubscribeType())){
			
		}

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

//	@RequestMapping
//	public ModelAndView subscribeBiz(HttpServletRequest request, Integer id,
//			String email, String searchKey, Map<String, Object> out) throws UnsupportedEncodingException {
//		CompanyContactsDO companyContacts = getCachedAccount(request);
//		Boolean isShowPwd = false;
//		Boolean isShowReg = false;
//		if (companyContacts != null) {
//			// 修改
//			email = companyContactsService.getCurrentEmail(companyContacts);
//			if (id != null) {
//				SubscribeDO entity = subscribeService.selectSubscribeById(id);
//				if (entity != null) {
//					// 判断是否为该公司定制的信息
//					if (companyContacts.getAccount()
//							.equals(entity.getAccount())) {
//						out.put("subscribe", entity);
//					} else {
//						out.put("errorText", "该信息不是其发布的");
//					}
//				} else {
//					out.put("errorText", "该信息不存在");
//				}
//			}
//			// 添加
//			else {
//				// 从发布成功页面转过来
//				if (!StringUtils.isEmpty(searchKey)) {
//					SubscribeDO subscribe = new SubscribeDO();
////					subscribe.setKeywords(StringUtils.decryptUrlParameter(searchKey));
//					subscribe.setKeywords(URLDecoder.decode(searchKey,"utf-8"));
//					out.put("subscribe", subscribe);
//				}
//			}
//			out.put("account", companyContacts.getAccount());
//		} else {
//			if (email != null) {
//				if (companyContactsService.countCompanyContactByEmail(email) > 0) {
//					// 显示输入密码
//					isShowPwd = true;
//				} else {
//					// 显示注册框
//					isShowReg = true;
//				}
//			} else {
//				return new ModelAndView(new RedirectView("subscribeIndex.htm"));
//			}
//		}
//		out.put("currentEmail", email);
//		out.put("isShowPwd", isShowPwd);
//		out.put("isShowReg", isShowReg);
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.TRADE_CENTER); // 具体每个板块的对应值请参照common里的NavConst.java
//		headDTO.setPageTitle("订阅最新商机_${site_name}");
//		headDTO.setPageKeywords("废料交易中心,废料交易,废料供求,求购废料,回收,废金属,废塑料,复活商机");
//		headDTO
//				.setPageDescription("${site_name}的废料交易中心是中国最大最旺的网上废料交易市场,为您精选了废金属、 废塑料、废纸、废橡胶、废旧二手物资、废旧二手设备的供应、求购、回收等信息，帮您快速寻找和复活废料商机， 轻松达成交易。");
//		setSiteInfo(headDTO, out);
//		return null;
//	}

//	@RequestMapping
//	public ModelAndView saveSubscribeBiz(String email, String pwd,
//			String provinceAreaCode, SubscribeDO subscribe,
//			CompanyContactsDO companyContactsDO, CompanyDO companyDO,
//			HttpServletRequest request, Map<String, Object> out)
//			throws IOException {
//		String username = email;
//		String password = companyContactsDO.getPassword();
//		CompanyContactsDO loginCompanyContacts = getCachedAccount(request);
//		// 判断是会员在未登录的情况下订制供求
//		if (loginCompanyContacts == null) {
//			if (!StringUtils.isBlank(username)
//					&& !StringUtils.isBlank(password)) {
//				try {
//					authService.validateUser(username, password);
//					AuthUser u;
//					if (StringUtils.isEmail(username)) {
//						u = authService.listOneUserByEmail(username);
//					} else {
//						u = authService.listOneUserByUsername(username);
//					}
//
//					loginCompanyContacts = companyContactsService
//							.queryContactByAccount(u.getUsername());
//
//				} catch (Exception e) {
//					return printJs(e.getMessage(), out);
//				}
//			}
//		}
//		if (loginCompanyContacts != null) {
//			if (subscribe.getIsSendByEmail() == null) {
//				subscribe.setIsSendByEmail(false);
//			}
//			// TODO:省份code得不到
//			subscribe.setAreaCode(provinceAreaCode);
//			subscribe.setSubscribeType("0");
//			subscribe.setCompanyId(loginCompanyContacts.getCompanyId());
//			subscribe.setAccount(loginCompanyContacts.getAccount());
//			if (subscribe.getId() == null) {
//				if (loginCompanyContacts == null) {
//					try {
//						// 判断非会员在未登录的情况下订制供求
//						Integer companyId = registerService.saveInfo(companyDO,
//								loginCompanyContacts);
//						subscribe.setCompanyId(companyId);
//						subscribe.setAccount(loginCompanyContacts.getAccount());
//					} catch (RegisterException e) {
//						return printJs("alert('" + e.getMessage() + "')", out);
//					}
//
//				}
//				List<SubscribeDO> subscribeList = subscribeService
//						.querySubscribeByCompanyIdAndSubscribeType(
//								loginCompanyContacts.getCompanyId(), "0");
//				Integer max = Integer.valueOf(memberRuleService
//						.listOneResultByAccount(loginCompanyContacts
//								.getAccount(), "subscribe_max"));
//				if (subscribeList.size() < max) {
//					if (loginCompanyContacts.getIsUseBackEmail() != null
//							&& loginCompanyContacts.getIsUseBackEmail().equals(
//									"1")) {
//						subscribe.setEmail(loginCompanyContacts.getBackEmail());
//					}
//					subscribeService.insertSubscribe(subscribe);
//					scoreChangeDetailsService
//							.saveChangeDetails(new ScoreChangeDetailsDo(
//									subscribe.getCompanyId(), null,
//									"get_subscribe_product", null, null, null));
//				} else {
//					return printJs("alert('你订制的信息已经达到" + max
//							+ "条!无法再进行订制!');top.location='"
//							+ request.getContextPath()
//							+ "/myrc/mysubscribe/manageSubscribeBiz.htm';", out);
//				}
//			} else {
//				subscribeService.updateSubscribe(subscribe);
//			}
//			// location.href='manageSubscribePrice.htm';
//			return printJs("alert('定制成功!');top.location='"
//					+ request.getContextPath()
//					+ "/myrc/mysubscribe/manageSubscribeBiz.htm';", out);
//		} else {
//			return printJs("alert('你尚未登录，无法进行订制');top.location='"
//					+ request.getContextPath() + "/login.htm';", out);
//		}
//	}

//	@RequestMapping
//	public ModelAndView subscribePrice(HttpServletRequest request,
//			String email, Map<String, Object> out) {
//
//		/*
//		 * 1 用户是否已登录系统? 是 --> 1.1 否 --> 1.2 1.1 从缓存中获取用户信息（email） 1.2 -->2 2
//		 * email是否为空? 是 -->页面退回到“/front/subscribe/subscribeIndex.htm” 否 --> 3 3
//		 * 该email是否已注册? 是 -->要求输入密码-->4 否 --> 注册新会员 4 验证密码是否正确? 是 -->准许提交 否
//		 * -->重新输入-->4
//		 */
//
//		CompanyContactsDO companyContacts = getCachedAccount(request);
//		Boolean isShowPwd = false;
//		Boolean isShowReg = false;
//		Boolean isSendByEmail = false;
//		if (companyContacts != null) {
//			// 修改
//			email = companyContactsService.getCurrentEmail(companyContacts);
//
//			List<SubscribeDO> list = subscribeService
//					.querySubscribeByCompanyIdAndSubscribeType(companyContacts
//							.getCompanyId(), "1");
//			List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
//
//			for (SubscribeDO subscribe : list) {
//				String text = "";
//				String type = "";
//				// 取主类别
//				Integer priceTypeId = subscribe.getPriceTypeId();
//				isSendByEmail = subscribe.getIsSendByEmail();
//				if (priceTypeId != null) {
//					PriceCategoryDO priceCategory = priceCategoryService
//							.queryPriceCategoryById(priceTypeId);
//					List<PriceCategoryDO> parentList = priceCategoryService
//							.getAllParentPriceCategoryByParentId(priceCategory
//									.getParentId());
//					for (PriceCategoryDO category : parentList) {
//						if (category != null) {
//							text += category.getName() + " + ";
//						}
//					}
//					text += priceCategory.getName();
//					type = priceTypeId.toString();
//				}
//				// 取辅助类别
//				Integer priceAssistTypeId = subscribe.getPriceAssistTypeId();
//				if (priceAssistTypeId != null) {
//					PriceCategoryDO priceCategory = priceCategoryService
//							.queryPriceCategoryById(priceAssistTypeId);
//					if (priceCategory != null) {
//						text += " + (" + priceCategory.getName() + ")";
//						type += "|" + priceAssistTypeId.toString();
//					}
//				}
//
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("text", text);
//				map.put("type", type);
//				newList.add(map);
//			}
//			out.put("subscribeList", newList);
//
//		} else {
//			if (email != null) {
//				if (companyContactsService.countCompanyContactByEmail(email) > 0) {
//					// 显示输入密码
//					isShowPwd = true;
//				} else {
//					// 显示注册框
//					isShowReg = true;
//				}
//			} else {
//				return new ModelAndView(new RedirectView("subscribeIndex.htm"));
//			}
//		}
//
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.TRADE_CENTER); // 具体每个板块的对应值请参照common里的NavConst.java
//		headDTO.setPageTitle("订阅报价_${site_name}");
//		headDTO.setPageKeywords("废料交易中心,废料交易,废料供求,求购废料,回收,废金属,废塑料,复活商机");
//		headDTO
//				.setPageDescription("${site_name}的废料交易中心是中国最大最旺的网上废料交易市场,为您精选了废金属、 废塑料、废纸、废橡胶、废旧二手物资、废旧二手设备的供应、求购、回收等信息，帮您快速寻找和复活废料商机， 轻松达成交易。");
//		setSiteInfo(headDTO, out);
//
//		out.put("currentEmail", email);
//		out.put("isSendByEmail", isSendByEmail);
//		out.put("isShowPwd", isShowPwd);
//		out.put("isShowReg", isShowReg);
//		return null;
//
//	}

//	@RequestMapping
//	public ModelAndView saveSubscribePrice(String email, String username,
//			String password, String priceAllTypeId, SubscribeDO subscribe,
//			CompanyContactsDO companyContactsDO, CompanyDO companyDO,
//			HttpServletRequest request, Map<String, Object> out)
//			throws IOException {
//		CompanyContactsDO loginCompanyContacts = getCachedAccount(request);
//		// 判断是会员在未登录的情况下订制报价
//		if (loginCompanyContacts == null) {
//			if (!StringUtils.isBlank(username)
//					&& !StringUtils.isBlank(password)) {
//				try {
//					authService.validateUser(username, password);
//					AuthUser u;
//					companyContactsDO.setEmail(username);
//					if (StringUtils.isEmail(username)) {
//						u = authService.listOneUserByEmail(username);
//					} else {
//						u = authService.listOneUserByUsername(username);
//					}
//
//					loginCompanyContacts = companyContactsService
//							.queryContactByAccount(u.getUsername());
//
//				} catch (Exception e) {
//					return printJs("alert('" + e.getMessage()
//							+ "');top.location='" + request.getContextPath()
//							+ "/myrc/mysubscribe/manageSubscribePrice.htm';",
//							out);
//				}
//			}
//		}
//		// 判断非会员在未登录的情况下订制报价
//		if (loginCompanyContacts == null) {
//			try {
//				TestRandom tr = new TestRandom();
//				String randomPassword = tr.getPassword();
//				companyContactsDO.setPassword(randomPassword);
//				companyContactsDO.setEmail(username);
//				Integer companyId = registerService.saveInfo(companyDO,
//						companyContactsDO);
//				subscribe.setCompanyId(companyId);
//				subscribe.setAccount(companyContactsDO.getAccount());
//			} catch (RegisterException e) {
//				return printJs("alert('" + e.getMessage() + "')", out);
//			}
//		}
//
//		if (loginCompanyContacts != null) {
//			// 先将所有订制的报价都删除
//			subscribeService.deleteSubscribeByCompanyIdAndSubscribeType(
//					loginCompanyContacts.getCompanyId(), "1");
//			if (subscribe.getIsSendByEmail() == null) {
//				subscribe.setIsSendByEmail(false);
//			}
//
//			subscribe.setSubscribeType("1");
//			subscribe.setCompanyId(loginCompanyContacts.getCompanyId());
//			subscribe.setAccount(loginCompanyContacts.getAccount());
//			String[] allTypes = priceAllTypeId.split(",");
//
//			for (String t : allTypes) {
//				if (t.contains("|")) {
//					String[] types = t.split("\\|");
//					subscribe.setPriceTypeId(Integer.valueOf(types[0].trim()));
//					subscribe.setPriceAssistTypeId(Integer.valueOf(types[1]
//							.trim()));
//				} else {
//					subscribe.setPriceTypeId(Integer.valueOf(t.trim()));
//				}
//
//				List<SubscribeDO> subscribeList = subscribeService
//						.querySubscribeByCompanyIdAndSubscribeType(
//								loginCompanyContacts.getCompanyId(), "1");
//				CompanyDO cp = companyService
//						.selectCompanyById(loginCompanyContacts.getCompanyId());
//				if (cp != null) {
//					Integer max = Integer.valueOf(MemberRuleFacade.getInstance().getValue(cp.getMembershipCode(), "subscribe_price_max"));
//					if (subscribeList.size() < max) {
//						if (loginCompanyContacts.getIsUseBackEmail() != null
//								&& loginCompanyContacts.getIsUseBackEmail()
//										.equals("1")) {
//							subscribe.setEmail(loginCompanyContacts
//									.getBackEmail());
//						}
//						subscribeService.insertSubscribe(subscribe);
//						scoreChangeDetailsService
//								.saveChangeDetails(new ScoreChangeDetailsDo(
//										subscribe.getCompanyId(), null,
//										"get_subscribe_price", null, null, null));
//					} else {
//						return printJs(
//								"alert('你订制的信息已经达到"
//										+ max
//										+ "条!无法再进行订制!');top.location='"
//										+ request.getContextPath()
//										+ "/myrc/mysubscribe/manageSubscribePrice.htm';",
//								out);
//					}
//				} else {
//					return printJs("alert('您的帐号信息有误!无法进行订制!')", out);
//				}
//			}
//			return printJs("alert('定制成功!');top.location='"
//					+ request.getContextPath()
//					+ "/myrc/mysubscribe/manageSubscribePrice.htm';", out);
//
//		} else {
//			return printJs("alert('你尚未登录，无法进行订制');top.location='"
//					+ request.getContextPath()
//					+ "/myrc/mysubscribe/manageSubscribePrice.htm';", out);
//		}
//	}

	@RequestMapping
	public ModelAndView subscribeIndex(String orderEmail, HttpServletRequest request,
			Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("myrc")+"/subscribe/subscribeIndex.htm");
//		PageHeadDTO headDTO = new PageHeadDTO();
//		setSiteInfo(headDTO, out);
////		CompanyContactsDO companyContacts = getCachedAccount(request);
//		SsoUser sessionUser = getCachedUser(request);
//		if (sessionUser != null) {
//			out.put("currentEmail", sessionUser.getEmail());
//		} else {
//			out.put("currentEmail", orderEmail);
//		}
	}

	@RequestMapping
	public void getSubCategory(String preCode, Map<String, Object> out) {
		// List<CategoryDO> list = CategoryFacade.getInstance()
		// .listCategoryByParentCode(preCode);
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
}
