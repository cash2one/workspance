/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.myesite.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.OauthAccess;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.StatisticsDto;
import com.zz91.ep.dto.comp.RegistDto;
import com.zz91.ep.dto.mblog.MBlogDto;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.common.ParamService;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.comp.OauthAccessService;
import com.zz91.ep.service.crm.CrmCompSvrService;
import com.zz91.ep.service.mblog.MBlogInfoService;
import com.zz91.ep.service.mblog.MBlogService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.utils.EpConst;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.domain.Param;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.seo.SeoUtil;

/**
 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：首页默认控制类。 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class RootController extends BaseController {
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private ParamService paramService;
	@Resource
	private CompAccountService compAccountService;
	@Resource
	private TradeCategoryService tradeCategoryService;
	@Resource
	private OauthAccessService oauthAccessService;
	@Resource
	private MyEsiteService myEsiteService;
	@Resource
	private CrmCompSvrService crmCompSvrService;
	@Resource
	private MBlogService mBlogService;
	@Resource
	private MBlogInfoService mBlogInfoService;
	/**
	 * 函数名称：index 功能描述：访问首页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request,HttpServletResponse response) {
		StatisticsDto staDto = compProfileService.statisticsMessage(EpAuthUtils
				.getInstance().getEpAuthUser(request, null).getUid());
		out.put("loginCount", compAccountService
				.queryLoginCountByCid(getCompanyId(request)));
		out.put("staDto", staDto);
		
		// 计算总留言数量
		// 计算未读留言数量
//		PageDto<CompNews> page = new PageDto<CompNews>();
//		page = compNewsService.pageCompNewsByCid(cachedUser.getCid(), null,
//				null, null, (short)2, (short)1, page);
//		List<CompNews> list = page.getRecords();
//		out.put("wshlist", list);
//		out.put("page", page);
		//计量没有通过审核的文章的数量
		EpAuthUser cachedUser = getCachedUser(request);
//		Integer count = compNewsService.queryWtgshCount(cachedUser.getCid());
//		out.put("count", count);
		myEsiteService.init(out, cachedUser.getCid());
		
		// 计算中环通会员年限
		out.put("memberYear", crmCompSvrService.queryYearByCid(cachedUser.getCid()));
		
		//查询出微交流的信息
		MBlogInfo info =mBlogInfoService.queryInfoByCid(cachedUser.getCid());
		if(info!=null && info.getIsDelete().equals("0") && StringUtils.isNotEmpty(info.getName())){
			out.put("info",info);
			//查询出全网动态的三条数据
			PageDto<MBlogDto> page=new PageDto<MBlogDto>();
			page.setLimit(5);
			page=mBlogService.queryAllBlog(page);
			out.put("page", page.getRecords());
			//查询出@最多的人	00
			List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
			out.put("anteList", anteList);
		}else{
			PageDto<MBlogDto> page=new PageDto<MBlogDto>();
			page.setLimit(5);
			page=mBlogService.queryAllBlog(page);
			out.put("page", page.getRecords());
		}
		
		// 清除 cdn cache
		clearCDN(response);
		
		return new ModelAndView();
	}

	/**
	 * 函数名称：login 功能描述：登录页面 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView login(Map<String, Object> out,
			HttpServletRequest request, String error, String refurl) {

		if (StringUtils.isNotEmpty(refurl)) {
			refurl = Jsoup.clean(refurl, Whitelist.none());
			out.put("refurl", refurl);
		}
		out.put("error", error);
		if (StringUtils.isNotEmpty(error)) {
			out.put("errorText", AuthorizeException.getMessage(error));
		}

		SeoUtil.getInstance().buildSeo("login", out);

		return new ModelAndView();
	}

	/**
	 * 函数名称：doLogin 功能描述：登录 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */

	@RequestMapping
	public ModelAndView doLogin(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			String username, String password, String vcode, String refurl) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			out.put("error", "用户名和密码不能为空");
			return new ModelAndView("login");
		}
		EpAuthUser user = null;
		try {
			user = EpAuthUtils.getInstance().validateUser(response, username,password, null);
			compAccountService.updateLoginInfo(user.getUid(), HttpUtils.getInstance().getIpAddr(request), user.getCid());
			EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
			if (StringUtils.isNotEmpty(refurl)) {
				return new ModelAndView("redirect:" + refurl);
			}
		} catch (NoSuchAlgorithmException e) {
			out.put("error", AuthorizeException.ERROR_SERVER);
			out.put("refurl", refurl);
			return new ModelAndView("redirect:login.htm");
		} catch (IOException e) {
			out.put("error", AuthorizeException.ERROR_SERVER);
			out.put("refurl", refurl);
			return new ModelAndView("redirect:login.htm");
		} catch (AuthorizeException e) {
			out.put("error", e.getMessage());
			out.put("refurl", refurl);
			return new ModelAndView("redirect:login.htm");
		}

		return new ModelAndView("redirect:index.htm");
	}

	
	/**
	 * 函数名称：logout 功能描述：用户注销 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView logout(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response) {
		EpAuthUtils.getInstance().logout(request, response, null);
		return new ModelAndView("redirect:login.htm");
	}

	/**
	 * 函数名称：regist 功能描述:跳转登录页面 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数 2012/12/12 马元生
	 *            2.0 增加token功能，增加注册来源等
	 */
	@RequestMapping
	public ModelAndView regist(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			String registerCode, String refurl, String error) {

		out.put("registerCode", registerCode);
		out.put("refurl", refurl);
		if (error != null) {
			out.put("error", EpConst.REGIST_ERROR_MAP.get(error));
		}

		String token = UUID.randomUUID().toString();
		EpAuthUtils.getInstance().setValue(request, response,
				EpConst.REGIST_TOKEN_KEY, token);
		out.put("token", token);

		SeoUtil.getInstance().buildSeo("regist", out);

		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doRegist(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String registerCode, String refurl, String verifyCode,
			String token, CompProfile profile, CompAccount compAccount) {
		do {
			// 判断token
			if (StringUtils.isEmpty(token)) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("0"));
				break;
			}
			
			// 判断用户名是否重复
			Integer i = compAccountService.getAccountIdByAccount(compAccount.getAccount());
			if(i!=null&&i>0){
				out.put("error", EpConst.REGIST_ERROR_MAP.get("3"));
				break;
			}

			String stoken = String.valueOf(EpAuthUtils.getInstance().getValue(
					request, response, EpConst.REGIST_TOKEN_KEY));
			EpAuthUtils.getInstance().remove(request, EpConst.REGIST_TOKEN_KEY);
			if (!token.equals(stoken)) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("0"));
				break;
			}

			// 判断验证码
			String vcode = String.valueOf(EpAuthUtils.getInstance().getValue(
					request, response, EpConst.VALIDATE_CODE_KEY));
			EpAuthUtils.getInstance()
					.remove(request, EpConst.VALIDATE_CODE_KEY);

			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode)
					|| !verifyCode.equalsIgnoreCase(vcode)) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("1"));
				break;
			}

			// 注册
			boolean reg = compProfileService.regist(profile, compAccount);
			if (!reg) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("2"));
				break;
			}

			// 自动登录
			EpAuthUser authUser = null;
			try {
				authUser = EpAuthUtils.getInstance().validateUser(response,
						compAccount.getAccount(),
						compAccount.getPasswordClear(),
						HttpUtils.getInstance().getIpAddr(request));
			} catch (NoSuchAlgorithmException e) {
			} catch (IOException e) {
			} catch (AuthorizeException e) {
			}
			if (authUser == null) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("2"));
				break;
			}

			setSessionUser(request, authUser);

			// 发送Email
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", compAccount.getAccount());
			MailUtil.getInstance().sendMail(MailArga.TITLE_REGISTER_SUCCESS,
					compAccount.getEmail(),
					MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
					MailArga.TEMPLATE_REGISTER_SUCCESS_CODE, map,
					MailUtil.PRIORITY_HEIGHT);

			out.put("account", compAccount.getAccount());

			return new ModelAndView();

		} while (false);

		// 原注册信息
		out.put("account", compAccount);
		out.put("profile", profile);

		out.put("refurl", refurl);
		out.put("registerCode", registerCode);

		token = UUID.randomUUID().toString();
		EpAuthUtils.getInstance().setValue(request, response,
				EpConst.REGIST_TOKEN_KEY, token);
		out.put("token", token);

		return new ModelAndView("regist");
	}

	/**
	 * 函数名称：resourceNotFound 功能描述：发生404错误页面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView resourceNotFound(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}

	/**
	 * 函数名称：uncaughtException 功能描述：发生异常错误页面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView uncaughtException(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}

	final static Logger LOG = Logger.getLogger(RootController.class);

	/**
	 * 函数名称：doRegist_StpOne 功能描述：注册第一步。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView doRegist_StpOne(Map<String, Object> out,
			HttpServletResponse response, HttpServletRequest request,
			String username, String password, String email, String mobile1,
			String verifyCodeKey, String verifyCode, Short mainProduct,
			String qq) {
		// Integer id = compProfileService.regist(username, password, email,
		// mobile1, verifyCodeKey, verifyCode, mainProduct,qq);
		// if (id!=null && id >0) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("username", username);
		// MailUtil.getInstance().sendMail(MailArga.TITLE_REGISTER_SUCCESS,
		// email, MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
		// MailArga.TEMPLATE_REGISTER_SUCCESS_CODE, map,
		// MailUtil.PRIORITY_HEIGHT);
		// //发送完善信息邮件
		// //MailUtil.getInstance().sendMail(MailArga.TITLE_COMPLETE_INFO,
		// email, MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
		// // MailArga.TEMPLATE_COMPLETE_INFO, map, MailUtil.PRIORITY_HEIGHT);
		//    		
		// EpAuthUser user =null;
		// try {
		// user = EpAuthUtils.getInstance().validateUser(response, username,
		// password, null);
		// compAccountService.updateLoginInfo(user.getUid(),
		// null,user.getCid());
		// EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
		// out.put("userId", id);
		// }catch (Exception e) {
		// return new ModelAndView("regist");
		// }
		//    		
		// }
		return new ModelAndView("redirect:regist_StpTwo.htm");
	}

	/**
	 * 跳转注册第二步
	 * 
	 * @param out
	 * @param request
	 * @return
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView regist_StpTwo(Map<String, Object> out,
			HttpServletRequest request, Integer userId) {
		if (getCompanyId(request) == null && userId == null) {
			return new ModelAndView("regist");
		}
		out.put("cid", getCompanyId(request));
		out.put("uid", userId);
		// 获取公司基本信息
		CompProfile compProfile = compProfileService
				.getCompProfileById(getCompanyId(request));
		out.put("compProfile", compProfile);
		// 获取帐号信息
		CompAccount account = compAccountService
				.getCompAccountByCid(getCompanyId(request));
		out.put("account", account);
		// 获取公司类型
		List<Param> companyCategory = paramService
				.queryParamsByType("company_industry_code");
		out.put("companyCategory", companyCategory);
		// 获取所处行业类型
		List<TradeCategory> categorys = tradeCategoryService
				.queryCategoryByParent("1000", 0, 0);
		out.put("companyIndustry", categorys);
		return null;
	}

	/**
	 * 函数名称：doRegStpTwo 功能描述：注册第二步 更新公司和员工信息。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView doRegStpTwo(Map<String, Object> out,
			HttpServletRequest request, RegistDto registDto,
			Integer[] industryChain) {

		boolean isSuccess = compProfileService.updateRegistInfo(registDto,
				getCompanyId(request), industryChain);

		if (isSuccess) {
			String loginname = EpAuthUtils.getInstance().getEpAuthUser(request,
					null).getAccount();
			out.put("username", loginname);
			return new ModelAndView("resuc");
		}

		CompProfile compProfile = compProfileService
				.getCompProfileById(getCompanyId(request));
		out.put("compProfile", compProfile);

		return new ModelAndView("regist_StpTwo");
	}

	/**
	 * 函数名称：resuc 功能描述：跳转到成功登录界面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView resuc(HttpServletRequest request,
			Map<String, Object> out, String username) {
		out.put("username", username);
		return null;
	}

	/**
	 * 函数名称：qqLoginAnalysis 功能描述：统计qq登录按钮点击次数，并跳转至qq登录接入模块。输入参数：
	 * 
	 * @param out
	 *            Map
	 *
	 */
	@RequestMapping
	public ModelAndView qqLoginAnalysis(Map<String, Object>out){
		String clientId = (String) MemcachedUtils.getInstance().getClient().get("qq.cliendId");
		String redirectUri = (String) MemcachedUtils.getInstance().getClient().get("qq.redirectUri");
		// 统计点击次数 环保
		LogUtil.getInstance().log("qq_login", "qq_login_click");
		return new ModelAndView(
				"redirect:http://openapi.qzone.qq.com/oauth/show?which=ConfirmPage&display=pc&client_id="
						+ clientId
						+ "&response_type=code&scope=get_user_info&redirect_uri="
						+ redirectUri);
	}
	/**
	 * 函数名称：qqLogin 功能描述：qq接入验证过后，进入绑定账号或者注册新账号页面
	 */
	@RequestMapping
	public ModelAndView qqLogin(String code, String accessToken,HttpServletRequest request, String account, String error,Map<String, Object> out,HttpServletResponse response) {
		String clientId = (String) MemcachedUtils.getInstance().getClient().get("qq.cliendId");
		String clientSecret = (String) MemcachedUtils.getInstance().getClient().get("qq.clientSecret");
		String redirectUri = (String) MemcachedUtils.getInstance().getClient().get("qq.redirectUri");
		// cliend_id与key 在open.qq管理中心获取 (http://connect.qq.com/manage/detail?id=1) qq号码：2389504685 ;密码：zj88****
		String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="+clientId+"&client_secret="+clientSecret+"&redirect_uri="+redirectUri+"&code=" + code;
		String accessTokenResult;
		do {
			try {
				accessTokenResult = HttpUtils.getInstance().httpGet(url,HttpUtils.CHARSET_UTF8);
			} catch (HttpException e) {
				accessTokenResult = null;
			} catch (IOException e) {
				accessTokenResult = null;
			}
			if (StringUtils.isEmpty(accessTokenResult)) {
				break;
			}
			accessToken = getQQAccessToken(accessTokenResult);
			// 获取不到access_token 跳回首页
			if (StringUtils.isEmpty(accessToken)) {
				break;
			}
			out.put("accessToken", accessToken);
			// 从腾讯给的接口获取与qq号码对应的open_id
			String openId = getQQOpenId(accessToken);
			OauthAccess oa = oauthAccessService.queryAccessByOpenIdAndType(	openId, OauthAccessService.OPEN_TYPE_QQ);
			// openId关联账号不存在, 跳转至 绑定账号或者重新注册账号
			if (oa == null || StringUtils.isEmpty(oa.getTargetAccount())) {
				break;
			}
			// 自动登录
			account = oa.getTargetAccount();
			CompAccount compAccount = compAccountService.queryAccountDetails(account);
			EpAuthUser authUser = null;
			try {
				authUser = EpAuthUtils.getInstance().validateUser(response,compAccount.getAccount(),compAccount.getPasswordClear(),HttpUtils.getInstance().getIpAddr(request));
			} catch (NoSuchAlgorithmException e) {
			} catch (IOException e) {
			} catch (AuthorizeException e) {
			}
			if (authUser == null) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("2"));
				break;
			}
			setSessionUser(request, authUser);
			return new ModelAndView("redirect:index.htm");
		} while (false);
		return new ModelAndView();
	}
	
	/**
	 * 注册新的账号，并绑定qq的open_id
	 * @param request
	 * @param response
	 * @param out
	 * @param profile
	 * @param compAccount
	 * @param accessToken
	 * @param passwordConfirm
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws AuthorizeException
	 */
	@RequestMapping
	public ModelAndView doQQRegist(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			CompProfile profile, CompAccount compAccount, String accessToken,
			String passwordConfirm) throws HttpException, IOException, AuthorizeException {
		do {
			// QQ导入注册
			profile.setRegisterCode("20");
			// 注册
			boolean reg = compProfileService.regist(profile, compAccount);
			if(reg){
				// 统计注册次数
				LogUtil.getInstance().log("qq_login", "qq_login_register");
			}
			
			// 自动登录
			EpAuthUser authUser = null;
			try {
				authUser = EpAuthUtils.getInstance().validateUser(response,
						compAccount.getAccount(),
						compAccount.getPasswordClear(),
						HttpUtils.getInstance().getIpAddr(request));
			} catch (NoSuchAlgorithmException e) {
			} catch (IOException e) {
			} catch (AuthorizeException e) {
			}
			if (authUser == null) {
//				out.put("error", EpConst.REGIST_ERROR_MAP.get("2"));
				break;
			}
			setSessionUser(request, authUser);
				
			// 获取 openId
			String openId = getQQOpenId(accessToken);
			Integer i = oauthAccessService.addOneAccess(openId,OauthAccessService.OPEN_TYPE_QQ, compAccount.getAccount());
			if(i==null||i<=0){
				break;
			}
			// myesite 首页
			return new ModelAndView("redirect:index.htm");
		} while (false);
		out.put("account", compAccount);
		out.put("profile", profile);
		out.put("accessToken", accessToken);
		return new ModelAndView("qqLogin");
	}
	/**
	 * 使用已有的账号，验证通过后，进行绑定制定qq号码所对应的open_id
	 * @param request
	 * @param response
	 * @param out
	 * @param account
	 * @param password
	 * @param accessToken
	 * @return
	 */
	@RequestMapping
	public ModelAndView doQQBind(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String account, String password, String accessToken) {
		do{
			// 自动登录 验证账号密码
			EpAuthUser user = null;
			try {
				user = EpAuthUtils.getInstance().validateUser(response, account,password, null);
				compAccountService.updateLoginInfo(user.getUid(), null, user.getCid());
				EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
			} catch (NoSuchAlgorithmException e) {
				out.put("error", AuthorizeException.ERROR_SERVER);
				break;
			} catch (IOException e) {
				out.put("error", AuthorizeException.ERROR_SERVER);
				break;
			} catch (AuthorizeException e) {
				out.put("error", e.getMessage());
				break;
			}
			
			String openId = getQQOpenId(accessToken);
			// 确定账号是否已经绑定过
			OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_QQ);
			if(oa!=null){
				//账号已经绑定过
				out.put("error","账号已经绑定");
				break;
			}
			// 执行 绑定操作 update 或者 insert openid
			oa = oauthAccessService.queryAccessByOpenIdAndType(openId, OauthAccessService.OPEN_TYPE_QQ);
			if(oa!=null){
				oauthAccessService.updateByOpenId(openId, account);
			}else{
				oauthAccessService.addOneAccess(openId,OauthAccessService.OPEN_TYPE_QQ,account);
			}
			// 统计注册次数
			LogUtil.getInstance().log("qq_login", "qq_login_bind");
			// myesite 首页
			return new ModelAndView("index");
		}while(false);
		// 1、账号不存在 或者 账号密码错误 绑定失败 2、清空登录状态
		EpAuthUtils.getInstance().logout(request, response, null);
		out.put("account", account);
		out.put("accessToken", accessToken);
		return new ModelAndView("qqLogin");
	}
	
	/**
	 * 获取 qq登录状态代码 access_token 在获取open_id之前必须获得
	 * @param accessTokenUrl
	 * 
	 */
	private String getQQAccessToken(String accessTokenUrl) {
		String[] str = accessTokenUrl.split("&");
		accessTokenUrl = str[0].replace("access_token=", "");
		return accessTokenUrl;
	}
	
	/**
	 * 根据 用户登录的状态access_token状态，获取与qq号码唯一绑定的open_id。
	 * 注：access_token不得为空
	 * @param accessToken
	 * @return
	 */
	public String getQQOpenId(String accessToken) {
		String str = null;
		try {
			str = HttpUtils.getInstance().httpGet("https://graph.qq.com/oauth2.0/me?access_token=" + accessToken, HttpUtils.CHARSET_UTF8);
		} catch (HttpException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		// 取大括号的内容 正则
		Pattern pattern = Pattern.compile("\\{[^}]+\\}");
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		str = matcher.group();
		JSONObject jobj = JSONObject.fromObject(str);
		return jobj.getString("openid");
	}

}