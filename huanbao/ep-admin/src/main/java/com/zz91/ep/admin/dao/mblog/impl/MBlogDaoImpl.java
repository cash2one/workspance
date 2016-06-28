package com.zz91.ep.admin.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.mblog.MBlogDao;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;
@Component("mblogDao")
public class MBlogDaoImpl extends BaseDao implements MBlogDao {

	final static String SQL_PREFIX="mblog";
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogDto> queryAllMBlog(MBlog mBlog,MBlogInfo info,PageDto<MBlogDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		root.put("mBlog", mBlog);
		root.put("info", info);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllMBlog"),root);
	}

	@Override
	public Integer queryMBlogCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMBlogCount"));
	}

	@Override
	public MBlog queryOneMBlogById(Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return (MBlog) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneMBlogById"),root);
	}

	@Override
	public MBlog queryoneMblog(Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return (MBlog) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneMBlog"),root);
	}

	@Override
	public Integer updateMBlog(MBlog mBlog) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("mBlog", mBlog);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMBlog"),root);
	}

	@Override
	public Integer delete(Integer id) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "delete"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogDto> queryAllTopic(MBlog mBlog, MBlogInfo info,
			PageDto<MBlogDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		root.put("mBlog", mBlog);
		root.put("info", info);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllTopic"),root);
	}

	@Override
	public Integer queryAllTopicCount() {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAllTopicCount"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogDto> queryTopicByTitle(String title) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("title", title);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTopicByTitle"),root);
	}

	@Override
	public Integer updateDelete(Integer id) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelete"),root);
	}

}
