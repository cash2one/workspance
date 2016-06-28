/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.persist.market.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.market.MarketDao;

@Component("marketDao")
public class MarketDaoImpl extends BaseDaoSupport implements MarketDao {
	final static String SQL_PREFIX="market";
	@Override
	public Integer countMarketByCondition(String area, String industry, String category) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("area", area);
		map.put("industry", industry);
		map.put("category", category);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countMarketByCondition"), map);
	}
	@Override
	public Integer insertMarket(Market market) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertMarket"), market);
	}
	@Override
	public Market queryMarketById(Integer id) {
		return (Market) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMarketById"), id);
	}
	@Override
	public Market queryMarketByWords(String words) {
		return (Market) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMarketByWords"), words);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Market> queryMarketByCondition(String industry, String area, Integer size) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("industry", industry);
		map.put("size", size);
		map.put("area", area);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryMarketByCondition"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Market> queryAllMarket(PageDto<Market> page, Integer type) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		map.put("size", type);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllMarket"), map);
	}
	@Override
	public Integer countAllMarket(PageDto<Market> page, Integer type) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		map.put("size", type);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countAllMarket"), map);
	}
	@Override
	public Integer countMarketByProvice(String area) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countMarketByProvice"), area);
	}
	@Override
	public Integer countCompanyByProvice(String area) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countCompanyByProvice"), area);
	}
	@Override
	public Integer countProductByProvice(String area) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countProductByProvice"), area);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Market> queryMarketByProOrCate(String area, String category) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("area", area);
		map.put("category", category);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryMarketByProOrCate"), map);
	}
    @SuppressWarnings("unchecked")
    @Override
	public List<Market> pageQueryMarket(Market market, PageDto<Market> page,Integer hasPic) {

		Map<String, Object> map=new HashMap<String, Object>();
		map.put("market", market);
		map.put("page", page);
		map.put("hasPic", hasPic);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "pageQueryMarket"), map);
	}
	@Override
	public Integer pageQueryMarketCount(Market market,Integer hasPic) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("market", market);
		map.put("hasPic", hasPic);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "pageQueryMarketCount"), map);
	}
	@Override
	public Integer updateMarkt(Market market) {
		
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateMarkt"), market);
	}
	@Override
	public Integer updateCompanyByMarketId(Integer id, Integer companyNum) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("id", id);
		map.put("companyNum", companyNum);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCompanyByMarketId"), map);
	}
	@Override
	public Integer sumProductNum() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "sumProductNum"));
	}
	

}
