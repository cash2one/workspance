package com.zz91.ep.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.mblog.MBlogGroupDao;
import com.zz91.ep.domain.mblog.MBlogGroup;
@Repository("mblogGroupDao")
public class MBlogGroupDaoImpl extends BaseDao implements MBlogGroupDao {
	
	final static String SQL_PREFIX="mblogGroup";
	
	@Override
	public Integer delete(Integer id, String isDelete) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("id", id);
		root.put("isDelete",isDelete);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateIsDeleteStatus"),root);
	}

	@Override
	public Integer insert(MBlogGroup group) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"),group);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogGroup> queryById(Integer infoId) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("infoId", infoId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllById"),root);
	}

	@Override
	public MBlogGroup queryOneByConditions(Integer infoId, String groupName,Integer id) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("groupName", groupName);
		root.put("id", id);
		return (MBlogGroup) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneByConditions"),root);
	}

}
