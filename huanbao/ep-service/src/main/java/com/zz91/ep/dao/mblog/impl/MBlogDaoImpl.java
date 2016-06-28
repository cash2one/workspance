package com.zz91.ep.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.mblog.MBlogDao;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;
@Repository("mblogDao")
public class MBlogDaoImpl extends BaseDao implements MBlogDao {
	
	final static String SQL_PREFIX="mblog";
	
	@Override
	public Integer delete(Integer id) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateIsDeleteStatus"),id);
	}

	@Override
	public Integer insert(MBlog mBlog) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"),mBlog);
	}

	@Override
	public Integer queryAllMBlogCountById(Integer infoId) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAllMBlogCountById"),infoId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogDto> queryAllmBlogById(Integer infoId, PageDto<MBlogDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllmBlogById"),root);
	}

	@Override
	public List<MBlog> queryByContent(String Content, PageDto<MBlog> page) {
		return null;
	}

	@Override
	public Integer queryCountByContent(String Content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MBlog queryOneById(Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return (MBlog) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneById"),root);
	}

	@Override
	public Integer updateSentCount(Integer id) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSentCount"),id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlog> queryAllmBlogByInfoId(Integer infoId,Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllmBlogByInfoId"),root);
	}

	@Override
	public Integer updateDiscussCount(Integer id) {
		
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDiscussCount"),id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlog> queryTopicTitle(Integer size) {
		Map<String,Object> root= new HashMap<String, Object>();
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTopicTitle"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogDto> queryMyFollowBlog(Integer infoId,Integer groupId,PageDto<MBlogDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("groupId", groupId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryMyFollowBlog"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogDto> queryAllBlog(PageDto<MBlogDto> page) {
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllBlog"),root);
	}

	@Override
	public Integer queryCountBlogByTime(String gmtCreated) {
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("gmtCreated", gmtCreated);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountBlogByTime"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogDto> queryMyBlog(Integer infoId,PageDto<MBlogDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryMyBlog"),root);
	}

	@Override
	public MBlog querymblogByTitle(String title) {
		Map<String, Object> root=new  HashMap<String, Object>();
		root.put("title", title);
		return (MBlog) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querymblogByTitle"),root);
	}

	@Override
	public Integer querytopicCountByInfo(String title) {
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("title", title);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querytopicCountByInfo"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogDto> querytopicbyInfo(String title,Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("title", title);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querytopicbyInfo"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlog> queryInfoBytopIcTitle(String title, Integer size) {
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("title", title);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryInfoBytopIcTitle"),root);
	}

	@Override
	public MBlogDto queryOneBymblogId(Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return (MBlogDto)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneBymblogId"),root);
	}

	@Override
	public Integer updateMblogIsDeleteStatus(Integer id, String isDelete) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("isDelete", isDelete);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMblogIsDeleteStatus"),root);
	}

	@Override
	public Integer queryPhotoCountByInfo(Integer infoId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPhotoCountByInfo"),root);
	}

	@Override
	public Integer queryMyFollowBlogCount(Integer infoId, Integer groupId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("groupId", groupId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMyFollowBlogCount"),root);
	}

	@Override
	public Integer queryMyBlogCount(Integer infoId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMyBlogCount"),root);
	}

	@Override
	public Integer queryAllBlogCount() {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAllBlogCount"));
	}

}
