/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-19
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.exception.RegisterException;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.ast.ast1949.service.yuanliao.YuanliaoPicService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.tags.TagsUtils;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Controller
public class InquiryController extends BaseController {

	@Autowired
	private InquiryService inquiryService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
//	@Resource
//	private AuthService authService;
//	@Resource
//	private CompanyUploadFileService companyUploadFileService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CategoryService categoryService;
	@Resource
    private ProductAddPropertiesService productAddPropertiesService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private YuanLiaoService yuanliaoService;
	@Resource
	private YuanliaoPicService yuanliaoPicService;
	
	
	final static String TOKEN_KEY="regist_token";

	// private final static String MAIL_SUBJECT = "您有一封邮件没有查收";
	// private final static int MAIL_PRIORITY = 20;

	@Deprecated
	@RequestMapping
	public ModelAndView createInquiry(Map<String, Object> model,
			HttpServletRequest request, Inquiry inquiry) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		// 如果session失效，配置权限控制
		if (ssoUser == null) {
			result.setData("sessionTimeOut");
			return printJson(result, model);
		}

		inquiry.setSenderAccount(ssoUser.getAccount());

		int i = inquiryService.inquiryByUser(inquiry, ssoUser.getCompanyId());

		if (i > 0)
			result.setSuccess(true);
		return printJson(result, model);
	}

	@Deprecated
	@RequestMapping
	public ModelAndView inquiry(Map<String, Object> out,
			HttpServletRequest request, Inquiry inquiry) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		do {
			// 如果session失效，配置权限控制
			if (ssoUser == null) {
				result.setData("sessionTimeOut");
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());

			int i = inquiryService.inquiryByUser(inquiry,
					ssoUser.getCompanyId());
			if (i > 0) {
				result.setSuccess(true);
			}

		} while (false);
		out.put("result", result);
		return new ModelAndView("submit");
	}

	@RequestMapping
	public ModelAndView doProduct(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			Inquiry inquiry, Integer productId, String mainCode,
			String destUrl, String verifyCode, String verifyCodeKey) {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);

		out.put("destUrl", destUrl);

		do {
			String vcode = String.valueOf(SsoUtils.getInstance().getValue(
					request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode)
					|| !verifyCode.equalsIgnoreCase(vcode)) {
				result.setData("您输入的验证码有错误！");
				break;
			}

			// 如果session失效，配置权限控制
			if (ssoUser == null) {
				result.setData("sessionTimeOut");
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());

			CompanyAccount account = companyAccountService
					.queryAccountByProductId(productId);
			inquiry.setReceiverAccount(account.getAccount());
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_DEFAULT);
			inquiry.setBeInquiredId(productId);

			int i = inquiryService.inquiryByUser(inquiry,
					ssoUser.getCompanyId());
			if (i > 0) {
				// TODO 加邮件是否发送的判断（从生意管家设置）
				// Map<String,Object> map = new HashMap<String,Object>();
				// map.put("content", inquiry.getContent());
				//
				// if("1".equals(account.getIsUseBackEmail())){
				// account.setEmail(account.getBackEmail());
				// }

				// MailUtil.getInstance().sendMail("ZZ91再生网供求留言 "+inquiry.getTitle(),
				// account.getEmail(), "zz91", "zz91-inquiry", map,
				// MailUtil.PRIORITY_DEFAULT);
				out.put("success", "true");
				out.put("data", "{}");
				return new ModelAndView();
			}

		} while (false);
		out.put("content", inquiry.getContent());

		out.put("error", "1");
		out.put("productId", productId);
		out.put("mainCode", mainCode);

		return new ModelAndView("redirect:/inquiry/product.htm");
	}

	@RequestMapping
	public ModelAndView product(Map<String, Object> out, Integer productId,
			String mainCode, String destUrl, String content,
			HttpServletRequest request, String error) {
		SsoUser ssoUser = getCachedUser(request);

		Inquiry inquiry = new Inquiry();
		if (ssoUser == null) {
			out.put("success", "false");
			return new ModelAndView("redirect:" + destUrl);
		} else {
			ProductsDO product = productsService.queryProductsById(productId);
			String str = "";
			if (product != null) {
				str = product.getTitle();
			}
			String title = "我对" + str + "产品感兴趣";
			inquiry.setTitle(title);
			out.put("inquiry", inquiry);
		}

		out.put("destUrl", destUrl);
		out.put("productId", productId);
		out.put("mainCode", mainCode);
		out.put("error", error);
		return null;
	}

	@RequestMapping
	public ModelAndView doCompany(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			Inquiry inquiry, Integer id, String mainCode, String destUrl,
			String verifyCode, String verifyCodeKey) {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		out.put("destUrl", destUrl);
		do {

			String vcode = String.valueOf(SsoUtils.getInstance().getValue(
					request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);

			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode)
					|| !verifyCode.equalsIgnoreCase(vcode)) {
				result.setData("您输入的验证码有错误！");
				break;
			}

			if (ssoUser == null) {
				result.setData("sessionTimeOut");
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());
			CompanyAccount account = companyAccountService
					.queryAccountByCompanyId(id);
			inquiry.setReceiverAccount(account.getAccount());
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY);
			inquiry.setBeInquiredId(id);
			int i = inquiryService.inquiryByUser(inquiry,
					ssoUser.getCompanyId());
			if (i > 0) {
				out.put("success", "true");
				out.put("data", "{}");
				return new ModelAndView();
			}

		} while (false);
		out.put("content", inquiry.getContent());
		out.put("error", "1");
		out.put("id", id);
		out.put("mainCode", mainCode);

		return new ModelAndView("redirect:/inquiry/company.htm");
	}

	@RequestMapping
	public ModelAndView company(HttpServletRequest request,
			Map<String, Object> out, Integer id, String mainCode,
			String destUrl, String content, String error) {
		SsoUser ssoUser = getCachedUser(request);
		Inquiry inquiry = new Inquiry();
		if (ssoUser == null) {
			out.put("success", "false");
			return new ModelAndView("redirect:" + destUrl);
		} else {
			// Company company=companyService.queryCompanyById(id);
			// String title=company.getName();
			inquiry.setTitle("您好，我对你的产品感兴趣！");
			out.put("inquiry", inquiry);
		}
		out.put("destUrl", destUrl);
		out.put("id", id);
		out.put("mainCode", mainCode);
		out.put("error", error);
		return null;
	}

	@Deprecated
	@RequestMapping
	public void leaveMsgSuces(Map<String, Object> out, String productId) {
		out.put("productId", productId);
	}

	@Deprecated
	@RequestMapping
	public ModelAndView doLeaveMsgSuces(Map<String, Object> out, String destUrl) {
		return new ModelAndView("redirect:" + destUrl);
	}

	@Deprecated
	@RequestMapping
	public ModelAndView inquiryToCompany(Map<String, Object> out,
			HttpServletRequest request, Inquiry inquiry, Integer companyId) {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		do {
			// 如果session失效，配置权限控制
			if (ssoUser == null) {
				result.setData("sessionTimeOut");
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY);
			int i = inquiryService.inquiryByUser(inquiry,
					ssoUser.getCompanyId());
			if (i > 0) {
				result.setSuccess(true);
				// CompanyDto companyDto =
				// companyService.queryCompanyDetailById(companyId);
				// Map<String,Object> map = new HashMap<String,Object>();
				// //组装参数
				// MailUtil.getInstance().sendMail(MAIL_SUBJECT,
				// companyDto.getAccount().getEmail(), "" , map, MAIL_PRIORITY);
			}

		} while (false);
		out.put("result", result);
		return new ModelAndView("submit");
	}

	@RequestMapping
	public ModelAndView doCompanyPrice(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			Inquiry inquiry, Integer id, String destUrl, String verifyCode,
			String verifyCodeKey) {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		out.put("destUrl", destUrl);
		do {
			String vcode = String.valueOf(SsoUtils.getInstance().getValue(
					request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);

			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode)
					|| !verifyCode.equalsIgnoreCase(vcode)) {
				result.setData("您输入的验证码有错误！");
				break;
			}

			// 如果session失效，配置权限控制
			if (ssoUser == null) {
				result.setData("sessionTimeOut");
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());
			Integer companyId = companyPriceService.queryCompanyPriceById(id)
					.getCompanyId();
			if (companyId == null) {
				break;
			}
			CompanyAccount account = companyAccountService
					.queryAccountByCompanyId(companyId);
			if (account == null) {
				break;
			}
			inquiry.setReceiverAccount(account.getAccount());
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY_PRICE);
			inquiry.setBeInquiredId(id);

			int i = inquiryService.inquiryByUser(inquiry,
					ssoUser.getCompanyId());
			if (i > 0) {
				out.put("success", "true");
				out.put("data", "{}");
				return new ModelAndView();
			}

		} while (false);
		out.put("content", inquiry.getContent());

		out.put("error", "1");
		out.put("id", id);

		return new ModelAndView("redirect:/inquiry/companyPrice.htm");
	}

	@RequestMapping
	public ModelAndView companyPrice(Map<String, Object> out, Integer id,
			String destUrl, String content, HttpServletRequest request,
			String error) {
		SsoUser ssoUser = getCachedUser(request);
		Inquiry inquiry = new Inquiry();
		if (ssoUser == null) {
			out.put("success", "false");
			return new ModelAndView("redirect:" + destUrl);
		} else {
			CompanyPriceDO cpDO = companyPriceService.queryCompanyPriceById(id);
			String str = "";
			if (cpDO != null) {
				str = cpDO.getTitle();
			}
			String title = "我对" + str + "产品感兴趣";
			inquiry.setTitle(title);
			out.put("inquiry", inquiry);
		}

		out.put("destUrl", destUrl);
		out.put("id", id);
		out.put("error", error);
		return null;
	}

	@RequestMapping
	public ModelAndView questionProduct(HttpServletRequest request,
			Map<String, Object> out, Integer productId,String destUrl, String content) {
		out.put("destUrl", destUrl);
		do {
			
			Inquiry inquiry = new Inquiry();
			ProductsDO product = productsService.queryProductsById(productId);
			String companyName = companyService.queryCompanyNameById(product
					.getCompanyId());
			String contact = companyAccountService.queryAccountByCompanyId(
					product.getCompanyId()).getContact();
			String str = "";
			if (product != null) {
				str = product.getTitle();
			}
			String title = "我对" + str + "产品感兴趣";
			inquiry.setTitle(title);
			out.put("inquiry", inquiry);
			out.put("productId", productId);
			out.put("companyName", companyName);
			out.put("contact", contact);
			if(getCachedUser(request)!=null){
				out.put("companyId", getCachedUser(request).getCompanyId());
				String checkInfo=companyService.validateCompanyInfo(getCachedUser(request)) ;
				if(checkInfo!=null && checkInfo.equals("1")){
					out.put("js", "showMessage();");
				}else{
					out.put("js", "loginsucess();");	
				}
				
			}
		} while (false);
		out.put("referUrl", request.getHeader("referer"));
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView questionCompanyPrice(HttpServletRequest request,
			Map<String, Object> out, Integer companyPriceId,String destUrl, String content) {
		out.put("destUrl", destUrl);
		do {
			Inquiry inquiry = new Inquiry();
			CompanyPriceDO cpDO = companyPriceService.queryCompanyPriceById(companyPriceId);
			
			String str = "";
			if (cpDO != null) {
				str = cpDO.getTitle();
			}
			String title = "我对" + str + "产品感兴趣";
			String companyName = companyService.queryCompanyNameById(cpDO
					.getCompanyId());
			String contact = companyAccountService.queryAccountByCompanyId(
					cpDO.getCompanyId()).getContact();
			inquiry.setTitle(title);
			out.put("inquiry", inquiry);
			out.put("companyPriceId", companyPriceId);
			out.put("companyName", companyName);
			out.put("contact", contact);
			if(getCachedUser(request)!=null){
				out.put("js", "loginsucess();");
			}
		} while (false);
		out.put("referUrl", request.getHeader("referer"));
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView questionCompany(HttpServletRequest request,
			Integer companyId, Map<String, Object> out,String destUrl) {
		out.put("destUrl", destUrl);
		do {
			Inquiry inquiry = new Inquiry();
			String companyName = companyService.queryCompanyNameById(companyId);
			String contact = companyAccountService.queryAccountByCompanyId(companyId).getContact();
			String title = "您好，我对你的产品感兴趣！";
			inquiry.setTitle(title);
			out.put("inquiry", inquiry);
			out.put("companyId", companyId);
			out.put("companyName", companyName);
			out.put("contact", contact);
			if(getCachedUser(request)!=null){
				out.put("js", "loginsucess();");
			}
		} while (false);
		out.put("referUrl", request.getHeader("referer"));
		return new ModelAndView();
	}
	
	
	
	final static Map<Integer, String> PRODUCT_AND_COMPANY_MAP = new HashMap<Integer, String>();
	static{
		PRODUCT_AND_COMPANY_MAP.put(1, "产品规格、型号：我想了解产品规格、型号，能否发一份详细资料给我参考，谢谢！");
		PRODUCT_AND_COMPANY_MAP.put(2, "价格：请问贵公司对该产品的报价是多少呢？");
		PRODUCT_AND_COMPANY_MAP.put(3, "数量：我想了解产品每月的供应或者求购的数量是多少？");
		PRODUCT_AND_COMPANY_MAP.put(4, "颜色：我想了解产品的颜色有哪些？");
		PRODUCT_AND_COMPANY_MAP.put(5, "图片：我对贵公司的产品非常感兴趣，能否把产品图片发给我参考？");
	}
	
	final static Map<Integer, String> PRICE_MAP = new HashMap<Integer, String>();
	static{
		PRICE_MAP.put(1, "产品规格、型号：我想了解产品规格、型号，能否发一份详细资料给我参考，谢谢！");
		PRICE_MAP.put(2, "价格：我对贵公司的产品非常感兴趣！针对您在ZZ91再生网发布的报价，能再议吗？");
		PRICE_MAP.put(3, "数量：我想了解产品每月的供应或者求购的数量是多少？");
		PRICE_MAP.put(4, "颜色：我想了解产品的颜色有哪些？");
		PRICE_MAP.put(5, "图片：我对贵公司的产品非常感兴趣，能否把产品图片发给我参考？");
	}

	@RequestMapping
	public ModelAndView doQuestion(HttpServletRequest request,
			Map<String, Object> out, HttpServletResponse response,
			Inquiry inquiry, Integer productId,String destUrl,Integer companyId,Integer companyPriceId,String info) {
		do {
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser == null) {
				out.put("js", "parent.login();");
				break;
			}
			
			inquiry.setSenderAccount(ssoUser.getAccount());
			CompanyAccount account = new CompanyAccount();
			if(productId!=null){
				// 设置内容
				inquiry.setContent(getContentFix(info,PRODUCT_AND_COMPANY_MAP)+ "<br/>" + inquiry.getContent());
				
				// 对供求寻盘
				account = companyAccountService.queryAccountByProductId(productId);
				if (account == null) {
					break;
				}
				inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_DEFAULT);
				inquiry.setBeInquiredId(productId);
			}else if(companyId!=null){
				// 设置内容
				inquiry.setContent(getContentFix(info,PRODUCT_AND_COMPANY_MAP)+ "<br/>" + inquiry.getContent());
				// 对公司寻盘
				account = companyAccountService.queryAccountByCompanyId(companyId);
				if (account == null) {
					break;
				}
				inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY);
				inquiry.setBeInquiredId(companyId);
			}else if(companyPriceId!=null){
				// 设置内容
				inquiry.setContent(getContentFix(info,PRICE_MAP)+ "<br/>" + inquiry.getContent());
				// 对企业报价寻盘
				CompanyPriceDO cpDO = companyPriceService.queryCompanyPriceById(companyPriceId);
				account = companyAccountService.queryAccountByCompanyId(cpDO.getCompanyId());
				if (account == null) {
					break;
				}
				inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY_PRICE);
				inquiry.setBeInquiredId(companyId);
			}else{
				break;
			}
			inquiry.setReceiverAccount(account.getAccount());
			int i = inquiryService.inquiryByUser(inquiry,
					ssoUser.getCompanyId());
			if (i > 0) {
				out.put("js", "parent.questionsucess('"+destUrl+"');");
			} else {
				break;
			}
		} while (false);
		return new ModelAndView("inquiry/questionJS");
	}

	@RequestMapping
	public ModelAndView question_suc(Map<String, Object>out,String destUrl) {
		out.put("destUrl", destUrl);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doInquiryLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String account, String password, String vcode,
			String t, String regfrom, String forUrl) {
		out.put("destUrl", destUrl);
		HttpUtils.getInstance().setCookie(response, AstConst.COOKIE_ACCOUNT,
				account, AstConst.COOKIE_DOMAIN, AstConst.COOKIE_AGE);
		SsoUser user = null;
		try {
			user = SsoUtils.getInstance().validateUser(response, account,
					password, AstConst.COOKIE_AGE,
					HttpUtils.getInstance().getIpAddr(request));
		} catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			out.put("errorCode", e.getMessage());
		} catch (IOException e) {
		}

		if (user != null) {
			
			setSessionUser(request, user);
			
			String checkInfo = companyService.validateCompanyInfo(user);
			if(checkInfo!=null && checkInfo.equals("1")){
				out.put("js", "parent.showMessage();");
			}else{                    
				out.put("js", "parent.loginsucess();");	
			}
			
		}else{
			out.put("js", "parent.loginerr();");
		}
		return new ModelAndView("inquiry/questionJS");
	}
	
	@RequestMapping
	public ModelAndView doInquiryReg(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl,Company company,CompanyAccount companyAccount, String password,
			String passwordConfirm, String verifyCodeKey, String verifyCode) throws Exception  {
		out.put("destUrl", destUrl);
		out.put("tab", "register");

		String error = "2";
		//地址地区中文
		String address = "";
		do {
			String vcode = String.valueOf(SsoUtils.getInstance().getValue(
					request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode)
					|| !verifyCode.equalsIgnoreCase(vcode)) {
				error = "3";
				break;
			}
			company.setRegfromCode("10031003");
			try {
				String username = companyAccountService.registerUser(
						companyAccount.getAccount(), companyAccount.getEmail(),
						password, passwordConfirm, companyAccount, company,
						HttpUtils.getInstance().getIpAddr(request));

				if (StringUtils.isEmpty(username)) {
					break;
				}
				companyValidateService.sendValidateByEmail(companyAccount.getAccount(), companyAccount.getEmail());
				// 自动登录
				SsoUser ssoUser = null;
				try {
					ssoUser = SsoUtils.getInstance().validateUser(response,companyAccount.getAccount(), password, null,HttpUtils.getInstance().getIpAddr(request));
				} catch (AuthorizeException e) {
				}
				setSessionUser(request, ssoUser);
				// 增加积分
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser.getCompanyId(), null, "base_register", null,null, null));
			} catch (RegisterException e) {
				break;
			} catch (NoSuchAlgorithmException e) {
				break;
			}
			
			if(company!=null&&StringUtils.isNotEmpty(company.getAreaCode())){
				String code = company.getAreaCode();
				if(company.getAreaCode().length()>=8){
					address +=CategoryFacade.getInstance().getValue(code.substring(0,8));
				}
				if(company.getAreaCode().length()>=12){
					address +=CategoryFacade.getInstance().getValue(code.substring(0,12));
				}
				if(company.getAreaCode().length()>=16){
					address +=CategoryFacade.getInstance().getValue(code.substring(0,16));
				}
				out.put("addressLabel", address);
			}

