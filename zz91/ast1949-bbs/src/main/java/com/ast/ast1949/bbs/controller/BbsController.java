/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-20 by liulei
 */
package com.ast.ast1949.bbs.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.bbs.util.BbsConst;
import com.ast.ast1949.domain.bbs.BbsPostCategoryDO;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.bbs.ReportsDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.site.FriendLinkDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.bbs.BbsPostDTO;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.site.FriendLinkDTO;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsPostTypeService;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.service.bbs.BbsViewLogService;
import com.ast.ast1949.service.bbs.ReportsService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.site.FriendLinkService;
import com.ast.ast1949.util.NavConst;
import com.ast.ast1949.util.PageCacheUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;
import com.zz91.util.velocity.AddressTool;

/**
 * @author liulei
 * 
 */
@Controller
public class BbsController extends BaseController {
	// Boolean success = true;
	@Autowired
	private BbsService bbsService;
	@Autowired
	private ReportsService reportsService;
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	@Autowired
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private BbsPostReplyService bbsPostReplyService;
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private CompanyService companyService;
	@Resource
	private BbsViewLogService bbsViewLogService;
	@Resource
	private BbsPostTypeService bbsPostTypeService;

	@Resource
	private FriendLinkService friendLinkService;
	private final static Integer DEFAULT_CATEGORY_ID = 1;

	public static Map<Integer, String> CATEGORY_MAP = new LinkedHashMap<Integer, String>();
	static {
		CATEGORY_MAP.put(1, "废料动态");
		CATEGORY_MAP.put(2, "行业知识");
		CATEGORY_MAP.put(3, "江湖风云");
		CATEGORY_MAP.put(4, "ZZ91动态");
	}

	/**
	 * 初始化bbs首页面
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out)
			throws UnsupportedEncodingException {
		// 设置页面头部信息
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.BBS);
		headDTO.setPageTitle("互助社区_废料论坛_废料生意人的交流平台_ZZ91再生网(原中国再生资源交易网)");
		headDTO.setPageKeywords("再生互助,互助社区,废料论坛,互助月刊,废料问题,商业防骗,废料再生技术");
		headDTO.setPageDescription("ZZ91再生网旗下再生互助社区，在这里你可以找到废料再生技术、"
				+ "废料贸易问题解答、商业防骗技术手段。每月发布互助月刊，报道每月热点关注的废料信息。");

		setPageHead(headDTO, out);

		commonHead(out);

		out.put("categoryMap", categoryMap());

		// 判断是否登入
		SsoUser sessionUser = getCachedUser(request);
		BbsUserProfilerDO bbsUserProfilerDO = new BbsUserProfilerDO();
		if (sessionUser != null) {
			// 个人基本信息
			bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
		}

		// 今日和昨日头条
		List<BbsPostDO> postByType3 = bbsPostService
				.queryPostWithContentByType("3", 4);
		out.put("postByType3", postByType3);

		// 最新动态
		// List<PostDto> postByType4=bbsPostService.queryPostByType("4", 11);
		// out.put("postByType4", postByType4);

		// 热门话题
		// List<PostDto> postByType5=bbsPostService.queryPostByType("5", 33);
		// out.put("postByType5", postByType5);

		// 查询24小时热帖
		// List<BbsPostDO> postDailylist=bbsPostService.query24HourHot(10);

		// 历史牛贴
		// List<BbsPostDO> allBbsPostList =
		// bbsPostService.queryMostViewedPost(10);

		// 最新话题
		// List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(6);

		// 本周牛贴
		// List<AnalysisBbsTop>
		// postOneWeeklist=analysisBbsTopService.queryBbsTopsByType("post", 5);
		// 本周牛人
		// List<AnalysisBbsTop>
		// userProfilerOneWeeklist=analysisBbsTopService.queryBbsTopsByType("profile",
		// 6);
		// 最牛网商
		// List<BbsUserProfilerDO> allIntegralList =
		// bbsUserProfilerService.queryTopByPostNum(6);
		// 互助周报
		// List<BbsPostDO> tagsList1 = bbsService.queryBbsPostBySignType(
		// BbsConst.DEFAULT_SIGN_TYPE1, BbsConst.DEFAULT_ONE_WEEK_SIZE);
		// out.put("tagsList1", bbsPostService.queryPostByType("7", 5));

		// 互助月刊
		// List<BbsPostDO> tagsList2 = bbsService.queryBbsPostBySignType(
		// BbsConst.DEFAULT_SIGN_TYPE2, BbsConst.DEFAULT_ONE_WEEK_SIZE);
		// out.put("tagsList2", bbsPostService.queryPostByType("6", 5));

		// out.put("allIntegralList", allIntegralList);
		// out.put("userProfilerOneWeeklist", userProfilerOneWeeklist);
		// out.put("postOneWeeklist", postOneWeeklist);

		// out.put("postDailylist", postDailylist);

		// out.put("frontBbsPostList", frontBbsPostList);
		// out.put("allBbsPostList", allBbsPostList);

		// 友情链接
		FriendLinkDTO friendLinkDTO = new FriendLinkDTO();
		FriendLinkDO friendLinkDO = new FriendLinkDO();
		friendLinkDO.setIsChecked("1");
		friendLinkDTO.setFriendLinkDO(friendLinkDO);
		out.put("friendLinkList", friendLinkService
				.queryFriendLinkByCondition(friendLinkDTO));

		out.put("bbsUserProfilerDO", bbsUserProfilerDO);

		out.put("url", "index.htm");
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		return new ModelAndView();
	}

	/**
	 * bbs我的头像
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView myalterimg(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "myalterimg.htm");
			return new ModelAndView("/login");
		}
		commonHead(out);
		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		return null;
	}

	/**
	 * 修改头像路径
	 */
	@RequestMapping
	public ModelAndView myupdateimg(String path, HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		commonHead(out);
		Integer i = null;
		ExtResult result = new ExtResult();

		SsoUser sessionUser = getCachedUser(request);
		if (!bbsUserProfilerService.isProfilerExist(sessionUser.getAccount())) {
			bbsUserProfilerService.createEmptyProfilerByUser(sessionUser
					.getAccount());
		}

		i = bbsService.updateBbsUserPicturePath(sessionUser.getAccount(), path);

		if (i > 0) {
			scoreChangeDetailsService
					.saveChangeDetails(new ScoreChangeDetailsDo(sessionUser
							.getCompanyId(), null, "base_bbs_user_avatar",
							null, null, null));
			result.setSuccess(true);

		}
		return printJson(result, out);
	}

