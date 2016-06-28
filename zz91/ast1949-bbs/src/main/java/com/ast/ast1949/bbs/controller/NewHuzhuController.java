package com.ast.ast1949.bbs.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.domain.bbs.BbsScore;
import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.service.bbs.BbsPostCategoryService;
import com.ast.ast1949.service.bbs.BbsPostNoticeRecommendService;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsPostTagsService;
import com.ast.ast1949.service.bbs.BbsPostTrendsService;
import com.ast.ast1949.service.bbs.BbsScoreService;
import com.ast.ast1949.service.bbs.BbsSignService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.service.bbs.BbsViewLogService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.CNToHexUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class NewHuzhuController extends BaseController {
	@Resource
	private BbsPostCategoryService bbsPostCategoryService;
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private BbsPostReplyService bbsPostReplyService;
	@Resource
	private BbsPostTagsService bbsPostTagsService;
	@Resource
	private ProductsService productsService;
	@Resource
	private BbsPostNoticeRecommendService bbsPostNoticeRecommendService;
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private BbsSignService bbsSignService;
	@Resource
	private BbsPostTrendsService bbsPostTrendsService;
	@Resource
	private MyfavoriteService myfavoriteService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private BbsScoreService bbsScoreService;
	@Resource
	private BbsViewLogService bbsViewLogService;

	// 问答首页
	@RequestMapping
	public ModelAndView queIndex(Map<String, Object> out,
			HttpServletRequest request) {
		// 有无关注标签
		Integer flag = 0;
		SsoUser sessionUser = getCachedUser(request);
		List<BbsPostDO> list = new ArrayList<BbsPostDO>();
		if (sessionUser != null) {
			list = bbsPostNoticeRecommendService.queryNoticeByAccount(
					sessionUser.getAccount(),
					BbsPostNoticeRecommendService.CATEGORY_QA, 6);
		}
		if (list.size() == 0) {
			list = bbsPostService.queryPostByNoticeAndView(
					BbsPostService.BBS_CATEGORY_QA, 6);
			flag++;
		}
		out.put("flag", flag);
		out.put("list", list);
		SeoUtil.getInstance().buildSeo("queIndex", out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView publish(Map<String, Object> out,
			HttpServletRequest request, Integer flag,
			HttpServletResponse response) {
		if (flag == null) {
			// 提问题
			flag = 1;
		}
		if (flag == 2) {
			// 类别列表
			List<BbsPostCategory> list = bbsPostCategoryService
					.queryCategorysByParentId(2);
			out.put("category", list);
		}
		out.put("flag", flag);
		String title = "我要提问";
		String description = "zz91再生网旗下互助社区废料问答频道,伴您一路前行,废料生意人可以根据自身需求,有针对性地提出问题,同时可以回答他人提出的问题,互帮互助的同时获得货源与知识。";
		if (flag == 2) {
			title = "我要发贴";
			description = "zz91再生网旗下互助社区,作为废料生意人的交流平台,在这里您可以咨询废料行业问题、获取废料行业知识、曝光行业骗子，赶紧加入，开启废料生意之旅吧！";
		}

		SeoUtil.getInstance().buildSeo("publish", new String[] { title }, null,
				new String[] { description }, out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView viewQue(Integer flag, PageDto<BbsPostDO> page,
			Map<String, Object> out, HttpServletRequest request)
			throws UnsupportedEncodingException {
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		SsoUser sessionUser = getCachedUser(request);
		if (flag == 1) {
			out.put("category", "发现问答");
			out.put("wendaPath", "wenti");
		} else if (flag == 2) {
			out.put("category", "等待回答");
			out.put("wendaPath", "question");
		} else if (flag == 3) {
			out.put("category", "最新问题");
			out.put("wendaPath", "zuixinwenti");
		} else if (flag == 4) {
			out.put("category", "热门关注");
			out.put("wendaPath", "remenguanzhu");
		} else if (flag == 6) {
			if (sessionUser != null) {
				page.setSort("post_time");
				page.setDir("desc");
				page = bbsPostService.ListBbsPostByCompanyId(
						sessionUser.getCompanyId(), 1, page);
				out.put("category", "我的问答");
				out.put("wendaPath", "myquestion");
			}
		} else if (flag == 7) {
			out.put("category", "热门回答");
			out.put("wendaPath", "remenanswers");
		} else if (flag == 5) {
			flag = 2;
			out.put("category", "等待回答");
			out.put("wendaPath", "huida");
		}
		if (flag != 6) {
			page = bbsPostService.pagePostForViewQue(null, page, flag);
		}

		// 标签处理
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (BbsPostDO post : page.getRecords()) {
			List<BbsPostTags> list = new ArrayList<BbsPostTags>();
			BbsPostTags tag = new BbsPostTags();
			if (StringUtils.isNotEmpty(post.getTags())) {
				String[] str = post.getTags().replaceAll(",", "，").split("，");
				for (String s : str) {
					if (StringUtils.isNotEmpty(s)) {
						tag = bbsPostTagsService.queryTagByName(s,
								post.getBbsPostCategoryId(), 0);
						if (tag != null) {
							list.add(tag);
						}
					}
				}
			}
			map.put(post.getId(), list);
		}
		// 我的关注领域
		if (sessionUser != null) {
			List<BbsPostNoticeRecommend> listTag = bbsPostNoticeRecommendService
					.queryNoticeByUser(sessionUser.getAccount(),
							BbsPostNoticeRecommendService.CATEGORY_TAG, 9);
			out.put("listTag", listTag);
		}
		out.put("map", map);
		out.put("flag", flag);
		out.put("page", page);
		int currentpage = page.getStartIndex() / page.getPageSize() + 1;
		String[] title = { String.valueOf(out.get("category")),
				String.valueOf(currentpage) };
		SeoUtil.getInstance().buildSeo("viewQue", title, null, null, out);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView viewReply(HttpServletRequest request,
			Map<String, Object> out, Integer postId,
			PageDto<BbsPostReplyDto> page) throws UnsupportedEncodingException,
			NoSuchAlgorithmException {
		do {
			if (postId == null) {
				break;
			}
			// 获取主帖或问题的详情
			BbsPostDO bbs = bbsPostService.queryPostById(postId);
			if (!"1".equals(bbs.getCheckStatus())
					|| !"0".equals(bbs.getIsDel())) {
				out.put("errorsign", 1);
				return new ModelAndView("/common/error");
			}
			//手机app发帖没有标题，则将内容作为标题
			if(StringUtils.isEmpty(bbs.getTitle())){
				bbs.setTitle(Jsoup.clean(bbs.getContent(), Whitelist.none()));
			}
			// 浏览数
			Integer i = 0;
			if (bbs.getVisitedCount() != null) {
				i = bbsPostService.updateVisitedCountById(
						bbs.getVisitedCount() + 1, bbs.getId());
				if (i > 0) {
					bbs.setVisitedCount(bbs.getVisitedCount() + 1);
				}
			} else {
				i = bbsPostService.updateVisitedCountById(1, bbs.getId());
				if (i > 0) {
					bbs.setVisitedCount(1);
				}
			}
			// 获取发贴人昵称
			if (!"leicf".equals(bbs.getAccount())) {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryProfilerOfAccount(bbs.getAccount());
				if (profiler != null
						&& StringUtils.isNotEmpty(profiler.getNickname())) {
					bbs.setNickname(profiler.getNickname());
				} else {
					CompanyAccount ca = companyAccountService
							.queryAccountByCompanyId(bbs.getCompanyId());
					if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
						bbs.setNickname(ca.getContact());
					}
				}
			} else {
				bbs.setNickname("互助管理员");
			}
			if (StringUtils.isNumber(bbs.getNickname())
					&& bbs.getNickname().length() > 6) {
				bbs.setNickname(bbs.getNickname().substring(0, 6));
			}

			out.put("bbs", bbs);
			List<BbsPostTags> listTag = new ArrayList<BbsPostTags>();
			BbsPostTags tag = new BbsPostTags();
			if (bbs != null && StringUtils.isNotEmpty(bbs.getTags())) {
				String[] str = bbs.getTags().replace(",", "，").split("，");
				for (String s : str) {
					if (StringUtils.isNotEmpty(s)) {
						tag = bbsPostTagsService.queryTagByName(s,
								bbs.getBbsPostCategoryId(), 0);
						if (tag != null) {
							listTag.add(tag);
						}
					}
				}
			}
			out.put("listTag", listTag);
			if (bbs.getBbsPostCategoryId() == null) {
				bbs.setBbsPostCategoryId(1);
			}
			out.put("categoryId", bbs.getBbsPostCategoryId());
			// 登入者信息
			SsoUser sessionUser = getCachedUser(request);
			out.put("sessionUser", sessionUser);
			String iconTemplate = "<img src=\"http://img0.zz91.com/bbs/images/bbs/{0}.jpg\" />";
			// 帖子或问题的回复集
			if (sessionUser == null) {
				sessionUser = new SsoUser();
			}
			page.setPageSize(4);
			page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
			page = bbsPostReplyService.pageReplyOfPost(postId, null,
					iconTemplate, page);

			out.put("page", page);
			// 相关帖子或问答
			PageDto<BbsPostDO> postPage = new PageDto<BbsPostDO>();
			PageDto<BbsPostDO> bbsPage = new PageDto<BbsPostDO>();
			postPage.setPageSize(7);
			bbsPage.setPageSize(7);
			if (bbs.getBbsPostCategoryId() == 2) {
				postPage = bbsPostService.pagePostByNewSearchEngine(
						bbs.getTags(), postPage, 2, 1);
			} else if (bbs.getBbsPostCategoryId() == 1) {
				postPage = bbsPostService.pagePostByNewSearchEngine(
						bbs.getTags(), postPage, 1, 1);
			} else {
				postPage = bbsPostService.pagePostByNewSearchEngine(
						bbs.getTags(), postPage, 1, 1);
				bbsPage = bbsPostService.pagePostByNewSearchEngine(
						bbs.getTags(), bbsPage, 2, 1);
				for (BbsPostDO post : bbsPage.getRecords()) {
					if (String.valueOf(bbs.getId()).equals(
							String.valueOf(post.getId()))) {
						postPage.getRecords().remove(post);
						break;
					}
				}
				if (bbsPage.getRecords().size() > 6) {
					bbsPage.getRecords().remove(6);
				}
				out.put("bbsPage", bbsPage.getRecords());
			}
			for (BbsPostDO post : postPage.getRecords()) {
				if (bbs==null||post==null) {
					continue;
				}
				if (String.valueOf(bbs.getId()).equals(
						String.valueOf(post.getId()))) {
					postPage.getRecords().remove(post);
					break;
				}
			}
			if (postPage.getRecords().size() > 6) {
				postPage.getRecords().remove(6);
			}
			out.put("postPage", postPage.getRecords());
			String titleLi = "";
			if (bbs.getBbsPostCategoryId() == 1) {
				titleLi = "问答";
			} else if (bbs.getBbsPostCategoryId() == 2) {
				titleLi = "社区";
			} else if (bbs.getBbsPostCategoryId() == 3) {
				titleLi = "学院";
			} else if (bbs.getBbsPostCategoryId() == 4) {
				titleLi = "商圈";
			}
			String[] title = { bbs.getTitle(), titleLi };
			String[] keywords = new String[2];
			String[] description = new String[2];
			if (bbs.getBbsPostCategoryId() == 1) {
				keywords[0] = bbs.getTitle();
			} else {
				keywords[0] = "无";
			}
			if (StringUtils.isNotEmpty(bbs.getContent())&&Jsoup.clean(bbs.getContent(), Whitelist.none()).length() > 80) {
				description[0] = Jsoup
						.clean(bbs.getContent(), Whitelist.none()).substring(0,
								80);
			}
			if(StringUtils.isNotEmpty(bbs.getContent())){
			description[0] = Jsoup.clean(bbs.getContent(), Whitelist.none());
			}
			SeoUtil.getInstance().buildSeo("viewReply", title, keywords,
					description, out);
			// 当前位置
			if (bbs.getBbsPostAssistId() != null
					&& bbs.getBbsPostAssistId() != 0) {
				getList(out, bbs.getBbsPostAssistId());
			} else {
				getList(out, bbs.getBbsPostCategoryId());
			}
		} while (false);
		return new ModelAndView();
	}

	/**
	 * 废料问答标签页
	 * 
	 * @param request
	 * @param out
	 * @param tag
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView viewTags(HttpServletRequest request,
			Map<String, Object> out, Integer tagId, Integer p,
			PageDto<BbsPostDO> page) throws UnsupportedEncodingException {
		if (p != null) {
			page.setStartIndex((p - 1) * 7);
		}
		// 标签信息
		BbsPostTags bbsPostTags = bbsPostTagsService.queryTagById(tagId);
		out.put("bbsPostTags", bbsPostTags);
		// 含有该标签的问题
		page.setPageSize(7);
		page = bbsPostService.pagePostByNewSearchEngine(
				bbsPostTags.getTagName(), page, bbsPostTags.getCategory(), 2);
		out.put("postPage", page);
		// 标签处理
		if (page.getRecords().size() > 0) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			for (BbsPostDO post : page.getRecords()) {
				BbsPostTags tag = new BbsPostTags();
				List<BbsPostTags> list = new ArrayList<BbsPostTags>();
				if (StringUtils.isNotEmpty(post.getTags())) {
					String[] str = post.getTags().split("，");
					for (String s : str) {
						if (StringUtils.isNotEmpty(s)) {
							tag = bbsPostTagsService.queryTagByName(s,
									post.getBbsPostCategoryId(), 0);
							if (tag != null) {
								list.add(tag);
							}
						}
					}
				}
				map.put(post.getId(), list);
			}
			out.put("map", map);
		}
		out.put("tagId", tagId);
		String[] title = new String[2];
		String[] keywords = new String[2];
		String[] desc = new String[2];
		if (bbsPostTags.getCategory() == 1) {
			title[0] = bbsPostTags.getTagName() + "_废料问答";
			desc[0] = "废料问答";
		} else if (bbsPostTags.getCategory() == 2) {
			title[0] = bbsPostTags.getTagName() + "_互助社区";
			desc[0] = "互助社区";
		} else if (bbsPostTags.getCategory() == 3) {
			title[0] = bbsPostTags.getTagName() + "_废料学院";
			desc[0] = "废料学院";
		}else if(bbsPostTags.getCategory()==106){
			title[0] = bbsPostTags.getTagName() + "_商圈";
			desc[0] = "商圈";
		}
		keywords[0] = bbsPostTags.getTagName();
		SeoUtil.getInstance().buildSeo("viewTags", title, keywords, desc, out);
		return null;
	}

	/**
	 * 社区标签页
	 * 
	 * @param request
	 * @param out
	 * @param tag
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView bbsTags(HttpServletRequest request,
			Map<String, Object> out, String tag, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		String tags = CNToHexUtil.getInstance().decode(tag);
		// 标签信息
		BbsPostTags bbsPostTags = bbsPostTagsService.queryTagByName(tags, 2, 0);
		out.put("bbsPostTags", bbsPostTags);
		// 含有该标签的帖子
		PageDto<BbsPostDO> postPage = new PageDto<BbsPostDO>();
		postPage.setPageSize(7);
		postPage = bbsPostService.pagePostByNewSearchEngine(tags, postPage, 2,
				2);
		out.put("postPage", postPage);
		out.put("keyTag", tag);
		return null;
	}

	/**
	 * 商圈标签页
	 * 
	 * @param request
	 * @param out
	 * @param tag
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView shangquanTags(HttpServletRequest request,
			Map<String, Object> out, String tag, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		String tags = CNToHexUtil.getInstance().decode(tag);
		// 标签信息
		BbsPostTags bbsPostTags = bbsPostTagsService.queryTagByName(tags, 2, 0);
		out.put("bbsPostTags", bbsPostTags);
		// 含有该标签的帖子
		PageDto<BbsPostDO> postPage = new PageDto<BbsPostDO>();
		postPage.setPageSize(7);
		postPage = bbsPostService.pagePostByNewSearchEngine(tags, postPage, 2,
				2);
		out.put("postPage", postPage);
		out.put("keyTag", tag);
		return null;
	}
	
	// 社区首页
	@RequestMapping
	public ModelAndView bbsIndex(Map<String, Object> out, Integer assistId,
			HttpServletRequest request) {
		PageDto<BbsPostDO> page = new PageDto<BbsPostDO>();
		page.setPageSize(5);
		page = bbsPostService.pagePostForBbsCategory("台州塑交会", page, 9, 1);
		for (BbsPostDO post : page.getRecords()) {
			post.setTitle(post.getTitle().replace("[展会]", ""));
		}
		out.put("list", page.getRecords());
		// 政策法规
		PageDto<BbsPostDO> page1 = new PageDto<BbsPostDO>();
		page1.setPageSize(5);
		page1.setStartIndex(0);
		page1 = bbsPostService
				.pagePostByNewSearchEngine("政策法规", page1, null, 2);
		List<BbsPostDO> list = page1.getRecords();
		out.put("list1", list);
		SeoUtil.getInstance().buildSeo("bbsIndex", out);
		return new ModelAndView();
	}

	// 商圈首页
	@RequestMapping
	public ModelAndView list_circle(Map<String, Object> out, String flagpp,
			HttpServletRequest request, PageDto<BbsPostDO> page) {
		page.setPageSize(10);
		if ("1".equals(flagpp)) {
			PageDto<BbsPostDO> page1 = new PageDto<BbsPostDO>();
			page1.setPageSize(10);
			page1.setSort("gmt_created");
			page1 = bbsPostService.pagePostNew(106, page1);
			PageDto<BbsPostDO> page2 = new PageDto<BbsPostDO>();
			page2.setPageSize(10);
			page2.setSort("notice_count");
			page2 = bbsPostService.pagePostNew(106, page2);
			PageDto<BbsPostDO> page3 = new PageDto<BbsPostDO>();
			page3.setPageSize(10);
			page3.setSort("reply_count");
			page3 = bbsPostService.pagePostNew(106, page3);
			out.put("list1", page1.getRecords());
			out.put("list2", page2.getRecords());
			out.put("list3", page3.getRecords());
			out.put("category", "商圈首页");
			out.put("currentPage", "1");
		} else if ("2".equals(flagpp)) {
			page.setSort("gmt_created");
			page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
			page = bbsPostService.pagePostNew(106, page);
			out.put("page", page);
			out.put("category", "最新商机");
			out.put("currentPage", page.getCurrentPage());
		} else if ("3".equals(flagpp)) {
			page.setSort("notice_count");
			page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
			page = bbsPostService.pagePostNew(106, page);
			out.put("page", page);
			out.put("category", "热门关注");
			out.put("currentPage", page.getCurrentPage());
		} else if ("4".equals(flagpp)) {
			page.setSort("reply_count");
			page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
			page = bbsPostService.pagePostNew(106, page);
			out.put("page", page);
			out.put("category", "热门回复");
			out.put("currentPage", page.getCurrentPage());
		}
		// 标签处理
		if (!"1".equals(flagpp)) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			for (BbsPostDO post : page.getRecords()) {
				List<BbsPostTags> list = new ArrayList<BbsPostTags>();
				BbsPostTags tag = new BbsPostTags();
				if (StringUtils.isNotEmpty(post.getTags())) {
					String[] str = post.getTags().replaceAll(",", "，")
							.split("，");
					for (String s : str) {
						if (StringUtils.isNotEmpty(s)) {
							tag = bbsPostTagsService.queryTagByName(s,
									post.getBbsPostCategoryId(), 0);
							if (tag != null) {
								list.add(tag);
							}
						}
					}
				}
				map.put(post.getId(), list);
			}
			out.put("map", map);
		}
		out.put("flagpp", flagpp);
		String[] title = { String.valueOf(out.get("category")),
				String.valueOf(out.get("currentPage")) };

		if ("1".equals(flagpp)) {
			SeoUtil.getInstance().buildSeo("list_circle2", out);
		} else {
			SeoUtil.getInstance().buildSeo("list_circle", title, null, null,
					out);
		}
		return new ModelAndView();
	}

	/**
	 * 社区列表页
	 * 
	 * @param out
	 * @param assistId
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView bbsCategory(Map<String, Object> out, Integer assistId,
			Integer flag, PageDto<BbsPostDO> page, HttpServletRequest request) {
		page.setPageSize(7);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		BbsPostCategory bbsPostCategory = bbsPostCategoryService
				.querySimpleCategoryById(assistId);
		if (bbsPostCategory == null) {
			bbsPostCategory = new BbsPostCategory();
			bbsPostCategory.setName("头条");
		}
		out.put("category", bbsPostCategory);
		// 列表页
		page = bbsPostService
				.pagePostForBbsCategory(null, page, assistId, flag);
		out.put("page", page);
		out.put("flag", flag);
		out.put("assistId", assistId);
		// seo三要素
		String[] title = new String[3];
		String[] keywords = new String[3];
		String[] desc = new String[3];
		if (flag == 1) {
			if (assistId == 4) {
				title[0] = "废料动态";
				keywords[0] = "废料动态";
				desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,每天为您精选废料行业的最新市场行情与动态，让您能在第一时间内了解到废料行业的最新信息。";
			} else if (assistId == 7) {
				title[0] = "江湖风云";
				keywords[0] = "江湖风云";
				desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,每天为您精选互联网上最热的话题或新闻，让您在互助社区也能够了解到最新的社会热点与资讯。";
			} else if (assistId == 0) {
				title[0] = "互助头条";
				keywords[0] = "互助头条";
				desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,每天会推荐废料生意人最关注的话题或资讯到头条，让您能够在第一时间了解到最热话题或资讯。";
			} else if (assistId == 6) {
				title[0] = "骗子曝光台";
				keywords[0] = "骗子曝光台";
				desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台，废料商友可以在这里曝光您所遇到的骗子或者受骗的过程，让骗子无所遁形，放心做生意。";
			} else if (assistId == 9) {
				title[0] = "商务交流";
				keywords[0] = "商务交流";
				desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,在这里您可以咨询废料行业问题、获取废料行业知识、曝光行业骗子，赶紧加入，开启废料生意之旅吧！";
			} else if (assistId == 10) {
				title[0] = "站务管理";
				keywords[0] = "站务管理";
				desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,在这里您可以咨询废料行业问题、获取废料行业知识、曝光行业骗子，赶紧加入，开启废料生意之旅吧！";
			}
		} else if (flag == 2) {
			title[0] = "热门帖子_互助社区";
			keywords[0] = "热门帖子";
			desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,在这里您可以咨询废料行业问题、获取废料行业知识、曝光行业骗子，赶紧加入，开启废料生意之旅吧！";
		} else if (flag == 3) {
			title[0] = "精华帖子_互助社区";
			keywords[0] = "精华帖子";
			desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,在这里您可以咨询废料行业问题、获取废料行业知识、曝光行业骗子，赶紧加入，开启废料生意之旅吧！";
		}
		SeoUtil.getInstance().buildSeo("bbsCategory", title, keywords, desc,
				out);
		// 对应类别的相应链接,以及当前位置
		String shequP = getPath().get(assistId);
		String shequPath = "";
		if (flag == 2) {
			shequPath = "remen" + shequP;
		} else if (flag == 3) {
			shequPath = "jinghua" + shequP;
		} else {
			shequPath = shequP;
		}
		out.put("shequP", shequP);
		out.put("shequPath", shequPath);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView colIndex(Map<String, Object> out,
			HttpServletRequest request) {
		// 类别列表
		List<BbsPostCategory> list = bbsPostCategoryService
				.queryCategorysByParentId(11);
		// map是某类别下的所有子类
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		List<BbsPostCategory> listC = new ArrayList<BbsPostCategory>();
		for (BbsPostCategory ca : list) {
			listC = bbsPostCategoryService.queryCategorysByParentId(ca.getId());
			if (listC.size() > 0) {
				map.put(ca.getId(), listC);
			}
		}
		out.put("list", list);
		out.put("map", map);
		// 大家都在关注
		List<BbsPostTags> listTags = bbsPostTagsService.queryTagsByMark(8);
		out.put("tags", listTags);
		SeoUtil.getInstance().buildSeo("colIndex", out);
		// url配置
		Map<Integer, String> mapPath = getPath();
		out.put("mapPath", mapPath);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView waitAnswer(Map<String, Object> out,
			HttpServletRequest request, PageDto<PostDto> page) {
		page.setPageSize(10);
		page.setSort("reply_time");
		page.setDir("desc");
		page = bbsPostService.ListWaitAnswer(page);
		out.put("page", page);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView list_qa(Map<String, Object> out,
			HttpServletRequest request, PageDto<PostDto> page, Integer type) {
		page.setPageSize(10);
		page.setSort("reply_time");
		page.setDir("desc");
		if (type == null) {
			type = 1;
		}
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			page = bbsPostService.ListBbsPostByUser(1,
					sessionUser.getAccount(), page, type, null);
		}
		out.put("page", page);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView myposted(Map<String, Object> out,
			HttpServletRequest request, PageDto<PostDto> page, Integer type) {
		page.setPageSize(10);
		page.setSort("reply_time");
		page.setDir("desc");
		if (type == null) {
			type = 1;
		}
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			page = bbsPostService.ListBbsPostByUser(1,
					sessionUser.getAccount(), page, type, null);
		}
		out.put("page", page);
		return new ModelAndView();
	}

	public Map<Integer, String> getMapCategory() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<BbsPostCategory> list = bbsPostCategoryService.queryAllCategory();
		for (BbsPostCategory ca : list) {
			map.put(ca.getId(), ca.getName());
		}
		return map;
	}

	@RequestMapping
	public ModelAndView inserNoticeOrRecom(Map<String, Object> out,
			HttpServletRequest request, BbsPostNoticeRecommend bbs)
			throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer i = 0;
		do {
			if (sessionUser == null) {
				i = -3;
				break;
			}
			if (String.valueOf(bbs.getContentId()).equals(
					String.valueOf(sessionUser.getCompanyId()))) {
				i = -2;
				break;
			}
			bbs.setCompanyId(sessionUser.getCompanyId());
			bbs.setAccount(sessionUser.getAccount());
			bbs.setState(0);
			if (bbs.getCategory() == 3) {
				// 标签
				BbsPostTags tag = bbsPostTagsService.queryTagById(bbs
						.getContentId());
				if (tag != null && StringUtils.isNotEmpty(tag.getTagName())) {
					bbs.setContentTitle(tag.getTagName());
				}
			} else if (bbs.getCategory() == 1 || bbs.getCategory() == 0
					|| bbs.getCategory() == 4) {
				// 帖子或问答
				BbsPostDO bp = bbsPostService.queryPostById(bbs.getContentId());
				if (bp != null && StringUtils.isNotEmpty(bp.getTitle())) {
					bbs.setContentTitle(bp.getTitle());
				}
			} else if (bbs.getCategory() == 2) {
				// 个人主页
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryUserByCompanyId(bbs.getContentId());
				if (profiler != null
						&& StringUtils.isNotEmpty(profiler.getNickname())) {
					bbs.setContentTitle(profiler.getNickname());
				} else {
					CompanyAccount ca = companyAccountService
							.queryAccountByCompanyId(bbs.getContentId());
					if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
						bbs.setContentTitle(ca.getContact());
					}
				}

			}
			i = bbsPostNoticeRecommendService.insertNoticeOrRecomend(bbs);
		} while (false);
		map.put("i", i);
		map.put("nr", bbs.getType());
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView personIndex(Map<String, Object> out,
			HttpServletRequest request, Integer companyId) {
		out.put("companyId", companyId);
		initCountDate(out, companyId);
		// 签名
		BbsSignDO sign = bbsSignService.querySignByCompanyId(companyId);
		if (sign != null) {
			out.put("sign", sign.getSignName());
		}
		// 地区
		Company company = companyService.queryCompanyById(companyId);
		CategoryDO categoryDo = new CategoryDO();
		if (company != null && StringUtils.isNotEmpty(company.getAreaCode())) {
			if (company.getAreaCode().length() < 9) {
				categoryDo = categoryService.queryCategoryByCode(company
						.getAreaCode());
				out.put("addr", categoryDo.getLabel());
			} else {
				String address = "";
				// 省
				if (company.getAreaCode().length() >= 12) {
					categoryDo = categoryService.queryCategoryByCode(company
							.getAreaCode().substring(0, 12));
					if (categoryDo != null) {
						address = categoryDo.getLabel();
					}
				}
				// 市
				if (company.getAreaCode().length() >= 16) {
					categoryDo = categoryService.queryCategoryByCode(company
							.getAreaCode().substring(0, 16));
					if (categoryDo != null) {
						address = address + " " + categoryDo.getLabel();
					}
				}
				out.put("addr", address);
			}
		}
		// 我的回答列表
		List<BbsPostReplyDO> listReply = bbsPostReplyService
				.queryReplyByCompanyId(1, companyId, 5);
		out.put("listReply", listReply);
		SeoUtil.getInstance().buildSeo("personIndex", out);
		return null;
	}

	/**
	 * 回帖、提问、回答等数量统计
	 * 
	 * @param out
	 * @param companyId
	 */
	public void initCountDate(Map<String, Object> out, Integer companyId) {
		// 提问数
		out.put("que", bbsPostService.countBbsPostByCompanyId(companyId, 1));
		// 帖子数
		out.put("bbs", bbsPostService.countBbsPostByCompanyId(companyId, 2));
		// 关注的个人中心
		CompanyAccount ca = companyAccountService
				.queryAccountByCompanyId(companyId);
		Integer answer = 0;
		if (ca != null) {
			List<BbsPostNoticeRecommend> list = bbsPostNoticeRecommendService
					.queryNoticeByUser(ca.getAccount(), 2, 8);
			out.put("list", list);
			answer = bbsPostReplyService.countReplyByCompanyId(companyId, 1);
		}
		// 回答数
		out.put("answer", answer);
		// 关注个人中心总数
		BbsPostNoticeRecommend notice = new BbsPostNoticeRecommend();
		notice.setCompanyId(companyId);
		notice.setCategory(2);
		notice.setState(0);
		notice.setType(1);
		Integer allNum = bbsPostNoticeRecommendService
				.countNumbyCompanyId(notice);
		out.put("allNum", allNum);
		// 昵称
		String name = "";
		BbsUserProfilerDO profiler = bbsUserProfilerService
				.queryUserByCompanyId(companyId);
		if (profiler == null || StringUtils.isEmpty(profiler.getNickname())) {
			if (ca != null) {
				name = ca.getContact();
			}
		} else {
			name = profiler.getNickname();
		}
		if (StringUtils.isEmpty(name)) {
			name = "匿名";
		}
		out.put("nickname", name);
		out.put("profiler", profiler);
		// 积分等级计算
		BbsScore bbsScore = new BbsScore();
		bbsScore.setCompanyId(companyId);
		Integer level = bbsScoreService.sumScore(bbsScore) / 100;
		out.put("level", level);

	}

	@RequestMapping
	public ModelAndView personList(Map<String, Object> out,
			HttpServletRequest request, Integer companyId, Integer flag,
			PageDto<BbsPostDO> page) {
		out.put("companyId", companyId);
		out.put("flag", flag);
		initCountDate(out, companyId);
		page.setPageSize(7);
		page.setDir("desc");
		if (flag < 3) {
			page.setSort("reply_time");
			page = bbsPostService.ListBbsPostByCompanyId(companyId, flag, page);
		} else if (flag == 3) {
			page.setSort("gmt_created");
			page = bbsPostTrendsService.ListBbsPostByCompanyId(companyId, page);
		} else if (flag == 4) {
			page.setSort("reply_time");
			CompanyAccount ca = companyAccountService
					.queryAccountByCompanyId(companyId);
			if (ca != null) {
				page = bbsPostReplyService.queryReplyByUser(ca.getAccount(),
						bbsPostService.BBS_CATEGORY_QA, page);
			}
		}
		out.put("page", page);
		String[] title = new String[2];
		if (flag == 1) {
			title[0] = "提问";
		} else if (flag == 2) {
			title[0] = "帖子";
		} else if (flag == 3) {
			title[0] = "动态";
		} else if (flag == 4) {
			title[0] = "回答";
		}
		Map<Integer, String> map = getPath();
		out.put("mapPath", map);
		SeoUtil.getInstance().buildSeo("personList", title, null, null, out);
		return null;
	}

	@RequestMapping
	public ModelAndView list_col(Map<String, Object> out,
			HttpServletRequest request, Integer categoryId,
			PageDto<BbsPostDO> page) {
		BbsPostCategory category = bbsPostCategoryService
				.querySimpleCategoryById(categoryId);
		page.setPageSize(7);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		page.setSort("reply_time");
		page.setDir("desc");
		page = bbsPostService.pagePostByNewSearchEngine(category.getName(),
				page, 3, 3);
		out.put("page", page);
		out.put("categoryId", categoryId);
		// 相关问答
		PageDto<BbsPostDO> pageQue = new PageDto<BbsPostDO>();
		pageQue.setPageSize(5);
		pageQue = bbsPostService.pagePostByNewSearchEngine(category.getName(),
				pageQue, 1, 1);
		out.put("pageQue", pageQue.getRecords());
		PageDto<BbsPostDO> pagePost = new PageDto<BbsPostDO>();
		pagePost.setPageSize(5);
		pagePost = bbsPostService.pagePostByNewSearchEngine(category.getName(),
				pageQue, 2, 1);
		out.put("pagePost", pagePost.getRecords());
		out.put("categoryId", categoryId);
		String[] title = new String[2];
		String[] keywords = new String[2];
		String[] desc = new String[2];
		if (category.getParentId() > -1 && category.getParentId() < 4) {
			title[0] = category.getName() + "_废料学院";
			keywords[0] = category.getName();
			desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,在废料学院您可以获得相关行业的专业知识，同时可以获取相关行业的生意经验，赶紧加入，开启废料生意之旅吧！";
		} else {
			BbsPostCategory cs = bbsPostCategoryService
					.querySimpleCategoryById(category.getParentId());
			if (cs != null) {
				title[0] = category.getName() + "_" + cs.getName() + "_废料学院";
				keywords[0] = category.getName() + cs.getName();
				desc[0] = "zz91再生网旗下互助社区,作为废料生意人的交流平台,在废料学院您可以获得"
						+ category.getName()
						+ "行业的专业知识，同时可以获取相关行业的生意经验，赶紧加入，开启废料生意之旅吧！";
			}
		}
		SeoUtil.getInstance().buildSeo("list_col", title, keywords, desc, out);
		// 类别对应相应的url以及位置
		String xueyuanPath = getPath().get(categoryId);
		out.put("xueyuanPath", xueyuanPath);
		getList(out, categoryId);
		return null;
	}

	@RequestMapping
	public ModelAndView addMyCollect(Map<String, Object> out,
			HttpServletRequest request, MyfavoriteDO favorite)
			throws IOException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		SsoUser sessionUser = getCachedUser(request);
		Integer i = -1;
		do {
			if (sessionUser != null) {
				// 获取该问题或帖子的title
				BbsPostDO post = bbsPostService.queryPostById(favorite
						.getContentId());
				if (StringUtils.isNotEmpty(post.getTitle())) {
					favorite.setContentTitle(post.getTitle());
				} else {
					break;
				}
				favorite.setCompanyId(sessionUser.getCompanyId());
				favorite.setAccount(sessionUser.getAccount());
			}
			i = myfavoriteService.insertMyCollectForNewhuzhuz(favorite);
		} while (false);
		map.put("i", i);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView search(Map<String, Object> out,
			HttpServletRequest request, PageDto<BbsPostDO> page,
			String keywords, String id) throws UnsupportedEncodingException {
		page.setPageSize(7);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		if (!StringUtils.isContainCNChar(keywords)) {
			// 解密
			keywords = StringUtils.decryptUrlParameter(keywords);
		}
		if (id == null) {
			page = bbsPostService.pagePostByNewSearchEngine(keywords, page,
					null, 2);
		} else {
			page = bbsPostService.pagePostByNewSearchEngine(keywords, page, 1,
					2);
		}
		// 标签处理
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (BbsPostDO post : page.getRecords()) {
			List<BbsPostTags> list = new ArrayList<BbsPostTags>();
			BbsPostTags tag = new BbsPostTags();
			if (StringUtils.isNotEmpty(post.getTags())) {
				String[] str = post.getTags().replaceAll(",", "，").split("，");
				for (String s : str) {
					if (StringUtils.isNotEmpty(s)) {
						tag = bbsPostTagsService.queryTagByName(s,
								post.getBbsPostCategoryId(), 0);
						if (tag != null) {
							list.add(tag);
						}
					}
				}
			}
			map.put(post.getId(), list);
		}
		// 加密
		String keyword = URLEncoder.encode(keywords, HttpUtils.CHARSET_UTF8);
		out.put("keywords", keywords);
		out.put("keyword", keyword);
		out.put("page", page);
		out.put("map", map);
		out.put("id", id);

		SeoUtil.getInstance().buildSeo(out);

		return new ModelAndView();
	}

	// url配置
	public Map<Integer, String> getPath() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(21, "hangye-feijs");
		map.put(22, "hangye-feisl");
		map.put(23, "hangye-zonghe");
		map.put(15, "tuiguang");
		map.put(16, "zhinan");
		map.put(17, "shiyong");
		map.put(18, "zhuangxiu");
		map.put(19, "youhua");
		map.put(4, "dongtai");
		map.put(6, "baoguang");
		map.put(7, "zixun");
		map.put(9, "jiaoliu");
		map.put(10, "zhanwu");
		map.put(11, "hangye");
		map.put(12, "aqsiq");
		map.put(13, "baike");
		map.put(14, "shengyijing");
		map.put(1, "wenda");
		map.put(2, "shequ");
		map.put(3, "xueyuan");
		map.put(0, "toutiao");
		map.put(107, "shangquan1");
		map.put(106, "shangquan1");
		return map;
	}

	// 获取当前位置
	public void getList(Map<String, Object> out, Integer num) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<Integer, String> mapUrl = getPath();
		Integer tip = 1;
		// 最末位置
		Map<String, String> map = new HashMap<String, String>();
		BbsPostCategory local = bbsPostCategoryService
				.querySimpleCategoryById(num);
		map.put("path", mapUrl.get(local.getId()));
		map.put("name", local.getName());
		list.add(map);
		while (tip == 1) {
			local = bbsPostCategoryService.querySimpleCategoryById(local
					.getParentId());
			if (local == null) {
				tip = 0;
			} else {
				map = new HashMap<String, String>();
				map.put("path", mapUrl.get(local.getId()));
				map.put("name", local.getName());
				list.add(map);
			}
		}
		// 反转
		Collections.reverse(list);
		String path = "";
		for (Map<String, String> li : list) {
			if (StringUtils.isNotEmpty(path) && !path.contains("shengyijing")
					&& !path.contains("hangye")) {
				path = path + "/" + li.get("path");
			} else if (path.contains("shengyijing") || path.contains("hangye")) {
				path = path.replace("shengyijing", li.get("path")).replace(
						"hangye", li.get("path"));
			} else {
				path = li.get("path");
			}
			li.put("path", path);
		}
		out.put("listPath", list);
	}

	@RequestMapping
	public ModelAndView zan(Map<String, Object> out,
			HttpServletRequest request, Integer replyId) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		Integer mark = -1;
		if (sessionUser != null) {
			mark = bbsPostReplyService.zanBbsPostReply(
					sessionUser.getCompanyId(), replyId);
		}
		map.put("mark", mark);
		return printJson(map, out);
	}
}
