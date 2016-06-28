package com.ast.ast1949.persist.photo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.photo.PhotoAbum;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.photo.PhotoAbumDao;

@Component("photoAbumDao")
public class PhotoAbumDaoImpl extends BaseDaoSupport implements PhotoAbumDao {

	final static String SQL_FIX = "photoAbum";

	@Override
	public Integer insert(PhotoAbum photoAbum) {

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), photoAbum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhotoAbum> queryPhotoAbumList(PageDto<PhotoAbum> page,
			Integer albumId, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("albumId", albumId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryPhotoAbumList"), map);
	}

	@Override
	public Integer queryPhotoAbumListCount(Integer albumId, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("albumId", albumId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryPhotoAbumListCount"), map);
	}

	@Override
	public Integer delPhotoAbum(Integer id) {

		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "delPhotoAbum"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhotoAbum> queryList(Integer albumId, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("albumId", albumId);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhotoAbum> queryPagePhotoAbum(PageDto<PhotoAbum> page,
			Integer albumId, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("albumId", albumId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryPagePhotoAbum"), map);
	}

	@Override
	public Integer queryPagePhotoAbumCount(Integer albumId, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("albumId", albumId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryPagePhotoAbumCount"), map);
	}

	@Override
	public PhotoAbum queryPhotoAbum(Integer albumId, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("albumId", albumId);
		return (PhotoAbum) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryPhotoAbum"), map);

	}

	@Override
	public PhotoAbum queryPhotoAbumById(Integer id) {
		return (PhotoAbum) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryPhotoAbumById"), id);
	}

	@Override
	public Integer updateIsMarkById(Integer id) {
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateIsMarkById"), id);
	}

	@Override
	public void updateCompanyIdById(Integer id, Integer companyId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		map.put("companyId", companyId);
		getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateComponyIdById"), map);
	}

}
