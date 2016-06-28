package com.zz91.ep.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.mblog.MBlogCommentDao;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;
@Repository("mblogCommentDao")
public class MBlogCommentDaoImpl extends BaseDao implements MBlogCommentDao {
	
	final static String SQL_PREFIX="mblogComment";
	
	@Override
	public Integer delete(Integer id, String isDelete) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("isDelete",isDelete);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateIsDeleteStstus"),root);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogCommentDto> queryCommentBymblogId(Integer mblogId,PageDto<MBlogCommentDto> page) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("mblogId", mblogId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCommentBymblogId"),root);
	}

	@Override
	public Integer queryCommentCountBymblogId(Integer mblogId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCommentCountBymblogId"),mblogId);
	}

	@Override
	public Integer sentComment(MBlogComment comment) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"),comment);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogCommentDto> queryMblogCommentBymblogId(Integer mblogId) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("mblogId", mblogId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryMblogCommentBymblogId"),root);
	}
	@Override
	public MBlogComment queryOneCommentById(Integer id) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("id", id);
		return (MBlogComment) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneCommentById"),root);
	}

}
