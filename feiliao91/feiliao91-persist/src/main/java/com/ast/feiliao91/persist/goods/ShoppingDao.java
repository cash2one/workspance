/**
 * @author shiqp
 * @date 2016-01-31
 */
package com.ast.feiliao91.persist.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.Shopping;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.ShoppingDto;

public interface ShoppingDao {
	/**
	 * 创建购物清单
	 * @param shopping
	 * @return
	 */
	public Integer insertShoppingMenu(Shopping shopping);
	/**
	 * 获取卖家列表
	 * @param buyCompanyId
	 * @param page
	 * @return
	 */
	public List<Integer> querySellCompanyId(PageDto<ShoppingDto> page, Integer buyCompanyId);
	/**
	 * 获取卖家列表总数
	 * @param buyCompanyId
	 * @return
	 */
	public Integer countSellCompanyId(Integer buyCompanyId);
	/**
	 *  获取同一个买家在某卖家处的购物单 
	 *  同一个用户同一个产品情况
	 * @param sellCompanyId
	 * @param buyCompanyId
	 * @param goodId
	 * @return
	 */
	public List<Shopping> queryShoppingByBothId(Integer sellCompanyId, Integer buyCompanyId, Integer goodId);
	/**
	 * 买方更新购物单信息
	 * @param id
	 * @param attribute
	 * @param number
	 * @param money
	 * @return
	 */
	public Integer updateShoppingInfo(Integer id, String attribute, String number, String money);
	/**
	 * 购物单删除状态修改
	 * @param id
	 * @param isDel
	 * @return
	 */
	public Integer updateIsDel(Integer id, Integer isDel);
	/**
	 * 购物车中购物单数
	 * @param buyCompanyId
	 * @param sellCompanyId
	 * @param goodId
	 * @return
	 */
	public Integer countShoppingByCondition(Integer buyCompanyId, Integer sellCompanyId, Integer goodId);
	
	public Shopping queryById(Integer id);
}
