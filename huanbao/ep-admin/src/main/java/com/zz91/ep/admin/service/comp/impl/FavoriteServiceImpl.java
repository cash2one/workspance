package com.zz91.ep.admin.service.comp.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.FavoriteDao;
import com.zz91.ep.admin.service.comp.FavoriteService;

@Component("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {

	@Resource
	private FavoriteDao favoriteDao;
	
//	@Override
//	public Integer createFavorite(Favorite favorite) {
//		return favoriteDao.createFavorite(favorite);
//	}

//	@Override
//	public PageDto<Favorite> queryFavorites(Integer uid, Integer cid, Short type,
//			Short delStatus,PageDto<Favorite> page) {
//		Assert.notNull(uid, "the uid can not be null");
//		Assert.notNull(cid, "the cid can not be null");
//		Assert.notNull(type, "the type can not be null");
//		Assert.notNull(delStatus, "the delStatus can not be null");
//		page.setRecords(favoriteDao.queryFavorites(uid, cid, type, delStatus,page));
//		page.setTotals(favoriteDao.queryFavoritesCount(uid, cid, type, delStatus));
//		return page;
//	}

//	@Override
//	public Integer updateFavoriteStatus(Integer id,Integer uid,Short status) {
//		return favoriteDao.updateFavorite(id, uid, status);
//	}


}