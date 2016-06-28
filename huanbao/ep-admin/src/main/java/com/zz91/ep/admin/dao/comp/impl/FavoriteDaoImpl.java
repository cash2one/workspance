/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.comp.impl;

import org.springframework.stereotype.Repository;
import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.comp.FavoriteDao;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Repository("favoriteDao")
public class FavoriteDaoImpl extends BaseDao implements FavoriteDao {

    final static String SQL_PREFIX = "favorite";

//	@Override
//	public Integer createFavorite(Favorite favorite) {
//		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createFavorite"),favorite);
//	}

//	@Override
//	public Integer updateFavorite(Integer id,Integer uid, Short status) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("id", id);
//		root.put("uid", uid);
//		root.put("status", status);
//		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateFavorite"),root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Favorite> queryFavorites(Integer uid, Integer cid, Short type,
//			Short delStatus, PageDto<Favorite> page) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("uid", uid);
//		root.put("cid", cid);
//		root.put("type", type);
//		root.put("delStatus", delStatus);
//		root.put("page", page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryFavorites"),root);
//	}
	
//	@Override
//	public Integer queryFavoritesCount(Integer uid, Integer cid,
//			Short type, Short delStatus) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("uid", uid);
//		root.put("cid", cid);
//		root.put("type", type);
//		root.put("delStatus", delStatus);
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryFavoritesCount"),root);
//	}

}