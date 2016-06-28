package com.ast.feiliao91.persist.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.trade.TradeLog;
import com.ast.feiliao91.domain.trade.TradeLogSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.TradeLogDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.trade.TradeLogDao;

@Component("tradeLogDao")
public class TradeLogDaoImpl extends BaseDaoSupport implements TradeLogDao{

	final static String SQL_FIX = "tradeLog";
	
	@Override
	public Integer insert(TradeLog tradeLog) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), tradeLog);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeLog> queryTradeLogList(PageDto<TradeLogDto> page,
			TradeLogSearch search){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("search", search);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryTradeLogList"), map);
	}
	
	@Override
	public Integer countTradeLogList(TradeLogSearch search){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countTradeLogList"), map);
	}
}
