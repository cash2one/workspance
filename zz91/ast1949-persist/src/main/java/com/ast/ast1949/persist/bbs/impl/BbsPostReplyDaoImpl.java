/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsPostZan;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
@Component("bbsPostReplyDao")
public class BbsPostReplyDaoImpl extends BaseDaoSupport implements BbsPostReplyDao {

	final static String SQL_PREFIX="bbsPostReply";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostReplyDto> queryReplyOfPost(Integer postId,Integer companyId,
			PageDto<BbsPostReplyDto> page) {
		
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postId", postId);
		root.put("companyId", companyId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryReplyOfPost"), root);
	}

	@Override
	public Integer queryReplyOfPostCount(Integer postId,Integer companyId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postId", postId);
		root.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryReplyOfPostCount"), root);
	}

	@Override
	public Integer createReplyByAdmin(BbsPostReplyDO reply) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "createReplyByAdmin"), reply);
	}

	@Override
	public Integer deleteByAdmin(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteByAdmin"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostReplyDO> queryReplyByAdmin(BbsPostReplyDO reply,
			PageDto<BbsPostReplyDO> page) {
		Map<String , Object> root=new HashMap<String, Object>();
		root.put("reply", reply);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByAdmin"), root);
	}

	@Override
	public Integer queryReplyByAdminCount(BbsPostReplyDO reply) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("reply", reply);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByAdminCount"), root);
	}

	@Override
	public Integer updateCheckStatus(Integer id, String checkstatus,
			String admin) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("checkStatus", checkstatus);
		root.put("admin", admin);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCheckStatus"), root);
	}

	@Override
	public Integer updateReplyByAdmin(BbsPostReplyDO reply) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateReplyByAdmin"), reply);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryReplyByUser(String account, String checkStatus,Integer bbsPostCategoryId,
			PageDto<BbsPostDO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("bbsPostCategoryId", bbsPostCategoryId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByUser"), root);
	}

	@Override
	public Integer queryReplyByUserCount(String account, String checkStatus,Integer bbsPostCategoryId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("bbsPostCategoryId", bbsPostCategoryId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByUserCount"), root);
	}

	@Override
	public Integer queryBbsPostByReplyId(Integer id) {
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryBbsPostByReplyId"),id);
	}
	
	@Override
	public Integer countBeReply(String account,Integer categoryId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("categoryId", categoryId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countBeReply"), map);
	}
	
	@Override
	public BbsPostReplyDO queryById(Integer id){
		return (BbsPostReplyDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"), id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostReplyDO> queryReplyByPostId(Integer bbsPostId,Integer size){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bbsPostId", bbsPostId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByPostId"), map);
	}
	
	@Override
	public BbsPostReplyDO queryLatestReplyByPostId(Integer bbsPostId){
		return (BbsPostReplyDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryLatestReplyByPostId"), bbsPostId);
	}
	
   @Override
   public Integer updateIsDel(Integer id, String isDel){
	   Map<String, Object> root=new HashMap<String, Object>();
		root.put("isDel", isDel);
		root.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsDel"), root);
	   
   }
   @SuppressWarnings("unchecked")
   @Override
   public List<BbsPostReplyDO> queryReplyByAccount(String account, String checkStatus,Integer bbsPostCategoryId,
		   PageDto<BbsPostReplyDO> page){
	   Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("bbsPostCategoryId", bbsPostCategoryId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByAccount"), root);
	   
   }
   @Override
   public  Integer updateReplyByUser(BbsPostReplyDO bbsPostReplyDO){
	   return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateReplyByUser"), bbsPostReplyDO);
   }
   
   @SuppressWarnings("unchecked")
	@Override
	public List<Integer> bbsPostReplyCompany(CompanyAccountSearchDto searchDto,PageDto<CompanyDto> page){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		root.put("searchDto",searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "bbsPostReplyCompany"),root);
	}
	
	@Override
	public Integer bbsPostReplyCompanyCount(CompanyAccountSearchDto searchDto){
		 Map<String, Object> root=new HashMap<String, Object>();
		 root.put("searchDto",searchDto);
	     return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "bbsPostReplyCompanyCount"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> receviceReplyCompany(CompanyAccountSearchDto searchDto,PageDto<CompanyDto> page){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		root.put("searchDto",searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "receviceReplyCompany"),root);
	}
	
	@Override
	public Integer receviceReplyCompanyCount(CompanyAccountSearchDto searchDto){
		 Map<String, Object> root=new HashMap<String, Object>();
		 root.put("searchDto",searchDto);
	     return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "receviceReplyCompanyCount"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostReplyDO> queryBestAnswerByViewCount(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBestAnswerByViewCount"),size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostReplyDto> queryReplyByReplyId(Integer replyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBestAnswerByViewCount"), replyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryBbsReplyByUser(String account,Integer categoryId, PageDto<PostDto> page,String keywords) {
		 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("account",account);
		 map.put("categoryId",categoryId);
		 map.put("page",page);
		 map.put("keywords", keywords);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBbsReplyByUser"), map);
	}

	@Override
	public Integer countBbsReplyByUser(String account, Integer categoryId,String keywords) {
		 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("account",account);
		 map.put("categoryId",categoryId);
		 map.put("keywords", keywords);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countBbsReplyByUser"), map);
	}

	@Override
	public Integer countReplyByCompanyId(Integer companyId, Integer categoryId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("categoryId", categoryId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countReplyByCompanyId"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostReplyDO> queryReplyByCompanyId(Integer categoryId, Integer companyId, Integer size) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("categoryId", categoryId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByCompanyId"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDO> queryReplyByUser(String account, Integer categoryId, PageDto<BbsPostDO> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("account", account);
		map.put("categoryId", categoryId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBbsReplyByUser"), map);
	}

	@Override
	public Integer countCompanyByZan(Integer companyId, Integer replyId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("replyId", replyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix("bbsPostZan", "countCompanyByZan"), map);
	}

	@Override
	public Integer insertZan(BbsPostZan bbsPostZan) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix("bbsPostZan", "insertZan"), bbsPostZan);
	}

	@Override
	public void updateZanCount(Integer id, Integer zanCount) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("id", id);
		map.put("zanCount", zanCount);
		getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateZanCount"), map);
	}
}