	/**
	 * bbs我的基本信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView myalterinfo(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面
		if (sessionUser == null) {
			out.put("url", "myalterinfo.htm");
			return new ModelAndView("/login");
		}
		commonHead(out);
		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		return null;
	}

	/**
	 * 修改基本信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView myupdateinfo(BbsUserProfilerDO bbsUserProfilerDO,
			HttpServletRequest request, Map<String, Object> model)
			throws UnsupportedEncodingException {
		Integer i = null;
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		bbsUserProfilerDO.setAccount(sessionUser.getAccount());
		if (!bbsUserProfilerService.isProfilerExist(sessionUser.getAccount())) {
			// 添加信息
			i = bbsService.insertBbsUserProfiler(bbsUserProfilerDO);
			if (i != null && i.intValue() > 0) {
				scoreChangeDetailsService
						.saveChangeDetails(new ScoreChangeDetailsDo(sessionUser
								.getCompanyId(), null, "base_bbs_profiler",
								null, null, null));
			}
		} else {
			// 修改信息
			i = bbsService.updateSomeBbsUserProfiler(bbsUserProfilerDO);
		}

		if (i != null && i.intValue() > 0) {
			return new ModelAndView(new RedirectView("myalterinfo.htm"));
		}
		return new ModelAndView("/common/error");
	}

	/**
	 * bbs我发表的帖子
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView myposted(PageDto<BbsPostDO> page,
			HttpServletRequest request, Map<String, Object> out)
			throws UnsupportedEncodingException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "myposted.htm");
			return new ModelAndView("/login");
		}
		commonHead(out);

		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		page = bbsPostService.pagePostByUser(sessionUser.getAccount(), null,
				null, page);
		out.put("page", page);
		out.put("bbsPostCount", page.getTotalRecords());
		out.put("categoryMap", categoryMap());
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		return null;
	}

	/**
	 * 添加企业签名
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView addsign(BbsSignDO bbsSignDO,
			HttpServletRequest request, Map<String, Object> model)
			throws UnsupportedEncodingException {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId = sessionUser.getCompanyId();
		String account = sessionUser.getAccount();
		bbsSignDO.setCompanyId(companyId);
		bbsSignDO.setAccount(account);
		Integer i = bbsService.insertBbsSign(bbsSignDO);
		if (i > 0) {
			return new ModelAndView(new RedirectView("mysign.htm"));
		}
		return new ModelAndView("/common/error");
	}

	/**
	 * bbs我的企业标签
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView mysign(String p, HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "mysign.htm");
			return new ModelAndView("/login");
		}
		commonHead(out);

		if (p == null || !StringUtils.isNumber(p)) {
			p = "1";
		}

		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		Integer startIndex = (Integer.valueOf(p) - 1)
				* BbsConst.DEFAULT_POST_MY_SIZE;

		Integer totalRecords = bbsService
				.queryBbsSignByAccountCount(sessionUser.getAccount());
		List<BbsSignDO> bbsSignDOList = bbsService.queryBbsSignByAccount(
				sessionUser.getAccount(), startIndex,
				BbsConst.DEFAULT_POST_MY_SIZE,

				BbsConst.DEFAULT_MODIFIED_TIME_SORT, BbsConst.DEFAULT_POST_DIR);
		for (BbsSignDO o : bbsSignDOList) {
			o.setSignName(StringUtils.controlLength(o.getSignName(), 100));
		}
		// 计算总page大小

		Integer totalPages = totalRecords / BbsConst.DEFAULT_POST_MY_SIZE + 1;
		if (totalRecords % BbsConst.DEFAULT_POST_MY_SIZE == 0) {
			totalPages--;
		}
		out.put("totalPages", totalPages);
		out.put("currentPage", Integer.valueOf(p));
		out.put("bbsSignDOList", bbsSignDOList);
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		return null;
	}

	/**
	 * bbs我的系统留言
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView mymessage(String p, HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "mymessage.htm");
			return new ModelAndView("/login");
		}
		if (p == null || !StringUtils.isNumber(p)) {
			p = "1";
		}
		commonHead(out);
		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		// Integer startIndex = (Integer.valueOf(p) - 1)
		// * BbsConst.DEFAULT_POST_MY_SIZE;

		// Integer totalRecords =
		// bbsService.queryBbsSysMessageByAccountCount(sessionUser
		// .getAccount());
		//
		// List<BbsSysMessageDO> bsSysMessageList = bbsService
		// .queryBbsSysMessageByAccount(sessionUser.getAccount(), startIndex,
		// BbsConst.DEFAULT_POST_MY_SIZE,
		// BbsConst.DEFAULT_MESSAGE_TIME_SORT,
		// BbsConst.DEFAULT_POST_DIR);
		// 计算总page大小

		// Integer totalPages = totalRecords / BbsConst.DEFAULT_POST_MY_SIZE +
		// 1;
		// if (totalRecords % BbsConst.DEFAULT_POST_MY_SIZE == 0) {
		// totalPages--;
		// }
		// out.put("totalPages", totalPages);
		out.put("currentPage", Integer.valueOf(p));
		// out.put("bsSysMessageList", bsSysMessageList);

		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		return null;
	}

	/**
	 * 根据标签查看帖子
	 * 
	 * @param tagsId
	 * @param p
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView bbsTags(Integer tagsId, String p, String title,
			String sign, Map<String, Object> out, HttpServletRequest request)
			throws UnsupportedEncodingException {

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "bbsTags.htm");
			return new ModelAndView("/login");
		}
		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		// 标签页广告
		// BbsPostDTO bbsPostDTO = new BbsPostDTO();
		String keywords = "";
		if (StringUtils.isNotEmpty(title)) {
			keywords = StringUtils.decryptUrlParameter(title);
		} else if (tagsId != null) {
			// 查询标签名
			// BbsTagsDO bbsTagsDO = bbsService.queryBbsTagsById(tagsId);
			keywords = "废电器";
		}
		keywords = URLEncoder.encode(keywords, "utf-8");
		return new ModelAndView("redirect:search.htm?keywords=" + keywords);

		// commonHead(out);
		//
		// SsoUser sessionUser = getCachedUser(request);
		// if (sessionUser != null) {
		//
		// // 个人基本信息
		// BbsUserProfilerDO bbsUserProfilerDO =
		// bbsUserProfilerService.queryProfilerOfAccount(sessionUser.getAccount());
		// out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		// }
		// // 查询24小时热帖
		// List<BbsPostDTO> postDailylist = bbsService
		// .queryBbsPostDailyStatistics(BbsConst.DEFAULT_ONE_DAY_BEFORE,
		// BbsConst.DEFAULT_SUM_SIZE);
		// if (postDailylist == null || postDailylist.size() < 8) {
		// postDailylist = bbsService.queryBbsPostDailyStatistics(5,
		// BbsConst.DEFAULT_ONE_WEEK_SIZE);
		// }
		// out.put("postDailylist", postDailylist);
		//
		// if (p == null || !StringUtils.isNumber(p)) {
		// p = "1";
		// }
		//		
		// Integer startIndex = (Integer.valueOf(p) - 1) *
		// BbsConst.DEFAULT_SIZE10;
		//
		//		
		// PageDto pageDto = new PageDto();
		// String signName = null;
		// if (sign == null) {
		// pageDto.setSort(BbsConst.DEFAULT_POST_TIME_SORT);
		// } else if ("1".equals(sign)) {
		// // 最新话题
		// pageDto.setSort(BbsConst.DEFAULT_POST_MODIFIED_SORT);
		// signName = "最新话题";
		//
		// } else if ("2".equals(sign)) {
		// // 最热话题
		// pageDto.setSort("a.visited_count");
		// signName = "最热话题";
		// } else if ("3".equals(sign)) {
		// // 最新动态
		// pageDto.setSort(BbsConst.DEFAULT_POST_MODIFIED_SORT);
		// bbsPostDTO.setPostType(BbsConst.DEFAULT_POST_TYPE4);
		// signName = "最新动态";
		// } else if ("4".equals(sign)) {
		// // 热门话题
		// pageDto.setSort(BbsConst.REPLY_TIME_SORT);
		// bbsPostDTO.setPostType(BbsConst.DEFAULT_POST_TYPE5);
		// signName = "热门话题";
		// }
		// pageDto.setDir(BbsConst.DEFAULT_POST_DIR);
		// pageDto.setStartIndex(startIndex);
		// pageDto.setPageSize(BbsConst.DEFAULT_SIZE10);
		//
		// bbsPostDTO.setPageDto(pageDto);
		// List<BbsPostDTO> bbsPostDTOList = bbsService
		// .queryBbsPostByTags(bbsPostDTO);
		// Integer totalRecords = bbsService.countBbsPostByTags(bbsPostDTO);
		// // 计算总page大小
		// Integer totalPages = totalRecords / BbsConst.DEFAULT_SIZE10 + 1;
		// if (totalRecords % BbsConst.DEFAULT_SIZE10 == 0) {
		// totalPages--;
		// }
		// for (BbsPostDTO o : bbsPostDTOList) {
		// String safe = Jsoup.clean(o.getBbsPost().getContent(), Whitelist
		// .basicWithImages());
		// Document doc= Jsoup.parse(safe);
		// o.getBbsPost().setContent(doc.select("body").text());
		// }
		// out.put("tagsId", tagsId);
		// out.put("signName", signName);
		// out.put("bbsPostDTOList", bbsPostDTOList);
		// out.put("totalRecords", totalRecords);
		// out.put("totalPages", totalPages);
		// out.put("currentPage", Integer.valueOf(p));
		// out.put("suffixUrl", "");
		// out.put("stringUtil", new StringUtils());
		// out.put("resourceUrl", (String)
		// MemcachedUtils.getInstance().getClient().get(
		// "baseConfig.resource_url"));

		// return null;
	}

	/**
	 * 根据类别查看帖子
	 * 
	 * @param categoryId
	 * @param p
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView bbsCategory(Integer categoryId, PageDto<PostDto> page,
			Map<String, Object> out, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {

			// 个人基本信息

			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());

			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		}
		commonHead(out);

		// 最新话题
		// List<BbsPostDTO> frontBbsPostList = bbsService
		// .queryBbsPostByStatistics(null,
		// BbsConst.DEFAULT_POST_MODIFIED_SORT,
		// BbsConst.DEFAULT_POST_DIR, BbsConst.DEFAULT_SUM_SIZE);
		// List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(6);
		// out.put("frontBbsPostList", frontBbsPostList);

		// 本周牛贴
		// List<BbsStatisticDO> postOneWeeklist = bbsService
		// .queryBbsStatisticByType(BbsConst.BBS_STASTISTIC_TYPE1,
		// BbsConst.DEFAULT_ONE_WEEK_SIZE);
		// List<AnalysisBbsTop>
		// postOneWeeklist=analysisBbsTopService.queryBbsTopsByType("post", 5);
		// out.put("postOneWeeklist", postOneWeeklist);
		// 本周牛人
		// List<BbsStatisticDO> userProfilerOneWeeklist = bbsService
		// .queryBbsStatisticByType(BbsConst.BBS_STASTISTIC_TYPE2,
		// BbsConst.DEFAULT_SUM_SIZE);
		// List<AnalysisBbsTop>
		// userProfilerOneWeeklist=analysisBbsTopService.queryBbsTopsByType("profile",
		// 6);
		// out.put("userProfilerOneWeeklist", userProfilerOneWeeklist);
		// 最牛网商
		// List<BbsUserProfilerDO> allIntegralList =
		// bbsUserProfilerService.queryTopByPostNum(6);
		// out.put("allIntegralList", allIntegralList);

		if (categoryId == null) {
			categoryId = 1;
		}
		page.setPageSize(30);

		BbsPostCategoryDO bbsPostCategoryDO = bbsService
				.queryBbsPostCategoryById(categoryId);
		int currentpage = page.getStartIndex() / page.getPageSize() + 1;
		// 设置页面头部信息
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.BBS);
		headDTO.setPageTitle(bbsPostCategoryDO.getName() + "_互助社区_废料生意人的交流平台_第"
				+ currentpage + "页_ZZ91再生网(原中国再生资源交易网)");
		headDTO.setPageKeywords(bbsPostCategoryDO.getName()
				+ "废料市场动态, 废料供应, 废料求购");
		headDTO
				.setPageDescription("ZZ91再生网旗下互助社区的废料动态商务交流区,在这里你可以找到废料市场动态，废料供应、废料求购，废料价格等废料动态。");
		setPageHead(headDTO, out);
		out.put("category", bbsPostCategoryDO);

		if (page.getSort() == null) {
			page.setSort("reply_time");
		}
		page.setDir("desc");
		page = bbsPostService.pagePostByCategory(categoryId, page);

		out.put("page", page);

		out.put("topPost", bbsService.queryTopPost(categoryId, 3));
		out.put("categoryId", categoryId);
		out.put("categoryName", CATEGORY_MAP.get(categoryId));

		PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);

		return new ModelAndView();
	}

	/**
	 * 发表帖子
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView postReply(String categoryId,
			HttpServletRequest request, Map<String, Object> out)
			throws IOException {
		SsoUser sessionUser = getCachedUser(request);

		if (sessionUser == null) {
			if (categoryId != null) {
				out.put("url", "postReply_c" + categoryId + ".htm");
			} else {
				out.put("url", "postReply.htm");
			}

			return new ModelAndView("/login");
		}
		commonHead(out);
		Integer id = DEFAULT_CATEGORY_ID;
		if (StringUtils.isNumber(categoryId)) {
			id = Integer.parseInt(categoryId);

		}

		BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount());

		out.put("bbsUserProfilerDO", bbsUserProfilerDO);

		if (categoryId != null) {
			BbsPostCategoryDO bbsPostCategoryDO = bbsService
					.queryBbsPostCategoryById(id);
			out.put("bbsPostCategoryDO", bbsPostCategoryDO);
		}
		out.put("id", "upfile");
		out.put("categoryId", categoryId);
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("uploadModelPosted", BbsConst.UPLOAD_MODEL_POSTED);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		return null;
	}

	/**
	 * 发帖
	 * 
	 * @param bbsPostDO
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public void addPostBbs(BbsPostDO bbsPostDO, String tagsName,
			Map<String, Object> out, HttpServletRequest request)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		if (tagsName == null) {
			tagsName = "";
		}
		tagsName = TagsUtils.getInstance().arrangeTags(tagsName);
		bbsPostDO.setAccount(sessionUser.getAccount());
		bbsPostDO.setCompanyId(sessionUser.getCompanyId());
		bbsPostDO.setTags(tagsName);
		Integer id = bbsPostService.createPostByUser(bbsPostDO, sessionUser
				.getMembershipCode());

		if (id > 0) {
			TagsUtils.getInstance().createTags(tagsName);
		}
		out.put("link", "myposted.htm");

	}

	/**
	 * 登入页面
	 */
	@RequestMapping
	public void login(String url, Map<String, Object> out) {
		// 查询热点标签
		commonHead(out);
		out.put("url", url);
	}

