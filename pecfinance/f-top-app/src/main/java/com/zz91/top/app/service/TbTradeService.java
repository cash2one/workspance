package com.zz91.top.app.service;

import com.taobao.api.domain.Trade;
import com.zz91.top.app.dto.PageDto;
import com.zz91.top.app.dto.TbTradeDto;
import com.zz91.top.app.dto.TbTradeSearch;


public interface TbTradeService {

	public PageDto<TbTradeDto> queryTrade(TbTradeSearch search, PageDto<TbTradeDto> page); 
	
	public void createFromTaobao(Trade trade);
	
}
