package com.zz91.ep.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.mblog.MBlogInfoDao;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
@Repository("mblogInfoDao")
public class MBlogInfoDaoImpl extends BaseDao implements MBlogInfoDao {
	
    final static String SQL_PREFIX="mblogInfo";
    
	@Override
	public Integer insert(MBlogInfo mBlogInfo) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"),mBlogInfo);
	}

	@Override
	public MBlogInfo queryInfoById(Integer id) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return (MBlogInfo) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryInfoById"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogInfo> queryInfoByName(String name, PageDto<MBlogInfo> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("name", name);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, ""),root);
	}

	@Override
	public Integer queryInfoCountByName(String name) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("name", name);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, ""),root);
	}

	@Override
	public Integer update(MBlogInfo mBlogInfo) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "update"),mBlogInfo);
	}

	@Override
	public Integer updateIsDeleteStatus(String account, String isDelete) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("isDelete", isDelete);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateIsDeleteStatus"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogInfo> queryInfobyAreaCode(String areaCode,
			String provinceCode) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("areaCode", areaCode);
		root.put("provinceCode", provinceCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryInfobyAreaCode"),root);
	}

	@Override
	public MBlogInfo queryInfoByInfoIdorCid(Integer infoId,Integer cid) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("infoId", infoId);
		root.put("cid", cid);
		return (MBlogInfo) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryInfoByInfoIdorCid"),root);
	}

	@Override
	public MBlogInfo queryInfoByInfoByName(String name) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("name", name);
		return (MBlogInfo) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryInfoByInfoByName"),root);
	}

	@Override
	public MBlogInfo queryInfoByCid(Integer cid) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", cid);
		return (MBlogInfo) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryInfoByCid"),root);
	}
   
}
