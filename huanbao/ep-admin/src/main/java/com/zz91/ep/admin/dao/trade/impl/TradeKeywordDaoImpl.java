/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-24 下午07:29:22
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.TradeKeywordDao;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.dto.PageDto;

@Component("tradeKeywordDao")
public class TradeKeywordDaoImpl extends BaseDao implements TradeKeywordDao {

	final static String SQL_PREFIX = "tradeKeyword";
	 
	@Override
	public Integer insertTradeKeyword(TradeKeyword keyword) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTradeKeyword"), keyword);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeKeyword> queryTradeKeyword(String keyword,
			PageDto<TradeKeyword> page, Short status, String start, String end) {
		 Map<String, Object> root = new HashMap<String, Object>();
		 root.put("keyword", keyword);
		 root.put("page", page);
		 root.put("status", status);
		 root.put("start", start);
		 root.put("end", end);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTradeKeyword"), root);
	}

	@Override
	public TradeKeyword queryTradeKeywordById(Integer id) {
		return(TradeKeyword) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTradeKeywordById"), id);
	}

	@Override
	public Integer queryTradeKeywordCount(String keyword, Short status,
			String start, String end) {
		 Map<String, Object> root = new HashMap<String, Object>();
		 root.put("keyword", keyword);
		 root.put("status", status);
		 root.put("start", start);
		 root.put("end", end);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTradeKeywordCount"), root);
	}

	@Override
	public Integer updateStatusById(Integer id, Short status) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStatusById"), map);
	}

	@Override
	public Integer updateTradeKeyword(TradeKeyword keyword) {
		return (Integer)getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTradeKeyword"), keyword);
	}

}
