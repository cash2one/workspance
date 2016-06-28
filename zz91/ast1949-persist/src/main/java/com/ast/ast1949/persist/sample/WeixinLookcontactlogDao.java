package com.ast.ast1949.persist.sample;

import com.ast.ast1949.domain.sample.WeixinLookcontactlog;


public interface WeixinLookcontactlogDao {
	
	public WeixinLookcontactlog queryById(String account, Integer targetId);	
	
	/**
	 * 手机站查看的个数
	 * @param account
	 * @return
	 */
	public Integer countClick(String account);
}