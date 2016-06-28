package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.PhotoAlbumDao;
import com.zz91.ep.domain.trade.PhotoAlbum;

@Component("photoAlbumDao")
public class PhotoAlbumDaoImpl extends BaseDao implements PhotoAlbumDao{
	
	final static String SQL_PREFIX="photoAlbum";
	
	@Override
	public Integer getAlbumNum(Integer cid) {
		
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getAlbumNum"), cid);
	}

	@Override
	public Integer updateAlbumName(Integer albumId,String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("albumId", albumId);
		map.put("name", name);
		return (Integer)getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateAlbumName"), map);
	}

	@Override
	public Integer deleteAlbumAndPhoto(Integer albumId) {
		
		return (Integer)getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteAlbumAndPhoto"),albumId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhotoAlbum> queryAlbumByCid(Integer cid,Integer albumId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("albumId", albumId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAlbumByCid"),map);
	}

	@Override
	public Integer insertAlbum(PhotoAlbum album) {
		
		return (Integer)getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertAlbum"), album);
	}

	@Override
	public String queryNameById(Integer albumId) {
		
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameById"),albumId);
	}
	

}
