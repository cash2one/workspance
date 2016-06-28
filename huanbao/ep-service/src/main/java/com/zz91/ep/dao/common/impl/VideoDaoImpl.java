package com.zz91.ep.dao.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.VideoDao;
import com.zz91.ep.domain.common.Video;

/**
 *	author:kongsj
 *	date:2013-8-3
 */
@Component("videoDao")
public class VideoDaoImpl extends BaseDao implements VideoDao{
	
	final static String SQL_PREFIX="video";

	@Override
	public Integer delete(Integer id) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "delete"), id);
	}

	@Override
	public Integer insert(Video video) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"), video);
	}

	@Override
	public Video queryByTypeAndId(Integer targetId, String targetType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetId", targetId);
		map.put("targetType", targetType);
		return (Video) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryByTypeAndId"), map);
	}

	@Override
	public Integer update(Video video) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "update"), video);
	}

}
