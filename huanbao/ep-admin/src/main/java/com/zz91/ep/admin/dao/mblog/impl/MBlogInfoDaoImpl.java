package com.zz91.ep.admin.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.mblog.MBlogInfoDao;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;
@Component("mblogInfoDao")
public class MBlogInfoDaoImpl extends BaseDao implements MBlogInfoDao {
	
	final static String SQL_PREFIX="mblogInfo";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogInfoDto> queryAllMblogInfo(MBlogInfo mBlogInfo,CompProfile compProfile,PageDto<MBlogInfoDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("mBlogInfo", mBlogInfo);
		root.put("compProfile", compProfile);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllMblogInfo"),root);
	}

	@Override
	public Integer queryAllMblogCountInfo(MBlogInfo mBlogInfo,CompProfile compProfile) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("mBlogInfo", mBlogInfo);
		root.put("compProfile", compProfile);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAllMblogCountInfo"),root);
	}

	@Override
	public Integer updateIsDeleteStatus(Integer infoId, String isDelete) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", infoId);
		root.put("isDelete", isDelete);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateIsDeleteStatus"),root);
	}

}
