/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 9, 2010 by Rolyer.
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostType;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostSearchDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsPostDAO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Component("bbsPostDAO")
public class BbsPostDAOImpl extends BaseDaoSupport implements BbsPostDAO {

	final static String SQL_PREFIX="bbsPost";

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryRecentByAccount(String account, Integer maxSize) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryRecentByAccount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> queryPostByCategory(Integer categoryId,Integer searchType,
			PageDto<PostDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("categoryId", categoryId);
		root.put("searchType", searchType);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByCategory"), root);
	}

	@Override
	public Integer queryPostByCategoryCount(Integer categoryId,Integer searchType) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("categoryId", categoryId);
		root.put("searchType", searchType);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPostByCategoryCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> queryPostBySearch(String keywords,
			PageDto<PostDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("keywords", keywords);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList("bbsPost.queryPostBySearch", root);
	}

	@Override
	public Integer queryPostBySearchCount(String keywords) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("keywords", keywords);
		return (Integer) getSqlMapClientTemplate().queryForObject("bbsPost.queryPostBySearchCount", root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> querySimplePostByCategory(Integer categoryId,
			Integer maxSize) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("categoryId", categoryId);
		root.put("max", maxSize);
		return getSqlMapClientTemplate().queryForList("bbsPost.querySimplePostByCategory", root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryPostByAdmin(String account, BbsPostDO post,
			PageDto<PostDto> page,String selectTime,String from,String to) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("admin", account);
		root.put("post", post);
		root.put("page", page);
		root.put("selectTime", selectTime);
		root.put("from", from);
		root.put("to", to);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByAdmin"), root);
	}

	@Override
	public Integer queryPostByAdminCount(String account, BbsPostDO post,String selectTime,String from,String to) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("admin", account);
		root.put("post", post);
		root.put("selectTime", selectTime);
		root.put("from", from);
		root.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPostByAdminCount"), root);
	}

