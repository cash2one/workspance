/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.service.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.GoodsCategory;
import com.ast.feiliao91.dto.ExtTreeDto;

public interface GoodsCategoryService { 
	/**
	 * 检索所有产品类别
	 * @return
	 */
	public List<GoodsCategory> queryAllGoodsCategory();
	/**
	 * 检索关键字列表
	 * @param keyword
	 * @param size
	 * @param length
	 * @return
	 */
	public List<GoodsCategory> queryGoodsCategoryByKeyword(String keyword, Integer size, Integer length);
	
	/**
	 * 后台树形图
	 */
	public List<ExtTreeDto> childByAdmin(String parentCode);
}
