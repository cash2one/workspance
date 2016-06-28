/*
 * 文件名称：PhotoService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-18 下午2:29:09
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade;

import java.util.List;
import java.util.Map;

import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.SupplyMessageDto;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Service层
 * 模块描述：相片处理接口
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface PhotoService {
	
	public final static String TARGET_SUPPLY="supply";
	
	public final static String TARGET_BUY="buy";
	
	public final static String TARGET_COMPANY="company";
	
	public final static String TARGET_LOGO="logo";
	
	/**
	 * 
	 * 函数名称：queryPhotoByTargetType
	 * 功能描述：根据相册类型查询图片信息
	 * 输入参数：@param type 相册类型
	 * 　　　　　@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public List<Photo> queryPhotoByTargetType(String targetType, Integer targetId, Integer size);
    
    /**
     * 
     * 函数名称：queryPhotoByTargetType
     * 功能描述：查看企业形象图片
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
    public List<Photo> queryPhotoByTargetType(String targetType, Integer targetId);
    
    /**
     * 
     * 函数名称：queryPhotosByCidCount
     * 功能描述：
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
//    public Integer queryPhotosByCidCount( Integer cid,Integer albumId);
    
    /**
     * 
     * 函数名称：createPhoto
     * 功能描述：创建图片信息
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
    public Integer createPhoto(Photo photo);
    
    /**
     * 
     * 函数名称：deletePhotoTargetIdById
     * 功能描述：删除图片
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
    public Integer deletePhotoTargetIdById(String type, Integer id);
    
    /**
     * 
     * 函数名称：updatePhotoTargetIdById
     * 功能描述：修改信息和图片的引用关系
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     * 			2013/1/7		马元生				1.0.1		更改无意义参数名, sid to targetId
     */
    public Integer updatePhotoTargetIdById(Integer id, Integer targetId, String targetType);
    
    /**
     * 
     * 函数名称：pagePhotosByCid
     * 功能描述：查询图片
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
    public PageDto<Photo> pagePhotosByCid( Integer cid, PageDto<Photo> page, 
    		Integer albumId, Integer queryTarget);
    
    /**
     * 根据图片id删除图片信息
     */
    public Integer deletePhotoById(Integer id, String path);
    
    
    /**
     * 统计相册图片数
     * @param palid
     * @return
     */
    public Integer queryPhotosByPalidCount(Integer palid,Integer cid);
    
    /**
     * 移动图片
     * @param albumId
     * @param pid
     */
    public void updatePhotoAlbumId(Integer albumId,Integer pid,String type);
    
    /**
     * 取得photo_album_id 为0 的相册信息
     */
//    public Map<String, Object> queryPhotoAlbumByCid(Integer cid,Integer albumId,String str);
    
    public void updateTargetId(Integer[] photoIdArr, Integer targetId);

    /**
     * 检索一张最新图片
     * @param type
     * @param id
     * @return
     */
	public Photo queryPhotoByTypeAndId(String type, Integer id);
	 /**
	 * 
	 * 函数名称：queryPhotoByIdcreatePhoto
	 * 功能描述：根据Id查找相应图片
	 * 输入参数：@param id 
	 * 　
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/06/13　　 方潮　　 　　 　   1.0.0　　 　　 创建方法函数
	 */
	 public Photo queryPhotoById(Integer id);

	 /**
	  * 传入供求列表，根据id搜索供求所上传的图片数量 使用于，生意管家 - 供求管理 页面 查看图片上传数量
	  * @param records
	  * @return
	  */
	public Map<Integer, Integer>  countPhotoBySupplyId(List<SupplyMessageDto> records);

	/**
	 * 根据Id查找相应图片列表 
	 * @param type
	 * @param id
	 * @param string2
	 * @return
	 */
	public List<Photo> queryPhotoListByTypeAndId(String type, Integer id, String checkStatus);
	
    /**
     * 
     * 函数名称：queryPhotoByTargetTypePass
     * 功能描述：查看企业形象图片-审核已经通过
     */
	List<Photo> queryPhotoByTargetTypePass(String targetType, Integer targetId);

	
	/**
	 * 更新图片的审核状态值
	 * @param id
	 * @param checkStatus
	 */
	public Integer  updateCheckStatus(Integer id, String checkStatus);
}