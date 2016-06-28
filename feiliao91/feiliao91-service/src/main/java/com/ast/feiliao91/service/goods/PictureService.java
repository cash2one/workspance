/**
 * @author shiqp
 * @date 2016-01-19
 */
package com.ast.feiliao91.service.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.dto.PageDto;

public interface PictureService {
	
	final static String TYPE_GOOD = "1";
	
	/**
	 * 创建图片信息
	 * @param picture
	 * @return
	 */
		public Integer createPicture(Picture picture);
		/**
		 * 1、相册上传图片，需要重新创建图片信息
		 * 2、本地上传图片，只需添加产品图片的编号
		 * 判断标准在于有无目标编号
		 * @param pid
		 * @param id
		 */
		public void dealPicture(String pid, Integer id, String targetType);
		/**
		 * //删除原来属于改产品的图片
		 * @param targetId
		 */
		public void deleteAllPicInThisGoods(Integer targetId);
		/**
		 * 图片列表
		 * @param page
		 * @param companyId
		 * @param targetType
		 * @return
		 */
		public PageDto<Picture> pagePictureList(PageDto<Picture> page, Integer companyId, String targetType);
		/**
		 * 根据目标编号、目标类型、目标用户以及图片张数来获取图片列表
		 * @param targetId
		 * @param targetType
		 * @param companyId
		 * @param size
		 * @return
		 */
		public List<Picture> queryPictureByCondition(Integer targetId,String targetType,Integer companyId,Integer size);
		/**
		 * 
		 * @param picAddress　pic　的id　数组字符串
		 * @return
		 */
		public List<String> selecPicById(String picAddress);
		/**
		 * 根据id删除图片
		 */
		public Integer deletePic(Integer id);
		/**
		 * 根据图片地址搜索ID
		 * @param picAddress
		 * @return
		 */
		public List<String> selectByAddr(List<String> picAddress);
		/**
		 * 根据ID更新TargetId为0（针对删除修改产品时的已存在的图片）
		 * @param Id
		 * @return
		 */
		public Integer updateTargetIdZeroById(Integer Id);
		/**
		 * 后台商品审核时获得商品图片和检测报告
		 * @param goodsId
		 * @param companyId
		 * @return
		 */
		public List<Picture> queryPictureByAdmin(Integer goodsId,Integer companyId);
		
		/**
		 * 后台批量审核图片
		 * @param ids
		 * @param checkPerson
		 * @param status
		 * @return
		 */
		public String batchUpdatePicStatus(String ids, String checkPerson,Integer status);
}