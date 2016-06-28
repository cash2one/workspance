package com.zz91.ep.dto.trade;

import java.io.Serializable;

import com.zz91.ep.domain.trade.TradeBuy;

public class BuyMessageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TradeBuy tradeBuy;
	private Integer count;
	public TradeBuy getTradeBuy() {
		return tradeBuy;
	}
	public void setTradeBuy(TradeBuy tradeBuy) {
		this.tradeBuy = tradeBuy;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}
