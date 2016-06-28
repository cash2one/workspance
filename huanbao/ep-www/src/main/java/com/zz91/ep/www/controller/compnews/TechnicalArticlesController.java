package com.zz91.ep.www.controller.compnews;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.common.Video;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.news.NewsSearchDto;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.common.ParamService;
import com.zz91.ep.service.common.SysAreaService;
import com.zz91.ep.service.common.VideoService;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.comp.CompNewsService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.news.NewsService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.utils.EpConst;
import com.zz91.ep.www.controller.BaseController;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

@Controller
public class TechnicalArticlesController extends BaseController {

	@Resource
	private CompNewsService compNewsService;
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private NewsService newsService;
	@Resource
	private CompAccountService compAccountService;
	@Resource
	private MyEsiteService myEsiteService;
	@Resource
	private SysAreaService sysAreaService;
	@Resource
	private ParamService paramService;
	@Resource
	private TradeCategoryService tradeCategoryService;
	@Resource
	private VideoService videoService;

	final static String Technical_Parameters = "Technical Parameters";
	final static String New_Technology = "New Technology";
	final static String Environmental_Protection = "Environmental Protection";

	/**
	 * 函数名称：index 功能描述：公司技术文章列表页 输入参数：
	 * 
	 * @param out
	 * @param page
	 *            异　　常： 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容 　　　　　2013/09/06　　
	 *            方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, PageDto<CompNewsDto> page) {

		// 公司技术文章
		page.setLimit(6);
		page = compNewsService.pageCompNewsForArticle(page);
		for (CompNewsDto compNewsDto : page.getRecords()) {
			if (compNewsDto.getCompNews().getDetails() != null) {
				compNewsDto.getCompNews().setDetails(
						Jsoup.clean(compNewsDto.getCompNews().getDetails(),
								Whitelist.none()));
			}
		}

		out.put("list", page.getRecords());
		SeoUtil.getInstance().buildSeo("articlesIndex", out);
		return null;
	}

	/**
	 * 函数名称：list 功能描述：公司技术文章列表页 输入参数：
	 * 
	 * @param out
	 * @param page
	 *            异　　常： 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容 　　　　　2013/08/23　　
	 *            方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView list(Map<String, Object> out, PageDto<CompNewsDto> page, String keywords) {
        
		page.setLimit(8);
		//搜索引擎的用法(根据标签搜索)
		if(StringUtils.isNotEmpty(keywords)){
			
			out.put("keywords", keywords);
			page = compNewsService.pageBySearchEngine(keywords, page);
			for (CompNewsDto compNewsDto : page.getRecords()) {
				
				compNewsDto.getCompNews().setDetails(
						Jsoup.clean(compNewsDto.getCompNews().getDetails().replaceAll("&nbsp;", ""),
								Whitelist.none()));
				
			}
			out.put("page", page);
			if (page.getTotals() == 0 || page.getTotals() == null) {
				return new ModelAndView("redirect:"
						+ "/compnews/technicalarticles/serchNotFound.htm?keywords="
						+ keywords);
			}
		} else {
			page = compNewsService.pageCompNewsForArticle(page);
			for (CompNewsDto compNewsDto : page.getRecords()) {
				CompProfile compProfile = compProfileService
						.queryShortCompProfileById(compNewsDto.getCompNews()
								.getCid());
				if (compNewsDto.getCompNews().getDetails() != null) {
					compNewsDto.getCompNews().setDetails(
							Jsoup.clean(compNewsDto.getCompNews().getDetails().replaceAll("&nbsp;", ""),
									Whitelist.none()));
				}
				if (compNewsDto.getCompNews().getTags() != null) {
					Map<String, String> map = TagsUtils.getInstance()
							.encodeTags(compNewsDto.getCompNews().getTags(),
									"utf-8");
					compNewsDto.setKeyTags(map);
				}
				if (compProfile != null) {
					compNewsDto.setCompName(compProfile.getName());
					compNewsDto.setMemberCode(compProfile.getMemberCode());
				}
			}
			page.getRecords();
			out.put("page", page);
		}
		SeoUtil.getInstance().buildSeo("articlesList", null, null, null, out);
		return null;
	}

	@RequestMapping
	public ModelAndView newsList(HttpServletRequest request,
			Map<String, Object> out, String categoryCode, String categoryKey,
			PageDto<NewsSearchDto> page) throws SolrServerException {
		// 根据类别查别查询信息数（分页）
		// 如果不是推荐资讯

		page.setLimit(8);
		String categoryString = "";
		if (StringUtils.isEmpty(categoryKey)) {
			page = newsService.pageNewsByCategory(categoryCode, page);
			for (NewsSearchDto newsDto : page.getRecords()) {
				if(newsDto.getDetails()!=null){
					newsDto.setDetails(Jsoup.clean(newsDto.getDetails().replaceAll("&nbsp;", ""),Whitelist.none()));
				}
			}
			out.put("page", page);
			categoryString = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_NEWS, categoryCode);
			out.put("categoryCode", categoryCode);
		} else {
			// 推荐资讯信息
			page = newsService.queryValueByTypeAndKey(categoryKey, page);
			for (NewsSearchDto newsDto : page.getRecords()) {
				if (newsDto.getDetails() != null) {
					newsDto.setDetails(Jsoup.clean(newsDto.getDetails().replaceAll("&nbsp;", ""),
							Whitelist.none()));
				}
			}
			out.put("page", page);
			categoryString = ParamUtils.getInstance().getValue(
					"news_recommend_type", categoryKey);
			out.put("categoryCode", categoryKey);
		}
		out.put("category", categoryString);

		// if(categoryString==null || StringUtils.isEmpty(categoryString)){
		// return new ModelAndView("redirect:" +
		// "http://www.huanbao.com/news/zhuanti/index.htm");
		// }
		String titleName = null;
		if (categoryCode.equals("100010041001")) {
			titleName = Technical_Parameters;
		} else if (categoryCode.equals("100010041000")) {
			titleName = Environmental_Protection;
		} else {
			titleName = New_Technology;
		}
		out.put("titleName", titleName);
		String[] titleParam = { categoryString, titleName };

		String[] keywordsParam = { categoryString };
		String[] descriptionParam = { categoryString };
		SeoUtil.getInstance().buildSeo("articlesNews", titleParam,
				keywordsParam, descriptionParam, out);

		return null;
	}

	@RequestMapping
	public ModelAndView details(Map<String, Object> out,
			HttpServletRequest request, Integer id, Integer flag) {

		do {
			if (id == null) {
				break;
			}
            
			CompNews compNews = compNewsService.queryCompNewsById(id);
			if (compNews == null) {
				break;
			}
			compNews.setDetails(Jsoup.clean(compNews.getDetails(), Whitelist.none().addTags("div")));
		
			String compName = compProfileService.queryNameById(compNews
					.getCid());
			if (compName == null) {
				break;
			}
			// 获取公司联系方式
			CompProfileDto compProfileDto = compProfileService
					.queryContactByCid(compNews.getCid());
			out.put("compProfileDto", compProfileDto);
			// 获取uid
			Integer uid = compAccountService.queryUidByCid(compNews.getCid());
			out.put("uid", uid);
           
			// 更新点击数
			compNewsService.updateViewCountById(id);
			// 查询全部列表页上一篇
			CompNews upNews = compNewsService.queryPrevCompNewsById(id, null,
					compNews.getCategoryCode());
			out.put("upNews", upNews);
			// 查询全部列表页下一篇
			CompNews downNews = compNewsService.queryNextCompNewsById(id, null,
					compNews.getCategoryCode());

			out.put("downNews", downNews);

			out.put("id", id);
			out.put("compNews", compNews);
			//标签
			String tags="";
			if(compNews.getTags()!=null){
				tags+=compNews.getTags();
			}
			
			out.put("compName", compName);
			out.put("tagsList", TagsUtils.getInstance().queryTagsByTag(tags.replace("/", ""), TagsUtils.ORDER_SEARCH, 10));
			if (flag != null && flag == 1) {
				out.put("show", 1);
			} else {
				out.put("show", 0);
			}

			SeoUtil.getInstance().buildSeo("articlesDetail", out);
			return new ModelAndView();
		} while (false);

		return brokenRequest();
	}

	@RequestMapping     
	public ModelAndView newsDetails(Map<String, Object> out,
			HttpServletRequest request, Integer id, Integer flag) {

		do {
			EpAuthUser sessionUser=EpAuthUtils.getInstance().getEpAuthUser(request, null);
			if(sessionUser!=null){
				out.put("uid", sessionUser.getUid());
				out.put("cid", sessionUser.getCid());
			}
			
			
			if (id == null || id.intValue() <= 0) {
				break;
			}
            out.put("id", id);
			News news = newsService.queryNewDetailsById(id);
			if (news == null || news.getPauseStatus() == 0) {
				break;
			}
			Document doc = Jsoup.parse(news.getDetails());
			// 修改table标签中的style属性
			doc.select("table").attr("style", "width:100% ;height:100%;");
			// 清除导致表格超出页面的属性，width
			doc.select("table").removeAttr("height");
			doc.select("table").removeAttr("width");
			news.setDetails(doc.select("body").html());
			// 更新点击数
			newsService.updateViewCountById(id);

			out.put("news", news);
			String category = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_NEWS, news.getCategoryCode());

			out.put("category", category);

			// 查询上一篇文章
			News onNews = newsService.queryPrevNewsById(id, news
					.getCategoryCode());
			out.put("onNews", onNews);
			// 查询下一篇文章
			News downNews = newsService.queryNextNewsById(id, news
					.getCategoryCode());
			out.put("downNews", downNews);

			// 视频地址
			Video video = videoService.queryByTypeAndId(id,
					VideoService.TYPE_NEWS);
			if (video != null) {
				out.put("videoContent", video.getContent());
			}

			if (flag != null && flag == 1) {
				out.put("show", 1);
			} else {
				out.put("show", 0);
			}

			String titleName = null;
			if (news.getCategoryCode().equals("100010041001")) {
				titleName = Technical_Parameters;
			} else if (news.getCategoryCode().equals("100010041000")) {
				titleName = Environmental_Protection;
			} else {
				titleName = New_Technology;
			}
			out.put("titleName", titleName);

			String[] titleParam = { category, titleName };

			String[] keywordsParam = { category };
			String[] descriptionParam = { category };
			SeoUtil.getInstance().buildSeo("articlesNewsDetail", titleParam,
					keywordsParam, descriptionParam, out);

			return new ModelAndView();
		} while (false);

		return brokenRequest();
	}

//	/**
//	 * 标签搜索列表
//	 * 
//	 * @param out
//	 * @param request
//	 * @param search
//	 *            页面搜索条件 （添加条件时，修改urlrewrite.xml的url规则）
//	 * @param feature
//	 *            页面专业属性（格式：id:value,id:value）
//	 * @param page
//	 * @return
//	 */
//
//	@RequestMapping
//	public ModelAndView tagsList(Map<String, Object> out, String keywords,
//			PageDto<CompNewsDto> page) {
//
//		// try {
//		// if(StringUtils.isContainCNChar(keywords)){
//		// String value=StringUtils.decryptUrlParameter(keywords);
//		// out.put("value", value);
//		// }
//		//				
//		// } catch (UnsupportedEncodingException e) {
//		//				
//		// e.printStackTrace();
//		// }
//
//		if (keywords == null || keywords == "") {
//			return new ModelAndView("redirect:"
//					+ "/compnews/technicalarticles/index");
//		}
//
//		page.setLimit(8);
//
//		out.put("keywords", keywords);
//		page = compNewsService.pageBySearchEngine(keywords, page);
//		for (CompNewsDto compNewsDto : page.getRecords()) {
//			compNewsDto.getCompNews().setDetails(
//					Jsoup.clean(compNewsDto.getCompNews().getDetails(),
//							Whitelist.none()));
//		}
//
//		out.put("page", page);
//
//		if (page.getTotals() == 0 || page.getTotals() == null) {
//			return new ModelAndView("redirect:"
//					+ "/compnews/technicalarticles/serchNotFound.htm?keywords="
//					+ keywords);
//		}
//		SeoUtil.getInstance().buildSeo("articlesTags", out);
//
//		return null;
//	}

