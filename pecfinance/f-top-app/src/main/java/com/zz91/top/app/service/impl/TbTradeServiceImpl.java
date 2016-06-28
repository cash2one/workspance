/**
 * 
 */
package com.zz91.top.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;
import com.zz91.top.app.dto.PageDto;
import com.zz91.top.app.dto.TbTradeDto;
import com.zz91.top.app.dto.TbTradeSearch;
import com.zz91.top.app.persist.TbTradeMapper;
import com.zz91.top.app.service.TbTradeService;

/**
 * @author mays
 *
 */
@Component("tbTradeService")
public class TbTradeServiceImpl implements TbTradeService {
	
	@Resource
	private TbTradeMapper tbTradeMapper;

	@Override
	public PageDto<TbTradeDto> queryTrade(TbTradeSearch search,
			PageDto<TbTradeDto> page) {
		return null;
	}

	@Override
	public void createFromTaobao(Trade trade) {
		tbTradeMapper.insertByTb(trade);
	}

}
