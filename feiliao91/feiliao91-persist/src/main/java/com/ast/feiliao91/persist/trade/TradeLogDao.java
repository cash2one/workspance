package com.ast.feiliao91.persist.trade;

import java.util.List;

import com.ast.feiliao91.domain.trade.TradeLog;
import com.ast.feiliao91.domain.trade.TradeLogSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.TradeLogDto;

public interface TradeLogDao {
	/**
	 * 插入交易明细表
	 * @param tradeLog
	 * @return
	 */
	public Integer insert(TradeLog tradeLog);
	
	/**
	 * 获得的交易明细列表
	 * @param page
	 * @param search
	 * @return
	 */
	public List<TradeLog> queryTradeLogList(PageDto<TradeLogDto> page,
			TradeLogSearch search);
	
	/**
	 * 获得的交易明细列表数量
	 * @param search
	 * @return
	 */
	public Integer countTradeLogList(TradeLogSearch search);
}
