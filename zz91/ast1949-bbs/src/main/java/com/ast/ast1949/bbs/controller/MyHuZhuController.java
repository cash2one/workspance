package com.ast.ast1949.bbs.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.domain.bbs.BbsScore;
import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.domain.bbs.BbsSystemMessage;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.bbs.BbsPostDTO;
import com.ast.ast1949.dto.bbs.BbsUserProfilerDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.service.bbs.BbsPostNoticeRecommendService;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsPostTagsService;
import com.ast.ast1949.service.bbs.BbsPostTrendsService;
import com.ast.ast1949.service.bbs.BbsScoreService;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.bbs.BbsSignService;
import com.ast.ast1949.service.bbs.BbsSystemMessageService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.BbsConst;
import com.ast.ast1949.util.NavConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

@Controller
public class MyHuZhuController extends BaseController {

	@Resource
	private BbsService bbsService;
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private MyfavoriteService myfavoriteService;
	@Resource
	private BbsPostReplyService bbsPostReplyService;
	@Resource
	private BbsScoreService bbsScoreService;
	@Resource
	private BbsPostNoticeRecommendService bbsPostNoticeRecommendService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private BbsSignService bbsSignService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private BbsPostTrendsService bbsPostTrendsService;
	@Resource
	private BbsSystemMessageService bbsSystemMessageService;
	@Resource
	private BbsPostTagsService bbsPostTagsService;

