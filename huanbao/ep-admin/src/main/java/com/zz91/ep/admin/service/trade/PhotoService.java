/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.Photo;

/**
 * @author totly
 *
 * created on 2011-9-15
 */
public interface PhotoService {
	final static String CHECK_STATUS_PASS = "1";
	final static String CHECK_STATUS_NO_PASS = "2";
	final static String CHECK_STATUS_WAIT = "0";
	final static String IS_DEFAULT = "1";
	final static String IS_NOT_DEFAULT = "0";
	
	public final static String TARGET_SUPPLY="supply";
	public final static String TARGET_BUY="buy";
	public final static String TARGET_COMPANY="company";
	public final static String TARGET_LOGO="logo";

    /**
     * 创建相片信息
     */
    public Integer createPhoto(Photo photo);

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
     * 查询企业形象照片
     */
    public List<Photo> queryPhotoByTargetType(String type, Integer id);
    
    /**
     * 更新所有资讯图片的targetId,仅用在后台发布最新资讯时使用
     * @param targetId
     * @return
     */
    public Integer updateNewsPhotoByTargetId(Integer targetId);

	public Integer batchUpdatePicStatus(String idArrayStr, String string, String checkStatusPass);

	public Integer queryPhotoByTargetType(String type, Integer productId, String unpassReason, String checkStatusNoPass);
    
    /**
     * 查询公司照片
     */
//    public PageDto<Photo> pagePhotosByCid(String type, Integer cid, PageDto<Photo> page);
    
//    public Integer queryPhotosByCidCount(String type, Integer cid);

    /**
     * 更新图片类型id
     * @param id
     * @param sid
     */
//	public Integer updatePhotoTargetIdById(Integer id, Integer sid);

//	public Integer deletePhotoTargetIdById(String type, Integer id);
}