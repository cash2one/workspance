package com.zz91.ep.admin.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.mblog.MBlogCommentDao;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;
@Component("mblogCommentDao")
public class MBlogCommentDaoImpl extends BaseDao implements MBlogCommentDao {

	final static String SQL_PREFIX="mblogComment";
	
	@SuppressWarnings("unchecked")
	
	@Override
	public List<MBlogCommentDto> queryCommentByMblogId(Integer mblogId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("mblogId", mblogId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCommentByMblogId"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogCommentDto> queryAllMblogComment(MBlogComment comment,MBlogInfo info,
			PageDto<MBlogCommentDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("comment", comment);
		root.put("info", info);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllMblogComment"),root);
	}

	@Override
	public Integer queryAllMblogCommentCount() {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAllMblogCommentCount"));
	}

	@Override
	public Integer delete(Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "delete"),root);
	}

	@Override
	public Integer updateDeleteStatus(Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDeleteStatus"),root);
	}

}
