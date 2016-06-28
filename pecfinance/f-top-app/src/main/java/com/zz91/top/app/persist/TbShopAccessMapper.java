package com.zz91.top.app.persist;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zz91.top.app.domain.TbShopAccess;

public interface TbShopAccessMapper {

	public Integer countAccess(String taobaoUserId);
	
	public void insert(TbShopAccess shop);
	
	public Integer update(TbShopAccess shop);
	
	public List<TbShopAccess> queryAccess(@Param("fromId") Integer fromId, 
			@Param("limit") Integer limit);
	
}
