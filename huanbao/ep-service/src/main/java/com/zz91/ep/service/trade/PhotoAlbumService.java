package com.zz91.ep.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.PhotoAlbum;
import com.zz91.ep.dto.trade.PhotoAlbumDto;

public interface PhotoAlbumService {
	
	public List<PhotoAlbumDto> querySystemAlbum(Integer cid);
	
	public List<PhotoAlbumDto> queryUserAlbum(Integer cid);
	
	
	
	/**********************/
	
	/**
	 * 根据cid获得用户还能创建的相册数
	 * @param cid
	 * @return
	 */
	public Integer getSurplusAlbum(Integer cid);
	
	/**
	 * 修改相册名称
	 * @param albumId
	 * @return
	 */
	public  void updateAlbumName(Integer albumId,String name);
	
	/**
	 * 删除相册
	 * @param albumId
	 * @return
	 */
	public void  deleteAlbumAndPhoto(Integer albumId, Integer cid) ;
	
	/**
	 * 查询所有相册
	 * @param cid
	 * @return
	 */
	public List<PhotoAlbumDto> queryAlbumByCid(Integer cid,Integer albumId) ;
	
	/**
	 * 创建相册
	 * @param album
	 * @ 
	 */
	public void createAlbum(PhotoAlbum album);

	
	/**
	 * 查询相册名称
	 * @param albumId
	 * @return
	 * @
	 */
	public String queryNameById(Integer albumId);
}
