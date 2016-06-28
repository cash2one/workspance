package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustTrade;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustTradeDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.trust.TrustTradeDao;
@Component("trustTradeDao")
public class TrustTradeDaoImpl extends BaseDaoSupport implements TrustTradeDao{
	final static String SQL_FIX = "trustTrade";

	@Override
	public Integer createTrustTrade(TrustTrade trustTrade) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insertTrustTrade"), trustTrade);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustTrade> queryTradeInfo(Integer isDel, PageDto<TrustTradeDto> page, TrustBuySearchDto searchDto) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("isDel", isDel);
		map.put("page", page);
		map.put("searchDto", searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryTradeInfo"), map);
	}

	@Override
	public Integer countTradeInfo(Integer isDel, TrustBuySearchDto searchDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isDel", isDel);
		map.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countTradeInfo"), map);
	}

	@Override
	public Integer updateTrustTradeInfo(TrustTrade trustTrade) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateTrustTradeInfo"), trustTrade);
	}

	@Override
	public TrustTrade queryOneTrade(Integer buyId) {
		return (TrustTrade) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryOneTrade"), buyId);
	}

}
