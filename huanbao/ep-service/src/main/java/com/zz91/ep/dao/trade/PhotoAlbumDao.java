package com.zz91.ep.dao.trade;


import java.util.List;

import com.zz91.ep.domain.trade.PhotoAlbum;

public interface PhotoAlbumDao {
	/**
	 * 根据cid获得用户创建的相册数
	 * @param cid
	 * @return
	 */
	public Integer getAlbumNum(Integer cid) ;
	
	/**
	 * 修改相册名称
	 * @param albumId
	 * @return
	 */
	public  Integer updateAlbumName(Integer albumId,String name);
	
	/**
	 * 删除相册
	 * @param albumId
	 * @return
	 */
	public Integer deleteAlbumAndPhoto(Integer albumId);
	
	/**
	 * 查询所有相册
	 * @param cid
	 * @return
	 */
	public List<PhotoAlbum> queryAlbumByCid(Integer cid,Integer albumId);
	
	/**
	 * 添加相册
	 * @param album
	 * @return
	 * @throws Exception 
	 */
	public Integer insertAlbum(PhotoAlbum album) ;
	
	/**
	 * 查询相册名称
	 * @param albumId
	 * @return
	 * @throws Exception
	 */
	public String queryNameById(Integer albumId);
}
