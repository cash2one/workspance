package com.ast.ast1949.persist.photo;

import java.util.List;

import com.ast.ast1949.domain.photo.PhotoAbum;
import com.ast.ast1949.dto.PageDto;

public interface PhotoAbumDao {
	
	public Integer insert(PhotoAbum photoAbum);
	
	/**
	 * 查找photoAbum
	 * @param albumId 相册类型 2:公司相册 ，3：产品相册1  4：产品相册2 5：互助相册
	 * @param companyId 公司Id
	 * @return
	 */
	public List<PhotoAbum> queryPhotoAbumList(PageDto<PhotoAbum>page,Integer albumId,Integer companyId);
	
	/**
	 * 统计总数
	 * @param albumId 相册类型  2:公司相册 ，3：产品相册1  4：产品相册2  5：互助相册
	 * @param companyId 公司Id
	 * @return
	 */
	
	public Integer queryPhotoAbumListCount(Integer albumId,Integer companyId);
	
	/**
	 * 删除相册中的图片
	 * @param id
	 * @return
	 */
	public Integer delPhotoAbum(Integer id);
	
	public List<PhotoAbum> queryList(Integer albumId,Integer companyId);
	
	/**
	 * 搜索出不包含某个相册类型的photoAbum
	 * @param page
	 * @param albumId  不包含的相册类型Id 相册类型 2:公司相册 ，3：产品相册 4：互助相册
	 * @param companyId
	 * @return
	 */
	public List<PhotoAbum> queryPagePhotoAbum(PageDto<PhotoAbum>page,Integer albumId,Integer companyId);
	
	/**
	 * 统计出不包含某个相册类型的总数
	 * @param page
	 * @param albumId  不包含的相册类型Id 相册类型 2:公司相册 ，3：产品相册 4：互助相册
	 * @param companyId
	 * @return
	 */
	public Integer queryPagePhotoAbumCount(Integer albumId,Integer companyId);
	
	/**
	 * 搜索客户相册的第一张
	 * @param albumId 相册类型 2:公司相册 ，3：产品相册 4：互助相册
	 * @param companyId
	 * @return
	 */
	public PhotoAbum queryPhotoAbum(Integer albumId,Integer companyId);

	/**
	 * 通过id查找图片
	 * @param id
	 * @return
	 */
	public PhotoAbum queryPhotoAbumById(Integer id);
	
	/**
	 * 更改图片是否有水印  isMark 1:有水印  0:无水印
	 * @param id
	 * @return
	 */
	public Integer updateIsMarkById(Integer id);
	
	/**
	* 上传图片时，更新公司id
	* @param id
	* @param companyId
	*/
	public void updateCompanyIdById(Integer id,Integer companyId);

}
