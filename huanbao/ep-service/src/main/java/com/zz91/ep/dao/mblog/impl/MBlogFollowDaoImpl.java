package com.zz91.ep.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.mblog.MBlogFollowDao;
import com.zz91.ep.domain.mblog.MBlogFollow;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;
@Repository("mblogFollowDao")
public class MBlogFollowDaoImpl extends BaseDao implements MBlogFollowDao {
	
	final static String SQL_PREFIX="mblogFollow";
	
	@Override
	public Integer insert(MBlogFollow follow) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"),follow);
	}

	@Override
	public MBlogFollow queryByIdAndTargetId(Integer infoId, Integer targetId,String followStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("targetId", targetId);
		root.put("followStatus", followStatus);
		return (MBlogFollow) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryByIdAndTargetId"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogFollow> queryFansByConditions(Integer targetId,
			PageDto<MBlogFollow> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryFansByConditions"),root);
	}

	@Override
	public Integer queryFansCountByConditions(Integer targetId ,String type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("type", type);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryFansCountByConditions"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogFollow> queryFollowByConditions(Integer infoId,
			Integer groupId,String type,PageDto<MBlogInfoDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("groupId", groupId);
		root.put("type", type);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryFollowByConditions"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogFollow> queryFollowByIdAndType(Integer infoId,
			Integer type, PageDto<MBlogFollow> page) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("type", type);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryFollowByIdAndType"),root);
	}

	@Override
	public Integer queryFollowCountByIdAndType(Integer infoId, Integer type) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("type", type);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryFollowCountByIdAndType"),root);
	}
	@Override
	public Integer queryFollowCountByConditions(Integer infoId, Integer groupId,String type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("groupId", groupId);
		root.put("type", type);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryFollowCountByConditions"),root);
	}

	@Override
	public Integer update(Integer id, String followStatus,String type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("followStatus", followStatus);
		root.put("type", type);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "update"),root);
	}

	@Override
	public Integer updateTypeById(Integer id,String type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("type", type);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTypeById"),root);
	}

	@Override
	public Integer updateFollowGroup(Integer infoId, Integer targetId,
			Integer groupId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("targetId", targetId);
		root.put("groupId", groupId);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateFollowGroup"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogFollow> queryByInfoIdOrGroupId(Integer infoId, Integer groupId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId",infoId );
		root.put("groupId",groupId );
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryByInfoIdOrGroupId"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogFollow> queryFansByTargetId(Integer targetId,String type,Integer start,Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("start", start);
		root.put("size", size);
		root.put("type",type);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryFansByTargetId"),root);
	}

	@Override
	public Integer updateFollowStatus(String type, String followStatus,
			Integer infoId, Integer targetId,Integer groupId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("type", type);
		root.put("followStatus", followStatus);
		root.put("infoId", infoId);
		root.put("targetId", targetId);
		root.put("groupId", groupId);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateFollowStstus"),root);
	}

	@Override
	public Integer updateNoteNameById(Integer infoId, Integer targetId,
			String noteName) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("targetId", targetId);
		root.put("noteName", noteName);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateNoteNameById"),root);
	}

	@Override
	public MBlogFollow queryOneFollowById(Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return (MBlogFollow) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneFollowById"),root);
	}
}
