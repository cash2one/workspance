/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.Photo;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
public interface PhotoDao {

    /**
     * 插入图片信息
     */
    public Integer insertPhoto(Photo photo);
    
    /**
     * 根据相册id查找相册下的所以图片(未分组的相册id为0)
     */
//    public List<Photo> queryPhotoByAlbumId(Integer photoAlbumId, Integer uid, Integer cid);
    
    /**
     * 根据图片id删除图片信息
     */
    public Integer deletePhotoById(Integer id);
    
    /**
     * 根据图片id更新删除状态图片信息
     */
    public Integer deletePhotoStatusById(Integer id);
    /**
     * 根据图片id取消删除图片信息
     */
    public Integer cancelDeletePhotoStatusById(Integer id);
    /**
     * 根据图片类型和相应Id查找相应图片
     */
    public List<Photo> queryPhotoByTargetType(String type, Integer id);

    /**
     * 更新所有资讯图片的targetId,仅用在后台发布最新资讯时使用
     * @param targetId
     * @return
     */
	public Integer updateNewsPhotoByTargetId(Integer targetId);

	public Integer batchUpdatePicStatus(Integer[] stringToIntegerArray, String checkStatus, String unpassReason);

    /**
     * 根据图片类型和相应CId查找相应图片
     */
//    public List<Photo> queryPhotosByCid(String type, Integer cid, PageDto<Photo> page);
    
    /**
     * 根据图片类型和相应CId查找相应图片数
     */
//    public Integer queryPhotosByCidCount(String type, Integer cid);

    /**
     * 更新图片类型Id
     * @param id
     * @param sid
     * @return
     */
//	public Integer updatePhotoTargetIdById(Integer id, Integer sid);

//	public Integer deletePhotoTargetIdById(String type,Integer id);
}