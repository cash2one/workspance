/**
 * @author shiqp
 * @date 2016-01-19
 */
package com.ast.feiliao91.persist.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.dto.PageDto;

public interface PictureDao {
	/**
	 * 创建图片信息
	 * @param picture
	 * @return
	 */
		public Integer insertPicture(Picture picture);
		/**
		 * 确定产品编号
		 * @param id
		 * @param targetId
		 * @return
		 */
		public Integer updateTargetId(Integer id, Integer targetId,String targetType);
		/**
		 * 根据编号获取图片信息
		 * @param id
		 * @return
		 */
		public Picture queryById(Integer id);
		/**
		 * 获取图片列表
		 * @param page
		 * @param companyId
		 * @param targetType
		 * @return
		 */
		public List<Picture> queryPicList(PageDto<Picture> page,Integer companyId,String targetType);
		/**
		 * 图片数
		 * @param companyId
		 * @param targetType
		 * @return
		 */
		public Integer countPicList(Integer companyId,String targetType);
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
		 * 根据id删除图片
		 * @param id
		 * @return
		 */
		public Integer deletePic(Integer id);
		/**
		 * 根据图片地址查询ID
		 * @param arr
		 * @return
		 */
		public Integer selectByAddr(String arr);
		/**
		 * 根据target_id(goods_id),删图片
		 * @param target_id
		 * @return
		 */
		public Integer deleteAllPicInThisGoods(Integer targetId);
		/**
		 * 根据id更新target_id
		 * @param id,target_id
		 * @return
		 */
		public Integer updateSellPostGoodsPic(Integer picId,Integer oId);
		/**
		 * 后台商品审核时获得商品图片和检测报告
		 * @param goodsId
		 * @param companyId
		 * @return
		 */
		public List<Picture> queryPictureByAdmin(Integer goodsId,
				Integer companyId);
		/**
		 * 后台批量审核产品图片
		 * @param stringToIntegerArray
		 * @param checkPerson
		 * @param status
		 * @return
		 */
		public Integer batchUpdatePicStatus(Integer id,
				String checkPerson, Integer status);
}
