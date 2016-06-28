/**
 * @author shiqp
 * @date 2015-01-08
 */
package com.ast.feiliao91.www.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoConst;
import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.common.DataIndexDO;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.GoodsSearchDto;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyDto;
import com.ast.feiliao91.service.commom.DataIndexService;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.ast.feiliao91.service.company.FeedBackService;
import com.ast.feiliao91.service.goods.GoodsService;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class RootController extends BaseController {

	final static String KEY_FORGET_ACCOUNT = "forgetAccount";

	final static String FLAG_VALID_EMAIL = "flagValidEmail";

	@Resource
	private FeedBackService feedBackService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private DataIndexService dataIndexService;

	@RequestMapping
	public void index(Map<String, Object> out) {
		// out.put("test", "测试成功");
		// seo
		// SeoUtil.getInstance().buildSeo(out);
		// zz91网采购信息列表获取 http://caigou.zz91.com/queryLatestBuy.htm
		String result = "";
		try {
			result = HttpUtils.getInstance().httpGet(
					"http://caigou.zz91.com/queryLatestBuy.htm?size=10",
					HttpUtils.CHARSET_UTF8);
		} catch (Exception e) {
		}
		if (StringUtils.isNotEmpty(result) && !"{}".equals(result)) {
			out.put("buyList", JSONArray.fromObject(result));
		}

		// zz91网采购信息实时信息 http://caigou.zz91.com/queryIngBuy.htm
		result = "";
		try {
			result = HttpUtils.getInstance().httpGet(
					"http://caigou.zz91.com/queryIngBuy.htm?size=15",
					HttpUtils.CHARSET_UTF8);
		} catch (Exception e) {
		}
		if (StringUtils.isNotEmpty(result) && !"{}".equals(result)) {
			out.put("ingList", JSONArray.fromObject(result));
		}

		// 手动静态数据
		List<DataIndexDO> ppList = dataIndexService.queryDataIndexByCode(
				"10001000", 4); // pp
		out.put("ppList", goodsService.buildDtoForIndex(ppList));
		List<DataIndexDO> psList = dataIndexService.queryDataIndexByCode(
				"10001001", 4); // ps
		out.put("psList", goodsService.buildDtoForIndex(psList));
		List<DataIndexDO> peList = dataIndexService.queryDataIndexByCode(
				"10001002", 4); // pe
		out.put("peList", goodsService.buildDtoForIndex(peList));
		List<DataIndexDO> petList = dataIndexService.queryDataIndexByCode(
				"10001003", 4); // pet
		out.put("petList", goodsService.buildDtoForIndex(petList));
		// 金牌供应商
		List<DataIndexDO> companyList = dataIndexService.queryDataIndexByCode(
				"10001004", 8);
		out.put("companyList",
				companyInfoService.bulidCompanyDtoListForIndex(companyList));
		SeoUtil.getInstance().buildSeo("index", out);
	}

	/**
	 * 登录页面
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public void login(String account, Map<String, Object> out, String url,
			String flag) throws UnsupportedEncodingException {
		// 设置seo
		SeoUtil.getInstance().buildSeo("login", out);
		if (StringUtils.isNotEmpty(account)
				&& !StringUtils.isContainCNChar(account)) {
			account = StringUtils.decryptUrlParameter(account);
		}
		out.put("account", account);
		out.put("url", url);
		out.put("flag", flag);
	}

	/**
	 * 登出页面
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView loginout(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String ticket = HttpUtils.getInstance().getCookie(request,
				SsoConst.TICKET_KEY, SsoConst.SSO_DOMAIN);
		if (StringUtils.isNotEmpty(ticket)) {
			MemcachedUtils.getInstance().getClient().delete(ticket);
		}
		SsoUtils.getInstance().clearnSessionUser(request, null);
		request.getSession().removeAttribute(SsoUser.SESSION_KEY);
		HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, "",SsoConst.SSO_DOMAIN, 1);
		return new ModelAndView("redirect:/login.htm");
	}

	/**
	 * 点击登录，进入交易管家
	 * 
	 * @param request
	 * @param account
	 * @param password
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView doLogin(HttpServletRequest request, String account,
			String password, HttpServletResponse response,
			Map<String, Object> out) {
		do {
			Integer i = companyAccountService.doLogin(account, password);
			if (i != 1) {
				break;
			}
			// 获取ssouser信息用于登录
			SsoUser ssoUser = companyAccountService.initSsoUser(account);
			if (ssoUser == null) {
				break;
			}
			//更新登录时间
			companyAccountService.updateGmtLastLogin(ssoUser.getCompanyId());
			String url = request.getParameter("url");
			String ip = HttpUtils.getInstance().getIpAddr(request);
			// 注册成功后，保存登录信息
			String ticket = null;
			try {
				ticket = MD5.encode(account + password + ip);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 登录信息设置
			HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY,
					ticket, SsoConst.SSO_DOMAIN, null);
			// 6小时内有效
			MemcachedUtils.getInstance().getClient()
					.set(ticket, 6 * 60 * 60, ssoUser);
			setSessionUser(request, ssoUser);
			if (StringUtils.isEmpty(url)) {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("trade"));
			} else {
				return new ModelAndView("redirect:" + url);
			}
		} while (false);
		out.put("account", account);
		return new ModelAndView("redirect:/login.htm");
	}

	/**
	 * 弹框登陆
	 * 
	 * @param request
	 * @param account
	 * @param password
	 * @param response
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView minidoLogin(HttpServletRequest request,String account,String password,HttpServletResponse response,Map<String , Object>out) throws IOException{
			ExtResult rs = new ExtResult();
			Integer i = companyAccountService.doLogin(account, password);
			if (i!=1) {
				rs.setSuccess(false);
				return printJson(rs, out);
			}
			// 获取ssouser信息用于登录
			SsoUser ssoUser = companyAccountService.initSsoUser(account);
			if (ssoUser==null) {
				rs.setSuccess(false);
				return printJson(rs, out);
			}
			//更新登录时间
			companyAccountService.updateGmtLastLogin(ssoUser.getCompanyId());
			String url = request.getParameter("url");
			String ip = HttpUtils.getInstance().getIpAddr(request);
			//注册成功后，保存登录信息
			String ticket = null;
			try {
				ticket = MD5.encode(account+password+ip);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			//登录信息设置
			HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
			//6小时内有效
			MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, ssoUser);
			setSessionUser(request, ssoUser);
			rs.setSuccess(true);
			rs.setData(url);
			return printJson(rs, out);
		}

	/**
	 * 忘记密码 页面
	 * 
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public void forgetPwd(Map<String, Object> out, String account)
			throws UnsupportedEncodingException {
		SeoUtil.getInstance().buildSeo("index", new String[] { "忘记密码" }, null,
				null, out);
		if (StringUtils.isNotEmpty(account)
				&& !StringUtils.isContainCNChar(account)) {
			account = StringUtils.decryptUrlParameter(account);
		}
		out.put("account", account);

	}

	/**
	 * 验证帐号是否存在
	 * 
	 * @param out
	 */
	@RequestMapping
	public ModelAndView doForgetPwd(Map<String, Object> out, String account,
			HttpServletRequest request) {
		do {
			CompanyAccount companyAccount = companyAccountService
					.queryAccountByAccount(account);
			if (companyAccount == null) {
				break;
			}
			String mobile = companyAccount.getMobile();
			out.put("mobile", mobile);
			if (StringUtils.isEmpty(mobile)) {
				//未绑定手机则先去绑定手机
				return new ModelAndView("redirect:"+AddressTool.getAddress("trade")+"/security/securityPhone.htm");
			}
			out.put("account", account);
			out.put("email", companyAccount.getEmail());
			out.put("hideAccount", addHide(account));
			out.put("hideMobile", addHide(mobile));
			SeoUtil.getInstance().buildSeo("index", new String[] { "重置登录密码" },
					null, null, out);
			return new ModelAndView("forgetPwdStep1");
		} while (false);
		out.put("account", account);
		return new ModelAndView("redirect:/forgetPwd.htm");
	}

	/**
	 * 加星隐藏保密隐私
	 * 
	 * @param str
	 * @return
	 */
	private String addHide(String str) {
		if (str.length() < 6) {
			return str;
		}
		int start = 0;
		if (str.length() > 7) {
			start = 3;
		} else {
			start = 3 - (8 - str.length());
		}
		int end = 0;
		if (str.length() < 9) {
			end = 1;
		} else {
			end = 1 + (str.length() - 8);
		}
		str = str.substring(0, start) + "****"
				+ str.substring(str.length() - end, str.length());
		return str;
	}

	/**
	 * 选择找回方式 页面
	 * 
	 * @param out
	 */
	// @RequestMapping
	// public void forgetPwdStep1(Map<String , Object> out,String mobile,String
	// account){
	// SeoUtil.getInstance().buildSeo("index", new String[]{"重置登录密码"}, null,
	// null, out);
	// out.put("hideAccount", addHide(account));
	// out.put("hideMobile",addHide(mobile));
	// out.put("account", account);
	// out.put("mobile",mobile);
	// }
	//
	/**
	 * 选择邮箱找回密码 页面
	 * 
	 * @param out
	 */
	@RequestMapping
	public ModelAndView forgetPwdEmail(Map<String, Object> out,
			HttpServletRequest request, String flag, String email) {
		SeoUtil.getInstance().buildSeo("index", new String[] { "邮箱找回" }, null,
				null, out);
		String[] em = email.split("@");
		if (em.length != 2) {
			return new ModelAndView("redirect:/forgetPwd.htm");
		}
		out.put("email", addHide(em[0]) + "@" + em[1]);
		out.put("readMyEmail", "http://mail." + em[1]);
		out.put("flag", flag);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doSendPhone(Map<String, Object> out,
			HttpServletRequest request, String mobile, String account) {
		companyValidateService.sendCodeByMobile(mobile, "0");
		return new ModelAndView("redirect:/forgetPwdPhone.htm?mobile=" + mobile
				+ "&account=" + account);
	}

	@RequestMapping
	public ModelAndView doSendEmail(Map<String, Object> out,
			HttpServletRequest request, String email, String account) {
		if(email==null){
			return new ModelAndView("redirect:doForgetPwd.htm?account="+account);
		}
		companyValidateService.sendCodeByEmail(email, account, "0");
		return new ModelAndView("redirect:/forgetPwdEmail.htm?account="
				+ account + "&email=" + email);
	}

	/**
	 * 选择手机找回密码 页面
	 * 
	 * @param out
	 */
	@RequestMapping
	public ModelAndView forgetPwdPhone(Map<String, Object> out,
			HttpServletRequest request, String flag, String account,
			String mobile, String erro) {
		if (mobile == null) {
			return new ModelAndView("redirect:/forgetPwd.htm");
		}
		out.put("erro", erro);
		out.put("account", account);
		out.put("mobile", mobile);
		out.put("flag", flag);
		SeoUtil.getInstance().buildSeo("index", new String[] { "手机找回" }, null,
				null, out);
		return new ModelAndView();
	}

	/**
	 * 获取短信验证码
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getMobileCode(Map<String, Object> out,
			HttpServletRequest request, String mobile) throws IOException {
		ExtResult result = new ExtResult();
		do {
			if (mobile == null) {
				result.setSuccess(false);
				return printJson(result, out);
			}
			result.setSuccess(true);
			companyValidateService.sendCodeByMobile(mobile, "0");
		} while (false);
		return printJson(result, out);
	}

	/**
	 * 验证手机验证码是否正确,正确后修改密码
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping
	public ModelAndView doValidPhone(Map<String, Object> out, String mobile,
			String vcode, String flag, String account)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		do {
			if (mobile == null) {
				return new ModelAndView("redirect:/forgetPwd.htm");
			}
			Integer i = companyValidateService.validateByMobile(mobile, vcode);
			if (i > 0) {
				flag = MD5.encode(account + mobile, 16);
				return new ModelAndView("redirect:/changePwd.htm?mobile="
						+ mobile + "&account=" + account + "&flag=" + flag);
			}
		} while (false);
		return new ModelAndView("redirect:/forgetPwdPhone.htm?mobile=" + mobile
				+ "&account="+account+"&erro=" + 1);
	}

	/**
	 * 选择手机找回密码 页面
	 * 
	 * @param out
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping
	public ModelAndView changePwd(Map<String, Object> out,
			HttpServletRequest request, String flag, String account,
			String mobile) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (flag == null || mobile == null) {
			return new ModelAndView("redirect:/forgetPwd.htm");
		}
		String code = MD5.encode(account + mobile, 16);
		if (!flag.equals(code)) {
			return new ModelAndView("redirect:/forgetPwdPhone.htm?mobile="
					+ mobile+"&account="+account);
		}
		out.put("account", account);
		out.put("mobile", mobile);
		SeoUtil.getInstance().buildSeo("index", new String[] { "手机找回" }, null,
				null, out);
		return new ModelAndView();
	}

	/**
	 * 验证邮箱
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping
	public ModelAndView doValidEmail(Map<String, Object> out, String key,
			String account, HttpServletRequest request, String vcode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			CompanyAccount ca = companyAccountService
					.queryAccountByAccount(account);
			String md5key = "";
			try {
				md5key = MD5.encode(account + ca.getEmail() + vcode);
			} catch (Exception e) {
			}
			// 检验两次加密的结果是否一致
			if (!md5key.equals(key)) {
				break;
			}

			// 验证代码是否正确
			Integer i = companyValidateService.validateByType(ca.getEmail(),
					vcode, CompanyValidateService.TYPE_EMAIL);
			if (i > 0) {
				String flag=MD5.encode(ca.getAccount()+ca.getMobile(), 16);
				return new ModelAndView("redirect:/changePwd.htm?account="+ca.getAccount()+"&mobile="+ca.getMobile()+"&flag="+flag);
			}

		} while (false);
		out.put("flag", 1);
		return new ModelAndView("redirect:/forgetPwdEmail.htm");
	}

	@RequestMapping
	public ModelAndView doChangPwd(Map<String, Object> out,
			HttpServletRequest request, String newPwd, String newPwdR,
			String account) {
		do {
			Integer i = companyAccountService.updatePwd(account, newPwd,
					newPwdR);
			if (i < 1) {
				break;
			}
			return new ModelAndView("redirect:/login.htm");
		} while (false);
		return new ModelAndView("redirect:/doValidPhone.htm");
	}

	@RequestMapping
	public ModelAndView compInfo(Map<String, Object> out, Integer id,
			GoodsSearchDto searchDto, PageDto<GoodsDto> page) {
		do {
			if (id == null) {
				break;
			}
			CompanyDto company = companyInfoService.queryCompanyDtoById(id);
			if (company == null) {
				break;
			}
			out.put("company", company);
			searchDto.setCompanyId(id);
			searchDto.setIsDel(0); // 未删除
			searchDto.setCheckStatus(1); // 审核通过
			searchDto.setIsSell(1); // 上架
			page = goodsService.pageBySearch(searchDto, page);
			out.put("page", page);
			// seo 参数
			SeoUtil.getInstance().buildSeo(
					"companyInfo",
					new String[] { company.getCompanyInfo().getName() },
					new String[] { company.getCompanyInfo().getName() },
					new String[] { Jsoup.clean(company.getCompanyInfo()
							.getIntroduce(), Whitelist.none()) }, out);
			return new ModelAndView();
		} while (false);
		return new ModelAndView("redirect:/index.htm");
	}

	@RequestMapping
	public ModelAndView list(Map<String, Object> out, GoodsSearchDto dto,
			PageDto<GoodsDto> page, HttpServletResponse response)
			throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(dto.getTitle())
				&& (page.getCurrentPage()).equals(1)
				&& StringUtils.isEmpty(dto.getCategory())
				&& StringUtils.isEmpty(dto.getColor())
				&& StringUtils.isEmpty(dto.getLevel())
				&& StringUtils.isEmpty(dto.getForm())) {
			// 无搜索条件的首页
			SeoUtil.getInstance().buildSeo("list", out);
		}
		if (StringUtils.isNotEmpty(dto.getTitle())&&!StringUtils.isContainCNChar(dto.getTitle())) {
			dto.setTitle(StringUtils.decryptUrlParameter(filterDHStr(dto.getTitle())));
			if (!StringUtils.isContainCNChar(dto.getTitle())) {
				dto.setTitle(URLDecoder.decode(dto.getTitle(), "utf-8"));
			}
		}
		if (StringUtils.isEmpty(dto.getTitle())) {
			if ((page.getCurrentPage() > 1)
					|| StringUtils.isNotEmpty(dto.getCategory())
					|| StringUtils.isNotEmpty(dto.getColor())
					|| StringUtils.isNotEmpty(dto.getLevel())
					|| StringUtils.isNotEmpty(dto.getForm())) {
				// 现货交易第N页以及条件选择页面(无搜索条件)
				SeoUtil.getInstance().buildSeo("listNpage",
						new String[] { "塑料现货交易-淘再生网" }, null, null, out);
			}
		}
		if (StringUtils.isNotEmpty(dto.getTitle())) {
			SeoUtil.getInstance().buildSeo("list_serch",
					new String[] { dto.getTitle() }, new String[] { "/" },
					new String[] { "/" }, out);
		}
		if (StringUtils.isNotEmpty(dto.getCategory())
				&& !StringUtils.isContainCNChar(dto.getCategory())) {
			dto.setCategory(StringUtils.decryptUrlParameter(filterDHStr(dto
					.getCategory())));
		}
		if (StringUtils.isNotEmpty(dto.getColor())
				&& !StringUtils.isContainCNChar(dto.getColor())) {
			dto.setColor(StringUtils.decryptUrlParameter(filterDHStr(dto
					.getColor())));
		}
		if (StringUtils.isNotEmpty(dto.getForm())
				&& !StringUtils.isContainCNChar(dto.getForm())) {
			dto.setForm(StringUtils.decryptUrlParameter(filterDHStr(dto
					.getForm())));
		}
		if (StringUtils.isNotEmpty(dto.getLevel())
				&& !StringUtils.isContainCNChar(dto.getLevel())) {
			dto.setLevel(StringUtils.decryptUrlParameter(filterDHStr(dto
					.getLevel())));
		}
		page.setPageSize(16);
		page = goodsService.pageBySearchEngine(dto, page);
		out.put("page", page); // 左边列表
		PageDto<GoodsDto> rightPage = new PageDto<GoodsDto>();
		rightPage.setSort("id");
		rightPage.setPageSize(4);
		rightPage = goodsService.pageBySearchEngine(new GoodsSearchDto(),
				rightPage);
		out.put("rightList", rightPage.getRecords()); // 右边最新商品
		out.put("url", "/list.htm" + getUrlParam(dto));
		out.put("dto", dto);
		// seo
		if(StringUtils.isEmpty(dto.getTitle())){
			if((page.getCurrentPage()>1) || StringUtils.isNotEmpty(dto.getCategory()) ||StringUtils.isNotEmpty(dto.getColor()) || StringUtils.isNotEmpty(dto.getLevel()) || StringUtils.isNotEmpty(dto.getForm())){
				//现货交易第N页以及条件选择页面(无搜索条件)
				SeoUtil.getInstance().buildSeo("listNpage", new String[]{"塑料现货交易-淘再生网"},null,null,out);
			}
		}
		if(StringUtils.isNotEmpty(dto.getTitle())){
			SeoUtil.getInstance().buildSeo("list_serch",new String[]{dto.getTitle()},new String[]{"/"}, new String[]{"/"}, out);
		}
		return new ModelAndView();
	}

	/**
	 * 去除参数头尾的“,”符号
	 */
	private String filterDHStr(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		if (str.startsWith(",")) {
			str = str.substring(1, str.length());
		}
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 解析search参数，构建url使用的参数序列
	 * 
	 * @param obj
	 * @return
	 */
	private String getUrlParam(Object obj) {
		String url = "";
		do {
			if (obj == null) {
				break;
			}
			JSONObject js = JSONObject.fromObject(obj);
			if (js == null || js.size() < 1) {
				break;
			}
			url = url + "?";
			@SuppressWarnings("rawtypes")
			Iterator it = js.keys();
			while (it.hasNext()) {
				String key = "" + it.next();
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				String value = "" + js.get(key);
				if (StringUtils.isEmpty(value) || "0".equals(value)) {
					continue;
				}
				url = url + key + "=" + value + "&";
			}
		} while (false);
		return url;
	}

}