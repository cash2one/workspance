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
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;
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
	public List<BbsPostReplyDto> queryReplyOfPost(Integer postId,
			PageDto<BbsPostReplyDto> page) {
		
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postId", postId);
		root.put("page", page);
		
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryReplyOfPost"), root);
	}

	@Override
	public Integer queryReplyOfPostCount(Integer postId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postId", postId);
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

	@Override
	public List<BbsPostDO> queryReplyByUser(String account, String checkStatus,
			PageDto<BbsPostDO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByUser"), root);
	}

	@Override
	public Integer queryReplyByUserCount(String account, String checkStatus) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryReplyByUserCount"), root);
	}

	@Override
	public Integer queryBbsPostByReplyId(Integer id) {
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryBbsPostByReplyId"),id);
	}

}
