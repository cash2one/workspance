package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.company.SubscribeService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

@Controller
public class MyrcController extends BaseController {

	final static int INEX_PRODUCTS_NUM = 8;

	@Resource
	private AuthService authService;
	@Resource
	private ProductsService productsService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Resource
	private CrmCsService crmCsService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private MyrcService myrcService;
	@Resource
	private SubscribeService subscribeService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request) throws Exception {
			// 从session获取用户信息
			SsoUser sessionUser = getCachedUser(request);
			//查询是否开通商铺服务
			myrcService.initMyrc(out, sessionUser.getCompanyId());
			// 判断是否信息确实，进入公司修改页
//			if(StringUtils.isEmpty(companyService.queryCompanyNameById(sessionUser.getCompanyId()))){
//				return new ModelAndView("redirect:/mycompany/updateCompany.htm?firstLogin=1");
//			}
			//最新供求
			List<SubscribeDO> list = subscribeService.querySubscribeByCompanyIdAndSubscribeType(sessionUser
					.getCompanyId(), "0");
			if(list.size()>0){
				List<ProductsDto> list2=productsService.queryVipProductsForMyrc(list.get(0).getKeywords(), 6);
				out.put("list2", list2);
			}else {
				Company company=companyService.queryCompanyById(sessionUser.getCompanyId());
				List<ProductsDto> list2=productsService.queryNewestVipProducts(null, company.getIndustryCode(), 6);
				out.put("list2", list2);
			}
			
			out.put(FrontConst.MYRC_SUBTITLE, "生意管家");
			out.put("membershipLable", CategoryFacade.getInstance().getValue(
					sessionUser.getMembershipCode()));
			
			Integer num = inquiryService.countUnviewedInquiry(null, sessionUser.getAccount(), sessionUser.getCompanyId());
			out.put("unviewedRecordNumber", num != null ? num : 0);
			//计算发布了多少供求信息
			Integer publishedProducts = productsService.countProductsByCompanyId(sessionUser.getCompanyId());
			out.put("publishedProducts", publishedProducts != null ? publishedProducts : 0);

			Date dt = productsService.queryMaxRefreshTimeByCompanyId(sessionUser.getCompanyId());
			if (dt != null) {
				int days = DateUtil.getIntervalDays(new Date(), dt);
				out.put("days", days);
			}
			
			// 最新话题
			//List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(9);
			//out.put("frontBbsPostList", frontBbsPostList);
			out.put("today", DateUtil.toString(new Date(), "yyyy-MM-dd"));
		
			CrmCompanySvr svr=crmCompanySvrService.queryZstSvr(sessionUser.getCompanyId());
			if(svr!=null){
				long start = DateUtil.getMillis(svr.getGmtStart());
				long end = DateUtil.getMillis(svr.getGmtEnd());
				long now = DateUtil.getMillis(new Date());
				if(end-start==0){
					out.put("membershipPercent", 100);
				}else{
					out.put("membershipPercent", (100 * (now - start) / (end - start)));
				}
				out.put("membershipFromDate", DateUtil.toString(svr.getGmtStart(), "yyyy-MM-dd"));
				out.put("membershipEndDate", DateUtil.toString(svr.getGmtEnd(), "yyyy-MM-dd"));

				CrmCs cs=crmCsService.queryCsOfCompany(sessionUser.getCompanyId());
				if(cs!=null && StringUtils.isNotEmpty(cs.getCsAccount())) {
					
					String csinfo=ParamUtils.getInstance().getValue("cs_info", cs.getCsAccount());
					if(csinfo!=null){
						String[] info=csinfo.split(",");
						if(info.length>=1){
							out.put("cs_name", info[0]);
						}
						if(info.length>=2){
							out.put("cs_phone", info[1]);
						}
						if(info.length>=3){
							out.put("cs_email", info[2]);
						}
					}
				}
				
				out.put("domainInfo", companyService.queryDomainOfCompany(sessionUser.getCompanyId()));
			}
			
			//非高会，显示普会信息
			CompanyAccount myaccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
			out.put("myaccount", myaccount);
			
			
//			List<SubscribeDO> subscribeList = subscribeService.querySubscribeByCompanyIdAndSubscribeType(sessionUser.getCompanyId(), "0");
//			out.put("subscribeList", subscribeList);
			
			//判断激活状态
			if(!companyValidateService.isValidate(sessionUser.getAccount())){
				out.put("compValidate", 1);
			}
			
			// 电话号码配置都去
			out.put("serviceTel", ParamUtils.getInstance().getValue("myrc_config", "member_service_tel"));
			out.put("upTel", ParamUtils.getInstance().getValue("myrc_config", "member_up_tel"));
			
			return null;
	}

	@RequestMapping
	public void changepassword(Map<String, Object> out, HttpServletRequest request, String error) {
		SsoUser sessionUser =getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		Company company = companyService.queryCompanyById(sessionUser.getCompanyId());
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		out.put("company", company);
		out.put("companyAccount", companyAccount);
		
		out.put(FrontConst.MYRC_SUBTITLE, "更改密码");
		
		out.put("error", error);
	}

	@RequestMapping
	public ModelAndView savepassword(Map<String, Object> model, HttpServletRequest request,
			String originalPassword, String newPassword, String verifyPassword) throws IOException {
		SsoUser sessionUser =getCachedUser(request);
		boolean result=false;
		try {
			result=companyAccountService.changePassword(sessionUser.getAccount(), originalPassword, newPassword, verifyPassword);
			if(!result){
				model.put("error", "1");
			}else{
				model.put("error", "0");
			}
		} catch (NoSuchAlgorithmException e) {
			model.put("error","1");
		} catch (AuthorizeException e) {
			model.put("error","1");
		}
		return new ModelAndView("changepassword");
	}

	@RequestMapping
	public void changeaccountname(Map<String, Object> out, HttpServletRequest request) {
//		AuthUser user = getCachedAuthUser(request);
		//TODO 修改用户名，实际是修改auth_user的account字段，username字段为注册时填写信息，永远无法修改
		SsoUser sessionUser = getCachedUser(request);
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		out.put("companyAccount", companyAccount);
		
		sessionUser = companyAccountService.initSessionUser(sessionUser.getAccount());
		boolean isEmailUserName = StringUtils.isEmail(companyAccount.getAccount());
		boolean isEmptyAccount = StringUtils.isEmpty(sessionUser.getAccount());
		boolean isChineseUserName = false;
		out.put("sessionUser", sessionUser);
		try {
			isChineseUserName = StringUtils.isContainCNChar(companyAccount.getAccount());
		} catch (UnsupportedEncodingException e) {}
		
		
		if(isChineseUserName||isEmailUserName){
			if(isEmptyAccount){
				out.put("enableModified", 1);
			}
		}
		
		
		
	   /*if(isChineseUserName && isEmailUserName && isEmptyAccount){
			out.put("enableModified", 1);
		}
	   */
	}

	@RequestMapping
	public ModelAndView saveaccountname(Map<String, Object> model, HttpServletRequest request,
			String newUserName) throws IOException {
		ExtResult result = new ExtResult();
		
		if (StringUtils.isNotEmpty(newUserName)) {
			try {
				
				SsoUser sessionUser = getCachedUser(request);				 
				Integer count = authService.resetAccount(sessionUser.getAccount(), newUserName);
				if(count>0){
					result.setSuccess(true);
				}else{
					result.setSuccess(false);
				}
			} catch (Exception e) {}
		}
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView loadesite(HttpServletRequest request, Map<String, Object> out, Integer id) throws IOException{
		ExtResult result = new ExtResult() ;
		Company company = companyService.queryDomainOfCompany(id);
		result.setData(company);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView loadaccount(HttpServletRequest request, Map<String, Object> out, Integer id) throws IOException{
		ExtResult result = new ExtResult() ;
		CompanyAccount account=companyAccountService.queryAccountByAccount(getCachedUser(request).getAccount());
		result.setData(account);
		return printJson(result, out);
	}
	//新添加的
	@RequestMapping
	public ModelAndView queryCategoryCompanyPrice(Map<String, Object> map) throws IOException {
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceService.queryCategoryCompanyPrice();
		return printJson(list, map);
	}
	
	@RequestMapping
	public ModelAndView queryCategoryCompanyPriceByCode(String code, Map<String, Object> map)
			throws IOException {
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceService
				.queryCategoryCompanyPriceByCode(code);
		return printJson(list, map);
	}
	
	@RequestMapping
	public void ajaxUpload(Map<String, Object> out, String model,
			String filetype, String control) {
		out.put("filetype", filetype);
		out.put("model", model);
		out.put("control", control);
	}
	
	@RequestMapping
	public ModelAndView checkUsername(Map<String, Object> model, String account)
			throws IOException {
		ExtResult result=new ExtResult();
		if (StringUtils.isNotEmpty(account)) {
			Integer userCount = authService
					.countUserByAccount(account);
			if (userCount == null || userCount == 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, String url, Map<String, Object> model)
			throws IOException {
		ExtResult extResult = new ExtResult();
		
		SsoUtils.getInstance().logout(request, response, null);
		extResult.setSuccess(true);
		if (StringUtils.isEmpty(url)) {
			extResult.setData("index.htm");
		} else {
			extResult.setData(url);
		}
		return printJson(extResult, model);
	}
	
	@RequestMapping
	public ModelAndView dologout(HttpServletRequest request, Map<String, Object> out, HttpServletResponse response){
		SsoUtils.getInstance().logout(request, response, null);
		//return new ModelAndView(new RedirectView(AddressTool.getAddress("front")));
	     return new ModelAndView("logout/index");
	}
	
	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request, Map<String, Object> out, 
			String success, String data){
		if(StringUtils.isEmpty(data)){
			data="{}";
		}
		try {
//			data=URLDecoder.decode(data, HttpUtils.CHARSET_UTF8);
			data=StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}
	
	@RequestMapping
	public ModelAndView unViewedMessage(HttpServletRequest request, Map<String, Object> out){
		SsoUser sessionUser=getCachedUser(request);
		Integer num = inquiryService.countUnviewedInquiry(null, sessionUser.getAccount(), sessionUser.getCompanyId());
		if(num==null){
			num=0;
		}
		out.put("num", num);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView sendValidate(HttpServletRequest request, Map<String, Object> out){
		companyValidateService.sendValidateByEmail(getCachedUser(request).getAccount(), null);
		return null;
	}
	
}
