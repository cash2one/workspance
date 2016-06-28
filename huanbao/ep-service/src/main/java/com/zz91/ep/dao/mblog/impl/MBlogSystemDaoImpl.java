package com.zz91.ep.dao.mblog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.mblog.MBlogSystemDao;
import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogSystemDto;
@Repository("mblogSystemDao")
public class MBlogSystemDaoImpl extends BaseDao implements MBlogSystemDao {
	
	final static String SQL_PREFIX="mblogSystem"; 
	
	@Override
	public Integer insert(MBlogSystem system) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"),system);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogSystem> queryById(Integer toId, String type, String isRead,Integer start,Integer size) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("toId", toId);
		root.put("type", type);
		root.put("isRead", isRead);
		root.put("start", start);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryById"),root);
	}

	@Override
	public Integer queryCountById(Integer toId, String type, String isRead) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("toId", toId);
		root.put("type", type);
		root.put("isRead", isRead);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountById"),root);
	}

	@Override
	public Integer updateIsReadStatus(Integer id) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateIsReadStatus"),id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogSystemDto> querySystemById(Integer toId, String type,
			String isRead, PageDto<MBlogSystemDto> page) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("toId", toId);
		root.put("type", type);
		root.put("isRead", isRead);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySystemById"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogSystem> querySystemByisReadAndType(Integer toId,
			String type, String isRead) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("toId", toId);
		root.put("type", type);
		root.put("isRead", isRead);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySystemByisReadAndType"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogSystem> queryAnteAndCountByfromId(Integer fromId,Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("fromId",fromId);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAnteAndCountByfromId"),root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MBlogSystem> queryMessageByConditions(Integer toId,String type,String isRead,PageDto<MBlogSystem> page){
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("toId", toId);
		root.put("type", type);
		root.put("isRead", isRead);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryMessageByConditions"),root);
	}
	
	
}