	@Override
	public Integer updateCheckStatus(Integer id, String checkStatus,
			String admin) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("admin", admin);
		root.put("checkStatus", checkStatus);
		root.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCheckStatus"), root);
	}

	@Override
	public Integer updateIsDel(Integer id, String isDel) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("isDel", isDel);
		root.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsDel"), root);
	}
	
	public Integer deleteBbsPost(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteBbsPost"), id);
	}

	@Override
	public Integer insertPost(BbsPostDO post) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertPost"), post);
	}

	@Override
	public BbsPostDO queryPostById(Integer id) {
		return (BbsPostDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPostById"),id);
	}

	@Override
	public Integer updatePostByAdmin(BbsPostDO post) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updatePostByAdmin"), post);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryPostWithContentByType(String type, Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postType", type);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostWithContentByType"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> queryPostByType(String type, Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postType", type);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByType"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryPostOrderBy(String sort, String dir,
			Integer size) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("sort", sort);
		root.put("dir", dir);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostOrderBy"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryPostByViewLog(long targetDate, Integer size) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("gmtTarget", targetDate);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByViewLog"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryPostByUser(BbsPostSearchDto bbsPostSearchDto, PageDto<BbsPostDO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("bbsPostSearchDto", bbsPostSearchDto);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByUser"), root);
	}

	@Override
	public Integer queryPostByUserCount(BbsPostSearchDto bbsPostSearchDto) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("bbsPostSearchDto", bbsPostSearchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPostByUserCount"), root);
	}
	@Override
	public Integer queryMyBbsCount(BbsPostSearchDto bbsPostSearchDto){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("bbsPostSearchDto", bbsPostSearchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMyBbsCount"), root);
	}

	@Override
	public Integer updatePostByUser(BbsPostDO post) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updatePostByUser"), post);
	}

	@Override
	public PostDto queryPostWithProfileById(Integer id) {
		
		return (PostDto) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPostWithProfileById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryPostOrderVisitCount(String account, Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostOrderVisitCount"), root);
	}

	@Override
	public String queryContent(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryContent"), id);
	}

	@Override
	public Integer updateContent(Integer id, String content) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("content", content);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateContent"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostType> queryPostTypeChild(String parentCode) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostTypeChild"), parentCode);
	}

	@Override
	public Integer queryPostTypeChildCount(String parentCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPostTypeChildCount"), parentCode);
	}

	@Override
	public String queryPostTypeName(Integer postType) {
		
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPostTypeName"), postType);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryTheNewsPost(String account, Integer maxSize) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querytheNewsPost"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> queryPostByKey(String title, Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("title", title);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByKey"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> queryMorePostByType(String type,
			PageDto<PostDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postType", type);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryMorePostByType"), root);

	}

	@Override
	public Integer queryMorePostByTypeCount(String postType) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postType", postType);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMorePostByTypeCount"), root);
	}

	@Override
	public Integer updateCheckStatusForFront(Integer id, String checkStatus) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("checkStatus", checkStatus);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCheckStatusForFront"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> query24HourHot(String targetDate, Integer size,Integer categoryId) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("gmtTarget", targetDate);
		root.put("size", size);
		root.put("categoryId", categoryId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "query24HourHot"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryMostViewedPost(String targetDate,Integer size) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("gmtTarget", targetDate);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryMostViewedPost"), root);
	}

	@Override
	public BbsPostDO queryDownBbsPostById(Integer id) {
		return (BbsPostDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryDownBbsPostById"),id);
	}

	@Override
	public BbsPostDO queryOnBbsPostById(Integer id) {
		return (BbsPostDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryOnBbsPostById"),id);
	}

	@Override
	public Integer queryCountPostByTime(String fromDate, String toDate,
			Integer categoryId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("fromDate", fromDate);
		root.put("toDate", toDate);
		root.put("categoryId", categoryId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCountPostByTime"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> query24HourHotByAnswer(String targetDate, Integer size,
			Integer categoryId) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("gmtTarget", targetDate);
		root.put("size", size);
		root.put("categoryId", categoryId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX,"query24HourHotByAnswer"),root);
	}

	@Override
	public Integer recommendPostById(Integer postId) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "recommendPostById"), postId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> bbsPostCompany(PageDto<CompanyDto> page){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "bbsPostCompany"),root);
	}
	
	@Override
	public Integer bbsPostCompanyCount(){
	     return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "bbsPostCompanyCount"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryBbsPostByNoticeAReplyTime(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBbsPostByNoticeAReplyTime"), size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryBbsPostByAssistId(Integer assistId, Integer size) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("assistId", assistId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBbsPostByAssistId"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryWaitAnswerBbsPost(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryWaitAnswerBbsPost"), size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryHotBbsPost(Integer size,Integer categoryId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("size", size);
		map.put("categoryId", categoryId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryHotBbsPost"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryListForQA(PageDto<BbsPostDO> page, String date) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		map.put("date", date);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListForQA"), map);
	}

	@Override
	public Integer countListForQA(String date) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countListForQA"), date);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryListForWait(PageDto<BbsPostDO> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListForWait"), map);
	}

	@Override
	public Integer countListForWait() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countListForWait"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryListForNewQA(PageDto<BbsPostDO> page, String date) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("page", page);
        map.put("date", date);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListForNewQA"), map);
	}

	@Override
	public Integer countListForNewQA(String date) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countListForNewQA"), date);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryListForHotNotice(PageDto<BbsPostDO> page) {
		 Map<String,Object> map=new HashMap<String,Object>();
	     map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListForHotNotice"), map);
	}

	@Override
	public Integer countListForHotNotice() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countListForHotNotice"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryAllListBbsPost(PageDto<BbsPostDO> page, Integer assistId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		map.put("assistId", assistId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllListBbsPost"), map);
	}
	@Override
	public Integer countBbsPostByassistId(Integer assistId) {
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countBbsPostByassistId"), assistId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryToutiaoByPostType(PageDto<BbsPostDO> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryToutiaoByPostType"), map);
	}

	@Override
	public Integer countToutiaoByPostType() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countToutiaoByPostType"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> queryWaitAnswerByReplyCount(PageDto<PostDto> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryWaitAnswerByReplyCount"), map);
	}

	@Override
	public Integer countWaitAnswerByReplyCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countWaitAnswerByReplyCount"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDto> queryBbsPostByUser(Integer categoryId, String account, PageDto<PostDto> page,String keywords) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("categoryId", categoryId);
		map.put("account", account);
		map.put("page", page);
		map.put("keywords", keywords);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBbsPostByUser"), map);
	}

	@Override
	public Integer countBbsPostByUser(Integer categoryId, String account,String keywords) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("categoryId", categoryId);
		map.put("account", account);
		map.put("keywords", keywords);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countBbsPostByUser"), map);
	}

	@Override
	public void updateNoticeCount(Integer id, Integer noticeCount) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("id", id);
		map.put("noticeCount", noticeCount);
		getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateNoticeCount"), map);
	}

	@Override
	public void updateCollectCount(Integer id, Integer collectCount) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("id", id);
		map.put("collectCount", collectCount);
		getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCollectCount"), map);
	}

	@Override
	public void updateRecommendCount(Integer id, Integer recommendCount) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("id", id);
		map.put("recommendCount", recommendCount);
		getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateRecommendCount"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryListHotBbsPost(PageDto<BbsPostDO> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListHotBbsPost"), map);
	}

	@Override
	public Integer countListHotBbsPost() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countListHotBbsPost"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryBbsPostByCompanyId(Integer companyId, Integer categoryId, Integer size) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("categoryId", categoryId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBbsPostByCompanyId"), map);
	}

	@Override
	public Integer countBbsPostByCompanyId(Integer companyId, Integer categoryId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("categoryId", categoryId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countBbsPostByCompanyId"), map);
	}
	
	@Override
	public Integer countBbsByCompanyId(Integer companyId, Integer categoryId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("categoryId", categoryId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countBbsByCompanyId"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> ListBbsPostByCompanyId(Integer companyId, Integer categoryId, PageDto<BbsPostDO> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("categoryId", categoryId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "ListBbsPostByCompanyId"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryPostByNoticeAndView(Integer categoryId, Integer size) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("categoryId", categoryId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByNoticeAndView"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryNewPost(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryNewPost"), size);
	}

	@Override
	public Integer updateVisitedCountById(Integer visitCount, Integer id) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("visitCount", visitCount);
		map.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateVisitedCountById"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> pagePostNew(Integer cateGoryId,
			PageDto<BbsPostDO> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cateGoryId", cateGoryId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "pagePostNew"),map);
	}

	@Override
	public Integer pagePostNewCount(Integer cateGoryId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cateGoryId", cateGoryId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "pagePostNewCount"),map);
	}

	@Override
	public Integer changShangQuan(Integer id) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "changShangQuan"),id);
	}

}