	/**
	 * 查看回复
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView viewReply(Integer postId,
			PageDto<BbsPostReplyDto> page, Map<String, Object> out,
			HttpServletRequest request) throws IOException {
		do {
			if (postId == null) {
				break;
			}
			// BbsPostDO bbsPostDO = bbsService.queryBbsPostById(postId);
			BbsPostDO bbsPostDO = bbsPostService.queryPostById(postId);
			if (bbsPostDO == null) {
				break;
			}
			if ("11".equals(bbsPostDO.getPostType())) {
				String url = AddressTool.getAddress("mingxing")+"/detail"+postId+"-t.htm";	
				return new ModelAndView("redirect:"+url);
			}

			SsoUser sessionUser = getCachedUser(request);
			Integer integral = null;
			if (sessionUser != null) {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryProfilerOfAccount(sessionUser.getAccount());
				integral = profiler.getIntegral();
				out.put("bbsUserProfilerDO", profiler);
			}

			if (bbsPostDO.getIntegral() > 0) {
				if (sessionUser == null) {
					return new ModelAndView("/login");
				}

				if (!bbsPostDO.getAccount().equals(sessionUser.getAccount())) {
					if (integral == null) {
						integral = bbsUserProfilerService
								.queryIntegralByAccount(sessionUser
										.getAccount());
					}
					if (integral.intValue() < bbsPostDO.getIntegral()
							.intValue()) {
						break;
					}
				}
			}

			commonHead(out);

			if (bbsPostDO != null) {
				BbsUserProfilerDO bProfiler = bbsUserProfilerService
						.queryProfilerOfAccount(bbsPostDO.getAccount());
				// 楼主信息

				out.put("bProfiler", bProfiler);
				if (bProfiler.getCompanyId() != null
						&& bProfiler.getCompanyId().intValue() > 0) {
					out
							.put("membershipCode", companyService
									.queryMembershipOfCompany(bProfiler
											.getCompanyId()));
				}
			}

			if (bbsPostDO.getCheckStatus() == null
					|| "0".equals(bbsPostDO.getCheckStatus())
					|| "3".equals(bbsPostDO.getCheckStatus())) {
				break;
			}

			Document doc = Jsoup.parse(bbsPostDO.getContent());
			bbsPostDO.setContent(doc.select("body").toString());
			out.put("bbsPostDO", bbsPostDO);
			out.put("postAccount", URLEncoder.encode(bbsPostDO.getAccount(),
					HttpUtils.CHARSET_UTF8));

			if (bbsPostDO.getBbsPostCategoryId() == null) {
				bbsPostDO.setBbsPostCategoryId(1);
			}
			BbsPostCategoryDO category = bbsService
					.queryBbsPostCategoryById(bbsPostDO.getBbsPostCategoryId());
			out.put("bbsPostCategory", category);
			page.setPageSize(10);
			Integer currentpage = page.getStartIndex() / page.getPageSize() + 1;
			// 设置头部信息
			PageHeadDTO headDTO = new PageHeadDTO();
			headDTO.setPageTitle(bbsPostDO.getTitle()
					+ "_"
					+ category.getName()
					+ "_互助社区_第"
					+ currentpage
					+ "页"
					+ ParamUtils.getInstance().getValue("site_info_front",
							"site_name"));
			headDTO.setPageKeywords(bbsPostDO.getTitle() + ","
					+ category.getName());
			if (doc.select("body").text().length() > 100) {
				headDTO.setPageDescription(doc.select("body").text().substring(
						0, 100));
			} else {
				headDTO.setPageDescription(doc.select("body").text());
			}
			setPageHead(headDTO, out);

			// 根据id查询标签m

			// List<BbsTagsDO> bbsTagsDOList = bbsService
			// .queryTagsNameByPostId(postId);

			out.put("bbsTagsDOList", TagsUtils.getInstance().encodeTags(
					bbsPostDO.getTags(), "utf-8"));
			// indexBbsUserProfiler(bbsUserProfilerService.queryProfilerOfAccount(sessionUser
			// .getAccount()), out, sessionUser.getAccount());

			if (StringUtils.isNotEmpty(bbsPostDO.getTags())) {
				if (bbsPostDO.getTags().indexOf(",") > 0) {
					String tag[] = bbsPostDO.getTags().split(",");
					out.put("theKeyword", URLEncoder.encode(tag[1].toString(),
							"utf-8"));
				}

			}

			// out.put("theurlList", URLEncoder.encode(bbsPostDO.getTags(),
			// "utf-8"));
			// 访问量加一
			bbsService.updateBbsPostVisitedCount(postId);
			// 从缓存中获取account信息

			List<BbsSignDO> bbsSignDOList = bbsService.queryBbsSignByAccount(
					bbsPostDO.getAccount(), 0, 1,
					BbsConst.DEFAULT_MODIFIED_TIME_SORT,
					BbsConst.DEFAULT_POST_DIR);
			out.put("bbsSignDOList", bbsSignDOList);
			// 精彩文章
			// List<BbsPostDO> myBbsPostList =
			// bbsPostService.queryHotPost(bbsPostDO.getAccount(), 6);
			// out.put("myBbsPostList", myBbsPostList);
			// 最热话题
			// List<BbsPostDO> hotBbsPostList =
			// bbsPostService.queryHotPost(null, 6);
			// out.put("hotBbsPostList", hotBbsPostList);
			// 最新话题
			// List<BbsPostDO> frontBbsPostList =
			// bbsPostService.queryNewestPost(6);
			// out.put("frontBbsPostList", frontBbsPostList);

			String iconTemplate = "<img src=\"http://img0.zz91.com/bbs/images/bbs/{0}.jpg\" />";
			page = bbsPostReplyService.pageReplyOfPost(postId, iconTemplate,
					page);

			Set<String> accountSet = new HashSet<String>();
			for (BbsPostReplyDto dto : page.getRecords()) {
				accountSet.add(dto.getReply().getAccount());
			}
			Map<String, List<BbsPostDO>> recentPost = bbsPostService
					.queryRecentPostOfAccounts(accountSet);
			out.put("page", page);
			out.put("recentPost", recentPost);

			bbsViewLogService.viewLog(bbsPostDO.getId());
			return null;
		} while (true);
		out.put("errorsign", 1);
		return new ModelAndView("/common/error");
	}

	/**
	 * 回复帖子
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView replyBbs(BbsPostReplyDO bbsPostReplyDO,
			HttpServletRequest request, Map<String, Object> out)
			throws UnsupportedEncodingException {

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "replyBbs.htm");
			return new ModelAndView("/login");
		}
		bbsPostReplyDO.setCompanyId(sessionUser.getCompanyId());
		bbsPostReplyDO.setAccount(sessionUser.getAccount());

		// 查询热点标签
		commonHead(out);
		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
//		if (bbsPostReplyDO.getContent() != null) {
//			String safe = Jsoup.clean(bbsPostReplyDO.getContent(), Whitelist
//					.basicWithImages());
//			safe = Jsoup.parse(safe).text();
//			bbsPostReplyDO.setContent(safe);
//		}

		Integer i = bbsService.insertBbsPostReply(bbsPostReplyDO);
		if (i > 0) {
			// BBS用户不存在，添加该用户
			if (!bbsUserProfilerService.isProfilerExist(sessionUser
					.getAccount())) {
				bbsUserProfilerService.createEmptyProfilerByUser(sessionUser
						.getAccount());
			}
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
			out.put("bbsPostReplyDO", bbsPostReplyDO);
			out.put("link", "viewReply" + bbsPostReplyDO.getBbsPostId()
					+ ".htm");
			return null;
		} else {
			return new ModelAndView("/common/error");
		}

	}

	/**
	 * bbs我的帖子回复
	 * 
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping
	public ModelAndView myreply(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {

			out.put("url", "myalterimg.htm");
			return new ModelAndView("/login");
		}

		// if (p == null || !StringUtils.isNumber(p)) {
		// p = "1";
		// }

		// 查询热点标签
		commonHead(out);
		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		out.put("countMyposted", bbsPostService.countMyposted(sessionUser
				.getAccount(), null, null));
		page = bbsPostReplyService.pageReplyByUser(sessionUser.getAccount(),
				null, page);
		out.put("page", page);

		// Integer startIndex= (Integer.valueOf(p) - 1)
		// * BbsConst.DEFAULT_POST_MY_SIZE;
		// Integer totalRecords = bbsService
		// .queryBbsPostReplyAndCategoryByAccountCount(sessionUser.getAccount());
		// List<BbsPostDTO> bbsPostReplyList = bbsService
		// .queryBbsPostReplyAndCategoryByAccount(sessionUser.getAccount(),
		// startIndex, BbsConst.DEFAULT_POST_MY_SIZE,
		// BbsConst.DEFAULT_POST_TIME_SORT,
		// BbsConst.DEFAULT_POST_DIR);
		// // 计算总page大小
		//
		// Integer totalPages = totalRecords / BbsConst.DEFAULT_POST_MY_SIZE +
		// 1;
		// if (totalRecords % BbsConst.DEFAULT_POST_MY_SIZE == 0) {
		// totalPages--;
		// }
		// out.put("totalPages", totalPages);
		// out.put("currentPage", Integer.valueOf(p));
		// out.put("bbsPostReplyList", bbsPostReplyList);

		out.put("categoryMap", categoryMap());

		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		// out.put("resourceUrl", (String)
		// MemcachedUtils.getInstance().getClient().get(
		// "baseConfig.resource_url"));
		out.put("url", "myreply.htm");
		return null;
	}

	/**
	 * 他人发表的帖子
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView othersposted(Integer id, PageDto<BbsPostDO> page,
			HttpServletRequest request, Map<String, Object> out)
			throws UnsupportedEncodingException {
		// 他人账号
		if (id == null) {
			return new ModelAndView("/common/error");
		}

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "othersposted"+id+".htm");
			return new ModelAndView("/login");
		}
		BbsUserProfilerDO bbsUser = bbsService.queryBbsUserProfilerById(id);
		out.put("bbsUser", bbsUser);
		// 最新话题
		// List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(6);

		// out.put("frontBbsPostList", frontBbsPostList);
		// 谁回复我的帖子
		if (bbsUser != null) {
			List<BbsPostDTO> bbsReplyList = bbsService
					.queryUserNicknameByReply(bbsUser.getAccount(),
							BbsConst.DEFAULT_START_INDEX,
							BbsConst.DEFAULT_SIZE,
							BbsConst.DEFAULT_POST_MODIFIED_SORT,
							BbsConst.DEFAULT_POST_DIR);
			out.put("bbsReplyList", bbsReplyList);
		}

		page = bbsPostService.pagePostByUser(bbsUser.getAccount(), null, null,
				page);
		out.put("page", page);

		out.put("categoryMap", categoryMap());
		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		out.put("id", id);
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("url", "othersposted" + id + ".htm");

		// 查询热点标签
		commonHead(out);

		// out.put("stringUtil", new StringUtils());
		return null;
	}

	/**
	 * 他人回复的帖子
	 * 
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping
	public ModelAndView othersreply(Integer id, PageDto<BbsPostDO> page,
			HttpServletRequest request, Map<String, Object> out)
			throws UnsupportedEncodingException {
		// 他人账号
		if (id == null) {
			return new ModelAndView("/common/error");
		}
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "othersreply.htm");
			return new ModelAndView("/login");
		}
		BbsUserProfilerDO bbsUser = bbsService.queryBbsUserProfilerById(id);
		out.put("bbsUser", bbsUser);
		// 谁回复我的帖子
		if (bbsUser != null) {
			List<BbsPostDTO> bbsReplyList = bbsService
					.queryUserNicknameByReply(bbsUser.getAccount(),
							BbsConst.DEFAULT_START_INDEX,
							BbsConst.DEFAULT_SIZE,
							BbsConst.DEFAULT_POST_MODIFIED_SORT,
							BbsConst.DEFAULT_POST_DIR);
			out.put("bbsReplyList", bbsReplyList);
		}
		// 最新话题
		// List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(6);
		// out.put("frontBbsPostList", frontBbsPostList);

		page = bbsPostReplyService.pageReplyByUser(bbsUser.getAccount(), "1",
				page);
		out.put("page", page);

		out.put("categoryMap", categoryMap());

		indexBbsUserProfiler(bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount()), out,
				sessionUser.getAccount());
		out.put("id", id);
		out.put("url", "othersreply" + id + ".htm");
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);

		commonHead(out);
		return null;
	}

	/**
	 * 编辑帖子：初始化
	 * 
	 * @param postId
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "postEdit.htm", method = RequestMethod.GET)
	public ModelAndView postEdit(Integer postId, HttpServletRequest request,
			Map<String, Object> out) throws IOException {

		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {

			return printJs("alert('对不起，您还没有登录！')", out);
		}
		BbsPostDO bbsPostDO = bbsPostService.queryPostById(postId);
		if (!sessionUser.getAccount().equals(bbsPostDO.getAccount())) {
			return printJs("alert('对不起，您无权修改此帖！')", out);
		} else {
			out.put("bbsPostDO", bbsPostDO);
			out.put("resourceUrl", (String) MemcachedUtils.getInstance()
					.getClient().get("baseConfig.resource_url"));
			return null;
		}
	}

	/**
	 * 编辑帖子
	 * 
	 * @param postId
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "postEdit.htm", method = RequestMethod.POST)
	public ModelAndView postEdit(BbsPostDO bbsPostDO,
			HttpServletRequest request, Map<String, Object> out)
			throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		Integer i = bbsPostService.updatePostByUser(bbsPostDO, sessionUser
				.getMembershipCode());
		if("10051000".equals(sessionUser.getMembershipCode())){
			bbsPostService.updateCheckStatusForFront(bbsPostDO.getId(), "0");
		}

		// 查询客户会员类型

		if (i != null && i.intValue() > 0) {
			return printJs(
					"alert('修改成功！');parent.onHideBox();parent.location.reload();",
					out);
		} else {
			return printJs(
					"alert('修改失败！');parent.document.getElementById(\"close\").click();",
					out);
		}
	}

	/**
	 * 删除帖子
	 * 
	 * @param postId
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "postDel.htm", method = RequestMethod.GET)
	public ModelAndView postDel(Integer postId, HttpServletRequest request,
			Map<String, Object> out) throws IOException {

		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			return printJs(
					"alert('对不起，您还没有登录！');window.location.href='/login.htm';",
					out);
		} else {
			BbsPostDO bbsPostDO = bbsPostService.queryPostById(postId);
			if (!sessionUser.getAccount().equals(bbsPostDO.getAccount())) {
				return printJs(
						"alert('对不起，您无权删除此帖！');window.location.href='/myposted.htm';",
						out);
			} else {
				bbsUserProfilerService.updateBbsReplyCount(sessionUser
						.getAccount());
				Integer im = bbsPostService.deleteBbsPost(postId);

				if (im.intValue() > 0) {
					bbsUserProfilerService.updatePostNumber(sessionUser
							.getAccount());
					return printJs(
							"alert('帖子已成功删除！');window.location.href='/myposted.htm';",
							out);

				} else {
					return printJs(
							"alert('删除失败！');window.location.href='/myposted.htm';",
							out);
				}
			}
		}
	}

	/**
	 * 图片上传页面初始化
	 * 
	 * @param out
	 * @param model
	 *            所属模块，如：bbs，ads，news
	 * @param filetype
	 *            文件类型，如：img，doc，zip
	 */
	@RequestMapping
	public void ajaxUpload(Map<String, Object> out, String model,
			String filetype) {

		out.put("filetype", filetype);
		out.put("model", model);
	}

