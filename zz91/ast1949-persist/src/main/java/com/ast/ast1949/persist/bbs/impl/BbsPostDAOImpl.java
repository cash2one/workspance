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
import com.ast.ast1949.dto.bbs.PostDto;
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
	public List<PostDto> queryPostByCategory(Integer categoryId,
			PageDto<PostDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("categoryId", categoryId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByCategory"), root);
	}

	@Override
	public Integer queryPostByCategoryCount(Integer categoryId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("categoryId", categoryId);
		return (Integer) getSqlMapClientTemplate().queryForObject("bbsPost.queryPostByCategoryCount", root);
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
			PageDto<PostDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("admin", account);
		root.put("post", post);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByAdmin"), root);
	}

	@Override
	public Integer queryPostByAdminCount(String account, BbsPostDO post) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("admin", account);
		root.put("post", post);
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
	public List<BbsPostDO> queryPostByUser(String account, String checkStatus,
			String isDel, PageDto<BbsPostDO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("isDel", isDel);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPostByUser"), root);
	}

	@Override
	public Integer queryPostByUserCount(String account, String checkStatus,
			String isDel) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("isDel", isDel);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryPostByUserCount"), root);
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
	public List<BbsPostDO> query24HourHot(String targetDate, Integer size) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("gmtTarget", targetDate);
		root.put("size", size);
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

}
