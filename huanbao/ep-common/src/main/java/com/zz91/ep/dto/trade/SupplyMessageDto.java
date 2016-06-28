package com.zz91.ep.dto.trade;

import java.io.Serializable;

import com.zz91.ep.domain.trade.TradeSupply;

public class SupplyMessageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TradeSupply getTradeSupply() {
		return tradeSupply;
	}
	public void setTradeSupply(TradeSupply tradeSupply) {
		this.tradeSupply = tradeSupply;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	private TradeSupply tradeSupply;
	private Integer count;//新留言数
	//private List<Photo> photoList;//图片信息
}