	@RequestMapping
	public ModelAndView register2(Map<String, Object> out, String error,
			String title, String tags, String jsdetails) {

		out.put("title", title);
		out.put("tags", tags);
		out.put("jsdetails", jsdetails);
		out.put("error", error);
		if (StringUtils.isNotEmpty(error)) {
			out.put("errorText", AuthorizeException.getMessage(error));
		}

		SeoUtil.getInstance().buildSeo("articlesLogin", null, null, null, out);

		return null;
	}

	@RequestMapping
	public ModelAndView register(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			String title, String tags, String jsdetails) {

		out.put("title", title);
		out.put("tags", tags);
		out.put("jsdetails", jsdetails);
		// 获取所处行业类型
		List<TradeCategory> companyIndustry = tradeCategoryService
				.queryCategoryByParent("1000", 0, 0);
		out.put("companyIndustry", companyIndustry);
		// 获取公司类型
		List<Param> companyCategory = paramService
				.queryParamsByType("company_industry_code");
		out.put("companyCategory", companyCategory);

		String token = UUID.randomUUID().toString();
		EpAuthUtils.getInstance().setValue(request, response,
				EpConst.REGIST_TOKEN_KEY, token);
		out.put("token", token);

		SeoUtil.getInstance().buildSeo("articlesLogin", null, null, null, out);

		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView publish(Map<String, Object> out,
			HttpServletRequest request, String title, String tags,
			String jsdetails) {
		// 用于返回的时候保存数据
		out.put("title", title);
		out.put("tags", tags);
		out.put("jsdetails", jsdetails);
		EpAuthUser cachedUser = getCachedUser(request);
		if (cachedUser != null) {
			Integer cid = cachedUser.getCid();
			out.put("cid", cid);
			CompProfile compProfile = compProfileService
					.queryCompProfileById(cid);
			out.put("compProfile", compProfile);
		}
		SeoUtil.getInstance()
				.buildSeo("articlesPublish", null, null, null, out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doLoginMini(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String account, String password, String vcode) {
		EpAuthUser user = null;
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			if (StringUtils.isEmpty(password)) {
				break;
			}
			try {
				user = EpAuthUtils.getInstance().validateUser(response,
						account, password, null);
				compAccountService.updateLoginInfo(user.getUid(), null, user
						.getCid());
				EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
				out.put("success", true);
				out.put("data", user.getCid());
				// myEsiteService.init(out, getCachedUser(request).getCid());

			} catch (NoSuchAlgorithmException e) {
				out.put("error", AuthorizeException.ERROR_SERVER);
				out.put("destUrl", destUrl);
				// myEsiteService.init(out, getCachedUser(request).getCid());
				// return new ModelAndView("redirect:loginMini.htm");
				break;
			} catch (IOException e) {
				out.put("error", AuthorizeException.ERROR_SERVER);
				out.put("destUrl", destUrl);
				// myEsiteService.init(out, getCachedUser(request).getCid());
				// return new ModelAndView("redirect:loginMini.htm");
				break;
			} catch (AuthorizeException e) {
				out.put("error", e.getMessage());
				out.put("destUrl", destUrl);
				// myEsiteService.init(out, getCachedUser(request).getCid());
				break;
			}
			if (StringUtils.isEmpty(destUrl)) {
				myEsiteService.init(out, getCachedUser(request).getCid());
				return new ModelAndView("redirect:/");
			} else {
				// myEsiteService.init(out, getCachedUser(request).getCid());
				myEsiteService.init(out, user.getCid());
				return new ModelAndView("redirect:" + destUrl);
			}
		} while (false);
		return new ModelAndView("redirect:register2.htm");
	}

	@RequestMapping
	public ModelAndView loginMini(HttpServletRequest request,
			Map<String, Object> out, String destUrl, String error) {
		// 验证码工具制造验证码
       
		out.put("error", error);
		if (StringUtils.isNotEmpty(error)) {
			out.put("errorText", AuthorizeException.getMessage(error));
		}
		// myEsiteService.init(out, getCachedUser(request).getCid());
		return null;
	}
	@RequestMapping
	public ModelAndView loginMini1(HttpServletRequest request,
			Map<String, Object> out, HttpServletResponse response) {
		// 验证码工具制造验证码
       
		// 获取所处行业类型
		List<TradeCategory> companyIndustry = tradeCategoryService
				.queryCategoryByParent("1000", 0, 0);
		out.put("companyIndustry", companyIndustry);
		// 获取公司类型
		List<Param> companyCategory = paramService
				.queryParamsByType("company_industry_code");
		out.put("companyCategory", companyCategory);

		String token = UUID.randomUUID().toString();
		EpAuthUtils.getInstance().setValue(request, response,
				EpConst.REGIST_TOKEN_KEY, token);
		out.put("token", token);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doLoginMini1(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String account, String password, String vcode) {
		EpAuthUser user = null;
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			if (StringUtils.isEmpty(password)) {
				break;
			}
			try {
				user = EpAuthUtils.getInstance().validateUser(response,
						account, password, null);
				compAccountService.updateLoginInfo(user.getUid(), null, user
						.getCid());
				EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
				out.put("success", true);
				out.put("data", user.getCid());
				// myEsiteService.init(out, getCachedUser(request).getCid());

			} catch (NoSuchAlgorithmException e) {
				out.put("error", AuthorizeException.ERROR_SERVER);
				out.put("destUrl", destUrl);
				// myEsiteService.init(out, getCachedUser(request).getCid());
				// return new ModelAndView("redirect:loginMini.htm");
				break;
			} catch (IOException e) {
				out.put("error", AuthorizeException.ERROR_SERVER);
				out.put("destUrl", destUrl);
				// myEsiteService.init(out, getCachedUser(request).getCid());
				// return new ModelAndView("redirect:loginMini.htm");
				break;
			} catch (AuthorizeException e) {
				out.put("error", e.getMessage());
				out.put("destUrl", destUrl);
				// myEsiteService.init(out, getCachedUser(request).getCid());
				break;
			}
			if (StringUtils.isEmpty(destUrl)) {
				myEsiteService.init(out, getCachedUser(request).getCid());
				return new ModelAndView("redirect:/");
			} else {
				// myEsiteService.init(out, getCachedUser(request).getCid());
				myEsiteService.init(out, user.getCid());
				return new ModelAndView("redirect:" + destUrl);
			}
		} while (false);
		return new ModelAndView("redirect:loginMini.htm");
	}
	
	/**
	 * 技术中心注册
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView doRegist(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String registerCode, String refurl, String verifyCode,
			String token, String compName, String accountName,
			CompProfile profile, CompAccount compAccount, String title,
			String tags, String jsdetails) {
		do {
			// 用于返回的时候保存数据
			out.put("title", title);
			out.put("tags", tags);
			out.put("jsdetails", jsdetails);
			// 注册帐号
			if (profile.getMainBuy() == null && profile.getMainSupply() == null) {
				profile.setMainBuy((short) 0);
				profile.setMainSupply((short) 1);
			}
			compAccount.setName(accountName);
			profile.setName(compName);

			// 判断token
			if (StringUtils.isEmpty(token)) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("0"));
				break;
			}
			//			
			// 判断用户名是否重复
			Integer i = compAccountService.getAccountIdByAccount(compAccount
					.getAccount());
			if (i != null && i > 0) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("3"));
				break;
			}
			//
			String stoken = String.valueOf(EpAuthUtils.getInstance().getValue(
					request, response, EpConst.REGIST_TOKEN_KEY));

			EpAuthUtils.getInstance().remove(request, EpConst.REGIST_TOKEN_KEY);
			if (!token.equals(stoken)) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("0"));
				break;
			}
			//
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

			// 注册公司
			boolean reg = compProfileService.regists(profile, compAccount);
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
			//
			setSessionUser(request, authUser);
			//
			// 发送Email
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", compAccount.getAccount());
			MailUtil.getInstance().sendMail(MailArga.TITLE_REGISTER_SUCCESS,
					compAccount.getEmail(),
					MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
					MailArga.TEMPLATE_REGISTER_SUCCESS_CODE, map,
					MailUtil.PRIORITY_HEIGHT);

			out.put("account", compAccount);
			out.put("profile", profile);
			return new ModelAndView("compnews/technicalarticles/register3");

		} while (false);
		// 用于技术文章返回数据
		out.put("title", title);
		out.put("tags", tags);
		out.put("jsdetails", jsdetails);

		// // 原注册信息
		out.put("account", compAccount);
		out.put("profile", profile);

		// 获取所处行业类型
		List<TradeCategory> companyIndustry = tradeCategoryService
				.queryCategoryByParent("1000", 0, 0);
		out.put("companyIndustry", companyIndustry);
		// 获取公司类型
		List<Param> companyCategory = paramService
				.queryParamsByType("company_industry_code");
		out.put("companyCategory", companyCategory);

		//
		// out.put("refurl", refurl);
		// out.put("registerCode", registerCode);
		//
		token = UUID.randomUUID().toString();
		EpAuthUtils.getInstance().setValue(request, response,
				EpConst.REGIST_TOKEN_KEY, token);
		out.put("token", token);

		return new ModelAndView("compnews/technicalarticles/register");
	}

	@RequestMapping
	public ModelAndView doRegist1(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String registerCode, String refurl, String verifyCode,
			String token, String compName, String accountName,
			CompProfile profile, CompAccount compAccount) {
		do {
			
			// 注册帐号
			if (profile.getMainBuy() == null && profile.getMainSupply() == null) {
				profile.setMainBuy((short) 0);
				profile.setMainSupply((short) 1);
			}
			compAccount.setName(accountName);
			profile.setName(compName);

			// 判断token
			if (StringUtils.isEmpty(token)) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("0"));
				break;
			}
			//			
			// 判断用户名是否重复
			Integer i = compAccountService.getAccountIdByAccount(compAccount
					.getAccount());
			if (i != null && i > 0) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("3"));
				break;
			}
			//
			String stoken = String.valueOf(EpAuthUtils.getInstance().getValue(
					request, response, EpConst.REGIST_TOKEN_KEY));

			EpAuthUtils.getInstance().remove(request, EpConst.REGIST_TOKEN_KEY);
			if (!token.equals(stoken)) {
				out.put("error", EpConst.REGIST_ERROR_MAP.get("0"));
				break;
			}
			//
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

			// 注册公司
			boolean reg = compProfileService.regists(profile, compAccount);
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
			//
			setSessionUser(request, authUser);
			//
			// 发送Email
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", compAccount.getAccount());
			MailUtil.getInstance().sendMail(MailArga.TITLE_REGISTER_SUCCESS,
					compAccount.getEmail(),
					MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
					MailArga.TEMPLATE_REGISTER_SUCCESS_CODE, map,
					MailUtil.PRIORITY_HEIGHT);

			out.put("account", compAccount);
			out.put("profile", profile);
			return new ModelAndView("compnews/technicalarticles/loginMini2");

		} while (false);
	

		// // 原注册信息
		out.put("account", compAccount);
		out.put("profile", profile);

		// 获取所处行业类型
		List<TradeCategory> companyIndustry = tradeCategoryService
				.queryCategoryByParent("1000", 0, 0);
		out.put("companyIndustry", companyIndustry);
		// 获取公司类型
		List<Param> companyCategory = paramService
				.queryParamsByType("company_industry_code");
		out.put("companyCategory", companyCategory);

		//
		// out.put("refurl", refurl);
		// out.put("registerCode", registerCode);
		//
		token = UUID.randomUUID().toString();
		EpAuthUtils.getInstance().setValue(request, response,
				EpConst.REGIST_TOKEN_KEY, token);
		out.put("token", token);

		return new ModelAndView("compnews/technicalarticles/loginMini");
	}
	
	
	
	

	@RequestMapping
	public ModelAndView getAreaCode(Map<String, Object> out,
			HttpServletRequest request, String parentCode, String provinceCode,
			String areaCode) {

		if (StringUtils.isNotEmpty(provinceCode)
				&& StringUtils.isNotEmpty(areaCode)) {
			String codeName = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, provinceCode);
			String areaName = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, areaCode);
			out.put("codeName", codeName);
			out.put("areaName", areaName);
		}

		List<SysArea> sysAreas = sysAreaService.querySysAreasByCode(parentCode);
		return printJson(sysAreas, out);
	}

	
	@RequestMapping
	public ModelAndView serchNotFound(Map<String, Object> out, Integer size,
			String keywords) {

		// 查询一周内最热门的数据
		if (size == null) {
			size = 8;
		}
		List<CompNews> list = compNewsService.queryWeekForArticle(size);

		out.put("list", list);
		out.put("keywords", keywords);
		return null;
	}

	private ModelAndView brokenRequest() {
		return new ModelAndView("resourceNotFound");
	}

	@RequestMapping
	public ModelAndView ajaxUser(HttpServletRequest request,HttpServletResponse response,Map<String, Object> out) {
		 ExtResult result = new ExtResult();
		do {
        	EpAuthUser epAuthUser=getCachedUser(request);
        	if(epAuthUser!=null && epAuthUser.getCid()!=null){
   		      result.setSuccess(true);
   		 }	
        	
		} while (false);
	    return printJson(result, out);
	}	

}