	/**
	 * 发起的问题
	 * 
	 * @param request
	 * @param type
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView list_qa(HttpServletRequest request,
			Map<String, Object> out, Integer type, String title,
			PageDto<PostDto> page, HttpServletResponse response)
			throws UnsupportedEncodingException {
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		page.setSort("reply_time");
		page.setDir("desc");
		if (type == null) {
			type = 1;
		}

		if (StringUtils.isNotEmpty(title)
				&& !StringUtils.isContainCNChar(title)) {
			title = URLDecoder.decode(title, HttpUtils.CHARSET_UTF8);
			out.put("title", title);
			out.put("titleEncode",
					URLEncoder.encode(title, HttpUtils.CHARSET_UTF8));
		}

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			page = bbsPostService.ListBbsPostByUser(
					BbsPostService.BBS_CATEGORY_QA, sessionUser.getAccount(),
					page, type, title);
		}

		out.put("page", page);
		out.put("type", type);
		// 初始化
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		SeoUtil.getInstance().buildSeo("list_qa", out);
		String path = "";
		if (type == 1) {
			path = "mywenda/posted";
		} else {
			path = "mywenda/reply";
		}
		out.put("path", path);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/**
	 * 商圈发起的问题
	 * 
	 * @param request
	 * @param type
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView list_sq(HttpServletRequest request,
			Map<String, Object> out, PageDto<PostDto> page,
			HttpServletResponse response, Integer type)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		page.setSort("reply_time");
		page.setDir("desc");
		if (type == 1) {
			page = bbsPostService.ListBbsPostByUser(
					BbsPostService.BBS_CATEGORY_SQ, sessionUser.getAccount(),
					page, type, null);
		} else {
			page = bbsPostService.ListBbsPostByUser(
					BbsPostService.BBS_CATEGORY_SQ, sessionUser.getAccount(),
					page, type, null);
		}
		out.put("type", type);
		out.put("page", page);
		SeoUtil.getInstance().buildSeo("list_post", out);
		Map<Integer, String> mapPath=getPath();
		Map<Integer, String> mapCategory = getCategoryName();
		out.put("mapPath", mapPath);
		out.put("mapCategory", mapCategory);
		// 清除 cdn
		clearCDN(response);
		if (type == 1) {
			String url = "postSq";
			out.put("path", url);
			return new ModelAndView();
		} else {
			String url = "postReply";
			out.put("path", url);
			return new ModelAndView("/myhuzhu/list_sqReply");
		}
	}

	/**
	 * 系统 消息列表页
	 * 
	 * @param request
	 * @param type
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView list_sys_message(HttpServletRequest request,
			Map<String, Object> out, BbsSystemMessage bbsSystemMessage,
			PageDto<BbsSystemMessage> page, HttpServletResponse response)
			throws UnsupportedEncodingException {
		page.setPageSize(10);

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			page = bbsSystemMessageService.pageList(bbsSystemMessage, page);
		}

		out.put("page", page);

		// 初始化
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/**
	 * 收藏的问题和帖子
	 * 
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping
	public ModelAndView list_favorite(HttpServletRequest request,
			PageDto<MyfavoriteDO> page, Map<String, Object> out,
			String keywords, String type, HttpServletResponse response)
			throws UnsupportedEncodingException {
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "favoritePost.htm");
			return new ModelAndView("/login");
		}
		if (StringUtils.isNotEmpty(keywords)
				&& !StringUtils.isContainCNChar(keywords)) {
			keywords = StringUtils.decryptUrlParameter(keywords);
		}
		out.put("keywords", keywords);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		commonHead(out);

		page.setPageSize(10);
		if ("2".equals(type)) {
			page = myfavoriteService.pageMyCollect(page, keywords,
					sessionUser.getCompanyId(), "10091010", null);
		} else if ("1".equals(type)) {
			page = myfavoriteService.pageMyCollect(page, keywords,
					sessionUser.getCompanyId(), "10091005", null);
		} else if ("3".equals(type)) {
			page = myfavoriteService.pageMyCollect(page, keywords,
					sessionUser.getCompanyId(), "10091011", null);
		} else {
			page = myfavoriteService.pageMyCollectForMyhuzhu(page, keywords,
					sessionUser.getCompanyId(), null, null);
		}
		List<PostDto> list = new ArrayList<PostDto>();
		for (MyfavoriteDO favoriteDO : page.getRecords()) {
			PostDto postDto = new PostDto();
			BbsPostDO bbsPost = bbsPostService.queryPostById(favoriteDO
					.getContentId());
			if (bbsPost == null) {
				continue;
			}
			bbsPost.setContent(Jsoup.clean(bbsPost.getContent(),
					Whitelist.none()));
			postDto.setPost(bbsPost);
			BbsPostReplyDO reply = bbsPostReplyService
					.queryLatestReplyByPostId(bbsPost.getId());
			if (reply != null) {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryUserByCompanyId(reply.getCompanyId());
				if (profiler != null
						&& StringUtils.isNotEmpty(profiler.getNickname())) {
					postDto.setReplyName(profiler.getNickname());
				} else {
					CompanyAccount ca = companyAccountService
							.queryAccountByCompanyId(reply.getCompanyId());
					if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
						postDto.setReplyName(ca.getContact());
					}
				}
			}
			list.add(postDto);
		}
		out.put("list", list);
		out.put("page", page);
		out.put("type", type);
		SeoUtil.getInstance().buildSeo("list_favorite", out);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView list_follow(Map<String, Object> out,
			HttpServletRequest request, String type, PageDto<PostDto> page,
			HttpServletResponse response) {

		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());

		out.put("type", type);

		if ("1".equals(type)) {
			out.put("page", bbsPostNoticeRecommendService.pageNotice(
					sessionUser.getAccount(),
					BbsPostNoticeRecommendService.CATEGORY_POST, page));
		} else if ("2".equals(type)) {
			out.put("page", bbsPostNoticeRecommendService.pageNotice(
					sessionUser.getAccount(),
					BbsPostNoticeRecommendService.CATEGORY_QA, page));
		} else if ("3".equals(type)) {
			out.put("tagList", bbsPostNoticeRecommendService.queryNoticeByUser(
					sessionUser.getAccount(),
					BbsPostNoticeRecommendService.CATEGORY_TAG, 9));
		} else if ("4".equals(type)) {
			out.put("postList", buildPostDto(bbsPostNoticeRecommendService
					.queryNoticeByUser(sessionUser.getAccount(),
							BbsPostNoticeRecommendService.CATEGORY_POST, 3)));
			out.put("qaList", buildPostDto(bbsPostNoticeRecommendService
					.queryNoticeByUser(sessionUser.getAccount(),
							BbsPostNoticeRecommendService.CATEGORY_QA, 3)));
			out.put("tagList", bbsPostNoticeRecommendService.queryNoticeByUser(
					sessionUser.getAccount(),
					BbsPostNoticeRecommendService.CATEGORY_TAG, 9));
		}
		String[] title = new String[2];
		if (Integer.valueOf(type) == 1) {
			title[0] = "关注的帖子";
		} else if (Integer.valueOf(type) == 2) {
			title[0] = "关注的问题";
		} else if (Integer.valueOf(type) == 4) {
			title[0] = "关注的动态";
		}
		SeoUtil.getInstance().buildSeo("list_follow", title, null, null, out);
		String path = "";
		if ("1".equals(type)) {
			path = "guanzhu/tiezi";
		} else if ("2".equals(type)) {
			path = "guanzhu/huida";
		}
		out.put("path", path);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/**
	 * 初始化用户信息
	 */
	private void initBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO,
			Map<String, Object> out, String loginedAccount) {
		out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		out.put("CategoryMap", BbsService.BBS_POST_CATEGORY_MAP);
		bbsService.countBbsInfo(out, loginedAccount,
				bbsUserProfilerDO.getCompanyId());
		Integer i = 0;
		i = bbsPostService.countMyposted(loginedAccount, "1", null, null);
		out.put("passPost", i);
		i = bbsPostService.countMyposted(loginedAccount, "1", null, 11);
		out.put("passQA", i);
		// 关注数
		BbsPostNoticeRecommend bbs = new BbsPostNoticeRecommend();
		bbs.setState(0);
		bbs.setType(1);
		bbs.setCompanyId(bbsUserProfilerDO.getCompanyId());
		out.put("countRecom",
				bbsPostNoticeRecommendService.countNumbyCompanyId(bbs));
		// 收藏数
		out.put("countFavorite", myfavoriteService
				.countByCompanyId(bbsUserProfilerDO.getCompanyId()));
	}

	public void commonHead(Map<String, Object> out) {
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
		out.put("tagsList",
				TagsUtils.getInstance().queryTagsByCode("100010001000", 0, 12));
	}

	private List<PostDto> buildPostDto(List<BbsPostNoticeRecommend> list) {
		List<PostDto> nlist = new ArrayList<PostDto>();
		for (BbsPostNoticeRecommend bbs : list) {
			PostDto postDto = new PostDto();
			BbsPostDO bbsPost = bbsPostService
					.queryPostById(bbs.getContentId());

			bbsPost.setContent(Jsoup.clean(bbsPost.getContent(),
					Whitelist.none()));
			BbsPostReplyDO reply = bbsPostReplyService
					.queryLatestReplyByPostId(bbsPost.getId());
			if (reply != null) {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryUserByCompanyId(reply.getCompanyId());
				if (profiler != null
						&& StringUtils.isNotEmpty(profiler.getNickname())) {
					postDto.setReplyName(profiler.getNickname());
					;
				} else {
					CompanyAccount ca = companyAccountService
							.queryAccountByCompanyId(reply.getCompanyId());
					if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
						postDto.setReplyName(ca.getContact());
					}
				}
			}
			postDto.setPost(bbsPost);
			nlist.add(postDto);
		}
		return nlist;
	}

	@RequestMapping
	public ModelAndView list_score(HttpServletRequest request,
			Map<String, Object> out, HttpServletResponse response) {
		SsoUser sessionUser = getCachedUser(request);
		commonHead(out);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());

		BbsUserProfilerDO obj = (BbsUserProfilerDO) out
				.get("bbsUserProfilerDO");
		BbsScore bbsScore = new BbsScore();
		bbsScore.setCompanyId(obj.getCompanyId());
		obj.setIntegral(bbsScoreService.sumScore(bbsScore));
		out.put("bbsUserProfilerDO", obj);

		out.put("rank", bbsUserProfilerService.queryRank(obj.getIntegral()));

		PageDto<BbsUserProfilerDto> page = new PageDto<BbsUserProfilerDto>();
		page.setSort("integral");
		page.setDir("desc");
		page.setPageSize(10);
		bbsUserProfilerService.pageUserByAdmin(new BbsUserProfilerDto(), page);

		out.put("page", page);

		// seo
		// 设置页面头部信息
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.BBS);
		headDTO.setPageTitle("互助社区_废料论坛_废料生意人的交流平台_ZZ91再生网");
		headDTO.setPageKeywords("再生互助,互助社区,废料论坛,互助月刊,废料问题,商业防骗,废料再生技术");
		headDTO.setPageDescription("ZZ91再生网旗下再生互助社区，在这里你可以找到废料再生技术、"
				+ "废料贸易问题解答、商业防骗技术手段。每月发布互助月刊，报道每月热点关注的废料信息。");
		setPageHead(headDTO, out);
		out.put("score", "answer");

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/**
	 * 等我答
	 * 
	 * @param out
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping
	public ModelAndView list_waitAnswer(Map<String, Object> out,
			HttpServletRequest request, PageDto<PostDto> page,
			HttpServletResponse response) {
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		page.setSort("reply_time");
		page.setDir("desc");
		SsoUser sessionUser = getCachedUser(request);
		List<BbsPostTags> list = bbsPostNoticeRecommendService
				.queryListForMyFollowSearch(sessionUser.getAccount());
		if (list.size() > 0) {
			String keywords = "";
			for (BbsPostTags obj : list) {
				if (obj != null) {
					keywords = keywords + "(" + obj.getTagName() + ")|";
				}
			}
			if (keywords.endsWith("|")) {
				keywords = keywords.substring(0, keywords.length() - 1);
			}
			page = bbsPostService.pagePostForNoAnswerBySearchEngine(keywords,
					page);
		} else {
			page = bbsPostService.ListWaitAnswer(page);
		}

		out.put("page", page);

		// 初始化
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		SeoUtil.getInstance().buildSeo("list_waitAnswer", out);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/**
	 * bbs我发表的帖子
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView list_post(PageDto<PostDto> page,
			HttpServletRequest request, Map<String, Object> out,
			HttpServletResponse response) throws UnsupportedEncodingException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		page.setSort("reply_time");
		page.setDir("desc");
		page = bbsPostService.ListBbsPostByUser(
				BbsPostService.BBS_CATEGORY_POST, sessionUser.getAccount(),
				page, 1, null);
		out.put("page", page);
		SeoUtil.getInstance().buildSeo("list_post", out);
		Map<Integer, String> mapPath = getPath();
		Map<Integer, String> mapCategory = getCategoryName();
		out.put("mapPath", mapPath);
		out.put("mapCategory", mapCategory);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();

	}

	/**
	 * bbs我发表的帖子
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView list_reply(PageDto<PostDto> page,
			HttpServletRequest request, Map<String, Object> out,
			HttpServletResponse response) throws UnsupportedEncodingException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		page.setSort("reply_time");
		page.setDir("desc");
		page = bbsPostService.ListBbsPostByUser(
				BbsPostService.BBS_CATEGORY_POST, sessionUser.getAccount(),
				page, 2, null);
		out.put("page", page);
		SeoUtil.getInstance().buildSeo("list_reply", out);
		Map<Integer, String> mapPath = getPath();
		Map<Integer, String> mapCategory = getCategoryName();
		out.put("mapPath", mapPath);
		out.put("mapCategory", mapCategory);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/***
	 * 私信收件箱列表页
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView list_message_in(HttpServletRequest request,
			Map<String, Object> out, PageDto<InquiryDto> page, String isViewed,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());

		// 收件箱
		Inquiry inquiry = new Inquiry();
		inquiry.setIsReceiverDel("0");
		if (StringUtils.isNotEmpty(isViewed)) {
			inquiry.setIsViewed(isViewed);
		}
		inquiry.setReceiverAccount(sessionUser.getAccount());
		inquiry.setBeInquiredType("5");
		page.setPageSize(10);
		page = inquiryService.pageInquiryByUser(inquiry, page);
		for (InquiryDto InquiryDto : page.getRecords()) {
			// 根据信息查查询出收件人
			if (InquiryDto.getInquiry().getSenderAccount().equals("0")) {
				InquiryDto.getInquiry().setSenderAccount("互助社区");
			} else {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryProfilerOfAccount(InquiryDto.getInquiry()
								.getSenderAccount());
				if (StringUtils.isNotEmpty(profiler.getNickname())) {
					InquiryDto.getInquiry().setSenderAccount(
							profiler.getNickname());
				} else {
					continue;
				}
			}
		}
		out.put("page", page);
		SeoUtil.getInstance().buildSeo("list_message_in", out);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/***
	 * 发件箱列表页 *
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView list_message_out(HttpServletRequest request,
			Map<String, Object> out, PageDto<InquiryDto> page,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		// 发件箱
		Inquiry inquiry = new Inquiry();
		inquiry.setIsSenderDel("0");
		inquiry.setSenderAccount(sessionUser.getAccount());
		inquiry.setBeInquiredType("5");
		page.setPageSize(15);
		page = inquiryService.pageInquiryByUser(inquiry, page);
		for (InquiryDto InquiryDto : page.getRecords()) {
			// 根据信息查查询出发件人
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(InquiryDto.getInquiry()
							.getReceiverAccount());
			if (StringUtils.isNotEmpty(profiler.getNickname())) {
				InquiryDto.getInquiry().setReceiverAccount(
						profiler.getNickname());
			} else {
				continue;
			}

		}
		out.put("page", page);
		SeoUtil.getInstance().buildSeo("list_message_out", out);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/**
	 * bbs我的基本信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView edit_info(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		// 为空返回登入页面
		if (sessionUser == null) {
			out.put("url", "myalterinfo.htm");
			return new ModelAndView("/login");
		}
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/**
	 * 修改基本信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView do_edit_info(BbsUserProfilerDO bbsUserProfilerDO,
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
			return new ModelAndView(new RedirectView("/myhuzhu/edit_info.htm"));
		}
		return new ModelAndView("/common/error");
	}

	/**
	 * bbs我的头像
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView edit_img(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		return new ModelAndView();
	}

	/**
	 * bbs我的企业标签
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView edit_sign(String p, HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		BbsSignDO sign = bbsSignService.querySignByCompanyId(sessionUser
				.getCompanyId());
		if (sign != null && StringUtils.isNotEmpty(sign.getSignName())) {
			sign.setSignName(Jsoup.clean(sign.getSignName(), Whitelist.none()));
			out.put("signName", sign.getSignName());
		}

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/**
	 * 添加企业签名
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView do_add_sign(BbsSignDO bbsSignDO,
			HttpServletRequest request, Map<String, Object> model)
			throws UnsupportedEncodingException {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId = sessionUser.getCompanyId();
		String account = sessionUser.getAccount();
		bbsSignDO.setCompanyId(companyId);
		bbsSignDO.setAccount(account);
		Integer i = bbsService.insertBbsSign(bbsSignDO);
		if (i > 0) {
			return new ModelAndView(new RedirectView("/myhuzhu/edit_sign.htm"));
		}
		return new ModelAndView("/common/error");
	}

	/***
	 * 发私信表单页面
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView send_message(HttpServletRequest request,
			Map<String, Object> out, Integer postId, String error,
			Integer companyId, HttpServletResponse response)
			throws UnsupportedEncodingException {
		if (error != null) {
			out.put("error", error);
		}
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		if (postId != null) {
			// 读取文章内容
			BbsPostDO bbsPost = bbsPostService.queryPostById(postId);
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(bbsPost.getAccount());
			out.put("profiler", profiler);
			out.put("bbsPost", bbsPost);
		} else if (companyId != null) {
			CompanyAccount ca = companyAccountService
					.queryAccountByCompanyId(companyId);
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryUserByCompanyId(companyId);
			out.put("profiler", profiler);
			out.put("account", ca);
		}
		SeoUtil.getInstance().buildSeo("send_message", out);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/***
	 * 发私信 insert
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView do_send_message(HttpServletRequest request,
			Map<String, Object> out, String accountName, Inquiry inquiry,
			Integer companyId) throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			return new ModelAndView("/login");
		}
		// 根据名字查询出bbsUserPost;
		BbsUserProfilerDO bbsUserProfiler = bbsUserProfilerService
				.queryUserByCompanyId(companyId);
		if (bbsUserProfiler == null) {
			out.put("error", "1");
			return new ModelAndView("redirect:/myhuzhu/send_message"
					+ companyId + ".htm");
		}
		if (!StringUtils.isContainCNChar(inquiry.getTitle())) {
			inquiry.setTitle(StringUtils.decryptUrlParameter(inquiry.getTitle()));
		}
		if (!StringUtils.isContainCNChar(inquiry.getContent())) {
			inquiry.setContent(StringUtils.decryptUrlParameter(inquiry
					.getContent()));
		}
		inquiry.setReceiverAccount(bbsUserProfiler.getAccount());// 接收者
		inquiry.setBeInquiredType("5");// 针对个人
		inquiry.setSenderAccount(sessionUser.getAccount()); // 发送者
		inquiry.setBeInquiredId(bbsUserProfiler.getCompanyId());// 对象者id为默认id
		inquiryService.inquiryByUser(inquiry, sessionUser.getCompanyId());

		return new ModelAndView("redirect:/myhuzhu/list_message_out.htm");
	}

	@RequestMapping
	public ModelAndView do_read(HttpServletRequest request,
			Map<String, Object> out, Integer id, String url)
			throws UnsupportedEncodingException {
		if (id != null) {
			bbsSystemMessageService.read(id);
		}
		if (StringUtils.isEmpty(url)) {
			url = "http://huzhu.zz91.com";
		}
		return new ModelAndView("redirect:" + url);
	}

	/**
	 * 添加关注关键词
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView do_follow(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		ExtResult result = new ExtResult();
		do {
			SsoUser sessionUser = getCachedUser(request);
			BbsPostNoticeRecommend bbs = new BbsPostNoticeRecommend();
			bbs.setContentId(id);
			BbsPostTags bpt = bbsPostTagsService.queryTagById(id);
			bbs.setContentTitle(bpt.getTagName());
			bbs.setType(BbsPostNoticeRecommendService.TYPE_NOTICE);
			bbs.setAccount(sessionUser.getAccount());
			bbs.setCompanyId(sessionUser.getCompanyId());
			bbs.setCategory(BbsPostNoticeRecommendService.CATEGORY_TAG);
			Integer i = bbsPostNoticeRecommendService
					.insertNoticeOrRecomend(bbs);
			if (i > 0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	/**
	 * 添加关注关键词
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView do_del_follow(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		ExtResult result = new ExtResult();
		do {
			SsoUser sessionUser = getCachedUser(request);
			Integer i = bbsPostNoticeRecommendService.deleteTag(
					sessionUser.getAccount(), id);
			if (i > 0) {
				bbsPostTagsService.down(id);
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	/**
	 * 我关注的领域
	 * 
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView edit_follow(Map<String, Object> out,
			HttpServletRequest request, String keyword,
			HttpServletResponse response) throws UnsupportedEncodingException {
		SsoUser sessionUser = getCachedUser(request);
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());

		// 我关注的标签
		List<BbsPostTags> list = bbsPostNoticeRecommendService
				.queryListForMyTags(sessionUser.getAccount());
		out.put("list", list);

		// 判断是否已关注
		Set<Integer> idSet = new HashSet<Integer>();
		for (BbsPostTags obj : list) {
			idSet.add(obj.getId());
		}
		out.put("idSet", idSet);

		// 搜索的 标签
		if (StringUtils.isNotEmpty(keyword)) {
			BbsPostTags bbsPostTags = new BbsPostTags();
			if (!StringUtils.isContainCNChar(keyword)) {
				keyword = StringUtils.decryptUrlParameter(keyword);
			}
			bbsPostTags.setTagName(keyword);
			PageDto<BbsPostTags> page = new PageDto<BbsPostTags>();
			page.setPageSize(100);
			out.put("page", bbsPostTagsService.pageTagsBySearchEngine(
					bbsPostTags, page));
			out.put("keyword", keyword);
		}
		SeoUtil.getInstance().buildSeo("edit_follow", out);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();
	}

	/***
	 * 私信最终页 *
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView showmessage(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer mark,
			HttpServletResponse response) throws UnsupportedEncodingException {
		out.put("mark", mark);
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "showmessage" + id + ".htm");
			return new ModelAndView("/login");
		}
		out.put("id", id);

		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		bbsService.countBbsInfo(out, sessionUser.getAccount(), null);
		commonHead(out);
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());

		Inquiry inquiry = inquiryService.queryOneInquiry(id);
		if (sessionUser.getAccount().equals(inquiry.getReceiverAccount())) {
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(inquiry.getSenderAccount());
			out.put("profiler", profiler);
		} else {
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(inquiry.getReceiverAccount());
			out.put("profiler", profiler);
		}

		out.put("inquiry", inquiry);
		List<InquiryDto> inquiryList = inquiryService.queryConversation(inquiry
				.getConversationGroup());
		for (InquiryDto inquiryDto : inquiryList) {
			BbsUserProfilerDO profilerDo = bbsUserProfilerService
					.queryProfilerOfAccount(inquiryDto.getInquiry()
							.getSenderAccount());
			if (profilerDo == null) {
				continue;
			}
			inquiryDto.setProfiler(profilerDo);

		}
		out.put("inquiryList", inquiryList);
		if (StringUtils.isEmpty(inquiry.getIsViewed())
				|| "0".equals(inquiry.getIsViewed())) {
			inquiryService.makeAsViewed(id, true);
		}
		out.put("message", "message");

		// 收件箱
		if (sessionUser.getAccount().equals(inquiry.getReceiverAccount())) {
			// 收件箱上一篇
			Inquiry onInquiry = inquiryService.queryOnMessageById(id,
					sessionUser.getAccount(), null, "5");
			out.put("onInquiry", onInquiry);
			// 收件箱下一篇
			Inquiry downInquiry = inquiryService.queryDownMessageById(id,
					sessionUser.getAccount(), null, "5");
			out.put("downInquiry", downInquiry);
		} else {
			// 发件箱上一篇
			Inquiry onInquiry = inquiryService.queryOnMessageById(id, null,
					sessionUser.getAccount(), "5");
			out.put("onInquiry", onInquiry);
			// 发件箱下一篇
			Inquiry downInquiry = inquiryService.queryDownMessageById(id, null,
					sessionUser.getAccount(), "5");
			out.put("downInquiry", downInquiry);
			out.put("showSent", "showSent");
		}
		SeoUtil.getInstance().buildSeo("showmessage",
				new String[] { inquiry.getTitle() }, null, null, out);

		// 清除 cdn
		clearCDN(response);

		return new ModelAndView();

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
		// 谁回复我的帖子
		List<BbsPostDTO> bbsReplyList = bbsService.queryUserNicknameByReply(
				loginedAccount, BbsConst.DEFAULT_START_INDEX,
				BbsConst.DEFAULT_SIZE, BbsConst.DEFAULT_POST_MODIFIED_SORT,
				BbsConst.DEFAULT_POST_DIR);
		out.put("countReply",
				bbsPostReplyService.countMyreply(loginedAccount, null, null));
		out.put("countPosted",
				bbsPostService.countMyposted(loginedAccount, null, null, null));
		out.put("bbsReplyList", bbsReplyList);
		out.put("bbsReplyCount", bbsReplyList.size());
	}

	// url配置
	public Map<Integer, String> getPath() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(4, "shequ/dongtai/");
		map.put(6, "shequ/baoguang/");
		map.put(7, "shequ/zixun/");
		map.put(10, "shequ/zhanwu/");
		map.put(107, "shangquan1/");
		return map;
	}

	public Map<Integer, String> getCategoryName() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(4, "废料动态");
		map.put(6, "骗子曝光台");
		map.put(7, "江湖风云");
		map.put(10, "站务管理");
		map.put(9, "商务交流");
		map.put(20, "社区公告");
		map.put(107, "商圈");
		return map;
	}
}
