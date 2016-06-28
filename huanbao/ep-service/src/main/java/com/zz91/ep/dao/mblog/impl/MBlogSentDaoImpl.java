package com.zz91.ep.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.mblog.MBlogSentDao;
import com.zz91.ep.domain.mblog.MBlogSent;
@Repository("mblogSentDao")
public class MBlogSentDaoImpl extends BaseDao implements MBlogSentDao{
	
	final static String SQL_PREFIX="mblogSent";
	@Override
	public Integer insert(MBlogSent sent) {
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"),sent);
	}

	@Override
	public Integer querymBlogSentCountById(Integer topId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("topId", topId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querymBlogSentCountById"),root);
	}

	@Override
	public MBlogSent querymBlogSentByMblogId(Integer mblogId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("mblogId", mblogId);
		return (MBlogSent) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querymBlogSentByMblogId"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogSent> querytopIdByinfoId(Integer infoId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querytopIdByinfoId"),root);
	}
   
}
