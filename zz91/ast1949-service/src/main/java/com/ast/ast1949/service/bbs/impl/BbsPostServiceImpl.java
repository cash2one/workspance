/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 11, 2010 by Rolyer.
 */
package com.ast.ast1949.service.bbs.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsPostType;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostSearchDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.persist.bbs.BbsPostCategoryDao;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.persist.bbs.BbsUserProfilerDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;
import com.zz91.util.tags.TagsUtils;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */

@Component("bbsPostService")
public class BbsPostServiceImpl implements BbsPostService {
	/**
	 * 已审核
	 */
	private static final String CHECK_STATUS_TRUE = "1";
	/**
	 * 未删除
	 */
	private static final String IS_DEL_FALSE = "0";
	/**
	 * 热门关注的条数限制
	 */
	private static final Integer NOTICE_MAX_NUMBER = 10000;

	@Resource
	private BbsPostDAO bbsPostDAO;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private BbsUserProfilerDao bbsUserProfilerDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private BbsPostReplyDao bbsPostReplyDao;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	@Resource
	private BbsPostCategoryDao bbsPostCategoryDao;
	@Resource
	private CompanyAccountService companyAccountService;
	
	@Override
	public Map<String, List<BbsPostDO>> queryRecentPostOfAccounts(
			Set<String> account) {
		Map<String, List<BbsPostDO>> map = new HashMap<String, List<BbsPostDO>>();
		for (String a : account) {
			if (map.get(a) == null) {
				map.put(a, bbsPostDAO.queryRecentByAccount(a, 3));
			}
		}
		return map;
	}

	@Override
	public PageDto<PostDto> pagePostByCategory(Integer categoryId,
			Integer searchType, PageDto<PostDto> page) {
		page.setRecords(bbsPostDAO.queryPostByCategory(categoryId, searchType,
				page));
		page.setTotalRecords(bbsPostDAO.queryPostByCategoryCount(categoryId,
				searchType));
		return page;
	}

	@Override
	public PageDto<PostDto> pagePostBySearch(String keywords,
			PageDto<PostDto> page) {
		List<PostDto> list = bbsPostDAO.queryPostBySearch(keywords, page);
		for (PostDto dto : list) {
			dto.getPost().setContent(
					Jsoup.clean(dto.getPost().getContent(), Whitelist.none()));
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.queryPostBySearchCount(keywords));
		return page;
	}

	@Override
	public List<BbsPostDO> querySimplePostByCategory(Integer categoryId,
			Integer max) {
		if (max == null) {
			max = 10;
		}
		List<BbsPostDO> list = bbsPostDAO.querySimplePostByCategory(categoryId,
				max);
		for (BbsPostDO post : list) {
			if (StringUtils.isNotEmpty(post.getContent())) {
				post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
			}
		}
		return list;
	}

	@Override
	public PageDto<PostDto> pagePostByAdmin(String account, BbsPostDO post,
			PageDto<PostDto> page, String selectTime, String from, String to) {
		if (page.getSort() == null) {
			page.setSort("id");
			page.setDir("desc");
		}
		List<PostDto> resultList = new ArrayList<PostDto>();
		List<BbsPostDO> list = bbsPostDAO.queryPostByAdmin(account, post, page,
				selectTime, from, to);
		Map<Integer, Company> map = new HashMap<Integer, Company>();
		for (BbsPostDO p : list) {
			PostDto dto = new PostDto();
			// 获取发贴人昵称
			if (!"leicf".equals(p.getAccount())) {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryProfilerOfAccount(p.getAccount());
				if (profiler != null
						&& StringUtils.isNotEmpty(profiler.getNickname())) {
					p.setNickname(profiler.getNickname());
				} else {
					CompanyAccount ca = companyAccountDao
							.queryAccountByCompanyId(p.getCompanyId());
					if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
						p.setNickname(ca.getContact());
					}
				}
			} else {
				p.setNickname("互助管理员");
			}
			if (StringUtils.isNumber(p.getNickname())
					&& p.getNickname().length() > 6) {
				p.setNickname(p.getNickname().substring(0, 6));
			}
			// 添加内容缩略列，不得超过30个字
			if (StringUtils.isNotEmpty(p.getContent())) {
				String intro = Jsoup.clean(p.getContent(), Whitelist.none())
						.replace("&nbsp;", "");
				if (intro.length() <= 30) {
					dto.setContentIntro(intro);
				} else {
					dto.setContentIntro(intro.substring(0, 30));
				}
			}
			dto.setPost(p);
			if (p.getCompanyId() != null && p.getCompanyId().intValue() > 0) {
				if (map.get(p.getCompanyId()) == null) {
					map.put(p.getCompanyId(),
							companyDAO.querySimpleCompanyById(p.getCompanyId()));
				}
			}
			if (map.get(p.getCompanyId()) != null) {
				dto.setCompany(map.get(p.getCompanyId()));
				dto.setMembershipLabel(CategoryFacade.getInstance().getValue(
						dto.getCompany().getMembershipCode()));
			} else {
				dto.setCompany(new Company());
			}
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(bbsPostDAO.queryPostByAdminCount(account, post,
				selectTime, from, to));
		return page;
	}

	@Override
	public Integer updateCheckStatus(Integer id, String checkStatus,
			String admin) {
		if (checkStatus == null) {
			return null;
		}
		return bbsPostDAO.updateCheckStatus(id, checkStatus, admin);
	}

	@Override
	public Integer updateIsDel(Integer id, String isDel) {
		return bbsPostDAO.updateIsDel(id, isDel);
	}

