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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostType;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.bbs.BbsUserProfilerDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.datetime.DateUtil;
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
	private static final String CHECK_STATUS_TRUE="1";
	/**
	 * 未删除
	 */
	private static final String IS_DEL_FALSE="0";
	
	@Resource
	private BbsPostDAO bbsPostDAO;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private BbsUserProfilerDao bbsUserProfilerDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	
	@Override
	public Map<String, List<BbsPostDO>> queryRecentPostOfAccounts(
			Set<String> account) {
		Map<String, List<BbsPostDO>> map=new HashMap<String, List<BbsPostDO>>();
		for(String a:account){
			if(map.get(a)==null){
				map.put(a, bbsPostDAO.queryRecentByAccount(a, 3));
			}
		}
		return map;
	}

	@Override
	public PageDto<PostDto> pagePostByCategory(Integer categoryId,
			PageDto<PostDto> page) {
		page.setRecords(bbsPostDAO.queryPostByCategory(categoryId, page));
		page.setTotalRecords(bbsPostDAO.queryPostByCategoryCount(categoryId));
		return page;
	}

	@Override
	public PageDto<PostDto> pagePostBySearch(String keywords,
			PageDto<PostDto> page) {
		List<PostDto> list=bbsPostDAO.queryPostBySearch(keywords, page);
		for(PostDto dto:list){
			dto.getPost().setContent(Jsoup.clean(dto.getPost().getContent(), Whitelist.none()));
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostDAO.queryPostBySearchCount(keywords));
		return page;
	}

	@Override
	public List<BbsPostDO> querySimplePostByCategory(Integer categoryId, Integer max) {
		if(max==null){
			max=10;
		}
		return bbsPostDAO.querySimplePostByCategory(categoryId,max);
	}

	@Override
	public PageDto<PostDto> pagePostByAdmin(String account, BbsPostDO post,PageDto<PostDto> page) {
		if(page.getSort()==null){
			page.setSort("id");
			page.setDir("desc");
		}
		List<PostDto> resultList=new ArrayList<PostDto>();
		List<BbsPostDO> list=bbsPostDAO.queryPostByAdmin(account, post, page);
		Map<Integer, Company> map=new HashMap<Integer, Company>();
		for(BbsPostDO p:list){
			PostDto dto=new PostDto();
			dto.setPost(p);
			if(p.getCompanyId()!=null && p.getCompanyId().intValue()>0){
				if(map.get(p.getCompanyId())==null){
					map.put(p.getCompanyId(), companyDAO.querySimpleCompanyById(p.getCompanyId()));
				}
			}
			if(map.get(p.getCompanyId())!=null){
				dto.setCompany(map.get(p.getCompanyId()));
				dto.setMembershipLabel(CategoryFacade.getInstance().getValue(dto.getCompany().getMembershipCode()));
			}else{
				dto.setCompany(new Company());
			}
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(bbsPostDAO.queryPostByAdminCount(account, post));
		return page;
	}

	@Override
	public Integer updateCheckStatus(Integer id, String checkStatus,
			String admin) {
		if(checkStatus==null){
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
		BbsPostDO post=bbsPostDAO.queryPostById(id);
		if(post!=null && post.getIntegral()==null){
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
		if(post.getIntegral()==null){
			post.setIntegral(0);
		}
//		post.setTags(TagsUtils.getInstance().arrangeTags(post.getTags()));
//		TagsUtils.getInstance().createTags(post.getTags());
		if(post.getContent()==null){
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
		if(size==null){
			size=5;
		}
		List<BbsPostDO> list=bbsPostDAO.queryPostWithContentByType(type, size);
		for(BbsPostDO post:list){
			if(post.getContent()==null){
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
		if(size==null){
			size=5;
		}
		return bbsPostDAO.queryPostByType(type, size);
	}

	@Override
	public List<BbsPostDO> queryNewestPost(Integer size) {
		if(size==null){
			size=6;
		}
		return bbsPostDAO.queryRecentByAccount(null, size);
	}

	@Override
	public List<BbsPostDO> queryMostViewedPost(Integer size) {
		if(size==null){
			size=10;
		}
		String targetDate=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -30), "yyyy-MM-dd");
		//return bbsPostDAO.queryPostOrderBy("visited_count","desc",size);
		return bbsPostDAO.queryMostViewedPost(targetDate, size);
	}

	@Override
	public List<BbsPostDO> query24HourHot(Integer size) {
		if(size==null){
			size=10;
		}
		//long targetDate=DateUtil.getTheDayZero(new Date(), -1);
		String targetDate=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -7), "yyyy-MM-dd");
		//return bbsPostDAO.queryPostByViewLog(targetDate, size);
		return bbsPostDAO.query24HourHot(targetDate, size);
	}

	@Override
	public Integer createPostByUser(BbsPostDO post, String membershipCode) {
		Assert.notNull(post, "the object post can not be null");
		Integer profilerId=bbsUserProfilerDao.countProfilerByAccount(post.getAccount());
		if(profilerId==null || profilerId.intValue()<=0){
			CompanyAccount account = companyAccountDao.queryAccountByAccount(post.getAccount());
			BbsUserProfilerDO profiler=new BbsUserProfilerDO();
			profiler.setCompanyId(account.getCompanyId());
			profiler.setAccount(account.getAccount());
			profiler.setNickname(account.getContact());
			profiler.setEmail(account.getEmail());
			profiler.setMobile(account.getMobile());
			profiler.setQq(account.getQq());
			
			profilerId=bbsUserProfilerDao.insertProfiler(profiler);
		}
		post.setBbsUserProfilerId(profilerId);
		post.setCheckStatus(MemberRuleFacade.getInstance().getValue(membershipCode, "new_help_bbs_check"));
		if(post.getCheckStatus()==null){
			post.setCheckStatus("0");
		}
		post.setIsDel("0");
		post.setPostTime(new Date());
		post.setReplyTime(new Date());
		post.setVisitedCount(0);
		post.setReplyCount(0);
		
		post.setTitle(Jsoup.clean(post.getTitle(), Whitelist.none()));
//		post.setContent(Jsoup.clean(post.getContent(), Whitelist.relaxed()));
		
		Integer id=bbsPostDAO.insertPost(post);
		if(id!=null && id.intValue()>0 && "1".equals(post.getCheckStatus())){
			bbsUserProfilerService.updatePostNumber(post.getAccount());
			scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(post
					.getCompanyId(), null, "get_post_bbs", null, id, null));
			
		}
		
		return id;
	}

	@Override
	public PageDto<BbsPostDO> pagePostByUser(String account, String checkStatus,
			String isDel, PageDto<BbsPostDO> page) {
		page.setSort("post_time");
		page.setDir("desc");
		page.setPageSize(10);
		
		page.setRecords(bbsPostDAO.queryPostByUser(account, checkStatus, isDel, page));
		page.setTotalRecords(bbsPostDAO.queryPostByUserCount(account, checkStatus, isDel));
		return page;
	}

	@Override
	public Integer updatePostByUser(BbsPostDO post, String membershipCode) {
		Assert.notNull(post.getId(), "the id can not be null");
		post.setCheckStatus(MemberRuleFacade.getInstance().getValue(membershipCode, "new_help_bbs_check"));
		if(post.getCheckStatus()==null || StringUtils.isEmpty(post.getCheckStatus())){
			post.setCheckStatus("0");
		}
		post.setTags(TagsUtils.getInstance().arrangeTags(post.getTags()));
		post.setTitle(Jsoup.clean(post.getTitle(), Whitelist.none()));
//		post.setContent(Jsoup.clean(post.getContent(), Whitelist.basicWithImages()));
		return bbsPostDAO.updatePostByUser(post);
	}

	@Override
	public PageDto<PostDto> pagePostBySearchEngine(String keywords, PageDto<PostDto> page) {
		
		if(page.getPageSize()==null || page.getPageSize().intValue()<=0){
			page.setPageSize(20);
		}

		if(page.getStartIndex()!=null && page.getStartIndex()>=10000){
			page.setStartIndex(10000);
		}

		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();

		List<PostDto> list=new ArrayList<PostDto>();
		try {
			sb.append("@(title,tags) ").append(keywords);

			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "post_time desc");
			
			SphinxResult res=cl.Query(sb.toString(), "huzhu");
			
			if(res==null){
				page.setTotalRecords(0);
			}else{
				page.setTotalRecords(res.totalFound);
				for ( int i=0; i<res.matches.length; i++ ){
					SphinxMatch info = res.matches[i];
					PostDto dto=bbsPostDAO.queryPostWithProfileById(Integer.valueOf(""+info.docId));
					if(dto!=null){
						dto.getPost().setContent(Jsoup.clean(dto.getPost().getContent(), Whitelist.none())); 
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
	public List<BbsPostDO> queryHotPost(String account, Integer size) {
		if(size==null || size.intValue()<=0){
			size=10;
		}
		return bbsPostDAO.queryPostOrderVisitCount(account, size);
	}

	@Override
	public String queryContent(Integer id) {
		if(id==null || id.intValue()<0){
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
		Integer i=null;
		for (BbsPostType m : list) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(m.getId()));
			i=bbsPostDAO.queryPostTypeChildCount(m.getCode());
			if(i!=null && i.intValue()>0){
				node.setLeaf(false);
			}else{
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
		if(!StringUtils.isNumber(postType)){
			return null;
		}
		Integer i=Integer.valueOf(postType);
		if(i<=0){
			return null;
		}
		return bbsPostDAO.queryPostTypeName(i);
	}
	
	

	@Override
	public PageDto<PostDto> pageTheNewsPost(Integer maxSize,PageDto<PostDto> page) {
		List<BbsPostDO> list=bbsPostDAO.queryTheNewsPost(null,maxSize);
		List<PostDto> listDto = new ArrayList<PostDto>();
		for (int i = 0; i < list.size(); i++) {
			PostDto dto  = new PostDto();
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
	public Integer countMyposted(String account, String checkStatus, String isDel) {
		return bbsPostDAO.queryPostByUserCount(account, checkStatus, isDel);
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

}
