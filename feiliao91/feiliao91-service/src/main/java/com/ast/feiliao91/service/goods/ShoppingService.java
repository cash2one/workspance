/**
 * @author shiqp
 * @date 2016-01-31
 */
package com.ast.feiliao91.service.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.Shopping;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.ShoppingDto;

public interface ShoppingService {
	/**
	 * 购物车列表
	 * @param page
	 * @param buyCompanyId
	 * @return
	 */
	public PageDto<ShoppingDto> pageShopping(PageDto<ShoppingDto> page, Integer buyCompanyId);
	/**
	 * 创建购物单
	 * @param shopping
	 * @return
	 */
	public Integer createShopping(Shopping shopping);
	/**
	 * 购物车的购物单数
	 * @param buyCompanyId
	 * @param sellCompanyId
	 * @param goodId
	 * @return
	 */
	public Integer countShoppingByCondition(Integer buyCompanyId, Integer sellCompanyId, Integer goodId);
	/**
	 * 删除购物车信息
	 * @param idString
	 * @return
	 */
	public Integer deleteShopping(String idString);
	
	/**
	 * 根据传入id数组分割为id，进行检索数据
	 * @param ids
	 * @return
	 */
	public List<ShoppingDto> queryShopping(String ids);

}
