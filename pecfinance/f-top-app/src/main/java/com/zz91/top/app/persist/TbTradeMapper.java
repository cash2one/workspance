/**
 * 
 */
package com.zz91.top.app.persist;

import java.util.Date;

import com.taobao.api.domain.Trade;

/**
 * @author mays
 *
 */
public interface TbTradeMapper {

	
	public Date queryLastCreated( String sellerNick);
	
	public void insertByTb(Trade trade);
}
