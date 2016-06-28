/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 11, 2010 by Rolyer.
 */
package com.ast.ast1949.service.bbs;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface BbsPostService {

	public Map<String, List<BbsPostDO>> queryRecentPostOfAccounts(Set<String> account);
	
	public PageDto<PostDto> pagePostByCategory(Integer categoryId, PageDto<PostDto> page);
	
	public PageDto<PostDto> pagePostBySearch(String keywords , PageDto<PostDto> page);
	
	public List<BbsPostDO> querySimplePostByCategory(Integer categoryId, Integer max);
	
	public PageDto<PostDto> pagePostByAdmin(String account, BbsPostDO post, PageDto<PostDto> page);
	
	public Integer updateCheckStatus(Integer id, String checkStatus, String admin);
	
	public Integer updateIsDel(Integer id, String isDel);
	
	public Integer deleteBbsPost(Integer id);
	
	public BbsPostDO queryPostById(Integer id);
	
	public Integer createPostByAdmin(BbsPostDO post,String admin);
	
	public Integer updatePostByAdmin(BbsPostDO post);
	
	public List<BbsPostDO> queryPostWithContentByType(String type, Integer size);
	
	public List<PostDto> queryPostByType(String type, Integer size);
	
	public List<BbsPostDO> queryNewestPost(Integer size);
	
	public List<BbsPostDO> queryMostViewedPost(Integer size);
	
	public List<BbsPostDO> query24HourHot(Integer size);
	
	public Integer createPostByUser(BbsPostDO post, String membershipCode);
	
	public PageDto<BbsPostDO> pagePostByUser(String account, String check_status, String is_del, PageDto<BbsPostDO> page);
	
	public Integer updatePostByUser(BbsPostDO post, String membershipCode);
	
	public PageDto<PostDto> pagePostBySearchEngine(String keywords, PageDto<PostDto> page);
	
	public List<BbsPostDO> queryHotPost(String account, Integer size);
	
	public String queryContent(Integer id);
	
	public Integer updateContent(Integer id, String content);
	
	public List<ExtTreeDto> queryPostTypechild(String parentCode);
	
	public String queryPostTypeName(String postType);
	
	 /******
	  * 互助首页，以及最终页的 “最新话题查询”
	  * @param page
	  * 分页
	  * @return
	  */
	public PageDto<PostDto> pageTheNewsPost(Integer maxSize , PageDto<PostDto> page);
	/****************
	 * 互助首页，更新 互助周报。
	 * @param type
	 * @return
	 */
	public PageDto<PostDto> pageMorePostByType(String type, PageDto<PostDto> page);
	
	public Integer countMyposted(String account, String checkStatus,String isDel);
	
	public Integer updateCheckStatusForFront(Integer id, String checkStatus);
	
	public BbsPostDO queryOnBbsPostById(Integer id);
	
	public BbsPostDO queryDownBbsPostById(Integer id);
}
