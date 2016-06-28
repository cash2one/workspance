package com.ast.ast1949.service.bbs.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.domain.bbs.BbsPostTrends;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostNoticeRecommendDto;
import com.ast.ast1949.dto.bbs.NoticeDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.bbs.BbsPostNoticeRecommendDao;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.persist.bbs.BbsPostTagsDao;
import com.ast.ast1949.persist.bbs.BbsPostTrendsDao;
import com.ast.ast1949.persist.bbs.BbsUserProfilerDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.service.bbs.BbsPostCategoryService;
import com.ast.ast1949.service.bbs.BbsPostNoticeRecommendService;
import com.zz91.util.lang.StringUtils;

@Component("bbsPostNoticeRecommendService")
public class BbsPostNoticeRecommendServiceImpl implements
		BbsPostNoticeRecommendService {
	@Resource
	private BbsPostNoticeRecommendDao bbsPostNoticeRecommendDao;
	@Resource
	private BbsPostDAO bbsPostDAO;
	@Resource
	private BbsUserProfilerDao bbsUserProfilerDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private BbsPostTrendsDao bbsPostTrendsDao;
	@Resource
	private BbsPostTagsDao bbsPostTagsDao;
	@Resource
	private BbsPostReplyDao bbsPostReplyDao;
	@Resource
	private BbsPostCategoryService bbsPostCategoryService;

	@Override
	public List<BbsPostNoticeRecommendDto> queryZuiXinRecommendByCondition(
			BbsPostNoticeRecommend bbs, Integer size) {
		List<BbsPostNoticeRecommendDto> listR=new ArrayList<BbsPostNoticeRecommendDto>();
		List<BbsPostNoticeRecommend> list=bbsPostNoticeRecommendDao.queryZuiXinRecommendByCondition(bbs,size);
		for(BbsPostNoticeRecommend recom:list){
			if(recom.getContentId()!=null){
				BbsPostNoticeRecommendDto dto=new BbsPostNoticeRecommendDto();
				BbsPostDO post=bbsPostDAO.queryPostById(recom.getContentId());
				dto.setRecom(recom);
				if(post!=null&&StringUtils.isNotEmpty(post.getUrl())){
					dto.setUrl(post.getUrl());
				}
				listR.add(dto);
			}
		}
		 return listR;
	}

	@SuppressWarnings("null")
	@Override
	public List<NoticeDto> ListNoticeByUser(String account, Integer category,
			Integer size) {
		List<NoticeDto> list = new ArrayList<NoticeDto>();
		List<BbsPostNoticeRecommend> listNot = bbsPostNoticeRecommendDao
				.queryNoticeByUser(account, size, category);
		BbsPostDO post = new BbsPostDO();
		BbsUserProfilerDO profiler = new BbsUserProfilerDO();
		for (BbsPostNoticeRecommend notice : listNot) {
			NoticeDto nd = new NoticeDto();
			nd.setNotice(notice);
			post = bbsPostDAO.queryPostById(notice.getContentId());
			if (post != null) {
				nd.setPost(post);
			}
			profiler = bbsUserProfilerDao.queryUserByAccount(account);
			if (profiler != null) {
				nd.setProfiler(profiler);
			} else {
				CompanyAccount ca = companyAccountDao
						.queryAccountByAccount(account);
				if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
					profiler.setNickname(ca.getContact());
				}
			}
			list.add(nd);
		}
		return list;
	}

	@Override
	public Integer insertNoticeOrRecomend(BbsPostNoticeRecommend bbs) {
		// 判断是否已经推荐或关注
		Integer i = bbsPostNoticeRecommendDao.querySimpleNoOrRem(bbs);
		// 关注数或推荐数
		Integer no = 0;
		if (i > 0) {
			i = -1;
		} else {
			if (bbs.getState()==null) {
				bbs.setState(0);
			}
			i = bbsPostNoticeRecommendDao.insertNoticeOrRecomend(bbs);
			// 1表示问答，2表示帖子
			if (i > 0 && bbs.getCategory()==4 || (bbs.getCategory() > -1 && bbs.getCategory() < 2)) {
				// 动态
				BbsPostTrends trends = new BbsPostTrends();
				trends.setCompanyId(bbs.getCompanyId());
				trends.setBbsPostId(bbs.getContentId());
				// 改变帖子或问答的关注数或推荐数
				BbsPostDO post = bbsPostDAO.queryPostById(bbs.getContentId());
				if (bbs.getType() == 1) {
					// 关注数
					if (post != null && post.getNoticeCount() != null) {
						no = post.getNoticeCount() + 1;
					} else {
						no = 1;
					}
					bbsPostDAO.updateNoticeCount(bbs.getContentId(), no);
					if (bbs.getCategory() == 0) {
						trends.setAction("关注问题");
					} else {
						trends.setAction("关注帖子");
					}
				} else {
					// 推荐数
					if (post != null && post.getRecommendCount() != null) {
						no = post.getRecommendCount() + 1;
					} else {
						no = 1;
					}
					bbsPostDAO.updateRecommendCount(bbs.getContentId(), no);
					if (bbs.getCategory() == 0) {
						trends.setAction("推荐问题");
					} else {
						trends.setAction("推荐帖子");
					}
				}
				bbsPostTrendsDao.insertBbsPostTrends(trends);
			}
			// 关注标签
			if (i > 0 && bbs.getCategory() == 3) {
				// 该标签的信息
				BbsPostTags tag = bbsPostTagsDao.queryTagById(bbs
						.getContentId());
				if (tag != null && tag.getNoticeCount() != 0) {
					no = tag.getNoticeCount() + 1;
				} else {
					no = 1;
				}
				// 更新标签的关注数
				tag.setNoticeCount(no);
				bbsPostTagsDao.updateTag(tag);
			}
			// 关注个人主页
			if (i > 0 && bbs.getCategory() == 2) {
				no = i;
			}
		}
		return no;
	}

	@Override
	public Integer countNumbyCompanyId(BbsPostNoticeRecommend bbs) {
		return bbsPostNoticeRecommendDao.countNumbyCompanyId(bbs);
	}

	@Override
	public Integer countNumByContentId(BbsPostNoticeRecommend bbs) {
		return bbsPostNoticeRecommendDao.countNumByContentId(bbs);
	}

	@Override
	public List<BbsPostNoticeRecommend> queryNoticeByUser(String account,
			Integer category, Integer size) {
		return bbsPostNoticeRecommendDao.queryNoticeByUser(account, size,
				category);
	}

	@Override
	public PageDto<PostDto> pageNotice(String account, Integer category,
			PageDto<PostDto> page) {
		List<BbsPostNoticeRecommend> list = bbsPostNoticeRecommendDao
				.queryListNotice(account, category, page);
		List<PostDto> nlist = new ArrayList<PostDto>();
		for (BbsPostNoticeRecommend obj : list) {
			PostDto dto = new PostDto();
			BbsPostDO bp = bbsPostDAO.queryPostById(obj.getContentId());
			if (bp == null) {
				continue;
			}
			bp.setContent(Jsoup.clean(bp.getContent(), Whitelist.none()));
			dto.setPost(bp);
			BbsPostReplyDO reply = bbsPostReplyDao.queryLatestReplyByPostId(bp.getId());
			if (reply != null) {
				BbsUserProfilerDO profiler = bbsUserProfilerDao.queryUserByCompanyId(reply.getCompanyId());
				if (profiler==null||profiler.getCompanyId()==null) {
					continue;
				}
				if (profiler != null && StringUtils.isNotEmpty(profiler.getNickname())) {
					dto.setReplyName(profiler.getNickname());
				} else {
					CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(profiler.getCompanyId());
					if (ca != null && StringUtils.isNotEmpty(ca.getContact())) {
						dto.setReplyName(ca.getContact());
					}
				}
			}
			nlist.add(dto);
		}
		page.setRecords(nlist);
		page.setTotalRecords(bbsPostNoticeRecommendDao.queryListNoticeCount(account, category));
		return page;
	}

	@Override
	public List<BbsPostTags> queryListForMyTags(String account) {
		if (StringUtils.isEmpty(account)) {
			return null;
		}
		List<BbsPostNoticeRecommend> list = queryNoticeByUser(account, CATEGORY_TAG, 100);
		List<BbsPostTags> nlist =new ArrayList<BbsPostTags>();
		for (BbsPostNoticeRecommend obj:list) {
			BbsPostTags bpt = bbsPostTagsDao.queryTagById(obj.getContentId());
			if(bpt!=null){
				BbsPostCategory bpc = bbsPostCategoryService.querySimpleCategoryById(bpt.getCategory());
				if (bpc!=null) {
					bpt.setTagName(bpt.getTagName()+"("+bpc.getName()+")");
				}
				nlist.add(bpt);
			}
		}
		
		return nlist;
	}
	
	@Override
	public List<BbsPostTags> queryListForMyFollowSearch(String account) {
		if (StringUtils.isEmpty(account)) {
			return null;
		}
		List<BbsPostNoticeRecommend> list = queryNoticeByUser(account, CATEGORY_TAG, 10);
		List<BbsPostTags> nlist =new ArrayList<BbsPostTags>();
		for (BbsPostNoticeRecommend obj:list) {
			BbsPostTags bpt = bbsPostTagsDao.queryTagById(obj.getContentId());
			nlist.add(bpt);
		}
		
		return nlist;
	}

	@Override
	public Integer deleteTag(String account, Integer contentId) {
		if(StringUtils.isEmpty(account)||contentId ==null){
			return 0;
		}
		// 删除该标签 state 为1
		return bbsPostNoticeRecommendDao.updateStateToDel(account, contentId, CATEGORY_TAG);
	}

	@Override
	public List<BbsPostDO> queryNoticeByAccount(String account, Integer category, Integer size) {
		List<BbsPostNoticeRecommend> list=bbsPostNoticeRecommendDao.queryNoticeByUser(account, size, category);
		List<BbsPostDO> listPost=new ArrayList<BbsPostDO>();
		BbsPostDO post=new BbsPostDO();
		for(BbsPostNoticeRecommend notice:list){
			post=bbsPostDAO.queryPostById(notice.getContentId());
			if(post!=null){
				listPost.add(post);
			}
		}
		return listPost;
	}
}