	public Integer deleteBbsPost(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return bbsPostDAO.deleteBbsPost(id);
	}

	@Override
	public BbsPostDO queryPostById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		BbsPostDO post = bbsPostDAO.queryPostById(id);
		if (post != null && post.getIntegral() == null) {
			post.setIntegral(0);
		}
		return post;
	}

	@Override
	public Integer createPostByAdmin(BbsPostDO post, String admin) {
		Assert.notNull(post, "the post must not be null");
		post.setCompanyId(0);
		post.setAccount(admin);
		post.setBbsUserProfilerId(0);
		post.setIsDel(IS_DEL_FALSE);
		post.setCheckPerson(admin);
		post.setCheckStatus(CHECK_STATUS_TRUE);
		if (post.getIntegral() == null) {
			post.setIntegral(0);
		}
		// post.setTags(TagsUtils.getInstance().arrangeTags(post.getTags()));
		// TagsUtils.getInstance().createTags(post.getTags());
		if (post.getContent() == null) {
			post.setContent("");
		}
		return bbsPostDAO.insertPost(post);
	}

	@Override
	public Integer updatePostByAdmin(BbsPostDO post) {
		Assert.notNull(post, "the post must not be null");
		post.setTags(TagsUtils.getInstance().arrangeTags(post.getTags()));
		TagsUtils.getInstance().createTags(post.getTags());
		return bbsPostDAO.updatePostByAdmin(post);
	}

	@Override
	public List<BbsPostDO> queryPostWithContentByType(String type, Integer size) {
		if (size == null) {
			size = 5;
		}
		List<BbsPostDO> list = bbsPostDAO
				.queryPostWithContentByType(type, size);
		for (BbsPostDO post : list) {
			if (post.getContent() == null) {
				post.setContent("");
			}
			try {
				post.setTitle(StringUtils.controlLength(post.getTitle(), 40));
				String content = StringUtils.removeHTML(post.getContent());
				content = content.replace("&nbsp;", "");
				post.setContent(StringUtils.controlLength(content, 272));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<PostDto> queryPostByType(String type, Integer size) {
		if (size == null) {
			size = 5;
		}
		return bbsPostDAO.queryPostByType(type, size);
	}

	@Override
	public List<BbsPostDO> queryNewestPost(Integer size) {
		if (size == null) {
			size = 6;
		}
		return bbsPostDAO.queryRecentByAccount(null, size);
	}

	@Override
	public List<BbsPostDO> queryMostViewedPost(Integer size) {
		if (size == null) {
			size = 10;
		}
		String targetDate = DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -30), "yyyy-MM-dd");
		// return bbsPostDAO.queryPostOrderBy("visited_count","desc",size);
		return bbsPostDAO.queryMostViewedPost(targetDate, size);
	}

	@Override
	public List<BbsPostDO> query24HourHot(Integer size, Integer categoryId) {
		if (size == null) {
			size = 10;
		}
		// long targetDate=DateUtil.getTheDayZero(new Date(), -1);
		String targetDate = DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -7), "yyyy-MM-dd");
		// return bbsPostDAO.queryPostByViewLog(targetDate, size);
		// 获取前七天的最热帖子
		List<BbsPostDO> list1 = bbsPostDAO.query24HourHot(targetDate, size,
				categoryId);
		// 总列表
		// 取size条数据
		List<BbsPostDO> resultList = new ArrayList<BbsPostDO>();
		do {
			// 1.如果小于size条
			if (list1.size() >= size) {
				resultList = list1;
				break;
			}
			// 2.取最新互助
			List<BbsPostDO> list2 = bbsPostDAO.querySimplePostByCategory(
					categoryId, size);
			list1.addAll(list2);
			// 3.补齐 返回
			Map<Integer, BbsPostDO> map = new LinkedHashMap<Integer, BbsPostDO>();
			for (BbsPostDO obj : list1) {
				map.put(obj.getId(), obj);
				if (map.size() >= 10) {
					break;
				}
			}
			// 遍历map
			for (Integer id : map.keySet()) {
				resultList.add(map.get(id));
			}
		} while (false);
		return resultList;
		// return list1;
		// 如果不小于 过直接返回

		// return list;
	}

	@Override
	public Integer createPostByUser(BbsPostDO post, String membershipCode) {
		Assert.notNull(post, "the object post can not be null");
		Integer profilerId = bbsUserProfilerDao.countProfilerByAccount(post
				.getAccount());
		if (profilerId == null || profilerId.intValue() <= 0) {
			CompanyAccount account = companyAccountDao
					.queryAccountByAccount(post.getAccount());
			BbsUserProfilerDO profiler = new BbsUserProfilerDO();
			profiler.setCompanyId(account.getCompanyId());
			profiler.setAccount(account.getAccount());
			profiler.setNickname(account.getContact());
			profiler.setEmail(account.getEmail());
			profiler.setMobile(account.getMobile().substring(0, 11));
			profiler.setQq(account.getQq());

			profilerId = bbsUserProfilerDao.insertProfiler(profiler);
		}
		post.setBbsUserProfilerId(profilerId);
		post.setCheckStatus(MemberRuleFacade.getInstance().getValue(
				membershipCode, "new_help_bbs_check"));
		if (post.getCheckStatus() == null) {
			post.setCheckStatus("0");
		}
		post.setIsDel("0");
		post.setPostTime(new Date());
		post.setReplyTime(new Date());
		post.setVisitedCount(0);
		post.setReplyCount(0);

		post = checkPostByAdmin(post); // 审核敏感词

		post.setTitle(Jsoup.clean(post.getTitle(), Whitelist.none()));
		// post.setContent(Jsoup.clean(post.getContent(), Whitelist.relaxed()));

		Integer id = bbsPostDAO.insertPost(post);
		if (id != null && id.intValue() > 0
				&& "1".equals(post.getCheckStatus())) {
			bbsUserProfilerService.updatePostNumber(post.getAccount());
			scoreChangeDetailsService
					.saveChangeDetails(new ScoreChangeDetailsDo(post
							.getCompanyId(), null, "get_post_bbs", null, id,
							null));
		}

		return id;
	}

	@Override
	public PageDto<BbsPostDO> pagePostByUser(String account,
			String checkStatus, String isDel, Integer bbsPostCategoryId,
			String replyAccount, String title, PageDto<BbsPostDO> page) {
		page.setSort("post_time");
		page.setDir("desc");
		if (page.getPageSize() == null) {
			page.setPageSize(10);
		}

		BbsPostSearchDto searchDto = new BbsPostSearchDto();

		searchDto.setAccount(account);
		searchDto.setCheckStatus(checkStatus);
		searchDto.setIsDel(isDel);
		searchDto.setBbsPostCategoryId(bbsPostCategoryId);
		searchDto.setReplyAccount(replyAccount);
		searchDto.setTitle(title);

		page.setRecords(bbsPostDAO.queryPostByUser(searchDto, page));
		page.setTotalRecords(bbsPostDAO.queryPostByUserCount(searchDto));
		return page;
	}

	@Override
	public Integer updatePostByUser(BbsPostDO post, String membershipCode) {
		Assert.notNull(post.getId(), "the id can not be null");
		post.setCheckStatus(MemberRuleFacade.getInstance().getValue(
				membershipCode, "new_help_bbs_check"));
		if (post.getCheckStatus() == null
				|| StringUtils.isEmpty(post.getCheckStatus())) {
			post.setCheckStatus("0");
		}

		post = checkPostByAdmin(post); // 敏感词过滤

		// String tags=post.getTags();
		// String title=post.getTitle();
		// String content=post.getContent();
		// try {
		// if (SensitiveUtils.validateSensitiveFilter(tags)) {
		// tags=SensitiveUtils.getSensitiveValue(tags, "*");
		// }
		// if (SensitiveUtils.validateSensitiveFilter(content)) {
		// tags=SensitiveUtils.getSensitiveValue(content, "*");
		// }
		// if (SensitiveUtils.validateSensitiveFilter(title)) {
		// title=SensitiveUtils.getSensitiveValue(title, "*");
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// post.setContent(content);
		post.setTags(TagsUtils.getInstance().arrangeTags(post.getTags()));
		post.setTitle(Jsoup.clean(post.getTitle(), Whitelist.none()));
		// post.setContent(Jsoup.clean(post.getContent(),
		// Whitelist.basicWithImages()));
		return bbsPostDAO.updatePostByUser(post);
	}

	@Override
	public PageDto<PostDto> pagePostBySearchEngine(String keywords,
			PageDto<PostDto> page) {

		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<PostDto> list = new ArrayList<PostDto>();
		try {
			sb.append("@(title,content_query,tags) ").append(keywords);

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "post_time desc");

			SphinxResult res = cl.Query(sb.toString(), "huzhu");

			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					PostDto dto = bbsPostDAO.queryPostWithProfileById(Integer
							.valueOf("" + info.docId));
					if (dto != null) {
						dto.getPost().setContent(
								Jsoup.clean(dto.getPost().getContent(),
										Whitelist.none()));
						list.add(dto);
					}
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}

	@Override
	public PageDto<PostDto> pagePostForNoAnswerBySearchEngine(String keywords,
			PageDto<PostDto> page) {
		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<PostDto> list = new ArrayList<PostDto>();
		try {
			sb.append("@(title,content_query,tags) ").append(keywords);
			// 问答
			cl.SetFilter("bbs_post_category_id", 1, false);
			// 没有回复
			cl.SetFilter("reply_count", 0, false);
			// 审核通过
			cl.SetFilter("check_status", 1, false);
			// 未删除
			cl.SetFilter("is_del", 0, false);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 10000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "post_time desc");
			SphinxResult res = cl.Query(sb.toString(), "huzhu");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					PostDto dto = bbsPostDAO.queryPostWithProfileById(Integer
							.valueOf("" + info.docId));
					if (dto == null) {
						page.setTotalRecords(res.totalFound - 1);
						continue;
					}
					if (dto.getProfiler().getNickname() == null) {
						CompanyAccount ca = companyAccountDao
								.queryAccountByCompanyId(dto.getPost()
										.getCompanyId());
						if (ca != null
								&& StringUtils.isNotEmpty(ca.getContact())) {
							dto.setContact(ca.getContact());
						}
					} else {
						dto.setContact(dto.getProfiler().getNickname());
					}

					if (dto != null) {
						dto.getPost().setContent(
								Jsoup.clean(dto.getPost().getContent(),
										Whitelist.none()));
						list.add(dto);
					}
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}

	@Override
	public PageDto<BbsPostDO> pagePostByNewSearchEngine(String keywords,
			PageDto<BbsPostDO> page, Integer type, Integer flag) {
		if (StringUtils.isNotEmpty(keywords)) {
			String[] tags = keywords.replaceAll("，", ",").split(",");
			keywords = "";
			for (String str : tags) {
				keywords = keywords + "(" + str + ")|";
			}
			keywords = keywords.substring(0, keywords.length() - 1);
			if ("()".equals(keywords)) {
				keywords = "";
			}
		}
		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<BbsPostDO> list = new ArrayList<BbsPostDO>();
		try {
			if (StringUtils.isNotEmpty(keywords)) {
				if (flag == 1) {
					// 相关帖子或问答
					sb.append("@(title,content_query,tags) ").append(keywords);
				} else if (flag == 2) {
					// 标签列表页
					sb.append("@(tags,title) ").append(keywords);
				} else if (flag == 3) {
					// 废料学院类别
					sb.append("@(category) ").append(keywords);
				}
			}
			// 标记帖子或标签
			if (type != null) {
				cl.SetFilter("bbs_post_category_id", type, false);
			}
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
					"post_time desc,reply_time desc");
			SphinxResult res = cl.Query(sb.toString(), "huzhu");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				if (res.totalFound > 10000) {
					page.setTotalRecords(10000);
				}
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					BbsPostDO post = bbsPostDAO.queryPostById(Integer
							.valueOf("" + info.docId));
					if (post != null) {
						if (StringUtils.isNotEmpty(post.getContent())) {
							post.setTitle(Jsoup.clean(post.getTitle(),
									Whitelist.none()));
							post.setContent(Jsoup.clean(post.getContent(),
									Whitelist.none()));
						}
						if (!"leicf".equals(post.getAccount())) {
							BbsUserProfilerDO profiler = bbsUserProfilerDao
									.queryProfilerOfAccount(post.getAccount());
							if (profiler != null
									&& StringUtils.isNotEmpty(profiler
											.getNickname())) {
								post.setAccount(profiler.getNickname());
							} else {
								CompanyAccount ca = companyAccountDao
										.queryAccountByAccount(post
												.getAccount());
								if (ca != null
										&& StringUtils.isNotEmpty(ca
												.getContact())) {
									post.setAccount(ca.getContact());
								} else {
									post.setAccount("匿名");
								}
							}
							if (StringUtils.isNumber(post.getAccount())
									&& post.getAccount().length() > 6) {
								post.setAccount(post.getAccount().substring(0,
										6));
							}
						} else {
							post.setAccount("互助管理员");
						}
						list.add(post);
					}
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}

	@Override
	public PageDto<BbsPostDO> pageCollegeByEngine(String keywords,
			PageDto<BbsPostDO> page, Integer assistId) {

		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<BbsPostDO> list = new ArrayList<BbsPostDO>();
		try {
			// 废料学院类别
			sb.append("@(category) ").append(keywords);
			// 标记帖子或标签
			cl.SetFilter("bbs_post_category_id", 3, false);
			cl.SetFilter("bbs_post_assist_id", assistId, true);
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);

			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "post_time desc");
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);

			SphinxResult res = cl.Query(sb.toString(), "huzhu");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				if (res.totalFound > 10000) {
					page.setTotalRecords(10000);
				}
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					BbsPostDO post = bbsPostDAO.queryPostById(Integer
							.valueOf("" + info.docId));
					if (post != null) {
						if (StringUtils.isNotEmpty(post.getContent())) {
							post.setTitle(Jsoup.clean(post.getTitle(),
									Whitelist.none()));
							post.setContent(Jsoup.clean(post.getContent(),
									Whitelist.none()));
						}
						BbsUserProfilerDO profiler = bbsUserProfilerDao
								.queryProfilerOfAccount(post.getAccount());
						if (profiler != null
								&& StringUtils.isNotEmpty(profiler
										.getNickname())) {
							post.setAccount(profiler.getNickname());
						} else {
							CompanyAccount ca = companyAccountDao
									.queryAccountByAccount(post.getAccount());
							if (ca != null
									&& StringUtils.isNotEmpty(ca.getContact())) {
								post.setAccount(ca.getContact());
							} else {
								post.setAccount("匿名");
							}
						}
						if (StringUtils.isNumber(post.getAccount())
								&& post.getAccount().length() > 6) {
							post.setAccount(post.getAccount().substring(0, 6));
						}

						list.add(post);
					}
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}

	@Override
	public PageDto<BbsPostDO> pagePostForBbsCategory(String keywords,
			PageDto<BbsPostDO> page, Integer type, Integer flag) {

		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<BbsPostDO> list = new ArrayList<BbsPostDO>();
		try {
			if (type == 9) {
				sb.append("@(title,content_query,tags) ").append("台州塑交会");
			} else {
				if (type != 0) {
					cl.SetFilter("bbs_post_assist_id", type, false);
				} else {
					cl.SetFilter("post_type", 3, false);
				}
			}
			// cl.SetFilter("bbs_post_assist_id", type, false);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			if (flag == 1) {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "post_time desc");
			} else if (flag == 2) {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
						"visited_count desc,reply_time desc");
			} else {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
						"notice_count desc,reply_time desc");
			}

			SphinxResult res = cl.Query(sb.toString(), "huzhu");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				if (res.totalFound > 10000) {
					page.setTotalRecords(10000);
				}
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					BbsPostDO post = bbsPostDAO.queryPostById(Integer
							.valueOf("" + info.docId));
					if (post != null) {
						if (StringUtils.isNotEmpty(post.getContent())) {
							post.setTitle(Jsoup.clean(post.getTitle(),
									Whitelist.none()));
							post.setContent(Jsoup.clean(post.getContent(),
									Whitelist.none()));
						}
						if (!"leicf".equals(post.getAccount())) {
							BbsUserProfilerDO profiler = bbsUserProfilerDao
									.queryProfilerOfAccount(post.getAccount());
							if (profiler != null
									&& StringUtils.isNotEmpty(profiler
											.getNickname())) {
								post.setAccount(profiler.getNickname());
							} else {
								CompanyAccount ca = companyAccountDao
										.queryAccountByAccount(post
												.getAccount());
								if (ca != null
										&& StringUtils.isNotEmpty(ca
												.getContact())) {
									post.setAccount(ca.getContact());
								} else {
									post.setAccount("匿名");
								}
							}
							if (StringUtils.isNumber(post.getAccount())
									&& post.getAccount().length() > 6) {
								post.setAccount(post.getAccount().substring(0,
										6));
							}
						} else {
							post.setAccount("互助管理员");
						}
						list.add(post);
					}
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}

	@Override
	public PageDto<BbsPostDO> pagePostForViewQue(String keywords,
			PageDto<BbsPostDO> page, Integer flag) {

		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<BbsPostDO> list = new ArrayList<BbsPostDO>();
		try {
			cl.SetFilter("bbs_post_category_id", 1, false);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			if (flag == 1) {
				// 一个月内
				cl.SetFilterRange(
						"post_time",
						DateUtil.getDateAfterDays(new Date(), -30).getTime() / 1000,
						new Date().getTime() / 1000, false);
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
						"visited_count desc");
			} else if (flag == 2) {
				// 回复数小于2
				cl.SetFilterRange("reply_count", 0, 2, false);
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
						"reply_time desc");
			} else if (flag == 3) {
				// 两周内
				cl.SetFilterRange(
						"post_time",
						DateUtil.getDateAfterDays(new Date(), -13).getTime() / 1000,
						new Date().getTime() / 1000, false);
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "post_time desc");
			} else if (flag == 4) {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
						"notice_count desc,reply_time desc");
			} else if (flag == 7) {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
						"visited_count desc,reply_time desc");
			}
			SphinxResult res = cl.Query(sb.toString(), "huzhu");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				if (res.totalFound > 10000) {
					page.setTotalRecords(10000);
				}
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					BbsPostDO post = bbsPostDAO.queryPostById(Integer
							.valueOf("" + info.docId));
					if (post != null) {
						if (StringUtils.isNotEmpty(post.getContent())) {
							post.setTitle(Jsoup.clean(post.getTitle(),
									Whitelist.none()));
							post.setContent(Jsoup.clean(post.getContent(),
									Whitelist.none()));
						}
						if (!"leicf".equals(post.getAccount())) {
							BbsUserProfilerDO profiler = bbsUserProfilerDao
									.queryProfilerOfAccount(post.getAccount());
							if (profiler != null
									&& StringUtils.isNotEmpty(profiler
											.getNickname())) {
								post.setAccount(profiler.getNickname());
							} else {
								CompanyAccount ca = companyAccountDao
										.queryAccountByAccount(post
												.getAccount());
								if (ca != null
										&& StringUtils.isNotEmpty(ca
												.getContact())) {
									post.setAccount(ca.getContact());
								} else {
									post.setAccount("匿名");
								}
							}
							if (StringUtils.isNumber(post.getAccount())
									&& post.getAccount().length() > 6) {
								post.setAccount(post.getAccount().substring(0,
										6));
							}
						} else {
							post.setAccount("互助管理员");
						}
						list.add(post);
					}
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}

	@Override
	public List<BbsPostDO> queryHotPost(String account, Integer size) {
		if (size == null || size.intValue() <= 0) {
			size = 10;
		}
		return bbsPostDAO.queryPostOrderVisitCount(account, size);
	}

	@Override
	public String queryContent(Integer id) {
		if (id == null || id.intValue() < 0) {
			return null;
		}
		return bbsPostDAO.queryContent(id);
	}

	@Override
	public Integer updateContent(Integer id, String content) {
		return bbsPostDAO.updateContent(id, content);
	}

	@Override
	public List<ExtTreeDto> queryPostTypechild(String parentCode) {

		List<BbsPostType> list = bbsPostDAO.queryPostTypeChild(parentCode);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		Integer i = null;
		for (BbsPostType m : list) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(m.getId()));
			i = bbsPostDAO.queryPostTypeChildCount(m.getCode());
			if (i != null && i.intValue() > 0) {
				node.setLeaf(false);
			} else {
				node.setLeaf(true);
			}
			node.setText(m.getName());
			node.setData(m.getCode());
			treeList.add(node);
		}
		return treeList;
	}

	@Override
	public String queryPostTypeName(String postType) {
		if (!StringUtils.isNumber(postType)) {
			return null;
		}
		Integer i = Integer.valueOf(postType);
		if (i <= 0) {
			return null;
		}
		return bbsPostDAO.queryPostTypeName(i);
	}

	@Override
	public PageDto<PostDto> pageTheNewsPost(Integer maxSize,
			PageDto<PostDto> page) {
		List<BbsPostDO> list = bbsPostDAO.queryTheNewsPost(null, maxSize);
		List<PostDto> listDto = new ArrayList<PostDto>();
		for (int i = 0; i < list.size(); i++) {
			PostDto dto = new PostDto();
			dto.setPost(list.get(i));
			listDto.add(dto);
		}
		page.setRecords(listDto);
		page.setTotalRecords(listDto.size());
		return page;
	}

	@Override
	public PageDto<PostDto> pageMorePostByType(String type,
			PageDto<PostDto> page) {
		page.setSort("post_time");
		page.setDir("desc");
		page.setTotalRecords(bbsPostDAO.queryMorePostByTypeCount(type));
		page.setRecords(bbsPostDAO.queryMorePostByType(type, page));
		return page;
	}

	@Override
	public Integer countMyposted(String account, String checkStatus,
			String isDel, Integer bbsPostCategoryId) {
		BbsPostSearchDto searchDto = new BbsPostSearchDto();
		searchDto.setAccount(account);
		searchDto.setCheckStatus(checkStatus);
		searchDto.setIsDel(isDel);
		searchDto.setBbsPostCategoryId(bbsPostCategoryId);
		Integer i = bbsPostDAO.queryPostByUserCount(searchDto);
		if (i == null) {
			return 0;
		}
		return i;
	}

	@Override
	public Integer countMyBbs(String account, String checkStatus, String isDel,
			Integer bbsPostCategoryId) {
		BbsPostSearchDto searchDto = new BbsPostSearchDto();
		searchDto.setAccount(account);
		searchDto.setCheckStatus(checkStatus);
		searchDto.setIsDel(isDel);
		searchDto.setBbsPostCategoryId(bbsPostCategoryId);
		Integer i = bbsPostDAO.queryMyBbsCount(searchDto);
		if (i == null) {
			return 0;
		}
		return i;
	}

	@Override
	public Integer updateCheckStatusForFront(Integer id, String checkStatus) {
		return bbsPostDAO.updateCheckStatusForFront(id, checkStatus);
	}

	@Override
	public BbsPostDO queryDownBbsPostById(Integer id) {
		return bbsPostDAO.queryDownBbsPostById(id);
	}

	@Override
	public BbsPostDO queryOnBbsPostById(Integer id) {
		return bbsPostDAO.queryOnBbsPostById(id);
	}

	@Override
	public Integer queryCountPostByTime(String fromDate, String toDate,
			Integer categoryId) {

		return bbsPostDAO.queryCountPostByTime(fromDate, toDate, categoryId);
	}

	@Override
	public List<PostDto> query24HourHotByAnswer(Integer size, Integer categoryId) {
		if (size == null) {
			size = 10;
		}
		String targetDate = DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -365), "yyyy-MM-dd");
		return bbsPostDAO.query24HourHotByAnswer(targetDate, size, categoryId);
	}

	@Override
	public Integer recommendPostById(Integer postId) {
		if (postId == null) {
			return 0;
		}
		return bbsPostDAO.recommendPostById(postId);
	}

	@Override
	public List<BbsPostDO> queryBbsPostByNoticeAReplyTime(Integer size) {
		if (size == null) {
			size = NOTICE_MAX_NUMBER;
		}
		List<BbsPostDO> list = bbsPostDAO.queryBbsPostByNoticeAReplyTime(size);
		for (BbsPostDO post : list) {
			post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
		}
		return list;
	}

	@Override
	public List<BbsPostDO> queryBbsPostByAssistId(Integer assistId, Integer size) {
		if (size == null) {
			size = NOTICE_MAX_NUMBER;
		}
		List<BbsPostDO> list = bbsPostDAO
				.queryBbsPostByAssistId(assistId, size);
		for (BbsPostDO post : list) {
			if (post.getContent() != null) {
				post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
			}
		}
		return list;
	}

	@Override
	public List<BbsPostDO> queryWaitAnswerBbsPost(Integer size) {
		if (size == null) {
			size = NOTICE_MAX_NUMBER;
		}
		List<BbsPostDO> list = bbsPostDAO.queryWaitAnswerBbsPost(size);
		for (BbsPostDO post : list) {
			post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
		}
		return list;
	}

	@Override
	public List<BbsPostDO> queryHotBbsPost(Integer size, Integer categoryId) {
		if (size == null) {
			size = NOTICE_MAX_NUMBER;
		}
		List<BbsPostDO> list = bbsPostDAO.queryHotBbsPost(size, categoryId);
		for (BbsPostDO post : list) {
			post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
		}
		return list;
	}

	@Override
	public PageDto<BbsPostDO> ListForQA(PageDto<BbsPostDO> page, String date) {
		List<BbsPostDO> list = bbsPostDAO.queryListForQA(page, date);
		for (BbsPostDO bbs : list) {
			bbs.setContent(Jsoup.clean(bbs.getContent(), Whitelist.none()));
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.countListForQA(date));
		return page;
	}

	@Override
	public PageDto<BbsPostDO> ListForWait(PageDto<BbsPostDO> page) {
		List<BbsPostDO> list = bbsPostDAO.queryListForWait(page);
		for (BbsPostDO bbs : list) {
			bbs.setContent(Jsoup.clean(bbs.getContent(), Whitelist.none()));
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.countListForWait());
		return page;
	}

	@Override
	public PageDto<BbsPostDO> ListForNewQA(PageDto<BbsPostDO> page, String date) {
		List<BbsPostDO> list = bbsPostDAO.queryListForNewQA(page, date);
		for (BbsPostDO bbs : list) {
			bbs.setContent(Jsoup.clean(bbs.getContent(), Whitelist.none()));
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.countListForNewQA(date));
		return page;
	}

	@Override
	public PageDto<BbsPostDO> ListForHotNotice(PageDto<BbsPostDO> page) {
		List<BbsPostDO> list = bbsPostDAO.queryListForHotNotice(page);
		for (BbsPostDO bbs : list) {
			bbs.setContent(Jsoup.clean(bbs.getContent(), Whitelist.none()));
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.countListForHotNotice());
		return page;
	}

	@Override
	public PageDto<BbsPostDO> ListBbsPost(PageDto<BbsPostDO> page,
			Integer assistId, Integer flag) {
		List<BbsPostDO> list = new ArrayList<BbsPostDO>();
		if (assistId == 0) {
			if (flag == 1) {
				page.setSort("reply_time");
				page.setDir("desc");
			} else if (flag == 2) {
				page.setSort("visited_count");
				page.setDir("desc");
			} else if (flag == 3) {
				page.setSort("notice_count");
				page.setDir("desc");
			}
			list = bbsPostDAO.queryToutiaoByPostType(page);
		} else {
			if (flag == 1) {
				// 全部
				page.setSort("reply_time");
				page.setDir("desc");
			} else if (flag == 2) {
				// 热门
				page.setSort("visited_count");
				page.setDir("desc");
			} else if (flag == 3) {
				// 精华
				page.setSort("notice_count");
				page.setDir("desc");
			}
			list = bbsPostDAO.queryAllListBbsPost(page, assistId);
		}
		for (BbsPostDO post : list) {
			BbsUserProfilerDO profiler = bbsUserProfilerDao
					.queryUserByCompanyId(post.getCompanyId());
			if (profiler != null
					&& StringUtils.isNotEmpty(profiler.getNickname())) {
				post.setCheckPerson(profiler.getNickname());
			} else {
				CompanyAccount ca = companyAccountDao
						.queryAccountByCompanyId(post.getCompanyId());
				if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
					post.setCheckPerson(ca.getContact());
				} else {
					post.setCheckPerson("匿名");
				}
			}
			if (StringUtils.isNumber(post.getAccount())
					&& post.getAccount().length() > 6) {
				post.setAccount(post.getAccount().substring(0, 6));
			}
			if (StringUtils.isNotEmpty(post.getContent())) {
				post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
			}
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.countBbsPostByassistId(assistId));
		return page;
	}

	@Override
	public PageDto<PostDto> ListWaitAnswer(PageDto<PostDto> page) {
		List<PostDto> list = bbsPostDAO.queryWaitAnswerByReplyCount(page);
		for (PostDto pd : list) {
			pd.getPost().setContent(
					Jsoup.clean(pd.getPost().getContent(), Whitelist.none()));
			if (pd.getProfiler() == null
					|| StringUtils.isNotEmpty(pd.getProfiler().getNickname())) {
				CompanyAccount ca = companyAccountDao
						.queryAccountByCompanyId(pd.getPost().getCompanyId());
				if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
					pd.setContact(ca.getContact());
				}
			} else {
				pd.setContact(pd.getProfiler().getNickname());
			}
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.countWaitAnswerByReplyCount());
		return page;
	}

	@Override
	public PageDto<PostDto> ListBbsPostByUser(Integer categoryId,
			String account, PageDto<PostDto> page, Integer type, String keywords) {
		List<PostDto> list = new ArrayList<PostDto>();
		if (type == 1) {
			list = bbsPostDAO.queryBbsPostByUser(categoryId, account, page,
					keywords);
			page.setTotalRecords(bbsPostDAO.countBbsPostByUser(categoryId,
					account, keywords));
		} else {
			List<BbsPostDO> listBbs = bbsPostReplyDao.queryBbsReplyByUser(
					account, categoryId, page, keywords);
			for (BbsPostDO bbs : listBbs) {
				PostDto pd = new PostDto();
				pd.setPost(bbs);
				BbsUserProfilerDO bp = bbsUserProfilerDao
						.queryProfilerOfAccount(account);
				if (bp != null) {
					pd.setProfiler(bp);
				}
				list.add(pd);
			}
			page.setTotalRecords(bbsPostReplyDao.countBbsReplyByUser(account,
					categoryId, keywords));
		}
		for (PostDto pd : list) {
			if (StringUtils.isNotEmpty(pd.getPost().getContent())) {
				pd.getPost()
						.setContent(
								Jsoup.clean(pd.getPost().getContent(),
										Whitelist.none()));
			}
			BbsPostReplyDO reply = bbsPostReplyDao.queryLatestReplyByPostId(pd
					.getPost().getId());
			if (reply == null) {
				continue;
			}
			BbsUserProfilerDO profiler = bbsUserProfilerDao
					.queryUserByCompanyId(reply.getCompanyId());
			if (profiler != null
					&& StringUtils.isNotEmpty(profiler.getNickname())) {
				pd.setReplyName(profiler.getNickname());
			} else {
				CompanyAccount ca = companyAccountDao
						.queryAccountByCompanyId(reply.getCompanyId());
				if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
					pd.setReplyName(ca.getContact());
				}
			}
			if (StringUtils.isEmpty(pd.getReplyName())) {
				pd.setReplyName("匿名");
			}
			if (StringUtils.isNumber(pd.getReplyName())
					&& pd.getReplyName().length() > 6) {
				pd.setReplyName(pd.getReplyName().substring(0, 6));
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public PageDto<BbsPostDO> ListHostBbsPost(PageDto<BbsPostDO> page) {
		List<BbsPostDO> list = bbsPostDAO.queryListHotBbsPost(page);
		for (BbsPostDO bbs : list) {
			bbs.setContent(Jsoup.clean(bbs.getContent(), Whitelist.none()));
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.countListHotBbsPost());
		return page;
	}

	@Override
	public List<BbsPostDO> queryBbsPostByCompanyId(Integer companyId,
			Integer categoryId, Integer size) {
		List<BbsPostDO> list = bbsPostDAO.queryBbsPostByCompanyId(companyId,
				categoryId, size);
		for (BbsPostDO bbs : list) {
			bbs.setContent(Jsoup.clean(bbs.getContent(), Whitelist.none()));
		}
		return list;
	}

	@Override
	public Integer countBbsPostByCompanyId(Integer companyId, Integer categoryId) {
		return bbsPostDAO.countBbsPostByCompanyId(companyId, categoryId);
	}

	@Override
	public Integer countBbsByCompanyId(Integer companyId, Integer categoryId) {
		return bbsPostDAO.countBbsPostByCompanyId(companyId, categoryId);
	}

	@Override
	public PageDto<BbsPostDO> ListBbsPostByCompanyId(Integer companyId,
			Integer categoryId, PageDto<BbsPostDO> page) {
		// 获取帖子或问题列表
		List<BbsPostDO> list = bbsPostDAO.ListBbsPostByCompanyId(companyId,
				categoryId, page);
		for (BbsPostDO post : list) {
			post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
			if (categoryId == 2) {
				post.setCheckPerson(getShequCategory(2).get(
						post.getBbsPostAssistId()));
			} else {
				post.setCheckPerson("废料问答");
			}
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.countBbsPostByCompanyId(companyId,
				categoryId));
		return page;
	}

	// 社区类别
	public Map<Integer, String> getShequCategory(Integer parentId) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<BbsPostCategory> list = bbsPostCategoryDao
				.queryCategoryByParentId(2);
		for (BbsPostCategory cate : list) {
			map.put(cate.getId(), cate.getName());
		}
		return map;
	}

	@Override
	public List<BbsPostDO> queryPostByNoticeAndView(Integer categoryId,
			Integer size) {
		return bbsPostDAO.queryPostByNoticeAndView(categoryId, size);
	}

	@Override
	public List<BbsPostDO> queryNewPost(Integer size) {
		return bbsPostDAO.queryNewPost(size);
	}

	@Override
	public Integer updateVisitedCountById(Integer visitCount, Integer id) {
		return bbsPostDAO.updateVisitedCountById(visitCount, id);
	}

	/**
	 * 帖子敏感词审核
	 * 
	 * @param post
	 * @return
	 */
	private BbsPostDO checkPostByAdmin(BbsPostDO post) {
		try {
			// 标题敏感词过滤
			boolean mgcFlag = false;
			Set<String> sensitiveSet = new HashSet<String>();
			if (SensitiveUtils.validateSensitiveFilter(post.getTitle())) {
				Map<String, Object> titleMap = SensitiveUtils
						.getSensitiveFilter(post.getTitle());
				@SuppressWarnings("unchecked")
				Set<String> s = (Set<String>) titleMap.get("sensitiveSet");
				for (String obj : s) {
					sensitiveSet.add(obj);
				}
				post.setTitle(titleMap.get("filterValue").toString());
				mgcFlag = true;
			}
			// 正文敏感词过滤
			if (SensitiveUtils.validateSensitiveFilter(post.getContent())) {
				Map<String, Object> contentMap = SensitiveUtils
						.getSensitiveFilter(post.getContent());
				@SuppressWarnings("unchecked")
				Set<String> s = (Set<String>) contentMap.get("sensitiveSet");
				for (String obj : s) {
					sensitiveSet.add(obj);
				}
				post.setContent(contentMap.get("filterValue").toString());
				mgcFlag = true;
			}
			// 标签敏感词过滤
			if (SensitiveUtils.validateSensitiveFilter(post.getTags())) {
				Map<String, Object> tagMap = SensitiveUtils
						.getSensitiveFilter(post.getTags());
				@SuppressWarnings("unchecked")
				Set<String> s = (Set<String>) tagMap.get("sensitiveSet");
				for (String obj : s) {
					sensitiveSet.add(obj);
				}
				post.setTags(tagMap.get("filterValue").toString());
				mgcFlag = true;
			}

			// 帖子不通过并设置相关属性
			if (mgcFlag) {
				post.setCheckStatus(CHECK_STATUS_UNPASS);
				post.setCheckPerson("admin");
				post.setCheckTime(new Date());
				post.setUnpassReason("该帖子包含敏感词：" + sensitiveSet.toString());
			}
		} catch (Exception e) {
			return post;
		}
		return post;
	}

	@Override
	public PageDto<BbsPostDO> pagePostNew(Integer cateGoryId,
			PageDto<BbsPostDO> page) {
		List<BbsPostDO> list = bbsPostDAO.pagePostNew(cateGoryId, page);
		Integer i = bbsPostDAO.pagePostNewCount(cateGoryId);
		for (BbsPostDO li : list) {
			if (StringUtils.isEmpty(li.getTitle())) {
				li.setTitle(Jsoup.clean(li.getContent(), Whitelist.none()));
			}
			if (li.getContent() != null) {
				li.setContent(Jsoup.clean(li.getContent(), Whitelist.none()));
			}

			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(li.getAccount());
			if (profiler != null
					&& StringUtils.isNotEmpty(profiler.getNickname())) {
				li.setNickname(profiler.getNickname());
			} else {
				CompanyAccount ca = companyAccountService
						.queryAccountByCompanyId(li.getCompanyId());
				if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
					li.setNickname(ca.getContact());
				}
			}
		}
		page.setRecords(list);
		page.setTotalRecords(i);
		return page;
	}

	@Override
	public Integer changShangQuan(Integer id) {
		return bbsPostDAO.changShangQuan(id);
	}
}
