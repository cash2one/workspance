package com.zz91.ep.admin.dao.mblog.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.mblog.MBlogSystemDao;
import com.zz91.ep.domain.mblog.MBlogSystem;

@Component("mblogSystemDao")
public class MBlogSystemDaoImpl extends BaseDao implements MBlogSystemDao {

	final static String SQL_PREFIX="mblogSystem";
	
	@Override
	public Integer insert(MBlogSystem mBlogSystem) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"),mBlogSystem);
	}

}
