/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-24 下午07:26:38
 */
package com.zz91.ep.admin.service.trade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.TradeKeywordDao;
import com.zz91.ep.admin.service.trade.TradeKeywordService;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.dto.PageDto;
import com.zz91.util.Assert;

@Component("tradeKeywordService")
public class TradeKeywordServiceImpl implements TradeKeywordService {

	@Resource
	private TradeKeywordDao tradeKeywordDao;
	
	@Override
	public Integer createTradeKeyword(TradeKeyword keyword) {
		if (keyword.getStatus()==null){
			keyword.setStatus(TradeKeywordDao.STATUS_N);
		}
		return tradeKeywordDao.insertTradeKeyword(keyword);
	}

	@Override
	public PageDto<TradeKeyword> pageTradeKeyword(String keyword,
			PageDto<TradeKeyword> page, Short status, String start, String end) {
		page.setRecords(tradeKeywordDao.queryTradeKeyword(keyword, page, status, start, end));
		page.setTotals(tradeKeywordDao.queryTradeKeywordCount(keyword, status, start, end));
		return page;
	}

	@Override 
	public TradeKeyword queryTradekeywordById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return tradeKeywordDao.queryTradeKeywordById(id);
	}

	@Override
	public Integer updateStatusById(Integer id, Short status) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(status, "the status can not be null");
		return tradeKeywordDao.updateStatusById(id, status);
	}

	@Override
	public Integer updateTradeKeywordById(TradeKeyword keyword) {
		if (keyword.getStatus()==null){
			keyword.setStatus(TradeKeywordDao.STATUS_N);
		}
		return tradeKeywordDao.updateTradeKeyword(keyword);
	}

}
