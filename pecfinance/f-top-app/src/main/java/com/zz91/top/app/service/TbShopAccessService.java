/**
 * 
 */
package com.zz91.top.app.service;

import com.zz91.top.app.dto.TbShopAccessDto;

/**
 * @author mays
 *
 */
public interface TbShopAccessService {

	public Integer createOrUpdateShopToken(TbShopAccessDto dto);
	
}
