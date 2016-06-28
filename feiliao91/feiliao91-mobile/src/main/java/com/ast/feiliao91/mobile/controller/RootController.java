/**
 * @author zhujq
 * @date 2016-06-01
 * @describe 淘再生手机战默认页
 */
package com.ast.feiliao91.mobile.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoConst;
import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.common.DataIndexDO;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.GoodsSearchDto;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.service.commom.DataIndexService;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.util.AstConst;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;
import com.zz91.util.velocity.AddressTool;

import net.sf.json.JSONArray;

@Controller
public class RootController extends BaseController {
	
	final static String MEMCACHE_TRUST_BUY_LIST = "feiliao-latest-trust-buy";
	
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
	/**
	 * 首页
	 * 
	 * @param out
	 */
	@RequestMapping
	public void index(Map<String, Object> out, HttpServletRequest request) {
		// seo
		SeoUtil.getInstance().buildSeo("index", out);
		// 获取最近搜索关键字
		getMySearch(request,out);
		// 获取首页图文关键字信息 熟料直通车
		List<DataIndexDO> keyList = dataIndexService.queryDataIndexByCode("100110001000", 4);
		out.put("keyList", keyList);
		
	}

	/**
	 * 搜索关键字
	 */
	@RequestMapping
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out,String k, GoodsSearchDto searchDto, PageDto<GoodsDto> page) {
		// seo
		SeoUtil.getInstance().buildSeo("search", out);
		getMySearch(request,out);// 获取最近搜索关键字
		do {
			// 判空
			if (StringUtils.isNotEmpty(k)) {
				k = Jsoup.clean(k, Whitelist.none());// 清楚关键字html痕迹
				searchDto.setTitle(k);
				out.put("keyword", k);
			}
			page.setPageSize(5);
			page = goodsService.pageBySearchEngine(searchDto, page);
			out.put("page", page);
		} while (false);
		return new ModelAndView();
	}
	
	/**
	 * 我要供货页面
	 */
	@RequestMapping
	public ModelAndView gongqiu(HttpServletRequest request, HttpServletResponse response,Map<String, Object> out){
		// seo
		SeoUtil.getInstance().buildSeo("gongqiu", out);
		getMySearch(request,out);// 获取最近搜索关键字
		String result = HttpUtils.getInstance().httpGet("http://caigou.zz91.com/queryLatestBuy.htm?size=100",HttpUtils.CHARSET_UTF8);
		if (StringUtils.isNotEmpty(result) && !"{}".equals(result)) {
			MemcachedUtils.getInstance().getClient().set(MEMCACHE_TRUST_BUY_LIST, 60*60*2, result);
			out.put("buyList", JSONArray.fromObject(result));
		}
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView moreGongqiu(Map<String, Object> out,Integer i) throws IOException{
		Object obj = MemcachedUtils.getInstance().getClient().get(MEMCACHE_TRUST_BUY_LIST);
		if (obj==null) {
			String result = HttpUtils.getInstance().httpGet("http://caigou.zz91.com/queryLatestBuy.htm?size=100",HttpUtils.CHARSET_UTF8);
			if (StringUtils.isNotEmpty(result) && !"{}".equals(result)) {
				MemcachedUtils.getInstance().getClient().set(MEMCACHE_TRUST_BUY_LIST, 60*60*2, result);
				obj = result;
			}
		}
		JSONArray ja = JSONArray.fromObject(obj.toString());
		JSONArray resultJa = new JSONArray();
		for (int count=0; count < 10; count++) {
			try {
				resultJa.add(ja.get((i-1)*10+count));
			} catch (Exception e) {
				continue;
			}
		}
		return printJson(resultJa, out);
	}

	/**
	 * 通用模块 - 获取最近搜索
	 * @param request
	 * @param out
	 */
	private void getMySearch(HttpServletRequest request,Map<String , Object> out){
		// 获取最近搜索
		String mySearchStr = HttpUtils.getInstance().getCookie(request, AstConst.COOKIE_MY_SEARCH,AddressTool.getAddress("domain"));
		if (StringUtils.isNotEmpty(mySearchStr)) {
			try {
				out.put("mySearch", JSONArray.fromObject(URLDecoder.decode(mySearchStr, HttpUtils.CHARSET_UTF8)));
			} catch (UnsupportedEncodingException e) {
			}
		}
		// 热门搜索
		Map<String, String> hotTags = TagsUtils.getInstance().queryTagsByHot(5, 7);
		out.put("hotTags", hotTags);
	}

	/**
	 * 记录最近搜索
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView searchKey(Map<String, Object> out, String k, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ExtResult result = new ExtResult();
		do {
			// 判空
			if (StringUtils.isEmpty(k)) {
				break;
			}
			k = Jsoup.clean(k.trim(), Whitelist.none());// 清楚关键字html痕迹
			TagsUtils.getInstance().searchTags(k);// 记录搜索关键字
			buildSearchCookie(request, response, k); // 记录cookie信息
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}

	/**
	 * 下拉获取更多资讯
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getMoreForSearch(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> out, String k, GoodsSearchDto searchDto, Integer startIndex) throws IOException {
		PageDto<GoodsDto> page = new PageDto<GoodsDto>();
		do {
			k = Jsoup.clean(k, Whitelist.none());// 清楚关键字html痕迹
			out.put("keyword", k);
			page.setStartIndex(startIndex); // 获取后面的数据
			page.setPageSize(5);
			page = goodsService.pageBySearchEngine(searchDto, page);
			if (page == null || page.getRecords() == null || page.getRecords().size() == 0) {
				page = null;
			}
		} while (false);
		return printJson(page, out);
	}

	/**
	 * 最近的搜索，数据放入cookie 有效期为一个月
	 * @param request
	 * @param response
	 * @param k
	 */
	private void buildSearchCookie(HttpServletRequest request, HttpServletResponse response, String k) {
		String mySearchStr = HttpUtils.getInstance().getCookie(request, AstConst.COOKIE_MY_SEARCH,AddressTool.getAddress("domain"));
		HttpUtils.getInstance().setCookie(response, AstConst.COOKIE_MY_SEARCH, "",AddressTool.getAddress("domain"), 0);
		JSONArray ja = new JSONArray();
		if (StringUtils.isNotEmpty(k)) {
			ja.add(k);
		}
		if (StringUtils.isNotEmpty(mySearchStr)) {
			JSONArray jaTemp = JSONArray.fromObject(mySearchStr);
			ja.addAll(jaTemp);
		}
		Set<String> tempSet = new LinkedHashSet<String>();
		for (int i = 0; i < ja.size(); i++) {
			tempSet.add(ja.get(i).toString());
		}
		ja = new JSONArray();
		for (String str : tempSet) {
			try {
				if (StringUtils.isContainCNChar(str)) {
					str = URLEncoder.encode(str, HttpUtils.CHARSET_UTF8);
				}
			} catch (UnsupportedEncodingException e) {
				// 异常，该词删除
				continue;
			}
			ja.add(str);
		}
		HttpUtils.getInstance().setCookie(response, AstConst.COOKIE_MY_SEARCH, ja.toString(),AddressTool.getAddress("domain"), 60 * 60 * 24 * 30); // 记录一个月的搜索关键字
	}

	/**
	 * 删除搜索cookie
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView cleanSearchCookie(Map<String, Object> out, HttpServletResponse response) throws IOException {
		ExtResult result = new ExtResult();
		HttpUtils.getInstance().setCookie(response, AstConst.COOKIE_MY_SEARCH, "", AddressTool.getAddress("domain"),0);
		result.setSuccess(true);
		return printJson(result, out);
	}

	/**
	 * 登录页面
	 * 
	 * @param account
	 * @param out
	 * @param url
	 * @param flag
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public void login(String account, Map<String, Object> out, String url, String flag)
			throws UnsupportedEncodingException {
		// 设置seo
		SeoUtil.getInstance().buildSeo("login", out);
		if (StringUtils.isNotEmpty(account) && !StringUtils.isContainCNChar(account)) {
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
	public ModelAndView loginout(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String ticket = HttpUtils.getInstance().getCookie(request, SsoConst.TICKET_KEY, SsoConst.SSO_DOMAIN);
		if (StringUtils.isNotEmpty(ticket)) {
			MemcachedUtils.getInstance().getClient().delete(ticket);
		}
		SsoUtils.getInstance().clearnSessionUser(request, null);
		request.getSession().removeAttribute(SsoUser.SESSION_KEY);
		HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, "", SsoConst.SSO_DOMAIN, 1);
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
	public ModelAndView doLogin(HttpServletRequest request, String account, String password,
			HttpServletResponse response, Integer rememberme, Map<String, Object> out) {
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
			// 更新登录时间
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
			if (rememberme!=null&&rememberme.intValue() == 1) {
				// 7天cookies
				HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN,
						60 * 60 * 24 * 7);
			} else {
				HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
			}
			// 6小时内有效
			MemcachedUtils.getInstance().getClient().set(ticket, 6 * 60 * 60, ssoUser);
			setSessionUser(request, ssoUser);
			if (StringUtils.isEmpty(url)) {
				return new ModelAndView("redirect:/home/index.htm");
			} else {
				return new ModelAndView("redirect:" + url);
			}
		} while (false);
		out.put("account", account);
		return new ModelAndView("redirect:/login.htm");
	}
	
	/**
	 * @param request
	 * @param account
	 * @param response
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doLoginByMobile(HttpServletRequest request, String mobile,String vcode,
			HttpServletResponse response,Map<String, Object> out) throws IOException {
		Map<String,Object> map = new HashMap<String,Object>();
		boolean isTrue = false;
		boolean isNewUser = false;
		boolean isSuccess = false;
		SsoUser ssoUser1 = new SsoUser();
		String ticket = null;
		do {
			//验证手机和验证码
			Integer i =companyValidateService.validateByMobile(mobile, vcode);
			if(i > 0){
				//验证通过
				isTrue = true;
			}else{
				//验证不通过,直接返回
//				break;
				map.put("isTrue",isTrue);
				return printJson(map, out);
			}
			Integer j = companyAccountService.hasAM(mobile,null);
			if(j > 0){
				//存在帐号
				// 获取ssouser信息用于登录
				SsoUser ssoUser = companyAccountService.initSsoUser(mobile);
				ssoUser1 = ssoUser;
				break;
			}else{
				//新建帐号
				isNewUser=true;
				CompanyInfo companyInfo = new CompanyInfo();
				CompanyAccount companyAccount = new CompanyAccount();
				companyAccount.setIp(HttpUtils.getInstance().getIpAddr(request));
				companyAccount.setAccount(mobile);
				companyAccount.setMobile(mobile);
				Integer cid = companyInfoService.insertCompanyInfo(companyInfo);
				if(cid > 0){
					//帐号信息处理
					companyAccount.setCompanyId(cid);
					Integer aid = companyAccountService.insertAccount(companyAccount);
					if(aid < 1){
						break;
					}else{
						isSuccess=true;
						SsoUser ssoUser = new SsoUser();
						ssoUser.setAccount(mobile);
						ssoUser.setAccountId(aid);
						ssoUser.setCompanyId(cid);
						ssoUser1 = ssoUser;
						try {
							 // TODO 增加私钥以保证安全
							ticket = MD5.encode(companyAccount.getAccount()+companyAccount.getIp());
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} while (false);
		HttpUtils.getInstance().setCookie(response,SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
		//6小时内有效
		MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, ssoUser1);
		setSessionUser(request, ssoUser1);
		map.put("isTrue", isTrue);
		map.put("isNewUser", isNewUser);
		map.put("isSuccess", isSuccess);
		return printJson(map, out);
	}
}
