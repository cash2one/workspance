/*
 * 文件名称：PhotoDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-18 下午2:34:16
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade;

import java.util.List;
import java.util.Map;

import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.PhotoAlbumDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：相片相关操作接口
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface PhotoDao {

	/**
	 * 函数名称：queryPhotoByTargetType
	 * 功能描述：根据相片类型查询公司相册信息
	 * 输入参数：@param type 相册类型
	 * 　　　　　@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/06/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<Photo> queryPhotoByTargetType(String targetType, Integer targetId,
			Integer size);
	
	public Integer    queryPhotoCountByTargetType(String targetType, Integer targetId);
	
	  /**
     * 插入图片信息
     */
    public Integer insertPhoto(Photo photo);
    
    
    public Integer updatePhotoTargetIdById(Integer id, Integer targetId, String targetType);

	public Integer deletePhotoTargetIdById(String type,Integer id);
	
	/**
	 * 
	 * 函数名称：queryPhotosByCidCount
	 * 功能描述：根据图片类型和相应CId查找相应图片数
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryPhotosByCidCount(Integer cid,Integer albumId, Integer queryTarget);

	/**
	 * 
	 * 函数名称：queryPhotosByCid
	 * 功能描述：根据图片类型和相应CId查找相应图片
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public List<Photo> queryPhotosByCid( Integer cid, PageDto<Photo> page,Integer albumId, Integer queryTarget);
    
    /**
     * 根据图片id删除图片信息
     */
    public Integer deletePhotoById(Integer id);
    
    /**
     * 根据公司id每个相册查询一张图片
     * @param albumId
     * @return
     */
    public Map<Integer, PhotoAlbumDto>  queryPhotoByCid(Integer cid) ;
    
    public Integer queryPhotosByPalidCount(Integer palid,Integer cid);
    
    /**
     * 根据相册id删除图片信息
     */
	public Integer deletePhotoByPalId(Integer palid);
	
	
	
	/**
	 * 修改图片相册id
	 * @param albumId
	 * @param pid
	 * @return
	 */
	public Integer updatePhotoAlbumId(Integer albumId,Integer pid,String type);
	
	/**
     * 
     * @param type
     * @param cid
     * @param albumId
     * @return
     */
    public String queryPathByAlbumId(Integer cid,Integer albumId);
    
    public Integer updateAlbum(Integer oldAlbum, Integer newAlbum, Integer cid);

    /**
     * 检索 一张最新图片 
     * @param type 
     * @param id
     * @return
     */
    public Photo queryPhotoByTypeAndId(String type, Integer id);
    /**
	 * 
	 * 函数名称：queryPhotoById
	 * 功能描述：根据Id查找相应图片
	 * 输入参数：@param id 
	 * 　
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/06/13　　 方潮　　 　　 　   1.0.0　　 　　 创建方法函数
	 */
    public Photo queryPhotoById(Integer id);

    /**
     * 根据相片类型查询公司相册信息
     * @param type
     * @param id
     * @param checkStatus
     * @return
     */
	public List<Photo> queryPhotoListByTypeAndId(String type, Integer id, String checkStatus);

	public Integer  updateCheckStatus(Integer id, String checkStatus);
}