/**
 * 
 */
package com.zz91.top.app.persist;

import com.taobao.api.domain.Shop;

/**
 * @author mays
 *
 */
public interface TbShopMapper {

	public Integer insertByTb(Shop shop);
	
	public Integer countShopBySid(Long sid);
}
