/**
 * @author kongsj
 * @date 2015年5月9日
 * 
 */
package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustSell;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustSellDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.trust.TrustSellDao;
@Component("trustSellDao")
public class TrustSellDaoImpl extends BaseDaoSupport implements TrustSellDao {

	final static String SQL_FIX = "trustSell";

	@Override
	public Integer insert(TrustSell trustSell) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), trustSell);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustSellDto> queryByCondition(TrustBuySearchDto searchDto,PageDto<TrustSellDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

	@Override
	public Integer queryCountByCondition(TrustBuySearchDto searchDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}

	@Override
	public Integer updateByStatus(Integer id, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateStatusByAdmin"), map);
	}
	
	@Override
	public TrustSell queryById(Integer id){
		return (TrustSell) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustSellDto> querySupplyByCondition(TrustSell sell,PageDto<TrustSellDto> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sell", sell);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "querySupplyByCondition"), map);
	}

	@Override
	public Integer countSupplyByCondition(TrustSell sell) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sell", sell);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countSupplyByCondition"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryBuyIdByCompanyId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryBuyIdByCompanyId"), companyId);
	}
	
	@Override
	public Integer countByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByCompanyId"), companyId);
	}

}