//			out.put("account", companyAccount.getAccount());
//			out.put("destUrl", destUrl);
			out.put("js", "parent.regstep2();"
					+"parent.jQuery(\"#addressLabel\").html(\""+address+"\");"
					+"parent.jQuery(\"#regContact\").html(\""+companyAccount.getContact()+"\");"
					+"parent.jQuery(\"#addressLabelInput\").val(\""+address+"\");"
			        +"parent.jQuery(\"#wxAccount\").val(\""+companyAccount.getAccount()+"\");");
		} while (false);
		out.put("company", company);
		out.put("companyAccount", companyAccount);
		out.put("destUrl", destUrl);
		if("3".equals(error)){
			out.put("js", "alert('验证码错误！')");
		}
		
		return new ModelAndView("inquiry/questionJS");
	}
	
	@RequestMapping
	public ModelAndView reg_suc(HttpServletRequest request,HttpServletResponse response,Map<String, Object>out,String industryCode,String serviceCode,String address,String addressFix,String introduction,String destUrl,String account){
		SsoUser ssoUser = getCachedUser(request);
		do {
			if(ssoUser==null){
				break;
			}
			Company companyU = companyService.queryCompanyById(ssoUser.getCompanyId());
			if(companyU==null){
				break;
			}
			companyU.setAddress(addressFix+address);
			companyU.setIndustryCode(industryCode);
			companyU.setIntroduction(introduction);
			companyU.setServiceCode(serviceCode);
			companyService.updateCompanyByUser(companyU);
			out.put("js", "parent.regstep3();");
			return new ModelAndView("inquiry/questionJS");
		} while (false);
		out.put("js", "parent.regstep2();");
		return new ModelAndView("inquiry/questionJS");
	}
	
	@RequestMapping
	public ModelAndView inquiryApi(Map<String, Object> out, Integer productId,
			Integer companyId, Integer companyPriceId,Integer yuanliaoId) throws ParseException, IOException {
		do {
			Inquiry inquiry = new Inquiry();
			String title = "";
			if (productId != null) {
				out.put("productId", productId);
				ProductsDto productDetails=productsService.queryProductsDetailsById(productId);
				//根据查询公司类型
				String memberShip= companyService.queryMembershipOfCompany(productDetails.getProducts().getCompanyId());
				out.put("memberShip", memberShip);
				//查询400来电号码
				if(StringUtils.isNotEmpty(memberShip) && memberShip.equals("10051003")){
					Phone phone=phoneService.queryByCompanyId(productDetails.getProducts().getCompanyId());
					out.put("phone", phone);
					//查询人
					if(phone!=null){
						CompanyAccount account=companyAccountService.queryAccountByCompanyId(phone.getCompanyId());
						out.put("contact", account.getContact());
						out.put("companyId",account.getCompanyId());
					}
				}
				
				//获取主类别,辅助类别code
				out.put("manufactureList", categoryService.queryCategoriesByPreCode("1011"));
				if (productDetails == null) {
					break;
				}
				// 对供求寻盘
				/*ProductsDO product = productsService.queryProductsById(productId);*/
				String str = "";
				if (productDetails.getProducts() != null) {
					str = productDetails.getProducts().getTitle();
				}
				title = "我对" + str + "产品感兴趣";
				//去掉属性空格
				
				if (productDetails.getProducts().getAppearance() != null) {
					productDetails.getProducts().setAppearance(productDetails.getProducts().getAppearance().trim());
				}
				if (productDetails.getProducts().getColor() != null) {
					productDetails.getProducts().setColor(productDetails.getProducts().getColor().trim());
				}
				if (productDetails.getProducts().getImpurity() != null) {
					productDetails.getProducts().setImpurity(productDetails.getProducts().getImpurity().trim());
				}
				if (productDetails.getProducts().getManufacture() != null) {
					if(productDetails.getProducts().getManufacture().length() == 1) {
						productDetails.getProducts().setManufacture("");
					} else {
						productDetails.getProducts().setManufacture(productDetails.getProducts().getManufacture().trim());
					}
				}
				if (productDetails.getProducts().getOrigin() != null) {
					productDetails.getProducts().setOrigin(productDetails.getProducts().getOrigin().trim());
				}
				if (productDetails.getProducts().getSource() != null) {
					productDetails.getProducts().setSource(productDetails.getProducts().getSource().trim());
				}
				if (productDetails.getProducts().getSpecification() != null) {
					productDetails.getProducts().setSpecification(productDetails.getProducts().getSpecification().trim());
				}
				if (productDetails.getProducts().getUseful() != null) {
					productDetails.getProducts().setUseful(productDetails.getProducts().getUseful().trim());
				}
				if (productDetails.getProducts().getLocation() != null) {
					productDetails.getProducts().setLocation(productDetails.getProducts().getLocation().trim());
				} 
				//根据主类别不同显示不同的属性
				out.put("productDetails", productDetails);
				out.put("productPicList", productsPicService.queryProductPicInfoByProductsIdForFront(productId));
				
				//获取增加属性
				String isDel= "0";
				List<ProductAddProperties> productAddProperties = productAddPropertiesService.queryByProductId(productId, isDel);
				out.put("productAddProperties", productAddProperties);
				
				// 检查过期情况
				int interval = DateUtil.getIntervalDays(new Date(),productDetails.getProducts().getExpireTime());
				if (interval > 0) {
					out.put("expiredFlag", "1");
				}
				// 获取相关的标签信息 step6
				String tags = "";
				if (productDetails.getProducts().getTags() != null) {
					tags += productDetails.getProducts().getTags();
				}
				if (productDetails.getProducts().getTagsAdmin() != null) {
					tags += "," + productDetails.getProducts().getTagsAdmin();
				}
				
				Map<String, String> map = TagsUtils.getInstance().encodeTags(tags, "utf-8");
				for(String key:map.keySet()){
					map.put(key, CNToHexUtil.getInstance().encode(key));
				}
				out.put("tagsInfoList",	map);
				
				
			} else if (companyId != null) {
				// 对公司寻盘
				title = "您好，我对你的产品感兴趣！";
				Company company = companyService.queryCompanyById(companyId);
				if(company==null||"1".equals(company.getIsBlock())){
					break;
				}
				if(StringUtils.isNotEmpty(company.getAreaCode())){
					if(company.getAreaCode().length()>=12){
						out.put("province",CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,12)));
					}
					if(company.getAreaCode().length()>=16){
						out.put("city",CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,16)));
					}
				}
				if (StringUtils.isNotEmpty(company.getIndustryCode())) {
					out.put("industryCodeName", CategoryFacade.getInstance().getValue(company.getIndustryCode()));
				}
				out.put("contact", companyAccountService.queryAccountByCompanyId(companyId).getContact());
				out.put("company", company);
			} else if (companyPriceId != null) {
				// 对企业报价寻盘
				CompanyPriceDO cpDO = companyPriceService.queryCompanyPriceById(companyPriceId);
				if (cpDO == null) {
					break;
				}
				companyId = cpDO.getCompanyId();
				title = "我对" + cpDO.getTitle() + "产品感兴趣";
				Company company = companyService.queryCompanyById(companyId);
				if(company==null){
					break;
				}
				if(StringUtils.isNotEmpty(company.getAreaCode())){
					if(company.getAreaCode().length()>=12){
						out.put("province",CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,12)));
					}
					if(company.getAreaCode().length()>=16){
						out.put("city",CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,16)));
					}
				}
				if (StringUtils.isNotEmpty(company.getIndustryCode())) {
					out.put("industryCodeName", categoryService.queryCategoryByCode(company.getIndustryCode()).getLabel());
				}
				out.put("contact", companyAccountService.queryAccountByCompanyId(companyId).getContact());
				out.put("company", company);
				out.put("companyPriceId", companyPriceId);
			}else if(yuanliaoId!=null){
				YuanliaoDto yld = new YuanliaoDto();
				Yuanliao yl = yuanliaoService.queryYuanliaoById(yuanliaoId);
				//标签
				Map<String,String> mapTags = new HashMap<String,String>();
				if(StringUtils.isNotEmpty(yl.getTags())){
					for(String s : yl.getTags().split(",")){
						if(StringUtils.isNotEmpty(s)){
							mapTags.put(s, URLEncoder.encode(s, HttpUtils.CHARSET_UTF8));
						}
					}
				}
				out.put("mapTags", mapTags);
				//加工级别
				yld.setProcessLabel(getLabel(yl.getProcessLevel()));
				//特性级别
				yld.setCharaLabel(getLabel(yl.getCharaLevel()));
				//用途级别
				yld.setUsefulLabel(getLabel(yl.getUsefulLevel()));
				//厂家（产地）
				if(StringUtils.isNotEmpty(yl.getCategoryMainDesc())){
					yld.setCategoryMainLabel(YuanliaoFacade.getInstance().getValue(yl.getCategoryMainDesc()));
				}else{
					yld.setCategoryMainLabel(yl.getCategoryAssistDesc());
				}
				//类型
				if(StringUtils.isNotEmpty(yl.getType())){
					yld.setTypeLabel(CategoryFacade.getInstance().getValue(yl.getType()));
				}
				//该原料供求的图片
				YuanliaoPic pic = new YuanliaoPic();
				pic.setYuanliaoId(yuanliaoId);
				pic.setCheckStatus(1);
				pic.setIsDel(0);
				List<YuanliaoPic> listpic = yuanliaoPicService.queryYuanliaoPicByYuanliaoId(pic, 1);
				out.put("listpic", listpic);
				//货源所在地
				String address = "";
				if(StringUtils.isNotEmpty(yl.getLocation())){
					if(yl.getLocation().length()>12){
						address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 12)) + CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 16));
					}else if(yl.getLocation().length()>8 && yl.getLocation().length()<13){
						address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 8)) + CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 12));
					}else{
						address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 8));
					}
				}
				yld.setAddress(address);
				//是否过期
				long expireTime = DateUtil.getDate(yl.getExpireTime(), "yyyy-MM-dd HH:mm:ss").getTime();
				long now = new Date().getTime();
				if(expireTime>now){
					//未过期
					yld.setIsExpire(1);
				}else{
					//已过期
					yld.setIsExpire(0);
				}
				yld.setYuanliao(yl);
				out.put("yld", yld);
				out.put("yuanliaoId", yuanliaoId);
				title = "我对" + yl.getTitle() + "产品感兴趣";
				out.put("title", title);
			} else {
				break;
			}
			inquiry.setTitle(title);
			out.put("inquiry", inquiry);
			return null;
		} while (false);
		return new ModelAndView("index");
	}
	
	public String getLabel(String code){
		String label = "";
		if(StringUtils.isNotEmpty(code)){
			for(String s : code.split(",")){
				if(StringUtils.isNotEmpty(label)){
					label = label + "," + CategoryFacade.getInstance().getValue(s);
				}else{
					label = CategoryFacade.getInstance().getValue(s);
				}
			}
		}
		return label;
		
	}
	
	@RequestMapping
	public ModelAndView doInquiryApi(HttpServletRequest request,
			Map<String, Object> out, Inquiry inquiry, Integer productId,
			Integer companyId, Integer companyPriceId,String info,Integer yuanliaoId) {
		do {

			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser == null) {
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());
			CompanyAccount account = new CompanyAccount();
			if(productId!=null){
				// 设置内容
				inquiry.setContent(getContentFix(info,PRODUCT_AND_COMPANY_MAP)+ "<br/>" + inquiry.getContent());
				
				account = companyAccountService.queryAccountByProductId(productId);
				inquiry.setBeInquiredId(productId);
				inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_DEFAULT);
				ProductsDO productsDO = productsService.queryProductsById(productId);
				out.put("productId", productId);
				ProductsDO product = new ProductsDO();
				product.setTitle(productsDO.getTitle());
				PageDto<ProductsDto> page = new PageDto<ProductsDto>();
				page.setPageSize(16);
				page = productsService.pageProductsBySearchEngine(product, null, true, page);
				List<ProductsDto> list = page.getRecords();
				if(page==null||page.getRecords().size()<16){
					page = new PageDto<ProductsDto>();
					page.setPageSize(16-list.size());
					product.setTitle(CategoryProductsFacade.getInstance().getValue(productsDO.getCategoryProductsMainCode()));
					list.addAll(productsService.pageProductsBySearchEngine(product, null, true, page).getRecords());
				}
				page.setRecords(list);
				out.put("productPage", page);
			}else if(companyId!=null){
				
				// 设置内容
				inquiry.setContent(getContentFix(info,PRODUCT_AND_COMPANY_MAP)+ "<br/>" + inquiry.getContent());
				account = companyAccountService.queryAccountByCompanyId(companyId);
				inquiry.setBeInquiredId(companyId);
				inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY);
				String industryCode = companyService.queryCompanyById(companyId).getIndustryCode();
				Company company = new Company();
				company.setIndustryCode(industryCode);
				out.put("companyId", companyId);
				out.put("companyPage", companyService.pageCompanyBySearchEngine(company, new PageDto<CompanyDto>()));
			}else if(companyPriceId!=null){
				// 设置内容
				inquiry.setContent(getContentFix(info,PRICE_MAP)+ "<br/>" + inquiry.getContent());
				
				CompanyPriceDO cpDO = companyPriceService.queryCompanyPriceById(companyPriceId);
				if(cpDO==null){
					break;
				}
				account = companyAccountService.queryAccountByCompanyId(cpDO.getCompanyId());
				inquiry.setBeInquiredId(companyPriceId);
				inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY_PRICE);
				out.put("companyPriceId", companyPriceId);
			}else if(yuanliaoId != null){
				// 设置内容
				Yuanliao yuanliao = yuanliaoService.queryYuanliaoById(yuanliaoId);
				inquiry.setContent(getContentFix(info,PRODUCT_AND_COMPANY_MAP)+ "<br/>" + inquiry.getContent());
				if(yuanliao!=null){
					account = companyAccountService.queryAccountByCompanyId(yuanliao.getCompanyId());
				}
				inquiry.setBeInquiredId(yuanliaoId);
				inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_YUANLIAO);
				out.put("yuanliaoId", yuanliaoId);
			}else{
				break;
			}
			
			inquiry.setReceiverAccount(account.getAccount());
			int i = inquiryService.inquiryByUser(inquiry,ssoUser.getCompanyId());
			if (i > 0) {
				return null;
			}
		} while (false);
		return new ModelAndView("index");
	}

	/**
	 * 获取选框 内容组装
	 * @param info
	 * @return
	 */
	private String getContentFix(String info,Map<Integer, String> map){
		String contentFixTitle = "";
		String contentFixBody = "";
		// 组装正文
		if(StringUtils.isNotEmpty(info)){
			String[] content = info.split(",");
			for(String str:content){
				String result = map.get(Integer.valueOf(str));
				String [] resultArray = result.split("：");
				if(StringUtils.isEmpty(contentFixTitle)){
					contentFixTitle = "你好，我想进一步了解：" + resultArray[0];
				}else{
					contentFixTitle = contentFixTitle + "、" + resultArray[0];
				}
				contentFixBody = contentFixBody  + "<br/>" + resultArray[1];
			}
		}
		return "<b>"+contentFixTitle+"</b>"+contentFixBody;
	}
	@RequestMapping
	public ModelAndView doYuanliao(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			Inquiry inquiry, Integer yuanliaoId, String mainCode,
			String destUrl, String verifyCode, String verifyCodeKey) {
		ExtResult result = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);

		out.put("destUrl", destUrl);

		do {
			String vcode = String.valueOf(SsoUtils.getInstance().getValue(
					request, response, AstConst.VALIDATE_CODE_KEY));
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode)
					|| !verifyCode.equalsIgnoreCase(vcode)) {
				result.setData("您输入的验证码有错误！");
				break;
			}
			// 如果session失效，配置权限控制
			if (ssoUser == null) {
				result.setData("sessionTimeOut");
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());
			Yuanliao yuanliao = yuanliaoService.queryYuanliaoById(yuanliaoId);
			CompanyAccount account = companyAccountService.queryAccountByCompanyId(yuanliao.getCompanyId());
			inquiry.setReceiverAccount(account.getAccount());
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_YUANLIAO);
			inquiry.setBeInquiredId(yuanliaoId);

			int i = inquiryService.inquiryByUser(inquiry,
					ssoUser.getCompanyId());
			if (i > 0) {
				out.put("success", "true");
				out.put("data", "{}");
				return new ModelAndView();
			}

		} while (false);
		out.put("content", inquiry.getContent());

		out.put("error", "1");
		out.put("yuanliaoId", yuanliaoId);
		out.put("mainCode", mainCode);

		return new ModelAndView("redirect:/inquiry/yuanliao.htm");
	}

	@RequestMapping
	public ModelAndView yuanliao(Map<String, Object> out, Integer yuanliaoId,
			String mainCode, String destUrl, String content,
			HttpServletRequest request, String error) {
		SsoUser ssoUser = getCachedUser(request);

		Inquiry inquiry = new Inquiry();
		if (ssoUser == null) {
			out.put("success", "false");
			return new ModelAndView("redirect:" + destUrl);
		} else {
			Yuanliao yuanliao = yuanliaoService.queryYuanliaoById(yuanliaoId);
			String str = "";
			if (yuanliao != null) {
				str = yuanliao.getTitle();
			}
			String title = "我对" + str + "产品感兴趣";
			inquiry.setTitle(title);
			out.put("inquiry", inquiry);
		}

		out.put("destUrl", destUrl);
		out.put("yuanliaoId", yuanliaoId);
		out.put("mainCode", mainCode);
		out.put("error", error);
		return null;
	}

}