	/**
	 * 
	 * @param out
	 * @param id
	 *            被举报信息ID
	 * @param reportsDO
	 * @param type
	 *            举报类型 （0代表举报帖子，1代表举报留言）
	 * @param account
	 *            举报人帐号
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping
	public void reports(Map<String, Object> out, Integer id,
			ReportsDO reportsDO, String type, HttpServletRequest request)
			throws IOException {
		out.put("id", id);
		out.put("account", getCachedUser(request).getAccount());

		out.put("type", type);
	}

	/**
	 * 提交举报信息
	 */
	@RequestMapping
	public ModelAndView reports_sub(ReportsDO reportsDO,
			Map<String, Object> out, HttpServletRequest request)
			throws IOException {
		reportsDO.setIp(HttpUtils.getInstance().getIpAddr(request));
		Integer i = reportsService.insertReportsDO(reportsDO);
		if (i > 0) {
			return printJs(
					"alert('举报成功，谢谢你的参与，我们将会尽快核实和处理！');parent.location.reload();",
					out);
		} else {
			return printJs("alert('举报失败！');history.back();", out);
		}
	}

	@RequestMapping
	public void other(Map<String, Object> out, HttpServletRequest request) {
		// 查询热点标签
		commonHead(out);
		// 判断是否登入
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		}

	}

	/**
	 * 我的互助社区
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void indexBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO,
			Map<String, Object> out, String loginedAccount)
			throws UnsupportedEncodingException {
		out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		// 个人基本信息
		// BbsUserProfilerDO bbsUserProfilerDO =
		// bbsUserProfilerService.queryProfilerOfAccount(account);
		// 最新话题
		// List<BbsPostDTO> frontBbsPostList = bbsService
		// .queryBbsPostByStatistics(null,
		// BbsConst.DEFAULT_POST_MODIFIED_SORT,
		// BbsConst.DEFAULT_POST_DIR, BbsConst.DEFAULT_SIZE);
		// List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(8);
		// out.put("frontBbsPostList", frontBbsPostList);

		// 谁回复我的帖子
		List<BbsPostDTO> bbsReplyList = bbsService.queryUserNicknameByReply(
				loginedAccount, BbsConst.DEFAULT_START_INDEX,
				BbsConst.DEFAULT_SIZE, BbsConst.DEFAULT_POST_MODIFIED_SORT,
				BbsConst.DEFAULT_POST_DIR);
		out.put("countReply", bbsPostReplyService.countMyreply(loginedAccount,
				null));
		out.put("countPosted", bbsPostService.countMyposted(loginedAccount,
				null, null));
		out.put("bbsReplyList", bbsReplyList);
		out.put("bbsReplyCount", bbsReplyList.size());
		// out.put("stringUtil", new StringUtils());
	}

	/**
	 * 
	 * @param searchTit
	 *            搜索关键字
	 * @param searchSorts
	 *            搜索类型 0>帖子 1>社区 2>会员
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView search(Map<String, Object> out,
			HttpServletRequest request, String keywords, PageDto<PostDto> page) {

		if (StringUtils.isEmpty(keywords)) {
			return null;
		}

		try {
			keywords = StringUtils.decryptUrlParameter(keywords);
			out.put("keywords", keywords);
			out.put("encodeKeywords", URLEncoder.encode(keywords, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		page = bbsPostService.pagePostBySearchEngine(keywords, page);
		// /如果page查询结果为空，
		if (page.getRecords().size() == 0 && "最新话题".equals(keywords)) {
			page = bbsPostService.pageTheNewsPost(16, page);
		}
		if ("互助周报".equals(keywords)) {
			page.setPageSize(16);
			page = bbsPostService.pageMorePostByType("7", page);
		}
		for (PostDto obj : page.getRecords()) {
			obj.getPost().setContent(
					Jsoup.clean(obj.getPost().getContent(), Whitelist.none()));
		}
		out.put("page", page);

		// 查询热点标签
		commonHead(out);

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {

			// 个人基本信息
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		}

		// 查询24小时热帖
		// List<BbsPostDO> postDailylist=bbsPostService.query24HourHot(10);
		// out.put("postDailylist", postDailylist);
		int currentpage = page.getStartIndex() / page.getPageSize() + 1;
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.BBS);
		headDTO.setPageTitle(keywords + ",互助社区_废料论坛_废料生意人的交流平台_第" + currentpage
				+ "页_ZZ91再生网");
		headDTO.setPageKeywords(keywords + ",再生互助,互助社区,废料论坛,互助月刊," + keywords
				+ "问题,商业防骗,废料再生技术");
		headDTO.setPageDescription("ZZ91再生网旗下再生互助社区，在这里你可以找到废料再生技术、"
				+ "废料贸易问题解答、商业防骗技术手段。每月发布互助月刊，报道每月热点关注的废料信息。");

		setPageHead(headDTO, out);

		return null;
	}

	/**
	 * 根据post_type 检索列表 分页 周报、月刊
	 */
	@RequestMapping
	public ModelAndView list(Map<String, Object> out,
			HttpServletRequest request, String postType, PageDto<PostDto> page) {

		page = bbsPostService.pageMorePostByType(postType, page);
		// /如果page查询结果为空
		for (PostDto obj : page.getRecords()) {
			obj.getPost().setContent(
					Jsoup.clean(obj.getPost().getContent(), Whitelist.none()));
		}
		out.put("page", page);

		// 查询热点标签
		commonHead(out);

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {

			// 个人基本信息
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		}
		String keywords = bbsPostTypeService.queryNameById(postType);
		int currentpage = page.getStartIndex() / page.getPageSize() + 1;
		// PageHeadDTO headDTO = new PageHeadDTO();
		// headDTO.setTopNavIndex(NavConst.BBS);
		// headDTO.setPageTitle(keywords+"_互助社区_废料论坛_废料生意人的交流平台_第"+currentpage+"页_ZZ91再生网");
		// headDTO.setPageKeywords(keywords+",再生互助,互助社区,废料论坛,互助月刊,"+keywords+"问题,商业防骗,废料再生技术");
		// headDTO.setPageDescription("ZZ91再生网旗下再生互助社区，在这里你可以找到废料再生技术、"
		// + "废料贸易问题解答、商业防骗技术手段。每月发布互助月刊，报道每月热点关注的废料信息。");
		//		
		// setPageHead(headDTO, out);
		SeoUtil.getInstance().buildSeo("list",
				new String[] { keywords, String.valueOf(currentpage) },
				new String[] { keywords }, new String[] {}, out);
		out.put("postType", postType);

		return null;
	}

	private Map<Integer, String> categoryMap() {
		Map<Integer, String> categoryMap = new HashMap<Integer, String>();
		categoryMap.put(1, "废料动态");
		categoryMap.put(2, "行业知识");
		categoryMap.put(3, "江湖风云");
		categoryMap.put(4, "zz91动态");
		return categoryMap;
	}

	private void commonHead(Map<String, Object> out) {
		Map<String, String> tagsMap = new LinkedHashMap<String, String>();
		try {
			tagsMap.put("废电器", URLEncoder.encode("废电器", "utf-8"));
			tagsMap.put("国内资讯", URLEncoder.encode("国内资讯", "utf-8"));
			tagsMap.put("废料百态", URLEncoder.encode("废料百态", "utf-8"));
			tagsMap.put("行情综述", URLEncoder.encode("行情综述", "utf-8"));
			tagsMap.put("今日导读", URLEncoder.encode("今日导读", "utf-8"));
			tagsMap.put("国外资讯", URLEncoder.encode("国外资讯", "utf-8"));
			tagsMap.put("热门评论", URLEncoder.encode("热门评论", "utf-8"));
			tagsMap.put("资讯评论", URLEncoder.encode("资讯评论", "utf-8"));
			tagsMap.put("商务交流", URLEncoder.encode("商务交流", "utf-8"));
			tagsMap.put("焦点关注", URLEncoder.encode("焦点关注", "utf-8"));
		} catch (UnsupportedEncodingException e) {
		}
		// 查询热点标签 100010001000
		out.put("tagsList", TagsUtils.getInstance().queryTagsByCode(
				"100010001000", 0, 12));
	}

	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			// data=URLDecoder.decode(data, HttpUtils.CHARSET_UTF8);
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}
}
