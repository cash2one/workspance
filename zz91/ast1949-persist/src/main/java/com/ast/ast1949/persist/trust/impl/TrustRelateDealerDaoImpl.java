/**
 * @author shiqp
 * @date 2015-05-14
 */
package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustRelateDealer;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.trust.TrustRelateDealerDao;

@Component("trustRelateDealerDao")
public class TrustRelateDealerDaoImpl extends BaseDaoSupport implements TrustRelateDealerDao {
	
	final static String SQL_FIX = "trustRelateDealer";

	@Override
	public Integer queryRelateDealerByBuyNo(String buyNo) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryRelateDealerByBuyNo"), buyNo);
	}

	@Override
	public Integer insertRelateDealer(TrustRelateDealer dealer) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insertRelateDealer"), dealer);
	}

	@Override
	public Integer updateRelateDealer(Integer dealerId, String buyNo) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("dealerId", dealerId);
		map.put("buyNo", buyNo);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateRelateDealer"), map);
	}
}
