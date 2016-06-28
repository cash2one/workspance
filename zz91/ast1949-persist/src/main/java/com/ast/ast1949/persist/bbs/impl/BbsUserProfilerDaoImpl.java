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

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsUserProfilerDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsUserProfilerDao;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
@Component("bbsUserProfilerDao")
public class BbsUserProfilerDaoImpl extends BaseDaoSupport implements BbsUserProfilerDao {

	final static String SQL_PREFIX = "bbsUserProfiler";
	
	@Override
	public Integer queryIntegralByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryIntegralByAccount"), account);
	}

	@Override
	public Integer countProfilerByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countProfilerByAccount"), account);
	}

	@Override
	public Integer insertProfiler(BbsUserProfilerDO profiler) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertProfiler"), profiler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsUserProfilerDO> queryTopByPostNum(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryTopByPostNum"), size);
	}

	@Override
	public Integer updatePostNumber(String account,Integer postNumber) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("postNumber", postNumber);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updatePostNumber"), root);
	}

	@Override
	public BbsUserProfilerDO queryProfilerOfAccount(String account) {
		return (BbsUserProfilerDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryProfilerOfAccount"), account);
	}
	
	@Override
	public Integer updateReplyCount(Integer replyCount, String account) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("replyCount", replyCount);
		root.put("account", account);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateReplyCount"),root);
	}

	@Override
	public Integer countUserProfilerByAccount(String accountName) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUserProfilerByAccount"),accountName);
	}

	@Override
	public BbsUserProfilerDO queryUserByAccount(String accountName) {
		return (BbsUserProfilerDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryUserByAccount"), accountName);
	}

	@Override
	public BbsUserProfilerDO queryUserByCompanyId(Integer companyId) {
		return (BbsUserProfilerDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryUserByCompanyId"),companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsUserProfilerDO> queryNewUser(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryNewUser"),size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsUserProfilerDO> queryByAdmin(BbsUserProfilerDto searchDto,PageDto<BbsUserProfilerDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryByAdmin"), map);
	}

	@Override
	public Integer queryCountByAdmin(BbsUserProfilerDto searchDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCountByAdmin"), map);
	}
	
	@Override
	public Integer queryRank(Integer integral){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryRank"), integral);
	}

}
