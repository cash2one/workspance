package com.ast.ast1949.service.photo;

import java.util.List;

import com.ast.ast1949.domain.photo.PhotoAbum;
import com.ast.ast1949.dto.PageDto;

public interface PhotoAbumService {
	
	public Integer insert(PhotoAbum photoAbum);
	/**
	 * 查找photoAbum 产品相册
	 * @param page
	 * @param albumId 相册类型  2:公司相册 ，3：产品相册 4：互助相册
	 * @param companyId 公司ID
	 * @return 
	 */
	public PageDto<PhotoAbum> queryPhotoAbumList(PageDto<PhotoAbum> page,Integer albumId,Integer companyId);
	
	/**
	 * 统计总数
	 * @param albumId 相册类型  相册类型 2:公司相册 ，3：产品相册 4：互助相册
	 * @param companyid 公司ID
	 * @return
	 */
	public Integer queryPhotoAbumListCount(Integer albumId,Integer companyId);
	
	
	public Integer delPhotoAbum(Integer id);
	/**
	 * 根据companyId和albumId 查找photoAbum
	 * @param albumId 相册类型
	 * @param companyId 公司ID
	 * @return
	 */
	public List<PhotoAbum> queryList(Integer albumId,Integer companyId);
	
	/**
	 * 搜索出不包含某个相册类型的photoAbum
	 * @param page
	 * @param albumId  不包含的相册类型Id 相册类型 2:公司相册 ，3：产品相册 4：互助相册
	 * @param companyId
	 * @return
	 */
	public PageDto<PhotoAbum> queryPagePhotoAbum(PageDto<PhotoAbum>page,Integer albumId,Integer companyId);
	
	/**
	 * 查找客户的相册第一张图片
	 * @param albumId
	 * @param companyId 相册类型 2:公司相册 ，3：产品相册 4：互助相册
	 * @return
	 */
	public PhotoAbum queryPhotoAbum(Integer albumId,Integer companyId);

	/**
	 * 通过id 查找相册图片
	 * @param id
	 * @return
	 */
	public PhotoAbum queryPhotoAbumById(Integer id);
	
	/**
	 * 更改图片是否有水印  1:有水印  0:无水印
	 * @param id
	 * @return
	 */
	public Integer updateIsMarkById(Integer id);
	
	/**
	 * 图片打水印
	 * @param companyId 公司Id
	 * @param photoAbumId  photo_abum 表的id
	 * @param path	图片地址
	 * @return
	 */
	public  Boolean waterMark(Integer companyId,Integer photoAbumId, String path); 
	
	/**
	* 上传图片时，更新公司id
	* @param id
	* @param companyId
	*/
	public void updateCompanyIdById(Integer id,Integer companyId);
	
}
