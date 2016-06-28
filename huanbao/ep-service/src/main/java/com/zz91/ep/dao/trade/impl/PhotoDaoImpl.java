/*
 * 文件名称：PhotoDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-18 下午2:36:38
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.PhotoDao;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.PhotoAlbumDto;

/**
 * 项目名称：中国环保网 模块编号：数据操作DAO层 模块描述：相片操作类 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　　 2012-06-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Component("photoDao")
public class PhotoDaoImpl extends BaseDao implements PhotoDao {

	final static String SQL_PREFIX = "photo";

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> queryPhotoByTargetType(String targetType, Integer targetId,
			Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("targetType", targetType);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				buildId(SQL_PREFIX, "queryPhotoByTargetType"), root);
	}
	
	@Override
	public Photo queryPhotoByTypeAndId(String type, Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("id", id);
		return (Photo) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPhotoByTypeAndId"), map);
	}

	@Override
	public Integer insertPhoto(Photo photo) {
		return (Integer) getSqlMapClientTemplate().insert(
				buildId(SQL_PREFIX, "insertPhoto"), photo);
	}

	@Override
	public Integer queryPhotosByCidCount(Integer cid,Integer albumId, Integer queryTarget) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("albumId", albumId);
		root.put("targetId", queryTarget);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryPhotosByCidCount"), root);
	}

	@Override
	public Integer updatePhotoTargetIdById(Integer id, Integer targetId, String targetType) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("targetId", targetId);
		root.put("targetType", targetType);
		return getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "updatePhotoTargetIdById"), root);
	}

	@Override
	public Integer deletePhotoTargetIdById(String type, Integer id) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("type", type);
		root.put("id", id);
		return (Integer) getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "deletePhotoTargetIdById"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> queryPhotosByCid(Integer cid,
			PageDto<Photo> page,Integer albumId, Integer queryTarget) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		root.put("cid", cid);
		root.put("albumId", albumId);
		root.put("targetId", queryTarget);
		return getSqlMapClientTemplate().queryForList(
				buildId(SQL_PREFIX, "queryPhotosByCid"), root);
	}

	@Override
	public Integer deletePhotoById(Integer id) {
		return getSqlMapClientTemplate().delete(
				buildId(SQL_PREFIX, "deletePhotoById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, PhotoAlbumDto> queryPhotoByCid(Integer cid)  {
		
		return getSqlMapClientTemplate().queryForMap(buildId(SQL_PREFIX, "queryPhotoByCid"),cid,"photoAlbum.id");
	}
	

    
	@Override
	public Integer queryPhotosByPalidCount(Integer palid,Integer cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("palid", palid);
		map.put("cid", cid);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryPhotosByPalidCount"), map);
	}
	
	@Override
	public Integer deletePhotoByPalId(Integer palid)  {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deletePhotoByPalId"), palid);
	}

	@Override
	public Integer updatePhotoAlbumId(Integer albumId, Integer pid,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("albumId", albumId);
		map.put("pid", pid);
		map.put("type", type);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePhotoAlbumId"),map);
	}

	@Override
	public String queryPathByAlbumId(Integer cid, Integer albumId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("albumId", albumId);
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPathByAlbumId"),map);
	}

	/* (non-Javadoc)
	 * @see com.zz91.ep.dao.trade.PhotoDao#updateAlbum(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer updateAlbum(Integer oldAlbum, Integer newAlbum, Integer cid) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("oldAlbum", oldAlbum);
		root.put("newAlbum", newAlbum);
		root.put("cid", cid);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateAlbum"), root);
	}
	
	@Override
	public Photo queryPhotoById(Integer id) {
		// TODO Auto-generated method stub
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		return (Photo) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPhotoById"), root);
	}

	@Override
	public Integer queryPhotoCountByTargetType(String targetType, Integer targetId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("targetType", targetType);
		
		return (Integer) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryPhotoCountByTargetType"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> queryPhotoListByTypeAndId(String type, Integer id, String checkStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("id", id);
		map.put("checkStatus", checkStatus);
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPhotoListByTypeAndId"), map);
	}

	@Override
	public Integer updateCheckStatus(Integer id, String checkStatus) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCheckStatus"), root);
	}
}
