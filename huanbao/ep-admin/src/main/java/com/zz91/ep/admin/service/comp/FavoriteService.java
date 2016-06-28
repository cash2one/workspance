/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.comp;

/**
 * @author totly
 *
 * created on 2011-9-15
 */
public interface FavoriteService {
	
	//收藏类型(0:未知类型 1:新闻 2:展会 3:供应产品 4:求购产品)
	public final static Short TYPE_UNKOWN=0;
	public final static Short TYPE_NEWS=1;
	public final static Short TYPE_EXHIBIT=2;
	public final static Short TYPE_SUPPLY=3;
	public final static Short TYPE_BUY=4;
	public final static Short DELETE_TURE=1;
	public final static Short DELETE_FALSE=0;
	
//	public PageDto<Favorite> queryFavorites(Integer uid,Integer cid,Short type,Short delStatus,PageDto<Favorite> page);
	
//	public Integer updateFavoriteStatus(Integer id,Integer uid,Short status);
	
//	public Integer createFavorite(Favorite favorite);
}