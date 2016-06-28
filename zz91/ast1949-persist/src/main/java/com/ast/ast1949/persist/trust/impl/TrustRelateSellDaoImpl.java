/**
 * @author shiqp
 * @date 2015-05-14
 */
package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustRelateSell;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.trust.TrustRelateSellDao;
@Component("trustRelateSellDao")
public class TrustRelateSellDaoImpl extends BaseDaoSupport implements TrustRelateSellDao {
	
	final static String SQL_FIX = "trustRelateSell";

	@Override
	public Integer insert(TrustRelateSell trustRelateSell) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), trustRelateSell);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustRelateSell> querySellsByBuyId(Integer buyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "querySellsByBuyId"), buyId);
	}
	
	@Override
	public TrustRelateSell queryBySellId(Integer sellId) {
		return (TrustRelateSell) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryBySellId"), sellId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> queryByCompanyIdAndBuyId(Integer companyId,Integer buyId) {
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("buyId", buyId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCompanyIdAndBuyId"), map);
	}
}
