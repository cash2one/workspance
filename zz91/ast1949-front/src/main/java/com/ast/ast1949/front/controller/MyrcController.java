package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.company.CompanyService;
import com.zz91.util.velocity.AddressTool;

@Controller
public class MyrcController extends BaseController {

	final static int INEX_PRODUCTS_NUM = 8;

	@Resource
	private CompanyService companyService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request) throws Exception {
		return new ModelAndView("redirect:"+AddressTool.getAddress("myrc")+"/index.htm");
//			out.put(FrontConst.MYRC_SUBTITLE, "生意管家");
//			SsoUser sessionUser = getCachedUser(request);
//			out.put("membershipLable", CategoryFacade.getInstance().getValue(
//					sessionUser.getMembershipCode()));
//			//功能求列表
//			List<ProductsDto> list=productsService.queryProductsByMainCode(null, null, 10);
//			out.put("sdList", list);
//			Integer num = inquiryService.countUnviewedInquiry(null, sessionUser.getAccount(), sessionUser.getCompanyId());
//			out.put("unviewedRecordNumber", num != null ? num : 0);
//			//计算发布了多少供求信息
//			Integer publishedProducts = productsService.countProductsByCompanyId(sessionUser.getCompanyId());
//			out.put("publishedProducts", publishedProducts != null ? publishedProducts : 0);
//
//			Date dt = productsService.queryMaxRefreshTimeByCompanyId(sessionUser.getCompanyId());
//			if (dt != null) {
//				int days = DateUtil.getIntervalDays(new Date(), dt);
//				out.put("days", days);
//			}
//			
//			// 最新话题
//			List<BbsPostDTO> frontBbsPostList = bbsService.queryBbsPostByStatistics(null,
//					"a.gmt_modified", "desc", 9);
//			out.put("frontBbsPostList", frontBbsPostList);
//			out.put("today", DateUtil.toString(new Date(), "yyyy-MM-dd"));
//			
//			CrmCompanySvr svr=crmCompanySvrService.queryZstSvr(sessionUser.getCompanyId());
//			if(svr!=null){
//				long start = DateUtil.getMillis(svr.getGmtStart());
//				long end = DateUtil.getMillis(svr.getGmtEnd());
//				long now = DateUtil.getMillis(new Date());
//				out.put("membershipPercent", (300 * (now - start) / (end - start)));
//				out.put("membershipFromDate", DateUtil.toString(svr.getGmtStart(), "yyyy-MM-dd"));
//				out.put("membershipEndDate", DateUtil.toString(svr.getGmtEnd(), "yyyy-MM-dd"));
//			}
			
//			CompanyMembershipDO c = companyMembershipService
//					.queryCompanyMembershipByCompanyId(sessionUser.getCompanyId());
//			if (c != null) {
//				long start = DateUtil.getMillis(c.getDateFrom());
//				long end = DateUtil.getMillis(c.getDateEnd());
//				long now = DateUtil.getMillis(new Date());
//				out.put("membershipPercent", (300 * (now - start) / (end - start)));
//				out.put("membershipFromDate", DateUtil.toString(c.getDateFrom(), "yyyy-MM-dd"));
//				out.put("membershipEndDate", DateUtil.toString(c.getDateEnd(), "yyyy-MM-dd"));
//			}
			
			//查找商务助理
//			AdminUserDO assistant = adminUserService.queryAdminUserByCompanyId(sessionUser.getCompanyId());
//			out.put("assistant", assistant);

//			List<SubscribeDO> subscribeList = subscribeService.querySubscribeByCompanyIdAndSubscribeType(sessionUser.getCompanyId(), "0");
//			out.put("subscribeList", subscribeList);
//			
			//最新商机
//			if (company != null) {
//				//获取Myrc小秘书
//				List<SubscribeDO> list2 = subscribeService
//						.querySubscribeByCompanyIdAndSubscribeType(company.getId(), "0");
//				
//				if (list2.size() > 0) {
//					String key = list2.get(0).getKeywords();
//					ProductsListItemForFrontDTO searchDto = new ProductsListItemForFrontDTO();
//					//			 关键字索引条件:dto.setKeywords(keywords);
//					searchDto.setKeywords(key);
//					PageDto<SearchSupport> pager = new PageDto<SearchSupport>();
//					pager.setPageSize(10);
//					pager.setStartIndex(0);
//					pager = productSearchService.search(searchDto, pager);
//					out.put("results", pager.getRecords());
//					//					ProductsSearchForFrontDTO searchDTO =new ProductsSearchForFrontDTO();
//					//					searchDTO.setKeywords(key);
//					//					searchDTO.getPageDto().setStartIndex(0);
//					//					searchDTO.getPageDto().setPageSize(10);
//					// 搜索，并获得总记录数
//					//					SearchProductsService search = new SearchProductsServiceImpl();
//					//					SearchProductsService search = searchEngineRpc
//					//							.buildRemoteService(SearchProductsService.class);
//					//SearchProductsService search= new SearchProductsServiceImpl();
//					//					ProductsListForFrontDTO results = searchProductsService.Search(searchDTO);
//					//					out.put("results", results);
//				}
//			}
//			return null;
//		} else {
//			out.put("js", "alert('本站为测试版，新的生意管家后期将开放。请您在老站点登录！');"
//					+ "window.location.href='http://www.zz91.com/cn/login.asp';");
//			return new ModelAndView("js");
//		}

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView changepassword(Map<String, Object> out, HttpServletRequest request) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("myrc")+"/changepassword.htm");
//		//		leftMenu(request, out);
//		out.put(FrontConst.MYRC_SUBTITLE, "更改密码");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView changepassword(Map<String, Object> model, HttpServletRequest request,
			String originalPassword, String newPassword, String verifyPassword) throws IOException {
		return new ModelAndView("redirect:"+AddressTool.getAddress("myrc")+"/changepassword.htm");
//		SsoUser sessionUser =getCachedUser(request);
//		ExtResult result = new ExtResult();
//		try {
//			result.setSuccess(companyAccountService.changePassword(sessionUser.getAccount(), originalPassword, newPassword, verifyPassword));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (AuthorizeException e) {
//			result.setData(e.getMessage());
//		}

//		try {
////			authService.changePassword(sessionUser.getAccount(), originalPassword, newPassword, verifyPassword);
////			companyContactsService.updatePasswordByAccount(sessionUser.getAccount(), newPassword);
//			result.setSuccess(true);
//		} catch (NoSuchAlgorithmException e) {} catch (UnsupportedEncodingException e) {} catch (AuthorizeException e) {
//			result.setData(e.getMessage());
//		}
//		return printJson(result, model);
	}

	@RequestMapping(method = RequestMethod.GET)
	public void changeaccountname(Map<String, Object> out, HttpServletRequest request) {
//		AuthUser user = getCachedAuthUser(request);
		//TODO 修改用户名，实际是修改auth_user的account字段，username字段为注册时填写信息，永远无法修改
//		SessionUser sessionUser = getCachedUser(request);
//		boolean isEmailUserName = StringUtils.isEmail(user.getUsername());
//		boolean isEmptyAccount = StringUtils.isEmpty(user.getAccount());
//		boolean isChineseUserName = false;
//		try {
//			isChineseUserName = StringUtils.isContainCNChar(user.getUsername());
//		} catch (UnsupportedEncodingException e) {}
//		out.put("isEmailUserName", isEmailUserName);
//		out.put("isEmptyAccount", isEmptyAccount);
//		out.put("isChineseUserName", isChineseUserName);
//		out.put("currentUserName", isEmptyAccount ? user.getUsername() : user.getAccount());
//		out.put(FrontConst.MYRC_SUBTITLE, "修改用户帐户名");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView changeaccountname(Map<String, Object> model, HttpServletRequest request,
			String newUserName) throws IOException {
		return new ModelAndView("redirect:"+AddressTool.getAddress("myrc")+"/changeaccountname.htm");
//		ExtResult result = new ExtResult();
//		result.setSuccess(false);
//		if (StringUtils.isNotEmpty(newUserName)) {
//			try {
//				SsoUser sessionUser = getCachedUser(request);
//				authService.resetAccount(sessionUser.getAccount(), newUserName);
//				result.setSuccess(true);
//			} catch (Exception e) {}
//		}
//		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView loadesite(HttpServletRequest request, Map<String, Object> out, Integer id) throws IOException{
		ExtResult result = new ExtResult() ;
		Company company = companyService.queryDomainOfCompany(id);
		result.setData(company);
		return printJson(result, out);
	}
	
}
