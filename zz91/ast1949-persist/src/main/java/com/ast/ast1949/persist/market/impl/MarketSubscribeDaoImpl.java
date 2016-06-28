package com.ast.ast1949.persist.market.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.market.MarketSubscribe;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketSearchDto;
import com.ast.ast1949.dto.market.MarketSubscribeDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.market.MarketSubscribeDao;

@Component("marketSubscribeDao")
public class MarketSubscribeDaoImpl extends BaseDaoSupport implements MarketSubscribeDao{
	
	final static String SQL_FIX = "marketSubscribe";
	
	@Override
	public Integer insert(MarketSubscribe marketSubscribe) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), marketSubscribe);
	}

	@Override
	public Integer updateToDel(Integer id, Integer companyId) {
		Map<String , Object> map =new HashMap<String, Object>();
		map.put("id", id);
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateToDel"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketSubscribe> queryByCompanyId(Integer companyId, Integer size) {
		Map<String , Object> map =new HashMap<String, Object>();
		map.put("size", size);
		map.put("companyId", companyId);
		return  getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketSubscribe> queryByAdmin(MarketSearchDto searchDto, PageDto<MarketSubscribeDto> page) {
		Map<String , Object> map =new HashMap<String, Object>();
		map.put("page", page);
		map.put("search", searchDto);
		return  getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByAdmin"), map);
	}

	@Override
	public Integer queryCountByAdmin(MarketSearchDto searchDto) {
		Map<String , Object> map =new HashMap<String, Object>();
		map.put("search", searchDto);
		return  (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByAdmin"), map);
	}
	
}
