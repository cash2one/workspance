package com.zz91.top.app.dto;

import java.io.Serializable;

import com.zz91.top.app.domain.TbTrade;

public class TbTradeDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1901489368289255303L;
	
	private TbTrade trade;

	public TbTrade getTrade() {
		return trade;
	}

	public void setTrade(TbTrade trade) {
		this.trade = trade;
	}
	
	

}
