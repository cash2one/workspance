/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.persist.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.GoodsCategory;

public interface GoodsCategoryDao {
	/**
	 * 创建产品类别
	 * @param goodCategory
	 * @return
	 */
	public Integer insertGoodsCategory(GoodsCategory goodCategory);
	/**
	 * 获取父类下的所有子类
	 * @param parentCode
	 * @return
	 */
	public List<GoodsCategory> queryCategoryByParentCode(String parentCode);
	/**
	 * 模糊匹配类别
	 * @param keyword 关键字
	 * @param size  条数
	 * @param length 位数(比如，二级以上才有该关键字的类别，length=8)
	 * @return
	 */
	public List<GoodsCategory> queryGoodsCategoryByKeyword(String keyword, Integer size, Integer length);
	/**
	 * 检索所有的产品类别（未删除）
	 * @return
	 */
	public List<GoodsCategory> queryAllGoodsCategory();
	
	/**
	 * 根据code获得GoodsCategory
	 * @param Code
	 * @return
	 */
	public GoodsCategory queryCategoryByCode(String code);
}
